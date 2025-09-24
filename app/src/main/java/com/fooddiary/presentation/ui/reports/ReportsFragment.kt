package com.fooddiary.presentation.ui.reports

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fooddiary.R
import com.fooddiary.databinding.FragmentReportsBinding
import com.fooddiary.presentation.viewmodel.ReportsViewModel
import com.fooddiary.presentation.viewmodel.TimeRange
import com.fooddiary.presentation.viewmodel.ReportType
import com.fooddiary.presentation.viewmodel.ExportFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReportsFragment : Fragment() {

    private var _binding: FragmentReportsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportsViewModel by viewModels()

    private lateinit var timeRangeAdapter: ArrayAdapter<String>
    private lateinit var reportTypeAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapters()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupAdapters() {
        // Time Range Adapter
        timeRangeAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            TimeRange.values().map { it.description }
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerTimeRange.setAdapter(timeRangeAdapter)

        // Report Type Adapter
        reportTypeAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            viewModel.availableReportTypes.map { it.displayName }
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerReportType.setAdapter(reportTypeAdapter)
    }

    private fun setupClickListeners() {
        // Time range selection
        binding.spinnerTimeRange.setOnItemClickListener { _, _, position, _ ->
            val selectedRange = TimeRange.values()[position]
            viewModel.updateTimeRange(selectedRange)
        }

        // Report type selection
        binding.spinnerReportType.setOnItemClickListener { _, _, position, _ ->
            val selectedType = viewModel.availableReportTypes[position]
            viewModel.updateReportType(selectedType)
        }

        // Generate report button
        binding.btnGenerateReport.setOnClickListener {
            viewModel.generateReport()
        }

        // Share report button
        binding.btnShareReport.setOnClickListener {
            viewModel.shareReport()
        }

        // Export buttons
        binding.btnExportPdf.setOnClickListener {
            viewModel.exportReport(ExportFormat.PDF)
        }

        binding.btnExportCsv.setOnClickListener {
            viewModel.exportReport(ExportFormat.CSV)
        }

        binding.btnExportText.setOnClickListener {
            viewModel.exportReport(ExportFormat.TEXT)
        }

        // Clear report button
        binding.btnClearReport.setOnClickListener {
            viewModel.clearGeneratedReport()
        }

        // Info buttons
        binding.btnInfoComprehensive.setOnClickListener {
            showReportTypeInfo(ReportType.COMPREHENSIVE)
        }

        binding.btnInfoMedical.setOnClickListener {
            showReportTypeInfo(ReportType.MEDICAL_SUMMARY)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Observe UI state
                launch {
                    viewModel.uiState.collect { state ->
                        updateUI(state)
                    }
                }

                // Observe time range
                launch {
                    viewModel.selectedTimeRange.collect { timeRange ->
                        binding.spinnerTimeRange.setText(timeRange.description, false)
                    }
                }

                // Observe report type
                launch {
                    viewModel.selectedReportType.collect { reportType ->
                        binding.spinnerReportType.setText(reportType.displayName, false)
                    }
                }
            }
        }
    }

    private fun updateUI(state: ReportsViewModel.ReportsUiState) {
        // Update summary statistics
        binding.tvTotalSymptoms.text = state.totalSymptoms.toString()
        binding.tvTotalFoodEntries.text = state.totalFoodEntries.toString()
        binding.tvSignificantPatternsCount.text = state.significantPatternsCount.toString()

        // Update descriptions
        binding.tvTimeRangeDescription.text = state.timeRangeDescription
        binding.tvReportTypeDescription.text = state.reportTypeDescription

        // Update report preview
        if (state.reportPreview.isNotEmpty()) {
            binding.tvReportPreview.text = state.reportPreview
            binding.cardReportPreview.visibility = View.VISIBLE
        } else {
            binding.cardReportPreview.visibility = View.GONE
        }

        // Show/hide no data message
        binding.layoutNoData.visibility = if (state.hasData) View.GONE else View.VISIBLE
        binding.layoutReportsContent.visibility = if (state.hasData) View.VISIBLE else View.GONE

        // Update button states
        binding.btnGenerateReport.isEnabled = state.canGenerateReport
        binding.btnShareReport.isEnabled = state.canExportReport
        binding.btnExportPdf.isEnabled = state.canExportReport
        binding.btnExportCsv.isEnabled = state.canExportReport
        binding.btnExportText.isEnabled = state.canExportReport
        binding.btnClearReport.isEnabled = state.generatedReport != null

        // Show loading states
        binding.progressBarGenerating.visibility = if (state.isGenerating) View.VISIBLE else View.GONE
        binding.progressBarExporting.visibility = if (state.isExporting) View.VISIBLE else View.GONE
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        // Update generation status
        if (state.generatedReport != null) {
            binding.cardGeneratedReport.visibility = View.VISIBLE
            binding.tvReportGenerated.text = "Report generated"

            state.lastGeneratedFormatted?.let { time ->
                binding.tvLastGenerated.text = "Generated: $time"
                binding.tvLastGenerated.visibility = View.VISIBLE
            }
        } else {
            binding.cardGeneratedReport.visibility = View.GONE
        }

        // Handle sharing
        state.shareableReportText?.let { shareText ->
            shareReport(shareText)
            viewModel.clearGeneratedReport()
        }

        // Handle export success
        state.exportSuccessMessage?.let { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }

        // Handle errors
        state.errorMessage?.let { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    private fun shareReport(reportText: String) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, reportText)
            putExtra(Intent.EXTRA_SUBJECT, "Food Diary Report")
            type = "text/plain"
        }

        val chooser = Intent.createChooser(shareIntent, "Share Report")
        if (shareIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(chooser)
        } else {
            Toast.makeText(requireContext(), "No app available to share report", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showReportTypeInfo(reportType: ReportType) {
        val message = when (reportType) {
            ReportType.COMPREHENSIVE -> """
                Comprehensive Report includes:
                • Complete food and symptom history
                • All identified patterns and correlations
                • Statistical analysis and confidence levels
                • Detailed timeline of events
                • Recommendations for dietary adjustments

                Best for: Personal tracking and detailed analysis
            """.trimIndent()

            ReportType.MEDICAL_SUMMARY -> """
                Medical Summary includes:
                • Concise symptom overview
                • Key food triggers identified
                • Severity patterns and frequency
                • Statistical significance summary
                • Professional formatting

                Best for: Sharing with healthcare providers
            """.trimIndent()

            ReportType.FOOD_PATTERNS -> """
                Food Patterns Report includes:
                • Focus on food triggers
                • FODMAP analysis results
                • Ingredient-specific correlations
                • Portion size effects
                • Preparation method impacts

                Best for: Dietary planning and food elimination
            """.trimIndent()

            ReportType.SYMPTOM_SUMMARY -> """
                Symptom Summary includes:
                • Symptom frequency and severity trends
                • Timing patterns analysis
                • Trigger identification
                • Recovery time analysis
                • Symptom progression tracking

                Best for: Understanding symptom patterns
            """.trimIndent()

            ReportType.WEEKLY_OVERVIEW -> """
                Weekly Overview includes:
                • Week-by-week breakdown
                • Pattern progression over time
                • Weekly symptom summaries
                • Food intake patterns
                • Trend analysis

                Best for: Monitoring progress and changes
            """.trimIndent()
        }

        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle(reportType.displayName)
            .setMessage(message)
            .setPositiveButton("Got it", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
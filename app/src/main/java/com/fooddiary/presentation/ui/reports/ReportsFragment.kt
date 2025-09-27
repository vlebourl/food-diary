package com.fooddiary.presentation.ui.reports

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.fooddiary.R
import com.fooddiary.databinding.FragmentReportsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Reports screen for generating and managing medical reports
 * Features: Report configuration, export formats, history management, sharing
 */
@AndroidEntryPoint
class ReportsFragment : Fragment() {

    private var _binding: FragmentReportsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportsViewModel by viewModels()
    private lateinit var reportHistoryAdapter: ReportHistoryAdapter

    private var startDate: LocalDate = LocalDate.now().minusMonths(1)
    private var endDate: LocalDate = LocalDate.now()
    private var selectedReportType: ReportType = ReportType.COMPREHENSIVE
    private var selectedExportFormat: ExportFormat = ExportFormat.PDF

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

        setupReportTypeChips()
        setupDateRangePickers()
        setupTemplateSpinner()
        setupExportFormatChips()
        setupRecyclerView()
        setupGenerateButton()
        observeViewModel()

        // Initialize date range display
        updateDateRangeDisplay()
    }

    private fun setupReportTypeChips() {
        binding.chipSummaryReport.setOnClickListener {
            selectedReportType = ReportType.COMPREHENSIVE
        }
        binding.chipDetailedReport.setOnClickListener {
            selectedReportType = ReportType.SYMPTOM_FOCUSED
        }
        binding.chipCorrelationReport.setOnClickListener {
            selectedReportType = ReportType.CORRELATION_ANALYSIS
        }
        binding.chipMedicalReport.setOnClickListener {
            selectedReportType = ReportType.MEDICAL_SUMMARY
        }
    }

    private fun setupDateRangePickers() {
        binding.btnStartDate.setOnClickListener {
            showDatePicker(true) { date ->
                startDate = date
                updateDateRangeDisplay()
            }
        }

        binding.btnEndDate.setOnClickListener {
            showDatePicker(false) { date ->
                endDate = date
                updateDateRangeDisplay()
            }
        }

        // Quick date range chips
        binding.chipLastWeek.setOnClickListener {
            endDate = LocalDate.now()
            startDate = endDate.minusDays(7)
            updateDateRangeDisplay()
        }

        binding.chipLastMonth.setOnClickListener {
            endDate = LocalDate.now()
            startDate = endDate.minusMonths(1)
            updateDateRangeDisplay()
        }

        binding.chipLast3Months.setOnClickListener {
            endDate = LocalDate.now()
            startDate = endDate.minusMonths(3)
            updateDateRangeDisplay()
        }
    }

    private fun setupTemplateSpinner() {
        val templates = arrayOf(
            "Standard Medical Report",
            "IBS Symptom Summary",
            "Food Trigger Analysis",
            "Dietary Pattern Report",
            "Doctor Consultation Report"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            templates
        )
        binding.spinnerTemplate.setAdapter(adapter)
        binding.spinnerTemplate.setText(templates[0], false)
    }

    private fun setupExportFormatChips() {
        binding.chipPdf.setOnClickListener {
            selectedExportFormat = ExportFormat.PDF
        }
        binding.chipCsv.setOnClickListener {
            selectedExportFormat = ExportFormat.CSV
        }
        binding.chipJson.setOnClickListener {
            selectedExportFormat = ExportFormat.JSON
        }
    }

    private fun setupRecyclerView() {
        reportHistoryAdapter = ReportHistoryAdapter(
            onReportClick = { report ->
                viewModel.loadReport(report.id)
                showReportPreview(report)
            },
            onShareClick = { report ->
                viewModel.shareReport(report, selectedExportFormat)
            },
            onDeleteClick = { report ->
                showDeleteConfirmation(report)
            }
        )

        binding.rvReportHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reportHistoryAdapter
        }
    }

    private fun setupGenerateButton() {
        binding.btnGenerateReport.setOnClickListener {
            generateReport()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun updateUI(state: ReportsUiState) {
        // Update loading states
        binding.progressBar.isVisible = state.isGenerating || state.isExporting
        binding.btnGenerateReport.isEnabled = !state.isGenerating && !state.isExporting

        // Update generation progress
        if (state.isGenerating) {
            binding.btnGenerateReport.text = state.progressMessage ?: "Generating..."
        } else {
            binding.btnGenerateReport.text = getString(R.string.generate_report)
        }

        // Update report history
        reportHistoryAdapter.submitList(state.reportHistory)
        binding.emptyReports.isVisible = state.reportHistory.isEmpty() && !state.isGenerating
        binding.rvReportHistory.isVisible = state.reportHistory.isNotEmpty()

        // Handle share requests
        state.shareRequest?.let { shareRequest ->
            shareReport(shareRequest.filePath, shareRequest.format)
            viewModel.clearShareRequest()
        }

        // Show messages
        state.message?.let { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            viewModel.clearMessage()
        }

        // Show errors
        state.error?.let { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
            viewModel.clearMessage()
        }
    }

    private fun generateReport() {
        if (startDate.isAfter(endDate)) {
            Toast.makeText(
                requireContext(),
                "Start date must be before end date",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        viewModel.generateReport(
            reportType = selectedReportType,
            startDate = startDate,
            endDate = endDate,
            includeCorrelations = true,
            includeInsights = true
        )
    }

    private fun showDatePicker(isStartDate: Boolean, onDateSelected: (LocalDate) -> Unit) {
        val currentDate = if (isStartDate) startDate else endDate
        val calendar = Calendar.getInstance().apply {
            set(currentDate.year, currentDate.monthValue - 1, currentDate.dayOfMonth)
        }

        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                onDateSelected(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateDateRangeDisplay() {
        val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
        val daysBetween = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1

        binding.tvSelectedDateRange.text =
            "${startDate.format(formatter)} - ${endDate.format(formatter)} ($daysBetween days)"
    }

    private fun showReportPreview(report: MedicalReport) {
        val summary = "Report: ${report.title}\n" +
                "Period: ${report.dateRange.startDate} to ${report.dateRange.endDate}\n" +
                "Food Entries: ${report.summary.totalFoodEntries}\n" +
                "Symptom Events: ${report.summary.totalSymptomEvents}\n" +
                "Average Severity: ${String.format("%.1f", report.summary.averageSeverity)}\n" +
                "Identified Triggers: ${report.summary.identifiedTriggers}"

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Report Preview")
            .setMessage(summary)
            .setPositiveButton("Export") { _, _ ->
                val exportPath = viewModel.exportToFormat(report, selectedExportFormat)
                if (exportPath != null) {
                    shareReport(exportPath, selectedExportFormat)
                }
            }
            .setNeutralButton("Share") { _, _ ->
                viewModel.shareReport(report, selectedExportFormat)
            }
            .setNegativeButton("Close", null)
            .show()
    }

    private fun showDeleteConfirmation(report: MedicalReport) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Report")
            .setMessage("Are you sure you want to delete this report? This action cannot be undone.")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteReport(report.id)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun shareReport(filePath: String, format: ExportFormat) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = when (format) {
                    ExportFormat.PDF -> "application/pdf"
                    ExportFormat.CSV -> "text/csv"
                    ExportFormat.JSON -> "application/json"
                    ExportFormat.DOCX -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                }
                putExtra(Intent.EXTRA_SUBJECT, "Food Diary Report")
                putExtra(Intent.EXTRA_TEXT, "Please find attached my food diary report.")
                // TODO: Add file URI when actual file export is implemented
            }

            startActivity(Intent.createChooser(shareIntent, "Share Report"))
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Failed to share report: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

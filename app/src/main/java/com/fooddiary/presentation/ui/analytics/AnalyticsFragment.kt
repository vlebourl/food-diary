package com.fooddiary.presentation.ui.analytics

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.fooddiary.R
import com.fooddiary.databinding.FragmentAnalyticsBinding
import com.fooddiary.presentation.adapters.TriggerPatternAdapter
import com.fooddiary.presentation.adapters.TriggerFoodSummaryAdapter
import com.fooddiary.presentation.viewmodel.AnalyticsViewModel
import com.fooddiary.presentation.viewmodel.TimeRange
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnalyticsFragment : Fragment() {

    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AnalyticsViewModel by viewModels()

    private lateinit var triggerPatternAdapter: TriggerPatternAdapter
    private lateinit var triggerFoodSummaryAdapter: TriggerFoodSummaryAdapter
    private lateinit var timeRangeAdapter: ArrayAdapter<TimeRange>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        setupTimeRangeSelector()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupRecyclerViews() {
        // Trigger Patterns RecyclerView
        triggerPatternAdapter = TriggerPatternAdapter { pattern ->
            // Handle pattern click - could show detailed view
            Toast.makeText(
                requireContext(),
                "${pattern.foodName} → ${pattern.symptomType.displayName}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.recyclerViewTriggerPatterns.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = triggerPatternAdapter
        }

        // Trigger Food Summary RecyclerView
        triggerFoodSummaryAdapter = TriggerFoodSummaryAdapter { foodSummary ->
            // Handle food summary click - could show food details
            Toast.makeText(
                requireContext(),
                "${foodSummary.foodName} - ${foodSummary.riskLevel}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.recyclerViewTopTriggerFoods.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = triggerFoodSummaryAdapter
        }
    }

    private fun setupTimeRangeSelector() {
        timeRangeAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            TimeRange.values().map { it.description }
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerTimeRange.setAdapter(timeRangeAdapter)
    }

    private fun setupClickListeners() {
        // Time range selection
        binding.spinnerTimeRange.setOnItemClickListener { _, _, position, _ ->
            val selectedRange = TimeRange.values()[position]
            viewModel.updateTimeRange(selectedRange)
        }

        // Refresh button
        binding.btnRefresh.setOnClickListener {
            viewModel.refreshAnalytics()
        }

        // View all patterns button
        binding.btnViewAllPatterns.setOnClickListener {
            // Navigate to detailed patterns view
            Toast.makeText(requireContext(), "View all patterns", Toast.LENGTH_SHORT).show()
        }

        // Statistical significance info button
        binding.btnInfoStatisticalSignificance.setOnClickListener {
            showStatisticalSignificanceInfo()
        }

        // Pattern confidence info button
        binding.btnInfoPatternConfidence.setOnClickListener {
            showPatternConfidenceInfo()
        }

        // Tab buttons for switching views
        binding.btnTabOverview.setOnClickListener {
            showOverviewTab()
        }

        binding.btnTabPatterns.setOnClickListener {
            showPatternsTab()
        }

        binding.btnTabFoods.setOnClickListener {
            showFoodsTab()
        }

        binding.btnTabSymptoms.setOnClickListener {
            showSymptomsTab()
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

                // Observe statistically significant patterns
                launch {
                    viewModel.statisticallySignificantPatterns.collect { patterns ->
                        triggerPatternAdapter.submitList(patterns)
                        updatePatternsVisibility(patterns.isNotEmpty())
                    }
                }

                // Observe top trigger foods
                launch {
                    viewModel.getTopTriggerFoods(5).collect { triggerFoods ->
                        triggerFoodSummaryAdapter.submitList(triggerFoods)
                        updateTriggerFoodsVisibility(triggerFoods.isNotEmpty())
                    }
                }
            }
        }
    }

    private fun updateUI(state: AnalyticsViewModel.AnalyticsUiState) {
        // Update summary statistics
        binding.tvTotalSymptoms.text = state.totalSymptoms.toString()
        binding.tvTotalFoodEntries.text = state.totalFoodEntries.toString()
        binding.tvStatisticallySignificantCount.text = state.statisticallySignificantCount.toString()
        binding.tvHighConfidenceCount.text = state.highConfidenceCount.toString()
        binding.tvTotalPatternsCount.text = state.totalPatternsCount.toString()

        // Update time range description
        binding.tvTimeRangeDescription.text = state.timeRangeDescription

        // Show/hide no data message
        binding.layoutNoData.visibility = if (state.hasData) View.GONE else View.VISIBLE
        binding.layoutAnalyticsContent.visibility = if (state.hasData) View.VISIBLE else View.GONE

        // Update significance rate
        if (state.totalPatternsCount > 0) {
            val significanceRate = (state.significanceRate * 100).toInt()
            binding.tvSignificanceRate.text = "$significanceRate%"
            binding.tvSignificanceRate.visibility = View.VISIBLE
            binding.tvSignificanceRateLabel.visibility = View.VISIBLE
        } else {
            binding.tvSignificanceRate.visibility = View.GONE
            binding.tvSignificanceRateLabel.visibility = View.GONE
        }

        // Show loading state
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.btnRefresh.isEnabled = !state.isLoading

        // Update last updated time
        state.lastUpdatedFormatted?.let { time ->
            binding.tvLastUpdated.text = "Updated: $time"
            binding.tvLastUpdated.visibility = View.VISIBLE
        } ?: run {
            binding.tvLastUpdated.visibility = View.GONE
        }

        // Handle errors
        state.errorMessage?.let { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    private fun updatePatternsVisibility(hasPatterns: Boolean) {
        binding.layoutTriggerPatterns.visibility = if (hasPatterns) View.VISIBLE else View.GONE
        binding.tvNoPatternsMessage.visibility = if (hasPatterns) View.GONE else View.VISIBLE
    }

    private fun updateTriggerFoodsVisibility(hasFoods: Boolean) {
        binding.layoutTopTriggerFoods.visibility = if (hasFoods) View.VISIBLE else View.GONE
    }

    private fun showOverviewTab() {
        updateTabSelection(0)
        binding.layoutOverview.visibility = View.VISIBLE
        binding.layoutPatterns.visibility = View.GONE
        binding.layoutFoods.visibility = View.GONE
        binding.layoutSymptoms.visibility = View.GONE
    }

    private fun showPatternsTab() {
        updateTabSelection(1)
        binding.layoutOverview.visibility = View.GONE
        binding.layoutPatterns.visibility = View.VISIBLE
        binding.layoutFoods.visibility = View.GONE
        binding.layoutSymptoms.visibility = View.GONE
    }

    private fun showFoodsTab() {
        updateTabSelection(2)
        binding.layoutOverview.visibility = View.GONE
        binding.layoutPatterns.visibility = View.GONE
        binding.layoutFoods.visibility = View.VISIBLE
        binding.layoutSymptoms.visibility = View.GONE
    }

    private fun showSymptomsTab() {
        updateTabSelection(3)
        binding.layoutOverview.visibility = View.GONE
        binding.layoutPatterns.visibility = View.GONE
        binding.layoutFoods.visibility = View.GONE
        binding.layoutSymptoms.visibility = View.VISIBLE
    }

    private fun updateTabSelection(selectedTab: Int) {
        val tabs = listOf(
            binding.btnTabOverview,
            binding.btnTabPatterns,
            binding.btnTabFoods,
            binding.btnTabSymptoms
        )

        tabs.forEachIndexed { index, button ->
            button.isSelected = (index == selectedTab)
        }
    }

    private fun showStatisticalSignificanceInfo() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Statistical Significance")
            .setMessage("""
                Statistical significance indicates patterns that are unlikely to occur by chance.

                Criteria used:
                • Correlation strength ≥ 60%
                • At least 10 data points
                • P-value < 0.05 (95% confidence)

                These patterns represent the strongest food-symptom relationships in your data.
            """.trimIndent())
            .setPositiveButton("Got it", null)
            .show()
    }

    private fun showPatternConfidenceInfo() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Pattern Confidence")
            .setMessage("""
                High confidence patterns meet additional quality criteria:

                • Strong correlation (≥ 70%)
                • Consistent timing patterns
                • Multiple occurrences
                • Low variability in symptoms

                These represent your most reliable food triggers.
            """.trimIndent())
            .setPositiveButton("Got it", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
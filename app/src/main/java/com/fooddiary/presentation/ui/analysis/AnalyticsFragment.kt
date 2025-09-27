package com.fooddiary.presentation.ui.analysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayout
import com.fooddiary.R
import com.fooddiary.databinding.FragmentAnalyticsBinding
import com.fooddiary.presentation.ui.analytics.AnalyticsViewModel
import com.fooddiary.presentation.ui.analytics.TimeRange
import com.fooddiary.presentation.ui.analytics.ChartType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Analytics screen with charts and insights for pattern detection
 * Features: Time range selection, statistics overview, trend charts, correlation analysis
 */
@AndroidEntryPoint
class AnalyticsFragment : Fragment() {

    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AnalyticsViewModel by viewModels()
    private lateinit var symptomTrendsAdapter: SymptomTrendsAdapter
    private lateinit var topTriggersAdapter: TopTriggersAdapter

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

        setupTimeRangeTabs()
        setupRecyclerViews()
        setupCharts()
        observeViewModel()
    }

    private fun setupTimeRangeTabs() {
        binding.tabLayoutTimeRange.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val timeRange = when (tab?.position) {
                    0 -> TimeRange.LAST_7_DAYS
                    1 -> TimeRange.LAST_30_DAYS
                    2 -> TimeRange.LAST_90_DAYS
                    3 -> showCustomDatePicker()
                    else -> TimeRange.LAST_30_DAYS
                }
                if (tab?.position != 3) {
                    viewModel.changeTimeRange(timeRange)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // Set default tab
        binding.tabLayoutTimeRange.selectTab(binding.tabLayoutTimeRange.getTabAt(1))
    }

    private fun setupRecyclerViews() {
        // Symptom trends adapter
        symptomTrendsAdapter = SymptomTrendsAdapter()
        binding.rvSymptomTrends.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = symptomTrendsAdapter
        }

        // Top triggers adapter
        topTriggersAdapter = TopTriggersAdapter { correlation ->
            // Handle trigger item click - could navigate to detailed view
            showTriggerDetails(correlation)
        }
        binding.rvTopTriggers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = topTriggersAdapter
        }
    }

    private fun setupCharts() {
        // Initialize chart containers with placeholder content
        setupTimelineChart()
        setupSeverityChart()
    }

    private fun setupTimelineChart() {
        // For now, show placeholder text
        // In a real app, this would integrate with a charting library like MPAndroidChart
        binding.chartContainerTimeline.removeAllViews()

        val chartPlaceholder = layoutInflater.inflate(R.layout.chart_placeholder, null)
        chartPlaceholder.findViewById<android.widget.TextView>(R.id.placeholder_text)?.text =
            "Symptom Timeline Chart\n(Chart implementation pending)"
        binding.chartContainerTimeline.addView(chartPlaceholder)
    }

    private fun setupSeverityChart() {
        // For now, show placeholder text
        binding.chartContainerSeverity.removeAllViews()

        val chartPlaceholder = layoutInflater.inflate(R.layout.chart_placeholder, null)
        chartPlaceholder.findViewById<android.widget.TextView>(R.id.placeholder_text)?.text =
            "Severity Distribution Chart\n(Chart implementation pending)"
        binding.chartContainerSeverity.addView(chartPlaceholder)
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

    private fun updateUI(state: com.fooddiary.presentation.ui.analytics.AnalyticsUiState) {
        // Update loading state
        binding.progressBar.isVisible = state.isLoading

        // Update statistics cards
        updateStatisticsCards(state.statistics)

        // Update chart data
        updateCharts(state.chartData)

        // Update trend lists
        updateTrendLists(state.insights, state.correlationData)

        // Show error if any
        state.error?.let { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
        }
    }

    private fun updateStatisticsCards(statistics: Map<String, com.fooddiary.presentation.ui.analytics.StatisticValue>) {
        // Update total entries
        statistics["total_food_entries"]?.let { stat ->
            binding.tvTotalEntriesCount.text = stat.value.toInt().toString()
        }

        // Update symptoms count
        statistics["total_symptoms"]?.let { stat ->
            binding.tvSymptomsCount.text = stat.value.toInt().toString()
        }

        // Update correlations (placeholder for now)
        binding.tvCorrelationsCount.text = statistics.size.toString()

        // Update average severity
        statistics["avg_severity"]?.let { stat ->
            binding.tvAvgSeverity.text = String.format("%.1f", stat.value)
        }
    }

    private fun updateCharts(chartData: Map<ChartType, com.fooddiary.presentation.ui.analytics.ChartData>) {
        // Update timeline chart
        chartData[ChartType.SYMPTOM_SEVERITY]?.let { data ->
            updateTimelineChartData(data)
        }

        // Update severity distribution chart
        chartData[ChartType.SYMPTOM_FREQUENCY]?.let { data ->
            updateSeverityChartData(data)
        }
    }

    private fun updateTimelineChartData(chartData: com.fooddiary.presentation.ui.analytics.ChartData) {
        // For now, just update the placeholder text with data summary
        binding.chartContainerTimeline.removeAllViews()

        val chartPlaceholder = layoutInflater.inflate(R.layout.chart_placeholder, null)
        val summaryText = "Timeline Chart\nData points: ${chartData.values.size}\n" +
                "Range: ${chartData.values.minOrNull()?.let { "%.1f".format(it) } ?: "N/A"} - " +
                "${chartData.values.maxOrNull()?.let { "%.1f".format(it) } ?: "N/A"}"
        chartPlaceholder.findViewById<android.widget.TextView>(R.id.placeholder_text)?.text = summaryText
        binding.chartContainerTimeline.addView(chartPlaceholder)
    }

    private fun updateSeverityChartData(chartData: com.fooddiary.presentation.ui.analytics.ChartData) {
        // For now, just update the placeholder text with data summary
        binding.chartContainerSeverity.removeAllViews()

        val chartPlaceholder = layoutInflater.inflate(R.layout.chart_placeholder, null)
        val summaryText = "Frequency Chart\nCategories: ${chartData.labels.size}\n" +
                "Total events: ${chartData.values.sum().toInt()}"
        chartPlaceholder.findViewById<android.widget.TextView>(R.id.placeholder_text)?.text = summaryText
        binding.chartContainerSeverity.addView(chartPlaceholder)
    }

    private fun updateTrendLists(
        insights: List<com.fooddiary.presentation.ui.analytics.AnalyticsInsight>,
        correlations: List<com.fooddiary.presentation.ui.analytics.CorrelationData>
    ) {
        // Convert insights to trend items for display
        val trendItems = insights.map { insight ->
            SymptomTrendItem(
                title = insight.title,
                description = insight.description,
                trend = when (insight.severity) {
                    com.fooddiary.presentation.ui.analytics.InsightSeverity.HIGH -> TrendDirection.INCREASING
                    com.fooddiary.presentation.ui.analytics.InsightSeverity.MEDIUM -> TrendDirection.STABLE
                    else -> TrendDirection.DECREASING
                },
                confidence = insight.confidence
            )
        }
        symptomTrendsAdapter.submitList(trendItems)

        // Show top correlations as triggers
        val topTriggers = correlations.sortedByDescending { it.strength }.take(5)
        topTriggersAdapter.submitList(topTriggers)
    }

    private fun showCustomDatePicker(): TimeRange {
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select custom date range")
            .build()

        dateRangePicker.addOnPositiveButtonClickListener { selection ->
            // For simplicity, map to closest predefined range
            // In a real app, you'd add custom range support to the ViewModel
            viewModel.changeTimeRange(TimeRange.LAST_90_DAYS)
        }

        dateRangePicker.show(parentFragmentManager, "date_range_picker")
        return TimeRange.LAST_90_DAYS
    }

    private fun showTriggerDetails(correlation: com.fooddiary.presentation.ui.analytics.CorrelationData) {
        // Show detailed correlation information
        val details = "Food: ${correlation.foodName}\n" +
                "Symptom: ${correlation.symptomType.name}\n" +
                "Correlation Strength: ${"%.1f".format(correlation.strength * 100)}%\n" +
                "Confidence: ${"%.1f".format(correlation.confidence * 100)}%\n" +
                "Occurrences: ${correlation.occurrences}"

        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Trigger Analysis")
            .setMessage(details)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// Data classes for adapters
data class SymptomTrendItem(
    val title: String,
    val description: String,
    val trend: TrendDirection,
    val confidence: Double
)

enum class TrendDirection {
    INCREASING, DECREASING, STABLE
}

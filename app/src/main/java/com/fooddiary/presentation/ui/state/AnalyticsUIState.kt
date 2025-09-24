package com.fooddiary.presentation.ui.state

import com.fooddiary.domain.analysis.CorrelationAnalysis
import com.fooddiary.domain.analysis.PatternInsight
import com.fooddiary.domain.analysis.TimeWindowAnalysis
import com.fooddiary.domain.analysis.TrendAnalysis

data class AnalyticsUIState(
    val isLoading: Boolean = false,
    val isLoadingTypeChange: Boolean = false,
    val isExporting: Boolean = false,
    val error: String? = null,
    val exportComplete: Boolean = false,
    val correlations: List<CorrelationAnalysis> = emptyList(),
    val timeWindowAnalysis: List<TimeWindowAnalysis> = emptyList(),
    val patternInsights: List<PatternInsight> = emptyList(),
    val trendAnalysis: List<TrendAnalysis> = emptyList()
)
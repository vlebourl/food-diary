# Analytics Dashboard Contract

## Purpose
Display visual patterns and insights about food-symptom correlations over time.

## UI Contract

### Data Requirements
```kotlin
data class AnalyticsState(
    val timeRange: TimeRange = TimeRange.LAST_MONTH,
    val overviewStats: OverviewStats? = null,
    val correlationChart: CorrelationChartData? = null,
    val symptomTrends: List<SymptomTrendData> = emptyList(),
    val topTriggers: List<TriggerFood> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val hasInsufficientData: Boolean = false
)

data class OverviewStats(
    val totalEntries: Int,
    val totalSymptoms: Int,
    val averageSeverity: Float,
    val mostCommonSymptom: SymptomType?,
    val correlationCount: Int
)

data class CorrelationChartData(
    val dataPoints: List<CorrelationPoint>,
    val timeLabels: List<String>,
    val maxSeverity: Int = 10
)

data class CorrelationPoint(
    val timestamp: Instant,
    val severity: Int,
    val symptomType: SymptomType,
    val relatedFoods: List<String>
)

data class SymptomTrendData(
    val symptomType: SymptomType,
    val frequency: Int,
    val averageSeverity: Float,
    val trendDirection: TrendDirection // UP, DOWN, STABLE
)

data class TriggerFood(
    val foodName: String,
    val correlationCount: Int,
    val averageConfidence: Float,
    val lastTriggered: Instant?
)

enum class TimeRange {
    LAST_WEEK, LAST_MONTH, LAST_3_MONTHS, CUSTOM
}

enum class TrendDirection { UP, DOWN, STABLE }
```

### User Actions
```kotlin
interface AnalyticsActions {
    fun updateTimeRange(timeRange: TimeRange)
    fun setCustomDateRange(start: LocalDate, end: LocalDate)
    fun refreshAnalytics()
    fun navigateToTriggerDetail(foodName: String)
    fun navigateToSymptomHistory(symptomType: SymptomType)
    fun exportAnalytics()
    fun shareInsights()
}
```

### Expected Behaviors
- Time range selector (tabs or dropdown)
- Overview statistics cards at top
- Interactive correlation timeline chart
- Symptom frequency breakdown
- Top trigger foods ranking list
- Handle insufficient data gracefully
- Export functionality for healthcare providers
- Loading states during calculations

## Visualization Requirements

### Correlation Timeline Chart
- Line chart showing symptom severity over time
- Color-coded points by symptom type
- Hover/tap details showing related foods
- Zoom and pan capabilities
- Missing data handling

### Symptom Trends
- Bar chart or card layout
- Trend indicators (arrows/icons)
- Frequency counts
- Average severity indicators

### Trigger Foods Ranking
- List with confidence meters
- Recent activity indicators
- Tap to view detailed correlations

## Data Analysis Rules
```kotlin
data class AnalyticsConfig(
    val minimumDataPoints: Int = 7,
    val correlationThreshold: Float = 0.3f,
    val maxTriggerFoods: Int = 10,
    val trendCalculationDays: Int = 14
)
```

- Require minimum 7 data points for meaningful analysis
- Only show correlations above confidence threshold
- Limit top triggers to most significant 10 foods
- Calculate trends over 14-day rolling windows
- Handle gaps in data gracefully

## Insufficient Data Handling
When data is below minimum thresholds:
- Show generic placeholder messages per FR-006
- Provide guidance on logging more entries
- Offer sample data for demonstration
- Suggest optimal logging frequency

## Test Scenarios
1. **Complete Dashboard**: All sections populated with sufficient data
2. **Insufficient Data**: Show placeholders and guidance messages
3. **Time Range Changes**: Update all visualizations when range changes
4. **Loading States**: Show progress during data calculations
5. **Interactive Charts**: Tap/hover interactions work correctly
6. **Export Functionality**: Generate and share analytics reports
7. **Empty States**: Handle periods with no symptoms or entries
8. **Large Datasets**: Performance with maximum data retention (1 year)
9. **Correlation Accuracy**: Verify correlation calculations are correct
10. **Trend Detection**: Properly identify increasing/decreasing symptom patterns
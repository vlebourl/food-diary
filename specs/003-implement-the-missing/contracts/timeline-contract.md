# Timeline Screen Contract

## Purpose
Display chronological view of food entries and symptoms with navigation to entry forms.

## UI Contract

### Data Requirements
```kotlin
data class TimelineState(
    val entries: List<TimelineEntry>,
    val isLoading: Boolean,
    val error: String?,
    val hasMoreData: Boolean
)

data class TimelineEntry(
    val id: Long,
    val timestamp: Instant,
    val type: EntryType, // FOOD, SYMPTOM
    val title: String,
    val subtitle: String?,
    val severity: Int?, // Only for symptoms
    val correlations: List<CorrelationInfo>
)

enum class EntryType { FOOD, SYMPTOM }

data class CorrelationInfo(
    val relatedEntryId: Long,
    val confidence: Float,
    val timeOffset: Duration
)
```

### User Actions
```kotlin
interface TimelineActions {
    fun loadTimeline()
    fun loadMoreEntries()
    fun refreshTimeline()
    fun navigateToFoodEntry()
    fun navigateToSymptomEntry()
    fun navigateToEntryDetail(entryId: Long, entryType: EntryType)
    fun filterByDateRange(start: LocalDate, end: LocalDate)
    fun clearFilters()
}
```

### Expected Behaviors
- Load entries in reverse chronological order (newest first)
- Pagination with 20 entries per page
- Visual distinction between food and symptom entries
- Show correlation indicators for linked entries
- Handle empty state with guidance message
- Pull-to-refresh functionality
- Loading states during data operations
- Error handling with retry options

## Validation Rules
- Timeline must show entries within user's data retention period
- Correlation indicators only shown for entries within correlation time window
- Soft-deleted entries must not appear in timeline
- Date filtering must respect timezone settings

## Test Scenarios
1. **Empty Timeline**: Show empty state with "Add your first entry" guidance
2. **Mixed Entries**: Display food and symptom entries with proper visual hierarchy
3. **Correlations**: Show connection indicators between related entries
4. **Pagination**: Load more entries when scrolling to bottom
5. **Filtering**: Apply date range filters and show filtered results
6. **Error States**: Handle database errors gracefully
7. **Loading States**: Show appropriate loading indicators during operations
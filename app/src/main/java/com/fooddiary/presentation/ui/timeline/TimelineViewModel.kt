package com.fooddiary.presentation.ui.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooddiary.data.repository.TimelineRepository
import com.fooddiary.data.repository.TimelineEntry
import com.fooddiary.data.repository.CorrelatedTimelineEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import java.time.Instant
import java.time.LocalDate
import java.time.Duration
import javax.inject.Inject

/**
 * ViewModel for Timeline screen implementing the timeline contract
 * Manages state for timeline data loading, pagination, filtering, and navigation
 */
@HiltViewModel
class TimelineViewModel @Inject constructor(
    private val timelineRepository: TimelineRepository
) : ViewModel(), TimelineActions {

    companion object {
        private const val PAGE_SIZE = 20
    }

    private val _timelineState = MutableStateFlow(TimelineState())
    val timelineState: StateFlow<TimelineState> = _timelineState.asStateFlow()

    private var loadTimelineJob: Job? = null
    private var currentPage = 0
    private var dateRangeStart: LocalDate? = null
    private var dateRangeEnd: LocalDate? = null

    init {
        loadTimeline()
    }

    /**
     * Load initial timeline data
     */
    override fun loadTimeline() {
        loadTimelineJob?.cancel()
        loadTimelineJob = viewModelScope.launch {
            try {
                _timelineState.value = _timelineState.value.copy(
                    isLoading = true,
                    error = null
                )

                currentPage = 0
                val entries = loadEntriesForPage(currentPage)

                // Load correlations for the entries
                val correlatedEntries = timelineRepository.getCorrelatedEntries(0.7f)
                    .firstOrNull() ?: emptyList()

                val timelineEntries = convertToTimelineEntries(entries, correlatedEntries)

                _timelineState.value = _timelineState.value.copy(
                    entries = timelineEntries,
                    isLoading = false,
                    hasMoreData = entries.size == PAGE_SIZE
                )
            } catch (e: Exception) {
                _timelineState.value = _timelineState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    /**
     * Refresh timeline data
     */
    override fun refreshTimeline() {
        loadTimeline()
    }

    /**
     * Load more entries for pagination
     */
    override fun loadMoreEntries() {
        if (_timelineState.value.isLoading || !_timelineState.value.hasMoreData) return

        viewModelScope.launch {
            try {
                _timelineState.value = _timelineState.value.copy(isLoading = true)

                currentPage++
                val newEntries = loadEntriesForPage(currentPage)

                // Load correlations for the new entries
                val correlatedEntries = timelineRepository.getCorrelatedEntries(0.7f)
                    .firstOrNull() ?: emptyList()

                val newTimelineEntries = convertToTimelineEntries(newEntries, correlatedEntries)
                val currentEntries = _timelineState.value.entries

                _timelineState.value = _timelineState.value.copy(
                    entries = currentEntries + newTimelineEntries,
                    isLoading = false,
                    hasMoreData = newEntries.size == PAGE_SIZE
                )
            } catch (e: Exception) {
                _timelineState.value = _timelineState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error loading more entries"
                )
            }
        }
    }

    /**
     * Filter timeline by date range
     */
    override fun filterByDateRange(start: LocalDate, end: LocalDate) {
        dateRangeStart = start
        dateRangeEnd = end
        loadTimeline()
    }

    /**
     * Clear date range filters
     */
    override fun clearFilters() {
        dateRangeStart = null
        dateRangeEnd = null
        loadTimeline()
    }

    /**
     * Navigation actions (to be handled by Fragment/Activity)
     * These methods are used to trigger navigation events
     */

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents: SharedFlow<NavigationEvent> = _navigationEvents.asSharedFlow()

    override fun navigateToFoodEntry() {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.ToFoodEntry)
        }
    }

    override fun navigateToSymptomEntry() {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.ToSymptomEntry)
        }
    }

    override fun navigateToEntryDetail(entryId: Long, entryType: EntryType) {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.ToEntryDetail(entryId, entryType))
        }
    }

    /**
     * Load entries for a specific page with optional date range filtering
     */
    private suspend fun loadEntriesForPage(page: Int): List<TimelineEntry> {
        return if (dateRangeStart != null && dateRangeEnd != null) {
            timelineRepository.getTimelineEntriesInRange(dateRangeStart!!, dateRangeEnd!!)
                .firstOrNull()
                ?.drop(page * PAGE_SIZE)
                ?.take(PAGE_SIZE)
                ?: emptyList()
        } else {
            timelineRepository.getTimelineEntries()
                .firstOrNull()
                ?.drop(page * PAGE_SIZE)
                ?.take(PAGE_SIZE)
                ?: emptyList()
        }
    }

    /**
     * Convert repository timeline entries to UI timeline entries with correlations
     */
    private fun convertToTimelineEntries(
        entries: List<TimelineEntry>,
        correlatedEntries: List<CorrelatedTimelineEntry>
    ): List<TimelineUiEntry> {
        return entries.map { entry ->
            val correlations = findCorrelationsForEntry(entry.id, correlatedEntries)

            when (entry) {
                is TimelineEntry.FoodEntry -> TimelineUiEntry(
                    id = entry.entryId,
                    timestamp = entry.entryTimestamp,
                    type = EntryType.FOOD,
                    title = entry.foods.joinToString(", "),
                    subtitle = "${entry.mealType.name.lowercase().replaceFirstChar { it.uppercase() }} • ${entry.portions.size} items",
                    severity = null,
                    correlations = correlations
                )
                is TimelineEntry.SymptomEntry -> TimelineUiEntry(
                    id = entry.eventId,
                    timestamp = entry.eventTimestamp,
                    type = EntryType.SYMPTOM,
                    title = entry.symptomType.name.lowercase().replaceFirstChar { it.uppercase() },
                    subtitle = "Severity: ${entry.severity}/10${entry.duration?.let { " • Duration: ${formatDuration(it)}" } ?: ""}",
                    severity = entry.severity,
                    correlations = correlations
                )
            }
        }
    }

    /**
     * Find correlations for a specific entry
     */
    private fun findCorrelationsForEntry(
        entryId: Long,
        correlatedEntries: List<CorrelatedTimelineEntry>
    ): List<CorrelationInfo> {
        return correlatedEntries.mapNotNull { correlation ->
            when {
                correlation.triggerFood.entryId == entryId -> CorrelationInfo(
                    relatedEntryId = correlation.resultingSymptom.eventId,
                    confidence = correlation.correlationStrength,
                    timeOffset = Duration.ofHours(correlation.timeOffsetHours.toLong())
                )
                correlation.resultingSymptom.eventId == entryId -> CorrelationInfo(
                    relatedEntryId = correlation.triggerFood.entryId,
                    confidence = correlation.correlationStrength,
                    timeOffset = Duration.ofHours(-correlation.timeOffsetHours.toLong())
                )
                else -> null
            }
        }
    }

    /**
     * Format duration for display
     */
    private fun formatDuration(duration: Duration): String {
        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60
        return when {
            hours > 0 -> "${hours}h ${minutes}m"
            else -> "${minutes}m"
        }
    }
}

/**
 * Timeline state according to the contract
 */
data class TimelineState(
    val entries: List<TimelineUiEntry> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val hasMoreData: Boolean = true
)

/**
 * Timeline entry UI representation according to the contract
 */
data class TimelineUiEntry(
    val id: Long,
    val timestamp: Instant,
    val type: EntryType,
    val title: String,
    val subtitle: String?,
    val severity: Int? = null, // Only for symptoms
    val correlations: List<CorrelationInfo> = emptyList()
)

/**
 * Entry type enum according to the contract
 */
enum class EntryType { FOOD, SYMPTOM }

/**
 * Correlation information according to the contract
 */
data class CorrelationInfo(
    val relatedEntryId: Long,
    val confidence: Float,
    val timeOffset: Duration
)

/**
 * Timeline actions interface according to the contract
 */
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

/**
 * Navigation events for communication with Fragment/Activity
 */
sealed class NavigationEvent {
    object ToFoodEntry : NavigationEvent()
    object ToSymptomEntry : NavigationEvent()
    data class ToEntryDetail(val entryId: Long, val entryType: EntryType) : NavigationEvent()
}
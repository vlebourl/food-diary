package com.fooddiary.utils

import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Handles timezone conflicts and conversions for cross-timezone entries
 * Ensures data consistency when users travel or change device timezone
 */
@Singleton
class TimezoneHandler @Inject constructor() {

    companion object {
        private const val MAX_TIMEZONE_DRIFT_HOURS = 26 // Maximum possible timezone difference
        private const val SUSPICIOUS_TIME_JUMP_HOURS = 12 // Flag entries with large time jumps
    }

    /**
     * Resolves timezone conflicts when an entry's timezone differs from current device timezone
     */
    fun resolveTimezoneConflict(
        entryTimestamp: Instant,
        entryTimezone: ZoneId?,
        currentTimezone: ZoneId = ZoneId.systemDefault()
    ): TimezoneResolution {

        val originalZone = entryTimezone ?: currentTimezone
        val originalDateTime = ZonedDateTime.ofInstant(entryTimestamp, originalZone)
        val currentDateTime = ZonedDateTime.ofInstant(entryTimestamp, currentTimezone)

        val offsetDifference = ChronoUnit.HOURS.between(
            originalDateTime.offset.totalSeconds / 3600,
            currentDateTime.offset.totalSeconds / 3600
        )

        return when {
            offsetDifference == 0L -> {
                TimezoneResolution.NoConflict(entryTimestamp, currentTimezone)
            }

            kotlin.math.abs(offsetDifference) > SUSPICIOUS_TIME_JUMP_HOURS -> {
                TimezoneResolution.SuspiciousConflict(
                    originalTimestamp = entryTimestamp,
                    originalZone = originalZone,
                    currentZone = currentTimezone,
                    hoursOffset = offsetDifference,
                    suggestedAction = ResolutionAction.REQUIRE_USER_CONFIRMATION,
                    warningMessage = "Large timezone difference detected ($offsetDifference hours). Please verify the entry time."
                )
            }

            else -> {
                TimezoneResolution.NormalConflict(
                    originalTimestamp = entryTimestamp,
                    originalZone = originalZone,
                    currentZone = currentTimezone,
                    hoursOffset = offsetDifference,
                    convertedTimestamp = convertToCurrentTimezone(entryTimestamp, originalZone, currentTimezone),
                    preserveOriginal = true
                )
            }
        }
    }

    /**
     * Converts timestamp from one timezone to another while preserving the original instant
     */
    fun convertToCurrentTimezone(
        timestamp: Instant,
        fromZone: ZoneId,
        toZone: ZoneId
    ): Instant {
        // The instant remains the same, only the representation changes
        return timestamp
    }

    /**
     * Detects if entries have inconsistent timezone patterns suggesting travel
     */
    fun detectTravelPatterns(entries: List<TimezoneAwareEntry>): TravelDetectionResult {
        if (entries.size < 2) {
            return TravelDetectionResult.InsufficientData
        }

        val sortedEntries = entries.sortedBy { it.timestamp }
        val timezoneChanges = mutableListOf<TimezoneChange>()

        for (i in 1 until sortedEntries.size) {
            val previous = sortedEntries[i - 1]
            val current = sortedEntries[i]

            if (previous.timezone != current.timezone) {
                val timeBetween = ChronoUnit.HOURS.between(previous.timestamp, current.timestamp)

                timezoneChanges.add(TimezoneChange(
                    fromZone = previous.timezone,
                    toZone = current.timezone,
                    changeTime = current.timestamp,
                    hoursBetweenEntries = timeBetween,
                    isProbableTravel = isProbableTravel(previous.timezone, current.timezone, timeBetween)
                ))
            }
        }

        return if (timezoneChanges.isNotEmpty()) {
            TravelDetectionResult.TravelDetected(
                changes = timezoneChanges,
                recommendation = generateTravelRecommendation(timezoneChanges)
            )
        } else {
            TravelDetectionResult.NoTravelDetected
        }
    }

    /**
     * Validates that a series of entries have consistent timezone progression
     */
    fun validateTimezoneConsistency(entries: List<TimezoneAwareEntry>): ConsistencyValidation {
        if (entries.isEmpty()) {
            return ConsistencyValidation.Valid("No entries to validate")
        }

        val issues = mutableListOf<TimezoneIssue>()
        val sortedEntries = entries.sortedBy { it.timestamp }

        // Check for impossible timezone jumps
        for (i in 1 until sortedEntries.size) {
            val previous = sortedEntries[i - 1]
            val current = sortedEntries[i]

            val timeDiff = ChronoUnit.HOURS.between(previous.timestamp, current.timestamp)
            val timezoneDiff = getTimezoneOffsetDifference(previous.timezone, current.timezone, current.timestamp)

            // Check if timezone change is physically possible given time between entries
            if (kotlin.math.abs(timezoneDiff) > timeDiff + 3) { // +3 hours for margin
                issues.add(TimezoneIssue.ImpossibleJump(
                    from = previous,
                    to = current,
                    reason = "Timezone changed by $timezoneDiff hours but only $timeDiff hours passed"
                ))
            }

            // Check for rapid back-and-forth timezone changes
            if (i < sortedEntries.size - 1) {
                val next = sortedEntries[i + 1]
                if (previous.timezone == next.timezone && previous.timezone != current.timezone) {
                    val totalTime = ChronoUnit.HOURS.between(previous.timestamp, next.timestamp)
                    if (totalTime < 24) {
                        issues.add(TimezoneIssue.SuspiciousPattern(
                            entries = listOf(previous, current, next),
                            reason = "Rapid timezone change and return within $totalTime hours"
                        ))
                    }
                }
            }
        }

        // Check for future timestamps
        val now = Instant.now()
        sortedEntries.filter { it.timestamp.isAfter(now) }.forEach { entry ->
            issues.add(TimezoneIssue.FutureTimestamp(
                entry = entry,
                reason = "Entry timestamp is in the future"
            ))
        }

        return if (issues.isEmpty()) {
            ConsistencyValidation.Valid("All timezone progressions are consistent")
        } else {
            ConsistencyValidation.Invalid(issues)
        }
    }

    /**
     * Suggests the most likely timezone for an entry based on surrounding entries
     */
    fun suggestTimezoneForEntry(
        entryTime: Instant,
        surroundingEntries: List<TimezoneAwareEntry>,
        defaultZone: ZoneId = ZoneId.systemDefault()
    ): ZoneId {
        if (surroundingEntries.isEmpty()) {
            return defaultZone
        }

        // Find entries within 24 hours before and after
        val relevantEntries = surroundingEntries.filter { entry ->
            val hoursDiff = kotlin.math.abs(ChronoUnit.HOURS.between(entry.timestamp, entryTime))
            hoursDiff <= 24
        }

        if (relevantEntries.isEmpty()) {
            // Find the closest entry
            val closest = surroundingEntries.minByOrNull { entry ->
                kotlin.math.abs(ChronoUnit.MINUTES.between(entry.timestamp, entryTime))
            }
            return closest?.timezone ?: defaultZone
        }

        // Use the most common timezone in relevant entries
        val timezoneFrequency = relevantEntries.groupingBy { it.timezone }.eachCount()
        return timezoneFrequency.maxByOrNull { it.value }?.key ?: defaultZone
    }

    /**
     * Handles daylight saving time transitions
     */
    fun handleDSTTransition(
        timestamp: Instant,
        timezone: ZoneId
    ): DSTHandling {
        val zoneRules = timezone.rules
        val transition = zoneRules.nextTransition(timestamp)
        val previousTransition = zoneRules.previousTransition(timestamp)

        return when {
            transition != null && ChronoUnit.HOURS.between(timestamp, transition.instant) <= 24 -> {
                DSTHandling.UpcomingTransition(
                    transitionTime = transition.instant,
                    offsetChange = transition.offsetAfter.totalSeconds - transition.offsetBefore.totalSeconds,
                    warning = "Daylight saving time change within 24 hours"
                )
            }

            previousTransition != null && ChronoUnit.HOURS.between(previousTransition.instant, timestamp) <= 24 -> {
                DSTHandling.RecentTransition(
                    transitionTime = previousTransition.instant,
                    offsetChange = previousTransition.offsetAfter.totalSeconds - previousTransition.offsetBefore.totalSeconds,
                    note = "Recent daylight saving time change may affect entry timing"
                )
            }

            else -> DSTHandling.NoTransition
        }
    }

    /**
     * Formats timestamp with timezone information for display
     */
    fun formatWithTimezone(
        timestamp: Instant,
        timezone: ZoneId,
        includeOffset: Boolean = true
    ): String {
        val zdt = ZonedDateTime.ofInstant(timestamp, timezone)
        val formatter = if (includeOffset) {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z (XXX)")
        } else {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z")
        }
        return zdt.format(formatter)
    }

    // Helper methods

    private fun isProbableTravel(
        fromZone: ZoneId,
        toZone: ZoneId,
        hoursBetween: Long
    ): Boolean {
        val offsetDiff = getTimezoneOffsetDifference(fromZone, toZone, Instant.now())

        // If timezone changed by more than 2 hours and entries are more than 2 hours apart
        return kotlin.math.abs(offsetDiff) >= 2 && hoursBetween >= 2
    }

    private fun getTimezoneOffsetDifference(
        zone1: ZoneId,
        zone2: ZoneId,
        atInstant: Instant
    ): Int {
        val offset1 = zone1.rules.getOffset(atInstant)
        val offset2 = zone2.rules.getOffset(atInstant)
        return (offset2.totalSeconds - offset1.totalSeconds) / 3600
    }

    private fun generateTravelRecommendation(changes: List<TimezoneChange>): String {
        return when {
            changes.any { !it.isProbableTravel } -> {
                "Some timezone changes appear inconsistent. Please verify entry times."
            }
            changes.size > 5 -> {
                "Multiple timezone changes detected. Consider reviewing entries for accuracy."
            }
            else -> {
                "Travel detected. Entry times have been preserved in their original timezones."
            }
        }
    }
}

// Data classes for timezone handling

data class TimezoneAwareEntry(
    val timestamp: Instant,
    val timezone: ZoneId,
    val data: Any? = null
)

sealed class TimezoneResolution {
    data class NoConflict(
        val timestamp: Instant,
        val timezone: ZoneId
    ) : TimezoneResolution()

    data class NormalConflict(
        val originalTimestamp: Instant,
        val originalZone: ZoneId,
        val currentZone: ZoneId,
        val hoursOffset: Long,
        val convertedTimestamp: Instant,
        val preserveOriginal: Boolean
    ) : TimezoneResolution()

    data class SuspiciousConflict(
        val originalTimestamp: Instant,
        val originalZone: ZoneId,
        val currentZone: ZoneId,
        val hoursOffset: Long,
        val suggestedAction: ResolutionAction,
        val warningMessage: String
    ) : TimezoneResolution()
}

enum class ResolutionAction {
    AUTO_CONVERT,
    PRESERVE_ORIGINAL,
    REQUIRE_USER_CONFIRMATION
}

data class TimezoneChange(
    val fromZone: ZoneId,
    val toZone: ZoneId,
    val changeTime: Instant,
    val hoursBetweenEntries: Long,
    val isProbableTravel: Boolean
)

sealed class TravelDetectionResult {
    object InsufficientData : TravelDetectionResult()
    object NoTravelDetected : TravelDetectionResult()
    data class TravelDetected(
        val changes: List<TimezoneChange>,
        val recommendation: String
    ) : TravelDetectionResult()
}

sealed class ConsistencyValidation {
    data class Valid(val message: String) : ConsistencyValidation()
    data class Invalid(val issues: List<TimezoneIssue>) : ConsistencyValidation()
}

sealed class TimezoneIssue {
    data class ImpossibleJump(
        val from: TimezoneAwareEntry,
        val to: TimezoneAwareEntry,
        val reason: String
    ) : TimezoneIssue()

    data class SuspiciousPattern(
        val entries: List<TimezoneAwareEntry>,
        val reason: String
    ) : TimezoneIssue()

    data class FutureTimestamp(
        val entry: TimezoneAwareEntry,
        val reason: String
    ) : TimezoneIssue()
}

sealed class DSTHandling {
    object NoTransition : DSTHandling()

    data class UpcomingTransition(
        val transitionTime: Instant,
        val offsetChange: Int,
        val warning: String
    ) : DSTHandling()

    data class RecentTransition(
        val transitionTime: Instant,
        val offsetChange: Int,
        val note: String
    ) : DSTHandling()
}
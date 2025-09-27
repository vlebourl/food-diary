package com.fooddiary.presentation.ui.timeline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fooddiary.R
import com.fooddiary.databinding.ItemTimelineEntryBinding
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Adapter for timeline entries with correlation indicators and load more functionality
 */
class TimelineAdapter(
    private val onEntryClick: (TimelineUiEntry) -> Unit,
    private val onLoadMore: () -> Unit
) : ListAdapter<TimelineUiEntry, TimelineAdapter.TimelineViewHolder>(TimelineDiffCallback()) {

    private var hasMoreData = true

    fun setHasMoreData(hasMore: Boolean) {
        hasMoreData = hasMore
        notifyItemChanged(itemCount - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val binding = ItemTimelineEntryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TimelineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        holder.bind(getItem(position), onEntryClick)

        // Trigger load more when reaching the last few items
        if (position == itemCount - 3 && hasMoreData) {
            onLoadMore()
        }
    }

    class TimelineViewHolder(
        private val binding: ItemTimelineEntryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            entry: TimelineUiEntry,
            onEntryClick: (TimelineUiEntry) -> Unit
        ) {
            binding.apply {
                // Set entry type icon
                when (entry.type) {
                    EntryType.FOOD -> {
                        ivEntryTypeIcon.setImageResource(R.drawable.ic_restaurant_24)
                        ivEntryTypeIcon.setColorFilter(
                            itemView.context.getColor(R.color.food_entry_color)
                        )
                    }
                    EntryType.SYMPTOM -> {
                        ivEntryTypeIcon.setImageResource(R.drawable.ic_medical_services_24)
                        ivEntryTypeIcon.setColorFilter(
                            itemView.context.getColor(R.color.symptom_entry_color)
                        )
                    }
                }

                // Set timestamp
                val localDateTime = LocalDateTime.ofInstant(entry.timestamp, ZoneId.systemDefault())
                tvTimestamp.text = formatTimestamp(localDateTime)

                // Set title and subtitle
                tvTitle.text = entry.title
                tvSubtitle.text = entry.subtitle
                tvSubtitle.isVisible = !entry.subtitle.isNullOrBlank()

                // Handle severity for symptoms
                if (entry.type == EntryType.SYMPTOM && entry.severity != null) {
                    setupSeverityIndicator(entry.severity)
                } else {
                    severityIndicator.isVisible = false
                }

                // Handle correlations
                if (entry.correlations.isNotEmpty()) {
                    setupCorrelationBadge(entry.correlations)
                } else {
                    correlationBadge.isVisible = false
                }

                // Set click listener
                root.setOnClickListener {
                    onEntryClick(entry)
                }
            }
        }

        private fun setupSeverityIndicator(severity: Int) {
            binding.severityIndicator.isVisible = true
            binding.severityDots.removeAllViews()

            // Create severity dots (1-10)
            for (i in 1..10) {
                val dot = View(itemView.context).apply {
                    layoutParams = ViewGroup.MarginLayoutParams(
                        itemView.context.resources.getDimensionPixelSize(R.dimen.severity_dot_size),
                        itemView.context.resources.getDimensionPixelSize(R.dimen.severity_dot_size)
                    ).apply {
                        marginEnd = itemView.context.resources.getDimensionPixelSize(R.dimen.severity_dot_margin)
                    }

                    val dotColor = if (i <= severity) {
                        getSeverityColor(severity)
                    } else {
                        itemView.context.getColor(R.color.severity_dot_inactive)
                    }
                    setBackgroundColor(dotColor)
                }
                binding.severityDots.addView(dot)
            }

            binding.tvSeverityLevel.text = getSeverityLabel(severity)
        }

        private fun setupCorrelationBadge(correlations: List<CorrelationInfo>) {
            binding.correlationBadge.isVisible = true

            val strongestCorrelation = correlations.maxByOrNull { it.confidence }
            val confidenceLevel = when {
                strongestCorrelation?.confidence ?: 0f >= 0.8f -> "Strong"
                strongestCorrelation?.confidence ?: 0f >= 0.6f -> "Moderate"
                else -> "Weak"
            }

            binding.correlationBadge.text = "🔗 $confidenceLevel link"
        }

        private fun formatTimestamp(dateTime: LocalDateTime): String {
            val now = LocalDateTime.now()
            val today = now.toLocalDate()
            val entryDate = dateTime.toLocalDate()

            return when {
                entryDate == today -> {
                    dateTime.format(DateTimeFormatter.ofPattern("h:mm a"))
                }
                entryDate == today.minusDays(1) -> {
                    "Yesterday, ${dateTime.format(DateTimeFormatter.ofPattern("h:mm a"))}"
                }
                entryDate.year == today.year -> {
                    dateTime.format(DateTimeFormatter.ofPattern("MMM d, h:mm a"))
                }
                else -> {
                    dateTime.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))
                }
            }
        }

        private fun getSeverityColor(severity: Int): Int {
            return when {
                severity <= 3 -> itemView.context.getColor(R.color.severity_low)
                severity <= 6 -> itemView.context.getColor(R.color.severity_moderate)
                severity <= 8 -> itemView.context.getColor(R.color.severity_high)
                else -> itemView.context.getColor(R.color.severity_severe)
            }
        }

        private fun getSeverityLabel(severity: Int): String {
            val level = when {
                severity <= 3 -> "Mild"
                severity <= 6 -> "Moderate"
                severity <= 8 -> "Severe"
                else -> "Very Severe"
            }
            return "$level ($severity/10)"
        }
    }

    class TimelineDiffCallback : DiffUtil.ItemCallback<TimelineUiEntry>() {
        override fun areItemsTheSame(oldItem: TimelineUiEntry, newItem: TimelineUiEntry): Boolean {
            return oldItem.id == newItem.id && oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: TimelineUiEntry, newItem: TimelineUiEntry): Boolean {
            return oldItem == newItem
        }
    }
}
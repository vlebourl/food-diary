package com.fooddiary.presentation.ui.entry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fooddiary.R
import com.fooddiary.databinding.ItemTriggerSelectionBinding
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Adapter for potential trigger foods in symptom entry
 * Shows food entries with correlation indicators and time offsets
 */
class TriggerFoodAdapter(
    private val onTriggerSelected: (CorrelationSuggestion) -> Unit
) : ListAdapter<CorrelationSuggestion, TriggerFoodAdapter.TriggerFoodViewHolder>(TriggerFoodDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TriggerFoodViewHolder {
        val binding = ItemTriggerSelectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TriggerFoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TriggerFoodViewHolder, position: Int) {
        holder.bind(getItem(position), onTriggerSelected)
    }

    class TriggerFoodViewHolder(
        private val binding: ItemTriggerSelectionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            suggestion: CorrelationSuggestion,
            onTriggerSelected: (CorrelationSuggestion) -> Unit
        ) {
            binding.apply {
                // Set food details
                tvFoodName.text = suggestion.foodEntry.foods.joinToString(", ")
                tvMealType.text = suggestion.foodEntry.mealType.name.lowercase()
                    .replaceFirstChar { it.uppercase() }

                // Set timestamp
                val localDateTime = LocalDateTime.ofInstant(
                    suggestion.foodEntry.timestamp,
                    ZoneId.systemDefault()
                )
                tvFoodTimestamp.text = localDateTime.format(
                    DateTimeFormatter.ofPattern("MMM d, h:mm a")
                )

                // Set time offset
                tvTimeOffset.text = when {
                    suggestion.timeOffset < 1 -> "Less than 1 hour ago"
                    suggestion.timeOffset == 1L -> "1 hour ago"
                    suggestion.timeOffset < 24 -> "${suggestion.timeOffset} hours ago"
                    else -> "${suggestion.timeOffset / 24} days ago"
                }

                // Set correlation strength indicator
                when {
                    suggestion.correlationStrength >= 0.7 -> {
                        tvCorrelationStrength.text = "Strong correlation"
                        tvCorrelationStrength.setTextColor(
                            itemView.context.getColor(R.color.correlation_strong)
                        )
                        chipCorrelationLevel.text = "Strong"
                        chipCorrelationLevel.setChipBackgroundColorResource(R.color.correlation_strong_bg)
                    }
                    suggestion.correlationStrength >= 0.4 -> {
                        tvCorrelationStrength.text = "Moderate correlation"
                        tvCorrelationStrength.setTextColor(
                            itemView.context.getColor(R.color.correlation_moderate)
                        )
                        chipCorrelationLevel.text = "Moderate"
                        chipCorrelationLevel.setChipBackgroundColorResource(R.color.correlation_moderate_bg)
                    }
                    suggestion.correlationStrength > 0 -> {
                        tvCorrelationStrength.text = "Weak correlation"
                        tvCorrelationStrength.setTextColor(
                            itemView.context.getColor(R.color.correlation_weak)
                        )
                        chipCorrelationLevel.text = "Weak"
                        chipCorrelationLevel.setChipBackgroundColorResource(R.color.correlation_weak_bg)
                    }
                    else -> {
                        tvCorrelationStrength.text = "No known correlation"
                        tvCorrelationStrength.setTextColor(
                            itemView.context.getColor(R.color.correlation_none)
                        )
                        chipCorrelationLevel.text = "None"
                        chipCorrelationLevel.setChipBackgroundColorResource(R.color.correlation_none_bg)
                    }
                }

                // Show portions if available
                if (suggestion.foodEntry.portions.isNotEmpty()) {
                    tvPortions.text = "Portions: ${suggestion.foodEntry.portions.joinToString(", ")}"
                    tvPortions.visibility = android.view.View.VISIBLE
                } else {
                    tvPortions.visibility = android.view.View.GONE
                }

                // Set click listener
                root.setOnClickListener {
                    onTriggerSelected(suggestion)
                }

                // Highlight selection if this is a strong correlation
                if (suggestion.correlationStrength >= 0.7) {
                    root.strokeWidth = itemView.context.resources.getDimensionPixelSize(R.dimen.card_stroke_width)
                    root.strokeColor = itemView.context.getColor(R.color.correlation_strong)
                } else {
                    root.strokeWidth = 0
                }
            }
        }
    }

    class TriggerFoodDiffCallback : DiffUtil.ItemCallback<CorrelationSuggestion>() {
        override fun areItemsTheSame(
            oldItem: CorrelationSuggestion,
            newItem: CorrelationSuggestion
        ): Boolean {
            return oldItem.foodEntry.id == newItem.foodEntry.id
        }

        override fun areContentsTheSame(
            oldItem: CorrelationSuggestion,
            newItem: CorrelationSuggestion
        ): Boolean {
            return oldItem == newItem
        }
    }
}
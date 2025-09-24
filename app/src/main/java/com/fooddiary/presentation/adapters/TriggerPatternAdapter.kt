package com.fooddiary.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fooddiary.R
import com.fooddiary.data.database.entities.TriggerPattern
import com.fooddiary.databinding.ItemTriggerPatternBinding

class TriggerPatternAdapter(
    private val onPatternClick: (TriggerPattern) -> Unit
) : ListAdapter<TriggerPattern, TriggerPatternAdapter.TriggerPatternViewHolder>(TriggerPatternDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TriggerPatternViewHolder {
        val binding = ItemTriggerPatternBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TriggerPatternViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TriggerPatternViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TriggerPatternViewHolder(
        private val binding: ItemTriggerPatternBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onPatternClick(getItem(adapterPosition))
                }
            }
        }

        fun bind(pattern: TriggerPattern) {
            binding.apply {
                // Food name and symptom type
                tvFoodName.text = pattern.foodName
                tvSymptomType.text = pattern.symptomType.displayName

                // Correlation strength
                val correlationPercentage = (pattern.correlationStrength * 100).toInt()
                tvCorrelationStrength.text = "${correlationPercentage}%"

                // Set correlation strength color based on strength
                val correlationColor = when {
                    pattern.correlationStrength >= 0.8f -> R.color.correlation_high
                    pattern.correlationStrength >= 0.6f -> R.color.correlation_medium
                    else -> R.color.correlation_low
                }
                tvCorrelationStrength.setTextColor(
                    binding.root.context.getColor(correlationColor)
                )

                // Occurrences count
                tvOccurrences.text = binding.root.context.getString(
                    R.string.occurrences_count,
                    pattern.occurrences
                )

                // Average time to onset
                val timeToOnset = formatTimeToOnset(pattern.averageTimeOffsetMinutes)
                tvTimeToOnset.text = binding.root.context.getString(
                    R.string.time_to_onset,
                    timeToOnset
                )

                // Confidence level indicator
                val confidenceLevel = when {
                    pattern.isStatisticallySignificant && pattern.correlationStrength >= 0.7f -> "High"
                    pattern.isStatisticallySignificant -> "Medium"
                    else -> "Low"
                }
                tvConfidenceLevel.text = confidenceLevel

                val confidenceColor = when (confidenceLevel) {
                    "High" -> R.color.confidence_high
                    "Medium" -> R.color.confidence_medium
                    else -> R.color.confidence_low
                }
                tvConfidenceLevel.setTextColor(
                    binding.root.context.getColor(confidenceColor)
                )

                // Statistical significance indicator
                iconStatisticalSignificance.visibility = if (pattern.isStatisticallySignificant) {
                    android.view.View.VISIBLE
                } else {
                    android.view.View.GONE
                }

                // P-value for statistical significance
                if (pattern.isStatisticallySignificant) {
                    tvPValue.text = "p < 0.05"
                    tvPValue.visibility = android.view.View.VISIBLE
                } else {
                    tvPValue.visibility = android.view.View.GONE
                }

                // Risk level based on correlation strength
                val riskLevel = when {
                    pattern.correlationStrength >= 0.8f -> "High Risk"
                    pattern.correlationStrength >= 0.6f -> "Moderate Risk"
                    else -> "Low Risk"
                }
                tvRiskLevel.text = riskLevel

                val riskColor = when {
                    pattern.correlationStrength >= 0.8f -> R.color.risk_high
                    pattern.correlationStrength >= 0.6f -> R.color.risk_medium
                    else -> R.color.risk_low
                }
                tvRiskLevel.setTextColor(
                    binding.root.context.getColor(riskColor)
                )

                // Last occurrence date
                if (pattern.lastOccurrenceDate.isNotEmpty()) {
                    tvLastOccurrence.text = binding.root.context.getString(
                        R.string.last_occurrence,
                        pattern.lastOccurrenceDate
                    )
                    tvLastOccurrence.visibility = android.view.View.VISIBLE
                } else {
                    tvLastOccurrence.visibility = android.view.View.GONE
                }
            }
        }

        private fun formatTimeToOnset(minutes: Int): String {
            return when {
                minutes < 60 -> "${minutes}m"
                minutes % 60 == 0 -> "${minutes / 60}h"
                else -> "${minutes / 60}h ${minutes % 60}m"
            }
        }
    }
}

class TriggerPatternDiffCallback : DiffUtil.ItemCallback<TriggerPattern>() {
    override fun areItemsTheSame(oldItem: TriggerPattern, newItem: TriggerPattern): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TriggerPattern, newItem: TriggerPattern): Boolean {
        return oldItem == newItem
    }
}
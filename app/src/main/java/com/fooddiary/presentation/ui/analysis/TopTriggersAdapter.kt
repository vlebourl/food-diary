package com.fooddiary.presentation.ui.analysis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fooddiary.R
import com.fooddiary.databinding.ItemTriggerAnalysisBinding
import com.fooddiary.presentation.ui.analytics.CorrelationData

/**
 * Adapter for displaying top trigger foods with correlation analysis
 */
class TopTriggersAdapter(
    private val onTriggerClick: (CorrelationData) -> Unit
) : ListAdapter<CorrelationData, TopTriggersAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTriggerAnalysisBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onTriggerClick)
    }

    class ViewHolder(
        private val binding: ItemTriggerAnalysisBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(correlation: CorrelationData, onTriggerClick: (CorrelationData) -> Unit) {
            binding.apply {
                tvTriggerFoodName.text = correlation.foodName
                tvSymptomType.text = correlation.symptomType.name.lowercase()
                    .replaceFirstChar { it.uppercase() }

                // Set correlation strength
                val strengthPercent = (correlation.strength * 100).toInt()
                tvCorrelationStrength.text = "$strengthPercent%"

                // Set strength color and level
                when {
                    correlation.strength >= 0.7 -> {
                        tvCorrelationStrength.setTextColor(
                            itemView.context.getColor(R.color.correlation_strong)
                        )
                        chipCorrelationLevel.text = "Strong"
                        chipCorrelationLevel.setChipBackgroundColorResource(R.color.correlation_strong_bg)
                    }
                    correlation.strength >= 0.4 -> {
                        tvCorrelationStrength.setTextColor(
                            itemView.context.getColor(R.color.correlation_moderate)
                        )
                        chipCorrelationLevel.text = "Moderate"
                        chipCorrelationLevel.setChipBackgroundColorResource(R.color.correlation_moderate_bg)
                    }
                    else -> {
                        tvCorrelationStrength.setTextColor(
                            itemView.context.getColor(R.color.correlation_weak)
                        )
                        chipCorrelationLevel.text = "Weak"
                        chipCorrelationLevel.setChipBackgroundColorResource(R.color.correlation_weak_bg)
                    }
                }

                // Set occurrences
                tvOccurrenceCount.text = "${correlation.occurrences} occurrences"

                // Set confidence
                val confidencePercent = (correlation.confidence * 100).toInt()
                tvConfidenceLevel.text = "$confidencePercent% confidence"

                // Set click listener
                root.setOnClickListener {
                    onTriggerClick(correlation)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CorrelationData>() {
        override fun areItemsTheSame(oldItem: CorrelationData, newItem: CorrelationData): Boolean {
            return oldItem.foodId == newItem.foodId && oldItem.symptomType == newItem.symptomType
        }

        override fun areContentsTheSame(oldItem: CorrelationData, newItem: CorrelationData): Boolean {
            return oldItem == newItem
        }
    }
}
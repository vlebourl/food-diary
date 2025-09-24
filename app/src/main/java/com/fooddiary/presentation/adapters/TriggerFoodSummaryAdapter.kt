package com.fooddiary.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fooddiary.R
import com.fooddiary.databinding.ItemTriggerFoodSummaryBinding
import com.fooddiary.presentation.viewmodel.TriggerFoodSummary

class TriggerFoodSummaryAdapter(
    private val onFoodClick: (TriggerFoodSummary) -> Unit
) : ListAdapter<TriggerFoodSummary, TriggerFoodSummaryAdapter.TriggerFoodSummaryViewHolder>(TriggerFoodSummaryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TriggerFoodSummaryViewHolder {
        val binding = ItemTriggerFoodSummaryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TriggerFoodSummaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TriggerFoodSummaryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TriggerFoodSummaryViewHolder(
        private val binding: ItemTriggerFoodSummaryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onFoodClick(getItem(adapterPosition))
                }
            }
        }

        fun bind(foodSummary: TriggerFoodSummary) {
            binding.apply {
                // Food name
                tvFoodName.text = foodSummary.foodName

                // Average correlation
                val correlationPercentage = (foodSummary.averageCorrelation * 100).toInt()
                tvAverageCorrelation.text = "${correlationPercentage}%"

                // Set correlation color
                val correlationColor = when {
                    foodSummary.averageCorrelation >= 0.8f -> R.color.correlation_high
                    foodSummary.averageCorrelation >= 0.6f -> R.color.correlation_medium
                    else -> R.color.correlation_low
                }
                tvAverageCorrelation.setTextColor(
                    binding.root.context.getColor(correlationColor)
                )

                // Risk level
                tvRiskLevel.text = foodSummary.riskLevel
                val riskColor = when (foodSummary.riskLevel) {
                    "High Risk" -> R.color.risk_high
                    "Moderate Risk" -> R.color.risk_medium
                    else -> R.color.risk_low
                }
                tvRiskLevel.setTextColor(
                    binding.root.context.getColor(riskColor)
                )

                // Total occurrences
                tvTotalOccurrences.text = binding.root.context.getString(
                    R.string.total_occurrences,
                    foodSummary.totalOccurrences
                )

                // Average time to onset
                tvTimeToOnset.text = binding.root.context.getString(
                    R.string.avg_time_to_onset,
                    foodSummary.timeToOnsetFormatted
                )

                // Affected symptom types
                val symptomTypeNames = foodSummary.symptomTypes
                    .map { it.displayName }
                    .take(3) // Show max 3 symptom types
                    .joinToString(", ")

                val symptomTypesText = if (foodSummary.symptomTypes.size > 3) {
                    "$symptomTypeNames +${foodSummary.symptomTypes.size - 3} more"
                } else {
                    symptomTypeNames
                }

                tvAffectedSymptoms.text = binding.root.context.getString(
                    R.string.affects_symptoms,
                    symptomTypesText
                )

                // Set card background color based on risk level
                val cardBackgroundColor = when (foodSummary.riskLevel) {
                    "High Risk" -> R.color.card_background_high_risk
                    "Moderate Risk" -> R.color.card_background_medium_risk
                    else -> R.color.card_background_low_risk
                }
                cardFood.setCardBackgroundColor(
                    binding.root.context.getColor(cardBackgroundColor)
                )

                // Risk indicator icon
                val riskIcon = when (foodSummary.riskLevel) {
                    "High Risk" -> R.drawable.ic_warning_high
                    "Moderate Risk" -> R.drawable.ic_warning_medium
                    else -> R.drawable.ic_info
                }
                iconRiskLevel.setImageResource(riskIcon)

                // Confidence indicator based on number of occurrences
                val confidenceLevel = when {
                    foodSummary.totalOccurrences >= 20 -> "Very High"
                    foodSummary.totalOccurrences >= 10 -> "High"
                    foodSummary.totalOccurrences >= 5 -> "Medium"
                    else -> "Low"
                }

                tvConfidenceLevel.text = binding.root.context.getString(
                    R.string.confidence_level,
                    confidenceLevel
                )

                val confidenceColor = when (confidenceLevel) {
                    "Very High", "High" -> R.color.confidence_high
                    "Medium" -> R.color.confidence_medium
                    else -> R.color.confidence_low
                }
                tvConfidenceLevel.setTextColor(
                    binding.root.context.getColor(confidenceColor)
                )
            }
        }
    }
}

class TriggerFoodSummaryDiffCallback : DiffUtil.ItemCallback<TriggerFoodSummary>() {
    override fun areItemsTheSame(oldItem: TriggerFoodSummary, newItem: TriggerFoodSummary): Boolean {
        return oldItem.foodName == newItem.foodName
    }

    override fun areContentsTheSame(oldItem: TriggerFoodSummary, newItem: TriggerFoodSummary): Boolean {
        return oldItem == newItem
    }
}
package com.fooddiary.presentation.ui.analysis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fooddiary.R
import com.fooddiary.databinding.ItemSymptomTrendBinding

/**
 * Adapter for displaying symptom trends and insights
 */
class SymptomTrendsAdapter : ListAdapter<SymptomTrendItem, SymptomTrendsAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSymptomTrendBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemSymptomTrendBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SymptomTrendItem) {
            binding.apply {
                tvTrendTitle.text = item.title
                tvTrendDescription.text = item.description

                // Set trend indicator
                when (item.trend) {
                    TrendDirection.INCREASING -> {
                        ivTrendIndicator.setImageResource(R.drawable.ic_trending_up_24)
                        ivTrendIndicator.setColorFilter(
                            itemView.context.getColor(R.color.trend_increasing)
                        )
                        tvTrendDirection.text = "Increasing"
                        tvTrendDirection.setTextColor(
                            itemView.context.getColor(R.color.trend_increasing)
                        )
                    }
                    TrendDirection.DECREASING -> {
                        ivTrendIndicator.setImageResource(R.drawable.ic_trending_down_24)
                        ivTrendIndicator.setColorFilter(
                            itemView.context.getColor(R.color.trend_decreasing)
                        )
                        tvTrendDirection.text = "Decreasing"
                        tvTrendDirection.setTextColor(
                            itemView.context.getColor(R.color.trend_decreasing)
                        )
                    }
                    TrendDirection.STABLE -> {
                        ivTrendIndicator.setImageResource(R.drawable.ic_trending_flat_24)
                        ivTrendIndicator.setColorFilter(
                            itemView.context.getColor(R.color.trend_stable)
                        )
                        tvTrendDirection.text = "Stable"
                        tvTrendDirection.setTextColor(
                            itemView.context.getColor(R.color.trend_stable)
                        )
                    }
                }

                // Set confidence level
                val confidencePercent = (item.confidence * 100).toInt()
                tvConfidenceLevel.text = "$confidencePercent% confidence"

                // Set confidence color
                val confidenceColor = when {
                    item.confidence >= 0.8 -> itemView.context.getColor(R.color.confidence_high)
                    item.confidence >= 0.6 -> itemView.context.getColor(R.color.confidence_medium)
                    else -> itemView.context.getColor(R.color.confidence_low)
                }
                tvConfidenceLevel.setTextColor(confidenceColor)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<SymptomTrendItem>() {
        override fun areItemsTheSame(oldItem: SymptomTrendItem, newItem: SymptomTrendItem): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: SymptomTrendItem, newItem: SymptomTrendItem): Boolean {
            return oldItem == newItem
        }
    }
}
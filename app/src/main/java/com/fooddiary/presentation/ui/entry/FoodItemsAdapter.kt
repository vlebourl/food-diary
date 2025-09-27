package com.fooddiary.presentation.ui.entry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fooddiary.R
import com.fooddiary.data.database.entities.FODMAPFood
import com.fooddiary.databinding.ItemFoodEntryBinding

/**
 * Adapter for food items in the food entry form
 * Handles portion editing and food removal
 */
class FoodItemsAdapter(
    private val onRemoveFood: (Int) -> Unit,
    private val onPortionChanged: (Int, String) -> Unit
) : ListAdapter<Pair<FODMAPFood, String>, FoodItemsAdapter.FoodItemViewHolder>(FoodItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
        val binding = ItemFoodEntryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FoodItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        holder.bind(getItem(position), position, onRemoveFood, onPortionChanged)
    }

    class FoodItemViewHolder(
        private val binding: ItemFoodEntryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            foodWithPortion: Pair<FODMAPFood, String>,
            position: Int,
            onRemoveFood: (Int) -> Unit,
            onPortionChanged: (Int, String) -> Unit
        ) {
            val (food, portion) = foodWithPortion

            binding.apply {
                // Set food name
                tvFoodName.text = food.name

                // Set FODMAP level with appropriate color
                tvFodmapLevel.text = food.overallLevel.displayName
                val fodmapColor = when (food.overallLevel) {
                    FODMAPLevel.LOW -> itemView.context.getColor(R.color.fodmap_low)
                    FODMAPLevel.MODERATE -> itemView.context.getColor(R.color.fodmap_moderate)
                    FODMAPLevel.HIGH -> itemView.context.getColor(R.color.fodmap_high)
                }
                tvFodmapLevel.setTextColor(fodmapColor)

                // Set category
                tvFoodCategory.text = food.category.name.lowercase()
                    .replaceFirstChar { it.uppercase() }

                // Set portion
                etPortion.setText(portion)

                // Handle portion changes
                etPortion.addTextChangedListener { text ->
                    val newPortion = text?.toString() ?: ""
                    if (newPortion != portion) {
                        onPortionChanged(position, newPortion)
                    }
                }

                // Handle removal
                btnRemoveFood.setOnClickListener {
                    onRemoveFood(position)
                }

                // Show serving size suggestion
                tvServingSizeSuggestion.text = "Suggested: ${food.servingSize}"

                // Show notes if available
                if (!food.notes.isNullOrBlank()) {
                    tvFoodNotes.text = food.notes
                    tvFoodNotes.visibility = android.view.View.VISIBLE
                } else {
                    tvFoodNotes.visibility = android.view.View.GONE
                }
            }
        }
    }

    class FoodItemDiffCallback : DiffUtil.ItemCallback<Pair<FODMAPFood, String>>() {
        override fun areItemsTheSame(
            oldItem: Pair<FODMAPFood, String>,
            newItem: Pair<FODMAPFood, String>
        ): Boolean {
            return oldItem.first.id == newItem.first.id
        }

        override fun areContentsTheSame(
            oldItem: Pair<FODMAPFood, String>,
            newItem: Pair<FODMAPFood, String>
        ): Boolean {
            return oldItem == newItem
        }
    }
}
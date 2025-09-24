package com.fooddiary.presentation.ui.entry

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.fooddiary.R

public class EntryFragmentDirections private constructor() {
  public companion object {
    public fun actionEntryToFoodEntry(): NavDirections =
        ActionOnlyNavDirections(R.id.action_entry_to_food_entry)

    public fun actionEntryToSymptomEntry(): NavDirections =
        ActionOnlyNavDirections(R.id.action_entry_to_symptom_entry)

    public fun actionEntryToBeverageEntry(): NavDirections =
        ActionOnlyNavDirections(R.id.action_entry_to_beverage_entry)
  }
}

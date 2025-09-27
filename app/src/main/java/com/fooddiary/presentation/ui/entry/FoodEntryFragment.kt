package com.fooddiary.presentation.ui.entry

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.fooddiary.R
import com.fooddiary.data.models.MealType
import com.fooddiary.databinding.FragmentFoodEntryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Food entry form with dynamic food list management, FODMAP analysis, and form validation
 * Features: Food search, portion tracking, meal type selection, context capture
 */
@AndroidEntryPoint
class FoodEntryFragment : Fragment() {

    private var _binding: FragmentFoodEntryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FoodEntryViewModel by viewModels()
    private lateinit var foodItemsAdapter: FoodItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupMealTypeChips()
        setupFoodItemInput()
        setupContextSpinners()
        setupDateTimePicker()
        setupActionButtons()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        foodItemsAdapter = FoodItemsAdapter(
            onRemoveFood = { index ->
                viewModel.removeFood(index)
            },
            onPortionChanged = { index, portion ->
                viewModel.updatePortion(index, portion)
            }
        )

        binding.rvFoodItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = foodItemsAdapter
        }
    }

    private fun setupMealTypeChips() {
        binding.chipBreakfast.setOnClickListener {
            viewModel.updateMealType(MealType.BREAKFAST)
        }
        binding.chipLunch.setOnClickListener {
            viewModel.updateMealType(MealType.LUNCH)
        }
        binding.chipDinner.setOnClickListener {
            viewModel.updateMealType(MealType.DINNER)
        }
        binding.chipSnack.setOnClickListener {
            viewModel.updateMealType(MealType.SNACK)
        }
        binding.chipBeverage.setOnClickListener {
            viewModel.updateMealType(MealType.BEVERAGE)
        }

        // Set default selection
        binding.chipBreakfast.isChecked = true
    }

    private fun setupFoodItemInput() {
        binding.btnAddFoodItem.setOnClickListener {
            showAddFoodDialog()
        }

        binding.btnAddIngredient.setOnClickListener {
            val ingredient = binding.etNewIngredient.text?.toString()?.trim()
            if (!ingredient.isNullOrBlank()) {
                addIngredientChip(ingredient)
                binding.etNewIngredient.text?.clear()
            }
        }

        binding.etNewIngredient.setOnEditorActionListener { _, _, _ ->
            binding.btnAddIngredient.performClick()
            true
        }
    }

    private fun setupContextSpinners() {
        // Setup location spinner
        val locationOptions = arrayOf(
            "Home", "Restaurant", "Work", "School", "Friend's house", "Other"
        )
        val locationAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            locationOptions
        )
        binding.spinnerLocation.setAdapter(locationAdapter)

        // Setup social context spinner
        val socialOptions = arrayOf(
            "Alone", "With family", "With friends", "With colleagues", "Date", "Business meal"
        )
        val socialAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            socialOptions
        )
        binding.spinnerSocialContext.setAdapter(socialAdapter)

        // Setup eating speed spinner
        val speedOptions = arrayOf(
            "Very slow", "Slow", "Normal", "Fast", "Very fast"
        )
        val speedAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            speedOptions
        )
        binding.spinnerEatingSpeed.setAdapter(speedAdapter)
    }

    private fun setupDateTimePicker() {
        updateDateTimeDisplay(Instant.now())

        binding.btnSelectDateTime.setOnClickListener {
            showDateTimePicker()
        }
    }

    private fun setupActionButtons() {
        binding.btnSave.setOnClickListener {
            saveEntry()
        }

        binding.btnCancel.setOnClickListener {
            showCancelConfirmation()
        }

        // Setup notes input
        binding.etNotes.addTextChangedListener { text ->
            viewModel.updateNotes(text?.toString() ?: "")
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun updateUI(state: FoodEntryUiState) {
        // Update food items list
        foodItemsAdapter.submitList(state.selectedFoods.zip(state.portions))
        binding.emptyFoodItems.isVisible = state.selectedFoods.isEmpty()
        binding.rvFoodItems.isVisible = state.selectedFoods.isNotEmpty()

        // Update meal type selection
        updateMealTypeSelection(state.mealType)

        // Update FODMAP analysis
        state.fodmapAnalysis?.let { analysis ->
            binding.cardFodmapAnalysis.isVisible = true
            binding.tvOverallFodmapLevel.text = analysis.overallLevel.displayName
            binding.tvOligosaccharidesLevel.text = analysis.oligosaccharides.name
            binding.tvDisaccharidesLevel.text = analysis.disaccharides.name
            binding.tvMonosaccharidesLevel.text = analysis.monosaccharides.name
            binding.tvPolyolsLevel.text = analysis.polyols.name

            if (analysis.problematicIngredients.isNotEmpty()) {
                binding.tvProblematicIngredients.isVisible = true
                binding.tvProblematicIngredients.text =
                    "Problematic ingredients: ${analysis.problematicIngredients.joinToString(", ")}"
            } else {
                binding.tvProblematicIngredients.isVisible = false
            }
        } ?: run {
            binding.cardFodmapAnalysis.isVisible = false
        }

        // Update loading states
        binding.progressBar.isVisible = state.isSaving
        binding.btnSave.isEnabled = !state.isSaving && state.isValid

        // Show validation errors
        if (state.validationErrors.isNotEmpty()) {
            Toast.makeText(
                requireContext(),
                state.validationErrors.first(),
                Toast.LENGTH_LONG
            ).show()
        }

        // Show duplicate warning
        state.duplicateWarning?.let { warning ->
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Duplicate Entry")
                .setMessage(warning)
                .setPositiveButton("Continue") { _, _ -> }
                .setNegativeButton("Review") { _, _ -> }
                .show()
        }

        // Show messages
        state.message?.let { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        // Update timestamp display
        updateDateTimeDisplay(state.timestamp)
    }

    private fun updateMealTypeSelection(mealType: MealType) {
        binding.chipGroupMealType.clearCheck()
        when (mealType) {
            MealType.BREAKFAST -> binding.chipBreakfast.isChecked = true
            MealType.LUNCH -> binding.chipLunch.isChecked = true
            MealType.DINNER -> binding.chipDinner.isChecked = true
            MealType.SNACK -> binding.chipSnack.isChecked = true
            MealType.BEVERAGE -> binding.chipBeverage.isChecked = true
        }
    }

    private fun showAddFoodDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_food, null)
        val searchInput = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.et_food_search)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add Food Item")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val foodName = searchInput.text?.toString()?.trim()
                if (!foodName.isNullOrBlank()) {
                    viewModel.addFood(foodName)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()

        searchInput.requestFocus()
    }

    private fun addIngredientChip(ingredient: String) {
        val chip = Chip(requireContext()).apply {
            text = ingredient
            isCloseIconVisible = true
            setOnCloseIconClickListener {
                binding.chipGroupIngredients.removeView(this)
            }
        }
        binding.chipGroupIngredients.addView(chip)
    }

    private fun showDateTimePicker() {
        val now = Calendar.getInstance()

        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        val selectedDateTime = Calendar.getInstance().apply {
                            set(year, month, dayOfMonth, hourOfDay, minute)
                        }
                        viewModel.updateTimestamp(selectedDateTime.toInstant())
                    },
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    false
                ).show()
            },
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateDateTimeDisplay(timestamp: Instant) {
        val localDateTime = LocalDateTime.ofInstant(timestamp, ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy 'at' h:mm a")
        binding.tvSelectedDatetime.text = localDateTime.format(formatter)
    }

    private fun saveEntry() {
        viewLifecycleOwner.lifecycleScope.launch {
            val success = viewModel.saveEntry()
            if (success) {
                Toast.makeText(requireContext(), "Entry saved successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }
    }

    private fun showCancelConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Discard Changes?")
            .setMessage("Are you sure you want to discard your changes?")
            .setPositiveButton("Discard") { _, _ ->
                findNavController().navigateUp()
            }
            .setNegativeButton("Continue Editing", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

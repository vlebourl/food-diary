package com.fooddiary.presentation.ui.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.fooddiary.R
import com.fooddiary.data.models.*
import com.fooddiary.databinding.FragmentFoodEntryBinding
import com.fooddiary.presentation.viewmodel.FoodEntryViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class FoodEntryFragment : Fragment() {

    private var _binding: FragmentFoodEntryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FoodEntryViewModel by viewModels()

    private lateinit var mealTypeAdapter: ArrayAdapter<MealType>
    private lateinit var locationAdapter: ArrayAdapter<LocationType>
    private lateinit var socialContextAdapter: ArrayAdapter<SocialContext>
    private lateinit var eatingSpeedAdapter: ArrayAdapter<EatingSpeed>
    private lateinit var portionUnitAdapter: ArrayAdapter<String>

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

        setupAdapters()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupAdapters() {
        // Meal Type Spinner
        mealTypeAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            MealType.values()
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerMealType.adapter = mealTypeAdapter

        // Location Spinner
        locationAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            LocationType.values()
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerLocation.adapter = locationAdapter

        // Social Context Spinner
        socialContextAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            SocialContext.values()
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerSocialContext.adapter = socialContextAdapter

        // Eating Speed Spinner
        eatingSpeedAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            EatingSpeed.values()
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerEatingSpeed.adapter = eatingSpeedAdapter

        // Portion Unit Spinner
        val portionUnits = listOf("grams", "ounces", "cups", "tablespoons", "teaspoons", "pieces", "slices")
        portionUnitAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            portionUnits
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerPortionUnit.adapter = portionUnitAdapter
    }

    private fun setupClickListeners() {
        // Text input listeners
        binding.etFoodName.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.updateFoodName(binding.etFoodName.text.toString())
            }
        }

        binding.etPortions.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.updatePortions(binding.etPortions.text.toString())
            }
        }

        binding.etPreparationMethod.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.updatePreparationMethod(binding.etPreparationMethod.text.toString())
            }
        }

        binding.etNotes.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.updateNotes(binding.etNotes.text.toString())
            }
        }

        // Spinner listeners
        binding.spinnerMealType.setOnItemSelectedListener { _, _, position, _ ->
            viewModel.updateMealType(MealType.values()[position])
        }

        binding.spinnerLocation.setOnItemSelectedListener { _, _, position, _ ->
            viewModel.updateLocation(LocationType.values()[position])
        }

        binding.spinnerSocialContext.setOnItemSelectedListener { _, _, position, _ ->
            viewModel.updateSocialContext(SocialContext.values()[position])
        }

        binding.spinnerEatingSpeed.setOnItemSelectedListener { _, _, position, _ ->
            viewModel.updateEatingSpeed(EatingSpeed.values()[position])
        }

        binding.spinnerPortionUnit.setOnItemSelectedListener { _, _, position, _ ->
            val units = listOf("grams", "ounces", "cups", "tablespoons", "teaspoons", "pieces", "slices")
            viewModel.updatePortionUnit(units[position])
        }

        // Date/Time picker
        binding.btnSelectDateTime.setOnClickListener {
            showDateTimePicker()
        }

        // Add ingredient button
        binding.btnAddIngredient.setOnClickListener {
            val ingredient = binding.etNewIngredient.text.toString().trim()
            if (ingredient.isNotEmpty()) {
                viewModel.addIngredient(ingredient)
                binding.etNewIngredient.setText("")
            }
        }

        // Save button
        binding.btnSave.setOnClickListener {
            viewModel.saveFoodEntry()
        }

        // Cancel button
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Observe UI state
                launch {
                    viewModel.uiState.collect { state ->
                        updateUI(state)
                    }
                }

                // Observe FODMAP analysis
                launch {
                    viewModel.fodmapAnalysis.collect { analysis ->
                        updateFODMAPAnalysis(analysis)
                    }
                }
            }
        }
    }

    private fun updateUI(state: FoodEntryViewModel.FoodEntryUiState) {
        // Update form fields if not currently focused
        if (!binding.etFoodName.hasFocus() && binding.etFoodName.text.toString() != state.foodName) {
            binding.etFoodName.setText(state.foodName)
        }

        if (!binding.etPortions.hasFocus() && binding.etPortions.text.toString() != state.portions) {
            binding.etPortions.setText(state.portions)
        }

        // Update ingredients chips
        updateIngredientsChips(state.ingredients)

        // Update date/time display
        binding.btnSelectDateTime.text = state.timestampFormatted

        // Show loading state
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.btnSave.isEnabled = !state.isLoading && state.isValid

        // Handle errors
        binding.tilFoodName.error = state.foodNameError
        binding.tilPortions.error = state.portionsError

        // Handle success
        if (state.isSuccess) {
            Toast.makeText(requireContext(), R.string.entry_saved, Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        // Handle general errors
        state.errorMessage?.let { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    private fun updateIngredientsChips(ingredients: List<String>) {
        binding.chipGroupIngredients.removeAllViews()

        ingredients.forEach { ingredient ->
            val chip = Chip(requireContext()).apply {
                text = ingredient
                isCloseIconVisible = true
                setOnCloseIconClickListener {
                    viewModel.removeIngredient(ingredient)
                }
            }
            binding.chipGroupIngredients.addView(chip)
        }
    }

    private fun updateFODMAPAnalysis(analysis: FODMAPAnalysis?) {
        if (analysis != null) {
            binding.cardFodmapAnalysis.visibility = View.VISIBLE
            binding.tvOverallFodmapLevel.text = getString(
                when (analysis.overallLevel) {
                    FODMAPLevel.LOW -> R.string.low_fodmap
                    FODMAPLevel.MEDIUM -> R.string.medium_fodmap
                    FODMAPLevel.HIGH -> R.string.high_fodmap
                }
            )

            // Update FODMAP component levels
            binding.tvOligosaccharidesLevel.text = analysis.oligosaccharides.displayName
            binding.tvDisaccharidesLevel.text = analysis.disaccharides.displayName
            binding.tvMonosaccharidesLevel.text = analysis.monosaccharides.displayName
            binding.tvPolyolsLevel.text = analysis.polyols.displayName

            // Show problematic ingredients
            if (analysis.problematicIngredients.isNotEmpty()) {
                binding.tvProblematicIngredients.visibility = View.VISIBLE
                binding.tvProblematicIngredients.text = getString(
                    R.string.problematic_ingredients,
                    analysis.problematicIngredients.joinToString(", ")
                )
            } else {
                binding.tvProblematicIngredients.visibility = View.GONE
            }
        } else {
            binding.cardFodmapAnalysis.visibility = View.GONE
        }
    }

    private fun showDateTimePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { dateInMillis ->
            showTimePicker(dateInMillis)
        }

        datePicker.show(parentFragmentManager, "DATE_PICKER")
    }

    private fun showTimePicker(dateInMillis: Long) {
        val now = LocalDateTime.now()
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(now.hour)
            .setMinute(now.minute)
            .setTitleText("Select Time")
            .build()

        timePicker.addOnPositiveButtonClickListener {
            val selectedDateTime = Instant.ofEpochMilli(dateInMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .atTime(timePicker.hour, timePicker.minute)
                .atZone(ZoneId.systemDefault())
                .toInstant()

            viewModel.updateTimestamp(selectedDateTime)
        }

        timePicker.show(parentFragmentManager, "TIME_PICKER")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
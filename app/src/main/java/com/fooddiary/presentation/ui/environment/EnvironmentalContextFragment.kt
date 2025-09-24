package com.fooddiary.presentation.ui.environment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fooddiary.R
import com.fooddiary.databinding.FragmentEnvironmentalContextBinding
import com.fooddiary.presentation.viewmodel.EnvironmentalContextViewModel
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class EnvironmentalContextFragment : Fragment() {

    private var _binding: FragmentEnvironmentalContextBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EnvironmentalContextViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnvironmentalContextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupClickListeners()
        observeViewModel()

        // Load today's context if exists
        viewModel.loadTodaysContext()
    }

    private fun setupUI() {
        // Set today's date
        val today = LocalDate.now()
        binding.textSelectedDate.text = today.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"))

        // Setup sliders with proper ranges and labels
        setupSliders()

        // Setup chip groups
        setupChipGroups()
    }

    private fun setupSliders() {
        // Stress Level Slider (1-10)
        binding.sliderStressLevel.apply {
            valueFrom = 1f
            valueTo = 10f
            value = 5f
            stepSize = 1f
            setLabelFormatter { value ->
                getStressLevelLabel(value.toInt())
            }
            addOnChangeListener { _, value, _ ->
                binding.textStressValue.text = "${value.toInt()}/10"
                viewModel.updateStressLevel(value.toInt())
            }
        }

        // Sleep Quality Slider (1-10)
        binding.sliderSleepQuality.apply {
            valueFrom = 1f
            valueTo = 10f
            value = 7f
            stepSize = 1f
            setLabelFormatter { value ->
                getSleepQualityLabel(value.toInt())
            }
            addOnChangeListener { _, value, _ ->
                binding.textSleepValue.text = "${value.toInt()}/10"
                viewModel.updateSleepQuality(value.toInt())
            }
        }

        // Energy Level Slider (1-10)
        binding.sliderEnergyLevel.apply {
            valueFrom = 1f
            valueTo = 10f
            value = 6f
            stepSize = 1f
            setLabelFormatter { value ->
                getEnergyLevelLabel(value.toInt())
            }
            addOnChangeListener { _, value, _ ->
                binding.textEnergyValue.text = "${value.toInt()}/10"
                viewModel.updateEnergyLevel(value.toInt())
            }
        }

        // Hydration Slider (0-4 liters)
        binding.sliderHydration.apply {
            valueFrom = 0f
            valueTo = 4f
            value = 2f
            stepSize = 0.25f
            setLabelFormatter { value ->
                "${value}L"
            }
            addOnChangeListener { _, value, _ ->
                binding.textHydrationValue.text = "${value}L"
                viewModel.updateHydration(value)
            }
        }

        // Sleep Hours Slider (4-12 hours)
        binding.sliderSleepHours.apply {
            valueFrom = 4f
            valueTo = 12f
            value = 8f
            stepSize = 0.5f
            setLabelFormatter { value ->
                "${value}h"
            }
            addOnChangeListener { _, value, _ ->
                binding.textSleepHoursValue.text = "${value}h"
                viewModel.updateSleepHours(value)
            }
        }
    }

    private fun setupChipGroups() {
        // Exercise chips - single selection
        binding.chipGroupExercise.setOnCheckedStateChangeListener { group, checkedIds ->
            val exerciseType = when {
                checkedIds.contains(R.id.chip_exercise_none) -> "None"
                checkedIds.contains(R.id.chip_exercise_light) -> "Light"
                checkedIds.contains(R.id.chip_exercise_moderate) -> "Moderate"
                checkedIds.contains(R.id.chip_exercise_intense) -> "Intense"
                else -> "None"
            }
            viewModel.updateExerciseLevel(exerciseType)
        }

        // Mood chips - multiple selection allowed
        binding.chipGroupMood.setOnCheckedStateChangeListener { group, checkedIds ->
            val moods = mutableListOf<String>()

            if (checkedIds.contains(R.id.chip_mood_happy)) moods.add("Happy")
            if (checkedIds.contains(R.id.chip_mood_anxious)) moods.add("Anxious")
            if (checkedIds.contains(R.id.chip_mood_stressed)) moods.add("Stressed")
            if (checkedIds.contains(R.id.chip_mood_calm)) moods.add("Calm")
            if (checkedIds.contains(R.id.chip_mood_tired)) moods.add("Tired")
            if (checkedIds.contains(R.id.chip_mood_energetic)) moods.add("Energetic")
            if (checkedIds.contains(R.id.chip_mood_irritable)) moods.add("Irritable")

            viewModel.updateMoods(moods)
        }

        // Environment chips - multiple selection
        binding.chipGroupEnvironment.setOnCheckedStateChangeListener { group, checkedIds ->
            val environments = mutableListOf<String>()

            if (checkedIds.contains(R.id.chip_env_home)) environments.add("Home")
            if (checkedIds.contains(R.id.chip_env_work)) environments.add("Work")
            if (checkedIds.contains(R.id.chip_env_travel)) environments.add("Travel")
            if (checkedIds.contains(R.id.chip_env_social)) environments.add("Social")
            if (checkedIds.contains(R.id.chip_env_restaurant)) environments.add("Restaurant")
            if (checkedIds.contains(R.id.chip_env_outdoor)) environments.add("Outdoor")

            viewModel.updateEnvironments(environments)
        }

        // Weather chips - single selection
        binding.chipGroupWeather.setOnCheckedStateChangeListener { group, checkedIds ->
            val weather = when {
                checkedIds.contains(R.id.chip_weather_sunny) -> "Sunny"
                checkedIds.contains(R.id.chip_weather_cloudy) -> "Cloudy"
                checkedIds.contains(R.id.chip_weather_rainy) -> "Rainy"
                checkedIds.contains(R.id.chip_weather_hot) -> "Hot"
                checkedIds.contains(R.id.chip_weather_cold) -> "Cold"
                checkedIds.contains(R.id.chip_weather_humid) -> "Humid"
                else -> null
            }
            viewModel.updateWeather(weather)
        }
    }

    private fun setupClickListeners() {
        binding.buttonSaveContext.setOnClickListener {
            // Add any additional notes
            val notes = binding.editTextNotes.text.toString().trim()
            viewModel.updateNotes(notes)

            // Save the context
            viewModel.saveEnvironmentalContext()
        }

        binding.buttonClearAll.setOnClickListener {
            clearAllInputs()
            viewModel.clearContext()
        }

        binding.chipQuickStress.setOnClickListener {
            // Quick preset for stressful day
            binding.sliderStressLevel.value = 8f
            binding.sliderEnergyLevel.value = 3f
            binding.sliderSleepQuality.value = 4f
            binding.chipMoodStressed.isChecked = true
            binding.chipMoodTired.isChecked = true
        }

        binding.chipQuickGood.setOnClickListener {
            // Quick preset for good day
            binding.sliderStressLevel.value = 3f
            binding.sliderEnergyLevel.value = 8f
            binding.sliderSleepQuality.value = 8f
            binding.chipMoodHappy.isChecked = true
            binding.chipMoodEnergetic.isChecked = true
        }

        binding.buttonViewHistory.setOnClickListener {
            // Navigate to environmental context history
            viewModel.navigateToHistory()
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { event ->
                    handleEvent(event)
                }
            }
        }
    }

    private fun updateUI(state: EnvironmentalContextUiState) {
        binding.apply {
            // Loading state
            progressIndicator.visibility = if (state.isLoading) View.VISIBLE else View.GONE

            // Save button state
            buttonSaveContext.isEnabled = !state.isLoading && state.hasUnsavedChanges

            // Load existing context if available
            state.existingContext?.let { context ->
                sliderStressLevel.value = context.stressLevel.toFloat()
                sliderSleepQuality.value = context.sleepQuality.toFloat()
                sliderEnergyLevel.value = context.energyLevel.toFloat()
                sliderHydration.value = context.hydration
                sliderSleepHours.value = context.sleepHours

                editTextNotes.setText(context.notes)

                // Update chip selections based on existing context
                updateChipSelections(context)
            }

            // Show save success indicator
            if (state.saveSuccessful) {
                cardSaveStatus.visibility = View.VISIBLE
                textSaveStatus.text = "✓ Context saved for today"
                cardSaveStatus.setCardBackgroundColor(
                    resources.getColor(R.color.success_background, null)
                )
            } else if (state.hasUnsavedChanges) {
                cardSaveStatus.visibility = View.VISIBLE
                textSaveStatus.text = "• Unsaved changes"
                cardSaveStatus.setCardBackgroundColor(
                    resources.getColor(R.color.warning_background, null)
                )
            } else {
                cardSaveStatus.visibility = View.GONE
            }

            // Error handling
            state.errorMessage?.let { error ->
                Snackbar.make(root, error, Snackbar.LENGTH_LONG)
                    .setAction("Dismiss") {
                        viewModel.clearError()
                    }
                    .show()
            }

            // Update summary card
            updateSummaryCard(state)
        }
    }

    private fun updateChipSelections(context: EnvironmentalContextData) {
        binding.apply {
            // Exercise level
            when (context.exerciseLevel) {
                "None" -> chipExerciseNone.isChecked = true
                "Light" -> chipExerciseLight.isChecked = true
                "Moderate" -> chipExerciseModerate.isChecked = true
                "Intense" -> chipExerciseIntense.isChecked = true
            }

            // Moods
            chipMoodHappy.isChecked = context.moods.contains("Happy")
            chipMoodAnxious.isChecked = context.moods.contains("Anxious")
            chipMoodStressed.isChecked = context.moods.contains("Stressed")
            chipMoodCalm.isChecked = context.moods.contains("Calm")
            chipMoodTired.isChecked = context.moods.contains("Tired")
            chipMoodEnergetic.isChecked = context.moods.contains("Energetic")
            chipMoodIrritable.isChecked = context.moods.contains("Irritable")

            // Environments
            chipEnvHome.isChecked = context.environments.contains("Home")
            chipEnvWork.isChecked = context.environments.contains("Work")
            chipEnvTravel.isChecked = context.environments.contains("Travel")
            chipEnvSocial.isChecked = context.environments.contains("Social")
            chipEnvRestaurant.isChecked = context.environments.contains("Restaurant")
            chipEnvOutdoor.isChecked = context.environments.contains("Outdoor")

            // Weather
            when (context.weather) {
                "Sunny" -> chipWeatherSunny.isChecked = true
                "Cloudy" -> chipWeatherCloudy.isChecked = true
                "Rainy" -> chipWeatherRainy.isChecked = true
                "Hot" -> chipWeatherHot.isChecked = true
                "Cold" -> chipWeatherCold.isChecked = true
                "Humid" -> chipWeatherHumid.isChecked = true
            }
        }
    }

    private fun updateSummaryCard(state: EnvironmentalContextUiState) {
        binding.apply {
            textStressValue.text = "${binding.sliderStressLevel.value.toInt()}/10"
            textSleepValue.text = "${binding.sliderSleepQuality.value.toInt()}/10"
            textEnergyValue.text = "${binding.sliderEnergyLevel.value.toInt()}/10"
            textHydrationValue.text = "${binding.sliderHydration.value}L"
            textSleepHoursValue.text = "${binding.sliderSleepHours.value}h"

            // Show correlation insights if available
            state.correlationInsights?.let { insights ->
                cardInsights.visibility = View.VISIBLE
                textInsights.text = insights
            } ?: run {
                cardInsights.visibility = View.GONE
            }
        }
    }

    private fun handleEvent(event: EnvironmentalContextEvent) {
        when (event) {
            is EnvironmentalContextEvent.SaveSuccess -> {
                Snackbar.make(binding.root, "Environmental context saved!", Snackbar.LENGTH_SHORT).show()
            }
            is EnvironmentalContextEvent.NavigateToHistory -> {
                // Navigate to history view
            }
            is EnvironmentalContextEvent.ShowCorrelationInsight -> {
                binding.cardInsights.visibility = View.VISIBLE
                binding.textInsights.text = event.insight
            }
        }
    }

    private fun clearAllInputs() {
        binding.apply {
            sliderStressLevel.value = 5f
            sliderSleepQuality.value = 7f
            sliderEnergyLevel.value = 6f
            sliderHydration.value = 2f
            sliderSleepHours.value = 8f

            chipGroupExercise.clearCheck()
            chipGroupMood.clearCheck()
            chipGroupEnvironment.clearCheck()
            chipGroupWeather.clearCheck()

            editTextNotes.setText("")
        }
    }

    private fun getStressLevelLabel(level: Int): String {
        return when (level) {
            1, 2 -> "Very Low"
            3, 4 -> "Low"
            5, 6 -> "Moderate"
            7, 8 -> "High"
            9, 10 -> "Very High"
            else -> "Moderate"
        }
    }

    private fun getSleepQualityLabel(quality: Int): String {
        return when (quality) {
            1, 2 -> "Poor"
            3, 4 -> "Fair"
            5, 6 -> "Good"
            7, 8 -> "Very Good"
            9, 10 -> "Excellent"
            else -> "Good"
        }
    }

    private fun getEnergyLevelLabel(level: Int): String {
        return when (level) {
            1, 2 -> "Exhausted"
            3, 4 -> "Low"
            5, 6 -> "Moderate"
            7, 8 -> "High"
            9, 10 -> "Energetic"
            else -> "Moderate"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// Data classes for environmental context

data class EnvironmentalContextUiState(
    val isLoading: Boolean = false,
    val hasUnsavedChanges: Boolean = false,
    val saveSuccessful: Boolean = false,
    val existingContext: EnvironmentalContextData? = null,
    val correlationInsights: String? = null,
    val errorMessage: String? = null
)

data class EnvironmentalContextData(
    val date: String,
    val stressLevel: Int,
    val sleepQuality: Int,
    val sleepHours: Float,
    val energyLevel: Int,
    val hydration: Float,
    val exerciseLevel: String,
    val moods: List<String>,
    val environments: List<String>,
    val weather: String?,
    val notes: String
)

sealed class EnvironmentalContextEvent {
    object SaveSuccess : EnvironmentalContextEvent()
    object NavigateToHistory : EnvironmentalContextEvent()
    data class ShowCorrelationInsight(val insight: String) : EnvironmentalContextEvent()
}
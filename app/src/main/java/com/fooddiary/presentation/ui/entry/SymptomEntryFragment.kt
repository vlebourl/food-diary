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
import com.fooddiary.data.models.SymptomType
import com.fooddiary.databinding.FragmentSymptomEntryBinding
import com.fooddiary.presentation.viewmodel.SymptomEntryViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.slider.Slider
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@AndroidEntryPoint
class SymptomEntryFragment : Fragment() {

    private var _binding: FragmentSymptomEntryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SymptomEntryViewModel by viewModels()

    private lateinit var symptomTypeAdapter: ArrayAdapter<SymptomType>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSymptomEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapters()
        setupClickListeners()
        setupSliders()
        observeViewModel()
    }

    private fun setupAdapters() {
        // Symptom Type Spinner
        symptomTypeAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            SymptomType.values().map { it.displayName }
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerSymptomType.setAdapter(symptomTypeAdapter)
    }

    private fun setupClickListeners() {
        // Symptom type selection
        binding.spinnerSymptomType.setOnItemClickListener { _, _, position, _ ->
            val selectedType = SymptomType.values()[position]
            viewModel.updateSymptomType(selectedType)
        }

        // Duration inputs
        binding.etDurationHours.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                updateDuration()
            }
        }

        binding.etDurationMinutes.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                updateDuration()
            }
        }

        // Body location
        binding.etBodyLocation.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.updateLocation(binding.etBodyLocation.text.toString())
            }
        }

        // Notes
        binding.etNotes.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.updateNotes(binding.etNotes.text.toString())
            }
        }

        // Date/Time picker
        binding.btnSelectDateTime.setOnClickListener {
            showDateTimePicker()
        }

        // Add trigger button
        binding.btnAddTrigger.setOnClickListener {
            val trigger = binding.etNewTrigger.text.toString().trim()
            if (trigger.isNotEmpty()) {
                viewModel.addSuspectedTrigger(trigger)
                binding.etNewTrigger.setText("")
            }
        }

        // Bristol Scale buttons
        setupBristolScaleButtons()

        // Photo attachment (placeholder)
        binding.btnTakePhoto.setOnClickListener {
            // TODO: Implement camera functionality
            Toast.makeText(requireContext(), "Camera functionality coming soon", Toast.LENGTH_SHORT).show()
        }

        // Save and Cancel buttons
        binding.btnSave.setOnClickListener {
            viewModel.saveSymptomEvent()
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupSliders() {
        // Severity slider (1-10)
        binding.sliderSeverity.apply {
            valueFrom = 1f
            valueTo = 10f
            stepSize = 1f
            value = 5f

            addOnChangeListener { _, value, fromUser ->
                if (fromUser) {
                    viewModel.updateSeverity(value.toInt())
                }
            }
        }
    }

    private fun setupBristolScaleButtons() {
        val bristolButtons = listOf(
            binding.btnBristol1 to 1,
            binding.btnBristol2 to 2,
            binding.btnBristol3 to 3,
            binding.btnBristol4 to 4,
            binding.btnBristol5 to 5,
            binding.btnBristol6 to 6,
            binding.btnBristol7 to 7
        )

        bristolButtons.forEach { (button, scale) ->
            button.setOnClickListener {
                viewModel.updateBristolScale(scale)
                updateBristolScaleSelection(scale)
            }
        }

        // Clear bristol scale button
        binding.btnClearBristolScale.setOnClickListener {
            viewModel.updateBristolScale(null)
            updateBristolScaleSelection(null)
        }
    }

    private fun updateBristolScaleSelection(selectedScale: Int?) {
        val buttons = listOf(
            binding.btnBristol1,
            binding.btnBristol2,
            binding.btnBristol3,
            binding.btnBristol4,
            binding.btnBristol5,
            binding.btnBristol6,
            binding.btnBristol7
        )

        buttons.forEachIndexed { index, button ->
            button.isChecked = (index + 1) == selectedScale
        }

        // Update description
        if (selectedScale != null) {
            binding.tvBristolDescription.text = viewModel.getBristolScaleDescription(selectedScale)
            binding.tvBristolDescription.visibility = View.VISIBLE
        } else {
            binding.tvBristolDescription.visibility = View.GONE
        }
    }

    private fun updateDuration() {
        val hours = binding.etDurationHours.text.toString().toIntOrNull()
        val minutes = binding.etDurationMinutes.text.toString().toIntOrNull()
        viewModel.updateDuration(hours, minutes)
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

                // Observe recent food suggestions
                launch {
                    viewModel.recentFoodSuggestions.collect { suggestions ->
                        updateFoodSuggestions(suggestions)
                    }
                }
            }
        }
    }

    private fun updateUI(state: SymptomEntryViewModel.SymptomEntryUiState) {
        // Update symptom type display
        binding.spinnerSymptomType.setText(state.symptomType.displayName, false)

        // Update severity slider and description
        if (binding.sliderSeverity.value != state.severity.toFloat()) {
            binding.sliderSeverity.value = state.severity.toFloat()
        }
        binding.tvSeverityValue.text = "${state.severity}/10"
        binding.tvSeverityDescription.text = viewModel.getSymptomSeverityDescription(state.severity)

        // Show/hide Bristol Scale section
        if (state.symptomType.requiresBristolScale) {
            binding.layoutBristolScale.visibility = View.VISIBLE
            updateBristolScaleSelection(state.bristolScale)
        } else {
            binding.layoutBristolScale.visibility = View.GONE
        }

        // Update duration display
        binding.etDurationHours.setText(state.durationHours?.toString() ?: "")
        binding.etDurationMinutes.setText(state.durationMinutes?.toString() ?: "")

        if (state.durationFormatted != null) {
            binding.tvDurationSummary.text = "Duration: ${state.durationFormatted}"
            binding.tvDurationSummary.visibility = View.VISIBLE
        } else {
            binding.tvDurationSummary.visibility = View.GONE
        }

        // Update suspected triggers chips
        updateTriggersChips(state.suspectedTriggers)

        // Update date/time display
        binding.btnSelectDateTime.text = state.timestampFormatted

        // Show loading state
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.btnSave.isEnabled = !state.isLoading && state.isValid

        // Handle errors
        binding.tilBodyLocation.error = null // Reset errors
        state.severityError?.let {
            binding.tvSeverityError.text = it
            binding.tvSeverityError.visibility = View.VISIBLE
        } ?: run {
            binding.tvSeverityError.visibility = View.GONE
        }

        state.bristolScaleError?.let {
            binding.tvBristolError.text = it
            binding.tvBristolError.visibility = View.VISIBLE
        } ?: run {
            binding.tvBristolError.visibility = View.GONE
        }

        // Handle success
        if (state.isSuccess) {
            Toast.makeText(requireContext(), "Symptom logged successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        // Handle general errors
        state.errorMessage?.let { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    private fun updateTriggersChips(triggers: List<String>) {
        binding.chipGroupTriggers.removeAllViews()

        triggers.forEach { trigger ->
            val chip = Chip(requireContext()).apply {
                text = trigger
                isCloseIconVisible = true
                setOnCloseIconClickListener {
                    viewModel.removeSuspectedTrigger(trigger)
                }
            }
            binding.chipGroupTriggers.addView(chip)
        }
    }

    private fun updateFoodSuggestions(suggestions: List<String>) {
        // Create suggestion chips for recent foods
        binding.chipGroupFoodSuggestions.removeAllViews()

        suggestions.take(5).forEach { foodName ->
            val chip = Chip(requireContext()).apply {
                text = foodName
                isCheckable = false
                setOnClickListener {
                    viewModel.addSuspectedTrigger(foodName)
                }
            }
            binding.chipGroupFoodSuggestions.addView(chip)
        }

        binding.layoutFoodSuggestions.visibility = if (suggestions.isNotEmpty()) View.VISIBLE else View.GONE
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
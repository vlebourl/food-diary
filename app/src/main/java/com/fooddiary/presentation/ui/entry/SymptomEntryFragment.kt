package com.fooddiary.presentation.ui.entry

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.fooddiary.R
import com.fooddiary.data.models.SymptomType
import com.fooddiary.databinding.FragmentSymptomEntryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Symptom entry form with severity tracking, duration input, and correlation detection
 * Features: Symptom type selection, severity slider, trigger food detection, correlation analysis
 */
@AndroidEntryPoint
class SymptomEntryFragment : Fragment() {

    private var _binding: FragmentSymptomEntryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SymptomEntryViewModel by viewModels()
    private lateinit var triggerFoodAdapter: TriggerFoodAdapter

    private var isOngoing = false

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

        setupSymptomTypeChips()
        setupSeveritySlider()
        setupDurationInputs()
        setupTriggerFoodList()
        setupDateTimePicker()
        setupActionButtons()
        observeViewModel()
    }

    private fun setupSymptomTypeChips() {
        binding.chipAbdominalPain.setOnClickListener {
            viewModel.selectSymptom(SymptomType.ABDOMINAL_PAIN)
        }
        binding.chipBloating.setOnClickListener {
            viewModel.selectSymptom(SymptomType.BLOATING)
        }
        binding.chipDiarrhea.setOnClickListener {
            viewModel.selectSymptom(SymptomType.DIARRHEA)
        }
        binding.chipConstipation.setOnClickListener {
            viewModel.selectSymptom(SymptomType.CONSTIPATION)
        }
        binding.chipNausea.setOnClickListener {
            viewModel.selectSymptom(SymptomType.NAUSEA)
        }
        binding.chipGas.setOnClickListener {
            viewModel.selectSymptom(SymptomType.GAS)
        }
        binding.chipHeartburn.setOnClickListener {
            viewModel.selectSymptom(SymptomType.HEARTBURN)
        }
        binding.chipOther.setOnClickListener {
            viewModel.selectSymptom(SymptomType.OTHER)
            binding.tilCustomSymptom.isVisible = true
        }

        // Handle custom symptom input visibility
        binding.chipGroupSymptomType.setOnCheckedStateChangeListener { _, checkedIds ->
            binding.tilCustomSymptom.isVisible = binding.chipOther.isChecked
        }
    }

    private fun setupSeveritySlider() {
        binding.sliderSeverity.addOnChangeListener { _, value, _ ->
            val severity = value.toInt()
            viewModel.setSeverity(severity)
            updateSeverityDisplay(severity)
        }

        // Set initial value
        updateSeverityDisplay(binding.sliderSeverity.value.toInt())
    }

    private fun setupDurationInputs() {
        binding.etDurationHours.addTextChangedListener {
            updateDuration()
        }

        binding.etDurationMinutes.addTextChangedListener {
            updateDuration()
        }

        binding.btnDurationOngoing.setOnClickListener {
            isOngoing = !isOngoing
            updateDurationUI()
            updateDuration()
        }
    }

    private fun setupTriggerFoodList() {
        triggerFoodAdapter = TriggerFoodAdapter { suggestion ->
            viewModel.selectTriggerFood(suggestion.foodEntry.id)
        }

        binding.rvPotentialTriggers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = triggerFoodAdapter
        }
    }

    private fun setupDateTimePicker() {
        updateDateTimeDisplay(Instant.now())

        binding.btnSelectDateTime.setOnClickListener {
            showDateTimePicker()
        }
    }

    private fun setupActionButtons() {
        binding.btnSave.setOnClickListener {
            saveSymptom()
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

    private fun updateUI(state: SymptomEntryUiState) {
        // Update symptom type selection
        updateSymptomTypeSelection(state.selectedSymptomType)

        // Update severity
        binding.sliderSeverity.value = state.severity.toFloat()
        updateSeverityDisplay(state.severity)

        // Update trigger food suggestions
        triggerFoodAdapter.submitList(state.correlationSuggestions)
        binding.emptyTriggers.isVisible = state.correlationSuggestions.isEmpty() && !state.isLoadingCorrelations
        binding.rvPotentialTriggers.isVisible = state.correlationSuggestions.isNotEmpty()

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

        // Show messages
        state.message?.let { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        // Update timestamp display
        updateDateTimeDisplay(state.timestamp)
    }

    private fun updateSymptomTypeSelection(symptomType: SymptomType?) {
        binding.chipGroupSymptomType.clearCheck()
        when (symptomType) {
            SymptomType.ABDOMINAL_PAIN -> binding.chipAbdominalPain.isChecked = true
            SymptomType.BLOATING -> binding.chipBloating.isChecked = true
            SymptomType.DIARRHEA -> binding.chipDiarrhea.isChecked = true
            SymptomType.CONSTIPATION -> binding.chipConstipation.isChecked = true
            SymptomType.NAUSEA -> binding.chipNausea.isChecked = true
            SymptomType.GAS -> binding.chipGas.isChecked = true
            SymptomType.HEARTBURN -> binding.chipHeartburn.isChecked = true
            SymptomType.OTHER -> {
                binding.chipOther.isChecked = true
                binding.tilCustomSymptom.isVisible = true
            }
            null -> {
                binding.tilCustomSymptom.isVisible = false
            }
        }
    }

    private fun updateSeverityDisplay(severity: Int) {
        binding.tvSeverityValue.text = "$severity/10"

        // Update severity label and color
        val (label, color) = when {
            severity <= 3 -> "Mild" to requireContext().getColor(R.color.severity_low)
            severity <= 6 -> "Moderate" to requireContext().getColor(R.color.severity_moderate)
            severity <= 8 -> "Severe" to requireContext().getColor(R.color.severity_high)
            else -> "Very Severe" to requireContext().getColor(R.color.severity_severe)
        }

        binding.tvSeverityValue.setTextColor(color)
    }

    private fun updateDuration() {
        val duration = if (isOngoing) {
            "ongoing"
        } else {
            val hours = binding.etDurationHours.text?.toString()?.toIntOrNull() ?: 0
            val minutes = binding.etDurationMinutes.text?.toString()?.toIntOrNull() ?: 0

            when {
                hours > 0 && minutes > 0 -> "${hours}h ${minutes}m"
                hours > 0 -> "${hours}h"
                minutes > 0 -> "${minutes}m"
                else -> "0m"
            }
        }

        viewModel.setDuration(duration)
    }

    private fun updateDurationUI() {
        binding.btnDurationOngoing.text = if (isOngoing) "Stop Ongoing" else "Ongoing"
        binding.etDurationHours.isEnabled = !isOngoing
        binding.etDurationMinutes.isEnabled = !isOngoing

        if (isOngoing) {
            binding.etDurationHours.setText("0")
            binding.etDurationMinutes.setText("0")
        }
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

    private fun saveSymptom() {
        viewLifecycleOwner.lifecycleScope.launch {
            val success = viewModel.saveSymptom()
            if (success) {
                Toast.makeText(requireContext(), "Symptom logged successfully", Toast.LENGTH_SHORT).show()
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

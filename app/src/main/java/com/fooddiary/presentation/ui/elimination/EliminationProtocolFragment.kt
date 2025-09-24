package com.fooddiary.presentation.ui.elimination

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.fooddiary.databinding.FragmentEliminationProtocolBinding
import com.fooddiary.presentation.ui.elimination.adapter.EliminationPhaseAdapter
import com.fooddiary.presentation.ui.elimination.adapter.FoodSelectionAdapter
import com.fooddiary.presentation.viewmodel.EliminationProtocolViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EliminationProtocolFragment : Fragment() {

    private var _binding: FragmentEliminationProtocolBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EliminationProtocolViewModel by viewModels()

    private lateinit var phaseAdapter: EliminationPhaseAdapter
    private lateinit var foodSelectionAdapter: FoodSelectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEliminationProtocolBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupRecyclerViews() {
        // Phase progress adapter
        phaseAdapter = EliminationPhaseAdapter { phase ->
            viewModel.selectPhase(phase)
        }

        binding.recyclerViewPhases.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = phaseAdapter
        }

        // Food selection adapter
        foodSelectionAdapter = FoodSelectionAdapter(
            onFoodToggle = { foodName, isEliminated ->
                viewModel.toggleFoodElimination(foodName, isEliminated)
            },
            onReintroduceFood = { foodName ->
                viewModel.reintroduceFood(foodName)
            }
        )

        binding.recyclerViewFoods.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = foodSelectionAdapter
        }
    }

    private fun setupClickListeners() {
        binding.fabStartProtocol.setOnClickListener {
            if (viewModel.uiState.value.isProtocolActive) {
                showStopProtocolDialog()
            } else {
                showStartProtocolDialog()
            }
        }

        binding.buttonAddCustomFood.setOnClickListener {
            showAddCustomFoodDialog()
        }

        binding.buttonPhaseComplete.setOnClickListener {
            viewModel.completeCurrentPhase()
        }

        binding.cardProtocolSummary.setOnClickListener {
            // Navigate to detailed protocol summary
        }

        binding.buttonSymptomLog.setOnClickListener {
            // Quick navigate to symptom logging
            // findNavController().navigate(R.id.action_elimination_to_symptom_entry)
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
                viewModel.phases.collect { phases ->
                    phaseAdapter.submitList(phases)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eliminatedFoods.collect { foods ->
                    foodSelectionAdapter.submitList(foods)
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

    private fun updateUI(state: EliminationProtocolUiState) {
        binding.apply {
            // Loading state
            progressIndicator.visibility = if (state.isLoading) View.VISIBLE else View.GONE

            // Protocol status
            if (state.isProtocolActive) {
                fabStartProtocol.text = "Stop Protocol"
                fabStartProtocol.setIconResource(R.drawable.ic_stop)

                layoutProtocolActive.visibility = View.VISIBLE
                layoutProtocolInactive.visibility = View.GONE

                // Current phase info
                state.currentPhase?.let { phase ->
                    textCurrentPhase.text = phase.name
                    textPhaseDescription.text = phase.description
                    textPhaseDuration.text = "Day ${phase.currentDay} of ${phase.durationDays}"

                    progressPhase.progress = ((phase.currentDay.toFloat() / phase.durationDays) * 100).toInt()

                    buttonPhaseComplete.isEnabled = phase.canComplete
                    buttonPhaseComplete.text = if (phase.isLastPhase) "Complete Protocol" else "Next Phase"
                }

                // Summary stats
                textEliminatedCount.text = state.eliminatedFoodsCount.toString()
                textReintroducedCount.text = state.reintroducedFoodsCount.toString()
                textSymptomsTracked.text = state.symptomEventsCount.toString()

            } else {
                fabStartProtocol.text = "Start Protocol"
                fabStartProtocol.setIconResource(R.drawable.ic_play_arrow)

                layoutProtocolActive.visibility = View.GONE
                layoutProtocolInactive.visibility = View.VISIBLE

                // Setup information
                textProtocolDescription.text = getString(R.string.elimination_protocol_description)
            }

            // Error handling
            state.errorMessage?.let { error ->
                Snackbar.make(root, error, Snackbar.LENGTH_LONG)
                    .setAction("Dismiss") {
                        viewModel.clearError()
                    }
                    .show()
            }

            // Success messages
            state.successMessage?.let { message ->
                Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()
                viewModel.clearSuccessMessage()
            }
        }
    }

    private fun handleEvent(event: EliminationProtocolEvent) {
        when (event) {
            is EliminationProtocolEvent.NavigateToSymptomEntry -> {
                // Navigate to symptom entry with pre-filled elimination context
            }
            is EliminationProtocolEvent.ShowPhaseCompletionDialog -> {
                showPhaseCompletionDialog(event.phase)
            }
            is EliminationProtocolEvent.ShowReintroductionGuidance -> {
                showReintroductionGuidanceDialog(event.foodName, event.guidelines)
            }
            is EliminationProtocolEvent.ProtocolCompleted -> {
                showProtocolCompletionDialog(event.summary)
            }
        }
    }

    private fun showStartProtocolDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Start Elimination Protocol")
            .setMessage("This will begin a structured elimination diet protocol. Make sure you've consulted with a healthcare provider before starting.")
            .setPositiveButton("Start") { _, _ ->
                viewModel.startProtocol()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showStopProtocolDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Stop Elimination Protocol")
            .setMessage("Are you sure you want to stop the current protocol? Your progress will be saved.")
            .setPositiveButton("Stop") { _, _ ->
                viewModel.stopProtocol()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showAddCustomFoodDialog() {
        // Create dialog for adding custom foods to elimination list
        val dialogBinding = DialogAddCustomFoodBinding.inflate(layoutInflater)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add Custom Food")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { _, _ ->
                val foodName = dialogBinding.editTextFoodName.text.toString().trim()
                val category = dialogBinding.spinnerCategory.selectedItem.toString()

                if (foodName.isNotBlank()) {
                    viewModel.addCustomEliminationFood(foodName, category)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showPhaseCompletionDialog(phase: EliminationPhase) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Complete ${phase.name}")
            .setMessage("Have you successfully completed the ${phase.name.lowercase()} phase? Any symptoms during this phase?")
            .setPositiveButton("Complete") { _, _ ->
                viewModel.confirmPhaseCompletion()
            }
            .setNeutralButton("Log Symptoms First") { _, _ ->
                // Navigate to symptom entry
            }
            .setNegativeButton("Not Yet", null)
            .show()
    }

    private fun showReintroductionGuidanceDialog(foodName: String, guidelines: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Reintroducing $foodName")
            .setMessage(guidelines)
            .setPositiveButton("Got it") { _, _ ->
                viewModel.acknowledgeReintroductionGuidance(foodName)
            }
            .show()
    }

    private fun showProtocolCompletionDialog(summary: ProtocolSummary) {
        val message = buildString {
            append("Protocol completed successfully!\n\n")
            append("Duration: ${summary.totalDays} days\n")
            append("Foods eliminated: ${summary.eliminatedFoods.size}\n")
            append("Foods successfully reintroduced: ${summary.successfulReintroductions}\n")
            append("Potential triggers identified: ${summary.identifiedTriggers.size}\n\n")

            if (summary.identifiedTriggers.isNotEmpty()) {
                append("Potential triggers:\n")
                summary.identifiedTriggers.forEach { trigger ->
                    append("• $trigger\n")
                }
            }
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Protocol Complete!")
            .setMessage(message)
            .setPositiveButton("View Full Report") { _, _ ->
                viewModel.generateProtocolReport()
            }
            .setNeutralButton("Continue Tracking", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// Data classes for the elimination protocol

data class EliminationProtocolUiState(
    val isLoading: Boolean = false,
    val isProtocolActive: Boolean = false,
    val currentPhase: EliminationPhase? = null,
    val eliminatedFoodsCount: Int = 0,
    val reintroducedFoodsCount: Int = 0,
    val symptomEventsCount: Int = 0,
    val protocolStartDate: String? = null,
    val protocolDayNumber: Int = 0,
    val totalProtocolDays: Int = 0,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

data class EliminationPhase(
    val id: String,
    val name: String,
    val description: String,
    val durationDays: Int,
    val currentDay: Int = 0,
    val isActive: Boolean = false,
    val isCompleted: Boolean = false,
    val isLastPhase: Boolean = false,
    val canComplete: Boolean = false,
    val foods: List<String> = emptyList()
)

sealed class EliminationProtocolEvent {
    object NavigateToSymptomEntry : EliminationProtocolEvent()
    data class ShowPhaseCompletionDialog(val phase: EliminationPhase) : EliminationProtocolEvent()
    data class ShowReintroductionGuidance(val foodName: String, val guidelines: String) : EliminationProtocolEvent()
    data class ProtocolCompleted(val summary: ProtocolSummary) : EliminationProtocolEvent()
}

data class EliminatedFood(
    val name: String,
    val category: String,
    val eliminationDate: String,
    val reintroductionDate: String? = null,
    val status: EliminationStatus,
    val reactionNotes: String? = null,
    val isCustom: Boolean = false
)

enum class EliminationStatus {
    ELIMINATED,
    REINTRODUCING,
    SAFE,
    TRIGGER_IDENTIFIED,
    NEEDS_FURTHER_TESTING
}

data class ProtocolSummary(
    val totalDays: Int,
    val eliminatedFoods: List<String>,
    val successfulReintroductions: Int,
    val identifiedTriggers: List<String>,
    val protocolType: String,
    val completionDate: String,
    val recommendedNextSteps: List<String>
)
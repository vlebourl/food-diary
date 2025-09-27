package com.fooddiary.presentation.ui.timeline

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.fooddiary.databinding.FragmentTimelineBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

/**
 * Timeline screen showing chronological food and symptom entries with correlation indicators
 * Features: RecyclerView with pagination, FAB speed dial, date filtering, pull-to-refresh
 */
@AndroidEntryPoint
class TimelineFragment : Fragment() {

    private var _binding: FragmentTimelineBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TimelineViewModel by viewModels()
    private lateinit var timelineAdapter: TimelineAdapter

    private var isSpeedDialOpen = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimelineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupFABSpeedDial()
        setupDateFilters()
        setupSwipeRefresh()
        observeViewModel()
        observeNavigationEvents()
    }

    private fun setupRecyclerView() {
        timelineAdapter = TimelineAdapter(
            onEntryClick = { entry ->
                viewModel.navigateToEntryDetail(entry.id, entry.type)
            },
            onLoadMore = {
                viewModel.loadMoreEntries()
            }
        )

        binding.recyclerTimeline.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = timelineAdapter

            // Add scroll listener for pagination
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItems = layoutManager.itemCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                    // Load more when reaching the last 3 items
                    if (lastVisibleItem >= totalItems - 3) {
                        viewModel.loadMoreEntries()
                    }

                    // Close speed dial when scrolling
                    if (dy != 0 && isSpeedDialOpen) {
                        closeSpeedDial()
                    }
                }
            })
        }
    }

    private fun setupFABSpeedDial() {
        binding.fabAddEntry.setOnClickListener {
            if (isSpeedDialOpen) {
                closeSpeedDial()
            } else {
                openSpeedDial()
            }
        }

        binding.overlaySpeedDial.setOnClickListener {
            closeSpeedDial()
        }

        binding.fabAddFood.setOnClickListener {
            closeSpeedDial()
            viewModel.navigateToFoodEntry()
        }

        binding.fabAddSymptom.setOnClickListener {
            closeSpeedDial()
            viewModel.navigateToSymptomEntry()
        }
    }

    private fun setupDateFilters() {
        binding.chipGroupDateFilter.setOnCheckedStateChangeListener { group, checkedIds ->
            when {
                binding.chipAllDates.isChecked -> {
                    viewModel.clearFilters()
                }
                binding.chipToday.isChecked -> {
                    val today = LocalDate.now()
                    viewModel.filterByDateRange(today, today)
                }
                binding.chipWeek.isChecked -> {
                    val today = LocalDate.now()
                    val weekStart = today.minusDays(6)
                    viewModel.filterByDateRange(weekStart, today)
                }
                binding.chipCustomRange.isChecked -> {
                    showDateRangePicker()
                }
            }
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshTimeline()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.timelineState.collect { state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun observeNavigationEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigationEvents.collect { event ->
                    handleNavigationEvent(event)
                }
            }
        }
    }

    private fun updateUI(state: TimelineState) {
        // Update loading states
        binding.swipeRefresh.isRefreshing = state.isLoading && state.entries.isEmpty()
        binding.progressLoading.isVisible = state.isLoading && state.entries.isEmpty()

        // Update entries
        timelineAdapter.submitList(state.entries) {
            // Show empty state if no entries and not loading
            binding.layoutEmptyState.isVisible =
                state.entries.isEmpty() && !state.isLoading && state.error == null
        }

        // Update error handling
        state.error?.let { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
        }

        // Update load more state
        timelineAdapter.setHasMoreData(state.hasMoreData)
    }

    private fun handleNavigationEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.ToFoodEntry -> {
                findNavController().navigate(
                    TimelineFragmentDirections.actionTimelineToFoodEntry()
                )
            }
            is NavigationEvent.ToSymptomEntry -> {
                findNavController().navigate(
                    TimelineFragmentDirections.actionTimelineToSymptomEntry()
                )
            }
            is NavigationEvent.ToEntryDetail -> {
                when (event.entryType) {
                    EntryType.FOOD -> {
                        findNavController().navigate(
                            TimelineFragmentDirections.actionTimelineToFoodEntryDetail(event.entryId)
                        )
                    }
                    EntryType.SYMPTOM -> {
                        findNavController().navigate(
                            TimelineFragmentDirections.actionTimelineToSymptomEntryDetail(event.entryId)
                        )
                    }
                }
            }
        }
    }

    private fun showDateRangePicker() {
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select date range")
            .setSelection(
                androidx.core.util.Pair(
                    MaterialDatePicker.thisMonthInUtcMilliseconds(),
                    MaterialDatePicker.todayInUtcMilliseconds()
                )
            )
            .build()

        dateRangePicker.addOnPositiveButtonClickListener { selection ->
            val startDate = Date(selection.first).toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate()
            val endDate = Date(selection.second).toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate()

            viewModel.filterByDateRange(startDate, endDate)
        }

        dateRangePicker.addOnNegativeButtonClickListener {
            // Reset to "All" filter if cancelled
            binding.chipAllDates.isChecked = true
        }

        dateRangePicker.show(parentFragmentManager, "date_range_picker")
    }

    private fun openSpeedDial() {
        if (isSpeedDialOpen) return

        isSpeedDialOpen = true

        // Show overlay
        binding.overlaySpeedDial.isVisible = true
        binding.overlaySpeedDial.alpha = 0f
        binding.overlaySpeedDial.animate()
            .alpha(1f)
            .setDuration(200)
            .start()

        // Rotate main FAB
        binding.fabAddEntry.animate()
            .rotation(45f)
            .setDuration(200)
            .start()

        // Show and animate mini FABs
        animateSpeedDialFAB(binding.fabAddFood, binding.labelAddFood, 0)
        animateSpeedDialFAB(binding.fabAddSymptom, binding.labelAddSymptom, 50)
    }

    private fun closeSpeedDial() {
        if (!isSpeedDialOpen) return

        isSpeedDialOpen = false

        // Hide overlay
        binding.overlaySpeedDial.animate()
            .alpha(0f)
            .setDuration(200)
            .withEndAction {
                binding.overlaySpeedDial.isVisible = false
            }
            .start()

        // Rotate main FAB back
        binding.fabAddEntry.animate()
            .rotation(0f)
            .setDuration(200)
            .start()

        // Hide mini FABs
        hideSpeedDialFAB(binding.fabAddFood, binding.labelAddFood, 0)
        hideSpeedDialFAB(binding.fabAddSymptom, binding.labelAddSymptom, 50)
    }

    private fun animateSpeedDialFAB(fab: View, label: View, delay: Long) {
        fab.isVisible = true
        label.isVisible = true

        fab.alpha = 0f
        label.alpha = 0f
        fab.scaleX = 0f
        fab.scaleY = 0f
        label.scaleX = 0f
        label.scaleY = 0f

        fab.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setStartDelay(delay)
            .setDuration(150)
            .start()

        label.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setStartDelay(delay)
            .setDuration(150)
            .start()
    }

    private fun hideSpeedDialFAB(fab: View, label: View, delay: Long) {
        fab.animate()
            .alpha(0f)
            .scaleX(0f)
            .scaleY(0f)
            .setStartDelay(delay)
            .setDuration(150)
            .withEndAction {
                fab.isVisible = false
            }
            .start()

        label.animate()
            .alpha(0f)
            .scaleX(0f)
            .scaleY(0f)
            .setStartDelay(delay)
            .setDuration(150)
            .withEndAction {
                label.isVisible = false
            }
            .start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

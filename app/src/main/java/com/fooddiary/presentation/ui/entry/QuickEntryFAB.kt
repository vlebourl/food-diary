package com.fooddiary.presentation.ui.entry

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.fooddiary.R
import com.fooddiary.databinding.ViewQuickEntryFabBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class QuickEntryFAB @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewQuickEntryFabBinding
    private var isExpanded = false
    private var animationInProgress = false

    private val speedDialFabs = mutableListOf<FloatingActionButton>()
    private val speedDialLabels = mutableListOf<View>()

    init {
        binding = ViewQuickEntryFabBinding.inflate(LayoutInflater.from(context), this, true)
        setupSpeedDial()
    }

    private fun setupSpeedDial() {
        // Main FAB click listener
        binding.mainFab.setOnClickListener {
            if (!animationInProgress) {
                toggleSpeedDial()
            }
        }

        // Speed dial options
        setupSpeedDialOption(
            fab = binding.fabFoodEntry,
            label = binding.labelFoodEntry,
            icon = R.drawable.ic_restaurant,
            action = { navigateToFoodEntry() }
        )

        setupSpeedDialOption(
            fab = binding.fabSymptomEntry,
            label = binding.labelSymptomEntry,
            icon = R.drawable.ic_health,
            action = { navigateToSymptomEntry() }
        )

        setupSpeedDialOption(
            fab = binding.fabQuickLog,
            label = binding.labelQuickLog,
            icon = R.drawable.ic_speed,
            action = { showQuickLogDialog() }
        )

        setupSpeedDialOption(
            fab = binding.fabVoiceEntry,
            label = binding.labelVoiceEntry,
            icon = R.drawable.ic_mic,
            action = { startVoiceEntry() }
        )

        // Add FABs and labels to lists for animation
        speedDialFabs.addAll(listOf(
            binding.fabFoodEntry,
            binding.fabSymptomEntry,
            binding.fabQuickLog,
            binding.fabVoiceEntry
        ))

        speedDialLabels.addAll(listOf(
            binding.labelFoodEntry,
            binding.labelSymptomEntry,
            binding.labelQuickLog,
            binding.labelVoiceEntry
        ))

        // Initially hide all speed dial options
        hideSpeedDialOptions(animate = false)
    }

    private fun setupSpeedDialOption(
        fab: FloatingActionButton,
        label: View,
        icon: Int,
        action: () -> Unit
    ) {
        fab.setImageResource(icon)
        fab.setOnClickListener {
            collapseSpeedDial()
            action()
        }

        // Hide label when FAB is clicked
        fab.setOnClickListener {
            if (isExpanded) {
                collapseSpeedDial()
                action()
            }
        }
    }

    private fun toggleSpeedDial() {
        if (isExpanded) {
            collapseSpeedDial()
        } else {
            expandSpeedDial()
        }
    }

    private fun expandSpeedDial() {
        if (animationInProgress || isExpanded) return

        isExpanded = true
        animationInProgress = true

        // Rotate main FAB
        val mainFabRotation = ObjectAnimator.ofFloat(binding.mainFab, "rotation", 0f, 135f)
        mainFabRotation.duration = 300

        // Show and animate speed dial options
        val animators = mutableListOf<Animator>()
        animators.add(mainFabRotation)

        speedDialFabs.forEachIndexed { index, fab ->
            fab.visibility = VISIBLE
            speedDialLabels[index].visibility = VISIBLE

            // Scale animation for FABs
            val scaleX = ObjectAnimator.ofFloat(fab, "scaleX", 0f, 1f)
            val scaleY = ObjectAnimator.ofFloat(fab, "scaleY", 0f, 1f)
            val alpha = ObjectAnimator.ofFloat(fab, "alpha", 0f, 1f)

            scaleX.startDelay = (index * 50).toLong()
            scaleY.startDelay = (index * 50).toLong()
            alpha.startDelay = (index * 50).toLong()

            animators.addAll(listOf(scaleX, scaleY, alpha))

            // Label animation
            val labelAlpha = ObjectAnimator.ofFloat(speedDialLabels[index], "alpha", 0f, 1f)
            labelAlpha.startDelay = (index * 50 + 100).toLong()
            animators.add(labelAlpha)
        }

        // Background dim animation
        binding.backgroundDim.visibility = VISIBLE
        val dimAlpha = ObjectAnimator.ofFloat(binding.backgroundDim, "alpha", 0f, 0.5f)
        animators.add(dimAlpha)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animators)
        animatorSet.interpolator = OvershootInterpolator()
        animatorSet.duration = 300

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                animationInProgress = false
            }
        })

        animatorSet.start()

        // Setup background dim click listener
        binding.backgroundDim.setOnClickListener {
            collapseSpeedDial()
        }
    }

    private fun collapseSpeedDial() {
        if (animationInProgress || !isExpanded) return

        isExpanded = false
        animationInProgress = true

        // Rotate main FAB back
        val mainFabRotation = ObjectAnimator.ofFloat(binding.mainFab, "rotation", 135f, 0f)
        mainFabRotation.duration = 200

        // Hide and animate speed dial options
        val animators = mutableListOf<Animator>()
        animators.add(mainFabRotation)

        speedDialFabs.forEachIndexed { index, fab ->
            // Scale animation for FABs
            val scaleX = ObjectAnimator.ofFloat(fab, "scaleX", 1f, 0f)
            val scaleY = ObjectAnimator.ofFloat(fab, "scaleY", 1f, 0f)
            val alpha = ObjectAnimator.ofFloat(fab, "alpha", 1f, 0f)

            scaleX.startDelay = ((speedDialFabs.size - index - 1) * 30).toLong()
            scaleY.startDelay = ((speedDialFabs.size - index - 1) * 30).toLong()
            alpha.startDelay = ((speedDialFabs.size - index - 1) * 30).toLong()

            animators.addAll(listOf(scaleX, scaleY, alpha))

            // Label animation
            val labelAlpha = ObjectAnimator.ofFloat(speedDialLabels[index], "alpha", 1f, 0f)
            labelAlpha.startDelay = ((speedDialFabs.size - index - 1) * 30).toLong()
            animators.add(labelAlpha)
        }

        // Background dim animation
        val dimAlpha = ObjectAnimator.ofFloat(binding.backgroundDim, "alpha", 0.5f, 0f)
        animators.add(dimAlpha)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animators)
        animatorSet.duration = 200

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                animationInProgress = false
                hideSpeedDialOptions(animate = false)
                binding.backgroundDim.visibility = GONE
            }
        })

        animatorSet.start()
    }

    private fun hideSpeedDialOptions(animate: Boolean) {
        speedDialFabs.forEach { fab ->
            fab.visibility = if (animate) VISIBLE else GONE
            if (!animate) {
                fab.scaleX = 0f
                fab.scaleY = 0f
                fab.alpha = 0f
            }
        }

        speedDialLabels.forEach { label ->
            label.visibility = if (animate) VISIBLE else GONE
            if (!animate) {
                label.alpha = 0f
            }
        }
    }

    private fun navigateToFoodEntry() {
        findNavController().navigate(R.id.action_global_food_entry)
    }

    private fun navigateToSymptomEntry() {
        findNavController().navigate(R.id.action_global_symptom_entry)
    }

    private fun showQuickLogDialog() {
        // Show quick log dialog with predefined templates
        val quickLogDialog = QuickLogDialog()
        quickLogDialog.show((context as androidx.fragment.app.FragmentActivity).supportFragmentManager, "quick_log")
    }

    private fun startVoiceEntry() {
        // Start voice input for food or symptom logging
        val voiceDialog = VoiceEntryDialog()
        voiceDialog.show((context as androidx.fragment.app.FragmentActivity).supportFragmentManager, "voice_entry")
    }

    fun setMainFabIcon(iconRes: Int) {
        binding.mainFab.setImageResource(iconRes)
    }

    fun setMainFabBackgroundColor(colorRes: Int) {
        binding.mainFab.backgroundTintList = ContextCompat.getColorStateList(context, colorRes)
    }

    fun isSpeedDialExpanded(): Boolean = isExpanded

    fun collapseIfExpanded() {
        if (isExpanded) {
            collapseSpeedDial()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // Clean up any ongoing animations
        if (animationInProgress) {
            clearAnimation()
        }
    }
}

// Placeholder dialog classes - these would be implemented separately
class QuickLogDialog : androidx.fragment.app.DialogFragment() {
    // Quick log implementation with predefined templates
}

class VoiceEntryDialog : androidx.fragment.app.DialogFragment() {
    // Voice input implementation
}
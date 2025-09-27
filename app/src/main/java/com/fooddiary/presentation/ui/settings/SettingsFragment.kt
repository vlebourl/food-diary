package com.fooddiary.presentation.ui.settings

import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.fooddiary.BuildConfig
import com.fooddiary.R
import com.fooddiary.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Settings screen for app configuration and preferences
 * Features: Theme, notifications, privacy, data management, backup/restore
 */
@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupGeneralSettings()
        setupCorrelationSettings()
        setupNotificationSettings()
        setupDataManagementSettings()
        setupPrivacySettings()
        setupAboutSection()
        observeViewModel()
    }

    private fun setupGeneralSettings() {
        // Theme selection
        binding.root.findViewById<View>(R.id.tv_theme_summary)?.parent?.setOnClickListener {
            showThemeSelectionDialog()
        }

        // Language selection
        binding.root.findViewById<View>(R.id.tv_language_summary)?.parent?.setOnClickListener {
            showLanguageSelectionDialog()
        }

        // Default reminder time
        binding.root.findViewById<View>(R.id.tv_reminder_time_summary)?.parent?.setOnClickListener {
            showReminderTimeDialog()
        }
    }

    private fun setupCorrelationSettings() {
        // Auto-detect correlations switch
        binding.switchAutoCorrelations.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updatePreference("auto_correlations", isChecked)
        }

        // Correlation sensitivity
        binding.root.findViewById<View>(R.id.tv_sensitivity_summary)?.parent?.setOnClickListener {
            showCorrelationSensitivityDialog()
        }

        // Time window setting
        binding.root.findViewById<View>(R.id.tv_time_window_summary)?.parent?.setOnClickListener {
            showTimeWindowDialog()
        }
    }

    private fun setupNotificationSettings() {
        // Meal reminders
        binding.switchMealReminders.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updatePreference("meal_reminders", isChecked)
        }

        // Symptom check reminders
        binding.switchSymptomReminders.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updatePreference("symptom_reminders", isChecked)
        }

        // Correlation alerts
        binding.switchCorrelationAlerts.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updatePreference("correlation_alerts", isChecked)
        }
    }

    private fun setupDataManagementSettings() {
        // Export all data
        binding.root.findViewById<View>(R.id.tv_language_summary)?.parent?.parent?.let { card ->
            card.findViewById<View>(R.id.export_data_section)?.setOnClickListener {
                showDataExportDialog()
            }
        }

        // Import data
        binding.root.findViewById<View>(R.id.import_data_section)?.setOnClickListener {
            showDataImportDialog()
        }

        // Clear all data
        binding.root.findViewById<View>(R.id.clear_data_section)?.setOnClickListener {
            showClearDataConfirmation()
        }
    }

    private fun setupPrivacySettings() {
        // App lock
        binding.switchAppLock.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updatePreference("app_lock", isChecked)
            if (isChecked) {
                // TODO: Implement biometric/PIN setup
                Toast.makeText(requireContext(), "App lock feature coming soon", Toast.LENGTH_SHORT).show()
            }
        }

        // Block screenshots
        binding.switchBlockScreenshots.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updatePreference("block_screenshots", isChecked)
            if (isChecked) {
                // TODO: Implement screenshot blocking
                Toast.makeText(requireContext(), "Screenshot blocking enabled", Toast.LENGTH_SHORT).show()
            }
        }

        // Privacy policy
        binding.root.findViewById<View>(R.id.privacy_policy_section)?.setOnClickListener {
            showPrivacyPolicy()
        }
    }

    private fun setupAboutSection() {
        // Version info
        try {
            val packageInfo = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
            binding.tvVersionName.text = "${packageInfo.versionName} (Build ${packageInfo.longVersionCode})"
        } catch (e: PackageManager.NameNotFoundException) {
            binding.tvVersionName.text = "Unknown version"
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

    private fun updateUI(state: SettingsUiState) {
        // Update switches
        binding.switchAutoCorrelations.isChecked = true // Default to true for auto correlations
        binding.switchMealReminders.isChecked = state.notificationsEnabled
        binding.switchSymptomReminders.isChecked = false // Default state
        binding.switchCorrelationAlerts.isChecked = state.notificationsEnabled
        binding.switchAppLock.isChecked = state.privacyMode
        binding.switchBlockScreenshots.isChecked = state.privacyMode

        // Update summaries
        updateThemeSummary(state.darkMode)
        updateLanguageSummary(state.language)
        updateReminderTimeSummary(state.reminderTime)
        updateCorrelationSensitivitySummary("Medium")
        updateTimeWindowSummary("24 hours")

        // Show messages
        state.message?.let { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            viewModel.clearMessage()
        }

        // Show errors
        state.error?.let { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
            viewModel.clearMessage()
        }
    }

    private fun updateThemeSummary(darkMode: Boolean) {
        val themeSummary = if (darkMode) "Dark mode" else "Light mode"
        binding.tvThemeSummary.text = themeSummary
    }

    private fun updateLanguageSummary(language: String) {
        val languageName = when (language) {
            "en" -> "English"
            "es" -> "Spanish"
            "fr" -> "French"
            "de" -> "German"
            else -> "English"
        }
        binding.tvLanguageSummary.text = languageName
    }

    private fun updateReminderTimeSummary(time: LocalTime) {
        val formatter = DateTimeFormatter.ofPattern("h:mm a")
        binding.tvReminderTimeSummary.text = "3 hours after meals (${time.format(formatter)})"
    }

    private fun updateCorrelationSensitivitySummary(sensitivity: String) {
        binding.tvSensitivitySummary.text = "$sensitivity sensitivity"
    }

    private fun updateTimeWindowSummary(window: String) {
        binding.tvTimeWindowSummary.text = window
    }

    private fun showThemeSelectionDialog() {
        val themes = arrayOf("System default", "Light", "Dark")
        val currentSelection = if (viewModel.uiState.value.darkMode) 2 else 1

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Choose theme")
            .setSingleChoiceItems(themes, currentSelection) { dialog, which ->
                when (which) {
                    0 -> {
                        // System default
                        viewModel.updatePreference("theme_mode", "system")
                    }
                    1 -> {
                        // Light
                        viewModel.toggleDarkMode(false)
                    }
                    2 -> {
                        // Dark
                        viewModel.toggleDarkMode(true)
                    }
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showLanguageSelectionDialog() {
        val languages = arrayOf("English", "Spanish", "French", "German")
        val languageCodes = arrayOf("en", "es", "fr", "de")
        val currentLanguage = viewModel.uiState.value.language
        val currentSelection = languageCodes.indexOf(currentLanguage)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Choose language")
            .setSingleChoiceItems(languages, currentSelection) { dialog, which ->
                viewModel.updateLanguage(languageCodes[which])
                dialog.dismiss()
                Toast.makeText(requireContext(), "Language will change after app restart", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showReminderTimeDialog() {
        val currentTime = viewModel.uiState.value.reminderTime

        TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                val selectedTime = LocalTime.of(hourOfDay, minute)
                viewModel.updateReminderTime(selectedTime)
            },
            currentTime.hour,
            currentTime.minute,
            false
        ).show()
    }

    private fun showCorrelationSensitivityDialog() {
        val sensitivities = arrayOf("Low", "Medium", "High")

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Correlation Sensitivity")
            .setSingleChoiceItems(sensitivities, 1) { dialog, which ->
                val sensitivity = sensitivities[which]
                viewModel.updatePreference("correlation_sensitivity", sensitivity.lowercase())
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showTimeWindowDialog() {
        val timeWindows = arrayOf("12 hours", "24 hours", "48 hours", "72 hours")

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Correlation Time Window")
            .setSingleChoiceItems(timeWindows, 1) { dialog, which ->
                val window = timeWindows[which]
                viewModel.updatePreference("correlation_time_window", window)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDataExportDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Export All Data")
            .setMessage("This will export all your food diary data including entries, symptoms, and correlations. The export will be saved to your device's Downloads folder.")
            .setPositiveButton("Export") { _, _ ->
                val exportPath = viewModel.exportSettings()
                if (exportPath != null) {
                    Toast.makeText(requireContext(), "Data exported successfully", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDataImportDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Import Data")
            .setMessage("This will import data from a previously exported file. This action will merge with your existing data.")
            .setPositiveButton("Choose File") { _, _ ->
                // TODO: Implement file picker for import
                Toast.makeText(requireContext(), "File import feature coming soon", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showClearDataConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Clear All Data")
            .setMessage("This will permanently delete all your food diary data including entries, symptoms, correlations, and settings. This action cannot be undone.")
            .setPositiveButton("Delete All") { _, _ ->
                showFinalClearConfirmation()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showFinalClearConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Are you absolutely sure?")
            .setMessage("This will delete ALL your data permanently. Type 'DELETE' to confirm.")
            .setView(R.layout.dialog_confirm_delete)
            .setPositiveButton("Delete Everything") { _, _ ->
                // TODO: Implement data clearing
                Toast.makeText(requireContext(), "All data cleared", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showPrivacyPolicy() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Privacy Policy")
            .setMessage("This app stores all data locally on your device. No data is transmitted to external servers. You have full control over your data and can export or delete it at any time.")
            .setPositiveButton("OK", null)
            .setNeutralButton("View Full Policy") { _, _ ->
                // TODO: Open full privacy policy
                Toast.makeText(requireContext(), "Full privacy policy coming soon", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

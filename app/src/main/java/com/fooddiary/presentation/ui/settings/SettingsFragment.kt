package com.fooddiary.presentation.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fooddiary.R
import com.fooddiary.databinding.FragmentSettingsBinding
import com.fooddiary.presentation.viewmodel.SettingsViewModel
import com.fooddiary.presentation.viewmodel.DataRetentionOption
import com.fooddiary.presentation.viewmodel.ExportFormatOption
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalTime

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels()

    private lateinit var dataRetentionAdapter: ArrayAdapter<String>
    private lateinit var exportFormatAdapter: ArrayAdapter<String>

    // File picker for import
    private val importLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.importData(it.toString())
        }
    }

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

        setupAdapters()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupAdapters() {
        // Data Retention Adapter
        dataRetentionAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            DataRetentionOption.values().map { it.displayName }
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerDataRetention.setAdapter(dataRetentionAdapter)

        // Export Format Adapter
        exportFormatAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            ExportFormatOption.values().map { it.displayName }
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerExportFormat.setAdapter(exportFormatAdapter)
    }

    private fun setupClickListeners() {
        // Notification settings
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateNotificationsEnabled(isChecked)
        }

        // Meal reminder time
        binding.btnMealReminderTime.setOnClickListener {
            showTimePicker("meal_reminder") { time ->
                viewModel.updateMealReminderTime(time)
            }
        }

        // Symptom reminder time
        binding.btnSymptomReminderTime.setOnClickListener {
            showTimePicker("symptom_reminder") { time ->
                viewModel.updateSymptomReminderTime(time)
            }
        }

        // Dark mode toggle
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateDarkModeEnabled(isChecked)
            updateAppTheme(isChecked)
        }

        // Medical mode toggle
        binding.switchMedicalMode.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateMedicalMode(isChecked)
        }

        // FODMAP info toggle
        binding.switchShowFodmap.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateShowFodmapInfo(isChecked)
        }

        // Biometric authentication toggle
        binding.switchBiometricAuth.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateBiometricAuthEnabled(isChecked)
        }

        // Data retention selection
        binding.spinnerDataRetention.setOnItemClickListener { _, _, position, _ ->
            val selectedRetention = DataRetentionOption.values()[position]
            viewModel.updateDataRetentionDays(selectedRetention.days)
        }

        // Export format selection
        binding.spinnerExportFormat.setOnItemClickListener { _, _, position, _ ->
            val selectedFormat = ExportFormatOption.values()[position]
            viewModel.updateExportFormat(selectedFormat.format)
        }

        // Data management actions
        binding.btnExportData.setOnClickListener {
            showExportConfirmation()
        }

        binding.btnImportData.setOnClickListener {
            importLauncher.launch("*/*")
        }

        binding.btnClearAllData.setOnClickListener {
            showClearDataConfirmation()
        }

        binding.btnOptimizeDatabase.setOnClickListener {
            showOptimizeConfirmation()
        }

        binding.btnCleanupOldData.setOnClickListener {
            showCleanupConfirmation()
        }

        // Notification test
        binding.btnTestNotifications.setOnClickListener {
            viewModel.testNotifications()
        }

        // Settings reset
        binding.btnResetSettings.setOnClickListener {
            showResetConfirmation()
        }

        // Info buttons
        binding.btnInfoMedicalMode.setOnClickListener {
            showMedicalModeInfo()
        }

        binding.btnInfoDataRetention.setOnClickListener {
            showDataRetentionInfo()
        }

        binding.btnInfoBiometric.setOnClickListener {
            showBiometricAuthInfo()
        }

        // About section
        binding.btnAbout.setOnClickListener {
            showAboutDialog()
        }

        binding.btnPrivacyPolicy.setOnClickListener {
            openPrivacyPolicy()
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

                // Observe settings
                launch {
                    viewModel.notificationsEnabled.collect { enabled ->
                        binding.switchNotifications.isChecked = enabled
                    }
                }

                launch {
                    viewModel.darkModeEnabled.collect { enabled ->
                        binding.switchDarkMode.isChecked = enabled
                    }
                }

                launch {
                    viewModel.medicalMode.collect { enabled ->
                        binding.switchMedicalMode.isChecked = enabled
                    }
                }

                launch {
                    viewModel.showFodmapInfo.collect { enabled ->
                        binding.switchShowFodmap.isChecked = enabled
                    }
                }

                launch {
                    viewModel.biometricAuthEnabled.collect { enabled ->
                        binding.switchBiometricAuth.isChecked = enabled
                    }
                }

                launch {
                    viewModel.mealReminderTime.collect { timeString ->
                        binding.btnMealReminderTime.text = formatTimeForDisplay(timeString)
                    }
                }

                launch {
                    viewModel.symptomReminderTime.collect { timeString ->
                        binding.btnSymptomReminderTime.text = formatTimeForDisplay(timeString)
                    }
                }

                launch {
                    viewModel.dataRetentionDays.collect { days ->
                        val option = DataRetentionOption.values().find { it.days == days }
                        option?.let {
                            binding.spinnerDataRetention.setText(it.displayName, false)
                        }
                    }
                }

                launch {
                    viewModel.exportFormat.collect { format ->
                        val option = ExportFormatOption.values().find { it.format == format }
                        option?.let {
                            binding.spinnerExportFormat.setText(it.displayName, false)
                        }
                    }
                }
            }
        }
    }

    private fun updateUI(state: SettingsViewModel.SettingsUiState) {
        // Update data statistics
        binding.tvTotalFoodEntries.text = state.totalFoodEntries.toString()
        binding.tvTotalSymptomEvents.text = state.totalSymptomEvents.toString()
        binding.tvDatabaseSize.text = state.databaseSizeFormatted
        binding.tvTotalEntries.text = state.totalEntriesFormatted

        // Update oldest entry date
        state.oldestEntryDate?.let { date ->
            binding.tvOldestEntry.text = "Since: $date"
            binding.tvOldestEntry.visibility = View.VISIBLE
        } ?: run {
            binding.tvOldestEntry.visibility = View.GONE
        }

        // Show loading states
        binding.progressBarExporting.visibility = if (state.isExporting) View.VISIBLE else View.GONE
        binding.progressBarImporting.visibility = if (state.isImporting) View.VISIBLE else View.GONE
        binding.progressBarClearing.visibility = if (state.isClearing) View.VISIBLE else View.GONE
        binding.progressBarOptimizing.visibility = if (state.isOptimizing) View.VISIBLE else View.GONE
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        // Enable/disable buttons during operations
        val isOperationInProgress = state.isExporting || state.isImporting ||
                                   state.isClearing || state.isOptimizing || state.isLoading

        binding.btnExportData.isEnabled = !isOperationInProgress && state.hasData
        binding.btnImportData.isEnabled = !isOperationInProgress
        binding.btnClearAllData.isEnabled = !isOperationInProgress && state.hasData
        binding.btnOptimizeDatabase.isEnabled = !isOperationInProgress && state.hasData
        binding.btnCleanupOldData.isEnabled = !isOperationInProgress && state.hasData

        // Update app info
        binding.tvAppVersion.text = "Version ${viewModel.getAppVersion()}"
        binding.tvBuildNumber.text = "Build ${viewModel.getAppBuildNumber()}"
        binding.tvDatabaseVersion.text = "DB Version ${viewModel.getDatabaseVersion()}"

        // Handle messages
        state.successMessage?.let { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }

        state.errorMessage?.let { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    private fun showTimePicker(tag: String, onTimeSelected: (LocalTime) -> Unit) {
        val now = LocalTime.now()
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(now.hour)
            .setMinute(now.minute)
            .setTitleText("Select Time")
            .build()

        timePicker.addOnPositiveButtonClickListener {
            val selectedTime = LocalTime.of(timePicker.hour, timePicker.minute)
            onTimeSelected(selectedTime)
        }

        timePicker.show(parentFragmentManager, tag)
    }

    private fun formatTimeForDisplay(timeString: String): String {
        return try {
            val time = LocalTime.parse(timeString)
            "${String.format("%02d", time.hour)}:${String.format("%02d", time.minute)}"
        } catch (e: Exception) {
            "Not set"
        }
    }

    private fun updateAppTheme(darkMode: Boolean) {
        val mode = if (darkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    private fun showExportConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Export All Data")
            .setMessage("This will create a backup file with all your food entries and symptoms. Continue?")
            .setPositiveButton("Export") { _, _ ->
                viewModel.exportAllData()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showClearDataConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Clear All Data")
            .setMessage("This will permanently delete ALL your food entries and symptoms. This action cannot be undone. Are you sure?")
            .setPositiveButton("Delete All") { _, _ ->
                viewModel.clearAllData()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showOptimizeConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Optimize Database")
            .setMessage("This will optimize the database for better performance and reduced size. Continue?")
            .setPositiveButton("Optimize") { _, _ ->
                viewModel.optimizeDatabase()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showCleanupConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Cleanup Old Data")
            .setMessage("This will remove data older than your retention setting. Continue?")
            .setPositiveButton("Cleanup") { _, _ ->
                viewModel.cleanupOldData()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showResetConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Reset Settings")
            .setMessage("This will reset all settings to their default values. Continue?")
            .setPositiveButton("Reset") { _, _ ->
                viewModel.resetAllSettings()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showMedicalModeInfo() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Medical Mode")
            .setMessage("""
                Medical Mode provides:
                • Enhanced privacy and security
                • Professional terminology
                • Additional medical data fields
                • Structured reporting for healthcare providers
                • Compliance with medical documentation standards
            """.trimIndent())
            .setPositiveButton("Got it", null)
            .show()
    }

    private fun showDataRetentionInfo() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Data Retention")
            .setMessage("""
                Data retention automatically removes old entries to:
                • Keep the app running smoothly
                • Reduce storage usage
                • Focus on recent patterns
                • Maintain privacy

                Choose "Keep Forever" to never delete data automatically.
            """.trimIndent())
            .setPositiveButton("Got it", null)
            .show()
    }

    private fun showBiometricAuthInfo() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Biometric Authentication")
            .setMessage("""
                Biometric authentication adds security by requiring:
                • Fingerprint verification
                • Face recognition
                • Other biometric methods

                Your biometric data stays on your device and is never shared.
            """.trimIndent())
            .setPositiveButton("Got it", null)
            .show()
    }

    private fun showAboutDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Food Diary")
            .setMessage("""
                Food Diary helps you track food intake and digestive symptoms to identify patterns and potential triggers.

                Features:
                • Comprehensive food logging
                • Symptom tracking with Bristol Stool Chart
                • Statistical pattern analysis
                • FODMAP analysis
                • Export capabilities for healthcare providers

                Version: ${viewModel.getAppVersion()}
                Build: ${viewModel.getAppBuildNumber()}
            """.trimIndent())
            .setPositiveButton("OK", null)
            .show()
    }

    private fun openPrivacyPolicy() {
        // Open privacy policy URL or show privacy policy text
        Toast.makeText(requireContext(), "Privacy policy would open here", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
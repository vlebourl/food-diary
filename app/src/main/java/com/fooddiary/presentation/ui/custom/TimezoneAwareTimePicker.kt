package com.fooddiary.presentation.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.*
import androidx.core.content.ContextCompat
import com.fooddiary.R
import com.fooddiary.databinding.ViewTimezoneAwareTimePickerBinding
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

class TimezoneAwareTimePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewTimezoneAwareTimePickerBinding
    private var onTimeChangedListener: ((ZonedDateTime) -> Unit)? = null

    private var selectedDateTime: ZonedDateTime = ZonedDateTime.now()
    private var is24HourFormat: Boolean = true
    private var showSeconds: Boolean = false
    private var allowPastDates: Boolean = true
    private var maxFutureDays: Int = 30

    private val timeZoneAdapter: ArrayAdapter<String>
    private val availableTimeZones = getCommonTimeZones()

    init {
        binding = ViewTimezoneAwareTimePickerBinding.inflate(
            LayoutInflater.from(context), this, true
        )

        // Setup timezone spinner
        timeZoneAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            availableTimeZones.map { "${it.id} (${it.displayName})" }
        )
        timeZoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTimezone.adapter = timeZoneAdapter

        setupViews()
        setupListeners()
        updateDisplayedTime()
    }

    private fun setupViews() {
        // Configure TimePicker
        binding.timePicker.setIs24HourView(is24HourFormat)

        // Set current time zone
        val currentZoneId = ZoneId.systemDefault()
        val currentIndex = availableTimeZones.indexOfFirst { it.id == currentZoneId.id }
        if (currentIndex >= 0) {
            binding.spinnerTimezone.setSelection(currentIndex)
        }

        // Configure DatePicker constraints
        binding.datePicker.minDate = if (allowPastDates) {
            System.currentTimeMillis() - (365L * 24 * 60 * 60 * 1000) // 1 year ago
        } else {
            System.currentTimeMillis()
        }

        binding.datePicker.maxDate = System.currentTimeMillis() +
            (maxFutureDays * 24L * 60 * 60 * 1000)

        // Setup current time button
        binding.buttonCurrentTime.setOnClickListener {
            setToCurrentTime()
        }

        // Setup toggle for seconds
        binding.toggleSeconds.isChecked = showSeconds
        binding.toggleSeconds.setOnCheckedChangeListener { _, isChecked ->
            showSeconds = isChecked
            updateTimeDisplay()
        }

        // Setup 24-hour format toggle
        binding.toggle24Hour.isChecked = is24HourFormat
        binding.toggle24Hour.setOnCheckedChangeListener { _, isChecked ->
            is24HourFormat = isChecked
            binding.timePicker.setIs24HourView(is24HourFormat)
            updateTimeDisplay()
        }
    }

    private fun setupListeners() {
        // Date picker listener
        binding.datePicker.setOnDateChangedListener { _, year, month, dayOfMonth ->
            val newDate = LocalDate.of(year, month + 1, dayOfMonth) // month is 0-based
            updateDateTime(newDate, selectedDateTime.toLocalTime(), selectedDateTime.zone)
        }

        // Time picker listener
        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            val time = if (showSeconds) {
                LocalTime.of(hourOfDay, minute, selectedDateTime.second)
            } else {
                LocalTime.of(hourOfDay, minute, 0)
            }
            updateDateTime(selectedDateTime.toLocalDate(), time, selectedDateTime.zone)
        }

        // Timezone spinner listener
        binding.spinnerTimezone.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedZoneId = availableTimeZones[position].id
                updateDateTime(
                    selectedDateTime.toLocalDate(),
                    selectedDateTime.toLocalTime(),
                    ZoneId.of(selectedZoneId)
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Seconds picker (if enabled)
        setupSecondsInput()
    }

    private fun setupSecondsInput() {
        binding.editTextSeconds.setText(selectedDateTime.second.toString().padStart(2, '0'))

        binding.editTextSeconds.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && showSeconds) {
                try {
                    val seconds = binding.editTextSeconds.text.toString().toIntOrNull()?.coerceIn(0, 59) ?: 0
                    val time = LocalTime.of(
                        selectedDateTime.hour,
                        selectedDateTime.minute,
                        seconds
                    )
                    updateDateTime(selectedDateTime.toLocalDate(), time, selectedDateTime.zone)
                } catch (e: Exception) {
                    // Reset to current seconds if invalid
                    binding.editTextSeconds.setText(selectedDateTime.second.toString().padStart(2, '0'))
                }
            }
        }
    }

    private fun updateDateTime(date: LocalDate, time: LocalTime, zoneId: ZoneId) {
        try {
            val newDateTime = ZonedDateTime.of(date, time, zoneId)

            // Validate constraints
            val now = ZonedDateTime.now()
            if (!allowPastDates && newDateTime.isBefore(now.minusMinutes(5))) {
                // Allow 5 minutes tolerance for past dates
                showError("Selected time cannot be in the past")
                return
            }

            if (newDateTime.isAfter(now.plusDays(maxFutureDays.toLong()))) {
                showError("Selected time is too far in the future")
                return
            }

            selectedDateTime = newDateTime
            updateDisplayedTime()
            updateTimeDisplay()
            onTimeChangedListener?.invoke(selectedDateTime)
            hideError()

        } catch (e: Exception) {
            showError("Invalid date/time combination")
        }
    }

    private fun updateDisplayedTime() {
        // Update the pickers to match current selection
        val date = selectedDateTime.toLocalDate()
        val time = selectedDateTime.toLocalTime()

        binding.datePicker.updateDate(date.year, date.monthValue - 1, date.dayOfMonth)
        binding.timePicker.hour = time.hour
        binding.timePicker.minute = time.minute

        if (showSeconds) {
            binding.editTextSeconds.setText(time.second.toString().padStart(2, '0'))
        }

        // Update timezone selection
        val zoneIndex = availableTimeZones.indexOfFirst { it.id == selectedDateTime.zone.id }
        if (zoneIndex >= 0 && binding.spinnerTimezone.selectedItemPosition != zoneIndex) {
            binding.spinnerTimezone.setSelection(zoneIndex)
        }
    }

    private fun updateTimeDisplay() {
        val formatter = if (is24HourFormat) {
            if (showSeconds) DateTimeFormatter.ofPattern("HH:mm:ss")
            else DateTimeFormatter.ofPattern("HH:mm")
        } else {
            if (showSeconds) DateTimeFormatter.ofPattern("h:mm:ss a")
            else DateTimeFormatter.ofPattern("h:mm a")
        }

        val timeText = selectedDateTime.format(formatter)
        val dateText = selectedDateTime.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"))
        val zoneText = selectedDateTime.zone.getDisplayName(java.time.format.TextStyle.SHORT, Locale.getDefault())

        binding.textSelectedTime.text = "$timeText ($zoneText)"
        binding.textSelectedDate.text = dateText

        // Show/hide seconds input
        binding.layoutSeconds.visibility = if (showSeconds) VISIBLE else GONE
    }

    private fun setToCurrentTime() {
        val currentZoneId = availableTimeZones[binding.spinnerTimezone.selectedItemPosition].id
        selectedDateTime = ZonedDateTime.now(ZoneId.of(currentZoneId))
        updateDisplayedTime()
        updateTimeDisplay()
        onTimeChangedListener?.invoke(selectedDateTime)
    }

    private fun showError(message: String) {
        binding.textError.text = message
        binding.textError.visibility = VISIBLE
        binding.textError.setTextColor(ContextCompat.getColor(context, R.color.error))
    }

    private fun hideError() {
        binding.textError.visibility = GONE
    }

    private fun getCommonTimeZones(): List<TimeZoneInfo> {
        return listOf(
            TimeZoneInfo("America/New_York", "Eastern Time"),
            TimeZoneInfo("America/Chicago", "Central Time"),
            TimeZoneInfo("America/Denver", "Mountain Time"),
            TimeZoneInfo("America/Los_Angeles", "Pacific Time"),
            TimeZoneInfo("America/Anchorage", "Alaska Time"),
            TimeZoneInfo("Pacific/Honolulu", "Hawaii Time"),
            TimeZoneInfo("Europe/London", "GMT"),
            TimeZoneInfo("Europe/Paris", "Central European Time"),
            TimeZoneInfo("Europe/Moscow", "Moscow Time"),
            TimeZoneInfo("Asia/Tokyo", "Japan Time"),
            TimeZoneInfo("Asia/Shanghai", "China Time"),
            TimeZoneInfo("Asia/Kolkata", "India Time"),
            TimeZoneInfo("Australia/Sydney", "Australian Eastern Time"),
            TimeZoneInfo("UTC", "UTC")
        ).plus(
            // Add system default if not in the list
            listOf(TimeZoneInfo(ZoneId.systemDefault().id, "System Default"))
        ).distinctBy { it.id }
    }

    // Public API methods
    fun setDateTime(dateTime: ZonedDateTime) {
        selectedDateTime = dateTime
        updateDisplayedTime()
        updateTimeDisplay()
    }

    fun getDateTime(): ZonedDateTime = selectedDateTime

    fun setOnTimeChangedListener(listener: (ZonedDateTime) -> Unit) {
        onTimeChangedListener = listener
    }

    fun set24HourFormat(use24Hour: Boolean) {
        is24HourFormat = use24Hour
        binding.toggle24Hour.isChecked = use24Hour
        binding.timePicker.setIs24HourView(use24Hour)
        updateTimeDisplay()
    }

    fun setShowSeconds(show: Boolean) {
        showSeconds = show
        binding.toggleSeconds.isChecked = show
        updateTimeDisplay()
    }

    fun setAllowPastDates(allow: Boolean) {
        allowPastDates = allow
        binding.datePicker.minDate = if (allow) {
            System.currentTimeMillis() - (365L * 24 * 60 * 60 * 1000)
        } else {
            System.currentTimeMillis()
        }
    }

    fun setMaxFutureDays(days: Int) {
        maxFutureDays = days
        binding.datePicker.maxDate = System.currentTimeMillis() +
            (days * 24L * 60 * 60 * 1000)
    }

    fun isValidDateTime(): Boolean {
        val now = ZonedDateTime.now()
        return (!allowPastDates || !selectedDateTime.isBefore(now.minusMinutes(5))) &&
                !selectedDateTime.isAfter(now.plusDays(maxFutureDays.toLong()))
    }

    fun getTimeZoneOffset(): String {
        return selectedDateTime.offset.toString()
    }

    fun convertToZone(targetZoneId: ZoneId): ZonedDateTime {
        return selectedDateTime.withZoneSameInstant(targetZoneId)
    }

    private data class TimeZoneInfo(
        val id: String,
        val displayName: String
    )
}
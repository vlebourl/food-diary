package com.fooddiary.presentation.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.fooddiary.R
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.roundToInt

class SeverityScaleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var selectedSeverity: Int = 0 // 0 means no selection, 1-10 are valid values
    private var onSeverityChangedListener: ((Int) -> Unit)? = null

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val selectedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val trackPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val scaleMin = 1
    private val scaleMax = 10
    private val circleRadius = 20f
    private val trackHeight = 8f
    private val padding = 48f

    // Severity colors - gradient from green (mild) to red (severe)
    private val severityColors = listOf(
        Color.parseColor("#4CAF50"), // Green - 1 (very mild)
        Color.parseColor("#8BC34A"), // Light Green - 2
        Color.parseColor("#CDDC39"), // Lime - 3
        Color.parseColor("#FFEB3B"), // Yellow - 4
        Color.parseColor("#FFC107"), // Amber - 5 (moderate)
        Color.parseColor("#FF9800"), // Orange - 6
        Color.parseColor("#FF5722"), // Deep Orange - 7
        Color.parseColor("#F44336"), // Red - 8
        Color.parseColor("#E91E63"), // Pink - 9
        Color.parseColor("#9C27B0")  // Purple - 10 (severe)
    )

    private val severityLabels = listOf(
        "Very Mild", "Mild", "Slight", "Noticeable", "Moderate",
        "Strong", "Intense", "Very Strong", "Severe", "Unbearable"
    )

    init {
        setupPaints()
    }

    private fun setupPaints() {
        textPaint.apply {
            textSize = resources.getDimensionPixelSize(R.dimen.text_size_body_small).toFloat()
            color = ContextCompat.getColor(context, R.color.on_surface)
            typeface = Typeface.DEFAULT
            textAlign = Paint.Align.CENTER
        }

        selectedPaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 4f
            color = ContextCompat.getColor(context, R.color.primary)
        }

        trackPaint.apply {
            style = Paint.Style.FILL
            color = ContextCompat.getColor(context, R.color.outline_variant)
        }

        paint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = resources.getDimensionPixelSize(R.dimen.severity_scale_width)
        val desiredHeight = resources.getDimensionPixelSize(R.dimen.severity_scale_height)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> min(desiredWidth, widthSize)
            else -> desiredWidth
        }

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> min(desiredHeight, heightSize)
            else -> desiredHeight
        }

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val availableWidth = width - (2 * padding)
        val stepWidth = availableWidth / (scaleMax - scaleMin)

        // Draw title
        drawTitle(canvas)

        // Calculate track position
        val trackY = height / 2f
        val trackLeft = padding
        val trackRight = width - padding

        // Draw track background
        val trackRect = RectF(
            trackLeft,
            trackY - trackHeight / 2,
            trackRight,
            trackY + trackHeight / 2
        )
        canvas.drawRoundRect(trackRect, trackHeight / 2, trackHeight / 2, trackPaint)

        // Draw severity circles and labels
        for (severity in scaleMin..scaleMax) {
            val x = padding + ((severity - scaleMin) * stepWidth)
            val isSelected = severity == selectedSeverity

            // Draw circle with severity color
            paint.color = severityColors[severity - 1]
            val radius = if (isSelected) circleRadius * 1.2f else circleRadius
            canvas.drawCircle(x, trackY, radius, paint)

            // Draw selection indicator
            if (isSelected) {
                canvas.drawCircle(x, trackY, radius, selectedPaint)
            }

            // Draw severity number
            textPaint.apply {
                color = Color.WHITE
                textSize = resources.getDimensionPixelSize(R.dimen.text_size_body_medium).toFloat()
                typeface = Typeface.DEFAULT_BOLD
            }
            val numberY = trackY + (textPaint.textSize / 3)
            canvas.drawText(severity.toString(), x, numberY, textPaint)

            // Draw severity label below
            textPaint.apply {
                color = ContextCompat.getColor(context, R.color.on_surface)
                textSize = resources.getDimensionPixelSize(R.dimen.text_size_body_small).toFloat()
                typeface = Typeface.DEFAULT
            }
            val labelY = trackY + circleRadius + 32f
            canvas.drawText(severityLabels[severity - 1], x, labelY, textPaint)
        }

        // Draw scale indicators
        drawScaleIndicators(canvas, trackY)

        // Draw current selection info
        if (selectedSeverity > 0) {
            drawSelectionInfo(canvas)
        }
    }

    private fun drawTitle(canvas: Canvas) {
        val title = "Symptom Severity Scale (1-10)"
        textPaint.apply {
            textSize = resources.getDimensionPixelSize(R.dimen.text_size_title_small).toFloat()
            color = ContextCompat.getColor(context, R.color.on_surface)
            typeface = Typeface.DEFAULT_BOLD
            textAlign = Paint.Align.CENTER
        }

        val titleY = 40f
        canvas.drawText(title, width / 2f, titleY, textPaint)
    }

    private fun drawScaleIndicators(canvas: Canvas, trackY: Float) {
        // Draw mild/moderate/severe zones
        textPaint.apply {
            textSize = resources.getDimensionPixelSize(R.dimen.text_size_caption).toFloat()
            color = ContextCompat.getColor(context, R.color.on_surface_variant)
            textAlign = Paint.Align.CENTER
        }

        val indicatorY = trackY - circleRadius - 20f

        // Mild zone (1-3)
        val mildX = padding + (1.5f * (width - 2 * padding) / (scaleMax - scaleMin))
        canvas.drawText("MILD", mildX, indicatorY, textPaint)

        // Moderate zone (4-7)
        val moderateX = padding + (5f * (width - 2 * padding) / (scaleMax - scaleMin))
        canvas.drawText("MODERATE", moderateX, indicatorY, textPaint)

        // Severe zone (8-10)
        val severeX = padding + (8.5f * (width - 2 * padding) / (scaleMax - scaleMin))
        canvas.drawText("SEVERE", severeX, indicatorY, textPaint)
    }

    private fun drawSelectionInfo(canvas: Canvas) {
        val infoText = "Selected: $selectedSeverity - ${severityLabels[selectedSeverity - 1]}"
        textPaint.apply {
            textSize = resources.getDimensionPixelSize(R.dimen.text_size_body_medium).toFloat()
            color = ContextCompat.getColor(context, R.color.primary)
            typeface = Typeface.DEFAULT_BOLD
            textAlign = Paint.Align.CENTER
        }

        val infoY = height - 32f
        canvas.drawText(infoText, width / 2f, infoY, textPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val touchX = event.x
                val availableWidth = width - (2 * padding)
                val stepWidth = availableWidth / (scaleMax - scaleMin)

                // Find closest severity level
                var closestSeverity = scaleMin
                var closestDistance = Float.MAX_VALUE

                for (severity in scaleMin..scaleMax) {
                    val severityX = padding + ((severity - scaleMin) * stepWidth)
                    val distance = abs(touchX - severityX)

                    if (distance < closestDistance) {
                        closestDistance = distance
                        closestSeverity = severity
                    }
                }

                // Only select if touch is reasonably close (within 40dp)
                val maxTouchDistance = resources.getDimensionPixelSize(R.dimen.touch_target_size) / 2f
                if (closestDistance <= maxTouchDistance) {
                    setSeverity(closestSeverity)
                    performClick()
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    fun setSeverity(severity: Int) {
        val clampedSeverity = severity.coerceIn(0, scaleMax)
        if (selectedSeverity != clampedSeverity) {
            selectedSeverity = clampedSeverity
            onSeverityChangedListener?.invoke(selectedSeverity)
            invalidate()
        }
    }

    fun getSeverity(): Int = selectedSeverity

    fun setOnSeverityChangedListener(listener: (Int) -> Unit) {
        onSeverityChangedListener = listener
    }

    fun clearSelection() {
        selectedSeverity = 0
        invalidate()
    }

    fun isValidSelection(): Boolean = selectedSeverity in scaleMin..scaleMax

    fun getSeverityCategory(): String {
        return when (selectedSeverity) {
            in 1..3 -> "Mild"
            in 4..7 -> "Moderate"
            in 8..10 -> "Severe"
            else -> "Not Selected"
        }
    }

    fun getSeverityDescription(): String {
        return if (selectedSeverity > 0) {
            severityLabels[selectedSeverity - 1]
        } else {
            "No severity selected"
        }
    }

    fun getSeverityColor(): Int {
        return if (selectedSeverity > 0) {
            severityColors[selectedSeverity - 1]
        } else {
            ContextCompat.getColor(context, R.color.outline_variant)
        }
    }

    companion object {
        const val MILD_MAX = 3
        const val MODERATE_MAX = 7
        const val SEVERE_MAX = 10
    }
}
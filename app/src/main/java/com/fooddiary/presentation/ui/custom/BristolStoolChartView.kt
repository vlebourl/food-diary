package com.fooddiary.presentation.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.fooddiary.R
import com.fooddiary.data.models.BristolStoolType
import kotlin.math.min

class BristolStoolChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var selectedType: BristolStoolType? = null
    private var onSelectionChangedListener: ((BristolStoolType?) -> Unit)? = null

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val selectedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val stoolTypes = BristolStoolType.values()
    private val stoolRects = mutableListOf<RectF>()

    private val cornerRadius = 16f
    private val itemSpacing = 12f
    private val padding = 24f

    // Bristol Stool Chart colors - medical accuracy
    private val stoolColors = mapOf(
        BristolStoolType.TYPE_1 to Color.parseColor("#8B4513"), // Saddle Brown - hard lumps
        BristolStoolType.TYPE_2 to Color.parseColor("#A0522D"), // Sienna - lumpy sausage
        BristolStoolType.TYPE_3 to Color.parseColor("#CD853F"), // Peru - cracked sausage
        BristolStoolType.TYPE_4 to Color.parseColor("#DEB887"), // Burlywood - smooth sausage
        BristolStoolType.TYPE_5 to Color.parseColor("#F4A460"), // Sandy Brown - soft blobs
        BristolStoolType.TYPE_6 to Color.parseColor("#DAA520"), // Goldenrod - mushy consistency
        BristolStoolType.TYPE_7 to Color.parseColor("#B8860B")  // Dark Goldenrod - liquid
    )

    init {
        setupPaints()
    }

    private fun setupPaints() {
        textPaint.apply {
            textSize = resources.getDimensionPixelSize(R.dimen.text_size_body_medium).toFloat()
            color = ContextCompat.getColor(context, R.color.on_surface)
            typeface = Typeface.DEFAULT_BOLD
        }

        selectedPaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 4f
            color = ContextCompat.getColor(context, R.color.primary)
        }

        borderPaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 2f
            color = ContextCompat.getColor(context, R.color.outline)
        }

        paint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = resources.getDimensionPixelSize(R.dimen.bristol_chart_width)
        val desiredHeight = resources.getDimensionPixelSize(R.dimen.bristol_chart_height)

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
        val availableHeight = height - (2 * padding)
        val itemHeight = (availableHeight - (itemSpacing * (stoolTypes.size - 1))) / stoolTypes.size

        stoolRects.clear()

        stoolTypes.forEachIndexed { index, stoolType ->
            val top = padding + (index * (itemHeight + itemSpacing))
            val rect = RectF(padding, top, padding + availableWidth, top + itemHeight)
            stoolRects.add(rect)

            // Draw background with stool color
            paint.color = stoolColors[stoolType] ?: Color.GRAY
            canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint)

            // Draw border
            canvas.drawRoundRect(rect, cornerRadius, cornerRadius, borderPaint)

            // Draw selection indicator
            if (stoolType == selectedType) {
                canvas.drawRoundRect(rect, cornerRadius, cornerRadius, selectedPaint)
            }

            // Draw type number and description
            val typeNumber = "Type ${stoolType.typeNumber}"
            val description = getStoolDescription(stoolType)

            // Calculate text positions
            val textX = rect.left + 16f
            val typeNumberY = rect.top + 32f
            val descriptionY = typeNumberY + 24f

            // Draw type number
            textPaint.color = Color.WHITE
            textPaint.textSize = resources.getDimensionPixelSize(R.dimen.text_size_title_small).toFloat()
            canvas.drawText(typeNumber, textX, typeNumberY, textPaint)

            // Draw description
            textPaint.textSize = resources.getDimensionPixelSize(R.dimen.text_size_body_small).toFloat()
            drawMultiLineText(canvas, description, textX, descriptionY, rect.width() - 32f)

            // Draw consistency indicator
            drawConsistencyIndicator(canvas, stoolType, rect)
        }

        // Draw title
        drawTitle(canvas)
    }

    private fun drawMultiLineText(canvas: Canvas, text: String, x: Float, startY: Float, maxWidth: Float) {
        val words = text.split(" ")
        var currentLine = ""
        var currentY = startY

        for (word in words) {
            val testLine = if (currentLine.isEmpty()) word else "$currentLine $word"
            val lineWidth = textPaint.measureText(testLine)

            if (lineWidth > maxWidth && currentLine.isNotEmpty()) {
                canvas.drawText(currentLine, x, currentY, textPaint)
                currentLine = word
                currentY += textPaint.textSize + 4f
            } else {
                currentLine = testLine
            }
        }

        if (currentLine.isNotEmpty()) {
            canvas.drawText(currentLine, x, currentY, textPaint)
        }
    }

    private fun drawConsistencyIndicator(canvas: Canvas, stoolType: BristolStoolType, rect: RectF) {
        val indicatorSize = 8f
        val indicatorX = rect.right - 24f
        val indicatorY = rect.top + 16f

        // Draw consistency dots based on stool type
        val dotCount = when (stoolType) {
            BristolStoolType.TYPE_1, BristolStoolType.TYPE_2 -> 1 // Hard
            BristolStoolType.TYPE_3, BristolStoolType.TYPE_4 -> 2 // Normal
            BristolStoolType.TYPE_5, BristolStoolType.TYPE_6 -> 3 // Soft
            BristolStoolType.TYPE_7 -> 4 // Liquid
        }

        paint.color = Color.WHITE
        repeat(dotCount) { index ->
            canvas.drawCircle(
                indicatorX - (index * (indicatorSize + 2f)),
                indicatorY,
                indicatorSize / 2,
                paint
            )
        }
    }

    private fun drawTitle(canvas: Canvas) {
        val title = "Bristol Stool Chart"
        textPaint.apply {
            textSize = resources.getDimensionPixelSize(R.dimen.text_size_title_medium).toFloat()
            color = ContextCompat.getColor(context, R.color.on_surface)
            typeface = Typeface.DEFAULT_BOLD
        }

        val titleWidth = textPaint.measureText(title)
        val titleX = (width - titleWidth) / 2
        val titleY = 20f

        canvas.drawText(title, titleX, titleY, textPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val touchX = event.x
                val touchY = event.y

                stoolRects.forEachIndexed { index, rect ->
                    if (rect.contains(touchX, touchY)) {
                        val newSelection = stoolTypes[index]
                        selectedType = if (selectedType == newSelection) null else newSelection
                        onSelectionChangedListener?.invoke(selectedType)
                        invalidate()
                        performClick()
                        return true
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    private fun getStoolDescription(stoolType: BristolStoolType): String {
        return when (stoolType) {
            BristolStoolType.TYPE_1 -> "Separate hard lumps, like nuts (hard to pass)"
            BristolStoolType.TYPE_2 -> "Sausage-shaped but lumpy"
            BristolStoolType.TYPE_3 -> "Like a sausage but with cracks on surface"
            BristolStoolType.TYPE_4 -> "Like a sausage or snake, smooth and soft"
            BristolStoolType.TYPE_5 -> "Soft blobs with clear-cut edges"
            BristolStoolType.TYPE_6 -> "Fluffy pieces with ragged edges, mushy"
            BristolStoolType.TYPE_7 -> "Watery, no solid pieces, entirely liquid"
        }
    }

    fun setSelectedType(stoolType: BristolStoolType?) {
        if (selectedType != stoolType) {
            selectedType = stoolType
            invalidate()
        }
    }

    fun getSelectedType(): BristolStoolType? = selectedType

    fun setOnSelectionChangedListener(listener: (BristolStoolType?) -> Unit) {
        onSelectionChangedListener = listener
    }

    fun clearSelection() {
        selectedType = null
        invalidate()
    }

    fun isValidSelection(): Boolean = selectedType != null

    companion object {
        const val CONSTIPATED_THRESHOLD = 2 // Types 1-2
        const val NORMAL_THRESHOLD = 4 // Types 3-4
        const val DIARRHEA_THRESHOLD = 5 // Types 5-7
    }
}
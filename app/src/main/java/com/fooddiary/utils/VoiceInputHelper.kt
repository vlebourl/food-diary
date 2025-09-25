package com.fooddiary.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.core.content.ContextCompat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Singleton
class VoiceInputHelper @Inject constructor(
    private val context: Context,
) {

    private var speechRecognizer: SpeechRecognizer? = null
    private var isListening = false

    private val _voiceInputState = MutableStateFlow(VoiceInputState())
    val voiceInputState: StateFlow<VoiceInputState> = _voiceInputState.asStateFlow()

    private val _recognizedText = MutableStateFlow("")
    val recognizedText: StateFlow<String> = _recognizedText.asStateFlow()

    fun isVoiceRecognitionAvailable(): Boolean {
        return SpeechRecognizer.isRecognitionAvailable(context)
    }

    fun hasAudioPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO,
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun startListening(
        language: String = "en-US",
        prompt: String = "Speak now...",
        maxResults: Int = 5,
    ) {
        if (!isVoiceRecognitionAvailable()) {
            _voiceInputState.value = _voiceInputState.value.copy(
                error = "Voice recognition not available on this device",
            )
            return
        }

        if (!hasAudioPermission()) {
            _voiceInputState.value = _voiceInputState.value.copy(
                error = "Audio permission required for voice input",
            )
            return
        }

        if (isListening) {
            stopListening()
        }

        try {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            speechRecognizer?.setRecognitionListener(createRecognitionListener())

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, language)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, language)
                putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, false)
                putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, maxResults)
                putExtra(RecognizerIntent.EXTRA_PROMPT, prompt)
                putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            }

            _voiceInputState.value = VoiceInputState(
                isListening = true,
                isProcessing = false,
                error = null,
            )

            speechRecognizer?.startListening(intent)
            isListening = true
        } catch (e: Exception) {
            _voiceInputState.value = _voiceInputState.value.copy(
                error = "Failed to start voice recognition: ${e.message}",
            )
        }
    }

    fun stopListening() {
        try {
            speechRecognizer?.stopListening()
            speechRecognizer?.destroy()
            speechRecognizer = null
            isListening = false

            _voiceInputState.value = _voiceInputState.value.copy(
                isListening = false,
                isProcessing = false,
            )
        } catch (e: Exception) {
            _voiceInputState.value = _voiceInputState.value.copy(
                error = "Failed to stop voice recognition: ${e.message}",
            )
        }
    }

    fun clearError() {
        _voiceInputState.value = _voiceInputState.value.copy(error = null)
    }

    fun clearRecognizedText() {
        _recognizedText.value = ""
    }

    private fun createRecognitionListener(): RecognitionListener {
        return object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                _voiceInputState.value = _voiceInputState.value.copy(
                    isListening = true,
                    isProcessing = false,
                    error = null,
                )
            }

            override fun onBeginningOfSpeech() {
                _voiceInputState.value = _voiceInputState.value.copy(
                    isProcessing = true,
                )
            }

            override fun onRmsChanged(rmsdB: Float) {
                // Update volume levels for UI feedback
                _voiceInputState.value = _voiceInputState.value.copy(
                    volumeLevel = rmsdB,
                )
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                // Not used
            }

            override fun onEndOfSpeech() {
                _voiceInputState.value = _voiceInputState.value.copy(
                    isListening = false,
                    isProcessing = true,
                )
            }

            override fun onError(error: Int) {
                val errorMessage = when (error) {
                    SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
                    SpeechRecognizer.ERROR_CLIENT -> "Client side error"
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
                    SpeechRecognizer.ERROR_NETWORK -> "Network error"
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
                    SpeechRecognizer.ERROR_NO_MATCH -> "No speech input"
                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Recognition service busy"
                    SpeechRecognizer.ERROR_SERVER -> "Server error"
                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
                    else -> "Unknown error"
                }

                _voiceInputState.value = VoiceInputState(
                    isListening = false,
                    isProcessing = false,
                    error = errorMessage,
                )

                isListening = false
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val confidence = results?.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES)

                if (!matches.isNullOrEmpty()) {
                    val bestMatch = matches[0]
                    val bestConfidence = confidence?.getOrNull(0) ?: 0f

                    _recognizedText.value = bestMatch
                    _voiceInputState.value = VoiceInputState(
                        isListening = false,
                        isProcessing = false,
                        error = null,
                        confidence = bestConfidence,
                        allResults = matches,
                    )
                } else {
                    _voiceInputState.value = VoiceInputState(
                        isListening = false,
                        isProcessing = false,
                        error = "No speech recognized",
                    )
                }

                isListening = false
            }

            override fun onPartialResults(partialResults: Bundle?) {
                val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    _recognizedText.value = matches[0]
                }
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                // Not used
            }
        }
    }

    // Specialized methods for food logging
    fun startFoodEntryListening() {
        startListening(
            prompt = "Tell me what you ate. For example: 'I had a turkey sandwich with lettuce and tomato'",
            maxResults = 3,
        )
    }

    fun startSymptomEntryListening() {
        startListening(
            prompt = "Describe your symptoms. For example: 'I have stomach pain, severity 6 out of 10'",
            maxResults = 3,
        )
    }

    fun startQuickLogListening() {
        startListening(
            prompt = "Quick log: What did you eat and how do you feel?",
            maxResults = 1,
        )
    }

    // Text processing methods
    fun parseFoodFromText(text: String): ParsedFoodEntry {
        val lowercaseText = text.lowercase(Locale.getDefault())

        // Extract food items
        val foodKeywords = listOf("ate", "had", "consumed", "drank", "eating", "drinking")
        val foods = mutableListOf<String>()

        // Simple parsing - look for common patterns
        val words = text.split(" ")
        var foundFoodKeyword = false

        for (i in words.indices) {
            val word = words[i].lowercase()
            if (foodKeywords.contains(word)) {
                foundFoodKeyword = true
                continue
            }

            if (foundFoodKeyword) {
                // Look for food items after food keywords
                val nextWords = words.drop(i).take(5) // Get next few words
                val foodItem = nextWords.takeWhile { it.lowercase() !in listOf("and", "with", "for", "at") }
                    .joinToString(" ")
                    .replace(Regex("[^a-zA-Z0-9\\s]"), "")
                    .trim()

                if (foodItem.isNotBlank() && foodItem.length > 2) {
                    foods.add(foodItem)
                }
                break
            }
        }

        // Extract meal type
        val mealType = when {
            lowercaseText.contains("breakfast") -> "Breakfast"
            lowercaseText.contains("lunch") -> "Lunch"
            lowercaseText.contains("dinner") -> "Dinner"
            lowercaseText.contains("snack") -> "Snack"
            else -> determineMealTypeByTime()
        }

        // Extract portion information
        val portionRegex = Regex("""(\d+(?:\.\d+)?)\s*(cups?|tablespoons?|teaspoons?|ounces?|grams?|pieces?|slices?)""")
        val portionMatch = portionRegex.find(lowercaseText)
        val portions = portionMatch?.groupValues?.get(1)?.toDoubleOrNull() ?: 1.0
        val portionUnit = portionMatch?.groupValues?.get(2) ?: "serving"

        return ParsedFoodEntry(
            foodName = foods.firstOrNull() ?: "Unknown food",
            portions = portions,
            portionUnit = portionUnit,
            mealType = mealType,
            ingredients = extractIngredients(text),
            originalText = text,
        )
    }

    fun parseSymptomFromText(text: String): ParsedSymptomEntry {
        val lowercaseText = text.lowercase(Locale.getDefault())

        // Extract severity
        val severityRegex = Regex("""(\d+)\s*(?:out of 10|/10|of 10)""")
        val severityMatch = severityRegex.find(lowercaseText)
        val severity = severityMatch?.groupValues?.get(1)?.toIntOrNull() ?: estimateSeverityFromText(lowercaseText)

        // Extract symptom type
        val symptomType = when {
            lowercaseText.contains("stomach") || lowercaseText.contains("abdominal") -> "ABDOMINAL_PAIN"
            lowercaseText.contains("bloat") -> "BLOATING"
            lowercaseText.contains("nausea") -> "NAUSEA"
            lowercaseText.contains("diarrhea") -> "DIARRHEA"
            lowercaseText.contains("constipat") -> "CONSTIPATION"
            lowercaseText.contains("gas") || lowercaseText.contains("flatulence") -> "GAS"
            lowercaseText.contains("cramp") -> "CRAMPING"
            lowercaseText.contains("heartburn") || lowercaseText.contains("reflux") -> "HEARTBURN"
            else -> "GENERAL_DISCOMFORT"
        }

        // Extract duration
        val durationRegex = Regex("""(\d+)\s*(hours?|minutes?|mins?)""")
        val durationMatch = durationRegex.find(lowercaseText)
        val durationMinutes = when {
            durationMatch != null -> {
                val value = durationMatch.groupValues[1].toIntOrNull() ?: 0
                val unit = durationMatch.groupValues[2]
                if (unit.contains("hour")) value * 60 else value
            }
            else -> null
        }

        return ParsedSymptomEntry(
            symptomType = symptomType,
            severity = severity.coerceIn(1, 10),
            duration = durationMinutes,
            notes = text,
            originalText = text,
        )
    }

    private fun extractIngredients(text: String): List<String> {
        val ingredients = mutableListOf<String>()
        val withPattern = Regex("""with\s+([^.]+)""", RegexOption.IGNORE_CASE)
        val matches = withPattern.findAll(text)

        for (match in matches) {
            val ingredientText = match.groupValues[1]
            val splitIngredients = ingredientText.split(" and ", ",")
                .map { it.trim().replace(Regex("[^a-zA-Z0-9\\s]"), "") }
                .filter { it.isNotBlank() && it.length > 2 }

            ingredients.addAll(splitIngredients)
        }

        return ingredients
    }

    private fun determineMealTypeByTime(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 5..10 -> "Breakfast"
            in 11..14 -> "Lunch"
            in 15..17 -> "Snack"
            in 18..22 -> "Dinner"
            else -> "Snack"
        }
    }

    private fun estimateSeverityFromText(text: String): Int {
        return when {
            text.contains("terrible") || text.contains("excruciating") || text.contains("unbearable") -> 9
            text.contains("severe") || text.contains("intense") || text.contains("very bad") -> 7
            text.contains("moderate") || text.contains("painful") || text.contains("bad") -> 5
            text.contains("mild") || text.contains("slight") || text.contains("little") -> 3
            text.contains("barely") || text.contains("hardly") -> 2
            else -> 4 // Default moderate severity
        }
    }

    fun destroy() {
        stopListening()
    }
}

data class VoiceInputState(
    val isListening: Boolean = false,
    val isProcessing: Boolean = false,
    val error: String? = null,
    val volumeLevel: Float = 0f,
    val confidence: Float = 0f,
    val allResults: List<String> = emptyList(),
)

data class ParsedFoodEntry(
    val foodName: String,
    val portions: Double,
    val portionUnit: String,
    val mealType: String,
    val ingredients: List<String>,
    val originalText: String,
)

data class ParsedSymptomEntry(
    val symptomType: String,
    val severity: Int,
    val duration: Int?,
    val notes: String,
    val originalText: String,
)

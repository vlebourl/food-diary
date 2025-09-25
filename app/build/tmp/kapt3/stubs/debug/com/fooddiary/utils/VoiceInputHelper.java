package com.fooddiary.utils;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0014\u001a\u00020\u0015J\u0006\u0010\u0016\u001a\u00020\u0015J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\u0006\u0010\u0019\u001a\u00020\u0015J\b\u0010\u001a\u001a\u00020\u0007H\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u0016\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00070\u001f2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u0006\u0010 \u001a\u00020\u000bJ\u0006\u0010!\u001a\u00020\u000bJ\u000e\u0010\"\u001a\u00020#2\u0006\u0010\u001d\u001a\u00020\u0007J\u000e\u0010$\u001a\u00020%2\u0006\u0010\u001d\u001a\u00020\u0007J\u0006\u0010&\u001a\u00020\u0015J$\u0010\'\u001a\u00020\u00152\b\b\u0002\u0010(\u001a\u00020\u00072\b\b\u0002\u0010)\u001a\u00020\u00072\b\b\u0002\u0010*\u001a\u00020\u001cJ\u0006\u0010+\u001a\u00020\u0015J\u0006\u0010,\u001a\u00020\u0015J\u0006\u0010-\u001a\u00020\u0015R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000f\u00a8\u0006."}, d2 = {"Lcom/fooddiary/utils/VoiceInputHelper;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_recognizedText", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_voiceInputState", "Lcom/fooddiary/utils/VoiceInputState;", "isListening", "", "recognizedText", "Lkotlinx/coroutines/flow/StateFlow;", "getRecognizedText", "()Lkotlinx/coroutines/flow/StateFlow;", "speechRecognizer", "Landroid/speech/SpeechRecognizer;", "voiceInputState", "getVoiceInputState", "clearError", "", "clearRecognizedText", "createRecognitionListener", "Landroid/speech/RecognitionListener;", "destroy", "determineMealTypeByTime", "estimateSeverityFromText", "", "text", "extractIngredients", "", "hasAudioPermission", "isVoiceRecognitionAvailable", "parseFoodFromText", "Lcom/fooddiary/utils/ParsedFoodEntry;", "parseSymptomFromText", "Lcom/fooddiary/utils/ParsedSymptomEntry;", "startFoodEntryListening", "startListening", "language", "prompt", "maxResults", "startQuickLogListening", "startSymptomEntryListening", "stopListening", "app_debug"})
public final class VoiceInputHelper {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.Nullable()
    private android.speech.SpeechRecognizer speechRecognizer;
    private boolean isListening = false;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.fooddiary.utils.VoiceInputState> _voiceInputState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.fooddiary.utils.VoiceInputState> voiceInputState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _recognizedText = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> recognizedText = null;
    
    @javax.inject.Inject()
    public VoiceInputHelper(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.fooddiary.utils.VoiceInputState> getVoiceInputState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getRecognizedText() {
        return null;
    }
    
    public final boolean isVoiceRecognitionAvailable() {
        return false;
    }
    
    public final boolean hasAudioPermission() {
        return false;
    }
    
    public final void startListening(@org.jetbrains.annotations.NotNull()
    java.lang.String language, @org.jetbrains.annotations.NotNull()
    java.lang.String prompt, int maxResults) {
    }
    
    public final void stopListening() {
    }
    
    public final void clearError() {
    }
    
    public final void clearRecognizedText() {
    }
    
    private final android.speech.RecognitionListener createRecognitionListener() {
        return null;
    }
    
    public final void startFoodEntryListening() {
    }
    
    public final void startSymptomEntryListening() {
    }
    
    public final void startQuickLogListening() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.utils.ParsedFoodEntry parseFoodFromText(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.utils.ParsedSymptomEntry parseSymptomFromText(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
        return null;
    }
    
    private final java.util.List<java.lang.String> extractIngredients(java.lang.String text) {
        return null;
    }
    
    private final java.lang.String determineMealTypeByTime() {
        return null;
    }
    
    private final int estimateSeverityFromText(java.lang.String text) {
        return 0;
    }
    
    public final void destroy() {
    }
}
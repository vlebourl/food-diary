package com.fooddiary.services;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2 = {"Lcom/fooddiary/services/ReminderService;", "", "()V", "Companion", "app_debug"})
public final class ReminderService {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String MEAL_REMINDER_CHANNEL_ID = "meal_reminder_channel";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SYMPTOM_REMINDER_CHANNEL_ID = "symptom_reminder_channel";
    public static final int MEAL_REMINDER_ID = 1001;
    public static final int SYMPTOM_REMINDER_ID = 1002;
    public static final int MEAL_REMINDER_REQUEST_CODE = 2001;
    public static final int SYMPTOM_REMINDER_REQUEST_CODE = 2002;
    @org.jetbrains.annotations.NotNull()
    public static final com.fooddiary.services.ReminderService.Companion Companion = null;
    
    public ReminderService() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u0010\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u0016\u0010\u0011\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013J\u0016\u0010\u0014\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/fooddiary/services/ReminderService$Companion;", "", "()V", "MEAL_REMINDER_CHANNEL_ID", "", "MEAL_REMINDER_ID", "", "MEAL_REMINDER_REQUEST_CODE", "SYMPTOM_REMINDER_CHANNEL_ID", "SYMPTOM_REMINDER_ID", "SYMPTOM_REMINDER_REQUEST_CODE", "cancelMealReminder", "", "context", "Landroid/content/Context;", "cancelSymptomReminder", "createNotificationChannels", "scheduleMealReminder", "time", "Ljava/time/LocalTime;", "scheduleSymptomReminder", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final void createNotificationChannels(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
        
        public final void scheduleMealReminder(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        java.time.LocalTime time) {
        }
        
        public final void scheduleSymptomReminder(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        java.time.LocalTime time) {
        }
        
        public final void cancelMealReminder(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
        
        public final void cancelSymptomReminder(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
    }
}
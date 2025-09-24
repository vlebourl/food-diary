package com.fooddiary.services;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010$\n\u0002\b\u000b\b\u0007\u0018\u00002\u00020\u0001:\u00014B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u0082@\u00a2\u0006\u0002\u0010\nJl\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000e2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000e2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u000e2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\t\u001a\u00020\u0006H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001aJP\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00060\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000e2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\t\u001a\u00020\u0006H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001c\u0010\u001dJX\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00060\f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000e2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u000e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\t\u001a\u00020\u0006H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b!\u0010\"JP\u0010#\u001a\u00020\b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000e2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000e2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u000e2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0002J$\u0010$\u001a\u00020\b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000e2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00150\u000eH\u0002J$\u0010&\u001a\u00020\b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000e2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00150\u000eH\u0002J\u0016\u0010\'\u001a\u00020\b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000eH\u0002J<\u0010(\u001a\u00020\b2\u0018\u0010)\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0*2\u0018\u0010+\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u000e0*H\u0002J4\u0010,\u001a\u00020\b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000e2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0002J<\u0010-\u001a\u00020\b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000e2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u000e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0002J$\u0010.\u001a\u00020\b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000e2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00150\u000eH\u0002J\u0016\u0010/\u001a\u00020\b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000eH\u0002J\u0016\u00100\u001a\u00020\b2\f\u00101\u001a\b\u0012\u0004\u0012\u00020\u00110\u000eH\u0002J\u0016\u00102\u001a\u00020\b2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00150\u000eH\u0002J\u0016\u00103\u001a\u00020\b2\f\u00101\u001a\b\u0012\u0004\u0012\u00020\u00110\u000eH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00065"}, d2 = {"Lcom/fooddiary/services/PDFExportService;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "convertHTMLToPDF", "Ljava/io/File;", "htmlContent", "", "outputFile", "(Ljava/lang/String;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "exportAnalyticsReport", "Lkotlin/Result;", "foodEntries", "", "Lcom/fooddiary/data/database/entities/FoodEntry;", "symptomEvents", "Lcom/fooddiary/data/database/entities/SymptomEvent;", "correlations", "Lcom/fooddiary/domain/analysis/CorrelationAnalysis;", "triggerPatterns", "error/NonExistentClass", "startDate", "Ljava/time/LocalDate;", "endDate", "exportAnalyticsReport-eH_QyT8", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/time/LocalDate;Ljava/time/LocalDate;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "exportFoodDiary", "exportFoodDiary-hUnOzRk", "(Ljava/util/List;Ljava/util/List;Ljava/time/LocalDate;Ljava/time/LocalDate;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "exportMedicalReport", "patientInfo", "Lcom/fooddiary/services/PDFExportService$PatientInfo;", "exportMedicalReport-bMdYcbs", "(Ljava/util/List;Ljava/util/List;Lcom/fooddiary/services/PDFExportService$PatientInfo;Ljava/time/LocalDate;Ljava/time/LocalDate;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateAnalyticsHTML", "generateClinicalFindingsHTML", "triggers", "generateClinicalRecommendationsHTML", "generateCorrelationsHTML", "generateDailyEntriesHTML", "entriesByDate", "", "symptomsByDate", "generateFoodDiaryHTML", "generateMedicalReportHTML", "generateRecommendationsHTML", "generateStatisticalAnalysisHTML", "generateSymptomTimelineHTML", "symptoms", "generateTriggersHTML", "getMostCommonSymptom", "PatientInfo", "app_debug"})
public final class PDFExportService {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    @javax.inject.Inject()
    public PDFExportService(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final java.lang.String generateAnalyticsHTML(java.util.List<com.fooddiary.data.database.entities.FoodEntry> foodEntries, java.util.List<com.fooddiary.data.database.entities.SymptomEvent> symptomEvents, java.util.List<com.fooddiary.domain.analysis.CorrelationAnalysis> correlations, java.util.List<error.NonExistentClass> triggerPatterns, java.time.LocalDate startDate, java.time.LocalDate endDate) {
        return null;
    }
    
    private final java.lang.String generateFoodDiaryHTML(java.util.List<com.fooddiary.data.database.entities.FoodEntry> foodEntries, java.util.List<com.fooddiary.data.database.entities.SymptomEvent> symptomEvents, java.time.LocalDate startDate, java.time.LocalDate endDate) {
        return null;
    }
    
    private final java.lang.String generateMedicalReportHTML(java.util.List<com.fooddiary.domain.analysis.CorrelationAnalysis> correlations, java.util.List<error.NonExistentClass> triggerPatterns, com.fooddiary.services.PDFExportService.PatientInfo patientInfo, java.time.LocalDate startDate, java.time.LocalDate endDate) {
        return null;
    }
    
    private final java.lang.Object convertHTMLToPDF(java.lang.String htmlContent, java.io.File outputFile, kotlin.coroutines.Continuation<? super java.io.File> $completion) {
        return null;
    }
    
    private final java.lang.String generateCorrelationsHTML(java.util.List<com.fooddiary.domain.analysis.CorrelationAnalysis> correlations) {
        return null;
    }
    
    private final java.lang.String generateTriggersHTML(java.util.List<error.NonExistentClass> triggers) {
        return null;
    }
    
    private final java.lang.String generateSymptomTimelineHTML(java.util.List<com.fooddiary.data.database.entities.SymptomEvent> symptoms) {
        return null;
    }
    
    private final java.lang.String generateRecommendationsHTML(java.util.List<com.fooddiary.domain.analysis.CorrelationAnalysis> correlations, java.util.List<error.NonExistentClass> triggers) {
        return null;
    }
    
    private final java.lang.String generateDailyEntriesHTML(java.util.Map<java.time.LocalDate, ? extends java.util.List<com.fooddiary.data.database.entities.FoodEntry>> entriesByDate, java.util.Map<java.time.LocalDate, ? extends java.util.List<com.fooddiary.data.database.entities.SymptomEvent>> symptomsByDate) {
        return null;
    }
    
    private final java.lang.String generateClinicalFindingsHTML(java.util.List<com.fooddiary.domain.analysis.CorrelationAnalysis> correlations, java.util.List<error.NonExistentClass> triggers) {
        return null;
    }
    
    private final java.lang.String generateStatisticalAnalysisHTML(java.util.List<com.fooddiary.domain.analysis.CorrelationAnalysis> correlations) {
        return null;
    }
    
    private final java.lang.String generateClinicalRecommendationsHTML(java.util.List<com.fooddiary.domain.analysis.CorrelationAnalysis> correlations, java.util.List<error.NonExistentClass> triggers) {
        return null;
    }
    
    private final java.lang.String getMostCommonSymptom(java.util.List<com.fooddiary.data.database.entities.SymptomEvent> symptoms) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J5\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001J\t\u0010\u0017\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t\u00a8\u0006\u0018"}, d2 = {"Lcom/fooddiary/services/PDFExportService$PatientInfo;", "", "name", "", "dateOfBirth", "gender", "medicalRecordNumber", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getDateOfBirth", "()Ljava/lang/String;", "getGender", "getMedicalRecordNumber", "getName", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class PatientInfo {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String name = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String dateOfBirth = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String gender = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String medicalRecordNumber = null;
        
        public PatientInfo(@org.jetbrains.annotations.NotNull()
        java.lang.String name, @org.jetbrains.annotations.NotNull()
        java.lang.String dateOfBirth, @org.jetbrains.annotations.Nullable()
        java.lang.String gender, @org.jetbrains.annotations.Nullable()
        java.lang.String medicalRecordNumber) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getDateOfBirth() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getGender() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getMedicalRecordNumber() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.services.PDFExportService.PatientInfo copy(@org.jetbrains.annotations.NotNull()
        java.lang.String name, @org.jetbrains.annotations.NotNull()
        java.lang.String dateOfBirth, @org.jetbrains.annotations.Nullable()
        java.lang.String gender, @org.jetbrains.annotations.Nullable()
        java.lang.String medicalRecordNumber) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}
package com.fooddiary.services;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2 = {"Lcom/fooddiary/services/ImportResult;", "", "()V", "Complete", "FoodEntriesOnly", "SymptomEventsOnly", "Lcom/fooddiary/services/ImportResult$Complete;", "Lcom/fooddiary/services/ImportResult$FoodEntriesOnly;", "Lcom/fooddiary/services/ImportResult$SymptomEventsOnly;", "app_debug"})
public abstract class ImportResult {
    
    private ImportResult() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B)\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\bH\u00c6\u0003J3\u0010\u0012\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000b\u00a8\u0006\u001b"}, d2 = {"Lcom/fooddiary/services/ImportResult$Complete;", "Lcom/fooddiary/services/ImportResult;", "foodEntries", "", "Lcom/fooddiary/data/database/entities/FoodEntry;", "symptomEvents", "Lcom/fooddiary/data/database/entities/SymptomEvent;", "metadata", "Lcom/fooddiary/services/ExportMetadata;", "(Ljava/util/List;Ljava/util/List;Lcom/fooddiary/services/ExportMetadata;)V", "getFoodEntries", "()Ljava/util/List;", "getMetadata", "()Lcom/fooddiary/services/ExportMetadata;", "getSymptomEvents", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class Complete extends com.fooddiary.services.ImportResult {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.fooddiary.data.database.entities.FoodEntry> foodEntries = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.fooddiary.data.database.entities.SymptomEvent> symptomEvents = null;
        @org.jetbrains.annotations.NotNull()
        private final com.fooddiary.services.ExportMetadata metadata = null;
        
        public Complete(@org.jetbrains.annotations.NotNull()
        java.util.List<com.fooddiary.data.database.entities.FoodEntry> foodEntries, @org.jetbrains.annotations.NotNull()
        java.util.List<com.fooddiary.data.database.entities.SymptomEvent> symptomEvents, @org.jetbrains.annotations.NotNull()
        com.fooddiary.services.ExportMetadata metadata) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.fooddiary.data.database.entities.FoodEntry> getFoodEntries() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.fooddiary.data.database.entities.SymptomEvent> getSymptomEvents() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.services.ExportMetadata getMetadata() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.fooddiary.data.database.entities.FoodEntry> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.fooddiary.data.database.entities.SymptomEvent> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.services.ExportMetadata component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.services.ImportResult.Complete copy(@org.jetbrains.annotations.NotNull()
        java.util.List<com.fooddiary.data.database.entities.FoodEntry> foodEntries, @org.jetbrains.annotations.NotNull()
        java.util.List<com.fooddiary.data.database.entities.SymptomEvent> symptomEvents, @org.jetbrains.annotations.NotNull()
        com.fooddiary.services.ExportMetadata metadata) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0006H\u00c6\u0003J#\u0010\u000e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0017"}, d2 = {"Lcom/fooddiary/services/ImportResult$FoodEntriesOnly;", "Lcom/fooddiary/services/ImportResult;", "foodEntries", "", "Lcom/fooddiary/data/database/entities/FoodEntry;", "metadata", "Lcom/fooddiary/services/ExportMetadata;", "(Ljava/util/List;Lcom/fooddiary/services/ExportMetadata;)V", "getFoodEntries", "()Ljava/util/List;", "getMetadata", "()Lcom/fooddiary/services/ExportMetadata;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class FoodEntriesOnly extends com.fooddiary.services.ImportResult {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.fooddiary.data.database.entities.FoodEntry> foodEntries = null;
        @org.jetbrains.annotations.NotNull()
        private final com.fooddiary.services.ExportMetadata metadata = null;
        
        public FoodEntriesOnly(@org.jetbrains.annotations.NotNull()
        java.util.List<com.fooddiary.data.database.entities.FoodEntry> foodEntries, @org.jetbrains.annotations.NotNull()
        com.fooddiary.services.ExportMetadata metadata) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.fooddiary.data.database.entities.FoodEntry> getFoodEntries() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.services.ExportMetadata getMetadata() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.fooddiary.data.database.entities.FoodEntry> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.services.ExportMetadata component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.services.ImportResult.FoodEntriesOnly copy(@org.jetbrains.annotations.NotNull()
        java.util.List<com.fooddiary.data.database.entities.FoodEntry> foodEntries, @org.jetbrains.annotations.NotNull()
        com.fooddiary.services.ExportMetadata metadata) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0006H\u00c6\u0003J#\u0010\u000e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0017"}, d2 = {"Lcom/fooddiary/services/ImportResult$SymptomEventsOnly;", "Lcom/fooddiary/services/ImportResult;", "symptomEvents", "", "Lcom/fooddiary/data/database/entities/SymptomEvent;", "metadata", "Lcom/fooddiary/services/ExportMetadata;", "(Ljava/util/List;Lcom/fooddiary/services/ExportMetadata;)V", "getMetadata", "()Lcom/fooddiary/services/ExportMetadata;", "getSymptomEvents", "()Ljava/util/List;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class SymptomEventsOnly extends com.fooddiary.services.ImportResult {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.fooddiary.data.database.entities.SymptomEvent> symptomEvents = null;
        @org.jetbrains.annotations.NotNull()
        private final com.fooddiary.services.ExportMetadata metadata = null;
        
        public SymptomEventsOnly(@org.jetbrains.annotations.NotNull()
        java.util.List<com.fooddiary.data.database.entities.SymptomEvent> symptomEvents, @org.jetbrains.annotations.NotNull()
        com.fooddiary.services.ExportMetadata metadata) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.fooddiary.data.database.entities.SymptomEvent> getSymptomEvents() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.services.ExportMetadata getMetadata() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.fooddiary.data.database.entities.SymptomEvent> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.services.ExportMetadata component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.services.ImportResult.SymptomEventsOnly copy(@org.jetbrains.annotations.NotNull()
        java.util.List<com.fooddiary.data.database.entities.SymptomEvent> symptomEvents, @org.jetbrains.annotations.NotNull()
        com.fooddiary.services.ExportMetadata metadata) {
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
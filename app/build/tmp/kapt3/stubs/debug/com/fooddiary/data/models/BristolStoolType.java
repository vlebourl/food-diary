package com.fooddiary.data.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u000f\b\u0086\u0081\u0002\u0018\u0000 \u001b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u001bB\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\f\u001a\u00020\r8F\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\r8F\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0010\u001a\u00020\r8F\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001a\u00a8\u0006\u001c"}, d2 = {"Lcom/fooddiary/data/models/BristolStoolType;", "", "scale", "", "description", "", "medicalCategory", "(Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "displayName", "getDisplayName", "isConstipation", "", "()Z", "isDiarrhea", "isNormal", "getMedicalCategory", "getScale", "()I", "TYPE_1", "TYPE_2", "TYPE_3", "TYPE_4", "TYPE_5", "TYPE_6", "TYPE_7", "Companion", "app_debug"})
public enum BristolStoolType {
    /*public static final*/ TYPE_1 /* = new TYPE_1(0, null, null) */,
    /*public static final*/ TYPE_2 /* = new TYPE_2(0, null, null) */,
    /*public static final*/ TYPE_3 /* = new TYPE_3(0, null, null) */,
    /*public static final*/ TYPE_4 /* = new TYPE_4(0, null, null) */,
    /*public static final*/ TYPE_5 /* = new TYPE_5(0, null, null) */,
    /*public static final*/ TYPE_6 /* = new TYPE_6(0, null, null) */,
    /*public static final*/ TYPE_7 /* = new TYPE_7(0, null, null) */;
    private final int scale = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String description = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String medicalCategory = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.fooddiary.data.models.BristolStoolType.Companion Companion = null;
    
    BristolStoolType(int scale, java.lang.String description, java.lang.String medicalCategory) {
    }
    
    public final int getScale() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMedicalCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDisplayName() {
        return null;
    }
    
    public final boolean isConstipation() {
        return false;
    }
    
    public final boolean isDiarrhea() {
        return false;
    }
    
    public final boolean isNormal() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.fooddiary.data.models.BristolStoolType> getEntries() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\b"}, d2 = {"Lcom/fooddiary/data/models/BristolStoolType$Companion;", "", "()V", "fromScale", "Lcom/fooddiary/data/models/BristolStoolType;", "scale", "", "fromScaleOrThrow", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.fooddiary.data.models.BristolStoolType fromScale(int scale) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.models.BristolStoolType fromScaleOrThrow(int scale) {
            return null;
        }
    }
}
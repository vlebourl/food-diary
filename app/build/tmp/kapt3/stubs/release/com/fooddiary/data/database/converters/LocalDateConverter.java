package com.fooddiary.data.database.converters;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0019\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007\u00a2\u0006\u0002\u0010\u0007J\u0019\u0010\b\u001a\u0004\u0018\u00010\u00062\b\u0010\t\u001a\u0004\u0018\u00010\u0004H\u0007\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/fooddiary/data/database/converters/LocalDateConverter;", "", "()V", "fromLocalDate", "", "date", "Ljava/time/LocalDate;", "(Ljava/time/LocalDate;)Ljava/lang/Long;", "toLocalDate", "epochDay", "(Ljava/lang/Long;)Ljava/time/LocalDate;", "app_release"})
public final class LocalDateConverter {
    
    public LocalDateConverter() {
        super();
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long fromLocalDate(@org.jetbrains.annotations.Nullable()
    java.time.LocalDate date) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalDate toLocalDate(@org.jetbrains.annotations.Nullable()
    java.lang.Long epochDay) {
        return null;
    }
}
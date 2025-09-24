# Data Model: IBS Food & Symptom Tracking Application

## Core Entities

### FoodEntry
```kotlin
@Entity(tableName = "food_entries")
data class FoodEntry(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val timestamp: Instant,
    val timezone: String,
    val name: String,
    val ingredients: List<String>,
    val portions: Float,
    val portionUnit: String,
    val preparationMethod: String?,
    val mealType: MealType,
    val context: ConsumptionContext,
    val notes: String?,
    val createdAt: Instant,
    val modifiedAt: Instant?,
    val isDeleted: Boolean = false
)

enum class MealType {
    BREAKFAST, LUNCH, DINNER, SNACK, OTHER
}

data class ConsumptionContext(
    val location: LocationType,
    val social: SocialContext,
    val speed: EatingSpeed
)

enum class LocationType { HOME, RESTAURANT, WORK, TRAVEL, OTHER }
enum class SocialContext { ALONE, FAMILY, FRIENDS, BUSINESS, OTHER }
enum class EatingSpeed { RUSHED, NORMAL, RELAXED }
```

### BeverageEntry
```kotlin
@Entity(tableName = "beverage_entries")
data class BeverageEntry(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val timestamp: Instant,
    val timezone: String,
    val name: String,
    val type: BeverageType,
    val volume: Float,
    val volumeUnit: VolumeUnit,
    val caffeineContent: Float?, // in mg
    val alcoholContent: Float?, // in percentage
    val sugarContent: Float?, // in grams
    val isCarbonated: Boolean,
    val temperature: Temperature,
    val notes: String?,
    val createdAt: Instant,
    val modifiedAt: Instant?,
    val isDeleted: Boolean = false
)

enum class BeverageType {
    WATER, COFFEE, TEA, JUICE, SODA, ALCOHOL, MILK, OTHER
}
enum class VolumeUnit { ML, OZ, CUP, GLASS }
enum class Temperature { HOT, WARM, COLD, ICED }
```

### SymptomEvent
```kotlin
@Entity(tableName = "symptom_events")
data class SymptomEvent(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val timestamp: Instant,
    val timezone: String,
    val type: SymptomType,
    val severity: Int, // 1-10 scale
    val duration: Duration?,
    val bristolScale: Int?, // 1-7 for bowel movements
    val location: String?, // body location if applicable
    val triggers: List<String>?, // suspected trigger foods
    val photoPath: String?, // local file path
    val notes: String?,
    val createdAt: Instant,
    val modifiedAt: Instant?,
    val isDeleted: Boolean = false
)

enum class SymptomType {
    BLOATING, GAS, PAIN, CRAMPING, DIARRHEA, CONSTIPATION,
    NAUSEA, REFLUX, HEARTBURN, FATIGUE, HEADACHE, OTHER
}
```

### EnvironmentalContext
```kotlin
@Entity(tableName = "environmental_context")
data class EnvironmentalContext(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val date: LocalDate,
    val stressLevel: Int?, // 1-10 scale
    val sleepHours: Float?,
    val sleepQuality: Int?, // 1-10 scale
    val exerciseMinutes: Int?,
    val exerciseType: String?,
    val exerciseIntensity: ExerciseIntensity?,
    val menstrualPhase: MenstrualPhase?,
    val weather: String?,
    val location: String?, // geographic
    val notes: String?,
    val createdAt: Instant,
    val isDeleted: Boolean = false
)

enum class ExerciseIntensity { LOW, MODERATE, HIGH }
enum class MenstrualPhase { MENSTRUAL, FOLLICULAR, OVULATION, LUTEAL }
```

### QuickEntryTemplate
```kotlin
@Entity(tableName = "quick_entry_templates")
data class QuickEntryTemplate(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val icon: String, // Material icon name
    val type: QuickEntryType,
    val defaultData: String, // JSON of prefilled values
    val position: Int, // display order
    val color: String, // hex color
    val isActive: Boolean = true,
    val createdAt: Instant,
    val modifiedAt: Instant?
)

enum class QuickEntryType {
    FOOD, BEVERAGE, SYMPTOM, MEAL_COPY
}
```

### TriggerPattern
```kotlin
@Entity(tableName = "trigger_patterns")
data class TriggerPattern(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val foodId: String?, // reference to food/beverage
    val foodName: String,
    val symptomType: SymptomType,
    val correlationStrength: Float, // 0.0 to 1.0
    val averageTimeOffset: Duration, // time between consumption and symptom
    val occurrences: Int,
    val confidence: Float, // statistical confidence
    val firstDetected: Instant,
    val lastUpdated: Instant
)
```

### EliminationProtocol
```kotlin
@Entity(tableName = "elimination_protocols")
data class EliminationProtocol(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val phase: EliminationPhase,
    val eliminatedFoods: List<String>,
    val reintroducedFoods: List<ReintroducedFood>,
    val symptoms: List<String>, // symptom IDs during protocol
    val isActive: Boolean,
    val notes: String?,
    val createdAt: Instant
)

enum class EliminationPhase {
    BASELINE, ELIMINATION, REINTRODUCTION, MAINTENANCE
}

data class ReintroducedFood(
    val name: String,
    val date: LocalDate,
    val reaction: String?,
    val severity: Int? // 1-10
)
```

### MedicalReport
```kotlin
@Entity(tableName = "medical_reports")
data class MedicalReport(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val generatedDate: Instant,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val format: ReportFormat,
    val filePath: String, // local file path
    val includedSections: List<ReportSection>,
    val totalEntries: Int,
    val totalSymptoms: Int,
    val identifiedPatterns: Int,
    val notes: String?,
    val createdAt: Instant
)

enum class ReportFormat { PDF, JSON }
enum class ReportSection {
    SUMMARY, FOOD_LOG, SYMPTOM_LOG, PATTERNS, CHARTS,
    BRISTOL_CHART, FODMAP_ANALYSIS, RECOMMENDATIONS
}
```

### FODMAPFood
```kotlin
@Entity(tableName = "fodmap_foods")
data class FODMAPFood(
    @PrimaryKey val id: String,
    val name: String,
    val category: FoodCategory,
    val oligosaccharides: FODMAPLevel,
    val disaccharides: FODMAPLevel,
    val monosaccharides: FODMAPLevel,
    val polyols: FODMAPLevel,
    val servingSize: String,
    val notes: String?
)

enum class FoodCategory {
    FRUITS, VEGETABLES, GRAINS, PROTEINS, DAIRY, NUTS_SEEDS, OTHER
}
enum class FODMAPLevel { LOW, MEDIUM, HIGH }
```

## Relationships

### Cross-Reference Tables

```kotlin
@Entity(
    tableName = "food_fodmap_cross_ref",
    primaryKeys = ["foodEntryId", "fodmapFoodId"]
)
data class FoodFODMAPCrossRef(
    val foodEntryId: String,
    val fodmapFoodId: String
)

@Entity(
    tableName = "symptom_food_correlation",
    primaryKeys = ["symptomId", "foodId"]
)
data class SymptomFoodCorrelation(
    val symptomId: String,
    val foodId: String,
    val timeOffset: Long, // minutes between consumption and symptom
    val confidence: Float
)
```

## Data Integrity Rules

1. **Immutability**: All entries use soft delete (isDeleted flag)
2. **Timestamps**: All timestamps include timezone information
3. **Validation**:
   - Severity scales: 1-10 only
   - Bristol scale: 1-7 only
   - Correlation strength: 0.0-1.0
   - Required fields enforced at database level
4. **Cascade Rules**:
   - Deleting food/beverage does NOT delete correlations
   - Reports reference data by ID but store snapshots
5. **Migration Strategy**:
   - Version tracked in database
   - Backward compatible migrations only
   - Data preservation on schema changes

## Indexes

```kotlin
@Entity(
    tableName = "food_entries",
    indices = [
        Index(value = ["timestamp"]),
        Index(value = ["isDeleted", "timestamp"]),
        Index(value = ["mealType"])
    ]
)

@Entity(
    tableName = "symptom_events",
    indices = [
        Index(value = ["timestamp"]),
        Index(value = ["type", "severity"]),
        Index(value = ["isDeleted", "timestamp"])
    ]
)
```

## Performance Considerations

- Pagination for all list queries (default page size: 50)
- Batch inserts for import operations
- Lazy loading for photo attachments
- Background processing for correlation calculations
- Cached computed values for frequently accessed statistics

---
*Data model version: 1.0.0*
*Complies with constitution data integrity requirements*
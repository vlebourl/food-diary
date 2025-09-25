# Data Model Design

## Core Entities

### FoodEntry (Enhanced)
**Purpose**: Represents a meal or snack with comprehensive tracking details
**State**: Immutable after creation (soft delete only per constitutional requirements)

**Fields**:
- `id: Long` (Primary key, auto-generated)
- `timestamp: Instant` (Timezone-aware per constitution)
- `mealType: MealType` (BREAKFAST, LUNCH, DINNER, SNACK)
- `foods: List<String>` (Ingredient list)
- `portions: Map<String, String>` (Ingredient to portion mapping, e.g., "rice" -> "1 cup")
- `notes: String?` (Optional contextual information)
- `isDeleted: Boolean` (Soft delete flag, default: false)
- `createdAt: Instant` (Audit trail)
- `modifiedAt: Instant` (Last modification timestamp)

**Validation Rules**:
- `timestamp` cannot be in future
- `foods` list cannot be empty
- `portions` must have entries for all foods
- Portion values must match acceptable format patterns

**Relationships**:
- One-to-many with SymptomEvent (via correlation)

### SymptomEvent (Enhanced)
**Purpose**: Captures digestive symptoms with detailed classification
**State**: Immutable after creation (soft delete only)

**Fields**:
- `id: Long` (Primary key, auto-generated)
- `timestamp: Instant` (Onset time, timezone-aware)
- `symptomType: SymptomType` (NAUSEA, BLOATING, CRAMPS, DIARRHEA, CONSTIPATION, HEARTBURN, etc.)
- `severity: Int` (1-10 scale)
- `duration: Duration?` (How long symptoms lasted)
- `notes: String?` (Additional details)
- `isDeleted: Boolean` (Soft delete flag, default: false)
- `createdAt: Instant` (Audit trail)
- `modifiedAt: Instant` (Last modification)

**Validation Rules**:
- `severity` must be between 1-10 inclusive
- `timestamp` cannot be in future
- `duration` cannot be negative
- `symptomType` must be from predefined enum

**Relationships**:
- Many-to-many with FoodEntry (via CorrelationPattern)

### CorrelationPattern (New)
**Purpose**: Links food entries to subsequent symptom events with confidence metrics
**State**: Calculated data, can be recalculated/updated

**Fields**:
- `id: Long` (Primary key, auto-generated)
- `foodEntryId: Long` (Foreign key to FoodEntry)
- `symptomEventId: Long` (Foreign key to SymptomEvent)
- `timeOffset: Duration` (Time between food consumption and symptom onset)
- `confidenceScore: Float` (0.0-1.0, strength of correlation)
- `correlationType: CorrelationType` (LIKELY, POSSIBLE, SUSPECTED)
- `calculatedAt: Instant` (When correlation was computed)

**Validation Rules**:
- `timeOffset` must be positive
- `confidenceScore` must be between 0.0-1.0
- `foodEntryId` and `symptomEventId` must reference existing, non-deleted entries
- `timeOffset` must be within user's configured correlation window

**Relationships**:
- Many-to-one with FoodEntry
- Many-to-one with SymptomEvent

### UserPreferences (Enhanced)
**Purpose**: Stores comprehensive user configuration settings
**State**: Mutable configuration data

**Fields**:
- `id: Long` (Primary key, single row table)
- `correlationTimeWindowHours: Int` (Default: 3, range: 1-48)
- `measurementUnit: MeasurementUnit` (METRIC, IMPERIAL)
- `notificationEnabled: Boolean` (Default: true)
- `notificationTime: LocalTime?` (Daily reminder time)
- `dataRetentionMonths: Int` (Default: 12, range: 1-60)
- `exportFormat: ExportFormat` (JSON, CSV)
- `customSymptomTypes: List<String>` (User-defined symptom categories)
- `triggerAlertEnabled: Boolean` (Alert for potential triggers)
- `reportTemplate: ReportTemplate` (BASIC, DETAILED, MEDICAL)
- `privacyMode: Boolean` (Hide sensitive data in exports)
- `createdAt: Instant`
- `modifiedAt: Instant`

**Validation Rules**:
- Only one row allowed in table
- `correlationTimeWindowHours` between 1-48
- `dataRetentionMonths` between 1-60
- `customSymptomTypes` max 20 entries

### ReportData (New)
**Purpose**: Aggregated summaries for healthcare provider sharing
**State**: Generated data, can be regenerated

**Fields**:
- `id: Long` (Primary key, auto-generated)
- `reportType: ReportType` (WEEKLY, MONTHLY, CUSTOM_RANGE)
- `startDate: LocalDate`
- `endDate: LocalDate`
- `totalEntries: Int`
- `totalSymptoms: Int`
- `topTriggerFoods: List<String>` (Most correlated foods)
- `symptomFrequency: Map<SymptomType, Int>` (Symptom occurrence counts)
- `averageSeverity: Float`
- `reportData: String` (JSON blob with detailed analysis)
- `generatedAt: Instant`

**Validation Rules**:
- `startDate` must be before `endDate`
- `endDate` cannot be in future
- Date range cannot exceed 1 year
- Must have minimum 7 days of data

## Enum Definitions

### MealType
```kotlin
enum class MealType {
    BREAKFAST, LUNCH, DINNER, SNACK, BEVERAGE
}
```

### SymptomType
```kotlin
enum class SymptomType {
    NAUSEA, BLOATING, ABDOMINAL_CRAMPS, DIARRHEA,
    CONSTIPATION, HEARTBURN, GAS, ACID_REFLUX,
    STOMACH_PAIN, INDIGESTION, OTHER
}
```

### CorrelationType
```kotlin
enum class CorrelationType {
    LIKELY, POSSIBLE, SUSPECTED, UNLIKELY
}
```

### MeasurementUnit
```kotlin
enum class MeasurementUnit {
    METRIC, IMPERIAL
}
```

### ExportFormat
```kotlin
enum class ExportFormat {
    JSON, CSV
}
```

### ReportTemplate
```kotlin
enum class ReportTemplate {
    BASIC, DETAILED, MEDICAL
}
```

## Database Constraints

### Storage Limits (Constitutional Requirement)
- Maximum 50 entries per day
- 1 year retention period (automatic cleanup)
- 20MB total storage limit
- Automatic data pruning when limits approached

### Indexing Strategy
- Primary indexes on all ID fields
- Composite index on `(timestamp, isDeleted)` for timeline queries
- Index on `correlationTimeOffset` for correlation queries
- Index on `symptomType` for analytics queries

### Migration Strategy
- All schema changes must be backward compatible
- Soft migration approach with default values
- Version-based migration scripts
- Data integrity validation after migrations

## State Transitions

### FoodEntry Lifecycle
1. `CREATED` → Entry created with validation
2. `ACTIVE` → Available for correlation analysis
3. `SOFT_DELETED` → Marked for deletion (preserves correlations)
4. `PURGED` → Removed during retention cleanup

### SymptomEvent Lifecycle
1. `CREATED` → Event logged with validation
2. `CORRELATED` → Linked to food entries
3. `SOFT_DELETED` → Marked for deletion
4. `PURGED` → Removed during cleanup

### Correlation Lifecycle
1. `CALCULATED` → Initial correlation computed
2. `VALIDATED` → User confirmed/adjusted
3. `STALE` → Needs recalculation due to data changes
4. `ARCHIVED` → Associated data deleted

## Data Integrity Rules

### Referential Integrity
- CorrelationPattern entries must reference valid, non-deleted FoodEntry and SymptomEvent
- Cascade deletion rules for soft-deleted parent entities
- Foreign key constraints enforced at database level

### Business Rules
- No duplicate FoodEntry for same user within 1-minute window
- Correlation time windows must respect user preferences
- Report generation requires minimum data thresholds
- Export operations must respect privacy settings

### Audit Requirements
- All mutations logged with timestamp
- Created/modified audit fields on all entities
- Correlation calculation audit trail
- User preference change history
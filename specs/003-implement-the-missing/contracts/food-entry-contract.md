# Food Entry Screen Contract

## Purpose
Allow users to log meals/snacks with ingredients, portions, and timing details.

## UI Contract

### Data Requirements
```kotlin
data class FoodEntryState(
    val mealType: MealType = MealType.LUNCH,
    val foods: List<FoodItem> = emptyList(),
    val timestamp: Instant = Clock.System.now(),
    val notes: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val validationErrors: Map<String, String> = emptyMap()
)

data class FoodItem(
    val name: String,
    val portion: String,
    val isValid: Boolean = true
)

enum class MealType {
    BREAKFAST, LUNCH, DINNER, SNACK, BEVERAGE
}
```

### User Actions
```kotlin
interface FoodEntryActions {
    fun updateMealType(mealType: MealType)
    fun addFoodItem(name: String, portion: String)
    fun removeFoodItem(index: Int)
    fun updateFoodItem(index: Int, name: String, portion: String)
    fun updateTimestamp(timestamp: Instant)
    fun updateNotes(notes: String)
    fun saveFoodEntry()
    fun cancelEntry()
    fun clearForm()
    fun searchFoodSuggestions(query: String): List<String>
}
```

### Expected Behaviors
- Meal type selection with radio buttons/chips
- Dynamic food item list with add/remove functionality
- Timestamp picker respecting user's timezone
- Auto-suggestions for common foods
- Portion input with measurement unit preferences
- Form validation before saving
- Success feedback on save
- Navigation back to timeline on completion

## Validation Rules
```kotlin
data class ValidationRules(
    val minFoodItems: Int = 1,
    val maxFoodItems: Int = 20,
    val maxNotesLength: Int = 500,
    val allowedPortionFormats: List<Regex> = listOf(
        Regex("""\d+(\.\d+)?\s*(cup|tbsp|tsp|oz|g|kg|ml|l)s?"""),
        Regex("""\d+(\.\d+)?\s*(small|medium|large|piece|slice)s?"""),
        Regex("""\d+(\.\d+)?""") // Just numbers
    )
)
```

- At least 1 food item required
- Food names cannot be empty or whitespace only
- Portion formats must match acceptable patterns
- Timestamp cannot be more than 24 hours in the future
- Timestamp cannot be more than 30 days in the past
- Notes limited to 500 characters
- Duplicate food items not allowed in same entry

## Test Scenarios
1. **Valid Entry Creation**: All fields valid, successful save
2. **Validation Errors**: Empty foods, invalid portions, future timestamp
3. **Food Management**: Add, edit, remove food items dynamically
4. **Timestamp Selection**: Date/time picker with timezone handling
5. **Auto-suggestions**: Search and select from food database
6. **Form Persistence**: Maintain form state during configuration changes
7. **Error Recovery**: Handle database save failures gracefully
8. **Navigation**: Cancel without saving, return to timeline after save
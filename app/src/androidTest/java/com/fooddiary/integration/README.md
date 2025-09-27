# Integration Tests for Food Diary App

This directory contains the integration tests for Phase 3.10 of the Food Diary application. These tests validate end-to-end workflows using the Espresso UI testing framework.

## Test Files Overview

### T053: FoodEntryFlowTest.kt
Tests the complete food entry creation workflow:
- Navigation to food entry screen
- Form filling (meal type, foods, portions, timestamp)
- Form validation edge cases
- Saving entries and verifying they appear in timeline
- FODMAP analysis display
- Context fields (location, social context, eating speed)
- Cancel workflow

Key test methods:
- `testCompleteBasicFoodEntryFlow()` - Basic entry creation
- `testFoodEntryWithMultipleItems()` - Multiple food items
- `testFoodEntryFormValidation()` - Validation scenarios
- `testFoodEntryTimestampSelection()` - Custom timestamp
- `testFoodEntryContextFields()` - Context information
- `testFoodEntryCancelFlow()` - Cancel functionality
- `testFODMAPAnalysisDisplay()` - FODMAP analysis

### T054: SymptomCorrelationTest.kt
Tests the food entry → symptom correlation workflow:
- Creating food entries at specific times
- Creating symptom entries with time delays
- Verifying correlation detection and display
- Testing correlation time window settings
- Multiple symptom types and strengths

Key test methods:
- `testBasicFoodSymptomCorrelationFlow()` - Basic correlation
- `testMultipleFoodToSymptomCorrelations()` - Multiple correlations
- `testCorrelationTimeWindowSettings()` - Time window configuration
- `testSymptomEntryWithPotentialTriggers()` - Trigger selection
- `testCorrelationWithDifferentSymptomTypes()` - Various symptoms
- `testCorrelationStrengthCalculation()` - Correlation strength
- `testNoCorrelationScenario()` - No correlation cases

### T055: AnalyticsDashboardTest.kt
Tests the analytics dashboard functionality:
- Overview statistics calculation
- Time range filtering (week, month, three months, custom)
- Chart data display and updates
- Symptom trends and top triggers lists
- Data refresh functionality

Key test methods:
- `testEmptyDashboardDisplay()` - Empty state
- `testOverviewStatsCalculation()` - Statistics calculation
- `testTimeRangeFiltering()` - Time range controls
- `testChartDataUpdates()` - Chart functionality
- `testSymptomTrendsList()` - Trends display
- `testTopTriggersList()` - Triggers analysis
- `testAnalyticsDataRefresh()` - Data refresh

### TestMatchers.kt
Custom Espresso matchers for enhanced test functionality:
- `hasMinimumChildCount()` - RecyclerView item count validation
- `hasChildCount()` - Exact item count validation
- `matchesPattern()` - Regular expression text matching

## Test Data Setup

Each test creates its own isolated test data using the `setupTestData()` helper methods:
- Food entries with various foods and meal types
- Symptom events with different types and severities
- Timed relationships for correlation testing
- Extended data sets for time range testing

## Database Management

Tests use proper database isolation:
- `@Before` setup clears all tables
- `@After` cleanup ensures no test pollution
- Direct database access for test data creation
- Transaction-based operations for consistency

## Navigation Testing

Tests validate navigation between screens:
- Bottom navigation tab switching
- Fragment transitions
- FAB and button interactions
- Back navigation behavior

## UI Interaction Patterns

Tests cover various UI interactions:
- Form filling and validation
- Chip selection and filtering
- RecyclerView scrolling and item selection
- Date/time picker interactions
- Slider and input controls

## Running the Tests

### Prerequisites
- Android device or emulator running API level 26+
- App compiled and ready for testing
- Test device connected and debugging enabled

### Execution Commands

Run all integration tests:
```bash
./gradlew connectedAndroidTest
```

Run specific test class:
```bash
./gradlew connectedAndroidTest --tests "com.fooddiary.integration.FoodEntryFlowTest"
```

Run specific test method:
```bash
./gradlew connectedAndroidTest --tests "com.fooddiary.integration.FoodEntryFlowTest.testCompleteBasicFoodEntryFlow"
```

### Test Configuration

Tests are configured with:
- Hilt dependency injection for database access
- ActivityTestRule for proper activity lifecycle
- Custom test application class for isolation
- Proper resource access and ID resolution

## Expected Test Behavior

### Successful Test Run
- All navigation works correctly
- Form inputs and validations function properly
- Database operations complete successfully
- UI elements display expected content
- Correlations are detected and shown accurately

### Common Issues
- **Navigation failures**: Check that navigation IDs match the actual app
- **Database errors**: Ensure DAO interfaces are properly implemented
- **UI timing issues**: May need IdlingResource for async operations
- **Resource not found**: Verify layout IDs and string resources exist

## Test Maintenance

When modifying the app:
1. Update navigation IDs if changed
2. Update layout IDs if UI is restructured
3. Adjust test data if database schema changes
4. Update assertions if UI behavior changes
5. Add new tests for new features

## Integration with CI/CD

These tests can be integrated into continuous integration:
- Run on pull requests
- Execute on device farm for multiple devices
- Generate test reports and screenshots
- Fail builds on test failures

## Dependencies

The tests rely on:
- Espresso UI testing framework
- Hilt for dependency injection
- Room database for data operations
- AndroidX test utilities
- Custom matcher utilities
# Quick Start Guide: IBS Food & Symptom Tracking App

## Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 34 (Android 14)
- Kotlin 1.9+
- Device/Emulator running Android 7.0+ (API 24+)

## Setup

### 1. Clone and Build
```bash
git clone [repository]
cd food-diary
./gradlew build
```

### 2. Run Tests
```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest

# All tests
./gradlew test connectedAndroidTest
```

### 3. Install on Device
```bash
# Debug build
./gradlew installDebug

# Or via Android Studio
# Run > Run 'app'
```

## First Launch Walkthrough

### Initial Setup
1. **Launch app** - Splash screen with privacy-first messaging
2. **Onboarding** - Brief tutorial on core features
3. **Local storage setup** - Database encryption initialization
4. **Permission requests**:
   - Notifications (for reminders)
   - Storage (for photo attachments)
   - Microphone (optional, for voice input)

### Quick Entry Configuration
1. Navigate to **Settings** > **Quick Entry**
2. Tap **Add Quick Entry Button**
3. Configure your frequent items:
   - Name: "Morning Coffee"
   - Type: Beverage
   - Default quantity: 250ml
   - Icon and color selection
4. Save and see button appear on main screen

### Your First Entry

#### Food Entry
1. From main screen, tap **+ Add Entry**
2. Select **Food**
3. Fill in:
   - Name: "Scrambled Eggs"
   - Portion: 2 eggs
   - Time: Adjust if not current
   - Meal type: Breakfast
   - Context: Home, Relaxed
4. Tap **Save**

#### Quick Coffee Entry
1. From main screen, tap your configured **Coffee** quick button
2. Entry is logged instantly with defaults
3. Optionally tap entry to add notes

#### Symptom Tracking
1. When experiencing symptoms, tap **+ Add Symptom**
2. Select type (e.g., Bloating)
3. Rate severity: 1-10 scale
4. Add duration if ongoing
5. For bowel movements, use Bristol Stool Chart selector
6. Save

### Viewing Patterns

#### Daily Summary
1. Navigate to **Analysis** tab
2. View today's timeline:
   - Food/beverage entries
   - Symptom occurrences
   - Time correlations

#### Weekly Trends
1. In Analysis, swipe to **Trends**
2. View charts:
   - Symptom frequency
   - Severity trends
   - Food correlation hints

### Generating Your First Report
1. After 1 week of tracking, go to **Reports**
2. Tap **Generate Report**
3. Select date range
4. Choose format:
   - PDF for doctor visit
   - JSON for data export
5. Report saves to device storage

## Key Features Demo

### FODMAP Analysis
1. Add a meal with multiple ingredients
2. System automatically analyzes FODMAP content
3. View breakdown in entry details

### Elimination Diet Tracking
1. Go to **Tools** > **Elimination Protocol**
2. Start new protocol
3. Select foods to eliminate
4. Track reintroduction phases
5. Monitor reactions

### Environmental Tracking
1. Daily, add context via **Environment** button
2. Log:
   - Stress level (1-10)
   - Sleep quality
   - Exercise
3. View correlations in analysis

## Validation Checklist

### Core Functionality
- [ ] Food entry saves and displays correctly
- [ ] Beverage tracking with hydration calculation
- [ ] Symptom logging with severity scale
- [ ] Quick entry buttons functional
- [ ] Timeline view shows chronological order

### Data Integrity
- [ ] Entries persist after app restart
- [ ] Timezone handled correctly
- [ ] Edit history maintained
- [ ] Soft delete functioning

### Analysis Features
- [ ] Basic correlations appear after 7 days
- [ ] Charts render correctly
- [ ] FODMAP analysis working
- [ ] Pattern detection identifies obvious triggers

### Privacy & Security
- [ ] Data stays local (no network requests)
- [ ] Database encrypted
- [ ] Photo attachments stored securely
- [ ] Export requires user action

### Performance
- [ ] App launches in <2 seconds
- [ ] Smooth scrolling (60fps)
- [ ] Quick entry instant (<500ms)
- [ ] No memory leaks after extended use

## Troubleshooting

### Database Issues
```bash
# Clear app data (warning: deletes all entries)
adb shell pm clear com.fooddiary

# Check database
adb shell run-as com.fooddiary sqlite3 databases/food_diary.db
```

### Performance Testing
```bash
# Profile app startup
./gradlew :app:benchmark

# Memory analysis
Android Studio > Profiler > Memory Profiler
```

### Export Problems
- Ensure storage permission granted
- Check available storage space
- Try JSON format if PDF fails

## Development Tips

### Adding Test Data
```kotlin
// In debug builds, use TestDataGenerator
TestDataGenerator.populateWeek()
```

### Debugging Correlations
```kotlin
// Enable correlation logging
CorrelationEngine.enableDebugLogging = true
```

### Quick Iteration
```bash
# Hot reload for UI changes
Android Studio > Apply Changes (Ctrl+Alt+F10)
```

## Support
- Check logs: `adb logcat | grep FoodDiary`
- Database inspector in Android Studio
- Run validation tests: `./gradlew validateApp`

---
*Quick Start Guide v1.0.0*
*For detailed documentation, see full user manual*
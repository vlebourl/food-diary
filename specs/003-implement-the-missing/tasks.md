# Tasks: Core Food Diary UI Implementation

**Input**: Design documents from `/specs/003-implement-the-missing/`
**Prerequisites**: plan.md (required), research.md, data-model.md, contracts/

## Execution Flow (main)
```
1. Load plan.md from feature directory
   → Tech stack: Kotlin 1.9+, Room, Navigation Component, Material Design 3, ViewBinding, Hilt DI
   → Structure: Android single-module app
2. Load design documents:
   → data-model.md: 5 entities (FoodEntry, SymptomEvent, CorrelationPattern, UserPreferences, ReportData)
   → contracts/: 6 screen contracts (timeline, food-entry, symptom-entry, analytics, reports, settings)
   → quickstart.md: 8 validation scenarios
3. Generate tasks by category: Android fragments, ViewModels, database, repositories, UI components
4. Apply TDD rules: Tests before implementation, parallel where possible
5. Generated 59 numbered tasks with dependencies
```

## Format: `[ID] [P?] Description`
- **[P]**: Can run in parallel (different files, no dependencies)
- Include exact file paths in Android project structure

## Path Conventions
- **Android project**: `app/src/main/java/com/fooddiary/`, `app/src/test/`, `app/src/androidTest/`
- **Resources**: `app/src/main/res/layout/`, `app/src/main/res/values/`
- **Database**: Existing Room setup in `app/src/main/java/com/fooddiary/data/`

## Phase 3.1: Setup & Database Enhancement

- [x] **T001** Update Room database schema with enhanced entities from data-model.md
- [x] **T002** [P] Create database migration scripts for enhanced FoodEntry in `app/src/main/java/com/fooddiary/data/database/migrations/Migration1to2.kt`
- [x] **T003** [P] Create database migration scripts for enhanced SymptomEvent in `app/src/main/java/com/fooddiary/data/database/migrations/Migration2to3.kt`
- [x] **T004** [P] Add CorrelationPattern entity in `app/src/main/java/com/fooddiary/data/database/entities/CorrelationPattern.kt`
- [x] **T005** [P] Add ReportData entity in `app/src/main/java/com/fooddiary/data/database/entities/ReportData.kt`
- [x] **T006** Update UserPreferences entity with comprehensive settings in `app/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/UserPreferences.kt`

## Phase 3.2: Database Access Layer (TDD) ⚠️ MUST COMPLETE BEFORE 3.3

**CRITICAL: These tests MUST be written and MUST FAIL before ANY implementation**

- [ ] **T007** [P] Unit test for enhanced FoodEntryDao in `app/src/test/java/com/fooddiary/data/database/dao/FoodEntryDaoTest.kt`
- [ ] **T008** [P] Unit test for enhanced SymptomEventDao in `app/src/test/java/com/fooddiary/data/database/dao/SymptomEventDaoTest.kt`
- [ ] **T009** [P] Unit test for CorrelationPatternDao in `app/src/test/java/com/fooddiary/data/database/dao/CorrelationPatternDaoTest.kt`
- [ ] **T010** [P] Unit test for ReportDataDao in `app/src/test/java/com/fooddiary/data/database/dao/ReportDataDaoTest.kt`
- [ ] **T011** [P] Unit test for enhanced UserPreferencesDao in `app/src/test/java/com/fooddiary/data/database/dao/UserPreferencesDaoTest.kt`

## Phase 3.3: DAO Implementation (ONLY after tests are failing)

- [ ] **T012** [P] Implement enhanced FoodEntryDao with correlation queries in `app/src/main/java/com/fooddiary/data/database/dao/FoodEntryDao.kt`
- [ ] **T013** [P] Implement enhanced SymptomEventDao with correlation queries in `app/src/main/java/com/fooddiary/data/database/dao/SymptomEventDao.kt`
- [ ] **T014** [P] Implement CorrelationPatternDao with analytics queries in `app/src/main/java/com/fooddiary/data/database/dao/CorrelationPatternDao.kt`
- [ ] **T015** [P] Implement ReportDataDao with export queries in `app/src/main/java/com/fooddiary/data/database/dao/ReportDataDao.kt`
- [ ] **T016** [P] Implement enhanced UserPreferencesDao in `app/src/main/java/com/fooddiary/data/database/dao/UserPreferencesDao.kt`

## Phase 3.4: Repository Layer Tests (TDD)

- [ ] **T017** [P] Unit test for TimelineRepository in `app/src/test/java/com/fooddiary/data/repository/TimelineRepositoryTest.kt`
- [ ] **T018** [P] Unit test for FoodEntryRepository in `app/src/test/java/com/fooddiary/data/repository/FoodEntryRepositoryTest.kt`
- [ ] **T019** [P] Unit test for SymptomRepository in `app/src/test/java/com/fooddiary/data/repository/SymptomRepositoryTest.kt`
- [ ] **T020** [P] Unit test for AnalyticsRepository in `app/src/test/java/com/fooddiary/data/repository/AnalyticsRepositoryTest.kt`
- [ ] **T021** [P] Unit test for ReportsRepository in `app/src/test/java/com/fooddiary/data/repository/ReportsRepositoryTest.kt`
- [ ] **T022** [P] Unit test for SettingsRepository in `app/src/test/java/com/fooddiary/data/repository/SettingsRepositoryTest.kt`

## Phase 3.5: Repository Implementation

- [ ] **T023** [P] Implement TimelineRepository with pagination in `app/src/main/java/com/fooddiary/data/repository/TimelineRepository.kt`
- [ ] **T024** [P] Implement FoodEntryRepository with validation in `app/src/main/java/com/fooddiary/data/repository/FoodEntryRepository.kt`
- [ ] **T025** [P] Implement SymptomRepository with correlation logic in `app/src/main/java/com/fooddiary/data/repository/SymptomRepository.kt`
- [ ] **T026** [P] Implement AnalyticsRepository with statistics calculations in `app/src/main/java/com/fooddiary/data/repository/AnalyticsRepository.kt`
- [ ] **T027** [P] Implement ReportsRepository with export functionality in `app/src/main/java/com/fooddiary/data/repository/ReportsRepository.kt`
- [ ] **T028** [P] Implement SettingsRepository with preferences management in `app/src/main/java/com/fooddiary/data/repository/SettingsRepository.kt`

## Phase 3.6: ViewModel Tests (TDD)

- [ ] **T029** [P] Unit test for TimelineViewModel in `app/src/test/java/com/fooddiary/presentation/ui/timeline/TimelineViewModelTest.kt`
- [ ] **T030** [P] Unit test for FoodEntryViewModel in `app/src/test/java/com/fooddiary/presentation/ui/entry/FoodEntryViewModelTest.kt`
- [ ] **T031** [P] Unit test for SymptomEntryViewModel in `app/src/test/java/com/fooddiary/presentation/ui/entry/SymptomEntryViewModelTest.kt`
- [ ] **T032** [P] Unit test for AnalyticsViewModel in `app/src/test/java/com/fooddiary/presentation/ui/analytics/AnalyticsViewModelTest.kt`
- [ ] **T033** [P] Unit test for ReportsViewModel in `app/src/test/java/com/fooddiary/presentation/ui/reports/ReportsViewModelTest.kt`
- [ ] **T034** [P] Unit test for SettingsViewModel in `app/src/test/java/com/fooddiary/presentation/ui/settings/SettingsViewModelTest.kt`

## Phase 3.7: ViewModel Implementation

- [ ] **T035** [P] Implement TimelineViewModel with state management in `app/src/main/java/com/fooddiary/presentation/ui/timeline/TimelineViewModel.kt`
- [ ] **T036** [P] Implement FoodEntryViewModel with form validation in `app/src/main/java/com/fooddiary/presentation/ui/entry/FoodEntryViewModel.kt`
- [ ] **T037** [P] Implement SymptomEntryViewModel with correlation detection in `app/src/main/java/com/fooddiary/presentation/ui/entry/SymptomEntryViewModel.kt`
- [ ] **T038** [P] Implement AnalyticsViewModel with chart data processing in `app/src/main/java/com/fooddiary/presentation/ui/analytics/AnalyticsViewModel.kt`
- [ ] **T039** [P] Implement ReportsViewModel with generation logic in `app/src/main/java/com/fooddiary/presentation/ui/reports/ReportsViewModel.kt`
- [ ] **T040** [P] Implement SettingsViewModel with preferences binding in `app/src/main/java/com/fooddiary/presentation/ui/settings/SettingsViewModel.kt`

## Phase 3.8: Layout Resources

- [ ] **T041** [P] Design timeline item layout in `app/src/main/res/layout/item_timeline_entry.xml`
- [ ] **T042** [P] Design food entry form layout in `app/src/main/res/layout/fragment_food_entry.xml`
- [ ] **T043** [P] Design symptom entry form layout in `app/src/main/res/layout/fragment_symptom_entry.xml`
- [ ] **T044** [P] Design analytics dashboard layout in `app/src/main/res/layout/fragment_analytics.xml`
- [ ] **T045** [P] Design reports screen layout in `app/src/main/res/layout/fragment_reports.xml`
- [ ] **T046** [P] Design settings screen layout in `app/src/main/res/layout/fragment_settings.xml`

## Phase 3.9: Fragment Implementation

- [ ] **T047** Replace TimelineFragment placeholder with full implementation in `app/src/main/java/com/fooddiary/presentation/ui/timeline/TimelineFragment.kt`
- [ ] **T048** Replace FoodEntryFragment placeholder with form handling in `app/src/main/java/com/fooddiary/presentation/ui/entry/FoodEntryFragment.kt`
- [ ] **T049** Replace SymptomEntryFragment placeholder with correlation UI in `app/src/main/java/com/fooddiary/presentation/ui/entry/SymptomEntryFragment.kt`
- [ ] **T050** Replace AnalyticsFragment placeholder with charts integration in `app/src/main/java/com/fooddiary/presentation/ui/analytics/AnalyticsFragment.kt`
- [ ] **T051** Replace ReportsFragment placeholder with generation UI in `app/src/main/java/com/fooddiary/presentation/ui/reports/ReportsFragment.kt`
- [ ] **T052** Replace SettingsFragment placeholder with preferences UI in `app/src/main/java/com/fooddiary/presentation/ui/settings/SettingsFragment.kt`

## Phase 3.10: Integration Tests

- [ ] **T053** [P] UI test for complete food entry flow in `app/src/androidTest/java/com/fooddiary/integration/FoodEntryFlowTest.kt`
- [ ] **T054** [P] UI test for symptom correlation workflow in `app/src/androidTest/java/com/fooddiary/integration/SymptomCorrelationTest.kt`
- [ ] **T055** [P] UI test for analytics dashboard functionality in `app/src/androidTest/java/com/fooddiary/integration/AnalyticsDashboardTest.kt`

## Phase 3.11: Edge Case Validation Tests

- [ ] **T056** [P] Test symptom logging with no recent food entries in `app/src/test/java/com/fooddiary/validation/NoFoodCorrelationTest.kt`
- [ ] **T057** [P] Test duplicate food entry prevention within 1-minute window in `app/src/test/java/com/fooddiary/validation/DuplicateEntryTest.kt`
- [ ] **T058** [P] Test report generation with insufficient data handling in `app/src/test/java/com/fooddiary/validation/InsufficientDataReportTest.kt`
- [ ] **T059** [P] Test invalid input validation (portion sizes, severity ratings) in `app/src/test/java/com/fooddiary/validation/InputValidationTest.kt`

## Dependencies

**Setup Dependencies:**
- T001 blocks T007-T011 (schema before tests)
- T002-T006 block T012-T016 (migrations before DAO implementation)

**Test-First Dependencies:**
- T007-T011 must FAIL before T012-T016 (TDD for DAOs)
- T017-T022 must FAIL before T023-T028 (TDD for Repositories)
- T029-T034 must FAIL before T035-T040 (TDD for ViewModels)

**Implementation Dependencies:**
- T012-T016 block T023-T028 (DAOs before Repositories)
- T023-T028 block T035-T040 (Repositories before ViewModels)
- T035-T040 block T047-T052 (ViewModels before Fragments)
- T041-T046 block T047-T052 (Layouts before Fragment implementation)

**Integration Dependencies:**
- T047-T052 block T053-T055 (Implementation before integration tests)

## Parallel Execution Examples

### Database Layer Setup (Phase 3.1)
```bash
# Run T002-T005 together (different migration files):
Task: "Create database migration scripts for enhanced FoodEntry in Migration1to2.kt"
Task: "Create database migration scripts for enhanced SymptomEvent in Migration2to3.kt"
Task: "Add CorrelationPattern entity in entities/CorrelationPattern.kt"
Task: "Add ReportData entity in entities/ReportData.kt"
```

### DAO Tests (Phase 3.2)
```bash
# Run T007-T011 together (different test files):
Task: "Unit test for enhanced FoodEntryDao in FoodEntryDaoTest.kt"
Task: "Unit test for enhanced SymptomEventDao in SymptomEventDaoTest.kt"
Task: "Unit test for CorrelationPatternDao in CorrelationPatternDaoTest.kt"
Task: "Unit test for ReportDataDao in ReportDataDaoTest.kt"
Task: "Unit test for enhanced UserPreferencesDao in UserPreferencesDaoTest.kt"
```

### Repository Implementation (Phase 3.5)
```bash
# Run T023-T028 together (different repository files):
Task: "Implement TimelineRepository with pagination"
Task: "Implement FoodEntryRepository with validation"
Task: "Implement SymptomRepository with correlation logic"
Task: "Implement AnalyticsRepository with statistics calculations"
Task: "Implement ReportsRepository with export functionality"
Task: "Implement SettingsRepository with preferences management"
```

### Layout Resources (Phase 3.8)
```bash
# Run T041-T046 together (different XML files):
Task: "Design timeline item layout in item_timeline_entry.xml"
Task: "Design food entry form layout in fragment_food_entry.xml"
Task: "Design symptom entry form layout in fragment_symptom_entry.xml"
Task: "Design analytics dashboard layout in fragment_analytics.xml"
Task: "Design reports screen layout in fragment_reports.xml"
Task: "Design settings screen layout in fragment_settings.xml"
```

## Notes

- **[P] tasks** = different files, no dependencies, can run in parallel
- **Sequential tasks** modify shared files or have dependencies
- **Verify tests fail** before implementing (TDD requirement)
- **Commit after each task** for clear progress tracking
- **Android-specific**: Follow Material Design 3 guidelines and existing app theming

## Validation Checklist

*GATE: Checked by main() before completion*

- [x] All 6 contracts have corresponding ViewModel tests (T029-T034)
- [x] All 5 entities have enhanced DAO implementations (T012-T016)
- [x] All tests come before implementation (T007-T011 before T012-T016, etc.)
- [x] Parallel tasks truly independent (different files, no shared dependencies)
- [x] Each task specifies exact file path in Android project structure
- [x] No [P] task modifies same file as another [P] task
- [x] TDD flow maintained: Tests → Implementation → Integration
- [x] All fragments replaced with functional implementations (T047-T052)
- [x] Integration tests validate complete user workflows (T053-T055)
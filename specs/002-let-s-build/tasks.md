# Tasks: IBS Food & Symptom Tracking Application

**Input**: Design documents from `/specs/002-let-s-build/`
**Prerequisites**: plan.md (required), research.md, data-model.md, contracts/

## Execution Flow (main)
```
1. Load plan.md from feature directory
   → If not found: ERROR "No implementation plan found"
   → Extract: tech stack, libraries, structure
2. Load optional design documents:
   → data-model.md: Extract entities → model tasks
   → contracts/: Each file → contract test task
   → research.md: Extract decisions → setup tasks
3. Generate tasks by category:
   → Setup: project init, dependencies, linting
   → Tests: contract tests, integration tests
   → Core: models, services, CLI commands
   → Integration: DB, middleware, logging
   → Polish: unit tests, performance, docs
4. Apply task rules:
   → Different files = mark [P] for parallel
   → Same file = sequential (no [P])
   → Tests before implementation (TDD)
5. Number tasks sequentially (T001, T002...)
6. Generate dependency graph
7. Create parallel execution examples
8. Validate task completeness:
   → All contracts have tests?
   → All entities have models?
   → All endpoints implemented?
9. Return: SUCCESS (tasks ready for execution)
```

## Format: `[ID] [P?] Description`
- **[P]**: Can run in parallel (different files, no dependencies)
- Include exact file paths in descriptions

## Path Conventions
- **Mobile (Android)**: `app/src/main/java/com/fooddiary/`, `app/src/test/`
- All paths relative to repository root

## Phase 3.1: Setup

- [x] T001 Create Android project structure following plan.md architecture (app/src/main/java/com/fooddiary/)
- [x] T002 Initialize Android project with Kotlin 1.9+, Room Database, Material Design 3, Hilt dependencies in app/build.gradle.kts
- [x] T003 [P] Configure ktlint, detekt, and ProGuard rules in build configuration files
- [x] T004 [P] Setup SQLCipher encryption for Room database in app/src/main/java/com/fooddiary/data/database/DatabaseModule.kt

## Phase 3.2: Tests First (TDD) ⚠️ MUST COMPLETE BEFORE 3.3
**CRITICAL: These tests MUST be written and MUST FAIL before ANY implementation**

### Repository Contract Tests
- [x] T005 [P] FoodEntryRepository contract test in app/src/test/java/com/fooddiary/data/repository/FoodEntryRepositoryTest.kt
- [x] T006 [P] BeverageEntryRepository contract test in app/src/test/java/com/fooddiary/data/repository/BeverageEntryRepositoryTest.kt
- [x] T007 [P] SymptomEventRepository contract test in app/src/test/java/com/fooddiary/data/repository/SymptomEventRepositoryTest.kt
- [x] T008 [P] EnvironmentalContextRepository contract test in app/src/test/java/com/fooddiary/data/repository/EnvironmentalContextRepositoryTest.kt
- [x] T009 [P] QuickEntryTemplateRepository contract test in app/src/test/java/com/fooddiary/data/repository/QuickEntryTemplateRepositoryTest.kt
- [x] T010 [P] TriggerPatternRepository contract test in app/src/test/java/com/fooddiary/data/repository/TriggerPatternRepositoryTest.kt

### Database Tests
- [x] T011 [P] Database migration tests in app/src/androidTest/java/com/fooddiary/data/database/MigrationTest.kt
- [x] T012 [P] Database encryption validation in app/src/androidTest/java/com/fooddiary/data/database/EncryptionTest.kt

### Integration Tests (User Stories)
- [x] T013 [P] Food entry quick-add flow test in app/src/androidTest/java/com/fooddiary/integration/QuickAddFlowTest.kt
- [x] T014 [P] Symptom tracking integration test in app/src/androidTest/java/com/fooddiary/integration/SymptomTrackingTest.kt
- [x] T015 [P] Pattern analysis integration test in app/src/androidTest/java/com/fooddiary/integration/PatternAnalysisTest.kt
- [x] T016 [P] Report generation test in app/src/androidTest/java/com/fooddiary/integration/ReportGenerationTest.kt
- [x] T017 [P] Elimination diet tracking test in app/src/androidTest/java/com/fooddiary/integration/EliminationProtocolTest.kt

## Phase 3.3: Core Implementation (ONLY after tests are failing)

### Database Layer
- [x] T018 [P] FoodEntry entity in app/src/main/java/com/fooddiary/data/database/entities/FoodEntry.kt
- [x] T019 [P] BeverageEntry entity in app/src/main/java/com/fooddiary/data/database/entities/BeverageEntry.kt
- [x] T020 [P] SymptomEvent entity in app/src/main/java/com/fooddiary/data/database/entities/SymptomEvent.kt
- [x] T021 [P] EnvironmentalContext entity in app/src/main/java/com/fooddiary/data/database/entities/EnvironmentalContext.kt
- [x] T022 [P] QuickEntryTemplate entity in app/src/main/java/com/fooddiary/data/database/entities/QuickEntryTemplate.kt
- [x] T023 [P] TriggerPattern entity in app/src/main/java/com/fooddiary/data/database/entities/TriggerPattern.kt
- [x] T024 [P] FODMAPFood entity and embedded FODMAP database in app/src/main/java/com/fooddiary/data/database/entities/FODMAPFood.kt

### Data Access Objects (DAOs)
- [x] T025 [P] FoodEntryDao in app/src/main/java/com/fooddiary/data/database/dao/FoodEntryDao.kt
- [x] T026 [P] BeverageEntryDao in app/src/main/java/com/fooddiary/data/database/dao/BeverageEntryDao.kt
- [x] T027 [P] SymptomEventDao in app/src/main/java/com/fooddiary/data/database/dao/SymptomEventDao.kt
- [x] T028 [P] EnvironmentalContextDao in app/src/main/java/com/fooddiary/data/database/dao/EnvironmentalContextDao.kt
- [x] T029 [P] QuickEntryTemplateDao in app/src/main/java/com/fooddiary/data/database/dao/QuickEntryTemplateDao.kt
- [x] T030 [P] TriggerPatternDao in app/src/main/java/com/fooddiary/data/database/dao/TriggerPatternDao.kt

### Database and Type Converters
- [x] T031 FoodDiaryDatabase class with Room configuration in app/src/main/java/com/fooddiary/data/database/FoodDiaryDatabase.kt
- [x] T032 [P] Type converters for complex types in app/src/main/java/com/fooddiary/data/database/Converters.kt

### Repository Implementations
- [x] T033 FoodEntryRepositoryImpl in app/src/main/java/com/fooddiary/data/repository/impl/FoodEntryRepositoryImpl.kt
- [x] T034 BeverageEntryRepositoryImpl in app/src/main/java/com/fooddiary/data/repository/impl/BeverageEntryRepositoryImpl.kt
- [x] T035 SymptomEventRepositoryImpl in app/src/main/java/com/fooddiary/data/repository/impl/SymptomEventRepositoryImpl.kt
- [x] T036 EnvironmentalContextRepositoryImpl in app/src/main/java/com/fooddiary/data/repository/impl/EnvironmentalContextRepositoryImpl.kt
- [x] T037 QuickEntryTemplateRepositoryImpl in app/src/main/java/com/fooddiary/data/repository/impl/QuickEntryTemplateRepositoryImpl.kt
- [x] T038 TriggerPatternRepositoryImpl in app/src/main/java/com/fooddiary/data/repository/impl/TriggerPatternRepositoryImpl.kt

### Domain Layer - Use Cases
- [x] T039 [P] AddFoodEntryUseCase in app/src/main/java/com/fooddiary/domain/usecase/AddFoodEntryUseCase.kt
- [x] T040 [P] AddSymptomEventUseCase in app/src/main/java/com/fooddiary/domain/usecase/AddSymptomEventUseCase.kt
- [x] T041 [P] CalculateCorrelationsUseCase in app/src/main/java/com/fooddiary/domain/usecase/CalculateCorrelationsUseCase.kt
- [x] T042 [P] GenerateReportUseCase in app/src/main/java/com/fooddiary/domain/usecase/GenerateReportUseCase.kt
- [x] T043 [P] AnalyzeFODMAPContentUseCase in app/src/main/java/com/fooddiary/domain/usecase/AnalyzeFODMAPContentUseCase.kt

### Analysis Engine
- [x] T044 [P] Statistical correlation algorithms in app/src/main/java/com/fooddiary/domain/analysis/CorrelationEngine.kt
- [x] T045 [P] FODMAP analysis engine in app/src/main/java/com/fooddiary/domain/analysis/FODMAPAnalyzer.kt
- [x] T046 [P] Time-window analysis for trigger detection in app/src/main/java/com/fooddiary/domain/analysis/TimeWindowAnalyzer.kt

## Phase 3.4: UI Layer

### ViewModels
- [x] T047 FoodEntryViewModel in app/src/main/java/com/fooddiary/presentation/viewmodel/FoodEntryViewModel.kt
- [x] T048 SymptomEntryViewModel in app/src/main/java/com/fooddiary/presentation/viewmodel/SymptomEntryViewModel.kt
- [x] T049 AnalyticsViewModel in app/src/main/java/com/fooddiary/presentation/viewmodel/AnalyticsViewModel.kt
- [x] T050 ReportsViewModel in app/src/main/java/com/fooddiary/presentation/viewmodel/ReportsViewModel.kt
- [x] T051 SettingsViewModel in app/src/main/java/com/fooddiary/presentation/viewmodel/SettingsViewModel.kt

### UI Components
- [x] T052 [P] MainActivity with Navigation Component setup in app/src/main/java/com/fooddiary/presentation/MainActivity.kt
- [x] T053 [P] FoodEntryFragment with Material Design 3 form in app/src/main/java/com/fooddiary/presentation/ui/entry/FoodEntryFragment.kt
- [x] T054 [P] SymptomEntryFragment with Bristol Stool Chart in app/src/main/java/com/fooddiary/presentation/ui/entry/SymptomEntryFragment.kt
- [x] T055 [P] QuickEntryFAB with speed dial pattern in app/src/main/java/com/fooddiary/presentation/ui/entry/QuickEntryFAB.kt
- [x] T056 [P] AnalyticsFragment with MPAndroidChart integration in app/src/main/java/com/fooddiary/presentation/ui/analysis/AnalyticsFragment.kt
- [x] T057 [P] ReportsFragment with PDF generation in app/src/main/java/com/fooddiary/presentation/ui/reports/ReportsFragment.kt
- [x] T058 [P] SettingsFragment for quick-entry configuration in app/src/main/java/com/fooddiary/presentation/ui/settings/SettingsFragment.kt

### Custom Views
- [x] T059 [P] Bristol Stool Chart selector view in app/src/main/java/com/fooddiary/presentation/ui/custom/BristolStoolChartView.kt
- [x] T060 [P] Severity scale selector (1-10) in app/src/main/java/com/fooddiary/presentation/ui/custom/SeverityScaleView.kt
- [x] T061 [P] Time picker with timezone support in app/src/main/java/com/fooddiary/presentation/ui/custom/TimezoneAwareTimePicker.kt

## Phase 3.5: Integration & Services

### Dependency Injection
- [x] T062 DatabaseModule for Hilt DI in app/src/main/java/com/fooddiary/di/DatabaseModule.kt
- [x] T063 RepositoryModule for Hilt DI in app/src/main/java/com/fooddiary/di/RepositoryModule.kt
- [x] T064 UseCaseModule for Hilt DI in app/src/main/java/com/fooddiary/di/UseCaseModule.kt

### Background Services
- [x] T065 [P] Reminder notification service using AlarmManager in app/src/main/java/com/fooddiary/services/ReminderService.kt
- [x] T066 [P] Background correlation calculation worker in app/src/main/java/com/fooddiary/services/CorrelationWorker.kt

### Export Services
- [x] T067 [P] PDF export service using Android Print Framework in app/src/main/java/com/fooddiary/services/PDFExportService.kt
- [x] T068 [P] JSON export service with schema validation in app/src/main/java/com/fooddiary/services/JSONExportService.kt

### Voice Input
- [x] T069 [P] Voice-to-text integration for food logging in app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt

### Missing Feature Implementation
- [x] T081 [P] Pattern learning engine for habit recognition in app/src/main/java/com/fooddiary/domain/analysis/PatternLearningEngine.kt
- [x] T082 [P] Elimination protocol UI management in app/src/main/java/com/fooddiary/presentation/ui/elimination/EliminationProtocolFragment.kt
- [x] T083 [P] Environmental context daily entry in app/src/main/java/com/fooddiary/presentation/ui/environment/EnvironmentalContextFragment.kt
- [x] T084 [P] Retroactive entry time limit validation (30-day maximum) in app/src/main/java/com/fooddiary/domain/validation/RetroactiveEntryValidator.kt
- [x] T085 [P] Optimal eating window and eating speed correlation analysis in app/src/main/java/com/fooddiary/domain/analysis/EatingWindowAnalyzer.kt

### Edge Case & Error Handling
- [ ] T086 [P] Timezone conflict resolution for cross-timezone entries in app/src/main/java/com/fooddiary/utils/TimezoneHandler.kt
- [ ] T087 [P] Data corruption detection and recovery in app/src/main/java/com/fooddiary/data/integrity/DataIntegrityChecker.kt
- [ ] T088 [P] Concurrent access handling for multi-entry scenarios in app/src/main/java/com/fooddiary/data/concurrency/ConcurrencyManager.kt

## Phase 3.6: Polish & Validation

### Unit Tests for Business Logic
- [x] T089 [P] CorrelationEngine unit tests in app/src/test/java/com/fooddiary/domain/analysis/CorrelationEngineTest.kt
- [ ] T090 [P] FODMAPAnalyzer unit tests in app/src/test/java/com/fooddiary/domain/analysis/FODMAPAnalyzerTest.kt
- [ ] T091 [P] UseCase unit tests in app/src/test/java/com/fooddiary/domain/usecase/UseCaseTests.kt
- [ ] T092 [P] ViewModel unit tests in app/src/test/java/com/fooddiary/presentation/viewmodel/ViewModelTests.kt

### UI Tests
- [ ] T093 [P] Food entry UI tests in app/src/androidTest/java/com/fooddiary/presentation/ui/FoodEntryUITest.kt
- [ ] T094 [P] Quick entry FAB tests in app/src/androidTest/java/com/fooddiary/presentation/ui/QuickEntryTest.kt
- [x] T095 [P] Bristol Stool Chart interaction tests in app/src/androidTest/java/com/fooddiary/presentation/ui/BristolStoolChartTest.kt

### Performance & Optimization
- [ ] T096 Database query optimization and indexing validation
- [ ] T097 App launch performance testing (<2s target)
- [ ] T098 Memory leak detection and fixing
- [ ] T099 Battery usage optimization

### Final Validation
- [x] T100 Run complete quickstart.md validation checklist
- [ ] T101 Accessibility testing with TalkBack
- [ ] T102 ProGuard/R8 configuration verification
- [ ] T103 APK size validation (<10MB target)

## Dependencies

### Critical Ordering
- **Setup (T001-T004) before everything**
- **All Tests (T005-T017) before implementation (T018+)**
- **Entities (T018-T024) before DAOs (T025-T030)**
- **DAOs before Database class (T031)**
- **Database before Repositories (T033-T038)**
- **Repositories before UseCases (T039-T046)**
- **UseCases before ViewModels (T047-T051)**
- **ViewModels before UI Components (T052-T061)**

### Blocking Dependencies
- T031 blocks T033-T038 (Database before Repositories)
- T033-T038 block T039-T046 (Repositories before UseCases)
- T039-T046 block T047-T051 (UseCases before ViewModels)
- T047-T051 block T052-T058 (ViewModels before UI)
- T062-T064 needed before app assembly

## Parallel Execution Examples

### Phase 3.2 - All Tests in Parallel
```bash
# Launch T005-T017 together (all test files):
Task: "FoodEntryRepository contract test in app/src/test/java/com/fooddiary/data/repository/FoodEntryRepositoryTest.kt"
Task: "BeverageEntryRepository contract test in app/src/test/java/com/fooddiary/data/repository/BeverageEntryRepositoryTest.kt"
Task: "SymptomEventRepository contract test in app/src/test/java/com/fooddiary/data/repository/SymptomEventRepositoryTest.kt"
Task: "Database migration tests in app/src/androidTest/java/com/fooddiary/data/database/MigrationTest.kt"
Task: "Food entry quick-add flow test in app/src/androidTest/java/com/fooddiary/integration/QuickAddFlowTest.kt"
```

### Phase 3.3 - Entity Creation in Parallel
```bash
# Launch T018-T024 together (all entity files):
Task: "FoodEntry entity in app/src/main/java/com/fooddiary/data/database/entities/FoodEntry.kt"
Task: "BeverageEntry entity in app/src/main/java/com/fooddiary/data/database/entities/BeverageEntry.kt"
Task: "SymptomEvent entity in app/src/main/java/com/fooddiary/data/database/entities/SymptomEvent.kt"
Task: "EnvironmentalContext entity in app/src/main/java/com/fooddiary/data/database/entities/EnvironmentalContext.kt"
```

### Phase 3.4 - UI Components in Parallel
```bash
# Launch T053-T058 together (different fragment files):
Task: "FoodEntryFragment with Material Design 3 form in app/src/main/java/com/fooddiary/presentation/ui/entry/FoodEntryFragment.kt"
Task: "SymptomEntryFragment with Bristol Stool Chart in app/src/main/java/com/fooddiary/presentation/ui/entry/SymptomEntryFragment.kt"
Task: "AnalyticsFragment with MPAndroidChart integration in app/src/main/java/com/fooddiary/presentation/ui/analysis/AnalyticsFragment.kt"
```

## Validation Checklist
*GATE: Checked by main() before returning*

- [x] All repository contracts have corresponding tests (T005-T010)
- [x] All entities have model tasks (T018-T024)
- [x] All tests come before implementation (T005-T017 before T018+)
- [x] Parallel tasks truly independent (different files)
- [x] Each task specifies exact file path
- [x] No task modifies same file as another [P] task
- [x] TDD ordering enforced (Phase 3.2 before 3.3)
- [x] Constitutional requirements covered (encryption, privacy, testing)

## Notes
- **[P] tasks** = different files, no dependencies
- **Critical**: Verify tests fail before implementing
- Commit after each task completion
- Follow quickstart.md for validation
- All tasks comply with Food Diary Constitution principles

---
*Generated from plan.md, data-model.md, and contracts/ - 103 total tasks*
*Ready for implementation following Test-Driven Development*
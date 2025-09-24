# Implementation Plan: IBS Food & Symptom Tracking Application

**Branch**: `002-let-s-build` | **Date**: 2025-09-24 | **Spec**: [spec.md](./spec.md)
**Input**: Feature specification from `/specs/002-let-s-build/spec.md`

## Execution Flow (/plan command scope)
```
1. Load feature spec from Input path
   → If not found: ERROR "No feature spec at {path}"
2. Fill Technical Context (scan for NEEDS CLARIFICATION)
   → Detect Project Type from context (web=frontend+backend, mobile=app+api)
   → Set Structure Decision based on project type
3. Fill the Constitution Check section based on the content of the constitution document.
4. Evaluate Constitution Check section below
   → If violations exist: Document in Complexity Tracking
   → If no justification possible: ERROR "Simplify approach first"
   → Update Progress Tracking: Initial Constitution Check
5. Execute Phase 0 → research.md
   → If NEEDS CLARIFICATION remain: ERROR "Resolve unknowns"
6. Execute Phase 1 → contracts, data-model.md, quickstart.md, agent-specific template file (e.g., `CLAUDE.md` for Claude Code, `.github/copilot-instructions.md` for GitHub Copilot, `GEMINI.md` for Gemini CLI, `QWEN.md` for Qwen Code or `AGENTS.md` for opencode).
7. Re-evaluate Constitution Check section
   → If new violations: Refactor design, return to Phase 1
   → Update Progress Tracking: Post-Design Constitution Check
8. Plan Phase 2 → Describe task generation approach (DO NOT create tasks.md)
9. STOP - Ready for /tasks command
```

**IMPORTANT**: The /plan command STOPS at step 7. Phases 2-4 are executed by other commands:
- Phase 2: /tasks command creates tasks.md
- Phase 3-4: Implementation execution (manual or via tools)

## Summary
Building a comprehensive Android IBS tracking application with local-only storage, quick-entry capabilities for food/symptom logging, advanced pattern analysis for trigger identification, and medical report generation. The app follows Material Design 3, MVVM architecture, and offline-first principles with Room database as the source of truth.

## Technical Context
**Language/Version**: Kotlin 1.9+ with Android SDK 34 (Android 14)
**Primary Dependencies**: Room Database, Navigation Component, Material Design 3, Kotlin Coroutines, Hilt
**Storage**: SQLite via Room (local-only, encrypted)
**Testing**: JUnit 5, Mockito, Espresso, Robolectric
**Target Platform**: Android 7.0+ (API 24+), optimized for Android 14
**Project Type**: mobile - Android application with offline-first architecture
**Performance Goals**: <2s app launch, 60fps UI, <100MB memory usage
**Constraints**: Local-only storage, mandatory encryption, full offline capability
**Scale/Scope**: Single user per device, unlimited historical data, 40+ tracking parameters

## Constitution Check
*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

### Core Principles Compliance
- [x] **User Privacy First**: Local-only storage, encrypted database, no cloud sync by default
- [x] **Medical Accuracy**: 1-10 severity scale, Bristol Stool Chart, evidence-based correlations
- [x] **Test-Driven Development**: Tests before implementation planned
- [x] **Data Integrity**: Immutable entries with soft deletes, timezone-aware timestamps
- [x] **Offline-First Architecture**: Fully functional without network, Room as source of truth

### Android Development Standards
- [x] **MVVM with Repository**: Planned architecture pattern
- [x] **Kotlin Coroutines**: For all async operations
- [x] **Navigation Component**: For screen transitions
- [x] **Material Design 3**: UI consistency
- [x] **Dependency Injection**: Hilt for DI

### Performance Constraints
- [x] **App launch < 2s**: Targeted in requirements
- [x] **60 fps UI**: Standard Android requirement
- [x] **Indexed queries**: For large datasets
- [x] **Memory < 100MB**: Constraint specified
- [x] **Battery efficient**: Background operations minimized

### Quality Assurance
- [x] **80% coverage**: Business logic testing planned
- [x] **ViewModel tests**: Required by constitution
- [x] **Database tests**: Instrumented tests planned
- [x] **E2E tests**: Critical flows covered
- [x] **APK < 10MB**: Without assets target

## Project Structure

### Documentation (this feature)
```
specs/002-let-s-build/
├── plan.md              # This file (/plan command output)
├── research.md          # Phase 0 output (/plan command)
├── data-model.md        # Phase 1 output (/plan command)
├── quickstart.md        # Phase 1 output (/plan command)
├── contracts/           # Phase 1 output (/plan command)
└── tasks.md             # Phase 2 output (/tasks command - NOT created by /plan)
```

### Source Code (repository root)
```
# Option 3: Mobile + API (Android application)
app/
├── src/
│   ├── main/
│   │   ├── java/com/fooddiary/
│   │   │   ├── data/
│   │   │   │   ├── database/
│   │   │   │   │   ├── entities/
│   │   │   │   │   ├── dao/
│   │   │   │   │   └── FoodDiaryDatabase.kt
│   │   │   │   ├── repository/
│   │   │   │   └── models/
│   │   │   ├── domain/
│   │   │   │   ├── usecase/
│   │   │   │   └── analysis/
│   │   │   ├── presentation/
│   │   │   │   ├── ui/
│   │   │   │   │   ├── entry/
│   │   │   │   │   ├── analysis/
│   │   │   │   │   ├── reports/
│   │   │   │   │   └── settings/
│   │   │   │   ├── viewmodel/
│   │   │   │   └── navigation/
│   │   │   ├── di/
│   │   │   └── utils/
│   │   └── res/
│   └── test/
│       ├── unit/
│       └── instrumented/
├── build.gradle.kts
└── proguard-rules.pro
```

**Structure Decision**: Option 3 - Mobile application structure (Android)

## Phase 0: Outline & Research
1. **Extract unknowns from Technical Context** above:
   - Voice input approach (voice-to-text vs commands)
   - Reminder scheduling implementation
   - FODMAP database integration
   - Bristol Stool Chart visual implementation
   - PDF generation library selection
   - Statistical analysis algorithms for pattern detection

2. **Generate and dispatch research agents**:
   ```
   Task: "Research Android voice-to-text APIs for food logging"
   Task: "Find best practices for Room database encryption"
   Task: "Research FODMAP categorization databases"
   Task: "Find Android PDF generation libraries"
   Task: "Research statistical correlation algorithms for time-series health data"
   Task: "Find Material Design 3 components for quick-entry buttons"
   ```

3. **Consolidate findings** in `research.md`

**Output**: research.md with all technical decisions documented

## Phase 1: Design & Contracts
*Prerequisites: research.md complete*

1. **Extract entities from feature spec** → `data-model.md`:
   - FoodEntry entity with ingredients, portions, context
   - BeverageEntry with caffeine, carbonation properties
   - SymptomEvent with severity (1-10), duration, type
   - EnvironmentalContext for stress, sleep, exercise
   - QuickEntryTemplate for user shortcuts
   - EliminationProtocol for diet phases
   - TriggerPattern for correlations
   - MedicalReport for exports

2. **Generate API contracts** from functional requirements:
   - Internal contracts for Repository interfaces
   - Data access contracts for DAOs
   - Export format schemas (PDF, JSON)
   - Output to `/contracts/`

3. **Generate contract tests** from contracts:
   - Database migration tests
   - Repository interface tests
   - Export format validation tests

4. **Extract test scenarios** from user stories:
   - Quick-entry flow tests
   - Pattern analysis validation
   - Report generation tests
   - Elimination diet tracking tests

5. **Update agent file incrementally**:
   - Run `.specify/scripts/bash/update-agent-context.sh claude`
   - Add Android tech stack details
   - Update with Room, Hilt, Material Design 3

**Output**: data-model.md, /contracts/*, failing tests, quickstart.md, CLAUDE.md

## Phase 2: Task Planning Approach
*This section describes what the /tasks command will do - DO NOT execute during /plan*

**Task Generation Strategy**:
- Database schema and entity creation tasks [P]
- DAO interface creation tasks [P]
- Repository implementation tasks
- ViewModel creation tasks for each screen
- UI component tasks for entry screens
- Quick-entry configuration tasks
- Analysis algorithm implementation tasks
- Report generation tasks
- Export functionality tasks

**Ordering Strategy**:
- Database layer first (entities, DAOs)
- Repository layer second
- Domain/UseCase layer third
- ViewModel layer fourth
- UI components last
- Tests interspersed following TDD

**Estimated Output**: 35-40 numbered, ordered tasks in tasks.md

**IMPORTANT**: This phase is executed by the /tasks command, NOT by /plan

## Phase 3+: Future Implementation
*These phases are beyond the scope of the /plan command*

**Phase 3**: Task execution (/tasks command creates tasks.md)
**Phase 4**: Implementation (execute tasks.md following constitutional principles)
**Phase 5**: Validation (run tests, execute quickstart.md, performance validation)

## Complexity Tracking
*No constitution violations detected - standard Android architecture patterns applied*

## Progress Tracking
*This checklist is updated during execution flow*

**Phase Status**:
- [x] Phase 0: Research complete (/plan command)
- [x] Phase 1: Design complete (/plan command)
- [x] Phase 2: Task planning complete (/plan command - describe approach only)
- [ ] Phase 3: Tasks generated (/tasks command)
- [ ] Phase 4: Implementation complete
- [ ] Phase 5: Validation passed

**Gate Status**:
- [x] Initial Constitution Check: PASS
- [x] Post-Design Constitution Check: PASS
- [x] All NEEDS CLARIFICATION resolved (all 4 items resolved in spec.md)
- [x] Complexity deviations documented (none)

---
*Based on Constitution v1.0.0 - See `.specify/memory/constitution.md`*
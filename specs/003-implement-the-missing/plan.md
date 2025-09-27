
# Implementation Plan: Core Food Diary UI Implementation

**Branch**: `003-implement-the-missing` | **Date**: 2025-01-25 | **Spec**: [spec.md](./spec.md)
**Input**: Feature specification from `/specs/003-implement-the-missing/spec.md`

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
Implement complete UI components and functionality for the Android food diary app. Replace placeholder fragments with functional screens for timeline view, food/symptom entry forms, analytics dashboard, reports generation, and comprehensive settings. Focus on MVVM architecture with Room database persistence, Material Design 3 theming, and offline-first approach following constitutional principles.

## Technical Context
**Language/Version**: Kotlin 1.9+ for Android
**Primary Dependencies**: Room database, Navigation Component, Material Design 3, ViewBinding, Hilt DI
**Storage**: Room SQLite database with local file system for exports (JSON/CSV)
**Testing**: JUnit 5, Espresso for UI, Room testing utilities, Mockk for mocking
**Target Platform**: Android API 26+ (Android 8.0+)
**Project Type**: Mobile (Android single-module app)
**Performance Goals**: <2s app launch, <16ms UI operations (60 fps), <100ms database queries
**Constraints**: <100MB memory usage, offline-capable, <20MB storage limit, 50 entries/day max
**Scale/Scope**: 6 main screens, 5 database entities, 1 year data retention, single-user local app

## Constitution Check
*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

**I. User Privacy First**: ✅ PASS
- Local Room database storage (no cloud dependency)
- Export/import functionality for user data control
- No third-party analytics mentioned

**II. Medical Accuracy**: ✅ PASS
- Focus on pattern presentation, not diagnosis
- Correlation tracking with transparent algorithms
- Healthcare provider reporting (not self-diagnosis)

**III. Test-Driven Development**: ✅ PASS
- JUnit 5 for unit tests, Espresso for UI tests
- Room testing utilities for database operations
- TDD approach specified in implementation flow

**IV. Data Integrity**: ✅ PASS
- Immutable food/symptom entries (soft delete pattern)
- Timezone-aware timestamps required
- Validation rules for health data integrity

**V. Offline-First Architecture**: ✅ PASS
- Room SQLite as local source of truth
- No network dependency for core functionality
- Export functionality works offline

**Android Development Standards**: ✅ PASS
- MVVM with Repository pattern specified
- Kotlin coroutines for async operations
- Navigation Component for screen transitions
- Material Design 3 theming
- Hilt dependency injection

**Performance Constraints**: ✅ PASS
- <2s app launch target
- <16ms UI operations (60 fps)
- <100MB memory usage
- Battery-efficient (offline-first reduces background ops)

**Testing Requirements**: ✅ PASS
- Unit tests for ViewModels
- Instrumented tests for database operations
- UI tests with Espresso for critical flows

## Project Structure

### Documentation (this feature)
```
specs/[###-feature]/
├── plan.md              # This file (/plan command output)
├── research.md          # Phase 0 output (/plan command)
├── data-model.md        # Phase 1 output (/plan command)
├── quickstart.md        # Phase 1 output (/plan command)
├── contracts/           # Phase 1 output (/plan command)
└── tasks.md             # Phase 2 output (/tasks command - NOT created by /plan)
```

### Source Code (repository root)
```
# Option 1: Single project (DEFAULT)
src/
├── models/
├── services/
├── cli/
└── lib/

tests/
├── contract/
├── integration/
└── unit/

# Option 2: Web application (when "frontend" + "backend" detected)
backend/
├── src/
│   ├── models/
│   ├── services/
│   └── api/
└── tests/

frontend/
├── src/
│   ├── components/
│   ├── pages/
│   └── services/
└── tests/

# Option 3: Mobile + API (when "iOS/Android" detected)
api/
└── [same as backend above]

ios/ or android/
└── [platform-specific structure]
```

**Structure Decision**: Option 3 (Mobile + API) - Android project structure with existing Room database integration

## Phase 0: Outline & Research
1. **Extract unknowns from Technical Context** above:
   - For each NEEDS CLARIFICATION → research task
   - For each dependency → best practices task
   - For each integration → patterns task

2. **Generate and dispatch research agents**:
   ```
   For each unknown in Technical Context:
     Task: "Research {unknown} for {feature context}"
   For each technology choice:
     Task: "Find best practices for {tech} in {domain}"
   ```

3. **Consolidate findings** in `research.md` using format:
   - Decision: [what was chosen]
   - Rationale: [why chosen]
   - Alternatives considered: [what else evaluated]

**Output**: research.md with all NEEDS CLARIFICATION resolved

## Phase 1: Design & Contracts
*Prerequisites: research.md complete*

1. **Extract entities from feature spec** → `data-model.md`:
   - Entity name, fields, relationships
   - Validation rules from requirements
   - State transitions if applicable

2. **Generate API contracts** from functional requirements:
   - For each user action → endpoint
   - Use standard REST/GraphQL patterns
   - Output OpenAPI/GraphQL schema to `/contracts/`

3. **Generate contract tests** from contracts:
   - One test file per endpoint
   - Assert request/response schemas
   - Tests must fail (no implementation yet)

4. **Extract test scenarios** from user stories:
   - Each story → integration test scenario
   - Quickstart test = story validation steps

5. **Update agent file incrementally** (O(1) operation):
   - Run `.specify/scripts/bash/update-agent-context.sh claude`
     **IMPORTANT**: Execute it exactly as specified above. Do not add or remove any arguments.
   - If exists: Add only NEW tech from current plan
   - Preserve manual additions between markers
   - Update recent changes (keep last 3)
   - Keep under 150 lines for token efficiency
   - Output to repository root

**Output**: data-model.md, /contracts/*, failing tests, quickstart.md, agent-specific file

## Phase 2: Task Planning Approach
*This section describes what the /tasks command will do - DO NOT execute during /plan*

**Task Generation Strategy**:
- Load `.specify/templates/tasks-template.md` as base
- Generate tasks from Phase 1 design docs (contracts, data model, quickstart)
- Each contract → contract test task [P]
- Each entity → model creation task [P] 
- Each user story → integration test task
- Implementation tasks to make tests pass

**Ordering Strategy**:
- TDD order: Tests before implementation 
- Dependency order: Models before services before UI
- Mark [P] for parallel execution (independent files)

**Detailed Task Generation Strategy**:

**From Contracts (6 screens × ~4-5 tasks each = ~25 tasks)**:
1. TimelineFragment: ViewModel, Repository integration, RecyclerView adapter, correlation indicators
2. FoodEntryFragment: Form validation, dynamic food list, timestamp picker, save logic
3. SymptomEntryFragment: Severity UI, trigger correlation, duration picker, save logic
4. AnalyticsFragment: Chart integration, statistics calculation, time range filtering
5. ReportsFragment: Template selection, generation logic, export handling, sharing
6. SettingsFragment: Preference categories, validation, import/export, reset functionality

**From Data Model (5 entities × ~3 tasks each = ~15 tasks)**:
1. Enhanced Room entities with new fields and relationships
2. DAO updates for correlation queries and analytics
3. Repository pattern implementation for each entity
4. Migration scripts for database schema changes
5. Data validation and integrity checks

**From Testing Requirements (~12 tasks)**:
1. Unit tests for ViewModels (6 screens)
2. Repository integration tests
3. Database migration tests
4. UI instrumented tests for critical flows
5. Contract validation tests
6. Performance regression tests

**Dependency Ordering Strategy**:
- Phase A: Database/Models (parallel execution possible)
- Phase B: Repositories (depends on Phase A)
- Phase C: ViewModels (depends on Phase B)
- Phase D: UI Components (depends on Phase C)
- Phase E: Integration & Testing (depends on all)

**Estimated Output**: 52-55 numbered, dependency-ordered tasks in tasks.md

**IMPORTANT**: This phase is executed by the /tasks command, NOT by /plan

## Phase 3+: Future Implementation
*These phases are beyond the scope of the /plan command*

**Phase 3**: Task execution (/tasks command creates tasks.md)  
**Phase 4**: Implementation (execute tasks.md following constitutional principles)  
**Phase 5**: Validation (run tests, execute quickstart.md, performance validation)

## Complexity Tracking
*Fill ONLY if Constitution Check has violations that must be justified*

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| [e.g., 4th project] | [current need] | [why 3 projects insufficient] |
| [e.g., Repository pattern] | [specific problem] | [why direct DB access insufficient] |


## Progress Tracking
*This checklist is updated during execution flow*

**Phase Status**:
- [x] Phase 0: Research complete (/plan command)
- [x] Phase 1: Design complete (/plan command)
- [x] Phase 2: Task planning complete (/plan command - describe approach only)
- [x] Phase 3: Tasks generated (/tasks command)
- [ ] Phase 4: Implementation complete
- [ ] Phase 5: Validation passed

**Gate Status**:
- [x] Initial Constitution Check: PASS
- [x] Post-Design Constitution Check: PASS
- [x] All NEEDS CLARIFICATION resolved
- [x] Complexity deviations documented (N/A - no violations)

---
*Based on Constitution v2.1.1 - See `/memory/constitution.md`*

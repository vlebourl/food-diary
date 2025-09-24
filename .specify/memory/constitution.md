<!--
Sync Impact Report
=================
Version change: 0.0.0 → 1.0.0 (Initial version)
Principles established:
  - I. User Privacy First (NEW)
  - II. Medical Accuracy (NEW)
  - III. Test-Driven Development (NEW)
  - IV. Data Integrity (NEW)
  - V. Offline-First Architecture (NEW)
Added sections:
  - Android Development Standards
  - Quality Assurance
Removed sections: None
Templates requiring updates:
  ✅ plan-template.md (Constitution Check gates aligned)
  ✅ spec-template.md (Requirements aligned)
  ✅ tasks-template.md (Task categorization aligned)
  ✅ agent-file-template.md (Project name aligned)
Follow-up TODOs:
  - RATIFICATION_DATE: Set to today as initial adoption
-->

# Food Diary Constitution

## Core Principles

### I. User Privacy First
All health data must remain under user control. Local storage is primary, cloud sync is optional and encrypted. No third-party analytics without explicit opt-in. User data export and deletion must be available at all times.

### II. Medical Accuracy
Food tracking and symptom correlation must use evidence-based methods. Avoid making medical diagnoses - only present patterns and data. Include disclaimers about consulting healthcare providers. All calculations and correlations must be transparent and explainable.

### III. Test-Driven Development
Every feature requires tests written before implementation. Unit tests for business logic, instrumented tests for UI components, integration tests for data persistence. Red-Green-Refactor cycle is mandatory.

### IV. Data Integrity
Food entries and symptom records are immutable once created - only soft deletes allowed. All timestamps must include timezone information. Data migrations must be backward compatible. Validation rules must prevent invalid health data entry.

### V. Offline-First Architecture
The app must be fully functional without network connectivity. Sync operations must handle conflicts gracefully. Local Room database is the source of truth. Network features are enhancements, not requirements.

## Android Development Standards

### Architecture Requirements
- MVVM pattern with Repository layer for all features
- Kotlin coroutines for async operations (no raw threads)
- Navigation Component for all screen transitions
- Material Design 3 for UI consistency
- Dependency injection via Hilt or manual factories

### Performance Constraints
- App launch time < 2 seconds on mid-range devices
- UI operations must complete in < 16ms (60 fps)
- Database queries must use indexes for datasets > 1000 records
- Memory usage < 100MB for typical usage patterns
- Battery-efficient background operations only

## Quality Assurance

### Testing Requirements
- Minimum 80% code coverage for business logic
- All ViewModels must have unit tests
- All database operations must have instrumented tests
- Critical user flows require end-to-end tests
- Performance regression tests for database operations

### Release Criteria
- All tests passing (unit, instrumented, lint)
- ProGuard/R8 configuration verified
- APK size < 10MB (without assets)
- Accessibility scanner passing
- Privacy policy and data handling documented

## Governance

This constitution supersedes all development practices. Amendments require:
- Documentation of the proposed change and rationale
- Impact analysis on existing features
- Migration plan for breaking changes
- Approval from project maintainer

All code reviews must verify compliance with these principles. Violations require explicit justification in code comments and PR description. Use CLAUDE.md for runtime development guidance.

**Version**: 1.0.0 | **Ratified**: 2025-09-23 | **Last Amended**: 2025-09-23
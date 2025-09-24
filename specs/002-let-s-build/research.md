# Research Findings: IBS Food & Symptom Tracking Application

## Voice Input Implementation

**Decision**: Android SpeechRecognizer API with voice-to-text only
**Rationale**:
- Native Android API, no external dependencies
- Works offline with downloaded language packs
- Simple text transcription sufficient for food/symptom entry
- Full voice commands add unnecessary complexity
**Alternatives considered**:
- Google Assistant integration (requires online, privacy concerns)
- Custom voice commands (too complex for MVP)

## Room Database Encryption

**Decision**: SQLCipher for Android
**Rationale**:
- Industry standard for SQLite encryption
- Transparent encryption/decryption
- Minimal performance impact
- Works seamlessly with Room
**Alternatives considered**:
- Android Keystore encryption (more complex, field-level only)
- Custom encryption (security risks, maintenance burden)

## FODMAP Database Integration

**Decision**: Embedded JSON resource with FODMAP categorization
**Rationale**:
- Monash University FODMAP data structure as reference
- Offline-first requirement met
- Easy updates via app releases
- Categories: High/Medium/Low per FODMAP type
**Alternatives considered**:
- External API (violates offline-first)
- User-maintained list (accuracy concerns)

## Bristol Stool Chart Implementation

**Decision**: Vector drawables with Material Design cards
**Rationale**:
- Medical-standard 7-type classification
- Visual selection with descriptions
- Scalable vector graphics for all screen sizes
- Accessibility-friendly with content descriptions
**Alternatives considered**:
- External images (larger APK)
- Text-only (less intuitive for users)

## PDF Generation Library

**Decision**: Android Print Framework with custom PrintDocumentAdapter
**Rationale**:
- Native Android solution, no external dependencies
- Generates PDF directly from Views
- Supports charts and tables
- Works offline
**Alternatives considered**:
- iText (licensing concerns, large size)
- Apache PDFBox (heavy dependency)

## Statistical Analysis Algorithms

**Decision**: Custom implementation of key algorithms
**Rationale**:
- Pearson correlation for food-symptom relationships
- Moving averages for trend detection
- Time-window analysis (2-48 hours post-consumption)
- Frequency analysis for pattern detection
**Algorithms**:
- Correlation coefficient calculation
- Sliding window analysis
- Frequency distribution analysis
- Time-lag correlation
**Alternatives considered**:
- ML Kit (overkill, requires Google Play Services)
- Apache Commons Math (large dependency)

## Material Design 3 Quick Entry

**Decision**: ExtendedFloatingActionButton with speed dial pattern
**Rationale**:
- Material Design 3 standard pattern
- Expandable FAB for multiple quick actions
- User-configurable action items
- Prominent placement for frequent use
**Alternatives considered**:
- Bottom app bar (less prominent)
- Custom widget (non-standard UX)

## Reminder Implementation

**Decision**: AlarmManager with NotificationManager
**Rationale**:
- Works reliably even when app is closed
- Battery-efficient for scheduled reminders
- Supports exact timing for meal reminders
- User-configurable schedules
**Alternatives considered**:
- WorkManager (less precise timing)
- Firebase Cloud Messaging (requires network)

## Data Export Format

**Decision**: Structured JSON with medical terminology
**Schema**:
```json
{
  "version": "1.0",
  "exportDate": "ISO-8601",
  "patient": {
    "entries": [],
    "symptoms": [],
    "correlations": [],
    "patterns": []
  }
}
```
**Rationale**:
- Machine-readable for integration
- Human-readable structure
- Extensible for future features
- Includes medical coding references

## Chart Visualization

**Decision**: MPAndroidChart library
**Rationale**:
- Mature, well-maintained library
- Supports all required chart types
- Material Design compatible
- Smooth animations
- Accessibility support
**Chart types**:
- Line charts for symptom trends
- Bar charts for food frequency
- Scatter plots for correlations
- Pie charts for category breakdowns

## Background Processing

**Decision**: Kotlin Coroutines with lifecycle-aware components
**Rationale**:
- Native Kotlin solution
- Automatic cancellation on lifecycle changes
- Structured concurrency
- Efficient thread management
**Use cases**:
- Database operations
- Statistical calculations
- Report generation
- Export operations

## Testing Strategy

**Decision**: Layered testing approach
**Unit Tests**:
- JUnit 5 with Mockito
- ViewModels, UseCases, Repositories
- Statistical algorithms
**Instrumented Tests**:
- Espresso for UI
- Room database tests
- Migration tests
**Integration Tests**:
- Full user flows
- Data persistence verification
- Export/import validation

## Performance Optimizations

**Decision**: Proactive optimization strategies
**Database**:
- Indexed columns for frequent queries
- Pagination for large datasets
- Batch inserts for imports
**UI**:
- RecyclerView for lists
- ViewBinding for efficient view access
- Lazy loading for charts
**Memory**:
- Lifecycle-aware data loading
- Image compression for photos
- Efficient data structures

## Accessibility Considerations

**Decision**: Full accessibility support
**Implementation**:
- Content descriptions for all UI elements
- TalkBack compatibility
- Keyboard navigation support
- High contrast mode support
- Font scaling support
**Testing**:
- Accessibility Scanner validation
- Manual TalkBack testing

---
*Research completed: 2025-09-24*
*All technical decisions align with constitution requirements*
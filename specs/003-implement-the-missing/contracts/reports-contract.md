# Reports Generation Contract

## Purpose
Generate exportable reports summarizing food-symptom patterns for healthcare providers.

## UI Contract

### Data Requirements
```kotlin
data class ReportsState(
    val reportType: ReportType = ReportType.MONTHLY,
    val dateRange: DateRange = DateRange.lastMonth(),
    val template: ReportTemplate = ReportTemplate.BASIC,
    val exportFormat: ExportFormat = ExportFormat.PDF,
    val isGenerating: Boolean = false,
    val generatedReport: GeneratedReport? = null,
    val error: String? = null,
    val hasInsufficientData: Boolean = false
)

data class GeneratedReport(
    val filePath: String,
    val fileName: String,
    val size: Long,
    val generatedAt: Instant,
    val reportSummary: ReportSummary
)

data class ReportSummary(
    val totalEntries: Int,
    val totalSymptoms: Int,
    val dateRange: DateRange,
    val topTriggers: List<String>,
    val primarySymptoms: List<SymptomType>
)

data class DateRange(
    val startDate: LocalDate,
    val endDate: LocalDate
)

enum class ReportType {
    WEEKLY, MONTHLY, QUARTERLY, CUSTOM
}

enum class ReportTemplate {
    BASIC,      // Essential correlations only
    DETAILED,   // Full analysis with charts
    MEDICAL     // Healthcare provider format
}

enum class ExportFormat {
    PDF, CSV, JSON
}
```

### User Actions
```kotlin
interface ReportsActions {
    fun updateReportType(reportType: ReportType)
    fun setCustomDateRange(start: LocalDate, end: LocalDate)
    fun updateTemplate(template: ReportTemplate)
    fun updateExportFormat(format: ExportFormat)
    fun generateReport()
    fun previewReport()
    fun shareReport()
    fun deleteReport(reportId: String)
    fun viewReportHistory()
}
```

### Expected Behaviors
- Report configuration form with options
- Preview functionality before generation
- Progress indicator during generation
- Success feedback with file details
- Share integration (email, file sharing)
- Report history with management options
- Handle insufficient data per FR-006 requirements

## Report Content Structure

### Basic Template
```kotlin
data class BasicReportContent(
    val executiveSummary: ExecutiveSummary,
    val topCorrelations: List<CorrelationSummary>,
    val symptomFrequency: Map<SymptomType, Int>,
    val recommendationsNote: String
)
```

### Detailed Template
```kotlin
data class DetailedReportContent(
    val executiveSummary: ExecutiveSummary,
    val correlationAnalysis: CorrelationAnalysis,
    val symptomTrendCharts: List<ChartData>,
    val timelineHighlights: List<TimelineEvent>,
    val statisticalSummary: StatisticalSummary
)
```

### Medical Template
```kotlin
data class MedicalReportContent(
    val patientSummary: PatientSummary,
    val clinicalCorrelations: List<ClinicalCorrelation>,
    val symptomSeverityAnalysis: SeverityAnalysis,
    val recommendedTests: List<String>,
    val dataReliabilityMetrics: ReliabilityMetrics
)
```

## Generation Rules
```kotlin
data class ReportGenerationRules(
    val minimumDays: Int = 7,
    val minimumEntries: Int = 5,
    val maxReportSize: Long = 10_000_000, // 10MB
    val correlationThreshold: Float = 0.3f,
    val includeRawData: Boolean = false // Privacy protection
)
```

- Minimum 7 days of data required
- At least 5 total entries needed
- Report files limited to 10MB
- Only significant correlations included
- Raw sensitive data excluded by default
- Generic placeholders for insufficient sections

## Insufficient Data Handling (FR-006)
When data is below thresholds:
- Generate report with available data
- Add disclaimer about limited data
- Include generic error messages for missing sections:
  - "Insufficient data for trend analysis"
  - "More entries needed for correlation confidence"
  - "Extend logging period for comprehensive insights"
- Provide guidance on improving data collection

## Export Formats

### PDF Format
- Professional healthcare-ready layout
- Charts and visualizations embedded
- Printer-friendly formatting
- Digital signature/timestamp

### CSV Format
- Raw correlation data
- Timeline entries
- Statistical summaries
- Machine-readable for further analysis

### JSON Format
- Complete structured data
- Preserves all metadata
- API-compatible format
- Technical analysis friendly

## Test Scenarios
1. **Complete Report Generation**: Sufficient data, all templates
2. **Insufficient Data**: Generate with placeholders and warnings
3. **Format Variations**: Test PDF, CSV, JSON outputs
4. **Large Datasets**: Performance with maximum retention data
5. **Custom Date Ranges**: Validate date range selection
6. **Template Switching**: Different content for each template
7. **Export Sharing**: Share functionality works correctly
8. **File Management**: Save, delete, view history operations
9. **Error Handling**: Generation failures, storage limits
10. **Privacy Compliance**: Verify sensitive data exclusion
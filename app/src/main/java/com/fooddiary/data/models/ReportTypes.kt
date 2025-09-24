package com.fooddiary.data.models

enum class ReportFormat {
    PDF,
    JSON,
    ;

    val displayName: String
        get() = when (this) {
            PDF -> "PDF (Healthcare Provider)"
            JSON -> "JSON (Data Export)"
        }

    val fileExtension: String
        get() = when (this) {
            PDF -> ".pdf"
            JSON -> ".json"
        }

    val mimeType: String
        get() = when (this) {
            PDF -> "application/pdf"
            JSON -> "application/json"
        }
}

enum class ReportSection {
    EXECUTIVE_SUMMARY,
    PATIENT_SUMMARY,
    FOOD_DIARY,
    SYMPTOM_SUMMARY,
    SYMPTOM_TIMELINE,
    TRIGGER_PATTERNS,
    STATISTICAL_ANALYSIS,
    FODMAP_ANALYSIS,
    ELIMINATION_PROTOCOL,
    ENVIRONMENTAL_FACTORS,
    RECOMMENDATIONS,
    DAILY_TIMELINE,
    WEEKLY_TRENDS,
    CORRELATION_MATRIX,
    ;

    val displayName: String
        get() = when (this) {
            EXECUTIVE_SUMMARY -> "Executive Summary"
            PATIENT_SUMMARY -> "Patient Summary"
            FOOD_DIARY -> "Food Diary"
            SYMPTOM_SUMMARY -> "Symptom Summary"
            SYMPTOM_TIMELINE -> "Symptom Timeline"
            TRIGGER_PATTERNS -> "Trigger Patterns"
            STATISTICAL_ANALYSIS -> "Statistical Analysis"
            FODMAP_ANALYSIS -> "FODMAP Analysis"
            ELIMINATION_PROTOCOL -> "Elimination Protocol Results"
            ENVIRONMENTAL_FACTORS -> "Environmental Factors"
            RECOMMENDATIONS -> "Clinical Recommendations"
            DAILY_TIMELINE -> "Daily Timeline"
            WEEKLY_TRENDS -> "Weekly Trends"
            CORRELATION_MATRIX -> "Correlation Matrix"
        }

    val description: String
        get() = when (this) {
            EXECUTIVE_SUMMARY -> "High-level overview of findings and key insights"
            PATIENT_SUMMARY -> "Patient demographics and tracking period overview"
            FOOD_DIARY -> "Complete log of food and beverage consumption"
            SYMPTOM_SUMMARY -> "Summary of all recorded symptoms and frequencies"
            SYMPTOM_TIMELINE -> "Chronological timeline of symptom occurrences"
            TRIGGER_PATTERNS -> "Identified food-symptom correlations with confidence levels"
            STATISTICAL_ANALYSIS -> "Statistical significance testing and correlation analysis"
            FODMAP_ANALYSIS -> "FODMAP categorization of consumed foods"
            ELIMINATION_PROTOCOL -> "Results from elimination diet protocols"
            ENVIRONMENTAL_FACTORS -> "Impact of stress, sleep, exercise on symptoms"
            RECOMMENDATIONS -> "Clinical recommendations based on analysis"
            DAILY_TIMELINE -> "Day-by-day breakdown of entries and symptoms"
            WEEKLY_TRENDS -> "Weekly patterns and trend analysis"
            CORRELATION_MATRIX -> "Comprehensive correlation matrix of all factors"
        }

    val isMedicallyRelevant: Boolean
        get() = when (this) {
            EXECUTIVE_SUMMARY, PATIENT_SUMMARY, SYMPTOM_SUMMARY,
            TRIGGER_PATTERNS, STATISTICAL_ANALYSIS, FODMAP_ANALYSIS,
            ELIMINATION_PROTOCOL, RECOMMENDATIONS,
            -> true
            else -> false
        }
}

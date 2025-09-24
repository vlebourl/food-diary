# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an Android application for tracking food habits and digestive symptoms to help diagnose potential IBS (Irritable Bowel Syndrome). The app allows users to log meals, track symptoms, and identify patterns between food intake and digestive issues.

## Development Commands

### Build & Run
- `./gradlew build` - Build the entire project
- `./gradlew assembleDebug` - Build debug APK
- `./gradlew assembleRelease` - Build release APK
- `./gradlew installDebug` - Install debug build on connected device
- `./gradlew clean` - Clean build artifacts

### Testing
- `./gradlew test` - Run unit tests
- `./gradlew connectedAndroidTest` - Run instrumented tests on device/emulator
- `./gradlew testDebugUnitTest` - Run debug unit tests only
- `./gradlew lint` - Run Android lint checks

### Code Quality
- `./gradlew ktlintCheck` - Check Kotlin code style
- `./gradlew ktlintFormat` - Format Kotlin code
- `./gradlew detekt` - Run static code analysis

## Architecture

### Core Features
1. **Food Entry System**: Log meals with timestamp, ingredients, portion sizes
2. **Symptom Tracking**: Record digestive symptoms with severity and timing
3. **Data Correlation**: Analyze patterns between food intake and symptoms
4. **Reports & Insights**: Generate reports to share with healthcare providers

### Data Layer
- Room database for local storage of food entries and symptoms
- Repository pattern for data access abstraction
- ViewModel for UI state management

### Key Components
- `MainActivity`: Entry point and navigation host
- `FoodEntryFragment`: Food logging interface
- `SymptomTrackerFragment`: Symptom recording interface
- `AnalyticsFragment`: Pattern analysis and insights
- `Database`: Room entities for Food, Symptom, and correlations

### Dependencies
- Room for local database
- Navigation Component for app navigation
- Material Design 3 for UI components
- Kotlin Coroutines for async operations
- ViewModel and LiveData for MVVM architecture

## Database Schema

### Food Entry
- id, timestamp, meal_type, foods, portions, notes

### Symptom
- id, timestamp, type, severity (1-10), duration, trigger_food_id (nullable)

### Food-Symptom Correlation
- food_id, symptom_id, correlation_strength, time_offset

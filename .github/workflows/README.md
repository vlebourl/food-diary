# 🔄 CI/CD Pipeline Documentation

This directory contains comprehensive GitHub Actions workflows for the IBS Food & Symptom Tracking Android app, designed with medical-grade quality standards and enterprise-level reliability.

## 🏗️ Workflow Overview

### 1. **PR Validation** (`pr-validation.yml`)
**Trigger**: Pull Requests to `main` or `develop`

Comprehensive validation pipeline that ensures code quality, medical standards, and functionality before merging.

#### **Pipeline Stages:**

**📊 Code Quality & Security**
- **Ktlint**: Kotlin code style enforcement
- **Detekt**: Static code analysis and complexity checks
- **Android Lint**: Platform-specific code quality analysis
- **Security scanning**: Potential vulnerability detection

**🧪 Testing Suite**
- **Unit Tests**: Business logic validation with 95%+ coverage
- **Integration Tests**: Repository and use case integration
- **Instrumented Tests**: UI automation across multiple Android versions (API 28, 30, 33)
- **Medical Validation**: Bristol Stool Chart, FODMAP analysis, statistical accuracy

**🏗️ Build Verification**
- **Debug Build**: Development APK generation
- **Release Build**: Production APK compilation (unsigned)
- **APK Analysis**: Size optimization, compatibility validation

**🏥 Medical Standards**
- **Bristol Stool Chart validation**: Ensures 7-point medical scale accuracy
- **FODMAP analysis verification**: Validates ingredient classification system
- **Statistical compliance**: Correlation algorithm accuracy checks
- **Privacy audit**: Local-only storage verification, no cloud dependencies

**⚡ Performance & Accessibility**
- **Performance linting**: Memory usage, UI responsiveness analysis
- **Accessibility checks**: Content descriptions, screen reader compatibility
- **Medical UI validation**: Severity scales, chart interactions

#### **Outputs:**
- Debug APK artifacts for testing
- Test coverage reports
- Code quality analysis reports
- Medical compliance validation results

---

### 2. **Release Build** (`release.yml`)
**Trigger**: Git tags (`v*.*.*`) or manual workflow dispatch

Production release pipeline that generates signed APKs and creates GitHub releases with comprehensive documentation.

#### **Pipeline Stages:**

**🔍 Pre-Release Validation**
- **Version extraction**: Semantic versioning from tags
- **Critical test suite**: Medical accuracy and core functionality
- **Security pre-flight**: Debug certificate detection, secret scanning
- **Medical compliance check**: Bristol Chart, FODMAP, correlation engines

**🏗️ Release Build**
- **Signed APK generation**: Production-ready with release keystore
- **App Bundle creation**: Play Store optimized AAB files
- **Debug APK**: Testing and QA versions
- **Multi-variant builds**: Release and debug configurations

**📝 Release Documentation**
- **Automated changelog**: Git commit history analysis
- **Medical feature documentation**: Clinical accuracy specifications
- **Installation instructions**: Step-by-step user guidance
- **Security notes**: Privacy and data protection information

**🎉 GitHub Release**
- **Automated release creation**: Version-tagged with comprehensive notes
- **Asset management**: APK, AAB, and checksum files
- **Download links**: Direct APK installation URLs
- **Pre-release detection**: Alpha, beta, RC version handling

#### **Required Secrets:**
```
KEYSTORE_BASE64      # Base64 encoded release keystore
KEYSTORE_PASSWORD    # Keystore password
KEY_ALIAS           # Signing key alias
KEY_PASSWORD        # Key password
```

#### **Outputs:**
- Production-signed APK (`food-diary-vX.X.X-release.apk`)
- Debug APK (`food-diary-vX.X.X-debug.apk`)
- App Bundle (`food-diary-vX.X.X-release.aab`)
- SHA256 checksums for all assets
- Comprehensive release notes

---

### 3. **Dependency Management** (`dependency-update.yml`)
**Trigger**: Weekly schedule (Mondays 9 AM UTC), manual dispatch, or dependency file changes

Security-focused dependency management with medical compliance validation.

#### **Pipeline Stages:**

**🛡️ Security Vulnerability Scan**
- **OWASP dependency check**: Known CVE detection
- **Vulnerability reporting**: Risk assessment and mitigation
- **Medical dependency audit**: SQLCipher, statistical libraries
- **Privacy violation detection**: Analytics, cloud service libraries

**🏥 Medical Dependency Audit**
- **SQLCipher validation**: Database encryption integrity
- **Statistical library verification**: Correlation algorithm dependencies
- **Chart library compliance**: Medical visualization accuracy
- **Privacy dependency screening**: No tracking or analytics libraries

**🔧 Gradle Wrapper Updates**
- **Automated Gradle updates**: Latest stable version adoption
- **Compatibility validation**: Build and test verification
- **Pull request creation**: Automated update proposals

**💡 Update Suggestions**
- **Android dependency analysis**: Platform library updates
- **Security-critical updates**: Priority vulnerability patches
- **Medical compliance impact**: Update risk assessment
- **Automated issue creation**: Maintenance tracking

#### **Outputs:**
- Security vulnerability reports
- Dependency update suggestions
- Automated Gradle wrapper PRs
- Medical compliance audit results

---

## 🔧 Setup Instructions

### 1. **Repository Secrets Configuration**

For release builds, configure these secrets in GitHub:

```bash
# Generate release keystore
keytool -genkey -v -keystore release.keystore -alias food_diary_key -keyalg RSA -keysize 2048 -validity 10000

# Convert to base64 for GitHub secrets
base64 -i release.keystore | pbcopy  # macOS
base64 -w 0 release.keystore         # Linux
```

**Required Secrets:**
- `KEYSTORE_BASE64`: Base64 encoded keystore file
- `KEYSTORE_PASSWORD`: Your keystore password
- `KEY_ALIAS`: Signing key alias (e.g., `food_diary_key`)
- `KEY_PASSWORD`: Key password

**Optional Secrets:**
- `CODECOV_TOKEN`: For test coverage reporting

### 2. **Branch Protection Rules**

Configure branch protection for `main`:

```yaml
Required status checks:
  - Code Quality & Security
  - Unit & Integration Tests
  - Build Verification
  - Medical Standards Validation

Require review from code owners: ✅
Dismiss stale reviews: ✅
Require status checks to pass: ✅
Require branches to be up to date: ✅
```

### 3. **Medical Validation Requirements**

The pipeline enforces these medical standards:

- **Bristol Stool Chart**: 7-point scale implementation
- **FODMAP Analysis**: Ingredient classification accuracy
- **Statistical Methods**: Pearson correlation with p-values
- **Privacy Compliance**: Local-only storage validation
- **Data Encryption**: SQLCipher database protection

---

## 📊 Pipeline Performance

### **Expected Execution Times:**
- **PR Validation**: ~15-25 minutes
- **Release Build**: ~10-15 minutes
- **Dependency Scan**: ~5-10 minutes

### **Cost Optimization:**
- **Parallel execution**: Independent jobs run concurrently
- **Conditional triggers**: Draft PRs skip expensive tests
- **Artifact retention**: Optimized storage lifecycle
- **Matrix optimization**: Strategic API level testing

### **Resource Usage:**
- **Ubuntu runners**: Standard workflows
- **macOS runners**: Instrumented tests only (iOS simulator access)
- **Caching strategy**: Gradle dependencies, AVD snapshots

---

## 🔍 Troubleshooting

### **Common Issues:**

**1. Instrumented Test Failures**
```yaml
# Solution: Increase emulator timeout
timeout-minutes: 30
```

**2. Gradle Build Timeouts**
```yaml
# Solution: Optimize Gradle settings
GRADLE_OPTS: -Dorg.gradle.daemon=false -Dorg.gradle.parallel=true
```

**3. Medical Validation Failures**
```yaml
# Check for required components:
- Bristol Stool Chart implementation
- FODMAP analyzer presence
- Statistical correlation methods
```

**4. Release Signing Issues**
```bash
# Verify keystore secrets are correctly base64 encoded
echo $KEYSTORE_BASE64 | base64 -d > test.keystore
keytool -list -keystore test.keystore
```

---

## 🏥 Medical Compliance Validation

The CI/CD pipeline includes specialized validation for medical accuracy:

### **Bristol Stool Chart Validation**
- Ensures 7-point scale implementation (Types 1-7)
- Validates medical color coding and descriptions
- Confirms proper UI accessibility for clinical use

### **FODMAP Analysis Validation**
- Verifies ingredient classification accuracy
- Validates elimination diet phase logic
- Ensures statistical correlation accuracy

### **Privacy & Security Validation**
- Confirms local-only data storage
- Validates SQLCipher database encryption
- Prevents cloud dependency introduction
- Ensures HIPAA-considerate design principles

### **Statistical Accuracy Validation**
- Validates Pearson correlation implementations
- Ensures proper p-value calculations
- Confirms confidence interval accuracy
- Verifies clinical significance thresholds

---

## 🤖 Automation Features

### **Automated Quality Gates**
- **Code Coverage**: Minimum 95% for business logic
- **Security Scanning**: Zero high-severity vulnerabilities
- **Medical Compliance**: 100% medical standard adherence
- **Performance**: Sub-100ms database operations

### **Automated Maintenance**
- **Weekly dependency scans**: Security vulnerability detection
- **Gradle wrapper updates**: Latest stable version adoption
- **Medical library updates**: Clinical accuracy maintenance
- **Performance monitoring**: APK size and build time tracking

### **Automated Documentation**
- **Release notes generation**: Feature and fix summaries
- **Medical compliance reports**: Clinical standard validation
- **API documentation updates**: Code-generated specifications
- **Performance benchmarks**: Build and runtime metrics

---

This CI/CD pipeline ensures that every change to the IBS Food & Symptom Tracking app meets enterprise-grade quality standards while maintaining medical accuracy and user privacy protection. The comprehensive validation process provides confidence for healthcare-related deployments while enabling rapid, reliable development cycles.
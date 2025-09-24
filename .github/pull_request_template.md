# 🔄 Pull Request

## 📋 Description
<!-- Provide a brief description of the changes in this PR -->

## 🎯 Type of Change
<!-- Mark the relevant option with an 'x' -->
- [ ] 🐛 Bug fix (non-breaking change that fixes an issue)
- [ ] ✨ New feature (non-breaking change that adds functionality)
- [ ] 💥 Breaking change (fix or feature that would cause existing functionality to not work as expected)
- [ ] 🏥 Medical accuracy improvement (ensures clinical standards)
- [ ] 🔒 Security/Privacy enhancement (improves data protection)
- [ ] 📚 Documentation update (README, comments, etc.)
- [ ] 🧪 Test improvement (adds or improves test coverage)
- [ ] ♿ Accessibility improvement (improves usability for all users)
- [ ] ⚡ Performance optimization (improves app speed or efficiency)

## 🏥 Medical Impact Assessment
<!-- If this PR affects medical functionality, please complete this section -->

### Medical Components Affected:
<!-- Mark all that apply -->
- [ ] Bristol Stool Chart (7-point medical scale)
- [ ] FODMAP Analysis (ingredient classification)
- [ ] Statistical Correlation (Pearson correlation, p-values)
- [ ] Symptom Severity Scales (1-10 rating system)
- [ ] Data Export (medical reports for healthcare providers)
- [ ] Database Schema (health data storage)
- [ ] None - This is a non-medical change

### Clinical Accuracy Validation:
<!-- For medical changes, confirm the following -->
- [ ] Medical terminology follows clinical standards
- [ ] Numerical scales match established medical practices
- [ ] Statistical calculations are clinically appropriate
- [ ] Data integrity is maintained for health information
- [ ] No personal health information (PHI) is exposed in logs or exports

## 🔒 Privacy & Security Checklist
<!-- Confirm privacy and security standards are maintained -->
- [ ] No cloud services or external data sharing introduced
- [ ] Local-only storage principle maintained
- [ ] Database encryption (SQLCipher) remains intact
- [ ] No hardcoded secrets or credentials
- [ ] User consent maintained for data usage
- [ ] HIPAA-considerate design principles followed

## 🧪 Testing
<!-- Describe the tests you ran to verify your changes -->

### Test Coverage:
- [ ] Unit tests added/updated for new functionality
- [ ] Integration tests cover data flow
- [ ] Medical accuracy tests validate clinical components
- [ ] UI tests ensure accessibility and usability
- [ ] Manual testing on physical devices completed

### Test Results:
<!-- Provide test results or screenshots -->
```
# Example test output
./gradlew test
BUILD SUCCESSFUL
Tests: X passed, Y failed, Z skipped
```

## 📱 Device Testing
<!-- Confirm testing across different devices and Android versions -->
- [ ] Tested on Android 7.0 (API 24) - minimum supported
- [ ] Tested on Android 14 (API 34) - target version
- [ ] Tested on small screen devices (< 5.5")
- [ ] Tested on large screen devices (> 6.5")
- [ ] Tested with accessibility services enabled (TalkBack, etc.)

## 📊 Performance Impact
<!-- Assess performance implications -->
- [ ] No significant increase in APK size
- [ ] Database queries remain optimized
- [ ] Memory usage is within acceptable limits
- [ ] UI remains responsive during data processing
- [ ] Battery usage impact is minimal

## 🔗 Related Issues
<!-- Link related issues using keywords -->
Closes #XXX
Fixes #XXX
References #XXX

## 📸 Screenshots/Videos
<!-- If UI changes, provide before/after screenshots -->
### Before:
<!-- Screenshots of UI before changes -->

### After:
<!-- Screenshots of UI after changes -->

## 🚀 Deployment Notes
<!-- Any special instructions for deployment -->
- [ ] Requires database migration
- [ ] Requires new permissions
- [ ] Requires updated dependencies
- [ ] Requires configuration changes
- [ ] No special deployment requirements

## 📝 Documentation Updates
<!-- Confirm documentation is updated -->
- [ ] README.md updated (if applicable)
- [ ] CLAUDE.md updated with new commands (if applicable)
- [ ] Code comments added for complex medical logic
- [ ] API documentation updated (if applicable)
- [ ] Medical accuracy notes documented

## ✅ Review Checklist
<!-- For reviewers to confirm -->
### Code Quality:
- [ ] Code follows established patterns and conventions
- [ ] Medical components maintain clinical accuracy
- [ ] Error handling is comprehensive and appropriate
- [ ] Performance impact is acceptable
- [ ] Security best practices are followed

### Medical Review (if applicable):
- [ ] Bristol Stool Chart implementation is medically accurate
- [ ] FODMAP classifications follow established guidelines
- [ ] Statistical calculations are clinically appropriate
- [ ] Medical terminology is correct and consistent
- [ ] Healthcare provider export format meets standards

### Privacy Review:
- [ ] No data leaves the device unexpectedly
- [ ] User consent is properly obtained and documented
- [ ] Database encryption is maintained
- [ ] No tracking or analytics added without explicit consent
- [ ] Personal health information handling is appropriate

---

## 📋 Pre-Merge Requirements
<!-- All items must be checked before merging -->
- [ ] All CI/CD checks pass
- [ ] Code review approved by CODEOWNERS
- [ ] Medical accuracy validated (if applicable)
- [ ] Privacy and security review completed
- [ ] Documentation updated
- [ ] Breaking changes communicated

---

**🏥 Medical Accuracy Declaration**: I confirm that any changes affecting medical functionality maintain clinical accuracy and follow established healthcare standards.

**🔒 Privacy Declaration**: I confirm that these changes maintain the app's commitment to local-only data storage and user privacy protection.

🤖 Generated with [Claude Code](https://claude.ai/code)
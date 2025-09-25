# detekt

## Metrics

* 142 number of properties

* 212 number of functions

* 173 number of classes

* 9 number of packages

* 32 number of kt files

## Complexity Report

* 3,327 lines of code (loc)

* 2,794 source lines of code (sloc)

* 2,256 logical lines of code (lloc)

* 146 comment lines of code (cloc)

* 498 cyclomatic complexity (mcc)

* 110 cognitive complexity

* 107 number of total code smells

* 5% comment source ratio

* 220 mcc per 1,000 lloc

* 47 code smells per 1,000 lloc

## Findings (107)

### complexity, CyclomaticComplexMethod (3)

Prefer splitting up complex methods into smaller, easier to test methods.

[Documentation](https://detekt.dev/docs/rules/complexity#cyclomaticcomplexmethod)

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:242:9
```
The function parseFoodFromText appears to be too complex based on Cyclomatic Complexity (complexity: 16). Defined complexity threshold for methods is set to '15'
```
```kotlin
239     }
240 
241     // Text processing methods
242     fun parseFoodFromText(text: String): ParsedFoodEntry {
!!!         ^ error
243         val lowercaseText = text.lowercase(Locale.getDefault())
244 
245         // Extract food items

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:300:9
```
The function parseSymptomFromText appears to be too complex based on Cyclomatic Complexity (complexity: 18). Defined complexity threshold for methods is set to '15'
```
```kotlin
297         )
298     }
299 
300     fun parseSymptomFromText(text: String): ParsedSymptomEntry {
!!!         ^ error
301         val lowercaseText = text.lowercase(Locale.getDefault())
302 
303         // Extract severity

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:370:17
```
The function estimateSeverityFromText appears to be too complex based on Cyclomatic Complexity (complexity: 16). Defined complexity threshold for methods is set to '15'
```
```kotlin
367         }
368     }
369 
370     private fun estimateSeverityFromText(text: String): Int {
!!!                 ^ error
371         return when {
372             text.contains("terrible") || text.contains("excruciating") || text.contains("unbearable") -> 9
373             text.contains("severe") || text.contains("intense") || text.contains("very bad") -> 7

```

### complexity, LongParameterList (15)

The more parameters a function has the more complex it is. Long parameter lists are often used to control complex algorithms and violate the Single Responsibility Principle. Prefer functions with short parameter lists.

[Documentation](https://detekt.dev/docs/rules/complexity#longparameterlist)

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/BeverageEntry.kt:32:19
```
The function create(name: String, type: BeverageType, volume: Float, volumeUnit: VolumeUnit, timestamp: Instant, timezone: String, caffeineContent: Float?, alcoholContent: Float?, carbonation: Boolean, temperature: Temperature, notes: String?) has too many parameters. The current threshold is set to 6.
```
```kotlin
29     val deletedAt: Instant? = null,
30 ) {
31     companion object {
32         fun create(
!!                   ^ error
33             name: String,
34             type: BeverageType,
35             volume: Float,

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/BeverageEntry.kt:66:15
```
The function update(name: String?, type: BeverageType?, volume: Float?, volumeUnit: VolumeUnit?, caffeineContent: Float?, alcoholContent: Float?, carbonation: Boolean?, temperature: Temperature?, notes: String?) has too many parameters. The current threshold is set to 6.
```
```kotlin
63         modifiedAt = Instant.now(),
64     )
65 
66     fun update(
!!               ^ error
67         name: String? = null,
68         type: BeverageType? = null,
69         volume: Float? = null,

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/EnvironmentalContext.kt:44:19
```
The function create(date: LocalDate, stressLevel: Int, sleepHours: Float, sleepQuality: Int, exerciseMinutes: Int?, exerciseType: String?, exerciseIntensity: ExerciseIntensity?, menstrualPhase: MenstrualPhase?, weather: String?, location: String?, additionalNotes: String?) has too many parameters. The current threshold is set to 6.
```
```kotlin
41     }
42 
43     companion object {
44         fun create(
!!                   ^ error
45             date: LocalDate,
46             stressLevel: Int,
47             sleepHours: Float,

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/EnvironmentalContext.kt:71:15
```
The function update(stressLevel: Int?, sleepHours: Float?, sleepQuality: Int?, exerciseMinutes: Int?, exerciseType: String?, exerciseIntensity: ExerciseIntensity?, menstrualPhase: MenstrualPhase?, weather: String?, location: String?, additionalNotes: String?) has too many parameters. The current threshold is set to 6.
```
```kotlin
68         )
69     }
70 
71     fun update(
!!               ^ error
72         stressLevel: Int? = null,
73         sleepHours: Float? = null,
74         sleepQuality: Int? = null,

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/FoodEntry.kt:37:19
```
The function create(name: String, ingredients: List<String>, portions: Float, portionUnit: String, mealType: MealType, context: ConsumptionContext, timestamp: Instant, timezone: String, preparationMethod: String?, notes: String?) has too many parameters. The current threshold is set to 6.
```
```kotlin
34     val deletedAt: Instant? = null,
35 ) {
36     companion object {
37         fun create(
!!                   ^ error
38             name: String,
39             ingredients: List<String>,
40             portions: Float,

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/FoodEntry.kt:69:15
```
The function update(name: String?, ingredients: List<String>?, portions: Float?, portionUnit: String?, preparationMethod: String?, mealType: MealType?, context: ConsumptionContext?, notes: String?) has too many parameters. The current threshold is set to 6.
```
```kotlin
66         modifiedAt = Instant.now(),
67     )
68 
69     fun update(
!!               ^ error
70         name: String? = null,
71         ingredients: List<String>? = null,
72         portions: Float? = null,

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/MedicalReport.kt:33:19
```
The function create(title: String, startDate: LocalDate, endDate: LocalDate, format: ReportFormat, sections: List<ReportSection>, notes: String?) has too many parameters. The current threshold is set to 6.
```
```kotlin
30     val notes: String?,
31 ) {
32     companion object {
33         fun create(
!!                   ^ error
34             title: String,
35             startDate: LocalDate,
36             endDate: LocalDate,

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/QuickEntryTemplate.kt:24:23
```
The function createFood(name: String, defaultFoodName: String, defaultPortion: String, defaultUnit: String, buttonColor: String, buttonIcon: String, sortOrder: Int, ingredients: List<String>, mealType: String) has too many parameters. The current threshold is set to 6.
```
```kotlin
21     val modifiedAt: Long? = null,
22 ) {
23     companion object {
24         fun createFood(
!!                       ^ error
25             name: String,
26             defaultFoodName: String,
27             defaultPortion: String,

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/QuickEntryTemplate.kt:49:27
```
The function createBeverage(name: String, defaultBeverageName: String, defaultVolume: String, defaultUnit: String, caffeineContent: String?, buttonColor: String, buttonIcon: String, sortOrder: Int) has too many parameters. The current threshold is set to 6.
```
```kotlin
46             sortOrder = sortOrder,
47         )
48 
49         fun createBeverage(
!!                           ^ error
50             name: String,
51             defaultBeverageName: String,
52             defaultVolume: String,

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/QuickEntryTemplate.kt:72:26
```
The function createSymptom(name: String, defaultSymptomType: String, defaultSeverity: String, buttonColor: String, buttonIcon: String, sortOrder: Int) has too many parameters. The current threshold is set to 6.
```
```kotlin
69             sortOrder = sortOrder,
70         )
71 
72         fun createSymptom(
!!                          ^ error
73             name: String,
74             defaultSymptomType: String,
75             defaultSeverity: String,

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/QuickEntryTemplate.kt:92:15
```
The function update(name: String?, defaultData: Map<String, String>?, buttonColor: String?, buttonIcon: String?, isActive: Boolean?, sortOrder: Int?) has too many parameters. The current threshold is set to 6.
```
```kotlin
89         )
90     }
91 
92     fun update(
!!               ^ error
93         name: String? = null,
94         defaultData: Map<String, String>? = null,
95         buttonColor: String? = null,

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/SymptomEvent.kt:51:19
```
The function create(type: SymptomType, severity: Int, timestamp: Instant, timezone: String, duration: Int?, location: String?, bristolScale: Int?, suspectedTriggers: List<String>?, notes: String?, photoPath: String?) has too many parameters. The current threshold is set to 6.
```
```kotlin
48     }
49 
50     companion object {
51         fun create(
!!                   ^ error
52             type: SymptomType,
53             severity: Int,
54             timestamp: Instant = Instant.now(),

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/SymptomEvent.kt:83:15
```
The function update(type: SymptomType?, severity: Int?, duration: Int?, location: String?, bristolScale: Int?, suspectedTriggers: List<String>?, notes: String?, photoPath: String?) has too many parameters. The current threshold is set to 6.
```
```kotlin
80         modifiedAt = Instant.now(),
81     )
82 
83     fun update(
!!               ^ error
84         type: SymptomType? = null,
85         severity: Int? = null,
86         duration: Int? = null,

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/TriggerPattern.kt:47:19
```
The function create(foodName: String, symptomType: SymptomType, correlationStrength: Float, averageTimeOffsetMinutes: Int, occurrences: Int, confidence: Float, pValue: Float?, standardDeviation: Float?, minTimeOffset: Int?, maxTimeOffset: Int?) has too many parameters. The current threshold is set to 6.
```
```kotlin
44     }
45 
46     companion object {
47         fun create(
!!                   ^ error
48             foodName: String,
49             symptomType: SymptomType,
50             correlationStrength: Float,

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/TriggerPattern.kt:92:15
```
The function update(correlationStrength: Float?, averageTimeOffsetMinutes: Int?, occurrences: Int?, confidence: Float?, pValue: Float?, standardDeviation: Float?, minTimeOffset: Int?, maxTimeOffset: Int?) has too many parameters. The current threshold is set to 6.
```
```kotlin
89     val averageTimeOffsetHours: Float
90         get() = averageTimeOffsetMinutes / 60f
91 
92     fun update(
!!               ^ error
93         correlationStrength: Float? = null,
94         averageTimeOffsetMinutes: Int? = null,
95         occurrences: Int? = null,

```

### complexity, NestedBlockDepth (1)

Excessive nesting leads to hidden complexity. Prefer extracting code to make it easier to understand.

[Documentation](https://detekt.dev/docs/rules/complexity#nestedblockdepth)

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/TimezoneHandler.kt:124:9
```
Function validateTimezoneConsistency is nested too deeply.
```
```kotlin
121     /**
122      * Validates that a series of entries have consistent timezone progression
123      */
124     fun validateTimezoneConsistency(entries: List<TimezoneAwareEntry>): ConsistencyValidation {
!!!         ^ error
125         if (entries.isEmpty()) {
126             return ConsistencyValidation.Valid("No entries to validate")
127         }

```

### complexity, TooManyFunctions (10)

Too many functions inside a/an file/class/object/interface always indicate a violation of the single responsibility principle. Maybe the file/class/object/interface wants to manage too many things at once. Extract functionality which clearly belongs together.

[Documentation](https://detekt.dev/docs/rules/complexity#toomanyfunctions)

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/concurrency/ConcurrentAccessManager.kt:14:7
```
Class 'ConcurrentAccessManager' with '16' functions detected. Defined threshold inside classes is set to '11'
```
```kotlin
11 import kotlinx.coroutines.withTimeoutOrNull
12 
13 @Singleton
14 class ConcurrentAccessManager @Inject constructor() {
!!       ^ error
15 
16     companion object {
17         private const val DEFAULT_TIMEOUT_MS = 30_000L // 30 seconds

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/dao/BeverageEntryDao.kt:9:11
```
Interface 'BeverageEntryDao' with '13' functions detected. Defined threshold inside interfaces is set to '11'
```
```kotlin
6  import kotlinx.coroutines.flow.Flow
7  
8  @Dao
9  interface BeverageEntryDao {
!            ^ error
10 
11     @Insert
12     suspend fun insert(entry: BeverageEntry): Long

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/dao/EliminationProtocolDao.kt:9:11
```
Interface 'EliminationProtocolDao' with '11' functions detected. Defined threshold inside interfaces is set to '11'
```
```kotlin
6  import kotlinx.coroutines.flow.Flow
7  
8  @Dao
9  interface EliminationProtocolDao {
!            ^ error
10 
11     @Insert
12     suspend fun insert(protocol: EliminationProtocol): Long

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/dao/EnvironmentalContextDao.kt:9:11
```
Interface 'EnvironmentalContextDao' with '11' functions detected. Defined threshold inside interfaces is set to '11'
```
```kotlin
6  import kotlinx.coroutines.flow.Flow
7  
8  @Dao
9  interface EnvironmentalContextDao {
!            ^ error
10 
11     @Insert(onConflict = OnConflictStrategy.REPLACE)
12     suspend fun insertOrUpdate(context: EnvironmentalContext)

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/dao/FoodEntryDao.kt:9:11
```
Interface 'FoodEntryDao' with '17' functions detected. Defined threshold inside interfaces is set to '11'
```
```kotlin
6  import kotlinx.coroutines.flow.Flow
7  
8  @Dao
9  interface FoodEntryDao {
!            ^ error
10 
11     @Insert
12     suspend fun insert(entry: FoodEntry): Long

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/dao/MedicalReportDao.kt:9:11
```
Interface 'MedicalReportDao' with '12' functions detected. Defined threshold inside interfaces is set to '11'
```
```kotlin
6  import kotlinx.coroutines.flow.Flow
7  
8  @Dao
9  interface MedicalReportDao {
!            ^ error
10 
11     @Insert
12     suspend fun insert(report: MedicalReport): Long

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/dao/QuickEntryTemplateDao.kt:8:11
```
Interface 'QuickEntryTemplateDao' with '11' functions detected. Defined threshold inside interfaces is set to '11'
```
```kotlin
5  import kotlinx.coroutines.flow.Flow
6  
7  @Dao
8  interface QuickEntryTemplateDao {
!            ^ error
9  
10     @Insert
11     suspend fun insert(template: QuickEntryTemplate): Long

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/dao/SymptomEventDao.kt:11:11
```
Interface 'SymptomEventDao' with '21' functions detected. Defined threshold inside interfaces is set to '11'
```
```kotlin
8  import kotlinx.coroutines.flow.Flow
9  
10 @Dao
11 interface SymptomEventDao {
!!           ^ error
12 
13     @Insert
14     suspend fun insert(event: SymptomEvent): Long

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/dao/TriggerPatternDao.kt:9:11
```
Interface 'TriggerPatternDao' with '12' functions detected. Defined threshold inside interfaces is set to '11'
```
```kotlin
6  import kotlinx.coroutines.flow.Flow
7  
8  @Dao
9  interface TriggerPatternDao {
!            ^ error
10 
11     @Insert(onConflict = OnConflictStrategy.REPLACE)
12     suspend fun insert(pattern: TriggerPattern)

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:20:7
```
Class 'VoiceInputHelper' with '16' functions detected. Defined threshold inside classes is set to '11'
```
```kotlin
17 import kotlinx.coroutines.flow.asStateFlow
18 
19 @Singleton
20 class VoiceInputHelper @Inject constructor(
!!       ^ error
21     private val context: Context,
22 ) {
23 

```

### exceptions, SwallowedException (1)

The caught exception is swallowed. The original exception could be lost.

[Documentation](https://detekt.dev/docs/rules/exceptions#swallowedexception)

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/MedicalReport.kt:78:22
```
The caught exception is swallowed. The original exception could be lost.
```
```kotlin
75         get() = sections.mapNotNull { sectionName ->
76             try {
77                 ReportSection.valueOf(sectionName)
78             } catch (e: IllegalArgumentException) {
!!                      ^ error
79                 null
80             }
81         }

```

### exceptions, TooGenericExceptionCaught (6)

The caught exception is too generic. Prefer catching specific exceptions to the case that is currently handled.

[Documentation](https://detekt.dev/docs/rules/exceptions#toogenericexceptioncaught)

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/concurrency/ConcurrentAccessManager.kt:79:30
```
The caught exception is too generic. Prefer catching specific exceptions to the case that is currently handled.
```
```kotlin
76                     try {
77                         val result = operation()
78                         ConcurrentOperationResult.Success(result)
79                     } catch (e: Exception) {
!!                              ^ error
80                         ConcurrentOperationResult.OperationFailed(e)
81                     } finally {
82                         currentOperations.remove(lockKey)

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/concurrency/ConcurrentAccessManager.kt:87:18
```
The caught exception is too generic. Prefer catching specific exceptions to the case that is currently handled.
```
```kotlin
84                     }
85                 }
86             } ?: ConcurrentOperationResult.TimeoutExceeded(timeoutMs)
87         } catch (e: Exception) {
!!                  ^ error
88             ConcurrentOperationResult.LockingFailed(e)
89         } finally {
90             // Clean up empty operation sets

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/concurrency/ConcurrentAccessManager.kt:180:30
```
The caught exception is too generic. Prefer catching specific exceptions to the case that is currently handled.
```
```kotlin
177                     try {
178                         val result = operation()
179                         ConcurrentOperationResult.Success(result)
180                     } catch (e: Exception) {
!!!                              ^ error
181                         ConcurrentOperationResult.OperationFailed(e)
182                     } finally {
183                         lockKeys.forEach { currentOperations.remove(it) }

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/concurrency/ConcurrentAccessManager.kt:187:18
```
The caught exception is too generic. Prefer catching specific exceptions to the case that is currently handled.
```
```kotlin
184                     }
185                 }
186             } ?: ConcurrentOperationResult.TimeoutExceeded(timeoutMs)
187         } catch (e: Exception) {
!!!                  ^ error
188             ConcurrentOperationResult.LockingFailed(e)
189         } finally {
190             if (currentOperations.isEmpty()) {

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:89:18
```
The caught exception is too generic. Prefer catching specific exceptions to the case that is currently handled.
```
```kotlin
86 
87             speechRecognizer?.startListening(intent)
88             isListening = true
89         } catch (e: Exception) {
!!                  ^ error
90             _voiceInputState.value = _voiceInputState.value.copy(
91                 error = "Failed to start voice recognition: ${e.message}",
92             )

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:107:18
```
The caught exception is too generic. Prefer catching specific exceptions to the case that is currently handled.
```
```kotlin
104                 isListening = false,
105                 isProcessing = false,
106             )
107         } catch (e: Exception) {
!!!                  ^ error
108             _voiceInputState.value = _voiceInputState.value.copy(
109                 error = "Failed to stop voice recognition: ${e.message}",
110             )

```

### style, LoopWithTooManyJumpStatements (1)

The loop contains more than one break or continue statement. The code should be refactored to increase readability.

[Documentation](https://detekt.dev/docs/rules/style#loopwithtoomanyjumpstatements)

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:253:9
```
The loop contains more than one break or continue statement. The code should be refactored to increase readability.
```
```kotlin
250         val words = text.split(" ")
251         var foundFoodKeyword = false
252 
253         for (i in words.indices) {
!!!         ^ error
254             val word = words[i].lowercase()
255             if (foodKeywords.contains(word)) {
256                 foundFoodKeyword = true

```

### style, MagicNumber (49)

Report magic numbers. Magic number is a numeric literal that is not defined as a constant and hence it's unclear what the purpose of this number is. It's better to declare such numbers as constants and give them a proper name. By default, -1, 0, 1, and 2 are not considered to be magic numbers.

[Documentation](https://detekt.dev/docs/rules/style#magicnumber)

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/concurrency/ConcurrentAccessManager.kt:134:60
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
131                 -> {
132                     if (attempt < maxRetries) {
133                         kotlinx.coroutines.delay(currentBackoff)
134                         currentBackoff = (currentBackoff * 1.5).toLong() // Exponential backoff
!!!                                                            ^ error
135                         lastResult = result
136                     } else {
137                         return result

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/concurrency/ConcurrentAccessManager.kt:308:42
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
305             }
306             OperationPriority.LOW -> {
307                 // Low priority operations wait longer but have extended timeout
308                 kotlinx.coroutines.delay(100) // Brief delay to allow other operations
!!!                                          ^ error
309                 withEntityLock(entityType, entityId, timeoutMs = 60_000L, operation)
310             }
311             OperationPriority.NORMAL -> {

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/converters/TypeConverters.kt:76:31
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
73     fun toConsumptionContext(string: String?): ConsumptionContext? {
74         return string?.let {
75             val parts = it.split("|")
76             if (parts.size == 3) {
!!                               ^ error
77                 ConsumptionContext(
78                     location = LocationType.valueOf(parts[0]),
79                     social = SocialContext.valueOf(parts[1]),

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/EnvironmentalContext.kt:27:35
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
24     val additionalNotes: String?,
25 ) {
26     init {
27         require(stressLevel in 1..10) {
!!                                   ^ error
28             "Stress level must be between 1 and 10"
29         }
30         require(sleepQuality in 1..10) {

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/EnvironmentalContext.kt:30:36
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
27         require(stressLevel in 1..10) {
28             "Stress level must be between 1 and 10"
29         }
30         require(sleepQuality in 1..10) {
!!                                    ^ error
31             "Sleep quality must be between 1 and 10"
32         }
33         require(sleepHours in 0f..24f) {

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/EnvironmentalContext.kt:33:35
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
30         require(sleepQuality in 1..10) {
31             "Sleep quality must be between 1 and 10"
32         }
33         require(sleepHours in 0f..24f) {
!!                                   ^ error
34             "Sleep hours must be between 0 and 24"
35         }
36         exerciseMinutes?.let { minutes ->

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/MedicalReport.kt:68:24
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
65     val formattedFileSize: String
66         get() = fileSize?.let { size ->
67             when {
68                 size < 1024 -> "${size}B"
!!                        ^ error
69                 size < 1024 * 1024 -> "${size / 1024}KB"
70                 else -> "${size / (1024 * 1024)}MB"
71             }

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/MedicalReport.kt:69:24
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
66         get() = fileSize?.let { size ->
67             when {
68                 size < 1024 -> "${size}B"
69                 size < 1024 * 1024 -> "${size / 1024}KB"
!!                        ^ error
70                 else -> "${size / (1024 * 1024)}MB"
71             }
72         } ?: "Unknown"

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/MedicalReport.kt:69:31
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
66         get() = fileSize?.let { size ->
67             when {
68                 size < 1024 -> "${size}B"
69                 size < 1024 * 1024 -> "${size / 1024}KB"
!!                               ^ error
70                 else -> "${size / (1024 * 1024)}MB"
71             }
72         } ?: "Unknown"

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/MedicalReport.kt:69:49
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
66         get() = fileSize?.let { size ->
67             when {
68                 size < 1024 -> "${size}B"
69                 size < 1024 * 1024 -> "${size / 1024}KB"
!!                                                 ^ error
70                 else -> "${size / (1024 * 1024)}MB"
71             }
72         } ?: "Unknown"

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/MedicalReport.kt:70:36
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
67             when {
68                 size < 1024 -> "${size}B"
69                 size < 1024 * 1024 -> "${size / 1024}KB"
70                 else -> "${size / (1024 * 1024)}MB"
!!                                    ^ error
71             }
72         } ?: "Unknown"
73 

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/MedicalReport.kt:70:43
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
67             when {
68                 size < 1024 -> "${size}B"
69                 size < 1024 * 1024 -> "${size / 1024}KB"
70                 else -> "${size / (1024 * 1024)}MB"
!!                                           ^ error
71             }
72         } ?: "Unknown"
73 

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/SymptomEvent.kt:35:32
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
32     val deletedAt: Instant? = null,
33 ) {
34     init {
35         require(severity in 1..10) {
!!                                ^ error
36             "Severity must be between 1 and 10 (medical standard)"
37         }
38         bristolScale?.let { scale ->

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/SymptomEvent.kt:39:33
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
36             "Severity must be between 1 and 10 (medical standard)"
37         }
38         bristolScale?.let { scale ->
39             require(scale in 1..7) {
!!                                 ^ error
40                 "Bristol Stool Scale must be between 1 and 7"
41             }
42         }

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/TriggerPattern.kt:81:32
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
78      * - P-value <0.05 for significance (FR-013)
79      */
80     val isStatisticallySignificant: Boolean
81         get() = occurrences >= 10 &&
!!                                ^ error
82             correlationStrength >= 0.6f &&
83             confidence >= 0.95f &&
84             (pValue?.let { it < 0.05f } ?: false)

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/TriggerPattern.kt:82:36
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
79      */
80     val isStatisticallySignificant: Boolean
81         get() = occurrences >= 10 &&
82             correlationStrength >= 0.6f &&
!!                                    ^ error
83             confidence >= 0.95f &&
84             (pValue?.let { it < 0.05f } ?: false)
85 

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/TriggerPattern.kt:83:27
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
80     val isStatisticallySignificant: Boolean
81         get() = occurrences >= 10 &&
82             correlationStrength >= 0.6f &&
83             confidence >= 0.95f &&
!!                           ^ error
84             (pValue?.let { it < 0.05f } ?: false)
85 
86     val isHighConfidence: Boolean

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/TriggerPattern.kt:84:33
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
81         get() = occurrences >= 10 &&
82             correlationStrength >= 0.6f &&
83             confidence >= 0.95f &&
84             (pValue?.let { it < 0.05f } ?: false)
!!                                 ^ error
85 
86     val isHighConfidence: Boolean
87         get() = correlationStrength >= 0.7f && confidence >= 0.85f

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/TriggerPattern.kt:87:40
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
84             (pValue?.let { it < 0.05f } ?: false)
85 
86     val isHighConfidence: Boolean
87         get() = correlationStrength >= 0.7f && confidence >= 0.85f
!!                                        ^ error
88 
89     val averageTimeOffsetHours: Float
90         get() = averageTimeOffsetMinutes / 60f

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/TriggerPattern.kt:87:62
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
84             (pValue?.let { it < 0.05f } ?: false)
85 
86     val isHighConfidence: Boolean
87         get() = correlationStrength >= 0.7f && confidence >= 0.85f
!!                                                              ^ error
88 
89     val averageTimeOffsetHours: Float
90         get() = averageTimeOffsetMinutes / 60f

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/TriggerPattern.kt:90:44
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
87         get() = correlationStrength >= 0.7f && confidence >= 0.85f
88 
89     val averageTimeOffsetHours: Float
90         get() = averageTimeOffsetMinutes / 60f
!!                                            ^ error
91 
92     fun update(
93         correlationStrength: Float? = null,

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/models/EliminationPhase.kt:29:28
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
26     val typicalDurationWeeks: IntRange
27         get() = when (this) {
28             BASELINE -> 1..2
29             ELIMINATION -> 3..6
!!                            ^ error
30             REINTRODUCTION -> 4..8
31             MAINTENANCE -> Int.MAX_VALUE..Int.MAX_VALUE // Ongoing
32         }

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/models/EliminationPhase.kt:29:31
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
26     val typicalDurationWeeks: IntRange
27         get() = when (this) {
28             BASELINE -> 1..2
29             ELIMINATION -> 3..6
!!                               ^ error
30             REINTRODUCTION -> 4..8
31             MAINTENANCE -> Int.MAX_VALUE..Int.MAX_VALUE // Ongoing
32         }

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/models/EliminationPhase.kt:30:31
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
27         get() = when (this) {
28             BASELINE -> 1..2
29             ELIMINATION -> 3..6
30             REINTRODUCTION -> 4..8
!!                               ^ error
31             MAINTENANCE -> Int.MAX_VALUE..Int.MAX_VALUE // Ongoing
32         }
33 }

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/models/EliminationPhase.kt:30:34
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
27         get() = when (this) {
28             BASELINE -> 1..2
29             ELIMINATION -> 3..6
30             REINTRODUCTION -> 4..8
!!                                  ^ error
31             MAINTENANCE -> Int.MAX_VALUE..Int.MAX_VALUE // Ongoing
32         }
33 }

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/TimezoneHandler.kt:36:92
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
33         val currentDateTime = ZonedDateTime.ofInstant(entryTimestamp, currentTimezone)
34 
35         val offsetDifference = (
36             (currentDateTime.offset.totalSeconds - originalDateTime.offset.totalSeconds) / 3600
!!                                                                                            ^ error
37             ).toLong()
38 
39         return when {

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/TimezoneHandler.kt:141:60
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
138             val timezoneDiff = getTimezoneOffsetDifference(previous.timezone, current.timezone, current.timestamp)
139 
140             // Check if timezone change is physically possible given time between entries
141             if (kotlin.math.abs(timezoneDiff) > timeDiff + 3) { // +3 hours for margin
!!!                                                            ^ error
142                 issues.add(
143                     TimezoneIssue.ImpossibleJump(
144                         from = previous,

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/TimezoneHandler.kt:156:37
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
153                 val next = sortedEntries[i + 1]
154                 if (previous.timezone == next.timezone && previous.timezone != current.timezone) {
155                     val totalTime = ChronoUnit.HOURS.between(previous.timestamp, next.timestamp)
156                     if (totalTime < 24) {
!!!                                     ^ error
157                         issues.add(
158                             TimezoneIssue.SuspiciousPattern(
159                                 entries = listOf(previous, current, next),

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/TimezoneHandler.kt:201:26
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
198         // Find entries within 24 hours before and after
199         val relevantEntries = surroundingEntries.filter { entry ->
200             val hoursDiff = kotlin.math.abs(ChronoUnit.HOURS.between(entry.timestamp, entryTime))
201             hoursDiff <= 24
!!!                          ^ error
202         }
203 
204         if (relevantEntries.isEmpty()) {

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/TimezoneHandler.kt:229:94
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
226         val previousTransition = zoneRules.previousTransition(timestamp)
227 
228         return when {
229             transition != null && ChronoUnit.HOURS.between(timestamp, transition.instant) <= 24 -> {
!!!                                                                                              ^ error
230                 DSTHandling.UpcomingTransition(
231                     transitionTime = transition.instant,
232                     offsetChange = transition.offsetAfter.totalSeconds -

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/TimezoneHandler.kt:238:110
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
235                 )
236             }
237 
238             previousTransition != null && ChronoUnit.HOURS.between(previousTransition.instant, timestamp) <= 24 -> {
!!!                                                                                                              ^ error
239                 DSTHandling.RecentTransition(
240                     transitionTime = previousTransition.instant,
241                     offsetChange = previousTransition.offsetAfter.totalSeconds -

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/TimezoneHandler.kt:288:64
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
285     ): Int {
286         val offset1 = zone1.rules.getOffset(atInstant)
287         val offset2 = zone2.rules.getOffset(atInstant)
288         return (offset2.totalSeconds - offset1.totalSeconds) / 3600
!!!                                                                ^ error
289     }
290 
291     private fun generateTravelRecommendation(changes: List<TimezoneChange>): String {

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/TimezoneHandler.kt:296:28
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
293             changes.any { !it.isProbableTravel } -> {
294                 "Some timezone changes appear inconsistent. Please verify entry times."
295             }
296             changes.size > 5 -> {
!!!                            ^ error
297                 "Multiple timezone changes detected. Consider reviewing entries for accuracy."
298             }
299             else -> {

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:262:52
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
259 
260             if (foundFoodKeyword) {
261                 // Look for food items after food keywords
262                 val nextWords = words.drop(i).take(5) // Get next few words
!!!                                                    ^ error
263                 val foodItem = nextWords.takeWhile { it.lowercase() !in listOf("and", "with", "for", "at") }
264                     .joinToString(" ")
265                     .replace(Regex("[^a-zA-Z0-9\\s]"), "")

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:328:52
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
325             durationMatch != null -> {
326                 val value = durationMatch.groupValues[1].toIntOrNull() ?: 0
327                 val unit = durationMatch.groupValues[2]
328                 if (unit.contains("hour")) value * 60 else value
!!!                                                    ^ error
329             }
330             else -> null
331         }

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:335:45
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
332 
333         return ParsedSymptomEntry(
334             symptomType = symptomType,
335             severity = severity.coerceIn(1, 10),
!!!                                             ^ error
336             duration = durationMinutes,
337             notes = text,
338             originalText = text,

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:362:16
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
359     private fun determineMealTypeByTime(): String {
360         val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
361         return when (hour) {
362             in 5..10 -> "Breakfast"
!!!                ^ error
363             in 11..14 -> "Lunch"
364             in 15..17 -> "Snack"
365             in 18..22 -> "Dinner"

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:362:19
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
359     private fun determineMealTypeByTime(): String {
360         val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
361         return when (hour) {
362             in 5..10 -> "Breakfast"
!!!                   ^ error
363             in 11..14 -> "Lunch"
364             in 15..17 -> "Snack"
365             in 18..22 -> "Dinner"

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:363:16
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
360         val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
361         return when (hour) {
362             in 5..10 -> "Breakfast"
363             in 11..14 -> "Lunch"
!!!                ^ error
364             in 15..17 -> "Snack"
365             in 18..22 -> "Dinner"
366             else -> "Snack"

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:363:20
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
360         val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
361         return when (hour) {
362             in 5..10 -> "Breakfast"
363             in 11..14 -> "Lunch"
!!!                    ^ error
364             in 15..17 -> "Snack"
365             in 18..22 -> "Dinner"
366             else -> "Snack"

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:364:16
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
361         return when (hour) {
362             in 5..10 -> "Breakfast"
363             in 11..14 -> "Lunch"
364             in 15..17 -> "Snack"
!!!                ^ error
365             in 18..22 -> "Dinner"
366             else -> "Snack"
367         }

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:364:20
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
361         return when (hour) {
362             in 5..10 -> "Breakfast"
363             in 11..14 -> "Lunch"
364             in 15..17 -> "Snack"
!!!                    ^ error
365             in 18..22 -> "Dinner"
366             else -> "Snack"
367         }

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:365:16
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
362             in 5..10 -> "Breakfast"
363             in 11..14 -> "Lunch"
364             in 15..17 -> "Snack"
365             in 18..22 -> "Dinner"
!!!                ^ error
366             else -> "Snack"
367         }
368     }

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:365:20
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
362             in 5..10 -> "Breakfast"
363             in 11..14 -> "Lunch"
364             in 15..17 -> "Snack"
365             in 18..22 -> "Dinner"
!!!                    ^ error
366             else -> "Snack"
367         }
368     }

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:372:106
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
369 
370     private fun estimateSeverityFromText(text: String): Int {
371         return when {
372             text.contains("terrible") || text.contains("excruciating") || text.contains("unbearable") -> 9
!!!                                                                                                          ^ error
373             text.contains("severe") || text.contains("intense") || text.contains("very bad") -> 7
374             text.contains("moderate") || text.contains("painful") || text.contains("bad") -> 5
375             text.contains("mild") || text.contains("slight") || text.contains("little") -> 3

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:373:97
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
370     private fun estimateSeverityFromText(text: String): Int {
371         return when {
372             text.contains("terrible") || text.contains("excruciating") || text.contains("unbearable") -> 9
373             text.contains("severe") || text.contains("intense") || text.contains("very bad") -> 7
!!!                                                                                                 ^ error
374             text.contains("moderate") || text.contains("painful") || text.contains("bad") -> 5
375             text.contains("mild") || text.contains("slight") || text.contains("little") -> 3
376             text.contains("barely") || text.contains("hardly") -> 2

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:374:94
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
371         return when {
372             text.contains("terrible") || text.contains("excruciating") || text.contains("unbearable") -> 9
373             text.contains("severe") || text.contains("intense") || text.contains("very bad") -> 7
374             text.contains("moderate") || text.contains("painful") || text.contains("bad") -> 5
!!!                                                                                              ^ error
375             text.contains("mild") || text.contains("slight") || text.contains("little") -> 3
376             text.contains("barely") || text.contains("hardly") -> 2
377             else -> 4 // Default moderate severity

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:375:92
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
372             text.contains("terrible") || text.contains("excruciating") || text.contains("unbearable") -> 9
373             text.contains("severe") || text.contains("intense") || text.contains("very bad") -> 7
374             text.contains("moderate") || text.contains("painful") || text.contains("bad") -> 5
375             text.contains("mild") || text.contains("slight") || text.contains("little") -> 3
!!!                                                                                            ^ error
376             text.contains("barely") || text.contains("hardly") -> 2
377             else -> 4 // Default moderate severity
378         }

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/VoiceInputHelper.kt:377:21
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
374             text.contains("moderate") || text.contains("painful") || text.contains("bad") -> 5
375             text.contains("mild") || text.contains("slight") || text.contains("little") -> 3
376             text.contains("barely") || text.contains("hardly") -> 2
377             else -> 4 // Default moderate severity
!!!                     ^ error
378         }
379     }
380 

```

### style, ReturnCount (3)

Restrict the number of return statements in methods.

[Documentation](https://detekt.dev/docs/rules/style#returncount)

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/concurrency/ConcurrentAccessManager.kt:116:21
```
Function withRetry has 4 return statements which exceeds the limit of 2.
```
```kotlin
113         }
114     }
115 
116     suspend fun <T> withRetry(
!!!                     ^ error
117         maxRetries: Int = MAX_RETRIES,
118         backoffMs: Long = 100,
119         operation: suspend () -> ConcurrentOperationResult<T>,

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/concurrency/ConcurrentAccessManager.kt:231:17
```
Function hasCircularDependency has 5 return statements which exceeds the limit of 2.
```
```kotlin
228         return hasCircularDependency(requestedLock, currentLocks, mutableSetOf())
229     }
230 
231     private fun hasCircularDependency(
!!!                 ^ error
232         target: String,
233         currentPath: Set<String>,
234         visited: MutableSet<String>,

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/TimezoneHandler.kt:189:9
```
Function suggestTimezoneForEntry has 3 return statements which exceeds the limit of 2.
```
```kotlin
186     /**
187      * Suggests the most likely timezone for an entry based on surrounding entries
188      */
189     fun suggestTimezoneForEntry(
!!!         ^ error
190         entryTime: Instant,
191         surroundingEntries: List<TimezoneAwareEntry>,
192         defaultZone: ZoneId = ZoneId.systemDefault(),

```

### style, UnusedParameter (3)

Function parameter is unused and should be removed.

[Documentation](https://detekt.dev/docs/rules/style#unusedparameter)

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/concurrency/ConcurrentAccessManager.kt:279:9
```
Function parameter `batchSize` is unused.
```
```kotlin
276     // Bulk operations support
277     suspend fun <T> withBulkInsert(
278         entityType: String,
279         batchSize: Int = 50,
!!!         ^ error
280         operation: suspend () -> T,
281     ): ConcurrentOperationResult<T> {
282         val bulkLockKey = "$entityType:BULK_INSERT"

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/TimezoneHandler.kt:74:9
```
Function parameter `fromZone` is unused.
```
```kotlin
71      */
72     fun convertToCurrentTimezone(
73         timestamp: Instant,
74         fromZone: ZoneId,
!!         ^ error
75         toZone: ZoneId,
76     ): Instant {
77         // The instant remains the same, only the representation changes

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/TimezoneHandler.kt:75:9
```
Function parameter `toZone` is unused.
```
```kotlin
72     fun convertToCurrentTimezone(
73         timestamp: Instant,
74         fromZone: ZoneId,
75         toZone: ZoneId,
!!         ^ error
76     ): Instant {
77         // The instant remains the same, only the representation changes
78         return timestamp

```

### style, UnusedPrivateProperty (1)

Property is unused and should be removed.

[Documentation](https://detekt.dev/docs/rules/style#unusedprivateproperty)

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/utils/TimezoneHandler.kt:19:27
```
Private property `MAX_TIMEZONE_DRIFT_HOURS` is unused.
```
```kotlin
16 class TimezoneHandler @Inject constructor() {
17 
18     companion object {
19         private const val MAX_TIMEZONE_DRIFT_HOURS = 26 // Maximum possible timezone difference
!!                           ^ error
20         private const val SUSPICIOUS_TIME_JUMP_HOURS = 12 // Flag entries with large time jumps
21     }
22 

```

### style, WildcardImport (14)

Wildcard imports should be replaced with imports using fully qualified class names. Wildcard imports can lead to naming conflicts. A library update can introduce naming clashes with your classes which results in compilation errors.

[Documentation](https://detekt.dev/docs/rules/style#wildcardimport)

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/FoodDiaryDatabase.kt:6:1
```
com.fooddiary.data.database.converters.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
3  import androidx.room.Database
4  import androidx.room.RoomDatabase
5  import androidx.room.TypeConverters
6  import com.fooddiary.data.database.converters.*
!  ^ error
7  import com.fooddiary.data.database.dao.*
8  import com.fooddiary.data.database.entities.*
9  

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/FoodDiaryDatabase.kt:7:1
```
com.fooddiary.data.database.dao.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
4  import androidx.room.RoomDatabase
5  import androidx.room.TypeConverters
6  import com.fooddiary.data.database.converters.*
7  import com.fooddiary.data.database.dao.*
!  ^ error
8  import com.fooddiary.data.database.entities.*
9  
10 @Database(

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/FoodDiaryDatabase.kt:8:1
```
com.fooddiary.data.database.entities.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
5  import androidx.room.TypeConverters
6  import com.fooddiary.data.database.converters.*
7  import com.fooddiary.data.database.dao.*
8  import com.fooddiary.data.database.entities.*
!  ^ error
9  
10 @Database(
11     entities = [

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/dao/BeverageEntryDao.kt:3:1
```
androidx.room.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
1 package com.fooddiary.data.database.dao
2 
3 import androidx.room.*
! ^ error
4 import com.fooddiary.data.database.entities.BeverageEntry
5 import java.time.Instant
6 import kotlinx.coroutines.flow.Flow

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/dao/EliminationProtocolDao.kt:3:1
```
androidx.room.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
1 package com.fooddiary.data.database.dao
2 
3 import androidx.room.*
! ^ error
4 import com.fooddiary.data.database.entities.EliminationProtocol
5 import com.fooddiary.data.models.EliminationPhase
6 import kotlinx.coroutines.flow.Flow

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/dao/EnvironmentalContextDao.kt:3:1
```
androidx.room.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
1 package com.fooddiary.data.database.dao
2 
3 import androidx.room.*
! ^ error
4 import com.fooddiary.data.database.entities.EnvironmentalContext
5 import java.time.LocalDate
6 import kotlinx.coroutines.flow.Flow

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/dao/FoodEntryDao.kt:3:1
```
androidx.room.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
1 package com.fooddiary.data.database.dao
2 
3 import androidx.room.*
! ^ error
4 import com.fooddiary.data.database.entities.FoodEntry
5 import java.time.Instant
6 import kotlinx.coroutines.flow.Flow

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/dao/MedicalReportDao.kt:3:1
```
androidx.room.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
1 package com.fooddiary.data.database.dao
2 
3 import androidx.room.*
! ^ error
4 import com.fooddiary.data.database.entities.MedicalReport
5 import com.fooddiary.data.models.ReportFormat
6 import kotlinx.coroutines.flow.Flow

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/dao/QuickEntryTemplateDao.kt:3:1
```
androidx.room.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
1 package com.fooddiary.data.database.dao
2 
3 import androidx.room.*
! ^ error
4 import com.fooddiary.data.database.entities.QuickEntryTemplate
5 import kotlinx.coroutines.flow.Flow
6 

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/dao/SymptomEventDao.kt:3:1
```
androidx.room.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
1 package com.fooddiary.data.database.dao
2 
3 import androidx.room.*
! ^ error
4 import com.fooddiary.data.database.entities.SymptomEvent
5 import com.fooddiary.data.models.SymptomFrequency
6 import com.fooddiary.data.models.SymptomType

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/dao/TriggerPatternDao.kt:3:1
```
androidx.room.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
1 package com.fooddiary.data.database.dao
2 
3 import androidx.room.*
! ^ error
4 import com.fooddiary.data.database.entities.TriggerPattern
5 import com.fooddiary.data.models.SymptomType
6 import kotlinx.coroutines.flow.Flow

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/BeverageEntry.kt:7:1
```
com.fooddiary.data.models.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
4  import androidx.room.PrimaryKey
5  import androidx.room.TypeConverters
6  import com.fooddiary.data.database.converters.InstantConverter
7  import com.fooddiary.data.models.*
!  ^ error
8  import java.time.Instant
9  import java.util.*
10 

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/FODMAPFood.kt:5:1
```
com.fooddiary.data.models.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
2 
3 import androidx.room.Entity
4 import androidx.room.PrimaryKey
5 import com.fooddiary.data.models.*
! ^ error
6 
7 @Entity(tableName = "fodmap_foods")
8 data class FODMAPFood(

```

* /home/vlb/food-diary/app/src/main/java/com/fooddiary/data/database/entities/FoodEntry.kt:9:1
```
com.fooddiary.data.models.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
6  import com.fooddiary.data.database.converters.ConsumptionContextConverter
7  import com.fooddiary.data.database.converters.InstantConverter
8  import com.fooddiary.data.database.converters.StringListConverter
9  import com.fooddiary.data.models.*
!  ^ error
10 import java.time.Instant
11 import java.util.*
12 

```

generated with [detekt version 1.23.4](https://detekt.dev/) on 2025-09-24 18:29:05 UTC

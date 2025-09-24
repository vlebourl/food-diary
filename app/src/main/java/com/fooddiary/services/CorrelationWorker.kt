package com.fooddiary.services

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.fooddiary.domain.usecase.CalculateCorrelationsUseCase
import com.fooddiary.domain.usecase.ValidateDataIntegrityUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.concurrent.TimeUnit

@HiltWorker
class CorrelationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val calculateCorrelationsUseCase: CalculateCorrelationsUseCase,
    private val validateDataIntegrityUseCase: ValidateDataIntegrityUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            setProgress(workDataOf("status" to "Starting correlation analysis"))

            // Validate data integrity first
            setProgress(workDataOf("status" to "Validating data integrity"))
            val validationResult = validateDataIntegrityUseCase()

            if (!validationResult.isValid) {
                return@withContext Result.failure(
                    workDataOf("error" to "Data integrity validation failed: ${validationResult.errors.joinToString()}")
                )
            }

            // Get the analysis period from input data
            val analysisDays = inputData.getInt("analysis_days", 90)
            val endDate = LocalDate.now()
            val startDate = endDate.minusDays(analysisDays.toLong())

            setProgress(workDataOf(
                "status" to "Calculating correlations",
                "start_date" to startDate.toString(),
                "end_date" to endDate.toString()
            ))

            // Calculate correlations for the specified period
            calculateCorrelationsUseCase(startDate, endDate)

            setProgress(workDataOf("status" to "Analysis complete"))

            // Return success with analysis summary
            val outputData = workDataOf(
                "analysis_completed" to true,
                "analysis_date" to endDate.toString(),
                "analysis_period_days" to analysisDays,
                "patterns_analyzed" to true
            )

            Result.success(outputData)
        } catch (e: Exception) {
            Result.failure(
                workDataOf("error" to (e.message ?: "Unknown error during correlation analysis"))
            )
        }
    }

    companion object {
        const val WORK_NAME = "correlation_analysis"
        private const val TAG = "CorrelationWorker"

        fun enqueueOneTime(
            context: Context,
            analysisDays: Int = 90,
            expedited: Boolean = false
        ) {
            val inputData = workDataOf("analysis_days" to analysisDays)

            val workRequestBuilder = OneTimeWorkRequestBuilder<CorrelationWorker>()
                .setInputData(inputData)
                .addTag(TAG)

            if (expedited) {
                workRequestBuilder.setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            }

            val workRequest = workRequestBuilder.build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                workRequest
            )
        }

        fun enqueuePeriodicAnalysis(context: Context) {
            val workRequest = PeriodicWorkRequestBuilder<CorrelationWorker>(
                repeatInterval = 24, // Run daily
                repeatIntervalTimeUnit = TimeUnit.HOURS,
                flexTimeInterval = 4, // Allow 4-hour flex window
                flexTimeIntervalUnit = TimeUnit.HOURS
            )
                .setInputData(workDataOf("analysis_days" to 30)) // Analyze last 30 days daily
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                        .setRequiresBatteryNotLow(true)
                        .setRequiresCharging(false)
                        .setRequiresDeviceIdle(false)
                        .build()
                )
                .addTag(TAG)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "periodic_$WORK_NAME",
                ExistingPeriodicWorkPolicy.UPDATE,
                workRequest
            )
        }

        fun enqueueForegroundAnalysis(context: Context, analysisDays: Int = 90) {
            val inputData = workDataOf("analysis_days" to analysisDays)

            val workRequest = OneTimeWorkRequestBuilder<CorrelationWorker>()
                .setInputData(inputData)
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .addTag("$TAG-foreground")
                .build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                "${WORK_NAME}_foreground",
                ExistingWorkPolicy.KEEP,
                workRequest
            )
        }

        fun cancelAllWork(context: Context) {
            WorkManager.getInstance(context).cancelAllWorkByTag(TAG)
        }

        fun getWorkInfo(context: Context) =
            WorkManager.getInstance(context).getWorkInfosForUniqueWorkLiveData(WORK_NAME)
    }
}

@HiltWorker
class DataCleanupWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val validateDataIntegrityUseCase: ValidateDataIntegrityUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            setProgress(workDataOf("status" to "Starting data cleanup"))

            // Run data validation and cleanup
            val validationResult = validateDataIntegrityUseCase()

            setProgress(workDataOf(
                "status" to "Cleanup complete",
                "issues_found" to validationResult.errors.size,
                "data_valid" to validationResult.isValid
            ))

            if (validationResult.isValid) {
                Result.success(workDataOf("cleanup_successful" to true))
            } else {
                Result.success(workDataOf(
                    "cleanup_successful" to true,
                    "issues_resolved" to validationResult.errors.size
                ))
            }
        } catch (e: Exception) {
            Result.failure(
                workDataOf("error" to (e.message ?: "Unknown error during data cleanup"))
            )
        }
    }

    companion object {
        fun enqueueWeeklyCleanup(context: Context) {
            val workRequest = PeriodicWorkRequestBuilder<DataCleanupWorker>(
                repeatInterval = 7, // Run weekly
                repeatIntervalTimeUnit = TimeUnit.DAYS,
                flexTimeInterval = 1, // Allow 1-day flex window
                flexTimeIntervalUnit = TimeUnit.DAYS
            )
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                        .setRequiresBatteryNotLow(true)
                        .setRequiresCharging(true) // Run while charging
                        .setRequiresDeviceIdle(true) // Run when device is idle
                        .build()
                )
                .addTag("data_cleanup")
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "weekly_data_cleanup",
                ExistingPeriodicWorkPolicy.UPDATE,
                workRequest
            )
        }
    }
}
package com.fooddiary

import android.app.Application

// @HiltAndroidApp  // Temporarily disabled for KAPT issues
class FoodDiaryApplication : Application() /* Configuration.Provider */ {

    // Temporarily disabled Hilt Worker integration
    /* @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface HiltWorkerFactoryEntryPoint {
        fun workerFactory(): HiltWorkerFactory
    }

    override fun getWorkManagerConfiguration(): Configuration {
        val hiltWorkerFactory = EntryPointAccessors.fromApplication(
            this,
            HiltWorkerFactoryEntryPoint::class.java
        ).workerFactory()

        return Configuration.Builder()
            .setWorkerFactory(hiltWorkerFactory)
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
    } */

    override fun onCreate() {
        super.onCreate()
        // Initialize any required services here
    }
}

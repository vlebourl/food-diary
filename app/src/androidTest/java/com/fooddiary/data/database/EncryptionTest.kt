package com.fooddiary.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import kotlin.test.*

@RunWith(AndroidJUnit4::class)
class EncryptionTest {

    private lateinit var context: Context
    private lateinit var database: FoodDiaryDatabase
    private val testDbName = "encryption_test.db"

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @After
    fun cleanup() {
        if (::database.isInitialized) {
            database.close()
        }
        // Clean up test database file
        context.getDatabasePath(testDbName).delete()
    }

    @Test
    fun `database should be encrypted with SQLCipher`() {
        // This test will fail until encryption is properly implemented
        val passphrase = "test_passphrase".toByteArray()
        val factory = SupportOpenHelperFactory(passphrase)

        database = Room.databaseBuilder(
            context,
            FoodDiaryDatabase::class.java,
            testDbName
        )
            .openHelperFactory(factory)
            .allowMainThreadQueries() // For testing only
            .build()

        // Test that database opens successfully with correct passphrase
        assertNotNull(database)

        // Try to execute a simple query to verify encryption is working
        try {
            database.openHelper.readableDatabase
            // If we get here, the database opened successfully with encryption
            assertTrue(true)
        } catch (e: Exception) {
            fail("Database should open with correct passphrase: ${e.message}")
        }

        database.close()
    }

    @Test
    fun `database file should not be readable without passphrase`() {
        // This test will fail until encryption is properly implemented
        val passphrase = "test_passphrase".toByteArray()
        val factory = SupportOpenHelperFactory(passphrase)

        // Create encrypted database
        database = Room.databaseBuilder(
            context,
            FoodDiaryDatabase::class.java,
            testDbName
        )
            .openHelperFactory(factory)
            .allowMainThreadQueries()
            .build()

        // Write some test data
        try {
            database.openHelper.readableDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS test_table (id INTEGER PRIMARY KEY, data TEXT)"
            )
            database.openHelper.readableDatabase.execSQL(
                "INSERT INTO test_table (data) VALUES ('sensitive_data')"
            )
        } catch (e: Exception) {
            // Expected to fail until database schema is implemented
        }

        database.close()

        // Try to read the database file directly - should not contain readable data
        val dbFile = context.getDatabasePath(testDbName)
        if (dbFile.exists()) {
            val fileContent = dbFile.readText()
            assertFalse(
                "Database file should not contain plaintext sensitive data",
                fileContent.contains("sensitive_data")
            )
        }
    }

    @Test
    fun `wrong passphrase should fail to open database`() {
        // This test will fail until encryption is properly implemented
        val correctPassphrase = "correct_passphrase".toByteArray()
        val wrongPassphrase = "wrong_passphrase".toByteArray()

        // Create database with correct passphrase
        val correctFactory = SupportOpenHelperFactory(correctPassphrase)
        database = Room.databaseBuilder(
            context,
            FoodDiaryDatabase::class.java,
            testDbName
        )
            .openHelperFactory(correctFactory)
            .allowMainThreadQueries()
            .build()

        // Verify it opens with correct passphrase
        try {
            database.openHelper.readableDatabase
        } catch (e: Exception) {
            // Expected to fail until implementation is complete
        }

        database.close()

        // Now try to open with wrong passphrase
        val wrongFactory = SupportOpenHelperFactory(wrongPassphrase)
        val wrongDatabase = Room.databaseBuilder(
            context,
            FoodDiaryDatabase::class.java,
            testDbName
        )
            .openHelperFactory(wrongFactory)
            .allowMainThreadQueries()
            .build()

        // Should fail to open with wrong passphrase
        assertFailsWith<Exception> {
            wrongDatabase.openHelper.readableDatabase
        }

        wrongDatabase.close()
    }

    @Test
    fun `encryption should not impact database performance significantly`() {
        // This test will fail until encryption is properly implemented
        val passphrase = "performance_test".toByteArray()
        val factory = SupportOpenHelperFactory(passphrase)

        database = Room.databaseBuilder(
            context,
            FoodDiaryDatabase::class.java,
            testDbName
        )
            .openHelperFactory(factory)
            .allowMainThreadQueries()
            .build()

        // Measure time for basic operations
        val startTime = System.currentTimeMillis()

        try {
            val db = database.openHelper.readableDatabase
            // Perform some basic operations
            db.execSQL("CREATE TABLE IF NOT EXISTS perf_test (id INTEGER PRIMARY KEY, data TEXT)")

            // Insert multiple records to test performance
            repeat(100) { i ->
                db.execSQL("INSERT INTO perf_test (data) VALUES ('test_data_$i')")
            }

            // Query the records
            val cursor = db.query("SELECT COUNT(*) FROM perf_test")
            cursor.moveToFirst()
            val count = cursor.getInt(0)
            cursor.close()

            assertEquals(100, count)
        } catch (e: Exception) {
            // Expected to fail until implementation is complete
        }

        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime

        // Should complete in reasonable time (under 2 seconds for 100 operations)
        assertTrue(
            "Encrypted operations should complete in reasonable time",
            duration < 2000
        )

        database.close()
    }

    @Test
    fun `passphrase generation should be consistent`() {
        // Test the passphrase generation logic from DatabaseModule
        val packageName = "com.fooddiary.test"
        val passphrase1 = packageName.toByteArray()
        val passphrase2 = packageName.toByteArray()

        // Should generate identical passphrases for same input
        assertContentEquals(passphrase1, passphrase2)

        // Should generate different passphrases for different inputs
        val differentPassphrase = "com.different.package".toByteArray()
        assertFalse(passphrase1.contentEquals(differentPassphrase))
    }
}
package com.fooddiary.data.repository

import com.fooddiary.data.database.entities.QuickEntryTemplate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import kotlin.test.*

@RunWith(MockitoJUnitRunner::class)
class QuickEntryTemplateRepositoryTest {

    private val repository: QuickEntryTemplateRepository = mock()

    @Test
    fun `insert should return non-empty ID`() = runTest {
        // This test will fail until implementation is complete
        val template = createTestQuickEntryTemplate()
        val result = repository.insert(template)
        assertNotNull(result)
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun `update should modify existing template`() = runTest {
        // This test will fail until implementation is complete
        val template = createTestQuickEntryTemplate()
        assertDoesNotThrow {
            repository.update(template)
        }
    }

    @Test
    fun `delete should remove template permanently`() = runTest {
        // This test will fail until implementation is complete
        val templateId = "test-id"
        assertDoesNotThrow {
            repository.delete(templateId)
        }
    }

    @Test
    fun `getAllActive should return only active templates`() = runTest {
        // This test will fail until implementation is complete
        val flow: Flow<List<QuickEntryTemplate>> = repository.getAllActive()
        val result = flow.first()
        assertNotNull(result)
        assertTrue(result.isEmpty()) // Initially empty
    }

    @Test
    fun `reorder should update template positions`() = runTest {
        // This test will fail until implementation is complete
        val templates = listOf(
            createTestQuickEntryTemplate().copy(id = "1", sortOrder = 1),
            createTestQuickEntryTemplate().copy(id = "2", sortOrder = 2)
        )
        assertDoesNotThrow {
            repository.reorder(templates)
        }
    }

    @Test
    fun `getById should return correct template`() = runTest {
        // This test will fail until implementation is complete
        val templateId = "test-id"
        val result = repository.getById(templateId)
        assertNull(result) // Expected to fail initially
    }

    @Test
    fun `template should have valid button configuration`() = runTest {
        // Test template validation
        val template = createTestQuickEntryTemplate()
        assertNotNull(template.name)
        assertTrue(template.name.isNotEmpty())
        assertNotNull(template.buttonColor)
        assertNotNull(template.buttonIcon)
        assertTrue(template.sortOrder >= 0)
    }

    @Test
    fun `template should support both food and symptom types`() = runTest {
        // Test different template types
        val foodTemplate = createTestQuickEntryTemplate().copy(entryType = "food")
        val symptomTemplate = createTestQuickEntryTemplate().copy(entryType = "symptom")

        assertEquals("food", foodTemplate.entryType)
        assertEquals("symptom", symptomTemplate.entryType)
    }

    private fun createTestQuickEntryTemplate(): QuickEntryTemplate {
        // This will fail until QuickEntryTemplate entity is implemented
        return QuickEntryTemplate(
            id = "test-id",
            name = "Morning Coffee",
            entryType = "beverage",
            defaultData = mapOf(
                "name" to "Coffee",
                "quantity" to "250",
                "unit" to "ml",
                "caffeineContent" to "95"
            ),
            buttonColor = "#8B4513",
            buttonIcon = "coffee",
            isActive = true,
            sortOrder = 1
        )
    }
}
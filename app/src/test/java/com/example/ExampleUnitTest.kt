package com.example

import com.example.data.model.PauseCategory
import com.example.data.model.PedagogicalData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testActivePausesCountAndCategories() {
        val pauses = PedagogicalData.ACTIVE_PAUSES
        assertEquals(20, pauses.size)

        val categories = pauses.map { it.category }.toSet()
        assertEquals(5, categories.size)
        assertTrue(categories.contains(PauseCategory.ATENCION))
        assertTrue(categories.contains(PauseCategory.MOVIMIENTO))
        assertTrue(categories.contains(PauseCategory.RITMO))
        assertTrue(categories.contains(PauseCategory.COOPERACION))
        assertTrue(categories.contains(PauseCategory.REGULACION))
    }

    @Test
    fun testLikertQuestionsCountAndPolarity() {
        val questions = PedagogicalData.QUESTIONS
        assertEquals(10, questions.size)

        val negativePolarityItems = questions.filter { it.isNegativePolarity }.map { it.number }
        assertEquals(listOf(5, 6), negativePolarityItems)
    }

    @Test
    fun testDigitalArtifactsCount() {
        val artifacts = PedagogicalData.DIGITAL_ARTIFACTS
        assertEquals(8, artifacts.size)
    }

    @Test
    fun testPedagogicalAdoptionRateFormula() {
        val teachersImplemented = 67
        val teachersDownloaded = 98
        val rate = (teachersImplemented.toFloat() / teachersDownloaded.toFloat()) * 100f
        assertTrue(rate in 68.3f..68.5f)
    }
}

package edu.uchicago.brmarcus.fetchassessment.viewModels

import edu.uchicago.brmarcus.fetchassessment.data.Item
import edu.uchicago.brmarcus.fetchassessment.data.Repository
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import org.junit.Test


internal class MainViewModelTest {

    private val repo = mockk<Repository>()
    private val subject = MainViewModel(repo)

    @Test
    fun `nonNullItems returns the correct List`() {
        val fullList = listOf(
            Item(1, 2, ""),
            Item(1, 2, null),
            Item(1, 2, "Item 1"))
        val nonNullList = subject.getNonNullItems(fullList)
        assertEquals(1, nonNullList.size)
        assertEquals(Item(1, 2, "Item 1"), nonNullList.get(0))
    }

    @Test
    fun `sorting an expected list of Items works correcting`() {
        val expectedSortList = listOf(
            Item(1, 1, "item 1"),
            Item(2, 1, "item 2"),
            Item(1, 2, "item 1")
        )
        val unsortedList= arrayListOf(
            Item(2, 1, "item 2"),
            Item(1, 2, "item 1"),
            Item(1, 1, "item 1")
        )

        assertEquals(expectedSortList, subject.customSortItems(unsortedList))
    }

    @Test
    fun `sorting unexpected values works correctly`() {
        val expectedSortList = listOf(
            Item(1, 1, "something weird"),
            Item(2, 1, "item 2"),
            Item(1, 2, "item 1")
        )
        val unsortedList= arrayListOf(
            Item(2, 1, "item 2"),
            Item(1, 2, "item 1"),
            Item(1, 1, "something weird")
        )

        assertEquals(expectedSortList, subject.customSortItems(unsortedList))
    }
}
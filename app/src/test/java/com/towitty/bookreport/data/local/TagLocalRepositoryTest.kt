import com.towitty.bookreport.data.local.FakeTagDao
import com.towitty.bookreport.model.TagEntity
import com.towitty.bookreport.model.emptyTagEntity
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TagLocalRepositoryTest {

    private lateinit var fakeTagDao: FakeTagDao
    private val tagDatabase = mutableListOf<TagEntity>()

    @Before
    fun setUp() {
        fakeTagDao = FakeTagDao(tagDatabase)
    }

    @Test
    fun insertTag_WhenTagAdded_ShouldRetrieveSameTag() = runTest {
        tagDatabase.clear()

        val tag = TagEntity(10, "", 0)

        fakeTagDao.insertTag(tag)

        val getTag = fakeTagDao.getTag(10).firstOrNull()
        assertEquals(tag, getTag)
    }

    @Test
    fun getAllTags_WhenMultipleTagsExist_ShouldReturnAllTags() = runTest {
        fakeTagDao.tagDatabase.clear()
        fakeTagDao.tagDatabase.add(TagEntity(1, "", 0))
        fakeTagDao.tagDatabase.add(TagEntity(2, "", 0))
        fakeTagDao.tagDatabase.add(TagEntity(3, "", 0))

        val tags = fakeTagDao.getAllTags().firstOrNull() ?: emptyList()

        assertEquals(3, tags.size)
        assertTrue(tags.contains(TagEntity(1, "", 0)))
        assertTrue(tags.contains(TagEntity(2, "", 0)))
        assertTrue(tags.contains(TagEntity(3, "", 0)))
    }

    @Test
    fun deleteTagById_WhenTagExists_ShouldRemoveTagFromDatabase() = runTest {
        fakeTagDao.tagDatabase.clear()
        fakeTagDao.tagDatabase.add(TagEntity(1, "", 0))
        fakeTagDao.deleteTag(TagEntity(1, "", 0))

        val deletedTag = fakeTagDao.getTag(1).firstOrNull() ?: emptyTagEntity

        assertEquals(deletedTag, emptyTagEntity)
        assertFalse(fakeTagDao.tagDatabase.contains(TagEntity(1, "", 0)))
    }
}
package desriel.kiki.newsapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import desriel.kiki.newsapp.data.model.BookmarkData

@Dao
interface NewsDao {
    /**
     * bookmark function
     **/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBookmark(bookmarkData: BookmarkData)
    @Query("DELETE FROM table_bookmark WHERE newsId IN (:newsId)")
    fun deleteBookmark(newsId: String?)
    @Query("SELECT * FROM  table_bookmark")
    fun getBookmarkData(): LiveData<List<BookmarkData>>
    @Query("SELECT * FROM table_bookmark WHERE newsId = :newsId")
    fun getBookmarkedNewsById(newsId: String?): LiveData<BookmarkData>

}
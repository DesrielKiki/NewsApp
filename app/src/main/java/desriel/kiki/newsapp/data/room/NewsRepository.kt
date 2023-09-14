package desriel.kiki.newsapp.data.room

import android.app.Application
import androidx.lifecycle.LiveData
import desriel.kiki.newsapp.data.model.BookmarkData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NewsRepository(application: Application) {
    private val dao: NewsDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = NewsDatabase.getDatabase(application)
        dao = db.newsDao()
    }

    /**
     * insert function
     * */

    fun insertBookmark(bookmarkData: BookmarkData) {
        executorService.execute { dao.insertBookmark(bookmarkData) }
    }
    /**
     * delete function
     * */

    fun deleteBookmark(newsId: String?){
        executorService.execute{dao.deleteBookmark(newsId)}
    }
    /**
     * get function
     * */

    fun getBookmarkData(): LiveData<List<BookmarkData>> = dao.getBookmarkData()
    fun getBookmarkDataByNewsId(newsId: String?): LiveData<BookmarkData> = dao.getBookmarkedNewsById(newsId)
}
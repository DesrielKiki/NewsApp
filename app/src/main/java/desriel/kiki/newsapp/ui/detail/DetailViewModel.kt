package desriel.kiki.newsapp.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import desriel.kiki.newsapp.data.room.NewsRepository
import desriel.kiki.newsapp.data.model.BookmarkData

class DetailViewModel(application: Application): AndroidViewModel(application) {
    private val repository: NewsRepository = NewsRepository(application)

    fun insertBookmark(bookmarkData: BookmarkData){
        Log.d("detail viewmodel", "add bookmark")

        repository.insertBookmark(bookmarkData)
    }
    fun deleteBookmark(newsId: String?){
        Log.d("detail viewmodel", "delete bookmark")
        repository.deleteBookmark(newsId)
    }
    fun getBookmarkByNewsId(newsId: String?): LiveData<BookmarkData> = repository.getBookmarkDataByNewsId(newsId)

}
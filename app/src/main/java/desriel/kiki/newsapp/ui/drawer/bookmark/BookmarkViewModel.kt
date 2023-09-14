package desriel.kiki.newsapp.ui.drawer.bookmark

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import desriel.kiki.newsapp.data.room.NewsRepository
import desriel.kiki.newsapp.data.model.BookmarkData

class BookmarkViewModel(application: Application): AndroidViewModel(application)  {
    private val repository: NewsRepository = NewsRepository(application)

    val getBookmarkData: LiveData<List<BookmarkData>> = repository.getBookmarkData()
}
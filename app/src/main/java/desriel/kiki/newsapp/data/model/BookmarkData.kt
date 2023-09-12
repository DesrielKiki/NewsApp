package desriel.kiki.newsapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity("table_bookmark")
@Parcelize
data class BookmarkData (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val newsId: String?,
    var newsTitle: String? = null,
    val createdAt: String? = null,
    val newsCategory: String? = null,
    val content: String? = null,
    val thumb: String? = null,
    var isBookmarked: Boolean
): Parcelable
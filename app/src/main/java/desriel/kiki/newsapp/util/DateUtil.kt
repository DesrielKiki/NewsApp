package desriel.kiki.newsapp.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun String.getTimeAgo(): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("UTC")
    val date = format.parse(this)
    val now = System.currentTimeMillis()
    val diff = kotlin.math.abs(now - date!!.time) / 1000

    return when {
        diff < 60 -> "just now"
        diff < 3600 -> "${diff / 60} minutes ago"
        diff < 86400 -> "${diff / 3600} hours ago"
        else -> "${diff / 86400} days ago"
    }
}
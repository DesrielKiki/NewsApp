package desriel.kiki.newsapp.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.appcompat.app.AppCompatDelegate

class SharedPreference(context:Context) {
    private val preference = context.getSharedPreferences(
        context.packageName,
        MODE_PRIVATE
    )
    private val editor = preference.edit()

    private val keyTheme = "theme"
    private val keyLanguage = "language"

    var language
        get() = preference.getInt(keyLanguage, 0)
        set(value){
            editor.putInt(keyLanguage, value)
            editor.commit()
        }

    var theme
        get() = preference.getInt(keyTheme, 2)
        set(value) {
            editor.putInt(keyTheme, value)
            editor.commit()
        }
    val themeFlag = arrayOf(
        AppCompatDelegate.MODE_NIGHT_NO,
        AppCompatDelegate.MODE_NIGHT_YES,
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM

    )
}
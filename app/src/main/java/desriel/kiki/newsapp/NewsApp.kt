package desriel.kiki.newsapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import desriel.kiki.newsapp.data.SharedPreference

class NewsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val sharedPreference = SharedPreference(this)
        setAppLocale()
        AppCompatDelegate.setDefaultNightMode(sharedPreference.themeFlag[sharedPreference.theme])
    }

    private fun setAppLocale() {
//        val sharedPreference = SharedPreference(this)
//        val selectedLanguageCode = if (sharedPreference.language == 0) "en" else "in" // Sesuaikan dengan kode bahasa yang sesuai
//        Log.d("Application news app", "selected language code  = $selectedLanguageCode")
//        Log.d("Application news app", "selected language id  = ${sharedPreference.language}")
//
//        val locale = Locale(selectedLanguageCode)
//        val resources = resources
//        val configuration = resources.configuration
//        configuration.setLocale(locale)
//        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}
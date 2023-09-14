package desriel.kiki.newsapp.ui.drawer.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import desriel.kiki.newsapp.R
import desriel.kiki.newsapp.data.SharedPreference
import desriel.kiki.newsapp.databinding.ActivitySettingsBinding
import desriel.kiki.newsapp.ui.main.activity.MainActivity
import java.util.Locale

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root

        binding.ThemeSetting.setOnClickListener {
            showThemeSelectionDialog()
        }
        binding.LanguageSetting.setOnClickListener {
            showLanguageSelectionDialog()
        }
        binding.settingToolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        setContentView(view)
    }

    private fun showThemeSelectionDialog() {
        val sharedPreference = SharedPreference(this)
        val themeList = arrayOf("Light", "Dark", "Auto (System Default)")
        var checkedTheme = sharedPreference.theme
        val themeDialog = MaterialAlertDialogBuilder(this)
            .setTitle("Theme")
            .setPositiveButton("Save") { _, _ ->
                sharedPreference.theme = checkedTheme
                AppCompatDelegate.setDefaultNightMode(sharedPreference.themeFlag[checkedTheme])
            }
            .setSingleChoiceItems(themeList, checkedTheme) { _, which ->
                checkedTheme = which
            }
            .setCancelable(false)
        themeDialog.show()
    }
    private fun showLanguageSelectionDialog() {
        val sharedPreference = SharedPreference(this)
        val languageList = arrayOf("English", "Indonesia")
        var checkedLanguage = sharedPreference.language
        val languageDialog = MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.language))
            .setPositiveButton("Save") { _, _ ->
                sharedPreference.language = checkedLanguage
                // Set Locale baru
                val selectedLanguageCode = if (checkedLanguage == 0) "en" else "in" // Sesuaikan dengan kode bahasa yang sesuai
                Log.d("setting activity", "selected language code  = $selectedLanguageCode")
                Log.d("setting activity", "selected language id  = $checkedLanguage")
                val locale = Locale(selectedLanguageCode)
                val resources = resources
                val configuration = resources.configuration
                configuration.setLocale(locale)
                resources.updateConfiguration(configuration, resources.displayMetrics)
                // Restart aktivitas untuk menerapkan perubahan bahasa
                val intent = Intent(this, SettingsActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            .setSingleChoiceItems(languageList, checkedLanguage) { _, which ->
                checkedLanguage = which
            }
            .setCancelable(false)
        languageDialog.show()
    }

}
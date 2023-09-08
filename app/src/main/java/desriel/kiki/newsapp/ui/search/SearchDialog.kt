package desriel.kiki.newsapp.ui.search

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatDialogFragment
import desriel.kiki.newsapp.R

class SearchDialog : AppCompatDialogFragment() {
    private lateinit var searchEditText: EditText
    private lateinit var searchResultsListView: ListView
    private val searchResults = mutableListOf<String>() // Gantilah ini dengan data pencarian yang sesuai

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater: LayoutInflater = requireActivity().layoutInflater
        val view: View = inflater.inflate(R.layout.search_dialog, null)

        searchEditText = view.findViewById(R.id.searchEditText)
        searchResultsListView = view.findViewById(R.id.searchResultsListView)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, searchResults)
        searchResultsListView.adapter = adapter

        builder.setView(view)
            .setTitle("Pencarian")
            .setPositiveButton("cari") {_, _ ->}
            .setNegativeButton("Tutup") { _, _ -> }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Di sini Anda bisa melakukan pencarian dan memperbarui daftar hasil
                // searchResults.clear()
                // searchResults.addAll(Hasil pencarian)
                // adapter.notifyDataSetChanged()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}
        })

        return builder.create()
    }
}




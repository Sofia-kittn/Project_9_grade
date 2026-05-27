package com.example.progectmood.notes

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progectmood.LogMoodActivity
import com.example.progectmood.R
import com.example.progectmood.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.text.Editable
import android.text.TextWatcher

class NotesListActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: NotesAdapter

    private var allNotes: List<Note> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_notes_list)

        val backArrow: ImageButton = findViewById(R.id.back_arrow)
        val searchField: EditText = findViewById(R.id.search_field)

        recycler = findViewById(R.id.recycler_notes)

        recycler.layoutManager = LinearLayoutManager(this)

        backArrow.setOnClickListener {
            finish()
        }

        loadNotes()

        searchField.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

                filterNotes(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun loadNotes() {

        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch(Dispatchers.IO) {

            val notes = db.noteDao().getAll()

            withContext(Dispatchers.Main) {

                allNotes = notes

                adapter = NotesAdapter(notes) { note ->

                    val intent = Intent(
                        this@NotesListActivity,
                        LogMoodActivity::class.java
                    )

                    intent.putExtra("note_id", note.uid)

                    startActivity(intent)
                }

                recycler.adapter = adapter
            }
        }
    }

    private fun filterNotes(query: String) {

        val filtered = allNotes.filter { note ->

            val search = query.lowercase()

            note.note.lowercase().contains(search) ||

                    note.tags.any {
                        it.lowercase().contains(search)
                    }
        }

        adapter = NotesAdapter(filtered) { note ->

            val intent = Intent(
                this@NotesListActivity,
                LogMoodActivity::class.java
            )

            intent.putExtra("note_id", note.uid)

            startActivity(intent)
        }

        recycler.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        loadNotes()
    }
}
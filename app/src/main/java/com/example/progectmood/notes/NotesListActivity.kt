package com.example.progectmood.notes

import android.content.Intent
import android.os.Bundle
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

class NotesListActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_notes_list)

        val backArrow: ImageButton = findViewById(R.id.back_arrow)

        recycler = findViewById(R.id.recycler_notes)

        recycler.layoutManager = LinearLayoutManager(this)

        backArrow.setOnClickListener {
            finish()
        }

        loadNotes()
    }

    private fun loadNotes() {

        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch(Dispatchers.IO) {

            val notes = db.noteDao().getAll()

            withContext(Dispatchers.Main) {

                val adapter = NotesAdapter(notes) { note ->

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

    override fun onResume() {
        super.onResume()

        loadNotes()
    }
}
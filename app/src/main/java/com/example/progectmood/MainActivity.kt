package com.example.progectmood

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.HorizontalScrollView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.progectmood.db.AppDatabase
import com.example.progectmood.notes.NotesListActivity
import com.example.progectmood.stats.HeatmapManager
import com.example.progectmood.stats.HeatmapView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonLog: Button = findViewById(R.id.button_log)
        val buttonNotes: Button = findViewById(R.id.button_notes_list)
        val buttonAddGoal: Button = findViewById(R.id.button_add_goal)

        buttonLog.setOnClickListener {
            val intent = Intent(this, LogMoodActivity::class.java)
            startActivity(intent)
        }

        buttonAddGoal.setOnClickListener {
            val intent = Intent(this, CreateTaskActivity::class.java)
            startActivity(intent)
        }

        buttonNotes.setOnClickListener {

            val intent = Intent(this, NotesListActivity::class.java)

            startActivity(intent)
        }

//        val goalList: RecyclerView = findViewById(R.id.goals_list)
//        val act_goals = arrayListOf<Goal>()

        // надо разобраться как обновлять список дел каждый день в нули
    }

    override fun onResume() {
        super.onResume()

        setupHeatmap()
    }

    private fun setupHeatmap() {

        val heatmapView: HeatmapView =
            findViewById(R.id.heatmap_view)

        val db = AppDatabase.getDatabase(this)
        val scrollView = findViewById<HorizontalScrollView>(R.id.heatmap_scroll)

        lifecycleScope.launch(Dispatchers.IO) {

            val notes = db.noteDao().getAll()

            val heatmapDays =
                HeatmapManager.generateHeatmap(notes)

            withContext(Dispatchers.Main) {
                heatmapView.setData(heatmapDays)

                scrollView.post {
                    scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
                }
            }
        }
    }
}
package com.example.progectmood

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.HorizontalScrollView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progectmood.db.AppDatabase
import com.example.progectmood.goals.CreateGoalActivity
import com.example.progectmood.goals.GoalsAdapter
import com.example.progectmood.notes.NotesListActivity
import com.example.progectmood.stats.HeatmapManager
import com.example.progectmood.stats.HeatmapView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var goalsList: RecyclerView

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
            val intent = Intent(this, CreateGoalActivity::class.java)
            startActivity(intent)
        }

        buttonNotes.setOnClickListener {

            val intent = Intent(this, NotesListActivity::class.java)

            startActivity(intent)
        }

        goalsList = findViewById(R.id.goals_list)
        goalsList.layoutManager = LinearLayoutManager(this)
//        val act_goals = arrayListOf<Goal>()

        // надо разобраться как обновлять список дел каждый день в нули
    }

    override fun onResume() {
        super.onResume()

        setupHeatmap()
        loadGoals()
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

    private fun loadGoals() {

        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch(Dispatchers.IO) {

            val goals = db.goalDao().getAll()

            withContext(Dispatchers.Main) {

                val adapter = GoalsAdapter(

                    goals = goals,

                    onClick = { goal ->

                        val intent = Intent(
                            this@MainActivity,
                            CreateGoalActivity::class.java
                        )

                        intent.putExtra(
                            "goal_id",
                            goal.uid
                        )

                        startActivity(intent)
                    },

                    onCheckedChange = { goal, isChecked ->

                        lifecycleScope.launch(Dispatchers.IO) {

                            db.goalDao().updateGoal(
                                goal.copy(
                                    isCompleted = isChecked
                                )
                            )
                        }
                    }
                )

                goalsList.adapter = adapter
            }
        }
    }

}
package com.example.progectmood

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.progectmood.db.AppDatabase
import com.example.progectmood.notes.NotesListActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonLog: Button = findViewById(R.id.button_log)
        val buttonAddGoal: Button = findViewById(R.id.button_add_goal)
        val buttonNotes: Button = findViewById(R.id.button_notes_list)

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
}
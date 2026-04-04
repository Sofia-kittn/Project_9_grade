package com.example.progectmood

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonLog: Button = findViewById(R.id.button_log)
        val buttonAddGoal: Button = findViewById(R.id.button_add_goal)

        buttonLog.setOnClickListener {
            val intent = Intent(this, LogMoodActivity::class.java)
            startActivity(intent)
        }

        buttonAddGoal.setOnClickListener {
            val intent = Intent(this, createTaskActivity::class.java)
            startActivity(intent)
        }

        val goalList: RecyclerView = findViewById(R.id.goals_list)
        val act_goals = arrayListOf<Goal>()

        // надо разобраться как обновлять список дел каждый день в нули
    }
}
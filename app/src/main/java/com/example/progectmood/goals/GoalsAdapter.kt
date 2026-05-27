package com.example.progectmood.goals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.progectmood.R
import java.text.DateFormat
import java.util.Date

class GoalsAdapter(
    private val goals: List<Goal>,
    private val onClick: (Goal) -> Unit,
    private val onCheckedChange: (Goal, Boolean) -> Unit
) : RecyclerView.Adapter<GoalsAdapter.GoalViewHolder>() {

    class GoalViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title: TextView = view.findViewById(R.id.goal_title)

        val date: TextView = view.findViewById(R.id.text_goal_date)

        val completed: CheckBox = view.findViewById(R.id.goal_checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_goal, parent, false)

        return GoalViewHolder(view)
    }

    override fun getItemCount(): Int = goals.size

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {

        val current = goals[position]

        holder.title.text = current.title

        val formattedDate = DateFormat.getDateInstance()
            .format(Date(current.date))

        holder.date.text = formattedDate

        holder.completed.setOnCheckedChangeListener(null)

        holder.completed.isChecked = current.isCompleted

        holder.completed.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChange(current, isChecked)
        }

        holder.itemView.setOnClickListener {
            onClick(current)
        }
    }
}
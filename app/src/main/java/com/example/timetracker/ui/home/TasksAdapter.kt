package com.example.timetracker.ui.home

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.timetracker.R
import com.example.timetracker.db.UserTask
import kotlin.reflect.KFunction2

class TasksAdapter(var tasks: List<UserTask>, var context: Context, val updateTask: KFunction2<String, Int, Unit>): RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    class TaskViewHolder(view: View): RecyclerView.ViewHolder(view){
        val taskName: TextView = view.findViewById(R.id.taskName)
        val startStop: Button = view.findViewById(R.id.StartStop)
        val timeSpent: TextView = view.findViewById(R.id.timeSpent)
    }
    lateinit var timer: Array<CountDownTimer?>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task, parent, false)
        timer = arrayOfNulls(itemCount)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.count()
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.taskName.text = tasks[position].name
        holder.startStop.backgroundTintList = ColorStateList.valueOf(Color.parseColor(tasks[position].hex))
        val minutes = tasks[position].timeSpend / 60
        val remainingSeconds = tasks[position].timeSpend % 60
        holder.timeSpent.text = String.format("%02d:%02d", minutes, remainingSeconds)
        var currSeconds = tasks[position].timeSpend
        holder.startStop.setOnClickListener{
            if (holder.startStop.text == "Start") {
                timer[position] = object : CountDownTimer(Long.MAX_VALUE, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        currSeconds++
                        val minutes = currSeconds / 60
                        val remainingSeconds = currSeconds % 60
                        holder.timeSpent.text =
                            String.format("%02d:%02d", minutes, remainingSeconds)
                    }

                    override fun onFinish() {
                        // Ничего не делаем, так как таймер будет продолжать работать бесконечно
                    }
                }
                timer[position]?.start()
                holder.startStop.text = "Stop"
            } else {
                timer[position]?.cancel()
                holder.startStop.text = "Start"
                updateTask(tasks[position].name, currSeconds)
            }
        }
    }
}
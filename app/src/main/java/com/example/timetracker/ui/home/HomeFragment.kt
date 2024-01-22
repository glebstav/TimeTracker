package com.example.timetracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timetracker.databinding.FragmentHomeBinding
import com.example.timetracker.db.DBHelper
import java.util.Random

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var db: DBHelper;
    lateinit var userLogin: String;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var dbHelper = context?.let { DBHelper(it, null) }

        if (dbHelper != null) {
            db = dbHelper
            val homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
            userLogin = activity?.intent?.getStringExtra("userLogin").toString()
            _binding = FragmentHomeBinding.inflate(inflater, container, false)
            var taskList = db.getTasks(userLogin).reversed()

            val newNameTask: EditText = binding.newTaskName
            val addTaskBtn: Button = binding.button
            addTaskBtn.setOnClickListener {
                val random = Random()
                val color = String.format("#%06X", 0xFFFFFF and random.nextInt(0x1000000))
                db.addTask(userLogin, newNameTask.text.toString(), color)
                newNameTask.setText("")
                taskList = db.getTasks(userLogin).reversed()
                val taskListView: RecyclerView = binding.taskRecycler
                taskListView.layoutManager = LinearLayoutManager(activity)
                taskListView.adapter = context?.let { TasksAdapter(taskList, it, ::updateTask) }
            }
//        val taskList = arrayListOf<UserTask>()
//        taskList.add(UserTask("edojos", "#99ffcc", 1243))
//        taskList.add(UserTask("123123", "#aaaaaa", 75))
//        taskList.add(UserTask("LolKek", "#ff00ff", 132))
            val taskListView: RecyclerView = binding.taskRecycler
            taskListView.layoutManager = LinearLayoutManager(activity)
            taskListView.adapter = context?.let { TasksAdapter(taskList, it, ::updateTask) }


//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        }
        return binding.root
    }

    fun updateTask(taskName: String, timeSpent: Int) {
        db.updateTask(userLogin, taskName, timeSpent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
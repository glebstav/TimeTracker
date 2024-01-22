package com.example.timetracker.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, "timeTracker", factory,4) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createUsers = "CREATE TABLE users (user_id INT PRIMARY KEY, login TEXT)"
        val createTasks =
            "CREATE TABLE tasks (id INT PRIMARY KEY, user_login TEXT, task_name TEXT, hex TEXT, spend_seconds INT)"
        db!!.execSQL(createUsers)
        db.execSQL(createTasks)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP table if exists users")
        db.execSQL("DROP table if exists tasks")
        onCreate(db)
    }

    fun addUser(login: String) {
        val db = this.writableDatabase
        val newUser = ContentValues()
        newUser.put("login", login)
        db.insert("users", null, newUser)
    }

    fun addTask(userLogin: String, taskName: String, hex: String) {
        val db = this.writableDatabase
        val newTask = ContentValues()
        newTask.put("user_login", userLogin)
        newTask.put("task_name", taskName)
        newTask.put("hex", hex)
        newTask.put("spend_seconds", 0)
        db.insert("tasks", null, newTask)
    }

    @SuppressLint("Range")
    fun getTasks(userLogin: String): List<UserTask> {
        val dataList = mutableListOf<UserTask>()

        val db = this.readableDatabase
        val query = "SELECT * FROM tasks where user_login = '$userLogin'"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val hex = cursor.getString(cursor.getColumnIndex("hex"))
            val name = cursor.getString(cursor.getColumnIndex("task_name"))
            val timeSpend = cursor.getInt(cursor.getColumnIndex("spend_seconds"))

            val dataObject = UserTask(name, hex, timeSpend)
            dataList.add(dataObject)
        }
        cursor.close()
        return dataList
    }

    fun updateTask(userLogin: String, taskName: String, timeSpend:Int) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("spend_seconds", timeSpend)
        db.update("tasks", values, "user_login = ? AND task_name = ?",
            arrayOf(userLogin, taskName))
    }


    fun isUserExist(login: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM users WHERE login = '$login'"
        val cursor = db.rawQuery(query,null)
        return cursor.moveToFirst()
    }




}
package com.appweek05

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.RoundedCornerCompat.Position

class MainActivity : AppCompatActivity() {
    // UI component
    private lateinit var buttonAdd: Button
    private lateinit var buttonClear: Button
    private lateinit var editTextStudent: EditText
    private lateinit var textViewCount: TextView
    private lateinit var listView: ListView

    //Collection
    private lateinit var studentList: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>

    companion object{
        private const val TAG = "KotlinWeek06App"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate : AppWeek05 started")
        // functions
        setupViews()
        setupListViews()
        setupListeners() 
        addInitialData()
    }
    private fun setupViews(){
        listView = findViewById(R.id.listViewStudents) //activity_main에 id 이름 확인.
        editTextStudent = findViewById(R.id.editTextStudent)
        buttonClear = findViewById(R.id.buttonClear)
        buttonAdd = findViewById(R.id.buttonAdd)
        textViewCount = findViewById(R.id.textViewCount)

        studentList = ArrayList()
        Log.d(TAG, "Views initialized")
    }
    private fun setupListViews(){
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, studentList)
        listView.adapter = adapter
        Log.d(TAG, "ListViews and Adapter setup completed")
    }
    private fun setupListeners(){
        buttonAdd.setOnClickListener{
            addStudent()
        }
        buttonClear.setOnClickListener{
            clearAllStudents()
        }
        listView.setOnItemLongClickListener { _, _, position, _ -> removeStudent(position)
            true
        }
        listView.setOnItemClickListener { _, _, position, _ ->
            val studentName = studentList[position]
            Toast.makeText(
                this,
                "Selected: $studentName (Position: ${position+1})",
                Toast.LENGTH_SHORT //짧게 떠 있는 것.
            ).show()
            Log.d(TAG, "Selected: $studentName at Position: ${position})")
        }
        Log.d(TAG, "Event listeners setup complted")
    } //이벤트 처리(버튼이 길게 눌렸거나 짧게 눌렸을 때)

    private fun addStudent(){
        val studentName = editTextStudent.text.toString().trim()

        if(studentName.isEmpty()){
            Toast.makeText(this, "Please enter a student name", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Attempted to add empty student name")
            return
        }
        if(studentList.contains(studentName)){ //중복된 이름
            Toast.makeText(this, "Student '$studentName' already exists", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Attempted to add duplicat student : $studentName")
            return
        }

        studentList.add(studentName)
        adapter.notifyDataSetChanged() //내용이 바뀜 -> 화면 업데이트
        editTextStudent.text.clear() //위젯 입력 상자 지워줌
        updateStudentCount()
        Toast.makeText(this, "Added: $studentName", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Added student: $studentName (Total: ${studentList.size})") //화면에 학생 이름, 전체 학생 수 출력
    }
    private fun clearAllStudents(){ //전체 학생 삭제
        if(studentList.isEmpty()){
            Toast.makeText(this, "List is already empty", Toast.LENGTH_SHORT).show()
            return
        }
        val count = studentList.size //얼마나 삭제 했나 보기위해
        studentList.clear() //실제로 삭제
        adapter.notifyDataSetChanged()
        updateStudentCount()
        Toast.makeText(this, "Cleared all $count student", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Cleared all student (Total cleared: $count)")
    }
    private fun removeStudent(position: Int){ //특정 학생 삭제(선택한 리스트 삭제)
        if(position >= 0 && position < studentList.size){
            val removedStudent = studentList.removeAt(position)
            adapter.notifyDataSetChanged()
            updateStudentCount()

            Toast.makeText(this,"Removed: $removedStudent", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Removed student: $removedStudent (Remaining: ${studentList.size}")
        }
    }

    private fun updateStudentCount(){
        textViewCount.text = "Total Students : ${studentList.size}"
    }


    private fun addInitialData(){
        val initialStudents = listOf("Kim","Lee","Park")
        studentList.addAll(initialStudents)
        adapter.notifyDataSetChanged()
        updateStudentCount()
        Log.d(TAG, "Added initial data: $initialStudents")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Current student count: ${studentList.size}")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Saving state with ${studentList.size} students")
    }
}
package com.appweek09

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appweek09.data.Student
import com.appweek09.databinding.ItemStudentBinding

class StudentAdapter(
    private val studentList: List<Student>,
    private val onItemClick:(Student, Int) -> Unit, //입력만 있고, 출력은 없음 (리턴타입이 없는 형태)
    private val onItemLongClick: (Int) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudetViewHolder>(){
    inner class StudetViewHolder(
        private val binding: ItemStudentBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(student: Student){
            binding.apply { //바인딩 한 것을 inner 클래스 안에서 묶고 있는 작업.
                textViewName.text = student.name
                textViewDept.text = student.department
                textViewGrade.text = student.grade
                textViewEmail.text = student.email

                root.setOnClickListener{ //함수 call
                    onItemClick(student, adapterPosition)
                }
                root.setOnLongClickListener{
                    onItemLongClick(adapterPosition)
                    true
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudetViewHolder {
        val binding = ItemStudentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StudetViewHolder(binding)
    }


    override fun onBindViewHolder(holder: StudetViewHolder, position: Int) {
        holder.bind(studentList[position])
    }
    override fun getItemCount() = studentList.size
}
package com.appweek09.data

import java.util.Date
import java.util.UUID

data class Student(
    val name: String,
    val id: String = UUID.randomUUID().toString(),
    val department: String = "Computer Science",
    val grade: String = "2nd year",
    val email: String = "",
    val addedDate: Date = Date()
) {
    override  fun toString():String = name //한 줄일때, 리턴만 할 경우

//    override  fun toString(): String{
//        return name
//    }
}
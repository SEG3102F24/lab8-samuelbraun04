package com.example.employee.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "employee")
data class Employee(
    var employeeNumber: Int,
    var firstName: String,
    var lastName: String,
    var email: String,
    var position: String?,
    var salary: Float?
) {
    @Id
    var id: String = ""
}

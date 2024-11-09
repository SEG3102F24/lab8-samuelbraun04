package com.example.employee.resolvers.types

data class CreateEmployeeInput(
    val employeeNumber: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val position: String?,
    val salary: Float?
)

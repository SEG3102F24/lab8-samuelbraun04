package com.example.employee.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import com.example.employee.entity.Employee

@Repository
interface EmployeeRepository : MongoRepository<Employee, String> {
    fun findByEmployeeNumber(employeeNumber: Int): Employee?
}

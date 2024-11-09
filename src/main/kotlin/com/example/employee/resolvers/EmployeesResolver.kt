package com.example.employee.resolvers

import com.example.employee.entity.Employee
import com.example.employee.repository.EmployeeRepository
import com.example.employee.resolvers.types.CreateEmployeeInput
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class EmployeesResolver(private val employeeRepository: EmployeeRepository) {

    @QueryMapping
    fun employees(): List<Employee> {
        return employeeRepository.findAll()
    }

    @QueryMapping
    fun employeeById(@Argument id: String): Employee? {
        return employeeRepository.findById(id).orElse(null)
    }

    @QueryMapping
    fun employeeByNumber(@Argument employeeNumber: Int): Employee? {
        return employeeRepository.findByEmployeeNumber(employeeNumber)
    }

    @MutationMapping
    fun newEmployee(@Argument createEmployeeInput: CreateEmployeeInput): Employee {
        val employee = Employee(
            employeeNumber = createEmployeeInput.employeeNumber,
            firstName = createEmployeeInput.firstName,
            lastName = createEmployeeInput.lastName,
            email = createEmployeeInput.email,
            position = createEmployeeInput.position,
            salary = createEmployeeInput.salary
        )
        employee.id = UUID.randomUUID().toString()
        return employeeRepository.save(employee)
    }

    @MutationMapping
    fun deleteEmployee(@Argument id: String): Boolean {
        return if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    @MutationMapping
    fun updateEmployee(@Argument id: String, @Argument createEmployeeInput: CreateEmployeeInput): Employee? {
        val employee = employeeRepository.findById(id).orElse(null) ?: return null
        employee.employeeNumber = createEmployeeInput.employeeNumber
        employee.firstName = createEmployeeInput.firstName
        employee.lastName = createEmployeeInput.lastName
        employee.email = createEmployeeInput.email
        employee.position = createEmployeeInput.position
        employee.salary = createEmployeeInput.salary
        return employeeRepository.save(employee)
    }
}

package seg3x02.employeeGql.resolvers

import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeeRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
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

    @MutationMapping
    fun newEmployee(@Argument createEmployeeInput: CreateEmployeeInput): Employee {
        val employee = Employee(
            name = createEmployeeInput.name ?: "Unknown",
            dateOfBirth = createEmployeeInput.dateOfBirth ?: "N/A",
            city = createEmployeeInput.city ?: "Unknown",
            salary = createEmployeeInput.salary ?: 0.0f,
            gender = createEmployeeInput.gender ?: "Not Specified",
            email = createEmployeeInput.email ?: "No Email"
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
        val existingEmployee = employeeRepository.findById(id).orElse(null) ?: return null

        val updatedEmployee = existingEmployee.copy(
            name = createEmployeeInput.name ?: existingEmployee.name,
            dateOfBirth = createEmployeeInput.dateOfBirth ?: existingEmployee.dateOfBirth,
            city = createEmployeeInput.city ?: existingEmployee.city,
            salary = createEmployeeInput.salary ?: existingEmployee.salary,
            gender = createEmployeeInput.gender ?: existingEmployee.gender,
            email = createEmployeeInput.email ?: existingEmployee.email
        )

        return employeeRepository.save(updatedEmployee)
    }
}

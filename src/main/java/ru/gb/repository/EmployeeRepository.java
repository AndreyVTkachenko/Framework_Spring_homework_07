package ru.gb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

package com.example.carpark.repository;

import com.example.carpark.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;

@Transactional
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    @Query(value = "select * from employee e", nativeQuery = true)
    Page<Employee> getAll(Pageable pageable);

    @Query(value = "select * from employee e where e.employee_id = ?1",
            nativeQuery = true)
    Employee getEmpById(Long id);

    @Query(value = "select * from employee ORDER BY employee_id DESC LIMIT 1",
            nativeQuery = true)
    Employee getInsertedEmployee();

    @Query(value = "select * from employee e where e.employee_name like lower(concat('%', ?1,'%'))", nativeQuery = true)
    Page<Employee> searchByName(String name, Pageable pageable);

    @Modifying
    @Query(value = "insert into employee(`account`, department, employee_address, employee_birthdate, employee_email, employee_name, employee_phone, `password`, sex) values(?1,?2,?3,?4,?5,?6,?7,?8,?9)",
            nativeQuery = true)
    void insertEmployee(String account, String department, String address, LocalDate dob, String email, String name, String phone, String password, String sex);

    @Modifying
    @Query(value = "update Employee set employee_name = ?1, employee_phone = ?2, employee_birthdate = ?3, sex = ?4, employee_address = ?5, employee_email = ?6, `account` = ?7, `password` = ?8, department = ?9 where employee_id = ?10", nativeQuery = true)
    void updateEmployee(String name, String phone, LocalDate dob, String sex, String address, String email, String account, String password, String department, Long id);

    @Modifying
    @Query(value = "delete from employee where employee_id = ?1", nativeQuery = true)
    void deleteEmpById(Long id);

}

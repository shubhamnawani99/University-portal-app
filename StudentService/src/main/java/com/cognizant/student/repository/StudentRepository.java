package com.cognizant.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.student.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

}

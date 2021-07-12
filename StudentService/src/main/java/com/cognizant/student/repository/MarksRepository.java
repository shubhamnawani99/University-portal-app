package com.cognizant.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.student.model.Marks;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Integer> {

	public List<Marks> findByStudentIdAndSemester(String studentId, Integer semester);
}

package com.cognizant.student.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognizant.student.model.Marks;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Integer> {

	public List<Marks> findByStudentIdAndSemester(String studentId, Integer semester);
	
	@Query(value = "SELECT MAX(SEMESTER) FROM marks m WHERE m.student_id = ?1", nativeQuery = true)
	public Optional<Integer> findMaxSemester(String student_id);
}

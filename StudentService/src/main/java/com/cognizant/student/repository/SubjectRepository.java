package com.cognizant.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.student.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {

}

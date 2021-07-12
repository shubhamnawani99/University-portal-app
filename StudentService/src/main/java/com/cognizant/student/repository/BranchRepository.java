package com.cognizant.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.student.model.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {

}

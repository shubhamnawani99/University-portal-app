package com.cognizant.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.student.model.Branch;
import com.cognizant.student.repository.BranchRepository;

/**
 * Branch Service for branch entity related operations
 * @author Shubham Nawani
 *
 */
@Service
public class BranchServiceImpl implements IBranchService {

	@Autowired
	private BranchRepository brepo;

	@Override
	public Branch addBranch(Branch branch) {
		return brepo.save(branch);
	}

}

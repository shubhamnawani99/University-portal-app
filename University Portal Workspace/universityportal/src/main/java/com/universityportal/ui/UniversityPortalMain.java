package com.universityportal.ui;

import com.universityportal.userservices.concrete.LoginServiceImpl;
import com.universityportal.userservices.interfaces.LoginService;

/**
 * 
 * @author Sunmeet
 *
 */
public class UniversityPortalMain {
	public static void main(String[] args) {
		LoginService loginService = new LoginServiceImpl();
		loginService.showMenu();
	}
}

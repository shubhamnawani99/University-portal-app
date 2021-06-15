package com.universityportal.userservices.concrete;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;

import com.universityportal.bean.Admin;
import com.universityportal.bean.Student;
import com.universityportal.database.dao.LoginDAO;
import com.universityportal.exceptions.DatabaseException;
import com.universityportal.exceptions.FileReadException;
import com.universityportal.userservices.interfaces.AdminService;
import com.universityportal.userservices.interfaces.LoginService;
import com.universityportal.userservices.interfaces.StudentService;
import com.universityportal.utils.Log;
import com.universityportal.utils.ShowOptions;

/**
 * Implements the LoginService and handles login
 * 
 * @author Sunmeet
 *
 */
public class LoginServiceImpl implements LoginService {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("Bundle");

	/**
	 * Show the main welcome menu to choose role from student or admin
	 */
	@Override
	public void showMenu() {
		ShowOptions.showHeader("Welcome");
		int choice = ShowOptions.showOptions("Student", "Admin", "Exit");
		switch (choice) {
		case 1:
			showStudentLogin();
			break;
		case 2:
			showAdminLogin();
			break;
		case 3:
			break;
		default:
			// shouldn't be called
			Log.getLogger().log(Level.SEVERE, "Show options returned invalid result");
		}
	}

	/**
	 * Gets student login details and validates the same
	 */
	private void showStudentLogin() {
		ShowOptions.showHeader(bundle.getString("student_login"));
		String[] response = getUsernameAndPassword();
		String username = response[0];
		String password = response[1];

		try {
			LoginDAO loginDao = new LoginDAO();
			Student s = loginDao.validateStudentCredentials(username, password);
			if (s != null) {
				// login success
				System.out.println("Student login Successful!");
				StudentService studentService = new StudentServiceImpl(s);
				studentService.showMenu(s);

			} else {
				// login failure
				showErrorMenu("invalid_credentials_mesage", "student");
			}
		} catch (FileReadException | DatabaseException e) {
			Log.getLogger().log(Level.SEVERE, e.getMessage());
			showErrorMenu("backend_error", "student");
		}
	}

	/**
	 * Gets admin login details and validates the same
	 */
	private void showAdminLogin() {
		ShowOptions.showHeader(bundle.getString("admin_login"));
		String[] response = getUsernameAndPassword();
		String username = response[0];
		String password = response[1];

		try {
			LoginDAO loginDao = new LoginDAO();
			Admin a = loginDao.validateAdminCredentials(username, password);
			if (a != null) {
				// login success
				System.out.println("Admin login Successful!");
				AdminService adminService = new AdminServiceImpl();
				adminService.adminMain();
			} else {
				// login failure
				showErrorMenu("invalid_credentials_mesage", "admin");
			}
		} catch (FileReadException | DatabaseException e) {
			Log.getLogger().log(Level.SEVERE, e.getMessage());
			showErrorMenu("backend_error", "admin");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display and get username and password from the user
	 * 
	 * @return String array with two values username and password
	 */
	private String[] getUsernameAndPassword() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter username:");
		String username = sc.nextLine();
		System.out.println("Enter password");
		String password = sc.nextLine();
		return new String[] { username, password };
	}

	/**
	 * Notifies about invalid credentials, backend error and provides further
	 * options
	 */
	private void showErrorMenu(String messageKey, String caller) {
		System.out.println(bundle.getString(messageKey));

		int choice = ShowOptions.showOptions("Retry", "Exit");
		switch (choice) {
		case 1:
			if (caller.equals("student"))
				showStudentLogin();
			else if (caller.equals("admin"))
				showAdminLogin();
			break;
		case 2:
			break;
		default:
			// shouldn't be called
			Log.getLogger().log(Level.SEVERE, "Show options returned invalid result");
		}
	}

}

/**
 * 
 */
package com.universityportal.userservices.concrete;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.universityportal.bean.Marks;
import com.universityportal.database.dao.AdminDAO;
import com.universityportal.exceptions.DatabaseException;
import com.universityportal.exceptions.FileReadException;
import com.universityportal.userservices.interfaces.AdminService;
import com.universityportal.utils.Log;
import com.universityportal.utils.ShowOptions;

/**
 * @author whoami
 * 
 */
public class AdminServiceImpl implements AdminService {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("Bundle");

	@Override
	public void adminMain() throws IOException {

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		AdminDAO adminDao = new AdminDAO();
		ArrayList<Marks> list = new ArrayList<Marks>();
		FileReader fr = null;
		BufferedReader br = null;
		System.out.println("\t\t\t\tInsert Data!");
		System.out.println("Enter path of the file to enter data from: ");
		String path = sc.nextLine();
		try {

			fr = new FileReader(new File(path));
			br = new BufferedReader(fr);
			String line;
			String[] arr = null;
			while ((line = br.readLine()) != null) {
				arr = line.trim().split("\\s*,\\s*");
				boolean check = ContainCheck(arr);
				if (check) {
					Marks mark = new Marks(Integer.parseInt(arr[0]), arr[1], Integer.parseInt(arr[2]), arr[3],
							Integer.parseInt(arr[4]));
					list.add(mark);
				}
			}
			boolean flag = adminDao.insertRecord(list);
			if (flag) {
				System.out.println("Insertion successfull!!");

			}

		} catch (FileNotFoundException e) {
			InvalidFilePath();
		} catch (IOException e) {
			// TODO: handle exception
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void showErrorMenu(String msg) throws IOException {
		System.out.println(String.format(bundle.getString(msg)));
		int choice = ShowOptions.showOptions("Retry", "Exit");
		switch (choice) {
		case 1:
			adminMain();
			break;
		case 2:
			break;
		default:
			// shouldn't be called
			Log.getLogger().log(Level.SEVERE, "Show options returned invalid result");
		}

	}

	private void InvalidFilePath() throws IOException {
		Log.getLogger().log(Level.SEVERE, String.format(bundle.getString("file_path_error")));
		showErrorMenu("file_path_error");
	}

	public boolean ContainCheck(String[] arr) {

		Pattern pattern;
		Matcher match;

		pattern = Pattern.compile("[0-9]+");
		match = pattern.matcher(arr[0]);
		if (!match.matches()) {
			String log = String.format(bundle.getString("marks_format"));
			Log.getLogger().log(Level.SEVERE, log);
			System.out.println(arr[0] + " : " + log);
			return false;
		}

		// check if student id length is 11

		if (arr[1].length() != 11) {
			String log = String.format(bundle.getString("studentid_length"));
			Log.getLogger().log(Level.SEVERE, log);
			System.out.println(arr[1] + " : " + log);
			return false;
		}

		// check if student id contain only numbers

		pattern = Pattern.compile("[0-9]+");
		match = pattern.matcher(arr[1]);
		if (!match.matches()) {
			String log = String.format(bundle.getString("studentid_format"));
			Log.getLogger().log(Level.SEVERE, log);
			System.out.println(arr[1] + " : " + log);
			return false;
		}

		// check semster is in range

		int sem = Integer.parseInt(arr[2]);
		if (sem < 1 || sem > 8) {
			String log = String.format(bundle.getString("semster_range"));
			Log.getLogger().log(Level.SEVERE, log);
			System.out.println(arr[2] + " : " + log);
			return false;
		}

		// check if subject id length is 8

		if (arr[3].length() != 8) {
			String log = String.format(bundle.getString("subjectid_length"));
			Log.getLogger().log(Level.SEVERE, log);
			System.out.println(arr[3] + " : " + log);
			return false;
		}

		// check if subject id is in proper format

		pattern = Pattern.compile("^([A-Z]{4}-[0-9]{3})$");
		match = pattern.matcher(arr[3]);
		if (!match.matches()) {
			String log = String.format(bundle.getString("subjectid_format"));
			Log.getLogger().log(Level.SEVERE, log);
			System.out.println(arr[3] + " : " + log);
			return false;
		}

		// check is marks is in range

		int mark = Integer.parseInt(arr[4]);
		if (mark <= 0 || mark > 100) {
			String log = String.format(bundle.getString("marks_range"));
			Log.getLogger().log(Level.SEVERE, log);
			System.out.println(arr[4] + " : " + log);
			return false;
		}

		return true;

	}

}

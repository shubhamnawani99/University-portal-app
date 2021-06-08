package com.universityportal.utils;


import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * Class used for displaying consistent ui
 * @author Sunmeet
 *
 */
public class ShowOptions {
	
	private static final ResourceBundle bundle = ResourceBundle.getBundle("Bundle");

	private ShowOptions() {}
	
	/**
	 * Shows an option menu and handles invalid inputs
	 * @param options String options to be displayed
	 * @return valid choice or -1 if no options are passed
	 */
	public static int showOptions(String... options) {
		if (options.length == 0)
			return -1;
		System.out.println("Select the appropriate option:");
		for (int i = 0; i < options.length; i++) {
			System.out.printf("%d. %s%n", i+1, options[i]);
		}
		try {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			int choice = Integer.parseInt(sc.nextLine());
			if(choice>0 && choice<=options.length) {
				return choice;
			}else {
				// if invalid notfiy and retry
				invalidOptionSelected();
				return showOptions(options);
			}
		}catch(NumberFormatException e) {
			// if invalid notfiy and retry
			invalidOptionSelected();
			return showOptions(options);
		}
	}
	
	/**
	 * Logs and notifies the user if invalid option is selected
	 */
	private static void invalidOptionSelected() {
		System.out.println(bundle.getString("invalid_option_selected"));
		Log.getLogger().log(Level.INFO, bundle.getString("invalid_option_selected"));
	}
	
	/**
	 * Show the main header with a subheading
	 * @param subheading String subheading to be displayed under college name
	 */
	public static void showHeader(String subheading) {
		System.out.println("\t\t\t" + bundle.getString("college_name"));
		System.out.println("\t\t\t\t" + subheading);
	}
}

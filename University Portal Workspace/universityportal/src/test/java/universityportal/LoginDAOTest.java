package universityportal;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.universityportal.database.dao.LoginDAO;
import com.universityportal.exceptions.DatabaseException;
import com.universityportal.exceptions.FileReadException;

public class LoginDAOTest {

	private LoginDAO dao;

	@Before
	public void startup() throws FileReadException, DatabaseException {
		dao = new LoginDAO();
	}

	@Test
	public void validateStudentCredentials_validLogin() {
		assertNotNull(dao.validateStudentCredentials("00714802717", "password"));
	}

	@Test
	public void validateStudentCredentials_wrongUsername() {
		assertNull(dao.validateStudentCredentials("00714802713", "password"));
	}

	@Test
	public void validateStudentCredentials_usernameMorethan11char() {
		assertNull(dao.validateStudentCredentials("00714802713154", "password"));
	}

	@Test
	public void validateStudentCredentials_wrongPassword() {
		assertNull(dao.validateStudentCredentials("00714802717", "adminpass@1234"));
	}
	
	@Test
	public void validateStudentCredentials_nullUsername() {
		assertNull(dao.validateStudentCredentials(null, "adminpass@1234"));
	}

	@Test
	public void validateAdminCredentials_validLogin() {
		assertNotNull(dao.validateAdminCredentials("916830", "adminpass@1234"));
	}

	@Test
	public void validateAdminCredentials_wrongUsername() {
		assertNull(dao.validateAdminCredentials("916831", "adminpass@1234"));
	}

	@Test
	public void validateAdminCredentials_usernameMorethan20char() {
		assertNull(dao.validateAdminCredentials("916830916830916830916830", "adminpass@1234"));
	}

	@Test
	public void validateAdminCredentials_wrongPassword() {
		assertNull(dao.validateAdminCredentials("916830", "admin@1234"));
	}

}

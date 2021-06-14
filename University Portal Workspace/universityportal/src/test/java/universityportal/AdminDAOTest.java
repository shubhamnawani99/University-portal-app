/**
 * 
 */
package universityportal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.universityportal.bean.Marks;
import com.universityportal.database.dao.AdminDAO;
import com.universityportal.exceptions.DatabaseException;
import com.universityportal.exceptions.FileReadException;
import com.universityportal.userservices.concrete.AdminServiceImpl;

/**
 * @author whoami, Shubham Nawani
 *
 */
public class AdminDAOTest {

	private AdminDAO dao;
	private ArrayList<Marks> list = new ArrayList<Marks>();
	private ArrayList<Marks> emptylist = new ArrayList<Marks>();
	static AdminServiceImpl adminService;

	@Before
	public void setup() throws DatabaseException, FileReadException {

		adminService = new AdminServiceImpl();
		dao = new AdminDAO();
		Marks mark = new Marks(16, "06714802717", 1, "ETCS-303", 90);
		list.add(mark);
	}

	@Test
	public void testInsertRecord_null() throws DatabaseException, FileReadException {

		assertFalse(dao.insertRecord(emptylist));
	}

	@Test
	public void testInsertRecord_notnull() throws DatabaseException, FileReadException {
		assertTrue(dao.insertRecord(list));
	}

	@Test
	public void testInsertRecord_MarksOutOfRange() {
		String[] record_MarksOutOfRange = { "16", "06714802717", "1", "ETCS-303", "110" };
		assertFalse(adminService.ContainCheck(record_MarksOutOfRange));
	}

	@Test
	public void testInsertRecord_NegativeMarks() {
		String[] record_NegativeMarks = { "16", "06714802717", "1", "ETCS-303", "-10" };
		assertFalse(adminService.ContainCheck(record_NegativeMarks));
	}

	@Test
	public void testInsertRecord_SemesterOutOfRange() {
		String[] record_SemesterOutOfRange = { "16", "06714802717", "9", "ETCS-303", "90" };
		assertFalse(adminService.ContainCheck(record_SemesterOutOfRange));
	}

	@Test
	public void testInsertRecord_NegativeSemester() {
		String[] record_NegativeSemester = { "16", "06714802717", "-1", "ETCS-303", "90" };
		assertFalse(adminService.ContainCheck(record_NegativeSemester));
	}

	@Test
	public void testInsertRecord_InvalidUsername() {
		String[] record_InvalidUsername = { "16", "067148027171234", "1", "ETCS-303", "90" };
		assertFalse(adminService.ContainCheck(record_InvalidUsername));
	}

	@Test
	public void testInsertRecord_InvalidCourseNameLength() {
		String[] record_InvalidCourseNameLength = { "16", "06714802717", "1", "ETCS-3033", "90" };
		assertFalse(adminService.ContainCheck(record_InvalidCourseNameLength));
	}

	@Test
	public void testInsertRecord_InvalidCourseNameFormat() {
		String[] record_InvalidCourseNameFormat = { "16", "06714802717", "1", "ETCS:303", "90" };
		assertFalse(adminService.ContainCheck(record_InvalidCourseNameFormat));
	}

}
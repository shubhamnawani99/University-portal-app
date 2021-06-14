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

/**
 * @author whoami
 *
 */
public class AdminDAOTest {

	private AdminDAO dao;
	private ArrayList<Marks> list = new ArrayList<Marks>();
	private ArrayList<Marks> emptylist = new ArrayList<Marks>();

	@Before
	public void setup() throws DatabaseException, FileReadException {

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

}

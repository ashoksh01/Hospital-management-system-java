package testing;

import Hospital.LoginPage;
import Hospital.Admitdasboard;
//import Hospital.Staff_Registration;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.sql.ResultSet;
import org.junit.Before;
import org.junit.Test;

public class Database {
	LoginPage obj;

	Admitdasboard hd;

	@Before
	public void setUp() {
		obj = new LoginPage();

		hd = new Admitdasboard();
	}

	@Test
	public void test_getDoctors() {
		boolean actual = obj.loginnn1("thapa", "1234", "Doctor");
		assertTrue(actual);
	}

	@Test
	public void test_table() {
		boolean actual = hd.table_update();
		assertTrue(actual);
	}

}
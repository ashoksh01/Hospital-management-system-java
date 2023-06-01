package Hospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	public static Connection conn;

	public DbConnection() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/hospitalcw?" + "user=root&password=root");
		} catch (SQLException ex) {

			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
}

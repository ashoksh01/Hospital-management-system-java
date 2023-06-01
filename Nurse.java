package Hospital;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.mysql.cj.jdbc.result.ResultSetMetaData;

import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

public class Nurse extends JFrame {
	private JPanel contentPane;
	private JTable table;
	DbConnection DB = new DbConnection();
	PreparedStatement st;
	public int nurse_id;
	private JTable table_1;
	private JTable table_keydate;

//	=============== Application Lunch ==================

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Nurse frame = new Nurse(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//	=============== Create Frame ==================

	public Nurse(String id) {
		nurse_id = Integer.parseInt(id);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1161, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Nurse Dashboard");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
		lblNewLabel.setBounds(20, 26, 271, 39);
		contentPane.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Patient Details", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 104, 1101, 279);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 27, 1048, 230);
		panel.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "First Name", "Last Name", "DOB",
				"Med History", "Bed Number", "Ward", "Status", "Doctor Name" }

		));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Medicine Time Table of Patient", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 393, 516, 255);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 30, 488, 215);
		panel_1.add(scrollPane_1);

		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		table_1.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Firstname", "Lastname", "Medicine Name", "Medicine Time" }));

		JLabel lblHospitalManagementSystem = new JLabel("Hospital Management System");
		lblHospitalManagementSystem.setForeground(new Color(0, 0, 0));
		lblHospitalManagementSystem.setFont(new Font("Times New Roman", Font.BOLD, 34));
		lblHospitalManagementSystem.setBounds(287, 26, 557, 62);
		contentPane.add(lblHospitalManagementSystem);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(240, 240, 240));
		panel_2.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Assign Key Date", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(536, 393, 572, 255);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 28, 552, 217);
		panel_2.add(scrollPane_2);

		table_keydate = new JTable();
		scrollPane_2.setViewportView(table_keydate);
		table_keydate.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "First Name", "LastName", "TreatmentDate", "Treatment", "Doctor Name" }));
		update_keydate_for_individual_nurse();
		assign_individual_patient_nurse();
		assign_individual_patientmedicine_nurse();
	}

	public void assign_individual_patient_nurse() {

		try {
			String sql = "select a.bed_no,a.ward,a.status, p.firstname,p.lastname,p.dos,p.medical_history,p.description,r.firstname as doctor_name from admit a\r\n"
					+ "inner join patient_registration p on\r\n" + "p.id = a.patient_registration_id\r\n"
					+ "inner join user_registration r on\r\n" + "r.id=p.doctor_id\r\n" + "where nurse_id=?;";

			st = DB.conn.prepareStatement(sql);
			st.setInt(1, nurse_id);

			ResultSet rs = st.executeQuery();
			ResultSetMetaData stdata = (ResultSetMetaData) rs.getMetaData();
			int q = stdata.getColumnCount();
			DefaultTableModel recordtable = (DefaultTableModel) table.getModel();
			recordtable.setRowCount(0);
			while (rs.next()) {
				Vector columnData = new Vector();
				for (int i = 0; i <= q; i++) {

					columnData.add(rs.getString("firstname"));
					columnData.add(rs.getString("lastname"));
					columnData.add(rs.getString("dos"));

					columnData.add(rs.getString("medical_history"));
					columnData.add(rs.getString("bed_no"));
					columnData.add(rs.getString("ward"));
					columnData.add(rs.getString("status"));
					columnData.add(rs.getString("doctor_name"));
					System.out.println();
				}
				recordtable.addRow(columnData);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}

	}

//	=============== Assign Petient medicine to Nurse ==================

	public void assign_individual_patientmedicine_nurse() {

		try {
			String sql = "select m.medicine_name,m.time as medicine_time,p.firstname,p.lastname from assign_medicine_tbl m\r\n"
					+ "inner join patient_registration p on\r\n" + "m.patient_registration_id=p.id\r\n"
					+ " where p.nurse_id=?";

			st = DB.conn.prepareStatement(sql);
			st.setInt(1, nurse_id);

			ResultSet rs = st.executeQuery();
			ResultSetMetaData stdata = (ResultSetMetaData) rs.getMetaData();
			int q = stdata.getColumnCount();
			DefaultTableModel recordtable = (DefaultTableModel) table_1.getModel();
			recordtable.setRowCount(0);
			while (rs.next()) {
				Vector columnData = new Vector();
				for (int i = 0; i <= q; i++) {

					columnData.add(rs.getString("firstname"));
					columnData.add(rs.getString("lastname"));
					columnData.add(rs.getString("medicine_name"));
					columnData.add(rs.getString("medicine_time"));
					System.out.println();
				}
				recordtable.addRow(columnData);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}

	public void update_keydate_for_individual_nurse() {

		try {
			String sql = "select  kdt.date, kdt.treatment, p.firstname, p.lastname,r.firstname as doctor_name from key_date_tbl  as kdt\r\n"
					+ "				inner join patient_registration p on\r\n"
					+ "            		kdt.patient_registration_id=p.id\r\n"
					+ "                    inner join user_registration r on\r\n"
					+ "                    r.id=p.doctor_id\r\n" + "            		where p.nurse_id=?";

			st = DB.conn.prepareStatement(sql);
			st.setInt(1, nurse_id);

			ResultSet rs = st.executeQuery();
			ResultSetMetaData stdata = (ResultSetMetaData) rs.getMetaData();
			int q = stdata.getColumnCount();
			DefaultTableModel recordtable = (DefaultTableModel) table_keydate.getModel();
			recordtable.setRowCount(0);
			while (rs.next()) {
				Vector columnData = new Vector();
				for (int i = 0; i <= q; i++) {

					columnData.add(rs.getString("firstname"));
					columnData.add(rs.getString("lastname"));
					columnData.add(rs.getString("date"));
					columnData.add(rs.getString("treatment"));
					columnData.add(rs.getString("doctor_name"));
					System.out.println();
				}
				recordtable.addRow(columnData);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}

	}

}
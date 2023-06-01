package Hospital;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.border.EtchedBorder;

public class Admitdasboard extends JFrame {
	private JTable table;
	private JPanel contentPane;
	JComboBox comboBox_ward_1;
	JComboBox comboBox_status;
	JComboBox comboBox_bedno;
	DbConnection connection = new DbConnection();
	PreparedStatement st;
	int q, i, id, deleteItem;
	JButton btn_updatepatient;
	public String admit_id;

//	=======================  Application Lunch===================

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admitdasboard frame = new Admitdasboard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//	===================== Frame Create ===================

	public Admitdasboard() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1084, 608);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Admit Patient Dashboard");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 21));
		lblNewLabel.setBounds(10, 20, 352, 35);
		contentPane.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setForeground(Color.GREEN);
		panel.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Assign Patient", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(336, 103, 687, 345);
		contentPane.add(panel);
		panel.setLayout(null);

		table = new JTable();

		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "PatientName", "Doctor", "Bed", "Ward", "Nurse", "Status", "Admit ID " }));

		table_update();
		JLabel lbl_ward = new JLabel("Ward");
		lbl_ward.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lbl_ward.setBounds(25, 227, 78, 24);
		contentPane.add(lbl_ward);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				TableModel recordtable = table.getModel();
				DefaultTableModel recordtable = (DefaultTableModel) table.getModel();
				int SelectedRow = table.getSelectedRow();
				comboBox_bedno.setSelectedItem(recordtable.getValueAt(SelectedRow, 2).toString());
				comboBox_status.setSelectedItem(recordtable.getValueAt(SelectedRow, 5).toString());
				comboBox_ward_1.setSelectedItem(recordtable.getValueAt(SelectedRow, 3).toString());

				admit_id = recordtable.getValueAt(SelectedRow, 6).toString();

			}

		});

		scrollPane.setBounds(25, 27, 650, 308);
		panel.add(scrollPane);

//		========================= Combo box=====================

		comboBox_ward_1 = new JComboBox();
		comboBox_ward_1.setModel(new DefaultComboBoxModel(new String[] { "ICU", "CCU" }));
		comboBox_ward_1.setBounds(143, 229, 152, 25);
		contentPane.add(comboBox_ward_1);

		JLabel lbl_bedno = new JLabel("Bed No");
		lbl_bedno.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lbl_bedno.setBounds(25, 299, 62, 24);
		contentPane.add(lbl_bedno);

		comboBox_bedno = new JComboBox();
		comboBox_bedno.setModel(new DefaultComboBoxModel(new String[] { "Bedno-12", "Bedno-13" }));
		comboBox_bedno.setBounds(143, 301, 152, 25);
		contentPane.add(comboBox_bedno);

		comboBox_status = new JComboBox();
		comboBox_status.setModel(new DefaultComboBoxModel(new String[] { "Pending", "Approved" }));
		comboBox_status.setBounds(143, 159, 152, 25);
		contentPane.add(comboBox_status);

		JLabel lbl_status = new JLabel("Status");
		lbl_status.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lbl_status.setBounds(25, 155, 62, 28);
		contentPane.add(lbl_status);

		btn_updatepatient = new JButton("Assign Patient Ward Bed and Approve doctor Request");
		btn_updatepatient.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btn_updatepatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String bed_no, ward_no, patient, nurse, status;
				int doctor, nurse1;

				ward_no = comboBox_ward_1.getSelectedItem().toString();
				bed_no = comboBox_bedno.getSelectedItem().toString();

				status = comboBox_status.getSelectedItem().toString();

				try {
					String sql = "update admit set bed_no=?,ward=?,status=? where id=? ";

					st = connection.conn.prepareStatement(sql);
					st.setString(1, bed_no);
					st.setString(2, ward_no);
					st.setString(3, status);
					st.setInt(4, Integer.parseInt(admit_id));

					st.execute();
					JOptionPane.showMessageDialog(null, "Update medicine successfully");
					table_update();

				} catch (SQLException e1) {

					e1.printStackTrace();

				}

			}
		});
		btn_updatepatient.setBounds(336, 470, 535, 35);
		contentPane.add(btn_updatepatient);

		JLabel lblHospitalManagementSystem = new JLabel("Hospital Management System");
		lblHospitalManagementSystem.setFont(new Font("Times New Roman", Font.BOLD, 28));
		lblHospitalManagementSystem.setBounds(435, 20, 394, 35);
		contentPane.add(lblHospitalManagementSystem);

	}

//	=======================  SQL query ===================

	public boolean table_update() {

		try {
			String sql = "SELECT a.id,a.status, a.bed_no, a.ward, p.firstname as patient_name, d.firstname as doctor_name, n.firstname as nurse_name, p.doctor_id as Docter_ID  FROM admit a\r\n"
					+ "inner join patient_registration p on \r\n" + "p.id = a.patient_registration_id\r\n"
					+ "inner join user_registration d on\r\n" + "p.doctor_id = d.id\r\n"
					+ "inner join user_registration n on\r\n" + "p.nurse_id = n.id";

			st = connection.conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();
			ResultSetMetaData stdata = (ResultSetMetaData) rs.getMetaData();

			q = stdata.getColumnCount();

			DefaultTableModel recordtable = (DefaultTableModel) table.getModel();
			recordtable.setRowCount(0);

			while (rs.next()) {

				Vector<String> columnData = new Vector<String>();

				for (i = 0; i <= q; i++) {

					columnData.add(rs.getString("patient_name"));
					columnData.add(rs.getString("doctor_name"));
					columnData.add(rs.getString("bed_no"));
					columnData.add(rs.getString("ward"));
					columnData.add(rs.getString("nurse_name"));
					columnData.add(rs.getString("status"));
					columnData.add(rs.getString("id"));

					System.out.println();

				}
				recordtable.addRow(columnData);

			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);

		}
		return true;

	}

}

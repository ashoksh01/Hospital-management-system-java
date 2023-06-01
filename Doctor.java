package Hospital;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class Doctor extends JFrame implements MouseListener {

	public int doctorId;
	private JPanel contentPane;
	private JTable table;

	private JTable table_5;
	int q, i, deleteItem;
	int m, k, n;
	DbConnection connection = new DbConnection();

	PreparedStatement st;
	private JTextField text_patient;
	public String patient_id;
	public String medicine_id;
	private static int id;
	JTextArea textarea_update_medical_record;
	private JTable table_1;
	private JTextField textField_medicine_name;
	private JTextField textField_medicine_time;
	JScrollPane scrollPane_2;
	JComboBox comboBox_patient_name;
	JButton btn_assign_key_date;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Doctor frame = new Doctor(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//	========================== Doctor Dashboard Frame===============

	public Doctor(String id) {

		doctorId = Integer.parseInt(id);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1169, 774);
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Doctor Dashboard");
		lblNewLabel_1.setBounds(321, 11, 377, 31);
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setBackground(Color.GREEN);
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 30));
		contentPane.add(lblNewLabel_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 350, 1123, 342);
		contentPane.add(scrollPane);

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Assigned patient Details", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setLayout(null);

		table = new JTable();
		table.setBounds(565, 20, 0, 0);
		panel.add(table);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 20, 1101, 310);
		panel.add(scrollPane_1);

		table_5 = new JTable();
		scrollPane_1.setViewportView(table_5);
		table_5.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Firstname", "Lastname", "DOB", "Admitted Date", "MedicalReport", "PatientID" }));
		TableColumnModel tableColumnModel = table_5.getColumnModel();
		tableColumnModel.removeColumn(tableColumnModel.getColumn(5));

		table_5.addMouseListener(this);

		JButton btn_admin_request = new JButton("Send Admin Request");
		btn_admin_request.setBounds(822, 234, 153, 39);
		btn_admin_request.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					String sql = "insert into admit(patient_registration_id,status) values(?,?)";

					st = connection.conn.prepareStatement(sql);
					st.setInt(1, Integer.parseInt(patient_id));
					st.setString(2, "pending");
					st.execute();
					JOptionPane.showMessageDialog(null, "request succesfully");
				} catch (SQLException e1) {

					e1.printStackTrace();

				}

			}
		});

//		==================  Update ==================

		btn_admin_request.setFont(new Font("Times New Roman", Font.BOLD, 13));
		contentPane.add(btn_admin_request);

		JButton btn_update_record = new JButton("Update");
		btn_update_record.setBounds(1007, 234, 117, 39);
		btn_update_record.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String digonisis = textarea_update_medical_record.getText();

				try {
					String sql = "update patient_registration set medical_history=? where id=?";

					st = connection.conn.prepareStatement(sql);

					st.setString(1, digonisis);
					st.setInt(2, Integer.parseInt(patient_id));
					st.execute();
					JOptionPane.showMessageDialog(null, "Update record successfully");
					update_db(doctorId);

				} catch (SQLException e1) {

					e1.printStackTrace();

				}

			}
		});

		btn_update_record.setFont(new Font("Times New Roman", Font.BOLD, 13));
		contentPane.add(btn_update_record);

		text_patient = new JTextField();
		text_patient.setBounds(822, 3, 127, 26);

		text_patient.setFont(new Font("Tahoma", Font.PLAIN, 16));
		text_patient.setForeground(Color.BLACK);
		text_patient.setBackground(Color.WHITE);
		text_patient.setBorder(null);
		text_patient.setEnabled(false);
		contentPane.add(text_patient);
		text_patient.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(21, 53, 775, 220);
		panel_1.setBackground(Color.WHITE);
		panel_1.setBorder(
				new TitledBorder(null, "Assign Medicine", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_1);

		table_1 = new JTable();
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(250, 21, 515, 172);
		scrollPane_2.setViewportView(table_1);
		table_1.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Medicine Name", "Medicine Time", "Patient Name", "ID" }));
		TableColumnModel tableColumnModel1 = table_1.getColumnModel();
		tableColumnModel1.removeColumn(tableColumnModel1.getColumn(3));

		update_medicine_tbl();

		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {

					DefaultTableModel recordtable = (DefaultTableModel) table_1.getModel();
					int SelectedRow = table_1.getSelectedRow();

					String name = (recordtable.getValueAt(SelectedRow, 2).toString());

					System.out.println("patient " + name);
					for (int i = 0; i < comboBox_patient_name.getItemCount(); i++) {

						if (comboBox_patient_name.getItemAt(i).toString().equalsIgnoreCase(name)) {
							comboBox_patient_name.setSelectedIndex(i);
						}
					}

					textField_medicine_name.setText(recordtable.getValueAt(SelectedRow, 0).toString());
					textField_medicine_time.setText(recordtable.getValueAt(SelectedRow, 1).toString());

					medicine_id = recordtable.getValueAt(SelectedRow, 3).toString();

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		panel_1.setLayout(null);
		panel_1.add(scrollPane_2);

		JLabel lbl_medicine_name = new JLabel("Medicine Name");
		lbl_medicine_name.setBounds(21, 101, 95, 23);
		lbl_medicine_name.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lbl_medicine_name);

		JLabel lbl_medicine_name_1 = new JLabel("Medicine Time");
		lbl_medicine_name_1.setBounds(21, 148, 95, 13);
		lbl_medicine_name_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lbl_medicine_name_1);

		textField_medicine_name = new JTextField();
		textField_medicine_name.setBounds(126, 103, 114, 23);
		panel_1.add(textField_medicine_name);
		textField_medicine_name.setColumns(10);

		textField_medicine_time = new JTextField();
		textField_medicine_time.setBounds(126, 145, 114, 23);
		textField_medicine_time.setColumns(10);
		panel_1.add(textField_medicine_time);

		JLabel lblNewLabel_patient_name = new JLabel("Patient Name");
		lblNewLabel_patient_name.setBounds(10, 56, 92, 23);
		lblNewLabel_patient_name.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_patient_name);

//		=============== Combo box patient ==================

		comboBox_patient_name = new JComboBox();
		comboBox_patient_name.setBounds(126, 58, 114, 21);
		panel_1.add(comboBox_patient_name);
		Updatepatient();

		JButton btn_add_medicine = new JButton("Add medicine For Patient ");
		btn_add_medicine.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_add_medicine.setBounds(31, 284, 226, 39);

		btn_add_medicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String medicine_name, time;

				Object patient = comboBox_patient_name.getSelectedItem();
				int patient_value = ((Combobox) patient).getValue();
				medicine_name = textField_medicine_name.getText();
				time = textField_medicine_time.getText();

				try {
					String sql = "insert into assign_medicine_tbl(medicine_name,time,patient_registration_id) values(?,?,?)";

					st = connection.conn.prepareStatement(sql);

					st.setString(1, medicine_name);
					st.setString(2, time);
					st.setInt(3, patient_value);

					st.execute();
					JOptionPane.showMessageDialog(null, "Add medicine successfully");
					update_medicine_tbl();

				} catch (SQLException e1) {

					e1.printStackTrace();

				}

			}

		});
		contentPane.add(btn_add_medicine);

//		=============== Medicine buttgin update ==================

		JButton btn_update_medicine = new JButton("Update Assign Medicine Table");
		btn_update_medicine.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_update_medicine.setBounds(267, 285, 294, 39);
		btn_update_medicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String medicinename, time, id;

				Object patient = comboBox_patient_name.getSelectedItem();
				int patient_value = ((Combobox) patient).getValue();
				medicinename = textField_medicine_name.getText();
				time = textField_medicine_time.getText();

				try {
					String sql = "update assign_medicine_tbl set medicine_name=?,time=?,patient_registration_id=? where id=?";

					st = connection.conn.prepareStatement(sql);
					st.setString(1, medicinename);
					st.setInt(3, patient_value);
					st.setString(2, time);
					st.setInt(4, Integer.parseInt(medicine_id));

					st.execute();
					JOptionPane.showMessageDialog(null, "Update medicine successfully");
					update_medicine_tbl();

				} catch (SQLException e1) {

					e1.printStackTrace();

				}

			}

		});
		contentPane.add(btn_update_medicine);

//		=============== Key date button provide date ==================

		btn_assign_key_date = new JButton("Assign  treatment Key date for Patient");
		btn_assign_key_date.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_assign_key_date.setBounds(571, 284, 378, 39);
		btn_assign_key_date.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Object source = e.getSource();
				if (source == btn_assign_key_date) {

					KeyDate KD = new KeyDate(doctorId);
					KD.setVisible(true);

				}

			}
		});

		contentPane.add(btn_assign_key_date);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(
				new TitledBorder(null, "Update Medical Report", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(829, 40, 316, 167);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		textarea_update_medical_record = new JTextArea();
		textarea_update_medical_record.setBounds(10, 23, 296, 133);
		panel_2.add(textarea_update_medical_record);

		update_db(doctorId);
	}

//	=============== sql query ==================

	public void update_db(int doctorId) {
		try {
			String sql = "select id, firstname,lastname,admitted_date,dos,medical_history from patient_registration where doctor_id=?";

			st = connection.conn.prepareStatement(sql);
			st.setInt(1, doctorId);
			ResultSet rs = st.executeQuery();
			ResultSetMetaData stdata = (ResultSetMetaData) rs.getMetaData();

			q = stdata.getColumnCount();

			DefaultTableModel recordtable = (DefaultTableModel) table_5.getModel();
			recordtable.setRowCount(0);

			while (rs.next()) {

				Vector columnData = new Vector();

				for (i = 0; i <= q; i++) {
					columnData.add(rs.getString("firstname"));
					columnData.add(rs.getString("lastname"));
					columnData.add(rs.getString("dos"));
					columnData.add(rs.getString("admitted_date"));
					columnData.add(rs.getString("medical_history"));
					columnData.add(rs.getString("id"));

					System.out.println();

				}
				recordtable.addRow(columnData);

			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		try {

			DefaultTableModel recordtable = (DefaultTableModel) table_5.getModel();
			int SelectedRow = table_5.getSelectedRow();
			textarea_update_medical_record.setText(recordtable.getValueAt(SelectedRow, 4).toString());
			text_patient.setText(recordtable.getValueAt(SelectedRow, 0).toString());
			patient_id = recordtable.getValueAt(SelectedRow, 5).toString();
//		System.out.println("ID: "+patient_id);
//		text_id.setText(patient_id);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void Updatepatient() {

		String sql = "select * from patient_registration where doctor_id=?";

		try {

			st = connection.conn.prepareStatement(sql);
			st.setInt(1, doctorId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				comboBox_patient_name.addItem(new Combobox(rs.getString(2), rs.getInt(1)));

			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}

	}

	public void update_medicine_tbl() {

		try {
			String sql = "SELECT a.id, a.medicine_name,a.time,pr.firstname as patient_name FROM hospitalcw.assign_medicine_tbl a\r\n"
					+ "inner join patient_registration pr on \r\n" + "	a.patient_registration_id = pr.id\r\n"
					+ "    inner join user_registration r on \r\n" + "    r.id = pr.doctor_id\r\n"
					+ "    where r.id =? ";

			st = connection.conn.prepareStatement(sql);
			st.setInt(1, doctorId);

			ResultSet rs = st.executeQuery();
			ResultSetMetaData stdata = (ResultSetMetaData) rs.getMetaData();

			n = stdata.getColumnCount();

			DefaultTableModel recordtable = (DefaultTableModel) table_1.getModel();
			recordtable.setRowCount(0);

			while (rs.next()) {

				Vector columnData = new Vector();

				for (m = 0; m <= n; m++) {
					columnData.add(rs.getString("medicine_name"));
					columnData.add(rs.getString("time"));
					columnData.add(rs.getString("patient_name"));
					columnData.add(rs.getString("id"));

					System.out.println();

				}
				recordtable.addRow(columnData);

			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);

		}

	}
}

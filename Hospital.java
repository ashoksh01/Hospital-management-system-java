package Hospital;

import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class Hospital extends JFrame {

	JButton btn_sort;
	private JPanel contentPane;
	private JTextField txtfname;
	private JTextField txtlname;
	private JTextField txtdob;
	private JTextField txtadmitdate;
	private JTable table;
	private ArrayList<Vector<String>> res;
	private JTextField txt_desp;
	JButton btnaddpatient;
	JButton btnupdate;
	JButton btnAdmitPatient;
	JRadioButton rdbtn_doctor;
	ButtonGroup group = new ButtonGroup();

	JRadioButton rdbtn_firstname;
	JRadioButton rdbtn_lastname;
	JRadioButton rdbtn_nurse;
	JButton btn_search;
	DbConnection connection = new DbConnection();
	PreparedStatement st;
	int q, i, id, deleteItem;
	public JTextField text_id;
	DefaultTableModel recordtable;
	DefaultTableModel model;
	JComboBox doctor_comboBox;
	JComboBox nurse_comboBox_1;
	private JTextField textField_search;

//	=============== Lunch Applications ==================

	public static void main(String[] args) {
		Hospital frame = new Hospital();
		frame.setVisible(true);
	}

//	=============== Frame Create ==================

	public Hospital() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1234, 697);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblNewLabel = new JLabel("Admin Dashboard");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setBounds(20, 19, 367, 59);
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Stencil Std", Font.BOLD | Font.ITALIC, 30));
		contentPane.add(lblNewLabel);
		JLabel lblfname = new JLabel("Firstname");
		lblfname.setBounds(24, 120, 111, 43);
		lblfname.setFont(new Font("Times New Roman", Font.BOLD, 20));
		contentPane.add(lblfname);
		JLabel lbllname = new JLabel("Lastname");
		lbllname.setBounds(20, 192, 90, 21);
		lbllname.setFont(new Font("Times New Roman", Font.BOLD, 20));
		contentPane.add(lbllname);
		JLabel lbldob = new JLabel("D.O.B");
		lbldob.setBounds(24, 260, 81, 27);
		lbldob.setFont(new Font("Times New Roman", Font.BOLD, 20));
		contentPane.add(lbldob);
		JLabel lbladmitdate = new JLabel("Admitted Date");
		lbladmitdate.setBounds(10, 329, 130, 27);
		lbladmitdate.setFont(new Font("Times New Roman", Font.BOLD, 20));
		contentPane.add(lbladmitdate);
		JLabel lbldoctor = new JLabel("Doctor");
		lbldoctor.setBounds(24, 391, 130, 27);
		lbldoctor.setFont(new Font("Times New Roman", Font.BOLD, 20));
		contentPane.add(lbldoctor);
		JLabel lblnurse = new JLabel("Nurse");
		lblnurse.setBounds(24, 456, 130, 27);
		lblnurse.setFont(new Font("Times New Roman", Font.BOLD, 20));
		contentPane.add(lblnurse);
		JLabel lbldesp = new JLabel("Description");
		lbldesp.setBounds(10, 562, 111, 27);
		lbldesp.setFont(new Font("Times New Roman", Font.BOLD, 20));
		contentPane.add(lbldesp);
		txtfname = new JTextField();
		txtfname.setBounds(150, 125, 192, 36);
		contentPane.add(txtfname);
		txtfname.setColumns(10);
		txtlname = new JTextField();
		txtlname.setBounds(150, 186, 192, 36);
		txtlname.setColumns(10);
		contentPane.add(txtlname);
		txtdob = new JTextField();
		txtdob.setBounds(150, 257, 192, 36);
		txtdob.setColumns(10);
		contentPane.add(txtdob);
		txtadmitdate = new JTextField();
		txtadmitdate.setBounds(150, 326, 192, 36);
		txtadmitdate.setColumns(10);
		contentPane.add(txtadmitdate);
		txt_desp = new JTextField();
		txt_desp.setBounds(129, 530, 309, 95);
		txt_desp.setColumns(10);
		contentPane.add(txt_desp);
		doctor_comboBox = new JComboBox<String>();
		doctor_comboBox.setBounds(150, 392, 192, 28);
		doctor_comboBox.setModel(new DefaultComboBoxModel(new String[] {}));
		contentPane.add(doctor_comboBox);
		Updtaedoctor();
		nurse_comboBox_1 = new JComboBox();
		nurse_comboBox_1.setBounds(150, 457, 192, 28);
		nurse_comboBox_1.setModel(new DefaultComboBoxModel(new String[] {}));
		contentPane.add(nurse_comboBox_1);
		Updatenurse();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(352, 101, 858, 412);
		scrollPane.addMouseListener(new MouseAdapter() {

			@Override

			public void mouseClicked(MouseEvent e) {

			}
		});

		contentPane.add(scrollPane);
		btnaddpatient = new JButton("Add Patient");
		btnaddpatient.setBounds(459, 556, 130, 36);
		btnaddpatient.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(btnaddpatient);
		btnupdate = new JButton("Update Paintent");
		btnupdate.setBounds(812, 556, 169, 36);
		btnupdate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(btnupdate);
		model = new DefaultTableModel();
		table = new JTable();
		Object[] column = { "ID", "Firstname", "Lastname", "D.O.B", "Admitted Date", "Doctor", "Nurse", "Description" };
		scrollPane.setViewportView(table);
		model.setColumnIdentifiers(column);
		table.setModel(model);
		TableColumnModel tableColumnModel = table.getColumnModel();
		tableColumnModel.removeColumn(tableColumnModel.getColumn(0));
		update_db();
		table.addMouseListener(new MouseAdapter() {
			@Override

			public void mouseClicked(MouseEvent e) {
				DefaultTableModel recordtable = (DefaultTableModel) table.getModel();
				int SelectedRow = table.getSelectedRow();
				text_id.setText(recordtable.getValueAt(SelectedRow, 0).toString());
				txtfname.setText(recordtable.getValueAt(SelectedRow, 1).toString());
				txtlname.setText(recordtable.getValueAt(SelectedRow, 2).toString());
				txtdob.setText(recordtable.getValueAt(SelectedRow, 3).toString());
				txtadmitdate.setText(recordtable.getValueAt(SelectedRow, 4).toString());
				doctor_comboBox.setSelectedItem(recordtable.getValueAt(SelectedRow, 5).toString());
				nurse_comboBox_1.setSelectedItem(recordtable.getValueAt(SelectedRow, 6).toString());
				txt_desp.setText(recordtable.getValueAt(SelectedRow, 7).toString());
			}
		});

		btnaddpatient.addActionListener(new ActionListener() {

//    		=============== sql query ==================

			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
				if (source == btnaddpatient) {
					String fname, lname, Dob, admit_date, med_history, desp, id;
					fname = txtfname.getText();
					lname = txtlname.getText();
					Dob = txtdob.getText();
					admit_date = txtadmitdate.getText();
					Object doc = doctor_comboBox.getSelectedItem();

					int doc1 = ((Combobox) doc).getValue();
					Object nurse = nurse_comboBox_1.getSelectedItem();
					int nurse1 = ((Combobox) nurse).getValue();

					desp = txt_desp.getText();
					id = text_id.getText();
					String sql = "insert into patient_registration(firstname, lastname, dos, admitted_date, doctor_id, nurse_id, description) values(?,?,?,?,?,?,?)";

					try {

						st = connection.conn.prepareStatement(sql);
						st.setString(1, fname);
						st.setString(2, lname);
						st.setString(3, Dob);
						st.setString(4, admit_date);

						st.setInt(5, doc1);
						st.setInt(6, nurse1);
						st.setString(7, desp);

						if (st.executeUpdate() > 0) {
							JOptionPane.showMessageDialog(null, "New Patinet Added Successfully");
							update_db();
						}
					}

					catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		btnAdmitPatient = new JButton("Admit Patient\r\n");
		btnAdmitPatient.setBounds(626, 556, 151, 36);
		btnAdmitPatient.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
				if (source == btnAdmitPatient) {
					Admitdasboard ad = new Admitdasboard();
					ad.setVisible(true);
				}
			}
		});

		btnAdmitPatient.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(btnAdmitPatient);
		JButton btnAddLabReport = new JButton("Add Lab Report\r\n");
		btnAddLabReport.setBounds(1010, 556, 169, 36);
		btnAddLabReport.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

			}

		});

		btnAddLabReport.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(btnAddLabReport);
		JButton btnLogout = new JButton("Logout\r\n");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(btnLogout, "Logout Successfully!");
				dispose();
			}
		});
		btnLogout.setBackground(Color.WHITE);
		btnLogout.setForeground(Color.RED);
		btnLogout.setBounds(1085, 17, 125, 36);
		btnLogout.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		contentPane.add(btnLogout);
		text_id = new JTextField();
		text_id.setBounds(129, 10, -6, 22);
		text_id.setBorder(null);
		text_id.setForeground(Color.GRAY);
		text_id.setBackground(Color.LIGHT_GRAY);
		text_id.setColumns(10);
		contentPane.add(text_id);
		JLabel lblSearch = new JLabel("Search");
		lblSearch.setForeground(Color.BLUE);
		lblSearch.setBounds(643, 11, 81, 43);
		lblSearch.setFont(new Font("Times New Roman", Font.BOLD, 20));
		contentPane.add(lblSearch);
		rdbtn_firstname = new JRadioButton("Firstname");
		rdbtn_firstname.setBounds(504, 10, 103, 21);
		contentPane.add(rdbtn_firstname);
		rdbtn_lastname = new JRadioButton("lastname");
		rdbtn_lastname.setBounds(504, 47, 103, 21);
		contentPane.add(rdbtn_lastname);
		rdbtn_doctor = new JRadioButton("Doctor");
		rdbtn_doctor.setBounds(751, 47, 103, 21);
		contentPane.add(rdbtn_doctor);
		rdbtn_nurse = new JRadioButton("Nurse");
		rdbtn_nurse.setBounds(751, 10, 103, 21);
		contentPane.add(rdbtn_nurse);
		btn_search = new JButton("Search");
		btn_search.setBounds(921, 11, 85, 27);
		btn_search.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String search = textField_search.getText();
				int pos = 0;
				if (rdbtn_firstname.isSelected()) {
					pos = 1;
				} else if (rdbtn_lastname.isSelected()) {
					pos = 2;
				} else if (rdbtn_doctor.isSelected()) {
					pos = 5;
				} else if (rdbtn_nurse.isSelected()) {
					pos = 6;
				}

				res = Searching.search(res, search, pos);

				addData(res);
			}
		});

		contentPane.add(btn_search);
		group.add(rdbtn_firstname);
		group.add(rdbtn_lastname);
		group.add(rdbtn_doctor);
		group.add(rdbtn_nurse);
		textField_search = new JTextField();
		textField_search.setBounds(900, 47, 151, 31);
		contentPane.add(textField_search);
		textField_search.setColumns(10);

//       =============== Sorting========================= 

		btn_sort = new JButton("Sort");
		btn_sort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sort(res);
				reverse(res);
				clearTable();
				for (Vector<String> i : res) {
					System.out.println("hi" + i);
					model.addRow(i);
				}
			}
		});

		btn_sort.setBounds(1109, 64, 85, 27);
		contentPane.add(btn_sort);
		btnupdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
				if (source == btnupdate) {
					String fname, lname, Dob, admit_date, med_history, desp;
					fname = txtfname.getText();
					lname = txtlname.getText();
					Dob = txtdob.getText();
					admit_date = txtadmitdate.getText();
					Object doc = doctor_comboBox.getSelectedItem();
					int doc1 = ((Combobox) doc).getValue();
					Object nurse = nurse_comboBox_1.getSelectedItem();
					int nurse1 = ((Combobox) nurse).getValue();
					desp = txt_desp.getText();
					String id = text_id.getText();
					String sql = "update patient_registration set firstname=?,lastname=?,dos=?,admitted_date=?,doctor_id=?,nurse_id=?,description=? where id=?";

					try {

						st = connection.conn.prepareStatement(sql);
						st.setString(1, fname);
						st.setString(2, lname);
						st.setString(3, Dob);
						st.setString(4, admit_date);
						st.setInt(5, doc1);
						st.setInt(6, nurse1);
						st.setString(7, desp);
						st.setString(8, id);
						if (st.executeUpdate() > 0) {
							JOptionPane.showMessageDialog(null, "data updated successsfully");
							update_db();
						}
					}

					catch (SQLException e1) {

//                		=============== TODO Auto-generated catch block ==================

						e1.printStackTrace();
					}
				}
			}
		});
	}

	public void Updtaedoctor() {
		String sql = "select * from user_registration where role='Doctor'";
		try {
			st = connection.conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				doctor_comboBox.addItem(new Combobox(rs.getString(2), rs.getInt(1)));

			}
		}

		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}

	public void Updatenurse() {
		String sql = "select * from user_registration where role='Nurse'";

		try {
			st = connection.conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				nurse_comboBox_1.addItem(new Combobox(rs.getString(2), rs.getInt(1)));
			}
		}

		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}

	public void update_db() {
		res = new ArrayList<Vector<String>>();

//		=============== sql query ==================     

		try {
			String sql = "select p.id, p.firstname, p.lastname, p.dos, p.admitted_date, p.description, d.firstname as doctor_name, n.firstname as nurse_name \r\n"
					+ "from patient_registration p\r\n" + "inner join user_registration d on \r\n"
					+ " d.id = p.doctor_id\r\n" + "    inner join user_registration n on \r\n"
					+ " n.id = p.nurse_id\r\n";

			st = connection.conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			ResultSetMetaData stdata = (ResultSetMetaData) rs.getMetaData();
			q = stdata.getColumnCount();
			recordtable = (DefaultTableModel) table.getModel();
			recordtable.setRowCount(0);
			while (rs.next()) {
				Vector<String> columnData = new Vector<String>();

				for (i = 0; i <= q; i++) {
					columnData.add(rs.getString("id"));
					columnData.add(rs.getString("firstname"));
					columnData.add(rs.getString("lastname"));
					columnData.add(rs.getString("dos"));
					columnData.add(rs.getString("admitted_date"));
					columnData.add(rs.getString("doctor_name"));
					columnData.add(rs.getString("nurse_name"));
					columnData.add(rs.getString("description"));

					recordtable.addRow(columnData);
					res.add(columnData);
				}
			}

		}

		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}

	public void clear() {
		txt_desp.setText("");
	}

	private void clearTable() {
		for (int i = recordtable.getRowCount() - 1; i >= 0; i--) {
			recordtable.removeRow(i);
		}
	}

	public void addData(ArrayList<Vector<String>> data) {
		clearTable();

		for (Vector<String> i : data) {
			recordtable.addRow(i);
		}

	}

//===================== sorting and searching==================

	public static ArrayList<Vector<String>> reverse(ArrayList<Vector<String>> arr) {

//	===================== Swapping high and low elements===============

		int low = 0;
		int high = arr.size() - 1;
		while (low < high) {
			reverseSwap(arr, low, high);
			low++;
			high--;
		}
		return arr;

	}

	public static void reverseSwap(ArrayList<Vector<String>> arr, int i, int j) {

//	===================== swap on the arraylist of vector string ===============

		Vector<String> temp = arr.get(i);
		arr.set(i, arr.get(j));
		arr.set(j, temp);
	}

	public void sort(ArrayList<Vector<String>> arr) {
		int n = arr.size();

//    	===================== One by one move boundary of unsorted sub array ===============

		for (int i = 0; i < n - 1; i++) {

//    	===================== Find the minimum element in unsorted array ===============

			int min_idx = i;
			for (int j = i + 1; j < n; j++)
				if (Integer.parseInt(arr.get(j).elementAt(0)) < Integer.parseInt(arr.get(min_idx).elementAt(0)))
					min_idx = j;

//            	===================== Swap the found minimum element with the first ===============

			Vector<String> temp = arr.get(min_idx);
			arr.set(min_idx, arr.get(i));
			arr.set(i, temp);
		}
	}

	public void sort1(ArrayList<Vector<String>> arr) {
		int n = arr.size();

//	===================== One by one move boundary of unsorted sub array ===============

		for (int i = 0; i < n - 1; i++) {

//    	===================== Find the minimum element in unsorted array ===============

			int min_idx = i;
			for (int j = i + 1; j < n; j++)
				if (Integer.parseInt(arr.get(j).elementAt(0)) < Integer.parseInt(arr.get(min_idx).elementAt(0)))
					min_idx = j;
//    	===================== Swap the found minimum element with the first ===============

			Vector<String> temp = arr.get(min_idx);
			arr.set(min_idx, arr.get(i));
			arr.set(i, temp);
		}
	}
}
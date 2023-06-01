package Hospital;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GraphicsConfiguration;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class KeyDate extends JFrame {

	private JPanel contentPane;
	private JTextField textField_date;
	private JTextField textField_treatment;
	private JTable table;
	private int docid;
	DbConnection connection = new DbConnection();
	PreparedStatement st;
	JComboBox comboBox_patient;
	JButton btnNewButton_add_key_date;
	int m, k, n;
	public String key_date_id;
	JButton btnNewButton_update_key_date;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					KeyDate frame = new KeyDate(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public KeyDate(int docid) {

		this.docid = docid;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 984, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Key Date Patient Pannel");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblNewLabel.setBounds(27, 20, 200, 38);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_date = new JLabel("Treatmnet Date ");
		lblNewLabel_date.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_date.setBounds(26, 218, 136, 24);
		contentPane.add(lblNewLabel_date);

		textField_date = new JTextField();
		textField_date.setBounds(162, 204, 192, 38);
		contentPane.add(textField_date);
		textField_date.setColumns(10);

		JLabel lblNewLabel_date_1 = new JLabel("Treatment");
		lblNewLabel_date_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_date_1.setBounds(26, 292, 96, 24);
		contentPane.add(lblNewLabel_date_1);

		textField_treatment = new JTextField();
		textField_treatment.setColumns(10);
		textField_treatment.setBounds(162, 288, 192, 38);
		contentPane.add(textField_treatment);

		JLabel lblNewLabel_date_1_1 = new JLabel("Patient Name");
		lblNewLabel_date_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_date_1_1.setBounds(26, 127, 118, 24);
		contentPane.add(lblNewLabel_date_1_1);

		comboBox_patient = new JComboBox();
		comboBox_patient.setBounds(162, 126, 192, 31);
		contentPane.add(comboBox_patient);
		Updatepatient();

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Add Key Date for Treatment", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		panel.setBounds(399, 111, 531, 215);
		contentPane.add(panel);
		panel.setLayout(null);

		table = new JTable();

		table.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Patient Name", "Date", "Treatment", "ID" }));

		TableColumnModel tableColumnModel = table.getColumnModel();
		tableColumnModel.removeColumn(tableColumnModel.getColumn(3));
		update_key_date_tbl();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {

					DefaultTableModel recordtable = (DefaultTableModel) table.getModel();
					int SelectedRow = table.getSelectedRow();
					comboBox_patient.setSelectedItem(recordtable.getValueAt(SelectedRow, 0).toString());
					textField_date.setText(recordtable.getValueAt(SelectedRow, 1).toString());
					textField_treatment.setText(recordtable.getValueAt(SelectedRow, 2).toString());

					key_date_id = recordtable.getValueAt(SelectedRow, 3).toString();

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		scrollPane.setBounds(10, 20, 500, 185);
		panel.add(scrollPane);

		btnNewButton_add_key_date = new JButton("Add key Date");
		btnNewButton_add_key_date.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Object source = e.getSource();
				if (source == btnNewButton_add_key_date) {

					String date, treatment;
					date = textField_date.getText();
					treatment = textField_treatment.getText();
					Object patient = comboBox_patient.getSelectedItem();
					int patient_value = ((Combobox) patient).getValue();

					try {
						String sql = "insert into key_date_tbl(date,treatment,patient_registration_id) values(?,?,?)";

						st = connection.conn.prepareStatement(sql);

						st.setString(1, date);
						st.setString(2, treatment);
						st.setInt(3, patient_value);

						st.execute();
						JOptionPane.showMessageDialog(null, "Add Key date for patient  successfully");

					} catch (SQLException e1) {

						e1.printStackTrace();

					}

				}

			}
		});

		btnNewButton_add_key_date.setBounds(273, 349, 136, 34);
		contentPane.add(btnNewButton_add_key_date);

		btnNewButton_update_key_date = new JButton("Update  key Date");

		btnNewButton_update_key_date.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
				if (source == btnNewButton_update_key_date) {

					String date, treatmernt;
					date = textField_date.getText();
					treatmernt = textField_treatment.getText();
					try {
						String sql = "update key_date_tbl set date=?, treatment=? where id=?";

						st = connection.conn.prepareStatement(sql);

						st.setString(1, date);
						st.setString(2, treatmernt);
						st.setInt(3, Integer.parseInt(key_date_id));
						st.execute();
						JOptionPane.showMessageDialog(null, "Update key date successfully");
						update_key_date_tbl();

					}

					catch (SQLException e1) {

						e1.printStackTrace();

					}

				}

			}
		});

		btnNewButton_update_key_date.setBounds(455, 349, 145, 34);
		contentPane.add(btnNewButton_update_key_date);

		JLabel lblNewLabel_1 = new JLabel("Hospital Management System");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_1.setBounds(316, 9, 401, 52);
		contentPane.add(lblNewLabel_1);
	}

	public KeyDate(Object object) {

	}

	public void Updatepatient() {

		String sql = "select * from patient_registration where doctor_id=?";

		try {

			st = connection.conn.prepareStatement(sql);
			st.setInt(1, docid);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				comboBox_patient.addItem(new Combobox(rs.getString(2), rs.getInt(1)));

			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}

	}

	public void update_key_date_tbl() {

		try {
			String sql = "SELECT a.id, a.date,a.treatment,pr.firstname as patient_name FROM hospitalcw.key_date_tbl a\r\n"
					+ "inner join patient_registration pr on \r\n" + "	a.patient_registration_id = pr.id\r\n"
					+ "    inner join user_registration r on \r\n" + "    r.id = pr.doctor_id\r\n"
					+ "    where r.id = ?";

			st = connection.conn.prepareStatement(sql);
			st.setInt(1, docid);

			ResultSet rs = st.executeQuery();
			ResultSetMetaData stdata = (ResultSetMetaData) rs.getMetaData();

			n = stdata.getColumnCount();

			DefaultTableModel recordtable = (DefaultTableModel) table.getModel();
			recordtable.setRowCount(0);

			while (rs.next()) {

				Vector columnData = new Vector();

				for (m = 0; m <= n; m++) {
					columnData.add(rs.getString("patient_name"));
					columnData.add(rs.getString("date"));
					columnData.add(rs.getString("treatment"));
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

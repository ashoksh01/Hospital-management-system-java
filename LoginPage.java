package Hospital;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class LoginPage {
	private JFrame frame;
	private JTextField textusername;
	private JTextField textpassword;
	JComboBox comboBox_select;
	JButton btnNewButton;
	DbConnection connection = new DbConnection();

	PreparedStatement st;
	Hospital hos = new Hospital();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
					window.frame.setVisible(true);
				}

				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//	============ Create Frame ==============

	public LoginPage() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 621, 511);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel.setBounds(216, 57, 124, 54);

		frame.getContentPane().add(lblNewLabel);
		JLabel lblusername = new JLabel("Username");
		lblusername.setForeground(Color.BLACK);
		lblusername.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblusername.setBounds(44, 199, 106, 34);
		frame.getContentPane().add(lblusername);
		JLabel lblpassword = new JLabel("Password");
		lblpassword.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblpassword.setForeground(Color.BLACK);
		lblpassword.setBounds(44, 271, 106, 34);
		frame.getContentPane().add(lblpassword);
		comboBox_select = new JComboBox();
		comboBox_select.setFont(new Font("Tahoma", Font.PLAIN, 13));
		comboBox_select
				.setModel(new DefaultComboBoxModel(new String[] { "Doctor", "Nurse", "LabAdmin", "HospitalAdmin" }));
		comboBox_select.setBounds(157, 132, 254, 31);
		frame.getContentPane().add(comboBox_select);
		textusername = new JTextField();
		textusername.setBounds(160, 202, 251, 31);
		frame.getContentPane().add(textusername);
		textusername.setColumns(10);
		textpassword = new JTextField();
		textpassword.setBounds(160, 275, 251, 31);
		frame.getContentPane().add(textpassword);
		textpassword.setColumns(10);
		btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener()

		{

//        	============ Login workplace ==============

			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();

				if (source == btnNewButton) {
					String usrname, password, value;
					usrname = textusername.getText();
					password = textpassword.getText();
					value = comboBox_select.getSelectedItem().toString();
					String sql = "select id, username,password,role from user_registration where username=? and password=? and role=?";

					try {
						st = connection.conn.prepareStatement(sql);
						st.setString(1, usrname);
						st.setString(2, password);
						st.setString(3, value);
						ResultSet rs = st.executeQuery();
						System.out.println(rs);
						if (rs.next()) {
							if (value == "Doctor") {
								Doctor doc = new Doctor(rs.getString("id"));
								doc.setVisible(true);
								frame.dispose();
							}

							else if (value == "Nurse") {
								Nurse ND = new Nurse(rs.getString("id"));
								ND.setVisible(true);
							}

							else {

							}
						}

						else {

							JOptionPane.showMessageDialog(null, "Invalid Username or Password");
						}
					}

					catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBounds(199, 336, 171, 34);
		frame.getContentPane().add(btnNewButton);
		JLabel lblNewLabel_1 = new JLabel("Role");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(44, 137, 106, 22);
		frame.getContentPane().add(lblNewLabel_1);
		JLabel lblNewLabel_2 = new JLabel("Hospital Management System");
		lblNewLabel_2.setForeground(Color.DARK_GRAY);
		lblNewLabel_2.setFont(new Font("Stencil Std", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel_2.setBounds(20, 10, 566, 36);

	}

	public void loginnn() {
		String usrname, password, value;
		usrname = textusername.getText();
		password = textpassword.getText();
		value = comboBox_select.getSelectedItem().toString();
		loginnn1(usrname, password, value);
	}

	public boolean loginnn1(String usrname, String password, String value) {
		String sql = "select id, username,password,role from user_registration where username=? and password=? and role=?";

		try {
			st = connection.conn.prepareStatement(sql);
			st.setString(1, usrname);
			st.setString(2, password);
			st.setString(3, value);
			ResultSet rs = st.executeQuery();
			System.out.println(rs);
			if (rs.next()) {
				if (value == "Doctor") {
					Doctor doc = new Doctor(rs.getString("id"));
					doc.setVisible(true);
					return true;

//               frame.dispose();
				}

				else if (value == "Nurse") {
					Nurse ND = new Nurse(rs.getString("id"));
					ND.setVisible(true);
				}

				else {

				}
			}

			else {

				JOptionPane.showMessageDialog(null, "Invalid Username or Password");
			}
		}

		catch (SQLException e1) {
			e1.printStackTrace();
		}

		return true;
	}
}

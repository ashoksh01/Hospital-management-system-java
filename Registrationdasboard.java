package Hospital;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Registrationdasboard {
	private JFrame frame;
	private JLabel lbl_lastname;
	private JLabel lbl_username;
	private JLabel lbl_email;
	private JLabel lbl_password;
	private JTextField text_firstname;
	private JTextField text_lastname;
	private JTextField text_username;
	private JTextField text_email;
	private JTextField text_password;
	private JLabel lbl_degisination;
	JButton btn_registration;
	JComboBox comboBox_select;

	DbConnection connection = new DbConnection();
	PreparedStatement st;

//	=============== Application Lunch ==================

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					Registrationdasboard window = new Registrationdasboard();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Registrationdasboard() {
		initialize();
	}

//	=============== Initialize the contents of the frame ==================

	private void initialize() {

		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setEnabled(false);
		frame.setBounds(100, 100, 693, 574);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JLabel lblNewLabel = new JLabel("User Registration");
		lblNewLabel.setBackground(Color.BLACK);
		lblNewLabel.setBounds(172, 10, 440, 45);
		lblNewLabel.setFont(new Font("Stencil Std", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel.setForeground(Color.BLACK);
		frame.getContentPane().add(lblNewLabel);
		JLabel lbl_firstname = new JLabel("First Name");
		lbl_firstname.setBounds(20, 60, 132, 45);
		lbl_firstname.setForeground(Color.BLACK);
		lbl_firstname.setBackground(Color.BLACK);
		lbl_firstname.setFont(new Font("Times New Roman", Font.BOLD, 18));
		frame.getContentPane().add(lbl_firstname);
		lbl_lastname = new JLabel("Last Name");
		lbl_lastname.setBounds(20, 119, 132, 45);
		lbl_lastname.setForeground(Color.BLACK);
		lbl_lastname.setFont(new Font("Times New Roman", Font.BOLD, 18));
		frame.getContentPane().add(lbl_lastname);
		lbl_username = new JLabel("User Name");
		lbl_username.setBounds(20, 175, 125, 45);
		lbl_username.setForeground(Color.BLACK);
		lbl_username.setFont(new Font("Times New Roman", Font.BOLD, 18));
		frame.getContentPane().add(lbl_username);
		lbl_email = new JLabel("Email Adress");
		lbl_email.setBounds(20, 231, 125, 45);
		lbl_email.setForeground(Color.BLACK);
		lbl_email.setFont(new Font("Times New Roman", Font.BOLD, 18));
		frame.getContentPane().add(lbl_email);
		lbl_password = new JLabel("Password");
		lbl_password.setBounds(20, 374, 125, 45);
		lbl_password.setForeground(Color.BLACK);
		lbl_password.setFont(new Font("Times New Roman", Font.BOLD, 18));
		frame.getContentPane().add(lbl_password);
		text_firstname = new JTextField();
		text_firstname.setBounds(262, 64, 228, 39);
		text_firstname.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		frame.getContentPane().add(text_firstname);
		text_firstname.setColumns(10);
		text_lastname = new JTextField();
		text_lastname.setBounds(262, 125, 228, 37);
		text_lastname.setColumns(10);
		frame.getContentPane().add(text_lastname);
		text_username = new JTextField();
		text_username.setBounds(262, 181, 228, 37);
		text_username.setColumns(10);
		frame.getContentPane().add(text_username);
		text_email = new JTextField();
		text_email.setBounds(262, 237, 228, 37);
		text_email.setColumns(10);
		frame.getContentPane().add(text_email);
		text_password = new JTextField();
		text_password.setBounds(262, 380, 228, 37);
		text_password.setColumns(10);
		frame.getContentPane().add(text_password);
		lbl_degisination = new JLabel("Degisination");
		lbl_degisination.setBounds(20, 303, 125, 45);
		lbl_degisination.setForeground(Color.BLACK);
		lbl_degisination.setFont(new Font("Times New Roman", Font.BOLD, 18));
		frame.getContentPane().add(lbl_degisination);
		comboBox_select = new JComboBox();
		comboBox_select.setBounds(262, 308, 228, 35);
		comboBox_select.setFont(new Font("Tahoma", Font.BOLD, 14));
		comboBox_select
				.setModel(new DefaultComboBoxModel(new String[] { "Doctor", "Nurse", "Lab Admin", "Hospital Admin" }));
		frame.getContentPane().add(comboBox_select);
		btn_registration = new JButton("Register");
		btn_registration.setBounds(95, 454, 132, 37);
		btn_registration.addActionListener(new ActionListener() {

//        	============================ sql query ===================

			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
				if (source == btn_registration) {
					String firstname, lastname, usrname, email, password, value;
					firstname = text_firstname.getText();
					lastname = text_lastname.getText();
					usrname = text_username.getText();
					email = text_email.getText();
					password = text_password.getText();
					value = comboBox_select.getSelectedItem().toString();
					String sql = "insert into user_registration(firstname, lastname, username,email,password,role) values(?,?,?,?,?,?)";

					try {
						st = connection.conn.prepareStatement(sql);
						st.setString(1, firstname);
						st.setString(2, lastname);
						st.setString(3, usrname);
						st.setString(4, email);
						st.setString(5, password);
						st.setString(6, value);
						if (st.executeUpdate() > 0) {
							JOptionPane.showMessageDialog(null, "New Patinet Added");
						}
					}

					catch (SQLException e1) {

						e1.printStackTrace();

					}

				}
			}
		});

		btn_registration.setFont(new Font("Times New Roman", Font.BOLD, 20));
		frame.getContentPane().add(btn_registration);
		JButton btn_cancel = new JButton("Cancel");
		btn_cancel.setForeground(Color.RED);
		btn_cancel.setBounds(363, 451, 132, 42);
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		btn_cancel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		frame.getContentPane().add(btn_cancel);
	}
}
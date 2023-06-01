package Hospital;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UploadReport extends JFrame {

	private JPanel contentPane;
	Connection con;
	JComboBox<String> patient_combo;
	PreparedStatement stmt;
	JLabel p_id;
	private JTextField txt_path;
	DbConnection connection = new DbConnection();
	PreparedStatement st;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UploadReport frame = new UploadReport();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//	==================== Create Frame ==================
	
	
	public UploadReport() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 628, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Upload Lab Report");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(212, 30, 160, 27);
		contentPane.add(lblNewLabel);

		patient_combo = new JComboBox<>();
		patient_combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String combo = (String) patient_combo.getSelectedItem();
				try {
					String query = "select id from patient_registration where firstname=?";
					st = connection.conn.prepareStatement(query);
					st.setString(1, combo);
					ResultSet rs = st.executeQuery();
					while (rs.next()) {
						p_id.setText(rs.getString("id"));
					}

				} catch (Exception es) {
					es.printStackTrace();
				}
			}
		});
		patient_combo.setBounds(270, 110, 204, 27);
		contentPane.add(patient_combo);

		JLabel lblNewLabel_1 = new JLabel("Select Patient");
		lblNewLabel_1.setBounds(169, 117, 124, 13);
		contentPane.add(lblNewLabel_1);

		JButton btnNewButton = new JButton("Upload Medical Report");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfile = new JFileChooser();
				jfile.setCurrentDirectory(new File(System.getProperty("user.dir")));

				// filter
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Image", "jpg", "png", "pdf");
				jfile.addChoosableFileFilter(filter);
				String id = p_id.getText();
				int result = jfile.showSaveDialog(null);
				System.out.println("" + result);
				File selectedFile = jfile.getSelectedFile();
				String filename = selectedFile.getName();
				System.out.println(selectedFile);
				if (filename.endsWith(".jpg") || filename.endsWith(".txt") || filename.endsWith(".pdf")) {
					if (result == JFileChooser.APPROVE_OPTION) {
						String path = selectedFile.getAbsolutePath();

						FileInputStream fis = null;
						try {
							txt_path.setText(path);
							fis = new FileInputStream(path);

							st = connection.conn
									.prepareStatement("update patient_registration set report=? where id=?");
							st.setBinaryStream(1, fis);
							st.setString(2, id);
							int conf = JOptionPane.showConfirmDialog(btnNewButton, "Are you sure want to save?");
							if (conf == 0) {
								int record = st.executeUpdate();
								if (record == 1) {
									JOptionPane.showMessageDialog(rootPane, "Uploaded successfully!");
								} else {
									JOptionPane.showMessageDialog(rootPane, "FAILED BRO!!");
								}
							}
							if (conf == 1) {
								JOptionPane.showMessageDialog(rootPane, "Not allowed by user!!");
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}

					}
				} else
					JOptionPane.showMessageDialog(btnNewButton, "Invalid file format");
			}
		});
		btnNewButton.setBounds(270, 234, 204, 34);
		contentPane.add(btnNewButton);

		p_id = new JLabel("id");
		p_id.setBounds(482, 117, 42, 13);
		contentPane.add(p_id);

		txt_path = new JTextField();
		txt_path.setBounds(169, 186, 305, 27);
		contentPane.add(txt_path);
		txt_path.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("Selected Filepath");
		lblNewLabel_1_1.setBounds(169, 171, 124, 13);
		contentPane.add(lblNewLabel_1_1);
		fill_patient();
	}

	private void fill_patient() {
		try {
			String sql = "select firstname from patient_registration";
			st = connection.conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				String name = rs.getString("firstname");
				patient_combo.addItem(name);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
}

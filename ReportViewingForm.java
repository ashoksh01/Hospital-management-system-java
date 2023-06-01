package Hospital;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.io.File;
//============================ Cross platform solution to view a PDF file====================
import javax.swing.JScrollPane;

public class ReportViewingForm extends JFrame {
	private JPanel contentPane;
	private JScrollPane scrollPane;

// =================== private SwingController control=====================
	public static void main(String[] args) {
		try {
			File pdfFile = new File("out.pdf");
			if (pdfFile.exists()) {
				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().open(pdfFile);
				} else {
					System.out.println("Awt Desktop is not supported!");
				}
			} else {
				System.out.println("File is not exists!");
			}
			System.out.println("Done");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
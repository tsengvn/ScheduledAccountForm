package com.tsengvn.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AboutDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			AboutDialog dialog = new AboutDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		setTitle("About");
		setBounds(100, 100, 453, 238);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Deposit Account Management");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 414, 29);
		contentPanel.add(lblNewLabel);
		
		JLabel lblAgribankSongThan = new JLabel("Agribank Song Than");
		lblAgribankSongThan.setHorizontalAlignment(SwingConstants.CENTER);
		lblAgribankSongThan.setBounds(10, 40, 414, 14);
		contentPanel.add(lblAgribankSongThan);
		
		JLabel lblCopyright = new JLabel("Copyright \u00A9 Tsengvn Production");
		lblCopyright.setHorizontalAlignment(SwingConstants.CENTER);
		lblCopyright.setBounds(10, 65, 414, 14);
		contentPanel.add(lblCopyright);
		
		JLabel lblContactNmhiengmailcom = new JLabel("Contact : nmhien88@gmail.com");
		lblContactNmhiengmailcom.setHorizontalAlignment(SwingConstants.CENTER);
		lblContactNmhiengmailcom.setBounds(10, 90, 414, 14);
		contentPanel.add(lblContactNmhiengmailcom);
		
		JLabel lblVersion = new JLabel("Version 0.3");
		lblVersion.setHorizontalAlignment(SwingConstants.CENTER);
		lblVersion.setBounds(10, 115, 414, 14);
		contentPanel.add(lblVersion);
		
		JLabel lblJan = new JLabel("Jan 2013");
		lblJan.setHorizontalAlignment(SwingConstants.CENTER);
		lblJan.setBounds(10, 140, 414, 14);
		contentPanel.add(lblJan);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AboutDialog.this.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}

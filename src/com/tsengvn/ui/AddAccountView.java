package com.tsengvn.ui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;

/**
 * Creator: Hien Ngo
 * Date: Nov 20, 2012
 */
public class AddAccountView extends JDialog {
	private JTextField tfAccNo;
	private JTextField tfCusNo;
	private JTextField tfCusName;
	private JTextField tfRate;
	private JTextField tfOpeningDate;
	private JTextField tfClosedDate;
	private JTextField tfBookedBalance;
	private JTextField tfMaturity;
	private JLabel lblCustomerName;
	private JLabel lblRate;
	private JLabel lblOpeningDate;
	private JLabel lblClosedDate;
	private JLabel lblBookedBalance;
	private JLabel lblMaturity;
	private JTextField tfAutoTransfer;


	/**
	 * Create the frame.
	 */
	public AddAccountView() {
		setBounds(100, 100, 591, 300);
		setLayout(null);
		
		JLabel lblAccountNo = new JLabel("Account No");
		lblAccountNo.setBounds(6, 28, 91, 16);
		add(lblAccountNo);
		
		JLabel lblCustomerNo = new JLabel("Customer No");
		lblCustomerNo.setBounds(303, 28, 91, 16);
		add(lblCustomerNo);
		
		tfAccNo = new JTextField();
		tfAccNo.setBounds(133, 22, 134, 28);
		add(tfAccNo);
		tfAccNo.setColumns(10);
		
		tfCusNo = new JTextField();
		tfCusNo.setBounds(406, 22, 134, 28);
		add(tfCusNo);
		tfCusNo.setColumns(10);
		
		tfCusName = new JTextField();
		tfCusName.setBounds(133, 56, 217, 28);
		add(tfCusName);
		tfCusName.setColumns(10);
		
		tfRate = new JTextField();
		tfRate.setBounds(479, 56, 61, 28);
		add(tfRate);
		tfRate.setColumns(10);
		
		tfOpeningDate = new JTextField();
		tfOpeningDate.setBounds(133, 96, 134, 28);
		add(tfOpeningDate);
		tfOpeningDate.setColumns(10);
		
		tfClosedDate = new JTextField();
		tfClosedDate.setBounds(406, 96, 134, 28);
		add(tfClosedDate);
		tfClosedDate.setColumns(10);
		
		tfBookedBalance = new JTextField();
		tfBookedBalance.setBounds(133, 136, 134, 28);
		add(tfBookedBalance);
		tfBookedBalance.setColumns(10);
		
		tfMaturity = new JTextField();
		tfMaturity.setBounds(406, 136, 134, 28);
		add(tfMaturity);
		tfMaturity.setColumns(10);
		
		lblCustomerName = new JLabel("Customer Name");
		lblCustomerName.setBounds(6, 62, 115, 16);
		add(lblCustomerName);
		
		lblRate = new JLabel("Rate");
		lblRate.setBounds(406, 62, 61, 16);
		add(lblRate);
		
		lblOpeningDate = new JLabel("Opening Date");
		lblOpeningDate.setBounds(6, 102, 91, 16);
		add(lblOpeningDate);
		
		lblClosedDate = new JLabel("Closed Date");
		lblClosedDate.setBounds(303, 102, 91, 16);
		add(lblClosedDate);
		
		lblBookedBalance = new JLabel("Booked Balance");
		lblBookedBalance.setBounds(6, 142, 103, 16);
		add(lblBookedBalance);
		
		lblMaturity = new JLabel("Maturity");
		lblMaturity.setBounds(303, 142, 61, 16);
		add(lblMaturity);
		
		JCheckBox chbAutoRenew = new JCheckBox("Auto Renew");
		chbAutoRenew.setBounds(303, 178, 128, 23);
		add(chbAutoRenew);
		
		tfAutoTransfer = new JTextField();
		tfAutoTransfer.setBounds(133, 176, 134, 28);
		add(tfAutoTransfer);
		tfAutoTransfer.setColumns(10);
		
		JLabel lblAutoTransfer = new JLabel("Auto Transfer");
		lblAutoTransfer.setBounds(6, 182, 91, 16);
		add(lblAutoTransfer);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(162, 239, 117, 29);
		add(btnAdd);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(303, 239, 117, 29);
		add(btnClear);

	}
}

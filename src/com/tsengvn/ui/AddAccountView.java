package com.tsengvn.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.event.ListDataListener;

import org.apache.commons.lang3.StringUtils;

import com.tsengvn.service.AccountModel;
import com.tsengvn.service.DBService;
import com.toedter.calendar.JDateChooser;

/**
 * Creator: Hien Ngo
 * Date: Nov 20, 2012
 */
public class AddAccountView extends JPanel implements ActionListener{
	private JTextField tfAccNo;
	private JTextField tfCusName;
	private JTextField tfRate;
	private JTextField tfBookedBalance;
	private JTextField tfAutoTransfer;
	private JButton btnAdd;
	private JButton btnClear;
	private JCheckBox chbAutoRenew;
	private JComboBox cbbAccountType;
	private JDateChooser dchOpeningDate;
	private JDateChooser dchClosedDate;
	private JLabel lblTerm;
	private JTextField tfOutstanding;
	private JTextField tfTerm;


	/**
	 * Create the frame.
	 */
	public AddAccountView() {
		setBounds(100, 100, 586, 297);
		setLayout(null);
		
		JLabel lblAccountNo = new JLabel("Account No");
		lblAccountNo.setBounds(6, 28, 91, 16);
		add(lblAccountNo);
		
		JLabel lblCustomerNo = new JLabel("Customer Name");
		lblCustomerNo.setBounds(303, 28, 91, 16);
		add(lblCustomerNo);
		
		tfAccNo = new JTextField();
		tfAccNo.setBounds(133, 22, 134, 28);
		add(tfAccNo);
		tfAccNo.setColumns(10);
		
		tfCusName = new JTextField();
		tfCusName.setBounds(406, 22, 134, 28);
		add(tfCusName);
		tfCusName.setColumns(10);
		
		tfRate = new JTextField();
		tfRate.setBounds(329, 55, 61, 28);
		add(tfRate);
		tfRate.setColumns(10);
		
		tfBookedBalance = new JTextField();
		tfBookedBalance.setBounds(133, 136, 134, 28);
		add(tfBookedBalance);
		tfBookedBalance.setColumns(10);
		
		JLabel lblRate = new JLabel("Rate");
		lblRate.setBounds(280, 61, 39, 16);
		add(lblRate);
		
		JLabel lblOpeningDate = new JLabel("Opening Date");
		lblOpeningDate.setBounds(6, 102, 91, 16);
		add(lblOpeningDate);
		
		JLabel lblClosedDate = new JLabel("Closed Date");
		lblClosedDate.setBounds(303, 102, 91, 16);
		add(lblClosedDate);
		
		JLabel lblBookedBalance = new JLabel("Booked Balance");
		lblBookedBalance.setBounds(6, 142, 103, 16);
		add(lblBookedBalance);
		
		chbAutoRenew = new JCheckBox("Auto Renew");
		chbAutoRenew.setBounds(303, 179, 128, 23);
		add(chbAutoRenew);
		
		tfAutoTransfer = new JTextField();
		tfAutoTransfer.setBounds(133, 176, 134, 28);
		add(tfAutoTransfer);
		tfAutoTransfer.setColumns(10);
		
		JLabel lblAutoTransfer = new JLabel("Transfer Account");
		lblAutoTransfer.setBounds(6, 182, 115, 16);
		add(lblAutoTransfer);
		
		btnAdd = new JButton("Add");
		btnAdd.setBounds(150, 232, 117, 29);
		add(btnAdd);
		
		btnClear = new JButton("Clear");
		btnClear.setBounds(303, 232, 117, 29);
		add(btnClear);
		
		btnAdd.addActionListener(this);
		btnClear.addActionListener(this);
		
		cbbAccountType = new JComboBox(AccountModel.model.values());
		cbbAccountType.setBounds(133, 57, 83, 27);
		add(cbbAccountType);
		
		JLabel lblAccountType = new JLabel("Account Type");
		lblAccountType.setBounds(6, 62, 88, 16);
		add(lblAccountType);
		
		dchOpeningDate = new JDateChooser();
		dchOpeningDate.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		dchOpeningDate.setBounds(133, 96, 134, 28);
		add(dchOpeningDate);
		
		dchClosedDate = new JDateChooser();
		dchClosedDate.setBounds(406, 96, 134, 28);
		add(dchClosedDate);
		
		JLabel lblOutstanding = new JLabel("Outstanding");
		lblOutstanding.setBounds(303, 145, 61, 14);
		add(lblOutstanding);
		
		tfOutstanding = new JTextField();
		tfOutstanding.setBounds(406, 136, 134, 28);
		add(tfOutstanding);
		tfOutstanding.setColumns(10);
		
		lblTerm = new JLabel("Term");
		lblTerm.setBounds(434, 61, 39, 14);
		add(lblTerm);
		
		tfTerm = new JTextField();
		tfTerm.setBounds(479, 55, 61, 28);
		add(tfTerm);
		tfTerm.setColumns(10);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdd){
			doAddNewAccount();
		} else if (e.getSource() == btnClear){
			tfAccNo.setText("");
			tfAutoTransfer.setText("");
			tfBookedBalance.setText("");
			tfCusName.setText("");
			tfRate.setText("");
			dchOpeningDate.setDate(Calendar.getInstance().getTime());
		}
		
	}
	
	private void doAddNewAccount(){
		if (StringUtils.isEmpty(tfAccNo.getText()) || StringUtils.isEmpty(tfCusName.getText())){
			showError("Account Number and Customer number are required!");
			return;
		}
		AccountModel addModel = new AccountModel(tfAccNo.getText());
		addModel.setCusName(tfCusName.getText());
		
		try {
			addModel.setBookedBalance(Long.parseLong(tfBookedBalance.getText()));
		} catch (Exception e) {
			showError("Invalid Booked Balance value!");
			return;
		}
		
		try {
			addModel.setBookedBalance(Long.parseLong(tfOutstanding.getText()));
		} catch (Exception e) {
			showError("Invalid Outstading value!");
			return;
		}
		
		try {
			addModel.setTerm(Integer.parseInt(tfTerm.getText()));
		} catch (Exception e) {
			showError("Invalid Outstading value!");
			return;
		}
		
		if (dchClosedDate.getDate() == null){
			showError("Invalid Closed Date value!");
			return;
		} else {
			addModel.setCloseDate(dchClosedDate.getDate().getTime());
		}
		
		if (dchOpeningDate.getDate() == null){
			showError("Invalid Opening Date value!");
			return;
		} else {
			addModel.setOpenDate(dchOpeningDate.getDate().getTime());
		}
		
//		if (StringUtils.isEmpty(tfCusName.getText())){
//			showError("Invalid Customer Name value!");
//			return;
//		}
//		
//		if (dchOpeningDate.getDate() != null){
//			addModel.setMaturity(dchMaturity.getDate().getTime());
//		}
		
		try {
			addModel.setRate(Float.parseFloat(tfRate.getText()));
		} catch (Exception e) {
			showError("Invalid Opening Date value!");
			return;
		}
		
		addModel.setAccType(AccountModel.model.values()[cbbAccountType.getSelectedIndex()].toString());
		
		DBService.getInstance().addNewAccount(addModel);
		
	}
	
	private void showError(String message){
		JOptionPane.showMessageDialog(this,
			    message,
			    "Invalid",
			    JOptionPane.WARNING_MESSAGE);
	}
}

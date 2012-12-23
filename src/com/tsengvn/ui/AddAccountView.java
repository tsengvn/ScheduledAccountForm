package com.tsengvn.ui;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.NumberFormat;
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
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import org.apache.commons.lang3.StringUtils;

import com.tsengvn.service.AccountModel;
import com.tsengvn.service.DBService;
import com.tutego.jrtf.Rtf;
import com.toedter.calendar.JDateChooser;
import javax.swing.JFormattedTextField;
import java.awt.BorderLayout;

/**
 * Creator: Hien Ngo
 * Date: Nov 20, 2012
 */
public class AddAccountView extends JPanel implements ActionListener{
	private JFormattedTextField tfAccNo;
	private JFormattedTextField tfCusNumber;
	private JFormattedTextField tfRate;
	private JFormattedTextField tfBookedBalance;
	private JFormattedTextField tfAutoTransfer;
	private JButton btnAdd;
	private JButton btnClear;
	private JCheckBox chbAutoRenew;
	private JComboBox cbbAccountType;
	private JDateChooser dchOpeningDate;
	private JDateChooser dchMaturity;
	private JLabel lblTerm;
	private JFormattedTextField tfOutstanding;
	private JFormattedTextField tfTerm;

	//Formats to format and parse numbers
    private NumberFormat amountFormat;
    private NumberFormat percentFormat;
    private NumberFormat paymentFormat;
    private JTextField tfCusName;

    private AccountModel addModel;
    private JPanel panel;
    
    private static final String NO_PATTERN = "#############";
    
	/**
	 * Create the frame.
	 */
	public AddAccountView() {
		setBounds(100, 100, 742, 417);
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblAccountNo = new JLabel("Account Number");
		lblAccountNo.setBounds(99, 58, 117, 16);
		panel.add(lblAccountNo);
		
		JLabel lblCustomerNo = new JLabel("Customer Number");
		lblCustomerNo.setBounds(398, 93, 113, 16);
		panel.add(lblCustomerNo);
		
		tfAccNo = new JFormattedTextField(createFormatter(NO_PATTERN));
		tfAccNo.setBounds(226, 52, 134, 28);
		panel.add(tfAccNo);
		tfAccNo.setColumns(13);
		
		tfCusNumber = new JFormattedTextField(createFormatter(NO_PATTERN));
		tfCusNumber.setBounds(521, 87, 134, 28);
		panel.add(tfCusNumber);
		tfCusNumber.setColumns(13);
		
		tfRate = new JFormattedTextField(NumberFormat.getNumberInstance());
		tfRate.setBounds(315, 243, 45, 28);
		panel.add(tfRate);
		tfRate.setColumns(10);
		
		tfBookedBalance = new JFormattedTextField(NumberFormat.getNumberInstance());
		tfBookedBalance.setBounds(226, 166, 134, 28);
		panel.add(tfBookedBalance);
		tfBookedBalance.setColumns(10);
		
		JLabel lblRate = new JLabel("Rate");
		lblRate.setBounds(263, 249, 39, 16);
		panel.add(lblRate);
		
		JLabel lblOpeningDate = new JLabel("Opening Date");
		lblOpeningDate.setBounds(99, 132, 117, 16);
		panel.add(lblOpeningDate);
		
		JLabel lblClosedDate = new JLabel("Maturity");
		lblClosedDate.setBounds(396, 132, 115, 16);
		panel.add(lblClosedDate);
		
		JLabel lblBookedBalance = new JLabel("Booked Balance");
		lblBookedBalance.setBounds(99, 172, 117, 16);
		panel.add(lblBookedBalance);
		
		chbAutoRenew = new JCheckBox("Auto Renew");
		chbAutoRenew.setBounds(398, 209, 128, 23);
		panel.add(chbAutoRenew);
		
		tfAutoTransfer = new JFormattedTextField(NumberFormat.getIntegerInstance());
		tfAutoTransfer.setBounds(226, 206, 134, 28);
		panel.add(tfAutoTransfer);
		tfAutoTransfer.setColumns(13);
		
		JLabel lblAutoTransfer = new JLabel("Transfer Account");
		lblAutoTransfer.setBounds(99, 212, 115, 16);
		panel.add(lblAutoTransfer);
		
		btnAdd = new JButton("Add");
		btnAdd.setBounds(243, 297, 117, 29);
		panel.add(btnAdd);
		
		btnClear = new JButton("Clear");
		btnClear.setBounds(398, 297, 117, 29);
		panel.add(btnClear);
		
		cbbAccountType = new JComboBox(AccountModel.model.values());
		cbbAccountType.setBounds(521, 53, 58, 27);
		panel.add(cbbAccountType);
		
		JLabel lblAccountType = new JLabel("Account Type");
		lblAccountType.setBounds(396, 58, 119, 16);
		panel.add(lblAccountType);
		
		dchOpeningDate = new JDateChooser();
		dchOpeningDate.setBounds(226, 126, 134, 28);
		panel.add(dchOpeningDate);
		dchOpeningDate.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		dchOpeningDate.setDate(Calendar.getInstance().getTime());
		
		JLabel lblOutstanding = new JLabel("Outstanding");
		lblOutstanding.setBounds(396, 175, 115, 14);
		panel.add(lblOutstanding);
		
		tfOutstanding = new JFormattedTextField(NumberFormat.getNumberInstance());
		tfOutstanding.setBounds(521, 166, 134, 28);
		panel.add(tfOutstanding);
		tfOutstanding.setColumns(10);
		
		lblTerm = new JLabel("Term");
		lblTerm.setBounds(398, 250, 39, 14);
		panel.add(lblTerm);
		
		tfTerm = new JFormattedTextField(NumberFormat.getIntegerInstance());
		tfTerm.setBounds(447, 243, 45, 28);
		panel.add(tfTerm);
		tfTerm.setColumns(10);
		
		JLabel lblCustomerName = new JLabel("Customer Name");
		lblCustomerName.setBounds(99, 93, 117, 16);
		panel.add(lblCustomerName);
		
		tfCusName = new JFormattedTextField();
		tfCusName.setBounds(226, 87, 134, 28);
		panel.add(tfCusName);
		tfCusName.setColumns(10);
		
		dchMaturity = new JDateChooser();
		dchMaturity.setBounds(521, 126, 134, 28);
		panel.add(dchMaturity);
		dchMaturity.setDate(Calendar.getInstance().getTime());
		btnClear.addActionListener(this);
		
		btnAdd.addActionListener(this);

	}
	

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdd){
			doAddNewAccount();
		} else if (e.getSource() == btnClear){
			tfAccNo.setText("");
			tfAutoTransfer.setText("");
			tfBookedBalance.setText("");
			tfCusNumber.setText("");
			tfRate.setText("");
			dchOpeningDate.setDate(Calendar.getInstance().getTime());
		}
		
	}
	
	protected MaskFormatter createFormatter(String s) {
	    MaskFormatter formatter = null;
	    try {
	        formatter = new MaskFormatter(s);
	    } catch (java.text.ParseException exc) {
	        System.err.println("formatter is bad: " + exc.getMessage());
	        System.exit(-1);
	    }
	    return formatter;
	}
	
	private void doAddNewAccount(){
		
		if (StringUtils.isEmpty(tfAccNo.getText()) || StringUtils.isEmpty(tfCusNumber.getText())){
			showError("Account Number and Customer number are required!");
			return;
		}
		addModel = new AccountModel(tfAccNo.getText());
		addModel.setCusNo(tfCusNumber.getText());
		addModel.setCusName(tfCusName.getText());
		
		if (!StringUtils.isEmpty(tfBookedBalance.getText())){
			addModel.setBookedBalance(((Number)tfBookedBalance.getValue()).longValue());
		} else {
			showError("Invalid Booked Balance value!");
			return;
		}
		
		if (!StringUtils.isEmpty(tfOutstanding.getText())){
			addModel.setOutstanding(((Number)tfOutstanding.getValue()).longValue());
		} else {
			showError("Invalid Outstading value!");
			return;
		}
		
		if (!StringUtils.isEmpty(tfTerm.getText())) {
			addModel.setTerm(((Number)tfTerm.getValue()).intValue());
		} else {
			showError("Invalid Outstading value!");
			return;
		}
		
		if (dchMaturity.getDate() == null){
			showError("Invalid Maturity value!");
			return;
		} else {
			addModel.setMaturity(dchMaturity.getDate().getTime());
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
		
		if (!StringUtils.isEmpty(tfRate.getText())) {
			addModel.setRate(((Number)tfRate.getValue()).floatValue());
		} else {
			showError("Invalid Opening Date value!");
			return;
		}
		addModel.setAutoRenew(chbAutoRenew.isSelected()); 
		addModel.setAutoTransfer(tfAutoTransfer.getText());
		
		addModel.setAccType(AccountModel.model.values()[cbbAccountType.getSelectedIndex()].toString());
		DBService.getInstance().addNewAccount(addModel);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				prepareOutput();
				
			}
		}).start();
		
		
	}
	
	private void prepareOutput(){
		try {
			Rtf.template( new FileInputStream("sample.rtf") )
				.inject( "CusNo", addModel.getCusNo() )
				.inject( "CusName", addModel.getCusName() )
				.inject( "AccNo", addModel.getAccNo() )
				.inject( "Bal", tfBookedBalance.getText() + addModel.getAccType())
				.inject( "Ter", addModel.getTerm() )
				.inject( "Rat", addModel.getRate() )
				.inject( "TrsAcc", addModel.getAutoTransfer() )
				.inject( "D", Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
				.inject( "M", Calendar.getInstance().get(Calendar.MONTH)+1 )
				.inject( "Y", Calendar.getInstance().get(Calendar.YEAR) )
				.out( new FileOutputStream("out.rtf") );
			Desktop.getDesktop().open(new File("G:\\ScheduledAccountForm\\out.rtf"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void showError(String message){
		JOptionPane.showMessageDialog(this,
			    message,
			    "Invalid",
			    JOptionPane.WARNING_MESSAGE);
	}
}

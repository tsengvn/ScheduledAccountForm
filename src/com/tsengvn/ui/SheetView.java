package com.tsengvn.ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JComboBox;

import com.tsengvn.service.AccountModel;
import com.tsengvn.service.DBService;
import com.tsengvn.ui.MyTableModel.OnTableDataChanged;
import com.tsengvn.util.IntegerTextField;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;

import org.apache.commons.lang3.StringUtils;
import com.toedter.calendar.JDateChooser;

/**
 * Creator: Hien Ngo
 * Date: Nov 23, 2012
 */
public class SheetView extends JPanel {
	
	private JTable table;
	private MyTableModel tableModel;
	private JScrollPane scrollPane;
	private JComboBox cbReportKind;
	private JTextField tfSelectorCusNo;
	private JTextField tfSelectorAccNo;
	private JLabel lblOutstandingTotal;
	private JButton btnNewButton;
	private JLabel lblType;
	private JComboBox accType;
	private JLabel lblAmount;
	private JLabel lblTime;
	private JDateChooser dateChooser;
	private JDateChooser dateChooser_1;
	private JTextField tfAmount;

	/**
	 * Create the panel.
	 */
	public SheetView() {
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		FlowLayout fl_panel = new FlowLayout(FlowLayout.LEADING, 5, 5);
		panel.setLayout(fl_panel);
		
		JPanel selectorPanel = new JPanel();
		panel.add(selectorPanel);
		selectorPanel.setLayout(new GridLayout(2, 2, 10, 10));
		
		JLabel lblCustomerName = new JLabel("Account Number");
		lblCustomerName.setHorizontalAlignment(SwingConstants.RIGHT);
		selectorPanel.add(lblCustomerName);
		
		tfSelectorAccNo = new JTextField();
		tfSelectorAccNo.setColumns(10);
		selectorPanel.add(tfSelectorAccNo);
		
		JLabel lblReportKind_1 = new JLabel("Customer Number");
		lblReportKind_1.setHorizontalAlignment(SwingConstants.RIGHT);
		selectorPanel.add(lblReportKind_1);
		
		tfSelectorCusNo = new JTextField();
		selectorPanel.add(tfSelectorCusNo);
		tfSelectorCusNo.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(2, 2, 10, 10));
		
		lblType = new JLabel("Type");
		lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_2.add(lblType);
		
		accType = new JComboBox(AccountModel.model.values());
		panel_2.add(accType);
		
		JLabel lblReportKind = new JLabel("Report Kind");
		lblReportKind.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_2.add(lblReportKind);
		
		cbReportKind = new JComboBox();
		panel_2.add(cbReportKind);
		cbReportKind.setModel(new DefaultComboBoxModel(new String[] {"", "Closed today", "Opened today", "Maturity today"}));
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 3, 10, 10));
		
		lblTime = new JLabel("Time");
		lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_3.add(lblTime);
		
		dateChooser_1 = new JDateChooser();
		panel_3.add(dateChooser_1);
		
		dateChooser = new JDateChooser();
		panel_3.add(dateChooser);
		
		lblAmount = new JLabel("Amount");
		lblAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_3.add(lblAmount);
		
		tfAmount = new IntegerTextField();
		tfAmount.setColumns(10);
		panel_3.add(tfAmount);
		
		btnNewButton = new JButton("Search");
		panel_3.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refeshData();
			}
		});
		
		
		tableModel = new MyTableModel();
		table = new JTable(tableModel);
		
		tableModel.setListener(new OnTableDataChanged() {
			
			@Override
			public void onDataChanged() {
				System.out.println("on data changed");
				refeshData();
				
			}
		});
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("Sum of outstanding : ");
		panel_1.add(lblNewLabel);
		
		lblOutstandingTotal = new JLabel("0");
		panel_1.add(lblOutstandingTotal);
		
	}
	
	private void refeshData(){
		String condition = "";

		switch (cbReportKind.getSelectedIndex()) {
			case 1:
				condition += DBService.CLOSED_DATE + "= curDate()";
				break;
			case 2:
				condition += DBService.OPENING_DATE + "= curDate()";
				break;
			case 3:
				condition += DBService.MATURITY + "= curDate()";
				break;
			case 0:
			default:
				break;
		}
		
		
		
		if (!StringUtils.isEmpty(tfSelectorCusNo.getText())){
			condition += (StringUtils.isEmpty(condition))? "" : " and ";
			condition += DBService.CUSTOMER_NO + "='" + tfSelectorCusNo.getText() +"'";
		}
		
		if (!StringUtils.isEmpty(tfSelectorAccNo.getText())){
			condition += (StringUtils.isEmpty(condition))? "" : " and ";
			condition += DBService.ACCOUNT_NO + "='" + tfSelectorAccNo.getText() +"'";
		}
		
		if (!StringUtils.isEmpty(tfAmount.getText())){
			condition += (StringUtils.isEmpty(condition))? "" : " and ";
			condition += DBService.BOOKED_BALANCE + ">=" + tfAmount.getText();
		}

		condition += (StringUtils.isEmpty(condition))? "" : " and ";
		condition += DBService.ACCOUNT_TYPE + "='" + AccountModel.model.values()[accType.getSelectedIndex()] + "'";
		
		tableModel = new MyTableModel(DBService.getInstance().getData(condition));
		tableModel.setListener(new OnTableDataChanged() {
			
			@Override
			public void onDataChanged() {
				System.out.println("on data changed");
				refeshData();
				
			}
		});
		table.setModel(tableModel);
		table.revalidate();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		NumberFormat f = NumberFormat.getInstance();

		lblOutstandingTotal.setText(f.format(tableModel.getSumOfOutstanding()));
	}
		
}

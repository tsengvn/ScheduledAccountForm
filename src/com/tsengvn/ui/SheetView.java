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

import org.apache.commons.lang3.StringUtils;

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
	private JPanel panel_2;
	private JLabel lblType;
	private JComboBox accType;

	/**
	 * Create the panel.
	 */
	public SheetView() {
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel selectorPanel = new JPanel();
		panel.add(selectorPanel);
		selectorPanel.setLayout(new GridLayout(0, 2, 10, 10));
		
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
		
		btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refeshData();
			}
		});
		
		panel_2 = new JPanel();
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
		panel.add(btnNewButton);
		
		
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

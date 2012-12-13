package com.tsengvn.ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JComboBox;

import com.tsengvn.service.DBService;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Creator: Hien Ngo
 * Date: Nov 23, 2012
 */
public class SheetView extends JPanel {
	
	private JTable table;
	private MyTableModel tableModel;
	private JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public SheetView() {
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JComboBox comboBox = new JComboBox();
		panel.add(comboBox);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				scrollPane.removeAll();
				tableModel = new MyTableModel(DBService.getInstance().getData());
				table.setModel(tableModel);
				table.revalidate();
				
				
				
			}
		});
		panel.add(btnNewButton);
		
		
		Vector<String> headers = new Vector<String>();
		headers.add(DBService.ACCOUNT_NO);
		headers.add(DBService.CUSTOMER_NAME);
		headers.add(DBService.RATE);
		headers.add(DBService.BOOKED_BALANCE);
		headers.add(DBService.OPENING_DATE);
		headers.add(DBService.CLOSED_DATE);
		headers.add(DBService.TERM);
		headers.add(DBService.AUTO_RENEW);
		headers.add(DBService.TRANSFER_ACCOUNT);
		headers.add(DBService.OUTSTANDING);
		tableModel = new MyTableModel(DBService.getInstance().getData());
		table = new JTable(tableModel);
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		
		add(scrollPane, BorderLayout.CENTER);
		

	}
	
	class MyTableModel extends AbstractTableModel{
		private String[] mColumnNames = {DBService.ACCOUNT_NO, DBService.CUSTOMER_NAME, DBService.RATE, 
			DBService.BOOKED_BALANCE, DBService.OPENING_DATE, DBService.CLOSED_DATE, DBService.TERM, DBService.AUTO_RENEW, 
			DBService.TRANSFER_ACCOUNT, DBService.OUTSTANDING};
		private Vector<Vector<Object>> mData;
		public MyTableModel(Vector<Vector<Object>> aData) {
			mData = aData;
		}
		
		@Override
		public String getColumnName(int column) {
			return mColumnNames[column];
		}
		
		@Override
		public int getColumnCount() {
			return mColumnNames.length;
		}

		@Override
		public int getRowCount() {
			return mData.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return mData.get(rowIndex).get(columnIndex);
		}
		
	}
}

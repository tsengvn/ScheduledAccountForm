package com.tsengvn.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import jxl.CellView;
import jxl.DateCell;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;

import com.toedter.calendar.JDateChooser;
import com.tsengvn.service.AccountModel;
import com.tsengvn.service.DBService;
import com.tsengvn.ui.MyTableModel.OnTableDataChanged;
import com.tsengvn.util.IntegerTextField;

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
	private JDateChooser dcTo;
	private JDateChooser dcFrom;
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
		cbReportKind.setModel(new DefaultComboBoxModel(new String[] {"", "Closed", "Opened", "Maturity"}));
		cbReportKind.addActionListener(mActionListener);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 3, 10, 10));
		
		lblTime = new JLabel("Duration");
		lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_3.add(lblTime);
		
		dcFrom = new JDateChooser();
		panel_3.add(dcFrom);
		dcFrom.setToolTipText("From date");
		dcFrom.getDateEditor().addPropertyChangeListener(mOnFromDateChangeListener);
		
		dcTo = new JDateChooser();
		panel_3.add(dcTo);
		dcTo.setToolTipText("To Date");
		dcTo.getDateEditor().addPropertyChangeListener(mOnToDateChangeListener);
		
		lblAmount = new JLabel("Amount");
		lblAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_3.add(lblAmount);
		
		tfAmount = new IntegerTextField();
		panel_3.add(tfAmount);
		tfAmount.setColumns(10);
		
		btnNewButton = new JButton("Search");
		panel.add(btnNewButton);
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
	
	private ActionListener mActionListener =  new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == cbReportKind){
				if (cbReportKind.getSelectedIndex() != 0 && dcFrom.getDate() == null && dcTo.getDate() == null){
					dcFrom.setDate(Calendar.getInstance().getTime());
					dcTo.setDate(Calendar.getInstance().getTime());
				}
				
				if (cbReportKind.getSelectedIndex() == 0){
					dcFrom.setCalendar(null);
					dcTo.setCalendar(null);
				}
			}
		}
	};
	
	private PropertyChangeListener mOnFromDateChangeListener = new PropertyChangeListener() {
		
		@Override
		public void propertyChange(PropertyChangeEvent e) {
			if (e.getNewValue() == null)
				return;
			
			if ("date".equals(e.getPropertyName())){
				Date newDate = (Date) e.getNewValue();
				
				if (dcTo.getDate() == null){
					dcTo.setDate(newDate);
				}
				
				if (newDate.getTime() > dcTo.getDate().getTime()){
					dcFrom.setDate((Date) e.getOldValue());
				}
			}	
		}
	};
	
	private PropertyChangeListener mOnToDateChangeListener = new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent e) {
			if (e.getNewValue() == null)
				return;
			
			if ("date".equals(e.getPropertyName()) && dcFrom.getDate() == null){
				Date newDate = (Date) e.getNewValue();
				dcFrom.setDate(newDate);
				
				if (newDate.getTime() < dcFrom.getDate().getTime()){
					dcTo.setDate((Date) e.getOldValue());
				}
			}
		}
	};
	
	private void refeshData(){
		String condition = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
		
		if (cbReportKind.getSelectedIndex() > 0){
			String temp = " %s >= '%s' and %s <= '%s' ";
			switch (cbReportKind.getSelectedIndex()) {
			case 1:
				condition += String.format(temp, DBService.CLOSED_DATE, dateFormat.format(dcFrom.getDate()), DBService.CLOSED_DATE, dateFormat.format(dcTo.getDate()));
				break;
			case 2:
				condition += String.format(temp, DBService.OPENING_DATE, dateFormat.format(dcFrom.getDate()), DBService.OPENING_DATE, dateFormat.format(dcTo.getDate()));
				break;
			case 3:
				condition += String.format(temp, DBService.MATURITY, dateFormat.format(dcFrom.getDate()), DBService.MATURITY, dateFormat.format(dcTo.getDate()));
				break;
			}
			System.out.println(condition);
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
		NumberFormat f = NumberFormat.getInstance();

		lblOutstandingTotal.setText(f.format(tableModel.getSumOfOutstanding()));
	}
		
	
	public void doExport(){
		JFileChooser fc = new JFileChooser();
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Specify a file to save");
		fc.setSelectedFile(new File("mySheet.xls"));
		
		if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
			File outFile = fc.getSelectedFile();
			if(!outFile.getPath().toLowerCase().endsWith(".xls")){
				outFile = new File(outFile.getPath() + ".xls");
			}
			export(outFile);
			
		}
		
	}
	
	private void export(File outFile){
		try {
			WritableWorkbook  workbook = Workbook.createWorkbook(outFile);
			
			WritableFont times16fontBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD); 
			WritableCellFormat times16formatBold = new WritableCellFormat (times16fontBold);
			
			WritableFont times16font = new WritableFont(WritableFont.TIMES, 12); 
			WritableCellFormat times16format = new WritableCellFormat (times16font);
			
			WritableSheet sheet = workbook.createSheet("First Sheet", 0);
			int row = 0;
			//
			Label label = new Label(0, row, "VIETNAM BANK FOR AGRICULTURE &RURAL DEVELOPMENT", times16formatBold);
			sheet.addCell(label);
			
			//***********
			row++;
			label = new Label(0, row, "SONG THAN INDUSTRIAL ZONE BRANCH", times16formatBold); 
			sheet.addCell(label);
			
			//***********
			row+=2;
			String title = "TIME DEPOSIT ACCOUNT LIST REPORT";
			if (!StringUtils.isEmpty(tfSelectorCusNo.getText())){
				String name = DBService.getInstance().getCustomerName(tfSelectorCusNo.getText());
				if (name != null){
					title += " OF " + name;
				}
			}
			label = new Label(0, row, title, times16formatBold); 
			sheet.addCell(label);
			
			//***********
			row++;
			label = new Label(0, row, "Account Type : " + accType.getSelectedItem()); 
			sheet.addCell(label);
			
			
			//***********
			row++;
			for (int i=0 ; i<tableModel.getColumnCount() ; i++){
				CellView cv = new CellView();
				
				if (i==0){
					cv.setSize(30);
				} else {
					cv.setAutosize(true);	
				} 
				
				sheet.setColumnView(i, cv);
				label = new Label(i, row, tableModel.getColumnName(i), times16formatBold); 
				sheet.addCell(label);
			}
			
			//***********
			row++;
			for (int i=0 ; i<tableModel.getRowCount() ; i++){
				for (int j=0 ; j<tableModel.getColumnCount() ; j++){
					if (tableModel.getColumnClass(j) == String.class ||  tableModel.getColumnClass(j) == Boolean.class ){
						label = new Label(j, i+row,  tableModel.getValueAt(i, j)+"");
						sheet.addCell(label);
					} else if (tableModel.getColumnClass(j) == java.sql.Date.class){
						
						java.sql.Date date = (java.sql.Date) tableModel.getValueAt(i, j);
						if (date != null){
							SimpleDateFormat format = new SimpleDateFormat("MMM/dd/YYYY");
							label = new Label(j, i+row, format.format(date));
							sheet.addCell(label);
						}
						
					} else {
						
						Number number = new Number(j, i+row, Double.parseDouble(tableModel.getValueAt(i, j) +""));
//						label = new Label(j, i+row, tableModel.getValueAt(i, j) + ""); 
						sheet.addCell(number);
					}
					
				}
			}
			
			workbook.write(); 
			workbook.close();
			
			System.out.println("Export done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

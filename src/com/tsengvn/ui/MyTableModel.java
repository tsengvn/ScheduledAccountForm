package com.tsengvn.ui;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.StringUtils;

import com.tsengvn.service.DBService;

public class MyTableModel extends AbstractTableModel{
	private String[] mColumnNames = {
			DBService.ACCOUNT_NO, 
			DBService.CUSTOMER_NO,
			DBService.CUSTOMER_NAME,
			DBService.RATE,
			DBService.TERM,
			DBService.OPENING_DATE,
			DBService.BOOKED_BALANCE,
			DBService.CLOSED_DATE,
			DBService.MATURITY,
			DBService.AUTO_RENEW,
			DBService.TRANSFER_ACCOUNT,
			DBService.OUTSTANDING,
			"Closed"
			};
	private Vector<Vector<Object>> mData;
	
	private OnTableDataChanged mListener;
	
	public MyTableModel() {
		mData = new Vector<Vector<Object>>();
	}
	
	public MyTableModel(Vector<Vector<Object>> aData) {
		mData = aData;
		
	}
	
	@Override
	public String getColumnName(int column) {
		return StringUtils.capitalize(mColumnNames[column].replaceAll("_", " "));
	}
	
	@Override
	public int getColumnCount() {
		return mColumnNames.length;
	}

	@Override
	public int getRowCount() {
		return (mData != null) ? mData.size() : 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return mData.get(rowIndex).get(columnIndex);
	}
	
	@Override
	public Class<?> getColumnClass(int i) {
		if (mData.get(0).get(i) == null){
			return super.getColumnClass(i);
		}
		return mData.get(0).get(i).getClass();
	}
	
	 public boolean isCellEditable(int row, int col) {
	        if (mColumnNames[col].equals("Closed")) {
	            return true;
	        } else {
	            return false;
	        }
	 }
	 
	 public void setValueAt(Object value, int row, int col) {
		 String accNo = (String) mData.get(row).get(0);
		 boolean closed = (Boolean) value;
		 
		 System.out.println("closed : " + closed);
		 if (!closed){
			 return;
		 }
			 
		 
		 if (DBService.getInstance().updateClosed(closed, accNo) == 1){
			 System.out.println("updateClosed : ok");
			 Vector<Object> modifiedRow = mData.get(row);
			 modifiedRow.set(col, value);
			 mData.set(row, modifiedRow);
			 System.out.println("mListener != null : " + (mListener != null));
			 if (mListener != null){
				 mListener.onDataChanged();
				 System.out.println("mListener.onDataChanged() : ");
			 }
		 }
	 }
	 
	 public float getSumOfOutstanding(){
		 float sum = 0;
//		 int outstandingIndex = 0;
//		 for (int i=0 ; i<mColumnNames.length ; i++){
//			 if (mColumnNames[i].equals(DBService.OUTSTANDING)){
//				 outstandingIndex = i;
//				 break;
//			 }
//		 }
		 
		 for (int i=0 ; i<getRowCount() ; i++){
			 sum += (Float) getValueAt(i, 11);
		 }
		 return sum;
	 }
	
	 public OnTableDataChanged getListener() {
		return mListener;
	}

	public void setListener(OnTableDataChanged mListener) {
		this.mListener = mListener;
	}

	public static interface OnTableDataChanged{
		 void onDataChanged();
	 }
}
package com.tsengvn.service;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;

import org.apache.commons.lang3.StringUtils;

/**
 * Creator: Hien Ngo
 * Date: Nov 20, 2012
 */
public class DBService {
	private final static String DATABASE_URL = "jdbc:hsqldb:file:db/clientdb";
	private final static String ACCOUNT_TABLE = "account";
	
	private static DBService instance = null;
	
	private Connection mCon;
	
	public final static String ACCOUNT_NO = "account_number";
	public final static String CUSTOMER_NO = "customer_number";
	public final static String CUSTOMER_NAME = "customer_name";
	public final static String RATE = "rate";
	public final static String OPENING_DATE = "opening_date";
	public final static String BOOKED_BALANCE = "booked_balance";
	public final static String CLOSED_DATE = "closed_date";
	public final static String MATURITY = "maturity";
	public final static String AUTO_RENEW = "auto_renew";
	public final static String TRANSFER_ACCOUNT = "transfer_account";
	public final static String ACCOUNT_TYPE = "account_type";
	public final static String TERM = "term";
	public final static String OUTSTANDING = "outstanding";
	
	public static DBService getInstance(){
		if (instance == null){
			instance = new DBService();
		}
		return instance;
	}
	
	public void init(){
		if (new File("db").exists()){
			System.out.println("Database exist, no need to create.");
			return;
		}
		
		try{
			openConnection();
			DatabaseMetaData meta = mCon.getMetaData();
			ResultSet res = meta.getTables(null, null, ACCOUNT_TABLE , null);
			
			if (!res.next()){
				String createTable = "Create table " + ACCOUNT_TABLE +
						"( " +
						ACCOUNT_NO 		+ " varchar(20) primary key ," +
						CUSTOMER_NO 	+ " varchar(20)," +
						CUSTOMER_NAME 	+ " varchar(255)," +
						RATE 			+ " float," +
						OPENING_DATE 	+ " date," +
						BOOKED_BALANCE 	+ " float," +
						CLOSED_DATE 	+ " date," +
						MATURITY 		+ " date," +
						AUTO_RENEW 		+ " boolean," +
						TRANSFER_ACCOUNT 	+ " varchar(20)," +
						ACCOUNT_TYPE 	+ " varchar(3)," +
						OUTSTANDING + " float," +
						TERM + " int" +
						")";
				mCon.createStatement().execute(createTable);
				System.out.println("Create database for first run ok !");
			} else {
				System.out.println("Database exist, no need to create.");
			}
			
			closeConnetion();
		}catch (SQLException e){
			closeConnetion();
			System.err.println("Error : " + e.getMessage());
		}
		
	}
	
	public void openConnection() throws SQLException{
		mCon = DriverManager.getConnection(DATABASE_URL, "sa", "");
		
	}
	
	public void closeConnetion(){
		try {
			mCon.close();
			mCon = null;
		} catch (Exception e) {
		}
		
	}
	
	public boolean addNewAccount(AccountModel aAccount){
		try {
			openConnection();
			String insert = "insert into " + ACCOUNT_TABLE + " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = mCon.prepareStatement(insert);
			pstmt.setString(1, aAccount.getAccNo());
			pstmt.setString(2, aAccount.getCusNo());
			pstmt.setString(3, aAccount.getCusName());
			pstmt.setFloat(4, aAccount.getRate());
			pstmt.setDate(5, new java.sql.Date(aAccount.getOpenDate()));
			pstmt.setLong(6, aAccount.getBookedBalance());
			pstmt.setDate(7, null);
			pstmt.setDate(8, new java.sql.Date((aAccount.getMaturity())));
			pstmt.setBoolean(9, aAccount.isAutoRenew());
			pstmt.setString(10, aAccount.getAutoTransfer());
			pstmt.setString(11, aAccount.getAccType());
			pstmt.setLong(12, aAccount.getOutstanding());
			pstmt.setInt(13, aAccount.getTerm());
			pstmt.executeUpdate();
//			String insert = "insert into " + ACCOUNT_TABLE + " values(" + 
//					"'"+ aAccount.getAccNo() + "'," +
//					"'"+ aAccount.getCusNo() + "'," +
//					"'"+  aAccount.getCusName() + "'," +
//					aAccount.getRate() + "," +
//					aAccount.getOpenDate() +  "," +
//					aAccount.getBookedBalance() + "," +
//					aAccount.getCloseDate() + "," +
//					aAccount.getMaturity() + "," +
//					aAccount.isAutoRenew() + "," +
//					"'"+ aAccount.getAutoTransfer() + "'," +
//					"'"+ aAccount.getAccType() + "'," +
//					aAccount.getOutstanding() + "," +
//					aAccount.getTerm() +
//					")";
			System.out.println("Insert SQL : " + insert);
//			mCon.createStatement().execute(insert);
			closeConnetion();
			return true;
		} catch (Exception e) {
			closeConnetion();
			System.err.println("Error : " + e.getMessage());
			return false;
		}
		
	}
	
	public Vector getData(){
		return getData(null);
	}
	
	public Vector getData(String condition){
		String query = "select * from " + ACCOUNT_TABLE;
		if (!StringUtils.isEmpty(condition)){
			query += " where " + condition;
		} 
		
		query += " order by " + OPENING_DATE;
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		try {
			openConnection();
			ResultSet rs = mCon.createStatement().executeQuery(query);
			while (rs.next()){
				Vector<Object> acc = new Vector<Object>();
				acc.add(rs.getString(ACCOUNT_NO));
				acc.add(rs.getString(CUSTOMER_NO));
				acc.add(rs.getString(CUSTOMER_NAME));
				acc.add(rs.getFloat(RATE));
				acc.add(rs.getInt(TERM));
				acc.add(rs.getDate(OPENING_DATE));
				acc.add(rs.getFloat(BOOKED_BALANCE));
				acc.add(rs.getDate(CLOSED_DATE));
				acc.add(rs.getDate(MATURITY));
				acc.add(rs.getBoolean(AUTO_RENEW));
				acc.add(rs.getString(TRANSFER_ACCOUNT));
				acc.add(rs.getFloat(OUTSTANDING));
				acc.add(rs.getDate(CLOSED_DATE) != null);	
				data.add(acc);
			}
			System.out.println("total data : " + data.size());
			closeConnetion();
		} catch (Exception e) {
			closeConnetion();
			e.printStackTrace();
		}
		return data;
	}
	
	public ArrayList<String> getAllCustomerName(){
		ArrayList<String> result = new ArrayList<String>();
		try {
			openConnection();
			ResultSet rs = mCon.createStatement().executeQuery("select " + CUSTOMER_NAME + " from " + ACCOUNT_TABLE);
			while (rs.next()){
				result.add(rs.getString(1));
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		closeConnetion();
		return result;
	}
	
	public String getCustomerName(String cusId){
		ArrayList<String> result = new ArrayList<String>();
		try {
			openConnection();
			String query = "select " + CUSTOMER_NAME + " from " + ACCOUNT_TABLE + " where " + CUSTOMER_NO + "=?";
			PreparedStatement ps = mCon.prepareStatement(query);
			ps.setString(1, cusId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				result.add(rs.getString(1));
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		closeConnetion();
		return (result.isEmpty())? null : result.get(0);
	}
	
	
	public int updateClosed(boolean closed, String accNo){
		int result = 0;
		try {
			openConnection();
			String update;
			if (closed){
				update = "update " + ACCOUNT_TABLE + " set " + CLOSED_DATE + "=curDate(), " + OUTSTANDING + "=0 where " + ACCOUNT_NO + " =?" ;
			} else {
				update = "update " + ACCOUNT_TABLE + " set " + CLOSED_DATE + "=null where " + ACCOUNT_NO + " =?";
			}
			PreparedStatement pstmt = mCon.prepareStatement(update);
			pstmt.setString(1, accNo);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeConnetion();
		return result;
	}
	
}

package com.tsengvn.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

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
		try{
			openConnection();
			DatabaseMetaData meta = mCon.getMetaData();
			ResultSet res = meta.getTables(null, null, ACCOUNT_TABLE , null);
			
			if (!res.next()){
				String createTable = "Create table " + ACCOUNT_TABLE +
						"( " +
						ACCOUNT_NO 		+ " varchar(20) primary key ," +
//						CUSTOMER_NO 	+ " varchar(20)," +
						CUSTOMER_NAME 	+ " varchar(255)," +
						RATE 			+ " float," +
						OPENING_DATE 	+ " BIGINT," +
						BOOKED_BALANCE 	+ " float," +
						CLOSED_DATE 	+ " BIGINT," +
//						MATURITY 		+ " BIGINT," +
						AUTO_RENEW 		+ " boolean," +
						TRANSFER_ACCOUNT 	+ " varchar(20)," +
						ACCOUNT_TYPE 	+ " varchar(3)," +
						OUTSTANDING + " float," +
						TERM + " int" +
						")";
				mCon.createStatement().execute(createTable);
				System.out.print("Create database for first run ok !");
			} else {
				System.out.print("Database exist, no need to create.");
			}
			
			closeConnetion();
		}catch (SQLException e){
			closeConnetion();
			System.err.println("Error : " + e.getMessage());
		}
		
	}
	
	public boolean addNewAccount(AccountModel aAccount){
		try {
			openConnection();
			String insert = "insert into " + ACCOUNT_TABLE + " values(" + 
					"'"+ aAccount.getAccNo() + "'," +
//					"'"+ aAccount.getCusNo() + "'," +
					"'"+  aAccount.getCusName() + "'," +
					aAccount.getRate() + "," +
					aAccount.getOpenDate() +  "," +
					aAccount.getBookedBalance() + "," +
					aAccount.getCloseDate() + "," +
//					aAccount.getMaturity() + "," +
					aAccount.isAutoRenew() + "," +
					"'"+ aAccount.getAutoTransfer() + "'," +
					"'"+ aAccount.getAccType() + "'," +
					aAccount.getOutstanding() + "," +
					aAccount.getTerm() +
					")";
			System.out.println("Insert SQL : " + insert);
			mCon.createStatement().execute(insert);
			closeConnetion();
			return true;
		} catch (Exception e) {
			closeConnetion();
			System.err.println("Error : " + e.getMessage());
			return false;
		}
		
	}
	
	public Vector getData(){
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		try {
			openConnection();
			ResultSet rs = mCon.createStatement().executeQuery("select * from " + ACCOUNT_TABLE);
			while (rs.next()){
				Vector<Object> acc = new Vector<Object>();
				acc.add(rs.getString(ACCOUNT_NO));
				acc.add(rs.getString(CUSTOMER_NAME));
				acc.add(rs.getFloat(RATE));
				acc.add(rs.getFloat(BOOKED_BALANCE));
				acc.add(new Date(rs.getLong(OPENING_DATE)));
				acc.add(new Date(rs.getLong(CLOSED_DATE)));
				acc.add(rs.getInt(TERM));
				acc.add(rs.getBoolean(AUTO_RENEW));
				acc.add(rs.getString(TRANSFER_ACCOUNT));
				acc.add(rs.getFloat(OUTSTANDING));
				
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
}

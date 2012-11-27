package com.tsengvn.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creator: Hien Ngo
 * Date: Nov 20, 2012
 */
public class DBService {
	private final static String DATABASE_URL = "jdbc:hsqldb:file:db/clientdb";
	private final static String ACCOUNT_TABLE = "account";
	
	private final static String ACCOUNT_NO = "account_number";
	private final static String CUSTOMER_NO = "customer_number";
	private final static String CUSTOMER_NAME = "customer_name";
	private final static String RATE = "rate";
	private final static String OPENING_DATE = "opening_date";
	private final static String BOOKED_BALANCE = "booked_balance";
	private final static String CLOSED_DATE = "closed_date";
	private final static String MATURITY = "maturity";
	private final static String AUTO_RENEW = "auto_renew";
	private final static String TRANSFER_ACCOUNT = "transfer_account";
	private final static String ACCOUNT_TYPE = "account_type";
	private static DBService instance = null;
	
	private Connection mCon;
	
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
						CUSTOMER_NO 	+ " varchar(20)," +
						CUSTOMER_NAME 	+ " varchar(255)," +
						RATE 			+ " float," +
						OPENING_DATE 	+ " BIGINT," +
						BOOKED_BALANCE 	+ " float," +
						CLOSED_DATE 	+ " BIGINT," +
						MATURITY 		+ " BIGINT," +
						AUTO_RENEW 		+ " boolean," +
						TRANSFER_ACCOUNT 	+ " varchar(20)," +
						ACCOUNT_TYPE 	+ " varchar(3)" +
						")";
				mCon.createStatement().execute(createTable);
				System.out.print("Create database for first run ok !");
			} else {
				System.out.print("Database exist, no need to create.");
			}
			
			closeConnetion();
		}catch (SQLException e){
			System.err.println("Error : " + e.getMessage());
		}
		
	}
	
	public boolean addNewAccount(AccountModel aAccount){
		try {
			openConnection();
			String insert = "insert into " + ACCOUNT_TABLE + " values(" + 
					"'"+ aAccount.getAccNo() + "'," +
					"'"+ aAccount.getCusNo() + "'," +
					"'"+  aAccount.getCusName() + "'," +
					aAccount.getRate() + "," +
					aAccount.getOpenDate() +  "," +
					aAccount.getBookedBalance() + "," +
					aAccount.getCloseDate() + "," +
					aAccount.getMaturity() + "," +
					aAccount.isAutoRenew() + "," +
					"'"+ aAccount.getAutoTransfer() + "'," +
					"'"+ aAccount.getAccType() + "'" +
					")";
			System.out.println("Insert SQL : " + insert);
			mCon.createStatement().execute(insert);
			return true;
		} catch (Exception e) {
			System.err.println("Error : " + e.getMessage());
			return false;
		}
		
	}
	
	public void openConnection() throws SQLException{
		mCon = DriverManager.getConnection(DATABASE_URL, "sa", "");
	}
	
	public void closeConnetion() throws SQLException{
		mCon.close();
	}
}

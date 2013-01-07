package com.tsengvn.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;

import com.jgoodies.looks.FontPolicies;
import com.jgoodies.looks.FontPolicy;
import com.jgoodies.looks.FontSet;
import com.jgoodies.looks.FontSets;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.tsengvn.service.DBService;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JTabbedPane;

/**
 * Creator: Hien Ngo
 * Date: Nov 20, 2012
 */
public class ScheduledAccountForm {
	
	private JFrame mFrame;
	private JPanel mCurrentPanel;
	private JMenuBar mMenuBar;
	
	private JPanel addView;
	private JPanel sheetView;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScheduledAccountForm window = new ScheduledAccountForm();
					window.mFrame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ScheduledAccountForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		DBService.getInstance().init();
		
		mFrame = new JFrame();
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mFrame.setBounds(100, 100, 893, 575);
		
		mMenuBar = new JMenuBar();
		mFrame.setJMenuBar(mMenuBar);
		
		JMenu mnFile = new JMenu("File");
		mMenuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnHelp = new JMenu("Help");
		mMenuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		mFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		sheetView = new SheetView();
		tabbedPane.addTab("Sheet", null, sheetView, null);
		
		addView = new AddAccountView();
		tabbedPane.addTab("Add new", null, addView, null);
		tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) arg0.getSource();
		        if (sourceTabbedPane.getSelectedIndex() == 0){
		        	
		        }
				
			}
		});

	}
	
	public void setMainView(JPanel aPanel){
		if (mCurrentPanel != null){
			mFrame.remove(mCurrentPanel);
		}
		mFrame.getContentPane().add(aPanel);
		Rectangle newSize = aPanel.getBounds();
		newSize.height += mMenuBar.getBounds().height;
		mFrame.setBounds(aPanel.getBounds(newSize));
		mFrame.setVisible(true);
		mFrame.getContentPane().add(aPanel, BorderLayout.CENTER);
	}	

}

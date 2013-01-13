package com.tsengvn.ui;

import java.io.File;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String root = new String("C:\\Users\\tsengvn\\Desktop\\Theme\\final\\themeAndroid_2\\res");
		
//		doRemove(root);
		
		doSignin(root);
		
		System.out.print("done");
	}
	
	public static void doSignin(String s){
		File root = new File(s);
		for (File folder : root.listFiles()){
			if (folder.isDirectory()){
				for (File file : folder.listFiles()){
					if (file.getName().contains("btn_sign_in_disabled")
							|| file.getName().contains("btn_sign_in_hover")
							|| file.getName().contains("btn_sign_in_normal")
							|| file.getName().contains("btn_sign_in_pressed")){
						file.delete();
					}
				} 
				
				for (File file : folder.listFiles()){
					if (file.getName().contains("Untitled-3_0003_Layer-1")){
						String newName = folder.getAbsolutePath() + "/btn_sign_in_pressed.png";
						file.renameTo(new File(newName));
					}
					
					if (file.getName().contains("Untitled-3_0002_Layer-2")){
						String newName = folder.getAbsolutePath() + "/btn_sign_in_hover.png";
						file.renameTo(new File(newName));
					}
					
					if (file.getName().contains("Untitled-3_0001_Layer-3")){
						String newName = folder.getAbsolutePath() + "/btn_sign_in_normal.png";
						file.renameTo(new File(newName));
					}
					
					if (file.getName().contains("Untitled-3_0000_Layer-4")){
						String newName = folder.getAbsolutePath() + "/btn_sign_in_disabled.png";
						file.renameTo(new File(newName));
					}
				} 
			}
		}
	}

	
	public static void doRemove(String s){
		File root = new File(s);
		for (File folder : root.listFiles()){
			if (folder.isDirectory()){
				for (File file : folder.listFiles()){
					if (file.getName().contains("btn_remove_pressed")
							|| file.getName().contains("btn_remove_disabled")
							|| file.getName().contains("btn_remove_hover")
							|| file.getName().contains("btn_remove_normal")){
						file.delete();
					}
				} 
				
				for (File file : folder.listFiles()){
					if (file.getName().contains("Untitled-3_0003_Layer-1")){
						String newName = folder.getAbsolutePath() + "/btn_remove_pressed.png";
						file.renameTo(new File(newName));
					}
					
					if (file.getName().contains("Untitled-3_0002_Layer-2")){
						String newName = folder.getAbsolutePath() + "/btn_remove_hover.png";
						file.renameTo(new File(newName));
					}
					
					if (file.getName().contains("Untitled-3_0001_Layer-3")){
						String newName = folder.getAbsolutePath() + "/btn_remove_normal.png";
						file.renameTo(new File(newName));
					}
					
					if (file.getName().contains("Untitled-3_0000_Layer-4")){
						String newName = folder.getAbsolutePath() + "/btn_remove_disabled.png";
						file.renameTo(new File(newName));
					}
				} 
			}
		}
	}

}

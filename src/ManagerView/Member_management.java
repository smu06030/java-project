package ManagerView;

import java.awt.Container;

import javax.swing.JFrame;

public class Member_management extends JFrame {
	Container ct;
	
	Member_management(){
		
	}
	
	public static void main(String[] args) {
		Member_management member = new Member_management();
		
		member.setVisible(true);
		member.setSize(1200,800);
		member.setLocationRelativeTo(null);
		member.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

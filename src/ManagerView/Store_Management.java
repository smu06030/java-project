package ManagerView;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import DBCheck.data_check;

public class Store_Management extends JFrame implements ActionListener{
	private data_check checking = new data_check();
	private Container ct;
	private JLabel loc_name1,loc_name2,loc_num1,loc_num2;
	private JButton btn;
	
	Store_Management(){
		ct = getContentPane();
		
		/*---------------------------- ������� ȭ�� ------------------------------*/
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5,1,0,0));
		panel.setBackground(new Color(22,28,24));
		
		// ������ ��
		loc_name1 = new JLabel("������");
		loc_name1.setFont(new Font("����������",Font.BOLD,25));
		loc_name1.setForeground(Color.white);
		loc_name1.setBorder(BorderFactory.createEmptyBorder(-10,130,0,0));
		
		// �������� ����DB���� �����´�.
		loc_name2 = new JLabel("["+checking.Store_name()+"]");
		loc_name2.setFont(new Font("����������",Font.BOLD,25));
		loc_name2.setForeground(Color.white);
		loc_name2.setBorder(BorderFactory.createEmptyBorder(-10,95,0,0));
		
		// ����ڹ�ȣ ��
		loc_num1 = new JLabel("����ڹ�ȣ");
		loc_num1.setFont(new Font("����������",Font.BOLD,25));
		loc_num1.setForeground(Color.white);
		loc_num1.setBorder(BorderFactory.createEmptyBorder(0,110,0,0));
		
		// ����ڹ�ȣ�� ����DB���� �����´�.
		loc_num2 = new JLabel("["+checking.Store_num()+"]");
		loc_num2.setFont(new Font("����������",Font.BOLD,25));
		loc_num2.setForeground(Color.white);
		loc_num2.setBorder(BorderFactory.createEmptyBorder(0,90,0,0));
		
		// �¼��� ���� ��ư
		btn = new JButton("�¼� ��");
		btn.setFont(new Font("����������",Font.BOLD,20));
		btn.setForeground(Color.white);
		btn.setBorder(null);
		btn.setFocusPainted(false);
		btn.setBackground(new Color(243,97,185));
		
		panel.add(loc_name1);
		panel.add(loc_name2);
		panel.add(loc_num1);
		panel.add(loc_num2);
		panel.add(btn);
		
		ct.add(panel);
		
		/*---------------------------- �̺�Ʈ ------------------------------*/
		
		btn.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		// �¼����� �Է��ؼ� DB�� �����Ѵ�.
		try {
			String num = JOptionPane.showInputDialog("�¼� ���� �Է����ּ���");
			int seatNum = Integer.parseInt(num);
			if(seatNum>30 || seatNum < 1)
				JOptionPane.showMessageDialog(null, "1~30���� �Է����ּ���");
			else {
				checking.seatNum(seatNum);
				JOptionPane.showMessageDialog(null, "<html>�¼��� ���� �Ǿ����ϴ�<br>���������ּ���.</html>");
			}
		}catch(NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "���ڸ� �Է��� �ּ���");
		}
	}
	
	/*---------------------------- ���� ------------------------------*/
	
	public static void main(String[] args) {
		Store_Management sm = new Store_Management();
		sm.setVisible(true);
		sm.setSize(350,300);
		sm.setTitle("�������");
		sm.setLocationRelativeTo(null);
		sm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

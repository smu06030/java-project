package ManagerView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DBCheck.data_check;

public class Member_UpAndDel extends JFrame implements ActionListener {
	
	private data_check checking = new data_check();
	private String phones[] = {"010","011","017","019"};
	private String emails[] = {"naver.com","google.com","nate.com","daum.net","hanmail.com"};
	private Vector<String> year = new Vector<String>();
	private Vector<String> month = new Vector<String>();
	private Vector<String> day = new Vector<String>();
	
	private JLabel id,password,name,birth,phone,email,email2;
	private JTextField id_field,name_field,birth_field,phone_field,email_field;
	private JPasswordField p_field;
	private JButton update,delete,close;
	private JComboBox year_box, month_box, day_box, phone_box, email_box;
	private Container ct;
	private static String pass;
		
	Member_UpAndDel(String id, String pass, String name, String years, int months, int days, String first_p, String last_p, String first_e0, String first_e1){
		start();
		this.pass = pass;
		id_field.setText(id); // ���̵� ����
		name_field.setText(name); // �̸� ����
		
		/*---------------------------- ������� �޺��ڽ� ���� �߰� ------------------------------*/

		//��,��,���� ���ͷ� �޺� �ڽ��� ����
		for(int i = 1990; i<=2010; i++)
			year.add(Integer.toString(i));
		for(int i = 1; i<=12; i++)
			month.add(Integer.toString(i));
		for(int i = 1;i <=31;i++)
			day.add(Integer.toString(i));
		
		// �� ����
		for(int i = 0;i<year.size();i++) 
			if(year.get(i).equals(years))
				year_box.setSelectedIndex(i);
		// �� ����
		for(int i = 0;i<month.size();i++) 
			if(month.get(i).equals(Integer.toString(months)))
				month_box.setSelectedIndex(i);
		// �� ����
		for(int i = 0;i<day.size();i++) 
			if(day.get(i).equals(Integer.toString(days)))
				day_box.setSelectedIndex(i);
		// �� ��ȣ ����	
		for(int i = 0;i<phones.length;i++)
			if(phones[i].equals(first_p))
				phone_box.setSelectedIndex(i);
		
		phone_field.setText(last_p); // �� ��ȣ ����
		email_field.setText(first_e0); // �� �̸��� ����
		
		// �� �̸��� ����
		for(int i = 0;i<emails.length;i++)
			if(emails[i].equals(first_e1))
				email_box.setSelectedIndex(i);
	}
	
	public void start() {
		ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		/*---------------------------- ȸ������ �г� ------------------------------*/
		
		// ȸ�� ���� �Է� �г�
		JPanel top = new JPanel();
		top.setLayout(null);
		top.setBackground(new Color(22,28,24));
		
		// ����
		JPanel bg = new JPanel();
		bg.setLayout(null);
		bg.setBounds(30,17,410,400);
		bg.setBackground(new Color(74,73,71));
				
		// ���̵� ��
		id = new JLabel("���̵� : ");
		id.setFont(new Font("�������",Font.BOLD,19));
		id.setBounds(75,20,150,50);
		id.setForeground(Color.WHITE);
		top.add(id);
		
		// ���̵� �ʵ�
		id_field = new JTextField(20);
		id_field.setFont(new Font("�������",Font.BOLD,20));
		id_field.setBounds(160,25,240,40);
		id_field.setEditable(false);
		top.add(id_field);
		
		// ��й�ȣ ��
		password = new JLabel("��й�ȣ : ");
		password.setFont(new Font("�������",Font.BOLD,19));
		password.setBounds(56,85, 150, 50);
		password.setForeground(Color.white);
		top.add(password);
		
		// �н����� �ʵ�
		p_field = new JPasswordField(20);
		p_field.setFont(new Font("�������",Font.BOLD,20));
		p_field.setBounds(160,90,240,40);
		top.add(p_field);
	
		// �̸� ��
		name = new JLabel("�̸� : ");
		name.setFont(new Font("�������",Font.BOLD,19));
		name.setBounds(91,150,150,50);
		name.setForeground(Color.WHITE);
		top.add(name);
		
		// �̸� �ʵ�
		name_field = new JTextField(20);
		name_field.setFont(new Font("�������",Font.BOLD,20));
		name_field.setBounds(160,155,240,40);
		top.add(name_field);
		
		// ���� ��
		birth = new JLabel("������� : ");
		birth.setFont(new Font("�������",Font.BOLD,19));
		birth.setBounds(56,215,150,50);
		birth.setForeground(Color.WHITE);
		top.add(birth);
		
		// �⵵
		year_box = new JComboBox(year);
		year_box.setBounds(160,225,70,30);
		year_box.setBackground(Color.WHITE);
		year_box.setForeground(Color.BLACK);
		top.add(year_box);
		
		// ��
		month_box = new JComboBox(month);
		month_box.setBounds(245,225,70,30);
		month_box.setBackground(Color.WHITE);
		month_box.setForeground(Color.BLACK);
		top.add(month_box);
		
		// ��
		day_box = new JComboBox(day);
		day_box.setBounds(330,225,70,30);
		day_box.setBackground(Color.WHITE);
		day_box.setForeground(Color.BLACK);
		top.add(day_box);
		
		// �޴��� ��
		phone = new JLabel("�޴��� : ");
		phone.setFont(new Font("�������",Font.BOLD,19));
		phone.setBounds(75,280,150,50);
		phone.setForeground(Color.WHITE);
		top.add(phone);
		
		// �޴��� �޺� �ڽ�
		phone_box = new JComboBox(phones);
		phone_box.setBounds(160,290,70,30);
		phone_box.setBackground(Color.WHITE);
		phone_box.setForeground(Color.BLACK);
		top.add(phone_box);
		
		// �޴��� �ʵ�
		phone_field = new JTextField(20);
		phone_field.setBounds(245,290,155,30);
		phone_field.setForeground(Color.BLACK);
		phone_field.setFont(new Font("�������",Font.BOLD,16));
		top.add(phone_field);
		
		// �̸��� ��
		email = new JLabel("�̸��� : ");
		email.setFont(new Font("�������",Font.BOLD,19));
		email.setBounds(75,345,150,50);
		email.setForeground(Color.WHITE);
		top.add(email);
		
		// �̸���
		email_field = new JTextField(20);
		email_field.setBounds(160,355,100,30);
		email_field.setForeground(Color.BLACK);
		email_field.setFont(new Font("�������",Font.BOLD,16));
		top.add(email_field);
		
		// @
		email2 = new JLabel("@");
		email2.setFont(new Font("�������",Font.BOLD,15));
		email2.setBounds(265,345,150,50);
		email2.setForeground(Color.WHITE);
		top.add(email2);
		
		// �̸��� �޺� �ڽ�
		email_box = new JComboBox(emails);
		email_box.setBounds(285,355,115,30);
		email_box.setBackground(Color.WHITE);
		email_box.setForeground(Color.BLACK);
		top.add(email_box);
		
		// ���� ��ư
		update = new JButton("����");
		update.setBounds(70,435,100,40);
		update.setFont(new Font("�������",Font.BOLD,20));
		update.setBackground(new Color(212,84,169));
		update.setForeground(Color.WHITE);
		update.setBorder(null);
		update.setFocusPainted(false);
		top.add(update);
		
		// Ż�� ��ư
		delete = new JButton("Ż��");
		delete.setBounds(185,435,100,40);
		delete.setFont(new Font("�������",Font.BOLD,20));
		delete.setBackground(new Color(212,84,169));
		delete.setForeground(Color.WHITE);
		delete.setBorder(null);
		delete.setFocusPainted(false);
		top.add(delete);
		
		// �ݱ� ��ư
		close = new JButton("�ݱ�");
		close.setBounds(300,435,100,40);
		close.setFont(new Font("�������",Font.BOLD,20));
		close.setBackground(new Color(212,84,169));
		close.setForeground(Color.WHITE);
		close.setBorder(null);
		close.setFocusPainted(false);
		top.add(close);
		top.add(bg);
		
		/*---------------------------- �����̳� �߰� ------------------------------*/
		
		ct.add(top,BorderLayout.CENTER);
		
		/*---------------------------- �̺�Ʈ ------------------------------*/
		
		update.addActionListener(this);
		delete.addActionListener(this);
		close.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		// ������ư�� Ŭ�� ���� ��
		if(e.getActionCommand().equals("����")) {
			int result = JOptionPane.showConfirmDialog(null,"���� �Ͻðڽ��ϱ�?","�޽���", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(result == JOptionPane.CANCEL_OPTION || result == JOptionPane.NO_OPTION) {}
			else if(result == JOptionPane.YES_OPTION) {
				if(new String(p_field.getPassword()).equals("")||name_field.getText().equals("") || 
				   phone_field.getText().equals("") || email_field.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "��ĭ�� �ֽ��ϴ�.");
				}else {
					// �������, �޴���, �̸����� ������ �����ϰ� ȸ��DB���� ����
					String birth = (String)year_box.getSelectedItem()+"-"+(String)month_box.getSelectedItem()+"-"+(String)day_box.getSelectedItem();
					String phone = (String)phone_box.getSelectedItem()+phone_field.getText();
					String email = email_field.getText()+"@"+(String)email_box.getSelectedItem();
							
					checking.member_update(id_field.getText(),new String(p_field.getPassword()),name_field.getText(),birth,phone,email);
					JOptionPane.showMessageDialog(null, "ȸ�������� ���� �Ǿ����ϴ�.");
					this.dispose();
				}
			}
		// Ż�� ��ư�� Ŭ�� ���� ��
		}else if(e.getActionCommand().equals("Ż��")) {
			int result = JOptionPane.showConfirmDialog(null,"���� Ż�� �Ͻðڽ��ϱ�?","�޽���", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			
			if(result == JOptionPane.CANCEL_OPTION || result == JOptionPane.NO_OPTION) {}
			else if(result == JOptionPane.YES_OPTION) {
				if(new String(p_field.getPassword()).equals("") || name_field.getText().equals("") 
				|| phone_field.getText().equals("") || email_field.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "��ĭ�� �ֽ��ϴ�.");
				}else if(!new String(p_field.getPassword()).equals(pass)) {
					JOptionPane.showMessageDialog(null, "��й�ȣ�� ���� �ʽ��ϴ�.");
				}
				else {
					checking.member_delete(id_field.getText());
					JOptionPane.showMessageDialog(null, "���� �Ǿ����ϴ�.");
					this.dispose();
				}
			}
		}
		// �ݱ� ��ư�� Ŭ�� ���� ��
		else if(e.getActionCommand().equals("�ݱ�")) {
			this.dispose();
		}
	}
}

package userView;

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

public class newMember extends JFrame implements ActionListener {
	private data_check checking = new data_check();
	private String phones[] = {"010","011","017","019"};
	private String emails[] = {"naver.com","google.com","nate.com","daum.net","hanmail.com"};
	private Vector<String> year = new Vector<String>();
	private Vector<String> month = new Vector<String>();
	private Vector<String> day = new Vector<String>();
	
	private JLabel id,password,password_check,name,birth,phone,email,email2;
	private JTextField id_field,name_field,birth_field,phone_field,email_field;
	private JPasswordField p_field,pCheck_field;
	private JButton check,join,close;
	private JComboBox year_box, month_box, day_box, phone_box, email_box;
	private Container ct;
	
	newMember(){
		ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		/*---------------------------- ������� �޺��ڽ� ���� �߰� ------------------------------*/
		
		//��,��,���� ���ͷ� �޺� �ڽ��� ����
		for(int i = 1990; i<=2010; i++) {
			year.add(Integer.toString(i));
		}
		for(int i = 1; i<=12; i++) {
			month.add(Integer.toString(i));
		}
		for(int i = 1;i <=31;i++) {
			day.add(Integer.toString(i));
		}
		
		/*---------------------------- ȸ������ �г� ------------------------------*/
		
		// ȸ�� ���� �Է� �г�
		JPanel top = new JPanel();
		top.setLayout(null);
		top.setBackground(new Color(22,28,24));
		
		// ����
		JPanel bg = new JPanel();
		bg.setLayout(null);
		bg.setBounds(17,17,450,400);
		bg.setBackground(new Color(74,73,71));
				
		// ���̵� ��
		id = new JLabel("���̵� : ");
		id.setFont(new Font("�������",Font.BOLD,19));
		id.setBounds(45,20,150,50);
		id.setForeground(Color.WHITE);
		top.add(id);
		
		// ���̵� �ʵ�
		id_field = new JTextField(20);
		id_field.setFont(new Font("�������",Font.BOLD,20));
		id_field.setBounds(130,25,200,40);
		top.add(id_field);
		
		// �ߺ�üũ ��ư
		check = new JButton("�ߺ� Ȯ��");
		check.setFont(new Font("�������",Font.BOLD,15));
		check.setBounds(350,25,100,40);
		check.setBackground(new Color(86,42,57));
		check.setFocusPainted(false);
		check.setBorder(null);
		check.setForeground(Color.WHITE);
		top.add(check);
		
		// ��й�ȣ ��
		password = new JLabel("��й�ȣ : ");
		password.setFont(new Font("�������",Font.BOLD,19));
		password.setBounds(26,75, 150, 50);
		password.setForeground(Color.white);
		top.add(password);
		
		// �н����� �ʵ�
		p_field = new JPasswordField(20);
		p_field.setFont(new Font("�������",Font.BOLD,20));
		p_field.setBounds(130,80,200,40);
		top.add(p_field);
		
		// ��й�ȣ üũ ��
		password_check = new JLabel("��� Ȯ�� : ");
		password_check.setFont(new Font("�������",Font.BOLD,19));
		password_check.setBounds(21,130, 150, 50);
		password_check.setForeground(Color.white);
		top.add(password_check);
		
		// ��й�ȣ üũ �ʵ�
		pCheck_field = new JPasswordField(20);
		pCheck_field.setFont(new Font("�������",Font.BOLD,20));
		pCheck_field.setBounds(130,135,200,40);
		top.add(pCheck_field);
		
		// �̸� ��
		name = new JLabel("�̸� : ");
		name.setFont(new Font("�������",Font.BOLD,19));
		name.setBounds(61,185,150,50);
		name.setForeground(Color.WHITE);
		top.add(name);
		
		// �̸� �ʵ�
		name_field = new JTextField(20);
		name_field.setFont(new Font("�������",Font.BOLD,20));
		name_field.setBounds(130,190,200,40);
		top.add(name_field);
		
		// ���� ��
		birth = new JLabel("������� : ");
		birth.setFont(new Font("�������",Font.BOLD,19));
		birth.setBounds(26,240,150,50);
		birth.setForeground(Color.WHITE);
		top.add(birth);
		
		// �⵵
		year_box = new JComboBox(year);
		year_box.setBounds(130,250,70,30);
		year_box.setBackground(Color.WHITE);
		year_box.setForeground(Color.BLACK);
		top.add(year_box);
		
		// ��
		month_box = new JComboBox(month);
		month_box.setBounds(215,250,70,30);
		month_box.setBackground(Color.WHITE);
		month_box.setForeground(Color.BLACK);
		top.add(month_box);
		
		// ��
		day_box = new JComboBox(day);
		day_box.setBounds(300,250,70,30);
		day_box.setBackground(Color.WHITE);
		day_box.setForeground(Color.BLACK);
		top.add(day_box);
		
		// �޴��� ��
		phone = new JLabel("�޴��� : ");
		phone.setFont(new Font("�������",Font.BOLD,19));
		phone.setBounds(45,295,150,50);
		phone.setForeground(Color.WHITE);
		top.add(phone);
		
		// �޴��� �޺� �ڽ�
		phone_box = new JComboBox(phones);
		phone_box.setBounds(130,305,70,30);
		phone_box.setBackground(Color.WHITE);
		phone_box.setForeground(Color.BLACK);
		top.add(phone_box);
		
		// �޴��� �ʵ�
		phone_field = new JTextField(20);
		phone_field.setBounds(215,305,155,30);
		phone_field.setForeground(Color.BLACK);
		phone_field.setFont(new Font("�������",Font.BOLD,16));
		top.add(phone_field);
		
		// �̸��� ��
		email = new JLabel("�̸��� : ");
		email.setFont(new Font("�������",Font.BOLD,19));
		email.setBounds(45,350,150,50);
		email.setForeground(Color.WHITE);
		top.add(email);
		
		// �̸���
		email_field = new JTextField(20);
		email_field.setBounds(130,360,100,30);
		email_field.setForeground(Color.BLACK);
		email_field.setFont(new Font("�������",Font.BOLD,16));
		top.add(email_field);
		
		// @
		email2 = new JLabel("@");
		email2.setFont(new Font("�������",Font.BOLD,15));
		email2.setBounds(235,350,150,50);
		email2.setForeground(Color.WHITE);
		top.add(email2);
		
		// �̸��� �޺� �ڽ�
		email_box = new JComboBox(emails);
		email_box.setBounds(255,360,115,30);
		email_box.setBackground(Color.WHITE);
		email_box.setForeground(Color.BLACK);
		top.add(email_box);
		
		// ���� ��ư
		join = new JButton("����");
		join.setBounds(130,435,100,40);
		join.setFont(new Font("�������",Font.BOLD,20));
		join.setBackground(new Color(212,84,169));
		join.setForeground(Color.WHITE);
		join.setBorder(null);
		join.setFocusPainted(false);
		join.setEnabled(false);
		top.add(join);
		
		// �ݱ� ��ư
		close = new JButton("�ݱ�");
		close.setBounds(240,435,100,40);
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
		
		check.addActionListener(this);
		join.addActionListener(this);
		close.addActionListener(this);
	}
	
	// �ݱ�,����, �ߺ�Ȯ�� ��ư�� Ŭ������ �� �̺�Ʈ
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("�ߺ� Ȯ��")) {
			if(checking.id_check(id_field.getText())) {
				JOptionPane.showMessageDialog(null, "��밡���� ID �Դϴ�.");
				join.setEnabled(true);
				id_field.setEditable(false);
			}else {
				JOptionPane.showMessageDialog(null, "�ߺ��� ID �Դϴ�.");
			}
		}else if(e.getActionCommand().equals("����")) {
			if(id_field.getText().equals("") || new String(p_field.getPassword()).equals("") || new String(pCheck_field.getPassword()).equals("")
			 ||name_field.getText().equals("") || phone_field.getText().equals("") || email_field.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "��ĭ�� �ֽ��ϴ�.");
			}else {
				String pass = new String(p_field.getPassword());
				String passCheck = new String(pCheck_field.getPassword());
				if(!pass.equals(passCheck)) {
					JOptionPane.showMessageDialog(null, "��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
				}else {
					// �������, �޴���, �̸����� ������ �����ϰ� ȸ��DB�� ������.
					String birth = (String)year_box.getSelectedItem()+"-"+(String)month_box.getSelectedItem()+"-"+(String)day_box.getSelectedItem();
					String phone = (String)phone_box.getSelectedItem()+phone_field.getText();
					String email = email_field.getText()+"@"+(String)email_box.getSelectedItem();
					
					checking.member_join(id_field.getText(),new String(p_field.getPassword()),name_field.getText(),birth,0,0,phone,email,0);
					JOptionPane.showMessageDialog(null, "ȸ�������� �Ǿ����ϴ�.");
					this.dispose();
				}
			}
		}else if(e.getActionCommand().equals("�ݱ�")) {
			this.dispose();
		}
	}
	
	/*---------------------------- ���� ------------------------------*/
	
	public static void main(String[] args) {
		newMember member = new newMember();
		member.setVisible(true);
		member.setTitle("ȸ������");
		member.setSize(500,550);
		member.setLocationRelativeTo(null);
		member.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

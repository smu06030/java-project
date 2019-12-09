package doyeon.charge;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import doyeon.DBConnection;
import doyeon.DBTable;
import doyeon.MessageDialog;

public class ChargeLogin extends JFrame implements ActionListener, DBTable{

	DBConnection connection;
	String id, passwd;
	JTextField idField, passwdField; 
	JButton btn;
	
	ChargeLogin(){
		
		connection = new DBConnection(TABLE_MEMBER);
		
		JPanel idPanel = new JPanel();

		idField = new JTextField(20);
		idField.addActionListener(this);
		btn = new JButton("Ȯ��");
		
		idPanel.add(new JLabel("���̵�:")); idPanel.add(idField);;
		
		idPanel.setBounds(100, 200, 250, 100);
		btn.setBounds(150, 400, 100, 50);
		btn.addActionListener(this);
		
		add(idPanel);
		add(btn);

		setTitle("���� �α���_����");
		setSize(1930, 1050);
		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
	
	public void actionPerformed(ActionEvent ae) {
		
		String id = idField.getText();

		if(connection.isSelect(ATTRIBUTE_MEMBER_ID+" = '"+id+"'")) {
			new ChargeMain(id);
			dispose();
		}else new MessageDialog(this, "���", true, "���̵�, ��й�ȣ�� Ȯ�����ּ���");
		
	}
	
	public static void main(String[] args) {
		new ChargeLogin();
	}
	
}

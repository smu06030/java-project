
package userView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import DBCheck.data_check;

// ���� �α��� ���� �� ���� ���̵� ����ڰ� ������̸� �� ���̵�� ����� �Դϴ� ��� ���� - ����
// �׸��� �������� pc�� 10�� �̻� �Ѿ�� �� ��� pc�� ����� �Դϴ� ��� ��� - 
/*---------------------------- �̹��� ��� ------------------------------*/

class ImagePanel extends JPanel{
	Image img;
	
	public ImagePanel(Image img){
		this.img = img;
		setSize(new Dimension(img.getWidth(null),img.getHeight(null)));
		setPreferredSize(new Dimension(img.getWidth(null),img.getHeight(null)));
		setLayout(null);
	}	
	public void paintComponent(Graphics g) {
		g.drawImage(img,0,0,null);
	}
}

public class login extends JFrame implements ActionListener{
	private static Connection conn;
	private static PreparedStatement pstmt;
	private data_check checking;
	private Container ct;
	private JButton login,newMember,search;
	private JTextField id;
	private JPasswordField password;
	
	login(){
		ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		/*---------------------------- �α��� ȭ�� �̹��� ------------------------------*/
		
		// �̹��� 1600x700 ������� ��ܿ� ��ġ
		ImagePanel imgPanel = new ImagePanel(new ImageIcon("./image/loginLogo.jpg").getImage());
		imgPanel.setLayout(null);
		imgPanel.setPreferredSize(new Dimension(1600,700));
		
		// �α��� �並 1600x200 ������� �ϴܿ� ��ġ
		JPanel logPanel = new JPanel();
		logPanel.setLayout(null);
		logPanel.setBackground(new Color(22,28,24));//new Color(54,53,108)
		logPanel.setPreferredSize(new Dimension(1600,200));
		
		/*---------------------------- �α��� �Է�  ------------------------------*/
		
		// �α��� �� 
		JLabel idLabel = new JLabel("���̵�");
		idLabel.setBounds(490, 60, 150, 60);
		idLabel.setForeground(Color.WHITE);
		idLabel.setFont(new Font("����������",Font.BOLD,25));
		logPanel.add(idLabel);
		
		// ���̵� �ʵ�
		id = new JTextField(20);
		id.setBounds(600,67,220,45);
		id.setBackground(null);
		id.setCaretColor(Color.white);
		id.setForeground(Color.WHITE);
		id.setFont(new Font("����������",Font.BOLD,25));
		id.setOpaque(false);
		logPanel.add(id);
		
		// ��й�ȣ ��
		JLabel pass = new JLabel("��й�ȣ");
		pass.setBounds(467,122,150,50);
		pass.setForeground(Color.WHITE);
		pass.setBackground(Color.DARK_GRAY);
		pass.setFont(new Font("����������",Font.BOLD,25));
		logPanel.add(pass);
		
		// ��й�ȣ �ʵ�
		password = new JPasswordField(20);
		password.setBounds(600,125,220,45);
		password.setBackground(null);
		password.setCaretColor(Color.WHITE);
		password.setOpaque(false);
		password.setFont(new Font("�������",Font.BOLD,20));
		password.setForeground(Color.WHITE);
		logPanel.add(password);
		
		// �α��� ��ư
		login = new JButton(new ImageIcon("image/loginButton.jpg"));
		login.setBounds(833,67,160,103);
		login.setBorder(new LineBorder(Color.WHITE,2));
		login.setFocusPainted(false);
		login.setContentAreaFilled(false);
		logPanel.add(login);

		// ȸ������ ��ư
		newMember = new JButton("ȸ������");
		newMember.setBounds(1005,67,120,46);
		newMember.setFont(new Font("�������",Font.BOLD,18));
		newMember.setForeground(Color.WHITE);
		newMember.setBackground(new Color(135,189,255));
		newMember.setBorder(new LineBorder(Color.WHITE,2));
		newMember.setFocusPainted(false);
		logPanel.add(newMember);
		
		// ID/PW ã�� ��ư
		search = new JButton("ID/PWã��");
		search.setBounds(1005,123,120,46);
		search.setFont(new Font("�������",Font.BOLD,18));
		search.setForeground(Color.WHITE);
		search.setBackground(new Color(135,189,255));
		search.setBorder(new LineBorder(Color.WHITE,2));
		search.setFocusPainted(false);
		logPanel.add(search);
		
		ct.add(imgPanel,BorderLayout.NORTH);
		ct.add(logPanel,BorderLayout.SOUTH);
		
		/*------------------------------ �̺�Ʈ --------------------------------*/
		
		// Ŭ�� �̺�Ʈ ó��
		login.addActionListener(this);
		newMember.addActionListener(this);
		search.addActionListener(this);
		password.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == login || e.getSource() == password) {
			String ids = id.getText();
			String passwd = new String(password.getPassword());
			// DBCheck ��ü ����
			checking = new data_check();
	
			try {// �Էµ� ���̵�� ��й�ȣ�� �Ű������� �Ѱ� ȸ��DB�� �ִ��� �˻��Ѵ�.
				if(checking.check(ids,passwd)){
					// ȸ���ð��� �ִ��� Ȯ���Ѵ�.
					int hour = checking.time_hour(ids);
					int minute = checking.time_minute(ids);
					int sec = checking.time_sec(ids);
					
					// ȸ���� �ð��� �ִ� ��� �α����� �Ѵ�.
					if((hour != 0 || minute != 0 || sec != 0)) { //���⿡�� ��������� �˻�.
						JOptionPane.showMessageDialog(null,"�α��� �����߽��ϴ�.");
						// �α��ο� �����ϸ� ������ �����ϱ� ���� ip�ּҿ� port��ȣ�� �������� pc��ȣ�� ������.
						String ip = "127.0.0.1";
						int port = 9999;
						int rand = (int)(Math.random()*10)+1;
					
						userMain main = new userMain(ip,port,rand,ids,hour,minute,sec);
						main.setVisible(true);
						main.setSize(400,400);
						main.setLocationRelativeTo(null);
						main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						
						this.dispose();
					}else {
						JOptionPane.showMessageDialog(null,"ȸ�� �ð��� �����ϴ�. ���� �ٶ�");
					}
				}else {
					JOptionPane.showMessageDialog(null,"���̵� ��й�ȣ�� Ʋ���ϴ�.");
				}
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			}
		}else if(e.getActionCommand().equals("ȸ������")) {
			newMember member = new newMember();
			member.setVisible(true);
			member.setTitle("ȸ������");
			member.setSize(500,550);
			member.setLocationRelativeTo(null);
			member.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
		}else if(e.getActionCommand().equals("ID/PWã��")) {
			
		}
	}
	
	/*---------------------------- ���� ------------------------------*/
	
	public static void main(String[] args) {
		login log = new login();
		
		log.setSize(1600,900);
		log.setTitle("�α���");
		log.setVisible(true);
		log.setLocationRelativeTo(null);
		log.setResizable(false);
		log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// �����ͺ��̽� ����
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("JDBC ����̹� �ε� ����");
		}
	}	
}

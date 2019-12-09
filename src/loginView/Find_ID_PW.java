package loginView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.sql.*;

//ID 찾기 탭 클래스
class ID extends JPanel implements ActionListener{
	//String url = "jdbc:mysql://172.111.117.107:3306/pcbang?serverTimezone=UTC";
	String url = "jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC";
	String user = "root";
	String pw = "dlscjf158!A";

	JLabel name_l, email_l, symbol;
	JTextField name_t, email_t;
	JButton find_id;
	JComboBox email_cb;
	JPanel name_panel, email_panel, btn_panel;
	Font font;
	String[] email = {"naver.com","nate.com","hanmail.net","gmail.com","kakao.com","google.com"};

	public ID(){
		this.setLayout(new FlowLayout(FlowLayout.CENTER));

		Color color = new Color(0xc0deaa); 
		this.setBackground(color);

		name_l = new JLabel("이름    : ");
		name_t = new JTextField(12);
		symbol = new JLabel("@");
		email_l = new JLabel("이메일  :");
		email_t = new JTextField(12);
		email_cb = new JComboBox(email);
		find_id = new JButton("아이디 찾기");
		find_id.addActionListener(this);

		name_panel = new JPanel();
		email_panel = new JPanel();
		btn_panel = new JPanel();
		name_panel.setPreferredSize(new Dimension(300,100));
		email_panel.setPreferredSize(new Dimension(400,50));


		font = new Font("",Font.BOLD,20);
		name_l.setFont(font);
		email_l.setFont(font);
		find_id.setFont(font);

		name_panel.setBackground(color);
		email_panel.setBackground(color);
		btn_panel.setBackground(color);

		name_panel.add(name_l);
		name_panel.add(name_t);
		email_panel.add(email_l);
		email_panel.add(email_t);
		email_panel.add(symbol);
		email_panel.add(email_cb);
		btn_panel.add(find_id);

		name_panel.setLayout(new FlowLayout(FlowLayout.LEFT,0,40));

		this.add(name_panel);
		this.add(email_panel);
		this.add(btn_panel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//<------------- 아이디 찾기 ------------->
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
		}catch(ClassNotFoundException e1) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection(url,user,pw);
			Statement dbSt = con.createStatement();
			String strSql;	
			String name = name_t.getText();
			int index = email_cb.getSelectedIndex();
			String full_email = email_t.getText()+"@"+email[index];

			strSql = "SELECT * FROM 회원 WHERE 이름 = '"+ name +"' and 이메일 = '"+ full_email +"';";
			ResultSet result = dbSt.executeQuery(strSql);
			if(result.next()) {
				JOptionPane.showMessageDialog(null, name+"회원님의 아이디는 '"+result.getString("아이디")+"' 입니다.");
			}
			else {
				JOptionPane.showMessageDialog(null, "회원이 아닙니다.");
			}
			dbSt.close();
			con.close();
		}catch(SQLException e1) {
			System.out.println("SQLException : "+e1.getMessage());
		}
	}
}

//Password 찾기 탭 클래스
class Password extends JPanel implements ActionListener{
	String url = "jdbc:mysql://172.111.117.107:3306/pcbang?serverTimezone=UTC";
	//String url = "jdbc:mysql://192.168.0.21:3306/pcbang?serverTimezone=UTC";
	String user = "minsuk";
	String userPassword = "1234";

	JLabel id_l, name_l, email_l, symbol;
	JTextField id_t, name_t, email_t;
	JButton find_pw;
	JComboBox email_cb;
	JPanel id_panel, name_panel, email_panel, btn_panel;
	Font font;
	String[] email = {"naver.com","nate.com","hanmail.net","gmail.com","kakao.com", "google.com"};

	public Password(){
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		Color color = new Color(0xc0deaa);
		this.setBackground(color);

		id_l = new JLabel("아이디  : ");
		id_t = new JTextField(12);
		name_l = new JLabel("이름    :");
		name_t = new JTextField(12);
		symbol = new JLabel("@");
		email_l = new JLabel("이메일  :");
		email_t = new JTextField(12);
		email_cb = new JComboBox(email);
		find_pw = new JButton("비밀번호 찾기");
		find_pw.addActionListener(this);


		id_panel = new JPanel();
		name_panel = new JPanel();
		email_panel = new JPanel();
		btn_panel = new JPanel();
		id_panel.setPreferredSize(new Dimension(320,80));
		name_panel.setPreferredSize(new Dimension(310,50));
		email_panel.setPreferredSize(new Dimension(400,50));

		font = new Font("",Font.BOLD,19);
		id_l.setFont(font);
		name_l.setFont(font);
		email_l.setFont(font);
		find_pw.setFont(font);

		id_panel.setBackground(color);
		name_panel.setBackground(color);
		email_panel.setBackground(color);
		btn_panel.setBackground(color);

		id_panel.add(id_l);
		id_panel.add(id_t);
		name_panel.add(name_l);
		name_panel.add(name_t);
		email_panel.add(email_l);
		email_panel.add(email_t);
		email_panel.add(symbol);
		email_panel.add(email_cb);
		btn_panel.add(find_pw);

		id_panel.setLayout(new FlowLayout(FlowLayout.LEFT,0,35));
		name_panel.setLayout(new FlowLayout(FlowLayout.LEFT));

		this.add(id_panel);
		this.add(name_panel);
		this.add(email_panel);
		this.add(btn_panel);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//<------------- 비밀번호 찾기 ------------->
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
		}catch(ClassNotFoundException e1) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection(url,user,userPassword);
			Statement dbSt = con.createStatement();
			String strSql;	
			String id = id_t.getText();
			String name = name_t.getText();
			int index = email_cb.getSelectedIndex();
			String full_email = email_t.getText()+"@"+email[index];

			strSql = "SELECT * FROM 회원 WHERE 아이디 = '"+ id +"' and 이름 = '"+ name +"' and 이메일 = '"+ full_email +"';";
			ResultSet result = dbSt.executeQuery(strSql);
			System.out.println(strSql);
			int flag = 0;
			while(result.next()) {
				flag = 1;
				//스레드를 이용해 비밀번호를 알려주는 창을 시간이 지나면 자동으로 꺼지게 함으로써 사용자 정보 보안에 신경썼습니다.
				new Thread() {  
					public void run() {  
						try {  
							Thread.sleep(5000);  
						}catch (InterruptedException e) {}  
						JOptionPane.getRootFrame().dispose();  
					}  
				}.start(); 
				JOptionPane.showMessageDialog(null, name+" 회원님의 비밀번호는 '"+result.getString("비밀번호")+"' 입니다."
						+ "\n(이 메세지는 5초뒤에 꺼집니다. 회원님의 정보 보안에 유의하세요!!)");
			}
			if(flag == 0){
				JOptionPane.showMessageDialog(null, "회원이 아닙니다.");
			}
			dbSt.close();
			con.close();
		}catch(SQLException e1) {
			System.out.println("SQLException : "+e1.getMessage());
		}
	}
}

public class Find_ID_PW{
	public static void main(String[] args) {
		FindPage win = new FindPage();
		win.setTitle("ID/PW 찾기");
		win.setSize(500, 500);
		win.setLocation(0,0);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.show();	
	}
}

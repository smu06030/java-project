
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
	static Connection conn;
	static PreparedStatement pstmt;
	data_check checking;
	Container ct;
	JButton login,newMember,search;
	JTextField id;
	JPasswordField password;
	
	login(){
		ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		// 이미지 1600x700 사이즈로 상단에 배치
		ImagePanel imgPanel = new ImagePanel(new ImageIcon("./image/loginLogo.jpg").getImage());
		imgPanel.setLayout(null);
		imgPanel.setPreferredSize(new Dimension(1600,700));
		
		// 로그인 뷰를 1600x200 사이즈로 하단에 배치
		JPanel logPanel = new JPanel();
		logPanel.setLayout(null);
		logPanel.setBackground(new Color(22,28,24));//new Color(54,53,108)
		logPanel.setPreferredSize(new Dimension(1600,200));
		
		// 로그인 라벨 
		JLabel idLabel = new JLabel("아이디");
		idLabel.setBounds(490, 60, 150, 60);
		idLabel.setForeground(Color.WHITE);
		idLabel.setFont(new Font("나눔스퀘어",Font.BOLD,25));
		logPanel.add(idLabel);
		
		// 아이디 필드
		id = new JTextField(20);
		id.setBounds(600,67,220,45);
		id.setBackground(null);
		id.setCaretColor(Color.white);
		id.setForeground(Color.WHITE);
		id.setFont(new Font("나눔스퀘어",Font.BOLD,25));
		id.setOpaque(false);
		logPanel.add(id);
		
		// 비밀번호 라벨
		JLabel pass = new JLabel("비밀번호");
		pass.setBounds(467,122,150,50);
		pass.setForeground(Color.WHITE);
		pass.setBackground(Color.DARK_GRAY);
		pass.setFont(new Font("나눔스퀘어",Font.BOLD,25));
		logPanel.add(pass);
		
		// 비밀번호 필드
		password = new JPasswordField(20);
		password.setBounds(600,125,220,45);
		password.setBackground(null);
		password.setCaretColor(Color.WHITE);
		password.setOpaque(false);
		password.setFont(new Font("나눔고딕",Font.BOLD,20));
		password.setForeground(Color.WHITE);
		logPanel.add(password);
		
		// 로그인 버튼
		login = new JButton("로그인");
		login.setBounds(833,67,160,103);
		login.setFont(new Font("나눔고딕",Font.BOLD,26));
		login.setForeground(Color.WHITE);
		login.setBackground(new Color(135,189,255));
		login.setBorder(new LineBorder(Color.WHITE,2));
		login.setFocusPainted(false);
		logPanel.add(login);

		// 회원가입 버튼
		newMember = new JButton("회원가입");
		newMember.setBounds(1005,67,120,46);
		newMember.setFont(new Font("나눔고딕",Font.BOLD,18));
		newMember.setForeground(Color.WHITE);
		newMember.setBackground(new Color(135,189,255));
		newMember.setBorder(new LineBorder(Color.WHITE,2));
		newMember.setFocusPainted(false);
		logPanel.add(newMember);
		
		// ID/PW 찾기 버튼
		search = new JButton("ID/PW찾기");
		search.setBounds(1005,123,120,46);
		search.setFont(new Font("나눔고딕",Font.BOLD,18));
		search.setForeground(Color.WHITE);
		search.setBackground(new Color(135,189,255));
		search.setBorder(new LineBorder(Color.WHITE,2));
		search.setFocusPainted(false);
		logPanel.add(search);
		
		ct.add(imgPanel,BorderLayout.NORTH);
		ct.add(logPanel,BorderLayout.SOUTH);
		
		// 클릭 이벤트 처리
		login.addActionListener(this);
		newMember.addActionListener(this);
		search.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("로그인")) {
			String ids = id.getText();
			String passwd = new String(password.getPassword());
			// DBCheck 객체 생성
			checking = new data_check();
	
			try {// 입력된 아이디랑 비밀번호를 매개변수로 넘겨 회원DB에 있는지 검사한다.
				if(checking.check(ids,passwd)){
					// 회원시간이 있는지 확인한다.
					int hour = checking.time_hour(ids);
					int minute = checking.time_minute(ids);
					int sec = checking.time_sec(ids);
					
					// 회원의 시간이 있는 경우 로그인을 한다.
					if((hour != 0 || minute != 0 || sec != 0)) {
						JOptionPane.showMessageDialog(null,"로그인 성공했습니다.");
						// 로그인에 성공하면 서버에 접속하기 위해 ip주소와 port번호와 랜덤으로 pc번호를 보낸다.
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
						JOptionPane.showMessageDialog(null,"회원 시간이 없습니다. 충전 바람");
					}
				}else {
					JOptionPane.showMessageDialog(null,"아이디나 비밀번호가 틀립니다.");
				}
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}else if(e.getActionCommand().equals("회원가입")) {
			
			
		}else if(e.getActionCommand().equals("ID/PW찾기")) {
			
		}
	}
	public static void main(String[] args) {
		login log = new login();
		
		log.setSize(1600,900);
		log.setTitle("로그인");
		log.setVisible(true);
		log.setLocationRelativeTo(null);
		log.setResizable(false);
		log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 데이터베이스 연결
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 로드 에러");
		}
		
	}	
}

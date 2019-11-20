package userView;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import DBCheck.data_check;

public class userMain extends JFrame implements ActionListener {
	// Client 객체 생성
	Client client = new Client();
	// DB 사용
	data_check checking;
	clock c;
	
	String ip,id = null;
	int port,rand,pcNumber,hour = 0,minute = 0,sec = 15;
	String pcNum;
	
	Container ct;
	JLabel pclabel,idLabel,idview,hourLabel,hourview;
	JButton close,food,member_info;
	
	userMain(String ip, int port,int rand, String id,int hour, int minute, int sec){
		this.ip = ip;
		this.port = port;
		this.rand = rand;
		this.id = id;
		this.hour = hour;
		this.minute = minute;
		this.sec = sec;
		
		client.startClient(ip,port,rand,id);
		// 서버에 접속하는데 시간이 조금 걸리기 때문에 스레드 sleep으로 잠시 지연 시켰다가 실행한다.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		start(hour,minute,sec);	
	}
	// 테스트용 생생자
	userMain(){
		start(hour,minute,sec);
	}

	public void start(int hour, int minute, int sec) {
		// 현재 pc 번호를 받아와서 main 화면에 출력
		pcNumber = client.pcNumber;
		pcNum = Integer.toString(pcNumber);
		
		ct = getContentPane();
		ct.setLayout(null);
		ct.setBackground(new Color(22,28,24));
		
		// pc 번호 라벨
		pclabel = new JLabel("No."+pcNum);
		pclabel.setBounds(25,30,100,30);
		pclabel.setFont(new Font("나눔고딕",Font.BOLD,30));
		pclabel.setForeground(Color.WHITE);
		ct.add(pclabel);
		
		close = new JButton("사용종료");
		close.setBounds(240,25,120,40);
		close.setFont(new Font("나눔고딕",Font.BOLD,20));
		close.setForeground(Color.WHITE);
		close.setBackground(new Color(57,58,42));
		close.setFocusPainted(false);
		close.setBorder(new LineBorder(Color.WHITE,2));
		ct.add(close);
		
		idLabel = new JLabel("아이디 : ");
		idLabel.setBounds(25,120,150,30);
		idLabel.setForeground(Color.WHITE);
		idLabel.setFont(new Font("나눔고딕",Font.BOLD,25));
		ct.add(idLabel);
		
		idview = new JLabel(""+id);
		idview.setBounds(135,120,200,30);
		idview.setForeground(new Color(182,183,177));
		idview.setFont(new Font("나눔고딕",Font.BOLD,25));
		ct.add(idview);
		
		hourLabel = new JLabel("잔여시간 : ");
		hourLabel.setBounds(25,180,350,30);
		hourLabel.setForeground(Color.WHITE);
		hourLabel.setFont(new Font("나눔고딕",Font.BOLD,25));
		ct.add(hourLabel);
		
		hourview = new JLabel("");
		hourview.setBounds(160,180,350,30);
		hourview.setForeground(new Color(182,183,177));
		hourview.setFont(new Font("나눔고딕",Font.BOLD,25));
		ct.add(hourview);
		
		food = new JButton("먹거리 주문");
		food.setBounds(30,280,150,50);
		food.setFont(new Font("나눔고딕",Font.BOLD,20));
		food.setForeground(new Color(221,236,193));
		food.setBackground(new Color(42,63,22));
		food.setFocusPainted(false);
		food.setBorder(new LineBorder(new Color(42,63,22),2));
		ct.add(food);
		
		member_info = new JButton("회원 정보");
		member_info.setBounds(200,280,150,50);
		member_info.setFont(new Font("나눔고딕",Font.BOLD,20));
		member_info.setForeground(new Color(255,232,179));
		member_info.setBackground(new Color(72,46,23));
		member_info.setFocusPainted(false);
		member_info.setBorder(new LineBorder(new Color(72,46,23),2));
		ct.add(member_info);
		
		// 받아온 시간으로 스레드를 돌린다.
		c = new clock(hourview);
		c.start();
		
		// 버튼을 눌렀을 때 이벤트
		close.addActionListener(this);
		food.addActionListener(this);
		member_info.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("사용종료")) {
			checking = new data_check();
			
			client.send("종료",pcNumber);
			
			System.out.println("남은 시간 "+hour+" 남은 분 "+minute+" 남은 초 "+sec+" id "+id);
			try {
				checking.timeInsert(hour,minute,sec,id);
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
			this.dispose();
		}else if(e.getActionCommand().equals("먹거리 주문")) {
			
		}else if(e.getActionCommand().equals("회원 정보")) {
			
		}
	}
	// 시간
	class clock extends Thread{
		JLabel cl;
		
		clock(JLabel cl){
			this.cl = cl;
		}
		public void run() {
			//Calendar time = Calendar.getInstance();
			while(true) {
				cl.setText((Integer.toString(hour))+"시간"+(Integer.toString(minute))+"분"+(Integer.toString(sec))+"초");

				// 선불 요금제 일 때
				if(hour == 0 && minute == 0 && sec == 0) {
					break;
				}
				sec--;
				
				if(hour >= 1 && minute == 0 && sec == 0) {
					minute = 60;
					hour--;	
				}
				
				if(minute >= 1 && sec == 0) {
					sec = 59;
					minute--;
				}
				
				try {
					Thread.sleep(1000);
				}catch(InterruptedException e) {
					
				}
			}
		}
	}
	
	public static void main(String[] args) {
		// 바로 실행은 테스트용
		userMain main = new userMain();
		main.setVisible(true);
		main.setSize(400,400);
		main.setLocationRelativeTo(null);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
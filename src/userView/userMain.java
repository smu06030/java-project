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
	private Client client = new Client();
	// DB 사용
	private data_check checking = new data_check();
	private clock c;
	
	private String ip,id = null;
	private int port,pcNumber,hour = 0,minute = 0,sec = 15;
	private String pcNum;
	
	private Container ct;
	private JLabel pclabel,idLabel,idview,hourLabel,hourview;
	private JButton close,food,member_info;
	
	userMain(String ip, int port, String id,int hour, int minute, int sec){
		this.ip = ip;
		this.port = port;
		this.id = id;
		this.hour = hour;
		this.minute = minute;
		this.sec = sec;
		
		/*---------------------------- 클라이언트 접속 ------------------------------*/
	
		client.startClient(ip,port,id);
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

	/*---------------------------- 사용자 화면 ------------------------------*/
	
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
		
		// 사용 종료 버튼
		close = new JButton("사용종료");
		close.setBounds(240,25,120,40);
		close.setFont(new Font("나눔고딕",Font.BOLD,20));
		close.setForeground(Color.WHITE);
		close.setBackground(new Color(57,58,42));
		close.setFocusPainted(false);
		close.setBorder(new LineBorder(Color.WHITE,2));
		ct.add(close);
		
		// 아이디 라벨
		idLabel = new JLabel("아이디 : ");
		idLabel.setBounds(25,120,150,30);
		idLabel.setForeground(Color.WHITE);
		idLabel.setFont(new Font("나눔고딕",Font.BOLD,25));
		ct.add(idLabel);
		
		// 아이디 뷰
		idview = new JLabel(""+id);
		idview.setBounds(135,120,200,30);
		idview.setForeground(new Color(182,183,177));
		idview.setFont(new Font("나눔고딕",Font.BOLD,25));
		ct.add(idview);
		
		// 잔여시간 라벨
		hourLabel = new JLabel("잔여시간 : ");
		hourLabel.setBounds(25,180,350,30);
		hourLabel.setForeground(Color.WHITE);
		hourLabel.setFont(new Font("나눔고딕",Font.BOLD,25));
		ct.add(hourLabel);
		
		// 잔여시간 뷰
		hourview = new JLabel("");
		hourview.setBounds(160,180,350,30);
		hourview.setForeground(new Color(182,183,177));
		hourview.setFont(new Font("나눔고딕",Font.BOLD,25));
		ct.add(hourview);
		
		// 먹거리 주문 버튼
		food = new JButton("먹거리 주문");
		food.setBounds(30,280,150,50);
		food.setFont(new Font("나눔고딕",Font.BOLD,20));
		food.setForeground(new Color(221,236,193));
		food.setBackground(new Color(42,63,22));
		food.setFocusPainted(false);
		food.setBorder(new LineBorder(new Color(42,63,22),2));
		ct.add(food);
		
		// 회원 정보 버튼
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
		
		/*---------------------------- 이벤트 ------------------------------*/
		
		// 버튼을 눌렀을 때 이벤트
		close.addActionListener(this);
		food.addActionListener(this);
		member_info.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("사용종료")) {
			// 시계 인터럽트
			c.interrupt();
			
			// 클라이언트에게 종료 메세지 전송
			client.send("종료",pcNumber);
			
			// 남은 시간 출력
			System.out.println("남은 시간 "+hour+" 남은 분 "+minute+" 남은 초 "+sec+" id "+id);
			
			// 남은 시간을 회원DB에 저장한다.
			checking.timeInsert(hour,minute,sec,id);
			
			// 현재 프로그램 종료
			System.exit(0);
			
		}else if(e.getActionCommand().equals("먹거리 주문")) {
			// 먹거리 주문
			// ID,pcNumber만 넘겨준다.
		}else if(e.getActionCommand().equals("회원 정보")) {
			// 아이디를 넘겨준다. 
		}
	}
	
	/*---------------------------- 시계 스레드 ------------------------------*/
	
	// 시간
	class clock extends Thread{
		JLabel cl;
		
		clock(JLabel cl){
			this.cl = cl;
		}
		public void run() {
			try {
				while(!Thread.currentThread().isInterrupted()) {
					cl.setText((Integer.toString(hour))+"시간"+(Integer.toString(minute))+"분"+(Integer.toString(sec))+"초");

					// 선불 요금제 일 때
					if(hour == 0 && minute == 0 && sec == 0) {
						// 클라이언트에게 종료 메세지 전송
						client.send("종료",pcNumber);
						
						// 남은 시간 출력
						System.out.println("남은 시간 "+hour+" 남은 분 "+minute+" 남은 초 "+sec+" id "+id);
						
						// 남은 시간을 회원DB에 저장한다.
						checking.timeInsert(hour,minute,sec,id);
						
						// 현재 프로그램 종료
						System.exit(0);
						
						break;
					}
					
					if(hour >= 1 && minute == 0 && sec == 0) {
						minute = 60;
						hour--;	
					}
					
					if(minute >= 1 && sec == 0) {
						sec = 59;
						minute--;
					}
					sec--;
					
					Thread.sleep(1000);
				}
			}catch(InterruptedException e) {
				System.out.println("시계  강제 종료");
			}
			System.out.println("시계 스레드 종료");
		}
	}
	
	/*---------------------------- 메인 ------------------------------*/
	
	public static void main(String[] args) {
		// 바로 실행은 테스트용
		userMain main = new userMain();
		main.setVisible(true);
		main.setSize(400,400);
		main.setLocationRelativeTo(null);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
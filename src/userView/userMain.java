package userView;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import DBCheck.data_check;
import doyeon.user.FoodOrder;
import doyeon.user.FoodOrder_Data;
import doyeon.user.FoodOrder_Object;
import doyeon.user.UserInfo;

public class userMain extends JFrame implements ActionListener {
	// Client ��ü ����
	private Client client = new Client();
	// DB ���
	private data_check checking = new data_check();
	private clock c;
	
	private String ip,id = null;
	private int port,pcNumber;
	private String pcNum;
	
	private Container ct;
	private JLabel pclabel,idLabel,idview,hourLabel,hourview;
	private JButton close,food,member_info;
	
	userMain(String ip, int port, String id){
		this.ip = ip;
		this.port = port;
		this.id = id;
		
		/*---------------------------- Ŭ���̾�Ʈ ���� ------------------------------*/
	
		client.startClient(ip,port,id);
		// ������ �����ϴµ� �ð��� ���� �ɸ��� ������ ������ sleep���� ��� ���� ���״ٰ� �����Ѵ�.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		start();	
	}
	
	// �׽�Ʈ�� ������
//	userMain(){
//		start(hour,minute,sec);
//	}

	/*---------------------------- ����� ȭ�� ------------------------------*/
	
	public void start() {
		// ���� pc ��ȣ�� �޾ƿͼ� main ȭ�鿡 ���
		pcNumber = client.pcNumber;
		pcNum = Integer.toString(pcNumber);
		
		ct = getContentPane();
		ct.setLayout(null);
		ct.setBackground(new Color(22,28,24));
		
		// pc ��ȣ ��
		pclabel = new JLabel("No."+pcNum);
		pclabel.setBounds(25,30,100,30);
		pclabel.setFont(new Font("�������",Font.BOLD,30));
		pclabel.setForeground(Color.WHITE);
		ct.add(pclabel);
		
		// ��� ���� ��ư
		close = new JButton("�������");
		close.setBounds(240,25,120,40);
		close.setFont(new Font("�������",Font.BOLD,20));
		close.setForeground(Color.WHITE);
		close.setBackground(new Color(57,58,42));
		close.setFocusPainted(false);
		close.setBorder(new LineBorder(Color.WHITE,2));
		ct.add(close);
		
		// ���̵� ��
		idLabel = new JLabel("���̵� : ");
		idLabel.setBounds(25,120,150,30);
		idLabel.setForeground(Color.WHITE);
		idLabel.setFont(new Font("�������",Font.BOLD,25));
		ct.add(idLabel);
		
		// ���̵� ��
		idview = new JLabel(""+id);
		idview.setBounds(135,120,200,30);
		idview.setForeground(new Color(182,183,177));
		idview.setFont(new Font("�������",Font.BOLD,25));
		ct.add(idview);
		
		// �ܿ��ð� ��
		hourLabel = new JLabel("�ܿ��ð� : ");
		hourLabel.setBounds(25,180,350,30);
		hourLabel.setForeground(Color.WHITE);
		hourLabel.setFont(new Font("�������",Font.BOLD,25));
		ct.add(hourLabel);
		
		// �ܿ��ð� ��
		hourview = new JLabel("");
		hourview.setBounds(160,180,350,30);
		hourview.setForeground(new Color(182,183,177));
		hourview.setFont(new Font("�������",Font.BOLD,25));
		ct.add(hourview);
		
		// �԰Ÿ� �ֹ� ��ư
		food = new JButton("�԰Ÿ� �ֹ�");
		food.setBounds(30,280,150,50);
		food.setFont(new Font("�������",Font.BOLD,20));
		food.setForeground(new Color(221,236,193));
		food.setBackground(new Color(42,63,22));
		food.setFocusPainted(false);
		food.setBorder(new LineBorder(new Color(42,63,22),2));
		ct.add(food);
		
		// ȸ�� ���� ��ư
		member_info = new JButton("ȸ�� ����");
		member_info.setBounds(200,280,150,50);
		member_info.setFont(new Font("�������",Font.BOLD,20));
		member_info.setForeground(new Color(255,232,179));
		member_info.setBackground(new Color(72,46,23));
		member_info.setFocusPainted(false);
		member_info.setBorder(new LineBorder(new Color(72,46,23),2));
		ct.add(member_info);
		
		// �޾ƿ� �ð����� �����带 ������.
		c = new clock(hourview,id);
		c.start();
		
		/*---------------------------- �̺�Ʈ ------------------------------*/
		
		// ��ư�� ������ �� �̺�Ʈ
		close.addActionListener(this);
		food.addActionListener(this);
		member_info.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("�������")) {
			// �ð� ���ͷ�Ʈ
			c.interrupt();
			
			// Ŭ���̾�Ʈ���� ���� �޼��� ����
			client.send("����",pcNumber);
			
			// ���� �ð� ���
			//System.out.println("���� �ð� "+hour+" ���� �� "+minute+" ���� �� "+sec+" id "+id);
			
			// ���� �ð��� ȸ��DB�� �����Ѵ�.
			//checking.timeInsert(hour,minute,sec,id);
			
			// ���� ���α׷� ����
			System.exit(0);
			
		}else if(e.getActionCommand().equals("�԰Ÿ� �ֹ�")) {
		     FoodOrder_Data myData = new FoodOrder_Data();
		     myData.setPcNumber(""+pcNumber);
		     myData.setUserID(id);
		     myData.setClient(client);
	
		     FoodOrder_Object myComponent = new FoodOrder_Object();
		     FoodOrder foodOrder = new FoodOrder(myData, myComponent);
		     myComponent.setParent(foodOrder);
		     
		}else if(e.getActionCommand().equals("ȸ�� ����")) {
			UserInfo ui = new UserInfo(id);
			ui.setVisible(true);
			ui.setLocationRelativeTo(null);
			ui.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
	}
	
	/*---------------------------- �ð� ������ ------------------------------*/
	
	// �ð�
	class clock extends Thread{
		JLabel cl;
		String id;
		int hour, minute, sec;
		clock(JLabel cl, String id){
			this.cl = cl;
			this.id = id;
		}
		public void run() {
			try {
				while(!Thread.currentThread().isInterrupted()) {
					hour = checking.time_hour(id);
					minute = checking.time_minute(id);
					sec = checking.time_sec(id);
					
					cl.setText((Integer.toString(hour))+"�ð�"+(Integer.toString(minute))+"��"+(Integer.toString(sec))+"��");
					
					// ���� ����� �� ��
					if(hour == 0 && minute == 0 && sec == 0) {
						// Ŭ���̾�Ʈ���� ���� �޼��� ����
						client.send("����",pcNumber);
						
						// ���� �ð� ���
						System.out.println("���� �ð� "+hour+" ���� �� "+minute+" ���� �� "+sec+" id "+id);
						
						// ���� �ð��� ȸ��DB�� �����Ѵ�.
						checking.timeInsert(hour,minute,sec,id);
						
						// ���� ���α׷� ����
						System.exit(0);
						
						break;
					}
					
					if(hour >= 1 && minute == 0 && sec == 0) {
						minute = 60;
						hour--;	
					}
					
					if(minute >= 1 && sec == 0) {
						sec = 60;
						minute--;
						
					}
					sec--;
					
					// �ð� ����
					checking.timeInsert(hour,minute,sec,id);
					
					Thread.sleep(1000);
				}
			}catch(InterruptedException e) {
				System.out.println("�ð�  ���� ����");
			}
			System.out.println("�ð� ������ ����");
		}
	}
	
	/*---------------------------- ���� ------------------------------*/
	
	public static void main(String[] args) {
		// �ٷ� ������ �׽�Ʈ��
//		userMain main = new userMain();
//		main.setVisible(true);
//		main.setSize(400,400);
//		main.setLocationRelativeTo(null);
//		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
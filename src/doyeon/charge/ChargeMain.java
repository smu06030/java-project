package doyeon.charge;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import doyeon.DBConnection;
import doyeon.DBTable;
import doyeon.MessageDialog;

public class ChargeMain extends JFrame implements DBTable, ActionListener{

	private Vector<String> attribute;
	private Vector<Vector<Object>> data;
	private JButton backBtn, cardBtn, cashBtn;
	private JLabel notice; //사용자 정보 출력
	private JLabel notice_time;
	private ProductBtn selectedBtn;
	private TimeChecker tc; //30초 동안 동작이 없을 경우 로그인 화면으로 돌아감
	private String id;
	private String name;
	
	ChargeMain(String id){
		
		setLayout(new BorderLayout());
		
		this.id = id;
		
		JPanel topJP = new JPanel();
		JPanel leftJP = new JPanel();
		JPanel rightJP = new JPanel();
		notice = new JLabel();	
		notice_time = new JLabel();
		notice_time.setForeground(Color.RED);
		
		cashBtn = new JButton("현금");
		cardBtn = new JButton("카드");
		
		leftJP.setLayout(new GridLayout(0,2, 100, 100));
		leftJP.setBorder(BorderFactory.createEmptyBorder(100, 20, 100, 20));
		leftJP.setSize(new Dimension(500, 900));
		
		rightJP.setLayout(null);
		cashBtn.setBounds(500, 250, 200, 100);
		cashBtn.addActionListener(this);
		cardBtn.setBounds(500, 450, 200, 100);
		cardBtn.addActionListener(this);
		rightJP.add(cashBtn);
		rightJP.add(cardBtn);
		
		tc = new TimeChecker(this, notice_time);
		tc.start();
		
		DBConnection connection = new DBConnection(TABLE);
		DBConnection memberConn = new DBConnection(TABLE_MEMBER);
		attribute = new Vector<String>();
		data = new Vector<Vector<Object>>();
		backBtn = new JButton("아이디/비밀번호 입력");
		backBtn.addActionListener(this);
		name = memberConn.getSelectString(ATTRIBUTE_MEMBER_NAME, ATTRIBUTE_MEMBER_ID+" = '"+id+"'");
		
		topJP.add(backBtn);
		topJP.add(notice);
		topJP.add(notice_time);
		
		for(int i = 0; i< ATTRIBUTE.length;i++) {
			attribute.add(ATTRIBUTE[i]);
		}
			
		connection.importRow(attribute, data);

		for(int i = 0;i<data.size();i++) {
			int time = Integer.parseInt((String) data.get(i).get(1));
			int price = Integer.parseInt((String) data.get(i).get(2));
			ProductBtn btn = new ProductBtn((String)data.get(i).get(0),time, price);
			btn.addActionListener(this);
			leftJP.add(btn);
			
		}
		add(topJP, BorderLayout.NORTH);
		add(new JScrollPane(leftJP), BorderLayout.WEST);
		add(rightJP, BorderLayout.CENTER);
		setTitle("충전 메인_도연");
		setSize(1930, 1050);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void back() {
		new ChargeLogin();
		dispose();
	}
	
	public void actionPerformed(ActionEvent ae) {
		String btnText = ae.getActionCommand();
		tc.resetTime();
		if(btnText.equals("아이디/비밀번호 입력")) {
			tc.setEndTime();
		}else if(btnText.equals("카드")||btnText.equals("현금")){
			
			try {
				//로그 저장, 충전 시간 추가
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar cal = Calendar.getInstance();
				String today = sdf.format(cal.getTime());
				Vector<String> saveData = new Vector<String>();
				Vector<String> attribute = new Vector<String>();
				attribute.add(ATTRIBUTE_LOG_DATE);
				attribute.add(ATTRIBUTE_LOG_USER);
				attribute.add(ATTRIBUTE_LOG_PCNUM);
				attribute.add(ATTRIBUTE_LOG_PRODUCT);
				attribute.add(ATTRIBUTE_LOG_COUNT);
				attribute.add(ATTRIBUTE_LOG_COST);
				attribute.add(ATTRIBUTE_LOG_METHOD);
				saveData.add(today);
				saveData.add(id);
				saveData.add(""+0); //pc번호
				saveData.add(selectedBtn.getName());//상품명
				saveData.add(""+1);//수량
				saveData.add(selectedBtn.getPrice());//금액
				saveData.add(btnText); //결제수단
				DBConnection conn = new DBConnection(TABLE_LOG);
				conn.insertStringData(saveData, attribute);
				new MessageDialog(this, "알림", true, "충전이 완료되었습니다.");
				DBConnection connM = new DBConnection(TABLE_MEMBER);
				connM.updatePlusInt(ATTRIBUTE_MEMBER_HOUR, selectedBtn.getTimeInt(), ATTRIBUTE_MEMBER_ID+" = '"+id+"'");
				
			}catch(Exception e) {new MessageDialog(this, "알림", true, "상품을 선택해주세요.");}
			
		}else {
			selectedBtn = (ProductBtn)ae.getSource();
			notice.setText(name+"님이 "+selectedBtn.getName()+"을 선택하셨습니다.");
		}
	}
	
	public static void main(String[] args) {
		new ChargeMain("123");
	}
	
}

class ProductBtn extends JButton{	
	//Vector<Vector<Object>> data; 
	private String name;
	private Integer time;
	private Integer price;
	
	ProductBtn(String name, Integer time, Integer price){
		this.name = name;
		this.time = time;
		this.price = price;

		add(new JLabel(name));
		add(new JLabel(price+"원"));
		
		setLayout(new GridLayout(0, 1));
		setPreferredSize(new Dimension(150, 100));

	}
	public String getName() {
		return name;
	}
	public String getTime() {
		return time.toString();
	}
	public String getPrice() {
		return price.toString();
	}
	public Integer getTimeInt() {
		return time;
	}
}

//30초 동안 동작이 없으면 로그인 창으로 돌아감
class TimeChecker extends Thread{
	private static int time=0; 
	ChargeMain cm;
	JLabel printLabel;
	
	TimeChecker(ChargeMain cm, JLabel printLabel){
		this.cm = cm;
		this.printLabel = printLabel;
	}
	
	public void run(){
		try {
			for(time=0;time<30;time++) {
				sleep(1000);
				printLabel.setText((30-time)+"초 후에 로그인 창으로 돌아갑니다.");
			}
			cm.back();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void resetTime() {
		time = 0;
	}
	public void setEndTime() {
		time = 30;
	}
	
}
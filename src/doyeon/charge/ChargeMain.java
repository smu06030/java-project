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
	private JLabel notice; //����� ���� ���
	private JLabel notice_time;
	private ProductBtn selectedBtn;
	private TimeChecker tc; //30�� ���� ������ ���� ��� �α��� ȭ������ ���ư�
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
		
		cashBtn = new JButton("����");
		cardBtn = new JButton("ī��");
		
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
		backBtn = new JButton("���̵�/��й�ȣ �Է�");
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
		setTitle("���� ����_����");
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
		if(btnText.equals("���̵�/��й�ȣ �Է�")) {
			tc.setEndTime();
		}else if(btnText.equals("ī��")||btnText.equals("����")){
			
			try {
				//�α� ����, ���� �ð� �߰�
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
				saveData.add(""+0); //pc��ȣ
				saveData.add(selectedBtn.getName());//��ǰ��
				saveData.add(""+1);//����
				saveData.add(selectedBtn.getPrice());//�ݾ�
				saveData.add(btnText); //��������
				DBConnection conn = new DBConnection(TABLE_LOG);
				conn.insertStringData(saveData, attribute);
				new MessageDialog(this, "�˸�", true, "������ �Ϸ�Ǿ����ϴ�.");
				DBConnection connM = new DBConnection(TABLE_MEMBER);
				connM.updatePlusInt(ATTRIBUTE_MEMBER_HOUR, selectedBtn.getTimeInt(), ATTRIBUTE_MEMBER_ID+" = '"+id+"'");
				
			}catch(Exception e) {new MessageDialog(this, "�˸�", true, "��ǰ�� �������ּ���.");}
			
		}else {
			selectedBtn = (ProductBtn)ae.getSource();
			notice.setText(name+"���� "+selectedBtn.getName()+"�� �����ϼ̽��ϴ�.");
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
		add(new JLabel(price+"��"));
		
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

//30�� ���� ������ ������ �α��� â���� ���ư�
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
				printLabel.setText((30-time)+"�� �Ŀ� �α��� â���� ���ư��ϴ�.");
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
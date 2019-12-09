package doyeon.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import doyeon.DBConnection;
import doyeon.DBTable;
import doyeon.MessageDialog;

class ProductSearchPanel extends JPanel implements ActionListener, OrderComponent {
	
	private FoodOrder_Data data;
	private FoodOrder_Object com;
	private JTextField text;
	private JPanel top;
	private ProductMainPanel center;
	
	ProductSearchPanel(FoodOrder_Data data, FoodOrder_Object com){
		
		this.data = data;
		this.com = com;

		JLabel warning = new JLabel("	※실제 상품은 상품이미지와 다소 상이할 수 있습니다.");
		JLabel searchText = new JLabel("검색: ");
		searchText.setFont(new Font("돋움",Font.BOLD,20));
		setLayout(new BorderLayout());
		
		top = new JPanel();
		top.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		center = new ProductMainPanel();
		
		text = new JTextField(15);
		text.setFont(new Font("고딕",Font.BOLD,20));
		text.addActionListener(this);

		top.add(searchText);
		top.add(text);
		top.add(warning);

		add(top, BorderLayout.NORTH);
		add(new JScrollPane(center), BorderLayout.CENTER);
		//최초 1번 이벤트 발생(전체검색)

		ProductObj[][] po = new ProductObj[data.getTabCount()][];
		for(int i = 0; i<po.length;i++) {
			po[i] = new ProductObj[data.getProductCount()[i]];
		}
		
		for(int i=0;i<data.getTabCount();i++) {
			for(int j=0;j<data.getProductCount()[i];j++) {
				po[i][j] = new ProductObj(data, com, i, j);
				center.add(po[i][j]);
				
			}
		}
		com.setProductObj(po);
		revalidate();
		repaint();
	}
	
	public void actionPerformed(ActionEvent ae) {
		//상품 화면 초기화
		center.removeAll();

		String input = ae.getActionCommand();
		for(int i=0;i<data.getTabCount();i++) {
			for(int j=0;j<data.getProductCount()[i];j++) {
				if(data.getProductName()[i][j].contains(input)) {
					center.add(com.getProductObj()[i][j]);
				}
			}
			revalidate();
			repaint();
			
		}
	}
	
	public void event() {
		text.postActionEvent();
	}
	
}

//상품 객체 패널
@SuppressWarnings("serial")
class ProductMainPanel extends JPanel implements OrderComponent, DBTable {
	
	FoodOrder_Object com;
	FoodOrder_Data data;
	int tabNum;
	
	ProductMainPanel(){ setting(); }
	
	ProductMainPanel(FoodOrder_Object com, FoodOrder_Data data, int tabNum){

		this.com = com;
		this.data = data;
		this.tabNum = tabNum;
		
		setting();

	}
	
	private void setting() {
		
		setBackground(Color.DARK_GRAY);
		setLayout(new GridLayout(0,MAIN_GRID_COLUMN_SIZE, 70, 50));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	}
	
	public void setComponent() {
		for(int i = 0; i < data.getProductName()[tabNum].length;i++) 
			add(com.getProductObj()[tabNum][i]);
		revalidate();
		repaint();
	}
	
}

//상품 객체
class ProductObj extends JPanel implements ChangeListener, OrderComponent, DBTable{ //상품객체
	
	private int tabNum, objNum; //상품 인덱스
	private FoodOrder_Data data;
	private FoodOrder_Object com;
	private JSpinner counter; //갯수 스피너

	public ProductObj(FoodOrder_Data data, FoodOrder_Object components, int index1, int index2) {

		this.data = data;
		this.com = components;
		this.tabNum = index1;
		this.objNum = index2;
		
		JLabel nameL, foodImgL, priceL;
		JPanel bottom;
		Font font = new Font("Times",Font.BOLD,30);

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(300, 300));
		
		//상품 이미지 설정
		foodImgL = new JLabel(makeImageIcon(getImgSrc()));
		foodImgL.setOpaque(true);
		foodImgL.setBackground(Color.WHITE);
		
		//이름 레이블 설정
		nameL = new JLabel(getName());
		nameL.setFont(font);
		nameL.setForeground(Color.WHITE);
		nameL.setHorizontalAlignment(JLabel.CENTER);
		nameL.setOpaque(true);
		nameL.setBackground(Color.GRAY);
		
		//가격 레이블 설정
		priceL = new JLabel(getStringPrice()+"원");
		priceL.setFont(font);
		priceL.setForeground(Color.WHITE);
		
		//카운터 설정
		counter = new JSpinner(); 
		counter.setFont(font);
		counter.setPreferredSize(new Dimension(120, 50));
		counter.addChangeListener(this);
		
		//하단(카운터, 가격)
		bottom = new JPanel();
		bottom.setBackground(Color.gray);
		bottom.setLayout(new BorderLayout());
		bottom.add(counter, BorderLayout.WEST);
		bottom.add(priceL, BorderLayout.EAST);
		
		add(nameL, BorderLayout.NORTH);
		add(foodImgL, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);
		
	}
	
	public void resetCounter() {
		counter.setValue(0);
	}
	
	public Integer getCount() { 
		return (Integer)counter.getValue(); 
	}
	public String getName() {
		return data.getProductName()[tabNum][objNum];
	}
	public String getImgSrc() { 
		return data.getProductImgSrc()[tabNum][objNum];
	}
	public int getPrice() {
		return Integer.parseInt(data.getProductPrice()[tabNum][objNum]); 
	}
	public String getStringPrice() {
		return data.getProductPrice()[tabNum][objNum];
	}

	public int getObjNum() {
		return objNum;
	}
	public int getTabNum() {
		return tabNum;
	}	
	
	//이미지 생성
	public ImageIcon makeImageIcon(String src) {
		
		ImageIcon icon = new ImageIcon(data.getIMAGEFOLDER()+"/"+src);
		Image size = icon.getImage();
		Image img = size.getScaledInstance(PRODUCT_WHITH, PRODUCT_HEIGHT, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
		
	}
	
	//상품 갯수를 증가시키거나 감소시켰을 때 / 예외처리: 스피너 값이 재고 이하일 때, 0 이하일 때 경고메시지
	public void stateChanged(ChangeEvent e) {
		DBConnection connection = new DBConnection(TABLE_PRODUCT);
		int max = connection.getSelectInt(ATTRIBUTE_STOCK, ATTRIBUTE_NAME+" = '"+getName()+"'");
		int total = 0, count;
		JSpinner spinner = (JSpinner) e.getSource();
		count = Integer.parseInt(spinner.getValue().toString());

		if(count < 0) { spinner.setValue(0); count = 0;}
		else if(count > max) {
			new MessageDialog(com.getParent(), "경고", true, max+"개 이상 주문하실 수 없습니다.(재고부족)");
			spinner.setValue(max);
			count = max;
		}
		total = getPrice() * count;
		
		com.getMakeTable().setProductRow(tabNum, objNum, count, total);

	}

}

//결과 패널(하단 패널)
class ResultsPanel extends JPanel implements OrderComponent {

	public ResultsPanel(FoodOrder_Data data, FoodOrder_Object compo){
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(B_PALEL_WHITH, B_PALEL_HEIGHT));

		ResultsPanelRight rpr = new ResultsPanelRight(data, compo);
		compo.setMakeTable(new MakeTable(data, compo));

		add(new JScrollPane(compo.getResultsTable()), BorderLayout.WEST);
		add(new ResultsPanelCenter(data, compo), BorderLayout.CENTER);
		add(rpr, BorderLayout.EAST);
		
	}
	
}

//하단 패널 가운데
class ResultsPanelCenter extends JPanel implements ActionListener{
	
	private final String COST[] = {"금액에 맞게", "카드", "1,000원 권", "5,000원 권", "10,000원 권", "50,000원  권"} ;
	//private JLabel selectText;
	private JLabel text;
	private JComboBox<String> selectCost;
	private JButton delete;
	private FoodOrder_Object com;
	private FoodOrder_Data data;
	
	ResultsPanelCenter(FoodOrder_Data data, FoodOrder_Object com){
		
		this.com = com;
		this.data = data;
		setLayout(new BorderLayout());
		JPanel center = new JPanel();
		JPanel left = new JPanel();
		delete = new JButton("선택 항목 제거");
		delete.addActionListener(this);
		text = new JLabel("결제수단");
		selectCost = new JComboBox<String>(COST);
		
		com.setSelectCost(selectCost);
		System.out.println((String)selectCost.getSelectedItem());
		left.add(delete);
		center.add(text);
		center.add(selectCost);
		add(left, BorderLayout.WEST);
		add(center, BorderLayout.CENTER);
		
	}
	
	//선택항목 제거
	public void actionPerformed(ActionEvent ae) {
		
		int selected[] = com.getResultsTable().getSelectedRows();
		ProductObj[] po = new ProductObj[selected.length];
		for(int i =0; i<selected.length;i++) {
			System.out.println(selected[i]);
			System.out.println(data.getRow().size());
			for(int k = 0;k<data.getTabCount();k++) {
				for(int j = 0;j<data.getProductCount()[k];j++) {
					if(data.getProductName()[k][j].equals(data.getRow().get(selected[i]).get(0)))
						po[i] = com.getProductObj()[k][j];
				}
			}
		}

		for(int i=0;i<po.length;i++) {
			po[i].resetCounter();
		}
		
		com.getResultsTable().updateUI();

	}
	
}

//하단 패널 오른쪽
class ResultsPanelRight extends JPanel implements ActionListener, DBTable{

	private JButton orderBtn, orderListBtn; //주문, 주문내역 버튼
	private FoodOrder_Object com;
	private FoodOrder_Data data;
	private Vector<Vector<String>> olrow; //주문내역 행

	ResultsPanelRight(FoodOrder_Data data, FoodOrder_Object com){

		this.data = data;
		this.com = com;
		olrow = new Vector<Vector<String>>();

		com.setPrintResult(new JLabel("0 원")); 
		
		orderBtn = new JButton("주문");
		orderBtn.addActionListener(this);
		orderListBtn = new JButton("주문내역");
		orderListBtn.addActionListener(this);
		
		add(com.getPrintResult());
		add(orderBtn);
		add(orderListBtn);
	}
	
	public void actionPerformed(ActionEvent ae) {
		
		//주문
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String today = sdf.format(cal.getTime());
		String getSelectCost = com.getSelectCost().getSelectedItem().toString();
		Vector<Vector<String>> saveDatas = new Vector<Vector<String>>();
		Vector<String> attribute = new Vector<String>();
		Vector<String> shortage = new Vector<String>();
		int stocks[] = new int[data.getRow().size()];
		
		attribute.add(ATTRIBUTE_LOG_DATE);
		attribute.add(ATTRIBUTE_LOG_USER);
		attribute.add(ATTRIBUTE_LOG_PCNUM);
		attribute.add(ATTRIBUTE_LOG_PRODUCT);
		attribute.add(ATTRIBUTE_LOG_COUNT);
		attribute.add(ATTRIBUTE_LOG_COST);
		attribute.add(ATTRIBUTE_LOG_METHOD);

		if(!getSelectCost.equals("카드"))
			getSelectCost = "현금";
		
		if(ae.getActionCommand().equals("주문내역")) {
			
			ProductOrderList orderList = new ProductOrderList(data.getColumnName(), olrow);
			orderList.setVisible(true);
		}
		//데이터베이스에 주문내역 저장
		else if(data.getRow().size()>0) {
			boolean check = true;
			for(int i = 0; i < data.getRow().size();i++) {
				Vector<String> saveData = new Vector<String>();

				saveData.add(today);
				saveData.add(data.getUserID());
				saveData.add(data.getPcNumber());
				saveData.add(data.getRow().get(i).get(0));//상품명
				saveData.add(data.getRow().get(i).get(2));//수량
				saveData.add(data.getRow().get(i).get(3));//금액
				saveData.add(getSelectCost);
				saveDatas.add(saveData);

				DBConnection connP = new DBConnection(TABLE_PRODUCT);
				int count = Integer.parseInt(data.getRow().get(i).get(2));
				int stock = connP.getSelectInt(ATTRIBUTE_STOCK, ATTRIBUTE_NAME+" = '"+data.getRow().get(i).get(0)+"'");
				stocks[i] = stock - count;
				if(stocks[i] < 0) {
					check = false;
					shortage.add(data.getRow().get(i).get(0));
				}

			}
			
			if(check) {
				DBConnection conn = new DBConnection(TABLE_LOG);
				DBConnection connP = new DBConnection(TABLE_PRODUCT);
				for(int i = 0; i<data.getRow().size();i++) {
					conn.insertStringData(saveDatas.get(i), attribute);
					connP.updateInt(ATTRIBUTE_STOCK, stocks[i], ATTRIBUTE_NAME +" = '"+data.getRow().get(i).get(0)+"'");
					olrow.add(data.getRow().get(i));
				}
				
				for(int i = 0;i<data.getTabCount();i++) {
					for(int j=0;j<data.getProductCount()[i];j++) {
						com.getProductObj()[i][j].resetCounter();
					}
				}
				data.getRow().clear(); //테이블 초기화
				com.getResultsTable().updateUI();
				
				//로그 메세지 보내기
				data.getClient().send("주문", Integer.parseInt(data.getPcNumber()));
	
				new MessageDialog(com.getParent(), "주문완료", true, "주문이 완료되었습니다.");

				
			}else {
				String message="";
				for(int i =0; i<shortage.size();i++) {
					if(i==shortage.size()-1) {
						message+=shortage.get(i);
					}else message+=shortage.get(i)+", ";
				}
				new MessageDialog(com.getParent(), "주문실패", true, message +"상품의 재고가 부족합니다.");
			}
		}else new MessageDialog(com.getParent(), "주문실패", true, "상품을 선택해주세요.");
	}
	
	
}

class MakeTable extends DefaultTableModel {
	
	private FoodOrder_Data data;
	private FoodOrder_Object com;
	
	public MakeTable(FoodOrder_Data data, FoodOrder_Object com){
		
		this.data = data;
		this.com = com;

		com.setResultsTable(new JTable(this));
		data.setRow(new Vector<Vector<String>>());
		data.setColumnName(new Vector<String>());

		//속성 추가
		data.getColumnName().add(data.getRTABLE_NAME());
		data.getColumnName().add(data.getRTABLE_PRICE());
		data.getColumnName().add(data.getRTABLE_COUNT());
		data.getColumnName().add(data.getRTABLE_TOT());
		
		setDataVector(data.getRow(), data.getColumnName());
		
	}
	
	public boolean isCellEditable(int i, int r) {//셀 수정 불가
		return false;
	}

	public void setProductRow(int tabNum, int objNum, int count, int total) {
		
		Integer row;
		row = rowOverlap(tabNum, objNum);
		
		Vector<String> add = new Vector<String>();
		add.add(data.getProductName()[tabNum][objNum]);
		add.add(data.getProductPrice()[tabNum][objNum]);
		add.add(""+count);
		add.add(""+total);

		if(row == null) 
			data.getRow().add(add);

		else if (count==0) data.getRow().remove((int)row);
			
		else {		
			data.getRow().remove((int)row);
			data.getRow().add((int)row, add);
		}

		com.getResultsTable().updateUI();
		com.getMakeTable().setResult();
	}
	
	public void setResult() {

		int index = data.getColumnName().indexOf(data.getRTABLE_TOT());
		int printTot = 0;
		for(int i = 0; i < data.getRow().size(); i++)
			printTot += Integer.parseInt(data.getRow().get(i).get(index));
		
		com.getPrintResult().setText(printTot+" 원");
	}
	
	public Integer rowOverlap(int tabNum, int objNum) {
		for(int i=0; i<data.getRow().size();i++) {
			if(data.getRow().get(i).indexOf(data.getProductName()[tabNum][objNum])!=-1)
				return i;
		}
		return null;
	}
	
}

class ProductOrderList extends JFrame {
	
	ProductOrderList(Vector<String>attribute, Vector<Vector<String>>data){

		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		JTable table = new JTable(data, attribute);
		
		ct.add(new JScrollPane(table), BorderLayout.CENTER);
		
		setTitle("주문내역");
		setSize(400, 500);
		setVisible(true);
		setLocationRelativeTo(null);

	}
	
}


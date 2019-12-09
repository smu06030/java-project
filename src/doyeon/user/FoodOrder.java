package doyeon.user;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import doyeon.DBConnection;
import doyeon.DBTable;


public class FoodOrder extends JFrame implements DBTable, OrderComponent, ChangeListener {
	
	ProductMainPanel[] topPanel; //Ÿ�� �� �г�
	ProductSearchPanel allPanel; //�˻� �г�
	
	public FoodOrder(FoodOrder_Data data, FoodOrder_Object compo) { // �԰Ÿ� �ֹ����� ����� �����Ϳ� ������Ʈ ����
		
		setData(data);

		String typeName[] = data.getTypeName();

		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		//�ϴ� �г� ����;
		ResultsPanel resultsPanel = new ResultsPanel(data, compo);

		allPanel = new ProductSearchPanel(data, compo);
		
		JTabbedPane pane = new JTabbedPane();
		pane.addTab("��ü", allPanel);
		pane.addChangeListener(this);
		
		topPanel = new ProductMainPanel[typeName.length];
		for(int i = 0; i < topPanel.length; i++) {

			topPanel[i] = new ProductMainPanel(compo, data, i);
			pane.addTab(typeName[i], new JScrollPane(topPanel[i]));
			
		}
		
		ct.add(pane, BorderLayout.CENTER);
		ct.add(resultsPanel, BorderLayout.SOUTH);
		
		//����
		compo.setResultsPanel(resultsPanel);
		compo.setMakeTable(compo.getMakeTable());
		setTitle(TITLE);
		setSize(WINDOW_WHITH, WINDOW_HEIGHT);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	
	public void setData(FoodOrder_Data data) { //�ʼ� ���� (��ǰ �̹���, �̸�)

		// code�� ���� �̸�, ����, �̹����� 2���� �迭�� ������ ��
		DBConnection connection_prod = new DBConnection(TABLE_PRODUCT);
		DBConnection connection_type = new DBConnection(TABLE_PRODUCT_TYPE);
		int productTypeNum = connection_type.getCardinality();
		int objNum[] = new int[productTypeNum];
		
		// ���� ���� ������ �迭
		String typeCode[] = new String[productTypeNum];
		String typeName[] = new String[productTypeNum];
		
		//Ÿ�� ���̺��� �ʵ带 �迭�� ������
		connection_type.fieldDataStringAry(ATTRIBUTE_TYPE_NUM, typeCode);
		connection_type.fieldDataStringAry(ATTRIBUTE_TYPE_NAME, typeName);
		
		String productName[][] = new String[productTypeNum][];
		String productPrice[][] = new String[productTypeNum][];
		String productImgSrc[][] = new String[productTypeNum][];
		
		for(int i = 0 ; i < productTypeNum;i++) {
			//��ǰ���̺��� ����(�̸�, ����, �̹���)�� �迭�� ������
			objNum[i] = connection_prod.whereQueryCnt(ATTRIBUTE_TYPE + "='" + typeCode[i]+"'");
			
			productName[i] = new String[objNum[i]];
			productPrice[i] = new String[objNum[i]];
			productImgSrc[i] = new String[objNum[i]];
			
			connection_prod.fieldDataStringAry(ATTRIBUTE_NAME, productName[i], (ATTRIBUTE_TYPE + "='" + typeCode[i]+"'"));
			connection_prod.fieldDataStringAry(ATTRIBUTE_PRICE, productPrice[i], (ATTRIBUTE_TYPE + "='" + typeCode[i]+"'"));
			connection_prod.fieldDataStringAry(ATTRIBUTE_IMGSRC, productImgSrc[i], (ATTRIBUTE_TYPE + "='" + typeCode[i]+"'"));
			
		}
	
		//��ǰ ����, ���� ����, ��ǰ�̹���, ��ǰ�̸�, ��ǰ����, ���� �ڵ�, �����̸� ����
		data.setProductCount(objNum);
		data.setTabCount(productTypeNum);
		data.setProductImgSrc(productImgSrc);
		data.setProductName(productName);
		data.setProductPrice(productPrice);
		data.setTypeCode(typeCode);
		data.setTypeName(typeName);
	}

	public static void main(String[] args) {
		FoodOrder_Data myData = new FoodOrder_Data();
		myData.setPcNumber("1");
		myData.setUserID("smu06030");
		FoodOrder_Object myComponent = new FoodOrder_Object();
		FoodOrder foodOrder = new FoodOrder(myData, myComponent);
		myComponent.setParent(foodOrder);
		
	}

	@Override
	public void stateChanged(ChangeEvent ce) { // ���� �������� �� ��ǰ ��ü�� �̵�
		// TODO Auto-generated method stub
		int tab = ((JTabbedPane)ce.getSource()).getSelectedIndex();

		if(tab==0) {
			allPanel.event();
		}
		else {
			topPanel[((JTabbedPane)ce.getSource()).getSelectedIndex()-1].setComponent();
		}
		
	}

}

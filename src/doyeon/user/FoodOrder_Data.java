package doyeon.user;

import java.awt.Font;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;

import userView.Client;

//interface 

public class FoodOrder_Data {

	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	private final String IMAGEFOLDER = "images";
	
	//���̺� �Ӽ���
	private final String RTABLE_NAME = "��ǰ��";
	private final String RTABLE_PRICE = "�ݾ�";
	private final String RTABLE_COUNT = "����";
	private final String RTABLE_TOT = "�Ǹűݾ�";
	
	//������ 
	private String userID;
	private String pcNumber;
	//private String payMethod;
	
	private Font productFont;
	
	private String typeName[]; //��ǰ Ÿ�� �̸�
	private String typeCode[]; //��ǰ Ÿ�� �ڵ�
	private String productName[][]; //��ǰ �̸� [tab][obj]
	private String productPrice[][]; //��ǰ ����
	private String productImgSrc[][]; //��ǰ �̹��� ���
	private int tabCount; //�� ��
	private int productCount[]; //�Ǻ� ��ǰ ��
	private int payment; //�����Ѿ�
	private Vector<String> columnName; //��� ǥ �÷�
	private Vector<Vector<String>> row; //��� ��
	private Vector<ProductObj> productRowV; //��ǰ ��ġ
	private Client client;

	//���ͼ���------------------------------------
	public Vector<String> getColumnName() {
		return columnName;
	}
	public void setColumnName(Vector<String> columnName) {
		this.columnName = columnName;
	}
	public Vector<Vector<String>> getRow() {
		return row;
	}
	public void setRow(Vector<Vector<String>> row) {
		this.row = row;
	}
	public Vector<ProductObj> getProductRowV() {
		return productRowV;
	}
	public void setProductRowV(Vector<ProductObj> productRowV) {
		this.productRowV = productRowV;
	}
	// �ʿ� ���� ���� ���ʹ� ����
	public int getPayment() {
		return payment;
	}
	public void setPayment(int payment) {
		this.payment = payment;
	}
	public int getTabCount() {
		return tabCount;
	}
	public void setTabCount(int tabCount) {
		this.tabCount = tabCount;
	}
	public int[] getProductCount() {
		return productCount;
	}
	public void setProductCount(int productCount[]) {
		this.productCount = productCount;
	}
	public String[] getTypeName() {
		return typeName;
	}
	public void setTypeName(String[] typeName) {
		this.typeName = typeName;
	}
	public String[] getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String[] typeCode) {
		this.typeCode = typeCode;
	}
	public String[][] getProductName() {
		return productName;
	}
	public void setProductName(String[][] productName) {
		this.productName = productName;
	}
	public String[][] getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String[][] productPrice) {
		this.productPrice = productPrice;
	}
	public String[][] getProductImgSrc() {
		return productImgSrc;
	}
	public void setProductImgSrc(String[][] productImgSrc) {
		this.productImgSrc = productImgSrc;
	}
	public void sumPayment(int num) {
		this.payment += num;
	}
	public String getRTABLE_TOT() {
		return RTABLE_TOT;
	}
	public String getRTABLE_NAME() {
		return RTABLE_NAME;
	}
	public String getRTABLE_PRICE() {
		return RTABLE_PRICE;
	}
	public String getRTABLE_COUNT() {
		return RTABLE_COUNT;
	}
	
	public String getIMAGEFOLDER() {
		return IMAGEFOLDER;
	}
	public String getPcNumber() {
		return pcNumber;
	}
	public void setPcNumber(String pcNumber) {
		this.pcNumber = pcNumber;
	}
	
	public Font getProductFont() {
		return productFont;
	}
	public void setProductFont(Font productFont) {
		this.productFont = productFont;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
}


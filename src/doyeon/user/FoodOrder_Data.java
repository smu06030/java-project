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
	
	//테이블 속성명
	private final String RTABLE_NAME = "상품명";
	private final String RTABLE_PRICE = "금액";
	private final String RTABLE_COUNT = "수량";
	private final String RTABLE_TOT = "판매금액";
	
	//데이터 
	private String userID;
	private String pcNumber;
	//private String payMethod;
	
	private Font productFont;
	
	private String typeName[]; //상품 타입 이름
	private String typeCode[]; //상품 타입 코드
	private String productName[][]; //상품 이름 [tab][obj]
	private String productPrice[][]; //상품 가격
	private String productImgSrc[][]; //상품 이미지 경로
	private int tabCount; //탭 수
	private int productCount[]; //탭별 상품 수
	private int payment; //결제총액
	private Vector<String> columnName; //결과 표 컬럼
	private Vector<Vector<String>> row; //결과 행
	private Vector<ProductObj> productRowV; //상품 위치
	private Client client;

	//게터세터------------------------------------
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
	// 필요 없는 셋터 겟터는 정리
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


package doyeon.user;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;

public class FoodOrder_Object {
	
	public JComboBox<String> getSelectCost() {
		return selectCost;
	}
	public void setSelectCost(JComboBox<String> selectCost) {
		this.selectCost = selectCost;
	}
	public ProductObj[][] getProductObj() {
		return productObj;
	}
	public void setProductObj(ProductObj[][] productObj) {
		this.productObj = productObj;
	}
	private FoodOrder parent; //최상위 컨테이너
	private ProductMainPanel productMain[]; //상단 메인 패널(위쪽에 상품 이미지 담고 있음)
	private ProductObj productObj[][]; //[탭번호][오브젝트번호]
	//private Vector<Vector<ProductObj>> productObj;
	private ResultsPanel resultsPanel; // 하단 메인 패널 (표, 결제수단, 주문 포함)
	private ResultsPanelCenter resultsCenter;
	private ResultsPanelRight resultsRight;
	private MakeTable makeTable; //테이블 모델
	private JTable resultsTable;
	private JLabel printResult; //최종 금액 출력
	private JComboBox<String> selectCost;
	
	//게터세터-------------------------------
	public FoodOrder getParent() {
		return parent;
	}
	public void setParent(FoodOrder parent) {
		this.parent = parent;
	}
	public JLabel getPrintResult() {
		return printResult;
	}
	public void setPrintResult(JLabel printResult) {
		this.printResult = printResult;
	}
	public JTable getResultsTable() {
		return resultsTable;
	}
	public void setResultsTable(JTable resultsTable) {
		this.resultsTable = resultsTable;
	}
	public ProductMainPanel[] getProductMain() {
		return productMain;
	}
	public void setProductMain(ProductMainPanel productMain[]) {
		this.productMain = productMain;
	}
	public ResultsPanel getResultsPanel() {
		return resultsPanel;
	}
	public void setResultsPanel(ResultsPanel resultsPanel) {
		this.resultsPanel = resultsPanel;
	}
	public ResultsPanelCenter getResultsCenter() {
		return resultsCenter;
	}
	public void setResultsCenter(ResultsPanelCenter resultsCenter) {
		this.resultsCenter = resultsCenter;
	}
	public ResultsPanelRight getResultsRight() {
		return resultsRight;
	}
	public void setResultsRight(ResultsPanelRight resultsRight) {
		this.resultsRight = resultsRight;
	}
	public MakeTable getMakeTable() {
		return makeTable;
	}
	public void setMakeTable(MakeTable makeTable) {
		this.makeTable = makeTable;
	}

}

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
	private FoodOrder parent; //�ֻ��� �����̳�
	private ProductMainPanel productMain[]; //��� ���� �г�(���ʿ� ��ǰ �̹��� ��� ����)
	private ProductObj productObj[][]; //[�ǹ�ȣ][������Ʈ��ȣ]
	//private Vector<Vector<ProductObj>> productObj;
	private ResultsPanel resultsPanel; // �ϴ� ���� �г� (ǥ, ��������, �ֹ� ����)
	private ResultsPanelCenter resultsCenter;
	private ResultsPanelRight resultsRight;
	private MakeTable makeTable; //���̺� ��
	private JTable resultsTable;
	private JLabel printResult; //���� �ݾ� ���
	private JComboBox<String> selectCost;
	
	//���ͼ���-------------------------------
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

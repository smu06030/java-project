package doyeon;

public interface DBTable {
	
	//��ǰ ���̺� ��, �Ӽ� ��
	final String TABLE_PRODUCT = "��ǰ";
	final String ATTRIBUTE_NAME = "��ǰ��";
	final String ATTRIBUTE_PRICE = "����";
	final String ATTRIBUTE_IMGSRC = "�̹���";
	final String ATTRIBUTE_TYPE = "�з�";
	final String ATTRIBUTE_STOCK = "���";
	
	//��ǰ ���� ���̺� ��, �Ӽ� ��
	final String TABLE_PRODUCT_TYPE = "��ǰ�з�";
	final String ATTRIBUTE_TYPE_NUM = "�з��ڵ�";
	final String ATTRIBUTE_TYPE_NAME = "�з���";
	
	//�α� ���̺� ��, �Ӽ� ��
	final String TABLE_LOG = "����";
	final String ATTRIBUTE_LOG_DATE = "��¥";
	final String ATTRIBUTE_LOG_PRODUCT = "��ǰ��";
	final String ATTRIBUTE_LOG_USER = "���̵�";
	final String ATTRIBUTE_LOG_PCNUM = "�Ǿ���ȣ";
	final String ATTRIBUTE_LOG_COST = "����";
	final String ATTRIBUTE_LOG_COUNT = "����";
	final String ATTRIBUTE_LOG_METHOD = "��������";
	
	final String TABLE_MEMBER = "ȸ��";
	final String ATTRIBUTE_MEMBER_ID = "���̵�";
	final String ATTRIBUTE_MEMBER_HOUR = "ȸ���ð�";
	final String ATTRIBUTE_MEMBER_PASSWD = "��й�ȣ";
	final String ATTRIBUTE_MEMBER_NAME = "�̸�";
	final String ATTRIBUTE_MEMBER_BIRTH = "�������";
	final String ATTRIBUTE_MEMBER_PHONE = "�޴���";
	final String ATTRIBUTE_MEMBER_EMAIL = "�̸���";

	//���� ���̺�
	final String TABLE = "����";
	final String[] ATTRIBUTE = {"��ǰ�̸�", "�����ð�", "����"};
}

package doyeon;

public interface DBTable {
	
	//상품 테이블 명, 속성 명
	final String TABLE_PRODUCT = "상품";
	final String ATTRIBUTE_NAME = "상품명";
	final String ATTRIBUTE_PRICE = "가격";
	final String ATTRIBUTE_IMGSRC = "이미지";
	final String ATTRIBUTE_TYPE = "분류";
	final String ATTRIBUTE_STOCK = "재고량";
	
	//상품 종류 테이블 명, 속성 명
	final String TABLE_PRODUCT_TYPE = "상품분류";
	final String ATTRIBUTE_TYPE_NUM = "분류코드";
	final String ATTRIBUTE_TYPE_NAME = "분류명";
	
	//로그 테이블 명, 속성 명
	final String TABLE_LOG = "결제";
	final String ATTRIBUTE_LOG_DATE = "날짜";
	final String ATTRIBUTE_LOG_PRODUCT = "상품명";
	final String ATTRIBUTE_LOG_USER = "아이디";
	final String ATTRIBUTE_LOG_PCNUM = "피씨번호";
	final String ATTRIBUTE_LOG_COST = "가격";
	final String ATTRIBUTE_LOG_COUNT = "수량";
	final String ATTRIBUTE_LOG_METHOD = "결제수단";
	
	final String TABLE_MEMBER = "회원";
	final String ATTRIBUTE_MEMBER_ID = "아이디";
	final String ATTRIBUTE_MEMBER_HOUR = "회원시간";
	final String ATTRIBUTE_MEMBER_PASSWD = "비밀번호";
	final String ATTRIBUTE_MEMBER_NAME = "이름";
	final String ATTRIBUTE_MEMBER_BIRTH = "생년월일";
	final String ATTRIBUTE_MEMBER_PHONE = "휴대폰";
	final String ATTRIBUTE_MEMBER_EMAIL = "이메일";

	//충전 테이블
	final String TABLE = "충전";
	final String[] ATTRIBUTE = {"상품이름", "충전시간", "가격"};
}

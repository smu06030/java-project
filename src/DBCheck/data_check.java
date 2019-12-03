package DBCheck;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class data_check {
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static String url = "jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC";
	private static String user = "root";
	private static String pw = "dlscjf158!A";
	
	/*---------------------------- DB------------------------------*/
	
	public data_check(){
		try {
			conn = DriverManager.getConnection(url,user,pw);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// 회원DB에서 아이디와 비밀번호가 있는지 확인하는 메소드
	public boolean check(String id, String password){
		try {
			String sql = "select * from 회원 where 아이디 = ? and 비밀번호 = ?";
			pstmt = conn.prepareStatement(sql); 
				
			pstmt.setString(1, id);
			pstmt.setString(2, password);
				
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				return true;
			}
			
			pstmt.close();
			conn.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return false;
	}
	// 회원 DB에서 아이디를 검색해 이름을 가져오는 메소드
	public String id_search(String id){
		String name = "";
		
		try {
			String sql = "select 이름  from 회원 where 아이디 = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				name = rs.getString("이름");
				return name;
			}
			pstmt.close();
			conn.close();
			rs.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return name;
	}
	// 회원DB에서 id랑 맞는 회원시간을 가지고 온다.
	public int time_hour(String id) {
		int hour = 0;
		
		try {
			String sql = "select 회원시간  from 회원 where 아이디 = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,id);
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				hour = rs.getInt("회원시간");
			}

			pstmt.close();
			conn.close();
			rs.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return hour;
	}
	// 회원DB에서 id랑 맞는 회원분을 가지고 온다.
	public int time_minute(String id) {
		int minute = 0;
		
		try {
			String sql = "select 회원분  from 회원 where 아이디 = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,id);
			ResultSet rs = pstmt.executeQuery();
		
			while(rs.next()) {
				minute = rs.getInt("회원분");
			}
			
			pstmt.close();
			conn.close();
			rs.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return minute;
	}
	// 회원DB에서 id랑 맞는 회원초을 가지고 온다.
	public int time_sec(String id) {
		int sec = 0;
		
		try {
			String sql = "select 회원초  from 회원 where 아이디 = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,id);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				sec = rs.getInt("회원초");
			}
		
			pstmt.close();
			conn.close();
			rs.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return sec;
	}
	
	// pc가 사용 종료를 누르면 현재 시간을 회원DB에 저장한다.
	public void timeInsert(int hour, int minute, int sec, String id){
		
		try {
			String sql = "update 회원 set 회원시간 = ?, 회원분 = ?, 회원초 = ? where 아이디 = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, hour);
			pstmt.setInt(2, minute);
			pstmt.setInt(3, sec);
			pstmt.setString(4,id);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
	}
	
	// id 중복체크
	public boolean id_check(String id) {
		try {
			String sql = "select * from 회원  where 아이디 = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,id);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				return false;
			}
			
			pstmt.close();
			conn.close();
			rs.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return true;
	}
	// 회원정보를 회원 DB에 저장
	public void member_join(String id, String pass, String name, String birth, int hour, int minute, String phone, String email, int sec) {
		try {
			// 생년월일을 date형식으로 변경
			Date date = Date.valueOf(birth);
			
			String sql = "insert into 회원 values(?,?,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			pstmt.setString(3, name);
			pstmt.setDate(4, date);
			pstmt.setInt(5, hour);
			pstmt.setInt(6, minute);
			pstmt.setString(7, phone);
			pstmt.setString(8, email);
			pstmt.setInt(9, sec);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// 회원정보를 회원 DB에서 수정
	public void member_update(String id, String pass, String name, String birth, String phone, String email) {
		try {
			// 생년월일을 date형식으로 변경
			Date date = Date.valueOf(birth);
			
			String sql = "update 회원 set 아이디 = ?, 비밀번호 = ?, 이름  = ?, 생년월일 = ?, 휴대폰 = ?, 이메일 = ? where 아이디 = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			pstmt.setString(3, name);
			pstmt.setDate(4, date);
			pstmt.setString(5, phone);
			pstmt.setString(6, email);
			pstmt.setString(7, id);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// 회원정보를 회원 DB에서 삭제
	public void member_delete(String id) {
		try {
			String sql = "delete from 회원 where 아이디 = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// 지점명을 가져온다.
	public String Store_name() {
		try {
			String sql = "select 지점명 from 매장 where 사업자번호 = ?";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "1234-5678");
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				return rs.getString("지점명");
			}
			
			pstmt.close();
			conn.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return "";
	}
	// 사업자번호를 가져온다.
	public String Store_num() {
		try {
			String sql = "select 사업자번호 from 매장 where 지점명 = ?";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "100억pc방");
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				return rs.getString("사업자번호");
			}
			
			pstmt.close();
			conn.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return "";
	}
	// 좌석 수를 변경한다.
	public void seatNum(int num) {
		try {
			String sql = "update 매장 set 좌석수 = ? where 사업자번호 = ?";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			pstmt.setString(2, "1234-5678");
		
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// 좌석 수를 가져온다.
	public int getSeatNum() {
		try {
			String sql = "select 좌석수 from 매장 where 사업자번호 = ?";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "1234-5678");
	
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				return rs.getInt("좌석수");
			}
			
			pstmt.close();
			conn.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}
}
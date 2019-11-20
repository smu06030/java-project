package DBCheck;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class data_check {
	static Connection conn;
	static PreparedStatement pstmt;
	
	// 회원DB에서 아이디와 비밀번호가 있는지 확인하는 메소드
	public boolean check(String id, String password) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC","root","dlscjf158!A");

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
		return false;
	}
	// 회원 DB에서 아이디를 검색해 이름을 가져오는 메소드
	public String id_search(String id) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC","root","dlscjf158!A");
		
		String sql = "select 이름  from 회원 where 아이디 = ?";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		
		String name = "";
		
		while(rs.next()) {
			name = rs.getString("이름");
			return name;
		}
		pstmt.close();
		conn.close();
		
		return name;
	}
	// 회원DB에서 id랑 맞는 회원시간을 가지고 온다.
	public int time_hour(String id) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC","root","dlscjf158!A");
		
		String sql = "select 회원시간  from 회원 where 아이디 = ?";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,id);
		ResultSet rs = pstmt.executeQuery();
		
		int hour = 0;
		while(rs.next()) {
			hour = rs.getInt("회원시간");
		}

		pstmt.close();
		conn.close();
		
		return hour;
	}
	// 회원DB에서 id랑 맞는 회원분을 가지고 온다.
	public int time_minute(String id) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC","root","dlscjf158!A");
		
		String sql = "select 회원분  from 회원 where 아이디 = ?";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,id);
		ResultSet rs = pstmt.executeQuery();
	
		int minute = 0;
		while(rs.next()) {
			minute = rs.getInt("회원분");
		}
		
		pstmt.close();
		conn.close();
		
		return minute;
	}
	// 회원DB에서 id랑 맞는 회원초을 가지고 온다.
	public int time_sec(String id) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC","root","dlscjf158!A");
		
		String sql = "select 회원초  from 회원 where 아이디 = ?";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,id);
		ResultSet rs = pstmt.executeQuery();
		
		int sec = 0;
		
		while(rs.next()) {
			sec = rs.getInt("회원초");
		}
	
		pstmt.close();
		conn.close();
		
		return sec;
	}
	
	// pc가 사용 종료를 누르면 현재 시간을 회원DB에 저장한다.
	public void timeInsert(int hour, int minute, int sec, String id) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC","root","dlscjf158!A");
		
		String sql = "update 회원 set 회원시간 = ?, 회원분 = ?, 회원초 = ? where 아이디 = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, hour);
		pstmt.setInt(2, minute);
		pstmt.setInt(3, sec);
		pstmt.setString(4,id);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
}

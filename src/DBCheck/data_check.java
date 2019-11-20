package DBCheck;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class data_check {
	static Connection conn;
	static PreparedStatement pstmt;
	
	// ȸ��DB���� ���̵�� ��й�ȣ�� �ִ��� Ȯ���ϴ� �޼ҵ�
	public boolean check(String id, String password) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC","root","dlscjf158!A");

		String sql = "select * from ȸ�� where ���̵� = ? and ��й�ȣ = ?";
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
	// ȸ�� DB���� ���̵� �˻��� �̸��� �������� �޼ҵ�
	public String id_search(String id) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC","root","dlscjf158!A");
		
		String sql = "select �̸�  from ȸ�� where ���̵� = ?";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		
		String name = "";
		
		while(rs.next()) {
			name = rs.getString("�̸�");
			return name;
		}
		pstmt.close();
		conn.close();
		
		return name;
	}
	// ȸ��DB���� id�� �´� ȸ���ð��� ������ �´�.
	public int time_hour(String id) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC","root","dlscjf158!A");
		
		String sql = "select ȸ���ð�  from ȸ�� where ���̵� = ?";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,id);
		ResultSet rs = pstmt.executeQuery();
		
		int hour = 0;
		while(rs.next()) {
			hour = rs.getInt("ȸ���ð�");
		}

		pstmt.close();
		conn.close();
		
		return hour;
	}
	// ȸ��DB���� id�� �´� ȸ������ ������ �´�.
	public int time_minute(String id) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC","root","dlscjf158!A");
		
		String sql = "select ȸ����  from ȸ�� where ���̵� = ?";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,id);
		ResultSet rs = pstmt.executeQuery();
	
		int minute = 0;
		while(rs.next()) {
			minute = rs.getInt("ȸ����");
		}
		
		pstmt.close();
		conn.close();
		
		return minute;
	}
	// ȸ��DB���� id�� �´� ȸ������ ������ �´�.
	public int time_sec(String id) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC","root","dlscjf158!A");
		
		String sql = "select ȸ����  from ȸ�� where ���̵� = ?";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,id);
		ResultSet rs = pstmt.executeQuery();
		
		int sec = 0;
		
		while(rs.next()) {
			sec = rs.getInt("ȸ����");
		}
	
		pstmt.close();
		conn.close();
		
		return sec;
	}
	
	// pc�� ��� ���Ḧ ������ ���� �ð��� ȸ��DB�� �����Ѵ�.
	public void timeInsert(int hour, int minute, int sec, String id) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC","root","dlscjf158!A");
		
		String sql = "update ȸ�� set ȸ���ð� = ?, ȸ���� = ?, ȸ���� = ? where ���̵� = ?";
		
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

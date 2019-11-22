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
	
	// ȸ��DB���� ���̵�� ��й�ȣ�� �ִ��� Ȯ���ϴ� �޼ҵ�
	public boolean check(String id, String password){
		try {
			conn = DriverManager.getConnection(url,user,pw);

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
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return false;
	}
	// ȸ�� DB���� ���̵� �˻��� �̸��� �������� �޼ҵ�
	public String id_search(String id){
		String name = "";
		
		try {
			conn = DriverManager.getConnection(url,user,pw);
			
			String sql = "select �̸�  from ȸ�� where ���̵� = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				name = rs.getString("�̸�");
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
	// ȸ��DB���� id�� �´� ȸ���ð��� ������ �´�.
	public int time_hour(String id) {
		int hour = 0;
		
		try {
			conn = DriverManager.getConnection(url,user,pw);

			String sql = "select ȸ���ð�  from ȸ�� where ���̵� = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,id);
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				hour = rs.getInt("ȸ���ð�");
			}

			pstmt.close();
			conn.close();
			rs.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return hour;
	}
	// ȸ��DB���� id�� �´� ȸ������ ������ �´�.
	public int time_minute(String id) {
		int minute = 0;
		
		try {
			conn = DriverManager.getConnection(url,user,pw);
			
			String sql = "select ȸ����  from ȸ�� where ���̵� = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,id);
			ResultSet rs = pstmt.executeQuery();
		
			while(rs.next()) {
				minute = rs.getInt("ȸ����");
			}
			
			pstmt.close();
			conn.close();
			rs.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return minute;
	}
	// ȸ��DB���� id�� �´� ȸ������ ������ �´�.
	public int time_sec(String id) {
		int sec = 0;
		
		try {
			conn = DriverManager.getConnection(url,user,pw);

			String sql = "select ȸ����  from ȸ�� where ���̵� = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,id);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				sec = rs.getInt("ȸ����");
			}
		
			pstmt.close();
			conn.close();
			rs.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return sec;
	}
	
	// pc�� ��� ���Ḧ ������ ���� �ð��� ȸ��DB�� �����Ѵ�.
	public void timeInsert(int hour, int minute, int sec, String id){
		
		try {
			conn = DriverManager.getConnection(url,user,pw);
			
			String sql = "update ȸ�� set ȸ���ð� = ?, ȸ���� = ?, ȸ���� = ? where ���̵� = ?";
			
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
	
	// id �ߺ�üũ
	public boolean id_check(String id) {
		try {
			conn = DriverManager.getConnection(url,user,pw);
			
			String sql = "select * from ȸ��  where ���̵� = ?";
			
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
	// ȸ�������� ȸ�� DB�� ����
	public void member_join(String id, String pass, String name, String birth, int hour, int minute, String phone, String email, int sec) {
		try {
			conn = DriverManager.getConnection(url,user,pw);
			
			// ��������� date�������� ����
			Date date = Date.valueOf(birth);
			
			String sql = "insert into ȸ�� values(?,?,?,?,?,?,?,?,?)";
			
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
	
	// ȸ�������� ȸ�� DB���� ����
	public void member_update(String id, String pass, String name, String birth, String phone, String email) {
		try {
			conn = DriverManager.getConnection(url,user,pw);
			
			// ��������� date�������� ����
			Date date = Date.valueOf(birth);
			
			String sql = "update ȸ�� set ���̵� = ?, ��й�ȣ = ?, �̸�  = ?, ������� = ?, �޴��� = ?, �̸��� = ? where ���̵� = ?";
			
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
	
	// ȸ�������� ȸ�� DB���� ����
	public void member_delete(String id) {
		try {
			conn = DriverManager.getConnection(url,user,pw);
			
			String sql = "delete from ȸ�� where ���̵� = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}

package doyeon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

interface DBConnectionI{
	
	//검색한 조건의 결과 갯수를 리턴
	public int whereQueryCnt( String where);

	//입력된 필드명의 데이터를 배열로 저장하는 메소드
	public void fieldDataStringAry( String field, String resultAry[]);
	public void fieldDataStringAry( String field, String resultAry[], String where);
	
	//테이블의 행의 갯수를 리턴
	public int getCardinality();
	
	//입력된 필드명의 데이터를 벡터로 저장하는 메소드
	public void fieldDataStringVector( String field, Vector<String> sv);
	public void fieldDataStringVector( String field, Vector<String> sv, String where);
	public void fieldDataObjectVector( String field, Vector<Object> vector);
	public void fieldDataObjectVector( String field, Vector<Object> vector, String where);
	
	//입력된 필드들의 조건에 따른 1행을 리턴
	public Vector<String> fieldsDataStringVector(String fields[], String where);

	//입력된 속성 벡터 테이블에 데이터 벡터를 삽입
	public void insertStringData( Vector<String> data, Vector<String> attribute);
	public boolean isInsertStringData( Vector<String> data, Vector<String> attribute);
	public boolean isInsertObject( Vector<Object> data, Vector<String> attribute);
	
	//조건에 맞는 데이터가 검색되는지 확인
	boolean isSelect(String where);
	public int getSelectInt(String field, String where); //검색한 정수데이터를 반환
	public String getSelectString(String field, String where); //검색한 문자열데이터를 반환

	//테이블의 행을 가져옴
	public boolean importRow(Vector<String> attribute, Vector<Vector<Object>> values,  String where);
	public boolean importRow(Vector<String> attribute, Vector<Vector<Object>> values);
	
	//업데이트문
	public void updatePlusInt(String attribute, int data, String where);
	public void updateInt(String attribute, int data, String where);
	public void updateString(String attribute, String data, String where);
	public void updateTupleAll( Vector<String>attribute, Vector<Object>tuple, String where);
	public boolean isUpdateTupleAll( Vector<String>attribute, Vector<Object>tuple, String where);
	
	//삭제
	public void deleteTuple( String attribute, int index);
}

public class DBConnection implements DBConnectionI{

	private Connection con; // DB 연결
	private Statement st; // SQL 처리
	private ResultSet rs; // DB 결과 값
	private String table;
	public DBConnection(String table) {
		this.table = table;
		try {
			
			//Class.forName("com.mysql.jdbc.Driver");
			//String url = "jdbc:mysql://localhost:3306/javatest?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
			//String url = "jdbc:mysql://172.111.117.107:3306/pcbang?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
			//String url = "jdbc:mysql://192.168.0.9:3306/pcbang?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
			String url = "jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC";
			String user = "root";
			String pw = "dlscjf158!A";
			
			//con = DriverManager.getConnection(url, "root", "3512877qwe");
			con = DriverManager.getConnection(url, user, pw);
			st = con.createStatement();
		}
		catch(Exception e) {
			
			System.out.println("데이터베이스 연결 오류 : "+e.getMessage());
			
		}
	}
	
	
	public int whereQueryCnt(String where) {

		int i;
		try {

			rs = st.executeQuery("Select * from "+table+" where "+where);
			for(i = 0; rs.next(); i++);
			return i;
					
		}catch(Exception e) {
			
			System.out.println("데이터베이스 검색 오류 : "+ e.getMessage());
			
		}
		return 0;
	}
	
	
	public void fieldDataStringAry( String field, String resultAry[]) {
		
		try {
			rs = st.executeQuery("Select * from " + table);

			for(int i = 0; rs.next(); i++)
				resultAry[i] = rs.getString(field);

		}catch(Exception e) {
			
			System.out.println("데이터베이스 검색 오류 : "+ e.getMessage());
			
		}

	}
	
	public void fieldDataStringAry( String field, String resultAry[], String where) {
		
		try {
			rs = st.executeQuery("Select * from " + table + " where " + where);
			
			for(int i = 0; rs.next(); i++)
				resultAry[i] = rs.getString(field);

		}catch(Exception e) {
			
			System.out.println("데이터베이스 조건 검색 오류 : "+ e.getMessage());
			
		}

	}
	
	public int getCardinality() {
		
		int i;
		
		try {
			rs = st.executeQuery("select * from "+table);
			for(i = 0; rs.next(); i++);
			return i;
		
		}catch(Exception e) { System.out.println("데이터베이스 검색 오류 : "+ e.getMessage()); }
		return 0;
	}
	
	//테스트 중 11/22
	public void fieldDataStringVector( String field, Vector<String> sv) {
		
		try {
			
			rs = st.executeQuery("Select * from " + table);
			while(rs.next())
			
				sv.add(rs.getString(field));

		}catch(Exception e) {
			
			System.out.println("데이터베이스 검색 오류 : "+ e.getMessage());
			
		}

	}
	
	public void fieldDataObjectVector( String field, Vector<Object> vector) {
		
		try {
			
			rs = st.executeQuery("Select * from " + table);
			while(rs.next())
				try {
					vector.add(Integer.parseInt(rs.getString(field)));
				}catch(NumberFormatException e) {
					vector.add(rs.getString(field));
				}
		}catch(Exception e) {
			
			System.out.println("데이터베이스 검색 오류 : "+ e.getMessage());
			
		}

	}
	
	public void fieldDataObjectVector( String field, Vector<Object> vector, String where) {
		
		try {
			
			rs = st.executeQuery("Select * from " + table + " where "+where);
			while(rs.next())
				try {
					vector.add(Integer.parseInt(rs.getString(field)));
				}catch(NumberFormatException e) {
					vector.add(rs.getString(field));
				}
		}catch(Exception e) {
			
			System.out.println("데이터베이스 검색 오류 : "+ e.getMessage());
			
		}

	}
	
	public void fieldDataStringVector( String field, Vector<String> sv, String where) {
		
		try {
			rs = st.executeQuery("Select * from " + table + " where " + where);
			
			while(rs.next())
				sv.add(rs.getString(field));

		}catch(Exception e) {
			
			System.out.println("데이터베이스 조건 검색 오류 : "+ e.getMessage());
			
		}

	}
	
	public void insertStringData( Vector<String> data, Vector<String> attribute) {
		
		try {
			//data
			String attS="", dataS="";
			for(int i = 0; i<data.size();i++) {

				if(i!=data.size()-1) {
					try {
						dataS+=Integer.parseInt(data.get(i))+",";

					}catch(NumberFormatException ne) {
						dataS+="'"+data.get(i)+"',";

					}
				}
				else {
					try {
						dataS+=Integer.parseInt(data.get(i));

					}catch(NumberFormatException ne) {
						dataS+="'"+data.get(i)+"'";

					}
				}
			}
			
			//속성
			for(int i = 0; i<attribute.size();i++) {
				if(i!=attribute.size()-1) attS+=attribute.get(i)+",";
				else attS+=attribute.get(i);
			}
			String sql = "insert into "+table+"("+attS+") values("+dataS+");";
			System.out.println(sql);
			st.executeUpdate(sql);
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
public boolean isInsertStringData( Vector<String> data, Vector<String> attribute) {
	
	try {
		//data
		String attS="", dataS="";
		for(int i = 0; i<data.size();i++) {

			if(i!=data.size()-1) {
				try {
					dataS+=Integer.parseInt(data.get(i))+",";

				}catch(NumberFormatException ne) {
					dataS+="'"+data.get(i)+"',";

				}
			}
			else {
				try {
					dataS+=Integer.parseInt(data.get(i));

				}catch(NumberFormatException ne) {
					dataS+="'"+data.get(i)+"'";

				}
			}
		}
		
		//속성
		for(int i = 0; i<attribute.size();i++) {
			if(i!=attribute.size()-1) attS+=attribute.get(i)+",";
			else attS+=attribute.get(i);
		}
		String sql = "insert into "+table+"("+attS+") values("+dataS+");";
		System.out.println(sql);
		st.executeUpdate(sql);
		return true;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	
}
	
public boolean isInsertObject( Vector<Object> data, Vector<String> attribute) {
		
		try {
			//data
			String attS="", dataS="";
			for(int i = 0; i<data.size();i++) {

				if(i!=data.size()-1) {
					try {
						dataS+=Integer.parseInt((String)data.get(i))+",";

					}catch(NumberFormatException ne) {
						dataS+="'"+data.get(i)+"',";

					}
				}
				else {
					try {
						dataS+=Integer.parseInt((String)data.get(i));

					}catch(NumberFormatException ne) {
						dataS+=attribute.get(i);

					}
				}
			}
			
			//속성
			for(int i = 0; i<attribute.size();i++) {
				if(i!=attribute.size()-1) attS+=attribute.get(i)+",";
				else attS+=attribute.get(i);
			}
			String sql = "insert into "+table+"("+attS+") values("+dataS+");";
			System.out.println(sql);
			st.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
		
	}
	
	
	public boolean importRow(Vector<String> attribute, Vector<Vector<Object>> values,  String where) {
		
		try {
			values.clear();
			System.out.println("Select * from " + table + " where " + where);
			rs = st.executeQuery("Select * from " + table + " where " + where);
			
			while(rs.next()) {
				Vector<Object> rowData = new Vector<Object>();
				for(int i=0; i<attribute.size();i++) {
					rowData.add(rs.getString(attribute.get(i)));
				}
				values.add(rowData);

			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(where);
			return false;
		}
		
	}
	
	public boolean importRow(Vector<String> attribute, Vector<Vector<Object>> values) {
		
		try {
			values.clear();
			rs = st.executeQuery("Select * from " + table);
			
			while(rs.next()) {
				Vector<Object> rowData = new Vector<Object>();
				for(int i=0; i<attribute.size();i++) {
					rowData.add(rs.getString(attribute.get(i)));
				}
				values.add(rowData);

			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void updateTupleAll( Vector<String>attribute, Vector<Object>tuple, String where) {
		String set="";
		try {
			//data
			for(int i = 0; i<attribute.size();i++) {

				if(i!=tuple.size()-1) {
					try {
						set += attribute.get(i)+" = "+ Integer.parseInt((String)tuple.get(i))+",";

					}catch(NumberFormatException ne) {
						set += attribute.get(i)+" = '"+tuple.get(i)+"',";

					}
				}
				else {
					try {
						set += attribute.get(i)+" = "+Integer.parseInt((String)tuple.get(i));

					}catch(NumberFormatException ne) {
						set += attribute.get(i)+" = "+tuple.get(i);

					}
				}
			}
			
			//SQL문
		
			String sql;
			try {
				int whereI = Integer.parseInt(where);
				sql = "update "+table+" set "+set+" where "+attribute.get(0)+" = "+whereI;

			}catch(NumberFormatException ne) {
				sql = "update "+table+" set "+set+" where "+attribute.get(0)+" = '"+where+"'";
			}

			//String sql = "update "+table+" set "+set+" where "+attribute.get(0)+" = "+where;
			System.out.println(sql);
			st.executeUpdate(sql);
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public boolean isUpdateTupleAll( Vector<String>attribute, Vector<Object>tuple, String where) {
			String set="";
			try {
				//data
				for(int i = 0; i<attribute.size();i++) {

					if(i!=tuple.size()-1) {
						try {
							set += attribute.get(i)+" = "+ Integer.parseInt((String)tuple.get(i))+",";

						}catch(NumberFormatException ne) {
							set += attribute.get(i)+" = '"+tuple.get(i)+"',";

						}
					}
					else {
						try {
							set += attribute.get(i)+" = "+Integer.parseInt((String)tuple.get(i));

						}catch(NumberFormatException ne) {
							set += attribute.get(i)+" = "+tuple.get(i);

						}
					}
				}
				
				//SQL문
			
				String sql;
				try {
					int whereI = Integer.parseInt(where);
					sql = "update "+table+" set "+set+" where "+attribute.get(0)+" = "+whereI;

				}catch(NumberFormatException ne) {
					sql = "update "+table+" set "+set+" where "+attribute.get(0)+" = '"+where+"'";
				}

				//String sql = "update "+table+" set "+set+" where "+attribute.get(0)+" = "+where;
				System.out.println(sql);
				st.executeUpdate(sql);
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				return false;
			}
		
	}
	
	public void deleteTuple( String attribute, int index) {

		try {
			
			rs = st.executeQuery("Select * from " + table);
			
			while(index>=0) {
				index-=1;
				rs.next();
				System.out.println(rs.getString(attribute));
			}
			
			String sql = "delete from "+table+" where "+attribute+" = '"+rs.getString(attribute)+"'";
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean isSelect(String where) {
		// TODO Auto-generated method stub
		try {
			rs = st.executeQuery("Select * from " + table + " where "+where);
			if(rs.next()) return true;
			else return false;
			
		} catch (SQLException e) {
			return false;
		}

	}


	@Override
	public int getSelectInt(String field, String where) {
		// TODO Auto-generated method stub
		try {
			rs = st.executeQuery("Select * from " + table + " where "+where);
			if(rs.next()) return rs.getInt(field);
			else return 0;
			
		} catch (SQLException e) {
			return 0;
		}
	}

	public void updateInt(String attribute, int data, String where) {
		// TODO Auto-generated method stub
		String sql;
		try {
			sql = "update "+table+" set "+attribute+" = "+data+" where "+where;
			System.out.println(sql);
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public void updatePlusInt(String attribute, int data, String where) {
		
		String sql;
		try {
			sql = "update "+table+" set "+attribute+" = "+attribute+" + "+data+" where "+where;
			System.out.println(sql);
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}


	@Override
	public Vector<String> fieldsDataStringVector(String[] fields, String where) {
		Vector<String> tuple;
		try {
			tuple = new Vector<String>();
			rs = st.executeQuery("Select * from " + table +" where "+ where);
			System.out.println("Select * from " + table +" where "+ where);
			tuple = new Vector<String>();
			rs.next();
			for(int i = 0; i<fields.length;i++) {
				tuple.add(rs.getString(fields[i]));
			}
			return tuple;
		}catch(Exception e) {
			
			System.out.println("데이터베이스 조건 검색 오류 : "+ e.getMessage());
			
		}

		return null;
	}


	@Override
	public String getSelectString(String field, String where) {
		// TODO Auto-generated method stub
		try {
			rs = st.executeQuery("Select * from " + table + " where "+where);
			if(rs.next()) return rs.getString(field);
			else return null;
			
		} catch (SQLException e) {
			return null;
		}
	}


	@Override
	public void updateString(String attribute, String data, String where) {
		String sql;
		try {
			sql = "update "+table+" set "+attribute+" = "+data+" where "+where;
			System.out.println(sql);
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

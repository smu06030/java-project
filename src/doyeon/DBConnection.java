package doyeon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

interface DBConnectionI{
	
	//�˻��� ������ ��� ������ ����
	public int whereQueryCnt( String where);

	//�Էµ� �ʵ���� �����͸� �迭�� �����ϴ� �޼ҵ�
	public void fieldDataStringAry( String field, String resultAry[]);
	public void fieldDataStringAry( String field, String resultAry[], String where);
	
	//���̺��� ���� ������ ����
	public int getCardinality();
	
	//�Էµ� �ʵ���� �����͸� ���ͷ� �����ϴ� �޼ҵ�
	public void fieldDataStringVector( String field, Vector<String> sv);
	public void fieldDataStringVector( String field, Vector<String> sv, String where);
	public void fieldDataObjectVector( String field, Vector<Object> vector);
	public void fieldDataObjectVector( String field, Vector<Object> vector, String where);
	
	//�Էµ� �ʵ���� ���ǿ� ���� 1���� ����
	public Vector<String> fieldsDataStringVector(String fields[], String where);

	//�Էµ� �Ӽ� ���� ���̺� ������ ���͸� ����
	public void insertStringData( Vector<String> data, Vector<String> attribute);
	public boolean isInsertStringData( Vector<String> data, Vector<String> attribute);
	public boolean isInsertObject( Vector<Object> data, Vector<String> attribute);
	
	//���ǿ� �´� �����Ͱ� �˻��Ǵ��� Ȯ��
	boolean isSelect(String where);
	public int getSelectInt(String field, String where); //�˻��� ���������͸� ��ȯ
	public String getSelectString(String field, String where); //�˻��� ���ڿ������͸� ��ȯ

	//���̺��� ���� ������
	public boolean importRow(Vector<String> attribute, Vector<Vector<Object>> values,  String where);
	public boolean importRow(Vector<String> attribute, Vector<Vector<Object>> values);
	
	//������Ʈ��
	public void updatePlusInt(String attribute, int data, String where);
	public void updateInt(String attribute, int data, String where);
	public void updateString(String attribute, String data, String where);
	public void updateTupleAll( Vector<String>attribute, Vector<Object>tuple, String where);
	public boolean isUpdateTupleAll( Vector<String>attribute, Vector<Object>tuple, String where);
	
	//����
	public void deleteTuple( String attribute, int index);
}

public class DBConnection implements DBConnectionI{

	private Connection con; // DB ����
	private Statement st; // SQL ó��
	private ResultSet rs; // DB ��� ��
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
			
			System.out.println("�����ͺ��̽� ���� ���� : "+e.getMessage());
			
		}
	}
	
	
	public int whereQueryCnt(String where) {

		int i;
		try {

			rs = st.executeQuery("Select * from "+table+" where "+where);
			for(i = 0; rs.next(); i++);
			return i;
					
		}catch(Exception e) {
			
			System.out.println("�����ͺ��̽� �˻� ���� : "+ e.getMessage());
			
		}
		return 0;
	}
	
	
	public void fieldDataStringAry( String field, String resultAry[]) {
		
		try {
			rs = st.executeQuery("Select * from " + table);

			for(int i = 0; rs.next(); i++)
				resultAry[i] = rs.getString(field);

		}catch(Exception e) {
			
			System.out.println("�����ͺ��̽� �˻� ���� : "+ e.getMessage());
			
		}

	}
	
	public void fieldDataStringAry( String field, String resultAry[], String where) {
		
		try {
			rs = st.executeQuery("Select * from " + table + " where " + where);
			
			for(int i = 0; rs.next(); i++)
				resultAry[i] = rs.getString(field);

		}catch(Exception e) {
			
			System.out.println("�����ͺ��̽� ���� �˻� ���� : "+ e.getMessage());
			
		}

	}
	
	public int getCardinality() {
		
		int i;
		
		try {
			rs = st.executeQuery("select * from "+table);
			for(i = 0; rs.next(); i++);
			return i;
		
		}catch(Exception e) { System.out.println("�����ͺ��̽� �˻� ���� : "+ e.getMessage()); }
		return 0;
	}
	
	//�׽�Ʈ �� 11/22
	public void fieldDataStringVector( String field, Vector<String> sv) {
		
		try {
			
			rs = st.executeQuery("Select * from " + table);
			while(rs.next())
			
				sv.add(rs.getString(field));

		}catch(Exception e) {
			
			System.out.println("�����ͺ��̽� �˻� ���� : "+ e.getMessage());
			
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
			
			System.out.println("�����ͺ��̽� �˻� ���� : "+ e.getMessage());
			
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
			
			System.out.println("�����ͺ��̽� �˻� ���� : "+ e.getMessage());
			
		}

	}
	
	public void fieldDataStringVector( String field, Vector<String> sv, String where) {
		
		try {
			rs = st.executeQuery("Select * from " + table + " where " + where);
			
			while(rs.next())
				sv.add(rs.getString(field));

		}catch(Exception e) {
			
			System.out.println("�����ͺ��̽� ���� �˻� ���� : "+ e.getMessage());
			
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
			
			//�Ӽ�
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
		
		//�Ӽ�
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
			
			//�Ӽ�
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
			
			//SQL��
		
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
				
				//SQL��
			
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
			
			System.out.println("�����ͺ��̽� ���� �˻� ���� : "+ e.getMessage());
			
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

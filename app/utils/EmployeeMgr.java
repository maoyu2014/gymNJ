package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.City;
import models.Employee;
import models.Store;
import models.StoreCity;


public class EmployeeMgr {
	
	private EmployeeMgr() {}
	
	private static EmployeeMgr em = null;
	static {
		if (em == null) {
			em = new EmployeeMgr();
		}
	}
	
	public static EmployeeMgr getInstance() {
		return em;
	}
	
	public void save(Employee e) {
		Connection conn = DB.getConn();
		String sql = "insert into employee values (null, ?,?,?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, e.username);
			pstmt.setString(2, e.password);
			pstmt.setString(3, e.name);
			pstmt.setString(4, e.headimage);
			pstmt.setInt(5, e.sex);
			pstmt.setString(6, e.phone);
			pstmt.setInt(7, e.ismanager);
			pstmt.setInt(8, e.isfinance);
			pstmt.setInt(9, e.iscoach);
			pstmt.setInt(10, e.domember);
			pstmt.setInt(11, e.doappointment);
			pstmt.setInt(12, e.docourse);
			pstmt.setInt(13, e.doplan);
			pstmt.setInt(14, e.domarkte);
			pstmt.setInt(15, e.dofinance);
			pstmt.setInt(16, e.doemployee);
			pstmt.setInt(17, e.dostore);
			pstmt.setInt(18, e.dostatistics);
			pstmt.setInt(19, e.storeid);
			pstmt.setString(20, e.introduce);
			pstmt.setString(21, e.openid);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public List<Employee> getAllEmployee() {
		List<Employee> list = new ArrayList<Employee>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from employee";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String headimage = rs.getString("headimage");
				int sex = rs.getInt("sex");			
				String phone = rs.getString("phone");
				
				int ismanager = rs.getInt("ismanager");
				int isfinance = rs.getInt("isfinance");
				int iscoach = rs.getInt("iscoach");
				
				int domember = rs.getInt("domember");
				int doappointment = rs.getInt("doappointment");
				int docourse = rs.getInt("docourse");
				int doplan = rs.getInt("doplan");
				int domarkte = rs.getInt("domarkte");
				int dofinance = rs.getInt("dofinance");
				int doemployee = rs.getInt("doemployee");
				int dostore = rs.getInt("dostore");
				int dostatistics = rs.getInt("dostatistics");
				
				int storeid = rs.getInt("storeid");
				String introduce = rs.getString("introduce");
				String openid = rs.getString("openid");
				Employee e = new Employee(id,username, password, name, headimage, sex, phone, ismanager, isfinance, iscoach, domember, doappointment, docourse, doplan, domarkte, dofinance, doemployee, dostore, dostatistics, storeid, introduce, openid);
				list.add(e);
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return list;
	}
	
	public int getNewestEmployeeID() {
		int ans=-1;
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select id from employee order by id desc";
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				ans = id;
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return ans;
	}
	
	public boolean deleteEmployee(int id) {
		boolean flag = false;
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		String sql = "delete from employee where id = " + id;
		try {
			stmt.executeUpdate(sql);
			flag=true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(stmt);
			DB.close(conn);
		}
		return flag;
	}
	
	public Employee findEmployeeById(int employeeid) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		Employee e = null;
		try {
			//加空格啊加空格，sql一定记得各种加空格
			String sql = "select * from employee where id = " + employeeid;
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String headimage = rs.getString("headimage");
				int sex = rs.getInt("sex");			
				String phone = rs.getString("phone");
				
				int ismanager = rs.getInt("ismanager");
				int isfinance = rs.getInt("isfinance");
				int iscoach = rs.getInt("iscoach");
				
				int domember = rs.getInt("domember");
				int doappointment = rs.getInt("doappointment");
				int docourse = rs.getInt("docourse");
				int doplan = rs.getInt("doplan");
				int domarkte = rs.getInt("domarkte");
				int dofinance = rs.getInt("dofinance");
				int doemployee = rs.getInt("doemployee");
				int dostore = rs.getInt("dostore");
				int dostatistics = rs.getInt("dostatistics");
				
				int storeid = rs.getInt("storeid");
				String introduce = rs.getString("introduce");
				String openid = rs.getString("openid");
				e = new Employee(id,username, password, name, headimage, sex, phone, ismanager, isfinance, iscoach, domember, doappointment, docourse, doplan, domarkte, dofinance, doemployee, dostore, dostatistics, storeid, introduce, openid);
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return e;
	}
	
	public void update(Employee e) {
		Connection conn = DB.getConn();
		String sql = "update employee set username=?, password=?, name = ?, headimage = ?, sex = ?, phone = ?, ismanager = ?, isfinance = ?, iscoach = ?, domember = ?, doappointment = ?, docourse = ?, doplan = ?, domarkte = ?, dofinance = ?, doemployee = ?, dostore = ?, dostatistics = ?, storeid = ?, introduce = ? where id = " +e.id;
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, e.username);
			pstmt.setString(2, e.password);
			pstmt.setString(3, e.name);
			pstmt.setString(4, e.headimage);
			pstmt.setInt(5, e.sex);
			pstmt.setString(6, e.phone);
			pstmt.setInt(7, e.ismanager);
			pstmt.setInt(8, e.isfinance);
			pstmt.setInt(9, e.iscoach);
			pstmt.setInt(10, e.domember);
			pstmt.setInt(11, e.doappointment);
			pstmt.setInt(12, e.docourse);
			pstmt.setInt(13, e.doplan);
			pstmt.setInt(14, e.domarkte);
			pstmt.setInt(15, e.dofinance);
			pstmt.setInt(16, e.doemployee);
			pstmt.setInt(17, e.dostore);
			pstmt.setInt(18, e.dostatistics);
			pstmt.setInt(19, e.storeid);
			pstmt.setString(20, e.introduce);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public boolean hasUser(String username) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from employee where username = '" + username + "'";
			rs = DB.executeQuery(stmt, sql);
			if (rs==null) {
				return false;
			}
			if (rs.next()) {
				return true;
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return false;
	}
	
	public Employee findEmployeeByUsername(String ausername) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		Employee e = null;
		try {
			//加空格啊加空格，sql一定记得各种加空格
			String sql = "select * from employee where username = '" + ausername + "'";
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String headimage = rs.getString("headimage");
				int sex = rs.getInt("sex");			
				String phone = rs.getString("phone");
				
				int ismanager = rs.getInt("ismanager");
				int isfinance = rs.getInt("isfinance");
				int iscoach = rs.getInt("iscoach");
				
				int domember = rs.getInt("domember");
				int doappointment = rs.getInt("doappointment");
				int docourse = rs.getInt("docourse");
				int doplan = rs.getInt("doplan");
				int domarkte = rs.getInt("domarkte");
				int dofinance = rs.getInt("dofinance");
				int doemployee = rs.getInt("doemployee");
				int dostore = rs.getInt("dostore");
				int dostatistics = rs.getInt("dostatistics");
				
				int storeid = rs.getInt("storeid");
				String introduce = rs.getString("introduce");
				String openid = rs.getString("openid");
				e = new Employee(id,username, password, name, headimage, sex, phone, ismanager, isfinance, iscoach, domember, doappointment, docourse, doplan, domarkte, dofinance, doemployee, dostore, dostatistics, storeid, introduce, openid);
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return e;
	}
	
	
	public List<Employee> getAllEmployeeByStoreID(int astoreid) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		List<Employee> list = new ArrayList<>();
		try {
			String sql = "select * from employee where storeid = " + astoreid;
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String headimage = rs.getString("headimage");
				int sex = rs.getInt("sex");			
				String phone = rs.getString("phone");
				
				int ismanager = rs.getInt("ismanager");
				int isfinance = rs.getInt("isfinance");
				int iscoach = rs.getInt("iscoach");
				
				int domember = rs.getInt("domember");
				int doappointment = rs.getInt("doappointment");
				int docourse = rs.getInt("docourse");
				int doplan = rs.getInt("doplan");
				int domarkte = rs.getInt("domarkte");
				int dofinance = rs.getInt("dofinance");
				int doemployee = rs.getInt("doemployee");
				int dostore = rs.getInt("dostore");
				int dostatistics = rs.getInt("dostatistics");
				
				int storeid = rs.getInt("storeid");
				String introduce = rs.getString("introduce");
				String openid = rs.getString("openid");
				Employee e = new Employee(id,username, password, name, headimage, sex, phone, ismanager, isfinance, iscoach, domember, doappointment, docourse, doplan, domarkte, dofinance, doemployee, dostore, dostatistics, storeid, introduce, openid);
				list.add(e);
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return list;
	}
	
	public List<Employee> searchEmployee(int astoreid, int classtype, String employeename) {
		List<Employee> list = new ArrayList<Employee>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from employee ";
			boolean flag = false;
			if (astoreid!=0) {
				if (!flag) {
					sql += " where storeid = " + astoreid;
					flag = true;
				} else {
					sql += " and storeid = " + astoreid;
				}
			}
			if (classtype==1) {
				if (!flag) {
					sql+= " where ismanager = 1";
					flag = true;
				} else {
					sql+= " and ismanager = 1";
				}
			} else if (classtype==2) {
				if (!flag) {
					sql+= " where isfinance = 1";
					flag = true;
				} else {
					sql+= " and isfinance = 1";
				}
			} else if (classtype==3) {
				if (!flag) {
					sql+= " where iscoach = 1";
					flag = true;
				} else {
					sql+= " and iscoach = 1";
				}
			}
			if (employeename!=null && employeename.length()>0) {
				if (!flag) {
					sql+=" where name like '%" + employeename + "%'";
					flag = true;
				} else {
					sql+=" and name like '%" + employeename + "%'";
				}
			}
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String headimage = rs.getString("headimage");
				int sex = rs.getInt("sex");			
				String phone = rs.getString("phone");
				
				int ismanager = rs.getInt("ismanager");
				int isfinance = rs.getInt("isfinance");
				int iscoach = rs.getInt("iscoach");
				
				int domember = rs.getInt("domember");
				int doappointment = rs.getInt("doappointment");
				int docourse = rs.getInt("docourse");
				int doplan = rs.getInt("doplan");
				int domarkte = rs.getInt("domarkte");
				int dofinance = rs.getInt("dofinance");
				int doemployee = rs.getInt("doemployee");
				int dostore = rs.getInt("dostore");
				int dostatistics = rs.getInt("dostatistics");
				
				int storeid = rs.getInt("storeid");
				String introduce = rs.getString("introduce");
				String openid = rs.getString("openid");
				Employee e = new Employee(id,username, password, name, headimage, sex, phone, ismanager, isfinance, iscoach, domember, doappointment, docourse, doplan, domarkte, dofinance, doemployee, dostore, dostatistics, storeid, introduce, openid);
				list.add(e);
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return list;
	}
	
	
	
}

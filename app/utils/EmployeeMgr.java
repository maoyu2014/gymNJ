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
		String sql = "insert into employee values (null, ?,?,?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?)";
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
				Employee e = new Employee(id,username, password, name, headimage, sex, phone, ismanager, isfinance, iscoach, domember, doappointment, docourse, doplan, domarkte, dofinance, doemployee, dostore, dostatistics, storeid, introduce);
				list.add(e);
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(conn);
			DB.close(stmt);
			DB.close(rs);
		}
		return list;
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
				e = new Employee(id,username, password, name, headimage, sex, phone, ismanager, isfinance, iscoach, domember, doappointment, docourse, doplan, domarkte, dofinance, doemployee, dostore, dostatistics, storeid, introduce);
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(conn);
			DB.close(stmt);
			DB.close(rs);
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
				e = new Employee(id,username, password, name, headimage, sex, phone, ismanager, isfinance, iscoach, domember, doappointment, docourse, doplan, domarkte, dofinance, doemployee, dostore, dostatistics, storeid, introduce);
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(conn);
			DB.close(stmt);
			DB.close(rs);
		}
		return e;
	}
	
	/*
	
	
	public boolean hasOtherMember(String pengid, int id) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		String sql = "select * from member where pengid = '" + pengid + "'";
		rs = DB.executeQuery(stmt, sql);
		try {
			if (rs.next()) {
				int tid = rs.getInt("id");
				if (tid != id)
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
	
	
	
	
	
	
	public List<Member> getMembers(int userid) {
		List<Member> list = new ArrayList<Member>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from member where associationid=" + userid;
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				Member m = new Member();
				m.setId(rs.getInt("id"));
				m.setAssociationid(rs.getInt("associationid"));
				m.setPengid(rs.getString("pengid"));
				m.setName(rs.getString("name"));
				m.setProvince(rs.getString("province"));
				m.setCity(rs.getString("city"));
				m.setLongitude(rs.getString("longitude"));
				m.setLatitude(rs.getString("latitude"));
				m.setPhone(rs.getString("phone"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn);
			DB.close(stmt);
			DB.close(rs);
		}
		return list;
	}
	
	
	
	
	
	
	public Member loadById(int id) {
		Member m = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DB.getConn();
			stmt = DB.getStmt(conn);
			String sql = "select * from member where id=" + id;
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				m = new Member();
				m.setId(rs.getInt("id"));
				m.setAssociationid(rs.getInt("associationid"));
				m.setPengid(rs.getString("pengid"));
				m.setName(rs.getString("name"));
				m.setProvince(rs.getString("province"));
				m.setCity(rs.getString("city"));
				m.setLongitude(rs.getString("longitude"));
				m.setLatitude(rs.getString("latitude"));
				m.setPhone(rs.getString("phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return m;
	}
	*/
	
}

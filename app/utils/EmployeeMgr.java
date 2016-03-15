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
		String sql = "insert into employee values (null, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, e.name);
			pstmt.setString(2, e.headimage);
			pstmt.setInt(3, e.sex);
			pstmt.setString(4, e.phone);
			pstmt.setInt(5, e.ismanager);
			pstmt.setInt(6, e.isfinance);
			pstmt.setInt(7, e.iscoach);
			pstmt.setInt(8, e.domember);
			pstmt.setInt(9, e.doappointment);
			pstmt.setInt(10, e.docourse);
			pstmt.setInt(11, e.doplan);
			pstmt.setInt(12, e.domarkte);
			pstmt.setInt(13, e.dofinance);
			pstmt.setInt(14, e.doemployee);
			pstmt.setInt(15, e.dostore);
			pstmt.setInt(16, e.dostatistics);
			pstmt.setInt(17, e.storeid);
			pstmt.setString(18, e.storename);
			pstmt.setString(19, e.introduce);
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
				String storename = rs.getString("storename");
				String introduce = rs.getString("introduce");
				Employee e = new Employee(id, name, headimage, sex, phone, ismanager, isfinance, iscoach, domember, doappointment, docourse, doplan, domarkte, dofinance, doemployee, dostore, dostatistics, storeid, storename, introduce);
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
	/*
	public StoreCity findStoreById(int storeid) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		StoreCity sc = null;
		try {
			//加空格啊加空格，sql一定记得各种加空格
			String sql = "select store.id as id, cityid, store.name as name, city.name as cityname, address, area, photo, manager, phone from store inner join city on store.cityid = city.id where store.id = " + storeid;
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				int cityid = rs.getInt("cityid");
				String name = rs.getString("name");
				String cityname = rs.getString("cityname");
				String address = rs.getString("address");
				double area = rs.getDouble("area");
				String photo = rs.getString("photo");
				String manager = rs.getString("manager");
				String phone = rs.getString("phone");
				sc = new StoreCity(id, cityid, name, cityname, address, area, photo, manager, phone);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn);
			DB.close(stmt);
			DB.close(rs);
		}
		return sc;
	}
	
	
	
	public boolean deleteStore(int id) {
		boolean flag = false;
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		String sql = "delete from store where id = " + id;
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
	
	public void update(Store s) {
		Connection conn = DB.getConn();
		String sql = "update store set cityid = ?, name = ?, address = ?, area = ?, photo = ?, manager = ?, phone = ? where id = " +s.id;
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setInt(1, s.cityid);
			pstmt.setString(2, s.name);
			pstmt.setString(3, s.address);
			pstmt.setDouble(4, s.area);
			pstmt.setString(5, s.photo);
			pstmt.setString(6, s.manager);
			pstmt.setString(7, s.phone);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	
	public boolean hasMember(String pengid) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		String sql = "select * from member where pengid = '" + pengid + "'";
		rs = DB.executeQuery(stmt, sql);
		try {
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

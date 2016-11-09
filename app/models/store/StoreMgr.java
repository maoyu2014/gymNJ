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
import models.Store;
import models.StoreCity;


public class StoreMgr {
	
	private StoreMgr() {}
	
	private static StoreMgr sm = null;
	static {
		if (sm == null) {
			sm = new StoreMgr();
		}
	}
	
	public static StoreMgr getInstance() {
		return sm;
	}
	
	public void save(Store s) {
		Connection conn = DB.getConn();
		String sql = "insert into store values (null, ?, ?, ?, ?, ?, ?, ?)";
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
	
	public List<StoreCity> getAllStore() {
		List<StoreCity> list = new ArrayList<StoreCity>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select store.id as id, cityid, store.name as name, city.name as cityname, address, area, photo, manager, phone from store inner join city on store.cityid = city.id";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				int cityid = rs.getInt("cityid");
				String name = rs.getString("name");
				String cityname = rs.getString("cityname");
				String address = rs.getString("address");;
				double area = rs.getDouble("area");
				String photo = rs.getString("photo");
				String manager = rs.getString("manager");
				String phone = rs.getString("phone");	
				StoreCity m  = new StoreCity(id, cityid, name, cityname, address, area, photo, manager, phone);	
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
	
	
	public List<StoreCity> searchStore(int acityid, String storename) {
		List<StoreCity> list = new ArrayList<StoreCity>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select store.id as id, cityid, store.name as name, city.name as cityname, address, area, photo, manager, phone from store inner join city on store.cityid = city.id ";
			boolean flag = false;
			if (acityid!=0) {
				if (!flag) {
					sql += " where cityid = " + acityid;
					flag = true;
				} else {
					sql += " and cityid = " + acityid;
				}
			}
			if (storename!=null && storename.length()>0) {
				if (!flag) {
					sql+= " where store.name like '%" + storename + "%'";
					flag = true;
				} else {
					sql+= " and store.name like '%" + storename + "%'";
				}
			}
//			System.out.println(sql);
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				int cityid = rs.getInt("cityid");
				String name = rs.getString("name");
				String cityname = rs.getString("cityname");
				String address = rs.getString("address");;
				double area = rs.getDouble("area");
				String photo = rs.getString("photo");
				String manager = rs.getString("manager");
				String phone = rs.getString("phone");	
				StoreCity m  = new StoreCity(id, cityid, name, cityname, address, area, photo, manager, phone);	
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
	/*
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

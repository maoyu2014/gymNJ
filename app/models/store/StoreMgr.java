package models.store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utils.common.DB;
import models.useless.City;
import models.useless.StoreCity;


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
	
	public Store findStoreById(int storeid) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		Store sc = null;
		try {
			//加空格啊加空格，sql一定记得各种加空格
			String sql = "select * from store where store.id = " + storeid;
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				int cityid = rs.getInt("cityid");
				String name = rs.getString("name");
				String address = rs.getString("address");
				double area = rs.getDouble("area");
				String photo = rs.getString("photo");
				String manager = rs.getString("manager");
				String phone = rs.getString("phone");
				sc = new Store(id, cityid, name, address, area, photo, manager, phone);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return sc;
	}
	
	public List<Store> getAllStore() {
		List<Store> list = new ArrayList<>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from store";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				int cityid = rs.getInt("cityid");
				String name = rs.getString("name");
				String address = rs.getString("address");;
				double area = rs.getDouble("area");
				String photo = rs.getString("photo");
				String manager = rs.getString("manager");
				String phone = rs.getString("phone");	
				Store m  = new Store(id, cityid, name, address, area, photo, manager, phone);	
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
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
	
	
}

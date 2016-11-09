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


public class ClassroomMgr {
	
	private ClassroomMgr() {}
	
	private static ClassroomMgr cm = null;
	static {
		if (cm == null) {
			cm = new ClassroomMgr();
		}
	}
	
	public static ClassroomMgr getInstance() {
		return cm;
	}
	
	public void save(Classroom s) {
		Connection conn = DB.getConn();
		String sql = "insert into classroom values (null, ?, ?, ?, ?)";
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, s.name);
			pstmt.setInt(2, s.holdnumber);
			pstmt.setString(3, s.usage);
			pstmt.setInt(4, s.storeid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public List<Classroom> getAllClassroomByStoreId(int storeid) {
		List<Classroom> list = new ArrayList<Classroom>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from classroom where storeid = " + storeid;
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int holdnumber = rs.getInt("holdnumber");
				String usage = rs.getString("usage");
				Classroom c = new Classroom(id, name, holdnumber, usage, storeid);
				list.add(c);
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
	
	public boolean deleteClassroom(int id) {
		boolean flag = false;
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		String sql = "delete from classroom where id = " + id;
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
	
	public Classroom findClassroomById(int classroomid) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		Classroom cr = null;
		try {
			//加空格啊加空格，sql一定记得各种加空格
			String sql = "select id, name, holdnumber, `usage`, storeid from classroom where id = " + classroomid;
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int holdnumber = rs.getInt("holdnumber");
				String usage = rs.getString("usage");
				int storeid = rs.getInt("storeid");
				cr = new Classroom(id, name, holdnumber, usage, storeid);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return cr;
	}
	
	
	public void update(Classroom cr) {
		Connection conn = DB.getConn();
		String sql = "update classroom set name = ?, holdnumber = ?, `usage` = ? where id = " +cr.id;
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, cr.name);
			pstmt.setInt(2, cr.holdnumber);
			pstmt.setString(3, cr.usage);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	
}	
	
	
	

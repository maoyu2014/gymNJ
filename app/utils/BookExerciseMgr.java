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

import models.Announcement;
import models.BookExercise;
import models.City;
import models.Employee;
import models.Member;
import models.Store;
import models.StoreCity;


public class BookExerciseMgr {
	
	private BookExerciseMgr() {}
	
	private static BookExerciseMgr em = null;
	static {
		if (em == null) {
			em = new BookExerciseMgr();
		}
	}
	
	public static BookExerciseMgr getInstance() {
		return em;
	}
	
//	public void save(Member e) {
//		Connection conn = DB.getConn();
//		String sql = "insert into Member values (null, ?, ?, ?, ?, ?, ?)";
//		PreparedStatement pstmt = DB.getPstmt(conn, sql);
//		try {
//			pstmt.setString(1, e.name);
//			pstmt.setInt(2, e.storeid);
//			pstmt.setString(3, e.starttime);
//			pstmt.setString(4, e.endtime);
//			pstmt.setString(5, e.content);
//			pstmt.setInt(6, e.employeeid);
//			pstmt.executeUpdate();
//		} catch (SQLException eee) {
//			eee.printStackTrace();
//		} finally {
//			DB.close(pstmt);
//			DB.close(conn);
//		}
//	}
	
	public List<BookExercise> getAllBookExercise() {
		List<BookExercise> list = new ArrayList<BookExercise>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from BookExercise";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				int memberid = rs.getInt("memberid");
				int type = rs.getInt("type");
				int exerciseid = rs.getInt("exerciseid");
				String booktime = rs.getString("booktime");
				BookExercise an = new BookExercise(id, memberid, type, exerciseid, booktime);
				list.add(an);
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
	
	public List<BookExercise> getAllActiveBookExercise() {
		List<BookExercise> list = new ArrayList<BookExercise>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from BookExercise where status = 1";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				int memberid = rs.getInt("memberid");
				int type = rs.getInt("type");
				int exerciseid = rs.getInt("exerciseid");
				String booktime = rs.getString("booktime");
				BookExercise an = new BookExercise(id, memberid, type, exerciseid, booktime);
				list.add(an);
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
	
	public boolean deleteBookExercise(int aid) {
		boolean flag = false;
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		String sql = "delete from BookExercise where id = " + aid;
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
	
	public BookExercise findBookExerciseById(int aid) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		BookExercise an = null;
		try {
			String sql = "select * from BookExercise where id = " + aid;
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				int memberid = rs.getInt("memberid");
				int type = rs.getInt("type");
				int exerciseid = rs.getInt("exerciseid");
				String booktime = rs.getString("booktime");
				an = new BookExercise(id, memberid, type, exerciseid, booktime);
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(conn);
			DB.close(stmt);
			DB.close(rs);
		}
		return an;
	}
	
	public List<BookExercise> findBookExerciseByMemberId(int mid) {
		List<BookExercise> list = new ArrayList<BookExercise>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		BookExercise an = null;
		try {
			String sql = "select * from BookExercise where memberid = " + mid;
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				int memberid = rs.getInt("memberid");
				int type = rs.getInt("type");
				int exerciseid = rs.getInt("exerciseid");
				String booktime = rs.getString("booktime");
				an = new BookExercise(id, memberid, type, exerciseid, booktime);
				list.add(an);
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
	
//	public void update(Member e) {
//		Connection conn = DB.getConn();
//		String sql = "update Member set name = ?, storeid = ?, starttime = ?, endtime = ?, content = ?, employeeid = ?  where id = " +e.id;
//		PreparedStatement pstmt = DB.getPstmt(conn, sql);
//		try {
//			pstmt.setString(1, e.name);
//			pstmt.setInt(2, e.storeid);
//			pstmt.setString(3, e.starttime);
//			pstmt.setString(4, e.endtime);
//			pstmt.setString(5, e.content);
//			pstmt.setInt(6, e.employeeid);
//			pstmt.executeUpdate();
//		} catch (SQLException eee) {
//			eee.printStackTrace();
//		} finally {
//			DB.close(pstmt);
//			DB.close(conn);
//		}
//	}
	
	
	
	
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

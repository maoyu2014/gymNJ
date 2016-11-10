package models.book;

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
import models.employee.Employee;
import models.member.Member;
import models.store.Store;
import models.useless.Announcement;
import models.useless.City;
import models.useless.StoreCity;


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
	
	public List<BookExercise> getMonthActiveBookTeamExerciseSchedule(String currentmonth) {
		List<BookExercise> list = new ArrayList<BookExercise>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select b.id as id, m.name as membername, b.type as type, s.name as storename, t.name as exercisename, b.booktime as booktime from BookExercise b, member m, teamexerciseschedule ts, store s, teamexercise t where b.booktime >= '" + currentmonth + "' and b.status = 1 and b.type = 0 and b.memberid = m.id and b.exerciseid = ts.id and ts.storeid = s.id and ts.teamexerciseid = t.id ";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String membername = rs.getString("membername");
				int type = rs.getInt("type");
				String storename = rs.getString("storename");
				String exercisename = rs.getString("exercisename");
				String booktime = rs.getString("booktime");
				BookExercise an = new BookExercise(id, membername, type, exercisename, booktime);
				an.storename = storename;
				list.add(an);
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
	
	/*
	public List<BookExercise> getAllActivePrivateBookExercise() {
		List<BookExercise> list = new ArrayList<BookExercise>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select b.id as id, m.name as membername, b.type as type, p.name as exercisename, b.booktime as booktime from BookExercise b, member m, privateexercise p where b.status = 1 and b.type = 1 and b.memberid = m.id and b.exerciseid = p.id ";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String membername = rs.getString("membername");
				int type = rs.getInt("type");
				String exercisename = rs.getString("exercisename");
				String booktime = rs.getString("booktime");
				BookExercise an = new BookExercise(id, membername, type, exercisename, booktime);
				list.add(an);
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
	*/
	
	public List<BookExercise> searchActiveBookExercise(int storeid, String yearmonth) {
		List<BookExercise> list = new ArrayList<BookExercise>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select b.id as id, m.name as membername, b.type as type, s.name as storename, t.name as exercisename, b.booktime as booktime from BookExercise b, member m, teamexerciseschedule ts, store s, teamexercise t where b.booktime >= '" + yearmonth + "' and b.status = 1 and b.type = 0 and b.memberid = m.id and b.exerciseid = ts.id and ts.storeid = s.id and ts.teamexerciseid = t.id ";
			if (storeid!=0) {
				sql += " and ts.storeid = " + storeid;
			}
			if (yearmonth!=null && yearmonth.length()>0) {
				String endtime = yearmonth + "-32";
				sql += " and b.booktime <= '" + endtime + "'";
			}
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String membername = rs.getString("membername");
				int type = rs.getInt("type");
				String storename = rs.getString("storename");
				String exercisename = rs.getString("exercisename");
				String booktime = rs.getString("booktime");
				BookExercise an = new BookExercise(id, membername, type, exercisename, booktime);
				an.storename = storename;
				list.add(an);
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
	
	public List<BookExercise> findActiveBookExerciseByMemberId(int mid) {
		List<BookExercise> list = new ArrayList<BookExercise>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		BookExercise an = null;
		try {
			String sql = "select * from BookExercise where status = 1 and memberid = " + mid;
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
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return list;
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

	public List<Member> getTeamExerciseScheduleBookMember(int aid) {
		List<Member> list = new ArrayList<>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		Member an = null;
		try {
			String sql = "select m.id as id, m.name as name, m.phone as phone from BookExercise b, member m where b.memberid=m.id and b.status=1 and b.exerciseid = " + aid;
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				an = new Member();
				an.id = id; 
				an.name = name; 
				an.phone = phone;
				list.add(an);
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

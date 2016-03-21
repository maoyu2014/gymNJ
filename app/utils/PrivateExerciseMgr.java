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
import models.City;
import models.Employee;
import models.FitnessPlan;
import models.PrivateExercise;
import models.Store;
import models.StoreCity;
import models.TeamExercise;


public class PrivateExerciseMgr {
	
	private PrivateExerciseMgr() {}
	
	private static PrivateExerciseMgr em = null;
	
	public static PrivateExerciseMgr getInstance() {
		if (em == null) {
			em = new PrivateExerciseMgr();
		}
		return em;
	}
	
	public void save(PrivateExercise e) {
		Connection conn = DB.getConn();
		String sql = "insert into PrivateExercise values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, e.name);
			pstmt.setString(2, e.image);
			pstmt.setInt(3, e.weeks);
			pstmt.setInt(4, e.period);
			pstmt.setInt(5, e.num);
			pstmt.setInt(6, e.oknum);
			pstmt.setDouble(7, e.price);
			pstmt.setInt(8, e.storeid);
			pstmt.setInt(9, e.classroomid);
			pstmt.setInt(10, e.employeeid);
			pstmt.setString(11, e.classbegintime);
			pstmt.setString(12, e.classendtime);
			pstmt.setInt(13, e.exerciseweeknum);
			pstmt.setString(14, e.exercisebegintime);
			pstmt.setString(15, e.exerciseendtime);
			pstmt.setString(16, e.signbegintime);
			pstmt.setString(17, e.signendtime);
			pstmt.setString(18, e.courseintroduce);
			pstmt.setString(19, e.courseplan);
			pstmt.setString(20, e.notice);
			pstmt.setString(21, e.fitstep);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public List<PrivateExercise> getAllPrivateExercise() {
		List<PrivateExercise> list = new ArrayList<PrivateExercise>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from PrivateExercise";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String image = rs.getString("image");
				int weeks = rs.getInt("weeks");
				int period = rs.getInt("period");
				int num = rs.getInt("num");
				int oknum = rs.getInt("oknum");
				double price = rs.getDouble("price");
				int storeid = rs.getInt("storeid");
				int classroomid = rs.getInt("classroomid");
				int employeeid = rs.getInt("employeeid");
				String classbegintime = rs.getString("classbegintime");		//开课时间
				String classendtime = rs.getString("classendtime");
				int exerciseweeknum = rs.getInt("exerciseweeknum");				//训练时间是礼拜几
				String exercisebegintime = rs.getString("exercisebegintime");	//训练时间是几时几分
				String exerciseendtime = rs.getString("exerciseendtime");
				String signbegintime = rs.getString("signbegintime");		//报名时间
				String signendtime = rs.getString("signendtime");
				String courseintroduce = rs.getString("courseintroduce");	//课程介绍
				String courseplan = rs.getString("courseplan");	//课程安排，中间用等于号分割
				String notice = rs.getString("notice");		//注意事项
				String fitstep = rs.getString("fitstep");		//健身步骤，中间用等于号分割
				PrivateExercise an = new PrivateExercise(id, name, image, weeks, period, num, oknum, price, storeid, classroomid, employeeid, classbegintime, classendtime, exerciseweeknum, exercisebegintime, exerciseendtime, signbegintime, signendtime, courseintroduce, courseplan, notice, fitstep);
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
	
	public boolean deletePrivateExercise(int id) {
		boolean flag = false;
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		String sql = "delete from PrivateExercise where id = " + id;
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
	
	public PrivateExercise findPrivateExerciseById(int aid) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		PrivateExercise an = null;
		try {
			//加空格啊加空格，sql一定记得各种加空格
			String sql = "select * from PrivateExercise where id = " + aid;
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String image = rs.getString("image");
				int weeks = rs.getInt("weeks");
				int period = rs.getInt("period");
				int num = rs.getInt("num");
				int oknum = rs.getInt("oknum");
				double price = rs.getDouble("price");
				int storeid = rs.getInt("storeid");
				int classroomid = rs.getInt("classroomid");
				int employeeid = rs.getInt("employeeid");
				String classbegintime = rs.getString("classbegintime");		//开课时间
				String classendtime = rs.getString("classendtime");
				int exerciseweeknum = rs.getInt("exerciseweeknum");				//训练时间是礼拜几
				String exercisebegintime = rs.getString("exercisebegintime");	//训练时间是几时几分
				String exerciseendtime = rs.getString("exerciseendtime");
				String signbegintime = rs.getString("signbegintime");		//报名时间
				String signendtime = rs.getString("signendtime");
				String courseintroduce = rs.getString("courseintroduce");	//课程介绍
				String courseplan = rs.getString("courseplan");	//课程安排，中间用等于号分割
				String notice = rs.getString("notice");		//注意事项
				String fitstep = rs.getString("fitstep");		//健身步骤，中间用等于号分割
				an = new PrivateExercise(id, name, image, weeks, period, num, oknum, price, storeid, classroomid, employeeid, classbegintime, classendtime, exerciseweeknum, exercisebegintime, exerciseendtime, signbegintime, signendtime, courseintroduce, courseplan, notice, fitstep);
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
	
	public void update(PrivateExercise e) {
		Connection conn = DB.getConn();
		String sql = "update PrivateExercise set name = ?, image = ?, weeks = ?, period = ?, num = ?, oknum = ?, price = ?,storeid = ?, classroomid = ?, employeeid = ?, classbegintime = ?, classendtime = ?, exerciseweeknum = ?, exercisebegintime = ?,exerciseendtime = ?, signbegintime = ?, signendtime = ?, courseintroduce = ?, courseplan = ?, notice = ?, fitstep = ?  where id = " +e.id;
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, e.name);
			pstmt.setString(2, e.image);
			pstmt.setInt(3, e.weeks);
			pstmt.setInt(4, e.period);
			pstmt.setInt(5, e.num);
			pstmt.setInt(6, e.oknum);
			pstmt.setDouble(7, e.price);
			pstmt.setInt(8, e.storeid);
			pstmt.setInt(9, e.classroomid);
			pstmt.setInt(10, e.employeeid);
			pstmt.setString(11, e.classbegintime);
			pstmt.setString(12, e.classendtime);
			pstmt.setInt(13, e.exerciseweeknum);
			pstmt.setString(14, e.exercisebegintime);
			pstmt.setString(15, e.exerciseendtime);
			pstmt.setString(16, e.signbegintime);
			pstmt.setString(17, e.signendtime);
			pstmt.setString(18, e.courseintroduce);
			pstmt.setString(19, e.courseplan);
			pstmt.setString(20, e.notice);
			pstmt.setString(21, e.fitstep);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
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

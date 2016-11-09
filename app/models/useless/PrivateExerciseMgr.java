package models.useless;

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
import models.store.Store;


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
		String sql = "insert into PrivateExercise values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, e.name);
			pstmt.setString(2, e.coursestyle);
			pstmt.setString(3, e.image);
			pstmt.setInt(4, e.weeks);
			pstmt.setInt(5, e.period);
			pstmt.setInt(6, e.num);
			pstmt.setInt(7, e.oknum);
			pstmt.setDouble(8, e.price);
			pstmt.setInt(9, e.storeid);
			pstmt.setInt(10, e.classroomid);
			pstmt.setInt(11, e.employeeid);
			pstmt.setString(12, e.classbegintime);
			pstmt.setString(13, e.classendtime);
			pstmt.setString(14, e.exerciseweeknum);
			pstmt.setString(15, e.exercisebegintime);
			pstmt.setString(16, e.exerciseendtime);
			pstmt.setString(17, e.signbegintime);
			pstmt.setString(18, e.signendtime);
			pstmt.setString(19, e.summary);
			pstmt.setString(20, e.courseintroduce);
			pstmt.setString(21, e.courseplan);
			pstmt.setString(22, e.notice);
			pstmt.setString(23, e.fitstep);
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
			String sql = "select p.id, p.name, p.coursestyle, p.weeks, p.period, p.num, p.oknum, p.price, s.name as storename, e.name as employeename, p.classbegintime, p.classendtime, p.exerciseweeknum, p.exercisebegintime, p.exerciseendtime, p.signbegintime, p.signendtime from PrivateExercise p, store s, employee e where p.storeid = s.id and p.employeeid = e.id";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name"); 
				String coursestyle = rs.getString("coursestyle");
				int weeks = rs.getInt("weeks");
				int period = rs.getInt("period");
				int num = rs.getInt("num");
				int oknum = rs.getInt("oknum");
				double price = rs.getDouble("price");
				String storename = rs.getString("storename");
				String employeename = rs.getString("employeename");
				String classbegintime = rs.getString("classbegintime");		//开课时间
				String classendtime = rs.getString("classendtime");
				String exerciseweeknum = rs.getString("exerciseweeknum");				//训练时间是礼拜几几几
				String exercisebegintime = rs.getString("exercisebegintime");	//训练时间是几时几分
				String exerciseendtime = rs.getString("exerciseendtime");
				String signbegintime = rs.getString("signbegintime");		//报名时间
				String signendtime = rs.getString("signendtime");
				PrivateExercise an = new PrivateExercise(id, name, coursestyle, weeks, period, num, oknum, price, storename, employeename, classbegintime, classendtime, exerciseweeknum, exercisebegintime, exerciseendtime, signbegintime, signendtime);
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
	
	public List<PrivateExercise> searchPrivateExercise(int astoreid, int aemployeeid, String privateexercisename) {
		List<PrivateExercise> list = new ArrayList<PrivateExercise>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select p.id, p.name, p.coursestyle, p.weeks, p.period, p.num, p.oknum, p.price, s.name as storename, e.name as employeename, p.classbegintime, p.classendtime, p.exerciseweeknum, p.exercisebegintime, p.exerciseendtime, p.signbegintime, p.signendtime from PrivateExercise p, store s, employee e where p.storeid=s.id and p.employeeid=e.id";
			if (astoreid!=0) {
				sql += " and p.storeid = " + astoreid;
			}
			if (aemployeeid!=0) {
				sql += " and p.employeeid = " + aemployeeid;
			}
			if (privateexercisename!=null && privateexercisename.length()>0) {
				sql+=" and p.name like '%" + privateexercisename + "%'";
			}
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name"); 
				String coursestyle = rs.getString("coursestyle");
				int weeks = rs.getInt("weeks");
				int period = rs.getInt("period");
				int num = rs.getInt("num");
				int oknum = rs.getInt("oknum");
				double price = rs.getDouble("price");
				String storename = rs.getString("storename");
				String employeename = rs.getString("employeename");
				String classbegintime = rs.getString("classbegintime");		//开课时间
				String classendtime = rs.getString("classendtime");
				String exerciseweeknum = rs.getString("exerciseweeknum");				//训练时间是礼拜几几几
				String exercisebegintime = rs.getString("exercisebegintime");	//训练时间是几时几分
				String exerciseendtime = rs.getString("exerciseendtime");
				String signbegintime = rs.getString("signbegintime");		//报名时间
				String signendtime = rs.getString("signendtime");
				PrivateExercise an = new PrivateExercise(id, name, coursestyle, weeks, period, num, oknum, price, storename, employeename, classbegintime, classendtime, exerciseweeknum, exercisebegintime, exerciseendtime, signbegintime, signendtime);
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
			String sql = "select * from PrivateExercise where id = " + aid;
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String coursestyle = rs.getString("coursestyle");
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
				String exerciseweeknum = rs.getString("exerciseweeknum");				//训练时间是礼拜几
				String exercisebegintime = rs.getString("exercisebegintime");	//训练时间是几时几分
				String exerciseendtime = rs.getString("exerciseendtime");
				String signbegintime = rs.getString("signbegintime");		//报名时间
				String signendtime = rs.getString("signendtime");
				String summary = rs.getString("summary");
				String courseintroduce = rs.getString("courseintroduce");	//课程介绍
				String courseplan = rs.getString("courseplan");	//课程安排，中间用等于号分割
				String notice = rs.getString("notice");		//注意事项
				String fitstep = rs.getString("fitstep");		//健身步骤，中间用等于号分割
				an = new PrivateExercise(id, name, coursestyle, image, weeks, period, num, oknum, price, storeid, classroomid, employeeid, classbegintime, classendtime, exerciseweeknum, exercisebegintime, exerciseendtime, signbegintime, signendtime, summary, courseintroduce, courseplan, notice, fitstep);
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return an;
	}
	
	public void update(PrivateExercise e) {
		Connection conn = DB.getConn();
		String sql = "update PrivateExercise set name = ?, image = ?, weeks = ?, period = ?, num = ?, oknum = ?, price = ?,storeid = ?, classroomid = ?, employeeid = ?, classbegintime = ?, classendtime = ?, exerciseweeknum = ?, exercisebegintime = ?,exerciseendtime = ?, signbegintime = ?, signendtime = ?, courseintroduce = ?, courseplan = ?, notice = ?, fitstep = ?, coursestyle = ?, summary = ?  where id = " +e.id;
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
			pstmt.setString(13, e.exerciseweeknum);
			pstmt.setString(14, e.exercisebegintime);
			pstmt.setString(15, e.exerciseendtime);
			pstmt.setString(16, e.signbegintime);
			pstmt.setString(17, e.signendtime);
			pstmt.setString(18, e.courseintroduce);
			pstmt.setString(19, e.courseplan);
			pstmt.setString(20, e.notice);
			pstmt.setString(21, e.fitstep);
			pstmt.setString(22, e.coursestyle);
			pstmt.setString(23, e.summary);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	
}

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
import models.Store;
import models.StoreCity;
import models.TeamExercise;


public class TeamExerciseMgr {
	
	private TeamExerciseMgr() {}
	
	private static TeamExerciseMgr em = null;
	
	public static TeamExerciseMgr getInstance() {
		if (em == null) {
			em = new TeamExerciseMgr();
		}
		return em;
	}
	
	public void save(TeamExercise e) {
		Connection conn = DB.getConn();
		String sql = "insert into TeamExercise values (null, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, e.name);
			pstmt.setString(2, e.image);
			pstmt.setDouble(3, e.usedtime);
			pstmt.setInt(4, e.strength);
			pstmt.setString(5, e.parts);
			pstmt.setString(6, e.introduce);
			pstmt.setString(7, e.notice);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public List<TeamExercise> getAllTeamExercise() {
		List<TeamExercise> list = new ArrayList<TeamExercise>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from teamexercise";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name =rs.getString("name");
				String image =rs.getString("image");
				double usedtime =rs.getDouble("usedtime");
				int strength  = rs.getInt("strength");
				String parts =rs.getString("parts");
				String introduce =rs.getString("introduce");
				String notice =rs.getString("notice");
				TeamExercise an = new TeamExercise(id, name, image, usedtime, strength, parts, introduce, notice);
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
	
	public boolean deleteTeamExercise(int id) {
		boolean flag = false;
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		String sql = "delete from teamexercise where id = " + id;
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
	
	public TeamExercise findTeamExerciseById(int aid) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		TeamExercise an = null;
		try {
			//加空格啊加空格，sql一定记得各种加空格
			String sql = "select * from teamexercise where id = " + aid;
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				String name =rs.getString("name");
				String image =rs.getString("image");
				double usedtime =rs.getDouble("usedtime");
				int strength  = rs.getInt("strength");
				String parts =rs.getString("parts");
				String introduce =rs.getString("introduce");
				String notice =rs.getString("notice");
				an = new TeamExercise(id, name, image, usedtime, strength, parts, introduce, notice);
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
	
	public void update(TeamExercise e) {
		Connection conn = DB.getConn();
		String sql = "update teamexercise set name = ?, image = ?, usedtime = ?, strength = ?, parts = ?, introduce = ?, notice = ?  where id = " +e.id;
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, e.name);
			pstmt.setString(2, e.image);
			pstmt.setDouble(3, e.usedtime);
			pstmt.setInt(4, e.strength);
			pstmt.setString(5, e.parts);
			pstmt.setString(6, e.introduce);
			pstmt.setString(7, e.notice);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	
}

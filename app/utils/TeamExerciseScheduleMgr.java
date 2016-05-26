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

import models.TeamExerciseSchedule;



public class TeamExerciseScheduleMgr {
	
	private TeamExerciseScheduleMgr() {}
	
	private static TeamExerciseScheduleMgr em = null;
	
	public static TeamExerciseScheduleMgr getInstance() {
		if (em == null) {
			em = new TeamExerciseScheduleMgr();
		}
		return em;
	}
	
	public void save(TeamExerciseSchedule e) {
		Connection conn = DB.getConn();
		String sql = "insert into TeamExerciseSchedule values (null, ?, ?, ?, ?, ?, ?, ?,?)";
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setInt(1, e.storeid);
			pstmt.setInt(2, e.classroomid);
			pstmt.setInt(3, e.employeeid);
			pstmt.setInt(4, e.teamexerciseid);
			pstmt.setInt(5, e.num);
			pstmt.setInt(6, e.oknum);
			pstmt.setString(7, e.begintime);
			pstmt.setString(8, e.endtime);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public List<TeamExerciseSchedule> getAllTeamExerciseSchedule() {
		List<TeamExerciseSchedule> list = new ArrayList<TeamExerciseSchedule>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from TeamExerciseSchedule order by begintime desc";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				int storeid = rs.getInt("storeid");
				int classroomid = rs.getInt("classroomid");
				int employeeid = rs.getInt("employeeid");
				int teamexerciseid = rs.getInt("teamexerciseid");
				
				int num = rs.getInt("num");
				int oknum = rs.getInt("oknum");
				
				String begintime = rs.getString("begintime");
				String endtime = rs.getString("endtime");
				TeamExerciseSchedule an = new TeamExerciseSchedule(id, storeid, classroomid, employeeid, teamexerciseid, num, oknum, begintime, endtime);
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
	
	public List<TeamExerciseSchedule> getAllTeamExerciseScheduleByYearMonth(String yearmonth) {
		List<TeamExerciseSchedule> list = new ArrayList<TeamExerciseSchedule>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select a.id as id, a.num as num, a.oknum as oknum, a.begintime as begintime, a.endtime as endtime, b.name as storename, c.name as classroomname, d.name as employeename, e.name as teamexercisename "
					+ "from TeamExerciseSchedule a, store b, classroom c, employee d, teamexercise e where "
					+ "a.storeid=b.id and a.classroomid=c.id and a.employeeid=d.id and a.teamexerciseid=e.id and begintime like '%" + yearmonth + "%' order by begintime desc";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String storename = rs.getString("storename");
				String classroomname = rs.getString("classroomname");
				String employeename = rs.getString("employeename");
				String teamexercisename = rs.getString("teamexercisename");
				
				int num = rs.getInt("num");
				int oknum = rs.getInt("oknum");
				
				String begintime = rs.getString("begintime");
				String endtime = rs.getString("endtime");
				TeamExerciseSchedule an = new TeamExerciseSchedule(id, storename, classroomname, employeename, teamexercisename, num, oknum, begintime, endtime);
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
	
	public List<TeamExerciseSchedule> searchTeamExerciseSchedule(int astoreid, int aemployeeid, String yearmonth) {
		List<TeamExerciseSchedule> list = new ArrayList<TeamExerciseSchedule>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select a.id as id, a.num as num, a.oknum as oknum, a.begintime as begintime, a.endtime as endtime, b.name as storename, c.name as classroomname, d.name as employeename, e.name as teamexercisename "
					+ "from TeamExerciseSchedule a, store b, classroom c, employee d, teamexercise e where "
					+ "a.storeid=b.id and a.classroomid=c.id and a.employeeid=d.id and a.teamexerciseid=e.id ";
			boolean flag = true;
			if (astoreid!=0) {
				if (!flag) {
					sql += " where a.storeid = " + astoreid;
				} else {
					sql += " and a.storeid = " + astoreid;
				}
			}
			if (aemployeeid!=0) {
				if (!flag) {
					sql += " where a.employeeid = " + aemployeeid;
				} else {
					sql += " and a.employeeid = " + aemployeeid;
				}
			}
			if (yearmonth!=null && yearmonth.length()>0) {
				if (!flag) {
					sql += " where a.begintime like '%" + yearmonth + "%'";
				} else {
					sql += " and a.begintime like '%" + yearmonth + "%'";
				}
			}
			sql += " order by a.begintime desc";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String storename = rs.getString("storename");
				String classroomname = rs.getString("classroomname");
				String employeename = rs.getString("employeename");
				String teamexercisename = rs.getString("teamexercisename");
				
				int num = rs.getInt("num");
				int oknum = rs.getInt("oknum");
				
				String begintime = rs.getString("begintime");
				String endtime = rs.getString("endtime");
				TeamExerciseSchedule an = new TeamExerciseSchedule(id, storename, classroomname, employeename, teamexercisename, num, oknum, begintime, endtime);
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
	
	public boolean deleteTeamExerciseSchedule(int id) {
		boolean flag = false;
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		String sql = "delete from TeamExerciseSchedule where id = " + id;
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
	
	public TeamExerciseSchedule findTeamExerciseScheduleById(int aid) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		TeamExerciseSchedule an = null;
		try {
			//加空格啊加空格，sql一定记得各种加空格
			String sql = "select * from TeamExerciseSchedule where id = " + aid;
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				int storeid = rs.getInt("storeid");
				int classroomid = rs.getInt("classroomid");
				int employeeid = rs.getInt("employeeid");
				int teamexerciseid = rs.getInt("teamexerciseid");
				
				int num = rs.getInt("num");
				int oknum = rs.getInt("oknum");
				
				String begintime = rs.getString("begintime");
				String endtime = rs.getString("endtime");
				an = new TeamExerciseSchedule(id, storeid, classroomid, employeeid, teamexerciseid, num, oknum, begintime, endtime);
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
	
	public void update(TeamExerciseSchedule e) {
		Connection conn = DB.getConn();
		String sql = "update TeamExerciseSchedule set storeid = ?, classroomid = ?, employeeid = ?, teamexerciseid = ?, num = ?, oknum = ?, begintime = ?, endtime = ?  where id = " +e.id;
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setInt(1, e.storeid);
			pstmt.setInt(2, e.classroomid);
			pstmt.setInt(3, e.employeeid);
			pstmt.setInt(4, e.teamexerciseid);
			pstmt.setInt(5, e.num);
			pstmt.setInt(6, e.oknum);
			pstmt.setString(7, e.begintime);
			pstmt.setString(8, e.endtime);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	
}

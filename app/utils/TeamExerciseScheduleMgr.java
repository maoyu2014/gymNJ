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
			DB.close(conn);
			DB.close(stmt);
			DB.close(rs);
		}
		return list;
	}
	
	public List<TeamExerciseSchedule> searchTeamExerciseSchedule(int astoreid, int aemployeeid) {
		List<TeamExerciseSchedule> list = new ArrayList<TeamExerciseSchedule>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from TeamExerciseSchedule";
			boolean flag = false;
			if (astoreid!=0) {
				if (!flag) {
					sql += " where storeid = " + astoreid;
					flag = true;
				} else {
					sql += " and storeid = " + astoreid;
				}
			}
			if (aemployeeid!=0) {
				if (!flag) {
					sql += " where employeeid = " + aemployeeid;
					flag = true;
				} else {
					sql += " and employeeid = " + aemployeeid;
				}
			}
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
			DB.close(conn);
			DB.close(stmt);
			DB.close(rs);
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
			DB.close(conn);
			DB.close(stmt);
			DB.close(rs);
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

package models.course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.common.DB;
import models.employee.Employee;
import models.member.Member;
import models.store.Store;
import models.useless.Announcement;
import models.useless.City;
import models.useless.StoreCity;


public class TeamExerciseScheduleResultMgr {
	
	private TeamExerciseScheduleResultMgr() {}
	
	private static TeamExerciseScheduleResultMgr em = null;
	static {
		if (em == null) {
			em = new TeamExerciseScheduleResultMgr();
		}
	}
	
	public static TeamExerciseScheduleResultMgr getInstance() {
		return em;
	}
	
	public void saveInfo(int teamexercisescheduleid, String yundong1, String yundong2, String yundong3, String yundong4,
			String yundongliang, String one2three, String four2six, String seven212 ) {
		Connection conn = DB.getConn();
		String sql = "insert into teamexercisescheduleresultinfo values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setInt(1, teamexercisescheduleid);
			pstmt.setString(2, yundong1);
			pstmt.setString(3, yundong2);
			pstmt.setString(4, yundong3);
			pstmt.setString(5, yundong4);
			pstmt.setString(6, yundongliang);
			pstmt.setString(7, one2three);
			pstmt.setString(8, four2six);
			pstmt.setString(9, seven212);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public void saveDetail(int teamexercisescheduleid,int memberid,String shijian1,String shijian2,
			String shijian3,String shijian4,String shijian5,String shijian6,String shijian7,String shijian8) {
		Connection conn = DB.getConn();
		String sql = "insert into teamexercisescheduleresultdetail values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setInt(1, teamexercisescheduleid);
			pstmt.setInt(2, memberid);
			pstmt.setString(3, shijian1);
			pstmt.setString(4, shijian2);
			pstmt.setString(5, shijian3);
			pstmt.setString(6, shijian4);
			pstmt.setString(7, shijian5);
			pstmt.setString(8, shijian6);
			pstmt.setString(9, shijian7);
			pstmt.setString(10, shijian8);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public boolean hasInfo(int teamexercisescheduleid) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		boolean flag = false;
		try {
			String sql = "select * from teamexercisescheduleresultinfo where teamexercisescheduleid = " + teamexercisescheduleid;
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				flag = true;
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return flag;
	}
	
	public TeamExerciseScheduleResultInfo getInfo(int teamexercisescheduleid) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		TeamExerciseScheduleResultInfo aa = null;
		try {
			String sql = "select * from teamexercisescheduleresultinfo where teamexercisescheduleid = " + teamexercisescheduleid;
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				String yundong1 = rs.getString("yundong1");
				String yundong2 = rs.getString("yundong2");
				String yundong3 = rs.getString("yundong3");
				String yundong4 = rs.getString("yundong4");
				String yundongliang = rs.getString("yundongliang");
				String one2three = rs.getString("one2three");
				String four2six = rs.getString("four2six");
				String seven212 = rs.getString("seven212");
				aa = new TeamExerciseScheduleResultInfo(teamexercisescheduleid, yundong1, yundong2, yundong3, yundong4, yundongliang, one2three, four2six, seven212);
				aa.id = id;
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return aa;
	}
	
	public boolean hasDetail(int teamexercisescheduleid, int memberid) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		boolean flag = false;
		try {
			String sql = "select * from teamexercisescheduleresultdetail where teamexercisescheduleid = " + teamexercisescheduleid + " and memberid = " + memberid;
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				flag = true;
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return flag;
	}
	
	public List<TeamExerciseScheduleResultDetail> getDetail(int teamexercisescheduleid) {
		List<TeamExerciseScheduleResultDetail> list = new ArrayList<>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from teamexercisescheduleresultdetail where teamexercisescheduleid = " + teamexercisescheduleid;
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				int memberid = rs.getInt("memberid");
				String shijian1 = rs.getString("shijian1");
				String shijian2 = rs.getString("shijian2");
				String shijian3 = rs.getString("shijian3");
				String shijian4 = rs.getString("shijian4");
				String shijian5 = rs.getString("shijian5");
				String shijian6 = rs.getString("shijian6");
				String shijian7 = rs.getString("shijian7");
				String shijian8 = rs.getString("shijian8");
				TeamExerciseScheduleResultDetail bb = new TeamExerciseScheduleResultDetail(teamexercisescheduleid, memberid, shijian1, shijian2, shijian3, shijian4, shijian5, shijian6, shijian7, shijian8);
				list.add(bb);
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

	public void updateInfo(int id, String yundong1, String yundong2,
			String yundong3, String yundong4, String yundongliang,
			String one2three, String four2six, String seven212) {
		Connection conn = DB.getConn();
		String sql = "update teamexercisescheduleresultinfo set yundong1 = ? , yundong2 = ? , yundong3 = ?, yundong4 = ?, yundongliang = ?, one2three = ?, four2six = ?, seven212 = ? where teamexercisescheduleid = " +id;
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, yundong1);
			pstmt.setString(2, yundong2);
			pstmt.setString(3, yundong3);
			pstmt.setString(4, yundong4);
			pstmt.setString(5, yundongliang);
			pstmt.setString(6, one2three);
			pstmt.setString(7, four2six);
			pstmt.setString(8, seven212);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}

	public void updateDetail(int id, int memberid, String shijian1,
			String shijian2, String shijian3, String shijian4, String shijian5,
			String shijian6, String shijian7, String shijian8) {
		Connection conn = DB.getConn();
		String sql = "update teamexercisescheduleresultdetail set shijian1 = ? , shijian2 = ? , shijian3 = ?, shijian4 = ?, shijian5 = ?, shijian6 = ?, shijian7 = ?, shijian8 = ? where teamexercisescheduleid = " +id + " and memberid = " + memberid;
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, shijian1);
			pstmt.setString(2, shijian2);
			pstmt.setString(3, shijian3);
			pstmt.setString(4, shijian4);
			pstmt.setString(5, shijian5);
			pstmt.setString(6, shijian6);
			pstmt.setString(7, shijian7);
			pstmt.setString(8, shijian8);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	/*
	
	public void updateDeaddate(int aid, int acardtype, String adeaddate) {
		Connection conn = DB.getConn();
		String sql = "update Member set cardtype = ? , deaddate = ?  where id = " +aid;
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setInt(1, acardtype);
			pstmt.setString(2, adeaddate);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public List<Member> getGoodRecommendedMember() {
		List<Member> list = new ArrayList<Member>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select id, openid, name, wechatname, phone, deaddate from member where phone in "
					+ "(select recommendedphone from Member group by recommendedphone having recommendedphone != '' and  count(recommendedphone) >= 1) "
					+ "order by id";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String openid = rs.getString("openid");			//微信唯一标识
				String name = rs.getString("name");
				String wechatname = rs.getString("wechatname");
				String phone = rs.getString("phone");
				String deaddate = rs.getString("deaddate");
				Member an = new Member(id, openid, name, wechatname, phone, deaddate);
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
	
	public Map<String, Integer> getPhoneVSNumber() {
		Map<String, Integer> map = new HashMap<>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select recommendedphone, count(recommendedphone) as numbers from Member group by recommendedphone having recommendedphone != '' and  count(recommendedphone) >= 1";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				String recommendedphone = rs.getString("recommendedphone");
				int numbers = rs.getInt("numbers");
				map.put(recommendedphone, numbers);
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return map;
	}
	
	public void updateMemberStatus(int memberid, String memberstatus) {
		Connection conn = DB.getConn();
		String sql = "update Member set memberstatus = ?  where id = " + memberid;
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, memberstatus);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public void updateMemberFitnessTest(int memberid, String fitnesstest) {
		Connection conn = DB.getConn();
		String sql = "update Member set fitnesstest = ?  where id = " + memberid;
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, fitnesstest);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	*/
	
}

package models.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import utils.common.DB;
import models.ComeInPassword;
import models.employee.Employee;
import models.store.Store;
import models.useless.Announcement;
import models.useless.City;
import models.useless.StoreCity;


public class DeadtimeLogMgr {
	
	private DeadtimeLogMgr() {}
	
	private static DeadtimeLogMgr em = null;
	
	public static DeadtimeLogMgr getInstance() {
		if (em == null) {
			em = new DeadtimeLogMgr();
		}
		return em;
	}
	
	public void save(DeadtimeLog dl) {
		Connection conn = DB.getConn();
		String sql = "insert into DeadtimeLog values (null, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setInt(1, dl.memberid);
			pstmt.setString(2, dl.updatetime);
			pstmt.setString(3, dl.deadtime);
			pstmt.setString(4, dl.employeename);
			pstmt.setInt(5, dl.cardtype);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public List<DeadtimeLog> findDeadtimeLogByMemberId(int mid) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		List<DeadtimeLog> list = new ArrayList<>();
		try {
			String sql = "select * from DeadtimeLog where memberid = " + mid;
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				int memberid = mid;
				String updatetime = rs.getString("updatetime");
				String deadtime = rs.getString("deadtime");
				String employeename = rs.getString("employeename");
				int cardtype = rs.getInt("cardtype");
				DeadtimeLog an = new DeadtimeLog(id, memberid, updatetime, cardtype, deadtime, employeename);
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

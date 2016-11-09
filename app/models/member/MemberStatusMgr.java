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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.common.DB;
import models.ComeInPassword;
import models.employee.Employee;
import models.front.InnerPwd;
import models.front.PwdToHard;
import models.store.Store;
import models.useless.Announcement;
import models.useless.City;
import models.useless.StoreCity;


public class MemberStatusMgr {
	
	private MemberStatusMgr() {}
	
	private static MemberStatusMgr em = null;
	
	public static MemberStatusMgr getInstance() {
		if (em == null) {
			em = new MemberStatusMgr();
		}
		return em;
	}
	
	public void save(MemberStatus e) {
		Connection conn = DB.getConn();
		String sql = "insert into MemberStatus values (null, ?, ?, ?, ?)";
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setInt(1, e.memberid);
			pstmt.setString(2, e.memberstatus);
			pstmt.setString(3, e.edittime);
			pstmt.setString(4, e.reason);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public List<MemberStatus> getMemberStatusByMemberId(int memberid) {
		List<MemberStatus> list = new ArrayList<MemberStatus>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from MemberStatus where memberid = " + memberid;
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String memberstatus = rs.getString("memberstatus");
				String edittime = rs.getString("edittime");
				String reason = rs.getString("reason");
				MemberStatus an = new MemberStatus(id, memberid, memberstatus, edittime, reason);
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

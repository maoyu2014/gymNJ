package utils;

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

import models.Announcement;
import models.City;
import models.ComeInPassword;
import models.Employee;
import models.Member;
import models.MemberStatus;
import models.Store;
import models.StoreCity;
import models.front.InnerPwd;
import models.front.PwdToHard;


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
	
//	public boolean deleteMember(int id) {
//		boolean flag = false;
//		Connection conn = DB.getConn();
//		Statement stmt = DB.getStmt(conn);
//		String sql = "delete from Member where id = " + id;
//		try {
//			stmt.executeUpdate(sql);
//			flag=true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DB.close(stmt);
//			DB.close(conn);
//		}
//		return flag;
//	}
	
	
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
	
	
	
	
	
}

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


public class MemberFitnessTestMgr {
	
	private MemberFitnessTestMgr() {}
	
	private static MemberFitnessTestMgr em = null;
	
	public static MemberFitnessTestMgr getInstance() {
		if (em == null) {
			em = new MemberFitnessTestMgr();
		}
		return em;
	}
	
	public void save(MemberFitnessTest e) {
		deleteMember(e.memberid);			//先删除旧的数据，然后写入新的数据
		Connection conn = DB.getConn();
		String sql = "insert into MemberFitnessTest values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setInt(1, e.memberid);
			pstmt.setString(2, e.fitnesstest);
			pstmt.setString(3, e.xiongwei);
			pstmt.setString(4, e.yaowei);
			pstmt.setString(5, e.tunwei);
			pstmt.setString(6, e.shangtunwei);
			pstmt.setString(7, e.datuiwei);
			pstmt.setString(8, e.xiaotuiwei);
			pstmt.setString(9, e.pullup);
			pstmt.setString(10, e.pushup);
			pstmt.setString(11, e.plank);
			pstmt.setString(12, e.sitandreach);
			pstmt.setString(13, e.squatandrise);
			pstmt.setString(14, e.situp);
			pstmt.setString(15, e.heartrateone);
			pstmt.setString(16, e.heartratetwo);
			pstmt.setString(17, e.heartratethree);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public MemberFitnessTest getMemberFitnessTestByMemberId(int memberid) {
		MemberFitnessTest mft = null;
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from MemberFitnessTest where memberid = " + memberid;
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String fitnesstest = rs.getString("fitnesstest");
				String xiongwei = rs.getString("xiongwei");
				String yaowei = rs.getString("yaowei");
				String tunwei = rs.getString("tunwei");
				String shangtunwei = rs.getString("shangtunwei");
				String datuiwei = rs.getString("datuiwei");
				String xiaotuiwei = rs.getString("xiaotuiwei");
				String pullup = rs.getString("pullup");
				String pushup = rs.getString("pushup");
				String plank = rs.getString("plank");
				String sitandreach = rs.getString("sitandreach");
				String squatandrise = rs.getString("squatandrise");
				String situp = rs.getString("situp");
				String heartrateone = rs.getString("heartrateone");
				String heartratetwo = rs.getString("heartratetwo");
				String heartratethree = rs.getString("heartratethree");
				mft = new MemberFitnessTest(id, memberid, fitnesstest, xiongwei, yaowei, tunwei, shangtunwei, datuiwei, xiaotuiwei, pullup, pushup, plank, sitandreach, squatandrise, situp, heartrateone, heartratetwo, heartratethree);
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return mft;
	}
	
	public boolean deleteMember(int memberid) {
		boolean flag = false;
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		String sql = "delete from MemberFitnessTest where memberid = " + memberid;
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
	
	
	
}

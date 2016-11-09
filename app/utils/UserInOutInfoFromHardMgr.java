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

import utils.common.DB;
import models.ComeInPassword;
import models.InOutInfo;
import models.employee.Employee;
import models.front.InnerPwd;
import models.front.PwdToHard;
import models.member.Member;
import models.store.Store;
import models.useless.Announcement;
import models.useless.City;
import models.useless.StoreCity;


public class UserInOutInfoFromHardMgr {
	
	private UserInOutInfoFromHardMgr() {}
	
	private static UserInOutInfoFromHardMgr em = null;
	
	public static UserInOutInfoFromHardMgr getInstance() {
		if (em == null) {
			em = new UserInOutInfoFromHardMgr();
		}
		return em;
	}
	
	public void save(InOutInfo e) {
		Connection conn = DB.getConn();
		String sql = "insert into InOutInfo values (null, ?, ?, ?, ?)";
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, e.deviceid);
			pstmt.setInt(2, e.memberid);
			pstmt.setInt(3, e.inOutType);
			pstmt.setString(4, e.inOutTm);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public List<InOutInfo> getAllInOutInfo() {
		List<InOutInfo> list = new ArrayList<InOutInfo>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from InOutInfo";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String deviceid = rs.getString("deviceid");
				int memberid = rs.getInt("memberid");
				int inOutType = rs.getInt("inOutType");
				String inOutTm = rs.getString("inOutTm");
				InOutInfo an = new InOutInfo(id, deviceid, memberid, inOutType, inOutTm);
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
	
	//0是进 
	public List<String> getMemberInInfo(int amemberid) {
//		List<InOutInfo> list = new ArrayList<InOutInfo>();
		List<String> list = new ArrayList<>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select inOutTm from InOutInfo where memberId = " + amemberid + " and inOutType = 0 order by inOutTm";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
//				int id = rs.getInt("id");
//				String deviceid = rs.getString("deviceid");
//				int memberid = rs.getInt("memberid");
//				int inOutType = rs.getInt("inOutType");
				String inOutTm = rs.getString("inOutTm");
//				InOutInfo an = new InOutInfo(id, deviceid, memberid, inOutType, inOutTm);
				list.add(inOutTm);
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
	
	//1是出 
	public List<String> getMemberOutInfo(int amemberid) {
//		List<InOutInfo> list = new ArrayList<InOutInfo>();
		List<String> list = new ArrayList<>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select inOutTm from InOutInfo where memberId = " + amemberid + " and inOutType = 1 order by inOutTm";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
//				int id = rs.getInt("id");
//				String deviceid = rs.getString("deviceid");
//				int memberid = rs.getInt("memberid");
//				int inOutType = rs.getInt("inOutType");
				String inOutTm = rs.getString("inOutTm");
//				InOutInfo an = new InOutInfo(id, deviceid, memberid, inOutType, inOutTm);
				list.add(inOutTm);
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

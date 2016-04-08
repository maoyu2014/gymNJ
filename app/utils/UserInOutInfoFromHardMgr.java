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
import models.InOutInfo;
import models.Member;
import models.Store;
import models.StoreCity;
import models.front.InnerPwd;
import models.front.PwdToHard;


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
			DB.close(conn);
			DB.close(stmt);
			DB.close(rs);
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
	
//	public Map<String, Object> findPwdToHardByToday() {
//		Calendar calendar = Calendar.getInstance();  
//		String today = "" + calendar.get(Calendar.YEAR)+"-";
//		int month = calendar.get(Calendar.MONTH)+1; 
//		if (month<10) today+="0"+month+"-"; else today += month+"-";
//		int day = calendar.get(Calendar.DAY_OF_MONTH);
//		if (day<10) today+="0"+day; else today+=day;
//		
//		Connection conn = DB.getConn();
//		Statement stmt = DB.getStmt(conn);
//		ResultSet rs = null;
//		Map<String, Object> map = new HashMap<>();
//		boolean flag = false;
//		List<InnerPwd> list = new ArrayList<>();
//		InnerPwd an = null;
//		try {
//			String sql = "select * from entrancepassword where cdate = '" + today + "'";
//			rs = DB.executeQuery(stmt, sql);
//			while (rs.next()) {
//				int memberid = rs.getInt("memberid"); String userid = String.valueOf(memberid);
//				String cdate = rs.getString("cdate");
//				int hour0 = rs.getInt("hour0"); String h0 = String.valueOf(hour0);
//				int hour1 = rs.getInt("hour1"); String h1 = String.valueOf(hour1);
//				int hour2 = rs.getInt("hour2"); String h2 = String.valueOf(hour2);
//				int hour3 = rs.getInt("hour3"); String h3 = String.valueOf(hour3);
//				int hour4 = rs.getInt("hour4"); String h4 = String.valueOf(hour4);
//				int hour5 = rs.getInt("hour5"); String h5 = String.valueOf(hour5);
//				int hour6 = rs.getInt("hour6"); String h6 = String.valueOf(hour6);
//				int hour7 = rs.getInt("hour7"); String h7 = String.valueOf(hour7);
//				int hour8 = rs.getInt("hour8"); String h8 = String.valueOf(hour8);
//				int hour9 = rs.getInt("hour9"); String h9 = String.valueOf(hour9);
//				int hour10 = rs.getInt("hour10"); String h10 = String.valueOf(hour10);
//				int hour11 = rs.getInt("hour11"); String h11 = String.valueOf(hour11);
//				int hour12 = rs.getInt("hour12"); String h12 = String.valueOf(hour12);
//				int hour13 = rs.getInt("hour13"); String h13 = String.valueOf(hour13);
//				int hour14 = rs.getInt("hour14"); String h14 = String.valueOf(hour14);
//				int hour15 = rs.getInt("hour15"); String h15 = String.valueOf(hour15);
//				int hour16 = rs.getInt("hour16"); String h16 = String.valueOf(hour16);
//				int hour17 = rs.getInt("hour17"); String h17 = String.valueOf(hour17);
//				int hour18 = rs.getInt("hour18"); String h18 = String.valueOf(hour18);
//				int hour19 = rs.getInt("hour19"); String h19 = String.valueOf(hour19);
//				int hour20 = rs.getInt("hour20"); String h20 = String.valueOf(hour20);
//				int hour21 = rs.getInt("hour21"); String h21 = String.valueOf(hour21);
//				int hour22 = rs.getInt("hour22"); String h22 = String.valueOf(hour22);
//				int hour23 = rs.getInt("hour23"); String h23 = String.valueOf(hour23);
//				if (!flag) {
//					String updateTime = rs.getString("updateTime");
//					flag = true;
//					map.put("updateTime", updateTime);
//				}
//				an = new InnerPwd(userid, cdate, h0, h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, h11, h12, h13, h14, h15, h16, h17, h18, h19, h20, h21, h22, h23);
//				list.add(an);
//			}
//			map.put("list", list);
//		} catch (SQLException eee) {
//			eee.printStackTrace();
//		} finally {
//			DB.close(conn);
//			DB.close(stmt);
//			DB.close(rs);
//		}
//		return map;
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

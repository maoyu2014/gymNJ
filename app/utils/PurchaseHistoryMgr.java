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
import models.Member;
import models.PurchaseHistory;
import models.Store;
import models.StoreCity;


public class PurchaseHistoryMgr {
	
	private PurchaseHistoryMgr() {}
	
	private static PurchaseHistoryMgr em = null;
	static {
		if (em == null) {
			em = new PurchaseHistoryMgr();
		}
	}
	
	public static PurchaseHistoryMgr getInstance() {
		return em;
	}
	
//	public void save(Member e) {
//		Connection conn = DB.getConn();
//		String sql = "insert into Member values (null, ?, ?, ?, ?, ?, ?)";
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
	
	public List<PurchaseHistory> getAllPurchaseHistory() {
		List<PurchaseHistory> list = new ArrayList<PurchaseHistory>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from PurchaseHistory";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String orderid = rs.getString("orderid");		//微信支付返回的一个值
				int memberid = rs.getInt("memberid");
				int carttype = rs.getInt("cardtype");	//1月 2季度 3半年 4年 5啥都不是
				double fee = rs.getDouble("fee");
				String purchasetime = rs.getString("purchasetime");
				int purchasetype = rs.getInt("purchasetype");	//购买方式,1代表微信支付，目前就做微信支付
				int isprivate = rs.getInt("isprivate");	//0表示购买会员，1表示购买私教课付费
				int bookid = rs.getInt("bookid");
				PurchaseHistory an = new PurchaseHistory(bookid, orderid, memberid, carttype, fee, purchasetime, purchasetype, isprivate, bookid);
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
	
	public boolean deletePurchaseHistory(int id) {
		boolean flag = false;
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		String sql = "delete from PurchaseHistory where id = " + id;
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
	
//	public PurchaseHistory findPurchaseHistoryById(int aid) {
//		Connection conn = DB.getConn();
//		Statement stmt = DB.getStmt(conn);
//		ResultSet rs = null;
//		PurchaseHistory an = null;
//		try {
//			//加空格啊加空格，sql一定记得各种加空格
//			String sql = "select * from Member where id = " + aid;
//			rs = DB.executeQuery(stmt, sql);
//			if (rs.next()) {
//				
//				an = new Member(id, openid, name, wechatname, sex, height, birthday, phone, fingerprint,  cityid,  cardtype, deaddate, exercisetime, exercisegoal, exercisehz, distance, bmi, muscle, fat, water, protein, basicrate, bodyage);
//			}
//		} catch (SQLException eee) {
//			eee.printStackTrace();
//		} finally {
//			DB.close(conn);
//			DB.close(stmt);
//			DB.close(rs);
//		}
//		return an;
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

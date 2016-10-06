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
	
	public List<PurchaseHistory> getAllPurchaseHistory() {
		List<PurchaseHistory> list = new ArrayList<PurchaseHistory>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select ph.id, ph.orderid, ph.memberid, m.name as membername, ph.cardtype, ph.fee, ph.purchasetime, ph.purchasetype, ph.isprivate, ph.bookid  from PurchaseHistory ph, member m where ph.memberid = m.id";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String orderid = rs.getString("orderid");		//微信支付返回的一个值
				int memberid = rs.getInt("memberid");
				String membername = rs.getString("membername");
				int cardtype = rs.getInt("cardtype");
				double fee = rs.getDouble("fee");
				String purchasetime = rs.getString("purchasetime");
				int purchasetype = rs.getInt("purchasetype");	//购买方式,1代表微信支付，目前就做微信支付
				int isprivate = rs.getInt("isprivate");	//0表示购买会员，1表示购买私教课付费
				int bookid = rs.getInt("bookid");
				PurchaseHistory an = new PurchaseHistory(id, orderid, memberid, membername, cardtype, fee, purchasetime, purchasetype, isprivate, bookid);
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
	
	
	public List<PurchaseHistory> searchPurchaseHistory(int cardtype, String starttime, String endtime) {
		List<PurchaseHistory> list = new ArrayList<PurchaseHistory>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from PurchaseHistory";
			boolean flag = false;
			if (cardtype==1 || cardtype==2 || cardtype==3 || cardtype==4) {
				if (!flag) {
					sql += " where cardtype = " + cardtype;
					flag = true;
				} else {
					sql += " and cardtype = " + cardtype;
				}
			}
			if (cardtype==5) {
				if (!flag) {
					sql += " where isprivate = 1 ";
					flag = true;
				} else {
					sql += " and isprivate = 1 ";
				}
			}
			if (starttime!=null && starttime.length()>0) {
				if (!flag) {
					sql+=" where purchasetime >= '" + starttime + "'";
					flag = true;
				} else {
					sql+=" and purchasetime >= '" + starttime + "'";
				}
			}
			if (endtime!=null && endtime.length()>0) {
				endtime += " 23:59:59";
				if (!flag) {
					sql+=" where purchasetime <= '" + endtime + "'";
					flag = true;
				} else {
					sql+=" and purchasetime <= '" + endtime + "'";
				}
			}
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String orderid = rs.getString("orderid");		//微信支付返回的一个值
				int memberid = rs.getInt("memberid");
				int carttype = rs.getInt("cardtype");	//1月 2季度 3半年 4年 
				double fee = rs.getDouble("fee");
				String purchasetime = rs.getString("purchasetime");
				int purchasetype = rs.getInt("purchasetype");	//购买方式,1代表微信支付，目前就做微信支付
				int isprivate = rs.getInt("isprivate");	//0表示购买会员，1表示购买私教课付费
				int bookid = rs.getInt("bookid");
				PurchaseHistory an = new PurchaseHistory(id, orderid, memberid, carttype, fee, purchasetime, purchasetype, isprivate, bookid);
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
	
	
}

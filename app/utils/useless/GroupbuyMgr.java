package utils.useless;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utils.common.DB;
import models.employee.Employee;
import models.store.Store;
import models.useless.Announcement;
import models.useless.City;
import models.useless.Groupbuy;
import models.useless.StoreCity;


public class GroupbuyMgr {
	
	private GroupbuyMgr() {}
	
	private static GroupbuyMgr em = null;
	static {
		if (em == null) {
			em = new GroupbuyMgr();
		}
	}
	
	public static GroupbuyMgr getInstance() {
		return em;
	}
	
	public void save(Groupbuy e) {
		Connection conn = DB.getConn();
		String sql = "insert into groupbuy values (null, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setInt(1, e.groupwebsiteid);
			pstmt.setInt(2, e.storeid);
			pstmt.setDouble(3, e.price);
			pstmt.setDouble(4, e.times);
			pstmt.setString(5, e.starttime);
			pstmt.setString(6, e.endtime);
			pstmt.setString(7, e.introduce);
			pstmt.setString(8, e.weburl);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public List<Groupbuy> getAllGroupbuy() {
		List<Groupbuy> list = new ArrayList<Groupbuy>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from groupbuy";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				int groupwebsiteid = rs.getInt("groupwebsiteid");
				int storeid = rs.getInt("storeid");
				double price = rs.getDouble("price");
				double times = rs.getDouble("times");
				String starttime = rs.getString("starttime");
				String endtime = rs.getString("endtime");
				String introduce = rs.getString("introduce");
				String weburl = rs.getString("weburl");
				Groupbuy an = new Groupbuy(id, groupwebsiteid, storeid, price, times, starttime, endtime, introduce, weburl);
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
	
	public List<Groupbuy> serachGroupbuy(int agroupwebsiteid, int astoreid, String astarttime) {
		List<Groupbuy> list = new ArrayList<Groupbuy>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from groupbuy";
			boolean flag = false;
			if (agroupwebsiteid!=0) {
				if (!flag) {
					sql += " where groupwebsiteid = " + agroupwebsiteid;
					flag = true;
				} else {
					sql += " and groupwebsiteid = " + agroupwebsiteid;
				}
			}
			if (astoreid!=0) {
				if (!flag) {
					sql += " where storeid = " + astoreid;
					flag = true;
				} else {
					sql += " and storeid = " + astoreid;
				}
			}
			if (astarttime!=null && astarttime.length()>0) {
				if (!flag) {
					sql+=" where starttime >= '" + astarttime + "'";
					flag = true;
				} else {
					sql+=" and starttime >= '" + astarttime + "'";
				}
			}
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				int groupwebsiteid = rs.getInt("groupwebsiteid");
				int storeid = rs.getInt("storeid");
				double price = rs.getDouble("price");
				double times = rs.getDouble("times");
				String starttime = rs.getString("starttime");
				String endtime = rs.getString("endtime");
				String introduce = rs.getString("introduce");
				String weburl = rs.getString("weburl");
				Groupbuy an = new Groupbuy(id, groupwebsiteid, storeid, price, times, starttime, endtime, introduce, weburl);
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
	
	public boolean deleteGroupbuy(int id) {
		boolean flag = false;
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		String sql = "delete from groupbuy where id = " + id;
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
	
	public Groupbuy findGroupbuyById(int groupbuyid) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		Groupbuy an = null;
		try {
			//加空格啊加空格，sql一定记得各种加空格
			String sql = "select * from groupbuy where id = " + groupbuyid;
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				int groupwebsiteid = rs.getInt("groupwebsiteid");
				int storeid = rs.getInt("storeid");
				double price = rs.getDouble("price");
				double times = rs.getDouble("times");
				String starttime = rs.getString("starttime");
				String endtime = rs.getString("endtime");
				String introduce = rs.getString("introduce");
				String weburl = rs.getString("weburl");
				an = new Groupbuy(id, groupwebsiteid, storeid, price, times,starttime, endtime, introduce, weburl);
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
	
	public void update(Groupbuy e) {
		Connection conn = DB.getConn();
		String sql = "update groupbuy set groupwebsiteid = ?, storeid = ?, price = ?, times = ?, starttime = ?, endtime = ?, introduce = ?, weburl = ?  where id = " +e.id;
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setInt(1, e.groupwebsiteid);
			pstmt.setInt(2, e.storeid);
			pstmt.setDouble(3, e.price);
			pstmt.setDouble(4, e.times);
			pstmt.setString(5, e.starttime);
			pstmt.setString(6, e.endtime);
			pstmt.setString(7, e.introduce);
			pstmt.setString(8, e.weburl);
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

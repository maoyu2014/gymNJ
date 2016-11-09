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
import models.useless.City;
import models.useless.StoreCity;


public class CityMgr {
	
	private CityMgr() {}
	
	private static CityMgr cm = null;
	static {
		if (cm == null) {
			cm = new CityMgr();
		}
	}
	
	public static CityMgr getInstance() {
		return cm;
	}
	
	public List<City> getAllCity() {
		List<City> list = new ArrayList<City>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from city";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				City m = new City(id,name);
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
	
	public City findCityById(int cid) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		City sc = null;
		try {
			String sql = "select * from city where id = " + cid;
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				sc = new City(id, name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn);
			DB.close(stmt);
			DB.close(rs);
		}
		return sc;
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
	
	public void save(Member m) {
		Connection conn = DB.getConn();
		String sql = "insert into member values (null, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setInt(1, m.getAssociationid());
			pstmt.setString(2, m.getPengid());
			pstmt.setString(3, m.getName());
			pstmt.setString(4, m.getProvince());
			pstmt.setString(5, m.getCity());
			pstmt.setString(6, m.getLongitude());
			pstmt.setString(7, m.getLatitude());
			pstmt.setString(8, m.getPhone());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public void update(Member m) {
		Connection conn = DB.getConn();
		String sql = "update member set pengid = ?, name = ?, province = ?, city = ?, longitude = ?, latitude = ?, phone = ? where id = " + m.getId();
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, m.getPengid());
			pstmt.setString(2, m.getName());
			pstmt.setString(3, m.getProvince());
			pstmt.setString(4, m.getCity());
			pstmt.setString(5, m.getLongitude());
			pstmt.setString(6, m.getLatitude());
			pstmt.setString(7, m.getPhone());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
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
	
	public List<Match> getAllMatches(String month) {
		List<Match> list = new ArrayList<Match>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select tmatch.id, tmatch.name, tmatch.location, tmatch.eastnorth, tmatch.weather, tmatch.starttime, tmatch.endtime, tmatch.award, association.nameofassociation from tmatch inner join association on tmatch.associationid=association.id where starttime like '" + month + "%'";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				Match m = new Match();
				m.setId(rs.getInt("id"));
				m.setNameofassociation(rs.getString("nameofassociation"));
				m.setName(rs.getString("name"));
				m.setLocation(rs.getString("location"));
				m.setEastnorth(rs.getString("eastnorth"));
				m.setWeather(rs.getString("weather"));
				m.setStarttime(rs.getString("starttime"));
				m.setEndtime(rs.getString("endtime"));
				m.setAward(rs.getString("award"));
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
	
	public boolean deleteMember(int id) {
		boolean flag = false;
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		String sql = "delete from member where id = " + id;
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

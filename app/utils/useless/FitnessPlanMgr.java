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
import models.useless.FitnessPlan;
import models.useless.StoreCity;


public class FitnessPlanMgr {
	
	private FitnessPlanMgr() {}
	
	private static FitnessPlanMgr em = null;
	static {
		if (em == null) {
			em = new FitnessPlanMgr();
		}
	}
	
	public static FitnessPlanMgr getInstance() {
		return em;
	}
	
	public void save(FitnessPlan e) {
		Connection conn = DB.getConn();
		String sql = "insert into fitnessplan values (null, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setInt(1, e.style);
			pstmt.setString(2, e.name);
			pstmt.setInt(3, e.fitsex);
			pstmt.setString(4, e.parts);
			pstmt.setString(5, e.number);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public List<FitnessPlan> getAllFitnessPlan() {
		List<FitnessPlan> list = new ArrayList<FitnessPlan>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from fitnessplan";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id=rs.getInt("id");
				int style = rs.getInt("style");
				String name=rs.getString("name");
				int fitsex=rs.getInt("fitsex");
				String parts=rs.getString("parts");
				String number=rs.getString("number");
				FitnessPlan an = new FitnessPlan(id, style, name, fitsex, parts, number);
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
	
	public boolean deleteFitnessPlan(int id) {
		boolean flag = false;
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		String sql = "delete from fitnessplan where id = " + id;
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
	
	public FitnessPlan findFitnessPlanById(int aid) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		FitnessPlan an = null;
		try {
			//加空格啊加空格，sql一定记得各种加空格
			String sql = "select * from fitnessplan where id = " + aid;
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				int id=rs.getInt("id");
				int style = rs.getInt("style");
				String name=rs.getString("name");
				int fitsex=rs.getInt("fitsex");
				String parts=rs.getString("parts");
				String number=rs.getString("number");
				an = new FitnessPlan(id, style, name, fitsex, parts, number);
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
	
	public void update(FitnessPlan e) {
		Connection conn = DB.getConn();
		String sql = "update fitnessplan set style = ?, name = ?, fitsex = ?, parts = ?, number = ?  where id = " +e.id;
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setInt(1, e.style);
			pstmt.setString(2, e.name);
			pstmt.setInt(3, e.fitsex);
			pstmt.setString(4, e.parts);
			pstmt.setString(5, e.number);
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

package models.company;

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
import models.store.Store;
import models.useless.City;
import models.useless.StoreCity;


public class CompanyMgr {
	
	private CompanyMgr() {}
	
	private static CompanyMgr em = null;
	static {
		if (em == null) {
			em = new CompanyMgr();
		}
	}
	
	public static CompanyMgr getInstance() {
		return em;
	}
	
	public void save(Company e) {
		Connection conn = DB.getConn();
		String sql = "insert into company values (null, ?, ?, ?, ?)";
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, e.name);
			pstmt.setString(2, e.address);
			pstmt.setString(3, e.manager);
			pstmt.setString(4, e.phone);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public List<Company> getAllCompanyCombo() {
		List<Company> list = new ArrayList<>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from company";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				Company e = new Company();
				e.id = rs.getInt("id");
				e.name = rs.getString("name");
				list.add(e);
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
	
	public List<Company> getAllCompany() {
		List<Company> list = new ArrayList<>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from company";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				Company e = new Company();
				e.id = rs.getInt("id");
				e.name = rs.getString("name");
				e.address = rs.getString("address");
				e.manager = rs.getString("manager");
				e.phone = rs.getString("phone");
				list.add(e);
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
	
	public Company getCompanyByID(int id) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		Company e = new Company();
		try {
			String sql = "select * from company where id = " + id;
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				e.id = rs.getInt("id");
				e.name = rs.getString("name");
				e.address = rs.getString("address");
				e.manager = rs.getString("manager");
				e.phone = rs.getString("phone");
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return e;
	}
	
	public boolean deleteCompany(int id) {
		boolean flag = false;
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		String sql = "delete from company where id = " + id;
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
	
	public void update(Company e) {
		Connection conn = DB.getConn();
		String sql = "update company set name = ?, address = ?, manager = ?, phone = ? where id = " +e.id;
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, e.name);
			pstmt.setString(2, e.address);
			pstmt.setString(3, e.manager);
			pstmt.setString(4, e.phone);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	
}

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
import models.Store;
import models.StoreCity;


public class MemberMgr {
	
	private MemberMgr() {}
	
	private static MemberMgr em = null;
	static {
		if (em == null) {
			em = new MemberMgr();
		}
	}
	
	public static MemberMgr getInstance() {
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
	
	public List<Member> getAllMember() {
		List<Member> list = new ArrayList<Member>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select * from Member";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String openid = rs.getString("openid");			//微信唯一标识
				String name = rs.getString("name");
				String wechatname = rs.getString("wechatname");
				int sex = rs.getInt("sex");		//1男 2女
				double height = rs.getDouble("height");
				String birthday = rs.getString("birthday");	//生日
				String phone = rs.getString("phone");
				int fingerprint = rs.getInt("fingerprint");	//指纹状态，1已录入 2未录入
				int comeinpasswordid = rs.getInt("comeinpasswordid");			//入场密码表
				int cityid = rs.getInt("cityid");
				int storeid = rs.getInt("storeid");
				int cardtype = rs.getInt("cardtype");	//会员卡种类，进来默认是0非会员  1月卡，2季卡，3半年卡，4年卡
				String deaddate = rs.getString("deaddate");
				int exercisetime = rs.getInt("exercisetime"); 	//时间   1早上 2下午 3晚上
				int exercisegoal = rs.getInt("exercisegoal");	//目标   1减脂 2塑形 3增肌
				int exercisehz = rs.getInt("exercisehz");	//频率，1难的  2一周一次   3一周三次   4每天
				int distance = rs.getInt("distance");	//距离  1：一公里以内  2：三公里以内  3：三公里以外
				//体脂信息
				String bmi = rs.getString("bmi");		//BMI
				String muscle = rs.getString("muscle");		//肌肉率
				String fat = rs.getString("fat");		//脂肪
				String water = rs.getString("water");		//水分
				String protein = rs.getString("protein");	//蛋白质
				int basicrate = rs.getInt("basicrate");	//基础代谢率
				int bodyage = rs.getInt("bodyage");		//身体年龄
				Member an = new Member(id, openid, name, wechatname, sex, height, birthday, phone, fingerprint, comeinpasswordid, cityid, storeid, cardtype, deaddate, exercisetime, exercisegoal, exercisehz, distance, bmi, muscle, fat, water, protein, basicrate, bodyage);
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
	
	public boolean deleteMember(int id) {
		boolean flag = false;
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		String sql = "delete from Member where id = " + id;
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
	
	public Member findMemberById(int aid) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		Member an = null;
		try {
			//加空格啊加空格，sql一定记得各种加空格
			String sql = "select * from Member where id = " + aid;
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				String openid = rs.getString("openid");			//微信唯一标识
				String name = rs.getString("name");
				String wechatname = rs.getString("wechatname");
				int sex = rs.getInt("sex");		//1男 2女
				double height = rs.getDouble("height");
				String birthday = rs.getString("birthday");	//生日
				String phone = rs.getString("phone");
				int fingerprint = rs.getInt("fingerprint");	//指纹状态，1已录入 2未录入
				int comeinpasswordid = rs.getInt("comeinpasswordid");			//入场密码表
				int cityid = rs.getInt("cityid");
				int storeid = rs.getInt("storeid");
				int cardtype = rs.getInt("cardtype");	//会员卡种类，进来默认是0非会员  1月卡，2季卡，3半年卡，4年卡
				String deaddate = rs.getString("deaddate");
				int exercisetime = rs.getInt("exercisetime"); 	//时间   1早上 2下午 3晚上
				int exercisegoal = rs.getInt("exercisegoal");	//目标   1减脂 2塑形 3增肌
				int exercisehz = rs.getInt("exercisehz");	//频率，1难的  2一周一次   3一周三次   4每天
				int distance = rs.getInt("distance");	//距离  1：一公里以内  2：三公里以内  3：三公里以外
				//体脂信息
				String bmi = rs.getString("bmi");		//BMI
				String muscle = rs.getString("muscle");		//肌肉率
				String fat = rs.getString("fat");		//脂肪
				String water = rs.getString("water");		//水分
				String protein = rs.getString("protein");	//蛋白质
				int basicrate = rs.getInt("basicrate");	//基础代谢率
				int bodyage = rs.getInt("bodyage");		//身体年龄
				an = new Member(id, openid, name, wechatname, sex, height, birthday, phone, fingerprint, comeinpasswordid, cityid, storeid, cardtype, deaddate, exercisetime, exercisegoal, exercisehz, distance, bmi, muscle, fat, water, protein, basicrate, bodyage);
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

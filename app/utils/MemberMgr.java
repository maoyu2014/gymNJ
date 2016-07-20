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
			String sql = "select a.id, a.openid, a.name, a.wechatname, a.phone, a.fingerprint, b.name as cityname, a.cardtype, a.wechatnumber, a.fitnesstest, a.memberstatus from Member a, city b where a.cityid=b.id and a.cardtype>0";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String openid = rs.getString("openid");			//微信唯一标识
				String name = rs.getString("name");
				String wechatname = rs.getString("wechatname");
				String wechatnumber = rs.getString("wechatnumber");
				String phone = rs.getString("phone");
				int fingerprint = rs.getInt("fingerprint");	//指纹状态，1已录入 2未录入
				String cityname = rs.getString("cityname");
				int cardtype = rs.getInt("cardtype");	//会员卡种类，进来默认是0非会员  1月卡，2季卡，3半年卡，4年卡
				String fitnesstest = rs.getString("fitnesstest");
				String memberstatus = rs.getString("memberstatus");
				Member an = new Member(id, openid, name, wechatname, phone, fingerprint, cityname, cardtype, wechatnumber, fitnesstest, memberstatus);
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
	
	public List<Member> searchMember(int acityid, int acardtype, String keyname) {
		List<Member> list = new ArrayList<Member>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select a.id, a.openid, a.name, a.wechatname, a.phone, a.fingerprint, b.name as cityname, a.cardtype, a.wechatnumber, a.fitnesstest, a.memberstatus from Member a, city b "
					+ "where a.cityid=b.id ";
			boolean flag = true;
			if (acityid!=0) {
				if (!flag) {
					sql += " where a.cityid = " + acityid;
				} else {
					sql += " and a.cityid = " + acityid;
				}
			}
			if (acardtype!=5) {		//注意这里是5表示所有会员，因为非会员是0
				if (!flag) {
					sql += " where a.cardtype = " + acardtype;
				} else {
					sql += " and a.cardtype = " + acardtype;
				}
			}
			if (keyname!=null && keyname.length()>0) {
				if (!flag) {
					sql+=" where ( a.name like '%" + keyname + "%' or a.wechatname like '%" + keyname + "%' or a.phone like '%" + keyname + "%' ) ";
				} else {
					sql+=" and ( a.name like '%" + keyname + "%' or a.wechatname like '%" + keyname + "%' or a.phone like '%" + keyname + "%' ) ";
				}
			}
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String openid = rs.getString("openid");			//微信唯一标识
				String name = rs.getString("name");
				String wechatname = rs.getString("wechatname");
				String phone = rs.getString("phone");
				int fingerprint = rs.getInt("fingerprint");	//指纹状态，1已录入 2未录入
				String cityname = rs.getString("cityname");
				int cardtype = rs.getInt("cardtype");	//会员卡种类，进来默认是0非会员  1月卡，2季卡，3半年卡，4年卡
				String wechatnumber = rs.getString("wechatnumber");
				String fitnesstest = rs.getString("fitnesstest");
				String memberstatus = rs.getString("memberstatus");
				Member an = new Member(id, openid, name, wechatname, phone, fingerprint, cityname, cardtype, wechatnumber, fitnesstest, memberstatus);
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
				String wechatnumber = rs.getString("wechatnumber");
				int sex = rs.getInt("sex");		//1男 2女
				double height = rs.getDouble("height");
				String birthday = rs.getString("birthday");	//生日
				String phone = rs.getString("phone");
				int fingerprint = rs.getInt("fingerprint");	//指纹状态，1已录入 2未录入
//				int comeinpasswordid = rs.getInt("comeinpasswordid");			//入场密码表
				int cityid = rs.getInt("cityid");
				int cardtype = rs.getInt("cardtype");	//会员卡种类，进来默认是0非会员  1月卡，2季卡，3半年卡，4年卡
				String deaddate = rs.getString("deaddate");
				int exercisetime = rs.getInt("exercisetime"); 	//时间   1早上 2下午 3晚上
				int exercisegoal = rs.getInt("exercisegoal");	//目标   1减脂 2塑形 3增肌
				int exercisehz = rs.getInt("exercisehz");	//频率，1难的  2一周一次   3一周三次   4每天
				int distance = rs.getInt("distance");	//距离  1：一公里以内  2：三公里以内  3：三公里以外
				
				String fitnesstest = rs.getString("fitnesstest");
				String memberstatus = rs.getString("memberstatus");
				
				//体脂信息
				String bmi = rs.getString("bmi");		//BMI
				String muscle = rs.getString("muscle");		//肌肉率
				String fat = rs.getString("fat");		//脂肪
				String innerfat = rs.getString("innerfat");		//内脏脂肪
				String water = rs.getString("water");		//水分
				String protein = rs.getString("protein");	//蛋白质
				int basicrate = rs.getInt("basicrate");	//基础代谢率
				int bodyage = rs.getInt("bodyage");		//身体年龄
				an = new Member(id, openid, name, wechatname, sex, height, birthday, phone, fingerprint, cityid, cardtype, deaddate, exercisetime, exercisegoal, exercisehz, distance, bmi, muscle, fat, water, protein, basicrate, bodyage, wechatnumber, fitnesstest, memberstatus, innerfat);
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return an;
	}
	
	public void updateDeaddate(int aid, int acardtype, String adeaddate) {
		Connection conn = DB.getConn();
		String sql = "update Member set cardtype = ? , deaddate = ?  where id = " +aid;
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setInt(1, acardtype);
			pstmt.setString(2, adeaddate);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public List<Member> getGoodRecommendedMember() {
		List<Member> list = new ArrayList<Member>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select id, openid, name, wechatname, phone, deaddate from member where phone in "
					+ "(select recommendedphone from Member group by recommendedphone having recommendedphone != '' and  count(recommendedphone) >= 1) "
					+ "order by id";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String openid = rs.getString("openid");			//微信唯一标识
				String name = rs.getString("name");
				String wechatname = rs.getString("wechatname");
				String phone = rs.getString("phone");
				String deaddate = rs.getString("deaddate");
				Member an = new Member(id, openid, name, wechatname, phone, deaddate);
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
	
	public void updateMemberStatus(int memberid, String memberstatus) {
		Connection conn = DB.getConn();
		String sql = "update Member set memberstatus = ?  where id = " + memberid;
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, memberstatus);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	public void updateMemberFitnessTest(int memberid, String fitnesstest) {
		Connection conn = DB.getConn();
		String sql = "update Member set fitnesstest = ?  where id = " + memberid;
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setString(1, fitnesstest);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
}

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
import java.util.List;

import models.Announcement;
import models.City;
import models.ComeInPassword;
import models.Employee;
import models.Member;
import models.Store;
import models.StoreCity;


public class ComeInPasswordMgr {
	
	private ComeInPasswordMgr() {}
	
	private static ComeInPasswordMgr em = null;
	
	public static ComeInPasswordMgr getInstance() {
		if (em == null) {
			em = new ComeInPasswordMgr();
		}
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
	
//	public List<Member> getAllMember() {
//		List<Member> list = new ArrayList<Member>();
//		Connection conn = DB.getConn();
//		Statement stmt = DB.getStmt(conn);
//		ResultSet rs = null;
//		try {
//			String sql = "select * from Member";
//			rs = DB.executeQuery(stmt, sql);
//			while (rs.next()) {
//				int id = rs.getInt("id");
//				String openid = rs.getString("openid");			//微信唯一标识
//				String name = rs.getString("name");
//				String wechatname = rs.getString("wechatname");
//				int sex = rs.getInt("sex");		//1男 2女
//				double height = rs.getDouble("height");
//				String birthday = rs.getString("birthday");	//生日
//				String phone = rs.getString("phone");
//				int fingerprint = rs.getInt("fingerprint");	//指纹状态，1已录入 2未录入
//				int comeinpasswordid = rs.getInt("comeinpasswordid");			//入场密码表
//				int cityid = rs.getInt("cityid");
//				int storeid = rs.getInt("storeid");
//				int cardtype = rs.getInt("cardtype");	//会员卡种类，进来默认是0非会员  1月卡，2季卡，3半年卡，4年卡
//				String deaddate = rs.getString("deaddate");
//				int exercisetime = rs.getInt("exercisetime"); 	//时间   1早上 2下午 3晚上
//				int exercisegoal = rs.getInt("exercisegoal");	//目标   1减脂 2塑形 3增肌
//				int exercisehz = rs.getInt("exercisehz");	//频率，1难的  2一周一次   3一周三次   4每天
//				int distance = rs.getInt("distance");	//距离  1：一公里以内  2：三公里以内  3：三公里以外
//				//体脂信息
//				String bmi = rs.getString("bmi");		//BMI
//				String muscle = rs.getString("muscle");		//肌肉率
//				String fat = rs.getString("fat");		//脂肪
//				String water = rs.getString("water");		//水分
//				String protein = rs.getString("protein");	//蛋白质
//				int basicrate = rs.getInt("basicrate");	//基础代谢率
//				int bodyage = rs.getInt("bodyage");		//身体年龄
//				Member an = new Member(id, openid, name, wechatname, sex, height, birthday, phone, fingerprint, comeinpasswordid, cityid, storeid, cardtype, deaddate, exercisetime, exercisegoal, exercisehz, distance, bmi, muscle, fat, water, protein, basicrate, bodyage);
//				list.add(an);
//			}
//		} catch (SQLException eee) {
//			eee.printStackTrace();
//		} finally {
//			DB.close(conn);
//			DB.close(stmt);
//			DB.close(rs);
//		}
//		return list;
//	}
//	
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
	
	public ComeInPassword findComeInPasswordByMemberId(int mid) {
		
		Calendar calendar = Calendar.getInstance();  
		String today = "" + calendar.get(Calendar.YEAR)+"-";
		int month = calendar.get(Calendar.MONTH)+1; 
		if (month<10) today+="0"+month+"-"; else today += month+"-";
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day<10) today+="0"+day; else today+=day;
		
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		ComeInPassword an = null;
		try {
			//加空格啊加空格，sql一定记得各种加空格
			String sql = "select * from entrancepassword where memberid = " + mid + " and cdate = '" + today + "'";
			rs = DB.executeQuery(stmt, sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				int memberid = mid;
				String cdate = rs.getString("cdate");
				int[] arr = new int[24];
				int hour = rs.getInt("hour0"); arr[0]=hour;
				hour = rs.getInt("hour1"); arr[1]=hour;
				hour = rs.getInt("hour2"); arr[2]=hour;
				hour = rs.getInt("hour3"); arr[3]=hour;
				hour = rs.getInt("hour4"); arr[4]=hour;
				hour = rs.getInt("hour5"); arr[5]=hour;
				hour = rs.getInt("hour6"); arr[6]=hour;
				hour = rs.getInt("hour7"); arr[7]=hour;
				hour = rs.getInt("hour8"); arr[8]=hour;
				hour = rs.getInt("hour9"); arr[9]=hour;
				hour = rs.getInt("hour10"); arr[10]=hour;
				hour = rs.getInt("hour11"); arr[11]=hour;
				hour = rs.getInt("hour12"); arr[12]=hour;
				hour = rs.getInt("hour13"); arr[13]=hour;
				hour = rs.getInt("hour14"); arr[14]=hour;
				hour = rs.getInt("hour15"); arr[15]=hour;
				hour = rs.getInt("hour16"); arr[16]=hour;
				hour = rs.getInt("hour17"); arr[17]=hour;
				hour = rs.getInt("hour18"); arr[18]=hour;
				hour = rs.getInt("hour19"); arr[19]=hour;
				hour = rs.getInt("hour20"); arr[20]=hour;
				hour = rs.getInt("hour21"); arr[21]=hour;
				hour = rs.getInt("hour22"); arr[22]=hour;
				hour = rs.getInt("hour23"); arr[23]=hour;
				String updateTime = rs.getString("updateTime");
				an = new ComeInPassword(id, memberid, cdate, arr, updateTime);
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

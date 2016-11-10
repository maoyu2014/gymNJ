package models.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.common.DB;
import models.employee.Employee;
import models.store.Store;
import models.useless.Announcement;
import models.useless.City;
import models.useless.StoreCity;


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
	
	
	public List<Member> getAllActiveNormalMember(String todaytime) {
		List<Member> list = new ArrayList<Member>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select a.id, a.openid, a.name, a.wechatname, a.phone, b.name as storename, a.cardtype, a.wechatnumber, a.fitnesstest, a.memberstatus, a.leftcoursenum from Member a, store b where a.storeid = b.id and a.deaddate > '" + todaytime + "' and (a.memberstatus is NULL or a.memberstatus != '课程卡成员')";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String openid = rs.getString("openid");			//微信唯一标识
				String name = rs.getString("name");
				String wechatname = rs.getString("wechatname");
				String wechatnumber = rs.getString("wechatnumber");
				String phone = rs.getString("phone");
				String storename = rs.getString("storename");
				int cardtype = rs.getInt("cardtype");	//会员卡种类，进来默认是0非会员  1月卡，2季卡，3半年卡，4年卡
				String fitnesstest = rs.getString("fitnesstest");
				String memberstatus = rs.getString("memberstatus");
				int leftcoursenum = rs.getInt("leftcoursenum");
				Member an = new Member(id, openid, name, wechatname, phone, storename, cardtype, wechatnumber, fitnesstest, memberstatus, leftcoursenum);
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
	
	public List<Member> getAllActiveSpecialMember(String todaytime) {
		List<Member> list = new ArrayList<Member>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select a.id, a.openid, a.name, a.wechatname, a.phone, b.name as storename, a.cardtype, a.wechatnumber, a.fitnesstest, a.memberstatus, a.leftcoursenum from Member a, store b where a.storeid = b.id and a.deaddate > '" + todaytime + "' and a.memberstatus = '课程卡成员'";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String openid = rs.getString("openid");			//微信唯一标识
				String name = rs.getString("name");
				String wechatname = rs.getString("wechatname");
				String wechatnumber = rs.getString("wechatnumber");
				String phone = rs.getString("phone");
				String storename = rs.getString("storename");
				int cardtype = rs.getInt("cardtype");	//会员卡种类，进来默认是0非会员  1月卡，2季卡，3半年卡，4年卡
				String fitnesstest = rs.getString("fitnesstest");
				String memberstatus = rs.getString("memberstatus");
				int leftcoursenum = rs.getInt("leftcoursenum");
				Member an = new Member(id, openid, name, wechatname, phone, storename, cardtype, wechatnumber, fitnesstest, memberstatus, leftcoursenum);
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
	
	public List<Member> searchNormalMember(String todaytime, int storeid, int acardtype, int amembertype, int asextype, String afitnesstest, String keyname) {
		List<Member> list = new ArrayList<Member>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		String sql = "select a.id, a.openid, a.name, a.wechatname, a.phone, b.name as storename, a.cardtype, a.wechatnumber, a.fitnesstest, a.memberstatus, a.leftcoursenum from Member a, store b where a.storeid = b.id and a.deaddate > '" + todaytime + "' and (a.memberstatus is NULL or a.memberstatus != '课程卡成员') ";
		try {
			if (storeid!=0) {
				sql += " and a.storeid = " + storeid;
			}
			acardtype=10;
			if (acardtype!=10) {		//注意这里是10表示所有会员，因为非会员是0
				sql += " and a.cardtype = " + acardtype;
			}
			amembertype=0;
			if (amembertype!=0) {
				if (amembertype==1)		//在期会员
					sql += " and a.deaddate > '" + todaytime + "'";
				else if (amembertype==2) // 过期会员
					sql += " and a.deaddate < '" + todaytime + "' and a.cardtype>0";
			}
			asextype=0;
			if (asextype!=0) {
				sql += " and a.sex = " + asextype;
			}
			afitnesstest="是否";
			if (!afitnesstest.equals("是否")) {
				sql += " and a.fitnesstest = '" + afitnesstest + "'";
			}
			if (keyname!=null && keyname.length()>0) {
				sql+=" and ( a.name like '%" + keyname + "%' or a.wechatname like '%" + keyname + "%' or a.phone like '%" + keyname + "%' ) ";
			}
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String openid = rs.getString("openid");			//微信唯一标识
				String name = rs.getString("name");
				String wechatname = rs.getString("wechatname");
				String wechatnumber = rs.getString("wechatnumber");
				String phone = rs.getString("phone");
				String storename = rs.getString("storename");
				int cardtype = rs.getInt("cardtype");	//会员卡种类，进来默认是0非会员  1月卡，2季卡，3半年卡，4年卡
				String fitnesstest = rs.getString("fitnesstest");
				String memberstatus = rs.getString("memberstatus");
				int leftcoursenum = rs.getInt("leftcoursenum");
				Member an = new Member(id, openid, name, wechatname, phone, storename, cardtype, wechatnumber, fitnesstest, memberstatus, leftcoursenum);
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
	
	public List<Member> searchSpecialMember(String todaytime, int storeid, int acardtype, int amembertype, int asextype, String afitnesstest, String keyname) {
		List<Member> list = new ArrayList<Member>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		String sql = "select a.id, a.openid, a.name, a.wechatname, a.phone, b.name as storename, a.cardtype, a.wechatnumber, a.fitnesstest, a.memberstatus, a.leftcoursenum from Member a, store b where a.storeid = b.id and a.deaddate > '" + todaytime + "' and a.memberstatus = '课程卡成员' ";
		try {
			if (storeid!=0) {
				sql += " and a.storeid = " + storeid;
			}
			acardtype=10;
			if (acardtype!=10) {		//注意这里是10表示所有会员，因为非会员是0
				sql += " and a.cardtype = " + acardtype;
			}
			amembertype=0;
			if (amembertype!=0) {
				if (amembertype==1)		//在期会员
					sql += " and a.deaddate > '" + todaytime + "'";
				else if (amembertype==2) // 过期会员
					sql += " and a.deaddate < '" + todaytime + "' and a.cardtype>0";
			}
			asextype=0;
			if (asextype!=0) {
				sql += " and a.sex = " + asextype;
			}
			afitnesstest="是否";
			if (!afitnesstest.equals("是否")) {
				sql += " and a.fitnesstest = '" + afitnesstest + "'";
			}
			if (keyname!=null && keyname.length()>0) {
				sql+=" and ( a.name like '%" + keyname + "%' or a.wechatname like '%" + keyname + "%' or a.phone like '%" + keyname + "%' ) ";
			}
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String openid = rs.getString("openid");			//微信唯一标识
				String name = rs.getString("name");
				String wechatname = rs.getString("wechatname");
				String wechatnumber = rs.getString("wechatnumber");
				String phone = rs.getString("phone");
				String storename = rs.getString("storename");
				int cardtype = rs.getInt("cardtype");	//会员卡种类，进来默认是0非会员  1月卡，2季卡，3半年卡，4年卡
				String fitnesstest = rs.getString("fitnesstest");
				String memberstatus = rs.getString("memberstatus");
				int leftcoursenum = rs.getInt("leftcoursenum");
				Member an = new Member(id, openid, name, wechatname, phone, storename, cardtype, wechatnumber, fitnesstest, memberstatus, leftcoursenum);
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
			String sql = "select m.*, s.name as storename from Member m, store s where m.storeid = s.id and m.id = " + aid;
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
				int cityid = rs.getInt("cityid");
				int storeid = rs.getInt("storeid");
				String storename = rs.getString("storename");
				int cardtype = rs.getInt("cardtype");	//会员卡种类，进来默认是0非会员  1月卡，2季卡，3半年卡，4年卡,5是299新人月卡
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
				int leftcoursenum = rs.getInt("leftcoursenum");
				an = new Member(id, openid, name, wechatname, sex, height, birthday, phone, storename, cardtype, deaddate, exercisetime, exercisegoal, exercisehz, distance, bmi, muscle, fat, water, protein, basicrate, bodyage, wechatnumber, fitnesstest, memberstatus, innerfat, leftcoursenum);
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
	
	public void updateLeftcoursenum(int aid, int acardtype) {
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		String sql1 = "select leftcoursenum from member where id = " + aid;
		String sql2 = "update Member set leftcoursenum = ? where id = " +aid;
		PreparedStatement pstmt = DB.getPstmt(conn, sql2);
		try {
			rs = DB.executeQuery(stmt, sql1);
			int leftnumber=0;
			if (rs.next()) {
				leftnumber = rs.getInt("leftcoursenum");
			}
			if (acardtype==1 || acardtype==5) leftnumber+=1;
			else if (acardtype==2) leftnumber+=3;
			else if (acardtype==3) leftnumber+=6;
			else if (acardtype==4) leftnumber+=12;
			pstmt.setInt(1, leftnumber);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
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
	
	public Map<String, Integer> getPhoneVSNumber() {
		Map<String, Integer> map = new HashMap<>();
		Connection conn = DB.getConn();
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = null;
		try {
			String sql = "select recommendedphone, count(recommendedphone) as numbers from Member group by recommendedphone having recommendedphone != '' and  count(recommendedphone) >= 1";
			rs = DB.executeQuery(stmt, sql);
			while (rs.next()) {
				String recommendedphone = rs.getString("recommendedphone");
				int numbers = rs.getInt("numbers");
				map.put(recommendedphone, numbers);
			}
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return map;
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

	public void updateMemberLeftcoursenum(int memberid, int aleftcoursenum) {
		Connection conn = DB.getConn();
		String sql = "update Member set leftcoursenum = ?  where id = " + memberid;
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setInt(1, aleftcoursenum);
			pstmt.executeUpdate();
		} catch (SQLException eee) {
			eee.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
		
	}
	
}

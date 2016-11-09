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

import utils.common.DB;
import models.ComeInPassword;
import models.employee.Employee;
import models.member.Member;
import models.store.Store;
import models.useless.Announcement;
import models.useless.City;
import models.useless.StoreCity;


public class ComeInPasswordMgr {
	
	private ComeInPasswordMgr() {}
	
	private static ComeInPasswordMgr em = null;
	
	public static ComeInPasswordMgr getInstance() {
		if (em == null) {
			em = new ComeInPasswordMgr();
		}
		return em;
	}
	
	public void save(int memberid, String cdate, int[] arr,	String nowtime) {
		Connection conn = DB.getConn();
		String sql = "insert into entrancepassword values (null, ?, ?, ?, ?,?,?, ?, ?,?,?, ?, ?,?,?, ?, ?,?,?, ?, ?,?,?, ?, ?,?,?,?)";
		PreparedStatement pstmt = DB.getPstmt(conn, sql);
		try {
			pstmt.setInt(1, memberid);
			pstmt.setString(2, cdate);
			pstmt.setInt(3, arr[0]);
			pstmt.setInt(4, arr[1]);
			pstmt.setInt(5, arr[2]);
			pstmt.setInt(6, arr[3]);
			pstmt.setInt(7, arr[4]);
			pstmt.setInt(8, arr[5]);
			pstmt.setInt(9, arr[6]);
			pstmt.setInt(10, arr[7]);
			pstmt.setInt(11, arr[8]);
			pstmt.setInt(12, arr[9]);
			pstmt.setInt(13, arr[10]);
			pstmt.setInt(14, arr[11]);
			pstmt.setInt(15, arr[12]);
			pstmt.setInt(16, arr[13]);
			pstmt.setInt(17, arr[14]);
			pstmt.setInt(18, arr[15]);
			pstmt.setInt(19, arr[16]);
			pstmt.setInt(20, arr[17]);
			pstmt.setInt(21, arr[18]);
			pstmt.setInt(22, arr[19]);
			pstmt.setInt(23, arr[20]);
			pstmt.setInt(24, arr[21]);
			pstmt.setInt(25, arr[22]);
			pstmt.setInt(26, arr[23]);
			pstmt.setString(27, nowtime);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(pstmt);
			DB.close(conn);
		}
	}
	
	
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
	
	
}

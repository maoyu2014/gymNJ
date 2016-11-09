package controllers;

import play.*;
import play.data.Upload;
import play.data.validation.Required;
import play.libs.Files;
import play.mvc.*;
import play.mvc.Scope.Session;
import play.mvc.results.RenderJson;
import utils.ComeInPasswordMgr;
import utils.UserInOutInfoFromHardMgr;
import utils.common.Md5_Utils;
import utils.useless.AnnouncementMgr;
import utils.useless.CityMgr;
import utils.useless.FitnessPlanMgr;
import utils.useless.GroupWebsiteMgr;
import utils.useless.GroupbuyMgr;
import utils.useless.SocketConnect;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import models.*;
import models.book.BookExerciseMgr;
import models.course.TeamExerciseMgr;
import models.course.TeamExerciseScheduleMgr;
import models.course.TeamExerciseScheduleResultMgr;
import models.employee.Employee;
import models.employee.EmployeeMgr;
import models.finance.PurchaseHistoryMgr;
import models.front.CheckTm;
import models.front.InnerPwd;
import models.front.PwdToHard;
import models.front.PwdToHardMgr;
import models.front.UserInfoFromHard;
import models.member.DeadtimeLogMgr;
import models.member.MemberFitnessTestMgr;
import models.member.MemberMgr;
import models.member.MemberStatusMgr;
import models.store.ClassroomMgr;
import models.store.StoreMgr;
import models.useless.PrivateExerciseMgr;

public class Application extends Controller {

	//首页
    public static void index() {
        render();
    }
    
    //首页图片
    public static void indexpic() {
    	render();
    }
    
	/*
	 * ------------------------登录管理----------------------
	 */
	
    //在所有功能前检查是否是已经登录的
    @Before(unless={"login","check","getPwdInfoList","getCheckTm","userInOutInfoSndToServer","twoDimensionCodePassword"})
    public static void checkAuthentification() {
    	if (session.get("user") == null) login();
    }
    
    //登录页面
    public static void login() {
        render();
    }
    
    //判断能否登录，能登录的话保存session
    public static void check(String username, String password) {
    	if (username.length()==0 || password.length()==0) {
    		renderJSON("请输入用户名或者密码！");
    	}
    	String md5password = Md5_Utils.getMD5String(password);
    	if (EmployeeMgr.getInstance().hasUser(username)) {
    		Employee e = EmployeeMgr.getInstance().findEmployeeByUsername(username);
    		if (e.password.equals(md5password)) {
    			session.put("user", e.toString());
    			index();
    		} else {
    			renderJSON("用户名或者密码错误！");
    		}
    	} else {
    		renderJSON("用户不存在！");
    	}
    }
    
    //登出
    public static void logout() {
    	session.remove("user");
    	index();
    }
    
    //解析session
    public static HashMap<String, Object> parseSession() {
    	HashSet<Integer> sets = new HashSet<>();
    	sets.add(2);
    	sets.add(3);
    	sets.add(4);
    	sets.add(6);
    	HashMap<String, Object> maps = new HashMap<>();
    	String user = session.get("user");
    	String[] temp3 = user.split(", ");
    	for (int i=0; i<temp3.length; i++) {
    		String[] str = temp3[i].split("=");
    		if (sets.contains(i+1)) {
    			if (str.length==2)
    				maps.put(str[0], str[1]);
    			else 
    				maps.put(str[0], null);
    		}
    		else {
    			int value = Integer.parseInt(str[1]);
    			maps.put(str[0], value);
    		}
    	}
    	return maps;
	}
    
    
    /*-----------------------权限管理----------------------
    
    //店长权限
    @Before(only={"storeSetting","employeeSetting","announcementSetting","groupbuySetting","MemberSetting","BookExerciseSetting"})
    public static void ismanager() {
    	//这里再次判断，防止用户直接拼url而不是从首页进去
    	//主要是因为，这个before居然会先于checkAuthentification执行，所以没办法啊，只能手动加
    	if (session.get("user") == null) login();	
    	int ismanager = (int) parseSession().get("ismanager");
    	if (ismanager==0) renderJSON("对不起，您没有权限！");
    }
    
    //财务权限
    @Before(only={"PurchaseHistorySetting"})
    public static void isfinance() {
    	if (session.get("user") == null) login();
    	int isfinance = (int) parseSession().get("isfinance");
    	if (isfinance==0) renderJSON("对不起，您没有权限！");
    }
    
    //教练权限
    @Before(only={"fitnessPlanSetting","teamExerciseSetting","PrivateExerciseSetting"})
    public static void iscoach() {
    	if (session.get("user") == null) login();
    	int iscoach = (int) parseSession().get("iscoach");
    	if (iscoach==0) renderJSON("对不起，您没有权限！");
    }
    	
    */
    
    
    //ueditor服务端
    public static void upload(String action, File upfile) {
    	Map<String, String> result = new HashMap<>();
    	FileReader fis = null;
    	String str = "";
		try {
			fis = new FileReader("./public/ueditor/php/Copyofconfig.json");
			int x;
	    	while ((x=fis.read())!=-1) {
	    		str += (char)x;
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	JSONObject jsonObj=(JSONObject) JSONValue.parse(str);
//    	System.out.println(jsonObj);
//    	System.out.println(action);
    	
    	if (action.equals("config")) {
    		renderJSON(jsonObj);
    	}
    	else if (action.equals("uploadimage")) {
    		if (upfile!=null) {
    	    	long currentTime = System.currentTimeMillis();
    	    	String fileName = currentTime + upfile.getName();
    	    	Files.copy(upfile, Play.getFile("./public/images/" + fileName));
    	    	result.put("state", "SUCCESS");
        		result.put("url", "http://gymanage.meeour.com/public/images/" + fileName);
        		result.put("title", "fileName");
        		result.put("original", "fileName");
//        		System.out.println(fileName);
        	}
    		renderJSON(result);
    	}
    }
    
    
    
    
    
    
    
    /*
     * 传送给硬件的json密码串
     */
    
    //每个用户今天的24小时段所有密码
    public static void getPwdInfoList(String deviceId) {
    	PwdToHard pth = new PwdToHard();
    	pth.datatype = "pwdList";
    	Map<String, Object> map = PwdToHardMgr.getInstance().findPwdToHardByToday();
    	if (deviceId==null || deviceId.length()!=12 || map==null || map.get("list")==null) {
    		pth.state = "0001";
    		renderJSON(pth);
    	}
    	List<InnerPwd> list = (List<InnerPwd>) map.get("list");
    	pth.state = "0000";
    	pth.lastUpdateTime = (String) map.get("updateTime");
    	pth.josnSize = String.valueOf(list.size());
    	pth.pAList = list;
    	renderJSON(pth);
    }
    
    //本地设备校时
    public static void getCheckTm(String deviceId) {
    	CheckTm ct = new CheckTm();
    	ct.datatype="devCheckSysTm";
    	if (deviceId==null || deviceId.length()!=12) {
    		ct.state="0001";
    		renderJSON(ct);
    	}
    	ct.state="0000";
    	
    	Calendar calendar = Calendar.getInstance();  
		String today = "" + calendar.get(Calendar.YEAR)+"-";
		int month = calendar.get(Calendar.MONTH)+1; 
		if (month<10) today+="0"+month+"-"; else today += month+"-";
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day<10) today+="0"+day+" "; else today+=day+" ";
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour<10) today+="0"+hour+":"; else today+=hour+":";
		int minutes = calendar.get(Calendar.MINUTE);
		if (minutes<10) today+="0"+minutes+":"; else today+=minutes+":";
		int seconds = calendar.get(Calendar.SECOND);
		if (seconds<10) today+="0"+seconds; else today+=seconds;
		
		ct.SysTm = today;
		renderJSON(ct);
    }
    
    
    /*
     * 硬件传递过来进用户出门的数据
     */
    public static void userInOutInfoSndToServer(String deviceId, int userid, int inOutType, String inOutTm) {
    	UserInfoFromHard uifh = new UserInfoFromHard();
    	uifh.datatype = "userInOutInfoSndToServer";
    	if (deviceId==null || deviceId.length()!=12 || inOutTm==null || inOutTm.length()!=19 ||
    			(inOutType!=0 && inOutType!=1) ) {
    		uifh.state="0001";
    		renderJSON(uifh);
    	}
    	InOutInfo e = new InOutInfo(deviceId, userid, inOutType, inOutTm);
    	UserInOutInfoFromHardMgr.getInstance().save(e);
    	uifh.state="0000";
    	renderJSON(uifh);
    }
    
    
    /*
     * 巍巍传递过来的memberid和password，用来处理二维码的，目前还没有启用这个方法
     */
    public static void twoDimensionCodePassword(int memberid, String password) {
    	if (memberid==0 || password==null || password.length()==0) {
    		renderJSON("输入有问题");
    	}
    	PrintWriter out = SocketConnect.getPrintInstance();
    	out.println("come_in_id_and_password");
    	out.println(memberid);
    	out.println(password);
    	renderJSON("发送成功");
    }
    
    
    
}
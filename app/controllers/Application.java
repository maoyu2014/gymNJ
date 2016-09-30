package controllers;

import play.*;
import play.data.Upload;
import play.data.validation.Required;
import play.libs.Files;
import play.mvc.*;
import play.mvc.Scope.Session;
import play.mvc.results.RenderJson;
import utils.AnnouncementMgr;
import utils.BookExerciseMgr;
import utils.CityMgr;
import utils.ClassroomMgr;
import utils.ComeInPasswordMgr;
import utils.DeadtimeLogMgr;
import utils.EmployeeMgr;
import utils.FitnessPlanMgr;
import utils.GroupWebsiteMgr;
import utils.GroupbuyMgr;
import utils.Md5_Utils;
import utils.MemberFitnessTestMgr;
import utils.MemberMgr;
import utils.MemberStatusMgr;
import utils.PrivateExerciseMgr;
import utils.PurchaseHistoryMgr;
import utils.PwdToHardMgr;
import utils.SocketConnect;
import utils.StoreMgr;
import utils.TeamExerciseMgr;
import utils.TeamExerciseScheduleMgr;
import utils.TeamExerciseScheduleResultMgr;
import utils.UserInOutInfoFromHardMgr;

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
import models.front.CheckTm;
import models.front.InnerPwd;
import models.front.PwdToHard;
import models.front.UserInfoFromHard;

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
	 * 权限管理
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
    private static HashMap<String, Object> parseSession() {
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
//    	System.out.println(maps);
    	return maps;
	}
    
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
    	
    /*
     * 公用方法
     */
    private static String getCurrentTimeSecond() {
    	Calendar calendar = Calendar.getInstance();  
		String updatetime = ""+calendar.get(Calendar.YEAR)+"-";
		int month = calendar.get(Calendar.MONTH)+1; 
		if (month<10) updatetime+="0"+month+"-"; else updatetime += month+"-";
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day<10) updatetime+="0"+day+" "; else updatetime+=day+" ";
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour<10) updatetime+="0"+hour+":"; else updatetime+=hour+":";
		int minutes = calendar.get(Calendar.MINUTE);
		if (minutes<10) updatetime+="0"+minutes+":"; else updatetime+=minutes+":";
		int seconds = calendar.get(Calendar.SECOND);
		if (seconds<10) updatetime+="0"+seconds; else updatetime+=seconds;
		return updatetime;
    }
    
    private static String getCurrentDay() {
    	Calendar calendar = Calendar.getInstance();  
		String cdate = ""+calendar.get(Calendar.YEAR)+"-";
		int month = calendar.get(Calendar.MONTH)+1; 
		if (month<10) cdate+="0"+month+"-"; else cdate += month+"-";
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day<10) cdate+="0"+day; else cdate+=day;
    	return cdate;
    }
    
    private static String getCurrentMonth() {
    	Calendar calendar = Calendar.getInstance();  
		String cdate = ""+calendar.get(Calendar.YEAR)+"-";
		int month = calendar.get(Calendar.MONTH)+1; 
		if (month<10) cdate+="0"+month; else cdate += month;
    	return cdate;
    }
    
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
     * ·························城市city
     */
    public static void getAllCity() {
    	List<City> cities = CityMgr.getInstance().getAllCity();
        renderJSON(cities);
    }
    
    /*
     * ·························团购网站groupwebsite
     */
    public static void getAllGroupWebsite() {
    	List<GroupWebsite> lists = GroupWebsiteMgr.getInstance().getAllGroupWebsite();
        renderJSON(lists);
    }
    
    
    /*
     * ························门店设置Store
     */
    
    //查询所有门店 + 全部门店
    public static void getAllStore() {
    	List<StoreCity> listsc = StoreMgr.getInstance().getAllStore();
//    	StoreCity sc = new StoreCity();
//    	sc.id=0;
//    	sc.name = "全部门店";
//    	listsc.add(sc);
        renderJSON(listsc);
    }
    
    //门店设置(展示)页面
    public static void storeSetting() {
    	List<StoreCity> listsc = StoreMgr.getInstance().getAllStore();
    	int number = listsc.size();
        render(listsc,number);
    }
    
    //添加门店页面
    public static void addStore() {
        render();
    }
    
    //添加门店
    public static void addStoreToDB(int cityid, String name, String address,
    		double area, File photofile, String manager, String phone) {
    	String photo = null;
    	if (photofile!=null) {
	    	long currentTime = System.currentTimeMillis();
	    	String fileName = currentTime + photofile.getName();
	    	photo = fileName;
	    	//这里加不加“.”都无所谓，因为利用了play的getFile方法
	    	Files.copy(photofile, Play.getFile("public/images/" + fileName));
    	}
    	//这里一定要加“.”，而在template中展示的时候，不要加“.”
//    	File storeFile = new File("./public/uploadpic/" + fileName);
//    	Files.copy(photofile, storeFile);
    	Store s = new Store(cityid, name, address, area, photo, manager, phone);
    	StoreMgr.getInstance().save(s);
    	storeSetting();
    }
    
    //删除门店
    public static void deleteStore(int id) {
    	boolean flag = StoreMgr.getInstance().deleteStore(id);
    	if (flag) 
    		storeSetting();
    	else 
    		renderText("删除失败");
    }
    
    //门店详情(修改)页面, 同时是教室展示页面
    public static void storeDetail(int id) {
    	StoreCity sc = StoreMgr.getInstance().findStoreById(id);
    	List<Classroom> listcr = ClassroomMgr.getInstance().getAllClassroomByStoreId(id);
    	render(sc, listcr);
    }
    
    //修改门店
    public static void updateStoreToDB(int id, int cityid, String name, String address,
    		double area, File photofile, String photo, String manager, String phone) {
    	if (photofile!=null) {
	    	long currentTime = System.currentTimeMillis();
	    	String fileName = currentTime + photofile.getName();
	    	photo = fileName;
	    	Files.copy(photofile, Play.getFile("public/images/" + fileName));
    	}
    	Store s = new Store(id, cityid, name, address, area, photo, manager, phone);
    	StoreMgr.getInstance().update(s);
    	storeSetting();
    }
    
    //门店搜索功能
    public static void storeSearch(int cityid, String storename) {
    	List<StoreCity> listsc = StoreMgr.getInstance().searchStore(cityid, storename);
    	int number = listsc.size();
        render("Application/storeSetting.html", listsc,number);
    }
    
    /*
     * ·················教室管理Classroom
     */
    
    //增加教室页面
    public static void addClassroom(int storeid) {
        render(storeid);
    }
    
    //添加教室
    public static void addClassroomToDB(int storeid, String name, int holdnumber, int usage) {
    	Classroom cr = new Classroom(name, holdnumber, usage, storeid);
    	ClassroomMgr.getInstance().save(cr);
    	storeDetail(storeid);
    }
    
    //删除教室
    public static void deleteClassroom(int id, int storeid) {
    	boolean flag = ClassroomMgr.getInstance().deleteClassroom(id);
    	if (flag) 
    		storeDetail(storeid);
    	else 
    		renderText("删除失败");
    }
    
    //教室详情(修改)页面
    public static void classroomDetail(int id) {
    	Classroom cr = ClassroomMgr.getInstance().findClassroomById(id);
    	render(cr);
    }
    
    //修改教室
    public static void updateClassroomToDB(int id, int storeid, String name, int holdnumber, int usage) {
    	Classroom cr = new Classroom(id, name, holdnumber, usage, storeid);
    	ClassroomMgr.getInstance().update(cr);
    	storeDetail(storeid);
    }
    
    //返回属于某个店铺的所有'团操'教室  。。现在修改成返回某个门店的教师，前台就不用改了
    public static void getAllTuanClassroomByStoreId(int storeid) {
    	List<Classroom> list = ClassroomMgr.getInstance().getAllClassroomByStoreId(storeid);
    	/*
    	List<Classroom> lists = new ArrayList<>();
    	for (Classroom c: list) {
    		if (c.usage==1) lists.add(c);
    	}
    	*/
    	renderJSON(list);
    }
    
    //返回属于某个店铺的所有'私教'教室。。现在修改成返回某个门店的教师，前台就不用改了
    public static void getAllPrivateClassroomByStoreId(int storeid) {
    	List<Classroom> list = ClassroomMgr.getInstance().getAllClassroomByStoreId(storeid);
    	/*
    	List<Classroom> lists = new ArrayList<>();
    	for (Classroom c: list) {
    		if (c.usage==2) lists.add(c);
    	}
    	*/
    	renderJSON(list);
    }
    
    /*
     * ----------------------员工设置Employee
     */
    
    //员工设置(展示)页面
    public static void employeeSetting() {
    	List<Employee> list = EmployeeMgr.getInstance().getAllEmployee();
    	List<EmployeeShow> liste = new ArrayList<>();
    	for (Employee em : list) {
    		EmployeeShow es = new EmployeeShow(em);
    		StoreCity s = StoreMgr.getInstance().findStoreById(em.storeid);
    		if (s!=null) es.storename = s.name;
    		// 入场密码
    		ComeInPassword cp = ComeInPasswordMgr.getInstance().findComeInPasswordByMemberId(es.id);
    		if (cp!=null) {
    			Calendar calendar = Calendar.getInstance();  
    			int hour = calendar.get(Calendar.HOUR_OF_DAY);
    			es.comeinpassword  = cp.arr[hour];
    		}
    		liste.add(es);
    	}
    	int number = liste.size();
        render(liste, number);
    }
    
    //添加员工页面
    public static void addEmployee() {
        render();
    }
    
    //添加员工
    public static void addEmployeeToDB(String username, String password, String name, 
    		File headimagefile, int sex, String phone, 
    		int[] identity, int[] authority, int storeid, String introduce) {
    	if (EmployeeMgr.getInstance().hasUser(username)) {
    		renderJSON("登录账号名已经存在，请换一个！");
    	}
    	//工作人员必须选择一个门店
    	if (storeid==0) {
    		renderJSON("请选择一个门店!!!");
    	}
    	String md5password = Md5_Utils.getMD5String(password);
    	
    	int ismanager = 0;
    	int isfinance = 0;
    	int iscoach = 0;
    	
    	int domember=0;
    	int doappointment=0;
    	int docourse=0;
    	int doplan=0;
    	int domarkte=0;
    	int dofinance=0;
    	int doemployee=0;
    	int dostore=0;
    	int dostatistics=0;
    	
    	if (identity!=null) {
    		int length = identity.length;
    		for (int i=0; i<length; i++) {
    			if (identity[i]==1) ismanager=1;
    			else if (identity[i]==2) isfinance=1;
    			else if (identity[i]==3) iscoach=1;
    		}
    	}
    	if (authority!=null) {
    		int length = authority.length;
    		for (int i=0; i<length; i++) {
    			if (authority[i]==1) domember=1;
    			else if (authority[i]==2) doappointment=1;
    			else if (authority[i]==3) docourse=1;
    			else if (authority[i]==4) doplan=1;
    			else if (authority[i]==5) domarkte=1;
    			else if (authority[i]==6) dofinance=1;
    			else if (authority[i]==7) doemployee=1;
    			else if (authority[i]==8) dostore=1;
    			else if (authority[i]==9) dostatistics=1;
    		}
    	}
    	String headimage = null;
    	if (headimagefile!=null) {
    		long currentTime = System.currentTimeMillis();
	    	String fileName = currentTime + headimagefile.getName();
	    	headimage = fileName;
	    	Files.copy(headimagefile, Play.getFile("public/images/" + fileName));
    	}
    	Employee e = new Employee(username, md5password, name, headimage, sex, phone, ismanager, isfinance, iscoach, domember, doappointment, docourse, doplan, domarkte, dofinance, doemployee, dostore, dostatistics, storeid, introduce, null);
    	EmployeeMgr.getInstance().save(e);
    	//给新的员工立刻生成今日密码
    	GeneratePasswordToNewEmployee();
    	employeeSetting();
    }
    
    //给新的员工立刻生成今日密码
    private static void GeneratePasswordToNewEmployee() {
    	int a = 100000;
		int b = 999999;
		Random rand = new Random();
		int[] arr = new int[24];
		int temp = rand.nextInt(b-a)+a;
		for (int k=0; k<24; k++) arr[k] = temp;
		
		String updatetime = getCurrentTimeSecond();
		String cdate = getCurrentDay();
		int employeeid=EmployeeMgr.getInstance().getNewestEmployeeID();
		if (employeeid!=-1) {
			ComeInPasswordMgr.getInstance().save(employeeid, cdate, arr, updatetime);
		} else {
			System.out.println("没获取到最新EmployeeID");
		}
	}
    
	//删除员工
    public static void deleteEmployee(int id) {
    	boolean flag = EmployeeMgr.getInstance().deleteEmployee(id);
    	if (flag) 
    		employeeSetting();
    	else 
    		renderText("删除失败");
    }
    
    //员工详情(修改)页面
    public static void employeeDetail(int id) {
    	Employee e = EmployeeMgr.getInstance().findEmployeeById(id);
    	render(e);
    }
    
    //修改员工
    public static void updateEmployeeToDB(int id, String username, String newpassword, String password, 
    		String name, File headimagefile, String headimage, int sex, String phone, 
    		int[] identity, int[] authority, int storeid, String introduce) {
    	//工作人员必须选择一个门店
    	if (storeid==0) {
    		renderJSON("请选择一个门店!!!");
    	}
    	String md5password;
    	if (newpassword==null || newpassword.length()==0) md5password=password;
    	else md5password= Md5_Utils.getMD5String(newpassword);
    	
    	int ismanager = 0;
    	int isfinance = 0;
    	int iscoach = 0;
    	
    	int domember=0;
    	int doappointment=0;
    	int docourse=0;
    	int doplan=0;
    	int domarkte=0;
    	int dofinance=0;
    	int doemployee=0;
    	int dostore=0;
    	int dostatistics=0;
    	
//    	if (identity==null) System.out.println("identity is null");
    	if (identity!=null) {
    		int length = identity.length;
    		for (int i=0; i<length; i++) {
    			if (identity[i]==1) ismanager=1;
    			else if (identity[i]==2) isfinance=1;
    			else if (identity[i]==3) iscoach=1;
    		}
    	}
    	if (authority!=null) {
    		int length = authority.length;
    		for (int i=0; i<length; i++) {
    			if (authority[i]==1) domember=1;
    			else if (authority[i]==2) doappointment=1;
    			else if (authority[i]==3) docourse=1;
    			else if (authority[i]==4) doplan=1;
    			else if (authority[i]==5) domarkte=1;
    			else if (authority[i]==6) dofinance=1;
    			else if (authority[i]==7) doemployee=1;
    			else if (authority[i]==8) dostore=1;
    			else if (authority[i]==9) dostatistics=1;
    		}
    	}
    	if (headimagefile!=null) {
    		long currentTime = System.currentTimeMillis();
	    	String fileName = currentTime + headimagefile.getName();
	    	headimage = fileName;
	    	Files.copy(headimagefile, Play.getFile("public/images/" + fileName));
    	}
    	Employee e = new Employee(id,username, md5password, name, headimage, sex, phone, ismanager, isfinance, iscoach, domember, doappointment, docourse, doplan, domarkte, dofinance, doemployee, dostore, dostatistics, storeid, introduce, null);
    	EmployeeMgr.getInstance().update(e);
    	employeeSetting();
    }
    
    //返回给员工设置中的第一个combobox使用
//    public static void getThreeKindsEmployee() {
//    	int id;
//    	String name;
//    	String result="[";
////    	id=0; name="全部工作人员";
////    	result+="{\"id\":"+id+",\"name\":"+"\""+name+"\""+"},";
//    	id=1; name="店长";
//    	result+="{\"id\":"+id+",\"name\":"+"\""+name+"\""+"},";
//    	id=2; name="财务";
//    	result+="{\"id\":"+id+",\"name\":"+"\""+name+"\""+"},";
//    	id=3; name="教练";
//    	result+="{\"id\":"+id+",\"name\":"+"\""+name+"\""+"}]";
//    	renderJSON(result);
//    }
    
    //搜索员工
    public static void employeeSearch(int storeid, int classtype, String employeename) {
    	List<Employee> list = EmployeeMgr.getInstance().searchEmployee(storeid, classtype, employeename);
    	List<EmployeeShow> liste = new ArrayList<>();
    	for (Employee em : list) {
    		EmployeeShow es = new EmployeeShow(em);
    		StoreCity s = StoreMgr.getInstance().findStoreById(em.storeid);
    		if (s!=null) es.storename = s.name;
    		// 入场密码
    		ComeInPassword cp = ComeInPasswordMgr.getInstance().findComeInPasswordByMemberId(es.id);
    		if (cp!=null) {
    			Calendar calendar = Calendar.getInstance();  
    			int hour = calendar.get(Calendar.HOUR_OF_DAY);
    			es.comeinpassword  = cp.arr[hour];
    		}
    		liste.add(es);
    	}
    	int number = liste.size();
        render("Application/employeeSetting.html", liste, number);
    }
    
    //返回属于某个店铺的所有教练
    public static void getAllCoachByStoreID(int storeid) {
    	List<Employee> list = EmployeeMgr.getInstance().getAllEmployeeByStoreID(storeid);
    	List<Employee> lists = new ArrayList<>();
    	for (Employee e: list) {
    		if (e.iscoach==1) lists.add(e);
    	}
    	renderJSON(lists);
    }
    
    /*
     * ···············--------营销管理1······---------·门店公告Announcement
     */
    
    
    //门店公告设置(展示)页面
    public static void announcementSetting() {
    	List<Announcement> lista = AnnouncementMgr.getInstance().getAllAnnouncement();
    	List<AnnouncementShow> listad = new ArrayList<>();
    	for (Announcement aa : lista) {
    		//TODO 时间问题需要更加详细的考虑
    		String starttime = aa.starttime;
    		if (starttime!=null && starttime.length()>0) {
	    		starttime = starttime.substring(0, 4)+starttime.substring(5, 7)+starttime.substring(8, 10)+
	    				starttime.substring(11, 13)+starttime.substring(14, 16);
    		}
    		String endtime = aa.endtime;
    		if (endtime!=null && endtime.length()>0) {
	    		endtime = endtime.substring(0, 4)+endtime.substring(5, 7)+endtime.substring(8, 10)+
	    				endtime.substring(11, 13)+endtime.substring(14, 16);
    		}
    		Calendar calendar = new GregorianCalendar();
    		String nowtime = ""+calendar.get(Calendar.YEAR);
    		int month = calendar.get(Calendar.MONTH)+1; 
    		if (month<10) nowtime+="0"+month;
    		else nowtime += month;
    		int day = calendar.get(Calendar.DAY_OF_MONTH);
    		if (day<10) nowtime+="0"+day;
    		else nowtime+=day;
    		int hour = calendar.get(Calendar.HOUR_OF_DAY);
    		if (hour<10) nowtime+="0"+hour;
    		else nowtime+=hour;
    		int minutes = calendar.get(Calendar.MINUTE);
    		if (minutes<10) nowtime+="0"+minutes;
    		else nowtime+=minutes;
    		String status = "已发布";
    		if (nowtime.compareTo(starttime)>0 && nowtime.compareTo(endtime)<0) 
    			status="进行中";
    		if (nowtime.compareTo(endtime)>0)
    			status="已结束";
    		AnnouncementShow as = new AnnouncementShow(aa);
    		as.status = status;
    		//
    		StoreCity s = StoreMgr.getInstance().findStoreById(aa.storeid);
    		if (s!=null) as.storename = s.name; 
    		Employee e = EmployeeMgr.getInstance().findEmployeeById(aa.employeeid);
    		if (e!=null ) as.employeename = e.name;
    		listad.add(as);
    	}
    	int number = listad.size();
        render(listad,number);
    }
    
    //搜索门店公告
    public static void announcementSearch(int storeid, String astarttime, int astatus, String announcementname) {
    	List<Announcement> lista = AnnouncementMgr.getInstance().searchAnnouncement(storeid, astarttime, announcementname);
    	List<AnnouncementShow> listad = new ArrayList<>();
    	for (Announcement aa : lista) {
    		//TODO 时间问题需要更加详细的考虑
    		String starttime = aa.starttime;
    		if (starttime!=null && starttime.length()>0) {
	    		starttime = starttime.substring(0, 4)+starttime.substring(5, 7)+starttime.substring(8, 10)+
	    				starttime.substring(11, 13)+starttime.substring(14, 16);
    		}
    		String endtime = aa.endtime;
    		if (endtime!=null && endtime.length()>0) {
	    		endtime = endtime.substring(0, 4)+endtime.substring(5, 7)+endtime.substring(8, 10)+
	    				endtime.substring(11, 13)+endtime.substring(14, 16);
    		}
    		Calendar calendar = new GregorianCalendar();
    		String nowtime = ""+calendar.get(Calendar.YEAR);
    		int month = calendar.get(Calendar.MONTH)+1; 
    		if (month<10) nowtime+="0"+month;
    		else nowtime += month;
    		int day = calendar.get(Calendar.DAY_OF_MONTH);
    		if (day<10) nowtime+="0"+day;
    		else nowtime+=day;
    		int hour = calendar.get(Calendar.HOUR_OF_DAY);
    		if (hour<10) nowtime+="0"+hour;
    		else nowtime+=hour;
    		int minutes = calendar.get(Calendar.MINUTE);
    		if (minutes<10) nowtime+="0"+minutes;
    		else nowtime+=minutes;
    		String status = "已发布";
    		if (nowtime.compareTo(starttime)>0 && nowtime.compareTo(endtime)<0) 
    			status="进行中";
    		if (nowtime.compareTo(endtime)>0)
    			status="已结束";
    		if ( astatus==0 || astatus==1 && status.equals("进行中") || 
    				astatus==2 && status.equals("已发布") || 
    				astatus==3 && status.equals("已结束") ) {
	    		AnnouncementShow as = new AnnouncementShow(aa);
	    		as.status = status;
	    		//
	    		StoreCity s = StoreMgr.getInstance().findStoreById(aa.storeid);
	    		if (s!=null) as.storename = s.name; 
	    		Employee e = EmployeeMgr.getInstance().findEmployeeById(aa.employeeid);
	    		if (e!=null ) as.employeename = e.name;
	    		listad.add(as);
    		}
    	}
    	int number = listad.size();
        render("Application/announcementSetting.html", listad,number);
    }
    
    //添加门店公告页面
    public static void addAnnouncement() {
        render();
    }
    
    //添加门店公告
    public static void addAnnouncementToDB(String name, int storeid,
			String starttime, String endtime, String content, int employeeid) {
    	if (storeid==0) {
    		renderJSON("请选择一个门店!!!");
    	}
    	employeeid = (int) parseSession().get("id");
    	Announcement a = new Announcement(name, storeid, starttime, endtime, content, employeeid);
    	AnnouncementMgr.getInstance().save(a);
    	announcementSetting();
    }

	//删除门店公告
    public static void deleteAnnouncement(int id) {
    	boolean flag = AnnouncementMgr.getInstance().deleteAnnouncement(id);
    	if (flag) 
    		announcementSetting();
    	else 
    		renderText("删除失败");
    }
    
    //门店公告详情(修改)页面
    public static void announcementDetail(int id) {
    	Announcement sc = AnnouncementMgr.getInstance().findAnnouncementById(id);
    	render(sc);
    }
    
    //修改门店公告
    public static void updateAnnouncementToDB(int id, String name, int storeid,
			String starttime, String endtime, String content, int employeeid) {
    	if (storeid==0) {
    		renderJSON("请选择一个门店!!!");
    	}
    	employeeid = (int) parseSession().get("id");
    	Announcement a = new Announcement(id, name, storeid, starttime, endtime, content, employeeid);
    	AnnouncementMgr.getInstance().update(a);
    	announcementSetting();
    }
    
    
    
    /*
     * ···············--------营销管理2······---------·团购活动Groupbuy
     */
    
    
    //团购活动设置(展示)页面
    public static void groupbuySetting() {
    	List<Groupbuy> list = GroupbuyMgr.getInstance().getAllGroupbuy();
    	List<GroupbuyShow> lists = new ArrayList<>();
    	for (Groupbuy aa : list) {
    		//TODO 时间问题需要更加详细的考虑
    		String starttime = aa.starttime;
    		if (starttime!=null && starttime.length()>0) {
	    		starttime = starttime.substring(0, 4)+starttime.substring(5, 7)+starttime.substring(8, 10)+
	    				starttime.substring(11, 13)+starttime.substring(14, 16);
    		}
    		String endtime = aa.endtime;
    		if (endtime!=null && endtime.length()>0) {
	    		endtime = endtime.substring(0, 4)+endtime.substring(5, 7)+endtime.substring(8, 10)+
	    				endtime.substring(11, 13)+endtime.substring(14, 16);
    		}
    		Calendar calendar = new GregorianCalendar();
    		String nowtime = ""+calendar.get(Calendar.YEAR);
    		int month = calendar.get(Calendar.MONTH)+1; 
    		if (month<10) nowtime+="0"+month;
    		else nowtime += month;
    		int day = calendar.get(Calendar.DAY_OF_MONTH);
    		if (day<10) nowtime+="0"+day;
    		else nowtime+=day;
    		int hour = calendar.get(Calendar.HOUR_OF_DAY);
    		if (hour<10) nowtime+="0"+hour;
    		else nowtime+=hour;
    		int minutes = calendar.get(Calendar.MINUTE);
    		if (minutes<10) nowtime+="0"+minutes;
    		else nowtime+=minutes;
    		String status = "已发布";
    		if (nowtime.compareTo(starttime)>0 && nowtime.compareTo(endtime)<0) 
    			status="进行中";
    		if (nowtime.compareTo(endtime)>0)
    			status="已结束";
    		//---
    		GroupbuyShow as = new GroupbuyShow(aa);
    		as.status = status;
    		//
    		StoreCity s = StoreMgr.getInstance().findStoreById(aa.storeid);
    		if (s!=null) as.storename = s.name;
    		GroupWebsite g = GroupWebsiteMgr.getInstance().findGroupWebsiteById(aa.groupwebsiteid);
    		if (g!=null) as.groupwebsitename = g.name;
    		lists.add(as);
    	}
    	int number = lists.size();
        render(lists,number);
    }
    
    //团购搜索
    public static void groupbuySearch(int groupwebsiteid, int storeid, String astarttime, int astatus) {
    	List<Groupbuy> list = GroupbuyMgr.getInstance().serachGroupbuy(groupwebsiteid,storeid,astarttime);
    	List<GroupbuyShow> lists = new ArrayList<>();
    	for (Groupbuy aa : list) {
    		//TODO 时间问题需要更加详细的考虑
    		String starttime = aa.starttime;
    		if (starttime!=null && starttime.length()>0) {
	    		starttime = starttime.substring(0, 4)+starttime.substring(5, 7)+starttime.substring(8, 10)+
	    				starttime.substring(11, 13)+starttime.substring(14, 16);
    		}
    		String endtime = aa.endtime;
    		if (endtime!=null && endtime.length()>0) {
	    		endtime = endtime.substring(0, 4)+endtime.substring(5, 7)+endtime.substring(8, 10)+
	    				endtime.substring(11, 13)+endtime.substring(14, 16);
    		}
    		Calendar calendar = new GregorianCalendar();
    		String nowtime = ""+calendar.get(Calendar.YEAR);
    		int month = calendar.get(Calendar.MONTH)+1; 
    		if (month<10) nowtime+="0"+month;
    		else nowtime += month;
    		int day = calendar.get(Calendar.DAY_OF_MONTH);
    		if (day<10) nowtime+="0"+day;
    		else nowtime+=day;
    		int hour = calendar.get(Calendar.HOUR_OF_DAY);
    		if (hour<10) nowtime+="0"+hour;
    		else nowtime+=hour;
    		int minutes = calendar.get(Calendar.MINUTE);
    		if (minutes<10) nowtime+="0"+minutes;
    		else nowtime+=minutes;
    		String status = "已发布";
    		if (nowtime.compareTo(starttime)>0 && nowtime.compareTo(endtime)<0) 
    			status="进行中";
    		if (nowtime.compareTo(endtime)>0)
    			status="已结束";
    		if ( astatus==0 || astatus==1 && status.equals("进行中") || 
    				astatus==2 && status.equals("已发布") || 
    				astatus==3 && status.equals("已结束") ) {
	    		//---
	    		GroupbuyShow as = new GroupbuyShow(aa);
	    		as.status = status;
	    		//
	    		StoreCity s = StoreMgr.getInstance().findStoreById(aa.storeid);
	    		if (s!=null) as.storename = s.name;
	    		GroupWebsite g = GroupWebsiteMgr.getInstance().findGroupWebsiteById(aa.groupwebsiteid);
	    		if (g!=null) as.groupwebsitename = g.name;
	    		lists.add(as);
    		}
    	}
    	int number = lists.size();
        render("Application/groupbuySetting.html",lists,number);
    }
    
    //添加团购活动页面
    public static void addGroupbuy() {
        render();
    }
    
    //添加团购活动
    public static void addGroupbuyToDB(int groupwebsiteid, int storeid, double price, double times,
			String starttime, String endtime, String introduce, String weburl) {
    	if (groupwebsiteid==0) {
    		renderJSON("请选择一个团购网站!!!");
    	}
    	if (storeid==0) {
    		renderJSON("请选择一个门店!!!");
    	}
    	Groupbuy a = new Groupbuy(groupwebsiteid, storeid, price, times, starttime, endtime, introduce, weburl);
    	GroupbuyMgr.getInstance().save(a);
    	groupbuySetting();
    }

	//删除团购活动
    public static void deleteGroupbuy(int id) {
    	boolean flag = GroupbuyMgr.getInstance().deleteGroupbuy(id);
    	if (flag) 
    		groupbuySetting();
    	else 
    		renderText("删除失败");
    }
    
    //团购活动详情(修改)页面
    public static void groupbuyDetail(int id) {
    	Groupbuy sc = GroupbuyMgr.getInstance().findGroupbuyById(id);
    	render(sc);
    }
    
    //修改团购活动
    public static void updateGroupbuyToDB(int id, int groupwebsiteid, int storeid, double price, double times,
			String starttime, String endtime, String introduce, String weburl) {
    	if (groupwebsiteid==0) {
    		renderJSON("请选择一个团购网站!!!");
    	}
    	if (storeid==0) {
    		renderJSON("请选择一个门店!!!");
    	}
    	Groupbuy a = new Groupbuy(id, groupwebsiteid, storeid, price, times, starttime, endtime, introduce, weburl);
    	GroupbuyMgr.getInstance().update(a);
    	groupbuySetting();
    }
    
    
    /*
     * ···············--------健身计划FitnessPlan
     */
    
    
    //健身计划设置(展示)页面
    public static void fitnessPlanSetting() {
    	List<FitnessPlan> lists = FitnessPlanMgr.getInstance().getAllFitnessPlan();
    	List<FitnessPlan> list1 = new ArrayList<>();
    	List<FitnessPlan> list2 = new ArrayList<>();
    	List<FitnessPlanShow> list3 = new ArrayList<>();
    	for (FitnessPlan fp : lists) {
    		if (fp.style==1) list1.add(fp);
    		else if (fp.style==2) list2.add(fp);
    		else if (fp.style==3) {
    			FitnessPlanShow fps = new FitnessPlanShow(fp);
    			TeamExerciseSchedule tes = TeamExerciseScheduleMgr.getInstance().findTeamExerciseScheduleById(Integer.parseInt(fps.name));
    			if (tes!=null) {
    				TeamExercise te = TeamExerciseMgr.getInstance().findTeamExerciseById(tes.teamexerciseid);
    				if (te!=null) {
    					fps.teamexercisename = te.name;
    				}
    			}
    			list3.add(fps);
    		}
    	}
    	int num1 = list1.size(), num2 = list2.size(), num3 = list3.size();
        render(list1,list2,list3,num1,num2,num3);
    }
    
    //添加健身计划页面  种类1
    public static void addFitnessPlan(int style) {
        render(style);
    }
    
    //添加健身计划页面  种类2
    public static void addFitnessPlan2(int style) {
        render(style);
    }
    
    //添加健身计划
    public static void addFitnessPlanToDB(int style, String name, int fitsex, int[] parts,
    		double geshu1, double geshu2, double geshu3, double geshu4, int geshu5,
    		double fenzhong1, double fenzhong2, double fenzhong3, double fenzhong4, int fenzhong5,
    		double keshu1, double keshu2, double keshu3, double keshu4, int keshu5) {
    	System.out.println("style= "+style);
    	String ans_parts = "";
    	String number = "";
    	if (parts!=null) {
    		int len = parts.length;
    		for (int i=0; i<len; i++) {
    			ans_parts +=parts[i]+",";
    		}
    	}
    	number = geshu1+","+geshu2+","+geshu3+","+geshu4+","+geshu5+","+
    			fenzhong1+","+fenzhong2+","+fenzhong3+","+fenzhong4+","+fenzhong5+","+
    			keshu1+","+keshu2+","+keshu3+","+keshu4+","+keshu5+",";
    	FitnessPlan a = new FitnessPlan(style, name, fitsex, ans_parts, number);
    	FitnessPlanMgr.getInstance().save(a);
    	fitnessPlanSetting();
    }

	//删除健身计划
    public static void deleteFitnessPlan(int id) {
    	boolean flag = FitnessPlanMgr.getInstance().deleteFitnessPlan(id);
    	if (flag) 
    		fitnessPlanSetting();
    	else 
    		renderText("删除失败");
    }
    
    //健身计划详情(修改)  页面1
    public static void fitnessPlanDetail(int id) {
    	FitnessPlan s = FitnessPlanMgr.getInstance().findFitnessPlanById(id);
    	FitnessPlanShow sc  = new FitnessPlanShow(s);
    	String parts = sc.parts;
    	String number = sc.number;
    	if (parts!=null && !parts.equals("")) {
	    	String[] str = parts.split(",");
	    	for (String ss : str) {
	    		int temp = Integer.parseInt(ss);
	    		if (temp==1) sc.one=1;
	    		if (temp==2) sc.two=1;
	    		if (temp==3) sc.three=1;
	    		if (temp==4) sc.four=1;
	    		if (temp==5) sc.five=1;
	    	}
    	}
    	if (number!=null) {
	    	String[] str = number.split(",");
	    	for (int i=0; i<str.length; i++) {
	    		String ss = str[i];
	    		if (i==0) sc.geshu1 = Double.parseDouble(ss);
	    		if (i==1) sc.geshu2 = Double.parseDouble(ss);
	    		if (i==2) sc.geshu3 = Double.parseDouble(ss);
	    		if (i==3) sc.geshu4 = Double.parseDouble(ss);
	    		if (i==4) sc.geshu5 = Integer.parseInt(ss);
	    		
	    		if (i==5) sc.fenzhong1 = Double.parseDouble(ss);
	    		if (i==6) sc.fenzhong2 = Double.parseDouble(ss);
	    		if (i==7) sc.fenzhong3 = Double.parseDouble(ss);
	    		if (i==8) sc.fenzhong4 = Double.parseDouble(ss);
	    		if (i==9) sc.fenzhong5 = Integer.parseInt(ss);
	    		
	    		if (i==10) sc.keshu1 = Double.parseDouble(ss);
	    		if (i==11) sc.keshu2 = Double.parseDouble(ss);
	    		if (i==12) sc.keshu3 = Double.parseDouble(ss);
	    		if (i==13) sc.keshu4 = Double.parseDouble(ss);
	    		if (i==14) sc.keshu5 = Integer.parseInt(ss);
	    	}
    	}
    	render(sc);
    }
    
    //健身计划详情(修改)  页面2
    public static void fitnessPlanDetail2(int id) {
    	FitnessPlan s = FitnessPlanMgr.getInstance().findFitnessPlanById(id);
    	FitnessPlanShow sc  = new FitnessPlanShow(s);
    	String number = sc.number;
    	if (number!=null) {
	    	String[] str = number.split(",");
	    	for (int i=0; i<str.length; i++) {
	    		String ss = str[i];
	    		if (i==0) sc.geshu1 = Double.parseDouble(ss);
	    		if (i==1) sc.geshu2 = Double.parseDouble(ss);
	    		if (i==2) sc.geshu3 = Double.parseDouble(ss);
	    		if (i==3) sc.geshu4 = Double.parseDouble(ss);
	    		if (i==4) sc.geshu5 = Integer.parseInt(ss);
	    		
	    		if (i==5) sc.fenzhong1 = Double.parseDouble(ss);
	    		if (i==6) sc.fenzhong2 = Double.parseDouble(ss);
	    		if (i==7) sc.fenzhong3 = Double.parseDouble(ss);
	    		if (i==8) sc.fenzhong4 = Double.parseDouble(ss);
	    		if (i==9) sc.fenzhong5 = Integer.parseInt(ss);
	    		
	    		if (i==10) sc.keshu1 = Double.parseDouble(ss);
	    		if (i==11) sc.keshu2 = Double.parseDouble(ss);
	    		if (i==12) sc.keshu3 = Double.parseDouble(ss);
	    		if (i==13) sc.keshu4 = Double.parseDouble(ss);
	    		if (i==14) sc.keshu5 = Integer.parseInt(ss);
	    	}
    	}
    	render(sc);
    }
    
    //修改健身计划
    public static void updateFitnessPlanToDB(int id, int style, String name, int fitsex, int[] parts,
    		double geshu1, double geshu2, double geshu3, double geshu4, int geshu5,
    		double fenzhong1, double fenzhong2, double fenzhong3, double fenzhong4, int fenzhong5,
    		double keshu1, double keshu2, double keshu3, double keshu4, int keshu5) {
    	String ans_parts = "";
    	String number = "";
    	if (parts!=null) {
    		int len = parts.length;
    		for (int i=0; i<len; i++) {
    			ans_parts +=parts[i]+",";
    		}
    	}
    	number = geshu1+","+geshu2+","+geshu3+","+geshu4+","+geshu5+","+
    			fenzhong1+","+fenzhong2+","+fenzhong3+","+fenzhong4+","+fenzhong5+","+
    			keshu1+","+keshu2+","+keshu3+","+keshu4+","+keshu5+",";
    	FitnessPlan a = new FitnessPlan(id, style, name, fitsex, ans_parts, number);
    	FitnessPlanMgr.getInstance().update(a);
    	fitnessPlanSetting();
    }
    
    
    /*
     * ···············--------课程管理1-----------团操课程TeamExercise
     */
    
    /*
    //就得四个Tab那个，已废弃
    public static void teamExerciseSetting() {
    	List<TeamExercise> lists = TeamExerciseMgr.getInstance().getAllTeamExercise();
    	String yearmonth = getCurrentMonth();
    	List<TeamExerciseSchedule> listss = TeamExerciseScheduleMgr.getInstance().getAllTeamExerciseScheduleByYearMonth(yearmonth);
    	List<TeamExerciseScheduleShow> listtt = new ArrayList<>();
    	for (TeamExerciseSchedule tes : listss) {
    		TeamExerciseScheduleShow tess = new TeamExerciseScheduleShow(tes);
    		StoreCity s = StoreMgr.getInstance().findStoreById(tes.storeid);
    		if (s!=null) tess.storename = s.name;
    		Employee e = EmployeeMgr.getInstance().findEmployeeById(tes.employeeid);
    		if (e!=null) tess.employeename = e.name;
    		Classroom c = ClassroomMgr.getInstance().findClassroomById(tes.classroomid);
    		if (c!=null) tess.classroomname = c.name;
    		TeamExercise t = TeamExerciseMgr.getInstance().findTeamExerciseById(tes.teamexerciseid);
    		if (t!=null) tess.teamexercisename = t.name;
    		listtt.add(tess);
    	}
    	//周课表 同样代码有两份
    	Map<Integer, List<TeamExerciseScheduleShow> > mapweek = new HashMap<>();
    	for (int i=1; i<=7; i++) mapweek.put(i, new ArrayList<>());
    	Calendar calendar =  Calendar.getInstance();
    	int[] dir = new int[] {0,7,1,2,3,4,5,6};
    	int x  =calendar.get(Calendar.DAY_OF_WEEK);
    	int xx = dir[x];
    	
    	calendar.add(Calendar.DAY_OF_YEAR, -xx+1);
		String mybegintime = ""+calendar.get(Calendar.YEAR)+"-";
		int month = calendar.get(Calendar.MONTH)+1; 
		if (month<10) mybegintime+="0"+month+"-";
		else mybegintime += month+"-";
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day<10) mybegintime+="0"+day;
		else mybegintime+=day;
//		System.out.println(mybegintime);
		
		calendar.add(Calendar.DAY_OF_MONTH, 6);
		String myendtime = ""+calendar.get(Calendar.YEAR)+"-";
		month = calendar.get(Calendar.MONTH)+1; 
		if (month<10) myendtime+="0"+month+"-";
		else myendtime += month+"-";
		day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day<10) myendtime+="0"+day;
		else myendtime+=day;
//		System.out.println(myendtime);
		
		for (TeamExerciseScheduleShow tess : listtt) {
    		String strday = tess.begintime.substring(0, 10);
    		if (strday.compareTo(mybegintime)>=0 && strday.compareTo(myendtime)<=0) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
					Date date = dateFormat.parse(strday);
					calendar.setTime(date); 
				} catch (ParseException e1) {
					e1.printStackTrace();
				}  
				x  =calendar.get(Calendar.DAY_OF_WEEK);
		    	xx = dir[x];
        		mapweek.get(xx).add(tess);
    		}
    	}
		//月课表 同样代码有两份
		Map<Integer, List<TeamExerciseScheduleShow> > mapmonth = new HashMap<>();
		for (int i=1; i<=35; i++) mapmonth.put(i, new ArrayList<>());	//35是为了配合前台5行7列
    	calendar =  Calendar.getInstance();
    	mybegintime = ""+calendar.get(Calendar.YEAR)+"-";
    	myendtime = ""+calendar.get(Calendar.YEAR)+"-";
    	month = calendar.get(Calendar.MONTH)+1; 
    	if (month<10) mybegintime+="0"+month+"-"; else mybegintime += month+"-";
    	if (month<10) myendtime+="0"+month+"-"; else myendtime += month+"-";
    	mybegintime+="01";
    	myendtime+="31";
    	
    	try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
			Date date = dateFormat.parse(mybegintime);
			calendar.setTime(date); 
		} catch (ParseException e1) {
			e1.printStackTrace();
		}  
		x  =calendar.get(Calendar.DAY_OF_WEEK);
		int num = dir[x];
    	for (TeamExerciseScheduleShow tess : listtt) {
    		String strday = tess.begintime.substring(0, 10);
    		if (strday.compareTo(mybegintime)>=0 && strday.compareTo(myendtime)<=0) {
    			xx = Integer.parseInt(strday.substring(8, 10));
        		mapmonth.get(xx).add(tess);
    		}
    	}
    	int aaa = lists.size();
    	int bbb = listtt.size();
    	render(lists, listtt, aaa, bbb, mapweek, mapmonth, num);
    }
    */
    
    //本月团操课程
    public static void teamExerciseMonth() {
    	String yearmonth = getCurrentMonth();
    	List<TeamExerciseSchedule> list = TeamExerciseScheduleMgr.getInstance().getAllTeamExerciseScheduleByYearMonth(yearmonth);
		Map<Integer, List<TeamExerciseSchedule> > mapmonth = new HashMap<>();
		for (int i=1; i<=35; i++) mapmonth.put(i, new ArrayList<>());	//35是为了配合前台5行7列
		Calendar calendar =  Calendar.getInstance();
		int[] dir = new int[] {0,7,1,2,3,4,5,6};
    	String mybegintime = ""+calendar.get(Calendar.YEAR)+"-";
    	String myendtime = ""+calendar.get(Calendar.YEAR)+"-";
    	int month = calendar.get(Calendar.MONTH)+1; 
    	if (month<10) mybegintime+="0"+month+"-"; else mybegintime += month+"-";
    	if (month<10) myendtime+="0"+month+"-"; else myendtime += month+"-";
    	mybegintime+="01";
    	myendtime+="31";
    	
    	try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
			Date date = dateFormat.parse(mybegintime);
			calendar.setTime(date); 
		} catch (ParseException e1) {
			e1.printStackTrace();
		}  
		int x  =calendar.get(Calendar.DAY_OF_WEEK);
		int num = dir[x];
    	for (TeamExerciseSchedule tess : list) {
    		String strday = tess.begintime.substring(0, 10);
    		if (strday.compareTo(mybegintime)>=0 && strday.compareTo(myendtime)<=0) {
    			int xx = Integer.parseInt(strday.substring(8, 10));
        		mapmonth.get(xx).add(tess);
    		}
    	}
    	render(mapmonth, num);
    }
    
    //本周团操课程
    public static void teamExerciseWeek() {
    	String yearmonth = getCurrentMonth();
    	List<TeamExerciseSchedule> list = TeamExerciseScheduleMgr.getInstance().getAllTeamExerciseScheduleByYearMonth(yearmonth);
    	Map<Integer, List<TeamExerciseSchedule> > mapweek = new HashMap<>();
    	for (int i=1; i<=7; i++) mapweek.put(i, new ArrayList<>());
    	Calendar calendar =  Calendar.getInstance();
    	int[] dir = new int[] {0,7,1,2,3,4,5,6};
    	int x  =calendar.get(Calendar.DAY_OF_WEEK);
    	int xx = dir[x];
    	
    	calendar.add(Calendar.DAY_OF_YEAR, -xx+1);
		String mybegintime = ""+calendar.get(Calendar.YEAR)+"-";
		int month = calendar.get(Calendar.MONTH)+1; 
		if (month<10) mybegintime+="0"+month+"-";
		else mybegintime += month+"-";
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day<10) mybegintime+="0"+day;
		else mybegintime+=day;
//		System.out.println(mybegintime);
		
		calendar.add(Calendar.DAY_OF_MONTH, 6);
		String myendtime = ""+calendar.get(Calendar.YEAR)+"-";
		month = calendar.get(Calendar.MONTH)+1; 
		if (month<10) myendtime+="0"+month+"-";
		else myendtime += month+"-";
		day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day<10) myendtime+="0"+day;
		else myendtime+=day;
//		System.out.println(myendtime);
		
		for (TeamExerciseSchedule tess : list) {
    		String strday = tess.begintime.substring(0, 10);
    		if (strday.compareTo(mybegintime)>=0 && strday.compareTo(myendtime)<=0) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
					Date date = dateFormat.parse(strday);
					calendar.setTime(date); 
				} catch (ParseException e1) {
					e1.printStackTrace();
				}  
				x  =calendar.get(Calendar.DAY_OF_WEEK);
		    	xx = dir[x];
        		mapweek.get(xx).add(tess);
    		}
    	}
    	render(mapweek);
    }
    
    //查看团操报名学员
    public static void teamExerciseScheduleBookMember(int id) {
    	List<Member> list = BookExerciseMgr.getInstance().getTeamExerciseScheduleBookMember(id);
    	int num = list.size();
    	render(list, num);
    }
    
    //写入本次团操课程锻炼结果
    public static void teamExerciseScheduleResultWriteIn(int id) {
    	TeamExerciseScheduleResultInfo aa = TeamExerciseScheduleResultMgr.getInstance().getInfo(id);
    	List<TeamExerciseScheduleResultDetail> anlist = TeamExerciseScheduleResultMgr.getInstance().getDetail(id);
    	List<Member> templist = BookExerciseMgr.getInstance().getTeamExerciseScheduleBookMember(id);
    	int number = templist.size();
    	List<Member> list = new ArrayList<>();
    	if (anlist.size()==0) {
    		anlist = null;
    		list = templist;
    	} else {
    		for (int i=0; i<number; i++) {
    			int memberid = anlist.get(i).memberid;
    			for (int j=0; j<number; j++) {
    				int aid = templist.get(j).id;
    				if (aid==memberid) {
    					list.add(templist.get(j));
    					break;
    				}
    			}
    		}
    	}
    	render(list, id, number, aa, anlist);
    }
    
    public static void addTeamExerciseScheduleResultToDB(int id, List<Member> list) {
    	String yundong1 = params.get("yundong1");
    	String yundong2 = params.get("yundong2");
    	String yundong3 = params.get("yundong3");
    	String yundong4 = params.get("yundong4");
    	String yundongliang = params.get("yundongliang");
    	String one2three = params.get("one2three");
    	String four2six = params.get("four2six");
    	String seven212 = params.get("seven212");
    	if (TeamExerciseScheduleResultMgr.getInstance().hasInfo(id)) {
    		TeamExerciseScheduleResultMgr.getInstance().updateInfo(id, yundong1, yundong2, yundong3, yundong4,
        			yundongliang, one2three, four2six, seven212);
    	} else {
    		TeamExerciseScheduleResultMgr.getInstance().saveInfo(id, yundong1, yundong2, yundong3, yundong4,
    			yundongliang, one2three, four2six, seven212);
    	}
    	TeamExerciseScheduleResultInfo aa = new TeamExerciseScheduleResultInfo(id, yundong1, yundong2, yundong3, yundong4, yundongliang, one2three, four2six, seven212);
    	int number = list.size();
    	List<TeamExerciseScheduleResultDetail> anlist = new ArrayList<>();
    	for (int i=1; i<=number; i++) {
    		int memberid = list.get(i-1).id;
    		String shijian1 = params.get(i+"shijian1");
    		String shijian2 = params.get(i+"shijian2");
    		String shijian3 = params.get(i+"shijian3");
    		String shijian4 = params.get(i+"shijian4");
    		String shijian5 = params.get(i+"shijian5");
    		String shijian6 = params.get(i+"shijian6");
    		String shijian7 = params.get(i+"shijian7");
    		String shijian8 = params.get(i+"shijian8");
    		if (TeamExerciseScheduleResultMgr.getInstance().hasDetail(id, memberid)) {
    			TeamExerciseScheduleResultMgr.getInstance().updateDetail(id, memberid, shijian1, shijian2,
        				shijian3, shijian4, shijian5, shijian6, shijian7, shijian8);
    		} else {
    			TeamExerciseScheduleResultMgr.getInstance().saveDetail(id, memberid, shijian1, shijian2,
    				shijian3, shijian4, shijian5, shijian6, shijian7, shijian8);
    		}
    		TeamExerciseScheduleResultDetail bb = new TeamExerciseScheduleResultDetail(id, memberid, shijian1, shijian2, shijian3, shijian4, shijian5, shijian6, shijian7, shijian8);
    		anlist.add(bb);
    	}
    	
    	render("Application/teamExerciseScheduleResultWriteIn.html",id, list, number, aa, anlist);
    }
    
    //团操
    public static void teamExerciseBasic() {
    	List<TeamExercise> lists = TeamExerciseMgr.getInstance().getAllTeamExercise();
    	int number = lists.size();
    	render(lists, number);
    }
    
    //添加团操课程页面
    public static void addTeamExercise() {
        render();
    }
    
    //添加团操课程
    public static void addTeamExerciseToDB(String name, File imagefile, double usedtime, int strength,
    		String parts, String introduce, String notice) {
    	String image=null;
    	if (imagefile!=null) {
    		long currentTime = System.currentTimeMillis();
	    	String fileName = currentTime + imagefile.getName();
	    	image = fileName;
	    	Files.copy(imagefile, Play.getFile("public/images/" + fileName));
    	}
    	TeamExercise a = new TeamExercise(name, image, usedtime, strength, parts, introduce, notice);
    	TeamExerciseMgr.getInstance().save(a);
    	teamExerciseBasic();
    }

	//删除团操课程
    public static void deleteTeamExercise(int id) {
    	boolean flag = TeamExerciseMgr.getInstance().deleteTeamExercise(id);
    	if (flag) 
    		teamExerciseBasic();
    	else 
    		renderText("删除失败");
    }
    
    //团操课程详情(修改)页面
    public static void teamExerciseDetail(int id) {
    	TeamExercise s = TeamExerciseMgr.getInstance().findTeamExerciseById(id);
    	TeamExerciseShow sc  = new TeamExerciseShow(s);
    	String parts = sc.parts;
    	if (parts!=null && !parts.equals("")) {
	    	String[] str = parts.split(", ");
	    	for (String ss : str) {
	    		int temp = Integer.parseInt(ss);
	    		if (temp==1) sc.one=1;
	    		if (temp==2) sc.two=1;
	    		if (temp==3) sc.three=1;
	    		if (temp==4) sc.four=1;
	    		if (temp==5) sc.five=1;
	    	}
    	}
    	render(sc);
    }
    
    //修改团操课程
    public static void updateTeamExerciseToDB(int id, String name, File imagefile, String image, 
    		double usedtime, int strength,
    		String parts, String introduce, String notice) {
    	if (imagefile!=null) {
    		long currentTime = System.currentTimeMillis();
	    	String fileName = currentTime + imagefile.getName();
	    	image = fileName;
	    	Files.copy(imagefile, Play.getFile("public/images/" + fileName));
    	}
    	TeamExercise a = new TeamExercise(id, name, image, usedtime, strength, parts, introduce, notice);
    	TeamExerciseMgr.getInstance().update(a);
    	teamExerciseBasic();
    }
    
    //获得所有团操课程的json串
    public static void getAllTeamExercise() {
    	List<TeamExercise> lists = TeamExerciseMgr.getInstance().getAllTeamExercise();
        renderJSON(lists);
    }
    
    
    /*
     * ···············--------课程管理1-----------团操排期TeamExerciseSchedule
     */
    
    //团操排期
    public static void teamExerciseSchedule() {
    	String yearmonth = getCurrentMonth();
    	List<TeamExerciseSchedule> list = TeamExerciseScheduleMgr.getInstance().getAllTeamExerciseScheduleByYearMonth(yearmonth);
    	int number = list.size();
    	render(list, number);
    }
    
    //搜索团操排期
    public static void TeamExerciseScheduleSearch(int storeid, int employeeid, String yearmonth) {
    	List<TeamExerciseSchedule> list = TeamExerciseScheduleMgr.getInstance().searchTeamExerciseSchedule(storeid,employeeid,yearmonth);
    	int number = list.size();
    	render("Application/teamExerciseSchedule.html", list, number);
    }
    
    //TODO 关于同一个教练教室不能一个时间段有两个课程
    //添加团操排期页面
    public static void addTeamExerciseSchedule() {
        render();
    }
    
    //添加新增的团操排期
    public static void addTeamExerciseScheduleToDB(int storeid,int classroomid,int employeeid,int teamexerciseid,
    		int num, String begintime1, String endtime1, String begintime2, String endtime2,
    		String begintime3, String endtime3, String begintime4, String endtime4, String begintime5, String endtime5,
    		int consumenum) {
    	if (storeid==0) {
    		renderJSON("必须选择一个门店");
    	}
    	if (classroomid==0) {
    		renderJSON("必须选择一个教室");
    	}
    	if (employeeid==0) {
    		renderJSON("必须选择一个教练");
    	}
    	if (teamexerciseid==0) {
    		renderJSON("必须选择一个课程");
    	}
    	if (! begintime1.substring(0, 10).equals(endtime1.substring(0, 10)) ) {
    		renderJSON("第一行课程必须是同一天的不同时间段，不能跨天!");
    	}
    	if (begintime2!=null && endtime2!=null && begintime2.length()>0 && endtime2.length()>0) {
    		if (! begintime2.substring(0, 10).equals(endtime2.substring(0, 10)) ) {
        		renderJSON("第二行必须是同一天的不同时间段，不能跨天!");
        	}
    	}
    	if (begintime3!=null && endtime3!=null && begintime3.length()>0 && endtime3.length()>0) {
    		if (! begintime3.substring(0, 10).equals(endtime3.substring(0, 10)) ) {
        		renderJSON("第三行必须是同一天的不同时间段，不能跨天!");
        	}
    	}
    	if (begintime4!=null && endtime4!=null && begintime4.length()>0 && endtime4.length()>0) {
    		if (! begintime4.substring(0, 10).equals(endtime4.substring(0, 10)) ) {
        		renderJSON("第四行必须是同一天的不同时间段，不能跨天!");
        	}
    	}
    	if (begintime5!=null && endtime5!=null && begintime5.length()>0 && endtime5.length()>0) {
    		if (! begintime5.substring(0, 10).equals(endtime5.substring(0, 10)) ) {
        		renderJSON("第五行必须是同一天的不同时间段，不能跨天!");
        	}
    	}
    	TeamExerciseSchedule a = new TeamExerciseSchedule(storeid, classroomid, employeeid, teamexerciseid, num, 0, begintime1, endtime1, consumenum);
    	TeamExerciseScheduleMgr.getInstance().save(a);
    	if (begintime2!=null && endtime2!=null && begintime2.length()>0 && endtime2.length()>0) {
    		TeamExerciseSchedule aa = new TeamExerciseSchedule(storeid, classroomid, employeeid, teamexerciseid, num, 0, begintime2, endtime2,consumenum);
        	TeamExerciseScheduleMgr.getInstance().save(aa);
    	}
    	if (begintime3!=null && endtime3!=null && begintime3.length()>0 && endtime3.length()>0) {
    		TeamExerciseSchedule aa = new TeamExerciseSchedule(storeid, classroomid, employeeid, teamexerciseid, num, 0, begintime3, endtime3,consumenum);
        	TeamExerciseScheduleMgr.getInstance().save(aa);
    	}
    	if (begintime4!=null && endtime4!=null && begintime4.length()>0 && endtime4.length()>0) {
    		TeamExerciseSchedule aa = new TeamExerciseSchedule(storeid, classroomid, employeeid, teamexerciseid, num, 0, begintime4, endtime4,consumenum);
        	TeamExerciseScheduleMgr.getInstance().save(aa);
    	}
    	if (begintime5!=null && endtime5!=null && begintime5.length()>0 && endtime5.length()>0) {
    		TeamExerciseSchedule aa = new TeamExerciseSchedule(storeid, classroomid, employeeid, teamexerciseid, num, 0, begintime5, endtime5,consumenum);
        	TeamExerciseScheduleMgr.getInstance().save(aa);
    	}
    	teamExerciseSchedule();
    }

    //团操复制页面
    public static void TeamExerciseScheduleCopy(int id) {
    	TeamExerciseSchedule sc = TeamExerciseScheduleMgr.getInstance().findTeamExerciseScheduleById(id);
    	render(sc);
    }
    
    //添加复制的团操排期
    public static void addCopyTeamExerciseScheduleToDB(int storeid,int classroomid,int employeeid,int teamexerciseid,
    		int num, String begintime, String endtime, int consumenum) {
    	if (storeid==0) {
    		renderJSON("必须选择一个门店");
    	}
    	if (classroomid==0) {
    		renderJSON("必须选择一个教室");
    	}
    	if (employeeid==0) {
    		renderJSON("必须选择一个教练");
    	}
    	if (teamexerciseid==0) {
    		renderJSON("必须选择一个课程");
    	}
    	if (! begintime.substring(0, 10).equals(endtime.substring(0, 10)) ) {
    		renderJSON("第一行课程必须是同一天的不同时间段，不能跨天!");
    	}
    	TeamExerciseSchedule a = new TeamExerciseSchedule(storeid, classroomid, employeeid, teamexerciseid, num, 0, begintime, endtime, consumenum);
    	TeamExerciseScheduleMgr.getInstance().save(a);
    	teamExerciseSchedule();
    }
    
	//删除团操排期
    public static void deleteTeamExerciseSchedule(int id) {
    	boolean flag = TeamExerciseScheduleMgr.getInstance().deleteTeamExerciseSchedule(id);
    	if (flag) 
    		teamExerciseSchedule();
    	else 
    		renderText("删除失败");
    }
    
    //团操排期详情(修改)页面
    public static void TeamExerciseScheduleDetail(int id) {
    	TeamExerciseSchedule sc = TeamExerciseScheduleMgr.getInstance().findTeamExerciseScheduleById(id);
    	render(sc);
    }
    
    //修改团操排期
    public static void updateTeamExerciseScheduleToDB(int id, int storeid,int classroomid,int employeeid,int teamexerciseid,
    		int num, int oknum, String begintime, String endtime, int consumenum) {
    	if (storeid==0) {
    		renderJSON("必须选择一个门店");
    	}
    	if (classroomid==0) {
    		renderJSON("必须选择一个教室");
    	}
    	if (employeeid==0) {
    		renderJSON("必须选择一个教练");
    	}
    	if (teamexerciseid==0) {
    		renderJSON("必须选择一个课程");
    	}
    	if (! begintime.substring(0, 10).equals(endtime.substring(0, 10)) ) {
    		renderJSON("必须是同一天的不同时间段，不能跨天!");
    	}
    	TeamExerciseSchedule a = new TeamExerciseSchedule(id, storeid, classroomid, employeeid, teamexerciseid, num, oknum, begintime, endtime, consumenum);
    	TeamExerciseScheduleMgr.getInstance().update(a);
    	teamExerciseSchedule();
    }
    
    //获得所有团操排期的可读性结果
    public static void getAllTeamExerciseScheduleAsJson() {
    	List<TeamExerciseSchedule> listss = TeamExerciseScheduleMgr.getInstance().getAllTeamExerciseSchedule();
    	List<TeamExerciseScheduleJson> listtesj = new ArrayList<>();
    	for (TeamExerciseSchedule tes : listss) {
    		TeamExerciseScheduleJson tesj = new TeamExerciseScheduleJson();
    		tesj.id = tes.id;
    		tesj.name="";
    		TeamExercise t = TeamExerciseMgr.getInstance().findTeamExerciseById(tes.teamexerciseid);
    		if (t!=null) tesj.name += t.name+"、";
    		StoreCity s = StoreMgr.getInstance().findStoreById(tes.storeid);
    		if (s!=null) tesj.name += s.name+"、";
    		Classroom c = ClassroomMgr.getInstance().findClassroomById(tes.classroomid);
    		if (c!=null) tesj.name += c.name+"、";
    		Employee e = EmployeeMgr.getInstance().findEmployeeById(tes.employeeid);
    		if (e!=null) tesj.name += e.name;
    		listtesj.add(tesj);
    	}
    	renderJSON(listtesj);
    }
    
    
    /*
     * ···············--------课程管理2-----------特训班PrivateExercise
     */
    
    
    //特训班PrivateExercise设置（展示）页面
    public static void PrivateExerciseSetting() {
    	List<PrivateExercise> list = PrivateExerciseMgr.getInstance().getAllPrivateExercise();
    	for (PrivateExercise p : list) {
    		String signbegintime = p.signbegintime;
    		String signendtime = p.signendtime;
    		String classbegintime = p.classbegintime;
    		String classendtime = p.classendtime;
    		if (signbegintime!=null && signbegintime.length()>0) {
	    		signbegintime = signbegintime.substring(0, 4)+signbegintime.substring(5, 7)
	    				+signbegintime.substring(8, 10);
    		}
    		if (signendtime!=null && signbegintime.length()>0) {
	    		signendtime = signendtime.substring(0, 4)+signendtime.substring(5, 7)
	    				+signendtime.substring(8, 10);
    		}
    		if (classbegintime!=null && signbegintime.length()>0) {
	    		classbegintime = classbegintime.substring(0, 4)+classbegintime.substring(5, 7)
	    				+classbegintime.substring(8, 10);
    		}
    		if (classendtime!=null && signbegintime.length()>0) {
	    		classendtime = classendtime.substring(0, 4)+classendtime.substring(5, 7)
	    				+classendtime.substring(8, 10);
    		}
    		Calendar calendar = new GregorianCalendar();
    		String nowtime = ""+calendar.get(Calendar.YEAR);
    		int month = calendar.get(Calendar.MONTH)+1; 
    		if (month<10) nowtime+="0"+month;
    		else nowtime += month;
    		int day = calendar.get(Calendar.DAY_OF_MONTH);
    		if (day<10) nowtime+="0"+day;
    		else nowtime+=day;
    		
    		String status = "已发布";
    		if (nowtime.compareTo(signbegintime)>=0 ) status="报名中";
    		if (nowtime.compareTo(classbegintime)>=0 ) status="开课中";
    		if (nowtime.compareTo(classendtime)>0 ) status="已结束";
    		p.status = status;
    	}
    	int number = list.size();
    	render(list, number);
    }
    
    //搜索特训班
    public static void PrivateExerciseSearch(int storeid, int employeeid, String astatus, String privateexercisename) {
    	List<PrivateExercise> listpe = PrivateExerciseMgr.getInstance().searchPrivateExercise(storeid, employeeid, privateexercisename);
    	List<PrivateExercise> list = new ArrayList<>();
    	for (PrivateExercise p : listpe) {
    		String signbegintime = p.signbegintime;
    		String signendtime = p.signendtime;
    		String classbegintime = p.classbegintime;
    		String classendtime = p.classendtime;
    		if (signbegintime!=null && signbegintime.length()>0) {
	    		signbegintime = signbegintime.substring(0, 4)+signbegintime.substring(5, 7)
	    				+signbegintime.substring(8, 10);
    		}
    		if (signendtime!=null && signbegintime.length()>0) {
	    		signendtime = signendtime.substring(0, 4)+signendtime.substring(5, 7)
	    				+signendtime.substring(8, 10);
    		}
    		if (classbegintime!=null && signbegintime.length()>0) {
	    		classbegintime = classbegintime.substring(0, 4)+classbegintime.substring(5, 7)
	    				+classbegintime.substring(8, 10);
    		}
    		if (classendtime!=null && signbegintime.length()>0) {
	    		classendtime = classendtime.substring(0, 4)+classendtime.substring(5, 7)
	    				+classendtime.substring(8, 10);
    		}
    		Calendar calendar = new GregorianCalendar();
    		String nowtime = ""+calendar.get(Calendar.YEAR);
    		int month = calendar.get(Calendar.MONTH)+1; 
    		if (month<10) nowtime+="0"+month;
    		else nowtime += month;
    		int day = calendar.get(Calendar.DAY_OF_MONTH);
    		if (day<10) nowtime+="0"+day;
    		else nowtime+=day;
    		String status = "已发布";
    		if (nowtime.compareTo(signbegintime)>=0 ) status="报名中";
    		if (nowtime.compareTo(classbegintime)>=0 ) status="开课中";
    		if (nowtime.compareTo(classendtime)>0 ) status="已结束";
    		p.status = status;
    		if (astatus.equals("全部状态") || astatus.equals(status)) {
	    		list.add(p);
    		}
    	}
    	int number = list.size();
    	render("Application/PrivateExerciseSetting.html", list, number);
    }
    
    //添加特训班页面
    public static void addPrivateExercise() {
        render();
    }
    
    //添加特训班
    public static void addPrivateExerciseToDB(String name, String coursestyle, File imagefile, int weeks, int period, 
    		int num, double price, int storeid, int classroomid, int employeeid, 
    		String classbegintime, String classendtime, 
    		String exerciseweeknum, String exercisebegintime, String exerciseendtime,
    		String signbegintime, String signendtime, String summary,
    		String courseintroduce, String courseplan, String notice, String fitstep) {
    	if (storeid==0) {
    		renderJSON("请选择一个门店");
    	}
    	if (classroomid==0) {
    		renderJSON("请选择一个教室");
    	}
    	if (employeeid==0) {
    		renderJSON("请选择一个教练");
    	}
    	String image=null;
    	if (imagefile!=null) {
	    	long currentTime = System.currentTimeMillis();
	    	String fileName = currentTime + imagefile.getName();
	    	image = fileName;
	    	Files.copy(imagefile, Play.getFile("public/images/" + fileName));
    	}
    	PrivateExercise a = new PrivateExercise(name, coursestyle, image, weeks, period, num, 0, price, storeid, classroomid, employeeid, classbegintime, classendtime, exerciseweeknum, exercisebegintime, exerciseendtime, signbegintime, signendtime, summary, courseintroduce, courseplan, notice, fitstep); 
    	PrivateExerciseMgr.getInstance().save(a);
    	PrivateExerciseSetting();
    }

	//删除特训班
    public static void deletePrivateExercise(int id) {
    	boolean flag = PrivateExerciseMgr.getInstance().deletePrivateExercise(id);
    	if (flag) 
    		PrivateExerciseSetting();
    	else 
    		renderText("删除失败");
    }
    
    //特训班详情(修改)页面
    public static void PrivateExerciseDetail(int id) {
    	PrivateExercise sc = PrivateExerciseMgr.getInstance().findPrivateExerciseById(id);
    	render(sc);
    }
    
    //修改特训班
    public static void updatePrivateExerciseToDB(int id, String name, String coursestyle, File imagefile, String image,
    		int weeks, 	int period, 
    		int num, int oknum, double price, int storeid, int classroomid, int employeeid, 
    		String classbegintime, String classendtime, 
    		String exerciseweeknum, String exercisebegintime, String exerciseendtime,
    		String signbegintime, String signendtime, String summary,
    		String courseintroduce, String courseplan, String notice, String fitstep) {
    	if (storeid==0) {
    		renderJSON("请选择一个门店");
    	}
    	if (classroomid==0) {
    		renderJSON("请选择一个教室");
    	}
    	if (employeeid==0) {
    		renderJSON("请选择一个教练");
    	}
    	if (imagefile!=null) {
	    	long currentTime = System.currentTimeMillis();
	    	String fileName = currentTime + imagefile.getName();
	    	image = fileName;
	    	Files.copy(imagefile, Play.getFile("public/images/" + fileName));
    	}
    	PrivateExercise a = new PrivateExercise(id, name, coursestyle, image, weeks, period, num, oknum, price, storeid, classroomid, employeeid, classbegintime, classendtime, exerciseweeknum, exercisebegintime, exerciseendtime, signbegintime, signendtime, summary, courseintroduce, courseplan, notice, fitstep);
    	PrivateExerciseMgr.getInstance().update(a);
    	PrivateExerciseSetting();
    }
    
    public static void getAllPrivateExerciseJSON() {
    	List<PrivateExercise> list = PrivateExerciseMgr.getInstance().getAllPrivateExercise();
    	renderJSON(list);
    }
    
    
    /*
     * ···············--------会员管理--------------------Member
     */
    
    
    //Member设置（展示）页面
    public static void MemberSetting() {
    	String todaytime = getCurrentTimeSecond();
    	List<Member> list = MemberMgr.getInstance().getAllMember(todaytime);
    	for (Member ps : list) {
    		if (ps.fingerprint==1) ps.fingerprinttype="已录入";
    		else ps.fingerprinttype="未录入";
    		if (ps.cardtype==0) ps.cardtypename="非会员";
    		else if (ps.cardtype==1) ps.cardtypename="月卡";
    		else if (ps.cardtype==2) ps.cardtypename="季卡";
    		else if (ps.cardtype==3) ps.cardtypename="半年卡";
    		else if (ps.cardtype==4) ps.cardtypename="年卡";
    		else if (ps.cardtype==5) ps.cardtypename="299新人月卡";
    		else if (ps.cardtype==6) ps.cardtypename="课程卡成员";
    		// 入场密码
    		ComeInPassword cp = ComeInPasswordMgr.getInstance().findComeInPasswordByMemberId(ps.id);
    		if (cp!=null) {
    			Calendar calendar = Calendar.getInstance();  
    			int hour = calendar.get(Calendar.HOUR_OF_DAY);
    			ps.comeinpassword  = cp.arr[hour];
    		}
    	}
    	int number = list.size();
    	render(list, number);
    }
    
    //搜索会员
    public static void MemberSearch(int acityid, int acardtype, int amembertype, int asextype, String afitnesstest, String keyname) {
    	String todaytime = getCurrentTimeSecond();
    	List<Member> list = MemberMgr.getInstance().searchMember(todaytime, acityid, acardtype, amembertype, asextype, afitnesstest, keyname);
    	for (Member ps : list) {
    		if (ps.fingerprint==1) ps.fingerprinttype="已录入";
    		else ps.fingerprinttype="未录入";
    		if (ps.cardtype==0) ps.cardtypename="非会员";
    		else if (ps.cardtype==1) ps.cardtypename="月卡";
    		else if (ps.cardtype==2) ps.cardtypename="季卡";
    		else if (ps.cardtype==3) ps.cardtypename="半年卡";
    		else if (ps.cardtype==4) ps.cardtypename="年卡";
    		else if (ps.cardtype==5) ps.cardtypename="299新人月卡";
    		else if (ps.cardtype==6) ps.cardtypename="课程卡成员";
    		// 入场密码
    		ComeInPassword cp = ComeInPasswordMgr.getInstance().findComeInPasswordByMemberId(ps.id);
    		if (cp!=null) {
    			Calendar calendar = Calendar.getInstance();  
    			int hour = calendar.get(Calendar.HOUR_OF_DAY);
    			ps.comeinpassword  = cp.arr[hour];
    		}
    	}
    	int number = list.size();
    	render("Application/MemberSetting.html", list, number);
    }
    
    /*
	后台不需要添加会员，会员在手机端添加
    //添加Member页面
    public static void addMember() {
        render();
    }

    //添加Member
    public static void addMemberToDB() {
    }
    */
    
    //修改会员deaddate页面
	public static void modifyMemberDeaddate(int aid) {
		Member m = MemberMgr.getInstance().findMemberById(aid);
		String cardtypename = "undefined";
		if (m.cardtype==0) cardtypename="非会员";
		else if (m.cardtype==1) cardtypename="月卡";
		else if (m.cardtype==2) cardtypename="季卡";
		else if (m.cardtype==3) cardtypename="半年卡";
		else if (m.cardtype==4) cardtypename="年卡";
		else if (m.cardtype==5) cardtypename="299新人月卡";
		else if (m.cardtype==6) cardtypename="课程卡成员";
		List<DeadtimeLog> list = DeadtimeLogMgr.getInstance().findDeadtimeLogByMemberId(aid);
		render(m, cardtypename, list);
	}
	
	//保存被修改的会员deaddate和卡类型
	public static void modifyMemberDeaddateToDB(int aid, String deaddate, int acardtype) {
		String today = getCurrentDay();
		if (acardtype==0 && deaddate.compareTo(today)>0) {
			renderText("选择非会员，则到期时间必须小于等于今天!");
		}
		MemberMgr.getInstance().updateDeaddate(aid, acardtype, deaddate);
		MemberMgr.getInstance().updateLeftcoursenum(aid, acardtype);		//按照情况，给购买会员的人送课，一月一节课
		String updatetime = getCurrentTimeSecond();
		String employeename = (String) parseSession().get("username");
		DeadtimeLog dl = new DeadtimeLog(aid, updatetime, acardtype, deaddate, employeename);
		DeadtimeLogMgr.getInstance().save(dl);
		
		if (acardtype != 0 && deaddate.compareTo(today) > 0) {
			ComeInPassword flag = ComeInPasswordMgr.getInstance().findComeInPasswordByMemberId(aid);
			if (flag==null) GeneratePasswordToAMember(aid);
		}
		modifyMemberDeaddate(aid);
	}
    
	//给修改过的会员且同时没有当日密码的生成密码
	private static void GeneratePasswordToAMember(int memberid) {
    	int a = 100000;
		int b = 999999;
		Random rand = new Random();
		int[] arr = new int[24];
		int temp = rand.nextInt(b-a)+a;
		for (int k=0; k<24; k++) arr[k] = temp;
		
		String updatetime = getCurrentTimeSecond();
		String cdate = getCurrentDay();
		ComeInPasswordMgr.getInstance().save(memberid, cdate, arr, updatetime);
	}
	
	//删除Member
    public static void deleteMember(int id) {
    	boolean flag = MemberMgr.getInstance().deleteMember(id);
    	if (flag) 
    		MemberSetting();
    	else 
    		renderText("删除失败");
    }
    
    //Member详情(修改)页面
    public static void MemberDetail(int id) {
    	Member sc = MemberMgr.getInstance().findMemberById(id);
    	if (sc==null) {
    		renderText("改会员已经被删除，数据库不存在该会员");
    	}
    	City s = CityMgr.getInstance().findCityById(sc.cityid);
		if (s!=null) {
			sc.cityname = s.name;
		}
		if (sc.fingerprint==1) sc.fingerprinttype="已录入";
		else sc.fingerprinttype="未录入";
		if (sc.cardtype==0) sc.cardtypename="非会员";
		else if (sc.cardtype==1) sc.cardtypename="月卡";
		else if (sc.cardtype==2) sc.cardtypename="季卡";
		else if (sc.cardtype==3) sc.cardtypename="半年卡";
		else if (sc.cardtype==4) sc.cardtypename="年卡";
		else if (sc.cardtype==5) sc.cardtypename="299新人月卡";
		else if (sc.cardtype==6) sc.cardtypename="课程卡成员";
		if (sc.sex==1) sc.sexvalue="男"; else sc.sexvalue="女";
		if (sc.exercisetime==1) sc.exercisetimevalue="早上";
		else if (sc.exercisetime==2) sc.exercisetimevalue="下午";
		else if (sc.exercisetime==3) sc.exercisetimevalue="晚上";
		if (sc.exercisegoal==1) sc.exercisegoalvalue="减脂";
		else if (sc.exercisegoal==2) sc.exercisegoalvalue="塑形";
		else if (sc.exercisegoal==3) sc.exercisegoalvalue="增肌";
		if (sc.exercisehz==1) sc.exercisehzvalue="难的";
		else if (sc.exercisehz==2) sc.exercisehzvalue="一周一次";
		else if (sc.exercisehz==3) sc.exercisehzvalue="一周三次";
		else if (sc.exercisehz==4) sc.exercisehzvalue="每天";
		if (sc.distance==1) sc.distancevalue="一公里以内";
		else if (sc.distance==2) sc.distancevalue="三公里以内";
		else if (sc.distance==3) sc.distancevalue="三公里以外";
		// 入场密码 展示
		ComeInPassword cp = ComeInPasswordMgr.getInstance().findComeInPasswordByMemberId(sc.id);
		if (cp!=null) {
			Calendar calendar = Calendar.getInstance();  
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			sc.comeinpassword  = cp.arr[hour];
		}
		//到店状态 展示
		List<String> listIn = UserInOutInfoFromHardMgr.getInstance().getMemberInInfo(sc.id);
		List<String> listOut = UserInOutInfoFromHardMgr.getInstance().getMemberOutInfo(sc.id);
		int inOutTmNum = Math.max(listIn.size(),listOut.size());
		//团操课程，特训课程展示
		List<BookExercise> listbe = BookExerciseMgr.getInstance().findActiveBookExerciseByMemberId(sc.id);
		List<TeamExerciseScheduleShow> listtes = new ArrayList<>();
		List<PrivateExerciseShow> listpe = new ArrayList<>();
		for (BookExercise be : listbe) {
			if (be.type==0) {
				//注意这里的be.exerciseid是团操排期表的id
				TeamExerciseSchedule tes = TeamExerciseScheduleMgr.getInstance().findTeamExerciseScheduleById(be.exerciseid);
				if (tes!=null) {
					TeamExerciseScheduleShow tess = new TeamExerciseScheduleShow(tes);
					TeamExercise te = TeamExerciseMgr.getInstance().findTeamExerciseById(tes.teamexerciseid);
					if (te!=null) tess.teamexercisename = te.name;
					Employee e = EmployeeMgr.getInstance().findEmployeeById(tes.employeeid);
		    		if (e!=null) tess.employeename = e.name;
					listtes.add(tess);
				}
			} else if (be.type==1) {
				PrivateExercise pe = PrivateExerciseMgr.getInstance().findPrivateExerciseById(be.exerciseid);
				if (pe!=null) {
					PrivateExerciseShow pes = new PrivateExerciseShow(pe);
					Employee e = EmployeeMgr.getInstance().findEmployeeById(pes.employeeid);
					if (e!=null) pes.employeename = e.name;
					listpe.add(pes);
				}
			}
		}
		//体能测试展示
		MemberFitnessTest mft = MemberFitnessTestMgr.getInstance().getMemberFitnessTestByMemberId(id);
		if (mft==null) {
			mft = new MemberFitnessTest();
		}
		render(sc, listIn, listOut, inOutTmNum, listtes, listpe, mft);
    }
    
    //修改会员剩余课时数
    public static void MemberLeftcoursenumEditPage(int id) {
    	Member sc = MemberMgr.getInstance().findMemberById(id);
    	int memberid = id;
    	render(sc, memberid);
    }
    
    public static void MemberLeftcoursenumEditToDB(int memberid, int aleftcoursenum) {
    	MemberMgr.getInstance().updateMemberLeftcoursenum(memberid, aleftcoursenum);
    	MemberLeftcoursenumEditPage(memberid);
    }
    
    //修改会员状态
    public static void MemberStatusEdit(int id) {
    	List<MemberStatus> list = MemberStatusMgr.getInstance().getMemberStatusByMemberId(id);
    	int memberid = id;
    	render(list, memberid);
    }
    
    public static void addMemberStatusToDB(int memberid, String memberstatus, String edittime, String reason) {
    	MemberStatus m = new MemberStatus(memberid, memberstatus, edittime, reason);
    	MemberStatusMgr.getInstance().save(m);
    	MemberMgr.getInstance().updateMemberStatus(memberid, memberstatus);
    	MemberStatusEdit(memberid);
    }
    
    //修改会员体能测试
    public static void MemberFitnessTestEdit(int id) {
    	MemberFitnessTest mft = MemberFitnessTestMgr.getInstance().getMemberFitnessTestByMemberId(id);
    	int memberid = id;
    	if (mft==null) 
    		render("Application/MemberFitnessTestAdd.html",memberid);
    	else
    		render(memberid, mft);
    }
    
    public static void addMemberFitnessTestToDB(int memberid, String fitnesstest, 
    		String xiongwei, String yaowei, String tunwei, String shangtunwei, String datuiwei, String xiaotuiwei,
    		String pullup, String pushup, String plank, String sitandreach, String squatandrise,
    		String situp, String heartrateone, String heartratetwo, String heartratethree) {
    	MemberFitnessTest mft = new MemberFitnessTest(memberid, fitnesstest, xiongwei, yaowei, tunwei, shangtunwei, datuiwei, xiaotuiwei, pullup, pushup, plank, sitandreach, squatandrise, situp, heartrateone, heartratetwo, heartratethree);
    	MemberFitnessTestMgr.getInstance().save(mft);
    	MemberMgr.getInstance().updateMemberFitnessTest(memberid, fitnesstest);
    	MemberDetail(memberid);
    }
    
    /*
	后台也不需要修改会员
    //修改Member
    public static void updateMemberToDB() {
    }
    */
    
    //优秀推介人界面
    public static void GoodRecommendedMember() {
    	List<Member> list = MemberMgr.getInstance().getGoodRecommendedMember();
    	Map<String, Integer> map = MemberMgr.getInstance().getPhoneVSNumber();
    	for (Member m : list) {
    		m.recommendnums = map.get(m.phone);
    	}
    	int number = list.size();
    	render(list, number);
    }
    
    
    /*
     * ···············--------预约管理-------------1,预约团操和私教BookExercise
     */
    
    //特训营预约管理
    public static void BookPrivateExerciseDisplay() {
    	List<BookExercise> list = BookExerciseMgr.getInstance().getAllActivePrivateBookExercise();
    	for (BookExercise b : list) {
    		if (b.type==0) b.typename="团操";
    		else if (b.type==1) b.typename="特训营";
    		else b.typename="undefined";
    	}
    	int number = list.size();
    	render(list, number);
    }
    
    //团操预约管理（展示）页面
    public static void BookExerciseSetting() {
    	List<BookExercise> listBookExercise = BookExerciseMgr.getInstance().getAllActiveBookExercise();
    	List<Member> listMember = new ArrayList<>();
    	Map<Integer, TeamExerciseScheduleShow> listTeamExerciseScheduleShow = new HashMap<>();
    	Map<Integer, PrivateExerciseShow> listPrivateExerciseShow = new HashMap<>();
    	for (BookExercise be : listBookExercise) {
    		Member m = MemberMgr.getInstance().findMemberById(be.memberid);
    		if (m==null) m = new Member();
    		listMember.add(m);
    		if (be.type==0) {
    			TeamExerciseSchedule tes = TeamExerciseScheduleMgr.getInstance().findTeamExerciseScheduleById(be.exerciseid);
    			if (tes==null) tes = new TeamExerciseSchedule();
    			TeamExerciseScheduleShow tess = new TeamExerciseScheduleShow(tes);
    			StoreCity sc = StoreMgr.getInstance().findStoreById(tess.storeid);
    			if (sc!=null) tess.storename = sc.name;
//    			Classroom c = ClassroomMgr.getInstance().findClassroomById(tess.classroomid);
//    			if (c!=null) tess.classroomname = c.name;
    			Employee e = EmployeeMgr.getInstance().findEmployeeById(tess.employeeid);
    			if (e!=null) tess.employeename = e.name;
    			TeamExercise te = TeamExerciseMgr.getInstance().findTeamExerciseById(tess.teamexerciseid);
    			if (te!=null) tess.teamexercisename = te.name;
    			listTeamExerciseScheduleShow.put(be.id, tess);
    		} else if (be.type==1) {
    			PrivateExercise pe = PrivateExerciseMgr.getInstance().findPrivateExerciseById(be.exerciseid);
    			if (pe==null) pe = new PrivateExercise();
    			PrivateExerciseShow pes = new PrivateExerciseShow(pe);
    			StoreCity sc = StoreMgr.getInstance().findStoreById(pes.storeid);
    			if (sc!=null) pes.storename = sc.name;
//    			Classroom c = ClassroomMgr.getInstance().findClassroomById(pes.classroomid);
//    			if (c!=null) pes.classroomname = c.name; 
    			Employee e = EmployeeMgr.getInstance().findEmployeeById(pes.employeeid);
    			if (e!=null) pes.employeename = e.name;
    			listPrivateExerciseShow.put(be.id, pes);
    		}
    	}
    	int number = listBookExercise.size();
    	render(listBookExercise, listMember, listPrivateExerciseShow, listTeamExerciseScheduleShow, number);
    }
    
    //搜索BookExercise预约设置（展示）页面
    public static void BookExerciseSearch(int storeid, int teamexercisescheduleid, int privateexerciseid,
    		String keyname, String starttime, String endtime) {
    	List<BookExercise> PrelistBookExercise = BookExerciseMgr.getInstance().searchActiveBookExercise(teamexercisescheduleid, privateexerciseid, starttime, endtime);
    	List<BookExercise> listBookExercise = new ArrayList<>();
    	List<Member> listMember = new ArrayList<>();
    	Map<Integer, TeamExerciseScheduleShow> listTeamExerciseScheduleShow = new HashMap<>();
    	Map<Integer, PrivateExerciseShow> listPrivateExerciseShow = new HashMap<>();
    	for (BookExercise be : PrelistBookExercise) {
    		Member m = MemberMgr.getInstance().findMemberById(be.memberid);
    		if (keyname!=null && keyname.length()>0) {
    			if (m==null) continue;
    			if (!m.name.contains(keyname) && !m.phone.contains(keyname)) continue;
    		}
    		if (be.type==0) {
    			TeamExerciseSchedule tes = TeamExerciseScheduleMgr.getInstance().findTeamExerciseScheduleById(be.exerciseid);
    			if (storeid!=0) {
    				if (tes==null) continue;
    				if (tes.storeid!=storeid) continue;
    			}
    			TeamExerciseScheduleShow tess = new TeamExerciseScheduleShow(tes);
    			StoreCity sc = StoreMgr.getInstance().findStoreById(tess.storeid);
    			if (sc!=null) tess.storename = sc.name;
//    			Classroom c = ClassroomMgr.getInstance().findClassroomById(tess.classroomid);
//    			if (c!=null) tess.classroomname = c.name;
    			Employee e = EmployeeMgr.getInstance().findEmployeeById(tess.employeeid);
    			if (e!=null) tess.employeename = e.name;
    			TeamExercise te = TeamExerciseMgr.getInstance().findTeamExerciseById(tess.teamexerciseid);
    			if (te!=null) tess.teamexercisename = te.name;
    			listBookExercise.add(be);
    			listMember.add(m);
    			listTeamExerciseScheduleShow.put(be.id, tess);
    		} else if (be.type==1) {
    			PrivateExercise pe = PrivateExerciseMgr.getInstance().findPrivateExerciseById(be.exerciseid);
    			if (storeid!=0) {
    				if (pe==null) continue;
    				if (pe.storeid!=storeid) continue;
    			}
    			PrivateExerciseShow pes = new PrivateExerciseShow(pe);
    			StoreCity sc = StoreMgr.getInstance().findStoreById(pes.storeid);
    			if (sc!=null) pes.storename = sc.name;
//    			Classroom c = ClassroomMgr.getInstance().findClassroomById(pes.classroomid);
//    			if (c!=null) pes.classroomname = c.name; 
    			Employee e = EmployeeMgr.getInstance().findEmployeeById(pes.employeeid);
    			if (e!=null) pes.employeename = e.name;
    			listBookExercise.add(be);
    			listMember.add(m);
    			listPrivateExerciseShow.put(be.id, pes);
    		}
    	}
    	int number = listBookExercise.size();
    	render("Application/BookExerciseSetting.html", listBookExercise, listMember, listPrivateExerciseShow, listTeamExerciseScheduleShow, number);
    }
    
	//删除BookExercise预约
    public static void deleteBookExercise(int id) {
    	boolean flag = BookExerciseMgr.getInstance().deleteBookExercise(id);
    	if (flag) 
    		BookExerciseSetting();
    	else 
    		renderText("删除失败");
    }
    
    
    /*
     * ···············--------财务管理-------------PurchaseHistory
     */
    
    
    //PurchaseHistory财务设置（展示）页面
    public static void PurchaseHistorySetting() {
    	List<PurchaseHistory> listPurchaseHistory = PurchaseHistoryMgr.getInstance().getAllPurchaseHistory();
    	List<MemberShow> listMemberShow = new ArrayList<>();
    	double feesum=0.0;
    	for (PurchaseHistory ph : listPurchaseHistory) {
    		feesum+=ph.fee;
    		if (ph.carttype==1) ph.carttypename="月卡";
    		else if (ph.carttype==2) ph.carttypename="季卡";
    		else if (ph.carttype==3) ph.carttypename="半年卡";
    		else if (ph.carttype==4) ph.carttypename="年卡";
    		if (ph.purchasetype==1) ph.purchasetypename="微信支付";
    		if (ph.isprivate==1) ph.carttypename="私教课程";
    		Member m = MemberMgr.getInstance().findMemberById(ph.memberid);
    		MemberShow ms = null;
    		if (m==null) {
    			ms = new MemberShow();
    		} else {
    			ms = new MemberShow(m);
    		}
    		listMemberShow.add(ms);
    	}
    	int number = listPurchaseHistory.size();
    	render(listPurchaseHistory, listMemberShow, number, feesum);
    }
    
    //财务搜索
    public static void PurchaseHistorySearch(int cardtype, String starttime, String endtime) {
    	List<PurchaseHistory> listPurchaseHistory = PurchaseHistoryMgr.getInstance().searchPurchaseHistory(cardtype, starttime, endtime);
    	List<MemberShow> listMemberShow = new ArrayList<>();
    	double feesum=0.0;
    	for (PurchaseHistory ph : listPurchaseHistory) {
    		feesum+=ph.fee;
    		if (ph.carttype==1) ph.carttypename="月卡";
    		else if (ph.carttype==2) ph.carttypename="季卡";
    		else if (ph.carttype==3) ph.carttypename="半年卡";
    		else if (ph.carttype==4) ph.carttypename="年卡";
    		if (ph.purchasetype==1) ph.purchasetypename="微信支付";
    		if (ph.isprivate==1) ph.carttypename="私教课程";
    		Member m = MemberMgr.getInstance().findMemberById(ph.memberid);
    		MemberShow ms = new MemberShow(m);
    		listMemberShow.add(ms);
    	}
    	int number = listPurchaseHistory.size();
    	render("Application/PurchaseHistorySetting.html", listPurchaseHistory, listMemberShow, number, feesum);
    }
    
// 后台不需要添加BookExercise，BookExercise在手机端添加
//    //添加BookExercise页面
//    public static void addBookExercise() {
//        render();
//    }
 
//    //添加BookExercise
//    public static void addBookExerciseToDB() {
//    	
//    }

	//删除PurchaseHistory某个付款
    public static void deletePurchaseHistory(int id) {
    	boolean flag = PurchaseHistoryMgr.getInstance().deletePurchaseHistory(id);
    	if (flag) 
    		PurchaseHistorySetting();
    	else 
    		renderText("删除失败");
    }
    
//    //BookExercise预约详情(修改)页面
//    public static void BookExerciseDetail(int id) {
//		render();
//    }
   
// 后台也不需要修改预约BookExercise
//    //修改预约
//    public static void updateBookExerciseToDB() {
//
//    }
    
    
    
    
    
    
    
    
    
    
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
     * 巍巍传递过来的memberid和password
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
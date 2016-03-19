package controllers;

import play.*;
import play.data.validation.Required;
import play.mvc.*;
import play.mvc.Scope.Session;
import play.mvc.results.RenderJson;
import utils.AnnouncementMgr;
import utils.CityMgr;
import utils.ClassroomMgr;
import utils.EmployeeMgr;
import utils.FitnessPlanMgr;
import utils.GroupWebsiteMgr;
import utils.GroupbuyMgr;
import utils.StoreMgr;

import java.util.*;

import models.*;

public class Application extends Controller {

	//首页
    public static void index() {
        render();
    }
    
	/*
	 * 权限管理
	 */
	
    //在所有功能前检查是否是已经登录的
    @Before(unless={"login","check"})
    public static void checkAuthentification() {
    	if (session.get("user") == null) login();
    }
    
    //登录页面
    public static void login() {
        render();
    }
    
    //判断能否登录，能登录的话保存session
    public static void check(String username, String password) {
    	if (EmployeeMgr.getInstance().hasUser(username)) {
    		Employee e = EmployeeMgr.getInstance().findEmployeeByUsername(username);
    		if (e.password.equals(password)) {
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
     * ··························会员管理
     */
    
    public static void memberManagement() {
        render();
    }
    
    /*
     * ························门店设置Store
     */
    
    //查询所有门店 + 全部门店
    public static void getAllStore() {
    	List<StoreCity> listsc = StoreMgr.getInstance().getAllStore();
    	StoreCity sc = new StoreCity();
    	sc.id=0;
    	sc.name = "全部门店";
    	listsc.add(sc);
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
    		double area, String photo, String manager, String phone) {
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
    		double area, String photo, String manager, String phone) {
    	Store s = new Store(id, cityid, name, address, area, photo, manager, phone);
    	StoreMgr.getInstance().update(s);
    	storeSetting();
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
    
    
    
    
    /*
     * ----------------------员工设置Employee
     */
    //员工设置(展示)页面
    public static void employeeSetting() {
    	List<Employee> list = EmployeeMgr.getInstance().getAllEmployee();
    	List<EmployeeShow> liste = new ArrayList<>();
    	for (Employee em : list) {
    		EmployeeShow es = new EmployeeShow(em);
    		//TODO 可能为空，需要判断
    		StoreCity s = StoreMgr.getInstance().findStoreById(em.storeid);
    		es.storename = s.name;
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
    public static void addEmployeeToDB(String username, String password, String name, String headimage, int sex, String phone, 
    		int[] identity, int[] authority, int storeid, String introduce) {
    	if (EmployeeMgr.getInstance().hasUser(username)) {
    		renderJSON("登录账号名已经存在，请换一个！");
    	}
    	//工作人员必须选择一个门店
    	if (storeid==0) {
    		renderJSON("请选择一个门店!!!");
    	}
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
    	Employee e = new Employee(username, password, name, headimage, sex, phone, ismanager, isfinance, iscoach, domember, doappointment, docourse, doplan, domarkte, dofinance, doemployee, dostore, dostatistics, storeid, introduce);
    	EmployeeMgr.getInstance().save(e);
    	employeeSetting();
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
    public static void updateEmployeeToDB(int id, String username, String password, String name, String headimage, int sex, String phone, 
    		int[] identity, int[] authority, int storeid, String introduce) {
    	//工作人员必须选择一个门店
    	if (storeid==0) {
    		renderJSON("请选择一个门店!!!");
    	}
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
    	Employee e = new Employee(id,username, password, name, headimage, sex, phone, ismanager, isfinance, iscoach, domember, doappointment, docourse, doplan, domarkte, dofinance, doemployee, dostore, dostatistics, storeid, introduce);
    	EmployeeMgr.getInstance().update(e);
    	employeeSetting();
    }
    
    //返回给员工设置中的第一个combobox使用
    public static void getThreeKindsEmployee() {
    	int id;
    	String name;
    	String result="[";
    	id=0; name="全部工作人员";
    	result+="{\"id\":"+id+",\"name\":"+"\""+name+"\""+"},";
    	id=1; name="店长";
    	result+="{\"id\":"+id+",\"name\":"+"\""+name+"\""+"},";
    	id=2; name="财务";
    	result+="{\"id\":"+id+",\"name\":"+"\""+name+"\""+"},";
    	id=3; name="教练";
    	result+="{\"id\":"+id+",\"name\":"+"\""+name+"\""+"}]";
    	renderJSON(result);
    }
    
    /*
     * ···············--------营销管理1······---------·门店公告Announcement
     */
    
    
    //门店公告设置(展示)页面
    public static void announcementSetting() {
    	List<Announcement> lista = AnnouncementMgr.getInstance().getAllAnnouncement();
    	List<AnnouncementShow> listad = new ArrayList<>();
    	for (Announcement aa : lista) {
    		String starttime = aa.starttime;
    		//TODO 时间问题需要更加详细的考虑
    		if (starttime!=null && starttime.length()==16) {
	    		starttime = starttime.substring(6, 10)+starttime.substring(0, 2)+starttime.substring(3, 5)+
	    				starttime.substring(11, 13)+starttime.substring(14, 16);
    		}
    		String endtime = aa.endtime;
    		if (endtime!=null && endtime.length()==16) {
	    		endtime = endtime.substring(6, 10)+endtime.substring(0, 2)+endtime.substring(3, 5)+
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
    		//TODO 可能为空，需要判断
    		as.storename = StoreMgr.getInstance().findStoreById(aa.storeid).name;
    		as.employeename = EmployeeMgr.getInstance().findEmployeeById(aa.employeeid).name;
    		listad.add(as);
    	}
    	int number = listad.size();
        render(listad,number);
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
    		//---
    		String starttime = aa.starttime;
    		//TODO 时间问题需要更加详细的考虑
    		if (starttime!=null && starttime.length()==16) {
	    		starttime = starttime.substring(6, 10)+starttime.substring(0, 2)+starttime.substring(3, 5)+
	    				starttime.substring(11, 13)+starttime.substring(14, 16);
    		}
    		String endtime = aa.endtime;
    		if (endtime!=null && endtime.length()==16) {
	    		endtime = endtime.substring(6, 10)+endtime.substring(0, 2)+endtime.substring(3, 5)+
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
    		//TODO 可能为空，需要判断
    		as.storename = StoreMgr.getInstance().findStoreById(aa.storeid).name;
    		as.groupwebsitename = GroupWebsiteMgr.getInstance().findGroupWebsiteById(aa.groupwebsiteid).name;
    		lists.add(as);
    	}
    	int number = lists.size();
        render(lists,number);
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
        render(lists);
    }
    
    //添加健身计划页面
    public static void addFitnessPlan() {
        render();
    }
    
    //添加健身计划
    public static void addFitnessPlanToDB(int style, String name, int fitsex, int[] parts,
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
    
    //健身计划详情(修改)页面
    public static void fitnessPlanDetail(int id) {
    	FitnessPlan s = FitnessPlanMgr.getInstance().findFitnessPlanById(id);
    	FitnessPlanShow sc  = new FitnessPlanShow(s);
    	String parts = sc.parts;
    	String number = sc.number;
    	String[] str = parts.split(",");
    	for (String ss : str) {
    		int temp = Integer.parseInt(ss);
    		if (temp==1) sc.one=1;
    		if (temp==2) sc.two=1;
    		if (temp==3) sc.three=1;
    		if (temp==4) sc.four=1;
    		if (temp==5) sc.five=1;
    	}
    	str = number.split(",");
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
    
    
    
    
    
}
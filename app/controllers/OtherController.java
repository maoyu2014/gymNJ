package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import models.course.TeamExercise;
import models.course.TeamExerciseMgr;
import models.course.TeamExerciseSchedule;
import models.course.TeamExerciseScheduleMgr;
import models.employee.Employee;
import models.employee.EmployeeMgr;
import models.store.StoreMgr;
import models.useless.Announcement;
import models.useless.AnnouncementShow;
import models.useless.City;
import models.useless.FitnessPlan;
import models.useless.FitnessPlanShow;
import models.useless.GroupWebsite;
import models.useless.Groupbuy;
import models.useless.GroupbuyShow;
import models.useless.PrivateExercise;
import models.useless.PrivateExerciseMgr;
import models.useless.StoreCity;
import play.Play;
import play.libs.Files;
import play.mvc.*;
import utils.useless.AnnouncementMgr;
import utils.useless.CityMgr;
import utils.useless.FitnessPlanMgr;
import utils.useless.GroupWebsiteMgr;
import utils.useless.GroupbuyMgr;

public class OtherController extends Controller {

    public static void index() {
        render();
    }

    /*
    
    public static void getAllCity() {
    	List<City> cities = CityMgr.getInstance().getAllCity();
        renderJSON(cities);
    }
    
    public static void getAllGroupWebsite() {
    	List<GroupWebsite> lists = GroupWebsiteMgr.getInstance().getAllGroupWebsite();
        renderJSON(lists);
    }
    
    
    
    
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
    
    
    
    
     // ···············--------课程管理  2-----------特训班 PrivateExercise
    
    
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
    
    
    */
    
}

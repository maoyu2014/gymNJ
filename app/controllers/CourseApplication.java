package controllers;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.book.BookExerciseMgr;
import models.course.TeamExercise;
import models.course.TeamExerciseMgr;
import models.course.TeamExerciseSchedule;
import models.course.TeamExerciseScheduleJson;
import models.course.TeamExerciseScheduleMgr;
import models.course.TeamExerciseScheduleResultDetail;
import models.course.TeamExerciseScheduleResultInfo;
import models.course.TeamExerciseScheduleResultMgr;
import models.employee.Employee;
import models.employee.EmployeeMgr;
import models.member.Member;
import models.store.Classroom;
import models.store.ClassroomMgr;
import models.store.Store;
import models.store.StoreMgr;
import models.useless.PrivateExercise;
import models.useless.PrivateExerciseMgr;
import models.useless.StoreCity;
import play.Play;
import play.libs.Files;
import play.mvc.*;
import utils.common.YearMonthDay;

public class CourseApplication extends Controller {

    public static void index() {
        render();
    }

    
    /*
     * --------------课程管理 1-----------课程 TeamExercise（原团操）-------------
     */
    
    //课程（原团操）
    public static void teamExerciseBasic() {
    	List<TeamExercise> lists = TeamExerciseMgr.getInstance().getAllTeamExercise();
    	int number = lists.size();
    	render(lists, number);
    }
    
    //添加课程（原团操）页面
    public static void addTeamExercise() {
        render();
    }
    
    //添加课程（原团操）动作
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

	//删除课程（原团操）
    public static void deleteTeamExercise(int id) {
    	boolean flag = TeamExerciseMgr.getInstance().deleteTeamExercise(id);
    	if (flag) 
    		teamExerciseBasic();
    	else 
    		renderText("删除失败");
    }
    
    //课程详情(修改)页面
    public static void teamExerciseDetail(int id) {
    	TeamExercise sc = TeamExerciseMgr.getInstance().findTeamExerciseById(id);
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
    
    //修改课程（原团操）动作
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
    
    //获得所有课程（原团操）的json串
    public static void getAllTeamExercise() {
    	List<TeamExercise> lists = TeamExerciseMgr.getInstance().getAllTeamExercise();
        renderJSON(lists);
    }
    
    
    /*
     * ----------课程管理 -------课程排期 TeamExerciseSchedule-----------
     */
    
     
    //课程排期
    public static void teamExerciseSchedule() {
    	String yearmonth = YearMonthDay.getCurrentMonth();
    	List<TeamExerciseSchedule> list = TeamExerciseScheduleMgr.getInstance().getAllTeamExerciseScheduleByYearMonth(yearmonth);
    	int number = list.size();
    	render(list, number);
    }
    
    //搜索课程排期
    public static void TeamExerciseScheduleSearch(int storeid, int employeeid, String yearmonth) {
    	String currentmonth = YearMonthDay.getCurrentMonth();
    	if (yearmonth==null || yearmonth.length()==0) yearmonth = currentmonth;
    	List<TeamExerciseSchedule> list = TeamExerciseScheduleMgr.getInstance().searchTeamExerciseSchedule(storeid,employeeid,yearmonth);
    	int number = list.size();
    	render("CourseApplication/teamExerciseSchedule.html", list, number);
    }
    
    //添加课程排期页面
    public static void addTeamExerciseSchedule() {
        render();
    }
    
    //添加课程排期到数据库动作
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

    //课程复制页面
    public static void TeamExerciseScheduleCopy(int id) {
    	TeamExerciseSchedule sc = TeamExerciseScheduleMgr.getInstance().findTeamExerciseScheduleById(id);
    	render(sc);
    }
    
    //添加复制的课程排期到数据库动作
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
    
	//删除课程排期
    public static void deleteTeamExerciseSchedule(int id) {
    	boolean flag = TeamExerciseScheduleMgr.getInstance().deleteTeamExerciseSchedule(id);
    	if (flag) 
    		teamExerciseSchedule();
    	else 
    		renderText("删除失败");
    }
    
    //课程排期详情(修改)页面
    public static void TeamExerciseScheduleDetail(int id) {
    	TeamExerciseSchedule sc = TeamExerciseScheduleMgr.getInstance().findTeamExerciseScheduleById(id);
    	render(sc);
    }
    
    //修改课程排期动作
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
    
    //获得所有课程排期的可读性结果
    public static void getAllTeamExerciseScheduleAsJson() {
    	List<TeamExerciseSchedule> listss = TeamExerciseScheduleMgr.getInstance().getAllTeamExerciseSchedule();
    	List<TeamExerciseScheduleJson> listtesj = new ArrayList<>();
    	for (TeamExerciseSchedule tes : listss) {
    		TeamExerciseScheduleJson tesj = new TeamExerciseScheduleJson();
    		tesj.id = tes.id;
    		tesj.name="";
    		TeamExercise t = TeamExerciseMgr.getInstance().findTeamExerciseById(tes.teamexerciseid);
    		if (t!=null) tesj.name += t.name+"、";
    		Store s = StoreMgr.getInstance().findStoreById(tes.storeid);
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
     * ----------------------------------------
     */

    //本周课程(原团操)
    public static void teamExerciseWeek() {
    	String yearmonth = YearMonthDay.getCurrentMonth();
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
		
		calendar.add(Calendar.DAY_OF_MONTH, 6);
		String myendtime = ""+calendar.get(Calendar.YEAR)+"-";
		month = calendar.get(Calendar.MONTH)+1; 
		if (month<10) myendtime+="0"+month+"-";
		else myendtime += month+"-";
		day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day<10) myendtime+="0"+day;
		else myendtime+=day;
		
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
    
    //搜索本周课程(原团操)
    public static void teamExerciseWeekSearch(int storeid) {
    	String yearmonth = YearMonthDay.getCurrentMonth();
    	List<TeamExerciseSchedule> list = TeamExerciseScheduleMgr.getInstance().searchTeamExerciseSchedule(storeid, 0, yearmonth);
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
		
		calendar.add(Calendar.DAY_OF_MONTH, 6);
		String myendtime = ""+calendar.get(Calendar.YEAR)+"-";
		month = calendar.get(Calendar.MONTH)+1; 
		if (month<10) myendtime+="0"+month+"-";
		else myendtime += month+"-";
		day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day<10) myendtime+="0"+day;
		else myendtime+=day;
		
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
    	render("CourseApplication/teamExerciseWeek.html", mapweek);
    }
    
    //本月课程(原团操)
    public static void teamExerciseMonth() {
    	String yearmonth = YearMonthDay.getCurrentMonth();
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
    
    //搜索本月课程(原团操)
    public static void teamExerciseMonthSearch(int storeid) {
    	String yearmonth = YearMonthDay.getCurrentMonth();
    	List<TeamExerciseSchedule> list = TeamExerciseScheduleMgr.getInstance().searchTeamExerciseSchedule(storeid, 0, yearmonth);
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
    	render("CourseApplication/teamExerciseMonth.html", mapmonth, num);
    }
    
    
    //查看课程（原团操）报名学员
    public static void teamExerciseScheduleBookMember(int id) {
    	List<Member> list = BookExerciseMgr.getInstance().getTeamExerciseScheduleBookMember(id);
    	int num = list.size();
    	render(list, num);
    }
    
    //写入本次课程（原团操）锻炼结果
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
    	
    	render("CourseApplication/teamExerciseScheduleResultWriteIn.html",id, list, number, aa, anlist);
    }
    
    
    /*
     * ---------------分店系统-------------------
     */
    
    //搜索本周课程(原团操)
    public static void teamExerciseWeekFen(int storeid) {
    	String yearmonth = YearMonthDay.getCurrentMonth();
    	List<TeamExerciseSchedule> list = TeamExerciseScheduleMgr.getInstance().searchTeamExerciseSchedule(storeid, 0, yearmonth);
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
		
		calendar.add(Calendar.DAY_OF_MONTH, 6);
		String myendtime = ""+calendar.get(Calendar.YEAR)+"-";
		month = calendar.get(Calendar.MONTH)+1; 
		if (month<10) myendtime+="0"+month+"-";
		else myendtime += month+"-";
		day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day<10) myendtime+="0"+day;
		else myendtime+=day;
		
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
    
    //搜索本月课程(原团操)
    public static void teamExerciseMonthFen(int storeid) {
    	String yearmonth = YearMonthDay.getCurrentMonth();
    	List<TeamExerciseSchedule> list = TeamExerciseScheduleMgr.getInstance().searchTeamExerciseSchedule(storeid, 0, yearmonth);
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
    
}

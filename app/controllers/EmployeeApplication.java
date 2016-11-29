package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import models.ComeInPassword;
import models.employee.Employee;
import models.employee.EmployeeMgr;
import models.store.Store;
import models.store.StoreMgr;
import models.useless.StoreCity;
import play.Play;
import play.libs.Files;
import play.mvc.*;
import utils.ComeInPasswordMgr;
import utils.common.Md5_Utils;
import utils.common.YearMonthDay;

/*
 * --------------员工管理Employee（包含了各店店长和总店长）------
 */
public class EmployeeApplication extends Controller {

    public static void index() {
        render();
    }
    
    
    //员工管理首页
    public static void employeeSetting() {
    	List<Employee> list = EmployeeMgr.getInstance().getAllEmployee();
    	for (Employee em : list) {
    		// 入场密码
    		ComeInPassword cp = ComeInPasswordMgr.getInstance().findComeInPasswordByMemberId(em.id);
    		if (cp!=null) {
    			Calendar calendar = Calendar.getInstance();  
    			int hour = calendar.get(Calendar.HOUR_OF_DAY);
    			em.comeinpassword  = cp.arr[hour];
    		}
    	}
    	int number = list.size();
        render(list, number);
    }
    
    //员工搜索功能
    public static void employeeSearch(int storeid) {
    	List<Employee> list = EmployeeMgr.getInstance().searchEmployee(storeid);
    	for (Employee em : list) {
    		// 入场密码
    		ComeInPassword cp = ComeInPasswordMgr.getInstance().findComeInPasswordByMemberId(em.id);
    		if (cp!=null) {
    			Calendar calendar = Calendar.getInstance();  
    			int hour = calendar.get(Calendar.HOUR_OF_DAY);
    			em.comeinpassword  = cp.arr[hour];
    		}
    	}
    	int number = list.size();
        render("EmployeeApplication/employeeSetting.html", list, number);
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
		
		String updatetime = YearMonthDay.getCurrentTimeSecond();
		String cdate = YearMonthDay.getCurrentDay();
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
    
    //员工详情页面
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
    
    /*
     * 新的系统教练属于所有门店
    //返回属于某个店铺的所有教练
    public static void getAllCoachByStoreID(int storeid) {
    	List<Employee> list = EmployeeMgr.getInstance().getAllEmployeeByStoreID(storeid);
    	List<Employee> lists = new ArrayList<>();
    	for (Employee e: list) {
    		if (e.iscoach==1) lists.add(e);
    	}
    	renderJSON(lists);
    }
    */
    
    //返回所有教练
    public static void getAllCoach(int storeid) {
    	storeid = 25;
    	List<Employee> list = EmployeeMgr.getInstance().getAllEmployeeByStoreID(storeid);
    	List<Employee> lists = new ArrayList<>();
    	for (Employee e: list) {
    		if (e.iscoach==1) lists.add(e);
    	}
    	renderJSON(lists);
    }
    
    
}

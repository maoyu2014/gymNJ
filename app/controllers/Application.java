package controllers;

import play.*;
import play.mvc.*;
import utils.CityMgr;
import utils.ClassroomMgr;
import utils.EmployeeMgr;
import utils.StoreMgr;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }

    /*
     * ·························城市city
     */
    public static void getAllCity() {
    	List<City> cities = CityMgr.getInstance().getAllCity();
        renderJSON(cities);
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
    
    //查询所有门店
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
    	storeDetail(id);
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
     * ----------------------员工设置
     */
    //员工设置(展示)页面
    public static void employeeSetting() {
    	List<Employee> liste = EmployeeMgr.getInstance().getAllEmployee();
    	int number = liste.size();
        render(liste, number);
    }
    
    //添加员工页面
    public static void addEmployee() {
        render();
    }
    
    //添加员工
    public static void addEmployeeToDB(String name, String headimage, int sex, String phone, 
    		int[] identity, int[] authority, int storeid, String introduce) {
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
    	//工作人员必须选择一个门店，否则默认为第一个门店
    	if (storeid==0) storeid=1;
    	StoreCity sc = StoreMgr.getInstance().findStoreById(storeid);
    	Employee e = new Employee(name, headimage, sex, phone, ismanager, isfinance, iscoach, domember, doappointment, docourse, doplan, domarkte, dofinance, doemployee, dostore, dostatistics, storeid, sc.name, introduce);
    	EmployeeMgr.getInstance().save(e);
    	employeeSetting();
    }
    
    //删除员工
    public static void deleteEmployee(int id) {
    	boolean flag = StoreMgr.getInstance().deleteStore(id);
    	if (flag) 
    		storeSetting();
    	else 
    		renderText("删除失败");
    }
    
    //员工详情(修改)页面
    public static void employeeDetail(int id) {
    	StoreCity sc = StoreMgr.getInstance().findStoreById(id);
    	List<Classroom> listcr = ClassroomMgr.getInstance().getAllClassroomByStoreId(id);
    	render(sc, listcr);
    }
    
    //修改员工
    public static void updateEmployeeToDB(int id, int cityid, String name, String address,
    		double area, String photo, String manager, String phone) {
    	Store s = new Store(id, cityid, name, address, area, photo, manager, phone);
    	StoreMgr.getInstance().update(s);
    	storeDetail(id);
    }
    
    
    
    
}
package controllers;

import play.*;
import play.mvc.*;
import utils.CityMgr;
import utils.ClassroomMgr;
import utils.StoreMgr;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }

    /*
     * 城市city
     */
    public static void getAllCity() {
    	List<City> cities = CityMgr.getInstance().getAllCity();
        renderJSON(cities);
    }
    
    /*
     * 会员管理
     */
    
    public static void memberManagement() {
        render();
    }
    
    /*
     * 门店设置Store
     */
    
    //门店设置页面
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
    
    //门店详情页面
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
     * 教室管理Classroom
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
    
    
    
    
}
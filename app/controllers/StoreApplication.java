package controllers;

import java.io.File;
import java.util.List;

import models.store.Classroom;
import models.store.ClassroomMgr;
import models.store.Store;
import models.store.StoreMgr;
import models.useless.StoreCity;
import play.Play;
import play.libs.Files;
import play.mvc.*;

/*
 * --------门店管理--------------
 */
public class StoreApplication extends Controller {

    public static void index() {
        render();
    }

    
    //查询所有门店
    public static void getAllStore() {
    	List<Store> listsc = StoreMgr.getInstance().getAllStore();
        renderJSON(listsc);
    }
    
    //门店管理首页
    public static void storeSetting() {
    	List<Store> listsc = StoreMgr.getInstance().getAllStore();
    	int number = listsc.size();
        render(listsc,number);
    }
    
    //添加门店页面
    public static void addStore() {
        render();
    }
    
    //添加门店动作
    public static void addStoreToDB(String name, String address,
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
    	Store s = new Store(5, name, address, area, photo, manager, phone);
    	StoreMgr.getInstance().save(s);
    	storeSetting();
    }
    
    //删除门店动作
    public static void deleteStore(int id) {
    	boolean flag = StoreMgr.getInstance().deleteStore(id);
    	if (flag) 
    		storeSetting();
    	else 
    		renderText("删除失败");
    }
    
    //门店详情(修改)页面, 同时是教室展示页面
    public static void storeDetail(int id) {
    	Store sc = StoreMgr.getInstance().findStoreById(id);
    	List<Classroom> listcr = ClassroomMgr.getInstance().getAllClassroomByStoreId(id);
    	render(sc, listcr);
    }
    
    //修改门店
    public static void updateStoreToDB(int id, String name, String address,
    		double area, File photofile, String photo, String manager, String phone) {
    	if (photofile!=null) {
	    	long currentTime = System.currentTimeMillis();
	    	String fileName = currentTime + photofile.getName();
	    	photo = fileName;
	    	Files.copy(photofile, Play.getFile("public/images/" + fileName));
    	}
    	Store s = new Store(id, 5, name, address, area, photo, manager, phone);
    	StoreMgr.getInstance().update(s);
    	storeSetting();
    }
    
    
    //-----------教室管理---------------
    
    
    
    //增加教室页面
    public static void addClassroom(int storeid) {
        render(storeid);
    }
    
    //添加教室动作
    public static void addClassroomToDB(int storeid, String name, int holdnumber, String usage) {
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
    public static void updateClassroomToDB(int id, int storeid, String name, int holdnumber, String usage) {
    	Classroom cr = new Classroom(id, name, holdnumber, usage, storeid);
    	ClassroomMgr.getInstance().update(cr);
    	storeDetail(storeid);
    }
    
    
    
    //-----------------可能被其他调用的方法---------------
    
    
    
    //返回属于某个店铺的所有'团操'教室  。。现在修改成返回某个门店的教室，前台就不用改了
    public static void getAllTuanClassroomByStoreId(int storeid) {
    	List<Classroom> list = ClassroomMgr.getInstance().getAllClassroomByStoreId(storeid);
    	renderJSON(list);
    }
    
    //返回属于某个店铺的所有'私教'教室。。现在修改成返回某个门店的教室，前台就不用改了
    public static void getAllPrivateClassroomByStoreId(int storeid) {
    	List<Classroom> list = ClassroomMgr.getInstance().getAllClassroomByStoreId(storeid);
    	renderJSON(list);
    }
    
    
}

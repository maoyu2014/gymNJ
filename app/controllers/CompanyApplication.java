package controllers;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import models.ComeInPassword;
import models.company.Company;
import models.company.CompanyMgr;
import models.member.Member;
import models.member.MemberMgr;
import models.store.Classroom;
import models.store.ClassroomMgr;
import models.store.Store;
import models.store.StoreMgr;
import models.useless.StoreCity;
import play.Play;
import play.libs.Files;
import play.mvc.*;
import utils.ComeInPasswordMgr;
import utils.common.YearMonthDay;

/*
 * --------企业管理--------------
 */
public class CompanyApplication extends Controller {

    public static void index() {
        render();
    }

    //查询所有企业
    public static void getAllCompanyCombo() {
    	List<Company> listsc = CompanyMgr.getInstance().getAllCompanyCombo();
        renderJSON(listsc);
    }
    
    //企业管理首页
    public static void companyIndex() {
    	List<Company> listsc = CompanyMgr.getInstance().getAllCompany();
    	int number = listsc.size();
        render(listsc,number);
    }
    
    //添加企业页面
    public static void addCompanyPage() {
        render();
    }
    
    //添加企业动作
    public static void addCompanyToDB(String name, String address, String manager, String phone) {
    	Company s = new Company();
    	s.name = name;
    	s.address = address;
    	s.manager = manager;
    	s.phone = phone;
    	CompanyMgr.getInstance().save(s);
    	companyIndex();
    }
    
    //删除企业动作
    public static void deleteCompany(int id) {
    	boolean flag = CompanyMgr.getInstance().deleteCompany(id);
    	if (flag) 
    		companyIndex();
    	else 
    		renderText("删除失败");
    }
    
    //企业详情(修改)页面
    public static void companyDetailPage(int id) {
    	Company sc = CompanyMgr.getInstance().getCompanyByID(id);
    	render(sc);
    }
    
    //修改企业动作
    public static void updateCompanyToDB(int id, String name, String address, String manager, String phone) {
    	Company s = new Company();
    	s.id = id;
    	s.name = name;
    	s.address = address;
    	s.manager = manager;
    	s.phone = phone;
    	CompanyMgr.getInstance().update(s);
    	companyIndex();
    }
    
    //属于某个企业的所有会员
    public static void hasMemberPage(int companyid) {
    	String todaytime = YearMonthDay.getCurrentTimeSecond();
    	List<Member> list = MemberMgr.getInstance().getMemberBelongCompany(todaytime, companyid);
    	processMemberAttribute(list);
    	int number = list.size();
    	render(number, list);
    }
    
    public static int processMemberAttribute(List<Member> list) {
    	for (Member ps : list) {
    		if (ps.cardtype==0) ps.cardtypename="非会员";
    		else if (ps.cardtype==1) ps.cardtypename="月卡";
    		else if (ps.cardtype==2) ps.cardtypename="季卡";
    		else if (ps.cardtype==3) ps.cardtypename="半年卡";
    		else if (ps.cardtype==4) ps.cardtypename="年卡";
    		else if (ps.cardtype==5) ps.cardtypename="299新人月卡";
    		else if (ps.cardtype==6) ps.cardtypename="课程卡成员";
    		if (ps.sex==1) ps.sexvalue="男"; 
    		else ps.sexvalue="女";
    		// 入场密码
    		ComeInPassword cp = ComeInPasswordMgr.getInstance().findComeInPasswordByMemberId(ps.id);
    		if (cp!=null) {
    			Calendar calendar = Calendar.getInstance();  
    			int hour = calendar.get(Calendar.HOUR_OF_DAY);
    			ps.comeinpassword  = cp.arr[hour];
    		}
    	}
    	return 1;
    }
    
    
}

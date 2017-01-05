package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import models.ComeInPassword;
import models.book.BookExercise;
import models.book.BookExerciseMgr;
import models.course.TeamExercise;
import models.course.TeamExerciseMgr;
import models.course.TeamExerciseSchedule;
import models.course.TeamExerciseScheduleMgr;
import models.employee.Employee;
import models.employee.EmployeeMgr;
import models.member.DeadtimeLog;
import models.member.DeadtimeLogMgr;
import models.member.Member;
import models.member.MemberFitnessTest;
import models.member.MemberFitnessTestMgr;
import models.member.MemberMgr;
import models.member.MemberStatus;
import models.member.MemberStatusMgr;
import models.useless.City;
import models.useless.PrivateExercise;
import models.useless.PrivateExerciseMgr;
import models.useless.PrivateExerciseShow;
import play.mvc.*;
import utils.ComeInPasswordMgr;
import utils.UserInOutInfoFromHardMgr;
import utils.common.YearMonthDay;
import utils.useless.CityMgr;

/*
 * ----------会员管理Member---------------
 */
public class MemberApplication extends Controller {

    public static void index() {
        render();
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
    		else if (ps.cardtype==7) ps.cardtypename="2年卡成员";
    		else if (ps.cardtype==8) ps.cardtypename="代金券成员";
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
    
    public static int processOneMemberAttribute(Member ps) {
    	if (ps.cardtype==0) ps.cardtypename="非会员";
    	else if (ps.cardtype==1) ps.cardtypename="月卡";
    	else if (ps.cardtype==2) ps.cardtypename="季卡";
    	else if (ps.cardtype==3) ps.cardtypename="半年卡";
    	else if (ps.cardtype==4) ps.cardtypename="年卡";
    	else if (ps.cardtype==5) ps.cardtypename="299新人月卡";
    	else if (ps.cardtype==6) ps.cardtypename="课程卡成员";
    	else if (ps.cardtype==7) ps.cardtypename="2年卡成员";
    	else if (ps.cardtype==8) ps.cardtypename="代金券成员";
    	return 1;
    }
    
    //搜索页面
    public static void MemberFindPage() {
    	render();
    }
    
    //会员关键字搜索
    public static void MemberFindFromDB(String keyname) {
    	List<Member> list = MemberMgr.getInstance().findMemberByNameOrPhone(keyname);
    	processMemberAttribute(list);
    	int number = list.size();
    	render("MemberApplication/MemberBasic.html", list, number);
    }
    
    //会员复杂搜索
    public static void MemberSearch(int storeid, int acardtype, int amembertype, int asextype, String afitnesstest, String keyname) {
    	String todaytime = YearMonthDay.getCurrentTimeSecond();
    	List<Member> list = MemberMgr.getInstance().searchMember(todaytime, storeid, acardtype, amembertype, asextype, afitnesstest, keyname);
    	processMemberAttribute(list);
    	int number = list.size();
    	render("MemberApplication/MemberBasic.html", list, number);
    }
    
    //所有非会员
    public static void NonMemberSearch(int storeid, int acardtype, int amembertype, int asextype, String afitnesstest, String keyname) {
    	String todaytime = YearMonthDay.getCurrentTimeSecond();
    	List<Member> list = MemberMgr.getInstance().searchMember(todaytime, 0, 0, 0, 0, afitnesstest, keyname);
    	processMemberAttribute(list);
    	int number = list.size();
    	render("MemberApplication/MemberBasic.html", list, number);
    }
    
    
    //普通会员展示页面
    public static void MemberBasic() {
    	String todaytime = YearMonthDay.getCurrentTimeSecond();
    	List<Member> list = MemberMgr.getInstance().getAllActiveNormalMember(todaytime, 0);
    	processMemberAttribute(list);
    	int number = list.size();
    	render(list, number);
    }
    
    //花钱购买课程卡会员展示页面
    public static void MemberSpecial() {
    	String todaytime = YearMonthDay.getCurrentTimeSecond();
    	List<Member> list = MemberMgr.getInstance().getAllActiveSpecialMember(todaytime, 0);
    	processMemberAttribute(list);
    	int number = list.size();
    	render(list, number);
    }
    
    
    //会员详情(修改)页面
    public static void MemberDetail(int id) {
    	Member sc = MemberMgr.getInstance().findMemberById(id);
    	if (sc==null) {
    		renderText("改会员已经被删除，数据库不存在该会员");
    	}
		if (sc.fingerprint==1) sc.fingerprinttype="已录入";
		else sc.fingerprinttype="未录入";
		processOneMemberAttribute(sc);
		if (sc.sex==1) sc.sexvalue="男"; 
		else sc.sexvalue="女";
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
		//该会员历史所有上过的课程展示
		List<BookExercise> listbe = BookExerciseMgr.getInstance().findActiveBookExerciseByMemberId(sc.id);
		List<TeamExerciseSchedule> listtes = new ArrayList<>();
		for (BookExercise be : listbe) {
			if (be.type==0) {
				//注意这里的be.exerciseid是课程排期表的id
				TeamExerciseSchedule tes = TeamExerciseScheduleMgr.getInstance().findTeamExerciseScheduleMoreNameById(be.exerciseid);
				if (tes!=null ) {
					listtes.add(tes);
				}
			}
		}
		//体能测试展示
		MemberFitnessTest mft = MemberFitnessTestMgr.getInstance().getMemberFitnessTestByMemberId(id);
		if (mft==null) {
			mft = new MemberFitnessTest();
		}
		render(sc, listIn, listOut, inOutTmNum, listtes, mft);
    }
    
    //修改会员deaddate页面
	public static void modifyMemberDeaddate(int aid) {
		Member m = MemberMgr.getInstance().findMemberById(aid);
		String cardtypename = "undefined";
		processOneMemberAttribute(m);
		List<DeadtimeLog> list = DeadtimeLogMgr.getInstance().findDeadtimeLogByMemberId(aid);
		render(m, cardtypename, list);
	}
	
	//保存被修改的会员deaddate和卡类型
	public static void modifyMemberDeaddateToDB(int aid, String deaddate, int acardtype) {
		String today = YearMonthDay.getCurrentDay();
		if (acardtype==0 && deaddate.compareTo(today)>0) {
			renderText("选择非会员，则到期时间必须小于等于今天!");
		}
		MemberMgr.getInstance().updateDeaddate(aid, acardtype, deaddate);
		//old:按照情况，给购买会员的人送课，一月一节课
		//new:店长后台操作时间时，并不会影响到课程数变化。
		//MemberMgr.getInstance().updateLeftcoursenum(aid, acardtype);		
		String updatetime = YearMonthDay.getCurrentTimeSecond();
		String employeename = (String) Application.parseSession().get("name");
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
		
		String updatetime = YearMonthDay.getCurrentTimeSecond();
		String cdate = YearMonthDay.getCurrentDay();
		ComeInPasswordMgr.getInstance().save(memberid, cdate, arr, updatetime);
	}
	
	//删除Member
    public static void deleteMember(int id) {
    	boolean flag = MemberMgr.getInstance().deleteMember(id);
    	if (flag) 
    		MemberBasic();
    	else 
    		renderText("删除失败");
    }
    
    //修改会员剩余课时数
    public static void MemberLeftcoursenumEditPage(int id) {
    	Member sc = MemberMgr.getInstance().findMemberById(id);
    	int memberid = id;
    	render(sc, memberid);
    }
    
    public static void MemberLeftcoursenumEditToDB(int memberid, int aleftcoursenum) {
    	MemberMgr.getInstance().updateMemberLeftcoursenum(memberid, aleftcoursenum);
    	MemberDetail(memberid);
    }
    
    //修改45天特训营购买次数 
    public static void MemberBuycountEditPage(int id) {
    	Member sc = MemberMgr.getInstance().findMemberById(id);
    	int memberid = id;
    	render(sc, memberid);
    }
    
    public static void MemberBuycountEditToDB(int memberid, int newbuycount) {
    	MemberMgr.getInstance().updateMemberBuycount(memberid, newbuycount);
    	MemberDetail(memberid);
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
    	MemberDetail(memberid);
    }
    
    //修改会员体能测试
    public static void MemberFitnessTestEdit(int id) {
    	MemberFitnessTest mft = MemberFitnessTestMgr.getInstance().getMemberFitnessTestByMemberId(id);
    	int memberid = id;
    	if (mft==null) 
    		render("MemberApplication/MemberFitnessTestAdd.html",memberid);
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
    
    //修改会员的所属门店
    public static void MemberStoreidEdit(int id) {
    	Member sc = MemberMgr.getInstance().findMemberById(id);
    	int memberid = id;
    	render(sc, memberid);
    }
    
    public static void addMemberStoreidToDB(int memberid, int storeid) {
    	MemberMgr.getInstance().updateMemberStoreid(memberid, storeid);
    	MemberDetail(memberid);
    }
    
    //修改会员企业
    public static void MemberCompanyidEdit(int id) {
    	Member sc = MemberMgr.getInstance().findMemberById(id);
    	int memberid = id;
    	render(sc, memberid);
    }
    
    public static void addMemberCompanyidToDB(int memberid, int companyid) {
    	MemberMgr.getInstance().updateMemberCompanyid(memberid, companyid);
    	MemberDetail(memberid);
    }
    
    //修改会员种类
    public static void MemberCardtypeEdit(int id) {
    	Member ps = MemberMgr.getInstance().findMemberById(id);
    	processOneMemberAttribute(ps);
    	int memberid = id;
    	render(ps, memberid);
    }
    
    public static void addMemberCardtypeToDB(int memberid, int newcardtype) {
    	MemberMgr.getInstance().updateMemberCardtype(memberid, newcardtype);
    	MemberDetail(memberid);
    }
    
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
     * -------------------------分店页面------------------------------
     */
    
    //搜索页面
    public static void MemberFindPageFen(int storeid) {
    	render(storeid);
    }
    
    //普通Member展示页面
    public static void MemberBasicFen(int storeid) {
    	String todaytime = YearMonthDay.getCurrentTimeSecond();
    	List<Member> list = MemberMgr.getInstance().getAllActiveNormalMember(todaytime, storeid);
    	processMemberAttribute(list);
    	int number = list.size();
    	render("MemberApplication/MemberBasicFen.html", storeid, list, number);
    }
    
    //花钱购买课程卡Member展示页面
    public static void MemberSpecialFen(int storeid) {
    	String todaytime = YearMonthDay.getCurrentTimeSecond();
    	List<Member> list = MemberMgr.getInstance().getAllActiveSpecialMember(todaytime, storeid);
    	processMemberAttribute(list);
    	int number = list.size();
    	render("MemberApplication/MemberBasicFen.html", storeid, list, number);
    }
    
    
    
    
}

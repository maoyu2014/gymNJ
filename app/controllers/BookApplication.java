package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.book.BookExercise;
import models.book.BookExerciseMgr;
import models.course.TeamExercise;
import models.course.TeamExerciseMgr;
import models.course.TeamExerciseSchedule;
import models.course.TeamExerciseScheduleMgr;
import models.employee.Employee;
import models.employee.EmployeeMgr;
import models.member.Member;
import models.member.MemberMgr;
import models.store.StoreMgr;
import models.useless.PrivateExercise;
import models.useless.PrivateExerciseMgr;
import models.useless.PrivateExerciseShow;
import models.useless.StoreCity;
import play.mvc.*;
import utils.common.YearMonthDay;

/*
 * --------预约管理-------------
 */
public class BookApplication extends Controller {

    public static void index() {
        render();
    }

    //课程预约展示
    public static void BookTeamExerciseScheduleDisplay(int storeid, String yearmonth) {
    	String currentmonth = YearMonthDay.getCurrentMonth();
    	if (yearmonth==null || yearmonth.length()==0) yearmonth = currentmonth;
    	List<BookExercise> listBookExercise = BookExerciseMgr.getInstance().searchActiveBookExercise(storeid, yearmonth);
    	int number = listBookExercise.size();
    	render(listBookExercise, number);
    }
    
    
    /*
     * --------------------------分店系统------------
     */
    
    public static void BookTeamExerciseScheduleDisplayFen(int storeid, String yearmonth) {
    	String currentmonth = YearMonthDay.getCurrentMonth();
    	if (yearmonth==null || yearmonth.length()==0) yearmonth = currentmonth;
    	List<BookExercise> listBookExercise = BookExerciseMgr.getInstance().searchActiveBookExercise(storeid, yearmonth);
    	int number = listBookExercise.size();
    	render(storeid, listBookExercise, number);
    }
    
    
    
    
}

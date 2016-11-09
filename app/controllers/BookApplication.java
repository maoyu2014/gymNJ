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

    /*
    //特训营预约展示
    public static void BookPrivateExerciseDisplay() {
    	List<BookExercise> list = BookExerciseMgr.getInstance().getAllActivePrivateBookExercise();
    	for (BookExercise b : list) {
    		if (b.type==0) b.typename="团操";
    		else if (b.type==1) b.typename="特训营";
    		else b.typename="undefined";
    	}
    	int number = list.size();
    	render(list, number);
    }
    */
    
    //课程预约展示
    public static void BookTeamExerciseScheduleDisplay() {
    	String currentmonth = YearMonthDay.getCurrentMonth();
    	List<BookExercise> listBookExercise = BookExerciseMgr.getInstance().getMonthActiveBookTeamExerciseSchedule(currentmonth);
//    	for (BookExercise b : listBookExercise) {
//    		b.typename="团操";
//    	}
    	int number = listBookExercise.size();
    	render(listBookExercise, number);
    }
    
    
    
}

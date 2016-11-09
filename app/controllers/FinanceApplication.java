package controllers;

import java.util.ArrayList;
import java.util.List;

import models.finance.PurchaseHistory;
import models.finance.PurchaseHistoryMgr;
import models.member.Member;
import models.member.MemberMgr;
import play.mvc.*;
import utils.common.YearMonthDay;

/*
 * ----------财务管理PurchaseHistory---------
 */
public class FinanceApplication extends Controller {

    public static void index() {
        render();
    }

    //当月财务展示页面
    public static void PurchaseHistoryMonth() {
    	String currentmonth = YearMonthDay.getCurrentMonth();
    	List<PurchaseHistory> listPurchaseHistory = PurchaseHistoryMgr.getInstance().getMonthPurchaseHistory(currentmonth);
    	double feesum=0.0;
    	for (PurchaseHistory ph : listPurchaseHistory) {
    		feesum+=ph.fee;
    		if (ph.cardtype==1) ph.cardtypename="月卡";
    		else if (ph.cardtype==2) ph.cardtypename="季卡";
    		else if (ph.cardtype==3) ph.cardtypename="半年卡";
    		else if (ph.cardtype==4) ph.cardtypename="年卡";
    		else if (ph.cardtype==5) ph.cardtypename="299新人月卡";
    		else if (ph.cardtype==6) ph.cardtypename="课程卡成员";
    		if (ph.purchasetype==1) ph.purchasetypename="微信支付";
    		if (ph.isprivate==1) ph.cardtypename="私教课程";
    	}
    	int number = listPurchaseHistory.size();
    	render(listPurchaseHistory, number, feesum);
    }
    
    //历史总收入
    public static void PurchaseHistoryAll() {
    	double feesum = PurchaseHistoryMgr.getInstance().getAllPurchaseHistoryFee();
    	render(feesum);
    }
    
}

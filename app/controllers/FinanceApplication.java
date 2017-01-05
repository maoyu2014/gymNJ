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

    public static int processOnePurchaseAttribute(PurchaseHistory ph) {
    	if (ph.cardtype==1) ph.cardtypename="月卡付费";
		else if (ph.cardtype==2) ph.cardtypename="季卡付费";
		else if (ph.cardtype==3) ph.cardtypename="半年卡付费";
		else if (ph.cardtype==4) ph.cardtypename="年卡付费";
		else if (ph.cardtype==5) ph.cardtypename="299新人月卡付费";
		else if (ph.cardtype==6) ph.cardtypename="课程卡付费";
		else if (ph.cardtype==7) ph.cardtypename="2年卡付费";
		else if (ph.cardtype==8) ph.cardtypename="代金券付费";
    	return 1;
    }
    
    //当月财务展示页面
    public static void PurchaseHistoryMonth(int storeid, String yearmonth) {
    	String currentmonth = YearMonthDay.getCurrentMonth();
    	if (yearmonth==null || yearmonth.length()==0) yearmonth = currentmonth;
    	List<PurchaseHistory> listPurchaseHistory = PurchaseHistoryMgr.getInstance().getMonthPurchaseHistory(storeid, yearmonth);
    	double feesum=0.0;
    	for (PurchaseHistory ph : listPurchaseHistory) {
    		feesum+=ph.fee;
    		processOnePurchaseAttribute(ph);
    		
    		if (ph.purchasetype==1) ph.purchasetypename="微信支付";
    		if (ph.isprivate==1) ph.cardtypename="私教课程付费";
    	}
    	int number = listPurchaseHistory.size();
    	render(listPurchaseHistory, number, feesum);
    }
    
    //历史总收入
    public static void PurchaseHistoryAll() {
    	double feesum = PurchaseHistoryMgr.getInstance().getAllPurchaseHistoryFee();
    	render(feesum);
    }
    
    
    /*
     * -----------分店系统----------------
     */
    public static void PurchaseHistoryMonthFen(int storeid, String yearmonth) {
    	String currentmonth = YearMonthDay.getCurrentMonth();
    	if (yearmonth==null || yearmonth.length()==0) yearmonth = currentmonth;
    	List<PurchaseHistory> listPurchaseHistory = PurchaseHistoryMgr.getInstance().getMonthPurchaseHistory(storeid, yearmonth);
    	double feesum=0.0;
    	for (PurchaseHistory ph : listPurchaseHistory) {
    		feesum+=ph.fee;
    		processOnePurchaseAttribute(ph);
    		
    		if (ph.purchasetype==1) ph.purchasetypename="微信支付";
    		if (ph.isprivate==1) ph.cardtypename="私教课程付费";
    	}
    	int number = listPurchaseHistory.size();
    	render(listPurchaseHistory, number, feesum);
    }
    
}

package utils.common;

import java.util.Calendar;

public class YearMonthDay {

	/*
     * -----------------公用方法--------------------
     */
    
    public static String getCurrentTimeSecond() {
    	Calendar calendar = Calendar.getInstance();  
		String updatetime = ""+calendar.get(Calendar.YEAR)+"-";
		int month = calendar.get(Calendar.MONTH)+1; 
		if (month<10) updatetime+="0"+month+"-"; else updatetime += month+"-";
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day<10) updatetime+="0"+day+" "; else updatetime+=day+" ";
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour<10) updatetime+="0"+hour+":"; else updatetime+=hour+":";
		int minutes = calendar.get(Calendar.MINUTE);
		if (minutes<10) updatetime+="0"+minutes+":"; else updatetime+=minutes+":";
		int seconds = calendar.get(Calendar.SECOND);
		if (seconds<10) updatetime+="0"+seconds; else updatetime+=seconds;
		return updatetime;
    }
    
    public static String getCurrentDay() {
    	Calendar calendar = Calendar.getInstance();  
		String cdate = ""+calendar.get(Calendar.YEAR)+"-";
		int month = calendar.get(Calendar.MONTH)+1; 
		if (month<10) cdate+="0"+month+"-"; else cdate += month+"-";
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day<10) cdate+="0"+day; else cdate+=day;
    	return cdate;
    }
    
    public static String getCurrentMonth() {
    	Calendar calendar = Calendar.getInstance();  
		String cdate = ""+calendar.get(Calendar.YEAR)+"-";
		int month = calendar.get(Calendar.MONTH)+1; 
		if (month<10) cdate+="0"+month; else cdate += month;
    	return cdate;
    }
    
    
}

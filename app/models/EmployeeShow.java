package models;

public class EmployeeShow {
	
	public int id;
	
	public String username;
	public String password;
	
	public String name;
	public String headimage;
	public int sex;				//1表示男，2表示女
	public String phone;
	
	public int ismanager;
	public int isfinance;
	public int iscoach;
	
	public int domember;
	public int doappointment;
	public int docourse;
	public int doplan;
	public int domarkte;
	public int dofinance;
	public int doemployee;
	public int dostore;
	public int dostatistics;
	
	public int storeid;
	public String storename;
	public String introduce;
	public String openid;
	
	public int comeinpassword;
	
	public EmployeeShow(int id, String username, String password, String name,
			String headimage, int sex, String phone, int ismanager,
			int isfinance, int iscoach, int domember, int doappointment,
			int docourse, int doplan, int domarkte, int dofinance,
			int doemployee, int dostore, int dostatistics, int storeid,
			String storename, String introduce, String openid) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.headimage = headimage;
		this.sex = sex;
		this.phone = phone;
		this.ismanager = ismanager;
		this.isfinance = isfinance;
		this.iscoach = iscoach;
		this.domember = domember;
		this.doappointment = doappointment;
		this.docourse = docourse;
		this.doplan = doplan;
		this.domarkte = domarkte;
		this.dofinance = dofinance;
		this.doemployee = doemployee;
		this.dostore = dostore;
		this.dostatistics = dostatistics;
		this.storeid = storeid;
		this.storename = storename;
		this.introduce = introduce;
		this.openid = openid;
	}
	
	public EmployeeShow(String username, String password, String name,
			String headimage, int sex, String phone, int ismanager,
			int isfinance, int iscoach, int domember, int doappointment,
			int docourse, int doplan, int domarkte, int dofinance,
			int doemployee, int dostore, int dostatistics, int storeid,
			String storename, String introduce, String openid) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.headimage = headimage;
		this.sex = sex;
		this.phone = phone;
		this.ismanager = ismanager;
		this.isfinance = isfinance;
		this.iscoach = iscoach;
		this.domember = domember;
		this.doappointment = doappointment;
		this.docourse = docourse;
		this.doplan = doplan;
		this.domarkte = domarkte;
		this.dofinance = dofinance;
		this.doemployee = doemployee;
		this.dostore = dostore;
		this.dostatistics = dostatistics;
		this.storeid = storeid;
		this.storename = storename;
		this.introduce = introduce;
		this.openid = openid;
	}

	public EmployeeShow(Employee em) {
		this.id = em.id;
		this.username = em.username;
		this.password = em.password;
		this.name = em.name;
		this.headimage = em.headimage;
		this.sex = em.sex;
		this.phone = em.phone;
		this.ismanager = em.ismanager;
		this.isfinance = em.isfinance;
		this.iscoach = em.iscoach;
		this.domember = em.domember;
		this.doappointment = em.doappointment;
		this.docourse = em.docourse;
		this.doplan = em.doplan;
		this.domarkte = em.domarkte;
		this.dofinance = em.dofinance;
		this.doemployee = em.doemployee;
		this.dostore = em.dostore;
		this.dostatistics = em.dostatistics;
		this.storeid = em.storeid;
		this.introduce = em.introduce;
		this.openid = em.openid;
	}
		
	
}

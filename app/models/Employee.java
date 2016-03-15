package models;

public class Employee {
	
	public int id;
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
	public String introduce;
	
	public Employee(int id, String name, String headimage, int sex,
			String phone, int ismanager, int isfinance, int iscoach,
			int domember, int doappointment, int docourse, int doplan,
			int domarkte, int dofinance, int doemployee, int dostore,
			int dostatistics, int storeid, String introduce) {
		this.id = id;
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
		this.introduce = introduce;
	}
	
	public Employee(String name, String headimage, int sex,
			String phone, int ismanager, int isfinance, int iscoach,
			int domember, int doappointment, int docourse, int doplan,
			int domarkte, int dofinance, int doemployee, int dostore,
			int dostatistics, int storeid, String introduce) {
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
		this.introduce = introduce;
	}
	
	
	
}

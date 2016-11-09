package models;

public class Groupbuy {

	public int id;
	public int groupwebsiteid;
	public int storeid;
	public double price;
	public double times;
	public String starttime;
	public String endtime;
	public String introduce;
	public String weburl;
	
	public Groupbuy(int id, int groupwebsiteid, int storeid, double price, double times,
			String starttime, String endtime, String introduce, String weburl) {
		this.id = id;
		this.groupwebsiteid = groupwebsiteid;
		this.storeid = storeid;
		this.price = price;
		this.times = times;
		this.starttime = starttime;
		this.endtime = endtime;
		this.introduce = introduce;
		this.weburl = weburl;
	}
	
	public Groupbuy(int groupwebsiteid, int storeid, double price, double times,
			String starttime, String endtime, String introduce, String weburl) {
		this.groupwebsiteid = groupwebsiteid;
		this.storeid = storeid;
		this.price = price;
		this.times = times;
		this.starttime = starttime;
		this.endtime = endtime;
		this.introduce = introduce;
		this.weburl = weburl;
	}
	
}

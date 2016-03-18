package models;

public class Groupbuy {

	public int id;
	public int groupwebsiteid;
	public int storeid;
	public double price;
	public String starttime;
	public String endtime;
	public String introduce;
	public String weburl;
	
	public Groupbuy(int id, int groupwebsiteid, int storeid, double price,
			String starttime, String endtime, String introduce, String weburl) {
		this.id = id;
		this.groupwebsiteid = groupwebsiteid;
		this.storeid = storeid;
		this.price = price;
		this.starttime = starttime;
		this.endtime = endtime;
		this.introduce = introduce;
		this.weburl = weburl;
	}
	
	public Groupbuy(int groupwebsiteid, int storeid, double price,
			String starttime, String endtime, String introduce, String weburl) {
		this.groupwebsiteid = groupwebsiteid;
		this.storeid = storeid;
		this.price = price;
		this.starttime = starttime;
		this.endtime = endtime;
		this.introduce = introduce;
		this.weburl = weburl;
	}
	
}

package models.useless;


public class GroupbuyShow {

	public int id;
	public int groupwebsiteid;
	public int storeid;
	public double price;
	public double times;
	public String starttime;
	public String endtime;
	public String introduce;
	public String weburl;
	
	public String groupwebsitename;
	public String storename;
	public String status;
	
	public GroupbuyShow(int id, int groupwebsiteid, int storeid, double price, double times,
			String starttime, String endtime, String introduce, String weburl,
			String groupwebsitename, String storename, String status) {
		this.id = id;
		this.groupwebsiteid = groupwebsiteid;
		this.storeid = storeid;
		this.price = price;
		this.times = times;
		this.starttime = starttime;
		this.endtime = endtime;
		this.introduce = introduce;
		this.weburl = weburl;
		this.groupwebsitename = groupwebsitename;
		this.storename = storename;
		this.status = status;
	}
	
	public GroupbuyShow(int groupwebsiteid, int storeid, double price, double times,
			String starttime, String endtime, String introduce, String weburl,
			String groupwebsitename, String storename, String status) {
		this.groupwebsiteid = groupwebsiteid;
		this.storeid = storeid;
		this.price = price;
		this.times = times;
		this.starttime = starttime;
		this.endtime = endtime;
		this.introduce = introduce;
		this.weburl = weburl;
		this.groupwebsitename = groupwebsitename;
		this.storename = storename;
		this.status = status;
	}
	
	
	public GroupbuyShow(Groupbuy gg) {
		this.id = gg.id;
		this.groupwebsiteid = gg.groupwebsiteid;
		this.storeid = gg.storeid;
		this.price = gg.price;
		this.times = gg.times;
		this.starttime = gg.starttime;
		this.endtime = gg.endtime;
		this.introduce = gg.introduce;
		this.weburl = gg.weburl;
	}
	
}

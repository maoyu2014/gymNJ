package models;

public class GroupbuyShow {

	public int id;
	public int groupwebsiteid;
	public int storeid;
	public double price;
	public String starttime;
	public String endtime;
	public String introduce;
	public String weburl;
	
	public String groupwebsitename;
	public String storename;
	
	public GroupbuyShow(int id, int groupwebsiteid, int storeid, double price,
			String starttime, String endtime, String introduce, String weburl,
			String groupwebsitename, String storename) {
		this.id = id;
		this.groupwebsiteid = groupwebsiteid;
		this.storeid = storeid;
		this.price = price;
		this.starttime = starttime;
		this.endtime = endtime;
		this.introduce = introduce;
		this.weburl = weburl;
		this.groupwebsitename = groupwebsitename;
		this.storename = storename;
	}
	
	public GroupbuyShow(int groupwebsiteid, int storeid, double price,
			String starttime, String endtime, String introduce, String weburl,
			String groupwebsitename, String storename) {
		this.groupwebsiteid = groupwebsiteid;
		this.storeid = storeid;
		this.price = price;
		this.starttime = starttime;
		this.endtime = endtime;
		this.introduce = introduce;
		this.weburl = weburl;
		this.groupwebsitename = groupwebsitename;
		this.storename = storename;
	}
	
	
	public GroupbuyShow(Groupbuy gg) {
		this.id = gg.id;
		this.groupwebsiteid = gg.groupwebsiteid;
		this.storeid = gg.storeid;
		this.price = gg.price;
		this.starttime = gg.starttime;
		this.endtime = gg.endtime;
		this.introduce = gg.introduce;
		this.weburl = gg.weburl;
	}
	
}

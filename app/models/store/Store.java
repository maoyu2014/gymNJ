package models.store;

public class Store {
	
	public int id;
	public int cityid;	//2016年11月弃用
	public String name;
	public String address;
	public double area;
	public String photo;
	public String manager;
	public String phone;
	
	public Store(int id, int cityid, String name, String address, double area,
			String photo, String manager, String phone) {
		this.id = id;
		this.cityid = cityid;
		this.name = name;
		this.address = address;
		this.area = area;
		this.photo = photo;
		this.manager = manager;
		this.phone = phone;
	}


	//用来添加门店时不知道ID情况时使用
	public Store(int cityid, String name, String address, double area,
			String photo, String manager, String phone) {
		this.cityid = cityid;
		this.name = name;
		this.address = address;
		this.area = area;
		this.photo = photo;
		this.manager = manager;
		this.phone = phone;
	}
	
	
}

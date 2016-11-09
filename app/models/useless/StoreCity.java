package models;

public class StoreCity {

	public int id;
	public int cityid;
	public String name;
	public String cityname;
	public String address;
	public double area;
	public String photo;
	public String manager;
	public String phone;
	
	public StoreCity() {
	}
	
	
	public StoreCity(int id, int cityid, String name, String cityname,
			String address, double area, String photo, String manager,
			String phone) {
		this.id = id;
		this.cityid = cityid;
		this.name = name;
		this.cityname = cityname;
		this.address = address;
		this.area = area;
		this.photo = photo;
		this.manager = manager;
		this.phone = phone;
	}
	
	
	
}

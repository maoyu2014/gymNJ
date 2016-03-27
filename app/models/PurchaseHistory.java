package models;

public class PurchaseHistory {

	public int id;
	public String orderid;		//微信支付返回的一个值
	public int memberid;
	public int carttype;	//1月 2季度 3半年 4年 5啥都不是
	public double fee;
	public String purchasetime;
	public int purchasetype;	//购买方式,1代表微信支付，目前就做微信支付
	public int isprivate;	//0表示购买会员，1表示购买私教课付费
	public int bookid;
	
	//额外展示使用
	public String notice;
	public String purchasetypename;
	
	public PurchaseHistory(int id, String orderid, int memberid, int carttype,
			double fee, String purchasetime, int purchasetype, int isprivate) {
		this.id = id;
		this.orderid = orderid;
		this.memberid = memberid;
		this.carttype = carttype;
		this.fee = fee;
		this.purchasetime = purchasetime;
		this.purchasetype = purchasetype;
		this.isprivate = isprivate;
	}
	
	public PurchaseHistory(int id, String orderid, int memberid, int carttype,
			double fee, String purchasetime, int purchasetype, int isprivate, int bookid) {
		this.id = id;
		this.orderid = orderid;
		this.memberid = memberid;
		this.carttype = carttype;
		this.fee = fee;
		this.purchasetime = purchasetime;
		this.purchasetype = purchasetype;
		this.isprivate = isprivate;
		this.bookid = bookid;
	}
	
	
	
}

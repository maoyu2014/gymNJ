package models;

//会员
public class Member {

	public int id;
	public String openid;			//微信唯一标识
	public String name;
	public String wechatname;
	public String wechatnumber;
	public int sex;		//1男 2女
	public double height;
	public String birthday;	//生日
	public String phone;
	public int fingerprint;	//指纹状态，1已录入 2未录入
	public int comeinpassword;				//入场密码表，注意这个字段不在构造函数中，因为字段是变化的
	public int cityid;		//会员属于哪个城市
	public int cardtype;	//会员卡种类，进来默认是0非会员  1月卡，2季卡，3半年卡，4年卡
	public String deaddate;	//到期时间
	
	public int exercisetime; 	//时间   1早上 2下午 3晚上
	public int exercisegoal; 	//目标   1减脂 2塑形 3增肌
	public int exercisehz;		//频率，1难的  2一周一次   3一周三次   4每天
	public int distance; 	//距离  1：一公里以内  2：三公里以内  3：三公里以外
	
	public String fitnesstest;	//体能测试
	public String memberstatus;	//会员状态
	
	//体脂信息
	public String bmi;			//BMI
	public String muscle;		//肌肉率
	public String fat;			//脂肪
	public String innerfat;		//内脏脂肪
	public String water;		//水分
	public String protein;		//蛋白质
	public int basicrate;	//基础代谢率
	public int bodyage;		//身体年龄
	
	public String recommendedphone;	//推荐人的的手机号
	public int recommendnums;		//推荐了多少人
	
	//展示信息
	public String sexvalue;
	public String fingerprinttype;
	public String cityname;
	public String offenstorename;
	public String cardtypename;
	public String exercisetimevalue;
	public String exercisegoalvalue;
	public String exercisehzvalue;
	public String distancevalue;
		
	public int leftcoursenum;
	
	public Member() {}
	
	public Member(int id, String openid, String name, String wechatname,
			int sex, double height, String birthday, String phone,
			int fingerprint, int cityid,
			int cardtype, String deaddate, int exercisetime, int exercisegoal, int exercisehz,
			int distance, String bmi, String muscle, String fat, String water,
			String protein, int basicrate, int bodyage,
			String wechatnumber, String fitnesstest, String memberstatus, String innerfat, int leftcouresnum) {
		this.id = id;
		this.openid = openid;
		this.name = name;
		this.wechatname = wechatname;
		this.sex = sex;
		this.height = height;
		this.birthday = birthday;
		this.phone = phone;
		this.fingerprint = fingerprint;
		this.cityid = cityid;
		this.cardtype = cardtype;
		this.deaddate = deaddate;
		this.exercisetime = exercisetime;
		this.exercisegoal = exercisegoal;
		this.exercisehz = exercisehz;
		this.distance = distance;
		this.bmi = bmi;
		this.muscle = muscle;
		this.fat = fat;
		this.water = water;
		this.protein = protein;
		this.basicrate = basicrate;
		this.bodyage = bodyage;
		this.wechatnumber = wechatnumber;
		this.fitnesstest = fitnesstest;
		this.memberstatus = memberstatus;
		this.innerfat = innerfat;
		this.leftcoursenum = leftcouresnum;
	}
	
	//recommended人用的
	public Member(int id, String openid, String name, String wechatname, String phone, String deaddate) {
		this.id = id;
		this.openid = openid;
		this.name = name;
		this.wechatname = wechatname;
		this.phone = phone;
		this.deaddate = deaddate;
	}

	//会员信息首页用的
	public Member(int id, String openid, String name, String wechatname,
			String phone, int fingerprint, String cityname, int cardtype,
			String wechatnumber, String fitnesstest, String memberstatus,
			int leftcoursenum) {
		this.id = id;
		this.openid = openid;
		this.name = name;
		this.wechatname = wechatname;
		this.phone = phone;
		this.fingerprint = fingerprint;
		this.cityname = cityname;
		this.cardtype = cardtype;
		this.wechatnumber = wechatnumber;
		this.fitnesstest = fitnesstest;
		this.memberstatus = memberstatus;
		this.leftcoursenum = leftcoursenum;
	}
	
	
	
	
	
	
}

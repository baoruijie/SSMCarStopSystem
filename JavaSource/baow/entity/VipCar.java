package baow.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import baow.tools.Utils;


public class VipCar {
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)     //表示主键自增
	private String id;
	
	private String carNo;
	@Column(name="CVC_BELONGNAME")
	private String belongName;
	@Column(name="CVC_MOBILE")
	private String mobile;
	@Column(name="CVC_ORDERDATE")
	private String orderDate="0";
	@Column(name="CVC_ENDDATE")
	private String endDate="0";
	@Column(name="CVC_STT")
	private String state;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name="CVC_CAR_NO")
	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getBelongName() {
		return belongName;
	}

	public void setBelongName(String belongName) {
		this.belongName = belongName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public VipCar(String id, String carNo, String belongName, String mobile,
			String orderDate, String endDate, String state) {
		super();
		this.id = id;
		this.carNo = carNo;
		this.belongName = belongName;
		this.mobile = mobile;
		this.orderDate = orderDate;
		this.endDate = endDate;
		this.state = state;
	}

	public VipCar() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "VipCar [id=" + id + ", carNo=" + carNo + ", mobile=" + mobile + ", orderDate="
				+ Utils.formatMilTime(Long.valueOf(orderDate),"yyyy年MM月dd日 HH:mm:ss") + ", endDate=" 
				+ Utils.formatMilTime(Long.valueOf(endDate),"yyyy年MM月dd日 HH:mm:ss") + ", state=" + state + "]";
	}
}

package baow.entity;

public class Garage {
	private int id;
	private String carNo;
	private String timeIn;
	private String flag;
	public Garage() {
		super();
	}
	public Garage(int id, String carNo, String timeIn, String flag) {
		super();
		this.id = id;
		this.carNo = carNo;
		this.timeIn = timeIn;
		this.flag = flag;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getTimeIn() {
		return timeIn;
	}
	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "Garage [id=" + id + ", carNo=" + carNo + ", timeIn=" + timeIn
				+ ", flag=" + flag + "]";
	}
	
}

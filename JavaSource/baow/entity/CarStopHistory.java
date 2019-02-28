package baow.entity;
/*
`ID` int(11) NOT NULL AUTO_INCREMENT,
`CS_CAR_NO` varchar(9) NOT NULL,
`CS_IN_TIME` varchar(20) NOT NULL,
`CS_OUT_TIME` varchar(20) NOT NULL,
`CS_STOP_TIME` varchar(20) NOT NULL,
*/
public class CarStopHistory {

	private int id;
	private String carNo;
	private String timeIn;
	private String timeOut;
	private String stopTime;
	public CarStopHistory() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CarStopHistory(String carNo, String timeIn, String timeOut,String stopTime) {
		super();
		this.carNo = carNo;
		this.timeIn = timeIn;
		this.timeOut = timeOut;
		this.stopTime = stopTime;
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
	public String getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}
	public String getStopTime() {
		return stopTime;
	}
	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
	@Override
	public String toString() {
		return "CarStopHistory [id="+ id +", carNo=" + carNo + ", timeIn=" + timeIn
				+ ", timeOut=" + timeOut + ", stopTime=" + stopTime + "]";
	}
	
}

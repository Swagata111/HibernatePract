public class Employee {

	private int empNo;
	private String empNm;
	private Float empSal;

	public Employee() {

	}
	
	

	public Employee( String empNm, Float empSal) {
		super();
		this.empNm = empNm;
		this.empSal = empSal;
	}



	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

	public String getEmpNm() {
		return empNm;
	}

	public void setEmpNm(String empNm) {
		this.empNm = empNm;
	}

	public Float getEmpSal() {
		return empSal;
	}

	public void setEmpSal(Float empSal) {
		this.empSal = empSal;
	}

	public String toString() {
		return "EmpNo:" + empNo + " Name:" + empNm + " Salary:" + empSal;
	}

}

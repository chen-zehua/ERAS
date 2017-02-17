package model;

public class Project 
{
	private String project;
	private String passSrc;
	private String failSrc;
	private String passExe;
	private String failExe;
	private String testcase;
	
	public Project(){
		this(null, null, null, null, null, null);
	}
	
	public Project(String project, String passSrc, String failSrc, String passExe, 
			String failExe, String testcase){
		this.project = project;
		this.passSrc = passSrc;
		this.failSrc = failSrc;
		this.passExe = passExe;
		this.failExe = failExe;
		this.testcase = testcase;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getPassSrc() {
		return passSrc;
	}
	public void setPassSrc(String passSrc) {
		this.passSrc = passSrc;
	}
	public String getFailSrc() {
		return failSrc;
	}
	public void setFailSrc(String failSrc) {
		this.failSrc = failSrc;
	}
	public String getPassExe() {
		return passExe;
	}
	public void setPassExe(String passExe) {
		this.passExe = passExe;
	}
	public String getFailExe() {
		return failExe;
	}
	public void setFailExe(String failExe) {
		this.failExe = failExe;
	}
	public String getTestcase() {
		return testcase;
	}
	public void setTestcase(String testcase) {
		this.testcase = testcase;
	}
	
	public String toString(){
		return project;
	}
}

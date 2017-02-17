package model;

public class NodeData {
	//the operation was done on specific variable
	public enum Action{
		WRITE,READ
	}
	//the operation tye
	private String action;
	//the variable address
	private String address;
	//the value of this variable
	private String value;

	public NodeData(String action, String address, String value){
		this.action = action;
		this.address =address;
		this.value = value;
	}
	public String getAction() {
		return action;
	}

	public String getAddress() {
		return address;
	}

	public String getValue() {
		return value;
	}

}

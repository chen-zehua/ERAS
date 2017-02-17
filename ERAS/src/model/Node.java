package model;

import java.util.ArrayList;

public class Node {
	// the Number of this node in the trace
	private String traceNum;
	//the file this node belong to
	private String file;
	//the line number of this node
	private String lineNum;
	//the category of this node
	private String category;
	//the dependences of this node
	private ArrayList<Node> dependences = new ArrayList<>();;
	//the variables' address and value written and read at this node
	private ArrayList<NodeData> nodeDatas = new ArrayList<>();
	//the corresponding node of this node
	private Node corresNode;
	
	public Node(String traceNum, String file, String lineNum, String category){
		this.traceNum = traceNum;
		this.file = file;
		this.lineNum = lineNum;
		this.category = category;
	}
	public String getTaceNum() {
		return traceNum;
	}

	public String getFile() {
		return file;
	}

	public String getLineNum() {
		return lineNum;
	}
	
	public String getcategory(){
		return category;
	}

	public ArrayList<Node> getDependences() {
		return dependences;
	}
	public ArrayList<NodeData> getNodeDatas() {
		return nodeDatas;
	}
	public Node getCorreNode() {
		return corresNode;
	}
	
	public void setCorresNode(Node node){
		this.corresNode = node;
	}
	
	public void addDependence(Node node){
		dependences.add(node);
	}
	
	public void addNodeData(NodeData data){
		nodeDatas.add(data);
	}
	
	public String toString(){
		return this.traceNum+this.file+this.lineNum;
	}
}

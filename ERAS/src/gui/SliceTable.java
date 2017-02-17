package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.Node;
import model.NodeData;

public class SliceTable {
	private JLabel jl;
	private JTable jt;
	private JTextArea jta = new JTextArea(20, 20);
	private JTextPane jtp = new JTextPane();
	private JPanel jp;
	
	private DefaultTableModel model;
	private String srcDir;
	private String corresSrcDir;
	private String projDir;
	private String[] columnNames = {"trace#", "file", "line#", "category"};
	private String title;
	private String prefix;
	
	private Map<String, Node> nodesMap = new HashMap<>();
	
	public SliceTable(String title, String prefix){
		this.title = title;
		this.prefix = prefix;
		jl = new JLabel(title + "Slice Table");
		model = new DefaultTableModel(columnNames, 0);
		jt = new JTable(model);
		jt.setFillsViewportHeight(true);
		jt.setRowSelectionAllowed(true);
		jt.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jt.getSelectionModel().addListSelectionListener(new RowListener());
				
		jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
//		jp.add(jl);
		jp.add(new JScrollPane(jt));
		
		jtp.setPreferredSize(new Dimension(100, 200));
		jtp.setEditable(false);
		jtp.setContentType("text/html");
		jtp.addHyperlinkListener(new HyperActive());
		JScrollPane textScrollPane = new JScrollPane(jtp);
//		textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		textScrollPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Selected Statement Details"),
				BorderFactory.createEmptyBorder(0, 0, 0, 0)));
		
		jp.add(textScrollPane);
		jp.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(title+"Slice Table"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}
	
	private class RowListener implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			int row = jt.getSelectedRow();
			String key = String.valueOf(jt.getValueAt(row, 0));
			Node node = nodesMap.get(key);
			//display statement details
			display(node);
			//display the corresponding src of this 'row' on SrcView
			int markLine = Integer.parseInt(String.valueOf(jt.getValueAt(row, 2)));
			String file = String.valueOf(jt.getValueAt(row, 1));
//			ERAS.getSrcView().getFailJlUp().setText(file+"&"+markLine);
			ERAS.getSrcView().getFileViewUp().open(srcDir+"/"+file, markLine);
			if(node.getCorreNode() != null){
				int corresLine = Integer.parseInt(String.valueOf(node.getCorreNode().getLineNum()));
				String corresFile = node.getCorreNode().getFile();
				ERAS.getSrcView().getFileViewDown().open(corresSrcDir+"/"+corresFile, corresLine);
			}else{
				ERAS.getSrcView().getFileViewDown().getJTA().setText("");
			}
			
			/*if(title == "Fail "){
				int count = jt.getSelectedRowCount();
				if(count == 1){
					
					int row = jt.getSelectedRow();
					String key = String.valueOf(jt.getValueAt(row, 0));
					Node node = nodesMap.get(key);
					//display statement details
					display(node);
					//display the corresponding src of this 'row' on SrcView
					int markLine = Integer.parseInt(String.valueOf(jt.getValueAt(row, 2)));
					String file = String.valueOf(jt.getValueAt(row, 1));
//					ERAS.getSrcView().getFailJlUp().setText(file+"&"+markLine);
					ERAS.getSrcView().getFileViewUp().open(srcDir+"/"+file, markLine);
					
					int corresLine = Integer.parseInt(String.valueOf(node.getCorreNode().getLineNum()));
					String corresFile = node.getCorreNode().getFile();
					ERAS.getSrcView().getFileViewDown().open(corresSrcDir+"/"+corresFile, corresLine);
				}else 
					if(count == 2){
						int[] rows = jt.getSelectedRows();
						int markLine = Integer.parseInt(String.valueOf(jt.getValueAt(rows[1], 2)));
						String file = String.valueOf(jt.getValueAt(rows[1], 1));
						ERAS.getSrcView().getFailJlDown().setText(file+"&"+markLine);
						ERAS.getSrcView().getFailFileViewDown().open(srcDir+"/"+file, markLine);;
					}else{
						return;
					}
			}		
			else{
				int count = jt.getSelectedRowCount();
				if(count == 1){
					
					int row = jt.getSelectedRow();			
					String key = String.valueOf(jt.getValueAt(row, 0));
					Node node = nodesMap.get(key);
					//display statement details
					display(node);
					//display the corresponding src of this 'row' on SrcView
					int markLine = Integer.parseInt(String.valueOf(jt.getValueAt(row, 2)));
					String file = String.valueOf(jt.getValueAt(row, 1));
//					ERAS.getSrcView().getPassJlUp().setText(file+"&"+markLine);
					ERAS.getSrcView().getFileViewUp().open(srcDir+"/"+file, markLine);;
				}else 
					if(count == 2){
						int[] rows = jt.getSelectedRows();
						int markLine = Integer.parseInt(String.valueOf(jt.getValueAt(rows[1], 2)));
						String file = String.valueOf(jt.getValueAt(rows[1], 1));
						ERAS.getSrcView().getPassJlDown().setText(file+"&"+markLine);
						ERAS.getSrcView().getPassFileViewDown().open(srcDir+"/"+file, markLine);;
					}else{
						return;
					}
			}*/
		}
	}
	
	private void display(Node node){
		String details = "";
		details += 
				" trace number:\t"+node.getTaceNum()+
				"<br/> file:\t"+node.getFile()+
				"<br/> line number:\t"+node.getLineNum()+
				"<br/> category:\t"+node.getcategory();
		details +="<br/><br/> variable read and write in this statement:";
		for(NodeData nd : node.getNodeDatas()){
			details +="<br/> "+nd.getAction()+" address "+nd.getAddress()+" with "+nd.getValue();
		}
		details +="<br/><br/> dependences:(trace number#file#line number)";
		for(Node n : node.getDependences()){
			details += "<br/> <a href='"+n.getTaceNum()+"'>"+n.getTaceNum()+"#"+n.getFile()+"#"+n.getLineNum()+"</a>";
		}
		details +="<br/><br/> alignment Statement:";
		if(node.getCorreNode() != null)
			details += "<br/> "+node.getCorreNode().getTaceNum()+"#"+node.getCorreNode().getFile()+"#"+node.getCorreNode().getLineNum();
		jtp.setText(details);
		jtp.requestFocus();
		jtp.setSelectionStart(0);
		jtp.setSelectionEnd(0);
	}
	
	private void sliceInitialize(){
		
		try{
			FileReader fr = new FileReader(projDir+prefix+"Slice.out");
			BufferedReader br = new BufferedReader(fr);
			String msg = null;
			try{				
				while((msg = br.readLine()) != null){					
					String[] fragment = msg.split(":");
					Node node = new Node(fragment[0], fragment[1], fragment[2], fragment[3]);					
					nodesMap.put(fragment[0], node);
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	private void dependenceInitialize(){
		
		try{
			FileReader fr = new FileReader(projDir+prefix+"Depen.out");
			BufferedReader br = new BufferedReader(fr);
			String msg = null;
			try{				
				while((msg = br.readLine()) != null){					
					String[] fragment = msg.split("@");
					String[] current = fragment[0].split(":");
					String[] depen = fragment[1].split(":");
					if(nodesMap.containsKey(depen[0])){
						nodesMap.get(current[0]).addDependence(nodesMap.get(depen[0]));
					}
					else{
						Node node = new Node(depen[0], depen[1], depen[2], depen[3]);
						nodesMap.get(current[0]).addDependence(node);
					}
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	private void valueInitialize(){
		
		try{
			FileReader fr = new FileReader(projDir+prefix+"Value.out");
			BufferedReader br = new BufferedReader(fr);
			String msg = null;
			try{				
				while((msg = br.readLine()) != null){
					String[] fragment = msg.split("@");
					String current = fragment[0].split(":")[0];
					Node node = nodesMap.get(current);
					String[] value = fragment[1].split(":");
					String action = null;
					
					if(value[0] == "W")
						action = "write";
					else
						action = "read";
					
					NodeData data = new NodeData(action, value[1], value[2]);
					node.addNodeData(data);
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	private void corresInitialize(){
		
		try{
			FileReader fr = new FileReader(projDir+prefix+"Corres.out");
			BufferedReader br = new BufferedReader(fr);
			String msg = null;
			try{				
				while((msg = br.readLine()) != null){					
					String[] fragment = msg.split("@");
					String[] corres = fragment[1].split(":");					
					nodesMap.get(fragment[0].split(":")[0]).setCorresNode(
							new Node(corres[0], corres[1], corres[2], null));
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public void showSlice(){
		sliceInitialize();
		valueInitialize();
		dependenceInitialize();
		corresInitialize();
		model.setDataVector(null, columnNames);
		TreeSet<String> nodeKeySet = new TreeSet(nodesMap.keySet());
		for(Iterator<String> iterator = nodeKeySet.iterator();iterator.hasNext();){
			Node node = nodesMap.get(iterator.next());
			String[] row = new String[4];
			row[0] = node.getTaceNum();
			row[1] = node.getFile();
			row[2] = node.getLineNum();
			row[3] = node.getcategory();
			model.addRow(row);
		}
		makeFace(jt);
	}

	public JPanel getJp() {
		return jp;
	}

	public void setSrcDir(String srcDir) {
		this.srcDir = srcDir;
	}
	
	public void setCorresSrcDir(String corresSrcDir){
		this.corresSrcDir = corresSrcDir;
	}

	public void setProjDir(String projDir) {
		this.projDir = projDir;
	}
	
	private void makeFace(JTable table) {
        try {
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {

                public Component getTableCellRendererComponent(JTable table,
                        Object value, boolean isSelected, boolean hasFocus,
                        int row, int column) {                	
                    if (table.getValueAt(row, 3).equals("SRC")) {
                        setBackground(Color.YELLOW);
                    }else{
                    	setBackground(Color.WHITE);
                    }
                    return super.getTableCellRendererComponent(table, value,
                            isSelected, hasFocus, row, column);
                }
            };
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
	
	public class HyperActive implements HyperlinkListener{

		@Override
		public void hyperlinkUpdate(HyperlinkEvent e) {
			// TODO Auto-generated method stub
			if(e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)){
//				System.out.println(e.getDescription());
				for(int i=0; i < jt.getRowCount();i++){
					if(jt.getValueAt(i, 0).equals(e.getDescription())){
						jt.setRowSelectionInterval(i, i);
					}
				}
			}
		}
		
	}
}

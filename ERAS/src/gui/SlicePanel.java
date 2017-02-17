package gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.Project;

public class SlicePanel extends JPanel						
{	
	private SliceTable passSliceTable;
	private SliceTable failSliceTable;
	
	public SlicePanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		passSliceTable = new SliceTable("Pass ", "src");
		add(passSliceTable.getJp());
//		passSliceTable.showSlice();		

		failSliceTable = new SliceTable("Fail ", "dst");
		add(failSliceTable.getJp());
//		failSliceTable.showSlice();
	}
	
	public SlicePanel(Project project){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		passSliceTable = new SliceTable("Pass ", "src");
		add(passSliceTable.getJp());
		
		failSliceTable = new SliceTable("Fail ", "dst");
		add(failSliceTable.getJp());
		
		String pass_src = project.getPassSrc();
		String fail_src = project.getFailSrc();
		String projDir = ERAS.getProjectFilePath().getParent() + 
				"/" + project.toString() + "/";
		//display the corresponding slicetable of this @param 'node'
		show(pass_src, fail_src, projDir);
	}
	
	public void show(String pass_src, String fail_src, String projDir){
		passSliceTable.setSrcDir(pass_src);
		passSliceTable.setCorresSrcDir(fail_src);
		passSliceTable.setProjDir(projDir);
		passSliceTable.showSlice();
		
		failSliceTable.setSrcDir(fail_src);
		failSliceTable.setCorresSrcDir(pass_src);
		failSliceTable.setProjDir(projDir);
		failSliceTable.showSlice();
	}	
}

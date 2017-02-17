package gui;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Project;

public class NewDialog extends JDialog
{
	private JTextField project;
	private JTextField pass_src;
	private JTextField fail_src;
	private JTextField pass_exe;
	private JTextField fail_exe;
	private JTextField testcase;
	private JLabel statusLabel;
	
	private String proj;
	private String passSrc;
	private String failSrc;
	private String passExe;
	private String failExe;
	private String testCase;
	
	public NewDialog(JFrame parent)
	{
		super(parent);		
      JButton ok = new JButton("OK");
      ok.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
            	proj = project.getText();
            	statusLabel.setText("running...");
            	if(proj == null || passSrc == null || failSrc == null ||
            			passExe == null || failExe == null || testCase == null)
            		JOptionPane.showMessageDialog(null, "Nothing could be null!",
            				"ERROR", JOptionPane.ERROR_MESSAGE);
            	else{
            		Boolean isExisted = false;
            		for(Project p : ERAS.getProjectData()){
            			if(p.getProject().equals(proj)){
            				isExisted = true;
            				break;
            			}            			
            		}
            		//save configuration of this project to file 'proj'            		
            		if(isExisted)
            			JOptionPane.showMessageDialog(null, "Project Existed!",
            					"ERROR", JOptionPane.ERROR_MESSAGE);
            		else{
            			Project project = 
                				new Project(proj, passSrc, failSrc, passExe, failExe, testCase);
//            			ERAS.getProjectView().addProject(project);
        				String[] cmd = {
        						"python",
        						"/home/hjwang/Desktop/DualSlice/DualSlice.py",
        						ERAS.getErasSpace(),
        						proj};        				
        				execCMD(cmd);
/*        				String[] data2json = {
        						"node",
        						ERAS.getErasSpace() + "/ajaxGen.js",
        						ERAS.getErasSpace() + "/" + proj
        				};
        				execCMD(data2json);*/
        				statusLabel.setText("finished!");
            			ERAS.getProjectData().add(project);
            			ERAS.addTab(project);
            		}	            	
	            	//execute the analysis process and 
            		//save the result files to dir 'erasspace'+"/proj/"
            		File projectFile = ERAS.getProjectFilePath();
            		ERAS.saveProjectDataToFile(projectFile);
	            	
	            	setVisible(false);
            	}
            }
         });

      // add OK button to southern border

      JPanel panel = new JPanel();
      panel.setLayout(new GridLayout(8, 3));
      final FileDialog fileDialog = new FileDialog(this, "select");
      
      panel.add(new JLabel("PROJECT:"));
      project = new JTextField();
      panel.add(project);
      panel.add(new JLabel(""));
      
      panel.add(new JLabel("PASS_SRC:"));
      pass_src = new JTextField();
      panel.add(pass_src);
      JButton psBrowse = new JButton("Browse");
      psBrowse.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fileDialog.setVisible(true);
				passSrc = fileDialog.getDirectory();
				pass_src.setText(passSrc);
			}    	  			
    	});
      panel.add(psBrowse);
      
      panel.add(new JLabel("FAIL_SRC:"));
      fail_src = new JTextField();
      panel.add(fail_src);
      JButton fsBrowse = new JButton("Browse");
      fsBrowse.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fileDialog.setVisible(true);
				failSrc = fileDialog.getDirectory();
				fail_src.setText(failSrc);
			}    	  			
    	});
      panel.add(fsBrowse);
      
      panel.add(new JLabel("PASS_EXE:"));
      pass_exe = new JTextField();
      panel.add(pass_exe);
      JButton peBrowse = new JButton("Browse");
      peBrowse.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fileDialog.setVisible(true);
				passExe = fileDialog.getDirectory() + fileDialog.getFile();
				pass_exe.setText(passExe);
			}    	  			
    	});
      panel.add(peBrowse);
      
      panel.add(new JLabel("FAIL_EXE:"));
      fail_exe = new JTextField();
      panel.add(fail_exe);
      JButton feBrowse = new JButton("Browse");
      feBrowse.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fileDialog.setVisible(true);
				failExe = fileDialog.getDirectory() + fileDialog.getFile();
				fail_exe.setText(failExe);
			}    	  			
    	});
      panel.add(feBrowse);
      
      panel.add(new JLabel("TESTCASE:"));
      testcase = new JTextField();
      panel.add(testcase);
      JButton tcBrowse = new JButton("Browse");
      tcBrowse.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fileDialog.setVisible(true);
				testCase = fileDialog.getDirectory() + fileDialog.getFile();
				testcase.setText(testCase);
			}    	  			
    	});
      panel.add(tcBrowse);

      panel.add(new JLabel(""));
      panel.add(ok);
      panel.add(new JLabel(""));
      panel.add(new JLabel(""));
      
      statusLabel = new JLabel("execution status");
      panel.add(statusLabel);
      add(panel, BorderLayout.CENTER);
      panel.setBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Create Project"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));
      
      setLocationRelativeTo(ERAS.getErasFrame());

      pack();
	}
	
	public void execCMD(String[] cmd){
		try {
			Process p = Runtime.getRuntime().exec(cmd);  //调用Linux的相关命令
			
			InputStreamReader ir = new InputStreamReader(p.getInputStream());  
			LineNumberReader input = new LineNumberReader (ir);      //创建IO管道，准备输出命令执行后的显示内容
			
			String line;			
			while ((line = input.readLine ()) != null){     //按行打印输出内容
				System.out.println(line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

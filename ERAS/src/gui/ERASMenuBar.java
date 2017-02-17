package gui;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.event.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class ERASMenuBar extends JMenuBar
				implements MenuListener{
	private JMenu newMenu = new JMenu("New");
	private JMenu preferences = new JMenu("Preferenes");
	private JMenu report = new JMenu("report");
	private NewDialog newDialog;
	private PreDialog preDialog;
	
	public ERASMenuBar()
	{
		newMenu.addMenuListener(this);
		preferences.addMenuListener(this);
		report.addMenuListener(this);
		add(newMenu);
		add(preferences);
		add(report);
	}

	@Override
	public void menuSelected(MenuEvent e) {
		// TODO Auto-generated method stub
		JMenu m = (JMenu)(e.getSource());
		String command = m.getText();
		
		if(command.equals("New")){
			
          if (newDialog == null) // first time
        	newDialog = new NewDialog(ERAS.getErasFrame());          
          newDialog.setVisible(true); // pop up dialog

		}else if(command.equals("preferences")){
			if(preDialog == null)
				preDialog = new PreDialog(ERAS.getErasFrame());
			preDialog.setVisible(true);
		}else{
			String title = ERAS.getTabbedPane().getTitleAt(ERAS.getTabbedPane().getSelectedIndex());
			System.out.println(title);
			String[] data2json = {
					"node",
					ERAS.getErasSpace() + "/ajaxGen.js",
					ERAS.getErasSpace() + "/" +title
			};
			execCMD(data2json);
			String[] showGraph = {
					"firefox",
					"localhost:3000"
			};
			execCMD(showGraph);
		}
	}

	@Override
	public void menuDeselected(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub
		
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
	
//	private class NewAction implements MenuListener
//	{
//		public void menuSelected(MenuEvent event)
//		{
//            if (dialog == null) // first time
//            	dialog = new NewDialog();
//            dialog.setVisible(true); // pop up dialog
////			System.exit(0);
//		}
//
//		@Override
//		public void menuCanceled(MenuEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void menuDeselected(MenuEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//	}
}


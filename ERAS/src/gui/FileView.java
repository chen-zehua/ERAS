package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FileView {
	private String file;
	
	private LinePanel lp;
	private JTextArea jta;
	private JPanel jp;
	private JScrollPane jsp;
	
	private int lines;
	
	public FileView(){
		
		lp = new LinePanel();
		jta = new JTextArea();
		jta.setTabSize(4);
		jta.setFont(new Font("Arial", Font.PLAIN, 12));
		
		jp = new JPanel(new BorderLayout());
		jp.add(lp, BorderLayout.WEST);
		jp.add(jta, BorderLayout.CENTER);
		
		jsp = new JScrollPane(jp);
	}
	
	public void open(String file, int targ){
		String text = "";
		int l = 0;
		try{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String msg = null;
			try{				
				while((msg = br.readLine()) != null){
					l++;					
					text = text + " " + msg + "\n";
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		jta.setText(text);
		lines = jta.getLineCount();
		lp.setRows(lines);
		lp.setTarget(targ);
		lineSelection(targ);
		jp.repaint();
	}
	
	private void lineSelection(int targ)
	{
		try{
			int selectionStart = jta.getLineStartOffset(targ - 1);
			int selectionEnd = jta.getLineEndOffset(targ - 1);
			
			if(targ != lines)
				selectionEnd--;
			jta.requestFocus();
			jta.setSelectionStart(selectionStart);
			jta.setSelectionEnd(selectionEnd);
			jta.setSelectionColor(Color.YELLOW);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public JScrollPane getJSP(){
		return jsp;
	}
	
	public JTextArea getJTA(){
		return jta;
	}
}

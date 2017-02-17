package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PreDialog extends JDialog{
	private String workspace;
	
	public PreDialog(JFrame parent){
		super(parent, "Preferences");
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 3));
		
		JLabel label = new JLabel("  workspace:  ");
		panel.add(label);
		
		final JTextField textField = new JTextField();
		panel.add(textField);
//		textField.setText(ERAS.getProjectFilePath().getParent());
		
		JButton browse = new JButton("Browse");
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		browse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fileChooser.showDialog(new JLabel(), "choose");
				workspace = fileChooser.getSelectedFile().toString();
				textField.setText(workspace);				
			}
		});
		panel.add(browse);
		
		panel.add(new JLabel(""));
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
				
				workspace = textField.getText();
				ERAS.setErasSpace(workspace);
				File projectFile = new File(workspace, "projects.xml");
				if(!projectFile.exists()){
					try {
						projectFile.createNewFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				ERAS.setProjectFilePath(projectFile);
			}
		});
		panel.add(ok);
		
		add(panel, BorderLayout.CENTER);
		setLocationRelativeTo(ERAS.getErasFrame());
		pack();
	}
	
}

package gui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;


public class SrcView
{
	private JTabbedPane tabbedPane;	
	
	private JPanel passPanel;
	private JPanel failPanel;
	private JLabel passJlUp;
	private JLabel passJlDown;
	private JLabel failJlUp;
	private JLabel failJlDown;
	
	private FileView passFileViewUp;
	private FileView passFileViewDown;
	private FileView failFileViewUp;
	private FileView failFileViewDown;
	
	private JSplitPane splitPane;
	private FileView fileViewUp;
	private FileView fileViewDown;
	
	public SrcView()
	{
		fileViewUp = new FileView();
		fileViewDown = new FileView();
		fileViewUp.getJSP().setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Selected Statement Source"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		fileViewDown.getJSP().setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Alignment Statement Source"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				fileViewUp.getJSP(), 
				fileViewDown.getJSP());
		splitPane.setPreferredSize(new Dimension(900, 600));
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(0.5);
		
//		tabbedPane = new JTabbedPane();		
//		tabbedPane.setPreferredSize(new Dimension(800, 600));
//		initialize();
	}
	
	public JSplitPane getSplitPane() {
		return splitPane;
	}

	public void setSplitPane(JSplitPane splitPane) {
		this.splitPane = splitPane;
	}

	public FileView getFileViewUp() {
		return fileViewUp;
	}

	public void setFileViewUp(FileView fileViewUp) {
		this.fileViewUp = fileViewUp;
	}

	public FileView getFileViewDown() {
		return fileViewDown;
	}

	public void setFileViewDown(FileView fileViewDown) {
		this.fileViewDown = fileViewDown;
	}

	public JSplitPane getSrcView()
	{
		return splitPane;		
	}
	
	public void initialize()
	{
		//**************PASS**********************
		passJlUp = new JLabel("file&line#");
		passJlDown = new JLabel("file&line#");
		
		passFileViewUp = new FileView();
		passFileViewDown = new FileView();
		
		passPanel = new JPanel();
		passPanel.setLayout(new BoxLayout(passPanel, BoxLayout.Y_AXIS));
//		passPanel.add(passJlUp);
		passFileViewUp.getJSP().setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Selected Statement Source"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//		passPanel.add(passFileViewUp.getJSP());
//		passPanel.add(passJlDown);
		passFileViewDown.getJSP().setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Alignment Statement Source"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//		passPanel.add(passFileViewDown.getJSP());
		
        JSplitPane passSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
        		passFileViewUp.getJSP(),
        		passFileViewDown.getJSP());
        passSplitPane.setOneTouchExpandable(true);
        passSplitPane.setResizeWeight(0.5);
		
		tabbedPane.addTab("pass", passSplitPane);
		
		//*************FAIL*****************
		failJlUp = new JLabel("file&line#");
		failJlDown = new JLabel("file&line#");
		
		failFileViewUp = new FileView();
		failFileViewDown = new FileView();
		
		failPanel = new JPanel();
		failPanel.setLayout(new BoxLayout(failPanel, BoxLayout.Y_AXIS));
//		failPanel.add(failJlUp);
//		failPanel.add(failFileViewUp.getJSP());
		failFileViewUp.getJSP().setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Selected Statement Source"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//		failPanel.add(failJlDown);
//		failPanel.add(failFileViewDown.getJSP());
		failFileViewDown.getJSP().setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Alignment Statement Source"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		JSplitPane failSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
        		failFileViewUp.getJSP(),
        		failFileViewDown.getJSP());
        failSplitPane.setOneTouchExpandable(true);
        failSplitPane.setResizeWeight(0.5);
		
		tabbedPane.addTab("fail", failSplitPane);
	}

	public JLabel getPassJlUp() {
		return passJlUp;
	}

	public JLabel getPassJlDown() {
		return passJlDown;
	}

	public JLabel getFailJlUp() {
		return failJlUp;
	}

	public JLabel getFailJlDown() {
		return failJlDown;
	}
	
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public FileView getPassFileViewUp() {
		return passFileViewUp;
	}

	public FileView getPassFileViewDown() {
		return passFileViewDown;
	}

	public FileView getFailFileViewUp() {
		return failFileViewUp;
	}

	public FileView getFailFileViewDown() {
		return failFileViewDown;
	}
}

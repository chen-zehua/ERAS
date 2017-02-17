package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import model.Project;
import model.ProjectListWrapper;
import utils.ButtonTabComponent;
/**
 * @version 0.1 2016-09-14
 * @author zehua
 *
 */
public class ERAS 
{
	private static int WIDTH;
	private static int HEIGHT;
	
	public static int getWIDTH() {
		return WIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}

	private static String erasSpace;

	private static ProjectView projView;
	private static SlicePanel slicePanel;
	private static SrcView srcView;
	private static JTabbedPane tabbedPane;
	private static JFrame erasFrame;
	
	private static PreDialog preDialog;

	private static List<Project> projectData = new ArrayList<>();
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				createAndShowGUI();
			}
		});
	}
	
	private static void createAndShowGUI()
	{
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolKit.getScreenSize();
		WIDTH = screenSize.width;
		HEIGHT = screenSize.height;
//		System.out.println(getProjectFilePath());
		erasFrame = new JFrame("ERAS");
		erasFrame.setJMenuBar(new ERASMenuBar());
		
		tabbedPane = new JTabbedPane();
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		initTabbedPane();
		erasFrame.add(tabbedPane, BorderLayout.CENTER);
		
//		projView = new ProjectView();		
//		erasFrame.add(projView.getProjectView(), BorderLayout.WEST);
//		
//		slicePanel = new SlicePanel();
//		erasFrame.add(slicePanel, BorderLayout.CENTER);
		
		srcView = new SrcView();
		erasFrame.add(srcView.getSrcView(), BorderLayout.EAST);
		
		erasFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		erasFrame.pack();
		erasFrame.setVisible(true);		
	}
	
	private static void initTabbedPane(){
		File file = getProjectFilePath();
		if(file == null){
			if(preDialog == null)
				preDialog = new PreDialog(erasFrame);
			preDialog.setVisible(true);
		}else{
			loadProjectDataFromFile(file);
			for(Project p: projectData)
				addTab(p);
		}
	}
	
	public static void addTab(Project project){
		tabbedPane.add(project.toString(), new SlicePanel(project));
		initTabComponent(tabbedPane.getTabCount() - 1);
	}
	
	private static void initTabComponent(int i){
		tabbedPane.setTabComponentAt(i, 
				new ButtonTabComponent(tabbedPane));
	}
	
	public static ProjectView getProjectView()
	{
		return projView;
	}
	
	public static SlicePanel getSlicePanel()
	{
		return slicePanel;
	}
	
	public static SrcView getSrcView()
	{
		return srcView;
	}
	
	public static JFrame getErasFrame()
	{
		return erasFrame;
	}
	
	public static String getErasSpace()
	{
		return erasSpace;
	}
	
	public static void setErasSpace(String workspace)
	{
		erasSpace = workspace;
	}
	
	public static JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	/**
	 * Returns the person file preference, i.e. the file that was last opened.
	 * The preference is read from the OS specific registry. If no such
	 * preference can be found, null is returned.
	 * 
	 * @return
	 */
	public static File getProjectFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(ERAS.class);
	    String filePath = prefs.get("filePath", null);
	    
	    if (filePath != null) {
	    	File projectFile = new File(filePath);
	    	erasSpace = projectFile.getParent();    	
	        return projectFile;
	    } else {
	        return null;
	    }
	}

	/**
	 * Sets the file path of the currently loaded file. The path is persisted in
	 * the OS specific registry.
	 * 
	 * @param file the file or null to remove the path
	 */
	public static void setProjectFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(ERAS.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());

	        // Update the stage title.
	        erasFrame.setTitle("ERAS - " + file.getName());
	    } else {
	        prefs.remove("filePath");

	        // Update the stage title.
	        erasFrame.setTitle("ERAS");
	    }
	}
	
	/**
	 * Loads person data from the specified file. The current person data will
	 * be replaced.
	 * 
	 * @param file
	 */
	public static void loadProjectDataFromFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(ProjectListWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();

	        // Reading XML from the file and unmarshalling.
	        ProjectListWrapper wrapper = (ProjectListWrapper) um.unmarshal(file);

	        projectData.clear();
	        projectData.addAll(wrapper.getProjects());

	        // Save the file path to the registry.
	        setProjectFilePath(file);

	    } catch (Exception e) { // catches ANY exception
//	        new Dialog(erasFrame, "ERROR").setVisible(true);
    		JOptionPane.showMessageDialog(null, "Could not load data from file:\n" + file.getPath(),
    				"ERROR", JOptionPane.ERROR_MESSAGE);
	    }
	}

	/**
	 * Saves the current person data to the specified file.
	 * 
	 * @param file
	 */
	public static void saveProjectDataToFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(ProjectListWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Wrapping our person data.
	        ProjectListWrapper wrapper = new ProjectListWrapper();
	        wrapper.setProjects(projectData);

	        // Marshalling and saving XML to the file.
	        m.marshal(wrapper, file);

	        // Save the file path to the registry.
	        setProjectFilePath(file);
	    } catch (Exception e) { // catches ANY exception
//	    	new Dialog(erasFrame, "ERROR").setVisible(true);
	    	e.printStackTrace();
	    }
	}

	public static List<Project> getProjectData() {
		return projectData;
	}
}

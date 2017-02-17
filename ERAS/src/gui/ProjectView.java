package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Enumeration;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import model.Project;

public class ProjectView implements 
			TreeSelectionListener, MouseListener, ActionListener
{
	private JScrollPane projectView;
	
	private JTree projTree;
	private DefaultMutableTreeNode root;
	private DefaultTreeModel model;
	private PreDialog preDialog;
	
	private JPopupMenu popupMenu;
	
	public ProjectView()
	{
		root = new DefaultMutableTreeNode("Projects");
		model = new DefaultTreeModel(root);
		projTree = new JTree(model);
		projTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		projTree.addTreeSelectionListener(this);
		
		popupMenu = new JPopupMenu();
		JMenuItem delItem = new JMenuItem("delete");
		delItem.addActionListener(this);
		popupMenu.add(delItem);
		projTree.addMouseListener(this);
		
		projectView = new JScrollPane(projTree);		
		projectView.setPreferredSize(new Dimension(160, 600));
		
		File file = ERAS.getProjectFilePath();
		if(file == null){
			if(preDialog == null)
				preDialog = new PreDialog(ERAS.getErasFrame());
			preDialog.setVisible(true);
		}else{
			ERAS.loadProjectDataFromFile(file);
			for(Project p: ERAS.getProjectData())
				addProject(p);
		}
	}
	
	public JTree getProjTree()
	{
		return projTree;
	}
	
	public JScrollPane getProjectView()
	{
		return projectView;
	}
	
	public void addProject(Project project)
	{
		DefaultMutableTreeNode projectNode =
				new DefaultMutableTreeNode(project);
		model.insertNodeInto(projectNode, root, 0);
		
		TreeNode[] nodes = model.getPathToRoot(projectNode);
		TreePath path = new TreePath(nodes);
		projTree.scrollPathToVisible(path);
	}
	
	private void expandAll(JTree tree, TreePath parent, boolean expand)
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) parent.getLastPathComponent();
		if (node.getChildCount() > 0 )
		{
			for(Enumeration e = node.children(); e.hasMoreElements();)
			{
				DefaultMutableTreeNode n = (DefaultMutableTreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);
			}
		}
		if(expand)
			tree.expandPath(parent);
		else
			tree.collapsePath(parent);
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode node = 
				(DefaultMutableTreeNode) projTree.getLastSelectedPathComponent();
		
		if (node == null) return;
		if(node.isLeaf()){
			Project selectedProject = (Project)(node.getUserObject());
			String pass_src = selectedProject.getPassSrc();
			String fail_src = selectedProject.getFailSrc();
			String projDir = ERAS.getProjectFilePath().getParent() + 
					"/" + selectedProject.toString() + "/";
			//display the corresponding slicetable of this @param 'node'
			ERAS.getSlicePanel().show(pass_src, fail_src, projDir);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode delNode = 
				(DefaultMutableTreeNode) projTree.getLastSelectedPathComponent();
		
		//delete the corresponding project files of this @param 'node'
		
		if (delNode != null && delNode.getParent() != null){
			model.removeNodeFromParent(delNode);
			ERAS.getProjectData().remove(delNode.getUserObject());
    		File projectFile = ERAS.getProjectFilePath();
    		ERAS.saveProjectDataToFile(projectFile);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		TreePath path = projTree.getPathForLocation(e.getX(), e.getY());
		
		if(path == null)
			return;
		
		projTree.setSelectionPath(path);
		
		if(e.getButton() == 3)
			popupMenu.show(projTree, e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}	
}

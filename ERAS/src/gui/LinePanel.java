package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.*;

public class LinePanel extends JPanel 
{
	private int rows;
	private int target;
	
	public LinePanel()
	{
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//		setBackground(Color.lightGray);
		setPreferredSize(new Dimension(36, 36));
	}
	
	public void setRows(int lines)
	{
		rows = lines;
	}
	
	public void setTarget(int targ)
	{
		target = targ;
	}
	
	public void paintComponent(Graphics g)
	{
		removeAll();
		
//		setSize(36, rows*17);
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		for(int i = 1; i <= rows; i++)
		{
			JLabel l = new JLabel(i + "");
			l.setFont(new Font("Arial", Font.PLAIN, 12));
			l.setSize(36, 15);
			l.setLocation(0, 15*(i - 1));
			if (i == target){
				l.setForeground(Color.YELLOW);
			}
			add(l);

		}
	}
}

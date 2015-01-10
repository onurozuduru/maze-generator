/**
 * 
 */
package com.ozuduru.mazegenerator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author onur
 *
 */
public class MazeFrame extends JFrame {
	protected static final int EAST_PANEL_WIDTH = 250;
	protected static final int EAST_PANEL_HEIGHT = 150;
	protected static final int SLIDER_MIN = 0;
	protected static final int SLIDER_MAX = 90;
	protected static final int SLIDER_SPACING_VAL = 10;
	
	private Maze maze;
	private JPanel mainPanel, eastPanel;
	private JButton bGenerate, bDrawPath, bGenAndSim;
	private JSlider animSpeedSlider;
	private ButtonListener buttonListener;

	/**
	 * @throws HeadlessException
	 */
	public MazeFrame(Maze maze) throws HeadlessException {
		this("MAZE", maze);
	}
	
	/**
	 * @param title
	 * @throws HeadlessException
	 */
	public MazeFrame(String title, Maze maze) throws HeadlessException {
		super(title);
		this.maze = maze;
		this.mainPanel = new JPanel(new BorderLayout());
		this.mainPanel.add(this.maze, BorderLayout.WEST);
		this.eastPanel = new JPanel();
		this.eastPanel.setPreferredSize(new Dimension(MazeFrame.EAST_PANEL_WIDTH, MazeFrame.EAST_PANEL_HEIGHT));
		this.buttonListener = new ButtonListener();
		this.setButtons();
		this.setAnimSpeedSlider();
		
		this.mainPanel.add(eastPanel, BorderLayout.EAST);
		this.add(this.mainPanel);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		pack();
	}

	private void setAnimSpeedSlider() {
		this.animSpeedSlider = new JSlider(
				JSlider.HORIZONTAL,
				MazeFrame.SLIDER_MIN,
				MazeFrame.SLIDER_MAX,
				1800 / Maze.REFRESH_TIME );
		this.animSpeedSlider.addChangeListener(new SliderListener());
		
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer(MazeFrame.SLIDER_MIN), new JLabel("Stop"));
		labelTable.put(new Integer(MazeFrame.SLIDER_MAX), new JLabel("Fast"));
		
		this.animSpeedSlider.setLabelTable(labelTable);
		
		this.animSpeedSlider.setPreferredSize(new Dimension(MazeFrame.EAST_PANEL_WIDTH, 50));
		this.animSpeedSlider.setMajorTickSpacing(MazeFrame.SLIDER_SPACING_VAL);
		this.animSpeedSlider.setPaintTicks(true);
		this.animSpeedSlider.setPaintLabels(true);
		this.animSpeedSlider.setSnapToTicks(true);
		this.animSpeedSlider.setEnabled(false);
		this.eastPanel.add(this.animSpeedSlider);
	}

	private void setButtons() {
		this.bGenerate = new JButton("Generate");
		this.bGenAndSim = new JButton("Generate And Simmulate");
		this.bDrawPath = new JButton("Show Path");
		
		this.bGenerate.addActionListener(buttonListener);
		this.bGenAndSim.addActionListener(buttonListener);
		this.bDrawPath.addActionListener(buttonListener);
		
		eastPanel.add(bGenerate);
		eastPanel.add(bGenAndSim);
		eastPanel.add(bDrawPath);
	}

	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			
			if(source == bGenerate) {
				maze.generate();
				bGenerate.setEnabled(false);
				bGenAndSim.setEnabled(false);
			}
			else if(source == bGenAndSim) {
				maze.generateAndSimmulate().start();
				bGenerate.setEnabled(false);
				bGenAndSim.setEnabled(false);
				animSpeedSlider.setEnabled(true);
			}
			else if(source == bDrawPath) {
				maze.drawPath();
				if(maze.isGenerated)
					bDrawPath.setEnabled(false);
			}
		}//END OF Method actionPerformed
	}//END OF Class ButtonListener
	
	class SliderListener implements ChangeListener {		 
		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			Timer timer = maze.getSimulationTimer();
			
			if(!source.getValueIsAdjusting() && !maze.isGenerated) {
				int value = (int) source.getValue();
				
				if(value == 0 && timer.isRunning()) timer.stop();
				else {
					int delay = 1800 / value;
					timer.setDelay(delay);
					timer.setInitialDelay(delay);
					
					if(!timer.isRunning()) timer.start();
				}
			}	
		}//END OF Method stateChanged		
	}//END OF Class SliderListener
	
}//END OF Class MazeFrame

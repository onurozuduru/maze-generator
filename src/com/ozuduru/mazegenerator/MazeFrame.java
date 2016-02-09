/*********************************************************************************
*File: MazeFrame.java
*Author: Onur Ozuduru
*   e-mail: onur.ozuduru { at } gmail.com
*   github: github.com/onurozuduru
*   twitter: twitter.com/OnurOzuduru
*
*License: The MIT License (MIT)
*
*   Copyright (c) 2016 Onur Ozuduru
*   Permission is hereby granted, free of charge, to any person obtaining a copy
*   of this software and associated documentation files (the "Software"), to deal
*   in the Software without restriction, including without limitation the rights
*   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
*   copies of the Software, and to permit persons to whom the Software is
*   furnished to do so, subject to the following conditions:
*  
*   The above copyright notice and this permission notice shall be included in all
*   copies or substantial portions of the Software.
*  
*   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
*   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
*   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
*   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
*   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
*   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
*   SOFTWARE.
*********************************************************************************/

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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

public class MazeFrame extends JFrame {
	protected static final int EAST_PANEL_WIDTH = 250;
	protected static final int EAST_PANEL_HEIGHT = 150;
	protected static final int SLIDER_MIN = 0;
	protected static final int SLIDER_MAX = 90;
	protected static final int SLIDER_SPACING_VAL = 10;
	
	private Maze maze;
	private JPanel mainPanel, eastPanel;
	private JButton bGenerate, bDrawPath, bGenAndSim, bReset, bXmlGen;
	private JSlider animSpeedSlider;
	private ButtonListener buttonListener;

	public MazeFrame(Maze maze) throws HeadlessException {
		this("MAZE", maze);
	}
	
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
		this.bGenAndSim = new JButton("Generate And Simulate");
		this.bDrawPath = new JButton("Show Path");
		this.bReset = new JButton("Reset");
		this.bXmlGen = new JButton("Generate XML");
		
		this.bGenerate.addActionListener(buttonListener);
		this.bGenAndSim.addActionListener(buttonListener);
		this.bDrawPath.addActionListener(buttonListener);
		this.bReset.addActionListener(buttonListener);
		this.bXmlGen.addActionListener(buttonListener);
		
		eastPanel.add(bGenerate);
		eastPanel.add(bGenAndSim);
		eastPanel.add(bDrawPath);
		eastPanel.add(bReset);
		eastPanel.add(bXmlGen);
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
				maze.generateAndSimulate().start();
				bGenerate.setEnabled(false);
				bGenAndSim.setEnabled(false);
				animSpeedSlider.setEnabled(true);
			}
			else if(source == bDrawPath) {
				maze.drawPath();
				if(maze.isGenerated)
					bDrawPath.setEnabled(false);
			}
			else if(source == bXmlGen) {
				if(!maze.isGenerated)
					return;
				if(bDrawPath.isEnabled()) {
					maze.drawPath();
					bDrawPath.setEnabled(false);
				}
				try {
					XMLGenerator.generateXML(maze.getContainer(), maze.BOUNDS_X, maze.BOUNDS_Y);
				} catch (ParserConfigurationException | TransformerException
						| TransformerFactoryConfigurationError e1) {
					e1.printStackTrace();
				}
				bXmlGen.setEnabled(false);
			}
			else if(source == bReset) {
				Timer timer = maze.getSimulationTimer();
				
				if(timer != null && timer.isRunning())
					timer.stop();
				mainPanel.remove(maze);
				maze = new Maze(Maze.BOUNDS_X, Maze.BOUNDS_Y);
				mainPanel.add(maze, BorderLayout.WEST);
				mainPanel.updateUI();
				
				bGenerate.setEnabled(true);
				bGenAndSim.setEnabled(true);
				bDrawPath.setEnabled(true);
				bXmlGen.setEnabled(true);
				animSpeedSlider.setEnabled(false);
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
				
				if(value == 0) {
					if(!timer.isRunning())
						return;
					timer.stop();
				}
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

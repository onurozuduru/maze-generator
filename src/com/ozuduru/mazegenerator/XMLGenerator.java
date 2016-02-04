/*********************************************************************************
*File: XMLGenerator.java
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

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class XMLGenerator {
	public static void generateXML(ArrayList<Cell> container, int width, int height) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element maze = document.createElement("Maze");
		maze.setAttribute("Width", Integer.toString(width));
		maze.setAttribute("Height", Integer.toString(height));
		document.appendChild(maze);
		
		for(Cell c : container) {
			String x = Integer.toString(c.get_X());
			String y = Integer.toString(c.get_Y());
			Color color = c.getBackground();
			String tagName = "Cell";
			
			if(color == Color.BLACK)
				tagName = "Wall";
			else if(color == Color.YELLOW)
				tagName = "Path";
			else if(color == Color.RED)
				tagName = "Finish";
			else if(color == Color.GREEN)
				tagName = "Start";
			
			Element cell = document.createElement(tagName);
			cell.setAttribute("x", x);
			cell.setAttribute("y", y);
			maze.appendChild(cell);
		}
		
		File file = new File("Maze-" + Long.toString(System.currentTimeMillis()) + ".xml");
		TransformerFactory.newInstance().newTransformer().transform(new DOMSource(document), new StreamResult(file));
	}
}

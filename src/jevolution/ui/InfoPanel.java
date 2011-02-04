/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jevolution.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.*;
import javax.swing.border.Border;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kuhlmancer
 */
public class InfoPanel extends JPanel {
	public InfoPanel() {
		super(new MigLayout("fill, wrap, gap 0", "", "[||]20[|||||]20[|]"));

		addHeader("properties:");
		addInfo("red, green, blue");
		addInfo("width, height");

		addHeader("operations:");
		addInfo("+ addition");
		addInfo("-  subtraction");
		addInfo("*  multiplication");
		addInfo("/  division");
		addInfo("^  exponentiation");

		addMemo("You can use parenthesis too.");
		addMemo(Color.red,"Red text means it's a bad expression.");

		setBackground(Color.white);
		setBorder(BorderFactory.createLineBorder(Color.black, 2));
	}

	private void addHeader(String text) {
		JLabel label = new JLabel(text.toUpperCase());
		Font font = label.getFont();
		label.setFont(new Font(font.getName(), Font.BOLD, font.getSize() + 1));
		
		this.add(label);
	}

	private void addInfo(String text) {
		JLabel label = new JLabel(text);
		Font font = label.getFont();
		label.setFont(new Font(font.getName(), Font.PLAIN, font.getSize()));

		this.add(label);
	}

	private void addMemo(String text) {
		addMemo(Color.black, text);
	}
	
	private void addMemo(Color color, String text) {
		JLabel label = new JLabel(text);
		Font font = label.getFont();
		label.setFont(new Font(font.getName(), Font.BOLD, font.getSize()));
		label.setForeground(color);

		this.add(label);
	}
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jevolution.ui;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 * This panel contains all the labels and text fields that the user can change.
 *
 * The reference to the parent ApplicationPanel is to make communication easy.
 * I could spent tons of time worrying about event wiring or just get things working with this container class.
 *
 * Be aware that the CreatureExpressionTextFields here are given a reference to the ApplicationPanel.
 * This class should only be used as an organizational tool.  Refactor stuff if it grows beyond that.
 * 
 * @author kuhlmancer
 */
public class ExpressionPanel extends JPanel {
	ApplicationPanel parent;

	final static String initialStrengthFunction = "blue - red - green + 3*(width - height)";

	public ExpressionPanel(ApplicationPanel parent) {
		super(new MigLayout("fill, insets 0, nogrid, flowy", "[grow, left]", "[top]"));
		
		this.parent = parent;

		// strength function
		this.add(new JLabel("Strength function:"));
		this.add(new CreatureExpressionTextField(initialStrengthFunction, parent), "growx");
	}
}

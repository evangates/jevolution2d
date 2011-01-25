/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jevolution;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kuhlmancer
 */
public class ConfigurationPanel extends JPanel {
	private JTextField strengthFunction;
	private int width;


	public ConfigurationPanel(int width) {
		super(new MigLayout("fill,flowy", "[grow,left,"+width+"]"));
		this.width = width;

		this.add(new JLabel("Strength function:"));
		strengthFunction = new JTextField("not working yet");
		this.add(strengthFunction, "grow");
	}

}

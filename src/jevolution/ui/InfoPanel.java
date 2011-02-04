/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jevolution.ui;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kuhlmancer
 */
public class InfoPanel extends JPanel {
	public InfoPanel() {
		super(new MigLayout("fill", "fill"));

		this.add(new JLabel("Hello world."));
	}
}

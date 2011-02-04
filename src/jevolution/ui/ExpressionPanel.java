/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jevolution.ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import jevolution.ui.ApplicationPanel;
import jevolution.ui.CreatureExpressionTextField;
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
	private ApplicationPanel parent;

	final static String initialStrengthFunction = "blue - red - green + 3*(width - height)";
	final static String initialCostOfLivingFunction = "0.01 * width * height * acceleration";

	private JSlider speedSlider;

	public ExpressionPanel(ApplicationPanel app) {
		super(new MigLayout("fillx, wrap", "left", "[top|]50[|]50[|]"));
		
		this.parent = app;

		// strength function
		this.add(new JLabel("How much energy per second that creatures steal while they touch each other:"));
		this.add(new CreatureExpressionTextField(initialStrengthFunction, ExpressionId.STRENGTH, app), "growx");

		// cost of living
		this.add(new JLabel("How much energy per second a creature loses just for existing:"));
		this.add(new CreatureExpressionTextField(initialCostOfLivingFunction, ExpressionId.COST_OF_LIVING, app), "growx");

		// speed
		speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 100);
		speedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				parent.updateSpeedModifier(speedSlider.getValue() / 100d);
			}
		});
		this.add(new JLabel("Simulation speed:"));
		this.add(speedSlider);
	}
}
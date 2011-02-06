/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jevolution.ui;

import javax.swing.*;
import javax.swing.event.*;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kuhlmancer
 */
public class ValueSliderWithLabel extends JPanel {

	private final static int NUM_TICKS = 1000;

	private ApplicationPanel application;
	private JLabel label;
	private JSlider slider;
	private ValueId id;
	private String labelFormatStr;

	public ValueSliderWithLabel(String formatStr, ValueId valueId, int max, double initialValue, ApplicationPanel app) {
		super(new MigLayout("fill, left, wrap, insets 0", "[grow]", "[top|top, grow]"));

		id = valueId;
		slider = new JSlider(JSlider.HORIZONTAL, 0, max * NUM_TICKS, (int) (initialValue * NUM_TICKS));
		labelFormatStr = formatStr;
		label = new JLabel(String.format(formatStr, initialValue));

		this.add(label);
		this.add(slider, "growx");
		this.application = app;

		updateLabel();

		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				application.updateValue(getValue(), id);
				updateLabel();
			}
		});
	}

	public void updateLabel() {
		label.setText(String.format(labelFormatStr, getValue()));
	}

	public double getValue() {
		return (double) slider.getValue() / NUM_TICKS;
	}
}

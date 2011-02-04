/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jevolution.ui;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import jevolution.expressions.CreatureExpression;

/**
 * Custom text field for entering creature expressions.
 * Tries to parse the expression when losing focus when pressing enter.
 * 
 * @author kuhlmancer
 */
public class CreatureExpressionTextField extends JTextField {

	private CreatureExpression lastValidExpression;
	private String lastSubmittedText;
	private boolean isValidExpression;
	private final static Color validTextColor = Color.BLACK;
	private final static Color invalidTextColor = Color.RED;
	private ApplicationPanel panel;
	private ExpressionId id;

	public CreatureExpressionTextField(String expression, ExpressionId type, ApplicationPanel panel) {
		super(expression);
		this.panel = panel;
		this.id = type;

		addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				super.focusLost(e);

				updateExpression();
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					updateExpression();
				}
			}
		});

		updateExpression();
	}

	public CreatureExpression getExpression() {
		return lastValidExpression;
	}

	/**
	 * Parses the text in this text field.
	 *
	 * If the expression has changed, but evaluates to the same expression, then no update notification is sent.
	 * The text just gets reformatted.
	 */
	public void updateExpression() {
		String text = getText();

		// reparse
		CreatureExpression newExpression = new CreatureExpression(text);
		String newText = newExpression.toString();

		this.lastSubmittedText = newText;

		if (newExpression.isValid()) {
			System.out.println("updated expression");
			this.lastValidExpression = newExpression;
			isValidExpression = true;
			setText(newText);
			panel.updateExpression(newExpression, this.id);

		} else {
			isValidExpression = false;
		}

		updateColor();
	}

	private void updateColor() {
		if (isValidExpression) {
			setForeground(validTextColor);
		} else {
			setForeground(invalidTextColor);
		}
	}
}

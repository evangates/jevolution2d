/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jevolution.expressions;

/**
 *
 * @author kuhlmancer
 */
public class StringUtils {
	public static boolean isBlank(String str) {
		return str == null || str.trim().isEmpty();
	}
}

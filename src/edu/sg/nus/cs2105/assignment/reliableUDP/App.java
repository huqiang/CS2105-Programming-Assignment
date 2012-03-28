/**
 * 
 */
package edu.sg.nus.cs2105.assignment.reliableUDP;

import javax.swing.SwingUtilities;

import edu.sg.nus.cs2105.assignment.reliableUDP.view.DisplayUDP;

/**
 * @author huqiang
 *
 */
public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		DisplayUDP du = new DisplayUDP();
//		du.setVisible(true);
		SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		        new DisplayUDP().setVisible(true);
		      }
		    });
	}

}

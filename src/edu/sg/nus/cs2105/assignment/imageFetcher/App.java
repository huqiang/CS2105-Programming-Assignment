/**
 * 
 */
package edu.sg.nus.cs2105.assignment.imageFetcher;

import edu.sg.nus.cs2105.assignment.imageFetcher.view.ImageFetcherFrame;

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
	        java.awt.EventQueue.invokeLater(new Runnable() {

	            public void run() {
	                new ImageFetcherFrame().setVisible(true);
	            }
	        });
	}

}

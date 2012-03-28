/**
 * 
 */
package edu.sg.nus.cs2105.assignment.reliableUDP;

import java.io.IOException;

import edu.sg.nus.cs2105.assignment.reliableUDP.controller.UDPClient;

/**
 * @author huqiang
 *
 */
public class Client {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		if(args.length <3){
			System.out.println("please input ports.......");
		}
		else{
			UDPClient client = new UDPClient(args[0],args[1],args[2]);
			System.out.println(args[0]+ args[1]+ args[2]);
//			client.execute();
			client.sendFile(args[0], args[1], args[2]);
		}
	}

}

package edu.sg.nus.cs2105.assignment.reliableUDP.controller;
import java.util.*;
import java.net.*;
import java.io.*;

import javax.swing.SwingWorker;

import edu.sg.nus.cs2105.assignment.reliableUDP.event.Informable;
import edu.sg.nus.cs2105.assignment.reliableUDP.model.Parser;
import edu.sg.nus.cs2105.assignment.reliableUDP.view.DisplayUDP;


public class UDPServer extends SwingWorker<String, String>{
	private int port;
	String folder;
	boolean isRun = true;
	int SEG_SIZE = 60003;
	DatagramSocket skt;
	private static final int SYN = -1;
	DisplayUDP view;
	
	private Informable informable;
	
	
	public UDPServer(int p) throws SocketException{
		port = p;
		skt = new DatagramSocket(p);
	}
	
	public UDPServer(String add, int p, String path) {
		port = p;
		folder = path;
		System.out.println("The address is:"+add);
//		view.getServerResponse("The address is: "+add+"\n");
		try {
			skt = new DatagramSocket(port, InetAddress.getByName(add));
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			publish(e.getMessage());
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			publish(e.getMessage());
			e.printStackTrace();
		}
	}
	

	private void sendAck(int i, InetAddress inetAdd, int port) throws IOException {
		// TODO Auto-generated method stub
		byte[] outBuf = new byte[3];
		Parser parser = new Parser(i);
		System.arraycopy(parser.getByteValue(), 0, outBuf, 0, 3);
		
		DatagramPacket outPacket = new DatagramPacket(
				outBuf, outBuf.length, inetAdd,
				port);

			// finally, send the packet
			skt.send(outPacket);
	}
	
//	public void setView(DisplayUDP v){
//		this.view = v;
//	}

	public void setInformable(Informable ifm){
		this.informable = ifm;
	}
	@Override
	protected String doInBackground() throws Exception {
		// TODO Auto-generated method stub
		publish("Server is running:");
		publish("the local address is: "+skt.getLocalAddress());
		publish("the local socketAddress is: "+skt.getLocalSocketAddress());
//		System.out.println("the local port is: "+skt.getLocalPort());
//		System.out.println("the local inetAddress is: "+skt.getInetAddress());
		isRun = true;
		byte[] inBuf = new byte[SEG_SIZE];
		while(isRun){
			DatagramPacket inPkt = new DatagramPacket(inBuf, inBuf.length);
			skt.receive(inPkt);
			byte[] data = inPkt.getData();
//			This should be the first package, to init connection with server.
			if(data[0] != SYN){
				
			}
			else {
				int totalSegNum = getTotalNumber(data);
				String fileName = new String(data, 4, data.length-4);
				System.out.println("In server, the filename: "+fileName+" Total packs are: "+ totalSegNum);
				publish("The incomming file is: "+fileName+" total number of segment is: "+totalSegNum);
				FileOutputStream fou = new FileOutputStream(folder+"/"+fileName);
				sendAck(0, inPkt.getAddress(), inPkt.getPort());
				for(int i = 1; i <= totalSegNum;){
					System.out.println("In server, received package: "+i);
					
					publish(String.format("Receiving package: %d , progress = %.2f%%", i, i*0.1/(totalSegNum*0.1)* 100));
					inPkt = new DatagramPacket(inBuf, inBuf.length);
					skt.receive(inPkt);
					data = inPkt.getData();
//					System.out.println(data);
					if(new Parser( getThreeBytes(data) ).getIntValue() == i){
						fou.write(data, 3, data.length-3);
						sendAck(i, inPkt.getAddress(), inPkt.getPort());
						i++;
					}
				}
				publish( "Finished receiving!");
				fou.close();
			}
			
		}
		return "Terminated";
	}
	
	private int getTotalNumber(byte[] data) {
		// TODO Auto-generated method stub
		return ((int)data[1])*127*127+
				((int)data[2])*127+
				(int)data[3];
	}

	private byte[] getThreeBytes(byte[] data) {
		// TODO Auto-generated method stub
		byte[] result = new byte[3];
		System.arraycopy(data, 0, result, 0, 3);
		return result;
	}

	@Override
	  protected void process(List<String> chunks){
	    for(String message : chunks){
	      informable.messageChanged(message);
	      System.out.println(message);
	    }
	  }
	
}
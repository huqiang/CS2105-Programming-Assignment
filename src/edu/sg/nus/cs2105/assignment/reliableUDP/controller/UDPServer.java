package edu.sg.nus.cs2105.assignment.reliableUDP.controller;
import java.util.*;
import java.net.*;
import java.io.*;

import javax.swing.SwingWorker;

import edu.sg.nus.cs2105.assignment.reliableUDP.event.Informable;
import edu.sg.nus.cs2105.assignment.reliableUDP.view.DisplayUDP;


public class UDPServer extends SwingWorker<String, String>{
	private int port;
	String folder;
	boolean isRun = true;
	int SEG_SIZE = 60001;
	DatagramSocket skt;
	
	DisplayUDP view;
	
	private Informable informable;
	
	
	public UDPServer(int p) throws SocketException{
		port = p;
		skt = new DatagramSocket(p);
	}
	
	public UDPServer(String add, int p, String path) throws SocketException, UnknownHostException{
		port = p;
		folder = path;
		System.out.println("The address is:"+add);
//		view.getServerResponse("The address is: "+add+"\n");
		skt = new DatagramSocket(port, InetAddress.getByName(add));
	}
	

	private void sendAck(int i, InetAddress inetAdd, int port) throws IOException {
		// TODO Auto-generated method stub
		byte[] outBuf = new byte[1];
		outBuf[0] = (byte)i;
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
			if(data[0] != 0){
				
			}
			else {
				int totalSegNum = data[1];
				String fileName = new String(data, 2, data.length-2);
				System.out.println("In server, the filename: "+fileName+" Total packs are: "+ totalSegNum);
				publish("The incomming file is: "+fileName+" total number of segment is: "+totalSegNum);
				FileOutputStream fou = new FileOutputStream(folder+"/"+fileName);
				sendAck(0, inPkt.getAddress(), inPkt.getPort());
				for(int i = 1; i <= totalSegNum;){
					System.out.println("In server, received package: "+i);
					publish("Receiving package: "+i);
					inPkt = new DatagramPacket(inBuf, inBuf.length);
					skt.receive(inPkt);
					data = inPkt.getData();
					System.out.println(data);
					if(data[0] == i){
						fou.write(data, 1, data.length-1);
						sendAck(i, inPkt.getAddress(), inPkt.getPort());
						i++;
					}
				}
				fou.close();
				publish( "Finished receiving!");
			}
			
		}
		return "Terminated";
	}
	
	@Override
	  protected void process(List<String> chunks){
	    for(String message : chunks){
	      informable.messageChanged(message);
	    }
	  }
	
}
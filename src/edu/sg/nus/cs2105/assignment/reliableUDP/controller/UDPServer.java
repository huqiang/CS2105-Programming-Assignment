package edu.sg.nus.cs2105.assignment.reliableUDP.controller;
import java.util.*;
import java.net.*;
import java.io.*;


public class UDPServer {
	private int port;
	String folder;
	boolean isRun = true;
	int SEG_SIZE = 60001;
	DatagramSocket skt;
	public UDPServer(int p) throws SocketException{
		port = p;
		skt = new DatagramSocket(p);
	}
	
	public UDPServer(String add, int p, String path) throws SocketException, UnknownHostException{
		port = p;
		folder = path;
		System.out.println("The address is:"+add);
		skt = new DatagramSocket(p, InetAddress.getByName(add));
	}
	
	public void run() throws IOException{
//		System.out.println("the local address is: "+skt.getLocalAddress());
//		System.out.println("the local socketAddress is: "+skt.getLocalSocketAddress());
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
				FileOutputStream fou = new FileOutputStream(folder+"/"+fileName);
				sendAck(0, inPkt.getAddress(), inPkt.getPort());
				for(int i = 1; i <= totalSegNum;){
					System.out.println("In server, received package: "+i);
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
				return;
			}
			
		}
			
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
}
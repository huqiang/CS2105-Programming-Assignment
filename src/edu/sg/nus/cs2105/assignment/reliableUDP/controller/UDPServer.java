package edu.sg.nus.cs2105.assignment.reliableUDP.controller;
import java.util.*;
import java.net.*;
import java.io.*;


public class UDPServer {
	private int port;
	boolean isRun = true;
	
	public UDPServer(int p){
		port = p;
	}
	
	public void run() throws SocketException{
		isRun = true;
		DatagramSocket skt = new DatagramSocket(port);
		byte[] inBuf = new byte[60002];
		while(isRun){
			DatagramPacket inPkt = new DatagramPacket(inBuf, inBuf.length);
			
		}
	}
}

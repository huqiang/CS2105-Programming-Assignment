/**
 * 
 */
package edu.sg.nus.cs2105.assignment.reliableUDP.controller;

/**
 * @author huqiang
 *
 */
import java.util.*;
import java.net.*;
import java.io.*;

public class UDPClient {
	private DatagramSocket skt;
//	private InetAddress server;
//	int port;
	int currentSegNum;
	int totalSegNum;
	final int SEG_SIZE = 60000;
	
	public UDPClient() {
		try{
			skt = new DatagramSocket();
			
		} catch (SocketException e){
			System.out.println("Fail to create socket"+e.getMessage());
		}
	}
	
	public boolean sendFile(String filePath, String addr, String port) throws IOException{
		File theFile = new File(filePath);
		InetAddress ad = null;
		try {
			ad = InetAddress.getByName(addr);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		FileInputStream fin = null;
		if(fin==null)
		try{
			fin = new FileInputStream(theFile);
//			fileContent = new byte[(int)theFile.length()];
//			fin.read(fileContent);
		}
		catch(FileNotFoundException e){
			System.out.println("File not found"+e);
		}
		catch (IOException ioe){
			System.out.println("Exception while reading the file"+ioe);
		}
		
//		initiate connection with server, sending info about the file.
		initConnection(theFile, ad, Integer.parseInt(port));
		
		
//		Assume that numOfSegs will be smaller than 128, indicating the file size is smaller than 128*60000 < 7GB.
//		int numOfSegs = fileContent.length/SEG_SIZE;
		byte[] fileContent = new byte[SEG_SIZE];
		for(int i = 0; i < totalSegNum;){
			fin.read(fileContent, i*SEG_SIZE, SEG_SIZE);
			byte[] data = new byte[SEG_SIZE+2];
			data[0] = (byte)i+1;
			currentSegNum = i+1;
			System.arraycopy(fileContent, 0, data, 1, fileContent.length);
			DatagramPacket outPkt = new DatagramPacket(data, data.length,ad,Integer.parseInt(port));
			boolean result = sendPacket(outPkt);
			if(!result)
				result = sendPacket(outPkt);
			i++;
		}
		return false;
	}
	
	private void initConnection(File theFile, InetAddress ad, int port) {
		// TODO Auto-generated method stub
		byte[] data = new byte[SEG_SIZE+2];
		byte[] fileName = theFile.getName().getBytes();
		totalSegNum = (int) Math.ceil(theFile.length()/SEG_SIZE);
		data[0] = (byte)0;
		data[1] = (byte)totalSegNum;
		currentSegNum = 0;
		System.arraycopy(fileName, 0, data, 2, fileName.length);
		DatagramPacket outPkt = new DatagramPacket(data, data.length, ad, port);
		boolean result = sendPacket(outPkt);
		if(!result)
			result = sendPacket(outPkt);			
	}

	private boolean sendPacket(DatagramPacket pkt){
		byte inBuf[] = new byte[1];
		DatagramPacket inPkt = new DatagramPacket(inBuf, 1);
		try {
			skt.send(pkt);
			skt.setSoTimeout(500);
			skt.receive(inPkt);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		
		if (inBuf[0] != (byte)currentSegNum)
		return false;
		
		else return true;
	}
}

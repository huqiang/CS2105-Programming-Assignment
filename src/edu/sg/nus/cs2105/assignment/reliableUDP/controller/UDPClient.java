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
		System.out.println("[" + filePath + "]");
		File theFile = new File(filePath);
		InetAddress ad = null;
		try {
			ad = InetAddress.getByName(addr);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		FileInputStream fin = null;
//		if(fin==null)
		try{
			fin = new FileInputStream(theFile);
//			fileContent = new byte[(int)theFile.length()];
//			fin.read(fileContent);
		}
		catch(FileNotFoundException e){
			System.out.println("File not found"+e);
		}
		
//		initiate connection with server, sending info about the file.
		initConnection(theFile, ad, Integer.parseInt(port));
		
		
//		Assume that numOfSegs will be smaller than 128, indicating the file size is smaller than 128*60000 < 7GB.
//		int numOfSegs = fileContent.length/SEG_SIZE;
		byte[] fileContent = new byte[SEG_SIZE];
		for(int i = 0; i < totalSegNum;){
			System.out.println("This is segment: "+i);
			fin.read(fileContent, 0, SEG_SIZE);
			byte[] data = new byte[SEG_SIZE+1];
			data[0] = (byte)(i+1);
			currentSegNum = i+1;
			System.arraycopy(fileContent, 0, data, 1, fileContent.length);
//			System.out.println(data);
			DatagramPacket outPkt = new DatagramPacket(data, data.length,ad,Integer.parseInt(port));
			boolean result = sendPacket(outPkt);
			if(!result){
				result = sendPacket(outPkt);
				System.out.println("resending package+"+i);
				
			}
			i++;
		}
		fin.close();
		return true;
	}
	
	private void initConnection(File theFile, InetAddress ad, int port) {
		// TODO Auto-generated method stub
		System.out.println("inside initConnection");
		byte[] data = new byte[SEG_SIZE+2];
		byte[] fileName = theFile.getName().getBytes();
		totalSegNum = (int) Math.ceil(theFile.length()/SEG_SIZE)+1;
		System.out.println("The file name is"+fileName+"  file length = "+theFile.length()+" totalSegNum = "+totalSegNum);
		data[0] = (byte)0;
		data[1] = (byte)totalSegNum;
		currentSegNum = 0;
		System.arraycopy(fileName, 0, data, 2, fileName.length);
		DatagramPacket outPkt = new DatagramPacket(data, data.length, ad, port);
		boolean result = sendPacket(outPkt);
		if(!result){
			System.out.println("resending init request");
			result = sendPacket(outPkt);	
			
		}
		System.out.println("Connection established");
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
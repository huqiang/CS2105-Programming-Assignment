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

import javax.swing.SwingWorker;

import edu.sg.nus.cs2105.assignment.reliableUDP.event.Informable;
import edu.sg.nus.cs2105.assignment.reliableUDP.model.Parser;

public class UDPClient extends SwingWorker<String, String> {
	private DatagramSocket skt;
//	private InetAddress server;
//	int port;
	int currentSegNum;
	int totalSegNum;
	final int SEG_SIZE = 60000;
	private final String filePath;
	private final String addr;
	private final String port;
	private Parser parser;
	private static final int SYN = -1;
	
	private Informable informable;
	
	public UDPClient(String f, String a, String p) {
		this.filePath = f;
		this.addr = a;
		this.port = p;
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
		try{
			fin = new FileInputStream(theFile);

		}
		catch(FileNotFoundException e){
			publish("File not found"+e);
		}
		System.out.println(filePath+addr+port);
		
//		initiate connection with server, sending info about the file.
		initConnection(theFile, ad, Integer.parseInt(port));
		
		
//		Assume that numOfSegs will be smaller than 128, indicating the file size is smaller than 128*60000 < 7GB.
//		int numOfSegs = fileContent.length/SEG_SIZE;
		publish("Start sending file");
		byte[] fileContent = new byte[SEG_SIZE];
		for(int i = 0; i < totalSegNum;){
			System.out.println("This is segment: "+i);
			fin.read(fileContent, 0, SEG_SIZE);
			byte[] data = new byte[SEG_SIZE+3];
//			data[0] = (byte)(i+1);
			parser = new Parser(i+1);
			System.arraycopy(parser.getByteValue(), 0, data, 0, 3);
			currentSegNum = i+1;
			System.arraycopy(fileContent, 0, data, 3, fileContent.length);
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
		publish("done");
		return true;
	}
	
	private boolean initConnection(File theFile, InetAddress ad, int port) {
		// TODO Auto-generated method stub
		System.out.println("inside initConnection");
		byte[] data = new byte[SEG_SIZE+4];
		byte[] fileName = theFile.getName().getBytes();
		totalSegNum = (int) Math.ceil(theFile.length()/SEG_SIZE)+1;
		parser  = new Parser(totalSegNum);
		System.out.println("The total number is "+totalSegNum+" the Max number is: "+parser.maxValue);
		if(totalSegNum > parser.maxValue){
			publish("File is too big");
			return false;
		}
		publish("The file name is"+fileName+"  file length = "+theFile.length()+" totalSegNum = "+totalSegNum);
		data[0] = (byte)SYN; //To indicate this is a SYN pack
//		data[1] = (byte)totalSegNum;
		System.arraycopy(parser.getByteValue(),0,data,1,3);
		currentSegNum = 0;
		System.arraycopy(fileName, 0, data, 4, fileName.length);
		DatagramPacket outPkt = new DatagramPacket(data, data.length, ad, port);
		boolean result = sendPacket(outPkt);
		if(!result){
			publish("resending init request");
			result = sendPacket(outPkt);	
//			
		}
		publish("Connection established");
		return true;
	}

	private boolean sendPacket(DatagramPacket pkt){
		byte inBuf[] = new byte[3];
//		counter = 0;
		DatagramPacket inPkt = new DatagramPacket(inBuf, 3);
		try {
			skt.send(pkt);
			skt.setSoTimeout(500);
			skt.receive(inPkt);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
//			if (counter < 10){
//				publish("resenting :"+counter+" time!");
//				sendPacket(pkt);
//				
//			}
			publish("senting failed");
		}
		
//		parser = new Parser(inBuf);
		if (new Parser(inBuf).getIntValue() == currentSegNum)
			return true;
		
//		if (counter >= 10)
//				return false;
		
		return false;
	}
	
	public void setInformable(Informable ifm){
		this.informable = ifm;
	}
	
	
	@Override
	  protected void process(List<String> chunks){
	    for(String message : chunks){
	      informable.messageChanged(message);
	      System.out.println(message);
	    }
	  }

	@Override
	protected String doInBackground() throws Exception {
		// TODO Auto-generated method stub
//		sendFile(filePath, addr, port);
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
		try{
			fin = new FileInputStream(theFile);

		}
		catch(FileNotFoundException e){
			publish("File not found"+e);
		}
		System.out.println(filePath+addr+port);
		
//		initiate connection with server, sending info about the file.
		if(!initConnection(theFile, ad, Integer.parseInt(port))){
			publish("fail to establish connection, exit");
			return "Fail in connection!";
		}
		
		
//		Assume that numOfSegs will be smaller than 128, indicating the file size is smaller than 128*60000 < 7GB.
//		int numOfSegs = fileContent.length/SEG_SIZE;
		publish("Start sending file");
		byte[] fileContent = new byte[SEG_SIZE];
		for(int i = 0; i < totalSegNum;){
			System.out.println("This is segment: "+i);
			fin.read(fileContent, 0, SEG_SIZE);
			byte[] data = new byte[SEG_SIZE+3];
//			data[0] = (byte)(i+1);
			parser = new Parser(i+1);
			System.arraycopy(parser.getByteValue(), 0, data, 0, 3);
			currentSegNum = i+1;
			System.arraycopy(fileContent, 0, data, 3, fileContent.length);
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
		publish("done");
		return "done";
	}
}
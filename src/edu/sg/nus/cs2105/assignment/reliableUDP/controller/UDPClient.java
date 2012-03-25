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
	int seq;
	final int SEG_SIZE = 60000;
	
	public UDPClient() {
		try{
			skt = new DatagramSocket();
			
		} catch (SocketException e){
			System.out.println("Fail to create socket"+e.getMessage());
		}
	}
	
	public boolean sendFile(String filePath, String addr, String port){
		File theFile = new File(filePath);
		InetAddress ad = null;
		try {
			ad = InetAddress.getByName(addr);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] fileContent = null;
		try{
			FileInputStream fin = new FileInputStream(theFile);
			fileContent = new byte[(int)theFile.length()];
			fin.read(fileContent);
		}
		catch(FileNotFoundException e){
			System.out.println("File not found"+e);
		}
		catch (IOException ioe){
			System.out.println("Exception while reading the file"+ioe);
		}
//		Assume that numOfSegs will be smaller than 128, indicating the file size is smaller than 128*60000 < 7GB.
		int numOfSegs = fileContent.length/SEG_SIZE;
		for(int i = 0; i <= numOfSegs;){
			byte[] seg = Arrays.copyOfRange(fileContent, i*SEG_SIZE, i*SEG_SIZE-1);
			byte[] data = new byte[SEG_SIZE+2];
			data[0] = (byte)i;
			data[1] = (i == numOfSegs)?(byte)0:(byte)1;
			seq = i;
			System.arraycopy(seg, 0, data, 2, seg.length);
			DatagramPacket outPkt = new DatagramPacket(data, data.length,ad,Integer.parseInt(port));
//			if(!sendPacket(outPkt)){
//				sendPacket(outPkt);
//			}
			boolean result = sendPacket(outPkt);
			if(!result)
				result = sendPacket(outPkt);
			i++;
		}
		return false;
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
		
		if (inBuf[0] != (byte)seq)
		return false;
		
		else return true;
	}
}

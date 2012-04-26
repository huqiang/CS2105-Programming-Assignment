/**
 * 
 */
package edu.sg.nus.cs2105.assignment.reliableUDP.model;

/**
 * @author huqiang
 *
 */
public class Parser {
	private byte[] byteValue;
	private int intValue;
	public int maxValue;
	
	public Parser(byte[] b){
		this.byteValue = b;
		maxValue = 127*127*127;
	}
	
	public Parser(int n){
		this.intValue = n;
		maxValue = 127*127*127;
	}
	
	public int getIntValue(){
		if(byteValue.length > 3)
			return -1;
		return ((int)byteValue[0])*127*127+
				((int)byteValue[1])*127+
				(int)byteValue[2];
	}
	
	public byte[] getByteValue(){
		byte[] result = new byte[3];
		int n = intValue;
		for(int i = 0; i < 3; i++){
			result[2-i] = (byte)(n%127);
			n = n/127;
		}
		return result;
	}
}

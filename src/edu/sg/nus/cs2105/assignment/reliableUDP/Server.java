package edu.sg.nus.cs2105.assignment.reliableUDP;

import java.io.IOException;
import java.net.*;

import edu.sg.nus.cs2105.assignment.reliableUDP.controller.UDPServer;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Server {

	/**
	 * @param args
	 * @throws NumberFormatException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
//		 InetAddress   in  = InetAddress.getLocalHost();
//		 InetAddress[] all = InetAddress.getAllByName(in.getHostName());
//		 for (int i=0; i<all.length; i++) {
//		     System.out.println("  address = " + all[i]);
//		 }
//		 
		String[] ips = new String[10];
		int c = 0;
		for (
			    final Enumeration< NetworkInterface > interfaces =
			        NetworkInterface.getNetworkInterfaces( );
			    interfaces.hasMoreElements( );
			)
			{
			    final NetworkInterface cur = interfaces.nextElement( );

			    if ( cur.isLoopback( ) )
			    {
			        continue;
			    }

			    System.out.println( "interface " + cur.getName( ) );

			    for ( final InterfaceAddress addr : cur.getInterfaceAddresses( ) )
			    {
			        final InetAddress inet_addr = addr.getAddress( );

			        if ( !( inet_addr instanceof Inet4Address ) )
			        {
			            continue;
			        }
			        
			        System.out.println(c+": ");
			        System.out.println(
			            "  address: " + inet_addr.getHostAddress( ) +
			            "/" + addr.getNetworkPrefixLength( )
			        );

			        System.out.println(
			            "  broadcast address: " +
			                addr.getBroadcast( ).getHostAddress( )
			        );
			        ips[c++]=addr.getBroadcast( ).getHostAddress( );
			    }
			}
//		System.out.print("please input the interface number you want to use and a port number");
//		Scanner sc = new Scanner(System.in);
//		int addr = sc.nextInt();
//		int port = sc.nextInt();
//		
//		
//		System.out.println("Your local address is: "+ips[addr]);
//		InetAddress a = InetAddress.getByName(ips[addr]);
//		InetSocketAddress sa = new InetSocketAddress(a,port);
//		if(args.length == 0){
//			System.out.println("Please inpu portnumber");
//		}
//		else{
		UDPServer server;
		if (args.length == 1){
			server = new UDPServer(Integer.parseInt(args[0]));
		}
		else
			server = new UDPServer(args[0],Integer.parseInt(args[1]) ,"./");
//		}
		server.execute();
		
	
	}

}

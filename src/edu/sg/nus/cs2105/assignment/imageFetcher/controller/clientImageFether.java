package edu.sg.nus.cs2105.assignment.imageFetcher.controller;

import java.net.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class clientImageFether {

    public static void main(String[] args) {
        URL url;
        InputStream is = null;
        DataInputStream dis;
        String line;
        Character c;
        Vector<URL> urls = new Vector<URL>();
        try {
            url = new URL("http://www.askmen.com/celebs/women/singer/2_britney_spears.html");
            System.out.println(url.toString());
            String host = url.getHost();
            String file = url.getFile();
            Socket s = new Socket(host, 80);
            System.out.println("Socket opened to " + host + "\n");

            OutputStreamWriter outWriter = new OutputStreamWriter(s.getOutputStream());
            outWriter.write("GET " + file + " HTTP/1.1\r\nHost: " + host + "\n\n");
            outWriter.flush();
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
//            Boolean more = false;
            while (!(line = inFromServer.readLine()).equals("</html>")) {
//                System.out.println(line);
//                System.out.println("NEW STUFF");
                if (line.contains("img src") && (line.contains("jpg") || line.contains("jpeg") || line.contains("png") || line.contains("gif"))) {
                    System.out.println(line);
                    String temp = line.split("img", 2)[1].split("src", 2)[1].split("\"")[1];
                    if (temp.charAt(0) == '.' && temp.charAt(1) == '.') {
                        temp = host + temp.substring(2);
                    }
                    System.out.println(temp);
                    URL tempUrl = new URL(temp);
                    urls.add(tempUrl);
                }

            }
            System.out.println("Done adding urls.");
            inFromServer.close();
            outWriter.close();
        } catch (EOFException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
//        long startTime = System.currentTimeMillis();

        System.out.println("Connecting...\n");
        for(Object o : urls)
            System.out.println(o.toString());
//        for (Object o : urls) {
        for(int k = 0; k < 1; k++){
            Object o = urls.get(k);
            try {
                String name = null;
                URL u = (URL) o;

                String webHost = u.getHost();
                System.out.println(webHost);
                String file = u.getFile();
                System.out.println(file);
                //how to get the port number??
                Socket clientSocket = new Socket(webHost, 80);
                System.out.println("Socket opened to " + webHost + "\n");

                OutputStreamWriter outWriter = new OutputStreamWriter(clientSocket.getOutputStream());
                outWriter.write("GET " + file + " HTTP/1.1\r\nHost: " + webHost + "\n\n");
                outWriter.flush();
                System.out.println("Done writing!");
                InputStream in = clientSocket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                int position = u.toString().lastIndexOf('/');
                if (position > -1) {
                    name = u.toString().substring(position + 1);
                    System.out.println("Name: "+name);
                }
                FileOutputStream writer = new FileOutputStream("/Users/liyilin/Desktop/new/" + name);
                String httpFile = null;
                boolean more = true;
                String input;

                while (more) {
                    //read one line at a time
                    input = br.readLine();
                    System.out.println(input+'\n');
                    //print the line if any
                    if (input==null) {
                        System.out.println("EOF");
                        more = false;
                    } else {
                        httpFile += input+"\n";
//                        System.out.println("Inside wile(more) loop: "+httpFile);
                    }
                }
                System.out.println("Print out whole httpFile: "+httpFile);
                int httpLength = httpFile.length();
                byte[] httpByteFile = httpFile.getBytes();
                int httpByteFileLength = httpByteFile.length;
                int contentStart = 0;
                for (int i = 3; i < httpLength; i++) {
                    if (httpByteFile[i - 3] == 13 && httpByteFile[i - 2] == 10 && httpByteFile[i - 1] == 13 && httpByteFile[i] == 10) //                buffer[bytesRead]=(byte)reader.read();
                    {
                        System.out.println("Find a bitch!! at: " + i);
                        contentStart = i;
                    }
                }
                try {
                    writer.write(httpByteFile, contentStart, httpByteFileLength - contentStart);
                    //                while(buffer[bytesRead]!=-1){
                    //                	buffer[bytesRead++]=(byte)reader.read();
                    //                }
                    //                bytesRead = reader.read(buffer);
                    //                System.out.println(new String(buffer));
                    //                for (int i = 0; i < buffer.length; i++) {
                    //                    if (buffer[i] == 13 && buffer[i + 1] == 10 && buffer[i + 2] == 13 && buffer[i + 3] == 10) {
                    ////                        System.out.println("Find a bitch!! at: " + i);
                    //                        break;
                    //                    }
                    //                }
                    // System.out.println(new String(buffer));
                    //                String bufString = new String(buffer);
                    //                System.out.println(bufString);
                    //                System.out.println(bufString.split("\\n\\n").toString());
                    //                byte[] contents = bufString.split("\\n\\n")[1].getBytes();
                    //                  System.out.println(bytesRead);
                    //                  System.out.println(buffer[262]);
                    //                  System.out.println(buffer[263]);
                    //                  System.out.println(buffer[264]);
                    //                  System.out.println(buffer[265]);
                    //                writer.write(buffer);
                    //                byte[] tmp = new byte[153600];
                    //                System.arraycopy(buffer, 263, tmp, 0, buffer.length-263);
                } catch (IOException ex) {
                    Logger.getLogger(clientImageFether.class.getName()).log(Level.SEVERE, null, ex);
                }

                //                while(buffer[bytesRead]!=-1){
                //                	buffer[bytesRead++]=(byte)reader.read();
                //                }
                //                bytesRead = reader.read(buffer);
                //                System.out.println(new String(buffer));
                //                for (int i = 0; i < buffer.length; i++) {
                //                    if (buffer[i] == 13 && buffer[i + 1] == 10 && buffer[i + 2] == 13 && buffer[i + 3] == 10) {
                ////                        System.out.println("Find a bitch!! at: " + i);
                //                        break;
                //                    }
                //                }
                // System.out.println(new String(buffer));
                //                String bufString = new String(buffer);
                //                System.out.println(bufString);
                //                System.out.println(bufString.split("\\n\\n").toString());
                //                byte[] contents = bufString.split("\\n\\n")[1].getBytes();
                //                  System.out.println(bytesRead);
                //                  System.out.println(buffer[262]);
                //                  System.out.println(buffer[263]);
                //                  System.out.println(buffer[264]);
                //                  System.out.println(buffer[265]);
                //                writer.write(buffer);
                //                byte[] tmp = new byte[153600];
                //                System.arraycopy(buffer, 263, tmp, 0, buffer.length-263);

                System.out.println(httpByteFileLength + "\n\n" + contentStart + "\n");


//                writer.write(buffer, 263, bytesRead-263);
//                writer.write(tmp);

//                while ((bytesRead = reader.read(buffer)) > 0) {
//                    System.out.print(bytesRead);
//                    writer.write(buffer, 0, bytesRead);
//                    buffer = new byte[153600];
//                    totalBytesRead += bytesRead;
//                }
//                long endTime = System.currentTimeMillis();
//
//                System.out.println("Done. " + (new Integer(totalBytesRead).toString()) + " bytes read (" + (new Long(endTime - startTime).toString()) + " millseconds).\n");
                writer.flush();
                writer.close();
                br.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
}

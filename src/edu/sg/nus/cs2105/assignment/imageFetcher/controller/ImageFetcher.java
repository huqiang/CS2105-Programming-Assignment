/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sg.nus.cs2105.assignment.imageFetcher.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Vector;

/**
 *
 * @author liyilin $$ huqiang
 */
public class ImageFetcher {

    Vector<URL> urls;
    String clientPath;
    //constructor

    public ImageFetcher() {
        urls = new Vector<URL>();
    }
    // urls getter

    public Vector<URL> getUrls() {
        return urls;
    }
    // urls setter

    public void setUrls(Vector<URL> urls) {
        this.urls = urls;
    }

    //path to store files
    public String getClientPath() {
        return clientPath;
    }

    public void setClientPath(String clientPath) {
        this.clientPath = clientPath;
    }

    //load url, get the input stream of the url
    public InputStream loadURL(URL url) {
        try {
            String host = url.getHost();
            String file = url.getFile();
            Socket s = new Socket(host, 80);
            OutputStreamWriter outWriter = new OutputStreamWriter(s.getOutputStream());
            outWriter.write("GET " + file + " HTTP/1.1\r\nHost: " + host + "\n\n");
            outWriter.flush();

            return s.getInputStream();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }
    //get the image tag urls from the input url

    public Vector<URL> getImageUrls(InputStream is, String host) {
        Vector<URL> urls = new Vector<URL>();
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            while (!(line = inFromServer.readLine()).equals("</html>")) {
                if (line.toLowerCase().contains("img src") &&
                        (line.toLowerCase().contains("jpg") || line.toLowerCase().contains("jpeg") ||
                        line.toLowerCase().contains("png") || line.toLowerCase().contains("gif"))) {
                    System.out.println(line);
                    String temp = line.split("img", 2)[1].split("src", 2)[1].split("\"")[1];
                    
                    //only accept standard url formats
                    if (temp.contains("?")) {
                        temp = temp.substring(0, temp.lastIndexOf('?'));
                    }

                    URL tempUrl = new URL(temp);
                    urls.add(tempUrl);
                    System.out.println(host);
                    System.out.println(temp);

                }

            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return urls;
    }


    //use image source url to get the image
    public void getImages(URL url) {
        try {


            String webHost = url.getHost();
            System.out.println(webHost);
            String file = url.getFile();

            Socket clientSocket = new Socket(webHost, 80);
            System.out.println("Socket opened to " + webHost + "\n");

            OutputStreamWriter outWriter = new OutputStreamWriter(clientSocket.getOutputStream());
            outWriter.write("GET " + file + " HTTP/1.0\r\nHost: " + webHost + "\n\n");
            outWriter.flush();

            InputStream in = clientSocket.getInputStream();

            DataInputStream dis = new DataInputStream(in);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int ch;

            while ((ch = dis.read()) != -1) {
                baos.write(ch);
            }




            byte[] httpResponse = baos.toByteArray();
            int contentStart = 0;
            for (int i = 3; i < httpResponse.length; i++) {
                if (httpResponse[i - 2] == 10 && httpResponse[i - 1] == 13 && httpResponse[i] == 10) {
                    contentStart = i + 1;
                }
            }

            String name = "default";
            int position = url.toString().lastIndexOf('/');
            if (position > -1) {
                name = url.toString().substring(position + 1);
                System.out.println("Name: " + name);
            }

            FileOutputStream writer = new FileOutputStream(clientPath + name);

            writer.write(httpResponse, contentStart, httpResponse.length - contentStart);

            System.out.println("\nImage: " + name + " writed.\n" + "Url is " + url + "\n");

//            in.close();
//            dis.close();
//            baos.close();
//            writer.close(); 
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

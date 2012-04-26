				ReliableUDP_ReadMe
#############################
#Special Note:
 This zip file contains both part 1 and part 2.
 Part 1 is in the package edu.sg.nus.cs2105.assignment.reliableUDP;
 Part 2 is in the package edu.sg.nus.cs2105.assignment.imageFetcher;

 The runnable file: ReliableUDP.jar is for part 1
 The runnable file: ImageFetcher.jar is for part 2.


############################							
1. In this assignment we used stop and wait protocol to support the file transferring. 

2. We divided the file into segments, each segment containing 60000 bytes of data. 

3. We used a 3 bytes header, so the maximum file size supported for the transferring is 127^3*6000/(1024^3)=114.5GB

4. We built a simple GUI to support the software, which contain two panels, clientPanel and serverPanel. 

5. Firstly, server specify the IP address and Port number and open connection to create socket. Then client can input the IP address and Port number in relevant test fields and browse the file he can to send. Server side is able to see the progress in the text area. 

6. Develop Environment: Eclipse (Indigo Service Release 1), we include 3 parts in the submission. (1) Original Workspace (2)Runnable jar file (3)ReadMe.text

7. If client did not receive the acknowledgement within timeout period, it will resend the segment.


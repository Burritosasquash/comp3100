import java.io.*;
import java.net.*;
public class wk6client {
public static void main(String[] args) {
try{
Socket s=new Socket("127.0.0.1", 50000);
DataOutputStream dout=new DataOutputStream(s.getOutputStream());
DataInputStream dis=new DataInputStream(s.getInputStream());
BufferedReader in=new BufferedReader(new InputStreamReader(s.getInputStream()));

String username = System.getProperty("user.name");

dout.write(("HELO\n").getBytes());
dout.flush();
String str = (String)in.readLine();
System.out.println("server says: "+str);
dout.write(("AUTH luke\n").getBytes());
dout.flush();
str = (String)in.readLine();
System.out.println("server says "+str);

//getting jobs to work with

	dout.write(("REDY\n").getBytes());
	dout.flush();
	str = (String)in.readLine();
	System.out.println("server says "+str);
	String[] serverJobs = str.split(" ");
	int jobNum = Integer.parseInt(serverJobs[2]);
//	int[]jobNumArr = new int[10];
//	jobNumArr[i] = Integer.parseInt(serverJobs[2]);
//	System.out.println("Job no. "+jobNumArr[i]);

//get server info

dout.write(("GETS All\n").getBytes());
dout.flush();
str = (String)in.readLine();
System.out.println("server says "+str);
String[] serverDatalist = str.split(" ");
dout.write(("OK\n").getBytes());
dout.flush();
int serverNum = Integer.parseInt(serverDatalist[1]);
int[] serverCoreArr = new int[serverNum];
int[] serverIdArr = new int[serverNum];
String[] serverTypeArr = new String[serverNum];

System.out.println(serverNum);

//finding all servers

for(int i=0; i< serverNum; i++){

	str = (String)in.readLine();
	System.out.println("server says "+str);
	String[] serverList = str.split(" ");

	serverCoreArr[i] = Integer.parseInt(serverList[4]);
	serverTypeArr[i] = serverList[0];
	serverIdArr[i] = Integer.parseInt(serverList[1]);


}

dout.write(("OK\n").getBytes());
dout.flush();
str = (String)in.readLine();
System.out.println("server says "+str);

System.out.println("highest core count: "+serverCoreArr[serverNum-1]);
System.out.println("server type with highest cores: "+serverTypeArr[serverNum-1]);
System.out.println("serverId: "+serverIdArr[serverNum-1]);

//schd must schedule jobs below here but must find largest server first


dout.write(("SCHD "+jobNum+" "+serverTypeArr[4]+" "+serverIdArr[4]+"\n").getBytes());
dout.flush();
str = (String)in.readLine();
System.out.println("server says "+str);

for(int i = 0; i<5; i++){

//getting new jobs in the loop and scheduling immediately
        
	dout.write(("REDY\n").getBytes());        
	dout.flush();
	str = (String)in.readLine();        
	System.out.println("server says "+str);
	serverJobs = str.split(" ");
	jobNum = Integer.parseInt(serverJobs[2]);

	dout.write(("SCHD "+jobNum+" "+serverTypeArr[serverNum-1]+" "+serverIdArr[serverNum-1]+"\n").getBytes());
	dout.flush();
	str = (String)in.readLine();
	System.out.println("server says "+str);


}

dout.write(("QUIT\n").getBytes());
dout.flush();
System.out.println("server connection ended");
dout.close();
s.close();
}catch(Exception e){System.out.println(e);}
}
}

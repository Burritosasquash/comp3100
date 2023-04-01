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


//get server info

dout.write(("GETS All\n").getBytes());
dout.flush();
str = (String)in.readLine();
System.out.println("server says "+str);
String[] serverDatalist = str.split(" ");
dout.write(("OK\n").getBytes());
dout.flush();

int serverCount = Integer.parseInt(serverDatalist[1]);
int[] serverNumArr = new int[serverCount];
int[] serverCoreArr = new int[serverCount];
int[] serverIdArr = new int[serverCount];
String[] serverTypeArr = new String[serverCount];

System.out.println(serverCount);

//finding all servers

for(int i=0; i< serverCount; i++){

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

System.out.println("highest core count: "+serverCoreArr[serverCount-1]);
System.out.println("server type with highest cores: "+serverTypeArr[serverCount-1]);
System.out.println("serverId: "+serverIdArr[serverCount-1]);


//schd must queue jobs below here but must find largest server first
int jobCount = 0;
while(!str.contains("CHKQ")){

//getting new jobs in the loop and queuing immediately
        
	dout.write(("REDY\n").getBytes());        
	dout.flush();
	str = (String)in.readLine();        
	System.out.println("server says "+str);

	if(str.contains("CHKQ")){
		break;
	}
	jobNum = Integer.parseInt(serverJobs[2]);
	jobCount++;
	System.out.println(jobCount);
	dout.write(("ENQJ GQ\n").getBytes());
        dout.flush();
        str = (String)in.readLine();
        System.out.println("server says "+str);
}

//list the jobs in global queue

dout.write(("LSTQ GQ *\n").getBytes());
dout.flush();
str = (String)in.readLine();
System.out.println("server says "+str);
String[] serverQueueList = str.split(" ");
dout.write(("OK\n").getBytes());
dout.flush();
str = (String)in.readLine();
System.out.println("server says "+str);
int serverQueue = Integer.parseInt(serverQueueList[1]);
dout.write(("OK\n").getBytes());
dout.flush();
for(int i = 0; i < serverQueue; i++){
	str = (String)in.readLine();
	System.out.println("server says "+str);
}


//int queueCount = Integer.parseInt(serverDatalist[1]);


int j = 0;
System.out.println(jobCount);
for(int i = 0; i  < jobCount; i++){
	System.out.println(i);
	System.out.println(j);
	System.out.println(serverTypeArr[serverCount-1]);
	dout.write(("DEQJ GQ 0\n").getBytes());
        dout.flush();
        str = (String)in.readLine();
        System.out.println("server says "+str);
        serverJobs = str.split(" ");

        if(j == serverIdArr[6]){
                j = 0;
        }


	dout.write(("SCHD "+i+" "+serverTypeArr[serverCount-1]+" "+serverIdArr[j]+"\n").getBytes());
	dout.flush();
	str = (String)in.readLine();
	System.out.println("server says "+str);
	


}
for(int i = 0; i < jobCount + 1; i++){
dout.write(("REDY\n").getBytes());
dout.flush();
str = (String)in.readLine();
System.out.println("server says "+str);
}

dout.write(("QUIT\n").getBytes());
dout.flush();
str = (String)in.readLine();
System.out.println("server says "+str);
System.out.println("server connection ended");
dout.close();
s.close();
}catch(Exception e){System.out.println(e);}
}
}

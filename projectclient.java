import java.io.*;
import java.net.*;
public class projectclient {
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
int largestServerId = 0;
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
	largestServerId = Integer.parseInt(serverList[1]);

}

dout.write(("OK\n").getBytes());
dout.flush();

//finding largest server type || first server to have largest amount of cores
int largestCoreServer = 0;
String largestServerType = null;
for(int i = 0; i < serverCount; i++){
	if(largestCoreServer < serverCoreArr[i]){
		largestCoreServer = serverCoreArr[i];
		largestServerType = serverTypeArr[i];
	}
}


System.out.println("highest core count: "+largestCoreServer);
System.out.println("server type with highest cores: "+largestServerType);
System.out.println("serverId: "+serverIdArr[serverCount-1]);

//schedule first job now knowing the largest server sizes

int j = 0;

        jobNum = Integer.parseInt(serverJobs[2]);
        dout.write(("SCHD "+jobNum+" "+largestServerType+" "+0+"\n").getBytes());
        dout.flush();
	j++;


//begin loop for redying, getting jobs and job information

int jobCount = 0;

while(!str.contains("NONE")){
	if(str.contains("NONE")){
		break;
	}

        str = (String)in.readLine();
        System.out.println("server says "+str);


//getting new jobs in the loop and queuing immediately
        
	serverJobs = str.split(" ");
	if(str.contains("JCPL")){
	        dout.write(("REDY\n").getBytes());
                dout.flush();

	}
	if(str.contains("JOBN")){
		if(j == largestServerId+1){
			j = 0;
		}
	        jobNum = Integer.parseInt(serverJobs[2]);
		dout.write(("SCHD "+jobNum+" "+largestServerType+" "+j+"\n").getBytes());
        	dout.flush();
		j++;
	}
	if(str.contains("OK")){
        dout.write(("REDY\n").getBytes());
        dout.flush();
	}
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

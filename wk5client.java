import java.io.*;
import java.net.*;
public class Myclient {
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
dout.write(("REDY\n").getBytes());
dout.flush();
str = (String)in.readLine();
System.out.println("server says "+str);
String[] serverJobs = str.split(" ");
int jobNum = Integer.parseInt(serverJobs[2]);
System.out.println("Job no. "+jobNum);

//get server info

dout.write(("GETS All\n").getBytes());
dout.flush();
str = (String)in.readLine();
System.out.println("server says "+str);
String[] serverDatalist = str.split(" ");
dout.write(("OK\n").getBytes());
dout.flush();
int serverNum = Integer.parseInt(serverDatalist[1]);
int serverCore = 0;
int serverId = 0;
String serverType = null;

System.out.println(serverNum);

for(int i=0; i< serverNum; i++){

	str = (String)in.readLine();
	System.out.println("server says "+str);
	String[] serverList = str.split(" ");
	if(Integer.parseInt(serverList[4]) > serverCore){
		serverCore = Integer.parseInt(serverList[4]);
		serverType = serverList[0];
		serverId = Integer.parseInt(serverList[1]);
	}

}

dout.write(("OK\n").getBytes());
dout.flush();
str = (String)in.readLine();
System.out.println("server says "+str);

System.out.println("highest core count: "+serverCore);
System.out.println("server type with highest cores: "+serverType);
System.out.println("serverId: "+serverId);

//schd must schedule jobs below here but must find largest server first

dout.write(("SCHD "+jobNum+" "+serverType+" "+serverId+"\n").getBytes());
dout.flush();
str = (String)in.readLine();
System.out.println("server says "+str);

dout.write(("QUIT\n").getBytes());
dout.flush();
System.out.println("server connection ended");
dout.close();
s.close();
}catch(Exception e){System.out.println(e);}
}
}

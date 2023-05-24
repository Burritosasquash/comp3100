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
	int jobCore = Integer.parseInt(serverJobs[4]);
	int jobMem = Integer.parseInt(serverJobs[5]);
	int jobDisk = Integer.parseInt(serverJobs[6]);

System.out.println(jobNum+" "+jobCore+" "+jobMem+" "+jobDisk);

//get server info via capable

dout.write(("GETS Capable "+jobCore+" "+jobMem+" "+jobDisk+"\n").getBytes());
dout.flush();
str = (String)in.readLine();
System.out.println("server says "+str);
String[] serverDatalist = str.split(" ");
dout.write(("OK\n").getBytes());
dout.flush();
dout.write(("OK\n").getBytes());
dout.flush();

int serverCount = Integer.parseInt(serverDatalist[1]);
int[] serverNumArr = new int[serverCount];
int[] serverCoreArr = new int[serverCount];
int largestServerId = 0;
int[] serverIdArr = new int[serverCount];
String[] serverTypeArr = new String[serverCount];

System.out.println(serverCount);

//finding all servers inputting server data into variables

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

//schedule first job to first server with resources

        jobNum = Integer.parseInt(serverJobs[2]);
        dout.write(("SCHD "+jobNum+" "+serverTypeArr[0]+" "+0+"\n").getBytes());
        dout.flush();
/*        
dout.write(("OK\n").getBytes());
dout.flush();
str = (String)in.readLine();
System.out.println("server says "+str);
serverDatalist = str.split(" ");
        
System.out.println("working");
	dout.write(("REDY\n").getBytes());
	dout.flush();
	str = (String)in.readLine();
	System.out.println("server says "+str);
	String[] serverJobs = str.split(" ");

System.out.println("working");

jobNum = Integer.parseInt(serverJobs[1]);
jobCore = Integer.parseInt(serverJobs[4]);
jobMem = Integer.parseInt(serverJobs[5]);
jobDisk = Integer.parseInt(serverJobs[6]);

System.out.println("working");

dout.write(("GETS Capable "+jobCore+" "+jobMem+" "+jobDisk+"\n").getBytes());
dout.flush();
str = (String)in.readLine();
System.out.println("server says "+str);
serverDatalist = str.split(" ");
dout.write(("OK\n").getBytes());
dout.flush();

        jobNum = Integer.parseInt(serverJobs[2]);
        dout.write(("SCHD "+jobNum+" "+serverTypeArr[0]+" "+0+"\n").getBytes());
        dout.flush();
*/

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

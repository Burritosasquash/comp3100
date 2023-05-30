import java.io.*;
import java.net.*;
public class S2Client {
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
	//System.out.println("server says "+str);
	String[] serverJobs = str.split(" ");
	int jobNum = Integer.parseInt(serverJobs[2]);
	int jobCore = Integer.parseInt(serverJobs[4]);
	int jobMem = Integer.parseInt(serverJobs[5]);
	int jobDisk = Integer.parseInt(serverJobs[6]);

//System.out.println("Job"+jobNum+" "+jobCore+" "+jobMem+" "+jobDisk);

//get server info via capable

dout.write(("GETS Avail "+jobCore+" "+jobMem+" "+jobDisk+"\n").getBytes());
dout.flush();
str = (String)in.readLine();
//System.out.println("server says "+str);
String[] serverDatalist = str.split(" ");
dout.write(("OK\n").getBytes());
dout.flush();


int serverCount = Integer.parseInt(serverDatalist[1]);
int[] serverCoreArr = new int[serverCount];
int largestServerId = 0;
int[] serverIdArr = new int[serverCount];
String[] serverTypeArr = new String[serverCount];

//System.out.println(serverCount);

//finding all servers inputting server data into variables

for(int i=0; i< serverCount; i++){

	str = (String)in.readLine();
	//System.out.println("server says "+str);
	String[] serverList = str.split(" ");

	serverCoreArr[i] = Integer.parseInt(serverList[4]);
	serverTypeArr[i] = serverList[0];
	serverIdArr[i] = Integer.parseInt(serverList[1]);
	largestServerId = Integer.parseInt(serverList[1]);

}

dout.write(("OK\n").getBytes());
dout.flush();

//schedule first job to first server with resources

        dout.write(("SCHD "+jobNum+" "+serverTypeArr[0]+" "+serverIdArr[0]+"\n").getBytes());
        dout.flush();
        str = (String)in.readLine();
        //System.out.println("server says "+str);
        
        
while(!str.contains("NONE")){
        str = (String)in.readLine();
        //System.out.println("server says "+str);
	serverJobs = str.split(" ");
	if(str.contains("JCPL")){
		dout.write(("REDY\n").getBytes());
        	dout.flush();
	}
	if(str.contains("JOBN")){
		
		jobNum = Integer.parseInt(serverJobs[2]);
		jobCore = Integer.parseInt(serverJobs[4]);
		jobMem = Integer.parseInt(serverJobs[5]);
		jobDisk = Integer.parseInt(serverJobs[6]);
		
		//System.out.println("Job"+jobNum+" "+jobCore+" "+jobMem+" "+jobDisk);
		
		dout.write(("GETS Avail "+jobCore+" "+jobMem+" "+jobDisk+"\n").getBytes());
		dout.flush();
		str = (String)in.readLine();
		//System.out.println("server says "+str);
		serverDatalist = str.split(" ");
		serverCount = Integer.parseInt(serverDatalist[1]);
		//System.out.println(serverCount);
		
		
		
		if(serverCount == 0){
			
			dout.write(("OK\n").getBytes());
			dout.flush();
			str = (String)in.readLine();
			//System.out.println("server says "+str);
			//System.out.println("gets Capable encountered");
			//System.out.println("server says "+str);
			dout.write(("GETS Capable "+jobCore+" "+jobMem+" "+jobDisk+"\n").getBytes());
			dout.flush();
			//System.out.println("gets Capable activated");
			str = (String)in.readLine();
			//System.out.println("server says "+str);
			serverDatalist = str.split(" ");
			serverCount = Integer.parseInt(serverDatalist[1]);
			//System.out.println(serverCount);
		}
		
		dout.write(("OK\n").getBytes());
		dout.flush();

		int[] AvailCoreArr = new int[serverCount];
		int[] AvailIdArr = new int[serverCount];
		String[] AvailTypeArr = new String[serverCount];
		
		for(int i=0; i < serverCount; i++){

			str = (String)in.readLine();
			//System.out.println("server says "+str);
			String [] serverList = str.split(" ");
			AvailCoreArr[i] = Integer.parseInt(serverList[4]);
			AvailTypeArr[i] = serverList[0];
			AvailIdArr[i] = Integer.parseInt(serverList[1]);
			largestServerId = Integer.parseInt(serverList[1]);
			
			
		}
		dout.write(("OK\n").getBytes());
		dout.flush();
		
		
		dout.write(("SCHD "+jobNum+" "+AvailTypeArr[0]+" "+AvailIdArr[0]+"\n").getBytes());
        	dout.flush();
        	str = (String)in.readLine();
        	//System.out.println("server says "+str);
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

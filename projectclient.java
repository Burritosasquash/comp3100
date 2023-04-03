import java.io.*;
import java.net.*;
public class projectclient {
public static void main(String[] args) {
try{

// the below section starts the connection between the server and the client
// the below section also creates our readers and output streams for communication

Socket s=new Socket("127.0.0.1", 50000);
DataOutputStream dout=new DataOutputStream(s.getOutputStream());
BufferedReader in=new BufferedReader(new InputStreamReader(s.getInputStream()));

String username = System.getProperty("user.name");

//the below is made so that initiation between client and server can be made

dout.write(("HELO\n").getBytes());
dout.flush();
String str = (String)in.readLine();
//System.out.println("server says: "+str);
dout.write(("AUTH luke\n").getBytes());
dout.flush();
str = (String)in.readLine();
//System.out.println("server says "+str);

//below is made so that the first job can be recieved from the server

dout.write(("REDY\n").getBytes());
dout.flush();
str = (String)in.readLine();
//System.out.println("server says "+str);
String[] serverJobs = str.split(" ");
int jobNum = Integer.parseInt(serverJobs[2]);

//below indicates how we get our server information 
//once we have this it does not have to be done again

dout.write(("GETS All\n").getBytes());
dout.flush();
str = (String)in.readLine();
//System.out.println("server says "+str);
String[] serverDatalist = str.split(" ");
dout.write(("OK\n").getBytes());
dout.flush();

int serverCount = Integer.parseInt(serverDatalist[1]);
int[] serverCoreArr = new int[serverCount];
int largestServerId = 0;
String[] serverTypeArr = new String[serverCount];

//System.out.println(serverCount);

//putting all server information needed into arrays and values so that they can be referenced later

for(int i=0; i< serverCount; i++){

	str = (String)in.readLine();
	//System.out.println("server says "+str);
	String[] serverList = str.split(" ");
	serverCoreArr[i] = Integer.parseInt(serverList[4]);
	serverTypeArr[i] = serverList[0];
	largestServerId = Integer.parseInt(serverList[1]);

}

dout.write(("OK\n").getBytes());
dout.flush();

//finding largest server type || first server to have largest amount of cores
//this is done so that we can implement the LRR algorithm into the scheduling loop

int largestCoreServer = 0;
String largestServerType = null;
for(int i = 0; i < serverCount; i++){
	if(largestCoreServer < serverCoreArr[i]){
		largestCoreServer = serverCoreArr[i];
		largestServerType = serverTypeArr[i];
	}
}

//System.out.println("highest core count: "+largestCoreServer);
//System.out.println("server type with highest cores: "+largestServerType);

//scheduling the first job from the begining of the server connection 
//now that we know what server it should be tasked to

int j = 0;

jobNum = Integer.parseInt(serverJobs[2]);
dout.write(("SCHD "+jobNum+" "+largestServerType+" "+j+"\n").getBytes());
dout.flush();
j++;

//begin loop for redying, getting jobs and job information
//this loop ensures that once we are getting jobs we are scheduling them directly into their correct server according to the LRR algorithm

int jobCount = 0;
while(!str.contains("NONE")){
        str = (String)in.readLine();
        //System.out.println("server says "+str);
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

//once the loop finishes that will indicate that we have assigned all jobs 
//meaning we can initialte the quitting of the client server connection and end the program

dout.write(("QUIT\n").getBytes());
dout.flush();
str = (String)in.readLine();
//System.out.println("server says "+str);
//System.out.println("server connection ended");
dout.close();
in.close();
s.close();
}catch(Exception e){System.out.println(e);}
}
}

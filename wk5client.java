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
dout.write(("GETS All\n").getBytes());
dout.flush();
str = (String)in.readLine();
System.out.println("server says "+str);
String[] serverDatalist = str.split(" ");
dout.write(("OK\n").getBytes());
dout.flush();
int serverNum = Integer.parseInt(serverDatalist[1]);
System.out.println(serverNum);
//str = (String)in.readLine();
//System.out.println("server says "+str);

for(int i=0; i< serverNum; i++){

str = (String)in.readLine();
System.out.println("server says "+str);
}

//schd

dout.write(("OK\n").getBytes());
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

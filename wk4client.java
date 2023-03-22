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
String str = (String)dis.readLine();
System.out.println("server says: "+str);
dout.write(("AUTH luke\n").getBytes());
dout.flush();
str = (String)dis.readLine();
System.out.println("server says "+str);
dout.write(("REDY\n").getBytes());
dout.flush();
str = (String)dis.readLine();
System.out.println("server says "+str);
dout.write(("NONE\n").getBytes());
dout.flush();
str = (String)dis.readLine();
System.out.println("server says "+str);
dout.write(("QUIT\n").getBytes());
dout.flush();
str = (String)dis.readLine();
System.out.println("server connection ended");
dout.close();
s.close();
}catch(Exception e){System.out.println(e);}
}
}

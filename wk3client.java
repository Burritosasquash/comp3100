import java.io.*;  
import java.net.*;  
public class MyClient {  
public static void main(String[] args) {  
try{      
Socket s=new Socket("localhost",6666);  
DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
DataInputStream dis=new DataInputStream(s.getInputStream());

dout.writeUTF("HELO");  
dout.flush();
String str = (String)dis.readUTF();
System.out.println("server says: "+str);
dout.writeUTF("BYE");
dout.flush();
str = (String)dis.readUTF();
System.out.println("server says: "+str);
dout.close();  
s.close();  
}catch(Exception e){System.out.println(e);}  
}
}

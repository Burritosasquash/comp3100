import java.net.*;  
import java.io.*;  
class MyClient{  
public static void main(String args[])throws Exception{  
Socket s=new Socket("127.0.0.1",50000);  
DataInputStream din=new DataInputStream(s.getInputStream());  
DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
BufferedReader in=new BufferedReader(new InputStreamReader(s.getInputStream()));  

String username = System.getProperty("user.name");

dout.write(("HELO\n").getBytes());
dout.flush();
  
String str="",str2="";  
while(!str.equals("stop")){  
str=in.readLine();  
dout.writeUTF(str);  
dout.flush();  
str2=din.readUTF();  
System.out.println("Server says: "+str2);  
}  
  
dout.close();  
s.close();  
}} 

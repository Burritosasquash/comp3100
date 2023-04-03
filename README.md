in this repository you will find my stage 1 project containing a client side program that connects to the application ds-server.

in order to run this program you must have installed the program ds-sim off of git hub using the link: https://github.com/distsys-MQ/ds-sim.git

please see the following steps to connect the file projectclient.class to the server and run the program:

step 1:
start the program ds-server locally in a terminal window with the command "./ds-server -c 'your-sample-file.xml' -v brief -n" 
the -n is important as it enables using newline at the end of each message and cannot run our client without it.

step 2: run the command "java projectclient" in another terminal window so that you can see the results on either end.

step 3: observe as the program runs.


if you want to compile the file projectclient.java yourself please run "javac projectclient.java"

if you would like to run the code without compiling into class please use the following command on step 2: "java projectclient.java"

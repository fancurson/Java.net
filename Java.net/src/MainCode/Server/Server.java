
package MainCode.Server;

import MainCode.Protocol;

import java.io.*;
import java.net.*;

public class Server {
    static public void main(String args[])
    {
        try (ServerSocket server = new ServerSocket(Protocol.PORT))
        {
            while (true) {
                Socket sock = accept( server );
                if ( sock != null ){
                    ServerThread serverThread = new ServerThread(sock);
                    serverThread.start();
                    System.err.println("Server started");
                }
                if (Server.getStopFlag()) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Server don't start");
            throw new RuntimeException(e);
        } finally {
            System.err.println("stopped");
        }
    }

    public static Socket accept( ServerSocket serv ) {
        assert( serv != null );
        try {
            serv.setSoTimeout( 1000 );
            Socket sock = serv.accept();
            return sock;
        } catch (SocketException e) {
        } catch (IOException e) {
        }
        return null;
    }
    private static Object syncFlags = new Object();
    private static boolean stopFlag = false;
    public static boolean getStopFlag() {
        synchronized ( Server.syncFlags ) {
            return stopFlag;
        }
    }
    public static void setStopFlag( boolean value ) {
        synchronized ( Server.syncFlags ) {
            stopFlag = value;
        }
    }
}

class ServerThread extends Thread {
    private Socket sock;
    private PrintWriter os;
    private BufferedReader is;
    private final InetAddress addr;
    private boolean disconnected = false;
    public ServerThread(Socket s) throws IOException {
        sock = s;
        s.setSoTimeout(1000);
        os = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
        is = new BufferedReader(new InputStreamReader(s.getInputStream()));
        addr = s.getInetAddress();
        this.setDaemon(true);
    }
    public void run(){
        try {
            while (true){
                String command = null;
                try {
                    command = new String(is.readLine());
                }
                catch (IOException e){
                }
                if (command != null)
                {
                    ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
                    Process process = processBuilder.start();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null ) {
                        os.println(line);
                    }
                }

            }
        }
        catch (Exception exception){
            System.err.println(exception.toString());
        }
        finally {
            disconnect();
        }
    }

    public void disconnect() {
        if ( ! disconnected )
            try {
                System.err.println( addr.getHostName() + " disconnected" );
                os.close();
                is.close();
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                this.interrupt();
                disconnected = true;
            }
    }

}
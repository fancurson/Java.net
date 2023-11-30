
package MainCode.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Vector;

public class Server {
    static public void main(String args[])
    {
        try
        {
            ServerSocket server = new ServerSocket(8000);
            server.accept();
            server.close();
        } catch (IOException e) {
            System.out.println("Server don't start");
            throw new RuntimeException(e);
        } finally {
            System.err.println("stopped");
        }
    }
}

class ServerThread extends Thread {
    private Socket              sock;
	private ObjectOutputStream 	os;
	private ObjectInputStream 	is;
	private InetAddress 		addr;

//	private String userNic = null;
//	private String userFullName;

//	private Object syncLetters = new Object();
//	private Vector<String> letters = null;

    /**
    *   Disconnect bool
     */
    private boolean disconnected = false;
    public ServerThread(Socket s) throws IOException {
        sock = s;
        s.setSoTimeout(1000);
        os = new ObjectOutputStream( s.getOutputStream() );
        is = new ObjectInputStream( s.getInputStream());
        addr = s.getInetAddress();
        this.setDaemon(true);
    }
    public void run(){

    }

    public void disconnect() {
        if ( ! disconnected )
            try {
                System.err.println( addr.getHostName() + " disconnected" );
//                unregister();
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

package MainCode.Server;

import MainCode.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Server {
    static public void main(String args[])
    {
        try (ServerSocket server = new ServerSocket(Protocol.PORT))
        {
            System.err.println("Server started");
            server.accept();

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
        try {

        }
        catch (Exception exception){
            System.err.println(exception.toString());
        }


//        try {
//            while ( true ) {
//                Message msg = null;
//                try {
//                    msg = ( Message ) is.readObject();
//                } catch (IOException e) {
//                } catch (ClassNotFoundException e) {
//                }
//                if (msg != null) switch ( msg.getID() ) {
//
//                    case Protocol.CMD_CONNECT:
//                        if ( !connect( (MessageConnect) msg ))
//                            return;
//                        break;
//
//                    case Protocol.CMD_DISCONNECT:
//                        return;
//
//                    case Protocol.CMD_USER:
//                        user(( MessageUser ) msg);
//                        break;
//
//                    case Protocol.CMD_CHECK_MAIL:
//                        checkMail(( MessageCheckMail ) msg );
//                        break;
//
//                    case Protocol.CMD_LETTER:
//                        letter(( MessageLetter ) msg );
//                        break;
//                }
//            }
//        } catch (IOException e) {
//            System.err.print("Disconnect...");
//        } finally {
//            disconnect();
//        }
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
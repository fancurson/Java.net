package MainCode;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.StringTokenizer;

public class XMLTest {

    static String makeFileName(Object obj) {
        String fileName;
        StringTokenizer st = new StringTokenizer(obj.getClass().getName(), ".");
        int n = st.countTokens();
        if ( n > 1 )
            while ( --n > 0 ) {
                st.nextToken();
            }
        fileName = st.nextToken();
        return fileName + ".xml";
    }

    public static void testXmlCommand(CommandMessageXml msg) throws IOException, JAXBException
    {
        String file = "XMLTestOUT" + File.separator +  makeFileName(msg) ;
        if (!(new File(file).getParentFile().exists())) {
            new File(file).getParentFile().mkdir();
        }
        OutputStream os = new FileOutputStream( file );
        CommandMessageXml.toXml( msg, os );
        os.close();
        InputStream is = new FileInputStream( file );
        CommandMessageXml msgx = CommandMessageXml.fromXml( msg.getClass(), is );
        is.close();
        System.out.println( "Write object: " + msg );
        System.out.println( "Read object: " + msgx );
    }

    public static void main(String[] args) {
        try {
            testXmlCommand( new CommandInputMessage("ls ~"));
            testXmlCommand( new CommandOutputMessage("Documents"));
        } catch (IOException e) {
            System.out.println(e);
        } catch (JAXBException e) {
            System.out.println(e);
        }
    }
}

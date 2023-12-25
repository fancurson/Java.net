package MainCode;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.Buffer;

/**
 * Base Message class
 */
public abstract class CommandMessageXml implements Serializable {

    public static void toXml( CommandMessageXml msg, DataOutputStream os ) throws JAXBException, IOException {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream(512))
        {
            JAXBContext context = JAXBContext.newInstance(msg.getClass());
            Marshaller m = context.createMarshaller();
            m.marshal( msg, baos );
            baos.flush();
            os.writeUTF(baos.toString());
        }
    }

    public static CommandMessageXml fromXml(Class<? extends CommandMessageXml> what, DataInputStream is ) throws JAXBException, IOException {
        String str = is.readUTF();
        try (ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes())) {
            JAXBContext context = JAXBContext.newInstance(what);
            Unmarshaller u = context.createUnmarshaller();
            return ( CommandMessageXml ) u.unmarshal( bais );
        }
    }

    public static void toXml( CommandMessageXml msg, OutputStream os ) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(msg.getClass());
        Marshaller m = context.createMarshaller();
        m.marshal( msg, os );
    }

    public static CommandMessageXml fromXml( Class<? extends CommandMessageXml> what, InputStream is ) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(what);
        Unmarshaller u = context.createUnmarshaller();
        return ( CommandMessageXml ) u.unmarshal( is );
    }
}

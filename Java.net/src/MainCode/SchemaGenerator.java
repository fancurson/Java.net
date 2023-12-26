package MainCode;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class SchemaGenerator {
    public static void createXmlSchema(String baseDirName, Class<? extends CommandMessageXml> what )
            throws JAXBException, IOException
    {
        JAXBContext context = JAXBContext.newInstance(what);
        context.generateSchema( new XmlScOutResolver(baseDirName, what.getName()));
    }

    static class XmlScOutResolver extends SchemaOutputResolver {
        private File file;
        public XmlScOutResolver(String baseDirName, String className) throws IOException
        {
            file = new File(baseDirName, className + ".xml");
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        public Result createOutput(String namespaceUri, String suggestedFileName ) throws IOException
        {
            return new StreamResult(file);
        }
    }

    public static void main(String[] args) {
        try {
            createXmlSchema("schema" + File.separator, CommandOutputMessage.class);
            createXmlSchema("schema" + File.separator, CommandInputMessage.class);
//            createXmlSchema("schema" + File.separator, CommandOutputMessage.class);
//            createXmlSchema("schema" + File.separator, CommandOutputMessage.class);
        }
        catch (JAXBException jaxbException){
        } catch (IOException ioException) {
        }
    }
}

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
            file = new File(baseDirName, className);
            file.mkdirs();
        }

        public Result createOutput(String namespaceUri, String suggestedFileName ) throws IOException
        {
            return new StreamResult(new File(file.getParent(), file.getName()));
        }
    }
}

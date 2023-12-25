package MainCode;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CommandInputMessage extends CommandMessage implements Serializable {
    String command;
    public CommandInputMessage()
    {
        assert (false);
    }
    public CommandInputMessage(String command){
        this.command = command;
    }
    public String getCommand() {
        return command;
    }
}

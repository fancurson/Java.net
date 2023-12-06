package MainCode;

import java.io.Serializable;

public class CommandMessage implements Serializable {
    String command;
    public CommandMessage()
    {
        assert (false);
    }
    public CommandMessage(String command){
        this.command = command;
    }
    public String getCommand() {
        return command;
    }
}

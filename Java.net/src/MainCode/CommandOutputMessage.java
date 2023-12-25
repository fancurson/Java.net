package MainCode;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CommandOutputMessage extends CommandMessage implements Serializable {
    String output;
    public CommandOutputMessage(){
        assert (false);
    }
    public CommandOutputMessage(String out){
        output = out;
    }
    public void append(String s){
        output += s + "\n";
    }
    public void setOutput(String output){
        this.output = output;
    }
    public String getOutput(){
        return output;
    }
}

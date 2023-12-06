package MainCode;

import java.io.Serializable;

public class CommandOutput implements Serializable {
    String output;
    public CommandOutput(){
        assert (false);
    }
    public CommandOutput(String out){
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

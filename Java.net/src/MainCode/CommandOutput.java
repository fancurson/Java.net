package MainCode;

import java.io.Serializable;

public class CommandOutput implements Serializable {
    String output;
    CommandOutput(){
        assert (false);
    }
    public void append(String s){
        output += s;
    }
    CommandOutput(String out){
        output = out;
    }
}

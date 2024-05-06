package help;

import java.io.Serializable;

public class Message  implements Serializable {

    private String content;
    public Message(String cont){
        this.content = cont;
    }


    @Override
    public String toString(){
        return content;
    }

}

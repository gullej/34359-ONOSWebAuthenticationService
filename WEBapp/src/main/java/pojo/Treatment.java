package pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

public class Treatment implements Serializable
{

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Object> instructions = null;


    public List<Object> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Object> instructions) {
        this.instructions = instructions;
    }

}

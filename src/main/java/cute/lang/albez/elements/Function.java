package cute.lang.albez.elements;

import java.util.HashMap;
import java.util.Map;

public class Function {
    private String functionName;
    private Params returningValue;
    private HashMap<String, Params> params;

    public Function(String functionName, Params returningValue, HashMap<String, Params> params) {
        this.functionName = functionName;
        this.returningValue = returningValue;
        this.params = params;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Params getReturningValue() {
        return returningValue;
    }

    public void setReturningValue(Params returningValue) {
        this.returningValue = returningValue;
    }

    public HashMap<String, Params> getParams() {
        return params;
    }

    public void setParams(HashMap<String, Params> params) {
        this.params = params;
    }


}

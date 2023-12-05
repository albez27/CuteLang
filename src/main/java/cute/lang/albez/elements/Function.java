package cute.lang.albez.elements;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
@Getter
@Setter
public class Function {
    private String functionName;
    private Params returningValue;
    private HashMap<String, Params> params;
    private Body functionBody;

    public Function(String functionName, Params returningValue, HashMap<String, Params> params, Body functionBody) {
        this.functionName = functionName;
        this.returningValue = returningValue;
        this.params = params;
        this.functionBody = functionBody;
    }
}

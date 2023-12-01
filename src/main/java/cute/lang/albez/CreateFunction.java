package cute.lang.albez;

import cute.lang.albez.elements.Function;

public class CreateFunction {
    public StringBuilder createEmptyFunction(String functionName, String returningValue, String params){
        StringBuilder sb = new StringBuilder();
        return sb;
    }
    public StringBuilder returnFunctionBody(Function function){
        StringBuilder sb = new StringBuilder();
        sb.append("public ").append(function.getReturningValue().getParamName()).append(" ").append(function.getFunctionName()).append("("); // public int functionName
        if(function.getParams().size() == 0) sb.append(")");
        else {
            for(String key : function.getParams().keySet()){
                sb.append(function.getParams().get(key).getParamName()).append(" ").append(key).append(", ");
            }
            sb.delete(sb.length()-2, sb.length());
            sb.append(")");
        }
        sb.append("{\n<<INSERT_CODE>>\n}");
        return sb;
    }
}

package cute.lang.albez.parsers;
import cute.lang.albez.elements.Function;
import cute.lang.albez.elements.Params;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BodyReader {
    public List<Function> readFunctionBody(List<Function> functionList){
        StringBuilder builder = new StringBuilder();
        for (Function function : functionList) {
            List<String> programLines = new ArrayList<>();
            String body = function.getFunctionBody().getRawBody();
            body = body.substring(1, body.length() - 7).replaceAll("\r\n    ", "");

            int currentIndex = 0;
            while (currentIndex < body.length() - 1) {
                while (body.charAt(currentIndex) != ';') {
                    builder.append(body.charAt(currentIndex));
                    currentIndex++;
                }
                programLines.add(builder.append(";").toString());
                builder = new StringBuilder();
                currentIndex++;
            }
            StringBuilder javaCodeBody = switcher(programLines);
            function.getFunctionBody().setJavaBody(javaCodeBody);
        }
        return functionList;
    }
    public StringBuilder switcher(List<String> programLines){
        StringBuilder javaCode = new StringBuilder();
        for(String str : programLines){
            str = str.trim();
            String keyWord = str.substring(0, str.indexOf(" "));
            if(str.contains("};")){
                str = str.substring(0, str.indexOf("};")) + ";}";
            }
            StringBuilder javaString = new StringBuilder();
            switch (keyWord){
                case "intik":
                case "flotik":
                case "boolik":
                case "dublik":
                case "strokulya": {
                    String var = str.substring(str.indexOf(" ") + 1);
                    var = var.substring(0, var.indexOf(" "));
                    javaString.append(Params.toParams(keyWord)).append(" ").append(var);
                    String statement = str.substring(str.indexOf("= "));
                    javaString.append(" ").append(convertStatementToJava(statement));
                    javaCode.append(javaString);
                    break;
                }
                case "returnik": {
                    String statement = str.substring(keyWord.length() + 1);
                    javaString.append("return").append(" ").append(convertStatementToJava(statement));
                    javaCode.append(javaString);
                    break;
                }
                case "ifik": {
                    String ifs = str.replaceAll("ifik", "if")
                            .replaceAll("dublik", "double")
                            .replaceAll("flotik", "float")
                            .replaceAll("intik", "int")
                            .replaceAll("boolik", "boolean")
                            .replaceAll(">", "")
                            .replaceAll("<", "")
                            .replaceAll("bolshe", ">")
                            .replaceAll("menshe", "<")
                            .replaceAll("ravno", "==")
                            .replaceAll("koreshok", "Math.sqrt");
                    javaCode.append(ifs);

                    break;
                }
                case "elsik":
                case "elsikif": {
                   String conditional = str
                           .replaceAll("printik", "System.out.println")
                           .replaceAll("elsikif", "else if")
                           .replaceAll("elsik", "else")
                           .replaceAll("dublik", "double")
                           .replaceAll("flotik", "float")
                           .replaceAll("intik", "int")
                           .replaceAll("boolik", "boolean")
                           .replaceAll(">", "")
                           .replaceAll("<", "")
                           .replaceAll("bolshe", ">")
                           .replaceAll("menshe", "<")
                           .replaceAll("ravno", "==")
                           .replaceAll("koreshok", "Math.sqrt");
                    javaCode.append(conditional);
                    break;
                }
                case "switchik": {
                    String switchWord = str.substring(str.indexOf(" ") + 1);
                    String statement = switchWord.substring(0, switchWord.indexOf(" "));
                    javaCode.append("switch (").append(statement).append(") {");
                    int index = switchWord.indexOf("\"");
                    String switchBody = switchWord.substring(index, switchWord.indexOf(";}")+1);
                    String[] test = switchBody.split(" <-");
                    Pattern pattern = Pattern.compile("(\"[\\w|\\W]+?\") -> ([\\w|\\W]+)");
                    Matcher matcher;
                    for(int i = 0; i < test.length - 2; i++){
                        matcher = pattern.matcher(test[i]);
                        if(matcher.find()){
                            javaCode.append("case (")
                                    .append(matcher.group(1))
                                    .append(") : { ")
                                    .append(matcher.group(2).replaceAll("printik", "System.out.println"))
                                    .append("; break;}");
                        }
                    }
                    javaCode.append("}");
                    break;
                }
                case "whilik": {
                    String whilikWord = str.substring(str.indexOf(" ") + 1);
                    String whilikStatement = whilikWord.substring(0, whilikWord.indexOf(" "));
                    Pattern pattern = Pattern.compile("whilik (\\([\\w|\\W]+?\\))");
                    Matcher matcher = pattern.matcher(str);
                    javaCode.append("while ");
                    if(matcher.find()){
                        String stat = matcher.group(1).replaceAll("elsik", "else")
                                .replaceAll("dublik", "double")
                                .replaceAll("flotik", "float")
                                .replaceAll("intik", "int")
                                .replaceAll("boolik", "boolean")
                                .replaceAll(">", "")
                                .replaceAll("<", "")
                                .replaceAll("bolshe", ">")
                                .replaceAll("menshe", "<")
                                .replaceAll("ravno", "==");
                        javaCode.append(stat);
                    }
                    String bodyWhile = whilikWord.substring(whilikWord.indexOf("{"), whilikWord.length() - 1)
                            .replaceAll("printik", "System.out.println")
                            .replaceAll(" <-", ";");
                    Pattern increment = Pattern.compile("incrementik\\(([\\w|\\W]*)\\)");
                    matcher = increment.matcher(bodyWhile);
                    if(matcher.find()){
                        bodyWhile = bodyWhile.replaceAll("incrementik\\(([\\w|\\W]*)\\)", matcher.group(1) + "++");
                    }
                    javaCode.append(bodyWhile).append("}");
                    break;
                }
                case "printik" : {
                    javaString.append(str.replaceAll("printik", "System.out.println"));
                    javaCode.append(javaString);
                    break;
                }
            }
        }
        return javaCode;
    }
    public void workWithTypes(){

    }
    public StringBuilder convertStatementToJava(String statement){
        StringBuilder sb = new StringBuilder();
        sb.append(statement.replaceAll("<","").replaceAll(">","").replaceAll("koreshok", "Math.sqrt"));
        return sb;
    }

}

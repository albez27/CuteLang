package cute.lang.albez.parsers;

import cute.lang.albez.elements.Body;
import cute.lang.albez.elements.Function;
import cute.lang.albez.elements.Params;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ParserFile {
    public static StringBuilder readFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("D:\\Studing\\maga\\albez\\src\\main\\resources\\static\\TestCase.txt"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            return sb;
        }  finally {
            br.close();
        }
    }
    // Regular expression for check valid functik -
    // functik [a-zA-Z]+ (intik|boolik|flotik|strokulya) \(((intik|boolik|flotik|strokulya) [a-zA-Z]+)(\, (intik|boolik|flotik|strokulya) [a-zA-Z])*\) *\{[\w|\W]*\}
    // ^(functik [a-zA-Z]+ (intik|boolik|flotik|strokulya) \((intik|boolik|flotik|strokulya) \w+(\, (intik|boolik|flotik|strokulya) \w+)+\) \{)+
    // ^(functik [a-zA-Z]+ (intik|boolik|flotik|strokulya) \((intik|boolik|flotik|strokulya) \w+(\, (intik|boolik|flotik|strokulya) \w+)+\) \{[\w|\W]*?\})+
    public static List<Function> getFunctionObject() throws IOException {
        StringBuilder sb = readFile();
        int countFunctions = 0;
        List<Function> functionsList = new ArrayList<>();
        String patternFunctik = "(functik [a-zA-Z]+ (intik|boolik|flotik|strokulya|nichegoshenki|dublik) \\((intik|boolik|flotik|strokulya|dublik) \\w+(\\, (intik|boolik|flotik|strokulya|dublik) \\w+)*\\) \\{[\\w|\\W]*?endik\\})+";

        Pattern patternWordFunctik = Pattern.compile("functik");
        Matcher matcher = patternWordFunctik.matcher(sb.toString());
        while (matcher.find()){
            countFunctions++;
        }

        Pattern patt = Pattern.compile(patternFunctik);
        matcher = patt.matcher(sb.toString());


        List<String> listWithFunction = new ArrayList<>();
        while(matcher.find()){
            listWithFunction.add(matcher.group());
            sb.delete(matcher.start(), matcher.end());
            matcher = patt.matcher(sb.toString());
        }
        if(countFunctions != listWithFunction.size()) {
            throw new RuntimeException("Ошибка парсинга");
        }
        String functionName = "";
        String returningValue = "";
        String arguments = "";
        String body = "";
        String functionSignature = "functik ([a-zA-Z]+) (intik|boolik|flotik|strokulya|nichegoshenki|dublik) (\\([\\w|\\W]*?\\)) (\\{[\\w|\\W]+?endik\\})";
        String extractParams = "(intik|boolik|flotik|strokulya) ([a-zA-Z]+)";
        Pattern extractParamsPattern = Pattern.compile(extractParams);
        Pattern extractFunctionGroups = Pattern.compile(functionSignature);

        for(String str : listWithFunction){
            matcher = extractFunctionGroups.matcher(str);
            if(matcher.find()){
                functionName = matcher.group(1);
                returningValue = matcher.group(2);
                arguments = matcher.group(3);
                body = matcher.group(4);
            }
            arguments = arguments.substring(1, arguments.length() - 1);
            Matcher extractorParams = extractParamsPattern.matcher(arguments);
            HashMap<String, Params> paramsMap = new HashMap<>();
            while (extractorParams.find()){
                paramsMap.put(extractorParams.group(2), Params.toParams(extractorParams.group(1)));
            }
            functionsList.add(new Function(functionName, Params.toParams(returningValue), paramsMap, new Body(body)));
        }
        System.out.println(functionsList);

        return functionsList;
    }

    public void workerWithBody(List<Function> functionList){
        String body = "";
        for(Function function : functionList){
            body = function.getFunctionBody().toString();
        }
    }
    public List<StringBuilder> createJavaCode(List<Function> functionList){
        List<StringBuilder> javaCodeList = new ArrayList<>();
        for(Function function : functionList){
            StringBuilder sb = new StringBuilder();
            sb.append("public static ").append(function.getReturningValue()).append(" ").append(function.getFunctionName()).append(" (");
            int count = 1;
            for(Map.Entry<String, Params> entry : function.getParams().entrySet()){
                sb.append(entry.getValue()).append(" ").append(entry.getKey());
                if(count != function.getParams().size()){
                    sb.append(", ");
                }
                count++;
            }
            sb.append(") {\n");
            sb.append(function.getFunctionBody().getJavaBody());
            sb.append("\n}");
            javaCodeList.add(sb);
        }
        return javaCodeList;
    }
    public void createJavaClassFile(List<StringBuilder> functionList) throws IOException {
        File myFile = new File("src/main/resources/static/Translated.java");
        FileOutputStream outputStream = new FileOutputStream(myFile);
        outputStream.write("public class Translated {\npublic static void main(String[] args){ }".getBytes());
        for(StringBuilder sb : functionList){
            outputStream.write(sb.toString().getBytes());
        }
        outputStream.write("}".getBytes());
        outputStream.close();

    }
}

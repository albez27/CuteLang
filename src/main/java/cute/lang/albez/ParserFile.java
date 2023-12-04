package cute.lang.albez;

import cute.lang.albez.elements.Function;
import cute.lang.albez.elements.Params;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static boolean checkIsFileCorrect() throws IOException {
        StringBuilder sb = readFile();
        int countFunctions = 0;
        int countParams = 0;
        List<Function> functionsList = new ArrayList<>();
        StringBuilder functik = new StringBuilder();
        StringBuilder functikName = new StringBuilder();
        StringBuilder params = new StringBuilder();
        HashMap<Function, List<Params>> argumentsMap = new HashMap<>();
        int index = 0;
        String patternFunctik = "(functik [a-zA-Z]+ (intik|boolik|flotik|strokulya) \\((intik|boolik|flotik|strokulya) \\w+(\\, (intik|boolik|flotik|strokulya) \\w+)+\\) \\{[\\w|\\W]*?\\})+";

        StringBuilder newSB = new StringBuilder();

        Pattern patternWordFunctik = Pattern.compile("functik");
        Matcher matcher = patternWordFunctik.matcher(sb.toString());
        while (matcher.find()){
            countFunctions++;
        }

        Pattern patt = Pattern.compile(patternFunctik);
        matcher = patt.matcher(sb.toString());

        int matches = 0;
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
        String functionSignature = "functik ([a-zA-Z]+) (intik|boolik|flotik|strokulya) (\\([\\w|\\W]*?\\))";
        Pattern functionSignaturePattern = Pattern.compile(functionSignature);

        String extractParams = "(intik|boolik|flotik|strokulya) ([a-zA-Z]+)";
        Pattern extractParamsPattern = Pattern.compile(extractParams);
        String extractFunctionName = "functik [a-zA-Z]*";
        Pattern extractFunctionGroups = Pattern.compile(functionSignature);

        for(String str : listWithFunction){
            matcher = extractFunctionGroups.matcher(str);
            if(matcher.find()){
                functionName = matcher.group(1);
                returningValue = matcher.group(2);
                arguments = matcher.group(3);
            }
            arguments = arguments.substring(1, arguments.length() - 1);
            Matcher extractorParams = extractParamsPattern.matcher(arguments);
            HashMap<String, Params> paramsMap = new HashMap();
            while (extractorParams.find()){
                paramsMap.put(extractorParams.group(2), Params.toParams(extractorParams.group(1)));
            }
            functionsList.add(new Function(functionName, Params.toParams(returningValue), paramsMap));
        }
        System.out.println(functionsList);

        return false;
    }
    public static int parseWordFunctik (StringBuilder sb, int index) {
        while (sb.charAt(index) != ' '){
            if(sb.indexOf("functik") == -1){
                throw new IllegalArgumentException();
            }
            else index++;
        }
        return index;
    }

}

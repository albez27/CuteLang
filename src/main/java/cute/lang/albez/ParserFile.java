package cute.lang.albez;

import cute.lang.albez.elements.Function;
import cute.lang.albez.elements.Params;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        for(int i = 0; i < sb.length()-1; i++){

//            i = parseWordFunctik(sb, i);
//
////            while (sb.charAt(i) != '{'){
////                i = parseWordFunctik(sb, i);
////            }
        }
        System.out.println(sb);
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

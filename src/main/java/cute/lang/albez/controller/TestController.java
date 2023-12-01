package cute.lang.albez.controller;

import cute.lang.albez.CreateFunction;
import cute.lang.albez.ParserFile;
import cute.lang.albez.elements.Function;
import cute.lang.albez.elements.Params;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class TestController {
    @GetMapping("/test")
    public void test() throws IOException {
        HashMap<String, Params> map = new HashMap<>();
        map.put("a", Params.intik);
        map.put("b", Params.intik);
        Function function = new Function("sum", Params.intik, map);
        CreateFunction createFunction = new CreateFunction();
        System.out.println(createFunction.returnFunctionBody(function));
        ParserFile.checkIsFileCorrect();
    }
}

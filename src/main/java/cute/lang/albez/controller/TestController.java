package cute.lang.albez.controller;

import cute.lang.albez.elements.Function;
import cute.lang.albez.parsers.BodyReader;
import cute.lang.albez.parsers.ParserFile;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final BodyReader bodyReader;
    private final ParserFile parserFile;
    @GetMapping("/test")
    public void test() throws IOException {
        List<Function> functionList = ParserFile.getFunctionObject();
        List<Function> editedList = bodyReader.readFunctionBody(functionList);
//        BodyReader.uravnenie(1, 17, -18);
        parserFile.createJavaClassFile(parserFile.createJavaCode(editedList));
    }
}

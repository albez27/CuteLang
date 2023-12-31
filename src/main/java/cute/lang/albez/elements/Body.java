package cute.lang.albez.elements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class Body {
    private String rawBody;
    private StringBuilder javaBody;

    // Сверху вниз читается файл и операции добавляются в лист в порядке чтения

    public Body(String rawBody) {
        this.rawBody = rawBody;
    }
}

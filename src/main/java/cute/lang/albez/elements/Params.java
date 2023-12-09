package cute.lang.albez.elements;

public enum Params {
    intik ("int"),
    strokulya ("String"),
    flotik ("float"),
    boolik ("boolean"),
    nichegoshenki ("void"),
    dublik ("double");

    private String paramName;

    Params(String paramName) {
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }

    @Override
    public String toString() {
        return paramName;
    }

    public static Params toParams(String paramName) {
        return switch (paramName) {
            case "intik" -> intik;
            case "strokulya" -> strokulya;
            case "flotik" -> flotik;
            case "boolik" -> boolik;
            case "nichegoshenki" -> nichegoshenki;
            case "dublik" -> dublik;
            default -> null;
        };
    }
}

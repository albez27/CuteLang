package cute.lang.albez.elements;

public enum Params {
    intik ("int"),
    strokulya ("String"),
    flotik ("float"),
    boolik ("boolean");

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
}

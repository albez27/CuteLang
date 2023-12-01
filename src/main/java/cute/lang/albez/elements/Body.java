package cute.lang.albez.elements;

import java.util.List;

public class Body {
    private String returnStatement;
    private List<String> loops;
    private List<String> cases;
    private List<OrderOperations> orderOperations;

    public String getReturnStatement() {
        return returnStatement;
    }

    public void setReturnStatement(String returnStatement) {
        this.returnStatement = returnStatement;
    }

    public List<String> getLoops() {
        return loops;
    }

    public void setLoops(List<String> loops) {
        this.loops = loops;
    }

    public List<String> getCases() {
        return cases;
    }

    public void setCases(List<String> cases) {
        this.cases = cases;
    }

    public List<OrderOperations> getOrderOperations() {
        return orderOperations;
    }

    public void setOrderOperations(List<OrderOperations> orderOperations) {
        this.orderOperations = orderOperations;
    }

    public Body(String returnStatement, List<String> loops, List<String> cases, List<OrderOperations> orderOperations) {
        this.returnStatement = returnStatement;
        this.loops = loops;
        this.cases = cases;
        this.orderOperations = orderOperations;
    }
}

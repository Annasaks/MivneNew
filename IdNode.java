public class IdNode extends Node{
    Tree runs;
    public IdNode(int min, Runner runner) {
        super(min, runner);
        this.key = runner.id;
    }

}

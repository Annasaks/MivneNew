public class MinNode extends Node{
    public MinNode(int min, Runner runner) {
            super(min, runner);
            this.key = runner.minRun;
    }

}

public class AvgNode extends Node {
    public AvgNode(int avg, Runner runner) {
        super(avg, runner);
        this.key = runner.sum / runner.numRuns;
    }
}


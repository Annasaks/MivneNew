public class Runner extends RunnerID {
    int id;
    int minRun;
    Tree runs;
    int numRuns;
    int sum;

    public Runner(Runner other){
        this.id = other.id;
    }
    public Runner(int id){
        this.id = id;
        this.runs = new Tree(2);
        minRun = runs.getRootValue();
        numRuns = 0;
        sum = 0;
    }

    @Override
    public boolean isSmaller(RunnerID other) {
        Runner other1 = new Runner((Runner) other);
        return (this.id < other1.id) ;
    }

    @Override
    public String toString() {
        return null;
    }
}
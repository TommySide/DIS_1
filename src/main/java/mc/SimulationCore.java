package mc;

public abstract class SimulationCore {
    private final int numberOfReps;
    private volatile boolean stopped = false;
    public SimulationCore(int numberOfReps) {
        this.numberOfReps = numberOfReps;
    }

    public void simuluj() {
        this.beforeReplications();
        for (int i = 0; i < this.numberOfReps; i++) {
            if (stopped)
                break;
            this.beforeReplication();
            this.replication();
            this.afterReplication();
        }
        this.afterReplications();
    }

    public void stopSimulation() {
        stopped = true;
    }

    public abstract void replication();
    public abstract void beforeReplications();
    public abstract void beforeReplication();
    public abstract void afterReplication();
    public abstract void afterReplications();
}

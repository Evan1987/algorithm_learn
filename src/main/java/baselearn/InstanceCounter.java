package baselearn;

public class InstanceCounter {
    private static int numInstances = 0;
    protected static int getCount() {
        return numInstances;
    }
    private static void addInstance() {
        numInstances ++;
    }

    InstanceCounter() {
        InstanceCounter.addInstance();
    }

    public static void main(String[] args) {
        System.out.printf("Starting with %d instances\n", InstanceCounter.getCount());
        for (int i = 0; i < 500; i++) {
            new InstanceCounter();
        }

        System.out.printf("Created %d instances\n", InstanceCounter.getCount());
    }
}

package BaseLearn;

public class FinalizationDemo {
    public static void main(String[] args){
        Cake c1 = new Cake(1);
        Cake c2 = new Cake(2);
        Cake c3 = new Cake(3);
        c2 = c3 = null;
        System.gc();
    }
}

class Cake extends Object {
    private int id;
    public Cake(int id){
        this.id = id;
        System.out.printf("Cake Object %d is created!\n", id);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.printf("Cake Object %d is disposed\n", this.id);
    }
}

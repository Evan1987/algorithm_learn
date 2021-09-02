package baselearn;

public class Puppy {
    int puppyAge;
    public Puppy(String name) {
        System.out.printf("The Dog's Name is: %s\n", name);
    }

    public void setAge(int age) {
        this.puppyAge = age;
    }

    public int getAge() {
        System.out.printf("The Dog's Age is: %d\n", this.puppyAge);
        return this.puppyAge;
    }

    public static void main(String[] args) {
        Puppy myPuppy = new Puppy("tommy");
        myPuppy.setAge(5);
        myPuppy.getAge();
        System.out.printf("Variables: %d", myPuppy.puppyAge);
    }
}

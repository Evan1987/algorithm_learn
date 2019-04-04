package BaseLearn;

public class Employee {
    public String name;
    private double salary;
    public Employee(String name){
        this.name = name;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void printInfo() {
        System.out.printf("Name: %s\n", this.name);
        System.out.printf("Salary: %.2f\n", salary);
    }

    public static void main(String[] args) {
        Employee emp = new Employee("Evan");
        emp.setSalary(6000.0);
        emp.printInfo();
    }

}

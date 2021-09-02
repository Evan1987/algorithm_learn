package algo4.chap05_string.Chap05_01;

/**
 * @author zhaochengming
 * @date 2020/8/16 20:49
 */
public class KeyIndexSort {

    private final int[] keyCount;

    public KeyIndexSort(int maxKey){
        this.keyCount = new int[maxKey + 2];
    }

    @SuppressWarnings("unchecked")
    public <K extends WithKey> void sort(K[] items) {
        for(K item: items)
            this.keyCount[item.getKey() + 1] ++;

        // change to cum count, known the start index for each key group
        for(int i = 0; i < this.keyCount.length - 1; i ++)
            this.keyCount[i + 1] += this.keyCount[i];

        // loop again to insert into an aux array ordered by group and seen
        K[] aux = (K[]) new WithKey[items.length];
        for(K item: items)
            aux[this.keyCount[item.getKey()] ++] = item;

        System.arraycopy(aux, 0, items, 0, items.length);
    }

    public static void main(String[] args) {
        String[] names = {
                "Anderson", "Brown", "Davis", "Garcia", "Harris",
                "Jackson", "Johnson", "Jones", "Martin", "Martinez",
                "Miller", "Moore", "Robinson", "Smith", "Taylor",
                "Thomas", "Thompson", "White", "Williams", "Wilson"};
        int[] groups = {
                2, 3, 3, 4, 1,
                3, 4, 3, 1, 2,
                2, 1, 2, 4, 3,
                4, 4, 2, 3, 4};

        Student[] students = new Student[20];
        for(int i = 0; i < 20; i ++)
            students[i] = new Student(names[i], groups[i]);

        KeyIndexSort sortObj = new KeyIndexSort(4);
        sortObj.sort(students);

        for(Student s: students)
            System.out.println(s);
    }
}


interface WithKey {
    int getKey();
}

class Student implements WithKey {
    String name;
    int group;
    public Student(String name, int group){
        this.name = name;
        this.group = group;
    }

    public int getKey(){
        return this.group;
    }

    @Override
    public String toString() {
        return this.name + " " + this.group;
    }
}

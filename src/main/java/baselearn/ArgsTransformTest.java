package baselearn;

public class ArgsTransformTest {
    public static void main(String[] args){
        int a = 10, b = 11;
        Data data = new Data(a, b);  // 引用型变量
        int[] nums = {a, b};  // 引用型变量
        Test tt = new Test();

        tt.swap(a, b);
        tt.print(a, b);  // 10, 11 not swapped

        tt.swap(data);
        tt.print(data);  // 11, 10 swapped

        tt.swap(nums);
        tt.print(nums);  // 11, 10 swapped
    }
}

class Data {
    int a;
    int b;
    Data(int a, int b){
        this.a = a;
        this.b = b;
    }
}

class Test{

    void swap(int a, int b){
        int temp = a;
        a = b;
        b = temp;
    }

    public void swap(Data data){
        int temp = data.a;
        data.a = data.b;
        data.b = temp;
    }

    public void swap(int[] data){
        int temp = data[0];
        data[0] = data[1];
        data[1] = temp;
    }

    public void print(int a, int b){
        System.out.printf("a is %d, b is %d\n\n", a, b);
    }
    public void print(Data data){
        System.out.printf("a is %d, b is %d\n\n", data.a, data.b);
    }
    public void print(int[] data){
        System.out.printf("%d, %d", data[0], data[1]);
    }
}

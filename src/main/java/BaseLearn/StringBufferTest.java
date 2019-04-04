package BaseLearn;

public class StringBufferTest {
    public static void main(String[] args) {
        StringBuffer sBuffer = new StringBuffer("My name is ");
        sBuffer.append("John");
        sBuffer.append(" ");
        sBuffer.append("Smith ");
        sBuffer.append(1984);
        System.out.println(sBuffer);

    }
}

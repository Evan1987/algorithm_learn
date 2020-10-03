package Chap05_String.Chap05_03;

public interface ISearch {
    default int search(String pattern, String text) {
        return text.length();
    }
}

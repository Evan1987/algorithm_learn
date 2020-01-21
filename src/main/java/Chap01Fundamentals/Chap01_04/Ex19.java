package Chap01Fundamentals.Chap01_04;

import utils.Tuple;

public class Ex19 {


}

class Point implements Comparable<Point>{
    public final int row;
    public final int col;
    public final int value;
    public Point(int x, int y, int value){
        this.row = x;
        this.col = y;
        this.value = value;
    }


    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Point)) return false;
        Point other = (Point) obj;
        return this.row == other.row
                && this.col == other.col
                && this.value == other.value;
    }

    @Override
    public int compareTo(Point o) {
        return this.value - o.value;
    }

    @Override
    public String toString() {
        return "Point(" + this.row + "," + this.col + "," + this.value + ")";
    }
}


class Offset {
    public int leftBound;
    public int rightBound;
    public int upBound;
    public int downBound;

    public Offset(int minRow, int maxRow, int minCol, int maxCol){
        this.leftBound = minCol;
        this.rightBound = maxCol;
        this.upBound = minRow;
        this.downBound = maxRow;
    }

    public Tuple<Integer, Integer> getMidRowAndCol(){
        int midRow = (this.upBound + this.downBound) / 2;
        int midCol = (this.leftBound + this.rightBound) / 2;
        return new Tuple<>(midRow, midCol);
    }
}


class FindLocalMinimum {
    private int[][] matrix;

    public FindLocalMinimum(int[][] matrix) throws Exception {
        this.matrix = matrix;
        int n = matrix.length;
        if(matrix[0].length != n) throw new Exception("The matrix is not a square");
    }

    /**
     * Compare the surrounding elements with given point.
     * @return if if is a local minimum and the minimum point
     * */
    private Tuple compareSurroundings(Point point, Offset offset){
        int row = point.row;
        int col = point.col;
        int minRow = offset.upBound;
        int maxRow = offset.downBound;
        int minCol = offset.leftBound;
        int maxCol = offset.rightBound;

        Point res = point;
        int[] indexChanges = {-1, 0, 1};
        for(int rowChange: indexChanges){
            for(int colChange: indexChanges){
                if(rowChange == 0 && colChange == 0)
                    continue;
                int targetRow = row + rowChange;
                int targetCol = col + colChange;

                if (targetRow < minRow || targetRow > maxRow || targetCol < minCol || targetCol > maxCol)
                    continue;
                int targetValue = this.matrix[targetRow][targetCol];
                if(targetValue < res.value)
                    res = new Point(targetRow, targetCol, targetValue);
            }
        }
        return new Tuple<>(res == point, res);
    }

    public Point search(Offset offset){

    }
}
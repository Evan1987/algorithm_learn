package Chap01Fundamentals.Chap01_04;

import java.util.Iterator;

public class Ex19 {
    public static void main(String[] args) {
        int[][] matrix = {
                {9,3,5,5,6,9,8},
                {7,2,5,4,5,0,3},
                {9,8,9,3,2,4,8},
                {7,6,3,7,3,5,6},
                {9,0,6,6,4,0,4},
                {8,9,0,5,5,3,5},
                {6,3,2,3,4,4,7}
        };
        FindLocalMinimum findLocalMinimum = new FindLocalMinimum(matrix);
        try{
            Point point = findLocalMinimum.search();
            System.out.println(point);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Point{
    int row;
    int col;
    int value;
    Point(int x, int y, int value){
        this.row = x;
        this.col = y;
        this.value = value;
    }

    Point(int x, int y, int[][] matrix){
        this.row = x;
        this.col = y;
        this.value = matrix[x][y];
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
    public String toString() {
        return "Point(" + this.row + "," + this.col + "," + this.value + ")";
    }

    void setValue(int value){
        this.value = value;
    }
}


class Offset {
    int leftCol;
    int rightCol;
    int upRow;
    int downRow;

    int midRow;
    int midCol;

    int nRows;
    int nCols;

    Offset(int minRow, int maxRow, int minCol, int maxCol){
        this.leftCol = minCol;
        this.rightCol = maxCol;
        this.upRow = minRow;
        this.downRow = maxRow;

        this.midRow = (minRow + maxRow) / 2;
        this.midCol = (minCol + maxCol) / 2;

        this.nRows = maxRow - minRow + 1;
        this.nCols = maxCol - minCol + 1;
    }

    /**
     * If the point is in the offset area including the boundaries.
     * */
    Boolean containPoint(Point point) {
        return point.row <= downRow && point.row >= upRow
                && point.col <= rightCol && point.col >= leftCol;
    }

    private Offset upperLeft() {
        return new Offset(upRow, midRow, leftCol, midCol);
    }

    private Offset upperRight() {
        return new Offset(upRow, midRow, midCol, rightCol);
    }

    private Offset lowerLeft() {
        return new Offset(midRow, downRow, leftCol, midCol);
    }

    private Offset lowerRight() {
        return new Offset(midRow, downRow, midCol, rightCol);
    }

    /**
     * Indicates which quadrant the point is in.
     * If the point is on the boundary, return null
     * If the point is not in the offset area, throw exception.
     * */
    Offset indexPoint(Point point) throws Exception{
        if(!this.containPoint(point))
            throw new Exception("The point is not in the offset area.");

        if(point.row == midRow || point.col == midCol)
            return null;  // The point is just on the mid boundaries.

        if(point.row < midRow){
            return point.col < midCol ? this.upperLeft(): this.upperRight();
        }
        else{
            return point.col < midCol ? this.lowerLeft(): this.lowerRight();
        }
    }

    Iterator<Point> getFramePoint(int[][] matrix){
        return new FramePoints(matrix);
    }

    private class FramePoints implements Iterator<Point> {
        private Offset outer = Offset.this;
        private int row = outer.upRow;
        private int col = outer.leftCol;
        int[][] matrix;

        FramePoints(int[][] matrix){
            this.matrix = matrix;
        }
        /**
         * If the index is already on the lower right.
         * */
        @Override
        public boolean hasNext() {
            return row <= outer.downRow;
        }

        @Override
        public Point next() {
            Point point = new Point(row, col, this.matrix);

            // move on each row
            // move on the top, bottom and mid row
            if(row == outer.upRow || row == outer.midRow || row == outer.downRow){
                col += 1;
            }else{
                // move on the frames
                if(col == outer.rightCol)
                    col += 1;
                else if(col == outer.midCol)
                    col = outer.rightCol;
                else  // col == outer.leftCol
                    col = outer.midCol;
            }

            // move to next row
            if(col > outer.rightCol){
                row += 1;
                col = outer.leftCol;
            }
            return point;
        }
    }
}

class FindLocalMinimum {
    private int[][] matrix;

    public FindLocalMinimum(int[][] matrix){
        this.matrix = matrix;
    }

    public Point search() throws Exception{
        int nRow = matrix.length;
        int nCol = matrix[0].length;
        Offset offset = new Offset(0, nRow - 1, 0, nCol - 1);
        Point targetPoint = new Point(0, 0, matrix);
        while(offset != null){
            // Loop on the window points (points on the window frames)
            // Find the minimum point
            Iterator<Point> points = offset.getFramePoint(matrix);
            while(points.hasNext()){
                Point point = points.next();
                Point minPoint = compareSurrounding(point, offset);
                // If the local minimum is on the frame, directly return it.
                if(minPoint == point)
                    return minPoint;
                // Else keep tracking the minimum point
                if(minPoint.value < targetPoint.value)
                    targetPoint = minPoint;
            }

            offset = offset.indexPoint(targetPoint);
        }
        return targetPoint;
    }

    /**
     * Compare the point to the surroundings.
     * @return : the minimum point among the point and its surroundings.
     * */
    private Point compareSurrounding(Point point, Offset offset) {
        int[] indexChanges = {-1, 0, 1};
        Point minPoint = point;
        int minValue = point.value;

        for(int rowChange: indexChanges){
            for(int colChange: indexChanges){
                if(rowChange == 0 && colChange == 0)
                    continue;
                int row = point.row + rowChange;
                int col = point.col + colChange;
                Point newPoint = new Point(row, col, 0);
                if(!offset.containPoint(newPoint)) continue;
                int value = this.matrix[row][col];
                if(value < minValue){
                    minValue = value;
                    newPoint.setValue(value);
                    minPoint = newPoint;
                }
            }
        }

        return minPoint;
    }
}
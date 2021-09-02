package algo4.chap01_fundamentals.Chap01_02;


public class Rational{
    private int numerator;
    private int denominator;
    private int MAX = Integer.MAX_VALUE;
    private int MIN = Integer.MIN_VALUE;

    private static int gcd(int x, int y){
        if(x == 0 || y == 0) return 1;
        x = Math.abs(x);
        y = Math.abs(y);
        if(x < y){
            int temp = x;
            x = y;
            y = temp;
        }
        if (x % y == 0) return y;
        return gcd(y, x % y);
    }

    public Rational(int numerator, int denominator) throws ArithmeticException{
        if(denominator == 0){
            throw new ArithmeticException("Divide by zero!");
        }
        int commonDivisor = gcd(numerator, denominator);
        this.numerator = numerator / commonDivisor;
        this.denominator = denominator / commonDivisor;
    }

    public Rational reverse(){
        return new Rational(this.numerator * -1, this.denominator);
    }

    public Rational reciprocal(){
        return new Rational(this.denominator, this.numerator);
    }

    public Rational times(Rational that){
        return new Rational(this.numerator * that.numerator,
                this.denominator * that.denominator);
    }

    public Rational plus(Rational that){
        return new Rational(
                this.numerator * that.denominator + this.denominator * that.numerator,
                this.denominator * that.denominator
        );
    }

    public Rational minus(Rational that){
        return plus(that.reverse());
    }

    public Rational divides(Rational that){
        return times(that.reciprocal());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Rational that = (Rational) obj;
        if (this.numerator != that.numerator) {
            return false;
        }
        if (this.denominator != that.denominator) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.numerator + " / " + this.denominator;
    }
}

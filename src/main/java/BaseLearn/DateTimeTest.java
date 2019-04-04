package BaseLearn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeTest {
    public static void main(String[] args){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(date.toString());
        System.out.println(format.format(date));
        String x = "2019-03-21 18:19:27";
        Date t;
        try{
            t = format.parse(x);
            System.out.println(t);
        }catch (ParseException e){
            System.out.printf("Unparsable String %s using format %s", x, format.toPattern());
        }

        // time diff test
        try{
            long tic = System.currentTimeMillis();
            Thread.sleep((int)(Math.random() * 1000));
            long toc = System.currentTimeMillis();
            long diff = toc - tic;
            System.out.printf("Time difference is: %.2f s", diff / 1000.0);
        }catch (InterruptedException e){
            System.out.println("Got an exception");
        }

        // calendar
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 29);

    }
}

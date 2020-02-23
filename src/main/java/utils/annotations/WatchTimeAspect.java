package utils.annotations;

import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.concurrent.TimeUnit;


@Aspect
public class WatchTimeAspect {

    @Around("execution(* *(..)) && @annotation(watchTime)")
    public Object watch(ProceedingJoinPoint joinPoint, WatchTime watchTime){
        Object response = null;
        StopWatch timer = new StopWatch();
        timer.start();
        try{
            response = joinPoint.proceed();
        }catch (Throwable ignored){

        }finally {
            timer.stop();
            long ns = timer.getTime(TimeUnit.NANOSECONDS);
            System.out.printf("[Time Cost] %s - %.4f ms \n", watchTime.methodDesc(), ns / 1000000.0);
        }
        return response;
    }
}
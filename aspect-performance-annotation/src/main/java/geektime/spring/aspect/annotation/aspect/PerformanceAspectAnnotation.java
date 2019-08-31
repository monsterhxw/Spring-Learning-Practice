package geektime.spring.aspect.annotation.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspectAnnotation {

    @Around("logOps()")
    public Object logPerformance(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        String name = "-";
        String result = "Y";
        try {
            name = pjp.getSignature().toShortString();
            return pjp.proceed();
        } catch (Throwable throwable) {
            result = "N";
            throw throwable;
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("Log Annotation;{};{};{}ms", name, result, endTime - startTime);
        }
    }

    /**
     * 以自定义 @Log 注解为切点
     */
    @Pointcut("@annotation(geektime.spring.aspect.annotation.aspect.Log)")
    private void logOps() {
    }

}

package dparant.exSpring.logging;

import dparant.exSpring.controller.UserController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Logging class,  used to display data during api calls
 *
 * @author dylan
 */
@Aspect
@Component
public class AspectLogger {
    private static final Logger LOGGER = LogManager.getLogger(AspectLogger.class);

    /**
     * Timer used to calculate the elapsed time during an api call
     */
    private long timer;

    /**
     * méthod called when we call any method from UserController
     *
     * @see UserController
     */
    @Pointcut("execution( public * dparant.exSpring.controller.UserController.*(..))")
    public void apiCall() {
    }

    /**
     * Display data before the api perform an operation
     *
     * @param joinPoint contains Data of the method called
     * @see #apiCall()
     */
    @Before("apiCall()")
    public void logBefore(JoinPoint joinPoint) {
        timer = System.currentTimeMillis();
        LOGGER.info("Logging Before api call ...");
        LOGGER.info("Method called: {}", joinPoint.getSignature().toString());
        Arrays.stream(joinPoint.getArgs()).forEach(e -> LOGGER.info("Input data: {}", e));
    }

    /**
     * Display data after the api perform an operation
     *
     * @param joinPoint      contains Data of the method called
     * @param responseEntity contains the Response of the method called
     * @see #apiCall()
     */
    @AfterReturning(pointcut = "apiCall()", returning = "responseEntity")
    public void logAfter(JoinPoint joinPoint, ResponseEntity responseEntity) {
        LOGGER.info("Logging After api call ...");
        LOGGER.info("Method called: {}", joinPoint.getSignature().toString());
        LOGGER.info("Output data: {}", responseEntity.toString());
        LOGGER.info("Elapsed time : {} ms", System.currentTimeMillis() - timer);
    }
}

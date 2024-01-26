package com.example.demo.common.logging;

import com.example.demo.common.error.ErrorResponse;
import com.google.common.base.Joiner;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.Duration;
import java.time.LocalDateTime;

@Log4j2
public class LoggingHelper {

    private LoggingHelper() {
    }

    private static String getJoinedArgs(Object[] args) {
        return args == null ? null : Joiner.on(",").useForNull("null").join(args);
    }

    public static void toControllerProcessedLog(String className, String methodName, LocalDateTime start, Object[] args, Object result, LocalDateTime end) {
        String joinedArgs = getJoinedArgs(args);
        String builder = System.lineSeparator()
                + "   - logType  : [Controller]" + System.lineSeparator()
                + "   - class    : [" + className + "]" + System.lineSeparator()
                + "   - method   : [" + methodName + "]" + System.lineSeparator()
                + "   - Input    : [" + joinedArgs + "] " + System.lineSeparator()
                + "   - Output   : [" + result + "] " + System.lineSeparator()
                + "   - Started  : [" + start + "] " + System.lineSeparator()
                + "   - Ended    : [" + end + "] " + System.lineSeparator()
                + "   - Elapsed  : [" + Duration.between(start, end).toMillis() + "]" + System.lineSeparator()
                + "   - All done.";
        log.info(builder);
    }

    public static void toControllerRequestLog(String requestURI, Object[] args) {
        String joinedArgs = getJoinedArgs(args);
        String builder = System.lineSeparator()
                + "   - logType : [Request]" + System.lineSeparator()
                + "   - URI     : [" + requestURI + "]" + System.lineSeparator()
                + "   - Input   : [" + joinedArgs + "] " + System.lineSeparator()
                + "   - All done.";
        log.info(builder);
    }

    public static void toExceptionLogger(String className, String methodName, Object[] args, Throwable t) {
        String joinedArgs = getJoinedArgs(args);
        String builder = System.lineSeparator()
                + "   - logType  : [Exception]" + System.lineSeparator()
                + "   - class    : [" + className + "]" + System.lineSeparator()
                + "   - method   : [" + methodName + "]" + System.lineSeparator()
                + "   - Input    : [" + joinedArgs + "] " + System.lineSeparator()
                + "   - Message  : [" + ExceptionUtils.getStackTrace(t) + "]" + System.lineSeparator()
                + "   - All done.";
        log.error(builder);
    }

    public static void toRequestInvalidLog(WebRequest request, ErrorResponse errorResponse) {
        LocalDateTime now = LocalDateTime.now();
        String requestURI = ((ServletWebRequest) request).getRequest().getRequestURI();
        String builder = System.lineSeparator()
                + "   - logType : [Invalid Request]" + System.lineSeparator()
                + "    - URI    : [" + requestURI + "]" + System.lineSeparator()
                + "   - Started : [" + now + "]" + System.lineSeparator()
                + "   - output  : [" + errorResponse + "] " + System.lineSeparator()
                + "   - All done.";
        log.info(builder);
    }

    public static void toServiceLog(String className, String methodName, LocalDateTime start, Object[] args, Object result, LocalDateTime end) {
        String joinedArgs = getJoinedArgs(args);
        String builder = System.lineSeparator()
                + "   - logType  : [Service]" + System.lineSeparator()
                + "   - class    : [" + className + "]" + System.lineSeparator()
                + "   - method   : [" + methodName + "]" + System.lineSeparator()
                + "   - Input    : [" + joinedArgs + "] " + System.lineSeparator()
                + "   - Output   : [" + result + "] " + System.lineSeparator()
                + "   - Started  : [" + start + "] " + System.lineSeparator()
                + "   - Ended    : [" + end + "] " + System.lineSeparator()
                + "   - Elapsed  : [" + Duration.between(start, end).toMillis() + "]" + System.lineSeparator()
                + "   - All done.";
        log.info(builder);
    }

}

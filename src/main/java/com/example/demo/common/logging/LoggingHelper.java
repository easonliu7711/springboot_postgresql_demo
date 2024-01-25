package com.example.demo.common.logging;

import com.google.common.base.Joiner;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.MDC;
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
        String builder = System.lineSeparator() + "   - class    : [" + className + "]" + System.lineSeparator() + "   - method   : [" + methodName + "]" + System.lineSeparator() + "   - Input    : [" + joinedArgs + "] " + System.lineSeparator() + "   - Output   : [" + result + "] " + System.lineSeparator() + "   - Started  : [" + start + "] " + System.lineSeparator() + "   - Ended    : [" + end + "] " + System.lineSeparator() + "   - Elapsed  : [" + Duration.between(start, end).toMillis() + "]" + System.lineSeparator() + "   - All done.";
        MDC.put("logType", "Controller");
        log.info(builder);
        MDC.put("logType", "");
    }

    public static void toControllerRequestLog(String requestURI, Object[] args) {
        String joinedArgs = getJoinedArgs(args);
        String builder = System.lineSeparator() + "   - URI  : [" + requestURI + "]" + System.lineSeparator() + "   - Input : [" + joinedArgs + "] " + System.lineSeparator() + "   - All done.";
        MDC.put("logType", "Request");
        log.info(builder);
        MDC.put("logType", "");
    }

    public static void toExceptionLogger(String className, String methodName, Object[] args, Throwable t) {
        String joinedArgs = getJoinedArgs(args);
        String builder = System.lineSeparator() + "   - class    : [" + className + "]" + System.lineSeparator() + "   - method   : [" + methodName + "]" + System.lineSeparator() + "   - Input    : [" + joinedArgs + "] " + System.lineSeparator() + "   - Message  : [" + ExceptionUtils.getStackTrace(t) + "]" + System.lineSeparator() + "   - All done.";
        MDC.put("logType", "Exception");
        log.error(builder);
        MDC.put("logType", "Error");
    }

    public static void toServiceLog(String className, String methodName, LocalDateTime start, Object[] args, Object result, LocalDateTime end) {
        String joinedArgs = getJoinedArgs(args);
        String builder = System.lineSeparator() + "   - class    : [" + className + "]" + System.lineSeparator() + "   - method   : [" + methodName + "]" + System.lineSeparator() + "   - Input    : [" + joinedArgs + "] " + System.lineSeparator() + "   - Output   : [" + result + "] " + System.lineSeparator() + "   - Started  : [" + start + "] " + System.lineSeparator() + "   - Ended    : [" + end + "] " + System.lineSeparator() + "   - Elapsed  : [" + Duration.between(start, end).toMillis() + "]" + System.lineSeparator() + "   - All done.";
        MDC.put("logType", "Service");
        log.info(builder);
        MDC.put("logType", "");
    }

}

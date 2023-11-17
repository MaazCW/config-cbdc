//package com.opl.cbdc.config.utils;
//
//import com.opl.cbdc.common.api.auth.utils.AuthCredentialUtils;
//import com.opl.cbdc.common.logs.api.models.RequestLogReq;
//import com.opl.cbdc.common.logs.client.AnsLogsClient;
//import com.opl.cbdc.utils.common.MultipleJSONObjectHelper;
//import com.opl.cbdc.utils.common.OPLUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StopWatch;
//import org.springframework.web.context.request.RequestContextHolder;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.*;
//
///**
// * @author jaimin.darji
// */
//@Aspect
//@Component
//public class LoggingAspect {
//
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//
//
//    @Value("${server.servlet.context-path}")
//    private String path = "";
//
//    @Autowired(required = false)
//    private AnsLogsClient clientLogs;
//
//    @Autowired
//    private HttpServletRequest request;
//    @Autowired
//    private HttpServletResponse response;
//
//    /**
//     * Pointcut that matches all repositories, services and Web REST endpoints.
//     */
////    @Pointcut("within(@org.springframework.stereotype.Service *)"
////            + " || within(@org.springframework.web.bind.annotation.RestController *)")
//    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
//    public void springBeanPointcut() {
//        // Method is empty as this is just a Pointcut, the implementations are in the
//        // advices.
//        log.info("springBeanPointcut");
//    }
//
//    /**
//     * Pointcut that matches all Spring beans in the application's main packages.
//     */
//    @Pointcut("within(com.opl.cbdc..*)")
//    public void applicationPackagePointcut() {
//        // Method is empty as this is just a Pointcut, the implementations are in the
//        // advices.
//        log.info("applicationPackagePointcut");
//    }
//
//    /**
//     * Pointcut that matches all Spring beans in the application's main packages.
//     */
//    @Pointcut("(within(com.opl.cbdc.msme.service.gst..*) && within(@org.springframework.transaction.annotation.Transactional *))" +
//            "|| (within(com.opl.cbdc.common.service.notification..*))")
//    public void removeHandler() {
//        // Method is empty as this is just a Pointcut, the implementations are in the
//        // advices.
//        log.info("Transactional Remove Handle");
//    }
//
//    /**
//     * Advice that logs methods throwing exceptions.
//     *
//     * @param joinPoint join point for advice
//     * @param e         exception
//     */
//    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut() && !removeHandler()", throwing = "e")
//    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
//        RequestLogReq requestLogReq = new RequestLogReq();
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        String className = methodSignature.getDeclaringType().getSimpleName();
//        String methodName = methodSignature.getName();
//        String methodParameterNames = methodSignature.getMethod().toString();
//        requestLogReq.setContextPath(path);
//        requestLogReq.setClassName(className);
//        requestLogReq.setMethodName(methodName);
//        requestLogReq.setMethodParamName(methodParameterNames);
//        requestLogReq.setClassPath(joinPoint.getTarget().getClass().getName());
//        requestLogReq.setRequestArgs(Arrays.toString(joinPoint.getArgs()));
//        requestLogReq.setCreatedDate(new Date());
//        requestLogReq.setErrorMessage(e.getMessage());
//        if (clientLogs != null) {
//            clientLogs.updateRequestLog(requestLogReq);
//        }
//
//    }
//
//    /**
//     * Advice that logs when a method is entered and exited.
//     *
//     * @param joinPoint join point for advice
//     * @return result
//     * @throws Throwable throws IllegalArgumentException
//     */
//    @Around("applicationPackagePointcut() && springBeanPointcut() && !removeHandler()")
//    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        RequestLogReq requestLogReq = new RequestLogReq();
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        String className = methodSignature.getDeclaringType().getSimpleName();
//        String methodName = methodSignature.getName();
//        String methodParameterNames = methodSignature.getMethod().toString();
//        requestLogReq.setContextPath(path);
//        requestLogReq.setClassName(className);
//        requestLogReq.setMethodName(methodName);
//        requestLogReq.setMethodParamName(methodParameterNames);
//        requestLogReq.setClassPath(joinPoint.getTarget().getClass().getName());
//        requestLogReq.setRequestArgs(Arrays.toString(joinPoint.getArgs()));
////            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        if (!OPLUtils.isObjectNullOrEmpty(request) && RequestContextHolder.getRequestAttributes() != null) {
//            requestLogReq.setRequestUrl(request.getRequestURL().toString());
//            requestLogReq.setRequestMethod(request.getMethod());
//            requestLogReq.setRequestIp(request.getRemoteAddr());
//            if (!OPLUtils.isObjectNullOrEmpty(request.getHeader("ur_cu"))) {
//                requestLogReq.setUserEmail(new String(Base64.getDecoder().decode(request.getHeader("ur_cu"))));
//            }
//            requestLogReq.setClient(AuthCredentialUtils.isClient(request));
//            Enumeration<String> headerNames = request.getHeaderNames();
//            Map<String, String> requestHeader = new HashMap<>();
//            if (!OPLUtils.isObjectNullOrEmpty(headerNames)) {
//                while (headerNames.hasMoreElements()) { // For Set Request Header
//                    String key = headerNames.nextElement();
//                    String value = request.getHeader(key);
//                    requestHeader.put(key, value);
//                }
//            }
//            requestLogReq.setRequestHeader(requestHeader.toString());// log.info("Request Headers ::{}", Collections.list(request.getHeaderNames()));
//        }
////            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
////                    .getResponse();
//        if (!OPLUtils.isObjectNullOrEmpty(response) && RequestContextHolder.getRequestAttributes() != null) {
//            Collection<String> responseHeaderNames = response.getHeaderNames();
//            Map responseHeader = new HashMap();
//            if (!OPLUtils.isObjectNullOrEmpty(responseHeaderNames)) { // For set Response header
//                for (String resH : responseHeaderNames) {
//                    responseHeader.put(resH, response.getHeader(resH));
//                }
//            }
//
//            requestLogReq.setResponseHeader(responseHeader.toString());// log.info(" Response Headers ::{}", response.getHeaderNames());
//            requestLogReq.setResponseStatus(response.getStatus());
//        }
//
//        requestLogReq.setCreatedDate(new Date());
//        try {
//            final StopWatch stopWatch = new StopWatch();
//            stopWatch.start();
//            Object result = joinPoint.proceed();
//            stopWatch.stop();
//            if (result != null) {
//            try {
//                    if (result instanceof ResponseEntity) {
//                        if (!(((ResponseEntity<?>) result).getBody() instanceof InputStreamResource)) {
//                            requestLogReq.setResponseResult(MultipleJSONObjectHelper.getStringfromObject(result));// log.info("result = {}", result);
//                        }
//                    } else {
//                        requestLogReq.setResponseResult(MultipleJSONObjectHelper.getStringfromObject(result));// log.info("result = {}", result);
//                    }
//
//                } catch(IOException ex){
//                    log.info("Error while convert data into string" + ex.getMessage());
//                    requestLogReq.setResponseResult("Error while convert data into string" + ex.getMessage());
//                }
//                requestLogReq.setResponseTime(stopWatch.getTotalTimeMillis());// log.info("EXECUTION TIME :::::: " + stopWatch.getTotalTimeMillis() + "ms");
//                if (clientLogs != null) {
//                    clientLogs.updateRequestLog(requestLogReq);
//                }
//            }
//            return result;
//        } catch (Exception e) {
//            requestLogReq.setErrorMessage(e.getMessage());
//            if (clientLogs != null) {
//                clientLogs.updateRequestLog(requestLogReq);
//            }
//            throw e;
//        }
//    }
//}

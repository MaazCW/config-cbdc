package com.opl.cbdc.config.utils;

import com.fasterxml.jackson.databind.*;
import com.opl.cbdc.utils.common.*;
import org.apache.poi.util.*;
import org.springframework.core.*;
import org.springframework.http.*;
import org.springframework.web.bind.support.*;
import org.springframework.web.context.request.*;
import org.springframework.web.method.support.*;

import javax.servlet.http.*;
import java.io.*;
import java.util.stream.*;

/**
 * @author sandip.bhetariya 28/01/2022
 */
public class RequestDataResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(Request.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Request attr = parameter.getParameterAnnotation(Request.class);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String body = null;
        if (request.getHeader("Content-Type") != null && request.getHeader("Content-Type").contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            StringBuilder requestBody = new StringBuilder();
            try (InputStream bounded = new BoundedInputStream(request.getInputStream(), 1_048_576);
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bounded));) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    requestBody.append(line);
                }
                body = String.valueOf(requestBody);
            } catch (Exception e) {
//                e.printStackTrace();
            }
        } else {
            body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        }
        Class<?> classDefined = attr.classT();
        String decryptText = null;
        if (request.getHeader("is_decrypt") != null && "false".equals(request.getHeader("is_decrypt"))) {
            decryptText = body;
        } else if (request.getHeader("req_auth") != null && "false".equals(request.getHeader("req_auth"))) {
            decryptText = body;
        } else {
			decryptText = AesGcmEncryptionUtils.decryptNew(body);
        }

        return new ObjectMapper().readValue(decryptText, classDefined);
    }

}

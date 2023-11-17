package com.opl.cbdc.config.utils;


import com.opl.cbdc.utils.common.*;
import com.opl.cbdc.common.api.auth.utils.*;
import org.springframework.core.convert.*;
import org.springframework.core.convert.converter.*;
import org.springframework.web.context.request.*;

import javax.servlet.http.*;
import java.nio.charset.*;
import java.util.*;

/**
 * @author sandip.bhetariya 28/01/2022
 */
public class PathVariableConverter implements GenericConverter {

	private static final Charset UTF_8 = StandardCharsets.UTF_8;

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {

		ConvertiblePair[] pairs = new ConvertiblePair[] { new ConvertiblePair(String.class, Long.class), new ConvertiblePair(String.class, Integer.class), new ConvertiblePair(String.class, Boolean.class),
				new ConvertiblePair(String.class, Double.class), new ConvertiblePair(String.class, String.class) };
		Set<ConvertiblePair> set = new HashSet<>(Arrays.asList(pairs));
		return set;
	}

	@Override
	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

			String auth = request.getHeader("req_auth");
			String is_decrypt = request.getHeader("is_decrypt");
			if ((auth != null && AuthCredentialUtils.REQUEST_HEADER_AUTHENTICATE_VALUE.equalsIgnoreCase(auth)) ||( is_decrypt != null && AuthCredentialUtils.REQUEST_HEADER_AUTHENTICATE_VALUE.equalsIgnoreCase(is_decrypt))) {
				if (targetType.getType() == Long.class) {
					return Long.valueOf(source.toString());
				} else if (targetType.getType() == Integer.class) {
					return Integer.valueOf(source.toString());
				} else if (targetType.getType() == Double.class) {
					return Double.valueOf(source.toString());
				} else if (targetType.getType() == Boolean.class) {
					return Boolean.valueOf(source.toString());
				} else if (targetType.getType() == String.class) {
					return source.toString();
				}
			}

			byte[] decodedBytes = source.toString().getBytes(StandardCharsets.UTF_8);
//			byte[] decodedBytes = Base64.getDecoder().decode(source.toString());
//			String decodedString = new String(source.toString());
//			String decryptText = EncryptionUtils.decryptText(decodedString);
//			decryptText = decryptText.replaceAll("\"", "");
			String decryptText = AesGcmEncryptionUtils.decryptNew(new String(decodedBytes,UTF_8));

			if (sourceType.getType() == String.class) {
				if (targetType.getType() == Long.class) {
					return Long.valueOf(decryptText);
				} else if (targetType.getType() == Integer.class) {
					return Integer.valueOf(decryptText);
				} else if (targetType.getType() == Double.class) {
					return Double.valueOf(decryptText);
				} else if (targetType.getType() == Boolean.class) {
					return Boolean.valueOf(decryptText);
				} else if (targetType.getType() == String.class) {
					return decryptText;
				}
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return source;
	}
}
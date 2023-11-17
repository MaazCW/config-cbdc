package com.opl.cbdc.config.utils;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.opl.cbdc.utils.common.*;
import org.apache.commons.io.*;
import org.jfree.util.*;
import org.json.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.http.converter.*;
import org.springframework.stereotype.*;
import org.springframework.util.StringUtils;
import org.springframework.util.*;
import org.springframework.web.context.request.*;

import javax.crypto.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.security.*;
import java.util.*;

/**
 * @author jaimin.darji 5/5/2021
 */


/**
 * @author sandip.bhetariya 28/01/2022
 */
@Component
public class RequestResponseConverter extends AbstractHttpMessageConverter<Object> {
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	private Charset charset = DEFAULT_CHARSET;

	@Autowired
	private ObjectMapper objectMapper;

	public RequestResponseConverter() {
		List<MediaType> mediaTypes = new ArrayList<>();
		mediaTypes.add(MediaType.APPLICATION_JSON);
//        mediaTypes.add(MediaType.TEXT_HTML); // adding text / html type of support
		setSupportedMediaTypes(mediaTypes);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return !clazz.getName().contains("java.util.List");
	}

	@Override
	public Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException {
		try {
			HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.
					currentRequestAttributes
					()).getRequest();
			ContentDisposition contentDisposition = inputMessage.getHeaders().getContentDisposition();
			if (contentDisposition.isFormData()) {
				return generateMultipart(inputMessage);
			}
			String is_decrypt =String.valueOf(servletRequest.getAttribute("is_decrypt"));
			Log.info(inputMessage.getBody());
			if ((inputMessage.getHeaders().containsKey("req_auth") && inputMessage.getHeaders().get("req_auth").contains("true")) 
					|| (inputMessage.getHeaders().containsKey("is_decrypt") && inputMessage.getHeaders().get("is_decrypt").contains("true"))||is_decrypt.equalsIgnoreCase("true")) {
				if (clazz.getName().contains("java.lang.String")) {
					return generateString(inputMessage.getBody());
				}
				return objectMapper.readValue(inputMessage.getBody(), clazz);
			}
			return objectMapper.readValue(decrypt(inputMessage.getBody(), clazz), clazz);

//            if (clazz.getName().equals("java.lang.String")) {
//                return generateString(inputMessage.getBody());
//            }
//            return objectMapper.readValue(inputMessage.getBody(), clazz);

		} catch (IOException e) {
			logger.error("IOException readInternal " + e);
			return inputMessage.getBody();
		}
	}

	@Override
	public void writeInternal(Object obj, HttpOutputMessage outputMessage) {
		
		try {
			if ((outputMessage.getHeaders().containsKey("req_auth") && outputMessage.getHeaders().get("req_auth").contains("true")) || (outputMessage.getHeaders().containsKey("is_decrypt") && outputMessage.getHeaders().get("is_decrypt").contains("true"))) {
				if (obj instanceof byte[]) {
					outputMessage.getBody().write((byte[]) obj);
				} else {
					outputMessage.getBody().write(objectMapper.writeValueAsBytes(obj));
				}
			} else {
				if (obj instanceof byte[]) {
					outputMessage.getBody().write((byte[]) obj);
				} else {
					outputMessage.getBody().write(encrypt(objectMapper.writeValueAsString(obj)));
				}
			}
		} catch (JsonProcessingException e) {
			logger.error("JsonProcessingException " + e);
		} catch (IOException e) {
			logger.error("IOException " + e);
		}

	}

	private String generateString(InputStream inputStream) throws IOException {
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		try {
			Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException" + e.getMessage());
		} catch (IOException e) {
			logger.error("IOException decrypt" + e.getMessage());
		} finally {
			inputStream.close();
		}
		return writer.toString().replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", "");
	}

	private InputStream decrypt(InputStream inputStream, Class clazz) throws IOException {
		// this is API request params
		String generateString = generateString(inputStream);
		try {
			JSONObject requestJsonObject = new JSONObject(generateString);
			if (requestJsonObject.has("data")) {
//				String decryptRequestString = EncryptionUtils.decryptText(requestJsonObject.getString("data"));

				String decryptRequestString = AesGcmEncryptionUtils.decryptNew(requestJsonObject.getString("data"));

				if (decryptRequestString != null) {
					if (clazz.getName().contains("java.lang.String")) {
						decryptRequestString = MultipleJSONObjectHelper.getStringfromObject(decryptRequestString);
					}
					InputStream byteArrayInputStream = IOUtils.toInputStream(decryptRequestString, StandardCharsets.UTF_8.name());
					return byteArrayInputStream;
				} else {
					return inputStream;
				}
			}
		} catch (JSONException err) {
			logger.error("Error JSONException :::::: " + err.getMessage());
		} catch (NoSuchAlgorithmException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException e) {
			logger.info("NoSuchAlgorithmException | BadPaddingException | DigestException | InvalidKeyException | " + "InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException :: " + e.getMessage());
		}
		return inputStream;
	}

	private byte[] encrypt(String dataToEncrypt) throws JsonProcessingException {
		// do your encryption here
		try {
//            stringFromObject = stringFromObject.replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t","");
//            String encrypt = EncryptionUtils.encryptText(stringFromObject.replaceAll("'", "\\\\'").replaceAll("\"", "\\\\\"").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t",""));
//            String encrypt = EncryptionUtils.encryptText(stringFromObject.replaceAll("'", "\\\\'").replaceAll("\"", "\\\\\"").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t",""));

			String encrypt2 = AesGcmEncryptionUtils.encryptNew(dataToEncrypt);
//			System.err.println("Response : " + encrypt2);

			Map<String, String> hashMap = new HashMap<>();
			hashMap.put("encData", encrypt2);
			JSONObject jsob = new JSONObject(hashMap);
			return jsob.toString().getBytes();
		} catch (Exception e) {
			logger.info("Error While Encrypt data :: " + e.getMessage());
			return dataToEncrypt.getBytes(StandardCharsets.UTF_8);
		}

	}

	public MultiValueMap<String, String> generateMultipart(HttpInputMessage inputMessage) throws IOException {
		MediaType contentType = inputMessage.getHeaders().getContentType();
		Charset charset = (contentType != null && contentType.getCharset() != null ? contentType.getCharset() : this.charset);
		String body = StreamUtils.copyToString(inputMessage.getBody(), charset);

		String[] pairs = StringUtils.tokenizeToStringArray(body, "&");
		MultiValueMap<String, String> result = new LinkedMultiValueMap<>(pairs.length);
		for (String pair : pairs) {
			int idx = pair.indexOf('=');
			if (idx == -1) {
				result.add(URLDecoder.decode(pair, charset.name()), null);
			} else {
				String name = URLDecoder.decode(pair.substring(0, idx), charset.name());
				String value = URLDecoder.decode(pair.substring(idx + 1), charset.name());
				result.add(name, value);
			}
		}
		return result;
	}

}

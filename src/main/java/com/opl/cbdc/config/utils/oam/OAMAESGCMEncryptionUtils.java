package com.opl.cbdc.config.utils.oam;

import com.fasterxml.jackson.databind.*;

import javax.crypto.*;
import javax.crypto.spec.*;
import javax.xml.bind.*;
import java.io.*;
import java.nio.charset.*;
import java.security.*;
import java.security.cert.*;
import java.security.spec.*;
import java.text.*;
import java.util.*;

public class OAMAESGCMEncryptionUtils {
	private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final String ALGO = "AES";
    private static final Charset UTF_8 = StandardCharsets.UTF_8;    
    
//    public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx89csv7W3bnmMI05J/VDu0xHsmziTiIINX6LAeDd15xfASacscuUm5gzjvOZrX/7N185JZ1e5AGCLbJu/BZHvvJGfTdtNoq2qAkZhbocfeIgp3e3xHVbKsI5rn1vjX6F09u7xCcUigOKCKVj3SHSCsoNnhOfTZxCEbEbjQGp4l1e3eZOCeuKuGPx5O09+RXx8+L8/IMQecQR9TsBT3573dEqZDP1SccFORbw5n+nflk95zYKJg9i3Ok0ouOANP6CiA36cZ/5puggegzcGiutbUxsz4891r3msHkeb2jyBDDrFFtmlScDl+7OFslfXt9vlWtijUnrm47UI8tScQitlQIDAQAB";
//    public static final String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCXY56hbAGp//h08GT8elWf6iXVbve0lJUh7upbiaJXgyVnpgKtG6pKjxiu9hb6bmPpYvCcgr2byYyCqn/PPAPBmxQWGlE4MZNfVgPLAajkkAMRK1eo0lAy5vS3XhdyroxahOSP2VQAnommsI2IIuXWETRhleks2ZFuko+ob02hVBD7luKO5NNFWHwitia7/22oMNkOLn0hibad3ujgBmwSZhoNAoXka2C/4ykuOtUxB3aeo0zuIsIEn8nlbG6/LHbPaOqK2Skur9j6HmISNrFUsSaUWLNPrYVLBK6EV2hnasXgd0be+lrUcKZpKo0DWvZqqmuCaP02cAtrPeqbe10vAgMBAAECggEAXHDWIldjRL0qp61K3OiOdFFzvLo/wyKfVdMfg7mtSaapJns2UyH7f/+U4PEf4teZZp+lsMwcn44YoIkf9hj9ajEZuM1lHt0W0jYhpspiqP5PASLoKgwSMKLad0WbJ8nxpixTrIrWQnKMNx7DrYcxtN9YjuYNNfe8WDpiQkWEr6c/flLopNigNwZZFxXre5bZysrI6aWGQj7XJF71KFGVtSvx4mOFYuCHZP+hBgrAmXoXY5Cyh+QPTX6ttFs9aFaSyLDIAtBQJZ0WHOv+6BXXroIhRzOizRbOj1nx3BUdYqWZKouuxygpOm6dWoXDviUJEs0ol3VH/XkGkHdcxPBCwQKBgQD9bQHX7J67JtvEbBhC4AFuXHu8nMfpyuJP5B/5Z7gq3Y4XGZL5YTZy1uLwJa4FDs1bX8PulWtVwN6Sic2Jc/QDR5J/G2RDq5W82zNWrxJUZw7goaxyvyweelk20o368DUCHYhXS14tLu8v8lMomSLDc5iYZ1rS9B4dRfxGfll7/wKBgQCY7UhYvop8Rk/iyEq8lYuBalvY+eZQWWxY0sII3wNbZ3kLwFBRz1vPLnEERaTcsjjFMZdwTk+dlt3D6+8HDDjG2fdzLbEwjQJ3xBDGmtsuccyzR23KavT2Ip0ay5lwlseonHSj15RyuAUd67jwtFGdCxDHheASKaVwI2bWvRre0QKBgQDSxBr5YoBs9Bdol+HhlH18NIxwRA/7f9GSUg7JlRpVBChNS+axYsku30qkISJGcJYX/WBth3VaP2qcguEfBQGWzKqo7bYIlaaWB9P9ruLWsVNtPOi4Egh2XJjgAGM2pRng5YDj4Pm1KEijHq1MoXeJCBWMAsFFmuOEwxTZ5/EyLwKBgBdE46E9PO0z9avYNicDyjpi6J7XR4gqUyYgy412AZjC4dQ76vzHxHx94Ix53GAIbkLzDMv13n9oGU5adABqh6kaOWC5LyDoE9wNZVAS+A7VGgSddcM7CxL4qVcOa8aJ6s9pHwZfIyNhhMXMvFND3Lc8m80APdwFojqx6E/8bj5BAoGBALtHFi0prDtg1AaOM7xHnSiH8o/D5oaDOB1P18leqI9sBkwObQhBJ4utAKJ9RRhu5UDsDjHKEwC1wG1cUZnRywYxaP+xVR3pQUmkV0W94Vf0rTjVVoBvgwXknecEU+s3P4xZPUcsNKuSpp5neEg/pq/IkLCcHCttNUxwml3+mQce";
//    public static final String CLIENT_ID = "oxsbwgomnsu9n25i2aq1qxvgbzgqrg26qnlsny0d2fnyqrqohrvaqbeu1ph3alhpau6zkhyeudz7qsizbz3ginhqr2kqyzbni1f8-65ffeebd-8994-47fb-bee2-691792bc0454";
//    public static final String SECRET_ID = "gn26bhbp3qffzikfi242zfn6rqrbp5rh0zgb1n2pbipewgrs2rbiq-18pq-5aseq7lkbrek4gbnibupux344ef0nsrpbodg4n3yahqh8he-r-lxgienzquv17x-fysguh4de3zp2a";
//    		
    public static String encrypt(String pText, String secretKey, String iv) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
    	Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
    	byte[] secBytes = secretKey.getBytes(UTF_8);
    	SecretKey secret = new SecretKeySpec(secBytes, 0, secBytes.length, ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, iv.getBytes()));
        byte[] encryptedText = cipher.doFinal(pText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedText);
    }

    public static String decrypt(String cText, String secretKey, String iv) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException  {
    	SecretKey secret = new SecretKeySpec(Base64.getDecoder().decode(secretKey), ALGO);
    	Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, iv.getBytes()));
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cText));
        return new String(plainText, UTF_8);
    }
	
	public static String decryptSecretKeyByPublicKey(String data, String publickeyPath) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchProviderException, InvalidKeySpecException, CertificateException, IOException  {
		byte[] encryptedData = Base64.getDecoder().decode(data);
		PublicKey publicKey = getPublicKey(publickeyPath);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] decryptedByte = cipher.doFinal(encryptedData);
		return new String(decryptedByte);
	}
	
	public static String encryptSecretKeyByPrivateKey(String data, String privatekeyPath) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
	    byte[] plaintext = data.getBytes();
	    PrivateKey privateKey = getPrivatekey(privatekeyPath);
	    Cipher cipher = Cipher.getInstance("RSA");
	    cipher.init(Cipher.ENCRYPT_MODE, privateKey);
	    byte[] encryptedByte = cipher.doFinal(plaintext);
	    return Base64.getEncoder().encodeToString(encryptedByte);
	}
	
	public static PrivateKey getPrivatekey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PrivateKey privateKey = null;
		KeyFactory keyFactory = null;
		byte[] encoded = DatatypeConverter.parseBase64Binary(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
		keyFactory = KeyFactory.getInstance("RSA");
		privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}
	
	public static PublicKey getPublicKey(String publickeyCert) throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publickeyCert)));
	    return publicKey;
	}
	
	public static String geenerateSecretKey() {
		return generateUUID().replaceAll("-", "");
	}
	
	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}
	
	public static String getBase64StringFromSecretKey(byte[] secretKey) {
		return Base64.getEncoder().encodeToString(secretKey);
	}
	
	public static SimpleDateFormat getPayloadTimestampFormat() {
		return new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	}
	
	public static <T> T getObjectFromString(String data, Class<?> clazz) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return (T) mapper.readValue(data, clazz);
	}
	
	public static String getStringfromObject(Object object) throws IOException {
		if (object != null) {
			return new ObjectMapper().writeValueAsString(object);
		} else {
			return "{}";
		}
	}
	
	/**
	 * To encrypt request pass String request in data field
	 * 
	 * Uncomment to check encryption
	 */
//	public static void main(String args[]) {
//		String data = "{\"portalId\":1,\"userId\":511,\"amount\":333,\"paymentReferenceId\":5151}";
//		String secretKey = geenerateSecretKey();
//		String base64StringFromSecretKey = getBase64StringFromSecretKey(secretKey.getBytes());
//		String timestamp = getPayloadTimestampFormat().format(new java.util.Date());
//		String iv = timestamp.substring(0, 16);
//		String encryptSecretKeyByPrivateKey = null;
//		try {
//			//encryptSecretKeyByPrivateKey = encryptSecretKeyByPrivateKey(base64StringFromSecretKey, "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCNpEaWuCBuMvQFWl40TWK/IT4tMmSkIgge5khVh9mo8vtQKxsjTBYRz7m6cKOkoAKZKOoy5+O/4gilKW/2U2e4EsXSq6wufDFnNPu9DStd+cJ2ivw8eNeUDRbQrMHLgbIsVQoJp+Bu35d2/cjlMndjFG50/z1osazaUe5pA5RKOHdIfXCXD/aZt2BjDGTgjuJEXcJvlE59XatXIzAuk/xHqoXExoQkEBJkQ+9bgGqW3kyzFbGTHtVDiwQDovBhNvPqdnouFZFvYRTnvy8aswaN61StN2NTgVnl/uJaQo3cTqspdjLkRuU4Bowxklb7BhBCuD6f2rVggAaXhs0/5GHJAgMBAAECggEADN4504OS3rAeX0YgfNvy9q5Mecq0Pf0hg70kITEgYysADf58jq5g6ulWVEvbch9fXQ7MiCdtF4evhWHIXDajm6OI+WTDVc3OpzO3EyvZHsaMFjz2QYAxBbBSvE6VpKl5yHFJYiswRqJlnlfcIoZ9NjEq/9IYgCR0Mblz0XKTzFJGO/SfWZNG00jvQnRfHBkp0y6A1H5GrOQZxVw7UBHtb4BUrT68WM2tpwumska75dmHqyH6v+61WUQOQgpyrLD27Ngj0oODfeMYICwM66ZF0KsKe/puR0tG2pXJN+e8oDB8kBxnm9CnUXwWgZyOmVzb2RCpx28k48KTAteFImhcCQKBgQDO4mWGs7w0B7C6jcDfUHbwpjD2E6xZEp+9B7ey7G1DXl8OgkzaJu29BjVYjxanB5TA2WwL91kfkf+UjHYoBUhF79XYRRuSrGv6/lOUmPINpsh405njwwOrbtBbqQf6fY8LOaWgTptPZhtiEIzojOZydxXhjjf0vqnAGmUfDqMw0wKBgQCvRLECpTatZF/MeFpUyOxDS8ero5OMtrncXGeXNxdD0F3yVVU4f5V1PVLg11vTW3VWVZfZ+1VZKqXbCcMRJVexbUu6pdhJK8HZXOacq2iuToxofXpQ99+I5IltxIk8b0watWJXlZKvRJKBrrD6QfJVDIljr6aFsDiIIFKceyThcwKBgCk9aA+Z9IJKpigJ6dCaMZt3BXuIIGsBE2aTC0NXbdBoF3rE7pzt9aYgfrHQlCtnj3eB38/i1pk8eT4bYdJIe0e1r2MVI5HxXPUDjZWph1bBUGUFiT+luC2MQWyEHVsJfGI4c5OpF7yUFhFOS0hwDd/zbSExQGZBO4VXvaCbQlyRAoGAaiyNopGdFtroYcBC0HvLDFVaxvJXzvVekEx2AJfCAzW1w7jH4GsBij00+LndiaMs6Lj/PXDQ2ixIvvEh90AM6JHSP2Z6KJFWZCuwFBaOWpGA40eoEmamRpJTYmidzjuJ+eAJjA8zBfPfYKoH0gKjH7xxei8NDI2dIXGbOeorhFsCgYAhshVGXBCUcB2X+N1xcq0vVdkf+c8zik7c+NpwRayItKPRjZR5157041YohpfvHkLfTmEJQ7tsR/oPPh/TbuqTM/Qj8phl6iLjIcIAjnb+i42m8iRQkhmyLxfXSBkYeJRlYKuCMlPPutYBowa72TU5Onh0HsSGMVAfCz2h+KKtNA==");
//			encryptSecretKeyByPrivateKey = encryptSecretKeyByPrivateKey(base64StringFromSecretKey, PRIVATE_KEY);
//			data = encrypt(data, secretKey, iv);
//			String encryptedString = getStringfromObject(new GatewayRequest(new GatewayMetaData(encryptSecretKeyByPrivateKey, "V1.0", timestamp, UUID.randomUUID().toString()), data));
//			System.out.println(encryptedString);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
	
	/**
	 * To decrypt request pass String response in data field
	 * 
	 * Uncomment to check decryption
	 */
//	public static void main(String args[]) {
//		String data = "{\"metadata\":{\"secretId\":\"nSTLVmSuSEWjB1V4LdluTYKb2WoFEWbauyI2qK3Jh0dc3wamDseVtm5zZXkcy5urGUWXz/CSAzB3rJTcm0fQCtNfs/yCrZsC6r7lVd6Vr9uU+kM7XlHzsJEmApMsS1Y+cqHW4qs5p0UWDTPKg8gII+4Znc3WCK/W1l45Wt8op4MyVpOOPTNUGHVXSG2iwpUilQ2UZWRa0KNOWH4hZemZYq8Ud1giJ0G5vIMa3nrKGBm4Joiu8NbovgfQHFZBsp4yLLvHXjwFRAz6pmRSn8JzQby5gPZTwJo1Wl8Ba12oChs9CQffkIXAiChdZAIiJU9Pk1HPZlRY4Y5FfKzEjdFUVw==\",\"version\":\"V1.0\",\"timestamp\":\"07-11-2022 04:07:14\",\"requestId\":\"acf523b0-9bd2-4aa7-b74f-0aa164490044\"},\"payload\":\"XdyAG+I+n0IJP+r6hA1CYiffXJcO6/Ujc8YSnv/G2/xxLMZ5OUEDVCYVV1d8KCyvU9+hkR8RD3Bx/kn3zjHGsNThA3p2eMifyNH5gRkWEroOvpWYECtdAn7H5qe7WQ84OttOHkzqC9ltWwWTTsnM7fbGCXEwfPXXf1onrBnnGpFPEQST2W0xmtPfYHz7883wcYA3Nb46EKgbSqKUsWnJx0ndDBnGBIcXAE6o2i/p2BVJzAG/lQUj0khxFIsA2bEY1r34pJFXGk+L1KGzk06TAXXkexEqm0waVmqDcBB+b2QhmqKoTWM//kJ/vE/ov5QWLsB7zZ52/S6KGzhXVkC2sBWesAlr5nJqQ+s44yy4LPmuus91CvRTy68dZKV7ue/XMyuNUw==\"}";
//		try {
//			GatewayRequest objectFromString = getObjectFromString(data, GatewayRequest.class);
//			String decSecretKey = AESGCMEncryptionUtils.decryptSecretKeyByPublicKey(objectFromString.getMetadata().getSecretId(), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx89csv7W3bnmMI05J/VDu0xHsmziTiIINX6LAeDd15xfASacscuUm5gzjvOZrX/7N185JZ1e5AGCLbJu/BZHvvJGfTdtNoq2qAkZhbocfeIgp3e3xHVbKsI5rn1vjX6F09u7xCcUigOKCKVj3SHSCsoNnhOfTZxCEbEbjQGp4l1e3eZOCeuKuGPx5O09+RXx8+L8/IMQecQR9TsBT3573dEqZDP1SccFORbw5n+nflk95zYKJg9i3Ok0ouOANP6CiA36cZ/5puggegzcGiutbUxsz4891r3msHkeb2jyBDDrFFtmlScDl+7OFslfXt9vlWtijUnrm47UI8tScQitlQIDAQAB");
//			String decrypt = AESGCMEncryptionUtils.decrypt(objectFromString.getPayload().toString(), decSecretKey, objectFromString.getMetadata().getTimestamp().toString().substring(0, 16));
//			System.out.println("decrypt :: " + decrypt);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
}

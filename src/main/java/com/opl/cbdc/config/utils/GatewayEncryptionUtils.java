package com.opl.cbdc.config.utils;

import com.opl.cbdc.utils.common.*;
import com.opl.cbdc.config.utils.namematch.models.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

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

@Slf4j
@Component
@Transactional
@Service
public class GatewayEncryptionUtils {

    @Autowired
    private AnsProperties ansProperties;

    public static String RSA_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCLqeqlz7//KTvXiLr9vO0cY63eSdRalaK/wGf/w83nh6FOnvBcQdJ2D3Xk0Um+1Q0hQJB7YhVfP3JbEEmH/jVJSvKNzRzcxO6A3J9XKgbfAkTLXwnLXkdKW8IC8U3S1L83uqTay021nB7pQSJ3iz3keXBDhJy2ItRCjuHZhD2XyrGJRvqY1S75tBKGbYWpcWDI+J1KFd1gYU1RjA/G/YBTI5sWpWqFxLX7x74ftSdpUY1QwAq7XfPj7sOk6DHR38CZ6yad2ktF0ClacKU412nufpzzQRoCDa7CvX2tGxtI8INFeHezNg+3/dS7RUo3wrmP02Ddz0EcvF5U2jZu4gcHAgMBAAECggEAcwqYdNJmpShy2XwQAvf/G9ZjnmmUGLWPJIG5aK0By0gmL9utw2CYmpTrmHpoU1+s/AnxuSA22IkriiUlXHahr+ijWPhuYM96YfzY53R/ymxU/GwhZjoXYQGqEsBvSU3i4GXGa07xiLm7oIj9ehjg93Ssa4EtkL2pt9V35bMPBRtBr5O8Z8pPhMtdidpoWVXOa9PCDxXgtTsKN3Vq6PrANNg76m626WcV50+8OTC0VpQzkrZd5Hc9notgUVcWLsH3/9Y/TOM3F/nlLjNDcB6FYMWrxlGhk/GQGsbyKgraPRnWIPqVF+hTbISxF1b9rEKvMxh6Chtx9R1W4CbYZn2hYQKBgQDMBL0kXqJP0sSU5MNZP0bWLXtuW7V60hsmK6lu1hG0hV1PKgRp7fT60GcFZqOOxhYNaxflkClP1gDLIPrYLsRVMBMydrPHAZn1bXmYpMCxSaymfyqbCdQ0GuppimBrv6ttSGjFDvt1EXQ4kxSnvCjApKbHtpGG+M/U9rvxtGsy8QKBgQCvP5bOkiUQyd6mRXsQLQm9lVjSBtxnpMbx87WdF+AZ0zAp0KWRRL3jr2mtV4fjhVlbIYceDmQktR32e3XZHlyd8WAcsZLlULs1H/59zV7R4DidgOFoO43ZFNCmof8ZvlXJdZAWtbYoHLMAhrY392B2Ce3bnOhp/TsNxfIumrDpdwKBgFx0+VJ2IJCUL8paMb1MU8nUKs/Ed+YFOZB+0PYNxVBN8A9kN0PaNt9DeqyT6yNU2cGS+u1p+47WqN1lQMifyRHiC5EfAjH5o8/5oNVwNHMjtD5v+trwBV7no7vYngG2hvWrju9e7s4K7uSEoyOwvbcv5vUy45iLQYR9nIbZAIvBAoGAQdcUI3vIrSwKmFFWUNd6510qx42IVPAVRmWXf/OuYEV1vOAEtDUtWlNW0qcmCJba4WARUXt6qEfbf/UWa4H6reQ3hdA5LipMi0L/6+eyjgs9ef8nsG9fdYVadqA6JUPbGxsuDToru5o8rh/uacxo0zAbyvyCXP+kpiIOQPAhMzUCgYAJLAahZCcmD/UMc7mMVEVnuJWvXniw4YKq/8lY459HeGku/qKs8dX+Xj2Bw2KzgFs6MwbWoNqeSyUrF5NG6SxtHt1TYvEX0eG54+cgKV+JRFhqzMjY7Yp5w/xZK45zL1BostvYEI7vwLbSPfKEqV4AZttz2GyYGwbHVQ+VywOwpw==";
//    public static String RSA_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDTbBMlz3stIevXJHJRnT5T467hckxMv8wZf1/Xcz5uDn5/66NXzQfvtW/jxw1Yc0qA6ElLOvCg3LcLBKnpN6BeXVeHGYTh6CkFHFhwDHTYO0ezzl2c6X+d8uMn1MoX/kHbF+Qb+ut+lA9PD9mV/sByUKjpjxve6+QNo2QU7PHyMfuFs2pKXmM4GoFcZ4rIvmDoM5jAT7lfBZMLMeK/c2fvy/JT9lRyurud4qOl0IwmJce/+hKNev7P2RnsLXp6CLHGEhhmrE6SRExnkc7FEaXJ4UtiEbYOZRPf7vIOLaOlbKZ4R0Up+rdkFXuddRooGpsdcWzEY8AtqxU9/XUrDb7vAgMBAAECggEABCRl66O5AhVcDYcFRlwSUIpxbrFSlV5cnVNFiW8Ulgtqpw7y0FGQCj4/xvKrbEQ1/XiPlw28kSpB29Fnks4oMjEZ+eeiFtr5ksQPZ4KW93WR3g/4/Y4Flg6gLLOakgXFPImnSnUMEBrOgfoJhUrMcnDqHklTfnaYrTu6Rj6uLtSKv0QNAAJr1geq1DSH/FnUL/UmAqymO4bLUej9a3GONezch4mPI5H8v0feWENSvOznuo/Kn4OPrll5RDiisBxTpIOGkrpwrtOC9DdrCYWfMElHHi/7Ajxqb0J2IrPEcbuYb2dRR4QnHxoJiA8w9VQjxwPunMVQctY2+DuStA+SAQKBgQDpI9VabBs8gahat/b+cSASvl+8K1mt+4vaT7zVXI+LrWBvp3U/Ho+s+aUBKx1g8iWc6hX6tFUdk40v3UxrDOj1khgx62sC1kP6kxSqBkY9n7aTdTlLWEf2ETxn2qglnKTRND/ui0DwIR5Xr4De8XXMSdqls7/gGR0qm1iyXesSrwKBgQDoJxdt9k2MtCcDk9HOXAqouRzXCN3wPP2KVhypnIPUY02DZE/m2SCgVSorn9ml+EI65Fd+3X9cd+Ef82J5WJXQxUnVSM9en0CQFwGtuqz1vZzhgJBnFFWbFLMPJy3vx2CFtutKF6lNZDrNYXLVB180R0kYRUpPs4Zg+dzMccQnwQKBgCMSe+8dYj+I9c2n7SU72WpYb0h8cQJGXDScWDj0srd9fottcqrrKwwHhjYVyVajNttTL7lGVaOcZdn0faGBEK6YqsEfvDpyB0nzxGELjpeIpkuqj9Lw1eqDj0XEVBVMalL6fD+jfUJiiREB2u4u/tybo6PX1Z6dyJB3TDGRBWMLAoGBAOF2/dFE8cIiQPW/fXdeetesmK7bJLkqcs8EnOe5Y2tFWkWuic9YttAghklCAR6owqHxnGxQ8F28676dINlbSH0u7msfQI7UOd73CSFR+KLdmVyFATmrTQzFiLXBxmJmj36tzUvzOPE508Ydy2nLtBHQ3dOdj/NiFrNeV6uTHf/BAoGBAOP2n3ZnhtDyH7I9p2IWloQDuod7Ks1LyfWnjdDKZnMM5+21z7O8fmYK9TXdlVSZ7N2gBHibVKC1I86LKsIhdC5dKgjbDN8SADaffOgxHeb3Y9b+ADNIHwJqZYlYux/69lMQ1bIES6mpPhXnP2XZXJlwumTsB0hFLr5P7FsTAkUA";
//	public static String RSA_KEY = "682q9zyxus888bzfhoodvfzytdthd3lemav94lnfb26qil21dhe2d69e9gygc2gcx6z--gn-fiyyq3ijkx1dfhe6iy416th26dehyz78nlxg8gtxl4141chvihxczfc3yg4f9-fq0";

    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 16;
    private static final int AES_KEY_BIT = 256;

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    public static byte[] encrypt(byte[] pText, SecretKey secret, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        byte[] encryptedText = cipher.doFinal(pText);
        return encryptedText;
    }

//    public static byte[] encryptWithPrefixIV(byte[] pText, SecretKey secret, byte[] iv) throws Exception {
//        byte[] cipherText = encrypt(pText, secret, iv);
//        byte[] cipherTextWithIv = ByteBuffer.allocate(iv.length + cipherText.length)
//                .put(iv)
//                .put(cipherText)
//                .array();
//        return cipherTextWithIv;
//    }

    public static String decrypt(byte[] cText, SecretKey secret, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        byte[] plainText = cipher.doFinal(cText);
        return new String(plainText, UTF_8);
    }

//    public static String decryptWithPrefixIV(byte[] cText, SecretKey secret) throws Exception {
//        ByteBuffer bb = ByteBuffer.wrap(cText);
//        byte[] iv = new byte[IV_LENGTH_BYTE];
//        bb.get(iv);
//        byte[] cipherText = new byte[bb.remaining()];
//        bb.get(cipherText);
//        String plainText = decrypt(cipherText, secret, iv);
//        return plainText;
//    }

//    public static byte[] getRandomNonce() {
//        byte[] nonce = new byte[IV_LENGTH_BYTE];
//        new SecureRandom().nextBytes(nonce);
//        return nonce;
//    }

    public static SecretKey getAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(AES_KEY_BIT, SecureRandom.getInstanceStrong());
        return keyGen.generateKey();
    }

//    public static String decryptSecretKey(String data, String privatekeyPath) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchProviderException, InvalidKeySpecException {
//        byte[] encryptedData = Base64.getDecoder().decode(data);
//        PrivateKey privateKey = getPrivatekey(privatekeyPath);
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.DECRYPT_MODE, privateKey);
//        byte[] encryptedByte = cipher.doFinal(encryptedData);
//        return new String(encryptedByte);
//    }

    public static String decryptSecretKeyByPublicKey(String data, String publickeyPath) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchProviderException, InvalidKeySpecException, CertificateException, IOException {
        byte[] encryptedData = Base64.getDecoder().decode(data);
        PublicKey publicKey = getPublicKey(publickeyPath);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedByte = cipher.doFinal(encryptedData);
        return new String(decryptedByte);
    }

//    public static String encryptSecretKey(String data, String publickeyPath) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, CertificateException, IOException {
//        byte[] plaintext = data.getBytes();
//        PublicKey publicKey = getPublicKey(publickeyPath);
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//        byte[] encryptedByte = cipher.doFinal(plaintext);
//        return Base64.getEncoder().encodeToString(encryptedByte);
//    }

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

//    public static String getPublicKeyStr(String publickeyCert) throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publickeyCert)));
//        return new String(publicKey.getEncoded());
//    }

    public static GatewayRequest prepareReqForGateway(String plainStringReq) {
        try {
            SecretKey sKey = getAESKey();
            String sKeyEnc = Base64.getEncoder().encodeToString(sKey.getEncoded());
//			String timestamp = CommonUtils.getPayloadTimestampFormat().format(new Date());
            String timestamp = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date());
            String iv = timestamp.substring(0, 16);

            byte[] encResponse = encrypt(plainStringReq.getBytes(), sKey, iv.getBytes());
            String encBase64 = Base64.getEncoder().encodeToString(encResponse);
            String encryptedSkey = encryptSecretKeyByPrivateKey(sKeyEnc, GatewayEncryptionUtils.RSA_KEY);
            GatewayRequest gatewayRequest = new GatewayRequest(new GatewayMetaData(encryptedSkey, "V1.0", timestamp, UUID.randomUUID().toString().replaceFirst("-", "").replaceAll("-", "")), encBase64);
            return gatewayRequest;
//			return new GatewayRequest(new GatewayMetaData(encryptedSkey, "V1.0", timestamp, CommonUtils.generateUUID().replaceAll("-", "")), encBase64);
        } catch (Exception e) {
            //log.info("Error while encrypt gateway req :: [{}]", e.getMessage());
//            e.printStackTrace();
            return null;
        }
    }

    public NameMatchResponse decryptContent(GatewayRequest gatewayRequest) {
        try {
            String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx89csv7W3bnmMI05J/VDu0xHsmziTiIINX6LAeDd15xfASacscuUm5gzjvOZrX/7N185JZ1e5AGCLbJu/BZHvvJGfTdtNoq2qAkZhbocfeIgp3e3xHVbKsI5rn1vjX6F09u7xCcUigOKCKVj3SHSCsoNnhOfTZxCEbEbjQGp4l1e3eZOCeuKuGPx5O09+RXx8+L8/IMQecQR9TsBT3573dEqZDP1SccFORbw5n+nflk95zYKJg9i3Ok0ouOANP6CiA36cZ/5puggegzcGiutbUxsz4891r3msHkeb2jyBDDrFFtmlScDl+7OFslfXt9vlWtijUnrm47UI8tScQitlQIDAQAB";
//            String publicKey = ansProperties.getValue(AnsProperties.NAME_MATCH_ALGO_PUBLIC_KEY);
//            String secretId = "B1ifhQKPFmu+B9VVIhadIIW0iFLqLAmc7/wjjuewj56m2IjI7BRQzY/+x+t91FkplLVH5wRrCq7n6G6zJVwkZeOuSTpAdbWochCzntkdDKWpjcCOm7MpHssQsFtBC1sTCuLYGl8UI/apj3ktUB7LHhjMhXXgpZqXZO+5IpYB5tgsh3XOHIMjpsb701N6wxQocghutnF9QR5QPSwS5EoJ9jrHHDCQrJYO9HaZ0TI3RPvZJrP8XG+4PmjoV6Xt4k9IhXdSm/nG1JyG4D/Nb6qUQtsNlD3Q3lzGHTUgMSD1sSnDuUnQXO+WoVA8sH/+dT1BCmvis1t732c20PrOJruVvg==";
//            String payLoad = "GJKvfYFaJFRxfoUd9lXTyGwPEjpy+PaeJt4A1SQF+ikNF2AZDAScXjd9sV/+vkkfsqp/mcSPmfvmg49x+PytlRNRWB75HWiytXQwqHLfRQ1mZWQ/Zglh/j9Cb98UjBls0dmhaSEOxulc2aN7OIh34dJWRYmukaCdEtuZcuyr49ExwyRoi7zHbjVvtNhHqYBksg==";
//            String timeStamp = "04-01-2022 06:35:45";


            String decryptSecretKeyByPublicKey = GatewayEncryptionUtils.decryptSecretKeyByPublicKey(gatewayRequest.getMetadata().getSecretId(), publicKey);
            String decrypt = GatewayEncryptionUtils.decrypt(Base64.getDecoder().decode(gatewayRequest.getPayload()), new SecretKeySpec(Base64.getDecoder().decode(decryptSecretKeyByPublicKey), "AES"), gatewayRequest.getMetadata().getTimestamp().substring(0, 16).getBytes());
//            log.info("Decrypted JSON :>>>>: {}", decrypt);
            log.info("Decrypted JSON :>>>>: {}");
            if(!OPLUtils.isObjectNullOrEmpty(decrypt)) {
                NameMatchResponse nameMatchResponse = MultipleJSONObjectHelper.getObjectFromString(decrypt, NameMatchResponse.class);
//                log.info("NameMatchResponse :>>>>: {}", nameMatchResponse.toString());
                log.info("NameMatchResponse :>>>>: {}");
//                NameMatchResponse nameMatchResponse = MultipleJSONObjectHelper.getObjectFromString(response.getPayload(), NameMatchResponse.class);
                return nameMatchResponse;
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return null;
    }

    public static String encryptContent(String json) {
        try {
//            String json = "{\"inputName\":\"anaaya\",\"matchWith\":\"ayaana\"}";
            GatewayRequest prepareReqForGateway = prepareReqForGateway(json);
            String stringfromObject = MultipleJSONObjectHelper.getStringfromObject(prepareReqForGateway);
//            log.info("encrypted value :: {}", stringfromObject);
            return stringfromObject;
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return null;
    }

    public static void main(String args[]) throws CertificateException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, Exception {
//        String encrypted = encryptContent("{\"inputName\":\"anaaya\",\"matchWith\":\"ayaana\"}");
//        GatewayRequest gatewayRequest = MultipleJSONObjectHelper.getObjectFromString(encrypted, GatewayRequest.class);
//        GatewayEncryptionUtils gatewayEncryptionUtils = new GatewayEncryptionUtils();
//        gatewayEncryptionUtils.decryptContent(gatewayRequest);
    }

//    public static void gnrtKey() {
//        try {
//            FileInputStream fin = new FileInputStream("C:/Users/hitesh.suthar.crt/Downloads/Hitesh_cert.crt");
//            CertificateFactory f = CertificateFactory.getInstance("X.509");
//            X509Certificate certificate = (X509Certificate) f.generateCertificate(fin);
//            PublicKey pk = certificate.getPublicKey();
//            log.info("EEEEEEE");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}

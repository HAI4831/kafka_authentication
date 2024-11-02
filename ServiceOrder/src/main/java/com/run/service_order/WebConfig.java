//package com.run.servicea;
//
//import lombok.extern.slf4j.Slf4j;
//import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
//import org.bouncycastle.openssl.jcajce.JceOpenSSLPKCS8DecryptorProviderBuilder;
//import org.bouncycastle.operator.InputDecryptorProvider;
//import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
//import org.bouncycastle.pkcs.PKCSException;
//import org.bouncycastle.util.io.pem.PemObject;
//import org.bouncycastle.util.io.pem.PemReader;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
//import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
//
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.security.KeyFactory;
//import java.security.PrivateKey;
//import java.security.Security;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
//
//@Configuration
//@Slf4j
//public class WebConfig {
//
//    @Value("${jwt.password}")
//    private String privateKeyPassword;
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public ReactiveJwtDecoder customReactiveJwtDecoder() throws Exception {
//        return NimbusReactiveJwtDecoder.withPublicKey(publicKey()).build();
//    }
////    @Bean
////    public CustomUserCreationRequestConverter customUserCreationRequestConverter() {
////        return new CustomUserCreationRequestConverter(new ObjectMapper());
////    }
////@Override
////public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
////    converters.add(customUserCreationRequestConverter());
////}
//
////    @Bean
////    public PrivateKey privateKey() throws Exception {
////        // Đảm bảo Bouncy Castle được đăng ký
////        Security.   addProvider(new BouncyCastleProvider());
////
////        ClassPathResource resource = new ClassPathResource("private_key.pem");
////
////        try (PemReader pemReader = new PemReader(new InputStreamReader(resource.getInputStream()))) {
////            PemObject pemObject = pemReader.readPemObject();
////
////            if (pemObject == null || pemObject.getContent().length == 0) {
////                throw new IllegalArgumentException("Tệp PEM trống hoặc không hợp lệ.");
////            }
////
////            PKCS8EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new PKCS8EncryptedPrivateKeyInfo(pemObject.getContent());
////
////            InputDecryptorProvider decryptorProvider = new JceOpenSSLPKCS8DecryptorProviderBuilder()
////                    .setProvider("BC")
////                    .build(privateKeyPassword.toCharArray());
////
////            PrivateKeyInfo privateKeyInfo = encryptedPrivateKeyInfo.decryptPrivateKeyInfo(decryptorProvider);
////
////            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
////
////            return converter.getPrivateKey(privateKeyInfo);
////        } catch (IndexOutOfBoundsException ex) {
////            throw new Exception("Lỗi xử lý khóa riêng: " + ex.getMessage(), ex);
////        } catch (IOException | PKCSException e) {
////            throw new Exception("Lỗi đọc tệp hoặc giải mã khóa: " + e.getMessage(), e);
////        }
////    }
//@Bean
//public RSAPrivateKey privateKey() throws Exception {
//    log.debug("Loading RSA private key from PEM file.");
//    // Ensure Bouncy Castle is registered
//    Security.addProvider(new BouncyCastleProvider());
//
//    ClassPathResource resource = new ClassPathResource("private_key.pem");
//
//    try (PemReader pemReader = new PemReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
//        PemObject pemObject = pemReader.readPemObject();
//
//        if (pemObject == null || pemObject.getContent().length == 0) {
//            throw new IllegalArgumentException("PEM file is empty or invalid.");
//        }
//
//        PKCS8EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new PKCS8EncryptedPrivateKeyInfo(pemObject.getContent());
//
//        InputDecryptorProvider decryptorProvider = new JceOpenSSLPKCS8DecryptorProviderBuilder()
//                .setProvider("BC")
//                .build(privateKeyPassword.toCharArray());
//
//        PrivateKeyInfo privateKeyInfo = encryptedPrivateKeyInfo.decryptPrivateKeyInfo(decryptorProvider);
//
//        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
//
//        PrivateKey privateKey = converter.getPrivateKey(privateKeyInfo);
//
//        if (privateKey instanceof RSAPrivateKey) {
//            return (RSAPrivateKey) privateKey;
//        } else {
//            throw new IllegalArgumentException("The private key is not an instance of RSAPrivateKey.");
//        }
//    } catch (IOException | PKCSException e) {
//        log.error("Error loading RSA private key: {}", e.getMessage());
//        throw new Exception("Error processing private key: " + e.getMessage(), e);
//    }
//}
//    @Bean
//    public RSAPublicKey publicKey() throws Exception {
//        log.debug("Loading RSA public key from PEM file.");
//        ClassPathResource resource = new ClassPathResource("public_key.pem");
//
//        try {
//            // Read the file content
//            byte[] keyBytes = Files.readAllBytes(resource.getFile().toPath());
//
//            // Convert the key content to a format suitable for key generation
//            String publicKeyContent = new String(keyBytes)
//                    .replace("-----BEGIN PUBLIC KEY-----", "")
//                    .replace("-----END PUBLIC KEY-----", "")
//                    .replaceAll("\\s+", "");
//
//            // Decode the key from Base64
//            byte[] decodedKey = Base64.getDecoder().decode(publicKeyContent);
//
//            // Create a key specification and generate the public key
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
////            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
//            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
//            return rsaPublicKey;
//        } catch (IOException e) {
//            throw new Exception("Error reading public key file: " + e.getMessage(), e);
//        } catch (Exception e) {
//            log.error("Error loading RSA public key: {}", e.getMessage());
//            throw new Exception("Error processing public key: " + e.getMessage(), e);
//        }
//    }
//}

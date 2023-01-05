package com.hseea.advance.oauthservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class RsaUtils {
    private static final Logger logger = LoggerFactory.getLogger(RsaUtils.class);
    /*
     *  密钥长度
     * */
    private final static int KEY_SIZE = 512;

    public static KeyPair genKeyPair(){
        return genKeyPair(KEY_SIZE);
    }

    public static KeyPair genKeyPair(int length){
        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(length, new SecureRandom());
            return keyPairGenerator.genKeyPair();
        }catch (Exception e){
            logger.error("get keypair exception: ", e);
        }
        return null;
    }

    public static void main(String[] args) {
        KeyPair keyPair = genKeyPair(1024);
        assert keyPair != null;
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKey = Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded());;
        logger.info("pub: {}", publicKey);
        logger.info("pri: {}", privateKey);
    }
}

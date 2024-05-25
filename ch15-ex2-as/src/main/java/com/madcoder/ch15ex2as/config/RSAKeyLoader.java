package com.madcoder.ch15ex2as.config;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class RSAKeyLoader {

    @Value("${jwt.rsa-key.store.path}")
    private String RSAKeyStorePath;

    @Value("${jwt.rsa-key.store.password}")
    private String RSAKeyStorePassword;

    @Value("${jwt.rsa-key.alias}")
    private String RSAKeyAlias;

    @Value("${jwt.rsa-key.password}")
    private String RSAKeyPassword;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    @PostConstruct
    public void init()
        throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, CertificateException {
        // KeyStore 파일 로드
        KeyStore keystore = KeyStore.getInstance("JKS");

        Resource resource = new ClassPathResource(RSAKeyStorePath);
        keystore.load(resource.getInputStream(), RSAKeyStorePassword.toCharArray());

        // 비밀키 및 공개키 로드
        Key key = keystore.getKey(RSAKeyAlias, RSAKeyPassword.toCharArray());
        if (key instanceof PrivateKey) {
            privateKey = (PrivateKey) key;
        }
        publicKey = keystore.getCertificate(RSAKeyAlias).getPublicKey();
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}

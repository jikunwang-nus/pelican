package com.charsmart.data.cases.utils;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * @Author: Wonder
 * @Date: Created on 2022/5/11 12:16 PM
 */
public class RSAUtils {
    public static void main(String[] args) {
        String path = "/Users/jeff/tls/ca/wonder.local.crt";
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(path));
//            byte[] decode = Base64.getDecoder().decode(bytes);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            CertificateFactory instance = CertificateFactory.getInstance("X.509");
            Certificate certificate = instance.generateCertificate(inputStream);
            System.out.println(certificate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

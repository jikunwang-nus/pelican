package com.charsmart.data.cases.tool;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: Wonder
 * @Date: Created on 2022/4/26 下午10:44
 */
public class HMACUtil {
    public static String hmacWithJava(String algorithm, String data, String key)
            throws NoSuchAlgorithmException, InvalidKeyException {
        System.out.println(algorithm);
        System.out.println(data);
        System.out.println(key);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(secretKeySpec);
        return Hex.encodeHexString(mac.doFinal(data.getBytes()));
    }
}

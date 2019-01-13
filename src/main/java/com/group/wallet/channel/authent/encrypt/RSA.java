package com.group.wallet.channel.authent.encrypt;

import com.group.wallet.channel.authent.common.Toolkit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

import javax.crypto.Cipher;


public class RSA {

    public static void main(String[] args) throws IOException {
    }

    public static RSAPublicKey getPublicKey(String keyPath) throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        FileInputStream in1 = new FileInputStream(keyPath);
        java.security.cert.Certificate cert = cf.generateCertificate(in1);
        in1.close();
        java.security.interfaces.RSAPublicKey pbk = (java.security.interfaces.RSAPublicKey) cert.getPublicKey();
        return pbk;
    }

    /**
     *
     * @param pubKey base64
     * @return
     * @throws Exception
     */
    public static RSAPublicKey toPublicKey(String pubKey64) throws Exception {
        byte[] key = Toolkit.base64Decode(pubKey64);
        KeyFactory rsaKeyFac = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
        RSAPublicKey pbk = (RSAPublicKey) rsaKeyFac.generatePublic(keySpec);
        return pbk;
    }

    public static RSAPrivateKey getPrivateKey(String keyPath, String passwd) throws Exception {

        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            FileInputStream fis = new FileInputStream(keyPath);
            // If the keystore password is empty(""), then we have to set
            // to null, otherwise it won't work!!!
            char[] nPassword = null;
            if ((passwd == null) || passwd.trim().equals("")) {
                nPassword = null;
            } else {
                nPassword = passwd.toCharArray();
            }
            ks.load(fis, nPassword);
            fis.close();

            // Now we loop all the aliases, we need the alias to get keys.
            // It seems that this value is the "Friendly name" field in the
            // detals tab <-- Certificate window <-- view <-- Certificate
            // Button <-- Content tab <-- Internet Options <-- Tools menu            //
            //In MS IE 6.
            Enumeration enumq = ks.aliases();
            String keyAlias = null;
            if (enumq.hasMoreElements()) // we are readin just one certificate.
            {
                keyAlias = (String) enumq.nextElement();
            }
            // Now once we know the alias, we could get the keys.
//            ToolKit.writeLog(RSA.class.getName(), "getPrivateKey.isKeyEntry", ks.isKeyEntry(keyAlias) + "");
            PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
//            ToolKit.writeLog(RSA.class.getName(), "getPrivateKey.base64", Formatter.base64Encode(prikey.getEncoded()));
            return (RSAPrivateKey) prikey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**  从公钥证书文件读取公钥
     *
     * @param certPath 证书路径
     * @return
     */
    public static String getCertFromFile(String certPath) {
        File file = new File(certPath);
        StringBuilder sb = new StringBuilder();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                if (!tempString.contains("-----")) {// && !tempString.contains("-----END CERTIFICATE-----")) {
                    sb.append(tempString);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     *  从签名证书读取公钥信息
     * @param keyPath
     * @param passwd
     * @return
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String keyPath, String passwd) throws Exception {

        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            FileInputStream fis = new FileInputStream(keyPath);
            // If the keystore password is empty(""), then we have to set
            // to null, otherwise it won't work!!!
            char[] nPassword = null;
            if ((passwd == null) || passwd.trim().equals("")) {
                nPassword = null;
            } else {
                nPassword = passwd.toCharArray();
            }
            ks.load(fis, nPassword);
            fis.close();

            // Now we loop all the aliases, we need the alias to get keys.
            // It seems that this value is the "Friendly name" field in the
            // detals tab <-- Certificate window <-- view <-- Certificate
            // Button <-- Content tab <-- Internet Options <-- Tools menu            //
            //In MS IE 6.
            Enumeration enumq = ks.aliases();
            String keyAlias = null;
            if (enumq.hasMoreElements()) // we are readin just one certificate.
            {
                keyAlias = (String) enumq.nextElement();
            }
            // Now once we know the alias, we could get the keys.
//            ToolKit.writeLog(RSA.class.getName(), "getPublicKey.isKeyEntry", ks.isKeyEntry(keyAlias) + "");
            Certificate cert = ks.getCertificate(keyAlias);
            PublicKey pubkey = cert.getPublicKey();
            return (RSAPublicKey) pubkey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encrypt(byte[] data, String keyPath) {

        try {

            RSAPublicKey pbk = getPublicKey(keyPath);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, pbk);

            byte[] encDate = cipher.doFinal(data);

            return Toolkit.base64Encode(encDate);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static byte[] encrypt64(byte[] data, String pubKey64) {

        try {
            RSAPublicKey pbk = toPublicKey(pubKey64);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, pbk);

            byte[] encDate = cipher.doFinal(data);
            return encDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt(byte[] data, String keyPath, String keyPasswd) {
        try {
            RSAPrivateKey pbk = getPrivateKey(keyPath, keyPasswd);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            cipher.init(Cipher.DECRYPT_MODE, pbk);

            byte[] btSrc = cipher.doFinal(data);
            return btSrc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] sign(byte[] data, String keyPath, String keyPasswd) {
        try {
            RSAPrivateKey pbk = getPrivateKey(keyPath, keyPasswd);

            // 用私钥对信息生成数字签名
            java.security.Signature signet = java.security.Signature.getInstance("MD5withRSA");
            signet.initSign(pbk);
            signet.update(data);
            byte[] signed = signet.sign(); // 对信息的数字签名
            return signed;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean verify(byte[] data, String pubKey64, String value) {
        try {
            RSAPublicKey pbk = toPublicKey(pubKey64);
            java.security.Signature signetcheck = java.security.Signature.getInstance("MD5withRSA");
            signetcheck.initVerify(pbk);
            signetcheck.update(value.getBytes("UTF-8"));
            if (signetcheck.verify(data)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] encryptByPrivateKey(byte[] data, String keyPath, String keyPasswd)
            throws Exception {
        RSAPrivateKey pbk = getPrivateKey(keyPath, keyPasswd);
        // 对数据加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, pbk);
        return cipher.doFinal(data);
    }

    public static byte[] decryptByPublicKey(byte[] data, String pubKey64)
            throws Exception {
        RSAPublicKey pbk = toPublicKey(pubKey64);
        // 对数据加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
        cipher.init(Cipher.DECRYPT_MODE, pbk);

        return cipher.doFinal(data);
    }

    /** 银联UPOP密钥加密算法
     *
     * @param passwd 密码
     * @param pubKey base64
     * @return 加密后的密文
     */
    public static String encryptPin(String passwd, String pubKey) throws IOException {

        String pinStr = passwd;
        String pinLen = String.valueOf(pinStr.length());
        pinStr = Toolkit.bytePadLeft(pinLen, '0', 2) + pinStr;
        if (pinStr.length() % 2 == 1) {
            pinStr += "0";
        }
        byte[] pin = Toolkit.str2Bcd(pinStr);

        byte[] pinblock = new byte[8];
        System.arraycopy(pin, 0, pinblock, 0, pin.length);
        for (int i = pin.length; i < 8; i++) {
            pinblock[i] = (byte) 0xFF;
        }

        return Toolkit.base64Encode(RSA.encrypt64(pinblock, pubKey));
    }

    /** 银联UPOP密钥加密算法
     *
     * @param passwd 密码
     * @param orderNo 订单号
     * @param pubKey base64
     * @return 加密后的密文
     */
    public static String encryptPin(String passwd, String orderNo, String pubKey) throws IOException {

        if(Toolkit.isNullOrEmpty(passwd)){
            return "";
        }
        int len = passwd.length();
        String pinStr = Toolkit.padLeft(len + "", 2) + passwd + orderNo;
        return Toolkit.base64Encode(RSA.encrypt64(pinStr.getBytes("UTF-8"), pubKey));
    }
}

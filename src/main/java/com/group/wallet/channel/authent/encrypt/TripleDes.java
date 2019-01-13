package com.group.wallet.channel.authent.encrypt;


import com.group.wallet.channel.authent.common.Toolkit;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public class TripleDes {

    private static final String Algorithm = "DESede"; //���� �����㷨,���� DES,DESede,Blowfish

    //keybyteΪ������Կ������Ϊ24�ֽ�
    //srcΪ�����ܵ����ݻ�������Դ��
    public static byte[] encrypt(byte[] keybyte, byte[] src) {
        try {
            //������Կ
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            //����
            Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            byte[] bts = c1.doFinal(src);
            return bts;
        } catch (Exception e3) {
        	e3.printStackTrace();
        }
        return null;
    }

    //keybyteΪ������Կ������Ϊ24�ֽ�
    //srcΪ���ܺ�Ļ�����
    public static byte[] decrypt(byte[] keybyte, byte[] src) {
        try {
            //������Կ
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            //����
            Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            c1.init(Cipher.DECRYPT_MODE, deskey);
            byte[] bts = c1.doFinal(src);
            return bts;
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        //����°�ȫ�㷨,�����JCE��Ҫ������ӽ�ȥ
        Security.addProvider(new com.sun.crypto.provider.SunJCE());

        byte[] keyBytes = {0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10, 0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD, 0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36, (byte) 0xE2};    //24�ֽڵ���Կ

        keyBytes = Toolkit.random(24).getBytes();
        String szSrc = "This is a 3DES test. ����";

        System.out.println("����ǰ���ַ���:" + szSrc);

        byte[] encoded = encrypt(keyBytes, szSrc.getBytes());
        System.out.println("���ܺ���ַ���:" + new String(encoded));

        byte[] srcBytes = decrypt(keyBytes, encoded);
        System.out.println("���ܺ���ַ���:" + (new String(srcBytes)));
    }
}
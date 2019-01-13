package com.group.wallet.channel.authent.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class Toolkit {

    public static final String GDYILIAN_CERT_PFX = "GDYILIAN_CERT_PFX";

    public static final String GDYILIAN_CERT_PFX_PASSWD = "GDYILIAN_CERT_PFX_PASSWD";

    /**
     * 平台CA证书公钥
     */
    public static final String GDYILIAN_CERT_PUB_64 = "GDYILIAN_CERT_PUB_64";

    /**
     * 交易报文版本号（报文加密方式）
     */
    public static final String GDYILIAN_CERT_METHOD = "GDYILIAN_CERT_METHOD";

    private static final char[] HEXCHAR = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static SimpleDateFormat HHmmss = null;
    private static SimpleDateFormat yyyyMMddHHmmss = null;

    /**
     * 格式化日期时间：yyyyMMddHHmmss
     * @param time
     * @return
     */
    public static String yyyyMMddHHmmss(Date time) {
        if (yyyyMMddHHmmss == null) {
            yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
        }
        return yyyyMMddHHmmss.format(time);
    }

    /**
     * 格式化日期时间：HHmmss
     * @param date
     * @return
     */
    public static String HHmmss(Date date) {
        if (HHmmss == null) {
            HHmmss = new SimpleDateFormat("HHmmss");
        }
        return HHmmss.format(date);
    }

    /**
     * 从属性文件中获取键对应的值
     * @param filename
     * @param key
     * @return
     */
    public synchronized static String getPropertyFromFile(String filename, String key) {
        ResourceBundle rb = ResourceBundle.getBundle(filename);
        return rb.getString(key).trim();
    }

    /**
     * 从默认属性文件systemSetting.properties中获取键对应的值
     * @param key
     * @return
     */
    public synchronized static String getPropertyFromFile(String key) {
        ResourceBundle rb = ResourceBundle.getBundle("systemSetting");
        return rb.getString(key).trim();
    }

    /**
     * 随机生成十六进制字符串
     * @param len 生产的字符串长度
     * @return
     */
    public static String random(int len) {
        String str = "";
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < len; i++) {
            str += HEXCHAR[random.nextInt(16)];
        }
        return str;
    }

    /**
     * base64解码
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] base64Decode(String data) throws IOException {
        if(Toolkit.isNullOrEmpty(data))
            return null;
        return new sun.misc.BASE64Decoder().decodeBuffer(data);
    }

    /**
     * base64编码
     * @param data
     * @return
     * @throws IOException
     */
    public static String base64Encode(byte[] data) throws IOException {
        if(data == null)
            return "";
        return new sun.misc.BASE64Encoder().encode(data);
    }

    /**
     * 是否为空白字符串
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str){
        return str == null || "".equals(str.trim());
    }

    /**
     * 是否为空
     * @param o
     * @return
     */
    public static boolean isNullOrEmpty(Object o){
        return o == null || "".equals(o.toString().trim());
    }

    /**
     * 对字符串左填充字符至指定（字节）长度
     * @param input
     * @param c
     * @param length
     * @return
     */
    public static String bytePadLeft(String input, char c, int length) {
        String output = input;
        while (output.getBytes().length < length) {
            output = c + output;
        }
        return output;
    }

    /**
     * 对字符串右填充字符至指定（字节）长度
     * @param input
     * @param c
     * @param length
     * @return
     */
    public static String bytePadRight(String input, char c, int length) {
        String output = input;
        while (output.getBytes().length < length) {
            output = output + c;
        }
        return output;
    }

    /**
     * 对字符串左填充字符至指定长度
     * @param input
     * @param c
     * @param length
     * @return
     */
    public static String padLeft(String input, char c, int length) {
        String output = input;
        while (output.length() < length) {
            output = c + output;
        }
        return output;
    }

    /**
     * 对字符串右填充字符至指定长度
     * @param input
     * @param c
     * @param length
     * @return
     */
    public static String padRight(String input, char c, int length) {
        String output = input;
        while (output.length() < length) {
            output = output + c;
        }
        return output;
    }

    /**
     * 对字符串右填充空格字符至指定长度
     * @param input
     * @param length
     * @return
     */
    public static String padRight(String input, int length) {
        return padRight(input, ' ', length);
    }

    /**
     * 对字符串左填充字符0至指定长度
     * @param input
     * @param length
     * @return
     */
    public static String padLeft(String input, int length) {
        return padLeft(input, '0', length);
    }

    /**
     * 10进制串转为BCD码(字节数组)，输入：0123456789，输出：00000001 00100011 01000101 01100111 10001001
     * 输入的必须是0-9 a-f A-F这22个字符
     * 左补‘0’
     * @param str
     * @return
     */
    public static byte[] str2Bcd(String str) {
        return str2Bcd(str,true);
    }

    /**
     * 10进制串转为BCD码(字节数组)，输入：0123456789，输出：00000001 00100011 01000101 01100111 10001001
     * 输入的必须是0-9 a-f A-F这22个字符
     * 如果不是左补‘0’，就是右补‘F’
     * @param str
     * @param leftAdd0
     * @return
     */
    public static byte[] str2Bcd(String str, boolean leftAdd0) {
        if(leftAdd0) {
            while(str.length()%2 != 0) str = "0" + str;
        } else {
            while(str.length()%2 != 0) str += "F";
        }

        byte[] temp = str.getBytes();
        byte[] result = new byte[temp.length/2];

        for(int i = 0;i < result.length;i++) {

            byte h = byte2OByte(temp[2*i]);
            byte l = byte2OByte(temp[2*i+1]);

            result[i] = (byte)((h << 4) + l);
        }

        return result;
    }

    private static byte byte2OByte(byte c) {

        if(c >= '0' && c <= '9') {
            c = (byte)(c - '0');
        } else if(c >= 'a' && c <= 'z') {
            c = (byte)(c - 'a' + 0x0A);
        } else if(c >= 'A' && c <= 'Z') {
            c = (byte)(c - 'A' + 0x0A);
        }

        return c;
    }

    public static String toString(Object o) {
        if (o == null) {
            return "";
        }
        return o.toString().trim();
    }

    /**
     * @param logLevel      日志等级，例如：InfoLevel.info
     * @param processName   日志名称，一般可这样获得： this.getClass().getPackage().getName()   或者 this.getClass().getName()。另参见 log4j.properties
     * @param threadName    线程名称， 这样获取：Thread.currentThread().getName()
     * @param eventMessage  与流程相关的消息
     */
    public synchronized static void writeLog(LogLevel logLevel, String processName,
                                             String threadName, String eventMessage) {

        System.out.println(processName + " " + threadName + " " + eventMessage);

        Logger logger = Logger.getLogger(processName);
        if (processName != null)
            processName = processName.replace(" ", ",");
        if (threadName != null)
            threadName = threadName.replace(" ", ",");

        eventMessage = threadName + " " + eventMessage;

        switch (logLevel) {
            case DEBUG:
                logger.debug(eventMessage);
                break;
            case INFO:
                logger.info(eventMessage);
                break;
            case WARN:
                logger.warn(eventMessage);
                break;
            case EXCEPTION:
                logger.error(eventMessage);
                break;
            case ERROR:
                logger.fatal(eventMessage);
                break;
            default:
                logger.info(eventMessage);
        }
    }

    public synchronized static void writeLog(String processName, String threadName,
                                             String message, Exception eventMessage) {

        String exStr = "";
        if (eventMessage != null) {
            exStr = "" + eventMessage.getMessage();
            for (StackTraceElement ste : eventMessage.getStackTrace()) {
                exStr = exStr + " " + ste.toString();
            }
        }

        writeLog(LogLevel.EXCEPTION, processName, threadName, exStr);
    }

    public synchronized static void writeLog(String processName, String message, Exception eventMessage) {
        String exStr = "";
        if (eventMessage != null) {
            exStr = "" + eventMessage.getMessage();
            for (StackTraceElement ste : eventMessage.getStackTrace()) {
                exStr = exStr + " " + ste.toString();
            }
        }
        writeLog(LogLevel.EXCEPTION, processName, Thread.currentThread().getName(), exStr);
    }

    public synchronized static void writeLog(LogLevel logLevel, String processName, String eventMessage) {
        writeLog(logLevel, processName, Thread.currentThread().getName(), eventMessage);
    }

    public synchronized static void writeLog(String processName, String eventMessage) {
        writeLog(LogLevel.INFO, processName, Thread.currentThread().getName(), eventMessage);
    }

    public synchronized static void writeLog(String processName, String threadName, String eventMessage) {
        writeLog(LogLevel.INFO, processName, threadName, eventMessage);
    }

    public static Integer toInt(LogLevel level) {
        if (level == LogLevel.DEBUG)
            return 0;
        else if (level == LogLevel.INFO)
            return 1;
        else if (level == LogLevel.WARN)
            return 2;
        else if (level == LogLevel.EXCEPTION)
            return 3;
        else
            return 4;
    }

}

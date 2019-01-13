package com.group.wallet.channel.quick.yibaoPay.utils;


import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 资质修改
 * @author sailfish
 * @create 2017-08-24-上午10:20
 */
public class CustomerPictureUpdateRequester {

    private static String MainCustomerNumber = "10021204560";// 代理商编码
    private static String MailStr = "1328338666@qq.com";// 商户邮箱


    public static void main(String[] args) {
        String result = "";  //返回结果

//        String filePath = "/Users/yeson/Desktop/develop/workspace/skb-test-demo/src/main/resources/resource/person.jpg";
        String filePath = "/Users/yeson/Desktop/develop/workspace/skb-test-demo/src/main/resources/resource/2.png";

        File file = new File(filePath);

        PostMethod postMethod = new PostMethod("https://skb.yeepay.com/skb-app" + "/customerPictureUpdate.action");
        HttpClient client = new HttpClient();

        try {
            String key = "n1634g5ZK2SXI79M3m90PyK7KCp3M9AB83859Q3Y38Dv7lLx950z3s6B46q8"; // 商户秘钥
            String customerNumber = "";//商户编号

            StringBuffer signature = new StringBuffer();
            signature
                    .append(MainCustomerNumber == null ? ""
                            : MainCustomerNumber)
                    .append(customerNumber == null ? ""
                            : customerNumber)
            ;

            System.out.println("source===" + signature.toString());
            String hmac = Digest.hmacSign(signature.toString(), key);
            System.out.println("hmac====" + hmac);

            Part[] parts = new CustomerPicturePartsBuilder()
                    .setMainCustomerNumber(MainCustomerNumber)
                    .setCustomerNumber(customerNumber)

                    /**
                     * 单张照片最大512k
                     * 需要传什么照片,就传入哪张,最少一张照片
                     */
                    
                    //身份证正面照
                    .setIdCardPhoto(file)
                    //身份证背面照
                    .setIdCardBackPhoto(file)
                    //银行卡正面照
                    .setBankCardPhoto(file)
                    //手持身份证,银行卡和本人合照
                    .setPersonPhoto(file)
                    
                    
                    .setHmac(hmac)
                    .generateParams();

            postMethod.setRequestEntity(new MultipartRequestEntity(parts,
                    postMethod.getParams()));
            System.out.println(postMethod.toString());

            int status = client.executeMethod(postMethod);
            if (status == HttpStatus.SC_OK) {
                System.out.println(postMethod.getResponseBodyAsString());
                result = postMethod.getResponseBodyAsString();

            } else if (status == HttpStatus.SC_MOVED_PERMANENTLY
                    || status == HttpStatus.SC_MOVED_TEMPORARILY) {
                // 从头中取出转向的地址
                Header locationHeader = postMethod
                        .getResponseHeader("location");
                String location = null;
                if (locationHeader != null) {
                    location = locationHeader.getValue();
                    System.out.println("The page was redirected to:" + location);
                } else {
                    System.err.println("Location field value is null.");
                }
            } else {
                System.out.println("fail======" + status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放连接
            postMethod.releaseConnection();
        }
    }
}

class CustomerPicturePartsBuilder {

    private List<Part> parts = new ArrayList<Part>(26);

    public Part[] generateParams() {
        return parts.toArray(new Part[parts.size()]);
    }

    /**
     * @param mainCustomerNumber
     *            the mainCustomerNumber to set
     */
    public CustomerPicturePartsBuilder setMainCustomerNumber(String mainCustomerNumber) {
        this.parts.add(new StringPart("mainCustomerNumber",
                mainCustomerNumber == null ? "" : mainCustomerNumber, "UTF-8"));
        return this;
    }

    /**
     * @param customerNumber
     *            the mainCustomerNumber to set
     */
    public CustomerPicturePartsBuilder setCustomerNumber(String customerNumber) {
        this.parts.add(new StringPart("customerNumber",
                customerNumber == null ? "" : customerNumber, "UTF-8"));
        return this;
    }

    /**
     * @param hmac
     *            the hmac to set
     */
    public CustomerPicturePartsBuilder setHmac(String hmac) {
        this.parts
                .add(new StringPart("hmac", hmac == null ? "" : hmac, "UTF-8"));
        return this;
    }

    // [end] jun.lin 2015-03-30 这里是普通入参

    // [start] jun.lin 2015-03-20 这里是文件入参

    private void configFilePart(File f, FilePart fp) {
        String fileName = f.getName();
        fp.setContentType("image/"
                + fileName.substring(fileName.lastIndexOf('.') + 1));
        fp.setCharSet("UTF-8");

        System.out.println(fp.getContentType());
    }

    private void configPdfFilePart(File f, FilePart fp) {
        String fileName = f.getName();
        fp.setContentType("application/"
                + fileName.substring(fileName.lastIndexOf('.') + 1));
        fp.setCharSet("UTF-8");

        System.out.println(fp.getContentType());
    }

    /**
     * 身份证正面照
     * @param idCardPhoto
     *            the idCardPhoto to set
     * @throws FileNotFoundException
     */
    public CustomerPicturePartsBuilder setIdCardPhoto(File idCardPhoto)
            throws FileNotFoundException {
        FilePart fp = new FilePart("idCardPhoto", idCardPhoto);
        configFilePart(idCardPhoto, fp);
        this.parts.add(fp);

        return this;
    }

    /**
     * 身份证背面照
     * @param idCardBackPhoto
     * @return
     * @throws FileNotFoundException
     */
    public CustomerPicturePartsBuilder setIdCardBackPhoto(File idCardBackPhoto)
            throws FileNotFoundException {
        FilePart fp = new FilePart("idCardBackPhoto", idCardBackPhoto);
        configFilePart(idCardBackPhoto, fp);
        this.parts.add(fp);

        return this;
    }

    /**
     * 银行卡正面照
     * @param bankCardPhoto
     *            the bankCardPhoto to set
     * @throws FileNotFoundException
     */
    public CustomerPicturePartsBuilder setBankCardPhoto(File bankCardPhoto)
            throws FileNotFoundException {
        FilePart fp = new FilePart("bankCardPhoto", bankCardPhoto);
        configFilePart(bankCardPhoto, fp);
        this.parts.add(fp);
        return this;
    }

    /**
     * 身份证,银行卡和本人合照
     * @param personPhoto
     *            the personPhoto to set
     * @throws FileNotFoundException
     */
    public CustomerPicturePartsBuilder setPersonPhoto(File personPhoto)
            throws FileNotFoundException {
        FilePart fp = new FilePart("personPhoto", personPhoto);
        configFilePart(personPhoto, fp);
        this.parts.add(fp);
        return this;
    }

    /**
     * 营业执照
     * @param businessLicensePhoto
     *            the businessLicensePhoto to set
     * @throws FileNotFoundException
     */
    public CustomerPicturePartsBuilder setBusinessLicensePhoto(
            File businessLicensePhoto) throws FileNotFoundException {
        FilePart fp = new FilePart("businessLicensePhoto", businessLicensePhoto);
        configFilePart(businessLicensePhoto, fp);
        this.parts.add(fp);
        return this;
    }

    /**
     * 电子协议
     * @param electronicAgreement
     * @return
     * @throws FileNotFoundException
     */
    public CustomerPicturePartsBuilder setElectronicAgreement(File electronicAgreement)
            throws FileNotFoundException {
        FilePart fp = new FilePart("electronicAgreement", electronicAgreement);
        configPdfFilePart(electronicAgreement, fp);
        this.parts.add(fp);
        return this;
    }
}

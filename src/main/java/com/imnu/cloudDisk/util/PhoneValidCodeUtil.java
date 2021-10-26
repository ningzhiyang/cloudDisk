package com.imnu.cloudDisk.util;

import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/8/21 <br>
 */
public class PhoneValidCodeUtil {
    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    public static void yzm(String code,String phoneNumber) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = PhoneValidCodeUtil.createClient("LTAI5tQG7WDUMj4i7JMR4DNA", "FIlSVvSrXjeaHANGYwuIYRR1L1mofa");
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phoneNumber)
                .setSignName("阿洋网盘")
                .setTemplateCode("SMS_222462399")
                .setTemplateParam("{\"code\":\""+code+"\"}");
        // 复制代码运行请自行打印 API 的返回值
        SendSmsResponse sendResp = client.sendSms(sendSmsRequest);
        String codea = sendResp.body.code;
        System.out.println(codea);
    }

}

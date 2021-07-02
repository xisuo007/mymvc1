package com.king.java8.assert0;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.king.util.OrderGenerater;
import com.king.util.rsa.RSAUtil;
import org.apache.catalina.Executor;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.Executors;


/**
 * Created by ljq on 2019/8/28 17:55
 */
public class Hao {

    public static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCFZnUVz07wuQfI5kf3uOaaJcpq*W3yQhJnIX2k-EKwKZaSkyuXutk0TXqwT-" +
                                            "GXxIQJqmkjLup*HN7H1uF7JMfxl00AnncHB82LqUQKQwf5wcdDTNhvKLQtjRoLE3ry6ARoYHu5AkZPKW7sMM4o*UegPlSr45p4ZsK0iVdjqmgZfwIDAQAB";
    public static final String PRIVATEKEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKOoelzwAU5Asw9zknkTYGvfZr0Ap6ZDL6NMSNRYZ2maVJd5xOfSRqTkEq1Ne*h2Qe3wCKdxo0SuCVWNjM-nd3af*fb4YcWdlDuHaA1s28I5hZtVp2sbF*nvgdeUwSz-X0hQGcaqVzcTKDH9l2XuMC**OEofyyosU2jvEIGdwqSNAgMBAAECgYAkojvxvc*tApKSbN5mt82nl-RZbmIYt4VcWmEbF0bevqsc1SccdVdW5a7AmE2aNY6AgnCNesR-RS3Vtr-Ech2tVfwMXypJsXN5hq0uyM6iDkE6kFhGL1zui72u9RQJvdB7CsNfEONIaFlX46MUOdF0fR2n-sGLMc1qzpj*L3k6QQJBAOJfQRF6ehE5d1Sm*7q9uObte1ubako89TSGZmCOk-3vpm9CRTey-18Ids98yMNg3Wy53M4oEzjwjdnnulX9PpUCQQC5E-NySYbigVCsO5Tjr*iAA1ykdGIgaRM45s2tvbMLYQdZYhnkPRjSj*Y7I915cp5klQ75T260InPYQqBkb2gZAkEAjRYtKcWZ*s5EL4B7eCHy8gqlTa0JjAd*FCSH-joexq-snX9CQLrRKtvNoPf28L6YgsE8e0jC4kQbROqGWj2iGQJBAKkXVUCBdL7UrsPs26b6PE1YxPdrbYt29Jz0Ic4ulro6t*AuBMHGIDugRRSbO*mNkrEKjlew-s*M*pIGrUuVjWECQQC3qMemXCmqp7lAaSqYy9Rk8HNVgEeDqJfhcIS4SrRH0DSExPE9yfhadaiC4IIYmmK5L*2V3dxIUI7KXbeO*ptz";

    public static void main(String[] args) throws Exception{
        Hao test = new Hao();
        //test.rsaTest();
        //test.hutoolRsaTest();
        tempFile();
    }

    private void orderNoTest(){
        String orderNo = OrderGenerater.generateOrderNo();
        System.out.println(orderNo);
    }

    private void rsaTest(){
        String str = "床前明月光，疑是地上霜，举头望明月，低头思故乡!";
        String encrypt = RSAUtil.encrypt(str, PUBLICKEY);
        System.out.println(encrypt);
        String decrypt = RSAUtil.decrypt(encrypt, PRIVATEKEY);
        System.out.println(decrypt);
    }

    private void hutoolRsaTest(){
        RSA rsa = new RSA();
        rsa.getPublicKey();
        rsa.getPublicKeyBase64();
        rsa.getPrivateKey();
        rsa.getPrivateKeyBase64();

        byte[] encrypt = rsa.encrypt(StrUtil.bytes("床前明月光，疑是地上霜，举头望明月，低头思故乡!", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        String string = Base64.encodeBase64String(encrypt);
        System.out.println(string);
        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
        String str = StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8);
        System.out.println(str);
    }

    /**
     * 临时文件用完既删测试
     */
    private static void tempFile() throws Exception{
        File testFile = File.createTempFile("testFile", ".pdf");
        FileInputStream in = new FileInputStream(new File("d:/test.pdf"));
        ContentBody cd = new InputStreamBody(in,"tempnamefile");
        FileOutputStream out = new FileOutputStream(testFile);
        cd.writeTo(out);
        String absolutePath = testFile.getAbsolutePath();
        System.out.println(absolutePath);
        System.out.println(testFile.getPath());
        in.close();
        out.close();
        testFile.deleteOnExit();
    }
}

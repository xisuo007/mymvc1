package com.king.lianxi;

import com.king.util.rsa.RSAUtil;

/**
 * @Author ljq
 * @Date 2019/10/24 17:46
 */
public class RSATest {
    public static void main(String[] args){
        String data = "TvM8P2A3XU9sio5VjorlMIsBqgrkQFmdrQAHOr7GliwPNMGPbpXIDd4sX6OKdF+c78I3Vw86vTEi3m1E4SMWy+7fxQ89sEGjM9au2Q+vflNaf2y3yaiojpTIWNyIvDBNzfjq9ktMU31Kk3lscgdzlpKWYbSqSRtJ4xZKwmIBkrE=";
        String text = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAL7cP6WlT4vbj5Pw8KmLdo1FJPvlXZrN2P8+HswzmodebQgWD3JWfBg14rSyXJhZ5bjKyi3ioNybii9Amts+j1ApuRa++GSZEhQDiPr9JJ6gCZumWNgOthTM7o4evozF7wB75UpiHh0DwRyjzzVwruFyN5ER8nftWv3D3X7V2U5dAgMBAAECgYEAj/beY3oQSHRzFqqVPa4e3GydewwF0TEYT19yJ6F9HELTh1fsithFfpWWzaoEQj79EqiUXIwnvn/sebwHkvzbZ9SmjXzPjL54IsWLz+4JHuZhBIN495Lc51KTZmglcA8Kr9dqdSUB5JSbreYXkNNj5fB2rPxR3x9oOJThDvXJoVkCQQDht5Sp0YyqmJk2jPJilvQmAWLeF7xkUYUGpNSyqGlU/u74/ZvvkoS32TSmTJqLUdtzPWIp+J1Ny1+9v+djGn8nAkEA2Hd8ngcXKZQp9CcPcLcMcs2dq2wluS40shZuHfZsanNE9zHQ0MfNv2XuN1Zinr5ydy4l+8D+4JufExr+JXI42wJAAesag1r5lBWZ78HA6Z+Y0Z3fZj3TZxLeS5EpEZ+mzYchJtKY+a9IG3voG6W5Bogakl8qL2sigx/AX496+velrwJBANK7mBTcAD+ZHzKyKATqHvNQmtM8iIj7o49P6MnAkZEcQKKJ26i1KubO25cBwSabbyH5Sj8JhtewiK930r0vsosCQQDERu0aTyPunGzfCDsc4rUFQ5PR+Ys/zLmxHYJHD+67IlsLOXI1aiF/p/6kIlZHqVVwUe0JvTugILXMEY15LXUa";
        String decrypt = RSAUtil.decrypt(data, text);
        System.out.println(decrypt);
    }
}

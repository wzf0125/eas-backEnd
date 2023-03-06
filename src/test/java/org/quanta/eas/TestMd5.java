package org.quanta.eas;

import org.junit.jupiter.api.Test;
import org.quanta.eas.utils.MD5Utils;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/11
 */
public class TestMd5 {
    @Test
    void testMd5(){
        String salt = MD5Utils.getSalt();
        System.out.println(salt);
        String password = "123456";
        System.out.println(MD5Utils.md5(password,salt));
    }
}

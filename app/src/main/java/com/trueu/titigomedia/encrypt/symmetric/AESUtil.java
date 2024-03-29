/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-09-07 17:30:16
 *
 * GitHub: https://github.com/GcsSloop
 * WeiBo: http://weibo.com/GcsSloop
 * WebSite: http://www.gcssloop.com
 */

package com.trueu.titigomedia.encrypt.symmetric;

import android.annotation.SuppressLint;

import com.trueu.titigomedia.encrypt.base.BaseUtils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import androidx.annotation.IntDef;


/**
 * AES 工具类
 */
public class AESUtil {
    private final static String SHA1PRNG = "SHA1PRNG";

    @IntDef({Cipher.ENCRYPT_MODE, Cipher.DECRYPT_MODE})
    @interface AESType {
    }

    /**
     * Aes加密/解密
     *
     * @param content  字符串
     * @param password 密钥
     * @param type     加密：{@link Cipher#ENCRYPT_MODE}，解密：{@link Cipher#DECRYPT_MODE}
     * @return 加密/解密结果字符串
     */
    @SuppressLint("DeletedProvider")
    public static String aes(String content, String password, @AESType int type) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");

            SecureRandom secureRandom;
            if (android.os.Build.VERSION.SDK_INT >= 24) {
                secureRandom = SecureRandom.getInstance(SHA1PRNG);
            } else if (android.os.Build.VERSION.SDK_INT >= 17) {
                secureRandom = SecureRandom.getInstance(SHA1PRNG, new CryptoProvider());
            } else {
                secureRandom = SecureRandom.getInstance(SHA1PRNG);
            }
            secureRandom.setSeed(password.getBytes());
            generator.init(128, secureRandom);
            SecretKey secretKey = generator.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/ZeroBytePadding");
            cipher.init(type, key);

            if (type == Cipher.ENCRYPT_MODE) {
                byte[] byteContent = content.getBytes("UTF-8");
                return BaseUtils.parseByte2HexStr(cipher.doFinal(byteContent));
            } else {
                byte[] byteContent = BaseUtils.parseHexStr2Byte(content);
                return new String(cipher.doFinal(byteContent),"UTF-8");
            }
        } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException | InvalidKeyException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    static final class CryptoProvider extends Provider {
        /**
         * Creates a Provider and puts parameters
         */
        public CryptoProvider() {
            super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
            put("SecureRandom.SHA1PRNG",
                    "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
    }
}

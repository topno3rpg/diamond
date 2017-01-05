package com.taobao.something;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.Test;

/**
 * Created by Admin on 2016/8/5.
 */
public class JdbcPassword {

    @Test
    public void encryptPassword() throws Exception {
        System.out.println(ConfigTools.encrypt("dtHLRHBqB2ai5aRtkhOz"));
        // C66J/kMf2tMh19D847AgzPLbKVJips90B5lJDlda31Q52S+tIwQnveLNztGlqlbfmb07Bb5TFTCmZ7/fYMMHcA==
//        System.out.println(ConfigTools.decrypt("MbCY4VVd3zttNK1OI0rLa8XvtaL9Ab7sbdDsrTz6kCv1IOPzStXWiL0LaMWTSGJOdthMHDckzjWp20ufGLoGPw=="));
    }

}

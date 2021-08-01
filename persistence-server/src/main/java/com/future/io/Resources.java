package com.future.io;

import java.io.InputStream;

/**
 * 功能描述:
 *
 * @author future
 * @date 2021-07-31 14:28
 */
public class Resources {

    /**
     * 功能描述: 根据配置文件路径 将配置文件加载成字节输入流 存储在内存中
     *
     * @author future
     * @date 2021/7/31 2:29 下午
     */
    public static InputStream getResourceAsStream(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }

}

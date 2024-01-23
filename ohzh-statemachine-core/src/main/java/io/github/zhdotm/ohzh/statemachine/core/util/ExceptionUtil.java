package io.github.zhdotm.ohzh.statemachine.core.util;

import lombok.SneakyThrows;

/**
 * 异常工具
 *
 * @author zhihao.mao
 */

public class ExceptionUtil {

    @SneakyThrows
    public static void isTrue(Boolean isSuccess, Class<? extends Exception> clazz, String format, Object... args) {
        if (!isSuccess) {
            throw clazz.getConstructor(String.class).newInstance(String.format(format, args));
        }
    }

}

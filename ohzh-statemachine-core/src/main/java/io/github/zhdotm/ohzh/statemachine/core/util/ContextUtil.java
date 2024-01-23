package io.github.zhdotm.ohzh.statemachine.core.util;

import cn.hutool.core.util.SystemPropsUtil;
import io.github.zhdotm.ohzh.statemachine.core.enums.ContextEnum;
import lombok.SneakyThrows;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author zhihao.mao
 */

public class ContextUtil {

    public static String getAppName() {

        return SystemPropsUtil.get(ContextEnum.APP_NAME.getCode());
    }

    @SneakyThrows
    public static String getIp() {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                if (!inetAddress.isLinkLocalAddress() && !inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) {

                    return inetAddress.getHostAddress();
                }
            }
        }

        return null;
    }

}

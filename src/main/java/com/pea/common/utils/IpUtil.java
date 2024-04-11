package com.pea.common.utils;

import cn.hutool.core.io.IoUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

/**
 * description: ip 地址工具类
 */
@SuppressWarnings("ALL")
@Slf4j
public class IpUtil {

    private static final String IP_V4 = "127.0.0.1";

    private static final String IP_V6 = "0:0:0:0:0:0:0:1";

    private static final String UNKNOWN = "unknown";

    /**
     * 用于IP定位转换
     */
    private static final String REGION = "内网IP|内网IP";

    private IpUtil() {}

    public static String getHostIp() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress ip = addresses.nextElement();
                    if (ip instanceof Inet4Address
                            && !ip.isLoopbackAddress()
                            && !ip.getHostAddress().contains(":")){
                        return ip.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return IP_V4;
    }


    public static String getIp() {
        return getIp(ServletUtils.getRequest());
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (IP_V4.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            }
            catch (UnknownHostException e) {
                log.error(e.getMessage());
            }
        }
        return ip;
    }

    /**
     * 根据ip获取详细地址
     */
    public static String getAddress(String ip) {
        // 1、创建 searcher 对象
        String dbPath = "ip2region/ip2region.xdb";
        // 获取ip2region.db文件的位置
        InputStream inputStream = IpUtil.class.getClassLoader()
                .getResourceAsStream(dbPath);
        Searcher searcher = null;
        try {
            if ("0:0:0:0:0:0:0:1".equals(ip)) {
                return REGION;
            }

            long sTime = System.nanoTime();
            searcher = Searcher.newWithBuffer(IoUtil.readBytes(inputStream));
            // 2、查询
            String region = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - sTime);
            log.info("region: {}, ioCount: {}, took: {} μs", region,
                    searcher.getIOCount(), cost);
            return region;
        }
        catch (Exception e) {
            log.error("failed to create searcher with {}", dbPath, e);
        }
        finally {
            if (searcher != null) {
                try {
                    searcher.close();
                }
                catch (IOException ignored) {

                }
            }
        }
        return REGION;
    }

    /**
     * 获取浏览器类型
     * @param request
     * @return
     */
    public static String getBrowser(HttpServletRequest request) {
        UserAgent userAgent = UserAgent
                .parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        return browser.getName();
    }

    /**
     * 操作系统
     */
    public static String getOs(HttpServletRequest request) {
        UserAgent userAgent = UserAgent
                .parseUserAgentString(request.getHeader("User-Agent"));
        return userAgent.getOperatingSystem().getName();
    }


}

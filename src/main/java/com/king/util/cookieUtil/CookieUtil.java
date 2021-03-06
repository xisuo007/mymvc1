package com.king.util.cookieUtil;

import com.king.util.StringTools;
import com.king.util.domain.ResultDTO;
import com.king.util.log.LogUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @program: kingcar-framework
 * @description: cookie工具类
 * @author: shenshuiliang
 * @create: 2019-03-18 19:03
 **/
public class CookieUtil {

    private static final String PATH               = "/";
    public static String       USER_COOKIE        = "_PK_USER";
    public static String PROJECT_COOKIE = "_PK_PROJECT";
    public final static Locale  LOCALE_US          = Locale.US;
    public final static String  OLD_COOKIE_PATTERN = "EEE, dd-MMM-yyyy HH:mm:ss z";
    private static final String TSPECIALS          = "()<>@,;:\\\"/[]?={} \t";
    private static boolean      checkFlag[]        = new boolean[127];
    static {
        for (int i = 0; i < TSPECIALS.length(); i++) {
            checkFlag[TSPECIALS.charAt(i)] = true;
        }
    }

    public static String getCookieValue(String key, HttpServletRequest request) {
        Cookie cookie = getCookie(key, request);
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

    public static Cookie getCookie(String key, HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        Cookie value = null;
        for (Cookie c : cookies) {
            if (key.equals(c.getName())) {
                value = c;
                break;
            }
        }
        return value;
    }

    public static void addCookie(String key, String value, HttpServletResponse response) {
        setCookie(key, value, -1, null, null, response);
    }

    public static void addCookie(String key, String value, final boolean httpOnly, HttpServletResponse response) {
        setCookie(key, value, -1, null, null, httpOnly, response);
    }

    public static void addCookie(String key, String value, final boolean httpOnly, final boolean secure,
                                 HttpServletResponse response) {
        setCookie(key, value, -1, null, null, httpOnly, secure, response);
    }

    public static void addCookie(String key, String value, int maxAge, HttpServletResponse response) {
        setCookie(key, value, maxAge, null, null, response);
    }

    public static void addCookie(String key, String value, int maxAge, final boolean httpOnly,
                                 HttpServletResponse response) {
        setCookie(key, value, maxAge, null, null, httpOnly, response);
    }

    public static void addCookie(String key, String value, int maxAge, final boolean httpOnly, final boolean secure,
                                 HttpServletResponse response) {
        setCookie(key, value, maxAge, null, null, httpOnly, secure, response);
    }

    public static void addCookie(String key, String value, int maxAge, String path, String domainName,
                                 HttpServletResponse response) {
        setCookie(key, value, maxAge, path, domainName, response);
    }

    public static void addCookie(String key, String value, int maxAge, String path, String domainName,
                                 final boolean httpOnly, HttpServletResponse response) {
        setCookie(key, value, maxAge, path, domainName, httpOnly, response);
    }

    public static void addCookie(String key, String value, int maxAge, String path, String domainName,
                                 final boolean httpOnly, final boolean secure, HttpServletResponse response) {
        setCookie(key, value, maxAge, path, domainName, httpOnly, secure, response);
    }

    public static void removeCookie(String key, HttpServletResponse response) {
        removeCookie(key, null, null, response);
    }

    public static void removeCookie(String key, String path, String domainName, HttpServletResponse response) {
        setCookie(key, StringTools.EMPTY_STRING, 0, path, domainName, false, response);
    }

    private static void setCookie(String key, String value, int maxAge, String path, String domainName,
                                  HttpServletResponse response) {
        setCookie(key, value, maxAge, path, domainName, false, false, response);
    }

    private static void setCookie(String key, String value, int maxAge, String path, String domainName,
                                  final boolean httpOnly, HttpServletResponse response) {
        setCookie(key, value, maxAge, path, domainName, httpOnly, false, response);
    }

    private static void setCookie(String key, String value, int maxAge, String path, String domainName,
                                  final boolean httpOnly, final boolean secure, HttpServletResponse response) {
        if (response != null) {
            Cookie cookie = new Cookie(key, value);
            cookie.setMaxAge(maxAge);
            if (!StringTools.isEmpty(path)) {
                cookie.setPath(path);
            } else {
                cookie.setPath(PATH);
            }
            if (!StringTools.isEmpty(domainName)) {
                cookie.setDomain(domainName);
            }
            cookie.setVersion(0);
            cookie.setSecure(secure);
            if (httpOnly) {
                final StringBuffer buf = new StringBuffer();
                getCookieHeaderValue(cookie, buf, httpOnly);
                response.addHeader(getCookieHeaderName(cookie), buf.toString());
            } else {
                response.addCookie(cookie);
            }
        }
    }

    private static String getCookieHeaderName(final Cookie cookie) {
        final int version = cookie.getVersion();
        if (version == 1) {
            return "Set-Cookie2";
        } else {
            return "Set-Cookie";
        }
    }

    private static void getCookieHeaderValue(final Cookie cookie, final StringBuffer buf, final boolean httpOnly) {
        final int version = cookie.getVersion();

        // this part is the same for all cookies

        String name = cookie.getName(); // Avoid NPE on malformed cookies
        if (name == null) {
            name = "";
        }
        String value = cookie.getValue();
        if (value == null) {
            value = "";
        }

        buf.append(name);
        buf.append("=");

        maybeQuote(version, buf, value);

        // add version 1 specific information
        if (version == 1) {
            // Version=1 ... required
            buf.append("; Version=1");

            // Comment=comment
            if (cookie.getComment() != null) {
                buf.append("; Comment=");
                maybeQuote(version, buf, cookie.getComment());
            }
        }

        // add domain information, if present

        if (cookie.getDomain() != null) {
            buf.append("; Domain=");
            maybeQuote(version, buf, cookie.getDomain());
        }

        // Max-Age=secs/Discard ... or use old "Expires" format
        if (cookie.getMaxAge() >= 0) {
            if (version == 0) {
                buf.append("; Expires=");
                SimpleDateFormat dateFormat = new SimpleDateFormat(OLD_COOKIE_PATTERN, LOCALE_US);
                dateFormat.setTimeZone(TimeZone.getTimeZone("GMT")); // 必须使用GMT模式
                if (cookie.getMaxAge() == 0) {
                    dateFormat.format(new Date(10000), buf, new FieldPosition(0));
                } else {
                    dateFormat.format(new Date(System.currentTimeMillis() + cookie.getMaxAge() * 1000L), buf,
                            new FieldPosition(0));
                }
            } else {
                buf.append("; Max-Age=");
                buf.append(cookie.getMaxAge());
            }
        } else if (version == 1) {
            buf.append("; Discard");
        }

        // Path=path
        if (cookie.getPath() != null) {
            buf.append("; Path=");
            maybeQuote(version, buf, cookie.getPath());
        }

        // Secure
        if (cookie.getSecure()) {
            buf.append("; Secure");
        }

        // HttpOnly
        if (httpOnly) {
            buf.append("; HttpOnly");
        }
    }

    private static void maybeQuote(final int version, final StringBuffer buf, final String value) {
        if (version == 0 || isToken(value)) {
            buf.append(value);
        } else {
            buf.append('"');
            buf.append(value);
            buf.append('"');
        }
    }

    /*
     * Return true iff the string counts as an HTTP/1.1 "token".
     */
    private static boolean isToken(final String value) {
        final int len = value.length();
        char c;
        final char[] charArray = value.toCharArray();
        for (int i = 0; i < len; i++) {
            c = charArray[i];
            if (c < 0x20 || c >= 0x7f) {
                return false;
            } else {
                if (checkFlag[c]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void setSessionIdCookie(HttpServletResponse response, String sessionId, String platformName) {
        try {
            addCookie(platformName+USER_COOKIE,sessionId, 3600 * 24,PATH,null, response);
        } catch (Exception e) {
            LogUtil.error("用户登陆设置sessionId失败",e);
        }
    }

    public static void removeCookie(String key, HttpServletResponse response, String platformName) {
        removeCookie(platformName+key, null, null, response);
    }
    
    public static ResultDTO<String> getSessionIdByCookie(HttpServletRequest request, String platformName) {
        ResultDTO<String> resultDTO = new ResultDTO<String>();
        Cookie cookie = getCookie(platformName+USER_COOKIE, request);
        if (null != cookie) {
            String sessionId = cookie.getValue();
            resultDTO.setSuccess(true);
            resultDTO.setData(sessionId);
        }else{
            resultDTO.setErrCode("401");
            resultDTO.setErrMsg("身份验证失败");
        }
        return resultDTO;
    }
}

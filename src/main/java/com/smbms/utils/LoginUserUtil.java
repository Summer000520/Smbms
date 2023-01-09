package com.smbms.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 判断浏览器中是否含有cookie
 */
public class LoginUserUtil {

    /**
     * 从cookie中获取userId
     * @param request
     * @return
     */
    public static int releaseUserIdFromCookie(HttpServletRequest request) {
        String userIdString = CookieUtil.getCookieValue(request, "id");
        if (StringUtils.isBlank(userIdString)) {
            return 0;
        }
//        System.out.println("releaseUserIdFromCookie中的字符串ID:"+userIdString);
        Integer userId = Integer.parseInt(userIdString);
        return userId;
    }
}

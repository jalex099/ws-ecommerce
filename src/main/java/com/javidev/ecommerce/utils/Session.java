package com.javidev.ecommerce.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class Session {
    public static Long getAuthUserId() {
        return Long.parseLong(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()
                .toString());
    }
}

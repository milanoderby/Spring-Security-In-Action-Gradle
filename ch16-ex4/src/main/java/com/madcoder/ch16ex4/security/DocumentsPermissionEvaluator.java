package com.madcoder.ch16ex4.security;

import com.madcoder.ch16ex4.model.Document;
import java.io.Serializable;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class DocumentsPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication,
        Object target,
        Object permission) {
        Document document = (Document) target;
        String p = (String) permission;

        boolean admin =
            authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(p));

        return admin || document.getOwner().equals(authentication.getName());
    }

    @Override
    public boolean hasPermission(Authentication authentication,
        Serializable targetId,
        String targetType,
        Object permission) {
        return false;
    }
}
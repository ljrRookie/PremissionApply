package com.ljr.premissionapply.annotation;

import com.ljr.premissionapply.base.ApplyPermissionActivity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 权限被取消 的注解
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionCancel {

    int rquestCode() default ApplyPermissionActivity.PARAM_REQUEST_CODE_DEFAULT;

}

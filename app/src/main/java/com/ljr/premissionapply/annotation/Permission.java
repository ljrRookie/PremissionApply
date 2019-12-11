package com.ljr.premissionapply.annotation;

import com.ljr.premissionapply.base.ApplyPermissionActivity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    String[] value();

    int requestCode() default ApplyPermissionActivity.PARAM_REQUEST_CODE_DEFAULT;

}

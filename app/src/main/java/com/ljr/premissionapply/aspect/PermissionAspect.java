package com.ljr.premissionapply.aspect;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.ljr.premissionapply.annotation.Permission;
import com.ljr.premissionapply.annotation.PermissionCancel;
import com.ljr.premissionapply.annotation.PermissionDenied;
import com.ljr.premissionapply.base.ApplyPermissionActivity;
import com.ljr.premissionapply.base.code.IPermission;
import com.ljr.premissionapply.util.PermissionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PermissionAspect {
    @Pointcut
            ("execution(@com.ljr.premissionapply.annotation.Permission * *(..)) && @annotation(permission)")
    public void pointActionMethod(Permission permission) {/* 法内部不做任何事情，只为了@Pointcut服务*/}
    @Around("pointActionMethod(permission)")
    public void aProceedingJoinPoint(final ProceedingJoinPoint point,Permission permission) throws Throwable{
        //先定义一个上下文操作环境
        Context context = null;
        //如果有兼容问题，thisObject == null
        final Object thisObject = point.getThis();

        //给context 初始化
        if(thisObject instanceof Context){
            context = (Context) thisObject;
        }else if(thisObject instanceof Fragment){
            context = ((Fragment) thisObject).getActivity();
        }

        // 判断是否为null
        if (null == context || permission == null) {
            throw new IllegalAccessException("null == context || permission == null is null");
        }
        // 调用权限处理的Activity 申请 检测 处理权限操作  permission.value() == Manifest.permission.READ_EXTERNAL_STORAGE
        final Context finalContext = context;
        ApplyPermissionActivity.requestPermissionAction
                (context, permission.value(), permission.requestCode(), new IPermission() {
                    @Override
                    public void ganted() { // 申请成功 授权成功
                        // 让被 @Permission 的方法 正常的执行下去
                        try {
                            point.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void cancel() { // 被拒绝
                        // 调用到 被 @PermissionCancel 的方法
                        PermissionUtils.invokeAnnotation(thisObject, PermissionCancel.class);
                    }

                    @Override
                    public void denied() { // 严重拒绝 勾选了 不再提醒
                        // 调用到 被 @PermissionDenied 的方法
                        PermissionUtils.invokeAnnotation(thisObject, PermissionDenied.class);

                        // 不仅仅要提醒用户，还需要 自动跳转到 手机设置界面
                        PermissionUtils.startAndroidSettings(finalContext);
                    }
                });
    }

}

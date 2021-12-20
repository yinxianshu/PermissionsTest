package com.lain.permissionstest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    String[] needPermissions = new String[]{
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 事件 - 打电话
    public void callVideoPhone(View view) {
        permissionToDo();
    }

    // 视频电话方法
    public void newVideoPhone() {
        // 打电话的ACTION
        Intent actionCall = new Intent(Intent.ACTION_CALL);
        actionCall.setData(Uri.parse("tel:10086"));
        startActivity(actionCall);
    }

    // 通过枚举类返回权限的中文名
    public void checkPermissionName(ExplainScope scope, List<String> deniedList) {
        for (String s : deniedList) {
            for (int i = 0; i < needPermissions.length; i++) {
                                               /*
                                                    如果被拒绝的权限列表中有申请所需的权限
                                                    那么就弹出对话框继续申请
                                                */
                if (s.equals(needPermissions[i])) {
                    scope.showRequestReasonDialog(
                            deniedList,
                            PermissionEnum
                                    .getPermissionChineseName(s)
                            + "权限是程序正常运行必须的权限",
                            "同意将继续申请权限",
                            "拒绝"
                    );
                }
            }
        }
    }

    // 权限检查
    public void permissionToDo() {
        PermissionX.init(MainActivity.this)
                   .permissions(needPermissions)
                   // 在权限请求之前被调用
                   .explainReasonBeforeRequest()
                   // 用于监听那些被用户拒绝，而又可以再次去申请的权限。
                   .onExplainRequestReason(
                           new ExplainReasonCallbackWithBeforeParam() {
                               /**
                                * 解释为什么需要 deniedList 中的权限时，调用此方法。
                                * @param scope          显示解释对话框
                                * @param deniedList     被拒绝的权限列表
                                * @param beforeRequest  在请求之前还是请求之后
                                */
                               @Override
                               public void onExplainReason(
                                       @NonNull ExplainScope scope,
                                       @NonNull List<String> deniedList,
                                       boolean beforeRequest) {

                                   // 如果是在权限请求之前
                                   // 弹出对话框解释权限申请的原因
                                   if (beforeRequest) {
                                       scope.showRequestReasonDialog(
                                               deniedList,
                                               "即将申请的权限是程序运行所必须的权限",
                                               "同意将继续申请权限",
                                               "拒绝"
                                       );

                                   }
                                   else {
                                       // 权限被拒绝后
                                       checkPermissionName(scope, deniedList);
                                   }


                               }
                           }

                           //
                           // new ExplainReasonCallback() {
                           //     @Override
                           //     public void onExplainReason(
                           //             @NonNull ExplainScope scope,
                           //             @NonNull List<String> deniedList) {
                           //
                           //     }
                           // }
                   )
                   // 当用户点击拒绝并不再询问后
                   .onForwardToSettings(
                           new ForwardToSettingsCallback() {
                               @Override
                               public void onForwardToSettings(
                                       @NonNull ForwardScope scope,
                                       @NonNull List<String> deniedList
                               ) {

                                   scope.showForwardToSettingsDialog(
                                           deniedList,
                                           "点击同意转到应用程序设置中打开必须权限",
                                           "同意", "取消"
                                   );
                               }
                           })
                   // 申请权限
                   .request(
                           new RequestCallback() {
                               @Override
                               public void onResult(
                                       boolean allGranted,
                                       @NonNull List<String> grantedList,
                                       @NonNull List<String> deniedList
                               ) {

                                   // 所有申请的权限都被同意
                                   if (allGranted) {
                                       // TODO 运行需要这个权限的逻辑、方法等。
                                       /*
                                            当然也可以从允许的权限列表（grantedList），
                                            和被拒绝的权限列表（deniedList）中，取出权限进行操作。
                                        */
                                       newVideoPhone();
                                   }
                                   // 有权限被拒绝
                                   else {

                                       for (String s : deniedList) {
                                           for (int i = 0; i < needPermissions.length; i++) {
                                               /*
                                                    如果被拒绝的权限列表中有申请所需的权限
                                                    那么就弹出对话框继续申请
                                                */
                                               if (s.equals(needPermissions[i])) {
                                                   Toast.makeText(
                                                           MainActivity.this,
                                                           "" + PermissionEnum
                                                                   .getPermissionChineseName(s)
                                                           + "所需权限被拒绝",
                                                           Toast.LENGTH_LONG
                                                   ).show();
                                               }
                                           }
                                       }
                                   }
                               }
                           }
                   );
    }




}
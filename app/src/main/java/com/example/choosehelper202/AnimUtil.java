package com.example.choosehelper202;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * 按钮点击动画工具类
 */
public class AnimUtil {
    /**
     * 执行缩放点击动画
     * @param v 被点击的View
     */
    public static void clickAnim(View v) {
        Animation anim = new ScaleAnimation(
                1.0f, 0.95f,  // 宽度缩放比例
                1.0f, 0.95f,  // 高度缩放比例
                Animation.RELATIVE_TO_SELF, 0.5f,  // 以中心点为锚点
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        anim.setDuration(100);
        anim.setFillAfter(true);
        v.startAnimation(anim);
        v.postDelayed(v::clearAnimation, 100);  // 动画结束后清除
    }
}
package com.project.wisdomfirecontrol.common.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/7/20.
 */

@SuppressLint("AppCompatCustomView")
public class DrawbleCenterTextView extends TextView {
    /**
     * drawableLeft与文本一起居中显示
     */
    public DrawbleCenterTextView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
    }

    public DrawbleCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawbleCenterTextView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // getCompoundDrawables() : Returns drawables for the left, top, right, and bottom borders.
        Drawable[] drawable = getCompoundDrawables();
        //得到drawableLeft设置的drawable对象
        Drawable leftDrawable = drawable[0];
        if (leftDrawable != null) {
            //得到leftdrawable 的宽度
            int leftDrawableWidth = leftDrawable.getIntrinsicWidth();
            //得到drawable与text之间的间距
            int drawablePadding = getCompoundDrawablePadding();
            //得到文本的宽度
            int textWidth = (int) getPaint().measureText(getText().toString().trim());

            int bodyWidth = leftDrawableWidth + drawablePadding + textWidth;
            canvas.save();
            //将内容在X轴方向平移
            canvas.translate((getWidth() - bodyWidth) / 2, 0);
        }

        super.onDraw(canvas);
    }

}


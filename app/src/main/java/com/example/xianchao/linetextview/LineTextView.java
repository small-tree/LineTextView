package com.example.xianchao.linetextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;


public class LineTextView extends AppCompatTextView {
    private Context context;
    private int viewWidth;
    private int viewHight;
    private int oneWidth;
    private int oneHight;
    private int num;
    private String str;
    private String[] texts;
    private int textSize = 15;
    private TextPaint mTextPaint;
    private int textColor;
    private CharSequence text;

    public LineTextView(Context context) {
        this(context, null);
    }

    public LineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        text = getText();
        textColor = getTextColors().getDefaultColor();
        textSize = (int) getTextSize();

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.density = getResources().getDisplayMetrics().density;
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(textColor);
        if (text != null && text.length() > 0) {
            setText(text + "");
        }
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        init();
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        if (num == 0)
            return;
        int lineWidth = viewWidth / num;
        int drawY = (viewHight - oneHight) / 3 + oneHight;
        for (int i = 0; i < num; i++) {
            if (i == num - 1) {// 最后一个
                canvas.drawText(texts[i],
                        viewWidth - oneWidth - dip2px(context, 2), drawY, mTextPaint);
            } else if (i != 0 && i != num - 1) {//
                canvas.drawText(texts[i], i * lineWidth + lineWidth / 2
                        - oneWidth / 2, drawY, mTextPaint);
            } else {// 第一个
                canvas.drawText(texts[i], i * lineWidth + dip2px(context, 2),
                        drawY, mTextPaint);
            }
        }
    }

    private void init() {
        viewWidth = this.getWidth();
        viewHight = this.getHeight();
    }

    public void setText(String str) {
        if (str == null)
            return;
        this.str = str;
        num = str.length();
        int width = ce("汉").width();
        this.oneWidth = width;
        this.oneHight = ce(str).height();
        String[] strs = new String[num];
        for (int i = 0; i < num; i++) {
            strs[i] = str.substring(i, i + 1);
        }
        this.texts = strs;
        this.invalidate();
    }

    public Rect ce(String str) {
        Rect rect = new Rect();
        mTextPaint.getTextBounds(str, 0, 1, rect);
        return rect;
    }

    /**
     * 根据手机的分辨率�? dp 的单�? 转成�? px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

package org.sorakun.soradisplay.natureremo;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.Calendar;

/**
 * Like AnalogClock, but digital.  Shows seconds.
 * FIXME: implement separate views for hours/minutes/seconds, so
 * proportional fonts don't shake rendering
 */
public class SoraDigitalClock extends androidx.appcompat.widget.AppCompatTextView {
    Calendar mCalendar;
    private final static String m24 = "k:mm";
    private Runnable mTicker;
    private Handler mHandler;
    private boolean mTickerStopped = false;
    String mFormat = m24;

    public SoraDigitalClock(Context context) {
        super(context);
        initClock();
    }
    public SoraDigitalClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        initClock();
    }
    private void initClock() {
        if (mCalendar == null) {
            mCalendar = Calendar.getInstance();
        }
    }
    @Override
    protected void onAttachedToWindow() {
        mTickerStopped = false;
        super.onAttachedToWindow();
        mHandler = new Handler();
        /*
          requests a tick on the next hard-second boundary
         */
        mTicker = () -> {
            if (mTickerStopped) return;
            mCalendar.setTimeInMillis(System.currentTimeMillis());
            setText(DateFormat.format(mFormat, mCalendar));
            invalidate();
            long now = SystemClock.uptimeMillis();
            long next = now + (1000 - now % 1000);
            mHandler.postAtTime(mTicker, next);
        };
        mTicker.run();
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mTickerStopped = true;
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(SoraDigitalClock.class.getName());
    }
    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(SoraDigitalClock.class.getName());
    }
}
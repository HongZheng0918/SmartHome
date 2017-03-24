package com.kotori.smarthome.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.kotori.smarthome.activity.monitorActivity;
import com.kotori.smarthome.constant.Constant;
import com.kotori.smarthome.util.DateUtil;

@SuppressLint("DrawAllocation")
public class MySurfaceView extends SurfaceView
        implements SurfaceHolder.Callback{
    //此处实现SurfaceHolder.Callback接口，为surfaceView添加生命周期回调函数

    int dy= Display.DEFAULT_DISPLAY;
    monitorActivity ma;                          //得到MyActivity的引用
    Paint paint;                                 //画笔的引用
    OnDrawThread odt;                            //OnDrawThread类引用

    private float picX=0;                        //图片x坐标
    private float picY=0;                        //图片y坐标

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.ma=(monitorActivity) context;

        //将ma的引用指向调用了该Surfaceview类构造器方法的对象，本例为MyActivity

        this.getHolder().addCallback(this);     //注册回调接口
        paint=new Paint();                      //实例化画笔
        odt=new OnDrawThread(this);             //实例化OnDrawThread类

    }
    public void setPicX(float picX) {           //图片x坐标的设置器
        this.picX = picX;
    }
    public void setPicY(float picY) {           //图片y坐标的设置器
        this.picY = picY;
    }

    @Override
    protected void onDraw(Canvas canvas) {  //onDraw方法，此方法用于绘制图像，图形等
        super.onDraw(canvas);
        paint.setColor(Color.GRAY);        //设置画笔为灰色
        canvas.drawRect(0, 0, Constant.SCREENWIDTH, Constant.SCREENHEIGHT, paint);

        //此处画了一个白色的全屏幕的矩形，目的是设置背景为白色，同时每次重绘时清除背景


        canvas.drawBitmap(DateUtil.bitmap, picX, picY, paint);


    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {           //此方法为当surfaceView改变时调用，如屏幕大小改变。
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {//此方法为在surfaceView创建时调用
        odt.start();                //启动onDraw的绘制线程
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {//此方法为在surfaceView销毁前调用
    }
}
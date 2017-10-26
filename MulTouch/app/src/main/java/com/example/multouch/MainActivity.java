package com.example.multouch;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends Activity {
 private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.imageView);
        findViewById(R.id.container).setOnTouchListener(new View.OnTouchListener() {

            float currentDistance;
            float lastDistance = -1;//这里设置为-1是为了便于分别 ，因为图片的长度不可能为负数

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){  //通过Action判断此次触摸的数据类型
                    case MotionEvent.ACTION_DOWN:
//                        System.out.println("向下按");
                        break;
                    case MotionEvent.ACTION_UP:
//                        System.out.println("向上弹");

                        break;
                    case MotionEvent.ACTION_MOVE:
//                        System.out.println("Move");
                        //第③次用于测试扩大或缩小图片所写
                        if (event.getPointerCount() >=2 ) {//这个是用于判断只有多个触摸点是才可以对图片进行拉伸和缩小，一个手指不可能这样操作
                            float offsetX = event.getX(0) - event.getX(1);//获取两条边的距离差
                            float offsetY = event.getY(0) - event.getY(1);
                            currentDistance = (float) Math.sqrt(offsetX * offsetX + offsetY * offsetY);//通过勾股定理就算两个点之间的距离
                            if (lastDistance<0){//如果小于0 说明这是我们自己定义的初始值，因为上面定义的是-1
                                lastDistance = currentDistance;
                            }else {
                                if (currentDistance-lastDistance>5){//这个数值可以随意设定 lastDistance是代表上一次操作时候的距离，如果当前操作的距离比上一次操作的距离大于5，说明就是扩大操作
                                    System.out.println("放大操作");
                                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) iv.getLayoutParams();
                                    lp.width = (int) (1.1 * iv.getWidth());//获取真实宽度
                                    lp.height = (int) (1.1 * iv.getHeight());
                                    iv.setLayoutParams(lp);
                                    lastDistance = currentDistance;
                                }else if (lastDistance-currentDistance>5){//如果上一次的操作减去当前操作大于5，说明图片正在缩小
                                    System.out.println("缩小操作");
                                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) iv.getLayoutParams();
                                    lp.width = (int) (0.9 * iv.getWidth());
                                    lp.height = (int) (0.9 * iv.getHeight());
                                    iv.setLayoutParams(lp);
                                    lastDistance = currentDistance;
                                }
                            }
                        }

//                        System.out.println("触摸点数:"+event.getPointerCount());//获取触摸点数      ②
//                        System.out.println(String.format("x1:%f y1:%f,x2:%f y2:%f",event.getX(0),event.getY(0),event.getX(1),event.getY(1)));//获取多个坐标点的位置 第②次测试所写
//                        这是获取一个触摸点，上面写的是获取多个触摸点
//                        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) iv.getLayoutParams();//获取布局参数 第①次测试
//                        lp.leftMargin = (int) event.getX();
//                        lp.topMargin = (int) event.getY();
//                        iv.setLayoutParams(lp);//图像的相对位置  这个是可以移动的写法，可以拉动图片
//                        System.out.println(String.format("x:%f,y:%f",event.getX(),event.getY()));//获取滑动过程中的坐标位置
                        break;
                }
                return true;//如果设置为false，就代表默认DOWN（向下按）操作触发失败，以后的操作没有触发。
            }
        });
    }
    //private FrameLayout root;
}

package com.example.a2048_2;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {
    private int      num=0;
    private TextView label;
    public Card(Context context) {
        super(context);
        label=new TextView(getContext());
        label.setTextSize(32);
        label.setGravity(Gravity.CENTER);
        label.setBackgroundColor(0x33ffffff);//设置每个卡片的颜色
        LayoutParams lp=new LayoutParams(-1,-1);//该类用来初始化layout控件textView里的宽高属性
        lp.setMargins(10, 10, 0, 0);//设置间隔
        addView(label,lp);
        setNum(0);
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num=num;
        if (num<=0) {
            label.setText("");
        }else {
            label.setText(num+"");
        }
    }
    //重写equals方法，判断卡片绑定的数字是否相等
    public boolean equals(Card o) {
        return getNum()==o.getNum();
    }
}

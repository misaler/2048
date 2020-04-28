package com.example.a2048_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private        String       currentUserID;      //当前用户accountID
    private        String       currentUserBest;    //当前用户最高分
    private        TextView     currentScore;
    private        TextView     currentBest;
    private static MainActivity mainActivity = null;
    private        int          score = 0;
    private        int          bestScore =0;
    private        GameView     gameView;


    public static MainActivity getMainActivity() {
        return mainActivity;
    }
    public MainActivity() {
        mainActivity = this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentScore = (TextView) findViewById(R.id.currentScore);
        currentBest = (TextView)findViewById(R.id.bestScore);
        gameView =(GameView)findViewById(R.id.gameView);

        //获取当前用户
        Intent intent = this.getIntent();
        Bundle bundle=intent.getExtras();
        currentUserID = bundle.getString("currentaccount");

        //显示最高得分
        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        String num=sharedPreferences.getString("number", "");
        currentUserBest="best"+getUserIndex(currentUserID,num);

        SharedPreferences sp = getSharedPreferences("game2048", Context.MODE_PRIVATE);
        bestScore = sp.getInt(currentUserBest, 0);
        currentBest.setText(bestScore+"");
    }

    //各个按钮点击监听
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_quit://退出按钮的点击事件
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);//添加对话框；
                dialog.setTitle("提示：");
                dialog.setMessage("是否退出游戏？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setCurrentUserBest();
                        System.exit(0);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//取消就什么都不做
                    }
                });
                dialog.show();
                break;
            case R.id.btn_restart:  //重新开始按钮
                gameView.startGame();
                break;
            case R.id.btn_phb:      //排行榜按钮，跳转界面
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,RankList.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    public void clearScore() {//清除分数
        score = 0;
        showScore();
    }
    public void showScore() {//展示分数
        currentScore.setText(score + "");
        currentBest.setText(bestScore+"");
    }
    public void addScore(int s) {//添加分数
        score += s;
        showScore();
    }
    public int getScore() { //获取当前得分
        return score;
    }

    public void setBestScore(int s) {
        bestScore = s;
        showScore();
    }

    //获取用户accountID
    public int getUserIndex(String currentID,String num) {
        SharedPreferences sp = getSharedPreferences("game2048", MODE_PRIVATE);
        String currentName = sp.getString(currentID,"");
        String account;
        int x=0;
        for(int i = 0; i < Integer.parseInt(num); i++){ //根据用户accountID获取ID；
            account = "account"+i;
            if( sp.getString(account, "").equals(currentName)) {
                x = i;
            }
        }
        return x;
    }

    //更新当前用户的最高得分
    public void setCurrentUserBest() {
        //获取当前用户
        Intent intent = this.getIntent();
        Bundle bundle=intent.getExtras();
        currentUserID = bundle.getString("currentaccount");

        int lastbestScore, score;//前最高分和，当前得分

        //user shared........获取num；
        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        String num=sharedPreferences.getString("number", "");

        //game2048 shared......
        SharedPreferences sp = this.getSharedPreferences("game2048", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        String cubest="best"+getUserIndex(currentUserID,num);
        lastbestScore=sp.getInt(cubest,0);
        score = getScore();
        bestScore = sp.getInt("best"+getUserIndex(currentUserID,num), 0);

        //新得分更高
        if (lastbestScore < score) {
            //Log.d("currentUserBest::::::",Integer.toString(score));
            editor.putInt(cubest, score);
            setBestScore(score);
            editor.apply();
        }
    }
}

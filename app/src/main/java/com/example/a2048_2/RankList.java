package com.example.a2048_2;

import android.accounts.Account;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.autofill.FillEventHistory;
import android.util.AndroidException;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.logging.XMLFormatter;

import javax.xml.namespace.NamespaceContext;

public class RankList extends Activity {
    private String[] names;
    private int[] bestscores;
    private String       max;
    private String    account;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranklist);

        String accountID;
        String bestID;

        SharedPreferences sharedPreferences = getSharedPreferences("game2048", MODE_PRIVATE);
        SharedPreferences number_sp=getSharedPreferences("user",MODE_PRIVATE);
        num = Integer.parseInt(number_sp.getString("number", ""));
        names = new String[num];
        bestscores=new int[num];

        for (int i = 0; i < num; i++) {
            accountID="account"+i;
            bestID="best"+i;
            max = Integer.toString(sharedPreferences.getInt(bestID, 0));      //根据ID取值
            account = number_sp.getString(accountID, "");

            names[i] = account; //赋值
            bestscores[i] = Integer.parseInt(max);
        }

        //排序
        sort(bestscores,names);

        //将数据存入ArrayList容器
        List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
        for (int i=0;i<num;i++){
            Map<String,Object> listitem=new HashMap<String,Object>();
            listitem.put("rank",(i+1));
            listitem.put("name",names[i]);
            listitem.put("bestscore",bestscores[i]);
            mapList.add(listitem);
        }

        //排行榜adapter
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,mapList,R.layout.rank,
                new String[]{"rank","name","bestscore"},
                new int[]{R.id.rank_rank,R.id.trank_account,R.id.rank_bestscore});
        ListView rankList = (ListView) findViewById(R.id.ranklist);
        rankList.setAdapter(simpleAdapter);

        //返回按钮
        Button returngame=(Button)findViewById(R.id.myreturn);
        returngame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //排序算法
    private void sort(int[] a,String[] b) { //排序
        String s;
        int i;
        for (int x = 0; x < num; x++) {
            for (int y = 0; y < num-1-x; y++) {
                if (a[x] < a[x+1]) {
                    i=a[x];
                    a[x]=a[x+1];
                    a[x+1]=i;  //换分数

                    s=b[x];    //换用户名
                    b[x]=b[x+1];
                    b[x+1]=s;
                }
            }
        }
    }
}


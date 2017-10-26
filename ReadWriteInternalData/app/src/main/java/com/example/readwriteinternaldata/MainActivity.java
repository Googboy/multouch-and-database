package com.example.readwriteinternaldata;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class MainActivity extends Activity {
    private String filename = "test";
    private TextView show;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.editText);
        show = findViewById(R.id.show);
        findViewById(R.id.readBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                   FileInputStream fis =  openFileInput(filename);
                    InputStreamReader is = new InputStreamReader(fis,"utf-8");
                    char input[] = new char[fis.available()];
                    is.read(input);
                    is.close();
                    fis.close();
                    String readed = new String(input);
                    show.setText(readed);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.writeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
                    OutputStreamWriter osw = new OutputStreamWriter(fos,"utf-8");
                    osw.write(et.getText().toString());
                    osw.flush();
                    fos.flush();//输出缓冲区的所有内容
                    osw.close();//后打开的流先关闭，由内向外逐渐关闭所有流
                    fos.close();
                    Toast.makeText(getApplicationContext(),"写入完成",Toast.LENGTH_SHORT).show();//给用户一个返回提醒
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

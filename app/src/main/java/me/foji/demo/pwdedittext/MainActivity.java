package me.foji.demo.pwdedittext;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import me.foji.widget.PwdEditText;

public class MainActivity extends AppCompatActivity {
    private PwdEditText mPwdEditText;
    private Button mJianshuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPwdEditText = (PwdEditText) findViewById(R.id.pwdEditText);
        mJianshuButton = (Button) findViewById(R.id.button_jianshu);

        mPwdEditText.setOnInputListener(new PwdEditText.OnInputListener() {
            @Override
            public void onInput(CharSequence text) {

            }

            @Override
            public void onInputFinish(CharSequence text) {
                Toast.makeText(MainActivity.this,text,Toast.LENGTH_SHORT).show();
            }
        });

        mJianshuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(getString(R.string.url_jianshu));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);             }
        });
    }
}

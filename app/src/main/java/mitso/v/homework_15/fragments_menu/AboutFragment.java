package mitso.v.homework_15.fragments_menu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import mitso.v.homework_15.R;

public class AboutFragment extends android.support.v4.app.Fragment {

    private TextView mTextView_ShowField;
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);

        Button mButton_LoadFromAsset = (Button) view.findViewById(R.id.btn_LoadFromAsset_AF);
        Button mButton_LoadFromRaw = (Button) view.findViewById(R.id.btn_LoadFromRaw_AF);

        mTextView_ShowField = (TextView) view.findViewById(R.id.tv_showField_AF);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        loadAssetText(mTextView_ShowField);
                        break;
                    case 2:
                        loadRawText(mTextView_ShowField);
                        break;
                }
            }
        };

        mButton_LoadFromAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(1);
                    }
                }).start();
            }
        });
        mButton_LoadFromRaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(2);
                    }
                }).start();
            }
        });

        return view;
    }

    private void loadRawText(TextView _textView) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    getResources().openRawResource(R.raw.loren_ipsum)));
            try {
                while (reader.ready())
                    sb.append(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        _textView.setText(sb.toString());
    }

    private void loadAssetText(TextView _textView) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    getResources().getAssets().open("loren_ipsum/loren_ipsum.txt")));
            try {
                while (reader.ready())
                    sb.append(reader.readLine().toUpperCase());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        _textView.setText(sb.toString());
    }
}
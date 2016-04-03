package mitso.v.homework_15.fragments_menu;

import android.os.Bundle;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);

        Button mButton_LoadFromAsset = (Button) view.findViewById(R.id.btn_LoadFromAsset_AF);
        Button mButton_LoadFromRaw = (Button) view.findViewById(R.id.btn_LoadFromRaw_AF);

        mTextView_ShowField = (TextView) view.findViewById(R.id.tv_showField_AF);

        mButton_LoadFromAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAssetText(mTextView_ShowField);
            }
        });
        mButton_LoadFromRaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRawText(mTextView_ShowField);
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
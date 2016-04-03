package mitso.v.homework_15.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import mitso.v.homework_15.R;
import mitso.v.homework_15.interfaces.EventHandler;

public class RegistrationFragment extends Fragment {

    private EditText mEditText_Login;
    private EditText mEditText_Password;
    private EditText mEditText_FirstName;
    private EditText mEditText_LastName;

    private EventHandler mEventHandler;

    private String gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_fragment, container, false);

        mEditText_Login = (EditText) view.findViewById(R.id.et_Login_RF);
        mEditText_Password = (EditText) view.findViewById(R.id.et_Password_RF);
        mEditText_FirstName = (EditText) view.findViewById(R.id.et_FirstName_RF);
        mEditText_LastName = (EditText) view.findViewById(R.id.et_LastName_RF);

        RadioGroup mRadioGroup_Gender = (RadioGroup) view.findViewById(R.id.rg_Gender_RF);
        mRadioGroup_Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_Female_RF:
                        gender = getResources().getString(R.string.s_personGender_female);
                        break;
                    case R.id.rb_Male_RF:
                        gender = getResources().getString(R.string.s_personGender_male);
                        break;
                }
            }
        });

        Button mButton_Register = (Button) view.findViewById(R.id.btn_Register_RF);
        mButton_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventHandler.registerPerson(
                        mEditText_Login.getText().toString(),
                        mEditText_Password.getText().toString(),
                        mEditText_FirstName.getText().toString(),
                        mEditText_LastName.getText().toString(),
                        gender);
            }
        });

        return view;
    }

    public void setEventHandler(EventHandler eventHandler) {
        mEventHandler = eventHandler;
    }

    public void releaseEventHandler() {
        mEventHandler = null;
    }
}
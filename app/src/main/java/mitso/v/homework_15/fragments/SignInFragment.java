package mitso.v.homework_15.fragments;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mitso.v.homework_15.R;
import mitso.v.homework_15.constants.Constants;
import mitso.v.homework_15.interfaces.EventHandler;
import mitso.v.homework_15.models.Person;

public class SignInFragment extends Fragment {

    private TextView mTextView_Greeting;
    private EditText mEditText_Login;
    private EditText mEditText_Password;

    private EventHandler mEventHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_in_fragment, container, false);

        mTextView_Greeting = (TextView) view.findViewById(R.id.tv_Greeting_SF);
        mEditText_Login = (EditText) view.findViewById(R.id.et_Login_SF);
        mEditText_Password = (EditText) view.findViewById(R.id.et_Password_FF);

        TextView mTextView_Registration = (TextView) view.findViewById(R.id.tv_Registration_SF);
        mTextView_Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventHandler.openRegistrationFragment();
            }
        });

        Button mButton_SignIn = (Button) view.findViewById(R.id.btn_SignIn_SF);
        mButton_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventHandler.signIn(
                        mEditText_Login.getText().toString(),
                        mEditText_Password.getText().toString(),
                        getAlertDialog());
            }
        });

        return view;
    }

    private AlertDialog getAlertDialog() {
        return new AlertDialog
                .Builder(getActivity())
                .setPositiveButton(R.string.s_dp_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setGreeting();
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .create();
    }

    private void setGreeting() {
        ArrayList<Person> persons = new ArrayList<>();

        try {
            LoadListTask loadListTask = new LoadListTask();
            loadListTask.execute();
            persons = loadListTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (persons != null) {
            if (!persons.isEmpty()) {
                for (int i = 0; i < persons.size(); i++) {
                    Person person = persons.get(i);
                    if (mEditText_Login.getText().toString().equals(person.getLogin()) && mEditText_Password.getText().toString().equals(person.getPassword())) {
                        String greeting;
                        if (person.getGender().equals(getResources().getString(R.string.s_personGender_female)))
                            greeting = getResources().getString(R.string.s_greetingMrs)
                                    + person.getFirstName() + " " + person.getLastName();
                        else
                            greeting = getResources().getString(R.string.s_greetingMr)
                                    + person.getFirstName() + " " + person.getLastName();
                        mTextView_Greeting.setText(greeting);
                        break;
                    } else {
                        mTextView_Greeting.setText(getResources().getString(R.string.s_greeting));
                    }
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList<Person> persons = new ArrayList<>();

        try {
            LoadListTask loadListTask = new LoadListTask();
            loadListTask.execute();
            persons = loadListTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (persons != null) {
            if (!persons.isEmpty()) {

                Person person = persons.get(persons.size() - 1);
                String greeting;
                if (person.getGender().equals(getResources().getString(R.string.s_personGender_female)))
                    greeting = getResources().getString(R.string.s_greetingMrs)
                            + person.getFirstName() + " " + person.getLastName();
                else
                    greeting = getResources().getString(R.string.s_greetingMr)
                            + person.getFirstName() + " " + person.getLastName();
                mTextView_Greeting.setText(greeting);
                mEditText_Login.setText(person.getLogin());
                mEditText_Password.setText(person.getPassword());
            }
        }
    }

    public void setEventHandler(EventHandler eventHandler) {
        mEventHandler = eventHandler;
    }

    public void releaseEventHandler() {
        mEventHandler = null;
    }

    private class LoadListTask extends AsyncTask<Void, Void, ArrayList<Person>> {
        SharedPreferences sharedPreferences;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sharedPreferences = getActivity().getPreferences(getContext().MODE_PRIVATE);
        }
        @Override
        protected ArrayList<Person> doInBackground(Void ... params) {
            List<Person> persons;
            if (sharedPreferences.contains(Constants.SAVED_LIST_KEY)) {
                String jsonFavorites = sharedPreferences.getString(Constants.SAVED_LIST_KEY, null);
                Gson gson = new Gson();
                Person[] personsArray = gson.fromJson(jsonFavorites,
                        Person[].class);
                persons = Arrays.asList(personsArray);
                persons = new ArrayList<>(persons);
            } else
                return new ArrayList<>();
            return (ArrayList<Person>) persons;
        }
        @Override
        protected void onPostExecute(ArrayList<Person> persons) {
            super.onPostExecute(persons);
        }
    }
}
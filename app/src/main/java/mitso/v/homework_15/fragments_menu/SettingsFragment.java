package mitso.v.homework_15.fragments_menu;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import mitso.v.homework_15.R;
import mitso.v.homework_15.constants.Constants;
import mitso.v.homework_15.fragments_menu.database.DatabaseHelper;

public class SettingsFragment extends Fragment {

    private String sortDatabaseBy = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);

        RadioGroup mRadioGroup_Gender = (RadioGroup) view.findViewById(R.id.rg_SortBy_RF);
        mRadioGroup_Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_sortByLogin_SF:
                        sortDatabaseBy = DatabaseHelper.PERSON_LOGIN;
                        break;
                    case R.id.rb_sortByPassword_SF:
                        sortDatabaseBy = DatabaseHelper.PERSON_PASSWORD;
                        break;
                    case R.id.rb_sortByFirstName_SF:
                        sortDatabaseBy = DatabaseHelper.PERSON_FIRST_NAME;
                        break;
                    case R.id.rb_sortByLastName_SF:
                        sortDatabaseBy = DatabaseHelper.PERSON_LAST_NAME;
                        break;
                    case R.id.rb_sortByGender_SF:
                        sortDatabaseBy = DatabaseHelper.PERSON_GENDER;
                        break;
                }
            }
        });

        return view;
    }

    private class SaveListTask extends AsyncTask<String, Void, Void> {
        SharedPreferences sharedPreferences;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sharedPreferences = getActivity().getPreferences(getContext().MODE_PRIVATE);
        }
        @Override
        protected Void doInBackground(String ... params) {
            String sortBy = "";
            for (String param : params)
                sortBy = param;
            SharedPreferences.Editor ed = sharedPreferences.edit();
            ed.putString(Constants.SAVED_SORT_BY_KEY, sortBy);
            ed.apply();
            return null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        try {
            new SaveListTask().execute(sortDatabaseBy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
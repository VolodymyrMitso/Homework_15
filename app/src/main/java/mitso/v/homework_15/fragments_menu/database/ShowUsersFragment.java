package mitso.v.homework_15.fragments_menu.database;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mitso.v.homework_15.R;
import mitso.v.homework_15.constants.Constants;
import mitso.v.homework_15.models.Person;

public class ShowUsersFragment extends ListFragment {

    private DatabaseHelper mDatabaseHelper = null;
    private Cursor mCursor = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SimpleCursorAdapter adapter=
                new SimpleCursorAdapter(getActivity(), R.layout.person_card,
                        mCursor,
                        new String[] {
                                DatabaseHelper.KEY_ID,
                                DatabaseHelper.PERSON_LOGIN,
                                DatabaseHelper.PERSON_PASSWORD,
                                DatabaseHelper.PERSON_FIRST_NAME,
                                DatabaseHelper.PERSON_LAST_NAME,
                                DatabaseHelper.PERSON_GENDER },
                        new int[] {
                                R.id.person_id,
                                R.id.person_login,
                                R.id.person_password,
                                R.id.person_first_name,
                                R.id.person_last_name,
                                R.id.person_gender },
                        0);

        setListAdapter(adapter);

        if (mCursor == null) {
            mDatabaseHelper = new DatabaseHelper(getActivity());
            ((CursorAdapter) getListAdapter()).changeCursor(doQuery());
        }

        checkList();
    }

    @Override
    public void onDestroy() {
        ((CursorAdapter)getListAdapter()).getCursor().close();
        mDatabaseHelper.close();
        super.onDestroy();
    }

    private Cursor doQuery() {

        String sortBy = "";
        try {
            LoadSortByTask loadSortByTask = new LoadSortByTask();
            loadSortByTask.execute();
            sortBy = loadSortByTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mDatabaseHelper.getReadableDatabase()
                .query(DatabaseHelper.DATABASE_TABLE,
                        new String[] {
                                DatabaseHelper.KEY_ID,
                                DatabaseHelper.PERSON_LOGIN,
                                DatabaseHelper.PERSON_PASSWORD,
                                DatabaseHelper.PERSON_FIRST_NAME,
                                DatabaseHelper.PERSON_LAST_NAME,
                                DatabaseHelper.PERSON_GENDER },
                        null, null, null, null, sortBy);
    }

    private void checkList() {
        ArrayList<Person> databasePersons = getDatabasePersons();

        ArrayList<Person> listPersons = new ArrayList<>();
        try {
            LoadListTask loadListTask = new LoadListTask();
            loadListTask.execute();
            listPersons = loadListTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (listPersons != null) {
            if (listPersons.size() > databasePersons.size()) {

                for (int i = databasePersons.size(); i < listPersons.size(); i++) {
                    ContentValues cv = new ContentValues();

                    cv.put(DatabaseHelper.PERSON_LOGIN, listPersons.get(i).getLogin());
                    cv.put(DatabaseHelper.PERSON_PASSWORD, listPersons.get(i).getPassword());
                    cv.put(DatabaseHelper.PERSON_FIRST_NAME, listPersons.get(i).getFirstName());
                    cv.put(DatabaseHelper.PERSON_LAST_NAME, listPersons.get(i).getLogin());
                    cv.put(DatabaseHelper.PERSON_GENDER, listPersons.get(i).getGender());

                    mDatabaseHelper.getWritableDatabase().insert(DatabaseHelper.DATABASE_TABLE, DatabaseHelper.PERSON_LOGIN, cv);
                    ((CursorAdapter) getListAdapter()).changeCursor(doQuery());
                }
            }
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Cursor mCursor_OnClick = (Cursor) l.getItemAtPosition(position);

        final String mString_PersonLogin = mCursor_OnClick.getString(mCursor_OnClick.getColumnIndex(DatabaseHelper.PERSON_LOGIN));
        final String mString_PersonPassword = mCursor_OnClick.getString(mCursor_OnClick.getColumnIndex(DatabaseHelper.PERSON_PASSWORD));
        final String mString_PersonFirstName = mCursor_OnClick.getString(mCursor_OnClick.getColumnIndex(DatabaseHelper.PERSON_FIRST_NAME));
        final String mString_PersonLastName = mCursor_OnClick.getString(mCursor_OnClick.getColumnIndex(DatabaseHelper.PERSON_LAST_NAME));
        final String mString_PersonGender = mCursor_OnClick.getString(mCursor_OnClick.getColumnIndex(DatabaseHelper.PERSON_GENDER));

        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(getContext());
        mAlertDialog.setTitle(R.string.s_editOrDelete);
        LayoutInflater mInflater = getActivity().getLayoutInflater();
        RelativeLayout mRelativeLayout_PersonCard = (RelativeLayout) mInflater.inflate(R.layout.person_card_edit, null);
        mAlertDialog.setView(mRelativeLayout_PersonCard);

        final EditText mEditText_PersonLogin = (EditText) mRelativeLayout_PersonCard.findViewById(R.id.et_PersonLogin_PC);
        final EditText mEditText_PersonPassword = (EditText) mRelativeLayout_PersonCard.findViewById(R.id.et_PersonPassword_PC);
        final EditText mEditText_PersonFirstName = (EditText) mRelativeLayout_PersonCard.findViewById(R.id.et_personFirstName_PC);
        final EditText mEditText_PersonLastName = (EditText) mRelativeLayout_PersonCard.findViewById(R.id.et_personLastName_PC);
        final EditText mEditText_PersonGender = (EditText) mRelativeLayout_PersonCard.findViewById(R.id.et_personGender_PC);

        mEditText_PersonLogin.setText(mString_PersonLogin);
        mEditText_PersonPassword.setText(mString_PersonPassword);
        mEditText_PersonFirstName.setText(mString_PersonFirstName);
        mEditText_PersonLastName.setText(mString_PersonLastName);
        mEditText_PersonGender.setText(mString_PersonGender);

        final long finalID = id;

        mAlertDialog.setNegativeButton(R.string.s_edit, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                if (ShowUsersSupport.personDataCheck(getContext(),
                        mEditText_PersonLogin.getText().toString(),
                        mEditText_PersonPassword.getText().toString(),
                        mEditText_PersonFirstName.getText().toString(),
                        mEditText_PersonLastName.getText().toString(),
                        mEditText_PersonGender.getText().toString())) {

                    if (ShowUsersSupport.checkGender(getContext(), mEditText_PersonGender.getText().toString())) {

                        ContentValues values = new ContentValues();
                        values.put(DatabaseHelper.PERSON_LOGIN, mEditText_PersonLogin.getText().toString());
                        values.put(DatabaseHelper.PERSON_PASSWORD, mEditText_PersonPassword.getText().toString());
                        values.put(DatabaseHelper.PERSON_FIRST_NAME, mEditText_PersonFirstName.getText().toString());
                        values.put(DatabaseHelper.PERSON_LAST_NAME, mEditText_PersonLastName.getText().toString());
                        values.put(DatabaseHelper.PERSON_GENDER, mEditText_PersonGender.getText().toString());

                        mDatabaseHelper.getWritableDatabase().update(DatabaseHelper.DATABASE_TABLE,
                                values, DatabaseHelper.KEY_ID + "=" + finalID, null);

                        ((CursorAdapter) getListAdapter()).changeCursor(doQuery());
                    }
                }
            }
        });

        mAlertDialog.setPositiveButton(R.string.s_delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                mDatabaseHelper.getWritableDatabase().delete(DatabaseHelper.DATABASE_TABLE,
                        DatabaseHelper.KEY_ID + "=" + finalID, null);

                ((CursorAdapter) getListAdapter()).changeCursor(doQuery());
            }
        });

        mAlertDialog.show();
    }

    @Override
    public void onStop() {
        super.onStop();
//        saveList(getDatabasePersons());
        try {
            new SaveListTask().execute(getDatabasePersons());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private class SaveListTask extends AsyncTask<ArrayList<Person>, Void, Void> {

        SharedPreferences sharedPreferences;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sharedPreferences = getActivity().getPreferences(getContext().MODE_PRIVATE);
        }

        @Override
        protected Void doInBackground(ArrayList<Person>... params) {

            ArrayList<Person> result = new ArrayList<>();
            for (ArrayList<Person> param : params)
                result = param;
            SharedPreferences.Editor ed = sharedPreferences.edit();
            Gson gson = new Gson();
            String jsonPersons = gson.toJson(result);
            ed.putString(Constants.SAVED_LIST_KEY, jsonPersons);
            ed.apply();

            return null;
        }
    }

//    private ArrayList<Person> loadList() {
//        SharedPreferences sharedPreferences = getActivity().getPreferences(getContext().MODE_PRIVATE);
//        List<Person> persons;
//        if (sharedPreferences.contains(Constants.SAVED_LIST_KEY)) {
//            String jsonFavorites = sharedPreferences.getString(Constants.SAVED_LIST_KEY, null);
//            Gson gson = new Gson();
//            Person[] personsArray = gson.fromJson(jsonFavorites,
//                    Person[].class);
//            persons = Arrays.asList(personsArray);
//            persons = new ArrayList<>(persons);
//        } else
//            return new ArrayList<>();
//        return (ArrayList<Person>) persons;
//    }

//    private void saveList(ArrayList<Person> persons) {
//        SharedPreferences sharedPreferences = getActivity().getPreferences(getContext().MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        String jsonPersons = gson.toJson(persons);
//        editor.putString(Constants.SAVED_LIST_KEY, jsonPersons);
//        editor.apply();
//    }

//    private String sortBy() {
//        SharedPreferences sPref = getActivity().getPreferences(getContext().MODE_PRIVATE);
//        return sPref.getString(Constants.SAVED_SORT_BY_KEY, DatabaseHelper.KEY_ID);
//    }

    private class LoadSortByTask extends AsyncTask<Void, Void, String> {
        SharedPreferences sharedPreferences;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sharedPreferences = getActivity().getPreferences(getContext().MODE_PRIVATE);
        }
        @Override
        protected String doInBackground(Void ... params) {
            sharedPreferences = getActivity().getPreferences(getContext().MODE_PRIVATE);
            return sharedPreferences.getString(Constants.SAVED_SORT_BY_KEY, DatabaseHelper.KEY_ID);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private ArrayList<Person> getDatabasePersons() {
        ArrayList<Person> databasePersons = new ArrayList<>();

        Cursor mCursor_CreateList = mDatabaseHelper.getWritableDatabase().query(DatabaseHelper.DATABASE_TABLE, new String[]{
                        DatabaseHelper.KEY_ID,
                        DatabaseHelper.PERSON_LOGIN,
                        DatabaseHelper.PERSON_PASSWORD,
                        DatabaseHelper.PERSON_FIRST_NAME,
                        DatabaseHelper.PERSON_LAST_NAME,
                        DatabaseHelper.PERSON_GENDER},
                null, null, null, null, null);

        while (mCursor_CreateList.moveToNext()) {
            String mString_PersonLogin = mCursor_CreateList.getString(mCursor_CreateList.getColumnIndex(DatabaseHelper.PERSON_LOGIN));
            String mString_PersonPassword = mCursor_CreateList.getString(mCursor_CreateList.getColumnIndex(DatabaseHelper.PERSON_PASSWORD));
            String mString_PersonFirstName = mCursor_CreateList.getString(mCursor_CreateList.getColumnIndex(DatabaseHelper.PERSON_FIRST_NAME));
            String mString_PersonLastName = mCursor_CreateList.getString(mCursor_CreateList.getColumnIndex(DatabaseHelper.PERSON_LAST_NAME));
            String mString_PersonGender = mCursor_CreateList.getString(mCursor_CreateList.getColumnIndex(DatabaseHelper.PERSON_GENDER));

            databasePersons.add(new Person(mString_PersonLogin, mString_PersonPassword, mString_PersonFirstName, mString_PersonLastName, mString_PersonGender));
        }
        mCursor_CreateList.close();

        return databasePersons;
    }
}
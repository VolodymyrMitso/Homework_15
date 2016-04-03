package mitso.v.homework_15;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

import mitso.v.homework_15.models.Person;

public class MainSupport {

    public static void signInSupport(Context context, ArrayList<Person> persons, String _login, String _password, AlertDialog _alertDialog) {
        if (!persons.isEmpty()) {
            for (int i = 0; i < persons.size(); i++) {
                Person person = persons.get(i);
                if (persons.get(i).getLogin().equals(_login) && persons.get(i).getPassword().equals(_password)) {
                    if (person.getGender().equals(context.getResources().getString(R.string.s_personGender_female)))
                        showAlertDialog(_alertDialog,
                                context.getResources().getString(R.string.s_dt_success),
                                context.getResources().getString(R.string.s_dm_welcomeMrs) +
                                        person.getFirstName() + " " + person.getLastName());
                    else
                        showAlertDialog(_alertDialog,
                                context.getResources().getString(R.string.s_dt_success),
                                context.getResources().getString(R.string.s_dm_welcomeMr) +
                                        person.getFirstName() + " " + person.getLastName());
                    break;
                } else {
                    showAlertDialog(_alertDialog,
                            context.getResources().getString(R.string.s_dt_failure),
                            context.getResources().getString(R.string.s_dm_user) +
                                    _login + context.getResources().getString(R.string.s_dm_notExist));
                }
            }
        } else {
            showAlertDialog(_alertDialog,
                    context.getResources().getString(R.string.s_dt_failure),
                    context.getResources().getString(R.string.s_dm_user) +
                            _login + context.getResources().getString(R.string.s_dm_notExist));
        }
    }

    private static void showAlertDialog(AlertDialog _alertDialog, String _title, String _message) {
        _alertDialog.setTitle(_title);
        _alertDialog.setMessage(_message);
        _alertDialog.show();
    }

    public static boolean personDataCheck(String _login, String _password, String _firstName, String _lastName, String _gender) {
        boolean result;
        if (_login.isEmpty()
                || _password.isEmpty()
                || _firstName.isEmpty()
                || _lastName.isEmpty()
                || _gender == null)
            result = false;
        else
            result = true;
        return result;
    }
}
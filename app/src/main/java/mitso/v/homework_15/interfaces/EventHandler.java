package mitso.v.homework_15.interfaces;

import android.support.v7.app.AlertDialog;

public interface EventHandler {

    void signIn(String login, String password, AlertDialog alertDialog);

    void openRegistrationFragment();

    void registerPerson(
            String firstName,
            String lastName,
            String login,
            String password,
            String gender);
}
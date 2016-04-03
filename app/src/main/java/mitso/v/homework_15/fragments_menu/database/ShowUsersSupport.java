package mitso.v.homework_15.fragments_menu.database;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import mitso.v.homework_15.R;

public class ShowUsersSupport {

    public static boolean personDataCheck(Context _c, String _login, String _password, String _firstName, String _lastName, String _gender) {
        boolean result;
        if (_login.isEmpty()
                || _password.isEmpty()
                || _firstName.isEmpty()
                || _lastName.isEmpty()
                || _gender.isEmpty()) {
            result = false;
            showToast(_c, _c.getResources().getString(R.string.s_edit_failure));
        } else
            result = true;
        return result;
    }

    public static boolean checkGender(Context _c, String _gender) {
        boolean result;
        if (_gender.equals(_c.getResources().getString(R.string.s_personGender_male)) ||
                _gender.equals(_c.getResources().getString(R.string.s_personGender_female)))
            result = true;
        else {
            result = false;
            showToast(_c, _c.getResources().getString(R.string.s_edit_failure));
        }
        return result;
    }

    private static void showToast(Context c, String toastString) {

        TextView toastView = new TextView(c);
        toastView.setBackgroundColor(c.getResources().getColor(R.color.c_toast_bg));
        toastView.setTextColor(c.getResources().getColor(R.color.c_toast_text));
        toastView.setText(toastString);
        int padding = c.getResources().getDimensionPixelSize(R.dimen.d_size_10dp);
        toastView.setPadding(padding, padding, padding, padding);

        Toast toast = new Toast(c);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastView);
        toast.show();
    }
}
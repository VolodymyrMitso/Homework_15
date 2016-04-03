package mitso.v.homework_15.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;

    public Person() {
    }

    public Person(String login, String password, String firstName, String lastName, String gender) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Person (Parcel in) {
        setLogin(in.readString());
        setPassword(in.readString());
        setFirstName(in.readString());
        setLastName(in.readString());
        setGender(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getLogin());
        dest.writeString(getPassword());
        dest.writeString(getFirstName());
        dest.writeString(getLastName());
        dest.writeString(getGender());
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {

        @Override
        public Person createFromParcel(Parcel source) {
            return new Person (source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public String toString() {
        return "Person{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
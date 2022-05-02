package com.example.movieticketshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegistrationActivity
        extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener{
    private static final String LOG_TAG = RegistrationActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 21;

    EditText userNameET;
    EditText userEmailET;
    EditText passwordET;
    EditText passwordAET;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        if (secret_key != 21){
            finish();
        }

        userNameET = findViewById(R.id.userNameEditText);
        userEmailET = findViewById(R.id.userEmailEditText);
        passwordET = findViewById(R.id.passwordEditText);
        passwordAET = findViewById(R.id.passwordAgainEditText);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);

        String userEmail = preferences.getString("userEmail", "");
        String password = preferences.getString("password", "");

        userEmailET.setText(userEmail);
        passwordET.setText(password);
        passwordAET.setText(password);

        mAuth = FirebaseAuth.getInstance();


    }

    public void registration(View view) {
        String userName = userNameET.getText().toString();
        String email = userEmailET.getText().toString();
        String password = passwordET.getText().toString();
        String passwordConfirm = passwordAET.getText().toString();

        if (!password.equals(passwordConfirm)) {
            Log.e(LOG_TAG, "A két jelszó nem egyezik.");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(LOG_TAG, "Sikeresen létrtehoztad a felhasználói fiókod!");

                    startTicketShopping();
                }else {
                    Log.d(LOG_TAG, "Nem sikerult létrehozni a felhasznalot");
                    Toast.makeText(RegistrationActivity.this, "A következő okok miatt: "+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        //startTicketShopping();
    }

    public void cancel(View view) {
        finish();
    }

    private void startTicketShopping(/*user data somehow*/){
        Intent intent = new Intent(this, ShoppingActivity.class);
        //intent.putExtra("SECRET_KEY", SECRET_KEY);
        Log.d(LOG_TAG,"Here we are");
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
        Log.i(LOG_TAG, selectedItem);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
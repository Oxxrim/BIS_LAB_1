package ua.kpi.bis_lab_1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import io.realm.Realm;
import ua.kpi.bis_lab_1.model.User;

public class ChangePassActivity extends AppCompatActivity {

    private Realm realm;
    private String login;
    private Context context;
    private EditText oldPass;
    private EditText newPass;
    private EditText repPass;
    private Button changePass;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        getSupportActionBar().hide();

        realm = Realm.getDefaultInstance();
        context = this;
        login = getIntent().getStringExtra("login");

        user = realm.where(User.class).equalTo("login", login).findFirst();

        oldPass = findViewById(R.id.oldPass);
        newPass = findViewById(R.id.newPass);
        repPass = findViewById(R.id.repeatPass);

        changePass = findViewById(R.id.change);

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oldPass.getText().toString().equals(user.getPassword())) {
                    if (user.isLimitations()){
                        String pass = newPass.getText().toString();
                        if (Pattern.compile("[a-zA-Z]").matcher(pass).find() && Pattern.compile("\\p{Punct}").matcher(pass).find() && Pattern.compile("[-+*/()]").matcher(pass).find()){
                            if (newPass.getText().toString().equals(repPass.getText().toString())){
                                realm.beginTransaction();
                                user.setPassword(newPass.getText().toString());
                                realm.commitTransaction();
                                finish();
                            } else {
                                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Password must have letter, punctuation character and math operator", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (newPass.getText().toString().equals(repPass.getText().toString())){
                            realm.beginTransaction();
                            user.setPassword(newPass.getText().toString());
                            realm.commitTransaction();
                            finish();
                        } else {
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(context, "Old password is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

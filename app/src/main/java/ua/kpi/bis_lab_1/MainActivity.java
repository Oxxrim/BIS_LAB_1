package ua.kpi.bis_lab_1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import ua.kpi.bis_lab_1.model.Role;
import ua.kpi.bis_lab_1.model.User;

public class MainActivity extends AppCompatActivity {
    private Realm realm;
    private Context context;
    private User user;
    private EditText login;
    private EditText password;
    private Button signIn;
    private int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Realm.init(this);

        realm = Realm.getDefaultInstance();
        context = this;

        user = realm.where(User.class).equalTo("login", "Admin").findFirst();

        if (user == null){
            realm.beginTransaction();
            realm.insert(new User("Admin", "", Role.ADMIN, false, false));
            realm.commitTransaction();
        }

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.signIn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = realm.where(User.class).equalTo("login", login.getText().toString()).findFirst();

                if (user == null){
                    Toast.makeText(context, "Don't find user with this login", Toast.LENGTH_SHORT).show();
                } else {
                    if (!user.isBlock()) {
                        if (user.getPassword().equals(password.getText().toString())) {
                            Intent intent = new Intent(context, MenuActivity.class);
                            intent.putExtra("login", user.getLogin());
                            startActivity(intent);
                        } else {
                            if (counter == 1) {
                                realm.beginTransaction();
                                user.setBlock(true);
                                realm.commitTransaction();
                            } else {
                                counter--;
                                Toast.makeText(context, "Password is incorrect \n" + counter + " attempts left!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(context, "This account is block", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }
}

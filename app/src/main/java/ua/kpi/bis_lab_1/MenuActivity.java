package ua.kpi.bis_lab_1;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.realm.Realm;
import ua.kpi.bis_lab_1.model.Role;
import ua.kpi.bis_lab_1.model.User;

public class MenuActivity extends AppCompatActivity {
    private Realm realm;
    private String login;
    private Context context;
    private Button changePass;
    private Button userList;
    private Button about;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();

        realm = Realm.getDefaultInstance();
        context = this;
        login = getIntent().getStringExtra("login");

        user = realm.where(User.class).equalTo("login", login).findFirst();

        changePass = findViewById(R.id.changePass);
        userList = findViewById(R.id.userList);
        about = findViewById(R.id.about);

        if (user.getRole().equals(Role.USER)){
            userList.setVisibility(View.INVISIBLE);
            userList.setHeight(0);
        }

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChangePassActivity.class);
                intent.putExtra("login", user.getLogin());
                startActivity(intent);
            }
        });

        userList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserListActivity.class);
                intent.putExtra("login", user.getLogin());
                startActivity(intent);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setTitle("About program");
                dialog.setContentView(R.layout.about_dialog);
                dialog.show();
            }
        });
    }
}

package ua.kpi.bis_lab_1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ua.kpi.bis_lab_1.adapter.UserListAdapter;
import ua.kpi.bis_lab_1.model.User;

public class UserListActivity extends AppCompatActivity {

    private Realm realm;
    private String login;
    private Context context;
    private User user;
    private List<User> userList;

    private RecyclerView listView;
    private UserListAdapter userListAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    FloatingActionButton addUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        getSupportActionBar().hide();

        realm = Realm.getDefaultInstance();
        context = this;
        login = getIntent().getStringExtra("login");

        user = realm.where(User.class).equalTo("login", login).findFirst();

        userList = realm.where(User.class).findAll();

        listView = findViewById(R.id.userList);

        mLayoutManager = new LinearLayoutManager(context);
        listView.setLayoutManager(mLayoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());

//        loadProductList(docAcceptance);
        userListAdapter = new UserListAdapter(context, userList);

        userListAdapter.setListener(new UserListAdapter.AdapterListener() {

            @Override
            public void onDeleteItem(Integer position) {

            }

            @Override
            public void onItemClickListener(View v, Integer position) {

            }
        });

        listView.setAdapter(userListAdapter);

        addUser = findViewById(R.id.addUser);
        addUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }
}

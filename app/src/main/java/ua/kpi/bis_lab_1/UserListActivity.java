package ua.kpi.bis_lab_1;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ua.kpi.bis_lab_1.adapter.UserListAdapter;
import ua.kpi.bis_lab_1.model.Role;
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

    private FloatingActionButton addUser;

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
                final Dialog dialog = new Dialog(context);
                dialog.setTitle("Edit");
                dialog.setContentView(R.layout.add_edit_user_dialog);

                final EditText login = dialog.findViewById(R.id.login);
                login.setText(userList.get(position).getLogin());
                login.setEnabled(false);

                final TextView block = dialog.findViewById(R.id.block);
                if (userList.get(position).isBlock()) {
                    block.setText("true");
                } else {
                    block.setText("false");
                }

                block.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (block.getText().toString().equals("true")){
                            block.setText("false");
                        } else {
                            block.setText("true");
                        }
                    }
                });

                final TextView limitations = dialog.findViewById(R.id.limitations);
                if (userList.get(position).isLimitations()) {
                    limitations.setText("true");
                } else {
                    limitations.setText("false");
                }

                limitations.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (limitations.getText().toString().equals("true")){
                            limitations.setText("false");
                        } else {
                            limitations.setText("true");
                        }
                    }
                });

                Button edit = dialog.findViewById(R.id.addEditUser);
                edit.setText("Edit");

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean blocking = block.getText().toString().equals("true") ? true : false;
                        Boolean limit = limitations.getText().toString().equals("true") ? true : false;
                        User user = realm.where(User.class).equalTo("login", login.getText().toString()).findFirst();

                        realm.beginTransaction();
                        user.setBlock(blocking);
                        user.setLimitations(limit);
                        realm.commitTransaction();

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        listView.setAdapter(userListAdapter);

        addUser = findViewById(R.id.addUser);
        addUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setTitle("Add");
                dialog.setContentView(R.layout.add_edit_user_dialog);

                final EditText login = dialog.findViewById(R.id.login);

                final TextView block = dialog.findViewById(R.id.block);
                block.setText("false");


                block.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (block.getText().toString().equals("true")){
                            block.setText("false");
                        } else {
                            block.setText("true");
                        }
                    }
                });

                final TextView limitations = dialog.findViewById(R.id.limitations);
                limitations.setText("false");

                limitations.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (limitations.getText().toString().equals("true")){
                            limitations.setText("false");
                        } else {
                            limitations.setText("true");
                        }
                    }
                });

                Button edit = dialog.findViewById(R.id.addEditUser);
                edit.setText("Add");

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean blocking = block.getText().toString().equals("true") ? true : false;
                        Boolean limit = limitations.getText().toString().equals("true") ? true : false;
                        User user = realm.where(User.class).equalTo("login", login.getText().toString()).findFirst();

                        if (user == null) {
                            User user1 = new User(login.getText().toString(), "", Role.USER, blocking, limit);
                            realm.beginTransaction();
                            realm.insert(user1);
                            realm.commitTransaction();

                            userList = realm.where(User.class).findAll();
                            userListAdapter.update(userList);

                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "This login is booked", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });
    }
}

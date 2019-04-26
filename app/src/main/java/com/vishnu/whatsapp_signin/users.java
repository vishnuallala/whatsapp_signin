package com.vishnu.whatsapp_signin;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class users extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        setTitle("users");
        listView=findViewById(R.id.listview);
        arrayList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter(users.this,android.R.layout.simple_list_item_1,arrayList);
        final SwipeRefreshLayout swipeRefreshLayout=findViewById(R.id.swipe);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(users.this,chat.class);
                intent.putExtra("selecteduser",arrayList.get(position));
                startActivity(intent);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ParseQuery<ParseUser> parseQuery=new ParseUser().getQuery();
                parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
                parseQuery.whereNotContainedIn("username",arrayList);
                parseQuery.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> objects, ParseException e) {
                        if(e==null && objects.size()>0){
                            for(ParseUser x:objects){
                                arrayList.add(x.getUsername());
                            }
                            arrayAdapter.notifyDataSetChanged();
                            if(swipeRefreshLayout.isRefreshing()){
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                        else{
                            if(swipeRefreshLayout.isRefreshing()){
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }
                });
            }
        });
        final ParseQuery<ParseUser> parseQuery=new ParseUser().getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null && objects.size()>0){
                    for(ParseUser x:objects){
                        arrayList.add(x.getUsername());
                    }
                    listView.setAdapter(arrayAdapter);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logout){
            ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    Intent intent=new Intent(users.this,login.class);
                    startActivity(intent);
                    finish();
                }
            });

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(users.this,chat.class);
        startActivity(intent);
    }
}

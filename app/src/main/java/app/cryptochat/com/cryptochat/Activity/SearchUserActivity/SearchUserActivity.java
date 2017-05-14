package app.cryptochat.com.cryptochat.Activity.SearchUserActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;

import java.util.ArrayList;

import app.cryptochat.com.cryptochat.R;

public class SearchUserActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private String query;

    ArrayList<UserViewModels> userViewModelses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user_result);
        Intent intent = getIntent();
        query = intent.getStringExtra("q");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        createMockData();
        ListView listView = (ListView) findViewById(R.id.listView);
        SearchUserAdapter searchUserAdapter = new SearchUserAdapter(this, userViewModelses);
        listView.setAdapter(searchUserAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setQuery(query, false);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                finish();
                overridePendingTransition(R.anim.transition_fade_in,R.anim.transition_fade_out);
                return false;
            }
        });

        return true;
    }


    private void createMockData(){
        for(int i = 0; i < 10; i++){
            UserViewModels userViewModels = new UserViewModels();
            userViewModelses.add(userViewModels);
        }
    }



    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.length() == 0){
            finish();
            overridePendingTransition(R.anim.transition_fade_in,R.anim.transition_fade_out);
        }
        return false;
    }
}

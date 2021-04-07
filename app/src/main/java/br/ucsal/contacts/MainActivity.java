package br.ucsal.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Objects;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import br.ucsal.contacts.adapter.RecycleViewAdapter;
import br.ucsal.contacts.adapter.ViewPageAdapter;
import br.ucsal.contacts.fragments.ContactsSortedByIdFragment;
import br.ucsal.contacts.models.Contact;
import br.ucsal.contacts.controller.ContactController;

public class MainActivity extends AppCompatActivity implements RecycleViewAdapter.OnContactClickListener {

    private static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;
    private static final String TAG = "Clicked";
    public static final String CONTACT_ID = "contact_id";
    private ContactController contactViewModel;
    private RecyclerView recyclerView;
    private RecycleViewAdapter recyclerViewAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Button banner_button;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_bar);
        banner_button = findViewById(R.id.banner_button);
        // todo colocar a lista nos TABs
        // recyclerView = findViewById(R.id.recycler_view);

        tabLayout.setupWithViewPager(this.viewPager);
        ViewPageAdapter viewPagerAdapter = new ViewPageAdapter(getSupportFragmentManager(), 0);
        viewPager.setAdapter(viewPagerAdapter);
        Fragment contactsSortedByIdFragment = new ContactsSortedByIdFragment();

        viewPagerAdapter.addFragment(contactsSortedByIdFragment, "Order By Id");
        viewPagerAdapter.addFragment(contactsSortedByIdFragment, "Order By Name");
        viewPagerAdapter.addFragment(contactsSortedByIdFragment, "Order By Phone");
        //viewPagerAdapter.addFragment(settingsFragment, "Settings");


        banner_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewContact.class);
                startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            String name = data.getStringExtra(NewContact.NAME_REPLY);
            String phone = data.getStringExtra(NewContact.PHONE);
            String age = data.getStringExtra(NewContact.AGE);
            assert name != null;
            Contact contact = new Contact(name, phone, Integer.parseInt(age));
            ContactController.store(contact);
        }
    }

    @Override
    public void onContactClick(int position) {
        Contact contact = Objects.requireNonNull(contactViewModel.contacts.getValue()).get(position);
        Log.d(TAG, "onContactClick: " + contact.getId());
        Intent intent = new Intent(MainActivity.this, NewContact.class);
        intent.putExtra(CONTACT_ID, contact.getId());
        startActivity(intent);
    }


}
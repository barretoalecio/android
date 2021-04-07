package br.ucsal.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import br.ucsal.contacts.models.Contact;
import br.ucsal.contacts.controller.ContactController;

public class NewContact extends AppCompatActivity {
    public static final String NAME_REPLY = "name_reply";
    public static final String PHONE = "occupation";
    public static final String AGE = "age";
    private EditText enterName;
    private EditText enterOccupation;
    private EditText enterAge;
    private Button saveInfoButton;
    private int contactId = 0;
    private Boolean isEdit = false;
    private Button updateButton;
    private Button deleteButton;

    private ContactController contactController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        enterName = findViewById(R.id.enter_name);
        enterOccupation = findViewById(R.id.enter_occupation);
        enterAge = findViewById(R.id.enter_age);
        saveInfoButton = findViewById(R.id.save_button);

        contactController = new ViewModelProvider.AndroidViewModelFactory(NewContact.this
                .getApplication())
                .create(ContactController.class);

        if (getIntent().hasExtra(MainActivity.CONTACT_ID)) {
            contactId = getIntent().getIntExtra(MainActivity.CONTACT_ID, 0);
            contactController.show(contactId).observe(this, contact -> {
                if (contact != null) {
                    enterOccupation.setText(contact.getPhone());
                    enterName.setText(contact.getName());
                    enterAge.setText(Integer.toString(contact.getAge()));
                }
            });
            isEdit = true;

        }
        saveInfoButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();

            if (!TextUtils.isEmpty(enterName.getText())
                    && !TextUtils.isEmpty(enterOccupation.getText())) {
                String name = enterName.getText().toString();
                String phone = enterOccupation.getText().toString();
                String age = enterAge.getText().toString();
                replyIntent.putExtra(NAME_REPLY, name);
                replyIntent.putExtra(PHONE, phone);
                replyIntent.putExtra(AGE, age);
                setResult(RESULT_OK, replyIntent);
            } else {
                setResult(RESULT_CANCELED, replyIntent);
            }
            finish();

        });

        deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(view -> edit(true));
        //Update button
        updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(view -> edit(false));


        if (isEdit) {
            saveInfoButton.setVisibility(View.GONE);
        } else {
            updateButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
        }

    }

    private void edit(Boolean isDelete) {
        String name = enterName.getText().toString().trim();
        String phone = enterOccupation.getText().toString().trim();
        String age = enterAge.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
            Snackbar.make(enterName, R.string.empty, Snackbar.LENGTH_SHORT)
                    .show();
        } else {
            Contact contact = new Contact();
            contact.setId(contactId);
            contact.setName(name);
            contact.setPhone(phone);
            contact.setAge(Integer.parseInt(age));
            if (isDelete)
                ContactController.destroy(contact);
            else
                ContactController.update(contact);
            finish();

        }
    }
}
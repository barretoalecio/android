package br.ucsal.contacts.controller;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.ucsal.contacts.models.Contact;
import br.ucsal.contacts.service.ContactService;

public class ContactController extends AndroidViewModel {

    public static ContactService service;
    public final LiveData<List<Contact>> contacts;

    public ContactController(@NonNull Application application) {
        super(application);
        this.service = new ContactService(application);
        contacts = service.read();
    }

    public LiveData<List<Contact>> index() { return contacts; }
    public static void store(Contact contact) { service.create(contact); }
    public LiveData<Contact> show(int id) { return service.read(id);}
    public static void update(Contact contact) { service.update(contact);}
    public static void destroy(Contact contact) { service.delete(contact);}
}

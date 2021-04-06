package br.ucsal.contacts.service;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import br.ucsal.contacts.models.Contact;
import br.ucsal.contacts.repository.ContactRepository;
import br.ucsal.contacts.utils.ContactRoomDatabase;

public class ContactService {
    private ContactRepository repository;
    private LiveData<List<Contact>> contacts;

    public ContactService(Application application) {
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application);
        repository = db.repository();
        contacts = repository.getAllContacts();
    }

    public void create(Contact contact) {
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> repository.insert(contact));
    }

    public LiveData<List<Contact>> read() {
        return this.contacts;
    }

    public LiveData<List<Contact>> readSortByName() {
        return this.repository.getAllContactsOrderByName();
    }

    public LiveData<List<Contact>> readSortByAge() {
        return this.repository.getAllContatsOrderByAge();
    }

    public LiveData<Contact> read(int id) {
        return this.repository.get(id);
    }

    public void update(Contact contact) {
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> repository.update(contact));
    }

    public void delete(Contact contact) {
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> repository.delete(contact));
    }

    public Double averageAge() {
        return this.repository.getAllContactsAgeAverage();
    }

}

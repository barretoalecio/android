package br.ucsal.contacts.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.ucsal.contacts.models.Contact;

@Dao
public interface ContactRepository {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Contact contact);

    @Query("DELETE FROM tb_contacts")
    void deleteAll();

    @Query("SELECT * FROM tb_contacts")
    LiveData<List<Contact>> getAllContacts();

    @Query("SELECT * FROM tb_contacts WHERE tb_contacts.id == :id")
    LiveData<Contact> get(int id);

    @Query("SELECT * FROM tb_contacts ORDER BY contact_name ASC")
    LiveData<List<Contact>> getAllContactsOrderByName();

    @Query("SELECT * FROM tb_contacts ORDER BY contact_age ASC")
    LiveData<List<Contact>> getAllContatsOrderByAge();

    @Query("SELECT AVG(contact_age) FROM tb_contacts")
    Double getAllContactsAgeAverage();

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);
}

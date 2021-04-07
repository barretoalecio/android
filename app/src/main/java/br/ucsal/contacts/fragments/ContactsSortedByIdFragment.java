package br.ucsal.contacts.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ucsal.contacts.MainActivity;
import br.ucsal.contacts.R;
import br.ucsal.contacts.adapter.RecycleViewAdapter;
import br.ucsal.contacts.controller.ContactController;

public class ContactsSortedByIdFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecycleViewAdapter recyclerViewAdapter;
    private ContactController contactViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contacts_sorted_by_id, container , false);
        this.recyclerView = view.findViewById(R.id.recycler_view);
        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity()
                .getApplication())
                .create(ContactController.class);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        contactViewModel.index().observe(getViewLifecycleOwner(), contacts -> {
            recyclerViewAdapter = new RecycleViewAdapter(contacts, getContext(), new RecycleViewAdapter.OnContactClickListener() {
                @Override
                public void onContactClick(int position) {

                }
            });
            recyclerView.setAdapter(recyclerViewAdapter);

        });

        return inflater.inflate(R.layout.fragment_contacts_sorted_by_id, container, false);
    }
}
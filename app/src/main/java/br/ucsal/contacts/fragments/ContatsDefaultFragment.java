package br.ucsal.contacts.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ucsal.contacts.R;
import br.ucsal.contacts.adapter.RecycleViewAdapter;
import br.ucsal.contacts.controller.ContactController;
import br.ucsal.contacts.fragments.dummy.DummyContent;

public class ContatsDefaultFragment extends Fragment {

    private RecycleViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private ContactController contactViewModel;
    private View view;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public ContatsDefaultFragment() {
    }

    public static ContatsDefaultFragment newInstance(int columnCount) {
        ContatsDefaultFragment fragment = new ContatsDefaultFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contats_default_list, container, false);
        contactViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity()
                .getApplication())
                .create(ContactController.class);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
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
        }
        return view;
    }
}
package br.com.jcamelo.appfotos.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import br.com.jcamelo.appfotos.MainActivity;
import br.com.jcamelo.appfotos.R;
import br.com.jcamelo.appfotos.model.AbstractFragment;

public class UserFragment extends AbstractFragment {

    private MaterialButton login;
    private TextInputEditText nameUser;
    private Toolbar toolbar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        login = view.findViewById(R.id.button_login);
        nameUser = view.findViewById(R.id.text_view_name_user);
        toolbar = getActivity().findViewById(R.id.toolbar);

        nameUser.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        nameUser.setOnEditorActionListener((v, actionId, event) -> {
            if (!nameUser.getText().toString().isEmpty()){
                moveFragment();
            }
            return false;
        });

        nameUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0 || start > 0){
                    login.setEnabled(true);
                } else {
                    login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        login.setOnClickListener(v -> {
            moveFragment();
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle("AppFotos");
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_user;
    }

    private void moveFragment(){
        Bundle bundle = new Bundle();
        bundle.putString("user", nameUser.getText().toString());
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        EquipmentFragment equipmentFragment = new EquipmentFragment();
        equipmentFragment.setArguments(bundle);
        fragmentTransaction.addToBackStack("home");
        fragmentTransaction.replace(R.id.main_frame_layout, equipmentFragment);
        fragmentTransaction.commit();
    }

}

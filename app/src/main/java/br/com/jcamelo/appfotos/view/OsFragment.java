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

public class OsFragment extends AbstractFragment {

    private MaterialButton orderService;
    private TextInputEditText os;
    private Toolbar toolbar;
    private String textToolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        orderService = view.findViewById(R.id.button_order_service);
        os = view.findViewById(R.id.text_view_order_service);
        toolbar = getActivity().findViewById(R.id.toolbar);

        os.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        os.setOnEditorActionListener((v, actionId, event) -> {
            if (!os.getText().toString().isEmpty()){
                moveFragment();
            }
            return false;
        });

        os.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0 || start > 0){
                    orderService.setEnabled(true);
                } else {
                    orderService.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (getArguments() != null){
            textToolbar = getArguments().getString("equip");
        }


        orderService.setOnClickListener(v -> {
            moveFragment();
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle(textToolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_os;
    }


    public void moveFragment(){
        Bundle bundle = new Bundle();
        bundle.putString("equip", getArguments().getString("equip"));
        bundle.putString("user", getArguments().getString("user"));
        bundle.putString("os", os.getText().toString());
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PhotoFragment photoFragment = new PhotoFragment();
        photoFragment.setArguments(bundle);
        fragmentTransaction.addToBackStack("os");
        fragmentTransaction.replace(R.id.main_frame_layout, photoFragment);
        fragmentTransaction.commit();
    }


}

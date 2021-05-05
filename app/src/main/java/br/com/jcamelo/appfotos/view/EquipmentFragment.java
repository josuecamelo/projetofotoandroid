package br.com.jcamelo.appfotos.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.jcamelo.appfotos.MainActivity;
import br.com.jcamelo.appfotos.R;
import br.com.jcamelo.appfotos.model.AbstractFragment;

public class EquipmentFragment extends AbstractFragment {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerview_equipment);
        adapter = new Adapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        toolbar = getActivity().findViewById(R.id.toolbar);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle("SELECIONE UM EQUIPAMENTO");
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_equipment;
    }


    private class ViewHolder extends RecyclerView.ViewHolder{

        private TextView equipmentName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            equipmentName = itemView.findViewById(R.id.text_view_equipment);

        }
    }


    private class Adapter extends RecyclerView.Adapter<ViewHolder>{

        private String[] equipmentList = getResources().getStringArray(R.array.equipment_string);

        public Adapter(){

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(getLayoutInflater().inflate(R.layout.item_equipment, parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.equipmentName.setText(equipmentList[position]);
            holder.equipmentName.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("equip", holder.equipmentName.getText().toString());
                bundle.putString("user", getArguments().getString("user"));

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                OsFragment osFragment = new OsFragment();
                osFragment.setArguments(bundle);
                fragmentTransaction.addToBackStack("equip");
                fragmentTransaction.replace(R.id.main_frame_layout, osFragment);
                fragmentTransaction.commit();
            });

        }

        @Override
        public int getItemCount() {
            return equipmentList.length;
        }
    }
}

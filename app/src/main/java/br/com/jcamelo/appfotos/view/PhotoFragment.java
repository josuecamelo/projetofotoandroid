package br.com.jcamelo.appfotos.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.com.jcamelo.appfotos.MainActivity;
import br.com.jcamelo.appfotos.R;
import br.com.jcamelo.appfotos.model.AbstractFragment;
import br.com.jcamelo.appfotos.model.DescriptionsPhoto;

import static android.app.Activity.RESULT_OK;

public class PhotoFragment extends AbstractFragment {


    private TextView textViewOS;
    private DescriptionsPhoto descriptionsPhoto;
    private PhotoAdapter photoAdapter;
    private VideoAdapter videoAdapter;
    private TextAdapter textAdapter;
    private RecyclerView recyclerViewPhoto, recyclerViewVideo, recyclerViewText;
    private MaterialButton send, captureVideo, generatorTxt;
    private Toolbar toolbar;
    private TextInputEditText obs;
    private File badFile;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_VIDEO_CAPTURE = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        recyclerViewPhoto = view.findViewById(R.id.recycler_view_main_photo);
        captureVideo = view.findViewById(R.id.button_video);
        recyclerViewVideo = view.findViewById(R.id.recycler_view_video);
        obs = view.findViewById(R.id.text_input_edit_text_obs);
        send = view.findViewById(R.id.button_send);
        generatorTxt = view.findViewById(R.id.button_generator_txt);
        textViewOS = view.findViewById(R.id.text_view_os);
        recyclerViewText = view.findViewById(R.id.recycler_view_txt);
        toolbar = getActivity().findViewById(R.id.toolbar);

        descriptionsPhoto = new DescriptionsPhoto();

        photoAdapter = new PhotoAdapter();
        recyclerViewPhoto.setAdapter(photoAdapter);
        recyclerViewPhoto.setLayoutManager(new LinearLayoutManager(getContext()));

        videoAdapter = new VideoAdapter();
        recyclerViewVideo.setAdapter(videoAdapter);
        recyclerViewVideo.setLayoutManager(new LinearLayoutManager(getContext()));

        textAdapter = new TextAdapter();
        recyclerViewText.setAdapter(textAdapter);
        recyclerViewText.setLayoutManager(new LinearLayoutManager(getContext()));


        if (getArguments() != null) {
            descriptionsPhoto.setEquip(getArguments().getString("equip"));
            descriptionsPhoto.setOs(getArguments().getString("os"));
            descriptionsPhoto.setUser(getArguments().getString("user"));
        }


        textViewOS.setText(descriptionsPhoto.getOs());

        captureVideo.setOnClickListener(v -> {
            dispatchTakeVideoIntent();
        });

        generatorTxt.setOnClickListener(v -> {
            createTxtFile();
            obs.setText("");
            textAdapter.setFileList(searchFileText());
            textAdapter.notifyDataSetChanged();
        });

        send.setOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            SendFileFragment sendFileFragment = new SendFileFragment();
            fragmentTransaction.addToBackStack("photo");
            fragmentTransaction.replace(R.id.main_frame_layout, sendFileFragment);
            fragmentTransaction.commit();

        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<String> list = new ArrayList<>();
        String[] listString;

        switch (descriptionsPhoto.getEquip()) {
            case "Motores":
                listString = getResources().getStringArray(R.array.equipment_motor);
                Collections.addAll(list, listString);
                descriptionsPhoto.setInitials("MO");
                descriptionsPhoto.setPartsEquip(list);
                photoAdapter.setStringList(descriptionsPhoto.getPartsEquip());
                photoAdapter.notifyDataSetChanged();
                break;
            case "Bombas":
                listString = getResources().getStringArray(R.array.equipment_bomb);
                Collections.addAll(list, listString);
                descriptionsPhoto.setInitials("BO");
                descriptionsPhoto.setPartsEquip(list);
                photoAdapter.setStringList(descriptionsPhoto.getPartsEquip());
                photoAdapter.notifyDataSetChanged();
                break;
            case "Motor Média":
                listString = getResources().getStringArray(R.array.equipment_motor_media);
                Collections.addAll(list, listString);
                descriptionsPhoto.setInitials("MM");
                descriptionsPhoto.setPartsEquip(list);
                photoAdapter.setStringList(descriptionsPhoto.getPartsEquip());
                photoAdapter.notifyDataSetChanged();
                break;
            case "Gerador":
                listString = getResources().getStringArray(R.array.equipment_generator);
                Collections.addAll(list, listString);
                descriptionsPhoto.setInitials("GE");
                descriptionsPhoto.setPartsEquip(list);
                photoAdapter.setStringList(descriptionsPhoto.getPartsEquip());
                photoAdapter.notifyDataSetChanged();
                break;
            case "Drives":
                listString = getResources().getStringArray(R.array.equipment_drivers);
                Collections.addAll(list, listString);
                descriptionsPhoto.setInitials("DR");
                descriptionsPhoto.setPartsEquip(list);
                photoAdapter.setStringList(descriptionsPhoto.getPartsEquip());
                photoAdapter.notifyDataSetChanged();
                break;
            case "Transformadores":
                listString = getResources().getStringArray(R.array.equipment_transformers);
                Collections.addAll(list, listString);
                descriptionsPhoto.setInitials("TF");
                descriptionsPhoto.setPartsEquip(list);
                photoAdapter.setStringList(descriptionsPhoto.getPartsEquip());
                photoAdapter.notifyDataSetChanged();
                break;
            case "Redutores":
                listString = getResources().getStringArray(R.array.equipment_reducer);
                Collections.addAll(list, listString);
                descriptionsPhoto.setInitials("RE");
                descriptionsPhoto.setPartsEquip(list);
                photoAdapter.setStringList(descriptionsPhoto.getPartsEquip());
                photoAdapter.notifyDataSetChanged();
                break;
            default:
                Toast.makeText(getContext(), "Error tipo categoria", Toast.LENGTH_SHORT).show();
                break;
        }
    }



    @Override
    protected int getLayout() {
        return R.layout.fragment_photo;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            videoAdapter.setFileList(searchFileVideo());
            videoAdapter.notifyDataSetChanged();
        } else if (requestCode == REQUEST_VIDEO_CAPTURE) {
            deleteFile(badFile);
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            photoAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle(descriptionsPhoto.toString());
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        photoAdapter.notifyDataSetChanged();
        videoAdapter.notifyDataSetChanged();
        videoAdapter.setFileList(searchFileVideo());
        textAdapter.setFileList(searchFileText());
        textAdapter.notifyDataSetChanged();


    }

    private void dispatchTakePictureIntent(String nameFile, String folder) {
        File storageDir = getActivity().getExternalFilesDir(folder);
        File photoFile = new File(storageDir, nameFile);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "br.com.jcamelo.appfotos.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void dispatchTakeVideoIntent() {
        File storageDir = getActivity().getExternalFilesDir("Video");
        File videoFile = new File(storageDir, nameVideo());
        badFile = videoFile;
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            if (videoFile != null) {
                Uri videoURI = FileProvider.getUriForFile(getContext(),
                        "br.com.jcamelo.appfotos.fileprovider",
                        videoFile);

                takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI);
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
            }
        }
    }

    private List<File> searchFilePhoto(String folder) {
        List<File> list = new ArrayList<>();
        String path = getActivity().getExternalFilesDir(folder).toString();
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: " + files.length);
        for (int i = 0; i < files.length; i++) {
            list.add(files[i]);
        }

        return list;
    }

    private void deleteFile(File file) {
        file.delete();

    }

    private String nameVideo() {
        String date = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
        String nameVideo = descriptionsPhoto.getOs() + "_" + descriptionsPhoto.getUser()
                + "_" + "VIDEO" + "_" + date + ".mp4";

        return nameVideo;
    }

    private List<File> searchFileVideo() {
        List<File> list = new ArrayList<>();
        String path = getActivity().getExternalFilesDir("Video").toString();
        File directory = new File(path);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            list.add(files[i]);
        }

        return list;
    }

    private void createTxtFile() {
        if (!obs.getText().toString().isEmpty()) {
            String date = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
            String nameTxt = getActivity().getExternalFilesDir("Texto") + "/"
                    + descriptionsPhoto.getOs()
                    + "_" + descriptionsPhoto.getUser()
                    + "_" + "TEXTO" + "_" + date + ".txt";

            try {
                File myFile = new File(nameTxt);
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter =
                        new OutputStreamWriter(fOut);
                myOutWriter.append(obs.getText().toString());
                myOutWriter.close();
            } catch (Exception e) {
                Toast.makeText(getContext(), "Error Arquivo de texto não criado", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Informar o texto no campo Observação", Toast.LENGTH_SHORT).show();
        }
    }

    private List<File> searchFileText() {
        List<File> list = new ArrayList<>();
        String path = getActivity().getExternalFilesDir("Texto").toString();
        File directory = new File(path);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            list.add(files[i]);
        }

        return list;
    }

    private class ViewHolderPhoto extends RecyclerView.ViewHolder {

        private TextView descPhoto;
        private ImageView camera;
        private MaterialCardView cardView;
        private RecyclerView recyclerView;


        public ViewHolderPhoto(@NonNull View itemView) {
            super(itemView);
            descPhoto = itemView.findViewById(R.id.text_view_item_desc_photo);
            camera = itemView.findViewById(R.id.image_view_camera);
            cardView = itemView.findViewById(R.id.card_view_photo);
            recyclerView = itemView.findViewById(R.id.recycler_view_photo);
            camera.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<ViewHolderPhoto> {

        private List<String> stringList = new ArrayList<>();

        public void setStringList(List<String> stringList) {
            this.stringList = stringList;
        }

        @NonNull
        @Override
        public ViewHolderPhoto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolderPhoto(getLayoutInflater().inflate(R.layout.item_photo, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderPhoto holder, int position) {
            String category = stringList.get(position);
            ItemAdapter itemAdapter = new ItemAdapter(searchFilePhoto(category));
            holder.recyclerView.setAdapter(itemAdapter);
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

            holder.descPhoto.setText(category);
            holder.cardView.setOnClickListener(v -> {
                if (holder.camera.getVisibility() != View.VISIBLE) {
                    holder.camera.setVisibility(View.VISIBLE);
                    holder.recyclerView.setVisibility(View.VISIBLE);
                } else {
                    holder.camera.setVisibility(View.GONE);
                    holder.recyclerView.setVisibility(View.GONE);
                }
            });

            holder.camera.setOnClickListener(v -> {
                String date = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
                String nameFile = descriptionsPhoto.getOs() + "_" + descriptionsPhoto.getInitials() +
                        "_" + descriptionsPhoto.getUser() + "_" + String.valueOf(position + 1) + "_";
                String nameFileComplete = nameFile + date + ".jpg";
                dispatchTakePictureIntent(nameFileComplete, category);
                holder.camera.setVisibility(View.GONE);
                holder.recyclerView.setVisibility(View.GONE);

            });

        }


        @Override
        public void onBindViewHolder(@NonNull ViewHolderPhoto holder, int position, @NonNull List<Object> payloads) {
            super.onBindViewHolder(holder, position, payloads);
        }

        @Override
        public int getItemCount() {
            return stringList.size();
        }


    }

    private class ViewHolderItem extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view_photo);
        }

        public void bind(File file) {
            Picasso.get().load(file).resize(100, 100)
                    .centerCrop().into(imageView);

            imageView.setOnClickListener(v -> {
                Intent intent = new Intent();
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "br.com.jcamelo.appfotos.fileprovider", file);

                intent.setDataAndType(uri, "image/*");
                intent.setAction(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);
            });


            imageView.setOnLongClickListener(v -> {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Excluir Imagem");
                alertDialog.setMessage("Deletar o arquivo: " + file.getName() + " ?");
                alertDialog.setNeutralButton("Cancelar", ((dialog, which) -> {
                    dialog.cancel();
                }));
                alertDialog.setPositiveButton("DELETAR", ((dialog, which) -> {
                    deleteFile(file);
                    photoAdapter.notifyDataSetChanged();
                    dialog.cancel();
                }));

                alertDialog.create().show();
                return false;
            });

        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ViewHolderItem> {

        private List<File> fileList = new ArrayList<>();

        public ItemAdapter(List<File> fileList) {
            this.fileList = fileList;
        }

        public void setFileList(List<File> fileList) {
            this.fileList = fileList;
        }

        @NonNull
        @Override
        public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolderItem(getLayoutInflater().inflate(R.layout.item_photo_camera, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderItem holder, int position) {
            holder.bind(fileList.get(position));

        }

        @Override
        public int getItemCount() {
            return fileList.size();
        }

    }

    private class ViewHolderVideo extends RecyclerView.ViewHolder {

        private TextView nameFile;
        private MaterialCardView cardView;

        public ViewHolderVideo(@NonNull View itemView) {
            super(itemView);

            nameFile = itemView.findViewById(R.id.text_view_name_video);
            cardView = itemView.findViewById(R.id.card_view_video);

        }

        public void bind(File file) {
            cardView.setOnClickListener(v -> {
                Intent intent = new Intent();
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "br.com.jcamelo.appfotos.fileprovider", file);

                intent.setDataAndType(uri, "video/*");
                intent.setAction(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);

            });

            cardView.setOnLongClickListener(v -> {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Excluir Video");
                alertDialog.setMessage("Deletar o video: " + file.getName() + " ?");
                alertDialog.setNeutralButton("Cancelar", ((dialog, which) -> {
                    dialog.cancel();
                }));
                alertDialog.setPositiveButton("DELETAR", ((dialog, which) -> {
                    deleteFile(file);
                    videoAdapter.setFileList(searchFileVideo());
                    videoAdapter.notifyDataSetChanged();
                    dialog.cancel();
                }));
                alertDialog.create().show();
                return false;
            });
        }
    }

    private class VideoAdapter extends RecyclerView.Adapter<ViewHolderVideo> {

        private List<File> fileList = new ArrayList<>(searchFileVideo());

        public void setFileList(List<File> fileList) {
            this.fileList = fileList;
        }

        @NonNull
        @Override
        public ViewHolderVideo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolderVideo(getLayoutInflater().inflate(R.layout.item_video, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderVideo holder, int position) {
            holder.nameFile.setText(fileList.get(position).getName());
            holder.bind(fileList.get(position));

        }

        @Override
        public int getItemCount() {
            return fileList.size();
        }
    }

    private class ViewHolderText extends RecyclerView.ViewHolder {
        private TextView nameFile;
        private MaterialCardView cardView;

        public ViewHolderText(@NonNull View itemView) {
            super(itemView);

            nameFile = itemView.findViewById(R.id.text_view_name_video);
            cardView = itemView.findViewById(R.id.card_view_video);

        }

        public void bind(File file) {
            cardView.setOnClickListener(v -> {
                Intent intent = new Intent();
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "br.com.jcamelo.appfotos.fileprovider", file);

                intent.setDataAndType(uri, "text/*");
                intent.setAction(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);

            });

            cardView.setOnLongClickListener(v -> {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Excluir Texto");
                alertDialog.setMessage("Deletar o Texto: " + file.getName() + " ?");
                alertDialog.setNeutralButton("Cancelar", ((dialog, which) -> {
                    dialog.cancel();
                }));
                alertDialog.setPositiveButton("DELETAR", ((dialog, which) -> {
                    deleteFile(file);
                    textAdapter.setFileList(searchFileText());
                    textAdapter.notifyDataSetChanged();
                    dialog.cancel();
                }));
                alertDialog.create().show();
                return false;
            });
        }


    }

    private class TextAdapter extends RecyclerView.Adapter<ViewHolderText> {

        private List<File> fileList = new ArrayList<>(searchFileText());

        public void setFileList(List<File> fileList) {
            this.fileList = fileList;
        }

        @NonNull
        @Override
        public ViewHolderText onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolderText(getLayoutInflater().inflate(R.layout.item_video, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderText holder, int position) {
            holder.nameFile.setText(fileList.get(position).getName());
            holder.bind(fileList.get(position));
        }

        @Override
        public int getItemCount() {
            return fileList.size();
        }
    }


}
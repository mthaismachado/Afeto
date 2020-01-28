package com.team.afeto.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.team.afeto.Helper.UsuarioSingleton;
import com.team.afeto.Model.Usuario;
import com.team.afeto.R;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilActivity extends AppCompatActivity {

    private TextView lbl_Nome_Completo;
    private TextView lbl_Nome;
    private TextView lbl_Email;
    private TextView lbl_Telefone;
    private TextView lbl_Estado;
    private TextView lbl_ComoAjuda;
    private TextView lbl_Descricao;
    private ImageView btn_Arrow_Back;
    private CircleImageView profile_Image;
    private Uri uri;

    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        lbl_Nome = findViewById(R.id.lbl_nome);
        lbl_Email = findViewById(R.id.lbl_email);
        lbl_Telefone = findViewById(R.id.lbl_telefone);
        lbl_Estado = findViewById(R.id.lbl_estado);
        lbl_ComoAjuda = findViewById(R.id.lbl_comoAjuda);
        lbl_Descricao = findViewById(R.id.lbl_descricao);
        lbl_Nome_Completo = findViewById(R.id.lbl_nome_completo);
        btn_Arrow_Back = findViewById(R.id.btn_arrow_back);
        profile_Image = findViewById(R.id.profile_image);

        profile_Image.setOnClickListener(crop_perfil);

        btn_Arrow_Back.setOnClickListener(arrow_back);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        mStorageRef = FirebaseStorage.getInstance().getReference();

        preencherTela();
    }

    public void preencherTela() {
        Usuario usuario = UsuarioSingleton.getUsuario();

        lbl_Nome.setText(usuario.getNome());
        lbl_Nome_Completo.setText(usuario.getNome());
        lbl_Email.setText(usuario.getEmail());
        //lbl_Telefone.setText(document.getData().get("nome").toString());
        lbl_Estado.setText(usuario.getEstado());
        lbl_ComoAjuda.setText(usuario.getComoAjuda().toString());
        if(usuario.getUri_perfil() != null){
            profile_Image.setImageURI(usuario.getUri_perfil());
        }else{
            try {
                recuperaImagemPerfil();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private View.OnClickListener crop_perfil = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CropImage.startPickImageActivity(PerfilActivity.this);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri imageuri = CropImage.getPickImageResultUri(this, data);
            if(CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)){
                uri = imageuri;
                if(Build.VERSION.SDK_INT > 23){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
                }
            }else{
                startCrop(imageuri);
            }
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult  result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Usuario usuario = UsuarioSingleton.getUsuario();
                Bitmap bitmap = null;
                try {
                    bitmap = Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri()), 1024, 1024, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri uri =  getImageUri(getApplicationContext(), bitmap);
                usuario.setUri_perfil(uri);
                gravarImagemPerfil(usuario, uri);
                UsuarioSingleton.setUsuario(usuario);
                profile_Image.setImageURI(uri);
                Toast.makeText(this, "Imagem atualizada com sucesso", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void gravarImagemPerfil(final Usuario usuario, Uri uri){
        FirebaseUser user = mAuth.getCurrentUser();
        Uri file = uri;
        final StorageReference perfilRef = mStorageRef.child("perfil_images/" + user.getUid() + ".jpg");

        perfilRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });
    }

    private void recuperaImagemPerfil() throws IOException {
        if(UsuarioSingleton.getUsuario().getUri_perfil() == null){
            FirebaseUser user = mAuth.getCurrentUser();

            final StorageReference perfilRef = mStorageRef.child("perfil_images/" + user.getUid() + ".jpg");

            final File localFile = File.createTempFile(user.getUid(), "jpg", getCacheDir());

            perfilRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Uri uri = Uri.fromFile(localFile);
                    UsuarioSingleton.getUsuario().setUri_perfil(uri);
                    profile_Image.setImageURI(uri);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }
    }

    private void startCrop(Uri imageuri){
        CropImage.activity(imageuri)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private View.OnClickListener arrow_back = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

}

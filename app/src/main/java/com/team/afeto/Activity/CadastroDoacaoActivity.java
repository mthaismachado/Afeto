package com.team.afeto.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.team.afeto.Model.Doacao;
import com.team.afeto.R;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class CadastroDoacaoActivity extends AppCompatActivity implements Validator.ValidationListener {
    private Spinner mCategoria;
    @Length(min = 4, message = "Escreve um título legal")
    private EditText mtitulo;
    private EditText mValor;
    private ImageView photo1;
    private ImageView photo2;
    private Button mBtn_concluido;
    private Uri uri;
    private int id_image_click;
    private Validator validator;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private String uidDoacao;
    private Uri uri_foto1;
    private Uri uri_foto2;
    private CountDownTimer mCountDownTimer;
    private ImageView btn_Arrow_Back;
    private static final String[] CATEGORIAS_DOACAO = new String[]{"Higiene", "Casa", "Acessórios", "Roupas", "Bebê"};
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_doacao);

        photo1 = findViewById(R.id.photo1);
        photo2 = findViewById(R.id.photo2);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mtitulo = findViewById(R.id.titulo);
        mValor = findViewById(R.id.valor);
        mBtn_concluido = findViewById(R.id.btn_concluido);

        photo1.setOnClickListener(crop_perfil);
        photo2.setOnClickListener(crop_perfil);
        mBtn_concluido.setOnClickListener(processarArquivos);

        btn_Arrow_Back = findViewById(R.id.btn_arrow_back);
        btn_Arrow_Back.setOnClickListener(arrowBack);

        mCategoria = findViewById(R.id.categoria);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CATEGORIAS_DOACAO);
        mCategoria.setAdapter(adapter);


        //Validator
        validator = new Validator(this);
        validator.setValidationListener(this);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

    }

    private View.OnClickListener processarArquivos = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            validator.validate();
        }
    };

    private View.OnClickListener crop_perfil = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(requestPermission()){
                id_image_click = v.getId();
                CropImage.startPickImageActivity(CadastroDoacaoActivity.this);
            }
        }
    };

    private View.OnClickListener arrowBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private boolean requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {
                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageuri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)) {
                uri = imageuri;
                if (Build.VERSION.SDK_INT > 23) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                }
            } else {
                startCrop(imageuri);
            }

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = null;
                try {
                    bitmap = Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri()), 800, 1024, false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri uri = getImageUri(getApplicationContext(), bitmap);
                if(uri == null){uri = result.getUri();}
                switch (id_image_click){
                    case(R.id.photo1) :
                        photo1.setImageURI(uri);
                        uri_foto1 = uri;
                        break;
                    case(R.id.photo2) :
                        photo2.setImageURI(uri);
                        uri_foto2 = uri;
                        break;
                }

                Toast.makeText(this, "Imagem carregada com sucesso", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void startCrop(Uri imageuri) {
        CropImage.activity(imageuri)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        if(path == null){
            return null;
        }else{
            return Uri.parse(path);
        }
    }

    private void gravaImagemDoacao(String uidDoacao, Uri uri, final int indice) {
        Uri file = uri;
        StorageReference perfilRef = mStorageRef.child("doacoes/" + uidDoacao + "/" + uidDoacao + indice + ".jpg");
        Toast.makeText(this, "Estamos salvando as imagens", Toast.LENGTH_SHORT).show();

        perfilRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if(indice == 1){
                            Intent intent = new Intent(getApplicationContext(), DoacaoEnviadaActivity.class);
                            mCountDownTimer.cancel();
                            mProgressBar.setVisibility(View.GONE);
                            startActivity(intent);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(CadastroDoacaoActivity.this, "Nao conseguimos salvar as fotos", Toast.LENGTH_SHORT).show();
                        mBtn_concluido.setEnabled(true);
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void gravaDoacaonabase(Doacao doacao){
        db.collection("doacoes")
                .add(doacao)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        uidDoacao = task.getResult().getId();
                        Toast.makeText(CadastroDoacaoActivity.this, "Doacao Cadastrada", Toast.LENGTH_SHORT).show();
                        gravarImagens();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                     @Override
                    public void onFailure(@NonNull Exception e) {
                         Toast.makeText(CadastroDoacaoActivity.this, "Não conseguimos cadastrar a doação", Toast.LENGTH_SHORT).show();
                         mBtn_concluido.setEnabled(true);
                         mProgressBar.setVisibility(View.GONE);
                    }
                });

    }


    @Override
    public void onValidationSucceeded() {
        mBtn_concluido.setEnabled(false);
        mProgressBar.setVisibility(View.VISIBLE);
        FirebaseUser user = mAuth.getCurrentUser();
        Doacao doacao = new Doacao();
        doacao.setCategoria(mCategoria.getSelectedItem().toString());
        doacao.setTitulo(mtitulo.getText().toString());
        doacao.setValor(mValor.getText().toString());
        doacao.setUidUsuario(user.getUid());

        if(uri_foto1 == null && uri_foto2 == null){
            Toast.makeText(this, "Adicione uma imagem do seu produto", Toast.LENGTH_LONG).show();
            mBtn_concluido.setEnabled(true);
            mProgressBar.setVisibility(View.GONE);
            return;
        }

        gravaDoacaonabase(doacao);

        mCountDownTimer = new CountDownTimer(3000, 3000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Toast.makeText(CadastroDoacaoActivity.this, "Estamos tentando salvar sua doação. Aguarde um pouco", Toast.LENGTH_SHORT).show();
            }
        }.start();


    }

    public void gravarImagens(){
        if(uri_foto1 != null){
            gravaImagemDoacao(uidDoacao, uri_foto1, 0);
        }
        if(uri_foto2 != null){
            gravaImagemDoacao(uidDoacao, uri_foto2, 1 );
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}

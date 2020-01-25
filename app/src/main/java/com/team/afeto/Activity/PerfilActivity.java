package com.team.afeto.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team.afeto.Helper.UsuarioSingleton;
import com.team.afeto.Model.Usuario;
import com.team.afeto.R;
import com.theartofdev.edmodo.cropper.CropImage;

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

        //TODO: Ver a possibilidade de colcoar os ícones ao lado do TextView.
        //TODO: Implementar o firebase Storage para upload de imagem(ver bibilioteca para fazer crop) e ver como referenciar a foto do storage no firestore.
        //TODO: Vai fazer a edição do perfil ?? Se for, colocar o botão de editar ao lado da foto.

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
                profile_Image.setImageURI(result.getUri());
                Toast.makeText(this, "Imagem atualizada com sucesso", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void startCrop(Uri imageuri){
        CropImage.activity(imageuri)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    private View.OnClickListener arrow_back = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

}

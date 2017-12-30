package com.interjoy.CardAndDatabase;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.interjoy.FruitsIdentification.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class ObjectCardActivity extends AppCompatActivity {

    private ImageView objectImView;
    private Button openCamera1,openAlbum1;
    private EditText objectcardname,objectcardinfo;
    private Uri photoUri;
    private Bitmap fbit;

    private DBHelper myDatebaseHelper;
    List<Object> objectList;

    private static final int TAKE_PHOTO=111;
    private static final int PICK_PHOTO=222, MY_PERMISSIONS_REQUEST_CALL_PHONE2=333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.objectcard);
        myDatebaseHelper = new DBHelper(this, "DAILY.db", null, 1);
        SQLiteDatabase db = myDatebaseHelper.getWritableDatabase();
        objectList = new ArrayList<Object>();
        fbit= BitmapFactory.decodeResource(getResources(), R.drawable.lantern);
        setview();
    }


    private void setview(){
        objectImView=(ImageView)findViewById(R.id.IV_ObjectCard);
        openCamera1=(Button)findViewById(R.id.OpenCamera1);
        openAlbum1=(Button)findViewById(R.id.OpenAlbum1);
        objectcardname=(EditText)findViewById(R.id.ET_ObjectCardName);
        objectcardinfo=(EditText)findViewById(R.id.ET_ObjectCardInfo);
    }

    public void openCameraC(View v){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    public void openAlbumC(View v){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);
        }
        else {
            Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,PICK_PHOTO);}
    }

    public void object_conf(View v){

        SQLiteDatabase db = myDatebaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String name;
        String info;
        name=objectcardname.getText().toString();
        info=objectcardinfo.getText().toString();

        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        fbit.compress(Bitmap.CompressFormat.PNG, 100, os);

        values.put("objectname",name);
        values.put("objectinfo",info);
        values.put("objectimage",os.toByteArray());
        db.insert("Photo",null,values);
        Toast.makeText(this, "数据创建成功", Toast.LENGTH_LONG).show();
        db.close();

        finish();
    }

    public void object_quit(View v){
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    Bundle bundle=data.getExtras();
                    Bitmap bitmap=(Bitmap)bundle.get("data");
                    fbit=bitmap;
                    objectImView.setImageBitmap(bitmap);}
                break;
            case PICK_PHOTO:if (resultCode == RESULT_OK) {
                try {
                    /**
                     * 该uri是上一个Activity返回的
                     */
                    Uri uri = data.getData();
                    Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    fbit=bit;
                    objectImView.setImageBitmap(bit);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("tag",e.getMessage());
                    Toast.makeText(this,"程序崩溃", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Log.i("liang", "失败");
            }

                break;

            default:
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE2)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,PICK_PHOTO);
            } else
            {
                // Permission Denied
                Toast.makeText(ObjectCardActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }




}

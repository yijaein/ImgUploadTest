package studios.codelight.smartlogin.IMG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import studios.codelight.smartlogin.Fragment.ThirdFragment;
import studios.codelight.smartlogin.R;

/**
 * Created by Jan on 2017-07-18.
 */

        /*
        bitmap 보내기 미진행
         */
public class IMGUpload extends AppCompatActivity implements View.OnClickListener{

    private Button uploadBn, ChooseBn;
    private EditText NANE;
    private ImageView imgView;
    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;
    private String uploadUrl = "http://jilee317.dothome.co.kr/php/updateinfo.php";
    //   private String uploadUrl = "http://127.0.0.1/htdocs/updateinfo.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imgupload);
        uploadBn = (Button)this.findViewById(R.id.uploadBn);
        ChooseBn = (Button)this.findViewById(R.id.chooseBn);
        NANE = (EditText)this.findViewById(R.id.name);
        imgView = (ImageView)this.findViewById(R.id.imageView);
        //imgview + 모양
        imgView.setImageResource(R.drawable.imgplus);

        ChooseBn.setOnClickListener(this);
        uploadBn.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.chooseBn:

                selectImage();

                break;

            case R.id.uploadBn:
                uploadImage();
                break;

        }
    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMG_REQUEST  && resultCode == RESULT_OK && data!= null)
        {
            Uri path = data.getData();





            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);

                imgView.setImageBitmap(bitmap);


                imgView.setVisibility(View.VISIBLE);


                NANE.setVisibility(View.VISIBLE);





            }catch (IOException e ){

                e.printStackTrace();
            }


        }//if end
    }


    private  void uploadImage(){
        StringRequest stringrequest = new StringRequest(Request.Method.POST, uploadUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String Response = jsonObject.getString("response");
                    Toast.makeText(IMGUpload.this,Response,Toast.LENGTH_LONG).show();
                    imgView.setImageResource(0);
                    imgView.setVisibility(View.GONE);
                    NANE.setText("");
                    NANE.setVisibility(View.GONE);




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",NANE.getText().toString().trim());
                params.put("image",imageToString(bitmap));


                return params;
            }
        };
        MySingleton.getInstance(IMGUpload.this).addToRequestQue(stringrequest);
    }



    private  String imageToString(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte[] imgBytes = byteArrayOutputStream.toByteArray();


        return Base64.encodeToString(imgBytes,Base64.DEFAULT);

    }
}

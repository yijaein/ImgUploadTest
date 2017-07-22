package studios.codelight.smartlogin.Fragment;


import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;

import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import studios.codelight.smartlogin.IMG.IMGUpload;

import studios.codelight.smartlogin.MAP.MapsActivity;

import studios.codelight.smartlogin.R;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by Jan on 2017-06-29.
 */

public class ThirdFragment extends Fragment  {
    public ImageView LogoImageView,missingImg;//상단 로고
    EditText editText1, editText2 ;//제목, 본문
    Button imgBtn,locBtn,regisBtn;// 하단 버튼 3개

    public ThirdFragment()
    {
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);



        return;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_third, container, false);

        imgBtn = (Button)layout.findViewById(R.id.imgBtn);//이미지 등록 버튼
        locBtn = (Button)layout.findViewById(R.id.locBtn);// 위치 등록 버튼
        regisBtn= (Button)layout.findViewById(R.id.regisBtn);//게시글 등록 버튼

        missingImg = (ImageView)layout.findViewById(R.id.missingImg1) ;
        /*
        IMGupload 페이지에서 사진을 올리면서 missinIMg 이미지뷰로 띄어야함
         */

        editText1 = (EditText)layout.findViewById(R.id.title);
        editText2 = (EditText)layout.findViewById(R.id.content);
        /*
        버튼 이벤트 처리
         */
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),IMGUpload.class);
                startActivity(intent);

            }
        });




        locBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);

//////////////////////////////////////

            }//onclick end
        });

        regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editText1.getText().toString();//제목
                String address = editText2.getText().toString();//내용

                InsertData task = new InsertData();
                task.execute(name,address);


                editText1.setText("");
                editText2.setText("");



            }
        });










        return layout;


    }











    /*
    DB에 넣기
     */

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait", null, true, true);

            Toast.makeText(getActivity(),"게시글이 등록되었습니다.",Toast.LENGTH_LONG).show();

        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String name = (String)params[0];
            String address = (String)params[1];

            String serverURL = "http://jilee317.dothome.co.kr/php/Test.html";
            String postParameters = "name=" + name + "&address=" + address;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setRequestProperty("content-type", "application/json");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();


                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }//doing background end



    }






    }

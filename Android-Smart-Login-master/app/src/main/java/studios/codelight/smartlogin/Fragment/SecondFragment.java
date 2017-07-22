package studios.codelight.smartlogin.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;

import studios.codelight.smartlogin.R;

/**
 * Created by Jan on 2017-06-29.
 */

public class SecondFragment extends Fragment {
    private WebView webView;
    private WebSettings webSettings;


    public SecondFragment()
    {
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_second, container, false);
        //fragment에서 findviewbyid 이렇게 사용할 것
       /*
       웹뷰 추가
        */

        webView = (WebView)layout.findViewById(R.id.webView);


        webView.setWebViewClient(new WebViewClient());
        webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://jjj4529123.dothome.co.kr/bo/index.html");





        return layout;
    }




}

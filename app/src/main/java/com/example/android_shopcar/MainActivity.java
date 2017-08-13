package com.example.android_shopcar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private TextView jiage;
    private TextView jiesuan;
    private String path = "http://api.eleteam.com/v1/cart?access_token=TW5yIGDt5rYDnualXyp6A4rdQNpZoSKQ&app_cart_cookie_id=9bd77a42ab439b618d51b99ed28f801b&user_id=158&_terminal_type=Android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getHttp();
    }
    public void getHttp(){
        RequestParams params = new RequestParams(path);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ShopCarBean shopCarBean = gson.fromJson(result, ShopCarBean.class);
                List<ShopCarBean.DataBean.CartItemsBean> cartItems = shopCarBean.getData().getCartItems();
                listView.setAdapter(new ShopCarAdapter(MainActivity.this,cartItems,jiage,jiesuan));
//                Toast.makeText(getActivity(),logBean.getMessage(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
    public void init(){
        listView = (ListView) findViewById(R.id.f3_listView);
        jiage = (TextView) findViewById(R.id.f3_jiage);
        jiesuan = (TextView) findViewById(R.id.f3_jiesuan);
    }
}

package vn.edu.poly.banhangonline_ph26291;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.edu.poly.banhangonline_ph26291.Adapter.Adapter_DonHang;
import vn.edu.poly.banhangonline_ph26291.Adapter.Adapter_GioHang;
import vn.edu.poly.banhangonline_ph26291.DTO.DTO_DonHang;
import vn.edu.poly.banhangonline_ph26291.Model.GioHang;
import vn.edu.poly.banhangonline_ph26291.Server.Server;

public class DonHangActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rcv_donhang;
    private int id_nguoidung;
    private List<DTO_DonHang>list_donhang;

    private Adapter_DonHang adapter_donHang;
    private int id_donhang,soluong;
    private String ten_sp,hinh_sp,gia_sp,tongtien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        setTitle("Đơn Hàng ");
        AnhXa();
        ActionBar();
        if(Check_Internet(this)){
            getDonHang();
        }else {
            Toast.makeText(getApplicationContext(),"không có kết nối internet",Toast.LENGTH_SHORT).show();
        }
    }
    private void AnhXa(){
        toolbar=findViewById(R.id.toolbar);
        rcv_donhang=findViewById(R.id.rcv_donhang);
        list_donhang=new ArrayList<>();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        rcv_donhang.setLayoutManager(layoutManager);
        adapter_donHang=new Adapter_DonHang(getApplicationContext(),list_donhang);
        rcv_donhang.setAdapter(adapter_donHang);


    }

    private void ActionBar(){

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

//
    private void getDonHang(){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());


        StringRequest stringRequest=new StringRequest(Request.Method.POST,Server.duongdan_getdonhang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response!=null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<response.length();i++){
                            try {
                                JSONObject jsonObject =jsonArray.getJSONObject(i);
                                id_donhang=jsonObject.getInt("id_donhang");
                                ten_sp=jsonObject.getString("ten_spmoi");
                                hinh_sp=jsonObject.getString("hinh_spmoi");
                                gia_sp=jsonObject.getString("gia_spmoi");
                                soluong=jsonObject.getInt("soluongct");
                                tongtien=jsonObject.getString("tongtien");
                                list_donhang.add(new DTO_DonHang(id_donhang,ten_sp,hinh_sp,gia_sp,soluong,tongtien));
                                adapter_donHang.notifyDataSetChanged();




                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                id_nguoidung=getIntent().getIntExtra("id_nguoidung",0);
                HashMap<String,String>param=new HashMap<>();
                param.put("id_nguoidung",String.valueOf(id_nguoidung) );
                Log.d("user",String.valueOf(id_nguoidung));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private boolean Check_Internet(Context context){
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobie= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(wifi != null && wifi.isConnected() || (mobie!= null && mobie.isConnected())){
            return  true;

        }else {
            return false;

        }
    }

}
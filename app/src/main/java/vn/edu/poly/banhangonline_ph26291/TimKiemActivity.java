package vn.edu.poly.banhangonline_ph26291;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.edu.poly.banhangonline_ph26291.Adapter.Adapter_SPTheoLoai;
import vn.edu.poly.banhangonline_ph26291.DTO.DTO_SPMoi;
import vn.edu.poly.banhangonline_ph26291.DTO.DTO_ThongTinNguoiDung;
import vn.edu.poly.banhangonline_ph26291.Server.Server;

public class TimKiemActivity extends AppCompatActivity {
private  Toolbar toolbar;
private GridView grv_timkiem;
private TextInputEditText tiLed_timkiem;
private Adapter_SPTheoLoai adapter_timkiem;
private List<DTO_SPMoi> list_timkiem;
private List<DTO_ThongTinNguoiDung>list_thongtin;
private ImageButton btn_tinkiem;
    //Sản Phẩm
    int id_spmoi=0;
    String ten_spmoi="";
    String hinh_spmoi="";
    String gia_spmoi="";
    String mota="";
    int id_loaisp_fk=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        setTitle("Tìm Kiếm");
        AnhXa();
        ActionBar();
        if(Check_Internet(this)){

            btn_tinkiem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String timkiem=tiLed_timkiem.getText().toString().trim();
                    if(timkiem.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Bạn chưa nhập tên sản phẩm",Toast.LENGTH_SHORT).show();

                    }else
                    {
                        TimKiem(timkiem);
                    }
                }
            });


        }else {
            Toast.makeText(getApplicationContext(),"Khống có kết nối internet",Toast.LENGTH_SHORT).show();
        }
    }
    private void AnhXa(){
        toolbar=findViewById(R.id.toolbar);
        grv_timkiem=findViewById(R.id.grv_tiemkiem);
        tiLed_timkiem=findViewById(R.id.tiLed_timkiem);
        btn_tinkiem=findViewById(R.id.btn_timkiem);
        int dataid=getIntent().getIntExtra("id_nguoidung",0);
        String dataemail=getIntent().getStringExtra("email");
        String dataphone=getIntent().getStringExtra("phone");

        //khởi tạo list
        list_thongtin=new ArrayList<>();
        list_thongtin.add(new DTO_ThongTinNguoiDung(dataid,dataemail,dataphone));
        list_timkiem=new ArrayList<>();
        adapter_timkiem=new Adapter_SPTheoLoai(getApplicationContext(),list_timkiem,list_thongtin);
        grv_timkiem.setAdapter(adapter_timkiem);

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
    private void TimKiem(String search){
        list_timkiem.clear();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String url= Server.duongdan_timkiem;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!=null)
                {
                    try {
                        //chuyen ket qua ve mang cac doi tuong
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < response.length(); i++){//dua vao vong lap
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                id_spmoi=jsonObject.getInt("id_spmoi");
                                ten_spmoi=jsonObject.getString("ten_spmoi");
                                hinh_spmoi=jsonObject.getString("hinh_spmoi");
                                gia_spmoi=jsonObject.getString("gia_spmoi");
                                mota=jsonObject.getString("mota");
                                id_loaisp_fk=jsonObject.getInt("id_loaisp");
                                list_timkiem.add(new DTO_SPMoi(id_spmoi,ten_spmoi,hinh_spmoi,gia_spmoi,mota,id_loaisp_fk));//dua vao mang

                                adapter_timkiem.notifyDataSetChanged();
//
                            }catch (JSONException e){
                                e.printStackTrace();

                            }
                        }
                    }
                    catch (Exception e)
                    {
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

                HashMap<String,String>param=new HashMap<>();
                param.put("ten_spmoi",search);
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
package vn.edu.poly.banhangonline_ph26291;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import vn.edu.poly.banhangonline_ph26291.Adapter.Adapter_GioHang;
import vn.edu.poly.banhangonline_ph26291.Model.GioHang;
import vn.edu.poly.banhangonline_ph26291.Server.Server;

public class ThanhToanActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btn_dathang;
    private TextInputEditText ed_location;
    private TextView tv_tongtien,tv_nbphone,tv_email;

    int soluongsp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        Anhxa();
        SoLuong();
        ActionBar();
        if(Check_Internet(this)){
            btn_dathang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String locatio=ed_location.getText().toString();
                    if(locatio.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Địa chỉ đang bỏ trống",Toast.LENGTH_SHORT).show();
                    }else{
                        // chuyển các sp trong giỏ hàng thành chuối json
                        InsertData(Server.duongdan_postdonhang);

                    }
                }
            });
        }else {
            Toast.makeText(getApplicationContext(),"không có kết nối internet",Toast.LENGTH_SHORT).show();
        }
    }
    private void Anhxa(){
        toolbar=findViewById(R.id.toolbar);
        ed_location=findViewById(R.id.tiLed_location);
        tv_tongtien=findViewById(R.id.tv_tongtienthanhtoan);
        tv_email=findViewById(R.id.tv_email);
        tv_nbphone=findViewById(R.id.tv_nbphone);
        btn_dathang=findViewById(R.id.btn_dathang);
        // set thông tin người dùng và tổng tiền
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        long tongtien=getIntent().getLongExtra("tongtien",0);
        int dataid=getIntent().getIntExtra("id_nguoidung",0);
        String dataemail=getIntent().getStringExtra("email");
        String dataphone=getIntent().getStringExtra("phone");
        tv_email.setText(dataemail);
        tv_nbphone.setText(dataphone);
        tv_tongtien.setText(decimalFormat.format(tongtien)+"Đ");
        Log.d("idnguoidung",String.valueOf(dataid));

    }
    private void SoLuong(){
         soluongsp=0;
        for(int i=0;i<GioHang.list_muahang.size();i++){
            soluongsp=soluongsp+GioHang.list_muahang.get(i).getSoluong();
        }
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
    private void InsertData(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("true")){
                    int dataid=getIntent().getIntExtra("id_nguoidung",0);
                    String dataemail=getIntent().getStringExtra("email");
                    String dataphone=getIntent().getStringExtra("phone");
                    Toast.makeText(getApplicationContext(),"Đơn hàng đã được đặt",Toast.LENGTH_SHORT).show();
                    Intent vehome=new Intent(getApplicationContext(),HomeActivity.class);
                    vehome.putExtra("id_nguoidung",dataid);
                    vehome.putExtra("email",dataemail);
                    vehome.putExtra("phone",dataphone);

                    startActivity(vehome);

                    GioHang.list_muahang.clear();
                }else if(response.trim().equals("false")){
                    Toast.makeText(getApplicationContext(),"Đơn hàng lỗi không đặt được",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getApplicationContext(),"Lỗi kết nối server",Toast.LENGTH_SHORT).show();

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
                long tongtien_donhang=getIntent().getLongExtra("tongtien",0);
                int id_nguoidung=getIntent().getIntExtra("id_nguoidung",0);
                String email_nguoidung=getIntent().getStringExtra("email");
                String numberphone_nguoidung=getIntent().getStringExtra("phone");
                String diachi=ed_location.getText().toString();


                Map<String,String>params=new HashMap<>();
                params.put("id_nguoidung", String.valueOf(id_nguoidung));
                params.put("diachi",diachi);
                params.put("sodienthoai",numberphone_nguoidung);
                params.put("emailnd",email_nguoidung);
                params.put("soluong", String.valueOf(soluongsp));
                params.put("tongtien", String.valueOf(tongtien_donhang));
                params.put("chitiet",new Gson().toJson(GioHang.list_muahang));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
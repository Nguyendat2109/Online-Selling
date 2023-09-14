package vn.edu.poly.banhangonline_ph26291;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import vn.edu.poly.banhangonline_ph26291.Server.Server;

public class DangKyActivity extends AppCompatActivity {
    private Button btn_dangky;
    private TextInputEditText ed_email,ed_nbphone,ed_user,ed_pass,ed_repass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        AnhXa();
        if(Check_Internet(this)){
            btn_dangky.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DangKy();
                }
            });
        }else {
            Toast.makeText(getApplicationContext(),"không có kết nối internet",Toast.LENGTH_SHORT).show();
        }

    }
    private void AnhXa(){
        ed_email=findViewById(R.id.tiLed_dangky_email);
        ed_nbphone=findViewById(R.id.tiLed_dangky_nbphone);
        ed_user=findViewById(R.id.tiLed_dangky_user);
        ed_pass=findViewById(R.id.tiLed_dangky_pass);
        ed_repass=findViewById(R.id.tiLed_dangky_repass);
        btn_dangky=findViewById(R.id.btn_dangky);

    }
    private void DangKy(){
        String email,nbphone,user,pass,repass;
        email=ed_email.getText().toString().trim();
        nbphone=ed_nbphone.getText().toString().trim();
        user=ed_user.getText().toString();
        pass=ed_pass.getText().toString().trim();
        repass=ed_repass.getText().toString().trim();
        if(email.isEmpty()){
            Toast.makeText(getApplicationContext(),"Không được để trống email",Toast.LENGTH_SHORT).show();
        }else if(nbphone.isEmpty()){
            Toast.makeText(getApplicationContext(),"Không được để trống numberphone",Toast.LENGTH_SHORT).show();
        }else if(user.isEmpty()){
            Toast.makeText(getApplicationContext(),"Không được để trống user",Toast.LENGTH_SHORT).show();
        }else if(pass.isEmpty()){
            Toast.makeText(getApplicationContext(),"Không được để trống pass",Toast.LENGTH_SHORT).show();
        }else if(repass.isEmpty()){
            Toast.makeText(getApplicationContext(),"Không được để trống repass",Toast.LENGTH_SHORT).show();
        }else {
            if(repass.equals(pass)){
                // set dữ liệu
                InsertData(Server.duongdan_dangkynguoidung);
            }else {
                Toast.makeText(getApplicationContext(),"repass chưa trùng khớp",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private  void InsertData(String url){
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("true")){
                    Toast.makeText(getApplicationContext(),"Thành Công",Toast.LENGTH_SHORT).show();
                    Intent dangnhap=new Intent(DangKyActivity.this,DangNhapActivity.class);
                    startActivity(dangnhap);
                }else if(response.trim().equals("emailtontai")){
                    Toast.makeText(getApplicationContext(),"Email đã tồn tại",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getApplicationContext(),"Lỗi kết nối server",Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DangKyActivity.this,"Lỗi hệ thống!",Toast.LENGTH_SHORT).show();
            }
          }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // khởi tạo đẩy dữ liệu
                String email,nbphone,user,pass,repass;
                email=ed_email.getText().toString().trim();
                nbphone=ed_nbphone.getText().toString().trim();
                user=ed_user.getText().toString();
                pass=ed_pass.getText().toString().trim();

                Map<String,String> params=new HashMap<>();
                params.put("email",email);
                params.put("numberphone",nbphone);
                params.put("user",user);
                params.put("pass",pass);



                return params;
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
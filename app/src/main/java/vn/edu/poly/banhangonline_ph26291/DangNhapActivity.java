package vn.edu.poly.banhangonline_ph26291;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.edu.poly.banhangonline_ph26291.Adapter.Adapter_LoaiSP;
import vn.edu.poly.banhangonline_ph26291.DTO.DTO_LoaiSP;
import vn.edu.poly.banhangonline_ph26291.DTO.DTO_NguoiDung;
import vn.edu.poly.banhangonline_ph26291.Server.Server;

public class DangNhapActivity extends AppCompatActivity {
private TextView tv_dangky;
private TextInputEditText ed_email,ed_pass;
private Button btn_dangnhap;
private List<DTO_NguoiDung>list_nguoidung;
private CheckBox cb_GhiDN;
int id_nguoidung=0;
String email="";
String numberphone="";
String user="";
String pass="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        AnhXa();
        if(Check_Internet(this)){

           getNguoiDung();
           btn_dangnhap.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   DangNhap();
               }
           });

        }else {
            Toast.makeText(getApplicationContext(),"không có kết nối internet",Toast.LENGTH_SHORT).show();
        }



    }
    private void AnhXa(){
        tv_dangky=findViewById(R.id.tv_dangky);
        ed_email=findViewById(R.id.tiLed_emaildangnhap);
        ed_pass=findViewById(R.id.tiLed_passdangnhap);
        btn_dangnhap=findViewById(R.id.btn_dangnhap);
        cb_GhiDN=findViewById(R.id.cb_ghi);
        //ghi nhớ đăng nhập
        SharedPreferences sharedPreferences=getSharedPreferences("UserFile",MODE_PRIVATE);
        String taikhoan=sharedPreferences.getString("taikhoan","");
        String matkhau=sharedPreferences.getString("matkhau","");
        Boolean rem=sharedPreferences.getBoolean("remember",false);
        ed_email.setText(taikhoan);
        ed_pass.setText(matkhau);
        cb_GhiDN.setChecked(rem);
        tv_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                      Intent dangky=new Intent(DangNhapActivity.this,DangKyActivity.class);
                        startActivity(dangky);
            }
        });

        list_nguoidung=new ArrayList<>();


    }

    private void DangNhap(){
        String email,pass;
        email=ed_email.getText().toString().trim();
        pass=ed_pass.getText().toString().trim();
        if(email.isEmpty()){
            Toast.makeText(getApplicationContext(),"Không được để trống email",Toast.LENGTH_SHORT).show();
        }else if(pass.isEmpty()){
            Toast.makeText(getApplicationContext(),"Không được để trống pass",Toast.LENGTH_SHORT).show();
        }else{
//                CheckNguoiDung(Server.duongdan_dangnhapnguoidung);



            for (int i=0;i<list_nguoidung.size();i++){
                Log.d("email", String.valueOf(list_nguoidung.get(i).getId_nguoidung()));
                Log.d("email",list_nguoidung.get(i).getEmail());
                Log.d("email",list_nguoidung.get(i).getPass());


                if(list_nguoidung.get(i).getEmail().equals(email)&&list_nguoidung.get(i).getPass().equals(pass)){

                    Toast.makeText(getApplicationContext(),"Thành công",Toast.LENGTH_SHORT).show();
                    remember(email,pass,cb_GhiDN.isChecked());
                    Intent home=new Intent(DangNhapActivity.this,HomeActivity.class);
                    home.putExtra("id_nguoidung",list_nguoidung.get(i).getId_nguoidung());
                    home.putExtra("email",list_nguoidung.get(i).getEmail());
                    home.putExtra("phone",list_nguoidung.get(i).getNumberphone());
                    startActivity(home);
                    return ;
                }
                 else {
                    Toast.makeText(getApplicationContext(),"Sai email hoặc password",Toast.LENGTH_SHORT).show();

                }

            }



        }
    }
    private void remember(String user,String pass,Boolean check){
        SharedPreferences pref=getSharedPreferences("UserFile",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        if(!check){
            // không lưu
            editor.clear();
        }else {
            //lưu dữ liệu
            editor.putString("taikhoan",user);
            editor.putString("matkhau",pass);
            editor.putBoolean("remember",check);

        }
        editor.commit();//lưu lại toàn bộ
    }

    private void getNguoiDung(){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.duongdan_dangnhapnguoidung, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null){
                    for (int i = 0; i < response.length(); i++)//dua vao vong lap
                    {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id_nguoidung = jsonObject.getInt("id_nguoidung");
                            email = jsonObject.getString("email");
                            numberphone = jsonObject.getString("numberphone");
                            user = jsonObject.getString("user");
                            pass= jsonObject.getString("pass");
                            list_nguoidung.add(new DTO_NguoiDung(id_nguoidung,email,numberphone,user,pass));//dua vao mang
//                            Log.d("nguoidung", String.valueOf(list_nguoidung.size()));
//

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
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
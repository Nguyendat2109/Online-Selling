package vn.edu.poly.banhangonline_ph26291;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
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

import vn.edu.poly.banhangonline_ph26291.Adapter.Adapter_SPTheoLoai;
import vn.edu.poly.banhangonline_ph26291.DTO.DTO_SPMoi;
import vn.edu.poly.banhangonline_ph26291.DTO.DTO_ThongTinNguoiDung;
import vn.edu.poly.banhangonline_ph26291.Server.Server;

public class DienThoaiActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private GridView grv_dienthoai;
    private Adapter_SPTheoLoai adapter_SPTheoLoai;
    private List<DTO_SPMoi>list_dienthoai;
    private List<DTO_ThongTinNguoiDung>list_thongtin;

    int id_dienthoai;

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
        setContentView(R.layout.activity_dien_thoai);
        setTitle("Điện Thoại");
        id_dienthoai=getIntent().getIntExtra("idloaidsanpham",1);
        Anhxa();
        ActionBar();
        if(Check_Internet(this)){


            getDienThoai();

        }else {
            Toast.makeText(getApplicationContext(),"Không có kết nối internet",Toast.LENGTH_SHORT).show();
        }
    }
    private void Anhxa(){
        grv_dienthoai=findViewById(R.id.grv_dienthoai);
        toolbar=findViewById(R.id.toolbar);
        int dataid=getIntent().getIntExtra("id_nguoidung",0);
        String dataemail=getIntent().getStringExtra("email");
        String dataphone=getIntent().getStringExtra("phone");

        //khởi tạo list
        list_thongtin=new ArrayList<>();
        list_thongtin.add(new DTO_ThongTinNguoiDung(dataid,dataemail,dataphone));
        list_dienthoai=new ArrayList<>();

        //khởi tạo adapter

        adapter_SPTheoLoai =new Adapter_SPTheoLoai(getApplicationContext(),list_dienthoai,list_thongtin);
        grv_dienthoai.setAdapter(adapter_SPTheoLoai);
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

    private  void getDienThoai(){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        //b2.Tao request
        //StringRequest(phuongThuc,DuongDan,ThanhCong,ThatBai)
        //chu ys: truyen tham so cho post
        String path=Server.duongdan_sptheotheloai;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, path, new
                Response.Listener<String>() {
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
                                        list_dienthoai.add(new DTO_SPMoi(id_spmoi,ten_spmoi,hinh_spmoi,gia_spmoi,mota,id_loaisp_fk));//dua vao mang
                                        Log.d("ditme", String.valueOf(list_dienthoai.size()));
                                        adapter_SPTheoLoai.notifyDataSetChanged();
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
            //truyền tham số
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("id_loaisp",String.valueOf(id_dienthoai));
                return param;
            }

        };
        requestQueue.add(stringRequest);
    }

}
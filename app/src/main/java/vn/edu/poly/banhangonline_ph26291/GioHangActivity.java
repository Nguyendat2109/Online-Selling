package vn.edu.poly.banhangonline_ph26291;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.banhangonline_ph26291.Adapter.Adapter_GioHang;
import vn.edu.poly.banhangonline_ph26291.DTO.DTO_SPMoi;
import vn.edu.poly.banhangonline_ph26291.EventBus.Event_TongTienAll_Moi;
import vn.edu.poly.banhangonline_ph26291.Model.GioHang;


public class GioHangActivity extends AppCompatActivity {
TextView tv_tongtienall,tv_btn_muahang;
private RecyclerView rcv_giohang;
private    Toolbar toolbar;
private Adapter_GioHang adapter_gioHang;
private long tongTienSP=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        setTitle("Giỏ Hàng");
        AnhXa();
        ActionBar();
        if(Check_Internet(this)){

            ShowGioHang();
            SumPriceProduct();
        }else {
            Toast.makeText(getApplicationContext(),"không có kết nốt internet",Toast.LENGTH_SHORT).show();
        }
    }
    private void AnhXa(){
        toolbar=findViewById(R.id.toolbar);
        tv_tongtienall=findViewById(R.id.tv_tongtienall_giohang);
        tv_btn_muahang=findViewById(R.id.tv_btn_muahang);
        rcv_giohang=findViewById(R.id.rcv_giohang);
        tv_btn_muahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dataid=getIntent().getIntExtra("id_nguoidung",0);
                String dataemail=getIntent().getStringExtra("email");
                String dataphone=getIntent().getStringExtra("phone");
                Intent thanhtoan=new Intent(GioHangActivity.this,ThanhToanActivity.class);
                thanhtoan.putExtra("tongtien",tongTienSP);
                thanhtoan.putExtra("id_nguoidung",dataid);
                thanhtoan.putExtra("email",dataemail);
                thanhtoan.putExtra("phone",dataphone);
                GioHang.list_giohang.clear();
                startActivity(thanhtoan);
            }
        });


    }
    private void ActionBar(){

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GioHang.list_muahang.clear();
                finish();
            }
        });


    }
    private void ShowGioHang(){
        rcv_giohang.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        rcv_giohang.setLayoutManager(layoutManager);
        if(GioHang.list_giohang.size()==0){
            Toast.makeText(getApplicationContext(),"Giỏ Hàng Trống",Toast.LENGTH_SHORT).show();
        }else {
            adapter_gioHang=new Adapter_GioHang(getApplicationContext(),GioHang.list_giohang);
            rcv_giohang.setAdapter(adapter_gioHang);
        }
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
    public void SumPriceProduct(){
         tongTienSP=0;
        for(int i=0;i<GioHang.list_muahang.size();i++){
            tongTienSP=tongTienSP+(GioHang.list_muahang.get(i).getGia_sp()*GioHang.list_muahang.get(i).getSoluong());
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        tv_tongtienall.setText("Tổng Tiền: "+decimalFormat.format(tongTienSP)+"Đ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //bug đã fix cho EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void SumPriceProductNew(Event_TongTienAll_Moi tongTienAll_moi){
        if(tongTienAll_moi!=null){
            SumPriceProduct();


        }
    }



}
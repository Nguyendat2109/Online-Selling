package vn.edu.poly.banhangonline_ph26291;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.banhangonline_ph26291.DTO.DTO_LoaiSP;
import vn.edu.poly.banhangonline_ph26291.DTO.DTO_SPMoi;
import vn.edu.poly.banhangonline_ph26291.Model.GioHang;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView img_chitietsp;
    private TextView tv_giachitietsp,tv_tenchitietsp,tv_motasanpham;
    private Spinner spn_dienthoai;
    private LinearLayout ln_btn_themvaogiohang;
    private DTO_SPMoi dto_spMoi;

    private NotificationBadge badge;
    private FrameLayout frame_giohang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        dto_spMoi=(DTO_SPMoi) getIntent().getSerializableExtra("chitietsp");
        int idloaisp=dto_spMoi.getId_loaisp();
        String ten_loaisp;
        if(idloaisp==1){
            ten_loaisp="Điện Thoại";
        }else if (idloaisp==2){
            ten_loaisp="LapTop";
        }else {
            ten_loaisp="";
        }
        setTitle(ten_loaisp+" ->"+dto_spMoi.getTen_spmoi());
        Anhxa();
        ActionBar();
        getInfor();
        Control();
    }


    private void Anhxa(){

        toolbar=findViewById(R.id.toolbar);
        img_chitietsp=findViewById(R.id.img_chitietsp);
        tv_tenchitietsp=findViewById(R.id.tv_tenchitietsp);
        tv_giachitietsp=findViewById(R.id.tv_giachitietsp);
        tv_motasanpham=findViewById(R.id.tv_motasanpham);
        spn_dienthoai=findViewById(R.id.spn_dienthoai);
        ln_btn_themvaogiohang=findViewById(R.id.ln_btn_themvaogiohang);
        frame_giohang=findViewById(R.id.frame_giohang);
        badge=findViewById(R.id.ntf_soluong);

        //set giá trị badge
        if(GioHang.list_giohang.size()>0){
            badge.setText(String.valueOf(GioHang.list_giohang.size()));
        }
        frame_giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dataid=getIntent().getIntExtra("id_nguoidung",0);
                String dataemail=getIntent().getStringExtra("email");
                String dataphone=getIntent().getStringExtra("phone");
                Intent giohang=new Intent(getApplicationContext(),GioHangActivity.class);
                giohang.putExtra("id_nguoidung",dataid);
                giohang.putExtra("email",dataemail);
                giohang.putExtra("phone",dataphone);
                startActivity(giohang);
            }
        });
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
    private void getInfor(){
         dto_spMoi=(DTO_SPMoi) getIntent().getSerializableExtra("chitietsp");
        tv_tenchitietsp.setText(dto_spMoi.getTen_spmoi());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        tv_giachitietsp.setText("Giá: "+decimalFormat.format(Integer.parseInt(dto_spMoi.getGia_spmoi()))+"Đ");
        Glide.with(getApplicationContext()).load(dto_spMoi.getHinh_spmoi()).into(img_chitietsp);
        Integer[] soluong= new Integer[]{1,2,3,4,5,6,7,8,9,10};
        tv_motasanpham.setText(dto_spMoi.getMota());
         ArrayAdapter<Integer> adapterspin= new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,soluong);
        spn_dienthoai.setAdapter(adapterspin);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(GioHang.list_giohang!=null){
            badge.setText(String.valueOf(GioHang.list_giohang.size()));
        }
    }
    private void Control() {
        ln_btn_themvaogiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemGioHang();
                Toast.makeText(getApplicationContext(),"Thêm vào thành công ",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void ThemGioHang(){
        if(GioHang.list_giohang.size()>0){
            boolean flag=false;
            int soluong=Integer.parseInt(spn_dienthoai.getSelectedItem().toString());
            for(int i=0;i <GioHang.list_giohang.size();i++){
                if(GioHang.list_giohang.get(i).getId_sp()==dto_spMoi.getId_spmoi()){
                    GioHang.list_giohang.get(i).setSoluong(soluong+GioHang.list_giohang.get(i).getSoluong());
                    long gia=Long.parseLong(dto_spMoi.getGia_spmoi());
                    GioHang.list_giohang.get(i).setGia_sp(gia);
                    flag=true;

                }else{

                }
            }
            if(flag==false){

                long gia=Long.parseLong(dto_spMoi.getGia_spmoi()) ;
                GioHang gioHang=new GioHang();
                gioHang.setGia_sp(gia);
                gioHang.setSoluong(soluong);
                gioHang.setId_sp(dto_spMoi.getId_spmoi());
                gioHang.setTen_sp(dto_spMoi.getTen_spmoi());
                gioHang.setHinh_sp(dto_spMoi.getHinh_spmoi());
                GioHang.list_giohang.add(gioHang);
            }

        }else{
            int soluong=Integer.parseInt(spn_dienthoai.getSelectedItem().toString());
            long gia=Long.parseLong(dto_spMoi.getGia_spmoi()) ;
            GioHang gioHang=new GioHang();
            gioHang.setGia_sp(gia);
            gioHang.setSoluong(soluong);
            gioHang.setId_sp(dto_spMoi.getId_spmoi());
            gioHang.setTen_sp(dto_spMoi.getTen_spmoi());
            gioHang.setHinh_sp(dto_spMoi.getHinh_spmoi());
            GioHang.list_giohang.add(gioHang);
        }
//        int totalItem=0;
//        for(int i=0;i<GioHang.list_giohang.size();i++){
//            totalItem=totalItem+GioHang.list_giohang.get(i).getSoluong();
//        }
        if(GioHang.list_giohang!=null){
            badge.setText(String.valueOf(GioHang.list_giohang.size()));
        }
    }
}
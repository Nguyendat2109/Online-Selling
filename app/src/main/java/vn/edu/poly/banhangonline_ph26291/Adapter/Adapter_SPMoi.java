package vn.edu.poly.banhangonline_ph26291.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.banhangonline_ph26291.ChiTietSanPhamActivity;
import vn.edu.poly.banhangonline_ph26291.DTO.DTO_SPMoi;
import vn.edu.poly.banhangonline_ph26291.DTO.DTO_ThongTinNguoiDung;
import vn.edu.poly.banhangonline_ph26291.DangNhapActivity;
import vn.edu.poly.banhangonline_ph26291.R;

public class Adapter_SPMoi extends BaseAdapter {
    Context context;
    List<DTO_SPMoi>list_spmoi;
    List<DTO_ThongTinNguoiDung>list_thongtin;
    String email,nbphone;


    public Adapter_SPMoi(Context context, List<DTO_SPMoi> list_spmoi,List<DTO_ThongTinNguoiDung>list_thongtin) {
        this.context = context;
        this.list_spmoi = list_spmoi;
        this.list_thongtin=list_thongtin;
    }

    @Override
    public int getCount() {
        return list_spmoi.size();
    }

    @Override
    public Object getItem(int position) {
        return list_spmoi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.item_spmoi,null);
            viewHolder.img_spmoi=convertView.findViewById(R.id.img_spmoi);
            viewHolder.tv_ten_spmoi=convertView.findViewById(R.id.tv_ten_spmoi);
            viewHolder.tv_gia_spmoi=convertView.findViewById(R.id.tv_gia_spmoi);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        //gắn dữ liệu
        DTO_SPMoi dto_spMoi=list_spmoi.get(position);
        viewHolder.tv_ten_spmoi.setLines(2);
        viewHolder.tv_ten_spmoi.setText(dto_spMoi.getTen_spmoi());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.tv_gia_spmoi.setText("Giá: "+decimalFormat.format(Integer.parseInt(dto_spMoi.getGia_spmoi())) +"Đ");

        //đọc ảnh online bằng Glide
        Glide.with(context).load(dto_spMoi.getHinh_spmoi()).into(viewHolder.img_spmoi);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chitietsp=new Intent(context, ChiTietSanPhamActivity.class);
                chitietsp.putExtra("chitietsp",dto_spMoi);
                chitietsp.putExtra("id_nguoidung",list_thongtin.get(0).getId_nguoidung());
                chitietsp.putExtra("email",list_thongtin.get(0).getEmail());
                chitietsp.putExtra("phone",list_thongtin.get(0).getNbphonel());
                chitietsp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(chitietsp);
            }
        });
        return convertView;
    }
    private class ViewHolder{
        ImageView img_spmoi;
        TextView tv_ten_spmoi,tv_gia_spmoi;
    }
}

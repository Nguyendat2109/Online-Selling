package vn.edu.poly.banhangonline_ph26291.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import vn.edu.poly.banhangonline_ph26291.ChiTietSanPhamActivity;
import vn.edu.poly.banhangonline_ph26291.DTO.DTO_SPMoi;
import vn.edu.poly.banhangonline_ph26291.DTO.DTO_ThongTinNguoiDung;
import vn.edu.poly.banhangonline_ph26291.R;

public class Adapter_SPTheoLoai extends BaseAdapter {
    Context context;
    List<DTO_SPMoi>list_dienthoai;
    List<DTO_ThongTinNguoiDung>list_thongtin;

    public Adapter_SPTheoLoai(Context context, List<DTO_SPMoi> list_dienthoai, List<DTO_ThongTinNguoiDung>list_thongtin) {
        this.context = context;
        this.list_dienthoai = list_dienthoai;
        this.list_thongtin=list_thongtin;
    }

    @Override
    public int getCount() {
        return list_dienthoai.size();
    }

    @Override
    public Object getItem(int position) {
        return list_dienthoai.get(position);
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
            convertView=inflater.inflate(R.layout.item_sptheoloai,null);
            viewHolder.img_dienthoai=convertView.findViewById(R.id.img_dienthoai);
            viewHolder.tv_tendt=convertView.findViewById(R.id.tv_tendt);
            viewHolder.tv_giadt=convertView.findViewById(R.id.tv_giadt);
            //set click item chuyển màn hình

            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        //gắn dữ liệu
        DTO_SPMoi dto_spMoi=list_dienthoai.get(position);
        viewHolder.tv_tendt.setLines(2);
        viewHolder.tv_tendt.setText(dto_spMoi.getTen_spmoi());
//         định dạng giá tiền
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.tv_giadt.setText("Giá: "+decimalFormat.format(Integer.parseInt(dto_spMoi.getGia_spmoi()) )+"Đ");

        //đọc ảnh online bằng Glide
        Glide.with(context).load(dto_spMoi.getHinh_spmoi()).into(viewHolder.img_dienthoai);
        //Click chuyển đến màn hình chi tiết sản phẩm
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
        ImageView img_dienthoai;
        TextView tv_tendt,tv_giadt;
    }

}

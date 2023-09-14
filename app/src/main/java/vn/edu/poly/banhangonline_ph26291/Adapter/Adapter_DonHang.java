package vn.edu.poly.banhangonline_ph26291.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import vn.edu.poly.banhangonline_ph26291.DTO.DTO_DonHang;
import vn.edu.poly.banhangonline_ph26291.R;

public class Adapter_DonHang extends RecyclerView.Adapter<Adapter_DonHang.ViewHolder> {
Context context;
List<DTO_DonHang>list_donhang;

    public Adapter_DonHang(Context context, List<DTO_DonHang> list_donhang) {
        this.context = context;
        this.list_donhang = list_donhang;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            DTO_DonHang dto_donHang=list_donhang.get(position);
            Glide.with(context).load(dto_donHang.getHinh_sp()).into(holder.img_sp_donhang);
            holder.tv_tensp_donhang.setText(dto_donHang.getTen_sp());
            holder.tv_tensp_donhang.setLines(2);
            holder.tv_soluongsp_donhang.setText("Số Lượng: "+String.valueOf(dto_donHang.getSoluongct()));
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.tv_giasp_donhang.setText("Giá: "+decimalFormat.format(Long.parseLong(dto_donHang.getGia_sp())) +"Đ");
    }

    @Override
    public int getItemCount() {
        return list_donhang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_sp_donhang;
        TextView tv_tensp_donhang,tv_soluongsp_donhang,tv_giasp_donhang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_sp_donhang=itemView.findViewById(R.id.img_sp_donhang);
            tv_tensp_donhang=itemView.findViewById(R.id.tv_tensp_donhang);
            tv_soluongsp_donhang=itemView.findViewById(R.id.tv_soluongsp_donhang);
            tv_giasp_donhang=itemView.findViewById(R.id.tv_giasp_donhang);

        }
    }
}

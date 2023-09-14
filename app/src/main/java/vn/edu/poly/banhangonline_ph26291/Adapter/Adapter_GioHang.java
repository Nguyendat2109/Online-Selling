package vn.edu.poly.banhangonline_ph26291.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import vn.edu.poly.banhangonline_ph26291.DTO.DTO_SPMoi;
import vn.edu.poly.banhangonline_ph26291.EventBus.Event_TongTienAll_Moi;
import vn.edu.poly.banhangonline_ph26291.GioHangActivity;
import vn.edu.poly.banhangonline_ph26291.Interface.ImageClick;
import vn.edu.poly.banhangonline_ph26291.Model.GioHang;
import vn.edu.poly.banhangonline_ph26291.R;

public class Adapter_GioHang extends RecyclerView.Adapter<Adapter_GioHang.ViewHolder> {
    Context context;
    List<GioHang>list_giohang;



    public Adapter_GioHang(Context context, List<GioHang> list_giohang) {
        this.context = context;
        this.list_giohang = list_giohang;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GioHang gioHang=list_giohang.get(position);

        holder.tv_tensp_giohang.setText(gioHang.getTen_sp());
        holder.tv_tensp_giohang.setLines(2);
        //định dạng giá sản phẩm
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.tv_giasp_giohang.setText("Giá: "+decimalFormat.format(gioHang.getGia_sp()) +"Đ");
        // set img
        Glide.with(context).load(gioHang.getHinh_sp()).into(holder.img_sanpham_giohang);
        holder.tv_soluong_giohang.setText(String.valueOf(gioHang.getSoluong()));
        long tongtien=gioHang.getGia_sp()* gioHang.getSoluong(); //gioHang.getGia_sp() đã bao gồm giá sp nhân với số lượng
        holder.tv_tongtiensp_giohang.setText("Tổng Tiền: "+decimalFormat.format(tongtien)+"Đ");
        // sự kiện click thêm bớt số lượng sản phẩm
        holder.setImageClick(new ImageClick() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                if(giatri==1){
                    if(list_giohang.get(pos).getSoluong()>1){
                        int soluongmoi=list_giohang.get(pos).getSoluong()-1;
                        list_giohang.get(pos).setSoluong(soluongmoi);
                        holder.tv_soluong_giohang.setText(String.valueOf(list_giohang.get(pos).getSoluong()));
                        long tongtienmoi=list_giohang.get(pos).getSoluong()*list_giohang.get(pos).getGia_sp();
                        holder.tv_tongtiensp_giohang.setText("Tổng Tiền: "+decimalFormat.format(tongtienmoi)+"Đ");
                        EventBus.getDefault().postSticky(new Event_TongTienAll_Moi());
                    }
                }else if(giatri==2){
                    if(list_giohang.get(pos).getSoluong()<10){
                        int soluongmoi=list_giohang.get(pos).getSoluong()+1;
                        list_giohang.get(pos).setSoluong(soluongmoi);
                        holder.tv_soluong_giohang.setText(String.valueOf(list_giohang.get(pos).getSoluong()));
                        long tongtienmoi=list_giohang.get(pos).getSoluong()*list_giohang.get(pos).getGia_sp();
                        holder.tv_tongtiensp_giohang.setText("Tổng Tiền: "+decimalFormat.format(tongtienmoi)+"Đ");
                        EventBus.getDefault().postSticky(new Event_TongTienAll_Moi());
                    }else {
                        Toast.makeText(context,"Số lượng đã đến giới hạn",Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        //xóa sản phẩm trong giỏ hàng
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getRootView().getContext());
                builder.setTitle("Thông Báo");
                builder.setMessage("Bạn có muốn xóa sản phẩm này không");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        GioHang.list_giohang.remove(gioHang);

                        notifyDataSetChanged();
                        EventBus.getDefault().postSticky(new Event_TongTienAll_Moi());
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        //checkbox giỏ hàng
        holder.cb_giohang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    GioHang.list_muahang.add(gioHang);
                    EventBus.getDefault().postSticky(new Event_TongTienAll_Moi());
                }else {

                    for (int i=0;i<GioHang.list_muahang.size();i++){
                        if(GioHang.list_muahang.get(i).getId_sp()==gioHang.getId_sp()){
                            GioHang.list_muahang.remove(i);
                            EventBus.getDefault().postSticky(new Event_TongTienAll_Moi());
                        }
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list_giohang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_sanpham_giohang,img_themsoluong,img_botsoluong,img_delete;
        TextView tv_tensp_giohang,tv_giasp_giohang,tv_soluong_giohang,tv_tongtiensp_giohang;
        ImageClick imageClick;
        CheckBox cb_giohang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_sanpham_giohang=itemView.findViewById(R.id.img_sanpham_giohang);
            img_themsoluong=itemView.findViewById(R.id.img_themsoluongsp);
            img_botsoluong=itemView.findViewById(R.id.img_botsoluongsp);
            img_delete=itemView.findViewById(R.id.img_delete);
            tv_tensp_giohang=itemView.findViewById(R.id.tv_tensp_giohang);
            tv_giasp_giohang=itemView.findViewById(R.id.tv_giasp_giohang);
            tv_soluong_giohang=itemView.findViewById(R.id.tv_soluongsp_giohang);
            tv_tongtiensp_giohang=itemView.findViewById(R.id.tv_tongtiensp_giohang);
            cb_giohang=itemView.findViewById(R.id.cb_giohang);
            // sự kiện click thêm bớt số lượng
            img_botsoluong.setOnClickListener(this);
            img_themsoluong.setOnClickListener(this);
        }

        public void setImageClick(ImageClick imageClick) {
            this.imageClick = imageClick;
        }

        @Override
        public void onClick(View view) {
            if(view==img_botsoluong){
                //giá trị 1 là bớt
                imageClick.onImageClick(view,getAdapterPosition(),1);
            }else if(view==img_themsoluong){
                //giá trị 2 là thêm
                imageClick.onImageClick(view,getAdapterPosition(),2);
            }
        }
    }
}

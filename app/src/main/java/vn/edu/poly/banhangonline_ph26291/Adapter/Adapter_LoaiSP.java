package vn.edu.poly.banhangonline_ph26291.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.banhangonline_ph26291.DTO.DTO_LoaiSP;
import vn.edu.poly.banhangonline_ph26291.R;

public class Adapter_LoaiSP extends BaseAdapter {
List<DTO_LoaiSP>list_loaisp;
Context context;

    public Adapter_LoaiSP(Context context,List<DTO_LoaiSP> list_loaisp) {
        this.list_loaisp = list_loaisp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list_loaisp.size();
    }

    @Override
    public Object getItem(int position) {
        return list_loaisp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        //tao view
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.item_menuloaisp,null);
            viewHolder.tv_tenloaisp=convertView.findViewById(R.id.tv_loaisp);
            viewHolder.img_loaisp=convertView.findViewById(R.id.img_loaisp);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        //gan du lieu
        DTO_LoaiSP dto_loaiSP=list_loaisp.get(position);
        viewHolder.tv_tenloaisp.setText(dto_loaiSP.getTenloaisp());
        //doc anh online bang Glide
        Glide.with(context).load(list_loaisp.get(position).getHinhloaisp()).into(viewHolder.img_loaisp);

        return convertView;
    }
    private class ViewHolder{
        ImageView img_loaisp;
        TextView tv_tenloaisp;
    }


}

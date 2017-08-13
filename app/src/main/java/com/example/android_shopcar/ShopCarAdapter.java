package com.example.android_shopcar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by 刘文艺 on 2017/8/11.
 */

public class ShopCarAdapter extends BaseAdapter {
    private Context context;
    private List<ShopCarBean.DataBean.CartItemsBean> list;
    TextView totalPrice;
    TextView order;

    public ShopCarAdapter(Context context, List<ShopCarBean.DataBean.CartItemsBean> list, TextView totalPrice, TextView order) {
        this.context = context;
        this.list = list;
        this.totalPrice = totalPrice;
        this.order = order;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.shop_item, null);
            holder = new Holder();
            holder.select = (ImageView) convertView.findViewById(R.id.shop_select);
            holder.zhanshi = (ImageView) convertView.findViewById(R.id.shop_shanshi);
            holder.delete = (ImageView) convertView.findViewById(R.id.shop_delete);

            holder.title = (TextView) convertView.findViewById(R.id.shop_title);
            holder.youhui = (TextView) convertView.findViewById(R.id.shop_youhui);
            holder.yuanjia = (TextView) convertView.findViewById(R.id.shop_yuanjia);
            holder.shu = (TextView) convertView.findViewById(R.id.shop_shu);

            holder.jian = (Button) convertView.findViewById(R.id.shop_jian);
            holder.plus = (Button) convertView.findViewById(R.id.shop_plus);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        if(list.get(position).getIs_selected()==1){
            holder.select.setImageResource(R.drawable.operator_selected);
        }else {
            holder.select.setImageResource(R.drawable.operator_unselected);
        }
        Glide.with(context).load(list.get(position).getProduct().getApp_long_image1()).into(holder.zhanshi);
        holder.title.setText(list.get(position).getProduct().getName());
        holder.shu.setText(list.get(position).getCount() + "");
        if (list.get(position).getProduct().getFeatured_price() == null) {
            holder.youhui.setText("￥" + list.get(position).getProduct().getPrice());
            holder.yuanjia.setVisibility(View.INVISIBLE);
        } else {
            holder.youhui.setText("￥" + list.get(position).getProduct().getFeatured_price());
            holder.yuanjia.setText("￥" + list.get(position).getProduct().getPrice());
        }
        holder.jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = list.get(position).getCount();
                if (count > 1) {
                    list.get(position).setCount(--count);
                    holder.shu.setText(list.get(position).getCount() + "");
                    totalPrice();
                } else {
                    Toast.makeText(context, "已达到商品最小值", Toast.LENGTH_SHORT).show();
                }


            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = list.get(position).getCount();
                if (count < 10) {
                    list.get(position).setCount(++count);
                    holder.shu.setText(list.get(position).getCount() + "");
                    totalPrice();
                } else {
                    Toast.makeText(context, "已达到商品最大值", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int is_selected = list.get(position).getIs_selected();
                if (is_selected == 0) {
                    holder.select.setImageResource(R.drawable.operator_selected);
                    list.get(position).setIs_selected(1);
                } else {
                    list.get(position).setIs_selected(0);
                    holder.select.setImageResource(R.drawable.operator_unselected);
                }
                totalPrice();
            }
        });
        return convertView;
    }

    public void totalPrice() {
        float price = 0;
        int count=0;
        for (ShopCarBean.DataBean.CartItemsBean item : list) {
            if (item.getIs_selected() == 1) {
                if (item.getProduct().getFeatured_price()==null){
                    price += Float.parseFloat(item.getProduct().getPrice())*item.getCount();
                }else {
                    price += Float.parseFloat(item.getProduct().getFeatured_price())*item.getCount();
                }
                count += item.getCount();
            }
        }
        totalPrice.setText("总价"+price);
        order.setText("去结算("+count+")");
    }

    class Holder {
        ImageView select, zhanshi, delete;
        TextView title, youhui, yuanjia, shu;
        Button jian, plus;
    }
}

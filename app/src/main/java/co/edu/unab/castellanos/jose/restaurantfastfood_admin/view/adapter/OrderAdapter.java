package co.edu.unab.castellanos.jose.restaurantfastfood_admin.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import co.edu.unab.castellanos.jose.restaurantfastfood_admin.R;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.entity.Order_products;

public class OrderAdapter extends RecyclerView.Adapter {


    private ArrayList<Order_products> products;
    private OnItemClickListener onItemClickListener;

    public OrderAdapter(ArrayList<Order_products> products) {

        this.products = products;
        this.onItemClickListener = null;
    }


    public void setProducts(ArrayList<Order_products> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivPicture;
        private TextView tvName, tvState;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPicture = itemView.findViewById(R.id.iv_pic_order);
            tvName = itemView.findViewById(R.id.tv_name_product);
            tvState = itemView.findViewById(R.id.tv_name_state);
        }

        public void enlazar(Order_products product){
            tvName.setText(product.getProduct().getName());
            //tvState.setText(order.getState()); cantidad y total
            Glide.with(ivPicture.getContext()).load(product.getProduct().getUrl_picture()).into(ivPicture);

            if (onItemClickListener!=null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(product, getAdapterPosition());
                    }
                });
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Order_products product = products.get(position);
        ProductViewHolder miHolder = (ProductViewHolder) holder;
        miHolder.enlazar(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public interface OnItemClickListener{

        void onItemClick(Order_products obj, int position);
    }
}

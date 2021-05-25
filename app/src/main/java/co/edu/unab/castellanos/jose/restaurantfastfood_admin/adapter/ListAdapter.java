package co.edu.unab.castellanos.jose.restaurantfastfood_admin.adapter;

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
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.entity.Product;

public class ListAdapter extends RecyclerView.Adapter {

    private ArrayList<Product> products;
    private OnItemClickListener onItemClickListener;

    public ListAdapter(ArrayList<Product> products) {
        this.products = products;
        this.onItemClickListener = null;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivPicture;
        private TextView tvName, tvPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPicture = itemView.findViewById(R.id.iv_picture_list);
            tvName = itemView.findViewById(R.id.tv_name_list);
            tvPrice = itemView.findViewById(R.id.tv_price_list);
        }

        public void enlazar(Product product){
            tvName.setText(product.getName());
            tvPrice.setText("Precio: " + product.getPrice());
            Glide.with(ivPicture.getContext()).load(product.getUrl_picture()).into(ivPicture);

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product product = products.get(position);
        ProductViewHolder miHolder = (ProductViewHolder) holder;
        miHolder.enlazar(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public interface OnItemClickListener{

        void onItemClick(Product obj, int position);
    }

}

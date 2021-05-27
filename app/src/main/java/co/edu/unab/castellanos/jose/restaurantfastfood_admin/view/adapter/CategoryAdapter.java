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

import co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.entity.Category;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.R;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.entity.Category;

public class CategoryAdapter extends RecyclerView.Adapter {

    private ArrayList<Category> categories;
    private OnItemClickListener onItemClickListener;

    public CategoryAdapter(ArrayList<Category> categories) {
        this.categories = categories;
        this.onItemClickListener = null;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivPicture;
        private TextView tvName, tvText;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPicture = itemView.findViewById(R.id.iv_picture_list);
            tvName = itemView.findViewById(R.id.tv_name_list);
            tvText = itemView.findViewById(R.id.tv_price_list);
        }

        public void enlazar(Category category){
            tvName.setText(String.valueOf(category.getName()));
            tvText.setText(String.valueOf(category.getText()));
            Glide.with(ivPicture.getContext()).load(category.getUrl_img()).into(ivPicture);

            if (onItemClickListener!=null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(category, getAdapterPosition());
                    }
                });
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Category category = categories.get(position);
        CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
        categoryViewHolder.enlazar(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public interface OnItemClickListener{

        void onItemClick(Category obj, int position);
    }
}

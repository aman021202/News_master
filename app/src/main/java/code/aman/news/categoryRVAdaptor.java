package code.aman.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Locale;

public class categoryRVAdaptor extends RecyclerView.Adapter<categoryRVAdaptor.ViewHolder> {
    private ArrayList<categoryRVModel> categoryRVModels;
    private Context context;
    private CategoryClickInterface categoryClickInterface;
    private ObjectInputStream.GetField picasso;

    public categoryRVAdaptor(ArrayList<categoryRVModel> categoryRVModels, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryRVModels = categoryRVModels;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public categoryRVAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_rv_item,parent,false);
        return new categoryRVAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull categoryRVAdaptor.ViewHolder holder, int position) {
        categoryRVModel categoryRVModel = categoryRVModels.get(position);
        holder.categoryTV.setText(categoryRVModel.getCategory());
        picasso.get().load(categoryRVModel.getCategoryImageUrl()).into(holder.categoryIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClickInterface.onCategoryClick(position);
            }
        });



    }

    @Override
    public int getItemCount() {
        return categoryRVModels.size();
    }
    public interface CategoryClickInterface{
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryTV;
        private ImageView categoryIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTV=itemView.findViewById(R.id.idTVCategory);
            categoryIV=itemView.findViewById(R.id.idIVCategory);
        }
    }
}

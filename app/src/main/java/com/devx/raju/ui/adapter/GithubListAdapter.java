package com.devx.raju.ui.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devx.raju.R;
import com.devx.raju.data.local.entity.GithubEntity;
import com.devx.raju.databinding.RepoListItemBinding;
import com.devx.raju.ui.custom.recyclerview.RecyclerLayoutClickListener;
import com.devx.raju.utils.AppUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GithubListAdapter extends RecyclerView.Adapter<GithubListAdapter.CustomViewHolder> {

    private Context context;
    private List<GithubEntity> items;
    private List<GithubEntity> filteredItems;
    private RecyclerLayoutClickListener listener;

    public GithubListAdapter(Context context, RecyclerLayoutClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.items = new ArrayList<>();
        this.filteredItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        RepoListItemBinding itemBinding = RepoListItemBinding.inflate(layoutInflater, parent, false);
        CustomViewHolder customItemViewHolder = new CustomViewHolder(itemBinding);

        itemBinding.cardView.setOnClickListener(v -> customItemViewHolder.onLayoutButtonClick());

        return customItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }

    public void setItems(List<GithubEntity> newItm) {
        items.clear();
        items.addAll(newItm);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public GithubEntity getItem(int position) {
        return items.get(position);
    }

    protected class CustomViewHolder extends RecyclerView.ViewHolder {

        private RepoListItemBinding binding;
        public CustomViewHolder(RepoListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(GithubEntity githubEntity) {
            Picasso.get().load(githubEntity.getOwner().getAvatarUrl())
                    .placeholder(R.drawable.ic_placeholder)
                    .into(binding.itemProfileImg);

            binding.itemTitle.setText(githubEntity.getFullName());
            binding.itemTime.setText(String.format(context.getString(R.string.item_date),
                    AppUtils.getDate(githubEntity.getCreatedAt()),
                    AppUtils.getTime(githubEntity.getCreatedAt())));

            binding.itemDesc.setText(githubEntity.getDescription());

            if(githubEntity.getLanguage() != null) {
                binding.itemImgLanguage.setVisibility(View.VISIBLE);
                binding.itemLikes.setVisibility(View.VISIBLE);
                binding.itemLikes.setText(githubEntity.getLanguage());

                GradientDrawable drawable = (GradientDrawable) context.getResources().getDrawable(R.drawable.ic_circle);
                drawable.setColor(AppUtils.getColorByLanguage(context, githubEntity.getLanguage()));
                binding.itemImgLanguage.setImageDrawable(drawable);

            } else {
                binding.itemLikes.setVisibility(View.GONE);
                binding.itemImgLanguage.setVisibility(View.GONE);
            }

        }

        private void onLayoutButtonClick() {
            listener.redirectToDetailScreen(binding.itemProfileImg, binding.itemTitle, binding.itemImgLanguage, binding.itemLikes, getItem(getLayoutPosition()));
        }

    }
}

package com.example.parstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private TextView tvUsername2;
        private ImageView ivPostImage;
        private ImageView ivProfileImage;
        private TextView tvDescription;
        private TextView tvTimeStamp;
        private ImageButton likeBtn;
        private ImageButton commentBtn;
        private ImageButton dmBtn;
        private ImageButton saveBtn;

        public ViewHolder(View itemView){
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvUsername2 = itemView.findViewById(R.id.tvUsername2);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);
            likeBtn = (ImageButton) itemView.findViewById(R.id.likeBtn);
            commentBtn = (ImageButton) itemView.findViewById(R.id.commentBtn);
            dmBtn = (ImageButton) itemView.findViewById(R.id.dmBtn);
            saveBtn = (ImageButton) itemView.findViewById(R.id.saveBtn);
        }

        public void bind(Post post) {
            //Bind the data to the view elements
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            tvUsername2.setText(post.getUser().getUsername());
            tvTimeStamp.setText(post.getTimeStamp());
            ParseFile postImage = post.getImage();
            if (postImage != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivPostImage);
            }
            ParseFile profileImage = post.getProfileImage();
            if (profileImage != null) {
                Glide.with(context).load(post.getProfileImage().getUrl()).into(ivProfileImage);
            }

            likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            commentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            dmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PostsAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
}

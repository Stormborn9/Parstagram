package fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.parstagram.EndlessRecyclerViewScrollListener;
import com.example.parstagram.Post;
import com.example.parstagram.PostsAdapter;
import com.example.parstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class PostsFragment extends Fragment {

    public PostsFragment() {
        // Required empty public constructor
    }


    protected RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    protected ProgressDialog pd;
    protected SwipeRefreshLayout swipeContainer;
    protected EndlessRecyclerViewScrollListener scrollListener;
    protected Boolean isRefreshing = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPosts = view.findViewById(R.id.rvPosts);
        swipeContainer = view.findViewById(R.id.swipeContainer);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshing = true;
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                loadPosts(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // Create data source
        allPosts = new ArrayList<>();
        // Create adapter
        adapter = new PostsAdapter(getContext(), allPosts);
        // Set adapter on RecyclerView
        rvPosts.setAdapter(adapter);
        // Set LayoutManager on RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(linearLayoutManager);

        // Set up scroll listener for endless scrolling
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                //Load the next page
                loadPosts(page);
            }
        };

        // Adds the scroll listener to RecyclerView
        rvPosts.addOnScrollListener(scrollListener);

        // Initialize Progress Dialog
        pd = new ProgressDialog(getContext());
        pd.setTitle("Loading...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);

        // Load timeline
        loadPosts(0);
    }

    // Loads timeline -- Top 20 posts
    protected void loadPosts(int page){

        Log.d("PostsFragment", String.format("Page: %s", page));

        // Show progress if this is the initial load
        if(!isRefreshing) pd.show();

        // Initialize post query
        ParseQuery<Post> postQuery = ParseQuery.getQuery(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.addDescendingOrder("createdAt");

        // Set query parameters -- Get top posts with nested users
        //postQuery.getTop().withUser().descending().paginate(page);

        // Get posts from parse server -- async
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e == null){

                    // Clear adapter only on refresh
                    if(isRefreshing) adapter.clear();

                    //Success
                    for(int i = 0; i < posts.size(); i++){
                        Log.d("CreatePostActivity", String.format("Post [%s] Username: %s Description: %s", i,
                                posts.get(i).getUser().getUsername(),
                                posts.get(i).getDescription()));
                    }
                    allPosts.addAll(posts);
                    adapter.notifyDataSetChanged();
                } else {
                    //error
                    e.printStackTrace();
                }

                // Dismiss progress dialog
                if(pd.isShowing()) pd.dismiss();

                // Reset swipe container
                if(isRefreshing){
                    swipeContainer.setRefreshing(false);
                    isRefreshing = false;
                }

            }
        });
    }


}

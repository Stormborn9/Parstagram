package fragments;

import android.util.Log;

import com.example.parstagram.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment  extends PostsFragment {

    @Override
    protected void loadPosts(int page) {
        Log.d("PostsFragment", String.format("Page: %s", page));

        // Show progress if this is the initial load
        if(!isRefreshing) pd.show();

        // Initialize post query
        ParseQuery<Post> postQuery = ParseQuery.getQuery(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
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

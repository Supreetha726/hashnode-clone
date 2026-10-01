import PostCard from "./PostCard";

function PostList({ posts, dashboard = false, onDelete }) {
  if (!posts || posts.length === 0) {
    return <div className="empty-state">No posts found.</div>;
  }

  return (
    <div className="post-list">
      {posts.map((post) => (
        <PostCard
          key={post.id}
          post={post}
          dashboard={dashboard}
          onDelete={onDelete}
        />
      ))}
    </div>
  );
}

export default PostList;
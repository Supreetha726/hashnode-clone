import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import API from "../api/axios";
import PostList from "../components/post/PostList";

function Dashboard() {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadPosts = async () => {
    setLoading(true);
    setError("");

    try {
      const response = await API.get("/api/posts/mine");
      setPosts(response.data);
    } catch (error) {
      console.error(error);
      setError("Unable to load your posts.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadPosts();
  }, []);

  const handleDelete = async (id) => {
    const confirmed = window.confirm("Delete this post?");

    if (!confirmed) return;

    try {
      await API.delete(`/api/posts/${id}`);
      setPosts((current) => current.filter((post) => post.id !== id));
    } catch (error) {
      console.error(error);
      setError("Unable to delete the post.");
    }
  };

  const drafts = posts.filter((post) => post.status === "DRAFT");
  const published = posts.filter((post) => post.status === "PUBLISHED");

  return (
    <main className="page-container">
      <div className="section-heading">
        <div>
          <p className="eyebrow">Private area</p>
          <h1>My Dashboard</h1>
          <p>Manage your drafts and published posts.</p>
        </div>
        <Link to="/editor/new" className="button primary">
          Create post
        </Link>
      </div>

      <div className="stats-grid">
        <div className="stat-card">
          <strong>{posts.length}</strong>
          <span>Total posts</span>
        </div>
        <div className="stat-card">
          <strong>{drafts.length}</strong>
          <span>Drafts</span>
        </div>
        <div className="stat-card">
          <strong>{published.length}</strong>
          <span>Published</span>
        </div>
      </div>

      {error && <div className="error-box">{error}</div>}
      {loading && <div className="page-message">Loading your posts...</div>}
      {!loading && !error && (
        <PostList posts={posts} dashboard onDelete={handleDelete} />
      )}
    </main>
  );
}

export default Dashboard;
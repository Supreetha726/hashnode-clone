import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import API from "../api/axios";
import PostList from "../components/post/PostList";

function TagPage() {
  const { slug } = useParams();
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadPosts = async () => {
      setLoading(true);
      setError("");

      try {
        const response = await API.get(`/api/tags/${slug}`);
        setPosts(response.data);
      } catch (error) {
        console.error(error);
        setError("Unable to load this tag.");
      } finally {
        setLoading(false);
      }
    };

    loadPosts();
  }, [slug]);

  return (
    <main className="page-container">
      <div className="section-heading">
        <div>
          <p className="eyebrow">Topic</p>
          <h1>#{slug}</h1>
        </div>
        <Link to="/" className="button secondary">
          All posts
        </Link>
      </div>

      {loading && <div className="page-message">Loading tag posts...</div>}
      {error && <div className="error-box">{error}</div>}
      {!loading && !error && <PostList posts={posts} />}
    </main>
  );
}

export default TagPage;

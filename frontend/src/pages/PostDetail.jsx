import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import API from "../api/axios";
import MarkdownPreview from "../components/editor/MarkdownPreview";
import TagPill from "../components/post/TagPill";

function PostDetail() {
  const { slug } = useParams();
  const [post, setPost] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadPost = async () => {
      setLoading(true);
      setError("");

      try {
        const response = await API.get(`/api/posts/${slug}`);
        setPost(response.data);
      } catch (error) {
        console.error(error);
        setError("Post not found or it is not published.");
      } finally {
        setLoading(false);
      }
    };

    loadPost();
  }, [slug]);

  if (loading) {
    return <div className="page-message">Loading post...</div>;
  }

  if (error) {
    return (
      <main className="page-container narrow">
        <div className="error-box">{error}</div>
        <Link to="/" className="button secondary">
          Back to feed
        </Link>
      </main>
    );
  }

  return (
    <main className="page-container narrow">
      {post.coverImage && (
        <img className="detail-cover" src={post.coverImage} alt={post.title} />
      )}

      <p className="eyebrow">Published post</p>
      <h1 className="post-title">{post.title}</h1>

      <div className="post-meta large">
        <Link to={`/profile/${post.author.id}`}>{post.author.name}</Link>
        <span>•</span>
        <span>{new Date(post.createdAt).toLocaleDateString()}</span>
      </div>

      <div className="tag-row detail-tags">
        {(post.tags || []).map((tag) => (
          <TagPill key={tag.id || tag.slug} tag={tag} />
        ))}
      </div>

      <MarkdownPreview content={post.content} />
    </main>
  );
}

export default PostDetail;
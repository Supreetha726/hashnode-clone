import { Link } from "react-router-dom";
import TagPill from "./TagPill";

function PostCard({ post, dashboard = false, onDelete }) {
  return (
    <article className="post-card">
      {post.coverImage && (
        <img className="post-cover" src={post.coverImage} alt={post.title} />
      )}

      <div className="post-card-body">
        <div className="post-meta">
          <span>{post.author?.name || "Unknown author"}</span>
          <span>•</span>
          <span>
            {post.createdAt
              ? new Date(post.createdAt).toLocaleDateString()
              : ""}
          </span>
        </div>

        <h2>
          <Link to={`/post/${post.slug}`}>{post.title}</Link>
        </h2>

        {post.excerpt && <p className="excerpt">{post.excerpt}</p>}

        <div className="tag-row">
          {(post.tags || []).map((tag) => (
            <TagPill key={tag.id || tag.slug} tag={tag} />
          ))}
        </div>

        {dashboard && (
          <div className="dashboard-actions">
            <span className={`status ${post.status?.toLowerCase()}`}>
              {post.status}
            </span>

            <Link className="button secondary" to={`/editor/${post.id}`}>
              Edit
            </Link>

            <button
              className="button danger"
              onClick={() => onDelete(post.id)}
            >
              Delete
            </button>
          </div>
        )}
      </div>
    </article>
  );
}

export default PostCard;

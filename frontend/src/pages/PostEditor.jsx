import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import API from "../api/axios";
import MarkdownEditor from "../components/editor/MarkdownEditor";

const initialForm = {
  title: "",
  content: "",
  excerpt: "",
  coverImage: "",
  status: "DRAFT",
  tags: "",
};

function PostEditor() {
  const { id } = useParams();
  const navigate = useNavigate();
  const editing = Boolean(id);

  const [form, setForm] = useState(initialForm);
  const [loading, setLoading] = useState(editing);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    if (!editing) return;

    const loadPost = async () => {
      try {
        const response = await API.get(`/api/posts/mine`);
        const post = response.data.find((item) => String(item.id) === String(id));

        if (!post) {
          setError("Post not found.");
          return;
        }

        setForm({
          title: post.title || "",
          content: post.content || "",
          excerpt: post.excerpt || "",
          coverImage: post.coverImage || "",
          status: post.status || "DRAFT",
          tags: (post.tags || []).map((tag) => tag.name).join(", "),
        });
      } catch (error) {
        console.error(error);
        setError("Unable to load the post.");
      } finally {
        setLoading(false);
      }
    };

    loadPost();
  }, [editing, id]);

  const updateField = (field, value) => {
    setForm((current) => ({ ...current, [field]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (!form.title.trim() || !form.content.trim()) {
      setError("Title and content are required.");
      return;
    }

    const payload = {
      title: form.title.trim(),
      content: form.content,
      excerpt: form.excerpt.trim(),
      coverImage: form.coverImage.trim(),
      status: form.status,
      tags: form.tags
        .split(",")
        .map((tag) => tag.trim())
        .filter(Boolean),
    };

    setSaving(true);

    try {
      if (editing) {
        await API.put(`/api/posts/${id}`, payload);
      } else {
        await API.post("/api/posts", payload);
      }

      navigate("/dashboard");
    } catch (error) {
      console.error(error);
      setError(error.response?.data?.message || "Unable to save the post.");
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return <div className="page-message">Loading editor...</div>;
  }

  return (
    <main className="page-container editor-page">
      <div className="section-heading">
        <div>
          <p className="eyebrow">{editing ? "Edit" : "Create"}</p>
          <h1>{editing ? "Edit Post" : "Create Post"}</h1>
        </div>
      </div>

      {error && <div className="error-box">{error}</div>}

      <form onSubmit={handleSubmit}>
        <div className="form-card wide">
          <label>Title</label>
          <input
            value={form.title}
            onChange={(e) => updateField("title", e.target.value)}
            placeholder="Your post title"
            required
          />

          <label>Excerpt</label>
          <textarea
            rows="3"
            value={form.excerpt}
            onChange={(e) => updateField("excerpt", e.target.value)}
            placeholder="Short summary of the post"
          />

          <label>Cover image URL</label>
          <input
            value={form.coverImage}
            onChange={(e) => updateField("coverImage", e.target.value)}
            placeholder="https://example.com/image.jpg"
          />

          <label>Tags</label>
          <input
            value={form.tags}
            onChange={(e) => updateField("tags", e.target.value)}
            placeholder="java, spring-boot, mysql"
          />

          <label>Status</label>
          <select
            value={form.status}
            onChange={(e) => updateField("status", e.target.value)}
          >
            <option value="DRAFT">Draft</option>
            <option value="PUBLISHED">Published</option>
          </select>
        </div>

        <MarkdownEditor
          value={form.content}
          onChange={(value) => updateField("content", value)}
        />

        <div className="editor-actions">
          <button
            type="button"
            className="button secondary"
            onClick={() => navigate("/dashboard")}
          >
            Cancel
          </button>
          <button className="button primary" disabled={saving}>
            {saving ? "Saving..." : editing ? "Update Post" : "Save Post"}
          </button>
        </div>
      </form>
    </main>
  );
}

export default PostEditor;
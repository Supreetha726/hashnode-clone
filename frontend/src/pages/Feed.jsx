import { useEffect, useState } from "react";
import { Link, useSearchParams } from "react-router-dom";
import API from "../api/axios";
import PostList from "../components/post/PostList";

function Feed() {
  const [searchParams, setSearchParams] = useSearchParams();
  const [posts, setPosts] = useState([]);
  const [search, setSearch] = useState(searchParams.get("search") || "");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadPosts = async (searchValue = "") => {
    setLoading(true);
    setError("");

    try {
      const params = searchValue.trim()
        ? { search: searchValue.trim() }
        : {};

      const response = await API.get("/api/posts", { params });
      setPosts(response.data);
    } catch (error) {
      console.error(error);
      setError("Unable to load posts.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadPosts(searchParams.get("search") || "");
  }, [searchParams]);

  const handleSearch = (e) => {
    e.preventDefault();

    if (search.trim()) {
      setSearchParams({ search: search.trim() });
    } else {
      setSearchParams({});
    }
  };

  return (
    <main className="page-container">
      <section className="hero-section">
        <div>
          <p className="eyebrow">Developer community</p>
          <h1>Share what you build.</h1>
          <p>Read and publish technical posts with Markdown and code examples.</p>
        </div>
      </section>

      <form className="search-form" onSubmit={handleSearch}>
        <input
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          placeholder="Search posts by title..."
        />
        <button className="button primary">Search</button>
        {search && (
          <button
            type="button"
            className="button secondary"
            onClick={() => {
              setSearch("");
              setSearchParams({});
            }}
          >
            Clear
          </button>
        )}
      </form>

      <div className="section-heading">
        <h2>{search ? `Search results for "${search}"` : "Latest posts"}</h2>
        <Link to="/editor/new" className="button primary">
          Write a post
        </Link>
      </div>

      {loading && <div className="page-message">Loading posts...</div>}
      {error && <div className="error-box">{error}</div>}
      {!loading && !error && <PostList posts={posts} />}
    </main>
  );
}

export default Feed;
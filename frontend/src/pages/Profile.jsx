import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import API from "../api/axios";
import PostList from "../components/post/PostList";

function Profile() {
  const { id } = useParams();

  const [profile, setProfile] = useState(null);
  const [posts, setPosts] = useState([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadProfile = async () => {
      setLoading(true);
      setError("");

      try {
        // Get user profile
        const profileResponse = await API.get(`/api/users/${id}`);

        // Get all published posts
        const postsResponse = await API.get("/api/posts");

        // Keep only posts belonging to this user
        const userPosts = postsResponse.data.filter(
          (post) => post.author?.id === Number(id)
        );

        setProfile(profileResponse.data);
        setPosts(userPosts);
      } catch (error) {
        console.error("Profile loading error:", error);

        if (error.response) {
          console.log("Status:", error.response.status);
          console.log("Response:", error.response.data);
        }

        setError("Unable to load profile.");
      } finally {
        setLoading(false);
      }
    };

    loadProfile();
  }, [id]);

  if (loading) {
    return (
      <div className="page-message">
        Loading profile...
      </div>
    );
  }

  if (error) {
    return (
      <main className="page-container">
        <div className="error-box">
          {error}
        </div>

        <Link to="/" className="button secondary">
          Back to Feed
        </Link>
      </main>
    );
  }

  if (!profile) {
    return (
      <main className="page-container">
        <div className="error-box">
          Profile not found.
        </div>
      </main>
    );
  }

  return (
    <main className="page-container">

      {/* Profile Header */}
      <section className="profile-header">

        {profile.avatarUrl ? (
          <img
            className="avatar"
            src={profile.avatarUrl}
            alt={profile.name}
          />
        ) : (
          <div className="avatar placeholder-avatar">
            {profile.name?.charAt(0)?.toUpperCase()}
          </div>
        )}

        <div className="profile-info">
          <p className="eyebrow">
            Developer Profile
          </p>

          <h1>
            {profile.name}
          </h1>

          <p>
            {profile.bio || "No bio added yet."}
          </p>

          <p className="muted">
            {profile.email}
          </p>
        </div>

        <Link
          to="/settings"
          className="button secondary"
        >
          Edit Profile
        </Link>

      </section>

      {/* Posts */}
      <div className="section-heading">
        <div>
          <p className="eyebrow">
            Published content
          </p>

          <h2>
            {profile.name}'s Posts
          </h2>
        </div>

        <span className="post-count">
          {posts.length} posts
        </span>
      </div>

      <PostList posts={posts} />

    </main>
  );
}

export default Profile;
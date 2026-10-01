import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../api/axios";
import useAuth from "../hooks/useAuth";

function ProfileSettings() {
  const { user, setUser } = useAuth();
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [bio, setBio] = useState("");
  const [avatarUrl, setAvatarUrl] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    if (!user) return;

    setName(user.name || "");
    setBio(user.bio || "");
    setAvatarUrl(user.avatarUrl || "");
  }, [user]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");
    setSaving(true);

    try {
      const response = await API.put("/api/users/me", {
        name: name.trim(),
        bio: bio.trim(),
        avatarUrl: avatarUrl.trim(),
      });

      setUser(response.data);
      setMessage("Profile updated successfully.");
    } catch (error) {
      console.error(error);
      setError(error.response?.data?.message || "Unable to update profile.");
    } finally {
      setSaving(false);
    }
  };

  return (
    <main className="page-container narrow">
      <div className="section-heading">
        <div>
          <p className="eyebrow">Account</p>
          <h1>Profile Settings</h1>
        </div>
      </div>

      <div className="form-card wide">
        {message && <div className="success-box">{message}</div>}
        {error && <div className="error-box">{error}</div>}

        <form onSubmit={handleSubmit}>
          <label>Name</label>
          <input value={name} onChange={(e) => setName(e.target.value)} required />

          <label>Email</label>
          <input value={user?.email || ""} disabled />

          <label>Bio</label>
          <textarea
            rows="5"
            maxLength="200"
            value={bio}
            onChange={(e) => setBio(e.target.value)}
            placeholder="Tell readers about yourself"
          />

          <label>Avatar URL</label>
          <input
            value={avatarUrl}
            onChange={(e) => setAvatarUrl(e.target.value)}
            placeholder="https://example.com/avatar.jpg"
          />

          <div className="editor-actions">
            <button
              type="button"
              className="button secondary"
              onClick={() => navigate(`/profile/${user.id}`)}
            >
              Cancel
            </button>
            <button className="button primary" disabled={saving}>
              {saving ? "Saving..." : "Save changes"}
            </button>
          </div>
        </form>
      </div>
    </main>
  );
}

export default ProfileSettings;
import MarkdownPreview from "./MarkdownPreview";

function MarkdownEditor({ value, onChange }) {
  return (
    <div className="editor-grid">
      <div>
        <label className="field-label">Markdown content</label>
        <textarea
          className="markdown-textarea"
          rows="22"
          value={value}
          onChange={(e) => onChange(e.target.value)}
          placeholder="# My post\n\nWrite your content here..."
        />
      </div>

      <div>
        <label className="field-label">Live preview</label>
        <MarkdownPreview content={value} />
      </div>
    </div>
  );
}

export default MarkdownEditor;
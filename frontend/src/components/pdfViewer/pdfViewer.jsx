export default function PdfViewer({ file, pages }) {
    return (
        <div style={{ color: "white" }}>
            <h2>{file}</h2>
            <p>Seiten: {pages.filter(Boolean).join(", ")}</p>

            {/* später hier PDF.js */}
        </div>
    );
}
export default function PdfModal({ isOpen, onClose, children }) {
    if (!isOpen) return null;

    return (
        <div className="pdf-modal-overlay">
            <div className="pdf-modal-content">

                <button className="pdf-close-btn" onClick={onClose}>
                    ✖
                </button>

                {children}

            </div>
        </div>
    );
}
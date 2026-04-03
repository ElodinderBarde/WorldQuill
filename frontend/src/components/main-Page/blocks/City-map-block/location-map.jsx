import { useState } from "react";
import "./location-map.css"; // eigenen Stil auslagern

export default function LocationMap({ location }) {
    const [showModal, setShowModal] = useState(false);


    const handleDoubleClick = () => {
        setShowModal(true);
    };

    const handleClose = () => {
        setShowModal(false);
    };

    const getImagePath = (location) => {
        if (!location || !location.locationMap) return null;
        return location.locationMap;
    };

    const imagePath = getImagePath(location) ;
    if (!imagePath) return (
        <div style={{ textAlign: "center", color: "#aaa" }}>
            Keine Karte verfügbar
        </div>
    );

    return (
        <div style={{ textAlign: "center" }}>
            <img
                src={imagePath}
                alt={`Bild von ${location.questlocation}`}
                onDoubleClick={handleDoubleClick}
                style={{
                    maxWidth: "100%",
                    maxHeight: "300px",
                    objectFit: "cover",
                    borderRadius: "8px",
                    cursor: "zoom-in"
                }}
            />

            {showModal && (
                <div className="modal-overlay" onClick={handleClose}>
                    <img
                        className="modal-image"
                        src={imagePath}
                        alt={`Bild von ${location.questlocation}`}
                    />
                </div>
            )}
        </div>
    );
}

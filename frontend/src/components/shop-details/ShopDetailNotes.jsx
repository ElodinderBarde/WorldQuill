import { useState, useEffect } from "react";

export default function ShopDetailNotes({ shopId }) {
    const [note, setNote] = useState("");
    const [originalNote, setOriginalNote] = useState("");
    const [isLoading, setIsLoading] = useState(true);

    // Laden der Notizen beim Mount
    useEffect(() => {
        if (!shopId || shopId === "undefined") {
            setNote("");
            setOriginalNote("");
            setIsLoading(false);
            return;
        }

        const fetchNotes = async () => {
            try {
                const response = await fetch(`http://localhost:8081/api/shops/${shopId}`);
                if (!response.ok) {
                    throw new Error(`Serverfehler: ${response.status}`);
                }
                const data = await response.json();
                setNote(data.notes || "");
                setOriginalNote(data.notes || "");
            } catch (error) {
                console.error("Fehler beim Laden der Notizen:", error);
            } finally {
                setIsLoading(false);
            }
        };
        fetchNotes();
    }, [shopId]);

    // Alert beim Verlassen der Seite bei ungespeicherten Änderungen
    useEffect(() => {
        const handleBeforeUnload = (e) => {
            if (note !== originalNote) {
                const message = "Vergiss nicht, deine Änderungen zu speichern!";
                e.preventDefault();
                e.returnValue = message; // für einige Browser notwendig
                return message; // für andere Browser notwendig
            }
        };

        window.addEventListener("beforeunload", handleBeforeUnload);
        return () => {
            window.removeEventListener("beforeunload", handleBeforeUnload);
        };
    }, [note, originalNote]);

    // Speichern der Notiz in die DB
    const handleSave = async () => {
        if (!shopId || shopId === "undefined") {
            return;
        }

        try {
            const response = await fetch(`http://localhost:8081/api/shops/${shopId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ notes: note })
            });
            if (!response.ok) {
                throw new Error(`Fehler beim Speichern: ${response.status}`);
            }
            const updatedShop = await response.json();
            setOriginalNote(updatedShop.notes || "");
            alert("Notiz erfolgreich gespeichert.");
        } catch (error) {
            console.error("Fehler beim Speichern:", error);
            alert("Fehler beim Speichern der Notiz.");
        }
    };

    if (isLoading) {
        return <p>Lade Notizen...</p>;
    }

    return (
        <div style={{
            display: "flex",
            flexDirection: "column",
            alignItems: "flex-start",
            width: "100%"
        }}>
            <h3>Notizen</h3>
            <div style={{ width: "100%" }}>
                <textarea
                    className="form-input2"
                    value={note}
                    onChange={(e) => setNote(e.target.value)}
                    rows={10}
                    style={{ width: "100%", resize: "vertical" }}
                />
            </div>
            <button className="form-button" onClick={handleSave}>Speichern</button>
        </div>
    );
}

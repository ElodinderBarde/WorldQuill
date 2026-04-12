

import "gridstack/dist/gridstack.min.css";
import {useEffect, useRef, useState} from "react";
import {GridStack} from "gridstack";
import "../components/questboard/QuestBoard.css";
import {createPortal} from "react-dom";


import Navbar from '../components/navbar';
import QuestFilter from "../components/questboard/QuestFilter.jsx";
import QuestLlist from "../components/questboard/QuestList.jsx";
import QuestCreate from "../components/questboard/QuestCreate.jsx";


export default function Quests() {
    const gridRef = useRef(null);
    const gridInstance = useRef(null);
    const [reloadTrigger, setReloadTrigger] = useState(0);
    const [filters, setFilters] = useState({
        name: "",
        questreihe: false,
        gruppe: "",
        ort: "",
        status: ""
    });


    const handleReload = () => {
        setReloadTrigger(prev => prev + 1);
    };
    const handleCloseClick = () => {
        setNoteModalText(null)
        handleReload()
    };


    const [noteModalText, setNoteModalText] = useState(null); // Zustand für Text
    useEffect(() => {
        if (noteModalText) {
            document.body.style.overflow = "hidden";
        } else {
            document.body.style.overflow = "auto";
        }
    }, [noteModalText]);

    useEffect(() => {
        const grid = GridStack.init({
            column: 12,
            margin: 5,
            cellHeight: 100,
            staticGrid: true,
            disableResize: true,
            disableDrag: true
        }, gridRef.current);
        gridInstance.current = grid;
    }, []);




    return (
        <>
            <Navbar />
            <div className="grid-stack" ref={gridRef}>
                <div className="grid-stack-item" gs-x="3" gs-y="0" gs-w="9" gs-h="1">
                    <div className="grid-stack-item-content">
                        <QuestFilter onFilterChange={setFilters} />
                    </div>
                </div>

                <div className="grid-stack-item" gs-x="3" gs-y="2" gs-w="9" gs-h="7">
                    <div className="grid-stack-item-content">
                        <QuestLlist
                            onOpenNote={(text) => setNoteModalText(text)}
                            reloadTrigger={reloadTrigger}
                            onReload={handleReload}
                            filters={filters}

                        />
                    </div>
                </div>

                <div className="grid-stack-item" gs-x="0" gs-y="0" gs-w="3" gs-h="8">
                    <div className="grid-stack-item-content">
                        <QuestCreate />
                    </div>
                </div>
            </div>

            {noteModalText &&
                createPortal(
                    <div className="modal-portal-overlay">
                        <div className="min-w-[600px] h-[400px] p-2 border rounded resize-none"   >
                            <h2 className="text-xl font-bold mb-4">Notizen zur Quest</h2>
                            <textarea
                                value={noteModalText.text}
                                onChange={(e) =>
                                    setNoteModalText({ ...noteModalText, text: e.target.value })
                                }
                                className="w-full h-64 p-2 border rounded resize-none"
                            />
                            <div className="flex justify-between mt-4">
                                <button
                                    onClick={() => {
                                        fetch(`http://localhost:8081/api/Quest/${noteModalText.id}/notes`, {
                                            method: "PUT",
                                            headers: { "Content-Type": "application/json" },
                                            body: JSON.stringify({ notes: noteModalText.text }),
                                        })
                                            .then((res) => {
                                                if (res.ok) {
                                                    handleCloseClick();
                                                } else {
                                                    alert("Fehler beim Speichern.");
                                                }

                                            });
                                    }}
                                    className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
                                >
                                    Speichern
                                </button>
                                <button
                                    onClick={handleCloseClick}
                                    className="bg-gray-800 text-white px-4 py-2 rounded hover:bg-gray-600"
                                >
                                    Schließen
                                </button>
                            </div>
                        </div>
                    </div>,
                    document.body
                )}





        </>
    );
}
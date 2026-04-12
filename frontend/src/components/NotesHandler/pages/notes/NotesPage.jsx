// src/pages/NotesPage.jsx
import Sidebar from "../../components/notes/sidebar/Sidebar.jsx";
import NoteList from "../../components/notes/noteList/NoteList.jsx";
import NoteEditor from "../../components/notes/noteEditor/NoteEditor.jsx";
import { useEffect, useState } from "react";
import api from "../../utils/api.js";
import "./NotesPage.css";
import Navbar from "@/components/navbar.jsx";
import {GridStack} from "gridstack";
import {  useRef } from "react";


export default function NotesPage() {
    const [selectedFolder, setSelectedFolder] = useState(null);
    const [selectedNote, setSelectedNote] = useState(null);
    const [selectedNoteId, setSelectedNoteId] = useState(null);
    const gridRef = useRef(null);
    const gridInstance = useRef(null)

    async function loadNotes(id) {

        if(!id)return;
        const res = await api.get(`/notes/${id}`);
        setSelectedNote(res.data);
    }

    useEffect(() => {
        setSelectedNoteId(null);
        setSelectedNote(null);
    }, [selectedFolder]);


    async function openWikiLink(title) {
        try {
            const res = await api.get(`/notes/by-title/${encodeURIComponent(title)}`);
            setSelectedNote(res.data);
        } catch {
            alert(`Note "${title}" nicht gefunden`);
        }
    }
    useEffect(() => {
        const grid = GridStack.init(
            {
                column: 12,
                margin: 5,
                cellHeight: 100,
                staticGrid: true,
                disableResize: true,
                disableDrag: true,
            },
            gridRef.current
        );

        gridInstance.current = grid;
    }, []);
    return (
        <>
            <Navbar />
            <div ref={gridRef} className="grid-stack">

                <div
                    className="grid-stack-item"
                    gs-x="0"
                    gs-y="0"
                    gs-w="3"
                    gs-h="12"
                >
                    <div className="grid-stack-item-content">
                        <Sidebar
                            selectedFolder={selectedFolder}
                            onSelectFolder={setSelectedFolder}
                            onSelectNote={(id) => {
                                setSelectedNoteId(id);
                                loadNotes(id);
                            }}
                            selectedNoteId={selectedNoteId}
                        />
                    </div>
                </div>

                <div
                    className="grid-stack-item"
                    gs-x="3"
                    gs-y="0"
                    gs-w="9"
                    gs-h="12"
                >
                    <div className="grid-stack-item-content">
                        <NoteEditor
                            note={selectedNote}
                            refreshNotes={() => loadNotes(selectedNote?.id)}
                            onWikiLink={openWikiLink}
                        />
                    </div>
                </div>

            </div>
        </>
    );
}

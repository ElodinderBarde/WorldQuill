import "./Sidebar.css";
import FolderTree from "./folderTree/FolderTree.jsx";

export default function Sidebar({ onSelectFolder, onSelectNote, selectedNoteId }) {
    return (
        <div className="sidebar">
            <FolderTree
                onSelectFolder={onSelectFolder}
                onSelectNote={onSelectNote}
                selectedNoteId={selectedNoteId}
            />
        </div>
    );
}

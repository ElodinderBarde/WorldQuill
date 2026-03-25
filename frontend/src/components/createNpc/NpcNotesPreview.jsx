import React from "react";

export default function NpcNotesPreview({ npc }) {
    if (!npc) return null;

    return (
        <div className="npc-detail">
            <img
                src="/npc-images/placeholder.png"
                alt="NPC Platzhalter"
                className="npc-placeholder-img"
            />
            <div className="npc-image-actions">
                <button type="button" disabled>Upload</button>
                <button type="button" disabled>Remove</button>
            </div>

            <h1 className="npc-name">
                {npc.firstname} {npc.lastname}
            </h1>

            <div className="npc-meta">
                Betonung: {npc.betonung}<br />
                Spracheigenheiten: {npc.talkingStyle}
            </div>

            <table className="npc-table">
                <tbody>
                <tr><th>Volk</th><td>{npc.race}</td></tr>
                <tr><th>Geschlecht</th><td>{npc.genderId}</td></tr>
                <tr><th>Alter</th><td>{npc.age}</td></tr>
                <tr><th>Hintergrund</th><td>{npc.background}</td></tr>
                </tbody>
            </table>

            <h2 className="npc-section-title"><u>Persönlichkeit:</u></h2>
            <div className="npc-text-block">
                <div>{npc.personality}</div>
                <div>{npc.otherDescription}</div>
                <div>{npc.likes}</div>
                <div>{npc.dislikes}</div>
                <div>{npc.flaw}</div>
                <div>{npc.ideals}</div>
            </div>

            <h2 className="npc-section-title"><u>Optik:</u></h2>
            <table className="npc-table">
                <tbody>
                <tr><th>Kleidung</th><td>{npc.kleidungsQuali}</td></tr>
                <tr><th>Oberteil</th><td>{npc.jackets}</td></tr>
                <tr><th>Hose</th><td>{npc.trousers}</td></tr>
                <tr><th>Haarstil</th><td>{npc.hairstyle}</td></tr>
                <tr><th>Haarfarbe</th><td>{npc.haircolor}</td></tr>
                <tr><th>Bartstil</th><td>{npc.beardstyle}</td></tr>
                </tbody>
            </table>
        </div>
    );
}
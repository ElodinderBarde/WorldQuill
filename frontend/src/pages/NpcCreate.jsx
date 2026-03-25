import { useEffect, useMemo, useRef, useState } from "react";
import Navbar from "../components/navbar";
import { GridStack } from "gridstack";
import "../components/npcBoard/npcBoard.css";
import CreateOptions from "../components/createNpc/CreateOptions.jsx";
import NpcNotesPreview from "../components/createNpc/NpcNotesPreview.jsx";

export default function NpcCreate() {
    const gridRef = useRef(null);
    const gridInstance = useRef(null);

    // 1) initialForm gehört hierhin (Parent), damit beide Komponenten denselben State sehen
    const initialForm = {
        vorname: "", vornameId: null,
        nachname: "", nachnameId: null,
        background: "", backgroundId: null,
        npc_age: "",
        race: "", raceId: null,
        genderId: "",
        classId: "",
        subclass: "", subclassId: null,
        npc_lvl: "0",
        armor: "10",
        personality: "", personalityId: null,
        otherDescription: "", otherDescriptionId: null,
        likes: "", likesId: null,
        flaw: "", flawId: null,
        dislikes: "", dislikesId: null,
        ideals: "", idealsId: null,
        betonung: "", betonungId: null,
        talkingstyle: "", talkingstyleId: null,
        notes: "",
        kleidungsQuali: "", kleidungQualiId: null,
        jackets: "", jacketsId: null,
        trousers: "", trousersId: null,
        jewellery: "", jewelleryId: null,
        hairstyle: "", hairstyleId: null,
        hairColor: "", haircolorId: null,
        beardstyle: "", beardstyleId: null,

        location: "", locationId: null,
        shopType: "", shopTypeId: null,
        shop: "", shopId: null,
        customerOrEmployee: "", customerOrEmployeeId: null,
        employeePosition: "", employeePositionId: null,
        customerRoleId: "",

        charisma: "10",
        wisdom: "10",
        strength: "10",
        constitution: "10",
        intelligence: "10",
        dexterity: "10",

        symbol: "",
    };

    const initialStats = { ATK: 10, CON: 10, WIS: 10, CHA: 10, DEX: 10, INT: 10, AC: 10 };

    // 2) Shared state
    const [form, setForm] = useState(initialForm);
    const [stats, setStats] = useState(initialStats);

    // 3) ViewModel fürs Preview (Presenter bekommt *nur* dieses Objekt)
    const npcDraft = useMemo(() => {
        return {
            firstname: form.vorname || "",
            lastname: form.nachname || "",
            race: form.race,
            gender: form.genderId,
            age: form.npc_age || null,
            background: form.background || "",
            betonung: form.betonung || "",
            talkingStyle: form.talkingstyle || "",

            personality: form.personality || "",
            otherDescription: form.otherDescription || "",
            likes: form.likes || "",
            dislikes: form.dislikes || "",
            flaw: form.flaw || "",
            ideals: form.ideals || "",

            kleidungsQuali: form.kleidungsQuali || "",
            jackets: form.jackets || "",
            trousers: form.trousers || "",
            hairstyle: form.hairstyle || "",
            haircolor: form.hairColor || "",
            beardstyle: form.beardstyle || "",

            symbol: form.symbol || "",

            armor: Number(stats.AC ?? 10),
            level: Number(form.npc_lvl ?? 1),
            npcClass: form.classId,
            subclass: form.subclass,

            stats: {
                strength: Number(stats.ATK ?? 10),
                constitution: Number(stats.CON ?? 10),
                wisdom: Number(stats.WIS ?? 10),
                charisma: Number(stats.CHA ?? 10),
                intelligence: Number(stats.INT ?? 10),
                dexterity: Number(stats.DEX ?? 10),
            },
        };
    }, [form, stats]);

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
        <div ref={gridRef} className="grid-stack">
            <Navbar />
            <main style={{ padding: "2rem" }}>
                <h2>NPC Create</h2>
            </main>

            <div
                id="createOption"
                className="grid-stack-item"
                gs-x="0"
                gs-y="0"
                gs-w="9"
                gs-h="8"
            >
                <div className="grid-stack-item-content">
                    <CreateOptions
                        form={form}
                        setForm={setForm}
                        stats={stats}
                        setStats={setStats}
                        initialForm={initialForm}
                    />
                </div>
            </div>

            <div
                id="npcNotes"
                className="grid-stack-item"
                gs-x="9"
                gs-y="0"
                gs-w="3"
                gs-h="8"
            >
                <div className="grid-stack-item-content">
                    <NpcNotesPreview npc={npcDraft} />
                </div>
            </div>
        </div>
    );
}
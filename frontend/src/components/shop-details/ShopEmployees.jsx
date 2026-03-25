import React, { useEffect, useState } from "react";
import axios from "axios";
import {useNpcNavigation} from "@/service/hooks/useNpcNavigation.js";

export default function ShopEmployees({ shopId }) {
    const [employees, setEmployees] = useState([]);
    const { handleNpcDoubleClick } = useNpcNavigation();


    useEffect(() => {
        if (!shopId || shopId === "undefined") return;
        axios.get(`http://localhost:8081/api/shops/${shopId}/employees`)
            .then(response => setEmployees(response.data))
            .catch(error => console.error("Fehler beim Laden der Mitarbeitenden:", error));
    }, [shopId]);

    return (
        <div className="npc-list-container">
            <h3>Mitarbeitende</h3>
            {employees.length === 0 ? (
                <p>Keine Mitarbeitenden gefunden.</p>
            ) : (
                <ul className="npc-list">
                    {employees.map(emp => (
                        <li
                            key={emp.npcId}
                            onDoubleClick={() => {
                                console.log(`Doppel-Klick auf NPC mit ID: ${emp.npcId}`);
                                handleNpcDoubleClick(emp.npcId);
                            }}
                            style={{ cursor: "pointer" }}
                        >
                            <details>
                                <summary>{emp.firstname} {emp.lastname}</summary>
                                <p>Geschlecht: {emp.gender}</p>
                                <p>Volk: {emp.race}</p>
                                <p>Alter: {emp.age}</p>
                                <p>Position: {emp.shopRelation}</p>
                            </details>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

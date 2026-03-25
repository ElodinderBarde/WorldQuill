import React, { useEffect, useState } from "react";
import axios from "axios";
import {useNpcNavigation} from "@/service/hooks/useNpcNavigation.js";

export default function ShopCustomers({ shopId }) {
    const [customers, setCustomers] = useState([]);
    const {handleNpcDoubleClick} = useNpcNavigation();

    useEffect(() => {
        if (!shopId || shopId === "undefined") return;
        axios.get(`http://localhost:8081/api/shops/${shopId}/customers`)
            .then(response => setCustomers(response.data))
            .catch(error => console.error("Fehler beim Laden der Kunden:", error));
    }, [shopId]);

    return (
        <div className="npc-list-container">
            <h3>Kund:innen</h3>
            {customers.length === 0 ? (
                <p>Keine Kunden gefunden.</p>
            ) : (
                <ul className="npc-list">
                    {customers.map(cus => (
                        <li key={cus.npcId}
                            onDoubleClick={() => {

                                console.log(`Doppel-Klick auf NPC mit ID: ${cus.npcId}`)
                                handleNpcDoubleClick(cus.npcId)}}

                        style = {{cursor: "pointer"}}

                        >
                            <details>
                                <summary>{cus.firstname} {cus.lastname}</summary>
                                <p>Geschlecht: {cus.gender}</p>
                                <p>Volk: {cus.race}</p>
                                <p>Alter: {cus.age}</p>
                                <p>Position: {cus.shopRelation}</p>
                            </details>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}
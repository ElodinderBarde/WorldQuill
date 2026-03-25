import React, { useMemo, useState } from "react";

export default function PresetModal({
    isOpen,
    title,
    columns,
    data,
    filterKeys,
    onSelect,
    onClose,
    selectedGenderId,
    selectedRaceId,
    debugFilterWatch = false,
}) {
    const [search, setSearch] = useState("");

    const filteredData = useMemo(() => {
        if (!isOpen) return [];
        const selectedGenderNumber = selectedGenderId == null ? null : Number(selectedGenderId);
        const selectedRaceNumber = selectedRaceId == null ? null : Number(selectedRaceId);




        const isBeardstyleModal =
            filterKeys.includes("beardstyle") ||
            columns.some((col) => toCamelCase(col) === "beardstyle") ||
            String(title || "").toLowerCase().includes("bart");



        if (isBeardstyleModal && selectedGenderNumber === 2 && selectedRaceNumber !== 2) {
            return [];
        }


        const requiresRaceFilter = filterKeys.includes("race") || isBeardstyleModal;
        const requiresGenderFilter = filterKeys.includes("gender") || isBeardstyleModal;

        const isFemaleDwarfSelection = Number(selectedGenderId) === 2 && Number(selectedRaceId) === 2;

        const genderMap = {
            1: "Male",
            2: "Female",
            3: "Other",
        };

        const normalizedSelectedGender =
            requiresGenderFilter && selectedGenderId !== undefined
                ? genderMap[selectedGenderId]
                : undefined;

        function normalizeGenderString(gender) {
            if (typeof gender !== "string") return gender;
            const lower = gender.toLowerCase();
            if (lower === "other" || lower === "unisex") return "Other";
            if (lower === "male") return "Male";
            if (lower === "female") return "Female";
            return gender;
        }

        let allowedGenders;
        if (normalizedSelectedGender === undefined) {
            allowedGenders = undefined;
        } else if (isBeardstyleModal && isFemaleDwarfSelection) {
            allowedGenders = ["Female", "Male", "Other"];
        } else if (normalizedSelectedGender === "Other") {
            allowedGenders = ["Other"];
        } else {
            // Fuer Male/Female auch Other (DB-Enum) zulassen.
            allowedGenders = [normalizedSelectedGender, "Other"];
        }

        const filteredRows = data.filter((row, rowIndex) => {
            const raceId =
                row.race?.id ??
                row.race?.race_ID ??
                row.race?.raceId ??
                row.raceId ??
                row.race_id;

            const genderName = normalizeGenderString(
                row.gender?.gendername ??
                    row.gender ??
                    row.gender?.gender_ID ??
                    row.genderId ??
                    row.gender_id
            );

            const selectedRaceNumber = selectedRaceId == null ? null : Number(selectedRaceId);
            const rowRaceNumber = raceId == null ? null : Number(raceId);

            const hasSelectedRace = selectedRaceNumber != null && !Number.isNaN(selectedRaceNumber);
            const hasRowRace = rowRaceNumber != null && !Number.isNaN(rowRaceNumber);
            const hasSelectedGender = normalizedSelectedGender !== undefined;
            const hasRowGender = genderName != null;

            const matchesRace = isBeardstyleModal
                // Race beeinflusst Beardstyle nur indirekt über den Female+Zwerg-Sonderfall.
                ? true
                : !requiresRaceFilter ||
                  selectedRaceNumber == null ||
                  rowRaceNumber == null ||
                  rowRaceNumber === selectedRaceNumber;

            const matchesGender = isBeardstyleModal
                ? !hasSelectedGender || !hasRowGender || allowedGenders.includes(genderName)
                : !requiresGenderFilter ||
                  allowedGenders === undefined ||
                  genderName == null ||
                  allowedGenders.includes(genderName);

            const matchesSearch = filterKeys.some((key) => {
                let value = row[key];
                if (key === "race" && typeof row.race === "object") value = row.race.racename;
                if (key === "gender" && typeof row.gender === "object") value = row.gender.gendername;
                if (key === "name" && row.firstname) value = row.firstname;
                if (key === "name" && row.lastname) value = row.lastname;
                return String(value).toLowerCase().includes(search.toLowerCase());
            });

            const included = matchesRace && matchesGender && matchesSearch;

            if (debugFilterWatch) {
                const rowLabel =
                    row.name ?? row.firstname ?? row.lastname ?? row.description ?? `row-${rowIndex}`;
                const watchStack = [
                    `RACE_${matchesRace ? "PASS" : "FAIL"}`,
                    `GENDER_${matchesGender ? "PASS" : "FAIL"}`,
                    `SEARCH_${matchesSearch ? "PASS" : "FAIL"}`,
                ];

                console.groupCollapsed(
                    `[FilterWatch] ${title} | ${rowLabel} | ${watchStack.join(" -> ")} | ${included ? "IN" : "OUT"}`
                );
                console.log({
                    selectedRaceId,
                    selectedGenderId,
                    raceId,
                    rowRaceNumber,
                    genderName,
                    allowedGenders,
                    search,
                    matchesRace,
                    matchesGender,
                    matchesSearch,
                });
                console.groupEnd();
            }

            return included;
        });

        if (debugFilterWatch) {
            console.log(`[FilterWatch] ${title}: ${filteredRows.length}/${data.length} sichtbar`);
        }

        return filteredRows;
    }, [isOpen, filterKeys, columns, title, selectedGenderId, selectedRaceId, data, search, debugFilterWatch]);

    if (!isOpen) return null;

    return (
        <div className="modal-overlay">
            <div className="modal-box" style={{ maxHeight: "80vh", overflow: "hidden", display: "flex", flexDirection: "column" }}>
                <h2>{title}</h2>

                <input
                    type="text"
                    placeholder="Suche..."
                    value={search}
                    onChange={(e) => setSearch(e.target.value)}
                    style={{ width: "100%", marginBottom: "1rem" }}
                />
                <div style={{ overflowY: "auto", border: "1px solid #444" }}>
                    <table>
                        <thead>
                            <tr>
                                {columns.map((col, idx) => (
                                    <th key={idx}>{col}</th>
                                ))}
                            </tr>
                        </thead>
                        <tbody>
                            {filteredData.map((row, rowIndex) => (
                                <tr key={rowIndex} onClick={() => onSelect(row)} style={{ cursor: "pointer" }}>
                                    {columns.map((col, colIndex) => {
                                        const key = toCamelCase(col);
                                        let value = row[key];

                                        if (key === "race" && row.race?.racename) value = row.race.racename;
                                        if (key === "gender" && row.gender?.gendername) value = row.gender.gendername;
                                        if (key === "firstname") value = row.firstname;
                                        if (key === "lastname") value = row.lastname;
                                        if (key === "background") value = row.name;

                                        if (key === "personality") value = row.description;
                                        if (key === "otherDescription") value = row.description;
                                        if (key === "likes") value = row.description;
                                        if (key === "dislikes") value = row.description;
                                        if (key === "ideals") value = row.description;
                                        if (key === "betonung") value = row.betonung;
                                        if (key === "talkingstyle") value = row.description;
                                        if (key === "flaw") value = row.name;

                                        if (key === "kleidungsQuali") value = row.description;
                                        if (key === "jackets") value = row.name;
                                        if (key === "trousers") value = row.name;
                                        if (key === "jewellery") value = row.name;
                                        if (key === "hairstyle") value = row.name;
                                        if (key === "hairColor") value = row.name;
                                        if (key === "beardstyle") value = row.name;

                                        return <td key={colIndex}>{value}</td>;
                                    })}
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
                <button onClick={onClose}>Abbrechen</button>
            </div>
        </div>
    );
}

function toCamelCase(text) {
    return text.charAt(0).toLowerCase() + text.slice(1);
}

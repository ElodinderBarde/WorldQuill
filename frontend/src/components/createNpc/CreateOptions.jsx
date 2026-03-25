import "./CreateNpc.css";
import React, { useEffect, useMemo, useState } from "react";
import PresetModal from "./PresetModal";
import axios from "axios";

export default function CreateOptions({ form, setForm, stats, setStats, initialForm }) {
    const [modalOpen, setModalOpen] = useState(false);
    const [modalField, setModalField] = useState(null);

    // Dropdown-Daten
    const [races, setRaces] = useState([]);
    const [genders, setGenders] = useState([]);
    const [classes, setClasses] = useState([]);
    const [subclass, setSubclass] = useState([]);
    const [locations, setLocations] = useState([]);
    const [shopTypes, setShopTypes] = useState([]);
    const [shop, setShop] = useState([]);
    const [employeeRoles, setEmployeeRoles] = useState([]);
    const [customerRoles, setCustomerRoles] = useState([]);
    const [firstname, setFirstname] = useState([]);
    const [lastname, setLastname] = useState([]);
    const [personality, setPersonality] = useState([]);
    const [otherDescription, setOtherDescription] = useState([]);
    const [likes, setLikes] = useState([]);
    const [flaw, setFlaw] = useState([]);
    const [dislikes, setDislikes] = useState([]);
    const [ideals, setIdeals] = useState([]);
    const [kleidungsQuali, setKleidungsQuali] = useState([]);
    const [hairColor, setHairColor] = useState([]);
    const [jackets, setJackets] = useState([]);
    const [trousers, setTrousers] = useState([]);
    const [hairstyle, setHairstyle] = useState([]);
    const [beardstyle, setBeardstyle] = useState([]);
    const [background, setBackground] = useState([]);
    const [betonung, setBetonung] = useState([]);
    const [talkingstyle, setTalkingstyle] = useState([]);
    const [jewellery, setJewellery] = useState([]);
    const [symbol, setSymbol] = useState([]);

    // Logging wie vorher (ohne Weglassen)
    useEffect(() => {
        const selectedValues = {
            race: form.race,
            raceId: form.raceId,
            genderId: form.genderId,
            classId: form.classId,
            subclass: form.subclass,
            subclassId: form.subclassId,
            location: form.location,
            locationId: form.locationId,
            shopType: form.shopType,
            shopTypeId: form.shopTypeId,
            shop: form.shop,
            shopId: form.shopId,
            customerOrEmployee: form.customerOrEmployee,
            employeePosition: form.employeePosition,
            employeePositionId: form.employeePositionId,
            customerRoleId: form.customerRoleId,
            symbol: form.symbol,
            vorname: form.vorname,
            vornameId: form.vornameId,
            nachname: form.nachname,
            nachnameId: form.nachnameId,
            background: form.background,
            backgroundId: form.backgroundId,
            personality: form.personality,
            personalityId: form.personalityId,
            otherDescription: form.otherDescription,
            otherDescriptionId: form.otherDescriptionId,
            likes: form.likes,
            likesId: form.likesId,
            dislikes: form.dislikes,
            dislikesId: form.dislikesId,
            ideals: form.ideals,
            idealsId: form.idealsId,
            flaw: form.flaw,
            flawId: form.flawId,
            betonung: form.betonung,
            betonungId: form.betonungId,
            talkingstyle: form.talkingstyle,
            talkingstyleId: form.talkingstyleId,
            kleidungsQuali: form.kleidungsQuali,
            kleidungQualiId: form.kleidungQualiId,
            jackets: form.jackets,
            jacketsId: form.jacketsId,
            trousers: form.trousers,
            trousersId: form.trousersId,
            jewellery: form.jewellery,
            jewelleryId: form.jewelleryId,
            hairstyle: form.hairstyle,
            hairstyleId: form.hairstyleId,
            hairColor: form.hairColor,
            haircolorId: form.haircolorId,
            beardstyle: form.beardstyle,
            beardstyleId: form.beardstyleId,
        };

        console.log("Aktuelle selected values (alle waehlbaren Felder):");
        console.table(selectedValues);
        console.log(JSON.stringify(selectedValues, null, 2));
    }, [form]);

    const resolveOrCreateId = async ({ apiName, value, idKey, matchField, contextFilter }) => {
        if (!value || !value.trim()) return null;

        const normalize = (v) => String(v ?? "").trim().toLowerCase();
        const extractId = (entry) => entry?.[idKey] ?? entry?.id ?? entry?.ID ?? null;
        const expectedValue = normalize(value);

        const getNameCandidates = (entry, key) => {
            const extraByKey = {
                firstname: ["firstname", "first_name", "name"],
                lastname: ["lastname", "last_name", "name"],
                description: ["description", "name"],
                betonung: ["betonung", "description", "name"],
                name: ["name", "description"],
            };

            const keys = extraByKey[key] || [key, "name", "description"];
            return keys.map((k) => entry?.[k]).filter((v) => typeof v === "string");
        };

        const { data } = await axios.get(`/api/${apiName}`);
        const byName = data.find((entry) =>
            getNameCandidates(entry, matchField).some((candidate) => normalize(candidate) === expectedValue)
        );

        const existing = data.find((entry) => {
            const sameName = getNameCandidates(entry, matchField).some(
                (candidate) => normalize(candidate) === expectedValue
            );
            if (!sameName) return false;
            return contextFilter ? contextFilter(entry) : true;
        });

        if (existing) {
            const existingId = extractId(existing);
            if (existingId != null) return existingId;
        }

        if (byName) {
            const fallbackId = extractId(byName);
            if (fallbackId != null) {
                console.warn(
                    `Eintrag fuer ${apiName} mit gleichem Namen existiert bereits; verwende vorhandene ID ${fallbackId}.`
                );
                return fallbackId;
            }
        }

        const postData = { [matchField]: value };
        const { data: created } = await axios.post(`/api/${apiName}`, postData);

        const createdId = extractId(created);
        if (createdId == null) {
            throw new Error(`API ${apiName} returned no ${idKey}. Response: ` + JSON.stringify(created));
        }
        return createdId;
    };

    const rollAbove = (min) => {
        let val;
        do {
            val = Math.floor(Math.random() * 18) + 3;
        } while (val <= min);
        return val;
    };

    const rollStatsWithTotalConstraint = (minValue, maxTotal) => {
        let nextStats;
        let sum;
        do {
            nextStats = {
                ATK: rollAbove(minValue),
                CON: rollAbove(minValue),
                WIS: rollAbove(minValue),
                CHA: rollAbove(minValue),
                INT: rollAbove(minValue),
                DEX: rollAbove(minValue),
                AC: rollAbove(minValue),
            };
            sum =
                nextStats.ATK +
                nextStats.CON +
                nextStats.WIS +
                nextStats.CHA +
                nextStats.INT +
                nextStats.DEX +
                nextStats.AC;
            console.log(`Summe der gewürfelten Werte: ${sum}`);
        } while (sum > maxTotal);
        console.log("wert gesetzt");
        return nextStats;
    };

    useEffect(() => {
        if (form.classId) {
            axios.get(`/api/npcs/subclasses/byClass/${form.classId}`).then((res) => {
                console.log("Subklassen geladen:", res.data);
                setSubclass(res.data);
            });
        } else {
            console.log("Keine Klasse gewählt, Subklassen geleert");
            setSubclass([]);
        }
    }, [form.classId]);

    useEffect(() => {
        axios.get("/api/Race").then((res) => setRaces(res.data));
        axios.get("/api/Gender").then((res) => setGenders(res.data));
        axios.get("/api/npc-class").then((res) => setClasses(res.data));
        axios.get("/api/locations").then((res) => setLocations(res.data));
        axios.get("/api/ShopType").then((res) => setShopTypes(res.data));
        axios.get("/api/shops").then((res) => setShop(res.data));
        axios.get("/api/ShopEmployee").then((res) => setEmployeeRoles(res.data));
        axios.get("/api/ShopCustomer").then((res) => setCustomerRoles(res.data));
        axios.get("/api/Firstname").then((res) => setFirstname(res.data));
        axios.get("/api/Lastname").then((res) => setLastname(res.data));
        axios.get("/api/Personality").then((res) => setPersonality(res.data));
        axios.get("/api/OtherDescription").then((res) => setOtherDescription(res.data));
        axios.get("/api/Likes").then((res) => setLikes(res.data));
        axios.get("/api/Dislikes").then((res) => setDislikes(res.data));
        axios.get("/api/Ideals").then((res) => setIdeals(res.data));
        axios.get("/api/KleidungQuali").then((res) => setKleidungsQuali(res.data));
        axios.get("/api/Jackets").then((res) => setJackets(res.data));
        axios.get("/api/Trousers").then((res) => setTrousers(res.data));
        axios.get("/api/Hairstyle").then((res) => setHairstyle(res.data));
        axios.get("/api/Haircolor").then((res) => setHairColor(res.data));
        axios.get("/api/Beardstyle").then((res) => setBeardstyle(res.data));
        axios.get("/api/background").then((res) => setBackground(res.data));
        axios.get("/api/Betonung").then((res) => setBetonung(res.data));
        axios.get("/api/TalkingStyle").then((res) => setTalkingstyle(res.data));
        axios.get("/api/Jewellery").then((res) => setJewellery(res.data));
        axios.get("/api/Flaw").then((res) => setFlaw(res.data));
        axios.get("/api/stats").then((res) => setStats(res.data));
        axios.get("/api/symbol").then((res) => setSymbol(res.data));
        axios.get("/api/Subclass").then((res) => setSubclass(res.data));
    }, [setStats]);

    // Filtern der Shoptypen
    const availableShopTypes = shopTypes.filter((type) =>
        shop.some((s) => s.locationId === parseInt(form.location, 10) && s.shopTypeId === type.id)
    );

    // Filtern der Shops
    const availableShops = (shop || []).filter(
        (s) =>
            s.locationId != null &&
            s.shopTypeId != null &&
            s.locationId === parseInt(form.location, 10) &&
            s.shopTypeId === parseInt(form.shopType, 10)
    );

    const handleChange = (e) => {
        const { name, value } = e.target;

        const numericFields = [
            "race",
            "genderId",
            "classId",
            "subclass",
            "location",
            "shopType",
            "shop",
            "employeePosition",
            "customerRoleId",
        ];

        const mirroredIdFields = {
            race: "raceId",
            subclass: "subclassId",
            location: "locationId",
            shopType: "shopTypeId",
            shop: "shopId",
            employeePosition: "employeePositionId",
        };

        if (numericFields.includes(name)) {
            const parsed = value === "" ? null : Number.parseInt(value, 10);
            const parsedValue = Number.isNaN(parsed) ? null : parsed;

            // Sonderfall: Geschlecht geändert -> abhängige Felder leeren
            if (name === "genderId") {
                setForm((prev) => ({
                    ...prev,
                    genderId: parsedValue,

                    jackets: "",
                    trousers: "",
                    beardstyle: "",
                    hairstyle: "",

                    jacketsId: null,
                    trousersId: null,
                    beardstyleId: null,
                    hairstyleId: null,
                }));
                return;
            }

            // Race ist bei dir Spezialfall: string + raceId spiegeln (wie vorher)
            if (name === "race") {
                setForm((prev) => ({
                    ...prev,
                    race: value,
                    raceId: parsedValue,
                }));
                return;
            }

            // subclass ist bei dir string + subclassId spiegeln (wie vorher)
            if (name === "subclass") {
                setForm((prev) => ({
                    ...prev,
                    subclass: value,
                    subclassId: parsedValue,
                }));
                return;
            }

            // normaler numeric-flow
            if (mirroredIdFields[name]) {
                setForm((prev) => ({
                    ...prev,
                    [name]: value,
                    [mirroredIdFields[name]]: parsedValue,
                }));
            } else {
                setForm((prev) => ({
                    ...prev,
                    [name]: parsedValue,
                }));
            }
        } else {
            setForm((prev) => ({
                ...prev,
                [name]: value,
            }));
        }
    };

    const renderCell = (label, value, onChange) => (
        <div className="stat-cell">
            <label>{label}</label>
            <input
                type="number"
                value={value ?? ""}
                onChange={(e) => onChange(label, e.target.value)}
                style={{ width: "60px", marginLeft: "5px" }}
            />
        </div>
    );

    const handleStatChange = (key, val) => {
        setStats((prev) => ({ ...prev, [key]: parseInt(val, 10) || 0 }));
    };

    const createNpc = async () => {
        try {
            const isEmptySelection = (v) => v === null || v === undefined || v === "" || v === 0 || v === "0";

            // Guard-Logik
            if (isEmptySelection(form.race) || isEmptySelection(form.genderId)) {
                alert("Bitte zuerst Volk und Geschlecht waehlen.");
                return;
            }

            if (form.customerOrEmployee === "Customer" && isEmptySelection(form.customerRoleId)) {
                alert("Bitte einen Kundentyp waehlen.");
                return;
            }

            if (form.customerOrEmployee === "Employee" && isEmptySelection(form.employeePosition)) {
                alert("Bitte eine Mitarbeiter-Position waehlen.");
                return;
            }

            const firstnameId = await resolveOrCreateId({
                apiName: "Firstname",
                value: form.vorname,
                idKey: "firstname_ID",
                matchField: "firstname",
                contextFilter: (entry) => {
                    const raceId = Number(entry?.raceId ?? entry?.race_id ?? entry?.race?.id);
                    const genderId = Number(entry?.genderId ?? entry?.gender_id ?? entry?.gender?.id);
                    return raceId === Number(form.raceId) && genderId === Number(form.genderId);
                },
            });

            const lastnameId = await resolveOrCreateId({
                apiName: "Lastname",
                value: form.nachname,
                idKey: "lastname_ID",
                matchField: "lastname",
                contextFilter: (entry) => {
                    const raceId = Number(entry?.raceId ?? entry?.race_id ?? entry?.race?.id);
                    return raceId === Number(form.raceId);
                },
            });

            const backgroundId = await resolveOrCreateId({
                apiName: "background",
                value: form.background,
                idKey: "background_ID",
                matchField: "name",
            });

            const personalityId = await resolveOrCreateId({
                apiName: "Personality",
                value: form.personality,
                idKey: "personality_ID",
                matchField: "description",
            });

            const otherDescriptionId = await resolveOrCreateId({
                apiName: "OtherDescription",
                value: form.otherDescription,
                idKey: "otherDescription_ID",
                matchField: "description",
            });

            const likesId = await resolveOrCreateId({
                apiName: "Likes",
                value: form.likes,
                idKey: "likes_ID",
                matchField: "description",
            });

            const flawId = await resolveOrCreateId({
                apiName: "Flaw",
                value: form.flaw,
                idKey: "flaw_ID",
                matchField: "name",
            });

            const dislikesId = await resolveOrCreateId({
                apiName: "Dislikes",
                value: form.dislikes,
                idKey: "dislikes_ID",
                matchField: "description",
            });

            const idealsId = await resolveOrCreateId({
                apiName: "Ideals",
                value: form.ideals,
                idKey: "ideals",
                matchField: "description",
            });

            const kleidungQualiId = await resolveOrCreateId({
                apiName: "KleidungQuali",
                value: form.kleidungsQuali,
                idKey: "kleidungsQuali",
                matchField: "description",
            });

            const jacketsId = await resolveOrCreateId({
                apiName: "Jackets",
                value: form.jackets,
                idKey: "jackets_ID",
                matchField: "name",
            });

            const trousersId = await resolveOrCreateId({
                apiName: "Trousers",
                value: form.trousers,
                idKey: "trousers_ID",
                matchField: "name",
            });

            const jewelleryId = await resolveOrCreateId({
                apiName: "Jewellery",
                value: form.jewellery,
                idKey: "jewellery_ID",
                matchField: "name",
            });

            const hairstyleId = await resolveOrCreateId({
                apiName: "Hairstyle",
                value: form.hairstyle,
                idKey: "hairstyle_ID",
                matchField: "name",
            });

            const haircolorId = await resolveOrCreateId({
                apiName: "Haircolor",
                value: form.hairColor,
                idKey: "haircolor_ID",
                matchField: "name",
            });

            const beardstyleId = await resolveOrCreateId({
                apiName: "Beardstyle",
                value: form.beardstyle,
                idKey: "beardstyle_ID",
                matchField: "name",
            });

            const betonungId = await resolveOrCreateId({
                apiName: "Betonung",
                value: form.betonung,
                idKey: "betonung_ID",
                matchField: "betonung",
            });

            const talkingstyleId = await resolveOrCreateId({
                apiName: "TalkingStyle",
                value: form.talkingstyle,
                idKey: "talkingstyle_ID",
                matchField: "description",
            });

            const payload = {
                firstnameId,
                lastnameId,
                backgroundId,
                genderId: form.genderId === "" ? null : parseInt(form.genderId, 10),
                npc_age: form.npc_age === "" ? null : parseInt(form.npc_age, 10),
                raceId: parseInt(form.race, 10) || null,
                levelId: parseInt(form.npc_lvl, 10) || 1,
                classId: form.classId ? parseInt(form.classId, 10) : null,
                subclassId: form.subclassId,
                personalityId,
                otherDescriptionId,
                likesId,
                dislikesId,
                idealsId,
                flawId,
                armorId: stats.AC ?? 10,
                kleidungQualiId,
                jacketsId,
                trousersId,
                jewelleryId,
                hairstyleId,
                haircolorId,
                beardstyleId,
                betonungId,
                talkingstyleId,
                notes: form.notes || null,
                strength: stats.ATK ?? 10,
                constitution: stats.CON ?? 10,
                wisdom: stats.WIS ?? 10,
                charisma: stats.CHA ?? 10,
                intelligence: stats.INT ?? 10,
                dexterity: stats.DEX ?? 10,

                shopId: form.shop ? parseInt(form.shop, 10) : null,
                shopCustomerRole:
                    form.customerRoleId == null || form.customerRoleId === "" ? null : parseInt(form.customerRoleId, 10),
                shopEmployeeRole:
                    form.employeePosition == null || form.employeePosition === ""
                        ? null
                        : parseInt(form.employeePosition, 10),
                symbol: form.symbol || null,
            };

            console.log("location::", form.location);
            console.log("employeeID:", form.employeePosition);
            console.log("customerRoleId im State:", form.customerRoleId, typeof form.customerRoleId);
            console.log("shop", form.shop);

            console.log("FINAL PAYLOAD:", JSON.stringify(payload, null, 2));
            console.log("Finaler Payload:", payload);

            await axios.post("/api/npcs", payload);
            alert("NPC erfolgreich erstellt!");

            // Reset erfolgt jetzt über Parent-state
            if (typeof initialForm === "object" && initialForm) {
                setForm(initialForm);
            }

            setStats((prev) => ({
                ATK: prev.ATK ?? 10,
                CON: prev.CON ?? 10,
                WIS: prev.WIS ?? 10,
                CHA: prev.CHA ?? 10,
                INT: prev.INT ?? 10,
                DEX: prev.DEX ?? 10,
                AC: 10,
            }));
        } catch (err) {
            console.error(err);
            alert("Fehler beim Erstellen des NPCs.");
        }
    };

    const openPreset = (field) => {
        setModalField(field);
        setModalOpen(true);
    };

    // bleibt drin (auch wenn du es später evtl. in Parent brauchst)
    const npcDraft = useMemo(() => {
        return {
            firstname: form.vorname || "",
            lastname: form.nachname || "",
            race: form.race,
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

    const getColumnsForField = (field) => {
        switch (field) {
            case "vorname":
                return ["firstname", "race", "gender"];
            case "nachname":
                return ["lastname", "race"];
            case "background":
                return ["background"];

            case "personality":
                return ["personality"];
            case "otherDescription":
                return ["otherDescription"];
            case "likes":
                return ["likes"];
            case "dislikes":
                return ["dislikes"];
            case "ideals":
                return ["ideals"];
            case "flaw":
                return ["flaw"];
            case "betonung":
                return ["betonung"];
            case "talkingstyle":
                return ["talkingstyle"];

            case "kleidungsQuali":
                return ["kleidungsQuali"];
            case "jackets":
                return ["jackets", "gender"];
            case "trousers":
                return ["trousers", "gender"];
            case "jewellery":
                return ["jewellery"];
            case "hairstyle":
                return ["hairstyle", "gender"];
            case "hairColor":
                return ["hairColor"];
            case "beardstyle":
                return ["beardstyle", "race", "gender"];
            default:
                return ["Name"];
        }
    };

    const getFilterKeysForField = (field) => {
        switch (field) {
            case "vorname":
                return ["firstname", "race", "gender"];
            case "nachname":
                return ["lastname", "race"];

            case "personality":
                return ["personality"];
            case "otherDescription":
                return ["otherDescription"];
            case "likes":
                return ["likes"];
            case "dislikes":
                return ["dislikes"];
            case "ideals":
                return ["ideals"];
            case "flaw":
                return ["flaw"];
            case "betonung":
                return ["betonung"];
            case "talkingstyle":
                return ["talkingstyle"];

            case "kleidungsQuali":
                return ["kleidungsQuali"];
            case "jackets":
                return ["jackets", "gender"];
            case "trousers":
                return ["trousers", "gender"];
            case "jewellery":
                return ["jewellery"];
            case "hairstyle":
                return ["hairstyle", "gender"];
            case "hairColor":
                return ["hairColor"];
            case "beardstyle":
                return ["beardstyle", "race", "gender"];

            default:
                return ["Name"];
        }
    };

    return (
        <div className="npc-options-grid">
            {/* LINKE SPALTE: Details */}
            <div className="left-column">
                <div className="field-row">
                    <label>Volk:</label>
                    <select name="race" value={form.race} onChange={handleChange}>
                        <option value="">Wähle Volk</option>
                        {races.map((race) => (
                            <option key={race.id} value={race.id}>
                                {race.racename}
                            </option>
                        ))}
                    </select>
                </div>

                <div className="field-row">
                    <label>Geschlecht</label>
                    <select name="genderId" value={form.genderId ?? ""} onChange={handleChange}>
                        <option value="">Wähle ein Geschlecht</option>
                        {genders.map((g) => (
                            <option key={g.id} value={g.id}>
                                {g.gendername}
                            </option>
                        ))}
                    </select>
                </div>

                <div className="field-row">
                    <label>Vorname:</label>
                    <input name="vorname" value={form.vorname} onChange={handleChange} disabled={!form.raceId || !form.genderId} />
                    <button onClick={() => openPreset("vorname")} disabled={!form.raceId}>
                        Preset
                    </button>
                </div>

                <div className="field-row">
                    <label>Nachname:</label>
                    <input name="nachname" value={form.nachname} onChange={handleChange} disabled={!form.raceId} />
                    <button onClick={() => openPreset("nachname")} disabled={!form.raceId}>
                        Preset
                    </button>
                </div>

                <div className="field-row">
                    <label>Alter:</label>
                    <input name="npc_age" value={form.npc_age} onChange={handleChange} />
                </div>

                <div className="field-row">
                    <label>Background:</label>
                    <input name="background" value={form.background} onChange={handleChange} />
                    <button onClick={() => openPreset("background")}>Preset</button>
                </div>

                <div>
                    <div className="stats-grid">
                        <div className="field-row">
                            <label>Klasse:</label>
                            <select name="classId" value={form.classId || ""} onChange={handleChange}>
                                <option value="">Wähle eine Klasse</option>
                                {classes.map((c) => (
                                    <option key={c.id} value={c.id}>
                                        {c.classname}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className="field-row">
                            <label>Subklasse:</label>
                            <select name="subclass" value={form.subclass} onChange={handleChange} disabled={!form.classId}>
                                <option value="">Wähle eine Subklasse</option>
                                {subclass.map((sc) => {
                                    const subclassValue = sc.subclassId ?? sc.subclass_ID ?? sc.id;
                                    return (
                                        <option key={subclassValue} value={subclassValue}>
                                            {sc.subclassname}
                                        </option>
                                    );
                                })}
                            </select>
                        </div>

                        <div className="field-row">
                            <label>Stufe:</label>
                            <input name="npc_lvl" id={"npc_lvl"} value={form.npc_lvl} onChange={handleChange} />
                        </div>

                        <br />
                        <br />
                        {renderCell("ATK", stats.ATK, handleStatChange)}
                        {renderCell("CON", stats.CON, handleStatChange)}
                        {renderCell("WIS", stats.WIS, handleStatChange)}
                        {renderCell("CHA", stats.CHA, handleStatChange)}
                        {renderCell("INT", stats.INT, handleStatChange)}
                        {renderCell("DEX", stats.DEX, handleStatChange)}
                        {renderCell("AC", stats.AC, handleStatChange)}
                    </div>

                    <button
                        onClick={() => {
                            const newStats = rollStatsWithTotalConstraint(7, 80);
                            setStats(newStats);
                        }}
                    >
                        Zufallswerte
                    </button>
                </div>
            </div>

            <div className="middle-column">
                <div>
                    <div className="field-row">
                        <label>Persönlichkeit:</label>
                        <input name="personality" value={form.personality} onChange={handleChange} />
                        <button onClick={() => openPreset("personality")}>Preset</button>
                    </div>

                    <div className="field-row">
                        <label>Weitere Information:</label>
                        <input name="otherDescription" value={form.otherDescription} onChange={handleChange} />
                        <button onClick={() => openPreset("otherDescription")}>Preset</button>
                    </div>

                    <div className="field-row">
                        <label>Gefällt:</label>
                        <input name="likes" value={form.likes} onChange={handleChange} />
                        <button onClick={() => openPreset("likes")}>Preset</button>
                    </div>

                    <div className="field-row">
                        <label>Gefällt nicht:</label>
                        <input name="dislikes" value={form.dislikes} onChange={handleChange} />
                        <button onClick={() => openPreset("dislikes")}>Preset</button>
                    </div>

                    <div className="field-row">
                        <label>Ideale:</label>
                        <input name="ideals" value={form.ideals} onChange={handleChange} />
                        <button onClick={() => openPreset("ideals")}>Preset</button>
                    </div>

                    <div className="field-row">
                        <label>Mangel:</label>
                        <input name="flaw" value={form.flaw} onChange={handleChange} />
                        <button onClick={() => openPreset("flaw")}>Preset</button>
                    </div>

                    <br />

                    <div className="field-row">
                        <label>Betonung:</label>
                        <input name="betonung" value={form.betonung} onChange={handleChange} />
                        <button onClick={() => openPreset("betonung")}>Preset</button>
                    </div>

                    <div className="field-row">
                        <label>Sprechstil:</label>
                        <input name="talkingstyle" value={form.talkingstyle} onChange={handleChange} />
                        <button onClick={() => openPreset("talkingstyle")}>Preset</button>
                    </div>

                    <br />
                    <br />

                    <label>Notizen:</label>
                    <textarea name="notes" className="notizfeld" value={form.notes} onChange={handleChange} />
                </div>
            </div>

            <div className="right-column">
                <div>
                    <div className="field-row">
                        <label>Kleidungsqualität:</label>
                        <input name="kleidungsQuali" value={form.kleidungsQuali} onChange={handleChange} />
                        <button onClick={() => openPreset("kleidungsQuali")}>Preset</button>
                    </div>

                    <div className="field-row">
                        <label>Oberteil:</label>
                        <input name="jackets" value={form.jackets} onChange={handleChange} />
                        <button onClick={() => openPreset("jackets")}>Preset</button>
                    </div>

                    <div className="field-row">
                        <label>Beinkleid:</label>
                        <input name="trousers" value={form.trousers} onChange={handleChange} />
                        <button onClick={() => openPreset("trousers")}>Preset</button>
                    </div>

                    <div className="field-row">
                        <label>Schmuck:</label>
                        <input name="jewellery" value={form.jewellery} onChange={handleChange} />
                        <button onClick={() => openPreset("jewellery")}>Preset</button>
                    </div>

                    <div className="field-row">
                        <label>Haarstil:</label>
                        <input name="hairstyle" value={form.hairstyle} onChange={handleChange} />
                        <button onClick={() => openPreset("hairstyle")}>Preset</button>
                    </div>

                    <div className="field-row">
                        <label>Haarfarbe:</label>
                        <input name="hairColor" value={form.hairColor} onChange={handleChange} />
                        <button onClick={() => openPreset("hairColor")}>Preset</button>
                    </div>

                    <div className="field-row">
                        <label>Bartstil:</label>
                        <input name="beardstyle" value={form.beardstyle} onChange={handleChange} />
                        <button onClick={() => openPreset("beardstyle")}>Preset</button>
                    </div>

                    <br />
                    <br />

                    <div className="field-row">
                        <label>Aufenthaltsort:</label>
                        <select name="location" value={form.location} onChange={handleChange}>
                            <option value="">Wähle eine Ortschaft</option>
                            {locations.map((l) => (
                                <option key={l.id} value={l.locationId}>
                                    {l.cityName || l.villageName}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div className="field-row">
                        <label>Gebäudetyp:</label>
                        <select name="shopType" value={form.shopType} onChange={handleChange} disabled={!form.location}>
                            <option value="">Wähle einen Shoptyp</option>
                            {availableShopTypes.map((st) => (
                                <option key={st.id} value={st.id}>
                                    {st.name}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div className="field-row">
                        <label>Gebäude:</label>
                        <select
                            name="shop"
                            value={form.shop || ""}
                            onChange={handleChange}
                            disabled={!form.shopType || !form.location}
                        >
                            <option value="shop">Wähle ein Gebäude</option>
                            {availableShops.map((s) => (
                                <option key={s.id} value={s.id}>
                                    {s.name}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div>
                        <div className="field-row">
                            <label>
                                Mitarbeiter
                                <input
                                    type="radio"
                                    name="customerOrEmployee"
                                    value="Employee"
                                    checked={form.customerOrEmployee === "Employee"}
                                    onChange={handleChange}
                                />
                            </label>
                            <label>
                                Kunde
                                <input
                                    type="radio"
                                    name="customerOrEmployee"
                                    value="Customer"
                                    checked={form.customerOrEmployee === "Customer"}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                    </div>

                    <div className="field-row">
                        <label>Position:</label>
                        <select
                            name="employeePosition"
                            value={form.employeePosition}
                            onChange={handleChange}
                            disabled={form.customerOrEmployee === "Customer" || !form.customerOrEmployee}
                        >
                            <option value="">Wähle den Beruf</option>
                            {employeeRoles.map((er) => (
                                <option key={er.shop_employee_ID ?? er.id} value={er.shop_employee_ID ?? er.id}>
                                    {er.position}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div className="field-row">
                        <label>Kundentyp:</label>
                        <select
                            name="customerRoleId"
                            value={form.customerRoleId}
                            onChange={handleChange}
                            disabled={form.customerOrEmployee === "Employee" || !form.customerOrEmployee}
                        >
                            <option value="">Wähle den Kundentyp</option>
                            {customerRoles.map((r) => (
                                <option key={r.shop_customer_ID ?? r.id} value={r.shop_customer_ID ?? r.id}>
                                    {r.position}
                                </option>
                            ))}
                        </select>
                    </div>

                    <br />
                    <br />
                    <br />

                    <div className="field-row">
                        <label>Symbol:</label>
                        <select name="symbol" value={form.symbol} onChange={handleChange}>
                            <option value="">Wähle Symbol</option>
                            {symbol.map((s) => (
                                <option key={s} value={s}>
                                    {s}
                                </option>
                            ))}
                        </select>
                    </div>

                    <br />
                    <br />

                    <button onClick={() => createNpc()}>Npc Erstellen</button>
                </div>

                <PresetModal
                    isOpen={modalOpen}
                    title={`${modalField} auswählen`}
                    data={
                        modalField === "vorname"
                            ? firstname
                            : modalField === "jackets"
                                ? jackets
                                : modalField === "nachname"
                                    ? lastname
                                    : modalField === "personality"
                                        ? personality
                                        : modalField === "otherDescription"
                                            ? otherDescription
                                            : modalField === "likes"
                                                ? likes
                                                : modalField === "dislikes"
                                                    ? dislikes
                                                    : modalField === "ideals"
                                                        ? ideals
                                                        : modalField === "betonung"
                                                            ? betonung
                                                            : modalField === "flaw"
                                                                ? flaw
                                                                : modalField === "talkingstyle"
                                                                    ? talkingstyle
                                                                    : modalField === "kleidungsQuali"
                                                                        ? kleidungsQuali
                                                                        : modalField === "trousers"
                                                                            ? trousers
                                                                            : modalField === "jewellery"
                                                                                ? jewellery
                                                                                : modalField === "hairstyle"
                                                                                    ? hairstyle
                                                                                    : modalField === "hairColor"
                                                                                        ? hairColor
                                                                                        : modalField === "beardstyle"
                                                                                            ? beardstyle
                                                                                            : modalField === "background"
                                                                                                ? background
                                                                                                : []
                    }
                    columns={getColumnsForField(modalField)}
                    filterKeys={getFilterKeysForField(modalField)}
                    selectedRaceId={form.raceId}
                    selectedGenderId={form.genderId}
                    debugFilterWatch={modalField === "beardstyle"}
                    currentValue={form[modalField]}
                    onSelect={(entry) => {
                        const entryValueMap = {
                            vorname: "firstname",
                            nachname: "lastname",
                            background: "name",
                            personality: "description",
                            otherDescription: "description",
                            likes: "description",
                            dislikes: "description",
                            flaw: "flaw",
                            ideals: "description",
                            betonung: "betonung",
                            talkingstyle: "description",
                            jackets: "name",
                            trousers: "name",
                            jewellery: "name",
                            hairstyle: "name",
                            hairColor: "name",
                            beardstyle: "name",
                            kleidungsQuali: "description",
                        };

                        const IdFieldMap = {
                            vorname: "firstname_ID",
                            nachname: "lastname_ID",
                            background: "background_ID",
                            gender: "gender_ID",
                            personality: "personality_ID",
                            otherDescription: "otherDescription_ID",
                            likes: "likes_ID",
                            flaw: "flaw_ID",
                            dislikes: "dislikes_ID",
                            ideals: "ideals",
                            betonung: "betonung_ID",
                            talkingstyle: "talkingstyle_ID",
                            jackets: "jackets_ID",
                            trousers: "trousers_ID",
                            jewellery: "jewellery_ID",
                            hairstyle: "hairstyle_ID",
                            hairColor: "haircolor_ID",
                            beardstyle: "beardstyle_ID",
                            kleidungsQuali: "kleidungsQuali",
                        };

                        const resolvedKey = entryValueMap[modalField];
                        const value =
                            typeof entry === "string"
                                ? entry
                                : resolvedKey && entry[resolvedKey] !== undefined
                                    ? entry[resolvedKey]
                                    : entry.name || "";

                        const idValue = entry?.[IdFieldMap[modalField]] ?? null;

                        setForm((prev) => ({
                            ...prev,
                            [modalField]: value,
                            [`${modalField}Id`]: idValue,
                        }));

                        setModalOpen(false);
                    }}
                    onRemove={() => {
                        setForm((prev) => ({
                            ...prev,
                            [modalField]: "",
                            [`${modalField}Id`]: null,
                        }));
                        setModalOpen(false);
                    }}
                    onClose={() => setModalOpen(false)}
                />

                {/* npcDraft bleibt hier nur zum Debug – der Presenter bekommt es aus dem Parent */}
                {false && <pre>{JSON.stringify(npcDraft, null, 2)}</pre>}
            </div>
        </div>
    );
}
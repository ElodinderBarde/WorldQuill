import './CreateNpc.css';
import React, {useState, useEffect} from "react";
import PresetModal from "./PresetModal";
import axios from "axios";



    export default function CreateOptions() {
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
        const [ dislikes, setDislikes] = useState([]);
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


        useEffect(() => {
            axios.get("/api/Race").then((response) => {
                if (JSON.stringify(races) !== JSON.stringify(response.data)) {
                    setRaces(response.data);
                }
            });
        }, [races]);




        const initialForm = {
            vorname: "", vornameId: null,
            nachname: "",nachnameId: null,
            background: "", backgroundId: null,
            npc_age: "",
            race: "",
            genderId: "",
            classId: "",
            subclass: "", subclassID: null,
            npc_lvl: "",
            armor: "",
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
            employeePosition: "", employeePositionID: null,
            customerRoleId: "",


            charisma: "",
            wisdom:"",
            strength:"",
            constitution: "",
            intelligence:"",


            symbol:"",

        };


        const [form, setForm] = useState(initialForm);


        const [stats, setStats] = useState({
            ATK: 10, CON: 10, WIS: 10, CHA: 10, INT: 10, AC: 10,
        });



        const resolveOrCreateId = async ({ apiName, value, formKey, idKey, matchField }) => {
            if (!value) return null;

            try {
                // 1. Abruf aller existierenden Einträge aus API
                const { data } = await axios.get(`/api/${apiName}`);
                const existing = data.find(e => e[matchField].toLowerCase() === value.toLowerCase());

                if (existing) {
                    return existing[idKey];
                } else {
                    // 2. Falls nicht gefunden, erstelle neuen Eintrag
                    const postData = { [matchField]: value };
                    const { data: created } = await axios.post(`/api/${apiName}`, postData);
                    return created[idKey];
                }
            } catch (err) {
                console.error(`Fehler bei ${formKey}`, err);
                return null;
            }
        };

        const rollAbove = (min) => {
            let val;
            do {
                val = Math.floor(Math.random() * 18) + 3;
            } while (val <= min);
            return val;
        };
        const rollStatsWithTotalConstraint = (minValue, maxTotal) => {
            let stats;
            let sum;
            do {
                stats = {
                    ATK: rollAbove(minValue),
                    CON: rollAbove(minValue),
                    WIS: rollAbove(minValue),
                    CHA: rollAbove(minValue),
                    INT: rollAbove(minValue),
                    AC: rollAbove(minValue)
                };
                sum = stats.ATK + stats.CON + stats.WIS + stats.CHA + stats.INT + stats.AC;
                console.log(`Summe der gewürfelten Werte: ${sum}`);
            } while (sum > maxTotal); // erneut würfeln, wenn die Summe zu hoch ist
            console.log("wert gesetzt");
            return stats;

        };

        useEffect(() => {
            if (form.classId) {
                axios.get(`/api/npcs/Subclasses/byClass/${form.classId}`)
                    .then(res => {
                        console.log("Subklassen geladen:", res.data);
                        setSubclass(res.data);
                    });
            } else {
                console.log("Keine Klasse gewählt, Subklassen geleert");
                setSubclass([]);
            }
        }, [form.classId]);




        useEffect(() => {
            axios.get("/api/Race").then(res => setRaces(res.data));
            axios.get("/api/Gender").then(res => setGenders(res.data));
            axios.get("/api/NpcClass").then(res => setClasses(res.data));
            axios.get("/api/locations").then(res => setLocations(res.data));
            axios.get("/api/ShopType").then(res => setShopTypes(res.data));
            axios.get("/api/shops").then(res => setShop(res.data));
            axios.get("/api/ShopEmployee").then(res => setEmployeeRoles(res.data));
            axios.get("/api/ShopCustomer").then(res => setCustomerRoles(res.data));
            axios.get("/api/Firstname").then(res => setFirstname(res.data));
            axios.get("/api/Lastname").then(res => setLastname(res.data));
            axios.get("/api/Personality").then(res => setPersonality(res.data));
            axios.get("/api/OtherDescription").then(res => setOtherDescription(res.data));
            axios.get("/api/Likes").then(res => setLikes(res.data));
            axios.get("/api/Dislikes").then(res => setDislikes(res.data));
            axios.get("/api/Ideals").then(res => setIdeals(res.data));
            axios.get("/api/KleidungQuali").then(res => setKleidungsQuali(res.data));
            axios.get("/api/Jackets").then(res => setJackets(res.data));
            axios.get("/api/Trousers").then(res => setTrousers(res.data));
            axios.get("/api/Hairstyle").then(res => setHairstyle(res.data));
            axios.get("/api/Haircolor").then(res => setHairColor(res.data));
            axios.get("/api/Beardstyle").then(res => setBeardstyle(res.data));
            axios.get("/api/background").then(res => setBackground(res.data));
            axios.get("/api/Betonung").then(res => setBetonung(res.data));
            axios.get("/api/TalkingStyle").then(res => setTalkingstyle(res.data));
            axios.get("/api/Jewellery").then(res => setJewellery(res.data));
            axios.get("/api/Flaw").then(res => setFlaw(res.data));
            axios.get("/api/Stats").then(res => setStats(res.data));
            axios.get("/api/Symbol").then(res =>setSymbol(res.data));
        }, []);






// Filtern der Shoptypen: nur solche, die an der gewählten location vorkommen
        const availableShopTypes = shopTypes.filter(type =>
            shop.some(s =>
                s.locationId === parseInt(form.location, 10) &&
                s.shopTypeId === type.id
            )
        );

// Filtern der Shops: nur solche, die zur gewählten location und zum gewählten Typ passen
        const availableShops = (shop || []).filter(s =>
            s.locationId != null &&
            s.shopTypeId != null &&
            s.locationId === parseInt(form.location, 10) &&
            s.shopTypeId === parseInt(form.shopType, 10)
        );



        //console.log("All Locations:", locations);
        //console.log("Selected ShopType:", form.shopType);

        //console.log("Example Shop:", shops[0]);
        //console.log("form.location:", form.location);
       // console.log("typeof form.location:", typeof form.location);




        const handleChange = (e) => {
            const { name, value } = e.target;




            const numericFields = [
                "race", "genderId", "classId", "subclass",
                "employeePositionId", "shopId",  "employeePosition"
            ];


            if (numericFields.includes(name)) {
                const parsedValue = value ? parseInt(value, 10) : null;

                // Wenn es zusätzlich eine *_Id- oder Mapping-Funktion gibt
                if (name === "race") {
                    setForm(prev => ({
                        ...prev,
                        race: value,
                        raceId: parsedValue
                    }));
                } else if (name === "subclass") {
                    setForm(prev => ({
                        ...prev,
                        subclass: value,
                        subclassId: parsedValue
                    }));
                } else {

                        setForm(prev => ({
                        ...prev,
                        [name]: value === "" ? "" : parseInt(value, 10)
                    }));
                }
            } else {
                setForm(prev => ({
                    ...prev,
                    [name]: value
                }));
            }
        };



        const renderCell = (label, value, onChange) => (
            <div className="stat-cell">
                <label>{label}</label>
                <input
                    type="number"
                    value={value ?? ''} // ensure controlled input never becomes undefined
                    onChange={(e) => onChange(label, e.target.value)}
                    style={{ width: "60px", marginLeft: "5px" }}
                />
            </div>
        );

        const handleStatChange = (key, val) => {
            setStats(prev => ({ ...prev, [key]: parseInt(val) || 0 }));
        };




        const createNpc = async () => {
            try {
                const firstnameId = await resolveOrCreateId({
                    apiName: "Firstname",
                    value: form.vorname,
                    formKey: "vorname",
                    idKey: "firstname_ID",
                    matchField: "firstname"
                });

                const lastnameId = await resolveOrCreateId({
                    apiName: "Lastname",
                    value: form.nachname,
                    formKey: "nachname",
                    idKey: "lastname_ID",
                    matchField: "lastname"
                });

                const backgroundId = await resolveOrCreateId({
                    apiName: "background",
                    value: form.background,
                    formKey: "background",
                    idKey: "background_ID",
                    matchField: "name"
                });

                const personalityId = await resolveOrCreateId({
                    apiName: "Personality",
                    value: form.personality,
                    formKey: "personality",
                    idKey: "personality_ID",
                    matchField: "description"
                });

                const otherDescriptionId = await resolveOrCreateId({
                    apiName: "OtherDescription",
                    value: form.otherDescription,
                    formKey: "otherDescription",
                    idKey: "otherDescription_ID",
                    matchField: "description"
                });

                const likesId = await resolveOrCreateId({
                    apiName: "Likes",
                    value: form.likes,
                    formKey: "likes",
                    idKey: "likes_ID",
                    matchField: "description"
                });
                const flawId = await resolveOrCreateId({
                    apiName: "Flaw",
                    value: form.flaw,
                    formKey: "flaw",
                    idKey: "flaw_ID",
                    matchField: "name"
                });
                const dislikesId = await resolveOrCreateId({
                    apiName: "Dislikes",
                    value: form.dislikes,
                    formKey: "dislikes",
                    idKey: "dislikes_ID",
                    matchField: "description"
                });

                const idealsId = await resolveOrCreateId({
                    apiName: "Ideals",
                    value: form.ideals,
                    formKey: "ideals",
                    idKey: "ideals",
                    matchField: "description"
                });

                const kleidungQualiId = await resolveOrCreateId({
                    apiName: "KleidungQuali",
                    value: form.kleidungsQuali,
                    formKey: "kleidungsQuali",
                    idKey: "kleidungsQuali",
                    matchField: "description"
                });

                const jacketsId = await resolveOrCreateId({
                    apiName: "Jackets",
                    value: form.jackets,
                    formKey: "jackets",
                    idKey: "jackets_ID",
                    matchField: "name"
                });

                const trousersId = await resolveOrCreateId({
                    apiName: "Trousers",
                    value: form.trousers,
                    formKey: "trousers",
                    idKey: "trousers_ID",
                    matchField: "name"
                });

                const jewelleryId = await resolveOrCreateId({
                    apiName: "Jewellery",
                    value: form.jewellery,
                    formKey: "jewellery",
                    idKey: "jewellery_ID",
                    matchField: "name"
                });

                const hairstyleId = await resolveOrCreateId({
                    apiName: "Hairstyle",
                    value: form.hairstyle,
                    formKey: "hairstyle",
                    idKey: "hairstyle_ID",
                    matchField: "name"
                });

                const haircolorId = await resolveOrCreateId({
                    apiName: "Haircolor",
                    value: form.hairColor,
                    formKey: "hairColor",
                    idKey: "haircolor_ID",
                    matchField: "name"
                });

                const beardstyleId = await resolveOrCreateId({
                    apiName: "Beardstyle",
                    value: form.beardstyle,
                    formKey: "beardstyle",
                    idKey: "beardstyle_ID",
                    matchField: "name"
                });

                const betonungId = await resolveOrCreateId({
                    apiName: "Betonung",
                    value: form.betonung,
                    formKey: "betonung",
                    idKey: "betonung_ID",
                    matchField: "betonung"
                });

                const talkingstyleId = await resolveOrCreateId({
                    apiName: "TalkingStyle",
                    value: form.talkingstyle,
                    formKey: "talkingstyle",
                    idKey: "tralkingstyle_ID",
                    matchField: "description"
                });



                const payload = {
                    firstnameId,
                    lastnameId,
                    backgroundId,
                    genderId: form.genderId === "" ? null : parseInt(form.genderId),
                    npc_age: form.npc_age, //
                    raceId: parseInt(form.race) || null,
                    levelId: parseInt(form.npc_lvl) || 1,
                    classId: form.classId ? parseInt(form.classId, 10) : null,
                    subclassId: form.subclass ? parseInt(form.subclass, 10) : null,
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
                    dexterity: stats.GES ?? 10,

                    location: form.location ? parseInt(form.location, 10) : null,
                    shopType: form.shopTypeId ? parseInt(form.shopTypeId, 10) : null,
                    shopId: form.shop ? parseInt(form.shop, 10) : null,
                    shopCustomerRole: form.customerRoleId === "" ? null : Number(form.customerRoleId),
                    shopEmployeeRole: form.employeePosition ? parseInt(form.employeePosition, 10) : null,
                    symbol: form.symbol || null,
                };


                console.log("location::",form.location);
                console.log("employeeID:",form.employeePosition);
                console.log('customerRoleId im State:', form.customerRoleId, typeof form.customerRoleId);
                console.log("shop", form.shop);

                console.log("FINAL PAYLOAD:", JSON.stringify(payload, null, 2));


                console.log("Finaler Payload:", payload);
                await axios.post("/api/npcs", payload);
                alert("NPC erfolgreich erstellt!");
                setForm(initialForm);

                setStats({
                    ATK: stats.ATK ?? 10,
                    CON: stats.CON ?? 10,
                    WIS: stats.WIS ?? 10,
                    CHA: stats.CHA ?? 10,
                    INT: stats.INT ?? 10,
                    AC: 10 })




            } catch (err) {
                console.error(err);
                alert("Fehler beim Erstellen des NPCs.");
            }
        };

        const openPreset = (field) => {
            setModalField(field);
            setModalOpen(true);
        };

        const getColumnsForField = (field) => {
            switch (field) {
                case "vorname": return ["firstname", "race", "gender"];
                case "nachname": return ["lastname", "race"];
                case "background": return ["background"];

                case "personality": return ["personality"];
                case "otherDescription": return ["otherDescription"];
                case "likes": return ["likes"];
                case "dislikes": return ["dislikes"];
                case "ideals": return ["ideals"];
                case "flaw": return ["flaw"];
                case "betonung": return ["betonung"];
                case "talkingstyle": return ["talkingstyle"];

                case "kleidungsQuali": return ["kleidungsQuali"];
                case "jackets": return ["jackets", "gender"];
                case "trousers": return ["trousers", "gender"];
                case "jewellery": return ["jewellery"];
                case "hairstyle": return ["hairstyle" , "gender"];
                case "hairColor": return ["hairColor"];
                case "bartstyle": return ["bartstyle"];
                default: return ["Name"];
            }
        };

        const getFilterKeysForField = (field) => {
            switch (field) {
                case "vorname": return ["firstname", "race", "gender"];
                case "nachname": return ["lastname", "race"];

                case "personality": return ["personality"];
                case "otherDescription": return ["otherDescription"];
                case "likes": return ["likes"];
                case "dislikes": return ["dislikes"];
                case "ideals": return ["ideals"];
                case "flaw": return ["flaw"];
                case "betonung": return ["betonung"];
                case "talkingstyle": return ["talkingstyle"];

                case "kleidungsQuali": return ["kleidungsQuali"];
                case "jackets": return ["jackets", "gender"];
                case "trousers": return ["trousers", "gender"];
                case "jewellery": return ["jewellery"];
                case "hairstyle": return ["hairstyle", "gender"];
                case "hairColor": return ["hairColor"];
                case "bartstyle": return ["bartstyle"];

                default: return ["Name"];
            }
        };
        const selectedRaceId = form.race?.raceId;
        const selectedGenderId = form.gender?.genderId;



        return (
            <div className="npc-options-grid">
                {/* LINKE SPALTE: Details */}
                <div className="left-column">

                    <div className="field-row">
                        <label>Volk:</label>
                        <select name="race" value={form.race} onChange={handleChange}>
                            <option value="">Wähle Volk</option>
                            {races.map(race => (
                                <option key={race.id} value={race.id}>{race.racename}</option>
                            ))}

                        </select>
                    </div>




                    <div className="field-row">
                        <label>Geschlecht</label>
                        <select name="genderId" value={form.genderId ?? ""} onChange={handleChange}>
                            <option value="">Wähle ein Geschlecht</option>
                            {genders.map((g) => (
                                <option key={g.gender_ID} value={g.gender_ID}>
                                    {g.gendername}
                                </option>
                            ))}
                        </select>
                    </div>






                    <div className="field-row">
                        <label>Vorname:</label>
                        <input name="vorname" value={form.vorname} onChange={handleChange}
                               disabled={!form.raceId || !form.genderId}
                        />
                        <button onClick={() => openPreset("vorname") } disabled={!form.raceId}>Preset</button>
                    </div>

                    <div className="field-row">
                        <label>Nachname:</label>
                        <input name="nachname" value={form.nachname} onChange={handleChange} disabled={!form.raceId}/>
                        <button onClick={() => openPreset("nachname")} disabled={!form.raceId}>Preset</button>
                    </div>



                    <div className="field-row">
                        <label>Alter:</label>
                        <input name="npc_age" value={form.npc_age} onChange={handleChange}/>
                    </div>


                    <div className="field-row">
                        <label>Background:</label>
                        <input name="background" value={form.background} onChange={handleChange}/>
                        <button onClick={() => openPreset("background")} >Preset</button>
                    </div>

                    <div>
                        <div className="stats-grid">
                            <div className="field-row">
                                <label>Klasse:</label>
                                <select name="classId" value={form.classId || ""} onChange={handleChange}>
                                    <option value="">Wähle eine Klasse</option>
                                    {classes.map(c => (
                                        <option key={c.class_ID} value={c.class_ID}>
                                            {c.classname}
                                        </option>

                                    ))}



                                </select>

                            </div>

                            <div className="field-row">
                                <label>Subklasse:</label>
                                <select
                                    name="subclass"
                                    value={form.subclass}
                                    onChange={handleChange}
                                    disabled={!form.classId}
                                >
                                    <option value="">Wähle eine Subklasse</option>
                                    {subclass.map(sc => (
                                        <option key={sc.subclass_ID} value={sc.subclass_ID}>
                                            {sc.subclassname}
                                        </option>
                                    ))}
                                </select>

                            </div>
                            <div className="field-row">
                                <label>Stufe:</label>
                                <input name="npc_lvl" id={"npc_lvl"} value={form.npc_lvl} onChange={handleChange}/>

                            </div>
                            <br/>
                            <br/>
                            {renderCell("ATK", stats.ATK, handleStatChange)}
                            {renderCell("CON", stats.CON, handleStatChange)}
                            {renderCell("WIS", stats.WIS, handleStatChange)}
                            {renderCell("CHA", stats.CHA, handleStatChange)}
                            {renderCell("INT", stats.INT, handleStatChange)}
                            {renderCell("AC", stats.AC, handleStatChange)}

                        </div>
                        <button onClick={() => {
                            const newStats = rollStatsWithTotalConstraint(7, 80);
                            setStats(newStats);

                        }}>
                            Zufallswerte
                        </button>
                    </div>






                </div>

                <div className="middle-column">
                    <div>
                        <div className="field-row">
                            <label>Persönlichkeit:</label>
                            <input name="personality" value={form.personality} onChange={handleChange}/>
                            <button onClick={() => openPreset("personality")} >Preset</button>
                        </div>
                        <div className="field-row">
                            <label>Weitere Information:</label>
                            <input name="otherDescription" value={form.otherDescription} onChange={handleChange}/>
                            <button onClick={() => openPreset("otherDescription")} >Preset</button>
                        </div>
                        <div className="field-row">
                            <label>Gefällt:</label>
                            <input name="likes" value={form.likes} onChange={handleChange} />
                            <button onClick={() => openPreset("likes")} >Preset</button>
                        </div>
                        <div className="field-row">
                            <label>Gefällt nicht:</label>
                            <input name="dislikes" value={form.dislikes} onChange={handleChange}/>
                            <button onClick={() => openPreset("dislikes")} >Preset</button>
                        </div>
                        <div className="field-row">
                            <label>Ideale:</label>
                            <input name="ideals" value={form.ideals} onChange={handleChange} />
                            <button onClick={() => openPreset("ideals")} >Preset</button>
                        </div>
                        <div className="field-row">
                            <label>Mangel:</label>
                            <input name="flaw" value={form.flaw} onChange={handleChange} />
                            <button onClick={() => openPreset("flaw")} >Preset</button>
                        </div>
                        <br/>

                        <div className="field-row">
                            <label>Betonung:</label>
                            <input name="betonung" value={form.betonung} onChange={handleChange} />
                            <button onClick={() => openPreset("betonung")} >Preset</button>
                        </div>

                        <div className="field-row">
                            <label>Sprechstil:</label>
                            <input name="talkingstyle" value={form.talkingstyle} onChange={handleChange} />
                            <button onClick={() => openPreset("talkingstyle")} >Preset</button>
                        </div>

                        <br/>
                        <br/>

                        <label>Notizen:</label>
                        <textarea name="notes" className="notizfeld" value={form.notes} onChange={handleChange} />


                    </div>
                </div>

                <div className="right-column">
                    <div>
                        <div className="field-row">
                            <label>Kleidungsqualität:</label>
                            <input name="kleidungsQuali" value={form.kleidungsQuali} onChange={handleChange} />
                            <button onClick={() => openPreset("kleidungsQuali")} >Preset</button>
                        </div>
                        <div className="field-row">
                            <label>Oberteil:</label>
                            <input name="jackets" value={form.jackets} onChange={handleChange}/>
                            <button onClick={() => openPreset("jackets")} >Preset</button>
                        </div>
                        <div className="field-row">
                            <label>Beinkleid:</label>
                            <input name="trousers" value={form.trousers} onChange={handleChange} />
                            <button onClick={() => openPreset("trousers")} >Preset</button>
                        </div>
                        <div className="field-row">
                            <label>Schmuck:</label>
                            <input name="jewellery" value={form.jewellery} onChange={handleChange} />
                            <button onClick={() => openPreset("jewellery")} >Preset</button>
                        </div>
                        <div className="field-row">
                            <label>Haarstil:</label>
                            <input name="hairstyle" value={form.hairstyle} onChange={handleChange}/>
                            <button onClick={() => openPreset("hairstyle")} >Preset</button>
                        </div>
                        <div className="field-row">
                            <label>Haarfarbe:</label>
                            <input name="hairColor" value={form.hairColor} onChange={handleChange} />
                            <button onClick={() => openPreset("hairColor")} >Preset</button>
                        </div>
                        <div className="field-row">
                            <label>Bartstil:</label>
                            <input name="beardstyle" value={form.beardstyle} onChange={handleChange}/>
                            <button onClick={() => openPreset("beardstyle")} >Preset</button>
                        </div>

                        <br/>
                        <br/>

                        <div className="field-row">
                            <label>Aufenthaltsort:</label>
                            <select name="location" value={form.location} onChange={handleChange}>
                                <option value="">Wähle eine Ortschaft</option>
                                {locations.map(l => (
                                    <option key={l.id} value={l.locationId}>{l.cityName || l.villageName}</option>

                            ))}



                            </select>
                        </div>
                        <div className="field-row">
                            <label>Gebäudetyp:</label>
                            <select name="shopType" value={form.shopType} onChange={handleChange} disabled={!form.location}>
                                <option value="">Wähle einen Shoptyp</option>
                                {availableShopTypes.map(st => (
                                    <option key={st.id} value={st.id}>{st.name}</option>
                                ))}
                            </select>
                        </div>
                        <div className="field-row">
                            <label>Gebäude:</label>
                            <select
                                name="shop"
                                value={form.shop||''}
                                onChange={handleChange}
                                disabled={!form.shopType || !form.location}
                            >
                                <option value="shop">Wähle ein Gebäude</option>
                                {availableShops.map(s => (
                                    <option key={s.id} value={s.id}>{s.name}</option>
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
                            <select name="employeePosition"
                                    value={form.employeePosition}
                                    onChange={handleChange}
                                    disabled={form.customerOrEmployee === "Customer"|| !form.customerOrEmployee}>
                                <option value="">Wähle den Beruf</option>
                                {employeeRoles.map(er => (
                                    <option key={er.shop_employee_ID} value={er.shop_employee_ID}>{er.position}</option>
                                ))}
                            </select>
                        </div>



                        <div className="field-row">
                            <label>Kundentyp:</label>
                            <select name="customerRoleId"
                                    value={form.customerRoleId}
                                    onChange={handleChange}
                                    disabled={form.customerOrEmployee === "Employee" || !form.customerOrEmployee}>
                                <option value="">Wähle den Kundentyp</option>
                                {customerRoles.map(r => (
                                    <option key={r.shop_customer_ID} value={r.shop_customer_ID}>{r.position}</option>

                                ))}
                            </select>




                        </div>
                        <br/>
                        <br/>
                        <br/>
                        <div className="field-row">
                            <label>Symbol:</label>
                            <select name="symbol"
                                    value={form.symbol}
                                    onChange={handleChange}>
                                <option value="">Wähle Symbol</option>
                                {symbol.map(s => (
                                    <option key={s} value={s}>{s}</option>

                                ))}
                            </select>
                        </div>
                        <br/>
                        <br/>


                        <button onClick={() => createNpc()}>

                            Npc Erstellen
                        </button>


                    </div>
                    <PresetModal
                        isOpen={modalOpen}
                        title={`${modalField} auswählen`}
                        data={
                            modalField === "vorname" ? firstname :
                            modalField === "jackets" ? jackets :
                            modalField === "nachname" ? lastname:
                            modalField ==="personality" ? personality:
                            modalField === "otherDescription" ? otherDescription:
                            modalField === "likes" ? likes:
                            modalField === "dislikes" ? dislikes:
                            modalField === "ideals" ? ideals:
                            modalField === "betonung" ? betonung:
                            modalField === "flaw" ? flaw:
                            modalField === "talkingstyle" ? talkingstyle:
                            modalField === "kleidungsQuali" ? kleidungsQuali:
                            modalField === "trousers" ? trousers:
                            modalField === "jewellery" ? jewellery:
                            modalField === "hairstyle" ? hairstyle:
                            modalField === "hairColor" ? hairColor:
                            modalField === "beardstyle" ? beardstyle:
                            modalField === "background" ? background:

                                []
                        }
                        columns={getColumnsForField(modalField)}
                        filterKeys={getFilterKeysForField(modalField)}
                        selectedRaceId={form.raceId}
                        selectedGenderId={form.genderId}
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
                            const value = typeof entry === "string"
                                ? entry
                                : resolvedKey && entry[resolvedKey] !== undefined
                                    ? entry[resolvedKey]
                                    : entry.name || "";

                            const idValue = entry?.[IdFieldMap[modalField]] ?? null;

                            setForm(prev => ({
                                ...prev,
                                [modalField]: value,
                                [`${modalField}Id`]: idValue
                            }));



                            console.log("==> modalField:", modalField);
                            console.log("==> expected id key:", IdFieldMap[modalField]);
                            console.log("==> raw entry:", entry);
                            console.log("==> extracted id value:", entry?.[IdFieldMap[modalField]]);
                            console.log("Filter active: race =", selectedRaceId, ", gender =", selectedGenderId);



                            setModalOpen(false);
                        }}


                        onRemove={() => {
                            setForm(prev => ({ ...prev, [modalField]: "" , [`${modalField}Id`]: null }));
                            setModalOpen(false);
                        }}
                        onClose={() => setModalOpen(false)}
                    />


                                </div>


            </div>
        );
    }

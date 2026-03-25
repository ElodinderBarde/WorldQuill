"use client";
import { Separator } from "@/components/ui/separator";

import { useEffect, useState } from "react";
import { IconMapPin } from "@tabler/icons-react";

import {
    Dialog,
    DialogContent,
    DialogTitle,        // neu
    DialogDescription,  // neu
} from "@/components/ui/dialog";



import { VisuallyHidden } from "@radix-ui/react-visually-hidden";
import {
    Command,
    CommandDialog,
    CommandEmpty,
    CommandGroup,
    CommandInput,
    CommandItem,
    CommandList,
} from "@/components/ui/command";

import { getLocation } from "@/service/locationAPI.js";

export function CommandMenu01({ open, setOpen, onSelectLocation }) {
    const [cities, setCities] = useState([]);
    const [villages, setVillages] = useState([]);

    const handleSelect = (location) => {
        onSelectLocation(location);
        setOpen(false);
    };




    useEffect(() => {
        async function loadLocations() {
            const locations = await getLocation();
            setCities(locations.filter(l => l.cityName));
            setVillages(locations.filter(l => l.villageName));
        }
        loadLocations();
    }, []);

    return (
        <CommandDialog open={open} onOpenChange={setOpen}>
            <VisuallyHidden>
                <DialogTitle>Ortschaft auswählen</DialogTitle>
                <DialogDescription>
                    Suche und wähle eine Stadt oder ein Dorf aus.
                </DialogDescription>
            </VisuallyHidden>
            <DialogContent className="overflow-hidden p-0 w-[90vw] max-w-[900px] max-h-[80vh]">
            <Command className="flex h-full w-full flex-col">
                <CommandInput placeholder="Stadt oder Dorf suchen…" className="border-b mb-3" />

                    <div className="pt-6"  />

                   <br/>
                   <br/>


                    <CommandInput
                        placeholder="Stadt oder Dorf suchen…"
                        className="border-b mb-3"
                    />

                    <div
                        className="flex-1 overflow-y-auto"
                        onWheel={(e) => e.stopPropagation()}
                    >
                        <CommandList className="max-h-none">
                            <CommandEmpty>Keine Ergebnisse gefunden.</CommandEmpty>

                            <CommandGroup heading="Städte">
                                <Separator className="my-2" />
                                {cities.map((city) => (
                                    <CommandItem
                                        key={city.locationId}
                                        onSelect={() => handleSelect(city)}
                                    >
                                        <IconMapPin className="mr-2 h-5 w-5" />
                                        {city.cityName}
                                    </CommandItem>
                                ))}
                            </CommandGroup>

                            <CommandGroup heading="Dörfer">
                                <Separator className="my-2" />
                                {villages.map((village) => (
                                    <CommandItem
                                        key={village.locationId}
                                        onSelect={() => handleSelect(village)}
                                    >
                                        <IconMapPin className="mr-2 h-5 w-5" />
                                        {village.villageName}
                                    </CommandItem>
                                ))}
                            </CommandGroup>
                        </CommandList>
                    </div>
            </Command>
            </DialogContent>
        </CommandDialog>
);
}

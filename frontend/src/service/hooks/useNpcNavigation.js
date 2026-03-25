import { useNavigate } from "react-router-dom";
import { useCallback } from "react";

export function useNpcNavigation() {
    const navigate = useNavigate();

    const handleNpcDoubleClick = useCallback((npcId) => {
        if (!npcId) return;
        navigate(`/npc/${npcId}`);
    }, [navigate]);

    return { handleNpcDoubleClick };
}
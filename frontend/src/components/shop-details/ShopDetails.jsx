// ShopDetails.jsx
import { useEffect, useState } from 'react';

const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8081';

export default function ShopDetail({ shopId }) {
    const [shop, setShop] = useState(null);

    useEffect(() => {
        if (!shopId) {
            setShop(null);
            return;
        }

        const fetchShop = async () => {
            try {
                const response = await fetch(`${BASE_URL}/api/shops/${shopId}`);
                if (!response.ok) {
                    throw new Error(`Fehler beim Laden: ${response.status}`);
                }
                const data = await response.json();
                setShop(data);
            } catch (error) {
                console.error('Fehler beim Laden des Shops:', error);
                console.log("Lade Shop mit ID:", shopId);
            }
        };

        void fetchShop();
    }, [shopId]);

    if (!shop) {
        return <p>Bitte versuche es nochmals...</p>;
    }

    return (
        <div>
            <div>
                <h2>{shop.name}</h2>
                <h3>
                    {shop?.location?.city?.cityName ??
                        shop?.location?.village?.villageName ??
                        "Ort unbekannt"}
                </h3>
            </div>
        </div>
    );
}

import { useLayoutEffect,  useRef } from 'react';

import { useParams } from 'react-router-dom';
import Navbar from '../components/Navbar';
import { GridStack } from 'gridstack';
import 'gridstack/dist/gridstack.min.css';
import ShopItems from "../components/shop-details/ShopItems.jsx";
import ShopEmployees from "../components/shop-details/ShopEmployees.jsx";
import ShopCustomers from "../components/shop-details/ShopCustomers.jsx";
import ShopDetail from "../components/shop-details/ShopDetails.jsx";
import ShopDetailNotes from "../components/shop-details/ShopDetailNotes.jsx";

export default function ShopDetails() {
    const { shopId } = useParams();
    const resolvedShopId = shopId && shopId !== "undefined" ? shopId : null;
    const gridRef = useRef(null);
    const gridInstance = useRef(null);

    useLayoutEffect(() => {
        const timer = setTimeout(() => {
            const grid = GridStack.init({
                column: 12,
                margin: 5,
                cellHeight: 100,
                disableResize: true,
                disableDrag: true,
            }, gridRef.current);
            gridInstance.current = grid;
            console.log(grid.save());
        }, 0);

        return () => clearTimeout(timer);
    }, []);

    return (
        <>
            <Navbar />
            <main style={{ padding: '1rem' }}>
                <div className="grid-stack" ref={gridRef}>
                    <div id="shopDetail" className="grid-stack-item" gs-x="0" gs-y="0" gs-w="4" gs-h="2">
                        <div className="grid-stack-item-content">
                            <ShopDetail shopId={resolvedShopId} />
                        </div>
                    </div>

                    <div id="shopItems" className="grid-stack-item" gs-x="4" gs-y="0" gs-w="6" gs-h="9">
                        <div className="grid-stack-item-content">
                            <ShopItems shopId={resolvedShopId} />
                        </div>
                    </div>

                    <div id="shopEmployees" className="grid-stack-item" gs-x="0" gs-y="10" gs-w="4" gs-h="3">
                        <div className="grid-stack-item-content">
                            <ShopEmployees shopId={resolvedShopId} />
                        </div>
                    </div>

                    <div id="shopCustomers" className="grid-stack-item" gs-x="10" gs-y="0" gs-w="2" gs-h="9">
                        <div className="grid-stack-item-content">
                            <ShopCustomers shopId={resolvedShopId} />
                        </div>
                    </div>

                    <div id="ShopNotes" className="grid-stack-item" gs-x="0" gs-y="6" gs-w="4" gs-h="4">
                        <div className="grid-stack-item-content">
                            <ShopDetailNotes shopId={resolvedShopId} />
                        </div>
                    </div>
                </div>
            </main>
        </>
    );
}

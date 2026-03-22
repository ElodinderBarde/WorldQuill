import { useEffect, useRef } from "react";
import { GridStack } from "gridstack";
import "gridstack/dist/gridstack.min.css";
import "./Board.css";

export default function BoardLayout({
                                        FormComponent,
                                        FilterComponent,
                                        ListComponent,
                                        DetailComponent,

                                        data,
                                        selected,
                                        onSelect,
                                        onDoubleClick,
                                        onFilterChange,
                                        onReload
                                    }) {
    const gridRef = useRef(null);
    const grid = useRef(null);
    const listRef = useRef(null);
    const detailRef = useRef(null);

    // =========================
    // GRID INIT
    // =========================
    useEffect(() => {
        if (!gridRef.current) return;

        grid.current = GridStack.init({
            column: 12,
            cellHeight: 100,
            margin: 5,
            staticGrid: true,
            disableResize: true,
            disableDrag: true
        }, gridRef.current);

        return () => {
            grid.current?.destroy(false);
            grid.current = null;
        };
    }, []);

    // =========================
    // DETAIL PANEL RESIZE
    // =========================
    useEffect(() => {
        if (!grid.current) return;

        const listEl = listRef.current;
        const detailEl = detailRef.current;

        if (!listEl || !detailEl) return;

        if (selected) {
            grid.current.update(listEl, { w: 5 });
            grid.current.update(detailEl, { w: 4, x: 8 });
        } else {
            grid.current.update(listEl, { w: 8 });
            grid.current.update(detailEl, { w: 0, x: 12 });
        }
    }, [selected]);

    return (
        <div className="grid-stack" ref={gridRef}>

            {/* FORM */}
            <div className="grid-stack-item" gs-x="0" gs-y="0" gs-w="3" gs-h="5">
                <div className="grid-stack-item-content">
                    <FormComponent onCreated={onReload} />
                </div>
            </div>

            {/* FILTER */}
            <div className="grid-stack-item" gs-x="0" gs-y="5" gs-w="3" gs-h="3">
                <div className="grid-stack-item-content">
                    <FilterComponent onFilterChange={onFilterChange} />
                </div>
            </div>

            {/* LIST */}
            <div
                ref={listRef}
                className="grid-stack-item board-list-panel"
                gs-x="3"
                gs-y="0"
                gs-w="9"
                gs-h="8"
            >
                <div className="grid-stack-item-content">
                    <ListComponent
                        data={data}
                        onSelect={onSelect}
                        onDoubleClick={onDoubleClick}
                    />
                </div>
            </div>

            {/* DETAIL */}
            <div
                ref={detailRef}
                className={`grid-stack-item detail-panel ${selected ? "active" : ""}`}
                gs-x="12"
                gs-y="0"
                gs-w="0"
                gs-h="8"
            >
                <div className="grid-stack-item-content">
                    <DetailComponent data={selected} />
                </div>
            </div>

        </div>
    );
}
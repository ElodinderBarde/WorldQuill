// BoardLayout.jsx

import { useEffect, useRef, useState } from "react";
import { createPortal } from "react-dom";
import "./Board.css";

export default function BoardLayout({
                                        FormComponent,
                                        FilterComponent,
                                        ListComponent,
                                        DetailComponent,
                                        formProps   = {},
                                        filterProps = {},
                                        listProps   = {},
                                        detailProps = {},
                                        data        = [],
                                        selected    = null,
                                        onSelect,
                                        onDoubleClick,
                                        loading     = false,
                                        error       = null,
                                    }) {
    const formRef   = useRef(null);
    const filterRef = useRef(null);
    const listRef   = useRef(null);
    const detailRef = useRef(null);

    const [formNode,   setFormNode]   = useState(null);
    const [filterNode, setFilterNode] = useState(null);
    const [listNode,   setListNode]   = useState(null);
    const [detailNode, setDetailNode] = useState(null);

    useEffect(() => {
        if (formRef.current)   setFormNode(formRef.current);
        if (filterRef.current) setFilterNode(filterRef.current);
        if (listRef.current)   setListNode(listRef.current);
        if (detailRef.current) setDetailNode(detailRef.current);
    }, []);

    const Form   = FormComponent;
    const Filter = FilterComponent;
    const List   = ListComponent;
    const Detail = DetailComponent;

    // =========================
    // LOADING / ERROR
    // ersetzt nur den Listen-Bereich,
    // Form und Filter bleiben bedienbar
    // =========================
    const renderList = () => {
        if (loading) {
            return (
                <div className="board-state-overlay">
                    <span className="board-state-text">Laden...</span>
                </div>
            );
        }
        if (error) {
            return (
                <div className="board-state-overlay">
                    <span className="board-state-text board-state-error">{error}</span>
                </div>
            );
        }
        return null;
    };

    return (
        <>
            <div className="board-layout">

                <div className="board-col-left">
                    <div className="board-cell board-cell-form"   ref={formRef}   />
                    <div className="board-cell board-cell-filter" ref={filterRef} />
                </div>

                <div
                    className="board-cell board-cell-list"
                    style={{ flex: selected ? "5" : "9" }}
                    ref={listRef}
                >
                    {/* Loading/Error inline im List-Container
                        Portal rendert nur wenn kein Fehler/Loading */}
                    {renderList()}
                </div>

                <div
                    className={`board-cell board-cell-detail${selected ? " detail-active" : ""}`}
                    style={{ flex: selected ? "4" : "0" }}
                    ref={detailRef}
                />

            </div>

            {formNode   && createPortal(<Form   {...formProps}   />, formNode)}
            {filterNode && createPortal(<Filter {...filterProps} />, filterNode)}

            {/* Liste nur rendern wenn kein Loading/Error */}
            {listNode && !loading && !error && createPortal(
                <List
                    {...listProps}
                    data={data}
                    onSelect={onSelect}
                    onDoubleClick={onDoubleClick}
                />,
                listNode
            )}

            {detailNode && createPortal(
                <Detail
                    {...detailProps}
                    data={selected}
                />,
                detailNode
            )}
        </>
    );
}
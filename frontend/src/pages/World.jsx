import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from '../components/navbar';

export default function World() {
    const navigate = useNavigate();

    useEffect(() => {
        navigate("/notes");  // ← Ziel anpassen
    }, []);

    return null;
}
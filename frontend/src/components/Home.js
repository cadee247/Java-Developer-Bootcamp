import React from "react";
import { Link } from "react-router-dom";
import "../css/Home.css";
export default function Home() {
    return (
        <div>
            <h1>Welcome to Event AI</h1>
            <p>
                <Link to="/login">Login</Link> or <Link to="/register">Register</Link>
            </p>
        </div>
    );
}

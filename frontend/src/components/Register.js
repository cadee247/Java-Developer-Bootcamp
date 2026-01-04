import React, { useState } from "react";
import axios from "axios";
import "../css/Register.css";

export default function Register() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState(""); // success or error messages

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage(""); // clear previous messages

        try {
            const res = await axios.post(
                "http://localhost:8080/api/auth/register",
                { username, password },
                { withCredentials: true }
            );

            if (res.status === 200) {
                setMessage(`✅ Registration successful! Welcome, ${res.data.username}`);
                setUsername("");
                setPassword("");
            } else {
                setMessage("⚠️ Registration failed: Unexpected response from server");
            }
        } catch (err) {
            console.error("Register error:", err);

            if (err.response) {
                setMessage(err.response.data?.message || `⚠️ ${err.response.statusText}`);
            } else if (err.request) {
                setMessage("⚠️ Registration failed: No response from server");
            } else {
                setMessage(`⚠️ Registration failed: ${err.message}`);
            }
        }
    };

    return (
        <div className="register-container">
            <div className="register-content">
                <h2>Register</h2>
                <form onSubmit={handleSubmit}>
                    <input
                        type="text"
                        placeholder="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                    <button type="submit">Register</button>
                </form>
                {message && <p className="message">{message}</p>}
            </div>
        </div>
    );
}

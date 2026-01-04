import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../css/Login.css";

export default function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage("");

        try {
            const res = await axios.post(
                "http://localhost:8080/api/auth/login",
                { username, password },
                { withCredentials: true }
            );

            setMessage("✅ Login successful!");
            console.log(res.data); // backend token

            navigate("/chatbot"); // redirect to dashboard
        } catch (err) {
            console.error(err);

            if (err.response) {
                setMessage(`⚠️ Login failed: ${err.response.data}`);
            } else if (err.request) {
                setMessage("⚠️ Login failed: No response from server.");
            } else {
                setMessage(`⚠️ Login failed: ${err.message}`);
            }
        }
    };

    return (
        <div className="login-container">
            <div className="login-card">
                <h2>Login</h2>
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
                    <button type="submit">Login</button>
                </form>
                {message && <p className="message">{message}</p>}

                <p className="link" onClick={() => navigate("/register")}>
                    Don't have an account? Register
                </p>
            </div>
        </div>
    );
}

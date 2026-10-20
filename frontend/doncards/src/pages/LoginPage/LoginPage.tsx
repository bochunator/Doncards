import { useState, type FormEvent } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "./LoginPage.css";
import { useAuth } from "../../authentication/AuthContext";
import type { TokenResponse } from "../../types/authentication/TokenResponse";
import API_URL from "../../config/api";
import { getErrorMessage } from "../../utils/getErrorMessage";

function LoginPage() {
    const navigate = useNavigate();
    const { login: saveLogin } = useAuth();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setError("");
        setIsSubmitting(true);

        try {
            const response = await axios.post<TokenResponse>(
                `${API_URL}/auth/login`,
                {
                    username: username.trim(),
                    password,
                }
            );

            if (!response.data.accessToken) {
                setError("The server did not return an access token.");
                return;
            }

            saveLogin(response.data.accessToken, username.trim());
            navigate("/");
        } catch (error: unknown) {
            setError(getErrorMessage(error));
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <main className="login-page">
            <section className="login-card">
                <Link to="/" className="login-logo">
                    Doncards
                </Link>

                <h1>Welcome back</h1>

                <p className="login-description">
                    Sign in to access your flashcards and continue learning.
                </p>

                <form onSubmit={handleSubmit} className="login-form">
                    <div className="login-field">
                        <label htmlFor="username">Username</label>
                        <input
                            id="username"
                            type="text"
                            autoComplete="username"
                            placeholder="Enter your username"
                            value={username}
                            onChange={(event) =>
                                setUsername(event.target.value)
                            }
                            required
                            disabled={isSubmitting}
                        />
                    </div>

                    <div className="login-field">
                        <label htmlFor="password">Password</label>
                        <input
                            id="password"
                            type="password"
                            autoComplete="current-password"
                            placeholder="Enter your password"
                            value={password}
                            onChange={(event) =>
                                setPassword(event.target.value)
                            }
                            required
                            disabled={isSubmitting}
                        />
                    </div>

                    {error && (
                        <p className="login-error" role="alert">
                            {error}
                        </p>
                    )}

                    <button
                        type="submit"
                        className="login-button"
                        disabled={isSubmitting}
                    >
                        {isSubmitting ? "Signing in..." : "Sign in"}
                    </button>
                </form>

                <p className="login-footer">
                    Don't have an account?{" "}
                    <Link to="/register">Sign up</Link>
                </p>
            </section>
        </main>
    );
}

export default LoginPage;

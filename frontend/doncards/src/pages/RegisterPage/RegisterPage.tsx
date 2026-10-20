import { useState, type FormEvent } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import "./RegisterPage.css";
import { getErrorMessage } from "../../utils/getErrorMessage";
import API_URL from "../../config/api";
import type { RegisterResposne } from "../../types/authentication/RegisterResponse";

export default function RegisterPage() {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    const [error, setError] = useState<string | null>(null);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [isRegistered, setIsRegistered] = useState(false);

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setError("");

        if (password !== confirmPassword) {
            setError("Passwords do not match.");
            return;
        }

        setIsSubmitting(true);

        try {
            await axios.post<RegisterResposne>(
                `${API_URL}/auth/register`,
                {
                username: username.trim(),
                email: email.trim(),
                password,
                }
            );

            setIsRegistered(true);
        } catch (error: unknown) {
            setError(getErrorMessage(error));
        } finally {
            setIsSubmitting(false);
        }
    };

    if (isRegistered) {
        return (
            <main className="register-page">
                <section className="register-card register-success">
                    <div className="register-logo">Doncards</div>

                    <div className="register-success-icon" aria-hidden="true">
                        ✓
                    </div>

                    <h1>Account created!</h1>

                    <p className="register-description">
                        Your account has been created successfully.
                        You can now sign in and start learning.
                    </p>

                    <Link to="/login" className="register-button">
                        Go to login
                    </Link>

                    <p className="register-footer">
                        Already have an account?{" "}
                        <Link to="/login">Sign in</Link>
                    </p>
                </section>
            </main>
        );
    }

    return (
        <main className="register-page">
            <section className="register-card">
                <Link to="/" className="register-logo">
                    Doncards
                </Link>

                <h1>Create your account</h1>

                <p className="register-description">
                    Create an account to organize your flashcards
                    and make learning easier.
                </p>

                <form onSubmit={handleSubmit} className="register-form">
                    <div className="register-field">
                        <label htmlFor="username">Username</label>
                        <input
                            id="username"
                            type="text"
                            autoComplete="username"
                            placeholder="Choose a username"
                            value={username}
                            onChange={(event) =>
                                setUsername(event.target.value)
                            }
                            required
                            minLength={3}
                            maxLength={50}
                            disabled={isSubmitting}
                        />
                    </div>

                    <div className="register-field">
                        <label htmlFor="email">Email address</label>
                        <input
                            id="email"
                            type="email"
                            autoComplete="email"
                            placeholder="you@example.com"
                            value={email}
                            onChange={(event) =>
                                setEmail(event.target.value)
                            }
                            required
                            disabled={isSubmitting}
                        />
                    </div>

                    <div className="register-field">
                        <label htmlFor="password">Password</label>
                        <input
                            id="password"
                            type="password"
                            autoComplete="new-password"
                            placeholder="Create a password"
                            value={password}
                            onChange={(event) =>
                                setPassword(event.target.value)
                            }
                            required
                            minLength={8}
                            disabled={isSubmitting}
                        />
                        <span className="register-hint">
                            Use at least 8 characters.
                        </span>
                    </div>

                    <div className="register-field">
                        <label htmlFor="confirmPassword">
                            Confirm password
                        </label>
                        <input
                            id="confirmPassword"
                            type="password"
                            autoComplete="new-password"
                            placeholder="Enter your password again"
                            value={confirmPassword}
                            onChange={(event) =>
                                setConfirmPassword(event.target.value)
                            }
                            required
                            minLength={8}
                            disabled={isSubmitting}
                        />
                    </div>

                    {error && (
                        <p className="register-error" role="alert">
                            {error}
                        </p>
                    )}

                    <button
                        type="submit"
                        className="register-button"
                        disabled={isSubmitting}
                    >
                        {isSubmitting
                            ? "Creating account..."
                            : "Create account"}
                    </button>
                </form>

                <p className="register-footer">
                    Already have an account?{" "}
                    <Link to="/login">Sign in</Link>
                </p>
            </section>
        </main>
    );
}
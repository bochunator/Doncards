import axios from "axios";
import { useState, type FormEvent } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../../authentication/AuthContext";
import CustomAlert from "../../components/CustomAlert/CustomAlert";
import API_URL from "../../config/api";
import type { CreateDeckRequest } from "../../types/deck/CreateDeckRequest";
import type { CreateDeckResponse } from "../../types/deck/CreateDeckResponse";
import { getErrorMessage } from "../../utils/getErrorMessage";
import "./CreateDeckPage.css";

async function createDeck(
    request: CreateDeckRequest,
    token: string
): Promise<CreateDeckResponse> {
    const response = await axios.post<CreateDeckResponse>(
        `${API_URL}/decks`,
        request,
        {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        }
    );

    return response.data;
}

function CreateDeckPage() {
    const navigate = useNavigate();
    const { token, isAuthenticated } = useAuth();

    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setError("");

        if (!token || !isAuthenticated) {
            navigate("/login");
            return;
        }

        const trimmedName = name.trim();
        const trimmedDescription = description.trim();

        if (!trimmedName) {
            setError("Please enter a name for your deck.");
            return;
        }

        setIsSubmitting(true);

        try {
            const deck = await createDeck(
                {
                    name: trimmedName,
                    description: trimmedDescription,
                },
                token
            );

            navigate(`/deck/${deck.id}`);
        } catch (error: unknown) {
            setError(getErrorMessage(error));
        } finally {
            setLoading(false);
        }
    };

    if (!isAuthenticated) {
        return (
            <CustomAlert
                title="You need an account to create and manage your flashcards."
                description={error}
                actionLabel="Login"
                actionUrl="/login"
            />
        );
    }

    return (
        <main className="create-deck-page">
            <section className="create-deck-card">
                <Link to="/" className="create-deck-logo">
                    Doncards
                </Link>

                <div className="create-deck-heading">
                    <span className="create-deck-eyebrow">NEW DECK</span>
                    <h1>Create a new deck</h1>
                    <p className="create-deck-description">
                        Start with a name and description. You can add
                        flashcards in the next step.
                    </p>
                </div>

                <form onSubmit={handleSubmit} className="create-deck-form">
                    <div className="create-deck-field">
                        <label htmlFor="deck-name">Deck name</label>
                        <input
                            id="deck-name"
                            type="text"
                            placeholder="e.g. English — B2 Vocabulary"
                            value={name}
                            onChange={(event) => setName(event.target.value)}
                            maxLength={100}
                            required
                            autoFocus
                            disabled={isSubmitting}
                        />
                    </div>

                    <div className="create-deck-field">
                        <label htmlFor="deck-description">
                            Description
                            <span className="create-deck-optional">
                                Optional
                            </span>
                        </label>
                        <textarea
                            id="deck-description"
                            placeholder="What will you learn with this deck?"
                            value={description}
                            onChange={(event) =>
                                setDescription(event.target.value)
                            }
                            maxLength={1000}
                            rows={4}
                            disabled={isSubmitting}
                        />
                    </div>

                    <div className="create-deck-info">
                        <span className="create-deck-info-icon" aria-hidden="true">
                            i
                        </span>
                        <div>
                            <h2>What happens next?</h2>
                            <p>
                                Your deck will be created first. Then you can
                                add terms and translations, edit cards, or
                                return to your deck later.
                            </p>
                        </div>
                    </div>

                    {error && (
                        <p className="create-deck-error" role="alert">
                            {error}
                        </p>
                    )}

                    <button
                        type="submit"
                        className="create-deck-button"
                        disabled={isSubmitting}
                    >
                        {isSubmitting
                            ? "Creating deck..."
                            : "Create deck and add cards"}
                        {!isSubmitting && <span aria-hidden="true">→</span>}
                    </button>

                    <Link to="/profile" className="create-deck-cancel">
                        Changed your mind? Cancel
                    </Link>
                </form>
            </section>
        </main>
    );
}

export default CreateDeckPage;
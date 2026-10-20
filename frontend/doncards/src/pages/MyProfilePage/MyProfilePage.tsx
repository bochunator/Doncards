import axios from "axios";
import { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../authentication/AuthContext";
import CustomAlert from "../../components/CustomAlert/CustomAlert";
import DeckList from "../../components/DeckList/DeckList";
import DeckSort, { type DeckSortOption, } from "../../components/DeckSort/DeckSort";
import LoadingIndicator from "../../components/LoadingIndicator/LoadingIndicator";
import ProfileHeader from "../../components/ProfileHeader/ProfileHeader";
import API_URL from "../../config/api";
import { useInfiniteScroll } from "../../hooks/useInfiniteScroll";
import type { AppUserDetailsResponse } from "../../types/appUser/AppUserDetailsResponse";
import type { DeckDetailsResponse } from "../../types/deck/DeckDetailsResponse";
import type { PageResponse } from "../../types/PageResponse";
import { getErrorMessage } from "../../utils/getErrorMessage";
import "./MyProfilePage.css";

function MyProfilePage() {
    const navigate = useNavigate();
    const { token, isAuthenticated } = useAuth();

    const [appUser, setAppUser] = useState<AppUserDetailsResponse>();
    const [appUserLoading, setAppUserLoading] = useState(true);
    const [appUserError, setAppUserError] = useState<string | null>(null);

    const [sort, setSort] = useState<DeckSortOption>("POPULAR");

    useEffect(() => {
        if (!isAuthenticated || !token) {
            navigate("/login", { replace: true });
            return;
        }

        async function fetchAppUser() {
            try {
                setAppUserLoading(true);
                setAppUserError(null);

                const response = await axios.get<AppUserDetailsResponse>(
                    `${API_URL}/app-users/me`,
                    {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    }
                );

                setAppUser(response.data);
            } catch (error: unknown) {
                setAppUserError(getErrorMessage(error));
            } finally {
                setAppUserLoading(false);
            }
        }

        fetchAppUser();
    }, [isAuthenticated, token, navigate]);


    const fetchDecks = useCallback(
        async (page: number, signal: AbortSignal) => {
            if (!appUser || !token) {
                throw new Error("User not found.");
            }
            const response = await axios.get<
                PageResponse<DeckDetailsResponse>
            >(`${API_URL}/decks/by-user`, {
                params: {
                    appUserId: appUser.id,
                    page,
                    size: 10,
                    deckSort: sort,
                },
                signal,
            });

            return response.data;
        },
        [sort, appUser, token]
    );

    const {
        items: decks,
        loading,
        loadingMore,
        error,
        loadMoreError,
        hasNext,
        sentinelRef,
        retryLoadingMore,
    } = useInfiniteScroll(fetchDecks);


    if (!isAuthenticated) {
        return null;
    }

    return (
        <main className="my-profile-page">
            <h1>My Profile</h1>

            {appUserLoading && (
                <LoadingIndicator message="Loading profile..." />
            )}

            {appUserError && (
                <CustomAlert
                    title="Failed to load user"
                    description={appUserError}
                    actionLabel="Login"
                    actionUrl="/login"
                />
            )}

            {!appUserLoading && !appUserError && (
                <>
                    <ProfileHeader
                        appUser={appUser}
                    />

                    <section className="my-profile-decks">
                        <div className="my-profile-decks-header">
                            <h2>My Decks</h2>

                            <button
                                type="button"
                                className="my-profile-create-deck"
                                onClick={() => navigate("/decks/create")}
                            >
                                Create Deck
                            </button>
                        </div>

                        <DeckSort sort={sort} setSort={setSort} />

                        {loading && (
                            <LoadingIndicator message="We're getting your decks ready..." />
                        )}

                        {error && (
                            <CustomAlert
                                title="Failed to load decks"
                                description={error}
                            />
                        )}

                        {!loading && !error && decks.length === 0 && (
                            <CustomAlert
                                title="No decks yet 📚"
                                description="This user hasn't created any decks yet."
                            />
                        )}

                        {!loading && !error && (
                            <>
                                <DeckList decks={decks} showAppUser={false} />

                                {loadingMore &&
                                    <CustomAlert
                                        title="Just a moment..."
                                        description="Loading more decks..."
                                    />}

                                {loadMoreError && (
                                    <CustomAlert
                                        title="Failed to load more decks"
                                        description={loadMoreError}
                                        actionLabel="Try again"
                                        onAction={retryLoadingMore}
                                    />
                                )}

                                {hasNext && !loadMoreError && (
                                    <div ref={sentinelRef} aria-hidden="true" />
                                )}
                            </>
                        )}
                    </section>

                </>
            )}
        </main>
    );
}

export default MyProfilePage;
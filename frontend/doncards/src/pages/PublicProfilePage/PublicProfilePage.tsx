import axios from "axios";
import { useCallback, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import CustomAlert from "../../components/CustomAlert/CustomAlert";
import DeckList from "../../components/DeckList/DeckList";
import DeckSort, { type DeckSortOption } from "../../components/DeckSort/DeckSort";
import LoadingIndicator from "../../components/LoadingIndicator/LoadingIndicator";
import ProfileHeader from "../../components/ProfileHeader/ProfileHeader";
import API_URL from "../../config/api";
import { useInfiniteScroll } from "../../hooks/useInfiniteScroll";
import type { AppUserDetailsResponse } from "../../types/appUser/AppUserDetailsResponse";
import type { DeckDetailsResponse } from "../../types/deck/DeckDetailsResponse";
import type { PageResponse } from "../../types/PageResponse";
import { getErrorMessage } from "../../utils/getErrorMessage";
import './PublicProfilePage.css';

function PublicProfilePage() {
    const { userId } = useParams();

    const [appUserLoading, setAppUserLoading] = useState(true);
    const [appUserError, setAppUserError] = useState<string | null>(null);
    const [appUser, setAppUser] = useState<AppUserDetailsResponse>();

    const [sort, setSort] = useState<DeckSortOption>("POPULAR");

    useEffect(() => {
        async function fetchAppUser() {
            try {
                setAppUserLoading(true);
                setAppUserError(null);

                const response = await axios.get<AppUserDetailsResponse>(
                    `${API_URL}/app-users/${userId}`
                );

                setAppUser(response.data);
            } catch (error: unknown) {
                setAppUserError(getErrorMessage(error));
            } finally {
                setAppUserLoading(false);
            }
        }

        fetchAppUser();
    }, [userId]);

    const fetchDecks = useCallback(
        async (page: number, signal: AbortSignal) => {
            if (!userId || !appUser) {
                throw new Error("User not found.");
            }
            const response = await axios.get<
                PageResponse<DeckDetailsResponse>
            >(`${API_URL}/decks/by-user`, {
                params: {
                    appUserId: userId,
                    page,
                    size: 10,
                    deckSort: sort,
                },
                signal,
            });

            return response.data;
        },
        [sort, appUser, userId]
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


    return (
        <main className="public-profile-page">
            <h1>Profile</h1>

            {appUserLoading && (
                <LoadingIndicator message="Loading profile..." />
            )}

            {appUserError && (
                <CustomAlert
                    title="Failed to load user"
                    description={appUserError}
                />
            )}

            {!appUserLoading && !appUserError && (
                <>
                    <ProfileHeader
                        appUser={appUser}
                    />

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

                </>
            )}
        </main>
    )
}

export default PublicProfilePage;

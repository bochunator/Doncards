import axios from "axios";
import { useCallback, useState } from "react";
import CustomAlert from "../../components/CustomAlert/CustomAlert";
import DeckList from "../../components/DeckList/DeckList";
import DeckSort, { type DeckSortOption, } from "../../components/DeckSort/DeckSort";
import LoadingIndicator from "../../components/LoadingIndicator/LoadingIndicator";
import API_URL from "../../config/api";
import { useInfiniteScroll } from "../../hooks/useInfiniteScroll";
import type { DeckDetailsResponse } from "../../types/deck/DeckDetailsResponse";
import type { PageResponse } from "../../types/PageResponse";
import "./HomePage.css";

function HomePage() {
    const [sort, setSort] = useState<DeckSortOption>("POPULAR");

    const fetchDecks = useCallback(
        async (page: number, signal: AbortSignal) => {
            const response = await axios.get<
                PageResponse<DeckDetailsResponse>
            >(`${API_URL}/decks`, {
                params: {
                    page,
                    size: 10,
                    deckSort: sort,
                },
                signal,
            });

            return response.data;
        },
        [sort]
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
        <main className="home-page">
            <h1>Discover decks</h1>

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

            {!loading && !error && (
                <>
                    <DeckList decks={decks} showAppUser={true} />

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
        </main>
    );
}

export default HomePage;

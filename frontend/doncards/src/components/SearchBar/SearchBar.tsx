
import { useCallback, useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

import API_URL from "../../config/api";
import { useInfiniteScroll } from "../../hooks/useInfiniteScroll";

import type { AppUserSearchResponse } from "../../types/appUser/AppUserSearchResponse";
import type { DeckSearchResponse } from "../../types/deck/DeckSearchResponse";
import type { PageResponse } from "../../types/PageResponse";

import "./SearchBar.css";

function highlightFirstMatch(text: string, query: string) {
    const index = text.toLowerCase().indexOf(query.toLowerCase());

    if (index === -1 || !query) {
        return text;
    }

    return (
        <>
            {text.slice(0, index)}
            <span className="search-highlight">
                {text.slice(index, index + query.length)}
            </span>
            {text.slice(index + query.length)}
        </>
    );
}

function SearchBar() {
    const [query, setQuery] = useState("");
    const [debouncedQuery, setDebouncedQuery] = useState("");
    const [isOpen, setIsOpen] = useState(false);

    const searchRef = useRef<HTMLDivElement>(null);

    // Debounce
    useEffect(() => {
        const timeout = setTimeout(() => {
            setDebouncedQuery(query.trim());
        }, 300);

        return () => clearTimeout(timeout);
    }, [query]);

    const isSearchEnabled = debouncedQuery.length > 0;

    // Fetch users
    const fetchUsers = useCallback(
        async (page: number, signal: AbortSignal) => {
            const response = await axios.get<
                PageResponse<AppUserSearchResponse>
            >(`${API_URL}/app-users/search`, {
                params: {
                    query: debouncedQuery,
                    page,
                    size: 10,
                },
                signal,
            });

            return response.data;
        },
        [debouncedQuery]
    );

    // Fetch decks
    const fetchDecks = useCallback(
        async (page: number, signal: AbortSignal) => {
            const response = await axios.get<
                PageResponse<DeckSearchResponse>
            >(`${API_URL}/decks/search`, {
                params: {
                    query: debouncedQuery,
                    page,
                    size: 10,
                },
                signal,
            });

            return response.data;
        },
        [debouncedQuery]
    );

    // Independent infinite scroll instances
    const {
        items: users,
        loading: usersLoading,
        loadingMore: usersLoadingMore,
        error: usersError,
        loadMoreError: usersLoadMoreError,
        hasNext: usersHasNext,
        sentinelRef: usersSentinelRef,
        retryLoadingMore: retryUsers,
    } = useInfiniteScroll(fetchUsers, isSearchEnabled);

    const {
        items: decks,
        loading: decksLoading,
        loadingMore: decksLoadingMore,
        error: decksError,
        loadMoreError: decksLoadMoreError,
        hasNext: decksHasNext,
        sentinelRef: decksSentinelRef,
        retryLoadingMore: retryDecks,
    } = useInfiniteScroll(fetchDecks, isSearchEnabled);

    // Close dropdown
    useEffect(() => {
        const handleClickOutside = (event: MouseEvent) => {
            if (
                searchRef.current &&
                !searchRef.current.contains(event.target as Node)
            ) {
                setIsOpen(false);
            }
        };

        const handleEscape = (event: KeyboardEvent) => {
            if (event.key === "Escape") {
                setIsOpen(false);
            }
        };

        document.addEventListener("mousedown", handleClickOutside);
        document.addEventListener("keydown", handleEscape);

        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
            document.removeEventListener("keydown", handleEscape);
        };
    }, []);

    const handleSelect = () => {
        setIsOpen(false);
        setQuery("");
    };

    const isDebouncing = query.trim() !== debouncedQuery;

    const isLoading =
        isDebouncing || usersLoading || decksLoading;

    const hasResults = users.length > 0 || decks.length > 0;

    const hasError = usersError || decksError;

    return (
        <div className="search-container" ref={searchRef}>
            <div className="search-input-wrapper">
                <span className="search-icon">⌕</span>

                <input
                    type="text"
                    className="search-input"
                    placeholder="Search users and decks..."
                    value={query}
                    onChange={(event) => {
                        setQuery(event.target.value);
                        setIsOpen(true);
                    }}
                    onFocus={() => setIsOpen(true)}
                />

                {query && (
                    <button
                        type="button"
                        className="search-clear"
                        onClick={() => {
                            setQuery("");
                            setDebouncedQuery("");
                            setIsOpen(false);
                        }}
                        aria-label="Clear search"
                    >
                        ×
                    </button>
                )}
            </div>

            {isOpen && query.trim() && (
                <div className="search-dropdown">

                    {isLoading ? (
                        <div className="search-message">
                            Searching...
                        </div>
                    ) : (
                        <>
                            {!hasResults && !hasError && (
                                <div className="search-message">
                                    No results found
                                </div>
                            )}

                            {/* USERS */}
                            {(users.length > 0 || usersError) && (
                                <div className="search-section">
                                    <div className="search-section-title">
                                        Users
                                    </div>

                                    {usersError && (
                                        <div className="search-message">
                                            Failed to load users.
                                        </div>
                                    )}

                                    {users.map((user) => (
                                        <Link
                                            key={user.id}
                                            to={`/users/${user.id}`}
                                            className="search-result"
                                            onClick={handleSelect}
                                        >
                                            <span>{highlightFirstMatch(user.username, debouncedQuery)}</span>
                                            <span className="search-result-type">
                                                user
                                            </span>
                                        </Link>
                                    ))}

                                    {usersLoadingMore && (
                                        <div className="search-message">
                                            Loading more users...
                                        </div>
                                    )}

                                    {usersLoadMoreError && (
                                        <div className="search-load-error">
                                            <span>
                                                Failed to load more users.
                                            </span>
                                            <button
                                                type="button"
                                                onClick={retryUsers}
                                            >
                                                Try again
                                            </button>
                                        </div>
                                    )}

                                    {usersHasNext && !usersLoadMoreError && (
                                        <div
                                            ref={usersSentinelRef}
                                            aria-hidden="true"
                                        />
                                    )}
                                </div>
                            )}

                            {users.length > 0 && decks.length > 0 && (
                                <div className="search-divider" />
                            )}

                            {/* DECKS */}
                            {(decks.length > 0 || decksError) && (
                                <div className="search-section">
                                    <div className="search-section-title">
                                        Decks
                                    </div>

                                    {decksError && (
                                        <div className="search-message">
                                            Failed to load decks.
                                        </div>
                                    )}

                                    {decks.map((deck) => (
                                        <Link
                                            key={deck.id}
                                            to={`/deck/${deck.id}`}
                                            className="search-result"
                                            onClick={handleSelect}
                                        >
                                            <span>{highlightFirstMatch(deck.name, debouncedQuery)}</span>
                                            <span className="search-result-type">
                                                deck
                                            </span>
                                        </Link>
                                    ))}

                                    {decksLoadingMore && (
                                        <div className="search-message">
                                            Loading more decks...
                                        </div>
                                    )}

                                    {decksLoadMoreError && (
                                        <div className="search-load-error">
                                            <span>
                                                Failed to load more decks.
                                            </span>
                                            <button
                                                type="button"
                                                onClick={retryDecks}
                                            >
                                                Try again
                                            </button>
                                        </div>
                                    )}

                                    {decksHasNext && !decksLoadMoreError && (
                                        <div
                                            ref={decksSentinelRef}
                                            aria-hidden="true"
                                        />
                                    )}
                                </div>
                            )}
                        </>
                    )}
                </div>
            )}
        </div>
    );
}

export default SearchBar;

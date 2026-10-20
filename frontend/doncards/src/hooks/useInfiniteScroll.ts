import { useCallback, useEffect, useRef, useState } from "react";
import type { PageResponse } from "../types/PageResponse";
import { getErrorMessage } from "../utils/getErrorMessage";

type FetchPage<T> = (
    page: number,
    signal: AbortSignal
) => Promise<PageResponse<T>>;

export function useInfiniteScroll<T>(fetchPage: FetchPage<T>) {
    const [items, setItems] = useState<T[]>([]);
    const [page, setPage] = useState(0);
    const [hasNext, setHasNext] = useState(false);

    const [loading, setLoading] = useState(true);
    const [loadingMore, setLoadingMore] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [loadMoreError, setLoadMoreError] = useState<string | null>(null);

    const sentinelRef = useRef<HTMLDivElement>(null);
    const loadingMoreRef = useRef(false);
    const requestControllerRef = useRef<AbortController | null>(null);

    useEffect(() => {
        const controller = new AbortController();
        requestControllerRef.current = controller;

        setItems([]);
        setPage(0);
        setHasNext(false);
        setError(null);
        setLoadMoreError(null);
        setLoading(true);
        setLoadingMore(false);
        loadingMoreRef.current = false;

        async function fetchFirstPage() {
            try {
                const response = await fetchPage(0, controller.signal);

                if (controller.signal.aborted) return;

                setItems(response.content);
                setPage(response.page);
                setHasNext(response.hasNext);
            } catch (error: unknown) {
                if (!controller.signal.aborted) {
                    setError(getErrorMessage(error));
                }
            } finally {
                if (!controller.signal.aborted) {
                    setLoading(false);
                }
            }
        }

        fetchFirstPage();

        return () => {
            controller.abort();
            requestControllerRef.current?.abort();
        };
    }, [fetchPage]);

    const fetchNextPage = useCallback(async () => {
        if (loading || loadingMoreRef.current || !hasNext) return;

        loadingMoreRef.current = true;
        setLoadingMore(true);
        setLoadMoreError(null);

        const controller = new AbortController();
        requestControllerRef.current = controller;

        try {
            const response = await fetchPage(page + 1, controller.signal);

            if (controller.signal.aborted) return;

            setItems((current) => [...current, ...response.content]);
            setPage(response.page);
            setHasNext(response.hasNext);
        } catch (error: unknown) {
            if (!controller.signal.aborted) {
                setLoadMoreError(getErrorMessage(error));
            }
        } finally {
            if (!controller.signal.aborted) {
                loadingMoreRef.current = false;
                setLoadingMore(false);
            }
        }
    }, [fetchPage, hasNext, loading, page]);

    useEffect(() => {
        const sentinel = sentinelRef.current;

        if (
            !sentinel ||
            loading ||
            loadingMore ||
            !hasNext ||
            loadMoreError
        ) {
            return;
        }

        const observer = new IntersectionObserver(([entry]) => {
            if (entry.isIntersecting) {
                observer.disconnect();
                fetchNextPage();
            }
        }, { rootMargin: "200px" });

        observer.observe(sentinel);

        return () => observer.disconnect();
    }, [fetchNextPage, hasNext, loading, loadingMore, loadMoreError]);

    return {
        items,
        loading,
        loadingMore,
        error,
        loadMoreError,
        hasNext,
        sentinelRef,
        retryLoadingMore: () => setLoadMoreError(null),
    };
}

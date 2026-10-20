export type PageResponse<T> = {
    content: T[];
    page: number;
    size: number;
    totalPages: number;
    hasNext: boolean;
};

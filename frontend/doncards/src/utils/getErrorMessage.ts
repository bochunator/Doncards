import axios from "axios";

export function getErrorMessage(error: unknown): string {
    if (axios.isAxiosError(error)) {
        if (error.response?.status === 401) {
            return "Your session has expired. Please log in again.";
        }
        
        if (!error.response) {
            return "Unable to connect to the server. Please try again later.";
        }

        const data: unknown = error.response.data;

        if (typeof data === "string" && data.trim()) {
            return data;
        }

        return "The request failed. Please try again.";
    }

    return "An unexpected error occurred.";
}

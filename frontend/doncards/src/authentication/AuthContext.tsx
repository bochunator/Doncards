import {
    createContext,
    useContext,
    useState,
    type ReactNode,
} from "react";

interface AuthContextType {
    token: string | null;
    username: string | null;
    isAuthenticated: boolean;
    login: (token: string, username: string) => void;
    logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface AuthProviderProps {
    children: ReactNode;
}

export function AuthProvider({ children }: AuthProviderProps) {
    const [token, setToken] = useState<string | null>(
        () => localStorage.getItem("accessToken")
    );

    const [username, setUsername] = useState<string | null>(
        () => localStorage.getItem("username")
    );

    const login = (accessToken: string, loggedInUsername: string) => {
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("username", loggedInUsername);

        setToken(accessToken);
        setUsername(loggedInUsername);
    };

    const logout = () => {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("username");

        setToken(null);
        setUsername(null);
    };

    return (
        <AuthContext.Provider
            value={{
                token,
                username,
                isAuthenticated: Boolean(token),
                login,
                logout,
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const context = useContext(AuthContext);

    if (!context) {
        throw new Error("useAuth must be used within an AuthProvider");
    }

    return context;
}
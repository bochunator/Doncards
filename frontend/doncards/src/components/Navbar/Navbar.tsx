import { Link, useLocation } from "react-router-dom";
import "./Navbar.css";
import { useAuth } from "../../authentication/AuthContext";
import SearchBar from "../SearchBar/SearchBar";

function Navbar() {
    const location = useLocation();
    const { username, isAuthenticated, logout } = useAuth();

    const isLoginPage = location.pathname === "/login";

    return (
        <header className="navbar">
            <div className="navbar-content">
                <Link to="/" className="navbar-brand">
                    <div className="logo">D</div>
                    <span>oncards</span>
                </Link>

                <SearchBar />

                {isAuthenticated ? (
                    <div className="profile-view">
                        <Link to="/profile" className="profile-link">
                            <span className="profile-avatar" aria-hidden="true">
                                {(username?.charAt(0) || "U").toUpperCase()}
                            </span>
                            <span className="profile-username">{username}</span>
                        </Link>
                        <button
                            type="button"
                            className="profile-logout"
                            onClick={logout}
                            aria-label="Log out"
                            title="Log out"
                        >
                            <svg
                                width="19"
                                height="19"
                                viewBox="0 0 24 24"
                                fill="none"
                                stroke="currentColor"
                                strokeWidth="2"
                                strokeLinecap="round"
                                strokeLinejoin="round"
                            >
                                <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" />
                                <polyline points="16 17 21 12 16 7" />
                                <line x1="21" y1="12" x2="9" y2="12" />
                            </svg>
                        </button>
                    </div>
                ) : (
                    !isLoginPage && (
                        <Link to="/login" className="login-link">
                            Sign in
                        </Link>
                    )
                )}
            </div>
        </header>
    );
}

export default Navbar;
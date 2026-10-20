import { Link, useLocation } from "react-router-dom";
import "./Navbar.css";
import { useAuth } from "../../authentication/AuthContext";

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
                        >
                            Log out
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
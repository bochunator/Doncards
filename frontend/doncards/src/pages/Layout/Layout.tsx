import { Link, Outlet } from 'react-router-dom'
import CustomAlert from '../../components/Alert/CustomAlert'


function Layout() {
    return (
        <>
            <nav>
                <Link to="/Doncards/">Home</Link>
                {" | "}
                <Link to="/Doncards/admin">Admin</Link>
                {" | "}
                <Link to="/Doncards/user">User</Link>
                {" | "}
                <Link to="/Doncards/user/deck">Deck</Link>
                {" | "}
                <Link to="/Doncards/info">Info</Link>
                {" | "}
                <Link to="/Doncards/auth/register">Registration</Link>
                {" | "}
                <Link to="/Doncards/auth/login">Login</Link>
                {" | "}
                <Link to="/Doncards/auth/logout">Logout</Link>
            </nav>
            <h1>Doncards</h1>
            <CustomAlert />
            <Outlet />
        </>
    )
}

export default Layout

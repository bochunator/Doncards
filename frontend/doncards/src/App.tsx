import './App.css'
import { Link, Outlet } from 'react-router-dom'

function App() {
  return (
    <>
    <nav>
      <Link to="/Doncards/">Home</Link>
      {" | "}
      <Link to="/Doncards/contact">Contact</Link>
      {" | "}
      <Link to="/Doncards/auth/register">Registration</Link>
      {" | "}
      <Link to="/Doncards/auth/login">Login</Link>
    </nav>
      <h1>Doncards</h1>
      <Outlet />
    </>
  )
}

export default App

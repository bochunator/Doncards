import { Component } from 'react';
import { Outlet, Link } from "react-router-dom";
import '../styles/Layout.css';

class Layout extends Component {
  render() {
    return (
      <>
      <header>
        <nav id="topnav">
          <ul class="menu">
            <li>
              <Link to="/" className='Link'>Home</Link>
            </li>
            <li>
              <Link to="/login" className='Link'>Log in</Link>
            </li>
            <li>
              <Link to="/signup" className='Link'>Sign up</Link>
            </li>
          </ul>
        </nav>
        </header>
        <Outlet />
      </>
    );
  }
}

export default Layout;
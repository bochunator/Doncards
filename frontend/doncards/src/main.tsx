import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import Home from './pages/Home.tsx'
import Contact from './pages/Contact.tsx'
import Registration from './pages/Registration/Registration.tsx'
import Login from './pages/Login/Login.tsx'
import AuthProvider from 'react-auth-kit'
import createStore from 'react-auth-kit/createStore'
import RequireAuth from '@auth-kit/react-router/RequireAuth'


const store = createStore({
    authName:'_auth',
    authType:'cookie',
    cookieDomain: window.location.hostname,
    cookieSecure: window.location.protocol === 'http:',
  });

const router = createBrowserRouter([
    {
        path: "/Doncards/",
        element: <App />,
        children: [
            {
                path: "/Doncards/",
                element: <Home />
            },
            {
                path: "/Doncards/contact",
                element: <RequireAuth fallbackPath="/Doncards/auth/login">
                    <Contact />
                </RequireAuth>
            },
            {
                path: "/Doncards/auth/register",
                element: <Registration />
            },
            {
                path: "/Doncards/auth/login",
                element: <Login />
            }
        ]
    }
])

ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
        <AuthProvider store={store}>
            <RouterProvider router={router} />
        </AuthProvider>
    </React.StrictMode>,
)

// Use contextBridge
window.ipcRenderer.on('main-process-message', (_event, message) => {
    console.log(message)
})

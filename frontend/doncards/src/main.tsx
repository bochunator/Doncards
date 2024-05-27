import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import Home from './pages/Home.tsx'
import Contact from './pages/Contact.tsx'
import Registration from './pages/Registration/Registration.tsx'

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
        element: <Contact />
      },
      {
        path: "/Doncards/auth/register",
        element: <Registration />
      }
    ]
  }
])

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
)

// Use contextBridge
window.ipcRenderer.on('main-process-message', (_event, message) => {
  console.log(message)
})

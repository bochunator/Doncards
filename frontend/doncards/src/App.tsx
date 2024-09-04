import React, { useEffect } from 'react'
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'

import './App.css'
import RequireAuth from './components/RequireAuth.tsx'
import ProtectedRoute from './components/ProtectedRoute.tsx'
import Layout from './pages/Layout/Layout'
import Home from './pages/Home/Home.tsx'
import Registration from './pages/Registration/Registration.tsx'
import Login from './pages/Login/Login.tsx'
import Unauthorized from './pages/Unauthorized.tsx'
import Missing from './pages/Missing.tsx'
import { AppDispatch, RootState } from './redux/Store.ts'
import { updateJwt, verifyUserByToken } from './redux/Slices/AuthSlice.ts'
import User from './pages/Profile.tsx'
import Logout from './pages/Logout.tsx'
import Admin from './pages/Admin.tsx'
import CreateDeck from './pages/CreateDeck/CreateDeck.tsx'
import Profile from './pages/Profile.tsx'
import { ROLES } from './types/applicationUserTypes.ts'
import LearningSession from './pages/LearningSession/LearningSession.tsx'
import Info from './pages/Info.tsx'


const router = createBrowserRouter([
    {
        path: "/Doncards/",
        element: <Layout />,
        children: [
            {
                path: "/Doncards/",
                element: <Home />
            },
            {
                path: "/Doncards/unauthorized",
                element: <Unauthorized />
            },
            {
                path: "/Doncards/admin",
                element: <RequireAuth allowedRoles={[ROLES.ADMIN]}><Admin /></RequireAuth>
            },
            {
                path: "/Doncards/user",
                element: <RequireAuth allowedRoles={[ROLES.USER]}><User /></RequireAuth>
            },
            {
                path: "/Doncards/user/deck",
                element: <RequireAuth allowedRoles={[ROLES.USER]}><CreateDeck /></RequireAuth>
            },
            {
                path: "/Doncards/info",
                element: <Info />
            },
            {
                path: "/Doncards/auth/register",
                element: <ProtectedRoute ><Registration /></ProtectedRoute>
            },
            {
                path: "/Doncards/auth/login",
                element: <ProtectedRoute ><Login /></ProtectedRoute>
            },
            {
                path: "/Doncards/auth/logout",
                element: <RequireAuth allowedRoles={[ROLES.USER]}><Logout /></RequireAuth>
            },
            {
                path: "/Doncards/profile/:userId",
                element: <Profile />
            },
            {
                path: "/Doncards/learning/:deckId",
                element: <LearningSession />
            },
            {
                path: "*",
                element: <Missing />
            }
        ]
    }
])

const App: React.FC = () => {
    const dispatch: AppDispatch = useDispatch()
    const { jwt } = useSelector((state: RootState) => state.auth)
    useEffect(() => {
        const storedJwt = localStorage.getItem('jwt')
        if (storedJwt !== null && jwt !== null) {
            dispatch(verifyUserByToken(jwt))
        } else if (storedJwt === null && jwt !== null) {
            localStorage.setItem('jwt', jwt)
        } else if (storedJwt !== null && jwt === null) {
            dispatch(updateJwt(storedJwt))
        }
    }, [jwt])
    return (
        <RouterProvider router={router} />
    )
}

export default App

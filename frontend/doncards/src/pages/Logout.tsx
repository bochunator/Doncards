import React, { useEffect } from 'react'
import { useDispatch } from 'react-redux'

import { AppDispatch } from '../redux/Store'
import { logoutUser } from '../redux/Slices/AuthSlice'
import { useNavigate } from '../hooks/useNavigate'


const Logout: React.FC = () => {
    const dispatch: AppDispatch = useDispatch()
    const navigate = useNavigate()

    useEffect(() => {
        localStorage.removeItem('jwt')
        dispatch(logoutUser())
        navigate("/Doncards/auth/login")
    }, [])

    return (
        <div>Logging out...</div>
    )
}

export default Logout
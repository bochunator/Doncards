import React, { ReactNode, useEffect } from "react"
import { useDispatch, useSelector } from "react-redux"

import { AppDispatch, RootState } from "../redux/Store"
import { useNavigate } from "../hooks/useNavigate"
import { CUSTOM_ALERTS } from "../types/authTypes"
import { updateAlert } from "../redux/Slices/AuthSlice"


interface ProtectedRouteProps {
    children: ReactNode
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ children }) => {
    const dispatch: AppDispatch = useDispatch()
    const navigate = useNavigate()
    const { applicationUser } = useSelector((state: RootState) => state.auth)
    const hasAccess = applicationUser !== null
    useEffect(() => {
        if (hasAccess) {
            dispatch(updateAlert({ variant: CUSTOM_ALERTS.SUCCESS, heading: 'Logged In', message: 'You are logged in. Redirected to the home page.' }))
            navigate("/Doncards/")
        }
    }, [hasAccess])
    return hasAccess ? null : <>{children}</>
}

export default ProtectedRoute

import React, { ReactNode, useEffect } from "react"
import { useDispatch, useSelector } from "react-redux"

import { AppDispatch, RootState } from "../redux/Store"
import { updateAlert } from "../redux/Slices/AuthSlice"
import { useNavigate } from "../hooks/useNavigate"
import { CUSTOM_ALERTS } from "../types/authTypes"


interface RequireAuthProps {
    allowedRoles: string[]
    children: ReactNode
}

const RequireAuth: React.FC<RequireAuthProps> = ({ allowedRoles, children }) => {
    const dispatch: AppDispatch = useDispatch()
    const navigate = useNavigate()
    const { applicationUser } = useSelector((state: RootState) => state.auth)
    const hasAccess = applicationUser?.authorities.some((role) => allowedRoles.includes(role.authority))
    useEffect(() => {
        if (!hasAccess) {
            if (applicationUser) {
                navigate("/Doncards/unauthorized")
            } else {
                dispatch(updateAlert({ variant: CUSTOM_ALERTS.DANGER, heading: 'Access denied', message: 'Please log in to continue' }))
                navigate("/Doncards/auth/login")
            }
        }
    }, [])
    return hasAccess ? <>{children}</> : null
}

export default RequireAuth

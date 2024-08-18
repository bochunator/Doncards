import { NavigateOptions, To, useNavigate as useReactRouterNavigate } from "react-router-dom"
import { useDispatch } from "react-redux"

import { AppDispatch } from "../redux/Store"
import { updateRedirected } from "../redux/Slices/AuthSlice"


export const useNavigate = () => {
    const reactRouterNavigate = useReactRouterNavigate()
    const dispatch: AppDispatch = useDispatch()
    const navigate = (to: To, options?: NavigateOptions) => {
        dispatch(updateRedirected(true))
        reactRouterNavigate(to, options)
    }
    return navigate
}

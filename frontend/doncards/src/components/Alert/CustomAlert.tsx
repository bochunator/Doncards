import { Alert } from 'react-bootstrap';

import './CustomAlert.css'
import { AppDispatch, RootState } from '../../redux/Store';
import { useDispatch, useSelector } from 'react-redux';
import { CustomAlertState, defaultCustomAlertState } from '../../types/authTypes';
import { updateAlert, updateRedirected } from '../../redux/Slices/AuthSlice';
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';


const CustomAlert: React.FC = () => {
    const dispatch: AppDispatch = useDispatch()
    const { customAlertState, redirected } = useSelector((state: RootState) => state.auth)
    const [localAlert, setLocalAlert] = useState<CustomAlertState>(defaultCustomAlertState)
    const { variant, heading, message } = localAlert
    const location = useLocation()

    const closeAlert = () => {
        setLocalAlert(defaultCustomAlertState)
        dispatch(updateAlert(defaultCustomAlertState))
    }

    useEffect(() => {
        if (!redirected) {
            closeAlert()
        } else {
            setLocalAlert({...customAlertState})
            dispatch(updateRedirected(false))
            dispatch(updateAlert(defaultCustomAlertState))
        }
    }, [location])

    return (
        <>
            {message && (
                <div className="alert">
                    <Alert variant={variant} onClose={closeAlert} dismissible>
                        <Alert.Heading>{heading}</Alert.Heading>
                        <p>{message}</p>
                    </Alert>
                </div>
            )}
        </>
    )
}

export default CustomAlert

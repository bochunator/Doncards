import { Alert } from 'react-bootstrap';

import './CustomAlert.css'

interface CustomAlertProps {
    visible: boolean;
    variant: string;
    heading: string;
    message: string;
    onClose: () => void;
}

const CustomAlert: React.FC<CustomAlertProps> = ({ visible, variant, heading, message, onClose }) => {
    return (
        <>
            {visible && (
                <div className="alert">
                    <Alert variant={variant} onClose={onClose}>
                        <Alert.Heading>{heading}</Alert.Heading>
                        <p>{message}</p>
                    </Alert>
                </div>
            )}
        </>
    )
}

export default CustomAlert
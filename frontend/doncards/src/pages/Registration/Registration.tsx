import { useState } from 'react';
import { Form, Button } from 'react-bootstrap'

import './Registration.css'
import CustomAlert from '../../components/CustomAlert/CustomAlert';

const defaultFormData = {
    email: "",
    username: "",
    password: "",
}

const defaultCustomAlert = {
    visible: true,
    variant: "variant",
    heading: "heading",
    message: "message",
    onClose: () => { }
}

const Registration: React.FC = () => {
    const [formData, setFormData] = useState(defaultFormData)
    const { email, username, password } = formData
    const [customAlert, setCustomAlert] = useState(defaultCustomAlert)

    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData(prevState => ({
            ...prevState,
            [e.target.id]: e.target.value,
        }))
    }

    const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        // console.log(formData)
        registerUser()
        // setFormData(defaultFormData)
    }

    const updateAlert = (variant: string, heading: string, message: string) => {
        setCustomAlert({
            ...customAlert,
            variant: variant,
            heading: heading,
            message: message
        });
    };

    const registerUser = async () => {
        try {
            const response = await fetch(`${import.meta.env.VITE_DONCARDS_BACKEND_URL}/auth/register`, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, username, password }),
            });
            const responseData = await response.json();
            if (response.status === 200) {
                updateAlert("success", "User registered!", responseData.message)
            } else if (response.status === 422) {
                updateAlert('danger', 'User already exists!', responseData.message);
            } else {
                updateAlert('danger', 'User not registered!', 'Something went wrong.');
            }
        } catch (error) {
            updateAlert('danger', 'Error!', 'Something went wrong.');
        }
    }

    return (
        <>
            <div className="register">
                <Form onSubmit={(e) => onSubmit(e)}>
                    <Form.Group controlId="email">
                        <Form.Label>E-mail</Form.Label>
                        <Form.Control autoFocus name="email" value={email} onChange={onChange} />
                    </Form.Group>
                    <Form.Group controlId="username">
                        <Form.Label>Username</Form.Label>
                        <Form.Control name="username" value={username} onChange={onChange} />
                    </Form.Group>
                    <Form.Group controlId="password">
                        <Form.Label>Password</Form.Label>
                        <Form.Control type="password" name="password" value={password} onChange={onChange} />
                    </Form.Group>
                    <Button size="lg" type="submit">Register</Button>
                </Form>
            </div>
            <CustomAlert {...customAlert} />
        </>
    )
}

export default Registration
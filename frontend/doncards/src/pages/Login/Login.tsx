import { useState } from 'react'
import { Form, Button } from 'react-bootstrap'

import './Login.css'
import { Link } from 'react-router-dom'
import { LoginData, defaultLoginData } from '../../types/authTypes'
import { EMAIL_REGEX, PASSWORD_REGEX, USER_REGEX } from '../../utils/validationPatterns'
import { loginUser } from '../../redux/Slices/AuthSlice'
import { AppDispatch } from '../../redux/Store'
import { useDispatch } from 'react-redux'


const Login: React.FC = () => {
    const [loginData, setLoginData] = useState<LoginData>(defaultLoginData)
    const { loginPayload } = loginData
    const { identifier, password } = loginPayload
    const dispatch: AppDispatch = useDispatch()

    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target
        setLoginData(prevState => ({
            ...prevState,
            loginPayload: {
                ...prevState.loginPayload,
                [name]: value
            }
        }))
    }

    const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()

        validForm() && dispatch(loginUser(loginPayload))
    }

    const validEmail = () => {
        return EMAIL_REGEX.test(identifier)
    }

    const validUsername = () => {
        return USER_REGEX.test(identifier)
    }

    const validPassword = () => {
        return PASSWORD_REGEX.test(password)
    }

    const validForm = () => {
        return (validEmail() || validUsername()) && validPassword()
    }

    return (
        <>
            <section>
                <Form onSubmit={(e) => onSubmit(e)}>
                    <h1>Login</h1>
                    <Form.Group controlId="identifier">
                        <Form.Label>Email or Username</Form.Label>
                        <Form.Control
                            autoFocus
                            name="identifier"
                            value={identifier}
                            onChange={onChange}
                        />
                    </Form.Group>
                    <Form.Group controlId="password">
                        <Form.Label>Password</Form.Label>
                        <Form.Control
                            type="password"
                            name="password"
                            value={password}
                            onChange={onChange}
                        />
                    </Form.Group>
                    <Button type='submit' disabled={!validForm()} size='lg'>Sign In</Button>
                </Form>
                <div>
                    Need an Account?
                    <div> <Link to="/Doncards/auth/register" style={{ color: 'dodgerblue' }}>Sign Up</Link></div>
                </div>
            </section>
        </>
    )
}

export default Login
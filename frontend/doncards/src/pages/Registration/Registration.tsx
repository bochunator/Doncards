import { useEffect, useState } from 'react'
import { Form, Button } from 'react-bootstrap'
import { faCheck, faTimes, faInfoCircle } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { Link } from 'react-router-dom'

import './Registration.css'
import { useDispatch, useSelector } from 'react-redux'
import { registerUser } from '../../redux/Slices/AuthSlice'
import { AppDispatch, RootState } from '../../redux/Store'
import { RegistrationData, defaultRegistrationData } from '../../types/authTypes'
import { EMAIL_REGEX, PASSWORD_REGEX, USER_REGEX } from '../../utils/validationPatterns'
import { useNavigate } from '../../hooks/useNavigate'


const Registration: React.FC = () => {
    const [registrationData, setRegistrationData] = useState<RegistrationData>(defaultRegistrationData)
    const { registrationPayload, matchPassword } = registrationData
    const { email, username, password } = registrationPayload
    const dispatch: AppDispatch = useDispatch()
    const { message } = useSelector((state: RootState) => state.auth.customAlertState)
    const navigate = useNavigate()

    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target
        if (name in registrationPayload) {
            setRegistrationData(prevState => ({
                ...prevState,
                registrationPayload: {
                    ...prevState.registrationPayload,
                    [name]: value
                }
            }))
        } else {
            setRegistrationData(prevState => ({
                ...prevState,
                [name]: value
            }))
        }
    }

    const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        validForm() && dispatch(registerUser(registrationPayload))
    }

    useEffect(() => {
        if (message !== null) {
            navigate("/Doncards/auth/login")
        }
    }, [message])

    const validEmail = () => {
        return EMAIL_REGEX.test(email)
    }

    const validUsername = () => {
        return USER_REGEX.test(username)
    }

    const validPassword = () => {
        return PASSWORD_REGEX.test(password)
    }

    const validMatchPassword = () => {
        return PASSWORD_REGEX.test(matchPassword) && matchPassword === password
    }

    const validForm = () => {
        return validEmail() && validUsername() && validPassword() && validMatchPassword()
    }

    return (
        <>
            <section>
                <Form onSubmit={(e) => onSubmit(e)}>
                    <h1>Register</h1>
                    <Form.Group controlId='email'>
                        <Form.Label>E-mail
                            <FontAwesomeIcon
                                icon={validEmail() ? faCheck : faTimes}
                                className={!email ? 'hide' : validEmail() ? 'valid' : 'invalid'}
                            />
                        </Form.Label>
                        <Form.Control
                            autoFocus
                            type='email'
                            name='email'
                            value={email}
                            onChange={onChange}
                            placeholder='name@example.com'
                            className={`border-5 ${!email ? '' : validEmail() ? 'border-success' : 'border-danger'}`}
                            size='lg'
                        />
                        <div id="emailNote" className={email && !validEmail() ? "instructions" : "hide"}>
                            <div>
                                <FontAwesomeIcon icon={faInfoCircle} className='invalid' />
                                It can contain lowercase letters, digits, and special characters.
                            </div>
                        </div>
                    </Form.Group>
                    <Form.Group controlId='username'>
                        <Form.Label>Username
                            <FontAwesomeIcon
                                icon={validUsername() ? faCheck : faTimes}
                                className={!username ? 'hide' : validUsername() ? 'valid' : 'invalid'}
                            />
                        </Form.Label>
                        <Form.Control
                            type='text'
                            name='username'
                            value={username}
                            onChange={onChange}
                            className={`border-5 ${!username ? '' : validUsername() ? 'border-success' : 'border-danger'}`}
                            size='lg'
                        />
                        <div id="usernameNote" className={username && !validUsername() ? "instructions" : "hide"}>
                            <div>
                                <FontAwesomeIcon icon={faInfoCircle} className='invalid' />
                                4 to 24 characters.
                            </div>
                            <div>Must begin with a letter.</div>
                            <div>Letters, numbers, underscores, hyphens allowed.</div>
                        </div>
                    </Form.Group>
                    <Form.Group controlId='password'>
                        <Form.Label>Password
                            <FontAwesomeIcon
                                icon={validPassword() ? faCheck : faTimes}
                                className={!password ? 'hide' : validPassword() ? 'valid' : 'invalid'}
                            />
                        </Form.Label>
                        <Form.Control
                            type='password'
                            name='password'
                            value={password}
                            onChange={onChange}
                            className={`border-5 ${!password ? '' : validPassword() ? 'border-success' : 'border-danger'}`}
                            size='lg' />
                        <div id="passwordNote" className={password && !validPassword() ? "instructions" : "hide"}>
                            <div>
                                <FontAwesomeIcon icon={faInfoCircle} className='invalid' />
                                8 to 24 characters.
                            </div>
                            <div>Must include uppercase and lowercase letters, a number and a special character.</div>
                            <div>Allowed special characters: ! @ # $ %</div>
                        </div>
                    </Form.Group>
                    <Form.Group controlId='matchPassword'>
                        <Form.Label>Confirm Password
                            <FontAwesomeIcon
                                icon={validMatchPassword() ? faCheck : faTimes}
                                className={!matchPassword ? 'hide' : validMatchPassword() ? 'valid' : 'invalid'}
                            />
                        </Form.Label>
                        <Form.Control
                            type='password'
                            name='matchPassword'
                            value={matchPassword}
                            onChange={onChange}
                            className={`border-5 ${!matchPassword ? '' : validMatchPassword() ? 'border-success' : 'border-danger'}`}
                            size='lg'
                        />
                        <div id="passwordNote" className={matchPassword && !validMatchPassword() ? "instructions" : "hide"}>
                            <div>
                                <FontAwesomeIcon icon={faInfoCircle} className='invalid' />
                                Must match the first password input field.
                            </div>
                        </div>
                    </Form.Group>
                    <Button type='submit' disabled={!validForm()} size='lg'>Sign Up</Button>
                </Form>
                <div>
                    Already registered?
                    <div> <Link to="/Doncards/auth/login" style={{ color: 'dodgerblue' }}>Sign In</Link></div>
                </div>
            </section>
        </>
    )
}

export default Registration

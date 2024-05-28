import { useState } from 'react';
import axios from 'axios'
import useSignIn from 'react-auth-kit/hooks/useSignIn'
import { Form, Button } from 'react-bootstrap'

import './Login.css'

const defaultFormData = {
    username: "",
    password: "",
}

const Login: React.FC = () => {
    const [formData, setFormData] = useState(defaultFormData)
    const { username, password } = formData

    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData(prevState => ({
            ...prevState,
            [e.target.id]: e.target.value,
        }))
    }
    
    const signIn = useSignIn()
    const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        axios.post(`${import.meta.env.VITE_DONCARDS_BACKEND_URL}/auth/login`, formData) 
            .then((res)=>{
                console.log(res.data.user)
                if(res.status === 200){
                    console.log('Token:', res.data.jwt)
                    if(signIn({
                        auth: {
                            token: res.data.jwt,
                            type: 'Bearer'
                        },
                        userState: res.data.user//res.data.authUserState
                    })){ // Only if you are using refreshToken feature
                        // Redirect or do-something
                    }else {
                        //Throw error
                        console.log("NIE DZIALA LOGOWANIE!")
                    }
                }
            })
    }
    return (
        <>
            <div className="login">
                <Form onSubmit={(e) => onSubmit(e)}>
                    <Form.Group controlId="username">
                        <Form.Label>Username</Form.Label>
                        <Form.Control autoFocus name="username" value={username} onChange={onChange} />
                    </Form.Group>
                    <Form.Group controlId="password">
                        <Form.Label>Password</Form.Label>
                        <Form.Control type="password" name="password" value={password} onChange={onChange} />
                    </Form.Group>
                    <Button size="lg" type="submit">Register</Button>
                </Form>
            </div>
        </>
    )
}

export default Login
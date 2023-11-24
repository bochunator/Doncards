import React, { Component } from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import logo from './logo.svg';

class Login extends Component {
  handleSubmit = event => {
    event.preventDefault();
  }
  render() {
    return <div className='Login'>

      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <Form onSubmit={this.handleSubmit}>
            <Form.Group controlId="email" size="lg">
              <Form.Label>E-mail</Form.Label>
              <Form.Control autoFocus name="email" />
            </Form.Group>
            <Form.Group controlId="password" size="lg">
              <Form.Label>Password</Form.Label>
              <Form.Control name="password" />
            </Form.Group>
            <Button block size="lg" type="submit">Login</Button>
          </Form>
          <a
            className="App-link"
            href="asd"
            rel="noopener noreferrer"
          >
            Sign up
          </a>
          <a
            className="App-link"
            href="asd"
            rel="noopener noreferrer"
          >
            Continue as Guest
          </a>
        </header>
      </div>
    </div>
  };
}

export default Login;
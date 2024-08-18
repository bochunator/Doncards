// import React from 'react'
import { Provider } from 'react-redux'
import ReactDOM from 'react-dom/client'

import App from './App.tsx'
import './index.css'
import { store } from './redux/Store.ts'


ReactDOM.createRoot(document.getElementById('root')!).render(
    <Provider store={store}>
        {/*<React.StrictMode>*/}
            <App />
        {/*</React.StrictMode>*/}
    </Provider>
)

// Use contextBridge
window.ipcRenderer.on('main-process-message', (_event, message) => {
    console.log(message)
})

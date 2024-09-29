import React, { useState, useEffect } from 'react'
import viteLogo from './assets/vite.svg'
import './App.css'
import { sayHello, SimpleInput } from '@liferay/vacation-shared-code'

function App() {
  const [count, setCount] = useState(0)
  const [name, setName] = useState('');
  const [greeting, setGreeting] = useState('');

  useEffect(() => {
        setGreeting(sayHello(name));
  }, [name]);


    return (
    <>
      <div>
        <a href="https://vitejs.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.jsx</code> and save to test HMR
        </p>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
        <SimpleInput label="Enter name to be greeted:" name="UserName"
                     value={name} onChange={(e) => setName(e.target.value)} />
        <p>{greeting}</p>
    </>
  )
}

export default App

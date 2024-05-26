import React, { useState, useEffect } from 'react';

function App() {
  const [message, setMessage] = useState('');

    useEffect(() => {
        const interval = setInterval(() => {
            fetch('/api/trades')
                .then(response => response.text())
                .then(message => setMessage(message));
        }, 1000);

        // This is important, it's the cleanup function that clears the interval when the component is unmounted
        return () => clearInterval(interval);
    }, []);

  return (
      <div className="App">
        <header className="App-header">
          <p>{message}</p>
        </header>
      </div>
  );
}

export default App;
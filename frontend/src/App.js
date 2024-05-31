import React from 'react';
import './index.css'; // Ensure your styles are imported
import StockInfo from './components/StockInfo';

function App() {
    return (
        <div className="App">
            <header className="App-header">
                <h1>Prices</h1>
            </header>
            <div className="stock-container">
                <StockInfo symbol="BINANCE:BTCUSDT" />
                <StockInfo symbol="BINANCE:ETHUSDT" />
                <StockInfo symbol={"AMZN"} />
                <StockInfo symbol={"AAPL"} />
            </div>
        </div>
    );
}

export default App;

import React, { useState, useEffect } from 'react';

const symbolMap = {
    'BINANCE:BTCUSDT': 'BTC (Bitcoin)',
    'BINANCE:ETHUSDT': 'ETH (Ethereum)',
    'AMZN': 'AMZN (Amazon)',
    'AAPL': 'AAPL (Apple)',
};

function Stock({ symbol, initialPrice }) {
    const [stockData, setStockData] = useState({ price: initialPrice });
    const [priceChange, setPriceChange] = useState(0);
    const [priceChangePercentage, setPriceChangePercentage] = useState(0);
    const [changeColor, setChangeColor] = useState('black');

    const friendlyName = symbolMap[symbol] || symbol;

    useEffect(() => {
        const fetchStockData = async () => {
            try {
                const response = await fetch(`/api/stocks/${symbol}`);
                const data = await response.json();

                const newPrice = data.price;
                const change = newPrice - initialPrice;
                const percentageChange = ((change / initialPrice) * 100).toFixed(2);

                setPriceChange(change);
                setPriceChangePercentage(percentageChange);

                if (change > 0) {
                    setChangeColor('green');
                } else if (change < 0) {
                    setChangeColor('red');
                }

                setStockData(data);
            } catch (error) {
                console.error('Error fetching stock data:', error);
            }
        };

        fetchStockData(); // Fetch data immediately on mount

        const interval = setInterval(fetchStockData, 4000); // Fetch data every 2 seconds

        return () => clearInterval(interval); // Cleanup on unmount
    }, [symbol, initialPrice]);

    return (
        <div className="stock">
            <h2>{friendlyName}</h2>
            <p>Price: {stockData.price}</p>
            <p style={{color: changeColor}}>
                {priceChange.toFixed(2)} ({priceChangePercentage}%)
            </p>
        </div>
    );
}

export default Stock;

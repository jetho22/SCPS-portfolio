import React, { useState, useEffect } from 'react';
import Stock from './Stock';
import StockChart from './StockChart';

function StockInfo({ symbol }) {
    const [initialPrice, setInitialPrice] = useState(null);

    useEffect(() => {
        const fetchInitialPrice = async () => {
            const response = await fetch(`/api/stocks/${symbol}/history`);
            const data = await response.json();
            if (data.length > 0) {
                setInitialPrice(data[0].price); // Set the first price from the historical data
            }
        };

        fetchInitialPrice();
    }, [symbol]);

    return (
        <div className="stock-info">
            {initialPrice !== null && (
                <>
                    <Stock symbol={symbol} initialPrice={initialPrice} />
                    <StockChart symbol={symbol} initialPrice={initialPrice} />
                </>
            )}
        </div>
    );
}

export default StockInfo;

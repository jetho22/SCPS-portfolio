import React, { useState, useEffect } from 'react';
import { Line } from 'react-chartjs-2';
import 'chart.js/auto';

const symbolMap = {
    'BINANCE:BTCUSDT': 'BTC',
    'BINANCE:ETHUSDT': 'ETH',
};

function StockChart({ symbol }) {
    const [chartData, setChartData] = useState({ labels: [], datasets: [] });
    const friendlyName = symbolMap[symbol] || symbol;

    useEffect(() => {
        const fetchHistoricalData = async () => {
            const response = await fetch(`/api/stocks/${symbol}/history`);
            const data = await response.json();

            const labels = data.map(stock => new Date(stock.timestamp).toLocaleTimeString());
            const prices = data.map(stock => stock.price);

            setChartData({
                labels: labels,
                datasets: [
                    {
                        label: `${friendlyName} Price`,
                        data: prices,
                        borderColor: 'rgba(75,192,192,1)',
                        backgroundColor: 'rgba(75,192,192,0.2)',
                        fill: true,
                        pointRadius: 0,
                        pointHoverRadius: 1
                    },
                ],
            });
        };

        fetchHistoricalData();

        const interval = setInterval(fetchHistoricalData, 4000);

        return () => clearInterval(interval);
    }, [symbol]);

    return (
        <div className="stock-chart">
            <Line
                data={chartData}
                options={{
                }}
                height={400}
                width={600}
            />
        </div>
    );
}

export default StockChart;

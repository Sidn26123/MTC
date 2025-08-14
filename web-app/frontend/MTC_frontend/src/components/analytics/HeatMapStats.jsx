import React from 'react';
import HeatMap from 'react-heatmap-grid';
import ChartContainer from './ChartContainer';

const days = ['CN', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7'];
const hours = Array.from({ length: 24 }, (_, i) => `${i}h`);

const HeatmapStats = ({ title, data }) => {
    const gridData = Array.from({ length: 7 }, () => Array(24).fill(0));
    data.forEach(d => {
        gridData[d.dayOfWeek][d.hour] = d.count;
    });

    return (
        <ChartContainer>
            <h3 className="font-semibold mb-3">{title}</h3>
            <HeatMap
                xLabels={hours}
                yLabels={days}
                data={gridData}
                squares
                height={30}
            />
        </ChartContainer>
    );
};

export default HeatmapStats;

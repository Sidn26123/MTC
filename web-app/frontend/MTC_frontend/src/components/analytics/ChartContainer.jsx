import React from 'react';

const ChartContainer = ({ children }) => {
    return (
        <div className="bg-white rounded-lg shadow p-4">
            {children}
        </div>
    );
};

export default ChartContainer;


import React from 'react';

const ChartToolbar = ({ title, filters, onFilterChange }) => {
    return (
        <div className="flex items-center justify-between mb-4 bg-gray-100 p-3 rounded-lg shadow">
            <h2 className="text-lg font-semibold">{title}</h2>
            <div className="flex items-center gap-3">
                {filters.map((filter) => (
                    <div key={filter.key}>
                        <label className="mr-2">{filter.label}</label>
                        <select
                            className="bg-white border border-gray-300 p-1 rounded"
                            value={filter.value}
                            onChange={(e) => onFilterChange(filter.key, e.target.value)}
                        >
                            {filter.options.map((opt) => (
                                <option key={opt} value={opt}>{opt}</option>
                            ))}
                        </select>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default ChartToolbar;

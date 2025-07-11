import React from 'react';

const LoadingSpinning = () => {
    return (
        <>
            <div>
                <div className="text-center my-6">
                    <svg className="inline animate-spin w-8 h-8 text-primary" xmlns="http://www.w3.org/2000/svg"
                         fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" aria-hidden="true"
                         data-slot="icon">
                        <path strokeLinecap="round" strokeLinejoin="round"
                              d="m21 7.5-2.25-1.313M21 7.5v2.25m0-2.25-2.25 1.313M3 7.5l2.25-1.313M3 7.5l2.25 1.313M3 7.5v2.25m9 3 2.25-1.313M12 12.75l-2.25-1.313M12 12.75V15m0 6.75 2.25-1.313M12 21.75V19.5m0 2.25-2.25-1.313m0-16.875L12 2.25l2.25 1.313M21 14.25v2.25l-2.25 1.313m-13.5 0L3 16.5v-2.25"></path>
                    </svg>
                </div>
            </div>
        </>
    )
}

export default LoadingSpinning;
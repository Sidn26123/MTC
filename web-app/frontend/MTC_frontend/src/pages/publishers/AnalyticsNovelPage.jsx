import React from "react";
import { DefaultNavigator } from '../../components/global/Navigators.jsx';
import { QuickStats } from '../../components/analytics/QuickStats.jsx';
import { OverviewStats } from '../../components/analytics/OverviewStats.jsx';
import { DetailedStats } from '../../components/analytics/DetailStats.jsx';

function AnalyticsNovelPage() {
    return (
        <>
            <div className="min-h-screen border-1 border-gray-500 border-rounded">

                <div className="container mx-auto px-4 py-8 space-y-8">
                    <OverviewStats />
                    <QuickStats />
                    <DetailedStats />

                </div>
            </div>
        </>
    );
}

export default AnalyticsNovelPage;
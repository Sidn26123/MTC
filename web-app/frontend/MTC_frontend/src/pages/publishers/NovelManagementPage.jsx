import { useCurrentPublishedNovel } from '../../stores/publisherStore.js';
import React from 'react';
import { NovelStat } from '../novels/NovelOverviewPage.jsx';
import AnalyticsNovelPage from './AnalyticsNovelPage.jsx';


export const NovelManagementPage = () => {
    const currentPublishedNovel = useCurrentPublishedNovel();
    console.log(currentPublishedNovel);
    return (
        <div>
            <h1>{currentPublishedNovel.name}</h1>
            <p>{currentPublishedNovel.description}</p>
            <br/>
            <AnalyticsNovelPage />
            {/*    Thông số cơ bản*/}
        {/*    Comment  & rating*/}
        {/*    Bảng dạng cột*/}
        </div>
    );
}


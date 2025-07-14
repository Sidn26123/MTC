import { Route } from 'react-router-dom';
import ProtectedRoute from '../components/global/ProtectedRoute.jsx';
import AdminLayout from '../layouts/AdminLayout.jsx';
import PublisherLayout from '../layouts/PublisherLayout.jsx';
import PublisherDashboard from '../pages/publishers/PublisherDashboard.jsx';
import PublishedNovelPage from '../pages/publishers/PublishedNovelListPage.jsx';
import PublishNewNovel from '../pages/publishers/PublishNewNovel.jsx';
import AnalyticsNovelPage from '../pages/publishers/AnalyticsNovelPage.jsx';
import AddChapterPage from '../pages/publishers/AddChapterPage.jsx';
import ChapterList from '../pages/publishers/ChapterList.jsx';
import ChapterEditPage from '../pages/publishers/ChapterEditPage.jsx';
import UpdateNovelInfoPage from '../pages/publishers/UpdateNovelInfoPage.jsx';
import ReportHandlePage from '../pages/publishers/ReportHandlePage.jsx';

const publisherRoutesPrefix = "/bookhub";

const AdminRoutes = ({ user }) => {
    return (
        // <Route element={<ProtectedRoute />}>
            <Route element={<PublisherLayout />}>
                <Route path={`${publisherRoutesPrefix}/dashboard`} element={<PublisherDashboard />} />
                <Route path={`${publisherRoutesPrefix}/published`} element={<PublishedNovelPage />} />
                <Route path={`${publisherRoutesPrefix}/new`} element={<PublishNewNovel />} />
                <Route path={`${publisherRoutesPrefix}/analytic`} element={<AnalyticsNovelPage />} />
                <Route path={`${publisherRoutesPrefix}/bao-cao`} element={<ReportHandlePage />} />
                <Route path={`${publisherRoutesPrefix}/ho-tro`} element={<AnalyticsNovelPage />} />
                <Route path={`${publisherRoutesPrefix}/novels/:novelSlug/upload-chapters`} element={<AddChapterPage />} />
                <Route path={`${publisherRoutesPrefix}/novels/:novelSlug/update`} element={<UpdateNovelInfoPage />} />
                <Route path={`${publisherRoutesPrefix}/novel/:novelSlug/chapters`} element={<ChapterList />} />
                <Route path={`${publisherRoutesPrefix}/chapters/:chapterId/edit`} element={<ChapterEditPage />} />
            </Route>
        // </Route>
    );
};

export default AdminRoutes;

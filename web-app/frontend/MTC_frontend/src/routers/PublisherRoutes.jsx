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
import DraftPage from '../pages/users/DraftPage.jsx';
import ManageDraftPage from '../pages/users/ManageDraftPage.jsx';

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
                {/*<Route path={`${publisherRoutesPrefix}/ban-nhap`} element={<ManageDraftPage />} />*/}
                {/*<Route path={`${publisherRoutesPrefix}/ban-nhap/tao`} element={<DraftPage />} />*/}
                {/*<Route path={`${publisherRoutesPrefix}/ban-nhap/:id`} element={<DraftPage />} />*/}
                <Route path={`${publisherRoutesPrefix}/ban-nhap`}>
                    <Route index element={<ManageDraftPage />} /> {/* /ban-nhap */}
                    <Route path="tao" element={<DraftPage />} />   {/* /ban-nhap/tao */}
                    <Route path=":id" element={<DraftPage />} />   {/* /ban-nhap/:id */}
                </Route>
                <Route path={`${publisherRoutesPrefix}/novels/:novelSlug/upload-chapters`} element={<AddChapterPage />} />
                <Route path={`${publisherRoutesPrefix}/novels/:novelSlug/update`} element={<UpdateNovelInfoPage />} />
                <Route path={`${publisherRoutesPrefix}/novels/:novelSlug/chapters`} element={<ChapterList />} />
                <Route path={`${publisherRoutesPrefix}/chapters/:chapterId/edit`} element={<ChapterEditPage />} />
            </Route>
        // </Route>
    );
};

export default AdminRoutes;

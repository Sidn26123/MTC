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
import ReportDetailPage from '../pages/publishers/ReportDetailPage.jsx';
import DraftPage from '../pages/users/DraftPage.jsx';
import ManageDraftPage from '../pages/users/ManageDraftPage.jsx';
import ReportListPage from '../pages/users/ReportListPage.jsx';

const publisherRoutesPrefix = "/bookhub";

const PublisherRoutes = ({ user }) => {
    const isAuthenticated = user && user.id;

    return (
        <Route element={<ProtectedRoute isAllowed={isAuthenticated} redirectTo={"/a"}/>}>
            <Route element={<PublisherLayout />}>
                <Route path={`${publisherRoutesPrefix}/dashboard`} element={<PublisherDashboard />} />
                <Route path={`${publisherRoutesPrefix}/published`} element={<PublishedNovelPage />} />
                <Route path={`${publisherRoutesPrefix}/new`} element={<PublishNewNovel />} />
                <Route path={`${publisherRoutesPrefix}/analytic`} element={<AnalyticsNovelPage />} />
                <Route path={`${publisherRoutesPrefix}/bao-cao`} element={<ReportListPage />} />
                <Route path={`${publisherRoutesPrefix}/bao-cao/:reportId`} element={<ReportDetailPage />} />
                <Route path={`${publisherRoutesPrefix}/ho-tro`} element={<AnalyticsNovelPage />} />
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
        </Route>
    );
};

export default PublisherRoutes;

import { Route } from 'react-router-dom';
import ContentModeratorDashboardPage from '../pages/contentModerators/ContentModeratorDashboardPage.jsx';
import ContentModeratorLayout from '../layouts/ContentModeratorLayout.jsx';
import ProtectedRoute from '../components/global/ProtectedRoute.jsx';
import PublisherLayout from '../layouts/PublisherLayout.jsx';

const contentModRoutesPrefix = "/mod";


const ContentModRoutes = () => {
    return (
        <Route element={<ProtectedRoute isAllowed={true} />}>
            <Route element={<ContentModeratorLayout />}>
                <Route path={`${contentModRoutesPrefix}/dashboard`} element={<ContentModeratorDashboardPage />} />
            </Route>
        </Route>
    );
}
export default ContentModRoutes;
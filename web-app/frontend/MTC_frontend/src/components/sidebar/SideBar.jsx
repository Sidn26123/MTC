
import { Link } from 'react-router-dom';

const Sidebar = () => (
  <div className="w-64 h-screen bg-gray-800 text-white p-4">
    <h2 className="text-xl font-bold mb-4">Admin</h2>
    <nav className="flex flex-col gap-2">
      <Link to="/admin/dashboard">Dashboard</Link>
      <Link to="/admin/users">Users</Link>
      <Link to="/admin/settings">Settings</Link>
    </nav>
  </div>
);

export default Sidebar;

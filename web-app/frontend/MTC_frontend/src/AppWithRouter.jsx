import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api, { setupInterceptors } from './middlewares/axios';
import App from './App';

const AppWithRouter = () => {
    const navigate = useNavigate();
    const [interceptorSetup, setInterceptorSetup] = useState(false);

    useEffect(() => {
        if (!interceptorSetup) {
            setupInterceptors(navigate);
            setInterceptorSetup(true);
        }
    }, [interceptorSetup, navigate]);

    return <App />;
};

export default AppWithRouter;
import React, { useEffect } from 'react';
import TicketTable from '../../components/feedbacks/TicketTable.jsx';
import { useTickets } from '../../stores/feedbackStore.js';

function ReportRequestPage() {
    const tickets = useTickets();
    useEffect(() => {

    }, []);
    return (
        <>
            <TicketTable tickets={tickets} />

        </>
    );
}

export default ReportRequestPage;
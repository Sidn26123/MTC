import React, { useEffect } from 'react';
import TicketTable from '../../components/feedbacks/TicketTable.jsx';
import { useTickets, useTicketsStatus } from '../../stores/feedbackStore.js';
import { useCurrentReportToHandle, useSetCurrentReportToHandle } from '../../stores/publisherStore.js';
import { filterMyReportToHandle } from '../../services/feedbackService.js';
import { getUserIdFromContext } from '../../services/authenticationService.js';
import { CategoryDropdown } from '../../common/CommonComponents.jsx';

function ReportRequestPage() {
    const currentReport = useCurrentReportToHandle();
    const setMyReport = useSetCurrentReportToHandle()
    const userId = getUserIdFromContext();
    const status = useTicketsStatus();


    var data = {
        page: 0,
        size: 10,
        status: "",
        sortBy: "createdAt",
        sortDirection: "DESC",
        role: "PUBLISHER",
        assignedTo: userId
    }
    useEffect(() => {
        filterMyReportToHandle(data).then((res) => {
            if (res && res.data) {
                setMyReport(res.data);
            } else {

            }
        })
    }, []);

    const handleStatusChange = (e) => {
        var newData = {
            ...data,
            status: e.id,
        };

        // useTicketStore.setState({ status: e.target.value });
        filterMyReportToHandle(newData).then(r => {
            setMyReport(r.data || []);
        });
    };

    return (
        <>
            <div className={'flex flex-col mr-2'}>
                <CategoryDropdown
                    dropdown={status}
                    placeholder={'Chọn thể loại'}
                    onSelect={(selected) => handleStatusChange(selected)}
                />
            </div>
            <TicketTable tickets={currentReport} />

        </>
    );
}

export default ReportRequestPage;
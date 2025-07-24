import React, { useEffect } from 'react';
import TicketTable from '../../components/feedbacks/TicketTable';
import { useIsLoading, useSetTickets, useTickets, useTicketsStatus } from '../../stores/feedbackStore.js';
import { fetchTickets } from '../../services/feedbackService.js';
import { CategoryDropdown } from '../../common/CommonComponents.jsx';
import { UserReport } from '../../components/feedbacks/Report.jsx';

const MyReportPage = () => {
    const isLoading = useIsLoading();
    const status = useTicketsStatus();
    const tickets = useTickets();
    const setTickets = useSetTickets();
    const [showReport, setShowReport] = React.useState(false);
    // d8b250b5-649b-4b0f-9063-491f02488ac7
    useEffect(() => {
        fetchTickets(status).then(r => {
            console.log("Tickets fetched:", r);
            setTickets(r.data.result || []);
        });
    }, [status]);

    const handleStatusChange = (e) => {
        // useTicketStore.setState({ status: e.target.value });
        fetchTickets(e.target.value).then(r => {});
    };

    return (
        <main className="px-4 mt-5">
            <div className="flex justify-between items-center bg-secondary h-12">
                <button className="bg-primary px-4 py-2 h-full font-medium uppercase text-white text-xs"
                    onClick={() => setShowReport(true)}
                >
                    Tạo Yêu Cầu Mới
                </button>

                <div className={'flex flex-col mr-2'}>
                    <CategoryDropdown
                        dropdown={status}
                        placeholder={'Chọn thể loại'}
                        onSelect={(selected) => handleStatusChange(selected)}
                    />
                </div>
            </div>

            {isLoading ? (
                <div className="flex justify-center my-4">
                    <svg
                        className="animate-spin w-8 h-8"
                        fill="none"
                        viewBox="0 0 24 24"
                        strokeWidth="1.5"
                        stroke="currentColor"
                    >
                        <path
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            d="M12 4v1m0 14v1m8-9h1M3 12H2m15.36 6.36l.707.707M4.93 4.93l.707.707m12.02 0l-.707.707M4.93 19.07l-.707.707"
                        />
                    </svg>
                </div>
            ) : tickets.length === 0 ? (
                <div className="text-center italic mt-6">
                    Không có yêu cầu hay báo cáo nào
                </div>
            ) : (
                <TicketTable tickets={tickets} />
            )}
            {showReport && (
                <UserReport onClose={()=>setShowReport(false)}/>
            )}
        </main>
    );
};

export default MyReportPage;
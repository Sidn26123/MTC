import React from 'react';
import TicketItem from './TicketItem';

const TicketTable = ({ tickets }) => {
    return (
        <div className="table w-full">
            <div className="table-header-group highlight">
                <div className="table-row">
                    <div className="table-cell text-left p-4 w-9/12">Thông tin</div>
                    <div className="table-cell text-left p-4 w-2/12">Thời gian</div>
                    <div className="table-cell text-left p-4 w-1/12">Tình trạng</div>
                </div>
            </div>
            <div className="table-row-group">
                {tickets && tickets.result && tickets.result.data.map((ticket, index) => (
                    <div
                        key={ticket.id}
                        className={`table-row ${
                            index % 2 === 0 ? 'bg-gray-500/50' : ''
                        } hover:bg-gray-500 transition`}
                    >
                        <TicketItem ticket={ticket} />
                    </div>
                ))}
            </div>

        </div>

    )

}

export default TicketTable;
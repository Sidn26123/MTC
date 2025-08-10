import React from 'react';
import dayjs from 'dayjs';
import { Link } from 'react-router-dom';

const TicketItem = ({ ticket }) => {
    return (
        <>
            <div className="table-cell p-4">
                <Link
                    to={`/yeu-cau-ho-tro/${ticket.id}`}
                    className="col-span-5 md:col-span-3 flex flex-col space-y-2 text-primary font-medium"
                >
                    <div>{ticket.title}</div>
                    <div className="text-xs">
                        {ticket.content ? ticket.content.substring(0, 199) : '...'}
                    </div>
                </Link>
            </div>
            <div className="table-cell p-4">
                {dayjs(ticket.created_at).format('YYYY-MM-DD HH:mm:ss')}
            </div>
            <div className="table-cell p-4">{ticket.status}</div>
        </>
    );
};


export default TicketItem;
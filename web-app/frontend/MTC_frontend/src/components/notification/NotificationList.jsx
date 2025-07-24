import React from 'react';
import dayjs from 'dayjs';

const NotificationList = ({
                              list = [],
                              markAllAsRead,
                              markAsRead,
                              remove,
                              goTo,
                          }) => {
    return (
        <div className="table w-full">
            <div className="table-header-group bg-secondary h-12 text-black">
                <div className="table-row">
                    <div className="table-cell text-left p-4 text-xs uppercase">Thông báo</div>
                    <div className="table-cell text-right align-middle px-4">
                        <button onClick={markAllAsRead} className="text-primary">
                            <svg className="w-5 h-5" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
                                <path strokeLinecap="round" strokeLinejoin="round" d="M9 12.75 11.25 15 15 9.75M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
                            </svg>
                        </button>
                    </div>
                </div>
            </div>

            <div className="table-row-group">
                {list.map((item, index) => (
                    <div key={index} className={`table-row ${!item.read_at ? 'highlight' : ''}`}>
                        <div onClick={() => goTo(item)} className="table-cell border-b border-auto p-4 cursor-pointer">
                            <div className="flex space-x-2 md:text-base">
                                {item.data?.image && (
                                    <img
                                        src={item.data.image}
                                        alt="notification"
                                        className="w-10 h-10 rounded-full aspect-square object-cover"
                                    />
                                )}
                                <div className="space-y-2">
                                    <div>
                                        <span className="font-medium dark:font-semibold">
                                          {item.title ? item.title + ': ' : ''}
                                        </span>
                                        <span>{item.data?.message}</span>
                                    </div>
                                    <div className="text-xs text-muted">
                                        {dayjs(item.created_at).format('YYYY-MM-DD HH:mm:ss')}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="table-cell border-b border-auto text-right p-4 space-x-1 align-middle">
                            <button onClick={() => markAsRead(item)} className="text-gray-500">
                                <svg className="w-5 h-5" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M9 12.75 11.25 15 15 9.75M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
                                </svg>
                            </button>
                            <button onClick={() => remove(item)} className="text-gray-500">
                                <svg className="w-5 h-5" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
                                    <path strokeLinecap="round" strokeLinejoin="round" d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166M4.772 5.79a48.11 48.11 0 0 1 3.478-.397M7.5 5.393v-.916c0-1.18.91-2.164 2.09-2.201a51.964 51.964 0 0 1 3.32 0c1.18.037 2.09 1.022 2.09 2.201v.916M4.772 5.79l1.068 13.883a2.25 2.25 0 0 0 2.244 2.077h7.832a2.25 2.25 0 0 0 2.244-2.077l1.068-13.883" />
                                </svg>
                            </button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default NotificationList;

import React from "react";

function ReportHandlePage() {
    const [showRequestDetail, setShowRequestDetail] = React.useState(false);

    return (
        <>
            <div>
                <div className={"flex flex-col gap-x-5 mt-3 rounded-md bg-background-light p-3"}>
                    <div className={"flex flex-col"}>
                        <h3>Bao cao loi</h3>
                        <span>Đây là các báo cáo lỗi bạn cần xử lý, ấn vào báo cáo để xem chi tiết.</span>
                    </div>
                    {!showRequestDetail ? (
                        <div className={"mt-4"}>
                            {/*<ReportHandleItem />*/}
                            <table className="w-full text-sm text-left ">
                                <thead
                                    className="text-sm text-gray-500">
                                <tr>

                                    <th scope="col" className="px-6 py-3">
                                        Thông tin
                                    </th>
                                    <th scope="col" className="px-6 py-3">
                                        TEN CHUONG
                                    </th>
                                    <th scope="col" className="px-6 py-3">
                                        XUAT BAN LUC
                                    </th>

                                </tr>
                                </thead>
                                <tbody>
                                <tr className="border-gray-500 hover:bg-gray-500/10 hover:cursor-pointer">

                                    <th scope="row"
                                        className="px-6 py-4 w-3/12 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                        <GeneralDetail />
                                    </th>
                                    <td className="px-6 py-4">
                                        <ReportContent />
                                    </td>
                                    <td className="px-6 py-4">
                                        <RequestState />
                                    </td>
                                </tr>

                                </tbody>
                            </table>

                        </div>
                    ) : (
                        <div className={'mt-4'}>

                        </div>
                    )}

                </div>
            </div>
        </>
    );
}

export default ReportHandlePage;

const RequestState = () => {
    return (
        <>
            <div>
                <div
                    className={'flex justify-center max-w-30 bg-green-500 p-1 rounded-md hover:cursor-pointer hover:bg-green-600'}>
                    <span>Đang xử lý</span>
                </div>
            </div>
        </>
    );
};

const GeneralDetail = () => {
    return (
        <>
            <div>
                <div className={'flex flex-col gap-y-2'}>
                    <div className={'flex flex-row gap-x-2'}>
                        <img
                            className="h-10 w-10 shadow-xl rounded"
                            loading="lazy"
                            src="https://static.cdnno.com/poster/quy-bi-chi-chu/150.jpg?1585206121"
                            alt=""
                        />
                        <div className={"flex flex-col text-yellow-500"}>
                            <span className={""}>Sidn</span>
                            <span className={"text-sm text-gray-400"}>Báo cáo nội dung truyện</span>
                        </div>
                    </div>

                </div>
            </div>
        </>
    );
};

const ReportContent = () => {
    return (
        <>
            <div>
                <div className={"flex flex-col"}>
                    <div className={"text-yellow-500"}>
                        Truyen abc - Chuong 1
                    </div>
                    <div>
                        <span>Mô tả: 12/12/2021</span>
                    </div>
                </div>
            </div>
        </>
    )
}

const ReportHandleItem = () => {
    return (
        <>
            <div className={''}>
                <div
                    className={'flex flex-row p-3 rounded-md gap-x-5 mt-3 rounded-md bg-background-light p-3  hover:bg-gray-500/10 hover:cursor-pointer'}>
                    <div className={'flex flex-col gap-y-2'}>
                        <span>Người báo cáo</span>
                        <span>Ngày báo cáo</span>
                        <span>Trạng thái</span>
                    </div>
                    <div className={'flex flex-col gap-y-2'}>
                        <span>Nguyễn Văn A</span>
                        <span>12/12/2021</span>
                        <span>Chưa xử lý</span>
                    </div>
                    <div className={'flex flex-col gap-y-2'}>
                        <span>Báo cáo</span>
                        <span>Ngày xử lý</span>
                        <span>Lý do</span>
                    </div>
                </div>
            </div>
        </>

    );
};

const ReportDetail = () => {
    return (
        <>
            <div>

            </div>
        </>
    );
};


const NovelContentDetail = () => {
    return (
        <>
            <div>
                <div className={"flex flex-col gap-x-5 mt-3 rounded-md bg-background-light p-3"}>
                    <div className={"flex flex-col"}>
                        <h3>Chi tiết nội dung truyện</h3>
                        <span>Đây là nội dung truyện bạn cần xem chi tiết.</span>
                    </div>
                    <div className={"mt-4"}>
                        <NovelContentDetailItem />
                    </div>
                </div>
            </div>
        </>
    )
}
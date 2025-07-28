import React from "react";

function PaymentHistoryPage() {
    const sampleData = [
        {
            id: "1dec14d7-0f30-4622-86be-5753ec96ba79",
            time: "14:23:45 25/07/2025",
            type: "Nạp tiền",
            amount: 50000,
            status: "Hoàn tất",
        },
        {
            id: "a2e1b88c-93c6-4cd0-bb7b-96f7e880f8a4",
            time: "16:52:57 25/07/2025",
            type: "Mua chương",
            amount: -3000,
            status: "Hoàn tất",
        },
    ];

    return (
        <>
            <div className="flex justify-between items-center bg-secondary text-black h-12">
                <div className="flex">
                    <button data-x-on:click="currentKey = 'default'"
                            data-x-bind:class="currentKey === 'default' &amp;&amp; 'bg-primary text-white'"
                            className="inline-flex items-center py-3 px-3 text-xs space-x-2 border-r bg-primary text-white">
                        <img src="https://assets.metruyencv.com/build/assets/potato-3246efaf.png" alt="KNBs"
                             className="w-auto h-5" /><span
                        data-x-text="numberFormat($store.account.userData.balance_default)"
                        className="bg-red-700 inline-flex items-center justify-center min-w-6 h-6 ms-2 px-2 text-xs font-semibold text-white rounded-full">0</span>
                    </button>

                </div>


            </div>
            <table
                className="w-full text-sm text-left rtl:text-right  border border-gray-200 dark:border-gray-700">
                <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-300">
                <tr>
                    <th scope="col" className="px-4 py-2">ID</th>
                    <th scope="col" className="px-4 py-2">Thời gian</th>
                    <th scope="col" className="px-4 py-2">Loại</th>
                    <th scope="col" className="px-4 py-2">Số lượng</th>
                    <th scope="col" className="px-4 py-2">Trạng thái</th>
                </tr>
                </thead>
                <tbody>
                {sampleData.map((item) => (
                    <tr key={item.id}
                        className="border-t border-gray-200 dark:border-gray-700">
                        <td className="px-4 py-2 font-mono">{item.id}</td>
                        <td className="px-4 py-2">{item.time}</td>
                        <td className="px-4 py-2">{item.type}</td>
                        <td className="px-4 py-2 text-right">{item.amount.toLocaleString()}</td>
                        <td className="px-4 py-2">{item.status}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </>
    );
}

export default PaymentHistoryPage;